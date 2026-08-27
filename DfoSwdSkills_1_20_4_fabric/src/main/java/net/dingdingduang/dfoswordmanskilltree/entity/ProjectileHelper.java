package net.dingdingduang.dfoswordmanskilltree.entity;

import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityByIntID;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;

public class ProjectileHelper extends AbstractProjectileHelper {
    private static final TrackedData<Integer> OWNER_ID = DataTracker.registerData(ProjectileHelper.class, TrackedDataHandlerRegistry.INTEGER);

    private MethodEntityAction ClientMethodEntityLoopAction = null;

    //accessible method net/minecraft/entity/projectile/ProjectileEntity <init> (Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V
    protected ProjectileHelper(EntityType<? extends AbstractProjectileHelper> pEntityType, World pLevel) {
//    protected ProjectileHelper(EntityType<? extends ProjectileEntity> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick() {

    }

    @Override
    public void setVelocityClient(double x, double y, double z) {

    }

//    @Override
//    public void updateRotation() {
//
//    }

//    @Override
//    public boolean canHit(Entity entity) {
//        return false;
//    }
//
//    @Override
//    public void setVelocity(double x, double y, double z, float speed, float divergence) {
//
//    }
//
//    @Override
//    public void setVelocity(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
//
//    }
//
//    @Override
//    protected void onCollision(HitResult hitResult) {
//
//    }
//
//    @Override
//    protected void onEntityHit(EntityHitResult entityHitResult) {
//    }
//
//    @Override
//    protected void onBlockHit(BlockHitResult blockHitResult) {
//
//    }

    @Override
    public void updateTrackedPositionAndAngles(double pX, double pY, double pZ, float pYRot, float pXRot, int pLerpSteps) {
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

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        Entity entity = this.getOwner();
        DfoSwdNetworkingFetchMsgMethods.FetchEntityExtraSpawnDataFromServer(this);
        return new EntitySpawnS2CPacket(this, entity == null ? 0 : entity.getId());
    }

    //ignored
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        Entity entity = this.getWorld().getEntityById(packet.getEntityData());
        if (entity != null) {
            this.setOwner(entity);
        }
    }

    public MethodEntityAction getClientMethodEntityLoopAction() { return this.ClientMethodEntityLoopAction; }
    public void setClientMethodEntityLoopAction(MethodEntityAction clientMethodEntityLoopAction) { this.ClientMethodEntityLoopAction = clientMethodEntityLoopAction; }
}
