package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
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

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class SwordSlashYellowEntity extends ProjectileHelper implements GeoEntity, IEntityWithComplexSpawn {
    private static final EntityDataAccessor<Float> POS_HORIZONTAL_DEG = SynchedEntityData.<Float>defineId(SwordSlashYellowEntity.class, EntityDataSerializers.FLOAT);
//    private static final EntityDataAccessor<Float> POS_VERTICAL_DEG = SynchedEntityData.<Float>defineId(SwordSlashYellowEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROTATION_Z = SynchedEntityData.<Float>defineId(SwordSlashYellowEntity.class, EntityDataSerializers.FLOAT);

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
//    private float RotationZ = ExtraMathMethods.randomFloat()*360;
    private float RotationZ = 0.0f;
    private int lifetime = 8;
    private final int randomModelTexture = ExtraMathMethods.randomInt(10);

    private GeoModelAnimationController<SwordSlashYellowEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<SwordSlashYellowEntity> getAnimationController() { return this.GeoAnimationController; }

    public SwordSlashYellowEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
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
    protected void defineSynchedData(SynchedEntityData.Builder syncBulider) {
        super.defineSynchedData(syncBulider);
        syncBulider.define(POS_HORIZONTAL_DEG, 0.0f);
//        syncBulider.define(POS_VERTICAL_DEG, 0.0f);
        syncBulider.define(ROTATION_Z, 0.0f);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
//        pCompound.putFloat("VERTICALDEG", getEntityVerticalFacingDeg(this));
        pCompound.putFloat("ROTATIONZ", this.RotationZ);
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
