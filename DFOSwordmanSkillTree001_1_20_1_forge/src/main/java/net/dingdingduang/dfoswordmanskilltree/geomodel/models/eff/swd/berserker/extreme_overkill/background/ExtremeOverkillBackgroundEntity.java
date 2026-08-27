package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.berserker.extreme_overkill.background;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class ExtremeOverkillBackgroundEntity extends ProjectileHelper implements GeoEntity, IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Float> POS_HORIZONTAL_DEG = SynchedEntityData.<Float>defineId(ExtremeOverkillBackgroundEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Integer> LIFETIME = SynchedEntityData.<Integer>defineId(ExtremeOverkillBackgroundEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(ExtremeOverkillBackgroundEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(ExtremeOverkillBackgroundEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> AOE_RANGE = SynchedEntityData.<Float>defineId(ExtremeOverkillBackgroundEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<String> CLIENT_ACTION_NAME = SynchedEntityData.<String>defineId(ExtremeOverkillBackgroundEntity.class, EntityDataSerializers.STRING);

    private static final String EXTREME_OVERKILL_START_ANIMATION = "start";
    private static final String EXTREME_OVERKILL_LOOPING_ANIMATION = "looping";
    private static final String EXTREME_OVERKILL_END_ANIMATION = "end";

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int lifetime = 300;
    private int MaxLifeTime = 300;

    private float EntityScale = 1f;

    private String ClientActionName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

    private float AoeRange = 8f;

    private GeoModelAnimationController<ExtremeOverkillBackgroundEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<ExtremeOverkillBackgroundEntity> getAnimationController() {
        return this.GeoAnimationController;
    }

    public ExtremeOverkillBackgroundEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    public int getMaxLifeTime() { return this.MaxLifeTime; }
    public void setMaxLifeTime(int maxLifeTime) { this.MaxLifeTime = maxLifeTime; }

    public String getClientActionName() { return this.ClientActionName; }
    public void setClientActionName(String clientActionName) { this.ClientActionName = clientActionName; }

    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }

    public float getAoeRange() { return this.AoeRange; }
    public void setAoeRange(float aoeRange) { this.AoeRange = aoeRange; }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

            if (this.lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
            }

            if (this.EntityServerAction != null) {
                int currentTickTime = (this.MaxLifeTime - this.lifetime) / this.EntityServerActionTickPeriod;
                if (this.PreviousEntityActionTriggeredTick != currentTickTime) {
                    this.PreviousEntityActionTriggeredTick = currentTickTime;
                    this.EntityServerAction.executeAction(this);
                }
            }
        }
        else {
            if (this.lifetime > 1) {
                this.lifetime--;
            }

            MethodEntityAction ClientActionMethod = GeoModelClientMethodsRegistry.getGeoModelClientActionMethod(this.ClientActionName);
            if (ClientActionMethod != null) {
                ClientActionMethod.executeAction(this);
            }
        }
    }

    protected <E extends ExtremeOverkillBackgroundEntity> PlayState predicate(AnimationState<E> event) {
        int currentLifeTime = this.MaxLifeTime - this.lifetime;
        if (currentLifeTime <= 5) {
            event.setAnimation(RawAnimation.begin().thenLoop(EXTREME_OVERKILL_START_ANIMATION));
        }
        else if (this.lifetime <= 5) {
            event.setAnimation(RawAnimation.begin().thenLoop(EXTREME_OVERKILL_END_ANIMATION));
        }
        else {
            event.setAnimation(RawAnimation.begin().thenLoop(EXTREME_OVERKILL_LOOPING_ANIMATION));
        }
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(POS_HORIZONTAL_DEG, 0f);

        this.entityData.define(LIFETIME, 300);
        this.entityData.define(MAX_LIFETIME, 300);

        this.entityData.define(ENTITY_SCALE, 1f);

        this.entityData.define(AOE_RANGE, 8f);

        this.entityData.define(CLIENT_ACTION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));

        pCompound.putInt("LIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifeTime);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        //aoe range
        pCompound.putFloat("AOERANGE", this.AoeRange);

        //client action method
        pCompound.putString("CLIENTACTIONAME", this.ClientActionName);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getEntityData().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
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

        //============
        if (pCompound.contains("ENTITYSCALE", 99)) {
            float tempFloat001 = pCompound.getFloat("ENTITYSCALE");
            this.getEntityData().set(ENTITY_SCALE, tempFloat001);
            this.EntityScale = tempFloat001;
        }

        //===============
        if (pCompound.contains("AOERANGE", 99)) {
            float tempFloat001 = pCompound.getFloat("AOERANGE");
            this.getEntityData().set(AOE_RANGE, tempFloat001);
            this.AoeRange = tempFloat001;
        }

        //=================
        if (pCompound.contains("CLIENTACTIONAME", 8)) {
            String tempString001 = pCompound.getString("CLIENTACTIONAME");
            this.getEntityData().set(CLIENT_ACTION_NAME, tempString001);
            this.ClientActionName = tempString001;
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();
        this.addAdditionalSaveData(tag);
        buffer.writeNbt(tag);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        CompoundTag tag = additionalData.readNbt();
        this.readAdditionalSaveData(tag);
    }
}
