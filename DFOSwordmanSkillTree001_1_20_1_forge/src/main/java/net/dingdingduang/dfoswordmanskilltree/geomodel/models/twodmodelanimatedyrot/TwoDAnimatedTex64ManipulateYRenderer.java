package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityVerticalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMinecraftInstance;

public class TwoDAnimatedTex64ManipulateYRenderer extends GeoEntityRenderer<TwoDAnimatedTex64ManipulateYEntity> {
    public TwoDAnimatedTex64ManipulateYRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TwoDAnimatedTex64ManipulateYModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(TwoDAnimatedTex64ManipulateYEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName()+(instance.getMaxLifeTime() - instance.getLifetime()) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/skillicons/sword/blademaster/blademaster_0_1.png");
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID,
////                instance.getTextureName()+( ExtraMathMethods.clamp(instance.getMaxLifeTime() - instance.getLifetime(), 0, instance.getMaxLifeTime()) )+".png"
//                instance.getTextureName()+(instance.getMaxLifeTime() - instance.getLifetime())+".png"
//        );
    }

    @Override
    public RenderType getRenderType(TwoDAnimatedTex64ManipulateYEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(TwoDAnimatedTex64ManipulateYEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
        poseStack.pushPose();
        if (getMinecraftInstance().getCameraEntity() != null) {
            Quaternionf rotatorY = new Quaternionf();
            QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)+45), 0f, -1.0f, 0f);

            poseStack.mulPose(rotatorY);
        }

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);
        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);
//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);

        poseStack.popPose();
    }

//    @Override
//    public Color getRenderColor(TwoDAnimatedTex64Entity animatable, float partialTick, int packedLight) {
//        return Color.ofRGBA(255, 255, 255, 255);
//    }
}
