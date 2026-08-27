package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
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

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMinecraftInstance;

public class HitEffRenderer extends GeoEntityRenderer<HitEffEntity> {
    public HitEffRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new HitEffModel());
        this.shadowOpacity = 0.0f;
    }

    @Override
    public int getBlockLight(HitEffEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public Identifier getTextureLocation(HitEffEntity instance) {
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
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName() + instance.getClientTotalFrameIndexHelper()[instance.getMaxLifetime() - Math.max(instance.getLifetime(), 1)] + ".png");
    }

    @Override
    public RenderLayer getRenderType(HitEffEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EntityRenderState entityRenderState, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        HitEffEntity entity = this.animatable;
        //client move
        if (entity.isBoundToEntity() && entity.getOwner() != null) {
            Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getOwner(), this.partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
            moveEntityToPos(entity, lerpVec);
        }
        poseStack.push();

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

            poseStack.multiply(rotatorY);

            poseStack.push();
            //rotate Z deg
            Quaternionf rotatorZ = new Quaternionf();
            QuatRotationByAxis(rotatorZ, MathMethods.AngleToRadians(entity.getRotationDegreeZ()), 0f, 0f, -1f);
            poseStack.multiply(rotatorZ);

            this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, this.partialTick, packedLight);

            poseStack.pop();

            poseStack.pop();
        }
        else {
//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, this.partialTick, packedLight);

            this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, this.partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, partialTick, packedLight);

            poseStack.pop();
        }
    }
}
