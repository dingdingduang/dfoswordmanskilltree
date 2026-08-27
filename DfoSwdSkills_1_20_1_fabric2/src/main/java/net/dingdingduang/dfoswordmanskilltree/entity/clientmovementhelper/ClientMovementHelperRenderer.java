package net.dingdingduang.dfoswordmanskilltree.entity.clientmovementhelper;

import net.dingdingduang.dfoswordmanskilltree.entity.renderhelper.NullRenderer;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.isBlockPosAirOnly;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.moveEntityToXYZ;

public class ClientMovementHelperRenderer<T extends ClientMovementHelper> extends NullRenderer<T> {
    public ClientMovementHelperRenderer(EntityRendererFactory.Context entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public boolean shouldRender(T pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTick, MatrixStack pPoseStack, VertexConsumerProvider pBuffer, int pPackedLight) {
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
