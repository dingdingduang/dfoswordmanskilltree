package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.handeff;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.GeoTrailPlaneParticle;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import org.joml.Matrix4f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class HandEffEntity extends ProjectileHelper implements GeoEntity, IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<String> GEO_MODEL_FILE_PATH_NAME = SynchedEntityData.<String>defineId(HandEffEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> TEXTURE_FILE_PATH_NAME = SynchedEntityData.<String>defineId(HandEffEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> ANIMATION_FILE_PATH_NAME = SynchedEntityData.<String>defineId(HandEffEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> ANIMATION_NAME = SynchedEntityData.<String>defineId(HandEffEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Integer> LIFETIME = SynchedEntityData.<Integer>defineId(HandEffEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(HandEffEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(HandEffEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<String> MODEL_PART_NAME = SynchedEntityData.<String>defineId(HandEffEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<String> CLIENT_ACTION_NAME = SynchedEntityData.<String>defineId(HandEffEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<String> SHOULD_RENDER_FUNC_NAME = SynchedEntityData.<String>defineId(HandEffEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Boolean> HAS_TRAILING_PARTICLE = SynchedEntityData.<Boolean>defineId(HandEffEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> TRAILING_PARTICLE_LENGTH = SynchedEntityData.<Float>defineId(HandEffEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> TRAILING_PARTICLE_MAX_LIFETIME = SynchedEntityData.<Integer>defineId(HandEffEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TRAILING_PARTICLE_RGB_ALPHA_START_FADING_AT_X_TICKS = SynchedEntityData.<Integer>defineId(HandEffEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> TRAILING_PARTICLE_RGB_R = SynchedEntityData.<Float>defineId(HandEffEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TRAILING_PARTICLE_RGB_G = SynchedEntityData.<Float>defineId(HandEffEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TRAILING_PARTICLE_RGB_B = SynchedEntityData.<Float>defineId(HandEffEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TRAILING_PARTICLE_RGB_INITIAL_A = SynchedEntityData.<Float>defineId(HandEffEntity.class, EntityDataSerializers.FLOAT);

    private String GeoModelFilePathName = "geo/sword1.geo.json";
    private String TextureFilePathName = "textures/entity/skilleff/blademaster/tempest/sword/0.png";
    private String AnimationFilePath = "animations/nullentity.animation.json";
    private String AnimationName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int lifetime = 300;
    private int MaxLifeTime = 300;

    private float EntityScale = 1f;

    private String ModelPartName = HandEffGetModelPartMethods.LEFT_ARM;

    private String ClientActionName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private String ShouldRenderFuncName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private boolean HasTrailingParticle = false;
    private float TrailingParticleLength = 2f;
    private int TrailingParticleMaxLifetime = 20;
    private int TrailingParticleStartFadingAtRemainingTicks = 20;
    private float TrailingParticleColorRed = 1f;
    private float TrailingParticleColorGreen = 1f;
    private float TrailingParticleColorBlue = 1f;
    private float TrailingParticleColorAlpha = 1f;

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

    //client side
//    private Vector3f currentParticleP0 = null;
//    private Vector3f currentParticleP1 = null;
//    private Vector3f previousParticleP0 = null;
//    private Vector3f previousParticleP1 = null;

    private Matrix4f currentRotationMatrix4f = null;
    private Matrix4f previousRotationMatrix4f = null;

    private int geoParticleIndex = 0;
    private HashMap<Integer, GeoTrailPlaneParticle> geoTrailPlaneParticleHashMap = new HashMap<>();

    private GeoModelAnimationController<HandEffEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<HandEffEntity> getAnimationController() {
        return this.GeoAnimationController;
    }

    public HandEffEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public String getGeoModelFilePathName() { return this.GeoModelFilePathName; }
    public void setGeoModelFilePathName(String geoModelFilePathName) { this.GeoModelFilePathName = geoModelFilePathName; }
    public String getTextureFilePathName() { return this.TextureFilePathName; }
    public void setTextureFilePathName(String textureFilePathName) { this.TextureFilePathName = textureFilePathName; }
    public String getAnimationFilePath() { return this.AnimationFilePath; }
    public void setAnimationFilePath(String animationFilePath) { this.AnimationFilePath = animationFilePath; }
    public String getAnimationName() { return this.AnimationName; }
    public void setAnimationName(String animationName) { this.AnimationName = animationName; }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    public int getMaxLifeTime() { return this.MaxLifeTime; }
    public void setMaxLifeTime(int maxLifeTime) { this.MaxLifeTime = maxLifeTime; }

    public String getModelPartName() { return this.ModelPartName; }
    public void setModelPartName(String modelPartName) { this.ModelPartName = modelPartName; }

    public String getClientActionName() { return this.ClientActionName; }
    public void setClientActionName(String clientActionName) { this.ClientActionName = clientActionName; }

    public String getShouldRenderFuncName() { return this.ShouldRenderFuncName; }
    public void setShouldRenderFuncName(String shouldRenderFuncName) { this.ShouldRenderFuncName = shouldRenderFuncName; }

    public boolean hasTrailingParticle() { return this.HasTrailingParticle; }
    public void setHasTrailingParticle(boolean hasTrailingParticle) { this.HasTrailingParticle = hasTrailingParticle; }

    public float getTrailingParticleLength() { return this.TrailingParticleLength; }
    public void setTrailingParticleLength(float trailingParticleLength) { this.TrailingParticleLength = trailingParticleLength; }
    public int getTrailingParticleMaxLifetime() { return this.TrailingParticleMaxLifetime; }
    public void setTrailingParticleMaxLifetime(int trailingParticleMaxLifetime) { this.TrailingParticleMaxLifetime = trailingParticleMaxLifetime; }
    public int getTrailingParticleStartFadingAtRemainingTicks() { return this.TrailingParticleStartFadingAtRemainingTicks; }
    public void setTrailingParticleStartFadingAtRemainingTicks(int trailingParticleStartFadingAtRemainingTicks) { this.TrailingParticleStartFadingAtRemainingTicks = trailingParticleStartFadingAtRemainingTicks; }
    public float getTrailingParticleColorRed() { return this.TrailingParticleColorRed; }
    public void setTrailingParticleColorRed(float trailingParticleColorRed) { this.TrailingParticleColorRed = trailingParticleColorRed; }
    public float getTrailingParticleColorGreen() { return this.TrailingParticleColorGreen; }
    public void setTrailingParticleColorGreen(float trailingParticleColorGreen) { this.TrailingParticleColorGreen = trailingParticleColorGreen; }
    public float getTrailingParticleColorBlue() { return this.TrailingParticleColorBlue; }
    public void setTrailingParticleColorBlue(float trailingParticleColorBlue) { this.TrailingParticleColorBlue = trailingParticleColorBlue; }
    public float getTrailingParticleColorAlpha() { return this.TrailingParticleColorAlpha; }
    public void setTrailingParticleColorAlpha(float trailingParticleColorAlpha) { this.TrailingParticleColorAlpha = trailingParticleColorAlpha; }

    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

            if (this.lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
            }

            Entity owner = this.getOwner();
            if (owner != null) {
                moveEntityToPos(this, getEntityPos(owner));
            }

            if (this.EntityServerAction != null) {
                int currentTickTime = (this.MaxLifeTime - this.lifetime) / this.EntityServerActionTickPeriod;
                if (this.PreviousEntityActionTriggeredTick != currentTickTime) {
                    this.PreviousEntityActionTriggeredTick = currentTickTime;
                    this.EntityServerAction.executeAction(this);
                }
            }
        }
        else {
//            if (!isClientSetup) {
//                isClientSetup = true;
//                Entity owner = this.getOwner();
//                if (owner instanceof LivingEntity livingEntity) {
//                    SkillEffLayer.setLivingEntityOffHandGeoModel(livingEntity, this);
//                }
//            }

            if (this.lifetime > 1) {
                this.lifetime--;
            }

            MethodEntityAction ClientActionMethod = GeoModelClientMethodsRegistry.getGeoModelClientActionMethod(this.ClientActionName);
            if (ClientActionMethod != null) {
                ClientActionMethod.executeAction(this);
            }
        }
    }

    protected <E extends HandEffEntity> PlayState predicate(AnimationState<E> event) {
        event.setAnimation(RawAnimation.begin().thenLoop(this.AnimationName));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(this.GeoAnimationController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GEO_MODEL_FILE_PATH_NAME, "geo/sword1.geo.json");
        this.entityData.define(TEXTURE_FILE_PATH_NAME, "textures/entity/skilleff/blademaster/tempest/sword/0.png");
        this.entityData.define(ANIMATION_FILE_PATH_NAME, "animations/nullentity.animation.json");
        this.entityData.define(ANIMATION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        this.entityData.define(LIFETIME, 300);
        this.entityData.define(MAX_LIFETIME, 300);

        this.entityData.define(ENTITY_SCALE, 1f);

        this.entityData.define(MODEL_PART_NAME, HandEffGetModelPartMethods.LEFT_ARM);

        this.entityData.define(CLIENT_ACTION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);

        this.entityData.define(SHOULD_RENDER_FUNC_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);

        this.entityData.define(HAS_TRAILING_PARTICLE, false);
        this.entityData.define(TRAILING_PARTICLE_LENGTH, 2f);
        this.entityData.define(TRAILING_PARTICLE_MAX_LIFETIME, 20);
        this.entityData.define(TRAILING_PARTICLE_RGB_ALPHA_START_FADING_AT_X_TICKS, 20);
        this.entityData.define(TRAILING_PARTICLE_RGB_R, 1f);
        this.entityData.define(TRAILING_PARTICLE_RGB_G, 1f);
        this.entityData.define(TRAILING_PARTICLE_RGB_B, 1f);
        this.entityData.define(TRAILING_PARTICLE_RGB_INITIAL_A, 1f);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("GEOMODELFILEPATHNAME", this.GeoModelFilePathName);
        pCompound.putString("TEXTUREFILEPATHNAME", this.TextureFilePathName);
        pCompound.putString("ANIMATIONFILEPATHNAME", this.AnimationFilePath);
        pCompound.putString("ANIMATIONAME", this.AnimationName);
        pCompound.putInt("LIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifeTime);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        pCompound.putString("MODELPARTNAME", this.ModelPartName);

        //client action method
        pCompound.putString("CLIENTACTIONAME", this.ClientActionName);

        pCompound.putString("SHOULDRENDERFUNCNAME", this.ShouldRenderFuncName);

        pCompound.putBoolean("HASTRAILINGPARTICLE", this.HasTrailingParticle);
        pCompound.putFloat("TRAILINGPARTICLELENGTH", this.TrailingParticleLength);
        pCompound.putInt("TRAILINGPARTICLEMAXLIFETIME", this.TrailingParticleMaxLifetime);
        pCompound.putInt("TRAILINGPARTICLERGBALPHASTARTFADINGATXTICKS", this.TrailingParticleStartFadingAtRemainingTicks);
        pCompound.putFloat("TRAILINGPARTICLERGBR", this.TrailingParticleColorRed);
        pCompound.putFloat("TRAILINGPARTICLERGBG", this.TrailingParticleColorGreen);
        pCompound.putFloat("TRAILINGPARTICLERGBB", this.TrailingParticleColorBlue);
        pCompound.putFloat("TRAILINGPARTICLERGGA", this.TrailingParticleColorAlpha);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        //=================
        if (pCompound.contains("GEOMODELFILEPATHNAME", 8)) {
            String tempString001 = pCompound.getString("GEOMODELFILEPATHNAME");
            this.getEntityData().set(GEO_MODEL_FILE_PATH_NAME, tempString001);
            this.GeoModelFilePathName = tempString001;
        }

        if (pCompound.contains("TEXTUREFILEPATHNAME", 8)) {
            String tempTexturePath = pCompound.getString("TEXTUREFILEPATHNAME");
            this.getEntityData().set(TEXTURE_FILE_PATH_NAME, tempTexturePath);
            this.TextureFilePathName = tempTexturePath;
        }

        if (pCompound.contains("ANIMATIONFILEPATHNAME", 8)) {
            String tempString001 = pCompound.getString("ANIMATIONFILEPATHNAME");
            this.getEntityData().set(ANIMATION_FILE_PATH_NAME, tempString001);
            this.AnimationFilePath = tempString001;
        }

        if (pCompound.contains("ANIMATIONAME", 8)) {
            String tempString001 = pCompound.getString("ANIMATIONAME");
            this.getEntityData().set(ANIMATION_NAME, tempString001);
            this.AnimationName = tempString001;
        }

        if (pCompound.contains("LIFETIME", 99)) {
            int tempInt = pCompound.getInt("LIFETIME");
            this.getEntityData().set(LIFETIME, tempInt);
            this.lifetime = tempInt;
        }

        if (pCompound.contains("MAXLIFETIME", 99)) {
            int tempInt = pCompound.getInt("MAXLIFETIME");
            this.getEntityData().set(MAX_LIFETIME, tempInt);
            this.MaxLifeTime = tempInt;
        }

        //============
        if (pCompound.contains("ENTITYSCALE", 99)) {
            float tempFloat001 = pCompound.getFloat("ENTITYSCALE");
            this.getEntityData().set(ENTITY_SCALE, tempFloat001);
            this.EntityScale = tempFloat001;
        }

        //=================
        if (pCompound.contains("MODELPARTNAME", 8)) {
            String tempString001 = pCompound.getString("MODELPARTNAME");
            this.getEntityData().set(MODEL_PART_NAME, tempString001);
            this.ModelPartName = tempString001;
        }

        //=================
        if (pCompound.contains("CLIENTACTIONAME", 8)) {
            String tempString001 = pCompound.getString("CLIENTACTIONAME");
            this.getEntityData().set(CLIENT_ACTION_NAME, tempString001);
            this.ClientActionName = tempString001;
        }

        //=================
        if (pCompound.contains("SHOULDRENDERFUNCNAME", 8)) {
            String tempString001 = pCompound.getString("SHOULDRENDERFUNCNAME");
            this.getEntityData().set(SHOULD_RENDER_FUNC_NAME, tempString001);
            this.ShouldRenderFuncName = tempString001;
        }

        //=================
        if (pCompound.contains("HASTRAILINGPARTICLE", 1)) {
            boolean tempBool = pCompound.getBoolean("HASTRAILINGPARTICLE");
            this.getEntityData().set(HAS_TRAILING_PARTICLE, tempBool);
            this.HasTrailingParticle = tempBool;
        }

        if (pCompound.contains("TRAILINGPARTICLELENGTH", 99)) {
            float tempFloat001 = pCompound.getFloat("TRAILINGPARTICLELENGTH");
            this.getEntityData().set(TRAILING_PARTICLE_LENGTH, tempFloat001);
            this.TrailingParticleLength = tempFloat001;
        }

        if (pCompound.contains("TRAILINGPARTICLEMAXLIFETIME", 99)) {
            int tempInt = pCompound.getInt("TRAILINGPARTICLEMAXLIFETIME");
            this.getEntityData().set(TRAILING_PARTICLE_MAX_LIFETIME, tempInt);
            this.TrailingParticleMaxLifetime = tempInt;
        }

        if (pCompound.contains("TRAILINGPARTICLERGBALPHASTARTFADINGATXTICKS", 99)) {
            int tempInt = pCompound.getInt("TRAILINGPARTICLERGBALPHASTARTFADINGATXTICKS");
            this.getEntityData().set(TRAILING_PARTICLE_RGB_ALPHA_START_FADING_AT_X_TICKS, tempInt);
            this.TrailingParticleStartFadingAtRemainingTicks = tempInt;
        }

        if (pCompound.contains("TRAILINGPARTICLERGBR", 99)) {
            float tempFloat001 = pCompound.getFloat("TRAILINGPARTICLERGBR");
            this.getEntityData().set(TRAILING_PARTICLE_RGB_R, tempFloat001);
            this.TrailingParticleColorRed = tempFloat001;
        }

        if (pCompound.contains("TRAILINGPARTICLERGBG", 99)) {
            float tempFloat001 = pCompound.getFloat("TRAILINGPARTICLERGBG");
            this.getEntityData().set(TRAILING_PARTICLE_RGB_G, tempFloat001);
            this.TrailingParticleColorGreen = tempFloat001;
        }

        if (pCompound.contains("TRAILINGPARTICLERGBB", 99)) {
            float tempFloat001 = pCompound.getFloat("TRAILINGPARTICLERGBB");
            this.getEntityData().set(TRAILING_PARTICLE_RGB_B, tempFloat001);
            this.TrailingParticleColorBlue = tempFloat001;
        }

        if (pCompound.contains("TRAILINGPARTICLERGBA", 99)) {
            float tempFloat001 = pCompound.getFloat("TRAILINGPARTICLERGBA");
            this.getEntityData().set(TRAILING_PARTICLE_RGB_INITIAL_A, tempFloat001);
            this.TrailingParticleColorAlpha = tempFloat001;
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();
        this.addAdditionalSaveData(tag);
        buffer.writeNbt(tag);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        CompoundTag tag = additionalData.readNbt();
        this.readAdditionalSaveData(tag);
    }

    public int getGeoParticleIndex() { return this.geoParticleIndex; }
    public void setGeoParticleIndex(int geoParticleIndex) { this.geoParticleIndex = geoParticleIndex; }

//    public Vector3f getCurrentParticleP0() { return this.currentParticleP0; }
//    public void setCurrentParticleP0(Vector3f currentParticleP0) { this.currentParticleP0 = currentParticleP0; }
//    public Vector3f getCurrentParticleP1() { return this.currentParticleP1; }
//    public void setCurrentParticleP1(Vector3f currentParticleP1) { this.currentParticleP1 = currentParticleP1; }
//    public Vector3f getPreviousParticleP0() { return this.previousParticleP0; }
//    public void setPreviousParticleP0(Vector3f previousParticleP0) { this.previousParticleP0 = previousParticleP0; }
//    public Vector3f getPreviousParticleP1() { return this.previousParticleP1; }
//    public void setPreviousParticleP1(Vector3f previousParticleP1) { this.previousParticleP1 = previousParticleP1; }

    public Matrix4f getCurrentRotationMatrix4f() { return this.currentRotationMatrix4f; }
    public void setCurrentRotationMatrix4f(Matrix4f currentRotationMatrix4f) { this.currentRotationMatrix4f = currentRotationMatrix4f; }
    public Matrix4f getPreviousRotationMatrix4f() { return this.previousRotationMatrix4f; }
    public void setPreviousRotationMatrix4f(Matrix4f previousRotationMatrix4f) { this.previousRotationMatrix4f = previousRotationMatrix4f; }

    public HashMap<Integer, GeoTrailPlaneParticle> getGeoTrailPlaneParticleHashMap() { return this.geoTrailPlaneParticleHashMap; }
    public void setGeoTrailPlaneParticleHashMap(HashMap<Integer, GeoTrailPlaneParticle> geoTrailPlaneParticleHashMap) { this.geoTrailPlaneParticleHashMap = geoTrailPlaneParticleHashMap; }
}
