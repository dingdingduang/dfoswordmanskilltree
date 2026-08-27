package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Identifier;
import net.minecraft.entity.Entity;
import modified.org.joml.Quaternionf;
import modified.org.joml.Vector3f;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import net.minecraft.util.math.Quaternion;
import net.minecraft.client.render.OverlayTexture;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.COLOR_1_OVER_255_PERCENT;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getReusableResourceByStringID;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class ChannelingIconRenderer extends GeoProjectilesRenderer<ChannelingIconEntity> {
    public ChannelingIconRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ChannelingIconModel());
        this.shadowOpacity = 0.0f;
    }

    @Override
    public int getBlockLight(ChannelingIconEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public Identifier getTextureLocation(ChannelingIconEntity instance) {
        return getReusableResourceByStringID(instance.getTextureName());
    }

    @Override
    public RenderLayer getRenderType(ChannelingIconEntity animatable, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void render(ChannelingIconEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
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
        poseStack.push();

        RenderSystem.setShaderTexture(0, getTextureLocation(entity));
        poseStack.translate(0, translatedY, 0);

        poseStack.push();

        Entity clientCameraEntity = DfoGeneralMethods.getClientCameraEntity();
        if (clientCameraEntity != null) {
            Quaternionf rotatorY = new Quaternionf();
            QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(EntityMethods.getEntityHorizontalFacingDeg(clientCameraEntity)), 0f, -1f, 0f);

            Quaternionf rotatorX = new Quaternionf();
            QuatRotationByAxis(rotatorX, MathMethods.AngleToRadians(EntityMethods.getEntityVerticalFacingDeg(clientCameraEntity)), 1f, 0f, 0f);

            rotatorY.mul(rotatorX);

            Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
            poseStack.multiply(mojangQuaternionY);
            float scale = entity.getEntityScale();
            if (scale != 1) {
                poseStack.scale(scale, scale, scale);
            }
        }

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, entityYaw, partialTick, packedLight);

        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
        Color color = getRenderColor(entity);
        super.render(model, entity, partialTick, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.DEFAULT_UV, color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

        poseStack.pop();

        poseStack.pop();
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
