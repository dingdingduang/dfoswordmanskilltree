package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodel;

import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.minecraft.world.level.Level;

import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class TwoDEntity extends ProjectileHelper implements GeoEntity, IEntityWithComplexSpawn {
    private static final EntityDataAccessor<String> TEXTURE_NAME = SynchedEntityData.<String>defineId(TwoDEntity.class, EntityDataSerializers.STRING);

    private String TextureName = "textures/skillicons/sword/blademaster/blademaster_0_1.png";

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int lifetime = 150;

    private GeoModelAnimationController<TwoDEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<TwoDEntity> getAnimationController() {
        return this.GeoAnimationController;
    }

    public TwoDEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    public String getTextureName() { return this.TextureName; }
    public void setTextureName(String textureName) { this.TextureName = textureName; }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            lifetime--;
            if (lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
            }
        }
        else {
            if (this.lifetime > 1) {
                this.lifetime--;
            }
        }
    }

    protected <E extends TwoDEntity> PlayState predicate(AnimationState<E> event) {
        event.setAnimation(RawAnimation.begin().thenPlay("flash"));
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
    protected void defineSynchedData(SynchedEntityData.Builder syncBuilder) {
        super.defineSynchedData(syncBuilder);
        syncBuilder.define(TEXTURE_NAME, "textures/skillicons/sword/blademaster/blademaster_0_1.png");
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("TEXTURENAME", this.TextureName);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("TEXTURENAME", 8)) {
            String tempTexturePath = pCompound.getString("TEXTURENAME");
            this.getEntityData().set(TEXTURE_NAME, tempTexturePath);
            this.TextureName = tempTexturePath;
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
