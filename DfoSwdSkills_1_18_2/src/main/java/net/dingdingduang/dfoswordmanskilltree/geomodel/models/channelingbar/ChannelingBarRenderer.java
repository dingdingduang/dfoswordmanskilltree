package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import modified.org.joml.Quaternionf;
import modified.org.joml.Vector3f;

import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import com.mojang.math.Quaternion;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import java.util.Collections;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.COLOR_1_OVER_255_PERCENT;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getReusableResourceByStringID;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class ChannelingBarRenderer extends GeoProjectilesRenderer<ChannelingBarEntity> {
    public ChannelingBarRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ChannelingBarModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public int getSkyLightLevel(ChannelingBarEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public int getBlockLightLevel(ChannelingBarEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(ChannelingBarEntity instance) {
        return getReusableResourceByStringID(instance.getTextureName());
    }

    @Override
    public RenderType getRenderType(ChannelingBarEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void render(ChannelingBarEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;

        //client move
        if (entity.hasTargetLocXYZ()) {
            float distPercent = (entity.getMaxLifetime() - entity.getLifetime() + partialTick) / entity.getMaxLifetime();
            float newX = entity.getStartPosX() + (entity.getTargetPosX() - entity.getStartPosX()) * distPercent;
            float newY = entity.getStartPosY() + (entity.getTargetPosY() - entity.getStartPosY()) * distPercent;
            float newZ = entity.getStartPosZ() + (entity.getTargetPosZ() - entity.getStartPosZ()) * distPercent;
            moveEntityToXYZ(entity, newX, newY, newZ);
        }
        else if (entity.isBoundToEntity()) {
            if (entity.getOwner() != null) {
                Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getOwner(), partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
                moveEntityToPos(entity, lerpVec);
            }
        }
        poseStack.pushPose();

        Entity clientCameraEntity = DfoGeneralMethods.getClientCameraEntity();
        if (clientCameraEntity != null) {
            Quaternionf rotatorY = new Quaternionf();
            QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(EntityMethods.getEntityHorizontalFacingDeg(clientCameraEntity)), 0f, -1f, 0f);

            Quaternionf rotatorX = new Quaternionf();
            QuatRotationByAxis(rotatorX, MathMethods.AngleToRadians(EntityMethods.getEntityVerticalFacingDeg(clientCameraEntity)), 1f, 0f, 0f);

            Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
            poseStack.mulPose(mojangQuaternionY);
            float scale = entity.getEntityScale();
            if (scale != 1) {
                poseStack.scale(scale, scale, scale);
            }
        }

        poseStack.pushPose();

//        Quaternionf extraRotateZ = new Quaternionf();
//        QuatRotationByAxis(extraRotateZ, MathMethods.PI, 0f, 0f, 1f);
//
//        Quaternion mojangQuaternionExtraRotZ = new Quaternion(extraRotateZ.x, extraRotateZ.y, extraRotateZ.z, extraRotateZ.w);
//        poseStack.mulPose(mojangQuaternionExtraRotZ);

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, entityYaw, partialTick, packedLight);

        AnimationEvent<ChannelingBarEntity> predicate = new AnimationEvent<ChannelingBarEntity>(entity, 0, 0, partialTick,
                false, Collections.singletonList(new EntityModelData()));

        this.modelProvider.setCustomAnimations(entity, getInstanceId(entity), predicate);

//        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
		Color color = getRenderColor(entity);
		super.render(model, entity, partialTick, RenderType.entityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.NO_OVERLAY, color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

        poseStack.popPose();

        poseStack.popPose();
    }

    public Color getRenderColor(ChannelingBarEntity animatable) {
        return Color.WHITE;
    }
}
