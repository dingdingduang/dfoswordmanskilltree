package net.dingdingduang.dfoswordmanskilltree.entity;

import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityByIntID;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;

public class ProjectileHelper extends AbstractProjectileHelper {
    private static final TrackedData<Integer> OWNER_ID = DataTracker.registerData(ProjectileHelper.class, TrackedDataHandlerRegistry.INTEGER);

    private MethodEntityAction ClientMethodEntityLoopAction = null;

    //accessible method net/minecraft/entity/projectile/ProjectileEntity <init> (Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V
    protected ProjectileHelper(EntityType<? extends AbstractProjectileHelper> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void updateTrackedPositionAndAngles(double pX, double pY, double pZ, float pYRot, float pXRot, int pLerpSteps, boolean pTeleport) {
        //DoNothing()
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(OWNER_ID, -1);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        int id = this.dataTracker.get(OWNER_ID);

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
            this.dataTracker.set(OWNER_ID, pOwner.getId());
        }
        else {
            this.dataTracker.set(OWNER_ID, -1);
        }

        super.setOwner(pOwner);
    }

    public MethodEntityAction getClientMethodEntityLoopAction() { return this.ClientMethodEntityLoopAction; }
    public void setClientMethodEntityLoopAction(MethodEntityAction clientMethodEntityLoopAction) { this.ClientMethodEntityLoopAction = clientMethodEntityLoopAction; }
}
