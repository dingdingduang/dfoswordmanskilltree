package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.somebasicskills.globalmethods.GeneralMethods;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMinecraftInstance;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.printInGameMsg;

public class ChannelingIconRenderer extends GeoEntityRenderer<ChannelingIconEntity> {
    private static boolean PreviousIsCamMirrorBoolean = false;

    public ChannelingIconRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ChannelingIconModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public int getSkyLightLevel(ChannelingIconEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public int getBlockLightLevel(ChannelingIconEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(ChannelingIconEntity instance) {
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName());
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName());
    }

    @Override
    public RenderType getRenderType(ChannelingIconEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(ChannelingIconEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
        float translatedY = 0.5f * (entity.getMaxLifetime() - entity.getLifetime()) / entity.getMaxLifetime();
        //client move
        if (entity.hasTargetLocXYZ()) {
            float distPercent = (entity.getMaxLifetime() - entity.getLifetime() + partialTick) / entity.getMaxLifetime();
            float newX = entity.getStartPosX() + (entity.getTargetPosX() - entity.getStartPosX()) * distPercent;
            float newY = entity.getStartPosY() + (entity.getTargetPosY() - entity.getStartPosY()) * distPercent;
            float newZ = entity.getStartPosZ() + (entity.getTargetPosZ() - entity.getStartPosZ()) * distPercent;
            moveEntityToXYZ(entity, newX, newY, newZ);
        }
        else if (entity.isBoundToEntity()) {
            Entity owner = entity.getOwner();
            if (owner != null) {
                Vector3f lerpVec = getEntityClientPosWithPartialTicks(owner, partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
                moveEntityToPos(entity, lerpVec);
//                Integer orderSequence = GlobalClientMaps.getChannelingIconOrder().get(owner);
//                if (orderSequence != null) {
//                    entity.setClientOrderSequence(orderSequence);
//                    GlobalClientMaps.getChannelingIconOrder().put(owner, orderSequence + 1);
//                }
//                translatedY = translatedY + entity.getClientOrderSequence() * DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_PADDING;
            }
        }
        poseStack.pushPose();

        poseStack.translate(0, translatedY, 0);

        poseStack.pushPose();

        Entity clientCameraEntity = DfoGeneralMethods.getClientCameraEntity();
        if (clientCameraEntity != null) {
            Quaternionf rotatorY = new Quaternionf();
            QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(EntityMethods.getEntityHorizontalFacingDeg(clientCameraEntity)), 0f, -1f, 0f);

            Quaternionf rotatorX = new Quaternionf();
            QuatRotationByAxis(rotatorX, MathMethods.AngleToRadians(EntityMethods.getEntityVerticalFacingDeg(clientCameraEntity)), 1f, 0f, 0f);

            rotatorY.mul(rotatorX);

            if (entity.getEntityScale() != 1) {
                rotatorY.scale(entity.getEntityScale());
            }

            poseStack.mulPose(rotatorY);
        }

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, entityYaw, partialTick, packedLight);

        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);

        poseStack.popPose();

        poseStack.popPose();
    }

//    @Override
//    public int getPackedOverlay(ChannelingIconEntity animatable, float u) {
//        return OverlayTexture.NO_OVERLAY;
//    }


    @Override
    public Color getRenderColor(ChannelingIconEntity animatable, float partialTick, int packedLight) {
        if (animatable.getLifetime() > animatable.getMaxLifetime() - 5) {
            return Color.ofRGBA(255, 255, 255, 48 * (animatable.getMaxLifetime() - animatable.getLifetime()) + 15);
        }
        else if (animatable.getLifetime() <= 5) {
            return Color.ofRGBA(255, 255, 255, 48 * animatable.getLifetime() + 15);
        }
        else {
            return Color.WHITE;
        }
    }
}
