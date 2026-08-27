package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair;

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

public class EffStrikeFromAirEntity extends ProjectileHelper implements IAnimatable, EntityExtraSpawnData {
    private static final TrackedData<Float> POS_HORIZONTAL_DEG = DataTracker.<Float>registerData(EffStrikeFromAirEntity.class, TrackedDataHandlerRegistry.FLOAT);
//    private static final TrackedData<Float> POS_VERTICAL_DEG = DataTracker.<Float>registerData(EffStabEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ROTATION_X = DataTracker.<Float>registerData(EffStrikeFromAirEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.<String>registerData(EffStrikeFromAirEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> LIFETIME = DataTracker.<Integer>registerData(EffStrikeFromAirEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<String> ANIMATION_NAME = DataTracker.<String>registerData(EffStrikeFromAirEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Float> ENTITY_SCALE = DataTracker.<Float>registerData(EffStrikeFromAirEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Float> TARGET_POS_X = DataTracker.<Float>registerData(EffStrikeFromAirEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_Y = DataTracker.<Float>registerData(EffStrikeFromAirEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_Z = DataTracker.<Float>registerData(EffStrikeFromAirEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private String TextureName = "textures/entity/skilleff/blademaster/strikefromair/0.png";
    private String AnimationName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;
    private float EntityScale = 1f;

//    private float RotationX = ExtraMathMethods.randomFloat()*360;
    private float RotationX = 0.0f;
    private int lifetime = 8;
//    private final int randomModelTexture = ExtraMathMethods.randomInt(10);
    private final int randomModelTexture = 0;

    private float TargetPosX = 0f;
    private float TargetPosY = 0f;
    private float TargetPosZ = 0f;

    private GeoModelAnimationController<EffStrikeFromAirEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<EffStrikeFromAirEntity> getAnimationController() { return this.GeoAnimationController; }

    public EffStrikeFromAirEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
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

    public int getRandomModelTexture() { return this.randomModelTexture; }

    public String getAnimationName() { return this.AnimationName; }
    public void setAnimationName(String animationName) { this.AnimationName = animationName; }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public float getTargetPosX() { return this.TargetPosX; }
    public void setTargetPosX(float targetPosX) { this.TargetPosX = targetPosX; }
    public float getTargetPosY() { return this.TargetPosY; }
    public void setTargetPosY(float targetPosY) { this.TargetPosY = targetPosY; }
    public float getTargetPosZ() { return this.TargetPosZ; }
    public void setTargetPosZ(float targetPosZ) { this.TargetPosZ = targetPosZ; }

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

        this.dataTracker.startTracking(TEXTURE_NAME, "textures/entity/skilleff/blademaster/strikefromair/0.png");
        this.dataTracker.startTracking(LIFETIME, 30);

        this.dataTracker.startTracking(ANIMATION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        this.dataTracker.startTracking(ENTITY_SCALE, 1f);

        this.dataTracker.startTracking(TARGET_POS_X, getEntityX(this));
        this.dataTracker.startTracking(TARGET_POS_Y, getEntityY(this));
        this.dataTracker.startTracking(TARGET_POS_Z, getEntityZ(this));
    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
//        pCompound.putFloat("VERTICALDEG", getEntityVerticalFacingDeg(this));
        pCompound.putFloat("ROTATIONX", this.RotationX);
        pCompound.putString("TEXTURENAME", this.TextureName);
        pCompound.putInt("LIFETIME", this.lifetime);

        pCompound.putString("ANIMATIONNAME", this.AnimationName);
        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        pCompound.putFloat("TARGETPOSX", this.TargetPosX);
        pCompound.putFloat("TARGETPOSY", this.TargetPosY);
        pCompound.putFloat("TARGETPOSZ", this.TargetPosZ);
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
