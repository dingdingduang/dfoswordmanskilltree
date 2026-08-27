package net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientmovementhelper;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.moveEntityToXYZ;

public class ClientMovementHelperEntityRenderer extends GeoEntityRenderer<ClientMovementHelperEntity> {
    public ClientMovementHelperEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ClientMovementHelperEntityModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(ClientMovementHelperEntity instance) {
        return DfoSwdGeneralGeoEntityResources.NULL_ENTITY_TEXTURE_RESOURCE;
    }

    @Override
    public boolean shouldRender(ClientMovementHelperEntity ClientMovementHelperEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public RenderType getRenderType(ClientMovementHelperEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EntityRenderState entityRenderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
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
