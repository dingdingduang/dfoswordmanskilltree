package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape;

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
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.printInGameMsg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class SlaughterscapeSwordRenderer extends GeoEntityRenderer<SlaughterscapeSwordEntity> {
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
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName());
    }

    @Override
    public RenderType getRenderType(SlaughterscapeSwordEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
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

        poseStack.mulPose(rotatorY);

        poseStack.pushPose();

        Quaternionf rotatorX = new Quaternionf();
        QuatRotationByAxis(rotatorX, AngleToRadians(getEntityVerticalFacingDeg(entity)), 1f, 0f, 0f);

        poseStack.mulPose(rotatorX);

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);
        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);
//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);

        poseStack.popPose();

        poseStack.popPose();
    }


    @Override
    public Color getRenderColor(SlaughterscapeSwordEntity animatable, float partialTick, int packedLight) {
//        return Color.ofRGBA(255, 255, 255, 160);
        return Color.ofRGBA(255, 255, 255, (animatable.getLifetime() > 32) ? 180 :animatable.getLifetime() * 5 + 20);
    }
}
