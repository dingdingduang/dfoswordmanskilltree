package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistryName;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleMethods;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.dingdingduang.somebasicskills.globalmethods.GeneralMethods;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.dingdingduang.dfoswordmanskilltree.entity.EntityExtraSpawnData;

import modified.org.joml.Vector3f;
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

public class ChannelingIconEntity extends ProjectileHelper implements IAnimatable, EntityExtraSpawnData {
    //color
    private static final TrackedData<Float> ENTITY_SCALE = DataTracker.<Float>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Integer> REMAINING_LIFETIME = DataTracker.<Integer>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MAX_LIFETIME = DataTracker.<Integer>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<String> TEXTURE_NAME = DataTracker.<String>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.STRING);

    private static final TrackedData<Boolean> HAS_TARGET_LOC_XYZ = DataTracker.<Boolean>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> START_POS_X = DataTracker.<Float>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_POS_Y = DataTracker.<Float>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_POS_Z = DataTracker.<Float>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_X = DataTracker.<Float>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_Y = DataTracker.<Float>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> TARGET_POS_Z = DataTracker.<Float>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Boolean> IS_BOUND_TO_ENTITY = DataTracker.<Boolean>registerData(ChannelingIconEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private int lifetime = 12;
    private int MaxLifetime = 12;

    private String TextureName = GeoModelRegistryName.ENTITY_CHANNELING_BAR_TEXTURE;

    private float EntityScale = 1f;

    private boolean HasTargetLocXYZ = false;
    private float StartPosX = 0f;
    private float StartPosY = 0f;
    private float StartPosZ = 0f;
    private float TargetPosX = 0f;
    private float TargetPosY = 0f;
    private float TargetPosZ = 0f;

    private boolean IsBoundToEntity = false;

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

    private int clientOrderSequence = 0;

    private GeoModelAnimationController<ChannelingIconEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<ChannelingIconEntity> getAnimationController() { return this.GeoAnimationController; }

    public ChannelingIconEntity(EntityType<? extends ProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.noClip = true;
        this.intersectionChecked = false;
    }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetimeTicks) { this.lifetime = lifetimeTicks; }
    public int getMaxLifetime() { return this.MaxLifetime; }
    public void setMaxLifetime(int maxLifetime) { this.MaxLifetime = maxLifetime; }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public String getTextureName() { return this.TextureName; }
    public void setTextureName(String textureName) { this.TextureName = textureName; }

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
    public void setTargetLoc(float start_x1, float start_y1, float start_z1, float target_x1, float target_y1, float target_z1, boolean shouldMove) {
        this.HasTargetLocXYZ = shouldMove;
        setStartPosLoc(start_x1, start_y1, start_z1);
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

    public boolean isBoundToEntity() { return this.IsBoundToEntity; }
    public void setIsBoundToEntity(boolean boundToEntity) { this.IsBoundToEntity = boundToEntity; }

    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }

    public int getClientOrderSequence() { return this.clientOrderSequence; }
    public void setClientOrderSequence(int clientOrderSequence) { this.clientOrderSequence = clientOrderSequence; }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

            if (!this.HasTargetLocXYZ && this.IsBoundToEntity) {
                Entity owner = this.getOwner();
                if (owner != null) {
                    moveEntityToPos(this, getEntityPos(owner).add(this.TargetPosX, this.TargetPosY, this.TargetPosZ));
                }
            }

            if (this.lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
            }

            if (EntityServerAction != null) {
                int currentTickTime = (this.MaxLifetime - this.lifetime) / this.EntityServerActionTickPeriod;
                if (this.PreviousEntityActionTriggeredTick != currentTickTime) {
                    this.PreviousEntityActionTriggeredTick = currentTickTime;
                    this.EntityServerAction.executeAction(this);
                }
            }
        }
        else {
//            Entity owner = this.getOwner();
//            if (owner != null) {
//                if (this.lifetime == 2 || this.lifetime == 1) {
//                    GlobalClientMaps.getChannelingIconOrder().remove(owner);
//                }
//                else {
//                    GlobalClientMaps.getChannelingIconOrder().put(owner, 0);
//                }
//            }
            if (this.lifetime == this.MaxLifetime) {
                Vector3f entityPos = getEntityPos(this);
                ParticleMethods.GenerateHitParticle(entityPos.x(), entityPos.y() + 0.5f, entityPos.z(), ExtraMathMethods.randomInt(5) + 10);
            }
            if (this.lifetime > 1) { this.lifetime--; }
        }
    }

    protected <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (GeneralMethods.isCamMirror()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(GeoAnimationAndTextureName.ICON_ENTITY_ANIMATION_Y_180DEG, ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(GeoAnimationAndTextureName.ICON_ENTITY_ANIMATION_NO_ACTION, ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
        }
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

        this.dataTracker.startTracking(ENTITY_SCALE, 1f);

        this.dataTracker.startTracking(REMAINING_LIFETIME, 12);
        this.dataTracker.startTracking(MAX_LIFETIME, 12);

        this.dataTracker.startTracking(TEXTURE_NAME, "textures/entity/slash/white/0_0");

        this.dataTracker.startTracking(START_POS_X, getEntityX(this));
        this.dataTracker.startTracking(START_POS_Y, getEntityY(this));
        this.dataTracker.startTracking(START_POS_Z, getEntityZ(this));
        this.dataTracker.startTracking(HAS_TARGET_LOC_XYZ, false);
        this.dataTracker.startTracking(TARGET_POS_X, getEntityX(this));
        this.dataTracker.startTracking(TARGET_POS_Y, getEntityY(this));
        this.dataTracker.startTracking(TARGET_POS_Z, getEntityZ(this));

        this.dataTracker.startTracking(IS_BOUND_TO_ENTITY, false);
    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        pCompound.putInt("REMAININGLIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifetime);

        pCompound.putString("TEXTURENAME", this.TextureName);

        //origin pos vec
        pCompound.putFloat("STARTPOSX", this.StartPosX);
        pCompound.putFloat("STARTPOSY", this.StartPosY);
        pCompound.putFloat("STARTPOSZ", this.StartPosZ);
        //target pos
        pCompound.putBoolean("HASTARGETLOCXYZ", this.HasTargetLocXYZ);
        pCompound.putFloat("TARGETPOSX", this.TargetPosX);
        pCompound.putFloat("TARGETPOSY", this.TargetPosY);
        pCompound.putFloat("TARGETPOSZ", this.TargetPosZ);

        //bound entity
        pCompound.putBoolean("ISBOUNDTOENTITY", this.IsBoundToEntity);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);
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
        if (pCompound.contains("TEXTURENAME", 8)) {
            String tempString001 = pCompound.getString("TEXTURENAME");
            this.getDataTracker().set(TEXTURE_NAME, tempString001);
            this.TextureName = tempString001;
        }

        //===================
        if (pCompound.contains("STARTPOSX", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSX");
            this.getDataTracker().set(START_POS_X, tempFloat001);
            this.StartPosX = tempFloat001;
        }

        if (pCompound.contains("STARTPOSY", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSY");
            this.getDataTracker().set(START_POS_Y, tempFloat001);
            this.StartPosY = tempFloat001;
        }

        if (pCompound.contains("STARTPOSZ", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSZ");
            this.getDataTracker().set(START_POS_Z, tempFloat001);
            this.StartPosZ = tempFloat001;
        }

        if (pCompound.contains("HASTARGETLOCXYZ", 1)) {
            boolean tempBool = pCompound.getBoolean("HASTARGETLOCXYZ");
            this.getDataTracker().set(HAS_TARGET_LOC_XYZ, tempBool);
            this.HasTargetLocXYZ = tempBool;
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

        //=================
        if (pCompound.contains("ISBOUNDTOENTITY", 1)) {
            boolean tempBool = pCompound.getBoolean("ISBOUNDTOENTITY");
            this.getDataTracker().set(IS_BOUND_TO_ENTITY, tempBool);
            this.IsBoundToEntity = tempBool;
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
