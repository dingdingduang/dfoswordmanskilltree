package net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityVerticalFacingDeg;

public class FloatingSwordRenderer extends GeoEntityRenderer<FloatingSwordEntity> {
    public FloatingSwordRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FloatingSwordModel());
        this.shadowRadius = 0.0f;
//        this.getAnimatable().
//        this.entityRenderTranslations.
    }

    @Override
    public void render(EntityRenderState entityRenderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
//        this.animatable = entity;
        FloatingSwordEntity entity = this.animatable;

        poseStack.pushPose();

//        Quaternionf rotator = new Quaternionf();
//        QuaternionHelper.setAngleXYZ(rotator, entity.testrot, entity.testrot, entity.testrot);
//        entity.testrot = entity.testrot + 1 % 360;
//        poseStack.mulPose(rotator);

        Quaternionf rotatorZ = new Quaternionf();
        rotatorZ.rotationAxis(MathMethods.AngleToRadians(getEntityVerticalFacingDeg(entity)), 0f, 0f, 1.0f);

        Quaternionf rotatorY = new Quaternionf();
        rotatorY.rotationAxis(MathMethods.AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorZ);

        poseStack.mulPose(rotatorY);

        this.defaultRender(poseStack, entity, bufferSource, (RenderType)null, (VertexConsumer)null, this.partialTick, packedLight);

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FloatingSwordEntity instance) {
        return DfoSwdGeneralGeoEntityResources.FLOATING_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public RenderType getRenderType(FloatingSwordEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
