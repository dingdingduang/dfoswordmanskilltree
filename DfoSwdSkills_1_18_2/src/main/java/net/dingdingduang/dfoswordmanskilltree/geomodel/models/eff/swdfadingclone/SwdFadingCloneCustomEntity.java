package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class SwdFadingCloneCustomEntity extends ProjectileHelper implements IAnimatable, IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Float> POS_HORIZONTAL_DEG = SynchedEntityData.<Float>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROTATION_X = SynchedEntityData.<Float>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.FLOAT);
    //color
    private static final EntityDataAccessor<Integer> RGB_R = SynchedEntityData.<Integer>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RGB_G = SynchedEntityData.<Integer>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RGB_B = SynchedEntityData.<Integer>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RGB_A = SynchedEntityData.<Integer>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Integer> REMAINING_LIFETIME = SynchedEntityData.<Integer>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> IS_START_FRAME_FADING = SynchedEntityData.<Boolean>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_END_FRAME_FADING = SynchedEntityData.<Boolean>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> START_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LOOP_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> END_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<String> ANIMATION_NAME = SynchedEntityData.<String>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> TEXTURE_NAME = SynchedEntityData.<String>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Boolean> HAS_TARGET_LOC_XYZ = SynchedEntityData.<Boolean>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> START_POS_X = SynchedEntityData.<Float>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_POS_Y = SynchedEntityData.<Float>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_POS_Z = SynchedEntityData.<Float>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_X = SynchedEntityData.<Float>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Y = SynchedEntityData.<Float>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Z = SynchedEntityData.<Float>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> TOTAL_TICKS_MOVING_TO_TARGET_POS = SynchedEntityData.<Integer>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> IS_BOUND_TO_ENTITY = SynchedEntityData.<Boolean>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<String> PARTICLE_NAME = SynchedEntityData.<String>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Float> GEO_ANIMATION_SPEED_MODIFIER = SynchedEntityData.<Float>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<String> CLIENT_ACTION_NAME = SynchedEntityData.<String>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Boolean> SHOULD_RENDER_MAIN_HAND_WEAPON = SynchedEntityData.<Boolean>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHOULD_RENDER_OFFHAND_WEAPON = SynchedEntityData.<Boolean>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> OWNER_MAIN_HAND_WEAPON_STR_ID = SynchedEntityData.<String>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> OWNER_OFFHAND_WEAPON_STR_ID = SynchedEntityData.<String>defineId(SwdFadingCloneCustomEntity.class, EntityDataSerializers.STRING);

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

    public SwdFadingCloneCustomEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
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

    protected <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!this.isSpeedModifierApplied) {
            if (this.GeoAnimationSpeedModifier != 1.0f) {
                this.GeoAnimationController.setAnimationSpeed(this.GeoAnimationSpeedModifier);
            }
            this.isSpeedModifierApplied = false;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation(this.AnimationName, ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(this.GeoAnimationController);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(POS_HORIZONTAL_DEG, 0.0f);
        this.entityData.define(ROTATION_X, 0.0f);
        this.entityData.define(RGB_R, 255);
        this.entityData.define(RGB_G, 255);
        this.entityData.define(RGB_B, 255);
        this.entityData.define(RGB_A, 255);

        this.entityData.define(ENTITY_SCALE, 1f);

        this.entityData.define(REMAINING_LIFETIME, 10);
        this.entityData.define(MAX_LIFETIME, 10);

        this.entityData.define(IS_START_FRAME_FADING, true);
        this.entityData.define(IS_END_FRAME_FADING, true);
        this.entityData.define(START_TOTAL_FRAME, 4);
        this.entityData.define(LOOP_TOTAL_FRAME, 1);
        this.entityData.define(END_TOTAL_FRAME, 7);

        this.entityData.define(TEXTURE_NAME, GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE);
        this.entityData.define(ANIMATION_NAME, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60);

        this.entityData.define(START_POS_X, getEntityX(this));
        this.entityData.define(START_POS_Y, getEntityY(this));
        this.entityData.define(START_POS_Z, getEntityZ(this));
        this.entityData.define(HAS_TARGET_LOC_XYZ, false);
        this.entityData.define(TARGET_POS_X, getEntityX(this));
        this.entityData.define(TARGET_POS_Y, getEntityY(this));
        this.entityData.define(TARGET_POS_Z, getEntityZ(this));
        this.entityData.define(TOTAL_TICKS_MOVING_TO_TARGET_POS, 10);

        this.entityData.define(IS_BOUND_TO_ENTITY, false);

        this.entityData.define(PARTICLE_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);

        this.entityData.define(GEO_ANIMATION_SPEED_MODIFIER, 1.0f);

        this.entityData.define(CLIENT_ACTION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);

        this.entityData.define(SHOULD_RENDER_MAIN_HAND_WEAPON, false);
        this.entityData.define(SHOULD_RENDER_OFFHAND_WEAPON, false);
        this.entityData.define(OWNER_MAIN_HAND_WEAPON_STR_ID, "diamond_sword");
        this.entityData.define(OWNER_OFFHAND_WEAPON_STR_ID, "stone_sword");
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
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
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getEntityData().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
        }

        if (pCompound.contains("ROTATIONX", 99)) {
            float tempFloat001 = pCompound.getFloat("ROTATIONX");
            this.getEntityData().set(ROTATION_X, tempFloat001);
            this.RotationX = tempFloat001;
        }

        if (pCompound.contains("RGBR", 99)) {
            int tempInt001 = pCompound.getInt("RGBR");
            this.getEntityData().set(RGB_R, tempInt001);
            this.RGB_Red = tempInt001;
        }

        if (pCompound.contains("RGBG", 99)) {
            int tempInt001 = pCompound.getInt("RGBG");
            this.getEntityData().set(RGB_G, tempInt001);
            this.RGB_Green = tempInt001;
        }

        if (pCompound.contains("RGBB", 99)) {
            int tempInt001 = pCompound.getInt("RGBB");
            this.getEntityData().set(RGB_B, tempInt001);
            this.RGB_Blue = tempInt001;
        }

        if (pCompound.contains("RGBA", 99)) {
            int tempInt001 = pCompound.getInt("RGBA");
            this.getEntityData().set(RGB_A, tempInt001);
            this.RGB_Alpha = tempInt001;
        }

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
        if (pCompound.contains("ISSTARTFRAMEFADING", 1)) {
            boolean tempBool = pCompound.getBoolean("ISSTARTFRAMEFADING");
            this.getEntityData().set(IS_START_FRAME_FADING, tempBool);
            this.IsStartFrameFading = tempBool;
        }

        if (pCompound.contains("ISENDFRAMEFADING", 1)) {
            boolean tempBool = pCompound.getBoolean("ISENDFRAMEFADING");
            this.getEntityData().set(IS_END_FRAME_FADING, tempBool);
            this.IsEndFrameFading = tempBool;
        }

        if (pCompound.contains("STARTTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("STARTTOTALFRAME");
            this.getEntityData().set(START_TOTAL_FRAME, tempInt001);
            this.StartTotalFrame = tempInt001;
        }

        if (pCompound.contains("LOOPTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("LOOPTOTALFRAME");
            this.getEntityData().set(LOOP_TOTAL_FRAME, tempInt001);
            this.LoopTotalFrame = tempInt001;
        }

        if (pCompound.contains("ENDTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("ENDTOTALFRAME");
            this.getEntityData().set(END_TOTAL_FRAME, tempInt001);
            this.EndTotalFrame = tempInt001;
        }

        //==================
        if (pCompound.contains("TEXTURENAME", 8)) {
            String tempString001 = pCompound.getString("TEXTURENAME");
            this.getEntityData().set(TEXTURE_NAME, tempString001);
            this.TextureName = tempString001;
        }

        if (pCompound.contains("ANIMATIONAME", 8)) {
            String tempString001 = pCompound.getString("ANIMATIONAME");
            this.getEntityData().set(ANIMATION_NAME, tempString001);
            this.AnimationName = tempString001;
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

        if (pCompound.contains("TOTALTICKSMOVINGTOTARGETPOS", 99)) {
            int tempInt001 = pCompound.getInt("TOTALTICKSMOVINGTOTARGETPOS");
            this.getEntityData().set(TOTAL_TICKS_MOVING_TO_TARGET_POS, tempInt001);
            this.TotalTicksThatRequiredToMoveToTargetLoc = tempInt001;
        }

        //=================
        if (pCompound.contains("ISBOUNDTOENTITY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISBOUNDTOENTITY");
            this.getEntityData().set(IS_BOUND_TO_ENTITY, tempBool);
            this.IsBoundToEntity = tempBool;
        }

        //=================
        if (pCompound.contains("PARTICLENAME", 8)) {
            String tempString001 = pCompound.getString("PARTICLENAME");
            this.getEntityData().set(PARTICLE_NAME, tempString001);
            this.ParticleName = tempString001;
        }

        //=========== Geo Anim
        if (pCompound.contains("GEOANIMATIONSPEEDMODIFIER", 99)) {
            float tempFloat001 = pCompound.getFloat("GEOANIMATIONSPEEDMODIFIER");
            this.getEntityData().set(GEO_ANIMATION_SPEED_MODIFIER, tempFloat001);
            this.GeoAnimationSpeedModifier = tempFloat001;
        }

        //=================
        if (pCompound.contains("CLIENTACTIONAME", 8)) {
            String tempString001 = pCompound.getString("CLIENTACTIONAME");
            this.getEntityData().set(CLIENT_ACTION_NAME, tempString001);
            this.ClientActionName = tempString001;
        }

        //==================
        if (pCompound.contains("SHOULDRENDERMAINHANDWEAPON", 1)) {
            boolean tempBool = pCompound.getBoolean("SHOULDRENDERMAINHANDWEAPON");
            this.getEntityData().set(SHOULD_RENDER_MAIN_HAND_WEAPON, tempBool);
            this.ShouldRenderMainHandWeapon = tempBool;
        }
        if (pCompound.contains("SHOULDRENDEROFFHANDWEAPON", 1)) {
            boolean tempBool = pCompound.getBoolean("SHOULDRENDEROFFHANDWEAPON");
            this.getEntityData().set(SHOULD_RENDER_OFFHAND_WEAPON, tempBool);
            this.ShouldRenderOffhandWeapon = tempBool;
        }
        if (pCompound.contains("OWNERMAINHANDWEAPONSTRID", 8)) {
            String tempString001 = pCompound.getString("OWNERMAINHANDWEAPONSTRID");
            this.getEntityData().set(OWNER_MAIN_HAND_WEAPON_STR_ID, tempString001);
            this.OwnerMainHandWeaponStrID = tempString001;
        }

        if (pCompound.contains("OWNEROFFHANDWEAPONSTRID", 8)) {
            String tempString001 = pCompound.getString("OWNEROFFHANDWEAPONSTRID");
            this.getEntityData().set(OWNER_OFFHAND_WEAPON_STR_ID, tempString001);
            this.OwnerOffHandWeaponStrID = tempString001;
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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
}
