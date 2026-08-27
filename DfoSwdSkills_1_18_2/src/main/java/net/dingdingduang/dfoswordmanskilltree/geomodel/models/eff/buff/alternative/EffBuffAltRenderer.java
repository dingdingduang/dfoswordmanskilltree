package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.alternative;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;

import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
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
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class EffBuffAltRenderer extends GeoProjectilesRenderer<EffBuffAltEntity> {
    public EffBuffAltRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EffBuffAltModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(EffBuffAltEntity instance) {
        String resPath = instance.getTextureName()+( (instance.getMaxLifeTime() - instance.getLifetime()) % 20) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX;
        return GlobalClientMaps.getReusableResourceByStringID(resPath);
    }

    @Override
    public RenderType getRenderType(EffBuffAltEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void render(EffBuffAltEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;

        Entity BoundEntity;
        if (entity.isBoundToEntity() && entity.getBoundEntityID() >= 0 && (BoundEntity = getEntityByIntID(getEntityLevel(getClientPlayer()), entity.getBoundEntityID())) != null) {
            Vector3f lerpVec = getEntityClientPosWithPartialTicks(BoundEntity, partialTick);
            moveEntityToPos(entity, lerpVec);
        }

        poseStack.pushPose();

//        Quaternionf rotatorZ = new Quaternionf();
//        QuatRotationByAxis(rotatorZ, AngleToRadians(entity.getRotationZ()), 0f, 0f, 1.0f);
//        Quaternionf rotatorX = new Quaternionf();
//        QuatRotationByAxis(rotatorX, AngleToRadians(entity.getRotationX()), 1.0f, 0f, 0f);

        Quaternionf rotatorY = new Quaternionf();
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
//        rotatorY.mul(rotatorX);

        Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
        poseStack.mulPose(mojangQuaternionY);
        float scale = entity.getEntityScale();
        if (scale != 1) {
            poseStack.scale(scale, scale, scale);
        }

        var predicate = new AnimationEvent<>(entity, 0, 0, partialTick,
                false, Collections.singletonList(new EntityModelData()));

        this.modelProvider.setCustomAnimations(entity, getInstanceId(entity), predicate);
        RenderSystem.setShaderTexture(0, getTextureLocation(entity));

        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
        Color color = getRenderColor(entity);
        super.render(model, entity, partialTick, RenderType.entityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.NO_OVERLAY, color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

        poseStack.popPose();
    }


    public Color getRenderColor(EffBuffAltEntity animatable) {
//        return Color.WHITE;
        int alphaInt = animatable.getLifetime() % 24;
        if (alphaInt > 11) {
//            return Color.ofRGBA(255, 255, 255, (int) (1.0f * (24 - alphaInt) / 12 * 240) + 15);
            return Color.ofRGBA(255, 255, 255, (int) (20.0f * (24 - alphaInt)) + 15);
        }
        else {
//            return Color.ofRGBA(255, 255, 255, (int) (1.0f * alphaInt / 12 * 240) + 15);
            return Color.ofRGBA(255, 255, 255, (int) (20.0f * alphaInt) + 15);
        }
    }
}
