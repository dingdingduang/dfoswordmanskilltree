package net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityVerticalFacingDeg;

public class FloatingSwordRenderer extends GeoEntityRenderer<FloatingSwordEntity> {
    public FloatingSwordRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FloatingSwordModel());
        this.shadowOpacity = 0.0f;
//        this.getAnimatable().
//        this.entityRenderTranslations.
    }

    @Override
    public void render(FloatingSwordEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        this.animatable = entity;
        poseStack.push();

//        Quaternionf rotator = new Quaternionf();
//        QuaternionHelper.setAngleXYZ(rotator, entity.testrot, entity.testrot, entity.testrot);
//        entity.testrot = entity.testrot + 1 % 360;
//        poseStack.multiply(rotator);

        Quaternionf rotatorZ = new Quaternionf();
        rotatorZ.rotationAxis(MathMethods.AngleToRadians(getEntityVerticalFacingDeg(entity)), 0f, 0f, 1.0f);

        Quaternionf rotatorY = new Quaternionf();
        rotatorY.rotationAxis(MathMethods.AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorZ);

        poseStack.multiply(rotatorY);

        this.defaultRender(poseStack, entity, bufferSource, null, (VertexConsumer)null, entityYaw, partialTick, packedLight);

        poseStack.pop();
    }

    @Override
    public Identifier getTextureLocation(FloatingSwordEntity instance) {
        return DfoSwdGeneralGeoEntityResources.FLOATING_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public RenderLayer getRenderType(FloatingSwordEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
