package net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import modified.org.joml.Quaternionf;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import java.util.Collections;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.COLOR_1_OVER_255_PERCENT;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityVerticalFacingDeg;

public class FloatingSwordRenderer extends GeoProjectilesRenderer<FloatingSwordEntity> {
    public FloatingSwordRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FloatingSwordModel());
        this.shadowRadius = 0.0f;
//        this.getAnimatable().
//        this.entityRenderTranslations.
    }

    @Override
    public void render(FloatingSwordEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
        poseStack.pushPose();

//        Quaternionf rotator = new Quaternionf();
//        QuaternionHelper.setAngleXYZ(rotator, entity.testrot, entity.testrot, entity.testrot);
//        entity.testrot = entity.testrot + 1 % 360;
//        Quaternion mojangQuaternion = new Quaternion(rotator.x, rotator.y, rotator.z, rotator.w);
//        poseStack.mulPose(mojangQuaternion);

        Quaternionf rotatorZ = new Quaternionf();
        rotatorZ.rotationAxis(MathMethods.AngleToRadians(getEntityVerticalFacingDeg(entity)), 0f, 0f, 1.0f);

        Quaternionf rotatorY = new Quaternionf();
        rotatorY.rotationAxis(MathMethods.AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorZ);

        Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
        poseStack.mulPose(mojangQuaternionY);

        var predicate = new AnimationEvent<>(entity, 0, 0, partialTick,
                false, Collections.singletonList(new EntityModelData()));

        this.modelProvider.setCustomAnimations(entity, getInstanceId(entity), predicate);
        RenderSystem.setShaderTexture(0, getTextureLocation(entity));

        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
        Color color = Color.WHITE;
        super.render(model, entity, partialTick, RenderType.entityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.NO_OVERLAY, color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FloatingSwordEntity instance) {
        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/mc_diamond_sword.png");
    }

    @Override
    public RenderType getRenderType(FloatingSwordEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
