package net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientmovementhelper;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.util.math.MatrixStack;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.moveEntityToXYZ;

public class ClientMovementHelperEntityRenderer extends GeoEntityRenderer<ClientMovementHelperEntity> {
    public ClientMovementHelperEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ClientMovementHelperEntityModel());
        this.shadowOpacity = 0.0f;
    }

    @Override
    public Identifier getTextureLocation(ClientMovementHelperEntity instance) {
        return DfoSwdGeneralGeoEntityResources.NULL_ENTITY_TEXTURE_RESOURCE;
    }

    @Override
    public boolean shouldRender(ClientMovementHelperEntity ClientMovementHelperEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public RenderLayer getRenderType(ClientMovementHelperEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EntityRenderState entityRenderState, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        ClientMovementHelperEntity pEntity = this.animatable;

        if (pEntity.hasTargetLocXYZ()) {
            float distPercent = (pEntity.getMaxLifetime() - pEntity.getLifetime() + this.partialTick) / pEntity.getMaxLifetime();
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


    @Override
    public Color getRenderColor(ClientMovementHelperEntity animatable, float partialTick, int packedLight) {
//        return Color.WHITE;
        return Color.ofRGBA(255, 255, 255, (int) (1.0f * animatable.getLifetime() / 12 * 240) + 15 );
    }
}
