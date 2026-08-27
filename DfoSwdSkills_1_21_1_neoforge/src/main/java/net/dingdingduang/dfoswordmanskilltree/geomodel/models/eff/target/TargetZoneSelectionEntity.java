package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingSendMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.nbt.CompoundTag;
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

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class TargetZoneSelectionEntity extends ProjectileHelper implements GeoEntity, IEntityWithComplexSpawn {
    private static final EntityDataAccessor<Float> POS_HORIZONTAL_DEG = SynchedEntityData.<Float>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Integer> REMAINING_LIFETIME = SynchedEntityData.<Integer>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> IS_START_FRAME_FADING = SynchedEntityData.<Boolean>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_END_FRAME_FADING = SynchedEntityData.<Boolean>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> START_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LOOP_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> END_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_FRAME_DISTRIBUTED_EVENLY = SynchedEntityData.<Boolean>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<String> ANIMATION_NAME = SynchedEntityData.<String>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> TEXTURE_NAME = SynchedEntityData.<String>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Boolean> IS_BOUND_TO_ENTITY = SynchedEntityData.<Boolean>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_VISIBLE_TO_ALL_PLAYERS = SynchedEntityData.<Boolean>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<String> PARTICLE_NAME = SynchedEntityData.<String>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<String> CLIENT_ACTION_NAME = SynchedEntityData.<String>defineId(TargetZoneSelectionEntity.class, EntityDataSerializers.STRING);

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int lifetime = 12;
    private int MaxLifetime = 12;

    //animation texutre and its frames
    private boolean IsStartFrameFading = true;
    private boolean IsEndFrameFading = true;
    private int StartTotalFrame = 4;
    private int LoopTotalFrame = 1;
    private int EndTotalFrame = 7;
    private boolean isFrameDistributedEvenly = false;
    private int ClientLoopFrameCounter = 0;
    private int[] ClientTotalFrameIndexHelper = null;

    private String TextureName = "textures/entity/slash/white/0_0";
    private String AnimationName = GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60;

    private float EntityScale = 1f;

    private boolean HasTargetLocXYZ = true;
    private boolean isPosInitialized = false;
    private float StartPosX = 0f;
    private float StartPosY = 0f;
    private float StartPosZ = 0f;
    private float TargetPosX = 0f;
    private float TargetPosY = 0f;
    private float TargetPosZ = 0f;

    private boolean IsBoundToEntity = false;

    private boolean IsVisibleToAllPlayers = false;

    private String ParticleName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

    private String ClientActionName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private GeoModelAnimationController<TargetZoneSelectionEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<TargetZoneSelectionEntity> getAnimationController() { return this.GeoAnimationController; }

    public String getClientActionName() { return this.ClientActionName; }
    public void setClientActionName(String clientActionName) { this.ClientActionName = clientActionName; }

    public TargetZoneSelectionEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetimeTicks) { this.lifetime = lifetimeTicks; }
    public int getMaxLifetime() { return this.MaxLifetime; }
    public void setMaxLifetime(int maxLifetime) { this.MaxLifetime = maxLifetime; }

    public int getClientLoopFrameCounter() { return this.ClientLoopFrameCounter; }
    public void setClientLoopFrameCounter(int clientLoopFrameCounter) { this.ClientLoopFrameCounter = clientLoopFrameCounter; }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public boolean isStartFrameFading() { return this.IsStartFrameFading; }
    public void setStartFrameFading(boolean startFrameFading) { this.IsStartFrameFading = startFrameFading; }
    public boolean isEndFrameFading() { return IsEndFrameFading; }
    public void setEndFrameFading(boolean endFrameFading) { this.IsEndFrameFading = endFrameFading; }

    public int getStartTotalFrame() { return this.StartTotalFrame; }
    public void setStartTotalFrame(int startTotalFrame) { this.StartTotalFrame = startTotalFrame; }
    public int getLoopTotalFrame() { return this.LoopTotalFrame; }
    public void setLoopTotalFrame(int loopTotalFrame) { this.LoopTotalFrame = loopTotalFrame; }
    public int getEndTotalFrame() { return this.EndTotalFrame; }
    public void setEndTotalFrame(int endTotalFrame) { this.EndTotalFrame = endTotalFrame; }
    public boolean isFrameDistributedEvenly() { return this.isFrameDistributedEvenly; }
    public void setFrameDistributedEvenly(boolean frameDistributedEvenly) { this.isFrameDistributedEvenly = frameDistributedEvenly; }
    public int[] getClientTotalFrameIndexHelper() { return this.ClientTotalFrameIndexHelper; }
    public void setClientTotalFrameIndexHelper(int[] clientTotalFrameIndexHelper) { ClientTotalFrameIndexHelper = clientTotalFrameIndexHelper; }

    public String getTextureName() { return this.TextureName; }
    public void setTextureName(String textureName, int startTotalFrame, int loopTotalFrame, int endTotalFrame, boolean isStartFrameFading, boolean isEndFrameFading) {
        this.TextureName = textureName;
        this.StartTotalFrame = startTotalFrame;
        this.LoopTotalFrame = loopTotalFrame;
        this.EndTotalFrame = endTotalFrame;
        this.IsStartFrameFading = isStartFrameFading;
        this.IsEndFrameFading = isEndFrameFading;
    }
    public String getAnimationName() { return this.AnimationName; }
    public void setAnimationName(String animationName) { this.AnimationName = animationName; }

    public boolean isBoundToEntity() { return this.IsBoundToEntity; }
    public void setIsBoundToEntity(boolean boundToEntity) { this.IsBoundToEntity = boundToEntity; }

    public boolean isVisibleToAllPlayers() { return this.IsVisibleToAllPlayers; }
    public void setVisibleToAllPlayers(boolean visibleToAllPlayers) { this.IsVisibleToAllPlayers = visibleToAllPlayers; }

    public String getParticleName() { return this.ParticleName; }
    public void setParticleName(String particleName) { this.ParticleName = particleName; }

    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }


    //client

    public void setStartPosLoc(float x1, float y1, float z1) {
        this.StartPosX = x1;
        this.StartPosY = y1;
        this.StartPosZ = z1;
    }
    public float getStartPosX() { return this.StartPosX; }
    public void setStartPosX(float StartPosX) { this.StartPosX = StartPosX; }
    public float getStartPosY() { return this.StartPosY; }
    public void setStartPosY(float StartPosY) { this.StartPosY = StartPosY; }
    public float getStartPosZ() { return this.StartPosZ; }
    public void setStartPosZ(float StartPosZ) { this.StartPosZ = StartPosZ; }

    public boolean hasTargetLocXYZ() { return this.HasTargetLocXYZ; }
    public void setHasTargetLocXYZBoolean(boolean a) { this.HasTargetLocXYZ = a; }

    public void setTargetLoc(float target_x1, float target_y1, float target_z1) {
        this.TargetPosX = target_x1;
        this.TargetPosY = target_y1;
        this.TargetPosZ = target_z1;
    }
    public float getTargetPosX() { return this.TargetPosX; }
    public void setTargetPosX(float targetPosX) { this.TargetPosX = targetPosX; }
    public float getTargetPosY() { return this.TargetPosY; }
    public void setTargetPosY(float targetPosY) { this.TargetPosY = targetPosY; }
    public float getTargetPosZ() { return this.TargetPosZ; }
    public void setTargetPosZ(float targetPosZ) { this.TargetPosZ = targetPosZ; }

    public boolean isPosInitialized() { return this.isPosInitialized; }
    public void setPosInitialized(boolean posInitialized) { this.isPosInitialized = posInitialized; }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

            if (!this.HasTargetLocXYZ && this.IsBoundToEntity) {
                if (this.getOwner() != null) {
                    moveEntityToPos(this, getEntityPos(this.getOwner()).add(this.TargetPosX, this.TargetPosY, this.TargetPosZ));
                }
            }

            if (this.lifetime <= 0) {
                RemoveEntity(this);
            }

            if (this.EntityServerAction != null) {
                int currentTickTime = (this.MaxLifetime - this.lifetime) / this.EntityServerActionTickPeriod;
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

            MethodEntityAction ParticleMethod = ParticleRegistry.getParticleMethod(this.ParticleName);
            if (ParticleMethod != null) {
                ParticleMethod.executeAction(this);
            }

            MethodEntityAction ClientActionMethod = GeoModelClientMethodsRegistry.getGeoModelClientActionMethod(this.ClientActionName);
            if (ClientActionMethod != null) {
                ClientActionMethod.executeAction(this);
            }

//            this.StartPosX = this.TargetPosX;
//            this.StartPosY = this.TargetPosY;
//            this.StartPosZ = this.TargetPosZ;
            DfoSwdNetworkingSendMsgMethods.SendUpdateEntityPosPacketFromClient(getEntityX(this), getEntityY(this), getEntityZ(this), getEntityHashID(this));
        }
    }

    protected <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        event.setAnimation(RawAnimation.begin().thenPlayAndHold(this.AnimationName));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(GeoAnimationController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder syncBuilder) {
        super.defineSynchedData(syncBuilder);
        syncBuilder.define(POS_HORIZONTAL_DEG, 0.0f);

        syncBuilder.define(ENTITY_SCALE, 1f);

        syncBuilder.define(REMAINING_LIFETIME, 12);
        syncBuilder.define(MAX_LIFETIME, 12);

        syncBuilder.define(IS_START_FRAME_FADING, true);
        syncBuilder.define(IS_END_FRAME_FADING, true);
        syncBuilder.define(START_TOTAL_FRAME, 4);
        syncBuilder.define(LOOP_TOTAL_FRAME, 1);
        syncBuilder.define(END_TOTAL_FRAME, 7);
        syncBuilder.define(IS_FRAME_DISTRIBUTED_EVENLY, false);

        syncBuilder.define(TEXTURE_NAME, "textures/entity/slash/white/0_0");
        syncBuilder.define(ANIMATION_NAME, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60);

        syncBuilder.define(IS_VISIBLE_TO_ALL_PLAYERS, false);

        syncBuilder.define(IS_BOUND_TO_ENTITY, false);

        syncBuilder.define(PARTICLE_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);

        syncBuilder.define(CLIENT_ACTION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        pCompound.putInt("REMAININGLIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifetime);

        pCompound.putBoolean("ISSTARTFRAMEFADING", this.IsStartFrameFading);
        pCompound.putBoolean("ISENDFRAMEFADING", this.IsEndFrameFading);
        pCompound.putInt("STARTTOTALFRAME", this.StartTotalFrame);
        pCompound.putInt("LOOPTOTALFRAME", this.LoopTotalFrame);
        pCompound.putInt("ENDTOTALFRAME", this.EndTotalFrame);
        pCompound.putBoolean("ISFRAMEDISTRIBUTEDEVENLY", this.isFrameDistributedEvenly);

        pCompound.putString("TEXTURENAME", this.TextureName);
        pCompound.putString("ANIMATIONAME", this.AnimationName);

        pCompound.putBoolean("ISVISIBLETOALLPLAYERS", this.IsVisibleToAllPlayers);

        //bound entity
        pCompound.putBoolean("ISBOUNDTOENTITY", this.IsBoundToEntity);

        //particle method name
        pCompound.putString("PARTICLENAME", this.ParticleName);

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

        //============
        if (pCompound.contains("ENTITYSCALE", 99)) {
            float tempFloat001 = pCompound.getFloat("ENTITYSCALE");
            this.getEntityData().set(ENTITY_SCALE, tempFloat001);
            this.EntityScale = tempFloat001;
        }

        //===========
        if (pCompound.contains("REMAININGLIFETIME", 99)) {
            int tempInt001 = pCompound.getInt("REMAININGLIFETIME");
            this.getEntityData().set(REMAINING_LIFETIME, tempInt001);
            this.lifetime = tempInt001;
        }

        if (pCompound.contains("MAXLIFETIME", 99)) {
            int tempInt001 = pCompound.getInt("MAXLIFETIME");
            this.getEntityData().set(MAX_LIFETIME, tempInt001);
            this.MaxLifetime = tempInt001;
        }

        //==================
        if (pCompound.contains("ISSTARTFRAMEFADING", 1)) {
            boolean tempBool = pCompound.getBoolean("ISSTARTFRAMEFADING");
            this.getEntityData().set(IS_START_FRAME_FADING, tempBool);
            this.IsStartFrameFading = tempBool;
        }

        if (pCompound.contains("ISENDFRAMEFADING", 1)) {
            boolean tempBool = pCompound.getBoolean("ISENDFRAMEFADING");
            this.getEntityData().set(IS_END_FRAME_FADING, tempBool);
            this.IsEndFrameFading = tempBool;
        }

        if (pCompound.contains("STARTTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("STARTTOTALFRAME");
            this.getEntityData().set(START_TOTAL_FRAME, tempInt001);
            this.StartTotalFrame = tempInt001;
        }

        if (pCompound.contains("LOOPTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("LOOPTOTALFRAME");
            this.getEntityData().set(LOOP_TOTAL_FRAME, tempInt001);
            this.LoopTotalFrame = tempInt001;
        }

        if (pCompound.contains("ENDTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("ENDTOTALFRAME");
            this.getEntityData().set(END_TOTAL_FRAME, tempInt001);
            this.EndTotalFrame = tempInt001;
        }

        if (pCompound.contains("ISFRAMEDISTRIBUTEDEVENLY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISFRAMEDISTRIBUTEDEVENLY");
            this.getEntityData().set(IS_FRAME_DISTRIBUTED_EVENLY, tempBool);
            this.isFrameDistributedEvenly = tempBool;
        }

        //==================
        if (pCompound.contains("TEXTURENAME", 8)) {
            String tempString001 = pCompound.getString("TEXTURENAME");
            this.getEntityData().set(TEXTURE_NAME, tempString001);
            this.TextureName = tempString001;
        }

        if (pCompound.contains("ANIMATIONAME", 8)) {
            String tempString001 = pCompound.getString("ANIMATIONAME");
            this.getEntityData().set(ANIMATION_NAME, tempString001);
            this.AnimationName = tempString001;
        }

        //===================
        if (pCompound.contains("ISVISIBLETOALLPLAYERS", 1)) {
            boolean tempBool = pCompound.getBoolean("ISVISIBLETOALLPLAYERS");
            this.getEntityData().set(IS_VISIBLE_TO_ALL_PLAYERS, tempBool);
            this.IsVisibleToAllPlayers = tempBool;
        }

        //=================
        if (pCompound.contains("ISBOUNDTOENTITY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISBOUNDTOENTITY");
            this.getEntityData().set(IS_BOUND_TO_ENTITY, tempBool);
            this.IsBoundToEntity = tempBool;
        }

        //=================
        if (pCompound.contains("PARTICLENAME", 8)) {
            String tempString001 = pCompound.getString("PARTICLENAME");
            this.getEntityData().set(PARTICLE_NAME, tempString001);
            this.ParticleName = tempString001;
        }

        //=================
        if (pCompound.contains("CLIENTACTIONAME", 8)) {
            String tempString001 = pCompound.getString("CLIENTACTIONAME");
            this.getEntityData().set(CLIENT_ACTION_NAME, tempString001);
            this.ClientActionName = tempString001;
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
