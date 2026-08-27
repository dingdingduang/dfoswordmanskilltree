package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
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
import software.bernie.geckolib.util.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class TargetZoneSelectionRenderer extends GeoEntityRenderer<TargetZoneSelectionEntity> {
    public TargetZoneSelectionRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TargetZoneSelectionModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public int getBlockLightLevel(TargetZoneSelectionEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(TargetZoneSelectionEntity instance) {
        if (instance.isFrameDistributedEvenly()) {
            if (instance.getClientTotalFrameIndexHelper() == null) {
                int totalTickTime = instance.getMaxLifetime();
                int totalFrame = instance.getLoopTotalFrame();
                int[] tempIndexHelperArr = new int[totalTickTime];
                float roundNum = 1.0f * totalTickTime / totalFrame;

                for (int i = totalTickTime; i >= 1; i--) {
                    tempIndexHelperArr[totalTickTime - i] = (int) (totalFrame - (i / roundNum));
                }

                instance.setClientTotalFrameIndexHelper(tempIndexHelperArr);
            }

            return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName() + instance.getClientTotalFrameIndexHelper()[instance.getMaxLifetime() - instance.getLifetime()] + DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
//            return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName() + instance.getClientTotalFrameIndexHelper()[instance.getMaxLifetime() - instance.getLifetime()] + ".png");
        }
        else {
            int targetFrame;
            //start frame tex path
            if (instance.getLifetime() > instance.getMaxLifetime() - instance.getStartTotalFrame()) {
                targetFrame = instance.getMaxLifetime() - instance.getLifetime();
            } //end frame tex path
            else if (instance.getLifetime() <= instance.getEndTotalFrame()) {
                targetFrame = instance.getStartTotalFrame() + instance.getLoopTotalFrame() + instance.getEndTotalFrame() - instance.getLifetime();
            } else {
                int clientLoopFrameCounter = instance.getClientLoopFrameCounter();
                targetFrame = instance.getStartTotalFrame() + clientLoopFrameCounter;
                instance.setClientLoopFrameCounter((clientLoopFrameCounter + 1) % instance.getLoopTotalFrame());
            }
            return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName() + targetFrame + DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
//            return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName() + targetFrame + ".png");
        }
    }

    @Override
    public RenderType getRenderType(TargetZoneSelectionEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public boolean shouldRender(TargetZoneSelectionEntity pEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        Entity player = getClientPlayer();
        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityX(player), EntityMethods.getEntityY(player), EntityMethods.getEntityZ(player), EntityMethods.getEntityX(pEntity), EntityMethods.getEntityY(pEntity), EntityMethods.getEntityZ(pEntity)) <= 32f;
    }

    @Override
    public void render(TargetZoneSelectionEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
        //client move
        Entity owner = entity.getOwner();
        if (entity.hasTargetLocXYZ()) {
            if (!entity.isPosInitialized()) {
                entity.setStartPosLoc(getEntityX(entity), getEntityY(entity), getEntityZ(entity));
                entity.setTargetLoc(getEntityX(entity), getEntityY(entity), getEntityZ(entity));
            }

            for (int direction: KeyboardMethods.getClientPlayerPressingMovementKeyDirectionArr()) {
                if (direction != -1) {
                    KeyboardMethods.getClientMovementEntityAction()[direction].executeAction(entity);
                }
            }

            float newX = entity.getStartPosX() + (entity.getTargetPosX() - entity.getStartPosX()) * partialTick;
            float newY = entity.getStartPosY() + (entity.getTargetPosY() - entity.getStartPosY()) * partialTick;
            float newZ = entity.getStartPosZ() + (entity.getTargetPosZ() - entity.getStartPosZ()) * partialTick;
            moveEntityToXYZ(entity, newX, newY, newZ);
            entity.setStartPosX(newX);
            entity.setStartPosY(newY);
            entity.setStartPosZ(newZ);
        }
        else if (entity.isBoundToEntity()) {
            if (owner != null) {
                Vector3f lerpVec = getEntityClientPosWithPartialTicks(owner, partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
                moveEntityToPos(entity, lerpVec);
            }
        }

        if (entity.isVisibleToAllPlayers() || (owner == null || owner.equals(getClientPlayer())) ) {
            poseStack.pushPose();

            Quaternionf rotatorY = new Quaternionf();
            QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);

            if (entity.getEntityScale() != 1) {
                rotatorY.scale(entity.getEntityScale());
            }

            poseStack.mulPose(rotatorY);

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, entityYaw, partialTick, packedLight);

            this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);

            poseStack.popPose();
        }
    }
}
