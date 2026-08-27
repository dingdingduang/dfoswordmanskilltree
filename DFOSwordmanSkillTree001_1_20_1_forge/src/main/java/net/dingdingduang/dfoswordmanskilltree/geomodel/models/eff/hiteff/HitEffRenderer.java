package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMinecraftInstance;

public class HitEffRenderer extends GeoEntityRenderer<HitEffEntity> {
    public HitEffRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HitEffModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public int getBlockLightLevel(HitEffEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(HitEffEntity instance) {
        if (instance.getClientTotalFrameIndexHelper() == null) {
            int totalTickTime = instance.getMaxLifetime();
            int totalFrame = instance.getTotalFrame();
            int[] tempIndexHelperArr = new int[totalTickTime];
            float roundNum = 1.0f * totalTickTime / totalFrame;

            for (int i = totalTickTime; i >= 1; i--) {
                tempIndexHelperArr[totalTickTime - i] = (int) (totalFrame - (i / roundNum));
            }

            instance.setClientTotalFrameIndexHelper(tempIndexHelperArr);
        }
        String resPath = instance.getTextureName() + instance.getClientTotalFrameIndexHelper()[instance.getMaxLifetime() - Math.max(instance.getLifetime(), 1)] + DfoSwordmanSkillTreeConstants.PNG_POSTFIX;
        return GlobalClientMaps.getReusableResourceByStringID(resPath);
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName() + instance.getClientTotalFrameIndexHelper()[instance.getMaxLifetime() - Math.max(instance.getLifetime(), 1)] + ".png");
    }

    @Override
    public RenderType getRenderType(HitEffEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(HitEffEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
        //client move
        if (entity.isBoundToEntity() && entity.getOwner() != null) {
            Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getOwner(), partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
            moveEntityToPos(entity, lerpVec);
        }
        poseStack.pushPose();

        Entity camEntity = getMinecraftInstance().getCameraEntity();

        if (camEntity != null) {
            Quaternionf rotatorY = new Quaternionf();
            QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(getEntityHorizontalFacingDeg(camEntity)), 0f, -1.0f, 0f);

            Quaternionf rotatorX = new Quaternionf();
            QuatRotationByAxis(rotatorX, MathMethods.AngleToRadians(getEntityVerticalFacingDeg(camEntity)), 1.0f, 0f, 0f);
            rotatorY.mul(rotatorX);

            if (entity.getEntityScale() != 1) {
                rotatorY.scale(entity.getEntityScale());
            }

            poseStack.mulPose(rotatorY);

            poseStack.pushPose();
            //rotate Z deg
            Quaternionf rotatorZ = new Quaternionf();
            QuatRotationByAxis(rotatorZ, MathMethods.AngleToRadians(entity.getRotationDegreeZ()), 0f, 0f, -1f);
            poseStack.mulPose(rotatorZ);

            this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, entityYaw, partialTick, packedLight);

            poseStack.popPose();

            poseStack.popPose();
        }
        else {
//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);

            this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);

            poseStack.popPose();
        }
    }
}
