package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.minecraft.world.level.Level;

import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class FancySwordEntity extends ProjectileHelper implements GeoEntity, IEntityWithComplexSpawn {
    private static final EntityDataAccessor<Float> POS_HORIZONTAL_DEG = SynchedEntityData.<Float>defineId(FancySwordEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> POS_VERTICAL_DEG = SynchedEntityData.<Float>defineId(FancySwordEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROTATION_Z = SynchedEntityData.<Float>defineId(FancySwordEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<String> GEO_MODEL_NAME = SynchedEntityData.<String>defineId(FancySwordEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> ANIMATION_NAME = SynchedEntityData.<String>defineId(FancySwordEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> TEXTURE_NAME = SynchedEntityData.<String>defineId(FancySwordEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> LIFETIME = SynchedEntityData.<Integer>defineId(FancySwordEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(FancySwordEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(FancySwordEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> TARGET_CENTER_POS_X = SynchedEntityData.<Float>defineId(FancySwordEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_CENTER_POS_Y = SynchedEntityData.<Float>defineId(FancySwordEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_CENTER_POS_Z = SynchedEntityData.<Float>defineId(FancySwordEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> AOE_RANGE = SynchedEntityData.<Float>defineId(FancySwordEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<String> CLIENT_ACTION_NAME = SynchedEntityData.<String>defineId(FancySwordEntity.class, EntityDataSerializers.STRING);

    private float RotationZ = 0f;

    private String GeoModelName = "textures/entity/skilleff/blademaster/tempest/geo/sword1.geo.json";
    private String AnimationName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;
    private String TextureName = "textures/entity/skilleff/blademaster/tempest/sword/0.png";

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int lifetime = 300;
    private int MaxLifeTime = 300;

    private float EntityScale = 1f;

    private float TargetCenterPosX = 0f;
    private float TargetCenterPosY = 0f;
    private float TargetCenterPosZ = 0f;

    private float FromPosX = 0f;
    private float FromPosY = 0f;
    private float FromPosZ = 0f;

    private float ToPosX = 0f;
    private float ToPosY = 0f;
    private float ToPosZ = 0f;

    private float PreviousToPosX = 0f;
    private float PreviousToPosY = 0f;
    private float PreviousToPosZ = 0f;

    private String ClientActionName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

    private float AoeRange = 8f;

    //client value
    private boolean hasClientNextMovingPos = false;
    private float clientTotalDistHasTraveled = 0f;

    private GeoModelAnimationController<FancySwordEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<FancySwordEntity> getAnimationController() {
        return this.GeoAnimationController;
    }

    public FancySwordEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    public float getRotationZ() { return this.RotationZ; }
    public void setRotationZ(float rotationZ) { this.RotationZ = rotationZ; }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public String getGeoModelName() { return this.GeoModelName; }
    public void setGeoModelName(String geoModelName) { this.GeoModelName = geoModelName; }
    public String getAnimationName() { return this.AnimationName; }
    public void setAnimationName(String animationName) { this.AnimationName = animationName; }
    public String getTextureName() { return this.TextureName; }
    public void setTextureName(String textureName) { this.TextureName = textureName; }

    public void setTargetLoc(float target_x1, float target_y1, float target_z1) {
        this.TargetCenterPosX = target_x1;
        this.TargetCenterPosY = target_y1;
        this.TargetCenterPosZ = target_z1;
    }
    public float getTargetCenterPosX() { return this.TargetCenterPosX; }
    public void setTargetCenterPosX(float targetCenterPosX) { this.TargetCenterPosX = targetCenterPosX; }
    public float getTargetCenterPosY() { return this.TargetCenterPosY; }
    public void setTargetCenterPosY(float targetCenterPosY) { this.TargetCenterPosY = targetCenterPosY; }
    public float getTargetCenterPosZ() { return this.TargetCenterPosZ; }
    public void setTargetCenterPosZ(float targetCenterPosZ) { this.TargetCenterPosZ = targetCenterPosZ; }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    public int getMaxLifeTime() { return this.MaxLifeTime; }
    public void setMaxLifeTime(int maxLifeTime) { this.MaxLifeTime = maxLifeTime; }

    public String getClientActionName() { return this.ClientActionName; }
    public void setClientActionName(String clientActionName) { this.ClientActionName = clientActionName; }

    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }

    public float getAoeRange() { return this.AoeRange; }
    public void setAoeRange(float aoeRange) { this.AoeRange = aoeRange; }

    //client
    public float getClientTotalDistHasTraveled() { return this.clientTotalDistHasTraveled; }
    public void setClientTotalDistHasTraveled(float clientTotalDistHasTraveled) { this.clientTotalDistHasTraveled = clientTotalDistHasTraveled; }

    public boolean hasClientNextMovingPos() { return this.hasClientNextMovingPos; }
    public void setClientHasNextMovingPos(boolean HasClientNextMovingPos) { this.hasClientNextMovingPos = HasClientNextMovingPos; }

    public void setFromToPositions(float fromPosX, float fromPosY, float fromPosZ, float toPosX, float toPosY, float toPosZ) {
        this.FromPosX = fromPosX;
        this.FromPosY = fromPosY;
        this.FromPosZ = fromPosZ;
        this.ToPosX = toPosX;
        this.ToPosY = toPosY;
        this.ToPosZ = toPosZ;
    }
    public float getFromPosX() { return this.FromPosX; }
    public void setFromPosX(float fromPosX) { this.FromPosX = fromPosX; }
    public float getFromPosY() { return this.FromPosY; }
    public void setFromPosY(float fromPosY) { this.FromPosY = fromPosY; }
    public float getFromPosZ() { return this.FromPosZ; }
    public void setFromPosZ(float fromPosZ) { this.FromPosZ = fromPosZ; }
    public float getToPosX() { return this.ToPosX; }
    public void setToPosX(float toPosX) { this.ToPosX = toPosX; }
    public float getToPosY() { return this.ToPosY; }
    public void setToPosY(float toPosY) { this.ToPosY = toPosY; }
    public float getToPosZ() { return this.ToPosZ; }
    public void setToPosZ(float toPosZ) { this.ToPosZ = toPosZ; }

    public float getPreviousToPosX() { return this.PreviousToPosX; }
    public void setPreviousToPosX(float previousToPosX) { this.PreviousToPosX = previousToPosX; }
    public float getPreviousToPosY() { return this.PreviousToPosY; }
    public void setPreviousToPosY(float previousToPosY) { this.PreviousToPosY = previousToPosY; }
    public float getPreviousToPosZ() { return this.PreviousToPosZ; }
    public void setPreviousToPosZ(float previousToPosZ) { this.PreviousToPosZ = previousToPosZ; }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

            if (this.lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
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
            if (this.lifetime > 1) {
                this.lifetime--;
            }

            MethodEntityAction ClientActionMethod = GeoModelClientMethodsRegistry.getGeoModelClientActionMethod(this.ClientActionName);
            if (ClientActionMethod != null) {
                ClientActionMethod.executeAction(this);
            }
        }
    }

    protected <E extends FancySwordEntity> PlayState predicate(AnimationState<E> event) {
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
    protected void defineSynchedData(SynchedEntityData.Builder syncBulider) {
        super.defineSynchedData(syncBulider);
        syncBulider.define(POS_HORIZONTAL_DEG, 0f);
        syncBulider.define(POS_VERTICAL_DEG, 0f);
        syncBulider.define(ROTATION_Z, 0f);

        syncBulider.define(GEO_MODEL_NAME, "textures/entity/skilleff/blademaster/tempest/geo/sword1.geo.json");
        syncBulider.define(ANIMATION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        syncBulider.define(TEXTURE_NAME, "textures/entity/skilleff/blademaster/tempest/sword/0.png");
        syncBulider.define(LIFETIME, 300);
        syncBulider.define(MAX_LIFETIME, 300);

        syncBulider.define(ENTITY_SCALE, 1f);

        syncBulider.define(TARGET_CENTER_POS_X, getEntityX(this));
        syncBulider.define(TARGET_CENTER_POS_Y, getEntityY(this));
        syncBulider.define(TARGET_CENTER_POS_Z, getEntityZ(this));

        syncBulider.define(AOE_RANGE, 8f);

        syncBulider.define(CLIENT_ACTION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
        pCompound.putFloat("VERTICALDEG", getEntityVerticalFacingDeg(this));

        pCompound.putFloat("ROTATIONZ", this.RotationZ);

        pCompound.putString("GEOMODELNAME", this.GeoModelName);
        pCompound.putString("ANIMATIONAME", this.AnimationName);
        pCompound.putString("TEXTURENAME", this.TextureName);
        pCompound.putInt("LIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifeTime);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        //target pos
        pCompound.putFloat("TARGETCENTERPOSX", this.TargetCenterPosX);
        pCompound.putFloat("TARGETCENTERPOSY", this.TargetCenterPosY);
        pCompound.putFloat("TARGETCENTERPOSZ", this.TargetCenterPosZ);

        //aoe range
        pCompound.putFloat("AOERANGE", this.AoeRange);

        //client action method
        pCompound.putString("CLIENTACTIONAME", this.ClientActionName);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getEntityData().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
        }

        if (pCompound.contains("VERTICALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("VERTICALDEG");
            this.getEntityData().set(POS_VERTICAL_DEG, tempFloat001);
            setEntityVerticalFacingDeg(this, tempFloat001);
        }

        if (pCompound.contains("ROTATIONZ", 99)) {
            float tempFloat001 = pCompound.getFloat("ROTATIONZ");
            this.getEntityData().set(ROTATION_Z, tempFloat001);
            this.RotationZ = tempFloat001;
        }

        //=================
        if (pCompound.contains("GEOMODELNAME", 8)) {
            String tempString001 = pCompound.getString("GEOMODELNAME");
            this.getEntityData().set(GEO_MODEL_NAME, tempString001);
            this.GeoModelName = tempString001;
        }

        if (pCompound.contains("ANIMATIONAME", 8)) {
            String tempString001 = pCompound.getString("ANIMATIONAME");
            this.getEntityData().set(ANIMATION_NAME, tempString001);
            this.AnimationName = tempString001;
        }

        if (pCompound.contains("TEXTURENAME", 8)) {
            String tempTexturePath = pCompound.getString("TEXTURENAME");
            this.getEntityData().set(TEXTURE_NAME, tempTexturePath);
            this.TextureName = tempTexturePath;
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

        //===================
        if (pCompound.contains("TARGETCENTERPOSX", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETCENTERPOSX");
            this.getEntityData().set(TARGET_CENTER_POS_X, tempFloat001);
            this.TargetCenterPosX = tempFloat001;
        }

        if (pCompound.contains("TARGETCENTERPOSY", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETCENTERPOSY");
            this.getEntityData().set(TARGET_CENTER_POS_Y, tempFloat001);
            this.TargetCenterPosY = tempFloat001;
        }

        if (pCompound.contains("TARGETCENTERPOSZ", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETCENTERPOSZ");
            this.getEntityData().set(TARGET_CENTER_POS_Z, tempFloat001);
            this.TargetCenterPosZ = tempFloat001;
        }

        //===============
        if (pCompound.contains("AOERANGE", 99)) {
            float tempFloat001 = pCompound.getFloat("AOERANGE");
            this.getEntityData().set(AOE_RANGE, tempFloat001);
            this.AoeRange = tempFloat001;
        }

        //=================
        if (pCompound.contains("CLIENTACTIONAME", 8)) {
            String tempString001 = pCompound.getString("CLIENTACTIONAME");
            this.getEntityData().set(CLIENT_ACTION_NAME, tempString001);
            this.ClientActionName = tempString001;
        }
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();
        this.addAdditionalSaveData(tag);
        buffer.writeNbt(tag);
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
        CompoundTag tag = additionalData.readNbt();
        this.readAdditionalSaveData(tag);
    }
}
