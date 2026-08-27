package net.dingdingduang.dfoswordmanskilltree.entity.clientmovementhelper;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class ClientMovementHelper extends ProjectileHelper implements IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Integer> REMAINING_LIFETIME = SynchedEntityData.<Integer>defineId(ClientMovementHelper.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LIFETIME = SynchedEntityData.<Integer>defineId(ClientMovementHelper.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> HAS_TARGET_LOC_XYZ = SynchedEntityData.<Boolean>defineId(ClientMovementHelper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> START_POS_X = SynchedEntityData.<Float>defineId(ClientMovementHelper.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_POS_Y = SynchedEntityData.<Float>defineId(ClientMovementHelper.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_POS_Z = SynchedEntityData.<Float>defineId(ClientMovementHelper.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_X = SynchedEntityData.<Float>defineId(ClientMovementHelper.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Y = SynchedEntityData.<Float>defineId(ClientMovementHelper.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Z = SynchedEntityData.<Float>defineId(ClientMovementHelper.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<String> CLIENT_ACTION_NAME = SynchedEntityData.<String>defineId(ClientMovementHelper.class, EntityDataSerializers.STRING);

    private int lifetime = 10;
    private int MaxLifetime = 10;

    private boolean HasTargetLocXYZ = false;
    private float StartPosX = 0f;
    private float StartPosY = 0f;
    private float StartPosZ = 0f;
    private float TargetPosX = 0f;
    private float TargetPosY = 0f;
    private float TargetPosZ = 0f;

    private String ClientActionName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;

    private int PreviousEntityActionTriggeredTick = -1;
    private int EntityServerActionTickPeriod = 2;
    private MethodEntityAction EntityServerAction = null;

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

    public String getClientActionName() { return this.ClientActionName; }
    public void setClientActionName(String clientActionName) { this.ClientActionName = clientActionName; }

    public MethodEntityAction getEntityServerAction() { return this.EntityServerAction; }
    public void setEntityServerAction(MethodEntityAction entityServerAction, int tickPeriod) {
        this.EntityServerAction = entityServerAction;
        this.EntityServerActionTickPeriod = Math.max(1, tickPeriod);
    }

    public ClientMovementHelper(EntityType<? extends ProjectileHelper> entityType, Level level) {
        super(entityType, level);
//        this.setNoGravity(true);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() { return NetworkHooks.getEntitySpawningPacket(this); }

    @Override
    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            this.lifetime--;

            if (this.HasTargetLocXYZ) {
                float distPercent = 1f * (this.MaxLifetime - this.lifetime) / this.MaxLifetime;
                float newX = this.StartPosX + (this.TargetPosX - this.StartPosX) * distPercent;
                float newY = this.StartPosY + (this.TargetPosY - this.StartPosY) * distPercent;
                float newZ = this.StartPosZ + (this.TargetPosZ - this.StartPosZ) * distPercent;

                if (isBlockPosAirOnly(this, newX, newY, newZ)) {
                    moveEntityToXYZ(this, newX, newY, newZ);
                    Entity owner = this.getOwner();
                    if (owner != null) {
                        moveEntityToXYZ(owner, newX, newY, newZ);
                    }
                }
                else {
                    this.HasTargetLocXYZ = false;
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
            if (this.lifetime > 0) {
                this.lifetime--;
            }

            MethodEntityAction ClientActionMethod = GeoModelClientMethodsRegistry.getGeoModelClientActionMethod(this.ClientActionName);
            if (ClientActionMethod != null) {
                ClientActionMethod.executeAction(this);
            }
        }
    }

    public int getLifetime() { return this.lifetime; }
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    public int getMaxLifetime() { return this.MaxLifetime; }
    public void setMaxLifetime(int maxLifetime) { this.MaxLifetime = maxLifetime; }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(REMAINING_LIFETIME, 12);
        this.entityData.define(MAX_LIFETIME, 12);

        this.entityData.define(START_POS_X, getEntityX(this));
        this.entityData.define(START_POS_Y, getEntityY(this));
        this.entityData.define(START_POS_Z, getEntityZ(this));
        this.entityData.define(HAS_TARGET_LOC_XYZ, false);
        this.entityData.define(TARGET_POS_X, getEntityX(this));
        this.entityData.define(TARGET_POS_Y, getEntityY(this));
        this.entityData.define(TARGET_POS_Z, getEntityZ(this));

        this.entityData.define(CLIENT_ACTION_NAME, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putInt("REMAININGLIFETIME", this.lifetime);
        pCompound.putInt("MAXLIFETIME", this.MaxLifetime);

        //origin pos vec
        pCompound.putFloat("STARTPOSX", this.StartPosX);
        pCompound.putFloat("STARTPOSY", this.StartPosY);
        pCompound.putFloat("STARTPOSZ", this.StartPosZ);
        //target pos
        pCompound.putBoolean("HASTARGETLOCXYZ", this.HasTargetLocXYZ);
        pCompound.putFloat("TARGETPOSX", this.TargetPosX);
        pCompound.putFloat("TARGETPOSY", this.TargetPosY);
        pCompound.putFloat("TARGETPOSZ", this.TargetPosZ);

        //client action method
        pCompound.putString("CLIENTACTIONAME", this.ClientActionName);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

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

        //===================
        if (pCompound.contains("STARTPOSX", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSX");
            this.getEntityData().set(START_POS_X, tempFloat001);
            this.StartPosX = tempFloat001;
        }

        if (pCompound.contains("STARTPOSY", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSY");
            this.getEntityData().set(START_POS_Y, tempFloat001);
            this.StartPosY = tempFloat001;
        }

        if (pCompound.contains("STARTPOSZ", 99)) {
            float tempFloat001 = pCompound.getFloat("STARTPOSZ");
            this.getEntityData().set(START_POS_Z, tempFloat001);
            this.StartPosZ = tempFloat001;
        }

        if (pCompound.contains("HASTARGETLOCXYZ", 1)) {
            boolean tempBool = pCompound.getBoolean("HASTARGETLOCXYZ");
            this.getEntityData().set(HAS_TARGET_LOC_XYZ, tempBool);
            this.HasTargetLocXYZ = tempBool;
        }

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
        if (pCompound.contains("CLIENTACTIONAME", 8)) {
            String tempString001 = pCompound.getString("CLIENTACTIONAME");
            this.getEntityData().set(CLIENT_ACTION_NAME, tempString001);
            this.ClientActionName = tempString001;
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf friendlyByteBuf) {
        CompoundTag tag = new CompoundTag();
        this.addAdditionalSaveData(tag);
        friendlyByteBuf.writeNbt(tag);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf friendlyByteBuf) {
        CompoundTag tag = friendlyByteBuf.readNbt();
        this.readAdditionalSaveData(tag);
    }
}
