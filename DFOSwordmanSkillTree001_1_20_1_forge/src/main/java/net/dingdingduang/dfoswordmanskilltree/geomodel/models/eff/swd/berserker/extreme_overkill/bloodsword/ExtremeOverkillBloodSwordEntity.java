package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.berserker.extreme_overkill.bloodsword;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class ExtremeOverkillBloodSwordEntity extends ProjectileHelper implements GeoEntity, IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Float> POS_HORIZONTAL_DEG = SynchedEntityData.<Float>defineId(ExtremeOverkillBloodSwordEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Integer> LIFETIME = SynchedEntityData.<Integer>defineId(ExtremeOverkillBloodSwordEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(ExtremeOverkillBloodSwordEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(ExtremeOverkillBloodSwordEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> TARGET_CENTER_POS_X = SynchedEntityData.<Float>defineId(ExtremeOverkillBloodSwordEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_CENTER_POS_Y = SynchedEntityData.<Float>defineId(ExtremeOverkillBloodSwordEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_CENTER_POS_Z = SynchedEntityData.<Float>defineId(ExtremeOverkillBloodSwordEntity.class, EntityDataSerializers.FLOAT);

//    private static final EntityDataAccessor<Float> AOE_RANGE = SynchedEntityData.<Float>defineId(BloodTwisterEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<String> CLIENT_ACTION_NAME = SynchedEntityData.<String>defineId(ExtremeOverkillBloodSwordEntity.class, EntityDataSerializers.STRING);

    public static final String[] EXTREME_OVERKILL_BLOOD_SWORD_STATES = {"state_0", "state_1", "state_2", "state_3", "state_4", "state_5", "state_6", "state_7", "state_8"};

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int lifetime = 300;
    private int MaxLifeTime = 300;

    private float EntityScale = 1f;

    private float TargetCenterPosX = 0f;
    private float TargetCenterPosY = 0f;
    private float TargetCenterPosZ = 0f;

    private String ClientActionName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

//    private float AoeRange = 8f;
    private int ExtremeOverkillClientAbsorptionCount = 0;
    private int ExtremeOverkillServerAbsorptionCount = 0;

    private GeoModelAnimationController<ExtremeOverkillBloodSwordEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<ExtremeOverkillBloodSwordEntity> getAnimationController() {
        return this.GeoAnimationController;
    }

    public ExtremeOverkillBloodSwordEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

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

//    public float getAoeRange() { return this.AoeRange; }
//    public void setAoeRange(float aoeRange) { this.AoeRange = aoeRange; }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            Entity owner = this.getOwner();
            if (owner != null) {
                float horizontalFacingDeg = getEntityHorizontalFacingDeg(owner);
                float newX = Vector3fMethods.PolarProjectionFromXHorizon(-1.2f, horizontalFacingDeg);
                float newZ = Vector3fMethods.PolarProjectionFromZHorizon(-1.2f, horizontalFacingDeg);
                moveEntityToPos(this, getEntityPos(owner).add(newX, 0, newZ));
            }

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

    protected <E extends ExtremeOverkillBloodSwordEntity> PlayState predicate(AnimationState<E> event) {
        event.setAnimation(RawAnimation.begin().thenLoop(EXTREME_OVERKILL_BLOOD_SWORD_STATES[this.ExtremeOverkillClientAbsorptionCount]));
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
        this.entityData.define(POS_HORIZONTAL_DEG, 0f);

        this.entityData.define(LIFETIME, 300);
        this.entityData.define(MAX_LIFETIME, 300);

        this.entityData.define(ENTITY_SCALE, 1f);

        this.entityData.define(TARGET_CENTER_POS_X, getEntityX(this));
        this.entityData.define(TARGET_CENTER_POS_Y, getEntityY(this));
        this.entityData.define(TARGET_CENTER_POS_Z, getEntityZ(this));

//        this.entityData.define(AOE_RANGE, 8f);

        this.entityData.define(CLIENT_ACTION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));

        pCompound.putInt("LIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifeTime);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        //target pos
        pCompound.putFloat("TARGETCENTERPOSX", this.TargetCenterPosX);
        pCompound.putFloat("TARGETCENTERPOSY", this.TargetCenterPosY);
        pCompound.putFloat("TARGETCENTERPOSZ", this.TargetCenterPosZ);

        //aoe range
//        pCompound.putFloat("AOERANGE", this.AoeRange);

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
//        if (pCompound.contains("AOERANGE", 99)) {
//            float tempFloat001 = pCompound.getFloat("AOERANGE");
//            this.getEntityData().set(AOE_RANGE, tempFloat001);
//            this.AoeRange = tempFloat001;
//        }

        //=================
        if (pCompound.contains("CLIENTACTIONAME", 8)) {
            String tempString001 = pCompound.getString("CLIENTACTIONAME");
            this.getEntityData().set(CLIENT_ACTION_NAME, tempString001);
            this.ClientActionName = tempString001;
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

    public int getExtremeOverkillClientAbsorptionCount() {
        return this.ExtremeOverkillClientAbsorptionCount;
    }

    public void setExtremeOverkillClientAbsorptionCount(int extremeOverkillClientAbsorptionCount) {
        this.ExtremeOverkillClientAbsorptionCount = Math.min(extremeOverkillClientAbsorptionCount, EXTREME_OVERKILL_BLOOD_SWORD_STATES.length - 1);
    }

    public int getExtremeOverkillServerAbsorptionCount() {
        return this.ExtremeOverkillServerAbsorptionCount;
    }

    public void setExtremeOverkillServerAbsorptionCount(int extremeOverkillServerAbsorptionCount) {
        this.ExtremeOverkillServerAbsorptionCount = extremeOverkillServerAbsorptionCount;
    }
}
