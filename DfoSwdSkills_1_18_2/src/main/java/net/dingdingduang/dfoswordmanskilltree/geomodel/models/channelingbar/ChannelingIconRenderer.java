package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import com.mojang.blaze3d.systems.RenderSystem;
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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import modified.org.joml.Quaternionf;
import modified.org.joml.Vector3f;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import com.mojang.math.Quaternion;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.COLOR_1_OVER_255_PERCENT;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getReusableResourceByStringID;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class ChannelingIconRenderer extends GeoProjectilesRenderer<ChannelingIconEntity> {
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
        return getReusableResourceByStringID(instance.getTextureName());
    }

    @Override
    public RenderType getRenderType(ChannelingIconEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
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

        RenderSystem.setShaderTexture(0, getTextureLocation(entity));
        poseStack.translate(0, translatedY, 0);

        poseStack.pushPose();

        Entity clientCameraEntity = DfoGeneralMethods.getClientCameraEntity();
        if (clientCameraEntity != null) {
            Quaternionf rotatorY = new Quaternionf();
            QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(EntityMethods.getEntityHorizontalFacingDeg(clientCameraEntity)), 0f, -1f, 0f);

            Quaternionf rotatorX = new Quaternionf();
            QuatRotationByAxis(rotatorX, MathMethods.AngleToRadians(EntityMethods.getEntityVerticalFacingDeg(clientCameraEntity)), 1f, 0f, 0f);

            rotatorY.mul(rotatorX);

            Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
            poseStack.mulPose(mojangQuaternionY);
            float scale = entity.getEntityScale();
            if (scale != 1) {
                poseStack.scale(scale, scale, scale);
            }
        }

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, entityYaw, partialTick, packedLight);

        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
        Color color = getRenderColor(entity);
        super.render(model, entity, partialTick, RenderType.entityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.NO_OVERLAY, color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

        poseStack.popPose();

        poseStack.popPose();
    }

    public Color getRenderColor(ChannelingIconEntity animatable) {
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
