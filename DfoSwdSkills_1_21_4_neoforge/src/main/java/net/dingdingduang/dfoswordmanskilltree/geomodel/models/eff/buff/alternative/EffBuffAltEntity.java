package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.alternative;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
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
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityPos;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class EffBuffAltEntity extends ProjectileHelper implements GeoEntity, IEntityWithComplexSpawn {
    private static final EntityDataAccessor<Float> POS_HORIZONTAL_DEG = SynchedEntityData.<Float>defineId(EffBuffAltEntity.class, EntityDataSerializers.FLOAT);
//    private static final EntityDataAccessor<Float> ROTATION_X = SynchedEntityData.<Float>defineId(EffBuffEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<String> TEXTURE_NAME = SynchedEntityData.<String>defineId(EffBuffAltEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> LIFETIME = SynchedEntityData.<Integer>defineId(EffBuffAltEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(EffBuffAltEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> ANIMATION_NAME = SynchedEntityData.<String>defineId(EffBuffAltEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(EffBuffAltEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> IS_BOUND_TO_ENTITY = SynchedEntityData.<Boolean>defineId(EffBuffAltEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> BOUND_ENTITY_ID = SynchedEntityData.<Integer>defineId(EffBuffAltEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> IS_ANIMATION_LOOPING = SynchedEntityData.<Boolean>defineId(EffBuffAltEntity.class, EntityDataSerializers.BOOLEAN);

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private String TextureName = "textures/entity/skilleff/common/buff/plus/";
    private String AnimationName = "buff_moving_up";
    private float EntityScale = 1f;

//    private float RotationX = ExtraMathMethods.randomFloat()*360;
//    private float RotationX = 0.0f;
    private int lifetime = 300;
    private int MaxLifeTime = 300;

    private boolean IsBoundToEntity = false;
    private int BoundEntityID = -1;

    private boolean IsAnimationLooping = false;

    private GeoModelAnimationController<EffBuffAltEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<EffBuffAltEntity> getAnimationController() { return this.GeoAnimationController; }

    public EffBuffAltEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
//        this.noPhysics = true;
        this.blocksBuilding = false;
    }

//    public float getRotationX() { return this.RotationX; }
//    public void setRotationX(float rotationX) { this.RotationX = rotationX; }

    public String getTextureName() { return this.TextureName; }
    public void setTextureName(String textureName) { this.TextureName = textureName; }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    public int getMaxLifeTime() { return this.MaxLifeTime; }
    public void setMaxLifeTime(int maxLifeTime) { this.MaxLifeTime = maxLifeTime; }

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

    public boolean isAnimationLooping() { return this.IsAnimationLooping; }
    public void setAnimationLooping(boolean animationLooping) { this.IsAnimationLooping = animationLooping; }

    //update every 0.1s?
    public void tick() {
        if (this.IsBoundToEntity && this.BoundEntityID < 0) {
            this.lifetime = 0;
        }

        Entity BoundEntity;
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;
            if (this.IsBoundToEntity && this.BoundEntityID >= 0 && (BoundEntity = getEntityByIntID(getEntityLevel(this), this.BoundEntityID)) != null) {
                if (SBConditions.hasMoreThanZeroHealth(BoundEntity)) {
                    moveEntityToPos(this, getEntityPos(BoundEntity));
                }
                else {
                    this.lifetime = 0;
                }
            }
            if (this.lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
            }
        }
        else {
            if (this.IsBoundToEntity && this.BoundEntityID >= 0 && (BoundEntity = getEntityByIntID(getEntityLevel(this), this.BoundEntityID)) != null) {
                if (!SBConditions.hasMoreThanZeroHealth(BoundEntity)) {
                    EntityMethods.RemoveEntity(this);
                }
            }
            if (this.lifetime > 1) {
                this.lifetime--;
            }
        }
    }

    protected <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (this.IsAnimationLooping) {
            event.setAnimation(RawAnimation.begin().thenLoop(this.AnimationName));
        }
        else {
            event.setAnimation(RawAnimation.begin().thenPlay(this.AnimationName));
        }
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
//        syncBulider.define(ROTATION_X, 0.0f);

        syncBulider.define(TEXTURE_NAME, "textures/entity/skilleff/common/buff/plus/0_7.png");
        syncBulider.define(LIFETIME, 30);
        syncBulider.define(MAX_LIFETIME, 30);

        syncBulider.define(ANIMATION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        syncBulider.define(ENTITY_SCALE, 1f);

        syncBulider.define(IS_BOUND_TO_ENTITY, false);
        syncBulider.define(BOUND_ENTITY_ID, -1);

        syncBulider.define(IS_ANIMATION_LOOPING, false);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
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

        pCompound.putBoolean("ISANIMATIONLOOPING", this.IsAnimationLooping);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getEntityData().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
        }

//        if (pCompound.contains("ROTATIONX", 99)) {
//            float tempFloat001 = pCompound.getFloat("ROTATIONX");
//            this.getEntityData().set(ROTATION_X, tempFloat001);
//            this.RotationX = tempFloat001;
//        }

        if (pCompound.contains("TEXTURENAME", 8)) {
            String tempTexturePath = pCompound.getString("TEXTURENAME");
            this.getEntityData().set(TEXTURE_NAME, tempTexturePath);
            this.TextureName = tempTexturePath;
        }

        if (pCompound.contains("LIFETIME", 99)) {
            int tempInt = pCompound.getInt("LIFETIME");
            this.getEntityData().set(LIFETIME, tempInt);
            this.lifetime = tempInt;
        }

        if (pCompound.contains("MAXLIFETIME", 99)) {
            int tempInt = pCompound.getInt("MAXLIFETIME");
            this.getEntityData().set(MAX_LIFETIME, tempInt);
            this.MaxLifeTime = tempInt;
        }

        if (pCompound.contains("ANIMATIONNAME", 8)) {
            String tempAnimationName = pCompound.getString("ANIMATIONNAME");
            this.getEntityData().set(ANIMATION_NAME, tempAnimationName);
            this.AnimationName = tempAnimationName;
        }

        if (pCompound.contains("ENTITYSCALE", 99)) {
            float tempFloat001 = pCompound.getFloat("ENTITYSCALE");
            this.getEntityData().set(ENTITY_SCALE, tempFloat001);
            this.EntityScale = tempFloat001;
        }

        if (pCompound.contains("ISBOUNDTOENTITY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISBOUNDTOENTITY");
            this.getEntityData().set(IS_BOUND_TO_ENTITY, tempBool);
            this.IsBoundToEntity = tempBool;
        }

        if (pCompound.contains("BOUNDENTITYID", 99)) {
            int tempInt = pCompound.getInt("BOUNDENTITYID");
            this.getEntityData().set(BOUND_ENTITY_ID, tempInt);
            this.BoundEntityID = tempInt;
        }

        if (pCompound.contains("ISANIMATIONLOOPING", 1)) {
            boolean tempBool = pCompound.getBoolean("ISANIMATIONLOOPING");
            this.getEntityData().set(IS_ANIMATION_LOOPING, tempBool);
            this.IsAnimationLooping = tempBool;
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
