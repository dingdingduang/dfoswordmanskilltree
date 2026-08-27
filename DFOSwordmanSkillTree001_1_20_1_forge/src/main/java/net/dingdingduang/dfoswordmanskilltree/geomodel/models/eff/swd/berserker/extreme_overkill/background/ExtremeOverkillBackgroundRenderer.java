package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.berserker.extreme_overkill.background;

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
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class ExtremeOverkillBackgroundRenderer extends GeoEntityRenderer<ExtremeOverkillBackgroundEntity> {
    public ExtremeOverkillBackgroundRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ExtremeOverkillBackgroundModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public int getBlockLightLevel(ExtremeOverkillBackgroundEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(ExtremeOverkillBackgroundEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID("textures/entity/skilleff/berserker/extreme_overkill/background/"+ ((instance.getMaxLifeTime() - instance.getLifetime()) % 20) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
    }

    @Override
    public RenderType getRenderType(ExtremeOverkillBackgroundEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }


    @Override
    public boolean shouldRender(ExtremeOverkillBackgroundEntity pEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
//        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityPos(getClientPlayer()), EntityMethods.getEntityPos(pEntity)) <= 32f;
        Entity player = getClientPlayer();
        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityX(player), EntityMethods.getEntityY(player), EntityMethods.getEntityZ(player), EntityMethods.getEntityX(pEntity), EntityMethods.getEntityY(pEntity), EntityMethods.getEntityZ(pEntity)) <= 32f;
    }

    @Override
    public void render(ExtremeOverkillBackgroundEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;

        //client move
//        float horizontalFacingDeg = getEntityHorizontalFacingDeg(entity);
//        if (entity.getOwner() != null) {
//            Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getOwner(), partialTick).add(entity.getTargetCenterPosX(), entity.getTargetCenterPosY(), entity.getTargetCenterPosZ());
//            moveEntityToPos(entity, lerpVec);
//            horizontalFacingDeg = getEntityHorizontalFacingDeg(entity.getOwner());
//        }
        poseStack.pushPose();


        if (entity.getEntityScale() != 1) {
            poseStack.scale(entity.getEntityScale(), 5.15f, entity.getEntityScale());
        }

        Quaternionf rotatorY = new Quaternionf();
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);

        poseStack.mulPose(rotatorY);

        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);
//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);
//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);

        poseStack.popPose();
    }

    @Override
    public Color getRenderColor(ExtremeOverkillBackgroundEntity animatable, float partialTick, int packedLight) {
        int currentLifeTime = animatable.getMaxLifeTime() - animatable.getLifetime();
        if (currentLifeTime < 10) {
            return Color.ofRGBA(255, 255 ,255, ( currentLifeTime * 24) + 15);
        }
        else {
            return Color.WHITE;
//            if (animatable.getLifetime() <= animatable.getEndTotalFrame()) {
//                return Color.ofRGBA(255, 255 ,255, (int) (animatable.getLifetime() * (240f / animatable.getEndTotalFrame())) + 15);
//            }
//            else {
//                return Color.ofRGBA(255, 255 ,255, 255);
//            }
        }
    }
}
