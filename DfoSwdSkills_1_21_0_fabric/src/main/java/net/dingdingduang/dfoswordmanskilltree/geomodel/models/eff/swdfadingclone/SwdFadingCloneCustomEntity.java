package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
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
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class SwdFadingCloneCustomEntity extends ProjectileHelper implements GeoEntity, EntityExtraSpawnData {
    private static final TrackedData<Float> POS_HORIZONTAL_DEG = DataTracker.<Float>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ROTATION_X = DataTracker.<Float>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    //color
    private static final TrackedData<Integer> RGB_R = DataTracker.<Integer>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> RGB_G = DataTracker.<Integer>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> RGB_B = DataTracker.<Integer>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> RGB_A = DataTracker.<Integer>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Float> ENTITY_SCALE = DataTracker.<Float>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Integer> REMAINING_LIFETIME = DataTracker.<Integer>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MAX_LIFETIME = DataTracker.<Integer>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Boolean> IS_START_FRAME_FADING = DataTracker.<Boolean>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_END_FRAME_FADING = DataTracker.<Boolean>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> START_TOTAL_FRAME = DataTracker.<Integer>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> LOOP_TOTAL_FRAME = DataTracker.<Integer>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> END_TOTAL_FRAME = DataTracker.<Integer>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<String> ANIMATION_NAME = DataTracker.<String>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.<String>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.STRING);

    private static final TrackedData<Boolean> HAS_TARGET_LOC_XYZ = DataTracker.<Boolean>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> START_POS_X = DataTracker.<Float>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_POS_Y = DataTracker.<Float>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_POS_Z = DataTracker.<Float>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_X = DataTracker.<Float>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_Y = DataTracker.<Float>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_Z = DataTracker.<Float>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> TOTAL_TICKS_MOVING_TO_TARGET_POS = DataTracker.<Integer>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Boolean> IS_BOUND_TO_ENTITY = DataTracker.<Boolean>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<String> PARTICLE_NAME = DataTracker.<String>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.STRING);

    private static final TrackedData<Float> GEO_ANIMATION_SPEED_MODIFIER = DataTracker.<Float>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<String> CLIENT_ACTION_NAME = DataTracker.<String>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.STRING);

    private static final TrackedData<Boolean> SHOULD_RENDER_MAIN_HAND_WEAPON = DataTracker.<Boolean>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SHOULD_RENDER_OFFHAND_WEAPON = DataTracker.<Boolean>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<String> OWNER_MAIN_HAND_WEAPON_STR_ID = DataTracker.<String>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> OWNER_OFFHAND_WEAPON_STR_ID = DataTracker.<String>registerData(SwdFadingCloneCustomEntity.class, TrackedDataHandlerRegistry.STRING);

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private float RotationX = 0.0f;
    private int lifetime = 10;
    private int MaxLifetime = 10;

    //animation texutre and its frames
    private boolean IsStartFrameFading = true;
    private boolean IsEndFrameFading = true;
    private int StartTotalFrame = 4;
    private int LoopTotalFrame = 1;
    private int EndTotalFrame = 5;
    private int ClientLoopFrameCounter = 0;

    private String TextureName = GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE;
    private String AnimationName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private int RGB_Red = 255;
    private int RGB_Green = 255;
    private int RGB_Blue = 255;
    private int RGB_Alpha = 255;

    private float EntityScale = 1f;

    private boolean HasTargetLocXYZ = false;
    private float StartPosX = 0f;
    private float StartPosY = 0f;
    private float StartPosZ = 0f;
    private float TargetPosX = 0f;
    private float TargetPosY = 0f;
    private float TargetPosZ = 0f;
    private int TotalTicksThatRequiredToMoveToTargetLoc = 10;

    private boolean IsBoundToEntity = false;

    private String ParticleName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private boolean isSpeedModifierApplied = false;
    private GeoModelAnimationController<SwdFadingCloneCustomEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);
    private float GeoAnimationSpeedModifier = 1.0f;

    private String ClientActionName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private boolean ShouldRenderMainHandWeapon = false;
    private boolean ShouldRenderOffhandWeapon = false;
    private String OwnerMainHandWeaponStrID = "diamond_sword";
    private String OwnerOffHandWeaponStrID = "stone_sword";

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

    public GeoModelAnimationController<SwdFadingCloneCustomEntity> getAnimationController() { return this.GeoAnimationController; }

    public SwdFadingCloneCustomEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.noClip = true;
        this.intersectionChecked = false;
    }

    public float getRotationX() { return this.RotationX; }
    public void setRotationX(float rotationX) { this.RotationX = rotationX; }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetimeTicks) { this.lifetime = lifetimeTicks; }
    public int getMaxLifetime() { return this.MaxLifetime; }
    public void setMaxLifetime(int maxLifetime) { this.MaxLifetime = maxLifetime; }

    public int getClientLoopFrameCounter() { return this.ClientLoopFrameCounter; }
    public void setClientLoopFrameCounter(int clientLoopFrameCounter) { this.ClientLoopFrameCounter = clientLoopFrameCounter; }

    public int getColorRed() { return this.RGB_Red; }
    public void setColorRed(int cRed) { this.RGB_Red = cRed; }
    public int getColorGreen() { return this.RGB_Green; }
    public void setColorGreen(int cGreen) { this.RGB_Green = cGreen; }
    public int getColorBlue() { return RGB_Blue; }
    public void setColorBlue(int cBlue) { this.RGB_Blue = cBlue; }
    public int getColorAlpha() { return this.RGB_Alpha; }
    public void setColorAlpha(int cAlpha) { this.RGB_Alpha = cAlpha; }
    public void setColor(int red, int green, int blue, int alpha) {
        this.RGB_Red = red;
        this.RGB_Green = green;
        this.RGB_Blue = blue;
        this.RGB_Alpha = alpha;
    }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public boolean isStartFrameFading() { return this.IsStartFrameFading; }
    public void setStartFrameFading(boolean startFrameFading) { this.IsStartFrameFading = startFrameFading; }
    public boolean isEndFrameFading() { return IsEndFrameFading; }
    public void setEndFrameFading(boolean endFrameFading) { this.IsEndFrameFading = endFrameFading; }

    public int getStartTotalFrame() { return this.StartTotalFrame; }
    public void setStartTotalFrame(int startTotalFrame) { this.StartTotalFrame = startTotalFrame; }
    public int getLoopTotalFrame() { return this.LoopTotalFrame; }
    public void setLoopTotalFrame(int loopTotalFrame) { this.LoopTotalFrame = loopTotalFrame; }
    public int getEndTotalFrame() { return this.EndTotalFrame; }
    public void setEndTotalFrame(int endTotalFrame) { this.EndTotalFrame = endTotalFrame; }

    public String getTextureName() { return this.TextureName; }
    public void setTextureName(String textureName, int startTotalFrame, int loopTotalFrame, int endTotalFrame, boolean isStartFrameFading, boolean isEndFrameFading) {
        this.TextureName = textureName;
        this.StartTotalFrame = startTotalFrame;
        this.LoopTotalFrame = loopTotalFrame;
        this.EndTotalFrame = endTotalFrame;
        this.IsStartFrameFading = isStartFrameFading;
        this.IsEndFrameFading = isEndFrameFading;
    }
    public String getAnimationName() { return this.AnimationName; }
    public void setAnimationName(String animationName) { this.AnimationName = animationName; }

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
    public void setTargetLoc(float start_x1, float start_y1, float start_z1, float target_x1, float target_y1, float target_z1, int totalTicksThatRequiredToMoveToTargetLoc) {
        this.HasTargetLocXYZ = true;
        setStartPosLoc(start_x1, start_y1, start_z1);
        this.TargetPosX = target_x1;
        this.TargetPosY = target_y1;
        this.TargetPosZ = target_z1;
        this.TotalTicksThatRequiredToMoveToTargetLoc = totalTicksThatRequiredToMoveToTargetLoc;
    }
    public float getTargetPosX() { return this.TargetPosX; }
    public void setTargetPosX(float targetPosX) { this.TargetPosX = targetPosX; }
    public float getTargetPosY() { return this.TargetPosY; }
    public void setTargetPosY(float targetPosY) { this.TargetPosY = targetPosY; }
    public float getTargetPosZ() { return this.TargetPosZ; }
    public void setTargetPosZ(float targetPosZ) { this.TargetPosZ = targetPosZ; }

    public int getTotalTicksThatRequiredToMoveToTargetLoc() { return this.TotalTicksThatRequiredToMoveToTargetLoc; }
    public void setTotalTicksThatRequiredToMoveToTargetLoc(int totalTicksThatRequiredToMoveToTargetLoc) { this.TotalTicksThatRequiredToMoveToTargetLoc = totalTicksThatRequiredToMoveToTargetLoc; }

    public boolean isBoundToEntity() { return this.IsBoundToEntity; }
    public void setIsBoundToEntity(boolean boundToEntity) { this.IsBoundToEntity = boundToEntity; }

    private float previousPartialTick = 0f;
    public float getPreviousPartialTick() { return previousPartialTick; }
    public void setPreviousPartialTick(float a) { this.previousPartialTick = a; }

    public String getParticleName() { return this.ParticleName; }
    public void setParticleName(String particleName) { this.ParticleName = particleName; }

    public float getGeoAnimationSpeedModifier() { return this.GeoAnimationSpeedModifier; }
    public void setGeoAnimationSpeedModifier(float geoAnimationSpeedModifier) {
        this.GeoAnimationSpeedModifier = geoAnimationSpeedModifier;
        this.isSpeedModifierApplied = false;
    }

    public String getClientActionName() { return this.ClientActionName; }
    public void setClientActionName(String clientActionName) { this.ClientActionName = clientActionName; }

    public boolean isRenderMainHandWeapon() { return this.ShouldRenderMainHandWeapon; }
    public void setShouldRenderMainHandWeapon(boolean shouldRenderMainHandWeapon) { this.ShouldRenderMainHandWeapon = shouldRenderMainHandWeapon; }
    public boolean isRenderOffhandWeapon() { return this.ShouldRenderOffhandWeapon; }
    public void setShouldRenderOffhandWeapon(boolean shouldRenderOffhandWeapon) { this.ShouldRenderOffhandWeapon = shouldRenderOffhandWeapon; }
    public String getOwnerMainHandWeaponStrID() { return this.OwnerMainHandWeaponStrID; }
    public void setOwnerMainHandWeaponStrID(String ownerMainHandWeaponStrID) { this.OwnerMainHandWeaponStrID = ownerMainHandWeaponStrID; }
    public String getOwnerOffhandWeaponStrID() { return this.OwnerOffHandWeaponStrID; }
    public void setOwnerOffhandWeaponStrID(String ownerOffHandWeaponStrID) { this.OwnerOffHandWeaponStrID = ownerOffHandWeaponStrID; }

    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

            if (!this.HasTargetLocXYZ) {
                if (this.IsBoundToEntity && this.getOwner() != null) {
                    moveEntityToPos(this, getEntityPos(this.getOwner()).add(this.TargetPosX, this.TargetPosY, this.TargetPosZ));
                }
            }
//            else if (!this.IsBoundToEntity) {
//                float distPercent = 1f * (MaxLifetime - lifetime) / MaxLifetime;
//                float newX = StartPosX + (TargetPosX - StartPosX) * distPercent;
//                float newY = StartPosY + (TargetPosY - StartPosY) * distPercent;
//                float newZ = StartPosZ + (TargetPosZ - StartPosZ) * distPercent;
//                moveEntityToXYZ(this, newX, newY, newZ);
//            }

            if (this.lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
            }

            if (this.EntityServerAction != null) {
                int currentTickTime = (this.MaxLifetime - this.lifetime) / this.EntityServerActionTickPeriod;
                if (this.PreviousEntityActionTriggeredTick != currentTickTime) {
                    this.PreviousEntityActionTriggeredTick = currentTickTime;
                    this.EntityServerAction.executeAction(this);
                }
            }
        }
        else {
            MethodEntityAction ParticleMethod = ParticleRegistry.getParticleMethod(ParticleName);
            if (ParticleMethod != null) {
                ParticleMethod.executeAction(this);
            }

            MethodEntityAction ClientActionMethod = GeoModelClientMethodsRegistry.getGeoModelClientActionMethod(this.ClientActionName);
            if (ClientActionMethod != null) {
                ClientActionMethod.executeAction(this);
            }

            if (this.lifetime > 1) { this.lifetime--; }
        }
    }

    protected <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (!this.isSpeedModifierApplied) {
            if (this.GeoAnimationSpeedModifier != 1.0f) {
                this.GeoAnimationController.setAnimationSpeed(this.GeoAnimationSpeedModifier);
            }
            this.isSpeedModifierApplied = false;
        }
        event.setAnimation(RawAnimation.begin().thenPlay(this.AnimationName));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
//        this.GeoAnimationController = new GeoModelAnimationController<>(
//                this, "controller", 0, this::predicate, 0);
        controllers.add(GeoAnimationController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }


    @Override
    protected void initDataTracker(DataTracker.Builder syncBuilder) {
        super.initDataTracker(syncBuilder);
        syncBuilder.add(POS_HORIZONTAL_DEG, 0.0f);
        syncBuilder.add(ROTATION_X, 0.0f);
        syncBuilder.add(RGB_R, 255);
        syncBuilder.add(RGB_G, 255);
        syncBuilder.add(RGB_B, 255);
        syncBuilder.add(RGB_A, 255);

        syncBuilder.add(ENTITY_SCALE, 1f);

        syncBuilder.add(REMAINING_LIFETIME, 10);
        syncBuilder.add(MAX_LIFETIME, 10);

        syncBuilder.add(IS_START_FRAME_FADING, true);
        syncBuilder.add(IS_END_FRAME_FADING, true);
        syncBuilder.add(START_TOTAL_FRAME, 4);
        syncBuilder.add(LOOP_TOTAL_FRAME, 1);
        syncBuilder.add(END_TOTAL_FRAME, 7);

        syncBuilder.add(TEXTURE_NAME, GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE);
        syncBuilder.add(ANIMATION_NAME, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60);

        syncBuilder.add(START_POS_X, getEntityX(this));
        syncBuilder.add(START_POS_Y, getEntityY(this));
        syncBuilder.add(START_POS_Z, getEntityZ(this));
        syncBuilder.add(HAS_TARGET_LOC_XYZ, false);
        syncBuilder.add(TARGET_POS_X, getEntityX(this));
        syncBuilder.add(TARGET_POS_Y, getEntityY(this));
        syncBuilder.add(TARGET_POS_Z, getEntityZ(this));
        syncBuilder.add(TOTAL_TICKS_MOVING_TO_TARGET_POS, 10);

        syncBuilder.add(IS_BOUND_TO_ENTITY, false);

        syncBuilder.add(PARTICLE_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);

        syncBuilder.add(GEO_ANIMATION_SPEED_MODIFIER, 1.0f);

        syncBuilder.add(CLIENT_ACTION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);

        syncBuilder.add(SHOULD_RENDER_MAIN_HAND_WEAPON, false);
        syncBuilder.add(SHOULD_RENDER_OFFHAND_WEAPON, false);
        syncBuilder.add(OWNER_MAIN_HAND_WEAPON_STR_ID, "diamond_sword");
        syncBuilder.add(OWNER_OFFHAND_WEAPON_STR_ID, "stone_sword");
    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
//        pCompound.putFloat("VERTICALDEG", getEntityVerticalFacingDeg(this));
        pCompound.putFloat("ROTATIONX", this.RotationX);
        pCompound.putInt("RGBR", this.RGB_Red);
        pCompound.putInt("RGBG", this.RGB_Green);
        pCompound.putInt("RGBB", this.RGB_Blue);
        pCompound.putInt("RGBA", this.RGB_Alpha);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        pCompound.putInt("REMAININGLIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifetime);

        pCompound.putBoolean("ISSTARTFRAMEFADING", this.IsStartFrameFading);
        pCompound.putBoolean("ISENDFRAMEFADING", this.IsEndFrameFading);
        pCompound.putInt("STARTTOTALFRAME", this.StartTotalFrame);
        pCompound.putInt("LOOPTOTALFRAME", this.LoopTotalFrame);
        pCompound.putInt("ENDTOTALFRAME", this.EndTotalFrame);

        pCompound.putString("TEXTURENAME", this.TextureName);
        pCompound.putString("ANIMATIONAME", this.AnimationName);

        //origin pos vec
        pCompound.putFloat("STARTPOSX", this.StartPosX);
        pCompound.putFloat("STARTPOSY", this.StartPosY);
        pCompound.putFloat("STARTPOSZ", this.StartPosZ);
        //target pos
        pCompound.putBoolean("HASTARGETLOCXYZ", this.HasTargetLocXYZ);
        pCompound.putFloat("TARGETPOSX", this.TargetPosX);
        pCompound.putFloat("TARGETPOSY", this.TargetPosY);
        pCompound.putFloat("TARGETPOSZ", this.TargetPosZ);
        pCompound.putInt("TOTALTICKSMOVINGTOTARGETPOS", this.TotalTicksThatRequiredToMoveToTargetLoc);

        //bound entity
        pCompound.putBoolean("ISBOUNDTOENTITY", this.IsBoundToEntity);

        //particle method name
        pCompound.putString("PARTICLENAME", this.ParticleName);

        //animation speed modifier
        pCompound.putFloat("GEOANIMATIONSPEEDMODIFIER", this.GeoAnimationSpeedModifier);

        //client action method
        pCompound.putString("CLIENTACTIONAME", this.ClientActionName);

        //owner weapon str id
        pCompound.putBoolean("SHOULDRENDERMAINHANDWEAPON", this.ShouldRenderMainHandWeapon);
        pCompound.putBoolean("SHOULDRENDEROFFHANDWEAPON", this.ShouldRenderOffhandWeapon);
        pCompound.putString("OWNERMAINHANDWEAPONSTRID", this.OwnerMainHandWeaponStrID);
        pCompound.putString("OWNEROFFHANDWEAPONSTRID", this.OwnerOffHandWeaponStrID);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getDataTracker().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
        }

        if (pCompound.contains("ROTATIONX", 99)) {
            float tempFloat001 = pCompound.getFloat("ROTATIONX");
            this.getDataTracker().set(ROTATION_X, tempFloat001);
            this.RotationX = tempFloat001;
        }

        if (pCompound.contains("RGBR", 99)) {
            int tempInt001 = pCompound.getInt("RGBR");
            this.getDataTracker().set(RGB_R, tempInt001);
            this.RGB_Red = tempInt001;
        }

        if (pCompound.contains("RGBG", 99)) {
            int tempInt001 = pCompound.getInt("RGBG");
            this.getDataTracker().set(RGB_G, tempInt001);
            this.RGB_Green = tempInt001;
        }

        if (pCompound.contains("RGBB", 99)) {
            int tempInt001 = pCompound.getInt("RGBB");
            this.getDataTracker().set(RGB_B, tempInt001);
            this.RGB_Blue = tempInt001;
        }

        if (pCompound.contains("RGBA", 99)) {
            int tempInt001 = pCompound.getInt("RGBA");
            this.getDataTracker().set(RGB_A, tempInt001);
            this.RGB_Alpha = tempInt001;
        }

        //============
        if (pCompound.contains("ENTITYSCALE", 99)) {
            float tempFloat001 = pCompound.getFloat("ENTITYSCALE");
            this.getDataTracker().set(ENTITY_SCALE, tempFloat001);
            this.EntityScale = tempFloat001;
        }

        //===========
        if (pCompound.contains("REMAININGLIFETIME", 99)) {
            int tempInt001 = pCompound.getInt("REMAININGLIFETIME");
            this.getDataTracker().set(REMAINING_LIFETIME, tempInt001);
            this.lifetime = tempInt001;
        }

        if (pCompound.contains("MAXLIFETIME", 99)) {
            int tempInt001 = pCompound.getInt("MAXLIFETIME");
            this.getDataTracker().set(MAX_LIFETIME, tempInt001);
            this.MaxLifetime = tempInt001;
        }

        //==================
        if (pCompound.contains("ISSTARTFRAMEFADING", 1)) {
            boolean tempBool = pCompound.getBoolean("ISSTARTFRAMEFADING");
            this.getDataTracker().set(IS_START_FRAME_FADING, tempBool);
            this.IsStartFrameFading = tempBool;
        }

        if (pCompound.contains("ISENDFRAMEFADING", 1)) {
            boolean tempBool = pCompound.getBoolean("ISENDFRAMEFADING");
            this.getDataTracker().set(IS_END_FRAME_FADING, tempBool);
            this.IsEndFrameFading = tempBool;
        }

        if (pCompound.contains("STARTTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("STARTTOTALFRAME");
            this.getDataTracker().set(START_TOTAL_FRAME, tempInt001);
            this.StartTotalFrame = tempInt001;
        }

        if (pCompound.contains("LOOPTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("LOOPTOTALFRAME");
            this.getDataTracker().set(LOOP_TOTAL_FRAME, tempInt001);
            this.LoopTotalFrame = tempInt001;
        }

        if (pCompound.contains("ENDTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("ENDTOTALFRAME");
            this.getDataTracker().set(END_TOTAL_FRAME, tempInt001);
            this.EndTotalFrame = tempInt001;
        }

        //==================
        if (pCompound.contains("TEXTURENAME", 8)) {
            String tempString001 = pCompound.getString("TEXTURENAME");
            this.getDataTracker().set(TEXTURE_NAME, tempString001);
            this.TextureName = tempString001;
        }

        if (pCompound.contains("ANIMATIONAME", 8)) {
            String tempString001 = pCompound.getString("ANIMATIONAME");
            this.getDataTracker().set(ANIMATION_NAME, tempString001);
            this.AnimationName = tempString001;
        }

        //===================
        if (pCompound.contains("STARTPOSX", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSX");
            this.getDataTracker().set(START_POS_X, tempFloat001);
            this.StartPosX = tempFloat001;
        }

        if (pCompound.contains("STARTPOSY", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSY");
            this.getDataTracker().set(START_POS_Y, tempFloat001);
            this.StartPosY = tempFloat001;
        }

        if (pCompound.contains("STARTPOSZ", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSZ");
            this.getDataTracker().set(START_POS_Z, tempFloat001);
            this.StartPosZ = tempFloat001;
        }

        if (pCompound.contains("HASTARGETLOCXYZ", 1)) {
            boolean tempBool = pCompound.getBoolean("HASTARGETLOCXYZ");
            this.getDataTracker().set(HAS_TARGET_LOC_XYZ, tempBool);
            this.HasTargetLocXYZ = tempBool;
        }

        if (pCompound.contains("TARGETPOSX", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETPOSX");
            this.getDataTracker().set(TARGET_POS_X, tempFloat001);
            this.TargetPosX = tempFloat001;
        }

        if (pCompound.contains("TARGETPOSY", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETPOSY");
            this.getDataTracker().set(TARGET_POS_Y, tempFloat001);
            this.TargetPosY = tempFloat001;
        }

        if (pCompound.contains("TARGETPOSZ", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETPOSZ");
            this.getDataTracker().set(TARGET_POS_Z, tempFloat001);
            this.TargetPosZ = tempFloat001;
        }

        if (pCompound.contains("TOTALTICKSMOVINGTOTARGETPOS", 99)) {
            int tempInt001 = pCompound.getInt("TOTALTICKSMOVINGTOTARGETPOS");
            this.getDataTracker().set(TOTAL_TICKS_MOVING_TO_TARGET_POS, tempInt001);
            this.TotalTicksThatRequiredToMoveToTargetLoc = tempInt001;
        }

        //=================
        if (pCompound.contains("ISBOUNDTOENTITY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISBOUNDTOENTITY");
            this.getDataTracker().set(IS_BOUND_TO_ENTITY, tempBool);
            this.IsBoundToEntity = tempBool;
        }

        //=================
        if (pCompound.contains("PARTICLENAME", 8)) {
            String tempString001 = pCompound.getString("PARTICLENAME");
            this.getDataTracker().set(PARTICLE_NAME, tempString001);
            this.ParticleName = tempString001;
        }

        //=========== Geo Anim
        if (pCompound.contains("GEOANIMATIONSPEEDMODIFIER", 99)) {
            float tempFloat001 = pCompound.getFloat("GEOANIMATIONSPEEDMODIFIER");
            this.getDataTracker().set(GEO_ANIMATION_SPEED_MODIFIER, tempFloat001);
            this.GeoAnimationSpeedModifier = tempFloat001;
        }

        //=================
        if (pCompound.contains("CLIENTACTIONAME", 8)) {
            String tempString001 = pCompound.getString("CLIENTACTIONAME");
            this.getDataTracker().set(CLIENT_ACTION_NAME, tempString001);
            this.ClientActionName = tempString001;
        }

        //==================
        if (pCompound.contains("SHOULDRENDERMAINHANDWEAPON", 1)) {
            boolean tempBool = pCompound.getBoolean("SHOULDRENDERMAINHANDWEAPON");
            this.getDataTracker().set(SHOULD_RENDER_MAIN_HAND_WEAPON, tempBool);
            this.ShouldRenderMainHandWeapon = tempBool;
        }
        if (pCompound.contains("SHOULDRENDEROFFHANDWEAPON", 1)) {
            boolean tempBool = pCompound.getBoolean("SHOULDRENDEROFFHANDWEAPON");
            this.getDataTracker().set(SHOULD_RENDER_OFFHAND_WEAPON, tempBool);
            this.ShouldRenderOffhandWeapon = tempBool;
        }
        if (pCompound.contains("OWNERMAINHANDWEAPONSTRID", 8)) {
            String tempString001 = pCompound.getString("OWNERMAINHANDWEAPONSTRID");
            this.getDataTracker().set(OWNER_MAIN_HAND_WEAPON_STR_ID, tempString001);
            this.OwnerMainHandWeaponStrID = tempString001;
        }

        if (pCompound.contains("OWNEROFFHANDWEAPONSTRID", 8)) {
            String tempString001 = pCompound.getString("OWNEROFFHANDWEAPONSTRID");
            this.getDataTracker().set(OWNER_OFFHAND_WEAPON_STR_ID, tempString001);
            this.OwnerOffHandWeaponStrID = tempString001;
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
