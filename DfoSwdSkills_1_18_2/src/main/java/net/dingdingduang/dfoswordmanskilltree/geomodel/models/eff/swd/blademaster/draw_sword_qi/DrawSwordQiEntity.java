package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.bus.DfoSwdLivingEntityRenderPreEvent;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.printInGameMsg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class DrawSwordQiEntity extends ProjectileHelper implements IAnimatable, IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Float> POS_HORIZONTAL_DEG = SynchedEntityData.<Float>defineId(DrawSwordQiEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> POS_VERTICAL_DEG = SynchedEntityData.<Float>defineId(DrawSwordQiEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(DrawSwordQiEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Integer> REMAINING_LIFETIME = SynchedEntityData.<Integer>defineId(DrawSwordQiEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(DrawSwordQiEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> IS_START_FRAME_FADING = SynchedEntityData.<Boolean>defineId(DrawSwordQiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_END_FRAME_FADING = SynchedEntityData.<Boolean>defineId(DrawSwordQiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> START_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(DrawSwordQiEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LOOP_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(DrawSwordQiEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> END_TOTAL_FRAME = SynchedEntityData.<Integer>defineId(DrawSwordQiEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_FRAME_DISTRIBUTED_EVENLY = SynchedEntityData.<Boolean>defineId(DrawSwordQiEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<String> ANIMATION_NAME = SynchedEntityData.<String>defineId(DrawSwordQiEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> TEXTURE_NAME = SynchedEntityData.<String>defineId(DrawSwordQiEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Float> GEO_ANIMATION_SPEED_MODIFIER = SynchedEntityData.<Float>defineId(DrawSwordQiEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> AOE_RANGE = SynchedEntityData.<Float>defineId(DrawSwordQiEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> MAX_AOE_SCALE_MULTIPLIER = SynchedEntityData.<Float>defineId(DrawSwordQiEntity.class, EntityDataSerializers.FLOAT);

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

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

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

//    private MethodCustomSlashEntityAction CustomMethodEntityAction = null;

    private boolean isSpeedModifierApplied = false;
    private GeoModelAnimationController<DrawSwordQiEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);
    private float GeoAnimationSpeedModifier = 1.0f;

    private float AoeRange = 5f;
    private float MaxAoeScaleMultiplier = 2f;

    //client var
    private float ClientRenderDist = 32f;

    public GeoModelAnimationController<DrawSwordQiEntity> getAnimationController() { return this.GeoAnimationController; }

    public DrawSwordQiEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
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

    public float getGeoAnimationSpeedModifier() { return this.GeoAnimationSpeedModifier; }
    public void setGeoAnimationSpeedModifier(float geoAnimationSpeedModifier) {
        this.GeoAnimationSpeedModifier = geoAnimationSpeedModifier;
        this.isSpeedModifierApplied = false;
    }

    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }

    public float getAoeRange() { return this.AoeRange; }
    public void setAoeRange(float aoeRange) { this.AoeRange = aoeRange; }

    public float getMaxAoeScaleMultiplier() { return this.MaxAoeScaleMultiplier; }
    public void setMaxAoeScaleMultiplier(float aoeScaleMultiplier) { this.MaxAoeScaleMultiplier = aoeScaleMultiplier; }

    public float getClientRenderDist() { return this.ClientRenderDist; }
    public void setClientRenderDist(float clientRenderDist) { this.ClientRenderDist = clientRenderDist; }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

            if (this.lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
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

            float MaxAoeScaleMultiplierPercent = 1f + (MaxAoeScaleMultiplier * (this.MaxLifetime - this.lifetime) / this.MaxLifetime);
            this.ClientRenderDist = 4 * AoeRange * MaxAoeScaleMultiplierPercent;
            this.EntityScale = AoeRange * 0.4f * MaxAoeScaleMultiplierPercent;
//            printInGameMsg("client scale: "+this.EntityScale+", extended range: "+(AoeRange * MaxAoeScaleMultiplierPercent));
        }
    }

    protected <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!this.isSpeedModifierApplied) {
            if (this.GeoAnimationSpeedModifier != 1.0f) {
                this.GeoAnimationController.setAnimationSpeed(this.GeoAnimationSpeedModifier);
            }
            this.isSpeedModifierApplied = false;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation(this.AnimationName, ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(this.GeoAnimationController);
    }

    @Override
    public AnimationFactory getFactory() { return this.factory; }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(POS_HORIZONTAL_DEG, 0.0f);
        this.entityData.define(POS_VERTICAL_DEG, 0.0f);

        this.entityData.define(ENTITY_SCALE, 1f);

        this.entityData.define(REMAINING_LIFETIME, 12);
        this.entityData.define(MAX_LIFETIME, 12);

        this.entityData.define(IS_START_FRAME_FADING, true);
        this.entityData.define(IS_END_FRAME_FADING, true);
        this.entityData.define(START_TOTAL_FRAME, 4);
        this.entityData.define(LOOP_TOTAL_FRAME, 1);
        this.entityData.define(END_TOTAL_FRAME, 7);
        this.entityData.define(IS_FRAME_DISTRIBUTED_EVENLY, false);

        this.entityData.define(TEXTURE_NAME, "textures/entity/slash/white/0_0");
        this.entityData.define(ANIMATION_NAME, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60);

        this.entityData.define(GEO_ANIMATION_SPEED_MODIFIER, 1.0f);

        this.entityData.define(AOE_RANGE, 5.0f);
        this.entityData.define(MAX_AOE_SCALE_MULTIPLIER, 2.0f);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("HORIZONTALDEG", getEntityHorizontalFacingDeg(this));
        pCompound.putFloat("VERTICALDEG", getEntityVerticalFacingDeg(this));

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

        //animation speed modifier
        pCompound.putFloat("GEOANIMATIONSPEEDMODIFIER", this.GeoAnimationSpeedModifier);

        //Aoe range
        pCompound.putFloat("AOERANGE", this.AoeRange);
        pCompound.putFloat("MAXAOESCALEMULTIPLIER", this.MaxAoeScaleMultiplier);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getEntityData().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
        }

        if (pCompound.contains("VERTICALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("VERTICALDEG");
            this.getEntityData().set(POS_VERTICAL_DEG, tempFloat001);
            setEntityVerticalFacingDeg(this, tempFloat001);
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

        //=========== Geo Anim
        if (pCompound.contains("GEOANIMATIONSPEEDMODIFIER", 99)) {
            float tempFloat001 = pCompound.getFloat("GEOANIMATIONSPEEDMODIFIER");
            this.getEntityData().set(GEO_ANIMATION_SPEED_MODIFIER, tempFloat001);
            this.GeoAnimationSpeedModifier = tempFloat001;
        }

        //=========== AOE RANGE
        if (pCompound.contains("AOERANGE", 99)) {
            float tempFloat001 = pCompound.getFloat("AOERANGE");
            this.getEntityData().set(AOE_RANGE, tempFloat001);
            this.AoeRange = tempFloat001;
        }

        //=========== AOE RANGE
        if (pCompound.contains("MAXAOESCALEMULTIPLIER", 99)) {
            float tempFloat001 = pCompound.getFloat("MAXAOESCALEMULTIPLIER");
            this.getEntityData().set(MAX_AOE_SCALE_MULTIPLIER, tempFloat001);
            this.MaxAoeScaleMultiplier = tempFloat001;
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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
