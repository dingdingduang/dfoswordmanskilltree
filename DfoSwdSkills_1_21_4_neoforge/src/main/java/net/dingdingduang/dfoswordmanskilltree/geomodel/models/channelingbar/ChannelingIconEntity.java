package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistryName;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleMethods;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.dingdingduang.somebasicskills.globalmethods.GeneralMethods;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class ChannelingIconEntity extends ProjectileHelper implements GeoEntity, IEntityWithComplexSpawn {
    //color
    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(ChannelingIconEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Integer> REMAINING_LIFETIME = SynchedEntityData.<Integer>defineId(ChannelingIconEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(ChannelingIconEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<String> TEXTURE_NAME = SynchedEntityData.<String>defineId(ChannelingIconEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Boolean> HAS_TARGET_LOC_XYZ = SynchedEntityData.<Boolean>defineId(ChannelingIconEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> START_POS_X = SynchedEntityData.<Float>defineId(ChannelingIconEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_POS_Y = SynchedEntityData.<Float>defineId(ChannelingIconEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_POS_Z = SynchedEntityData.<Float>defineId(ChannelingIconEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_X = SynchedEntityData.<Float>defineId(ChannelingIconEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Y = SynchedEntityData.<Float>defineId(ChannelingIconEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Z = SynchedEntityData.<Float>defineId(ChannelingIconEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Boolean> IS_BOUND_TO_ENTITY = SynchedEntityData.<Boolean>defineId(ChannelingIconEntity.class, EntityDataSerializers.BOOLEAN);

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int lifetime = 12;
    private int MaxLifetime = 12;

    private String TextureName = GeoModelRegistryName.ENTITY_CHANNELING_BAR_TEXTURE;

    private float EntityScale = 1f;

    private boolean HasTargetLocXYZ = false;
    private float StartPosX = 0f;
    private float StartPosY = 0f;
    private float StartPosZ = 0f;
    private float TargetPosX = 0f;
    private float TargetPosY = 0f;
    private float TargetPosZ = 0f;

    private boolean IsBoundToEntity = false;

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

    private int clientOrderSequence = 0;

    private GeoModelAnimationController<ChannelingIconEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<ChannelingIconEntity> getAnimationController() { return this.GeoAnimationController; }

    public ChannelingIconEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetimeTicks) { this.lifetime = lifetimeTicks; }
    public int getMaxLifetime() { return this.MaxLifetime; }
    public void setMaxLifetime(int maxLifetime) { this.MaxLifetime = maxLifetime; }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public String getTextureName() { return this.TextureName; }
    public void setTextureName(String textureName) { this.TextureName = textureName; }

    public void setStartPosLoc(float x1, float y1, float z1) {
        this.StartPosX = x1;
        this.StartPosY = y1;
        this.StartPosZ = z1;
    }
    public float getStartPosX() { return this.StartPosX; }
    public void setStartPosX(float StartPosX) { this.StartPosX = StartPosX; }
    public float getStartPosY() { return this.StartPosY; }
    public void setStartPosY(float StartPosY) { this.StartPosY = StartPosY; }
    public float getStartPosZ() { return this.StartPosZ; }
    public void setStartPosZ(float StartPosZ) { this.StartPosZ = StartPosZ; }
    public boolean hasTargetLocXYZ() { return this.HasTargetLocXYZ; }
    public void setHasTargetLocXYZBoolean(boolean a) { this.HasTargetLocXYZ = a; }
    public void setTargetLoc(float start_x1, float start_y1, float start_z1, float target_x1, float target_y1, float target_z1, boolean shouldMove) {
        this.HasTargetLocXYZ = shouldMove;
        setStartPosLoc(start_x1, start_y1, start_z1);
        this.TargetPosX = target_x1;
        this.TargetPosY = target_y1;
        this.TargetPosZ = target_z1;
    }
    public float getTargetPosX() { return this.TargetPosX; }
    public void setTargetPosX(float targetPosX) { this.TargetPosX = targetPosX; }
    public float getTargetPosY() { return this.TargetPosY; }
    public void setTargetPosY(float targetPosY) { this.TargetPosY = targetPosY; }
    public float getTargetPosZ() { return this.TargetPosZ; }
    public void setTargetPosZ(float targetPosZ) { this.TargetPosZ = targetPosZ; }

    public boolean isBoundToEntity() { return this.IsBoundToEntity; }
    public void setIsBoundToEntity(boolean boundToEntity) { this.IsBoundToEntity = boundToEntity; }

    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }

    public int getClientOrderSequence() { return this.clientOrderSequence; }
    public void setClientOrderSequence(int clientOrderSequence) { this.clientOrderSequence = clientOrderSequence; }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

            if (!this.HasTargetLocXYZ && this.IsBoundToEntity) {
                Entity owner = this.getOwner();
                if (owner != null) {
                    moveEntityToPos(this, getEntityPos(owner).add(this.TargetPosX, this.TargetPosY, this.TargetPosZ));
                }
            }

            if (this.lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
            }

            if (EntityServerAction != null) {
                int currentTickTime = (this.MaxLifetime - this.lifetime) / this.EntityServerActionTickPeriod;
                if (this.PreviousEntityActionTriggeredTick != currentTickTime) {
                    this.PreviousEntityActionTriggeredTick = currentTickTime;
                    this.EntityServerAction.executeAction(this);
                }
            }
        }
        else {
//            Entity owner = this.getOwner();
//            if (owner != null) {
//                if (this.lifetime == 2 || this.lifetime == 1) {
//                    GlobalClientMaps.getChannelingIconOrder().remove(owner);
//                }
//                else {
//                    GlobalClientMaps.getChannelingIconOrder().put(owner, 0);
//                }
//            }
            if (this.lifetime == this.MaxLifetime) {
                Vector3f entityPos = getEntityPos(this);
                ParticleMethods.GenerateHitParticle(entityPos.x(), entityPos.y() + 0.5f, entityPos.z(), ExtraMathMethods.randomInt(5) + 10);
            }
            if (this.lifetime > 1) { this.lifetime--; }
        }
    }

    protected <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
//        event.setAnimation(RawAnimation.begin().thenPlayAndHold(this.AnimationName));
        if (GeneralMethods.isCamMirror()) {
            event.setAnimation(RawAnimation.begin().thenPlayAndHold(GeoAnimationAndTextureName.ICON_ENTITY_ANIMATION_Y_180DEG));
        } else {
            event.setAnimation(RawAnimation.begin().thenPlayAndHold(GeoAnimationAndTextureName.ICON_ENTITY_ANIMATION_NO_ACTION));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(GeoAnimationController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder syncBulider) {
        super.defineSynchedData(syncBulider);

        syncBulider.define(ENTITY_SCALE, 1f);

        syncBulider.define(REMAINING_LIFETIME, 12);
        syncBulider.define(MAX_LIFETIME, 12);

        syncBulider.define(TEXTURE_NAME, "textures/entity/slash/white/0_0");

        syncBulider.define(START_POS_X, getEntityX(this));
        syncBulider.define(START_POS_Y, getEntityY(this));
        syncBulider.define(START_POS_Z, getEntityZ(this));
        syncBulider.define(HAS_TARGET_LOC_XYZ, false);
        syncBulider.define(TARGET_POS_X, getEntityX(this));
        syncBulider.define(TARGET_POS_Y, getEntityY(this));
        syncBulider.define(TARGET_POS_Z, getEntityZ(this));

        syncBulider.define(IS_BOUND_TO_ENTITY, false);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        pCompound.putInt("REMAININGLIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifetime);

        pCompound.putString("TEXTURENAME", this.TextureName);

        //origin pos vec
        pCompound.putFloat("STARTPOSX", this.StartPosX);
        pCompound.putFloat("STARTPOSY", this.StartPosY);
        pCompound.putFloat("STARTPOSZ", this.StartPosZ);
        //target pos
        pCompound.putBoolean("HASTARGETLOCXYZ", this.HasTargetLocXYZ);
        pCompound.putFloat("TARGETPOSX", this.TargetPosX);
        pCompound.putFloat("TARGETPOSY", this.TargetPosY);
        pCompound.putFloat("TARGETPOSZ", this.TargetPosZ);

        //bound entity
        pCompound.putBoolean("ISBOUNDTOENTITY", this.IsBoundToEntity);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        //============
        if (pCompound.contains("ENTITYSCALE", 99)) {
            float tempFloat001 = pCompound.getFloat("ENTITYSCALE");
            this.getEntityData().set(ENTITY_SCALE, tempFloat001);
            this.EntityScale = tempFloat001;
        }

        //===========
        if (pCompound.contains("REMAININGLIFETIME", 99)) {
            int tempInt001 = pCompound.getInt("REMAININGLIFETIME");
            this.getEntityData().set(REMAINING_LIFETIME, tempInt001);
            this.lifetime = tempInt001;
        }

        if (pCompound.contains("MAXLIFETIME", 99)) {
            int tempInt001 = pCompound.getInt("MAXLIFETIME");
            this.getEntityData().set(MAX_LIFETIME, tempInt001);
            this.MaxLifetime = tempInt001;
        }

        //==================
        if (pCompound.contains("TEXTURENAME", 8)) {
            String tempString001 = pCompound.getString("TEXTURENAME");
            this.getEntityData().set(TEXTURE_NAME, tempString001);
            this.TextureName = tempString001;
        }

        //===================
        if (pCompound.contains("STARTPOSX", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSX");
            this.getEntityData().set(START_POS_X, tempFloat001);
            this.StartPosX = tempFloat001;
        }

        if (pCompound.contains("STARTPOSY", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSY");
            this.getEntityData().set(START_POS_Y, tempFloat001);
            this.StartPosY = tempFloat001;
        }

        if (pCompound.contains("STARTPOSZ", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSZ");
            this.getEntityData().set(START_POS_Z, tempFloat001);
            this.StartPosZ = tempFloat001;
        }

        if (pCompound.contains("HASTARGETLOCXYZ", 1)) {
            boolean tempBool = pCompound.getBoolean("HASTARGETLOCXYZ");
            this.getEntityData().set(HAS_TARGET_LOC_XYZ, tempBool);
            this.HasTargetLocXYZ = tempBool;
        }

        if (pCompound.contains("TARGETPOSX", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETPOSX");
            this.getEntityData().set(TARGET_POS_X, tempFloat001);
            this.TargetPosX = tempFloat001;
        }

        if (pCompound.contains("TARGETPOSY", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETPOSY");
            this.getEntityData().set(TARGET_POS_Y, tempFloat001);
            this.TargetPosY = tempFloat001;
        }

        if (pCompound.contains("TARGETPOSZ", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETPOSZ");
            this.getEntityData().set(TARGET_POS_Z, tempFloat001);
            this.TargetPosZ = tempFloat001;
        }

        //=================
        if (pCompound.contains("ISBOUNDTOENTITY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISBOUNDTOENTITY");
            this.getEntityData().set(IS_BOUND_TO_ENTITY, tempBool);
            this.IsBoundToEntity = tempBool;
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
