package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot;

import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.EntityType;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.minecraft.world.World;

import net.dingdingduang.dfoswordmanskilltree.entity.EntityExtraSpawnData;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class TwoDAnimatedTex64ManipulateYEntity extends ProjectileHelper implements GeoEntity, EntityExtraSpawnData {
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.<String>registerData(TwoDAnimatedTex64ManipulateYEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> LIFETIME = DataTracker.<Integer>registerData(TwoDAnimatedTex64ManipulateYEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MAX_LIFETIME = DataTracker.<Integer>registerData(TwoDAnimatedTex64ManipulateYEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private String TextureName = "textures/skillicons/sword/blademaster/blademaster_0_1.png";

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int lifetime = 30;
    private int MaxLifeTime = 30;

    private GeoModelAnimationController<TwoDAnimatedTex64ManipulateYEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<TwoDAnimatedTex64ManipulateYEntity> getAnimationController() {
        return this.GeoAnimationController;
    }

    public TwoDAnimatedTex64ManipulateYEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.noClip = true;
        this.intersectionChecked = false;
    }

    public String getTextureName() { return this.TextureName; }
    public void setTextureName(String textureName) { this.TextureName = textureName; }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    public int getMaxLifeTime() { return this.MaxLifeTime; }
    public void setMaxLifeTime(int maxLifeTime) { this.MaxLifeTime = maxLifeTime; }

//    public int getFrame() { return this.frame; }
//    public void setFrame(int frame) { this.frame = frame; }

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

    protected <E extends TwoDAnimatedTex64ManipulateYEntity> PlayState predicate(AnimationState<E> event) {
//        event.setAnimation(RawAnimation.begin().thenPlay("flash"));
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
        syncBuilder.add(TEXTURE_NAME, "textures/skillicons/sword/blademaster/blademaster_0_1.png");
        syncBuilder.add(LIFETIME, 30);
        syncBuilder.add(MAX_LIFETIME, 30);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putString("TEXTURENAME", this.TextureName);
        pCompound.putInt("LIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifeTime);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);

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
