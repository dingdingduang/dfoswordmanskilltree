package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords;

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
import net.minecraft.entity.EntityType;
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

public class FancySwordEntity extends ProjectileHelper implements GeoEntity, EntityExtraSpawnData {
    private static final TrackedData<Float> POS_HORIZONTAL_DEG = DataTracker.<Float>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> POS_VERTICAL_DEG = DataTracker.<Float>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ROTATION_Z = DataTracker.<Float>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<String> GEO_MODEL_NAME = DataTracker.<String>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> ANIMATION_NAME = DataTracker.<String>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.<String>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> LIFETIME = DataTracker.<Integer>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MAX_LIFETIME = DataTracker.<Integer>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Float> ENTITY_SCALE = DataTracker.<Float>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Float> TARGET_CENTER_POS_X = DataTracker.<Float>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_CENTER_POS_Y = DataTracker.<Float>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_CENTER_POS_Z = DataTracker.<Float>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Float> AOE_RANGE = DataTracker.<Float>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<String> CLIENT_ACTION_NAME = DataTracker.<String>registerData(FancySwordEntity.class, TrackedDataHandlerRegistry.STRING);

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

    public FancySwordEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.noClip = true;
        this.intersectionChecked = false;
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
    protected void initDataTracker(DataTracker.Builder syncBuilder) {
        super.initDataTracker(syncBuilder);
        syncBuilder.add(POS_HORIZONTAL_DEG, 0f);
        syncBuilder.add(POS_VERTICAL_DEG, 0f);
        syncBuilder.add(ROTATION_Z, 0f);

        syncBuilder.add(GEO_MODEL_NAME, "textures/entity/skilleff/blademaster/tempest/geo/sword1.geo.json");
        syncBuilder.add(ANIMATION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        syncBuilder.add(TEXTURE_NAME, "textures/entity/skilleff/blademaster/tempest/sword/0.png");
        syncBuilder.add(LIFETIME, 300);
        syncBuilder.add(MAX_LIFETIME, 300);

        syncBuilder.add(ENTITY_SCALE, 1f);

        syncBuilder.add(TARGET_CENTER_POS_X, getEntityX(this));
        syncBuilder.add(TARGET_CENTER_POS_Y, getEntityY(this));
        syncBuilder.add(TARGET_CENTER_POS_Z, getEntityZ(this));

        syncBuilder.add(AOE_RANGE, 8f);

        syncBuilder.add(CLIENT_ACTION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
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

        if (pCompound.contains("ROTATIONZ", 99)) {
            float tempFloat001 = pCompound.getFloat("ROTATIONZ");
            this.getDataTracker().set(ROTATION_Z, tempFloat001);
            this.RotationZ = tempFloat001;
        }

        //=================
        if (pCompound.contains("GEOMODELNAME", 8)) {
            String tempString001 = pCompound.getString("GEOMODELNAME");
            this.getDataTracker().set(GEO_MODEL_NAME, tempString001);
            this.GeoModelName = tempString001;
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
        if (pCompound.contains("AOERANGE", 99)) {
            float tempFloat001 = pCompound.getFloat("AOERANGE");
            this.getDataTracker().set(AOE_RANGE, tempFloat001);
            this.AoeRange = tempFloat001;
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
