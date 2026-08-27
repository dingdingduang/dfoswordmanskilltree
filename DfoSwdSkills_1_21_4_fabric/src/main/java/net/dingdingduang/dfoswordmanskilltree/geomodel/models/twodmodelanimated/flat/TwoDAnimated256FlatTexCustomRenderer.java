package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.flat;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
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

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class TwoDAnimated256FlatTexCustomRenderer extends GeoEntityRenderer<TwoDAnimated256FlatTexCustomEntity> {
    public TwoDAnimated256FlatTexCustomRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new TwoDAnimated256FlatTexCustomModel());
        this.shadowOpacity = 0.0f;
    }

    @Override
    public int getBlockLight(TwoDAnimated256FlatTexCustomEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public Identifier getTextureLocation(TwoDAnimated256FlatTexCustomEntity instance) {
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
//            return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName() + instance.getClientTotalFrameIndexHelper()[instance.getMaxLifetime() - instance.getLifetime()] + ".png");
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
//            return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName() + targetFrame + ".png");
        }
    }

    @Override
    public RenderLayer getRenderType(TwoDAnimated256FlatTexCustomEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public boolean shouldRender(TwoDAnimated256FlatTexCustomEntity pEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        Entity player = getClientPlayer();
        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityX(player), EntityMethods.getEntityY(player), EntityMethods.getEntityZ(player), EntityMethods.getEntityX(pEntity), EntityMethods.getEntityY(pEntity), EntityMethods.getEntityZ(pEntity)) <= 32f;
    }

    @Override
    public void render(EntityRenderState entityRenderState, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        TwoDAnimated256FlatTexCustomEntity entity = this.animatable;
        //client move
        if (entity.hasTargetLocXYZ()) {
            float distPercent = (entity.getMaxLifetime() - entity.getLifetime() + this.partialTick) / entity.getMaxLifetime();
            float newX = entity.getStartPosX() + (entity.getTargetPosX() - entity.getStartPosX()) * distPercent;
            float newY = entity.getStartPosY() + (entity.getTargetPosY() - entity.getStartPosY()) * distPercent;
            float newZ = entity.getStartPosZ() + (entity.getTargetPosZ() - entity.getStartPosZ()) * distPercent;
            moveEntityToXYZ(entity, newX, newY, newZ);
        }
        else if (entity.isBoundToEntity()) {
            if (entity.getOwner() != null) {
                Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getOwner(), this.partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
                moveEntityToPos(entity, lerpVec);
            }
        }
        poseStack.push();

        Quaternionf rotatorY = new Quaternionf();
        QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);

//            Quaternionf rotatorX = new Quaternionf();
//            QuatRotationByAxis(rotatorX, MathMethods.AngleToRadians(getEntityVerticalFacingDeg(entity)), 1.0f, 0f, 0f);
//            rotatorY.mul(rotatorX);

        if (entity.getEntityScale() != 1) {
            rotatorY.scale(entity.getEntityScale());
        }

        poseStack.multiply(rotatorY);

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, this.partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, this.partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, partialTick, packedLight);

        if (entity.getLifetime() > entity.getMaxLifetime() - entity.getStartTotalFrame()) {
            this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, this.partialTick, packedLight);
        }
        else {
            this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, this.partialTick, packedLight);
        }

        poseStack.pop();
    }


    @Override
    public Color getRenderColor(TwoDAnimated256FlatTexCustomEntity animatable, float partialTick, int packedLight) {
//        return Color.WHITE;
        if (animatable.isStartFrameFading() && animatable.getLifetime() > animatable.getMaxLifetime() - animatable.getStartTotalFrame()) {
            return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), (int) ( (animatable.getMaxLifetime() - animatable.getLifetime()) * (240f / animatable.getStartTotalFrame()) ) + 15);
        }
        else {
            if (animatable.isEndFrameFading() && animatable.getLifetime() <= animatable.getEndTotalFrame()) {
                return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), (int) (animatable.getLifetime() * (240f / animatable.getEndTotalFrame())) + 15);
            }
            else {
                return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), 255);
            }
        }
    }
}
