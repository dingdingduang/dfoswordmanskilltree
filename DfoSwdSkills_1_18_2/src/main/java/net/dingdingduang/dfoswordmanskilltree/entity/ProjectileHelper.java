package net.dingdingduang.dfoswordmanskilltree.entity;

import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityByIntID;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;

public class ProjectileHelper extends Projectile {
    private static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(ProjectileHelper.class, EntityDataSerializers.INT);

    private MethodEntityAction ClientMethodEntityLoopAction = null;

    protected ProjectileHelper(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        //DoNothing()
    }

    @Override
    public void lerpTo(double pX, double pY, double pZ, float pYRot, float pXRot, int pLerpSteps, boolean pTeleport) {
        //DoNothing()
    }

    @Override
    public void lerpMotion(double x, double y, double z) {
        //DoNothing()
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(OWNER_ID, -1);
    }

    @Nullable
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
    public void setOwner(@Nullable Entity pOwner) {
        if (pOwner != null) {
            this.entityData.set(OWNER_ID, pOwner.getId());
        }
        else {
            this.entityData.set(OWNER_ID, -1);
        }

        super.setOwner(pOwner);
    }

    public MethodEntityAction getClientMethodEntityLoopAction() { return this.ClientMethodEntityLoopAction; }
    public void setClientMethodEntityLoopAction(MethodEntityAction clientMethodEntityLoopAction) { this.ClientMethodEntityLoopAction = clientMethodEntityLoopAction; }
}
