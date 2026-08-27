package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.Entity;
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

public class EffBuffEntity extends ProjectileHelper implements IAnimatable, EntityExtraSpawnData {
    private static final TrackedData<Float> POS_HORIZONTAL_DEG = DataTracker.<Float>registerData(EffBuffEntity.class, TrackedDataHandlerRegistry.FLOAT);
//    private static final TrackedData<Float> ROTATION_X = DataTracker.<Float>registerData(EffBuffEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.<String>registerData(EffBuffEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> LIFETIME = DataTracker.<Integer>registerData(EffBuffEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MAX_LIFETIME = DataTracker.<Integer>registerData(EffBuffEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<String> ANIMATION_NAME = DataTracker.<String>registerData(EffBuffEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Float> ENTITY_SCALE = DataTracker.<Float>registerData(EffBuffEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Boolean> IS_BOUND_TO_ENTITY = DataTracker.<Boolean>registerData(EffBuffEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> BOUND_ENTITY_ID = DataTracker.<Integer>registerData(EffBuffEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private String TextureName = "textures/entity/skilleff/common/buff/plus/";
    private String AnimationName = "buff_moving_up";
    private float EntityScale = 1f;

//    private float RotationX = ExtraMathMethods.randomFloat()*360;
//    private float RotationX = 0.0f;
    private int lifetime = 300;
    private int MaxLifeTime = 300;
//    private final int randomModelTexture = ExtraMathMethods.randomInt(10);
    private final int randomModelTexture = 0;

    private boolean IsBoundToEntity = false;
    private int BoundEntityID = -1;

    private int randomDelayTicks = ExtraMathMethods.randomInt(60);

    private GeoModelAnimationController<EffBuffEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<EffBuffEntity> getAnimationController() { return this.GeoAnimationController; }

    public EffBuffEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.noClip = true;
        this.intersectionChecked = false;
    }

//    public float getRotationX() { return this.RotationX; }
//    public void setRotationX(float rotationX) { this.RotationX = rotationX; }

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
    public void setBoundToEntity(boolean boundToEntity, int entityID) {
        this.IsBoundToEntity = boundToEntity;
        this.BoundEntityID = entityID;
    }
    public int getBoundEntityID() { return this.BoundEntityID; }
    public void setBoundEntityID(int boundEntityID) { this.BoundEntityID = boundEntityID; }

    public int getRandomDelayTicks() { return this.randomDelayTicks; }

    //update every 0.1s?
    public void tick() {
        Entity BoundEntity;
        if (this.IsBoundToEntity && this.BoundEntityID >= 0 && (BoundEntity = getEntityByIntID(getEntityLevel(this), this.BoundEntityID)) != null) {
//            if (SBConditions.hasMoreThanZeroHealth(BoundEntity)) {
//                moveEntityToPos(this, getEntityPos(BoundEntity));
////                setEntityDeltaMovement(this, getEntityDeltaMovement(BoundEntity));
//            } else {
//                this.lifetime = 0;
//            }
            if (!SBConditions.hasMoreThanZeroHealth(BoundEntity)) {
                this.lifetime = 0;
            }
        }
//        printInGameMsg("remaining lifetime: "+this.lifetime+", basetick: "+tickCount);
        if (this.IsBoundToEntity && this.BoundEntityID < 0) {
            this.lifetime = 0;
        }

        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;
            if (this.lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
//                return;
            }
        } else {
            if (this.lifetime > 1) {
                this.lifetime--;
            }
//            return;
        }

//        super.tick();
    }

    protected <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.MaxLifeTime - this.lifetime > this.randomDelayTicks) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(this.AnimationName, ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(DfoSwordmanSkillTreeConstants.NO_ANIMATION, ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
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
//        this.dataTracker.startTracking(ROTATION_X, 0.0f);

        this.dataTracker.startTracking(TEXTURE_NAME, "textures/entity/skilleff/common/buff/plus/0_7.png");
        this.dataTracker.startTracking(LIFETIME, 30);
        this.dataTracker.startTracking(MAX_LIFETIME, 30);

        this.dataTracker.startTracking(ANIMATION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        this.dataTracker.startTracking(ENTITY_SCALE, 1f);

        this.dataTracker.startTracking(IS_BOUND_TO_ENTITY, false);
        this.dataTracker.startTracking(BOUND_ENTITY_ID, -1);
    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
//        pCompound.putFloat("VERTICALDEG", getEntityVerticalFacingDeg(this));
//        pCompound.putFloat("ROTATIONX", this.RotationX);
        pCompound.putString("TEXTURENAME", this.TextureName);
        pCompound.putInt("LIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifeTime);

        pCompound.putString("ANIMATIONNAME", this.AnimationName);
        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        pCompound.putBoolean("ISBOUNDTOENTITY", this.IsBoundToEntity);
        pCompound.putInt("BOUNDENTITYID", this.BoundEntityID);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getDataTracker().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
        }

//        if (pCompound.contains("ROTATIONX", 99)) {
//            float tempFloat001 = pCompound.getFloat("ROTATIONX");
//            this.getDataTracker().set(ROTATION_X, tempFloat001);
//            this.RotationX = tempFloat001;
//        }

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

        if (pCompound.contains("BOUNDENTITYID", 99)) {
            int tempInt = pCompound.getInt("BOUNDENTITYID");
            this.getDataTracker().set(BOUND_ENTITY_ID, tempInt);
            this.BoundEntityID = tempInt;
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
