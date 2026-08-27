package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Identifier;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMinecraftInstance;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.printInGameMsg;

public class ChannelingIconRenderer extends GeoEntityRenderer<ChannelingIconEntity> {
    private static boolean PreviousIsCamMirrorBoolean = false;

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
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName());
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName());
    }

    @Override
    public RenderLayer getRenderType(ChannelingIconEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EntityRenderState entityRenderState, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        ChannelingIconEntity entity = this.animatable;
        float translatedY = 0.5f * (entity.getMaxLifetime() - entity.getLifetime()) / entity.getMaxLifetime();
        //client move
        if (entity.hasTargetLocXYZ()) {
            float distPercent = (entity.getMaxLifetime() - entity.getLifetime() + this.partialTick) / entity.getMaxLifetime();
            float newX = entity.getStartPosX() + (entity.getTargetPosX() - entity.getStartPosX()) * distPercent;
            float newY = entity.getStartPosY() + (entity.getTargetPosY() - entity.getStartPosY()) * distPercent;
            float newZ = entity.getStartPosZ() + (entity.getTargetPosZ() - entity.getStartPosZ()) * distPercent;
            moveEntityToXYZ(entity, newX, newY, newZ);
        }
        else if (entity.isBoundToEntity()) {
            Entity owner = entity.getOwner();
            if (owner != null) {
                Vector3f lerpVec = getEntityClientPosWithPartialTicks(owner, this.partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
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

        poseStack.translate(0, translatedY, 0);

        poseStack.push();

        Entity clientCameraEntity = DfoGeneralMethods.getClientCameraEntity();
        if (clientCameraEntity != null) {
            Quaternionf rotatorY = new Quaternionf();
            QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(EntityMethods.getEntityHorizontalFacingDeg(clientCameraEntity)), 0f, -1f, 0f);

            Quaternionf rotatorX = new Quaternionf();
            QuatRotationByAxis(rotatorX, MathMethods.AngleToRadians(EntityMethods.getEntityVerticalFacingDeg(clientCameraEntity)), 1f, 0f, 0f);

            rotatorY.mul(rotatorX);

            if (entity.getEntityScale() != 1) {
                rotatorY.scale(entity.getEntityScale());
            }

            poseStack.multiply(rotatorY);
        }

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, this.partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, this.partialTick, packedLight);

        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, this.partialTick, packedLight);

        poseStack.pop();

        poseStack.pop();
    }

//    @Override
//    public int getPackedOverlay(ChannelingIconEntity animatable, float u) {
//        return OverlayTexture.DEFAULT_UV;
//    }


    @Override
    public Color getRenderColor(ChannelingIconEntity animatable, float partialTick, int packedLight) {
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
