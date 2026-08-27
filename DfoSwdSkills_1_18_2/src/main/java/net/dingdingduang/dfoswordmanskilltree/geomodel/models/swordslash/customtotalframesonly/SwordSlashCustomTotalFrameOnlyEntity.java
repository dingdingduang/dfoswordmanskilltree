package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.customtotalframesonly;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;

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

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class SwordSlashCustomTotalFrameOnlyEntity extends ProjectileHelper implements IAnimatable, IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Float> POS_HORIZONTAL_DEG = SynchedEntityData.<Float>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> POS_VERTICAL_DEG = SynchedEntityData.<Float>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROTATION_Z = SynchedEntityData.<Float>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> ALWAYS_FACE_TO_OWNER_HORIZONTAL_FACING_ANGLE = SynchedEntityData.<Boolean>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.BOOLEAN);
    //color
    private static final EntityDataAccessor<Integer> RGB_R = SynchedEntityData.<Integer>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RGB_G = SynchedEntityData.<Integer>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RGB_B = SynchedEntityData.<Integer>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Integer> REMAINING_LIFETIME = SynchedEntityData.<Integer>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> IS_START_FRAME_FADING = SynchedEntityData.<Boolean>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_END_FRAME_FADING = SynchedEntityData.<Boolean>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> START_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.INT);
//    private static final EntityDataAccessor<Integer> LOOP_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> END_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ALL_FRAME_COUNT = SynchedEntityData.<Integer>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<String> ANIMATION_NAME = SynchedEntityData.<String>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> TEXTURE_NAME = SynchedEntityData.<String>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Boolean> HAS_TARGET_LOC_XYZ = SynchedEntityData.<Boolean>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> START_POS_X = SynchedEntityData.<Float>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_POS_Y = SynchedEntityData.<Float>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_POS_Z = SynchedEntityData.<Float>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_X = SynchedEntityData.<Float>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Y = SynchedEntityData.<Float>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Z = SynchedEntityData.<Float>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Boolean> IS_BOUND_TO_ENTITY = SynchedEntityData.<Boolean>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<String> PARTICLE_NAME = SynchedEntityData.<String>defineId(SwordSlashCustomTotalFrameOnlyEntity.class, EntityDataSerializers.STRING);

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
//    private float RotationZ = ExtraMathMethods.randomFloat()*360;
    private float RotationZ = 0.0f;
    private boolean AlwaysFaceToOwnerHorizontalFacingAngle = true;

    private int lifetime = 12;
    private int MaxLifetime = 12;

    //animation texutre and its frames
    private boolean IsStartFrameFading = true;
    private boolean IsEndFrameFading = true;
    private int StartTotalFrame = 4;
//    private int LoopTotalFrame = 1;
    private int EndTotalFrame = 7;
    private int TotalFrameCount = 12;
    private int ClientLoopFrameCounter = 0;
    private int[] ClientTotalFrameIndexHelper = null;

    private String TextureName = "textures/entity/slash/white/0_0";
    private String AnimationName = GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60;

    private int RGB_Red = 255;
    private int RGB_Green = 255;
    private int RGB_Blue = 255;

    private float EntityScale = 1f;

//    private float DistanceTravelPerTick = 0f;

    private boolean HasTargetLocXYZ = false;
    private float StartPosX = 0f;
    private float StartPosY = 0f;
    private float StartPosZ = 0f;
    private float TargetPosX = 0f;
    private float TargetPosY = 0f;
    private float TargetPosZ = 0f;

    private boolean IsBoundToEntity = false;

    private String ParticleName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private GeoModelAnimationController<SwordSlashCustomTotalFrameOnlyEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

    public boolean isAlwaysFaceToOwnerHorizontalFacingAngle() { return this.AlwaysFaceToOwnerHorizontalFacingAngle; }
    public void setAlwaysFaceToOwnerHorizontalFacingAngle(boolean alwaysFaceToOwnerHorizontalFacingAngle) { this.AlwaysFaceToOwnerHorizontalFacingAngle = alwaysFaceToOwnerHorizontalFacingAngle; }

    public GeoModelAnimationController<SwordSlashCustomTotalFrameOnlyEntity> getAnimationController() { return this.GeoAnimationController; }

    public SwordSlashCustomTotalFrameOnlyEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    public float getRotationZ() { return this.RotationZ; }
    public void setRotationZ(float rotationZ) { this.RotationZ = rotationZ; }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetimeTicks) { this.lifetime = lifetimeTicks; }
    public int getMaxLifetime() { return this.MaxLifetime; }
    public void setMaxLifetime(int maxLifetime) { this.MaxLifetime = maxLifetime; }

    public int getClientLoopFrameCounter() { return this.ClientLoopFrameCounter; }
    public void setClientLoopFrameCounter(int clientLoopFrameCounter) { this.ClientLoopFrameCounter = clientLoopFrameCounter; }

    public int getColorRed() { return RGB_Red; }
    public void setColorRed(int RGB_Red) { this.RGB_Red = RGB_Red; }
    public int getColorGreen() { return RGB_Green; }
    public void setColorGreen(int RGB_Green) { this.RGB_Green = RGB_Green; }
    public int getColorBlue() { return RGB_Blue; }
    public void setColorBlue(int RGB_Blue) { this.RGB_Blue = RGB_Blue; }
    public void setColor(int red, int green, int blue) {
        this.RGB_Red = red;
        this.RGB_Green = green;
        this.RGB_Blue = blue;
    }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public boolean isStartFrameFading() { return this.IsStartFrameFading; }
    public void setStartFrameFading(boolean startFrameFading) { this.IsStartFrameFading = startFrameFading; }
    public boolean isEndFrameFading() { return IsEndFrameFading; }
    public void setEndFrameFading(boolean endFrameFading) { this.IsEndFrameFading = endFrameFading; }

    public int getStartTotalFrame() { return this.StartTotalFrame; }
    public void setStartTotalFrame(int startTotalFrame) { this.StartTotalFrame = startTotalFrame; }
//    public int getLoopTotalFrame() { return this.LoopTotalFrame; }
//    public void setLoopTotalFrame(int loopTotalFrame) { this.LoopTotalFrame = loopTotalFrame; }
    public int getEndTotalFrame() { return this.EndTotalFrame; }
    public void setEndTotalFrame(int endTotalFrame) { this.EndTotalFrame = endTotalFrame; }
    public int getTotalFrameCount() { return this.TotalFrameCount; }
    public void setTotalFrameCount(int totalFrameCount) { this.TotalFrameCount = totalFrameCount; }
    public int[] getClientTotalFrameIndexHelper() { return this.ClientTotalFrameIndexHelper; }
    public void setClientTotalFrameIndexHelper(int[] clientTotalFrameIndexHelper) { ClientTotalFrameIndexHelper = clientTotalFrameIndexHelper; }

    public String getTextureName() { return this.TextureName; }
//    public void setTextureName(String textureName, int startTotalFrame, int loopTotalFrame, int endTotalFrame, int totalFrameCount, boolean isStartFrameFading, boolean isEndFrameFading) {
    public void setTextureName(String textureName, int startTotalFrame, int endTotalFrame, int totalFrameCount, boolean isStartFrameFading, boolean isEndFrameFading) {
        this.TextureName = textureName;
        this.StartTotalFrame = startTotalFrame;
//        this.LoopTotalFrame = loopTotalFrame;
        this.EndTotalFrame = endTotalFrame;
        this.TotalFrameCount = totalFrameCount;
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

    private float previousPartialTick = 0f;
    public float getPreviousPartialTick() { return previousPartialTick; }
    public void setPreviousPartialTick(float a) { this.previousPartialTick = a; }

    public String getParticleName() { return this.ParticleName; }
    public void setParticleName(String particleName) { this.ParticleName = particleName; }

    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

//            if (this.CustomMethodEntityAction != null) {
//                this.CustomMethodEntityAction.execute(this);
//            }

            if (!this.HasTargetLocXYZ && this.IsBoundToEntity) {
                if (this.getOwner() != null) {
                    moveEntityToPos(this, getEntityPos(this.getOwner()).add(this.TargetPosX, this.TargetPosY, this.TargetPosZ));
                }
            }

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
//            this.moveTo(this.position().x()+ExtraMathMethods.randomFloat()  - 0.5, this.position().y()+ExtraMathMethods.randomFloat()*0.5 - 0.25, this.position().z()+ExtraMathMethods.randomFloat() - 0.5);
        }
        else {
            if (this.lifetime > 1) { this.lifetime--; }
            MethodEntityAction ParticleMethod = ParticleRegistry.getParticleMethod(ParticleName);

            if (ParticleMethod != null) {
                ParticleMethod.executeAction(this);
            }

        }
    }

    protected <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(this.AnimationName, ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(this.GeoAnimationController);
    }

    @Override
    public AnimationFactory getFactory() { return this.factory; }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(POS_HORIZONTAL_DEG, 0.0f);
        this.entityData.define(POS_VERTICAL_DEG, 0.0f);
        this.entityData.define(ROTATION_Z, 0.0f);
        this.entityData.define(ALWAYS_FACE_TO_OWNER_HORIZONTAL_FACING_ANGLE, true);

        this.entityData.define(RGB_R, 255);
        this.entityData.define(RGB_G, 255);
        this.entityData.define(RGB_B, 255);

        this.entityData.define(ENTITY_SCALE, 1f);

        this.entityData.define(REMAINING_LIFETIME, 12);
        this.entityData.define(MAX_LIFETIME, 12);

        this.entityData.define(IS_START_FRAME_FADING, true);
        this.entityData.define(IS_END_FRAME_FADING, true);
        this.entityData.define(START_TOTAL_FRAME, 4);
//        this.entityData.define(LOOP_TOTAL_FRAME, 1);
        this.entityData.define(END_TOTAL_FRAME, 7);
        this.entityData.define(ALL_FRAME_COUNT, 12);

        this.entityData.define(TEXTURE_NAME, "textures/entity/slash/white/0_0");
        this.entityData.define(ANIMATION_NAME, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60);

        this.entityData.define(START_POS_X, getEntityX(this));
        this.entityData.define(START_POS_Y, getEntityY(this));
        this.entityData.define(START_POS_Z, getEntityZ(this));
        this.entityData.define(HAS_TARGET_LOC_XYZ, false);
        this.entityData.define(TARGET_POS_X, getEntityX(this));
        this.entityData.define(TARGET_POS_Y, getEntityY(this));
        this.entityData.define(TARGET_POS_Z, getEntityZ(this));

        this.entityData.define(IS_BOUND_TO_ENTITY, false);

        this.entityData.define(PARTICLE_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
        pCompound.putFloat("VERTICALDEG", getEntityVerticalFacingDeg(this));
        pCompound.putFloat("ROTATIONZ", this.RotationZ);
        pCompound.putBoolean("ALWAYSFACETOOWNERHORIZONTALFACINGANGLE", this.AlwaysFaceToOwnerHorizontalFacingAngle);

        pCompound.putInt("RGBR", this.RGB_Red);
        pCompound.putInt("RGBG", this.RGB_Green);
        pCompound.putInt("RGBB", this.RGB_Blue);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        pCompound.putInt("REMAININGLIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifetime);

        pCompound.putBoolean("ISSTARTFRAMEFADING", this.IsStartFrameFading);
        pCompound.putBoolean("ISENDFRAMEFADING", this.IsEndFrameFading);
        pCompound.putInt("STARTTOTALFRAME", this.StartTotalFrame);
//        pCompound.putInt("LOOPTOTALFRAME", this.LoopTotalFrame);
        pCompound.putInt("ENDTOTALFRAME", this.EndTotalFrame);
        pCompound.putInt("ALLFRAMECOUNT", this.TotalFrameCount);

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

        //bound entity
        pCompound.putBoolean("ISBOUNDTOENTITY", this.IsBoundToEntity);

        //particle method name
        pCompound.putString("PARTICLENAME", this.ParticleName);
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

        if (pCompound.contains("ALWAYSFACETOOWNERHORIZONTALFACINGANGLE", 1)) {
            boolean tempBool = pCompound.getBoolean("ALWAYSFACETOOWNERHORIZONTALFACINGANGLE");
            this.getEntityData().set(ALWAYS_FACE_TO_OWNER_HORIZONTAL_FACING_ANGLE, tempBool);
            this.AlwaysFaceToOwnerHorizontalFacingAngle = tempBool;
        }

        //=============
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

//        if (pCompound.contains("LOOPTOTALFRAME", 99)) {
//            int tempInt001 = pCompound.getInt("LOOPTOTALFRAME");
//            this.getEntityData().set(LOOP_TOTAL_FRAME, tempInt001);
//            this.LoopTotalFrame = tempInt001;
//        }

        if (pCompound.contains("ENDTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("ENDTOTALFRAME");
            this.getEntityData().set(END_TOTAL_FRAME, tempInt001);
            this.EndTotalFrame = tempInt001;
        }

        if (pCompound.contains("ALLFRAMECOUNT", 99)) {
            int tempInt001 = pCompound.getInt("ALLFRAMECOUNT");
            this.getEntityData().set(ALL_FRAME_COUNT, tempInt001);
            this.TotalFrameCount = tempInt001;
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
