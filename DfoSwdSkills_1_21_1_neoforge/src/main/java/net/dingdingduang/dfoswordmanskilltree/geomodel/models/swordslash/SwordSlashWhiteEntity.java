package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class SwordSlashWhiteEntity extends ProjectileHelper implements GeoEntity, IEntityWithComplexSpawn {
    private static final EntityDataAccessor<Float> POS_HORIZONTAL_DEG = SynchedEntityData.<Float>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.FLOAT);
//    private static final EntityDataAccessor<Float> POS_VERTICAL_DEG = SynchedEntityData.<Float>defineId(SwordSlashPurpleEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROTATION_Z = SynchedEntityData.<Float>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.FLOAT);
    //color
    private static final EntityDataAccessor<Integer> RGB_R = SynchedEntityData.<Integer>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RGB_G = SynchedEntityData.<Integer>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RGB_B = SynchedEntityData.<Integer>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.FLOAT);
//    private static final EntityDataAccessor<Float> DIST_TRAVEL_PER_TICK = SynchedEntityData.<Float>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<String> ANIMATION_NAME = SynchedEntityData.<String>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Boolean> HAS_TARGET_LOC_XYZ = SynchedEntityData.<Boolean>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> START_POS_X = SynchedEntityData.<Float>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_POS_Y = SynchedEntityData.<Float>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_POS_Z = SynchedEntityData.<Float>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_X = SynchedEntityData.<Float>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Y = SynchedEntityData.<Float>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Z = SynchedEntityData.<Float>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Boolean> IS_BOUND_TO_ENTITY = SynchedEntityData.<Boolean>defineId(SwordSlashWhiteEntity.class, EntityDataSerializers.BOOLEAN);

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
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

    public SwordSlashWhiteEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
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

    protected <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        event.setAnimation(RawAnimation.begin().thenPlay(this.AnimationName));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(GeoAnimationController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder syncBuilder) {
        super.defineSynchedData(syncBuilder);
        syncBuilder.define(POS_HORIZONTAL_DEG, 0.0f);
//        syncBuilder.define(POS_VERTICAL_DEG, 0.0f);
        syncBuilder.define(ROTATION_Z, 0.0f);
        syncBuilder.define(RGB_R, 255);
        syncBuilder.define(RGB_G, 255);
        syncBuilder.define(RGB_B, 255);

        syncBuilder.define(ENTITY_SCALE, 1f);
//        syncBuilder.define(DIST_TRAVEL_PER_TICK, 0f);
        syncBuilder.define(ANIMATION_NAME, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60);

        syncBuilder.define(START_POS_X, getEntityX(this));
        syncBuilder.define(START_POS_Y, getEntityY(this));
        syncBuilder.define(START_POS_Z, getEntityZ(this));
        syncBuilder.define(HAS_TARGET_LOC_XYZ, false);
        syncBuilder.define(TARGET_POS_X, getEntityX(this));
        syncBuilder.define(TARGET_POS_Y, getEntityY(this));
        syncBuilder.define(TARGET_POS_Z, getEntityZ(this));

        syncBuilder.define(IS_BOUND_TO_ENTITY, false);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
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
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getEntityData().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
        }

//        if (pCompound.contains("VERTICALDEG", 99)) {
//            float tempFloat001 = pCompound.getFloat("VERTICALDEG");
//            this.getEntityData().set(POS_VERTICAL_DEG, tempFloat001);
//            setEntityVerticalFacingDeg(this, tempFloat001);
//        }

        if (pCompound.contains("ROTATIONZ", 99)) {
            float tempFloat001 = pCompound.getFloat("ROTATIONZ");
            this.getEntityData().set(ROTATION_Z, tempFloat001);
            this.RotationZ = tempFloat001;
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

        if (pCompound.contains("ENTITYSCALE", 99)) {
            float tempFloat001 = pCompound.getFloat("ENTITYSCALE");
            this.getEntityData().set(ENTITY_SCALE, tempFloat001);
            this.EntityScale = tempFloat001;
        }

        if (pCompound.contains("ANIMATIONAME", 8)) {
            String tempString001 = pCompound.getString("ANIMATIONAME");
            this.getEntityData().set(ANIMATION_NAME, tempString001);
            this.AnimationName = tempString001;
        }

//        if (pCompound.contains("DISTTRAVELPERTICK", 99)) {
//            float tempFloat001 = pCompound.getFloat("DISTTRAVELPERTICK");
//            this.getEntityData().set(DIST_TRAVEL_PER_TICK, tempFloat001);
//            this.DistanceTravelPerTick = tempFloat001;
//        }

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
