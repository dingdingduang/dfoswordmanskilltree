package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.berserker.extreme_overkill.bloodsword;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
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
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class ExtremeOverkillBloodSwordRenderer extends GeoEntityRenderer<ExtremeOverkillBloodSwordEntity> {
    public ExtremeOverkillBloodSwordRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ExtremeOverkillBloodSwordModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public int getBlockLightLevel(ExtremeOverkillBloodSwordEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(ExtremeOverkillBloodSwordEntity instance) {
//        return DfoSwdGeneralGeoEntityResources.EXTREME_OVERKILL_BLOOD_SWORD_TEXTURE_PNG_RESOURCE;
        return GlobalClientMaps.getReusableResourceByStringID("textures/entity/skilleff/berserker/extreme_overkill/blood_sword_2/"+ ((instance.getMaxLifeTime() - instance.getLifetime()) % 6) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
    }

    @Override
    public RenderType getRenderType(ExtremeOverkillBloodSwordEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }


    @Override
    public boolean shouldRender(ExtremeOverkillBloodSwordEntity pEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
//        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityPos(getClientPlayer()), EntityMethods.getEntityPos(pEntity)) <= 32f;
        Entity player = getClientPlayer();
        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityX(player), EntityMethods.getEntityY(player), EntityMethods.getEntityZ(player), EntityMethods.getEntityX(pEntity), EntityMethods.getEntityY(pEntity), EntityMethods.getEntityZ(pEntity)) <= 32f;
    }

    @Override
    public void render(ExtremeOverkillBloodSwordEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
        //client move
        float horizontalFacingDeg = getEntityHorizontalFacingDeg(entity);
        if (entity.getOwner() != null) {
            horizontalFacingDeg = getEntityHorizontalFacingDeg(entity.getOwner());
            float newX = Vector3fMethods.PolarProjectionFromXHorizon(-1.2f, horizontalFacingDeg);
            float newZ = Vector3fMethods.PolarProjectionFromZHorizon(-1.2f, horizontalFacingDeg);
            entity.setTargetCenterPosX(newX);
            entity.setTargetCenterPosZ(newZ);

            Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getOwner(), partialTick).add(entity.getTargetCenterPosX(), entity.getTargetCenterPosY(), entity.getTargetCenterPosZ());
            moveEntityToPos(entity, lerpVec);
        }
        poseStack.pushPose();

        if (entity.getEntityScale() != 1) {
            poseStack.scale(entity.getEntityScale(), entity.getEntityScale(), entity.getEntityScale());
        }

        Quaternionf rotatorY = new Quaternionf();
        QuatRotationByAxis(rotatorY, AngleToRadians(horizontalFacingDeg), 0f, -1.0f, 0f);

        poseStack.mulPose(rotatorY);

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);
        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);
//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);

        poseStack.popPose();
    }

    @Override
    public Color getRenderColor(ExtremeOverkillBloodSwordEntity animatable, float partialTick, int packedLight) {
        return Color.WHITE;
    }
}
