package net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientparticlehelper;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.moveEntityToPos;

public class ClientParticleHelperEntityRenderer extends GeoEntityRenderer<ClientParticleHelperEntity> {
    public ClientParticleHelperEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ClientParticleHelperEntityModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(ClientParticleHelperEntity instance) {
        return DfoSwdGeneralGeoEntityResources.NULL_ENTITY_TEXTURE_RESOURCE;
    }

    @Override
    public boolean shouldRender(ClientParticleHelperEntity ClientMovementHelperEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public RenderType getRenderType(ClientParticleHelperEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(ClientParticleHelperEntity pEntity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = pEntity;

        //client move
        if (pEntity.isBoundToEntity() && pEntity.getOwner() != null) {
            Vector3f lerpVec = getEntityClientPosWithPartialTicks(pEntity.getOwner(), partialTick).add(pEntity.getTargetPosX(), pEntity.getTargetPosY(), pEntity.getTargetPosZ());
            moveEntityToPos(pEntity, lerpVec);
        }
        else if (pEntity.hasTargetLocXYZ()) {
            float distPercent = (pEntity.getMaxLifetime() - pEntity.getLifetime() + partialTick) / pEntity.getMaxLifetime();
            float newX = pEntity.getStartPosX() + (pEntity.getTargetPosX() - pEntity.getStartPosX()) * distPercent;
            float newY = pEntity.getStartPosY() + (pEntity.getTargetPosY() - pEntity.getStartPosY()) * distPercent;
            float newZ = pEntity.getStartPosZ() + (pEntity.getTargetPosZ() - pEntity.getStartPosZ()) * distPercent;

            moveEntityToXYZ(pEntity, newX, newY, newZ);
        }
    }


    @Override
    public Color getRenderColor(ClientParticleHelperEntity animatable, float partialTick, int packedLight) {
        return Color.WHITE;
//        return Color.ofRGBA(255, 255, 255, (int) (1.0f * animatable.getLifetime() / 12 * 240) + 15 );
    }
}
