package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.movingpos;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
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
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class TwoDAnimatedTex256ManipulateYCustomEntity extends ProjectileHelper implements GeoEntity, EntityExtraSpawnData {
    private static final TrackedData<Float> POS_HORIZONTAL_DEG = DataTracker.<Float>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
//    private static final TrackedData<Float> POS_VERTICAL_DEG = DataTracker.<Float>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
//    private static final TrackedData<Float> ROTATION_Z = DataTracker.<Float>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Boolean> ALWAYS_FACE_TO_OWNER_HORIZONTAL_FACING_ANGLE = DataTracker.<Boolean>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<String> ANIMATION_NAME = DataTracker.<String>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.<String>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> LIFETIME = DataTracker.<Integer>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MAX_LIFETIME = DataTracker.<Integer>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Float> ENTITY_SCALE = DataTracker.<Float>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Boolean> IS_START_FRAME_FADING = DataTracker.<Boolean>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_END_FRAME_FADING = DataTracker.<Boolean>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> START_TOTAL_FRAME = DataTracker.<Integer>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> END_TOTAL_FRAME = DataTracker.<Integer>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> ALL_FRAME_COUNT = DataTracker.<Integer>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> IS_FRAME_DISTRIBUTED_EVENLY = DataTracker.<Boolean>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<Boolean> HAS_TARGET_LOC_XYZ = DataTracker.<Boolean>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> START_POS_X = DataTracker.<Float>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_POS_Y = DataTracker.<Float>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_POS_Z = DataTracker.<Float>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_X = DataTracker.<Float>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_Y = DataTracker.<Float>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_Z = DataTracker.<Float>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Boolean> IS_BOUND_TO_ENTITY = DataTracker.<Boolean>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<Boolean> CHECK_CAM = DataTracker.<Boolean>registerData(TwoDAnimatedTex256ManipulateYCustomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private String AnimationName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;
    private String TextureName = "textures/entity/skilleff/blademaster/flow_stance/stab/slash_zanbato/";

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean AlwaysFaceToOwnerHorizontalFacingAngle = true;

    private int lifetime = 300;
    private int MaxLifeTime = 300;

    private float EntityScale = 1f;

    //animation texutre and its frames
    private boolean IsStartFrameFading = true;
    private boolean IsEndFrameFading = true;
    private int StartTotalFrame = 3;
    private int EndTotalFrame = 3;
    private int TotalFrameCount = 8;
    private boolean isFrameDistributedEvenly = false;
    private int ClientLoopFrameCounter = 0;
    private int[] ClientTotalFrameIndexHelper = null;

    private boolean HasTargetLocXYZ = false;
    private float StartPosX = 0f;
    private float StartPosY = 0f;
    private float StartPosZ = 0f;
    private float TargetPosX = 0f;
    private float TargetPosY = 0f;
    private float TargetPosZ = 0f;

    private boolean IsBoundToEntity = false;

    private boolean CheckCamera = false;

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

    private GeoModelAnimationController<TwoDAnimatedTex256ManipulateYCustomEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public boolean isAlwaysFaceToOwnerHorizontalFacingAngle() { return this.AlwaysFaceToOwnerHorizontalFacingAngle; }
    public void setAlwaysFaceToOwnerHorizontalFacingAngle(boolean alwaysFaceToOwnerHorizontalFacingAngle) { this.AlwaysFaceToOwnerHorizontalFacingAngle = alwaysFaceToOwnerHorizontalFacingAngle; }

    public GeoModelAnimationController<TwoDAnimatedTex256ManipulateYCustomEntity> getAnimationController() {
        return this.GeoAnimationController;
    }

    public TwoDAnimatedTex256ManipulateYCustomEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.noClip = true;
        this.intersectionChecked = false;
    }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public boolean isStartFrameFading() { return this.IsStartFrameFading; }
    public void setStartFrameFading(boolean startFrameFading) { this.IsStartFrameFading = startFrameFading; }
    public boolean isEndFrameFading() { return IsEndFrameFading; }
    public void setEndFrameFading(boolean endFrameFading) { this.IsEndFrameFading = endFrameFading; }

    public int getStartTotalFrame() { return this.StartTotalFrame; }
    public void setStartTotalFrame(int startTotalFrame) { this.StartTotalFrame = startTotalFrame; }
    public int getEndTotalFrame() { return this.EndTotalFrame; }
    public void setEndTotalFrame(int endTotalFrame) { this.EndTotalFrame = endTotalFrame; }
    public int getTotalFrameCount() { return this.TotalFrameCount; }
    public void setTotalFrameCount(int totalFrameCount) { this.TotalFrameCount = totalFrameCount; }
    public boolean isFrameDistributedEvenly() { return this.isFrameDistributedEvenly; }
    public void setFrameDistributedEvenly(boolean frameDistributedEvenly) { this.isFrameDistributedEvenly = frameDistributedEvenly; }
    public int[] getClientTotalFrameIndexHelper() { return this.ClientTotalFrameIndexHelper; }
    public void setClientTotalFrameIndexHelper(int[] clientTotalFrameIndexHelper) { this.ClientTotalFrameIndexHelper = clientTotalFrameIndexHelper; }

    public String getAnimationName() { return this.AnimationName; }
    public void setAnimationName(String animationName) { this.AnimationName = animationName; }
    public String getTextureName() { return this.TextureName; }
    //    public void setTextureName(String textureName, int startTotalFrame, int loopTotalFrame, int endTotalFrame, int totalFrameCount, boolean isStartFrameFading, boolean isEndFrameFading) {
    public void setTextureName(String textureName, int startTotalFrame, int endTotalFrame, int totalFrameCount, boolean isStartFrameFading, boolean isEndFrameFading) {
        this.TextureName = textureName;
        this.StartTotalFrame = startTotalFrame;
        this.EndTotalFrame = endTotalFrame;
        this.TotalFrameCount = totalFrameCount;
        this.IsStartFrameFading = isStartFrameFading;
        this.IsEndFrameFading = isEndFrameFading;
    }

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

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    public int getMaxLifeTime() { return this.MaxLifeTime; }
    public void setMaxLifeTime(int maxLifeTime) { this.MaxLifeTime = maxLifeTime; }

    public int getClientLoopFrameCounter() { return this.ClientLoopFrameCounter; }
    public void setClientLoopFrameCounter(int clientLoopFrameCounter) { this.ClientLoopFrameCounter = clientLoopFrameCounter; }

    public boolean isCheckCamera() { return this.CheckCamera; }
    public void setCheckCamera(boolean checkCamera) { this.CheckCamera = checkCamera; }

    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

            if (!this.HasTargetLocXYZ && this.IsBoundToEntity) {
                if (this.getOwner() != null) {
                    moveEntityToPos(this, getEntityPos(this.getOwner()).add(this.TargetPosX, this.TargetPosY, this.TargetPosZ));
                }
            }

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
            if (this.lifetime > 1) { this.lifetime--; }
        }
    }

    protected <E extends TwoDAnimatedTex256ManipulateYCustomEntity> PlayState predicate(AnimationState<E> event) {
        event.setAnimation(RawAnimation.begin().thenPlay(this.AnimationName));
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
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(POS_HORIZONTAL_DEG, 0.0f);
        this.dataTracker.startTracking(ALWAYS_FACE_TO_OWNER_HORIZONTAL_FACING_ANGLE, true);

        this.dataTracker.startTracking(ANIMATION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        this.dataTracker.startTracking(TEXTURE_NAME, "textures/entity/skilleff/blademaster/flow_stance/stab/slash_zanbato/");
        this.dataTracker.startTracking(LIFETIME, 300);
        this.dataTracker.startTracking(MAX_LIFETIME, 300);

        this.dataTracker.startTracking(ENTITY_SCALE, 1.0f);

        this.dataTracker.startTracking(IS_START_FRAME_FADING, true);
        this.dataTracker.startTracking(IS_END_FRAME_FADING, true);
        this.dataTracker.startTracking(START_TOTAL_FRAME, 4);
        this.dataTracker.startTracking(END_TOTAL_FRAME, 7);
        this.dataTracker.startTracking(ALL_FRAME_COUNT, 12);
        this.dataTracker.startTracking(IS_FRAME_DISTRIBUTED_EVENLY, false);

        this.dataTracker.startTracking(START_POS_X, getEntityX(this));
        this.dataTracker.startTracking(START_POS_Y, getEntityY(this));
        this.dataTracker.startTracking(START_POS_Z, getEntityZ(this));
        this.dataTracker.startTracking(HAS_TARGET_LOC_XYZ, false);
        this.dataTracker.startTracking(TARGET_POS_X, getEntityX(this));
        this.dataTracker.startTracking(TARGET_POS_Y, getEntityY(this));
        this.dataTracker.startTracking(TARGET_POS_Z, getEntityZ(this));

        this.dataTracker.startTracking(IS_BOUND_TO_ENTITY, false);

        this.dataTracker.startTracking(CHECK_CAM, false);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
        pCompound.putBoolean("ALWAYSFACETOOWNERHORIZONTALFACINGANGLE", this.AlwaysFaceToOwnerHorizontalFacingAngle);

        pCompound.putString("ANIMATIONAME", this.AnimationName);
        pCompound.putString("TEXTURENAME", this.TextureName);
        pCompound.putInt("LIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifeTime);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        pCompound.putBoolean("ISSTARTFRAMEFADING", this.IsStartFrameFading);
        pCompound.putBoolean("ISENDFRAMEFADING", this.IsEndFrameFading);
        pCompound.putInt("STARTTOTALFRAME", this.StartTotalFrame);
        pCompound.putInt("ENDTOTALFRAME", this.EndTotalFrame);
        pCompound.putInt("ALLFRAMECOUNT", this.TotalFrameCount);
        pCompound.putBoolean("ISFRAMEDISTRIBUTEDEVENLY", this.isFrameDistributedEvenly);

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

        //Check cam
        pCompound.putBoolean("CHECKCAMERA", this.CheckCamera);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getDataTracker().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
        }

        if (pCompound.contains("ALWAYSFACETOOWNERHORIZONTALFACINGANGLE", 1)) {
            boolean tempBool = pCompound.getBoolean("ALWAYSFACETOOWNERHORIZONTALFACINGANGLE");
            this.getDataTracker().set(ALWAYS_FACE_TO_OWNER_HORIZONTAL_FACING_ANGLE, tempBool);
            this.AlwaysFaceToOwnerHorizontalFacingAngle = tempBool;
        }

        //=============
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

        if (pCompound.contains("ENDTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("ENDTOTALFRAME");
            this.getDataTracker().set(END_TOTAL_FRAME, tempInt001);
            this.EndTotalFrame = tempInt001;
        }

        if (pCompound.contains("ALLFRAMECOUNT", 99)) {
            int tempInt001 = pCompound.getInt("ALLFRAMECOUNT");
            this.getDataTracker().set(ALL_FRAME_COUNT, tempInt001);
            this.TotalFrameCount = tempInt001;
        }

        if (pCompound.contains("ISFRAMEDISTRIBUTEDEVENLY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISFRAMEDISTRIBUTEDEVENLY");
            this.getDataTracker().set(IS_FRAME_DISTRIBUTED_EVENLY, tempBool);
            this.isFrameDistributedEvenly = tempBool;
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

        //=================
        if (pCompound.contains("ISBOUNDTOENTITY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISBOUNDTOENTITY");
            this.getDataTracker().set(IS_BOUND_TO_ENTITY, tempBool);
            this.IsBoundToEntity = tempBool;
        }

        //=================
        if (pCompound.contains("CHECKCAMERA", 1)) {
            boolean tempBool = pCompound.getBoolean("CHECKCAMERA");
            this.getDataTracker().set(CHECK_CAM, tempBool);
            this.CheckCamera = tempBool;
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
