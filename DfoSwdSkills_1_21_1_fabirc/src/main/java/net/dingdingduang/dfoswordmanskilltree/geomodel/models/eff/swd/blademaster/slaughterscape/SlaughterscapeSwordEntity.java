package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.dingdingduang.dfoswordmanskilltree.entity.EntityExtraSpawnData;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class SlaughterscapeSwordEntity extends ProjectileHelper implements GeoEntity, EntityExtraSpawnData {
    private static final TrackedData<Float> POS_HORIZONTAL_DEG = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> POS_VERTICAL_DEG = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Float> RANDOM_HIT_HEIGHT_OFFSET = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<String> ANIMATION_NAME = DataTracker.<String>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.<String>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> LIFETIME = DataTracker.<Integer>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MAX_LIFETIME = DataTracker.<Integer>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Float> ENTITY_SCALE = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Float> ADDITIONAL_POS_X = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ADDITIONAL_POS_Y = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ADDITIONAL_POS_Z = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Float> TARGET_CENTER_POS_X = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_CENTER_POS_Y = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_CENTER_POS_Z = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Integer> SWORD_POS_ID = DataTracker.<Integer>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TARGET_ENTITY_ID = DataTracker.<Integer>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> DEGREE_SHOULD_INCREMENT_EACH_TIME = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> SWORD_HIT_AOE_RANGE = DataTracker.<Float>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Boolean> IS_BOUND_TO_ENTITY = DataTracker.<Boolean>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<String> CLIENT_ACTION_NAME = DataTracker.<String>registerData(SlaughterscapeSwordEntity.class, TrackedDataHandlerRegistry.STRING);

    private float RandomHitHeightOffset = 0f;

    private String AnimationName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;
    private String TextureName = "textures/entity/skilleff/blademaster/tempest/sword/0.png";

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int lifetime = 300;
    private int MaxLifeTime = 300;

    private float EntityScale = 1f;

    private float AdditionalPosX = 0f;
    private float AdditionalPosY = 0f;
    private float AdditionalPosZ = 0f;

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

    private int SwordPositionID = 0;
    private int TargetEntityID = -1;
    private float DegreeShouldIncrementEachTime = 8f;
    private float SwordHitAoeRange = 2f;

    private boolean IsBoundToEntity = false;

    //server value
    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

    private float ServerDistHasTraveled = 0f;

    //client value
    private boolean hasClientNextMovingPos = false;
    private float clientTotalDistHasTraveled = 0f;
    private float clientScale = 0f;
    private int clientHitEntityID = -1;
    private boolean isHitOnTarget = false;

    private boolean isSpeedModifierApplied = false;
    private GeoModelAnimationController<SlaughterscapeSwordEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);
    private float GeoAnimationSpeedModifier = 1.0f;

    public GeoModelAnimationController<SlaughterscapeSwordEntity> getAnimationController() { return this.GeoAnimationController; }

    public float getRandomHitHeightOffset() { return this.RandomHitHeightOffset; }
    public void setRandomHitHeightOffset(float randomHitHeightOffset) { this.RandomHitHeightOffset = randomHitHeightOffset; }

    public SlaughterscapeSwordEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.noClip = true;
        this.intersectionChecked = false;
    }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public String getAnimationName() { return this.AnimationName; }
    public void setAnimationName(String animationName) { this.AnimationName = animationName; }
    public String getTextureName() { return this.TextureName; }
    public void setTextureName(String textureName) { this.TextureName = textureName; }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    public int getMaxLifeTime() { return this.MaxLifeTime; }
    public void setMaxLifeTime(int maxLifeTime) { this.MaxLifeTime = maxLifeTime; }

    public String getClientActionName() { return this.ClientActionName; }
    public void setClientActionName(String clientActionName) { this.ClientActionName = clientActionName; }

    public float getAdditionalPosX() { return this.AdditionalPosX; }
    public void setAdditionalPosX(float additionalPosX) { this.AdditionalPosX = additionalPosX; }
    public float getAdditionalPosY() { return this.AdditionalPosY; }
    public void setAdditionalPosY(float additionalPosY) { this.AdditionalPosY = additionalPosY; }
    public float getAdditionalPosZ() { return this.AdditionalPosZ; }
    public void setAdditionalPosZ(float additionalPosZ) { this.AdditionalPosZ = additionalPosZ; }

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

    public float getDegreeShouldIncrementEachTime() { return this.DegreeShouldIncrementEachTime; }
    public void setDegreeShouldIncrementEachTime(float degreeShouldIncrementEachTime) { this.DegreeShouldIncrementEachTime = degreeShouldIncrementEachTime; }

    public int getSwordPositionID() { return this.SwordPositionID; }
    public void setSwordPositionID(int swordPositionID) { this.SwordPositionID = swordPositionID; }
    public float getSwordHitAoeRange() { return this.SwordHitAoeRange; }
    public void setSwordHitAoeRange(float swordHitAoeRange) { this.SwordHitAoeRange = swordHitAoeRange; }

    public LivingEntity getTargetEntity() {
        Entity targetEntity = getEntityByIntID(getEntityLevel(this), this.TargetEntityID);
        return (targetEntity instanceof LivingEntity livingEntity) ? livingEntity : null;
    }
    public int getTargetEntityID() { return this.TargetEntityID; }
    public void setTargetEntityID(int targetEntityID) { this.TargetEntityID = targetEntityID; }

    public boolean isBoundToEntity() { return this.IsBoundToEntity; }
    public void setIsBoundToEntity(boolean boundToEntity) { this.IsBoundToEntity = boundToEntity; }

    //server
    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }

    //client
    public float getGeoAnimationSpeedModifier() { return this.GeoAnimationSpeedModifier; }
    public void setGeoAnimationSpeedModifier(float geoAnimationSpeedModifier) {
        this.GeoAnimationSpeedModifier = geoAnimationSpeedModifier;
        this.isSpeedModifierApplied = false;
    }

    public float getClientTotalDistHasTraveled() { return this.clientTotalDistHasTraveled; }
    public void setClientTotalDistHasTraveled(float clientTotalDistHasTraveled) { this.clientTotalDistHasTraveled = clientTotalDistHasTraveled; }

    public boolean hasClientNextMovingPos() { return this.hasClientNextMovingPos; }
    public void setClientHasNextMovingPos(boolean HasClientNextMovingPos) { this.hasClientNextMovingPos = HasClientNextMovingPos; }

    public float getClientScale() { return this.clientScale; }
    public void setClientScale(float clientScale) { this.clientScale = clientScale; }

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

    public boolean isHitOnTarget() { return this.isHitOnTarget; }
    public void setHitOnTarget(boolean hitOnTarget) { this.isHitOnTarget = hitOnTarget; }

    public LivingEntity getClientTargetEntity() {
        Entity targetEntity = getEntityByIntID(getEntityLevel(this), this.clientHitEntityID);
        return (targetEntity instanceof LivingEntity livingEntity) ? livingEntity : null;
    }
    public int getClientHitEntityID() { return this.clientHitEntityID; }
    public void setClientHitEntityID(int clientHitEntityID) { this.clientHitEntityID = clientHitEntityID; }

    public float getServerDistHasTraveled() { return this.ServerDistHasTraveled; }
    public void setServerDistHasTraveled(float distHasTraveled) { this.ServerDistHasTraveled = distHasTraveled; }

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

    protected <E extends SlaughterscapeSwordEntity> PlayState predicate(AnimationState<E> event) {
        if (!this.isSpeedModifierApplied) {
            if (this.GeoAnimationSpeedModifier != 1.0f) {
                this.GeoAnimationController.setAnimationSpeed(this.GeoAnimationSpeedModifier);
            }
            this.isSpeedModifierApplied = false;
        }
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
    protected void initDataTracker(DataTracker.Builder syncBuilder) {
        super.initDataTracker(syncBuilder);
        syncBuilder.add(POS_HORIZONTAL_DEG, 0f);
        syncBuilder.add(POS_VERTICAL_DEG, 0f);

        syncBuilder.add(RANDOM_HIT_HEIGHT_OFFSET, 0f);

        syncBuilder.add(ANIMATION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        syncBuilder.add(TEXTURE_NAME, "textures/entity/skilleff/blademaster/tempest/sword/0.png");
        syncBuilder.add(LIFETIME, 300);
        syncBuilder.add(MAX_LIFETIME, 300);

        syncBuilder.add(ENTITY_SCALE, 1f);

        syncBuilder.add(ADDITIONAL_POS_X, 0f);
        syncBuilder.add(ADDITIONAL_POS_Y, 0f);
        syncBuilder.add(ADDITIONAL_POS_Z, 0f);

        syncBuilder.add(SWORD_POS_ID, 0);
        syncBuilder.add(TARGET_ENTITY_ID, -1);
        syncBuilder.add(DEGREE_SHOULD_INCREMENT_EACH_TIME, 8f);
        syncBuilder.add(SWORD_HIT_AOE_RANGE, 2f);

        syncBuilder.add(TARGET_CENTER_POS_X, 0f);
        syncBuilder.add(TARGET_CENTER_POS_Y, 0f);
        syncBuilder.add(TARGET_CENTER_POS_Z, 0f);

        syncBuilder.add(IS_BOUND_TO_ENTITY, false);

        syncBuilder.add(CLIENT_ACTION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
        pCompound.putFloat("VERTICALDEG", getEntityVerticalFacingDeg(this));

        pCompound.putFloat("RANDOMHITHEIGHTOFFSET", this.RandomHitHeightOffset);

        pCompound.putString("ANIMATIONAME", this.AnimationName);
        pCompound.putString("TEXTURENAME", this.TextureName);
        pCompound.putInt("LIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifeTime);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        //additional pos
        pCompound.putFloat("ADDITIONALPOSX", this.AdditionalPosX);
        pCompound.putFloat("ADDITIONALPOSY", this.AdditionalPosY);
        pCompound.putFloat("ADDITIONALPOSZ", this.AdditionalPosZ);

        //target pos
        pCompound.putFloat("TARGETCENTERPOSX", this.TargetCenterPosX);
        pCompound.putFloat("TARGETCENTERPOSY", this.TargetCenterPosY);
        pCompound.putFloat("TARGETCENTERPOSZ", this.TargetCenterPosZ);

        //TargetEntityID id
        pCompound.putInt("TARGETENTITYID", this.TargetEntityID);
        //sword pos id
        pCompound.putInt("SWORDPOSID", this.SwordPositionID);
        //deg
        pCompound.putFloat("DEGREESHOULDINCREMENTEACHTIME", this.DegreeShouldIncrementEachTime);
        //aoe range
        pCompound.putFloat("SWORDHITAOERANGE", this.SwordHitAoeRange);

        //bound entity
        pCompound.putBoolean("ISBOUNDTOENTITY", this.IsBoundToEntity);

        //client action method
        pCompound.putString("CLIENTACTIONAME", this.ClientActionName);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getDataTracker().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
        }

        if (pCompound.contains("VERTICALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("VERTICALDEG");
            this.getDataTracker().set(POS_VERTICAL_DEG, tempFloat001);
            setEntityVerticalFacingDeg(this, tempFloat001);
        }

        if (pCompound.contains("RANDOMHITHEIGHTOFFSET", 99)) {
            float tempFloat001 = pCompound.getFloat("RANDOMHITHEIGHTOFFSET");
            this.getDataTracker().set(RANDOM_HIT_HEIGHT_OFFSET, tempFloat001);
            this.RandomHitHeightOffset = tempFloat001;
        }

        if (pCompound.contains("ANIMATIONAME", 8)) {
            String tempString001 = pCompound.getString("ANIMATIONAME");
            this.getDataTracker().set(ANIMATION_NAME, tempString001);
            this.AnimationName = tempString001;
        }

        if (pCompound.contains("TEXTURENAME", 8)) {
            String tempTexturePath = pCompound.getString("TEXTURENAME");
            this.getDataTracker().set(TEXTURE_NAME, tempTexturePath);
            this.TextureName = tempTexturePath;
        }

        if (pCompound.contains("LIFETIME", 99)) {
            int tempInt = pCompound.getInt("LIFETIME");
            this.getDataTracker().set(LIFETIME, tempInt);
            this.lifetime = tempInt;
        }

        if (pCompound.contains("MAXLIFETIME", 99)) {
            int tempInt = pCompound.getInt("MAXLIFETIME");
            this.getDataTracker().set(MAX_LIFETIME, tempInt);
            this.MaxLifeTime = tempInt;
        }

        //============
        if (pCompound.contains("ENTITYSCALE", 99)) {
            float tempFloat001 = pCompound.getFloat("ENTITYSCALE");
            this.getDataTracker().set(ENTITY_SCALE, tempFloat001);
            this.EntityScale = tempFloat001;
        }

        //============
        if (pCompound.contains("ADDITIONALPOSX", 99)) {
            float tempFloat001 = pCompound.getFloat("ADDITIONALPOSX");
            this.getDataTracker().set(ADDITIONAL_POS_X, tempFloat001);
            this.AdditionalPosX = tempFloat001;
        }

        if (pCompound.contains("ADDITIONALPOSY", 99)) {
            float tempFloat001 = pCompound.getFloat("ADDITIONALPOSY");
            this.getDataTracker().set(ADDITIONAL_POS_Y, tempFloat001);
            this.AdditionalPosY = tempFloat001;
        }

        if (pCompound.contains("ADDITIONALPOSZ", 99)) {
            float tempFloat001 = pCompound.getFloat("ADDITIONALPOSZ");
            this.getDataTracker().set(ADDITIONAL_POS_Z, tempFloat001);
            this.AdditionalPosZ = tempFloat001;
        }

        //===================
        if (pCompound.contains("TARGETCENTERPOSX", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETCENTERPOSX");
            this.getDataTracker().set(TARGET_CENTER_POS_X, tempFloat001);
            this.TargetCenterPosX = tempFloat001;
        }

        if (pCompound.contains("TARGETCENTERPOSY", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETCENTERPOSY");
            this.getDataTracker().set(TARGET_CENTER_POS_Y, tempFloat001);
            this.TargetCenterPosY = tempFloat001;
        }

        if (pCompound.contains("TARGETCENTERPOSZ", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETCENTERPOSZ");
            this.getDataTracker().set(TARGET_CENTER_POS_Z, tempFloat001);
            this.TargetCenterPosZ = tempFloat001;
        }

        //===============
        if (pCompound.contains("SWORDPOSID", 99)) {
            int tempInt = pCompound.getInt("SWORDPOSID");
            this.getDataTracker().set(SWORD_POS_ID, tempInt);
            this.SwordPositionID = tempInt;
        }

        //===============
        if (pCompound.contains("TARGETENTITYID", 99)) {
            int tempInt = pCompound.getInt("TARGETENTITYID");
            this.getDataTracker().set(TARGET_ENTITY_ID, tempInt);
            this.TargetEntityID = tempInt;
        }

        //===============
        if (pCompound.contains("DEGREESHOULDINCREMENTEACHTIME", 99)) {
            float tempFloat001 = pCompound.getFloat("DEGREESHOULDINCREMENTEACHTIME");
            this.getDataTracker().set(DEGREE_SHOULD_INCREMENT_EACH_TIME, tempFloat001);
            this.DegreeShouldIncrementEachTime = tempFloat001;
        }

        //===============
        if (pCompound.contains("SWORDHITAOERANGE", 99)) {
            float tempFloat001 = pCompound.getFloat("SWORDHITAOERANGE");
            this.getDataTracker().set(SWORD_HIT_AOE_RANGE, tempFloat001);
            this.SwordHitAoeRange = tempFloat001;
        }

        //=================
        if (pCompound.contains("ISBOUNDTOENTITY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISBOUNDTOENTITY");
            this.getDataTracker().set(IS_BOUND_TO_ENTITY, tempBool);
            this.IsBoundToEntity = tempBool;
        }

        //=================
        if (pCompound.contains("CLIENTACTIONAME", 8)) {
            String tempString001 = pCompound.getString("CLIENTACTIONAME");
            this.getDataTracker().set(CLIENT_ACTION_NAME, tempString001);
            this.ClientActionName = tempString001;
        }
    }

    @Override
    public void writeSpawnData(PacketByteBuf buffer) {
        NbtCompound tag = new NbtCompound();
        this.writeCustomDataToNbt(tag);
        buffer.writeNbt(tag);
    }

    @Override
    public void readSpawnData(PacketByteBuf additionalData) {
        NbtCompound tag = additionalData.readNbt();
        this.readCustomDataFromNbt(tag);
    }
}
