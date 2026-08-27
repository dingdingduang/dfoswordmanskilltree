package net.dingdingduang.dfoswordmanskilltree.entity.clientmovementhelper;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dingdingduang.dfoswordmanskilltree.entity.renderhelper.NullRenderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.isBlockPosAirOnly;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.moveEntityToXYZ;

public class ClientMovementHelperRenderer<T extends ClientMovementHelper> extends NullRenderer<T> {
    public ClientMovementHelperRenderer(EntityRendererProvider.Context entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public boolean shouldRender(T pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        //client move
        if (pEntity.hasTargetLocXYZ()) {
            float distPercent = (pEntity.getMaxLifetime() - pEntity.getLifetime() + pPartialTick) / pEntity.getMaxLifetime();
            float newX = pEntity.getStartPosX() + (pEntity.getTargetPosX() - pEntity.getStartPosX()) * distPercent;
            float newY = pEntity.getStartPosY() + (pEntity.getTargetPosY() - pEntity.getStartPosY()) * distPercent;
            float newZ = pEntity.getStartPosZ() + (pEntity.getTargetPosZ() - pEntity.getStartPosZ()) * distPercent;

            if (isBlockPosAirOnly(pEntity, newX, newY, newZ)) {
                moveEntityToXYZ(pEntity, newX, newY, newZ);
                Entity owner = pEntity.getOwner();
                if (owner != null) {
                    moveEntityToXYZ(owner, newX, newY, newZ);
                }
            }
            else {
                pEntity.setHasTargetLocXYZBoolean(false);
            }
        }
    }
}
