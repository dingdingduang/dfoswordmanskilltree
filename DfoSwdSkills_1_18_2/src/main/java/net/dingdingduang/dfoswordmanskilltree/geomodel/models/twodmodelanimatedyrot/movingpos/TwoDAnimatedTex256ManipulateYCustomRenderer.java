package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.movingpos;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
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
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMinecraftInstance;

public class TwoDAnimatedTex256ManipulateYCustomRenderer extends GeoProjectilesRenderer<TwoDAnimatedTex256ManipulateYCustomEntity> {
    public TwoDAnimatedTex256ManipulateYCustomRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TwoDAnimatedTex256ManipulateYCustomModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public int getBlockLightLevel(TwoDAnimatedTex256ManipulateYCustomEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(TwoDAnimatedTex256ManipulateYCustomEntity instance) {
        if (instance.isFrameDistributedEvenly()) {
            if (instance.getClientTotalFrameIndexHelper() == null) {
                int totalTickTime = instance.getMaxLifeTime();
                int totalFrame = instance.getTotalFrameCount();
                int[] tempIndexHelperArr = new int[totalTickTime];
                float roundNum = 1.0f * totalTickTime / totalFrame;

                for (int i = totalTickTime; i >= 1; i--) {
                    tempIndexHelperArr[totalTickTime - i] = (int) (totalFrame - (i / roundNum));
                }

                instance.setClientTotalFrameIndexHelper(tempIndexHelperArr);
            }

            return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName() + instance.getClientTotalFrameIndexHelper()[instance.getMaxLifeTime() - instance.getLifetime()] + DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
        }
        else {
            int targetFrame;
            //start frame tex path
            if (instance.getLifetime() > instance.getMaxLifeTime() - instance.getStartTotalFrame()) {
                targetFrame = instance.getMaxLifeTime() - instance.getLifetime();
            } //end frame tex path
            else if (instance.getLifetime() <= instance.getEndTotalFrame()) {
                targetFrame = instance.getStartTotalFrame() + instance.getTotalFrameCount() + instance.getEndTotalFrame() - instance.getLifetime();
            } else {
                int clientLoopFrameCounter = instance.getClientLoopFrameCounter();
                targetFrame = instance.getStartTotalFrame() + clientLoopFrameCounter;
                instance.setClientLoopFrameCounter((clientLoopFrameCounter + 1) % instance.getTotalFrameCount());
            }
            return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName() + targetFrame + DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
        }
    }

    @Override
    public RenderType getRenderType(TwoDAnimatedTex256ManipulateYCustomEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public boolean shouldRender(TwoDAnimatedTex256ManipulateYCustomEntity pEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        Entity player = getClientPlayer();
        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityX(player), EntityMethods.getEntityY(player), EntityMethods.getEntityZ(player), EntityMethods.getEntityX(pEntity), EntityMethods.getEntityY(pEntity), EntityMethods.getEntityZ(pEntity)) <= 32f;
    }

    @Override
    public void render(TwoDAnimatedTex256ManipulateYCustomEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
        //client move
        float entityFacingAngleOri = EntityMethods.getEntityHorizontalFacingDeg(entity);
        float currentEntityFacingAngle = entityFacingAngleOri;
        if (entity.hasTargetLocXYZ()) {
            float distPercent = (entity.getMaxLifeTime() - entity.getLifetime() + partialTick) / entity.getMaxLifeTime();
            float newX = entity.getStartPosX() + (entity.getTargetPosX() - entity.getStartPosX()) * distPercent;
            float newY = entity.getStartPosY() + (entity.getTargetPosY() - entity.getStartPosY()) * distPercent;
            float newZ = entity.getStartPosZ() + (entity.getTargetPosZ() - entity.getStartPosZ()) * distPercent;
            EntityMethods.moveEntityToXYZ(entity, newX, newY, newZ);
        }
        else if (entity.isBoundToEntity()) {
            if (entity.getOwner() != null) {
                if (entity.isAlwaysFaceToOwnerHorizontalFacingAngle()) {
//                currentEntityFacingAngle = EntityMethods.getEntityHorizontalFacingDeg(entity.getOwner());
                    currentEntityFacingAngle = EntityMethods.getEntityBodyHorizontalFacingDeg(entity.getOwner());
                }
                Vector3f lerpVec = EntityMethods.getEntityClientPosWithPartialTicks(entity.getOwner(), partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
                EntityMethods.moveEntityToPos(entity, lerpVec);
            }
        }
        poseStack.pushPose();
        if (getMinecraftInstance().getCameraEntity() != null) {

            Quaternionf rotatorZ = new Quaternionf();
            boolean isZModified = false;


            if (entity.isCheckCamera()) {
                Vector3f camPos = EntityMethods.getEntityPos(getMinecraftInstance().getCameraEntity());
                Vector3f effEntityPos = EntityMethods.getEntityPos(entity);

                float Hypotenuse_HorizontalDistBetweenCamAndEntity = Vector3fMethods.DistanceBetweenVec3fHorizontal(camPos, effEntityPos);

                float AngleFromEntity2Cam = Vector3fMethods.AngleFromLocationToLocationHorizontal(effEntityPos, camPos);

                int PosOrNeg = 1;
//            Vector3fMethods.IsAngleDegBetweenAB(MathMethods.RoundAngle(AngleFromEntity2Cam + entityFacingAngleOri), MathMethods.RoundAngle(180f+entityFacingAngleOri), MathMethods.RoundAngle(entityFacingAngleOri));
//            if (AngleFromEntity2Cam > 180)
                if (Vector3fMethods.IsAngleDegBetweenAB(MathMethods.RoundAngle(AngleFromEntity2Cam + entityFacingAngleOri), MathMethods.RoundAngle(180.1f + entityFacingAngleOri), MathMethods.RoundAngle(entityFacingAngleOri))) {
                    PosOrNeg = -1;
                }

                float Opposite_temp = camPos.y() - effEntityPos.y();


                float Opposite_Adjacent_temp = MathMethods.cos(MathMethods.AngleToRadians((AngleFromEntity2Cam - entityFacingAngleOri) * MathMethods.DEG_TO_RAD)) * Hypotenuse_HorizontalDistBetweenCamAndEntity;

                float ZRot;
                if (Opposite_Adjacent_temp == 0) {
                    ZRot = 90;
                }
                else {
                    ZRot = (float) Math.atan2(Opposite_temp, Opposite_Adjacent_temp);
                }
//                ZRot = (float) Math.atan2(Opposite_temp, Opposite_Adjacent_temp);
                QuatRotationByAxis(rotatorZ, PosOrNeg * ZRot, 0f, 0f, 1f);
                isZModified = true;
            }
//            printInGameMsg("Opposite_temp" + Opposite_temp);
//            printInGameMsg("Opposite_Adjacent_temp" + Opposite_Adjacent_temp);
//            printInGameMsg("ZRot Radians: "+ (ZRot));
//            printInGameMsg("ZRot Angle: "+ MathMethods.AngleToDegrees(ZRot));

            Quaternionf rotatorY = new Quaternionf();
            QuatRotationByAxis(rotatorY, AngleToRadians(currentEntityFacingAngle), 0f, -1.0f, 0f);

//            QuatRotationByAxis(rotatorZ,  PosOrNeg * Degree_rotateZ, 0f, 0f, 1f);

            if (isZModified) {
                rotatorY.mul(rotatorZ);
            }

            Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
            poseStack.mulPose(mojangQuaternionY);

            float scale = entity.getEntityScale();
            if (scale != 1) {
                poseStack.scale(scale, scale, scale);
            }
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

//    @Override
//    public Color getRenderColor(TwoDAnimatedTex64Entity animatable, float partialTick, int packedLight) {
//        return Color.ofRGBA(255, 255, 255, 255);
//    }

    public Color getRenderColor(TwoDAnimatedTex256ManipulateYCustomEntity animatable) {
        if (animatable.isStartFrameFading() && animatable.getLifetime() > animatable.getMaxLifeTime() - animatable.getStartTotalFrame()) {
            return Color.ofRGBA(255, 255, 255, (int) ( (animatable.getMaxLifeTime() - animatable.getLifetime()) * (240f / animatable.getStartTotalFrame()) ) + 15);
        }
        else {
            if (animatable.isEndFrameFading() && animatable.getLifetime() <= animatable.getEndTotalFrame()) {
                return Color.ofRGBA(255, 255, 255, (int) (animatable.getLifetime() * (240f / animatable.getEndTotalFrame())) + 15);
            }
            else {
                return Color.ofRGBA(255, 255, 255, 255);
            }
        }
    }
}
