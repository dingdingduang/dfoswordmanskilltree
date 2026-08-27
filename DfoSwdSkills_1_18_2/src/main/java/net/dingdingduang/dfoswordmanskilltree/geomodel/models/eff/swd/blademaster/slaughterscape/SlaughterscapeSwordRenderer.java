package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
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
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.printInGameMsg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class SlaughterscapeSwordRenderer extends GeoProjectilesRenderer<SlaughterscapeSwordEntity> {
    public SlaughterscapeSwordRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SlaughterscapeSwordModel());
        this.shadowRadius = 0.0f;
    }

//    @Override
//    public int getSkyLightLevel(BMTempestSwordEntity pEntity, BlockPos pPos) {
//        return 15;
//    }

    @Override
    public int getBlockLightLevel(SlaughterscapeSwordEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(SlaughterscapeSwordEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName());
    }

    @Override
    public RenderType getRenderType(SlaughterscapeSwordEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public boolean shouldRender(SlaughterscapeSwordEntity pEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        Entity player = getClientPlayer();
        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityX(player), EntityMethods.getEntityY(player), EntityMethods.getEntityZ(player), EntityMethods.getEntityX(pEntity), EntityMethods.getEntityY(pEntity), EntityMethods.getEntityZ(pEntity)) <= 32f;
    }

    @Override
    public void render(SlaughterscapeSwordEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
        //client move
        if (entity.hasClientNextMovingPos()) {
            if (entity.isHitOnTarget()) {
                if (entity.getClientTargetEntity() != null) {
                    Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getClientTargetEntity(), partialTick).add(entity.getAdditionalPosX(), entity.getAdditionalPosY(), entity.getAdditionalPosZ());
                    EntityMethods.moveEntityToPos(entity, lerpVec);
                }
            }
            else {
                float newX = Vector3fMethods.lerp1D(partialTick, entity.getFromPosX(), entity.getToPosX());
                float newY = Vector3fMethods.lerp1D(partialTick, entity.getFromPosY(), entity.getToPosY());
                float newZ = Vector3fMethods.lerp1D(partialTick, entity.getFromPosZ(), entity.getToPosZ());

                EntityMethods.moveEntityToXYZ(entity, newX, newY, newZ);
            }
        }

        poseStack.pushPose();

        float clientScale = entity.getClientScale();
        float correctScale = entity.getEntityScale();
        if (clientScale < correctScale) {
            poseStack.scale(clientScale, clientScale, clientScale);
//            entity.setClientScale(clientScale + 0.025f);
            entity.setClientScale(clientScale + 0.02f);
        }
        else {
            poseStack.scale(correctScale, correctScale, correctScale);
        }

        Quaternionf rotatorY = new Quaternionf();
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);

        Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
        poseStack.mulPose(mojangQuaternionY);

        poseStack.pushPose();

        Quaternionf rotatorX = new Quaternionf();
        QuatRotationByAxis(rotatorX, AngleToRadians(getEntityVerticalFacingDeg(entity)), 1f, 0f, 0f);

        Quaternion mojangQuaternionX = new Quaternion(rotatorX.x, rotatorX.y, rotatorX.z, rotatorX.w);
        poseStack.mulPose(mojangQuaternionX);

        var predicate = new AnimationEvent<>(entity, 0, 0, partialTick,
                false, Collections.singletonList(new EntityModelData()));

        this.modelProvider.setCustomAnimations(entity, getInstanceId(entity), predicate);
        RenderSystem.setShaderTexture(0, getTextureLocation(entity));

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);
        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
        Color color = getRenderColor(entity);
        super.render(model, entity, partialTick, RenderType.entityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.NO_OVERLAY, color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

        poseStack.popPose();

        poseStack.popPose();
    }


    public Color getRenderColor(SlaughterscapeSwordEntity animatable) {
//        return Color.ofRGBA(255, 255, 255, 160);
        return Color.ofRGBA(255, 255, 255, (animatable.getLifetime() > 32) ? 180 :animatable.getLifetime() * 5 + 20);
    }
}
