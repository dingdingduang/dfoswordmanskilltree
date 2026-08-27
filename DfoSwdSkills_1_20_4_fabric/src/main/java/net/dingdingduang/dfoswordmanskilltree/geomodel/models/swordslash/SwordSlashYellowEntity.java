package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;

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

public class SwordSlashYellowEntity extends ProjectileHelper implements GeoEntity, EntityExtraSpawnData {
    private static final TrackedData<Float> POS_HORIZONTAL_DEG = DataTracker.<Float>registerData(SwordSlashYellowEntity.class, TrackedDataHandlerRegistry.FLOAT);
//    private static final TrackedData<Float> POS_VERTICAL_DEG = DataTracker.<Float>registerData(SwordSlashYellowEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ROTATION_Z = DataTracker.<Float>registerData(SwordSlashYellowEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
//    private float RotationZ = ExtraMathMethods.randomFloat()*360;
    private float RotationZ = 0.0f;
    private int lifetime = 8;
    private final int randomModelTexture = ExtraMathMethods.randomInt(10);

    private GeoModelAnimationController<SwordSlashYellowEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<SwordSlashYellowEntity> getAnimationController() { return this.GeoAnimationController; }

    public SwordSlashYellowEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.noClip = true;
        this.intersectionChecked = false;
    }

    public float getRotationZ() { return this.RotationZ; }
    public void setRotationZ(float rotationZ) { this.RotationZ = rotationZ; }

    public int getRandomModelTexture() { return this.randomModelTexture; }
    public int getLifetime() { return this.lifetime; }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;
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
        event.setAnimation(RawAnimation.begin().thenPlay(GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(GeoAnimationController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(POS_HORIZONTAL_DEG, 0.0f);
//        this.dataTracker.startTracking(POS_VERTICAL_DEG, 0.0f);
        this.dataTracker.startTracking(ROTATION_Z, 0.0f);
    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
//        pCompound.putFloat("VERTICALDEG", getEntityVerticalFacingDeg(this));
        pCompound.putFloat("ROTATIONZ", this.RotationZ);
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
