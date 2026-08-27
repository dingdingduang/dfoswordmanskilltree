package net.dingdingduang.dfoswordmanskilltree.entity;

import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityByIntID;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;

public class ProjectileHelper extends AbstractMCProjectileEntity {
    private static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(ProjectileHelper.class, EntityDataSerializers.INT);

    private MethodEntityAction ClientMethodEntityLoopAction = null;

    protected ProjectileHelper(EntityType<? extends AbstractMCProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(OWNER_ID, -1);
    }

    @Override
    public Entity getOwner() {
        int id = this.entityData.get(OWNER_ID);

        if (0 <= id) {
            Entity tempEntity = getEntityByIntID(getEntityLevel(this), id);
            if (super.getOwner() != tempEntity)
                this.setOwner(tempEntity);
        }
        else {
            this.setOwner(null);
        }

        return super.getOwner();
    }

    @Override
    public void setOwner(Entity pOwner) {
        if (pOwner != null) {
            this.entityData.set(OWNER_ID, pOwner.getId());
        }
        else {
            this.entityData.set(OWNER_ID, -1);
        }

        super.setOwner(pOwner);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public MethodEntityAction getClientMethodEntityLoopAction() { return this.ClientMethodEntityLoopAction; }
    public void setClientMethodEntityLoopAction(MethodEntityAction clientMethodEntityLoopAction) { this.ClientMethodEntityLoopAction = clientMethodEntityLoopAction; }
}
