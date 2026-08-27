package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target;

import com.mojang.blaze3d.systems.RenderSystem;
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
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class TargetZoneSelectionRenderer extends GeoProjectilesRenderer<TargetZoneSelectionEntity> {
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
        }
    }

    @Override
    public RenderType getRenderType(TargetZoneSelectionEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
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

            Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
            poseStack.mulPose(mojangQuaternionY);
            float scale = entity.getEntityScale();
            if (scale != 1) {
                poseStack.scale(scale, scale, scale);
            }

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, entityYaw, partialTick, packedLight);

            var predicate = new AnimationEvent<>(entity, 0, 0, partialTick,
                    false, Collections.singletonList(new EntityModelData()));

            this.modelProvider.setCustomAnimations(entity, getInstanceId(entity), predicate);
            RenderSystem.setShaderTexture(0, getTextureLocation(entity));

            GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
            Color color = Color.WHITE;
            super.render(model, entity, partialTick, RenderType.entityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.NO_OVERLAY, color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

            poseStack.popPose();
        }
    }
}
