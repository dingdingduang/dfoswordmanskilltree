package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodel;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityVerticalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMinecraftInstance;

public class TwoDRenderer extends GeoEntityRenderer<TwoDEntity> {
    public TwoDRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new TwoDModel());
        this.shadowOpacity = 0.0f;
    }

    @Override
    public Identifier getTextureLocation(TwoDEntity instance) {
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/skillicons/sword/blademaster/blademaster_0_1.png");
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName());
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName());
    }

    @Override
    public RenderLayer getRenderType(TwoDEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(TwoDEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        this.animatable = entity;
        poseStack.push();
        if (getMinecraftInstance().getCameraEntity() != null) {
            Quaternionf rotatorY = new Quaternionf();
            QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(getEntityHorizontalFacingDeg(getMinecraftInstance().getCameraEntity())), 0f, -1.0f, 0f);

            Quaternionf rotatorX = new Quaternionf();
            QuatRotationByAxis(rotatorX, MathMethods.AngleToRadians(getEntityVerticalFacingDeg(getMinecraftInstance().getCameraEntity())), 1.0f, 0f, 0f);
            rotatorY.mul(rotatorX);

            poseStack.multiply(rotatorY);
        }

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);
        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);

        poseStack.pop();
    }

//    @Override
//    public Color getRenderColor(TwoDEntity animatable, float partialTick, int packedLight) {
//        return Color.ofRGBA(255, 255, 255, 255);
//    }
}
