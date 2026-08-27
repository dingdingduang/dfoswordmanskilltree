package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
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
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class HitEffEntity extends ProjectileHelper implements IAnimatable, IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Float> ROTATION_DEGREE_Z = SynchedEntityData.<Float>defineId(HitEffEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.<Float>defineId(HitEffEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Integer> REMAINING_LIFETIME = SynchedEntityData.<Integer>defineId(HitEffEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(HitEffEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TOTAL_FRAME = SynchedEntityData.<Integer>defineId(HitEffEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> TARGET_POS_X = SynchedEntityData.<Float>defineId(HitEffEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Y = SynchedEntityData.<Float>defineId(HitEffEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Z = SynchedEntityData.<Float>defineId(HitEffEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<String> TEXTURE_NAME = SynchedEntityData.<String>defineId(HitEffEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Boolean> IS_BOUND_TO_ENTITY = SynchedEntityData.<Boolean>defineId(HitEffEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<String> PARTICLE_NAME = SynchedEntityData.<String>defineId(HitEffEntity.class, EntityDataSerializers.STRING);

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private float RotationDegreeZ = 0f;

    private int lifetime = 4;
    private int MaxLifetime = 4;
    private int TotalFrame = 4;
    private int ClientLoopFrameCounter = 0;
    private int[] ClientTotalFrameIndexHelper = null;

    private String TextureName = "textures/entity/skilleff/common/hit_damage/";

    private float EntityScale = 1f;

    private float TargetPosX = 0f;
    private float TargetPosY = 0f;
    private float TargetPosZ = 0f;

    private boolean IsBoundToEntity = false;

    private String ParticleName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private GeoModelAnimationController<HitEffEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<HitEffEntity> getAnimationController() { return this.GeoAnimationController; }

    public HitEffEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetimeTicks) { this.lifetime = lifetimeTicks; }
    public int getMaxLifetime() { return this.MaxLifetime; }
    public void setMaxLifetime(int maxLifetime) { this.MaxLifetime = maxLifetime; }

    public int getTotalFrame() { return this.TotalFrame; }
    public void setTotalFrame(int totalFrame) { this.TotalFrame = totalFrame; }

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

    public boolean isBoundToEntity() { return this.IsBoundToEntity; }
    public void setIsBoundToEntity(boolean boundToEntity) { this.IsBoundToEntity = boundToEntity; }

    public float getEntityScale() { return this.EntityScale; }
    public void setEntityScale(float EntityScale) { this.EntityScale = EntityScale; }

    public String getTextureName() { return this.TextureName; }
    public void setTextureName(String textureName) { this.TextureName = textureName; }


    //client
    public int getClientLoopFrameCounter() { return this.ClientLoopFrameCounter; }
    public void setClientLoopFrameCounter(int clientLoopFrameCounter) { this.ClientLoopFrameCounter = clientLoopFrameCounter; }
    public int[] getClientTotalFrameIndexHelper() { return this.ClientTotalFrameIndexHelper; }
    public void setClientTotalFrameIndexHelper(int[] clientTotalFrameIndexHelper) { this.ClientTotalFrameIndexHelper = clientTotalFrameIndexHelper; }

    public String getParticleName() { return this.ParticleName; }
    public void setParticleName(String particleName) { this.ParticleName = particleName; }

    public float getRotationDegreeZ() { return this.RotationDegreeZ; }
    public void setRotationDegreeZ(float rotationDegreeZ) { this.RotationDegreeZ = rotationDegreeZ; }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

            if (this.IsBoundToEntity && this.getOwner() != null) {
                moveEntityToPos(this, getEntityPos(this.getOwner()).add(this.TargetPosX, this.TargetPosY, this.TargetPosZ));
            }

            if (this.lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
            }
        }
        else {
            if (this.lifetime > 0) {
                this.lifetime--;
            }

            MethodEntityAction ParticleMethod = ParticleRegistry.getParticleMethod(ParticleName);
            if (ParticleMethod != null) {
                ParticleMethod.executeAction(this);
            }
        }
    }

    protected <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
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
        this.entityData.define(ROTATION_DEGREE_Z, 0f);

        this.entityData.define(ENTITY_SCALE, 1f);

        this.entityData.define(REMAINING_LIFETIME, 4);
        this.entityData.define(MAX_LIFETIME, 4);
        this.entityData.define(TOTAL_FRAME, 4);

        this.entityData.define(TEXTURE_NAME, "textures/entity/skilleff/common/hit_damage/");

        this.entityData.define(TARGET_POS_X, getEntityX(this));
        this.entityData.define(TARGET_POS_Y, getEntityY(this));
        this.entityData.define(TARGET_POS_Z, getEntityZ(this));

        this.entityData.define(IS_BOUND_TO_ENTITY, false);

        this.entityData.define(PARTICLE_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("ROTATIONDEGREEZ", this.RotationDegreeZ);

        pCompound.putFloat("ENTITYSCALE", this.EntityScale);

        pCompound.putInt("REMAININGLIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifetime);
        pCompound.putInt("TOTALFRAME", this.TotalFrame);

        pCompound.putString("TEXTURENAME", this.TextureName);

        pCompound.putFloat("TARGETPOSX", this.TargetPosX);
        pCompound.putFloat("TARGETPOSY", this.TargetPosY);
        pCompound.putFloat("TARGETPOSZ", this.TargetPosZ);

        //bound entity
        pCompound.putBoolean("ISBOUNDTOENTITY", this.IsBoundToEntity);

        //particle method name
        pCompound.putString("PARTICLENAME", this.ParticleName);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("ROTATIONDEGREEZ", 99)) {
            float tempFloat001 = pCompound.getFloat("ROTATIONDEGREEZ");
            this.getEntityData().set(ROTATION_DEGREE_Z, tempFloat001);
            this.RotationDegreeZ = tempFloat001;
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

        if (pCompound.contains("TOTALFRAME", 99)) {
            int tempInt001 = pCompound.getInt("TOTALFRAME");
            this.getEntityData().set(TOTAL_FRAME, tempInt001);
            this.TotalFrame = tempInt001;
        }

        //==================
        if (pCompound.contains("TEXTURENAME", 8)) {
            String tempString001 = pCompound.getString("TEXTURENAME");
            this.getEntityData().set(TEXTURE_NAME, tempString001);
            this.TextureName = tempString001;
        }

        //==================
        if (pCompound.contains("TARGETPOSX", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETPOSX");
            this.getEntityData().set(TARGET_POS_X, tempFloat001);
            this.TargetPosX = tempFloat001;
        }

        if (pCompound.contains("TARGETPOSY", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETPOSY");
            this.getEntityData().set(TARGET_POS_Y, tempFloat001);
            this.TargetPosY = tempFloat001;
        }

        if (pCompound.contains("TARGETPOSZ", 99)) {
            float tempFloat001 = pCompound.getFloat("TARGETPOSZ");
            this.getEntityData().set(TARGET_POS_Z, tempFloat001);
            this.TargetPosZ = tempFloat001;
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
