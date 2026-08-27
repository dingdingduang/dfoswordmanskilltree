package net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Quaternion;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.util.Identifier;
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
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class FloatingSwordRenderer extends GeoProjectilesRenderer<FloatingSwordEntity> {
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
//        Quaternion mojangQuaternion = new Quaternion(rotator.x, rotator.y, rotator.z, rotator.w);
//        poseStack.multiply(mojangQuaternion);

        Quaternionf rotatorZ = new Quaternionf();
        rotatorZ.rotationAxis(MathMethods.AngleToRadians(getEntityVerticalFacingDeg(entity)), 0f, 0f, 1.0f);

        Quaternionf rotatorY = new Quaternionf();
        rotatorY.rotationAxis(MathMethods.AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorZ);

        Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
        poseStack.multiply(mojangQuaternionY);

        var predicate = new AnimationEvent<>(entity, 0, 0, partialTick,
                false, Collections.singletonList(new EntityModelData()));

        this.modelProvider.setCustomAnimations(entity, getInstanceId(entity), predicate);
        RenderSystem.setShaderTexture(0, getTextureLocation(entity));

        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
        Color color = Color.WHITE;
        super.render(model, entity, partialTick, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.DEFAULT_UV, color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

        poseStack.pop();
    }

    @Override
    public Identifier getTextureLocation(FloatingSwordEntity instance) {
        return getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/mc_diamond_sword.png");
    }

    @Override
    public RenderLayer getRenderType(FloatingSwordEntity animatable, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
