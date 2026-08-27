package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.groundquake;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
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
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class EffGroundQuakeEntity extends ProjectileHelper implements GeoEntity, EntityExtraSpawnData {
    private static final TrackedData<Float> POS_HORIZONTAL_DEG = DataTracker.<Float>registerData(EffGroundQuakeEntity.class, TrackedDataHandlerRegistry.FLOAT);
//    private static final TrackedData<Float> POS_VERTICAL_DEG = DataTracker.<Float>registerData(EffGroundQuakeEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ROTATION_X = DataTracker.<Float>registerData(EffGroundQuakeEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.<String>registerData(EffGroundQuakeEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> LIFETIME = DataTracker.<Integer>registerData(EffGroundQuakeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MAX_LIFETIME = DataTracker.<Integer>registerData(EffGroundQuakeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<String> ANIMATION_NAME = DataTracker.<String>registerData(EffGroundQuakeEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Float> ENTITY_SCALE = DataTracker.<Float>registerData(EffGroundQuakeEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private String TextureName = "textures/entity/skilleff/blademaster/stab/";
    private String AnimationName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;
    private float EntityScale = 1f;

//    private float RotationX = ExtraMathMethods.randomFloat()*360;
    private float RotationX = 0.0f;
    private int lifetime = 8;
    private int MaxLifeTime = 8;
//    private final int randomModelTexture = ExtraMathMethods.randomInt(10);
    private final int randomModelTexture = 0;

    private GeoModelAnimationController<EffGroundQuakeEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<EffGroundQuakeEntity> getAnimationController() { return this.GeoAnimationController; }

    public EffGroundQuakeEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.noClip = true;
        this.intersectionChecked = false;
    }

    public float getRotationX() { return this.RotationX; }
    public void setRotationX(float rotationX) { this.RotationX = rotationX; }

    public String getTextureName() { return this.TextureName; }
    public void setTextureName(String textureName) { this.TextureName = textureName; }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    public int getMaxLifeTime() { return this.MaxLifeTime; }
    public void setMaxLifeTime(int maxLifeTime) { this.MaxLifeTime = maxLifeTime; }

    public int getRandomModelTexture() { return this.randomModelTexture; }

    public String getAnimationName() { return this.AnimationName; }
    public void setAnimationName(String animationName) { this.AnimationName = animationName; }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;
            if (this.lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
            }
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
    protected void initDataTracker(DataTracker.Builder syncBuilder) {
        super.initDataTracker(syncBuilder);
        syncBuilder.add(POS_HORIZONTAL_DEG, 0.0f);
//        syncBuilder.add(POS_VERTICAL_DEG, 0.0f);
        syncBuilder.add(ROTATION_X, 0.0f);

        syncBuilder.add(TEXTURE_NAME, "textures/entity/skilleff/blademaster/strikefromair/0.png");
        syncBuilder.add(LIFETIME, 30);
        syncBuilder.add(MAX_LIFETIME, 30);

        syncBuilder.add(ANIMATION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        syncBuilder.add(ENTITY_SCALE, 1f);
    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
//        pCompound.putFloat("VERTICALDEG", getEntityVerticalFacingDeg(this));
        pCompound.putFloat("ROTATIONX", this.RotationX);
        pCompound.putString("TEXTURENAME", this.TextureName);
        pCompound.putInt("LIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifeTime);

        pCompound.putString("ANIMATIONNAME", this.AnimationName);
        pCompound.putFloat("ENTITYSCALE", this.EntityScale);
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

        if (pCompound.contains("ROTATIONX", 99)) {
            float tempFloat001 = pCompound.getFloat("ROTATIONX");
            this.getDataTracker().set(ROTATION_X, tempFloat001);
            this.RotationX = tempFloat001;
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

        if (pCompound.contains("ANIMATIONNAME", 8)) {
            String tempAnimationName = pCompound.getString("ANIMATIONNAME");
            this.getDataTracker().set(ANIMATION_NAME, tempAnimationName);
            this.AnimationName = tempAnimationName;
        }

        if (pCompound.contains("ENTITYSCALE", 99)) {
            float tempFloat001 = pCompound.getFloat("ENTITYSCALE");
            this.getDataTracker().set(ENTITY_SCALE, tempFloat001);
            this.EntityScale = tempFloat001;
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
