package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi;

import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
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

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class DrawSwordQiEntity extends ProjectileHelper implements GeoEntity, EntityExtraSpawnData {
    private static final TrackedData<Float> POS_HORIZONTAL_DEG = DataTracker.<Float>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> POS_VERTICAL_DEG = DataTracker.<Float>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Float> ENTITY_SCALE = DataTracker.<Float>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Integer> REMAINING_LIFETIME = DataTracker.<Integer>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MAX_LIFETIME = DataTracker.<Integer>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Boolean> IS_START_FRAME_FADING = DataTracker.<Boolean>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_END_FRAME_FADING = DataTracker.<Boolean>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> START_TOTAL_FRAME = DataTracker.<Integer>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> LOOP_TOTAL_FRAME = DataTracker.<Integer>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> END_TOTAL_FRAME = DataTracker.<Integer>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> IS_FRAME_DISTRIBUTED_EVENLY = DataTracker.<Boolean>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<String> ANIMATION_NAME = DataTracker.<String>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.<String>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.STRING);

    private static final TrackedData<Float> GEO_ANIMATION_SPEED_MODIFIER = DataTracker.<Float>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Float> AOE_RANGE = DataTracker.<Float>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> MAX_AOE_SCALE_MULTIPLIER = DataTracker.<Float>registerData(DrawSwordQiEntity.class, TrackedDataHandlerRegistry.FLOAT);

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

    public DrawSwordQiEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.noClip = true;
        this.intersectionChecked = false;
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

    protected <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (!this.isSpeedModifierApplied) {
            if (this.GeoAnimationSpeedModifier != 1.0f) {
                this.GeoAnimationController.setAnimationSpeed(this.GeoAnimationSpeedModifier);
            }
            this.isSpeedModifierApplied = false;
        }
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
    protected void initDataTracker(DataTracker.Builder syncBuilder) {
        super.initDataTracker(syncBuilder);
        syncBuilder.add(POS_HORIZONTAL_DEG, 0.0f);
        syncBuilder.add(POS_VERTICAL_DEG, 0.0f);

        syncBuilder.add(ENTITY_SCALE, 1f);

        syncBuilder.add(REMAINING_LIFETIME, 12);
        syncBuilder.add(MAX_LIFETIME, 12);

        syncBuilder.add(IS_START_FRAME_FADING, true);
        syncBuilder.add(IS_END_FRAME_FADING, true);
        syncBuilder.add(START_TOTAL_FRAME, 4);
        syncBuilder.add(LOOP_TOTAL_FRAME, 1);
        syncBuilder.add(END_TOTAL_FRAME, 7);
        syncBuilder.add(IS_FRAME_DISTRIBUTED_EVENLY, false);

        syncBuilder.add(TEXTURE_NAME, "textures/entity/slash/white/0_0");
        syncBuilder.add(ANIMATION_NAME, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG30_2_POS60);

        syncBuilder.add(GEO_ANIMATION_SPEED_MODIFIER, 1.0f);

        syncBuilder.add(AOE_RANGE, 5.0f);
        syncBuilder.add(MAX_AOE_SCALE_MULTIPLIER, 2.0f);
    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
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
    protected void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);

        if (pCompound.contains("HORIZONTALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("HORIZONTALDEG");
            this.getDataTracker().set(POS_HORIZONTAL_DEG, tempFloat001);
            setEntityHorizontalFacingDeg(this, tempFloat001);
        }

        if (pCompound.contains("VERTICALDEG", 99)) {
            float tempFloat001 = pCompound.getFloat("VERTICALDEG");
            this.getDataTracker().set(POS_VERTICAL_DEG, tempFloat001);
            setEntityVerticalFacingDeg(this, tempFloat001);
        }

        //============
        if (pCompound.contains("ENTITYSCALE", 99)) {
            float tempFloat001 = pCompound.getFloat("ENTITYSCALE");
            this.getDataTracker().set(ENTITY_SCALE, tempFloat001);
            this.EntityScale = tempFloat001;
        }

        //===========
        if (pCompound.contains("REMAININGLIFETIME", 99)) {
            int tempInt001 = pCompound.getInt("REMAININGLIFETIME");
            this.getDataTracker().set(REMAINING_LIFETIME, tempInt001);
            this.lifetime = tempInt001;
        }

        if (pCompound.contains("MAXLIFETIME", 99)) {
            int tempInt001 = pCompound.getInt("MAXLIFETIME");
            this.getDataTracker().set(MAX_LIFETIME, tempInt001);
            this.MaxLifetime = tempInt001;
        }

        //==================
        if (pCompound.contains("ISSTARTFRAMEFADING", 1)) {
            boolean tempBool = pCompound.getBoolean("ISSTARTFRAMEFADING");
            this.getDataTracker().set(IS_START_FRAME_FADING, tempBool);
            this.IsStartFrameFading = tempBool;
        }

        if (pCompound.contains("ISENDFRAMEFADING", 1)) {
            boolean tempBool = pCompound.getBoolean("ISENDFRAMEFADING");
            this.getDataTracker().set(IS_END_FRAME_FADING, tempBool);
            this.IsEndFrameFading = tempBool;
        }

        if (pCompound.contains("STARTTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("STARTTOTALFRAME");
            this.getDataTracker().set(START_TOTAL_FRAME, tempInt001);
            this.StartTotalFrame = tempInt001;
        }

        if (pCompound.contains("LOOPTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("LOOPTOTALFRAME");
            this.getDataTracker().set(LOOP_TOTAL_FRAME, tempInt001);
            this.LoopTotalFrame = tempInt001;
        }

        if (pCompound.contains("ENDTOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("ENDTOTALFRAME");
            this.getDataTracker().set(END_TOTAL_FRAME, tempInt001);
            this.EndTotalFrame = tempInt001;
        }

        if (pCompound.contains("ISFRAMEDISTRIBUTEDEVENLY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISFRAMEDISTRIBUTEDEVENLY");
            this.getDataTracker().set(IS_FRAME_DISTRIBUTED_EVENLY, tempBool);
            this.isFrameDistributedEvenly = tempBool;
        }

        //==================
        if (pCompound.contains("TEXTURENAME", 8)) {
            String tempString001 = pCompound.getString("TEXTURENAME");
            this.getDataTracker().set(TEXTURE_NAME, tempString001);
            this.TextureName = tempString001;
        }

        if (pCompound.contains("ANIMATIONAME", 8)) {
            String tempString001 = pCompound.getString("ANIMATIONAME");
            this.getDataTracker().set(ANIMATION_NAME, tempString001);
            this.AnimationName = tempString001;
        }

        //=========== Geo Anim
        if (pCompound.contains("GEOANIMATIONSPEEDMODIFIER", 99)) {
            float tempFloat001 = pCompound.getFloat("GEOANIMATIONSPEEDMODIFIER");
            this.getDataTracker().set(GEO_ANIMATION_SPEED_MODIFIER, tempFloat001);
            this.GeoAnimationSpeedModifier = tempFloat001;
        }

        //=========== AOE RANGE
        if (pCompound.contains("AOERANGE", 99)) {
            float tempFloat001 = pCompound.getFloat("AOERANGE");
            this.getDataTracker().set(AOE_RANGE, tempFloat001);
            this.AoeRange = tempFloat001;
        }

        //=========== AOE RANGE
        if (pCompound.contains("MAXAOESCALEMULTIPLIER", 99)) {
            float tempFloat001 = pCompound.getFloat("MAXAOESCALEMULTIPLIER");
            this.getDataTracker().set(MAX_AOE_SCALE_MULTIPLIER, tempFloat001);
            this.MaxAoeScaleMultiplier = tempFloat001;
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
