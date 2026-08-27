package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.stab;

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

public class EffStabEntity extends ProjectileHelper implements IAnimatable, EntityExtraSpawnData {
    private static final TrackedData<Float> POS_HORIZONTAL_DEG = DataTracker.<Float>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.FLOAT);
//    private static final TrackedData<Float> POS_VERTICAL_DEG = DataTracker.<Float>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ROTATION_X = DataTracker.<Float>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.<String>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> LIFETIME = DataTracker.<Integer>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MAX_LIFETIME = DataTracker.<Integer>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<String> ANIMATION_NAME = DataTracker.<String>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Float> ENTITY_SCALE = DataTracker.<Float>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Boolean> IS_BOUND_TO_ENTITY = DataTracker.<Boolean>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> ADDITIONAL_POS_X = DataTracker.<Float>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ADDITIONAL_POS_Y = DataTracker.<Float>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ADDITIONAL_POS_Z = DataTracker.<Float>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private String TextureName = "textures/entity/skilleff/blademaster/stab/";
    private String AnimationName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;
    private float EntityScale = 1f;

//    private float RotationX = ExtraMathMethods.randomFloat()*360;
    private float RotationX = 0.0f;
    private int lifetime = 8;
    private int MaxLifeTime = 8;
//    private final int randomModelTexture = ExtraMathMethods.randomInt(10);
    private final int randomModelTexture = 0;

    private boolean IsBoundToEntity = false;
    private float AdditionalPosX = 0;
    private float AdditionalPosY = 0;
    private float AdditionalPosZ = 0;

    private GeoModelAnimationController<EffStabEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<EffStabEntity> getAnimationController() { return this.GeoAnimationController; }

    public EffStabEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
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

    public boolean isBoundToEntity() { return this.IsBoundToEntity; }
    public void setBoundToEntity(boolean boundToEntity) { this.IsBoundToEntity = boundToEntity; }
    public float getAdditionalPosX() { return this.AdditionalPosX; }
    public void setAdditionalPosX(float additionalPosX) { this.AdditionalPosX = additionalPosX; }
    public float getAdditionalPosZ() { return this.AdditionalPosZ; }
    public void setAdditionalPosZ(float additionalPosZ) { this.AdditionalPosZ = additionalPosZ; }
    public float getAdditionalPosY() { return this.AdditionalPosY; }
    public void setAdditionalPosY(float additionalPosY) { this.AdditionalPosY = additionalPosY; }

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

    protected <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(this.AnimationName, ILoopType.EDefaultLoopTypes.LOOP));
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
        this.dataTracker.startTracking(ROTATION_X, 0.0f);

        this.dataTracker.startTracking(TEXTURE_NAME, "textures/entity/skilleff/blademaster/stab/0_0.png");
        this.dataTracker.startTracking(LIFETIME, 30);
        this.dataTracker.startTracking(MAX_LIFETIME, 30);

        this.dataTracker.startTracking(ANIMATION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        this.dataTracker.startTracking(ENTITY_SCALE, 1f);

        this.dataTracker.startTracking(IS_BOUND_TO_ENTITY, false);
        this.dataTracker.startTracking(ADDITIONAL_POS_X, 0f);
        this.dataTracker.startTracking(ADDITIONAL_POS_Y, 0f);
        this.dataTracker.startTracking(ADDITIONAL_POS_Z, 0f);
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

        pCompound.putBoolean("ISBOUNDTOENTITY", this.IsBoundToEntity);

        pCompound.putFloat("ADDITIONALPOSX", this.AdditionalPosX);
        pCompound.putFloat("ADDITIONALPOSY", this.AdditionalPosY);
        pCompound.putFloat("ADDITIONALPOSZ", this.AdditionalPosZ);
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

        if (pCompound.contains("ISBOUNDTOENTITY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISBOUNDTOENTITY");
            this.getDataTracker().set(IS_BOUND_TO_ENTITY, tempBool);
            this.IsBoundToEntity = tempBool;
        }

        //additional pos
        if (pCompound.contains("ADDITIONALPOSX", 99)) {
            float tempFloat001 = pCompound.getFloat("ADDITIONALPOSX");
            this.getDataTracker().set(ADDITIONAL_POS_X, tempFloat001);
            this.AdditionalPosX = tempFloat001;
        }

        if (pCompound.contains("ADDITIONALPOSY", 99)) {
            float tempFloat001 = pCompound.getFloat("ADDITIONALPOSY");
            this.getDataTracker().set(ADDITIONAL_POS_Y, tempFloat001);
            this.AdditionalPosY = tempFloat001;
        }

        if (pCompound.contains("ADDITIONALPOSZ", 99)) {
            float tempFloat001 = pCompound.getFloat("ADDITIONALPOSZ");
            this.getDataTracker().set(ADDITIONAL_POS_Z, tempFloat001);
            this.AdditionalPosZ = tempFloat001;
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
