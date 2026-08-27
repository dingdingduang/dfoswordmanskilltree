package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

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


import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.util.GeckoLibUtil;

import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class SwordSlashWhiteEntity extends ProjectileHelper implements IAnimatable, EntityExtraSpawnData {
    private static final TrackedData<Float> POS_HORIZONTAL_DEG = DataTracker.<Float>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.FLOAT);
//    private static final TrackedData<Float> POS_VERTICAL_DEG = DataTracker.<Float>registerData(SwordSlashPurpleEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ROTATION_Z = DataTracker.<Float>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.FLOAT);
    //color
    private static final TrackedData<Integer> RGB_R = DataTracker.<Integer>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> RGB_G = DataTracker.<Integer>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> RGB_B = DataTracker.<Integer>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Float> ENTITY_SCALE = DataTracker.<Float>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.FLOAT);
//    private static final TrackedData<Float> DIST_TRAVEL_PER_TICK = DataTracker.<Float>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<String> ANIMATION_NAME = DataTracker.<String>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.STRING);

    private static final TrackedData<Boolean> HAS_TARGET_LOC_XYZ = DataTracker.<Boolean>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> START_POS_X = DataTracker.<Float>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_POS_Y = DataTracker.<Float>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_POS_Z = DataTracker.<Float>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_X = DataTracker.<Float>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_Y = DataTracker.<Float>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_Z = DataTracker.<Float>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Boolean> IS_BOUND_TO_ENTITY = DataTracker.<Boolean>registerData(SwordSlashWhiteEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
//    private float RotationZ = ExtraMathMethods.randomFloat()*360;
    private float RotationZ = 0.0f;
    private int lifetime = 12;
    private final int randomModelTexture = ExtraMathMethods.randomInt(10);

    private int RGB_Red = 255;
    private int RGB_Green = 255;
    private int RGB_Blue = 255;

    private float EntityScale = 1f;

//    private float DistanceTravelPerTick = 0f;
    private String AnimationName = GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60;

    private boolean HasTargetLocXYZ = false;
    private float StartPosX = 0f;
    private float StartPosY = 0f;
    private float StartPosZ = 0f;
    private float TargetPosX = 0f;
    private float TargetPosY = 0f;
    private float TargetPosZ = 0f;

    private boolean IsBoundToEntity = false;

    private GeoModelAnimationController<SwordSlashWhiteEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<SwordSlashWhiteEntity> getAnimationController() { return this.GeoAnimationController; }

    public SwordSlashWhiteEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.noClip = true;
        this.intersectionChecked = false;
    }

    public float getRotationZ() { return this.RotationZ; }
    public void setRotationZ(float rotationZ) { this.RotationZ = rotationZ; }

    public int getRandomModelTexture() { return this.randomModelTexture; }
    public int getLifetime() { return this.lifetime; }

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

//    public float getDistanceTravelPerTick() { return this.DistanceTravelPerTick; }
//    public void setDistanceTravelPerTick(float distanceTravelPerTick) { this.DistanceTravelPerTick = distanceTravelPerTick; }

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

    public void tick() {
//        if (this.DistanceTravelPerTick > 0) {
//            Vector3f tempVec = Vector3fMethods.PolarProjectionFromVecHorizon(getEntityPos(this), DistanceTravelPerTick, getEntityHorizontalFacingDeg(this));
//
//            //check collision
//            int x = MathMethods.floor(tempVec.x()+0.5f);
//            int y = MathMethods.floor(tempVec.y());
//            int z = MathMethods.floor(tempVec.z()+0.5f);
//
//            BlockState state = getEntityLevel(this).getBlockState(new BlockPos(x, y, z));
//
//            if (state.isAir()) {
//                moveEntityToXYZ(this, tempVec.x(), tempVec.y(), tempVec.z());
//            }
//        }

        //Test
//        printInGameMsg("tickcount: "+this.tickCount);
//        Vector3f abc = Vector3fMethods.PolarProjectionFromVecHorizon(getEntityPos(this), 0.1f, getEntityHorizontalFacingDeg(this));
//        this.moveTo(abc.x(), abc.y(), abc.z());

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
//            this.moveTo(this.position().x()+ExtraMathMethods.randomFloat()  - 0.5, this.position().y()+ExtraMathMethods.randomFloat()*0.5 - 0.25, this.position().z()+ExtraMathMethods.randomFloat() - 0.5);
        }
        else {
            if (this.lifetime > 1) { this.lifetime--; }
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
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(POS_HORIZONTAL_DEG, 0.0f);
//        this.dataTracker.startTracking(POS_VERTICAL_DEG, 0.0f);
        this.dataTracker.startTracking(ROTATION_Z, 0.0f);
        this.dataTracker.startTracking(RGB_R, 255);
        this.dataTracker.startTracking(RGB_G, 255);
        this.dataTracker.startTracking(RGB_B, 255);

        this.dataTracker.startTracking(ENTITY_SCALE, 1f);
//        this.dataTracker.startTracking(DIST_TRAVEL_PER_TICK, 0f);
        this.dataTracker.startTracking(ANIMATION_NAME, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60);

        this.dataTracker.startTracking(START_POS_X, getEntityX(this));
        this.dataTracker.startTracking(START_POS_Y, getEntityY(this));
        this.dataTracker.startTracking(START_POS_Z, getEntityZ(this));
        this.dataTracker.startTracking(HAS_TARGET_LOC_XYZ, false);
        this.dataTracker.startTracking(TARGET_POS_X, getEntityX(this));
        this.dataTracker.startTracking(TARGET_POS_Y, getEntityY(this));
        this.dataTracker.startTracking(TARGET_POS_Z, getEntityZ(this));

        this.dataTracker.startTracking(IS_BOUND_TO_ENTITY, false);
    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
//        pCompound.putFloat("VERTICALDEG", getEntityVerticalFacingDeg(this));
        pCompound.putFloat("ROTATIONZ", this.RotationZ);
        pCompound.putFloat("RGBR", this.RGB_Red);
        pCompound.putFloat("RGBG", this.RGB_Green);
        pCompound.putFloat("RGBB", this.RGB_Blue);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);
//        pCompound.putFloat("DISTTRAVELPERTICK", this.DistanceTravelPerTick);
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
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getDataTracker().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
        }

//        if (pCompound.contains("VERTICALDEG", 99)) {
//            float tempFloat001 = pCompound.getFloat("VERTICALDEG");
//            this.getDataTracker().set(POS_VERTICAL_DEG, tempFloat001);
//            setEntityVerticalFacingDeg(this, tempFloat001);
//        }

        if (pCompound.contains("ROTATIONZ", 99)) {
            float tempFloat001 = pCompound.getFloat("ROTATIONZ");
            this.getDataTracker().set(ROTATION_Z, tempFloat001);
            this.RotationZ = tempFloat001;
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

        if (pCompound.contains("ENTITYSCALE", 99)) {
            float tempFloat001 = pCompound.getFloat("ENTITYSCALE");
            this.getDataTracker().set(ENTITY_SCALE, tempFloat001);
            this.EntityScale = tempFloat001;
        }

        if (pCompound.contains("ANIMATIONAME", 8)) {
            String tempString001 = pCompound.getString("ANIMATIONAME");
            this.getDataTracker().set(ANIMATION_NAME, tempString001);
            this.AnimationName = tempString001;
        }

//        if (pCompound.contains("DISTTRAVELPERTICK", 99)) {
//            float tempFloat001 = pCompound.getFloat("DISTTRAVELPERTICK");
//            this.getDataTracker().set(DIST_TRAVEL_PER_TICK, tempFloat001);
//            this.DistanceTravelPerTick = tempFloat001;
//        }

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
