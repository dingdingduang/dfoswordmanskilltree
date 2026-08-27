package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.customcolor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMinecraftInstance;

public class TwoDAnimatedTexRGB256Renderer extends GeoEntityRenderer<TwoDAnimatedTexRGB256Entity> {
    public TwoDAnimatedTexRGB256Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TwoDAnimatedTexRGB256Model());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(TwoDAnimatedTexRGB256Entity instance) {
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName()+(instance.getMaxLifeTime() - instance.getLifetime())+DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/common/hitdamage/1.png");
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID,
//                instance.getTextureName()+(instance.getMaxLifeTime() - instance.getLifetime())+".png"
//        );
    }

    @Override
    public RenderType getRenderType(TwoDAnimatedTexRGB256Entity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(TwoDAnimatedTexRGB256Entity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
        //client move
        float entityFacingAngle = getEntityHorizontalFacingDeg(entity);
        if (entity.hasTargetLocXYZ()) {
            float distPercent = (entity.getMaxLifeTime() - entity.getLifetime() + partialTick) / entity.getMaxLifeTime();
            float newX = entity.getStartPosX() + (entity.getTargetPosX() - entity.getStartPosX()) * distPercent;
            float newY = entity.getStartPosY() + (entity.getTargetPosY() - entity.getStartPosY()) * distPercent;
            float newZ = entity.getStartPosZ() + (entity.getTargetPosZ() - entity.getStartPosZ()) * distPercent;
            moveEntityToXYZ(entity, newX, newY, newZ);
        }
        else if (entity.isBoundToEntity()) {
            if (entity.getOwner() != null) {
                entityFacingAngle = getEntityHorizontalFacingDeg(entity.getOwner());
//                printInGameMsg(String.format("client pos x: %.3f, y: %.3f, z: %.3f", ownerPos.x(), ownerPos.y(), ownerPos.z() ));
                Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getOwner(), partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
                moveEntityToPos(entity, lerpVec);
//                entity.setIsPreviousClientPosExist(true);
//                entity.setPreviousPosX(lerpVec.x());
//                entity.setPreviousPosY(lerpVec.y());
//                entity.setPreviousPosZ(lerpVec.z());

//                moveEntityToPos(entity, Vector3fMethods.convertMCVec3fToVector3f(entity.getOwner().getPosition(partialTick)).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ()));
            }
        }

        poseStack.pushPose();
        if (getMinecraftInstance().getCameraEntity() != null) {
            Quaternionf rotatorY = new Quaternionf();
            QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(entityFacingAngle), 0f, -1.0f, 0f);

            if (entity.getEntityScale() != 1) {
                rotatorY.scale(entity.getEntityScale());
            }

            poseStack.mulPose(rotatorY);
        }

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);
//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);
//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);

        if (entity.getLifetime() > entity.getMaxLifeTime() - entity.getStartTotalFrame()) {
            this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);
        }
        else {
            this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);
        }

        poseStack.popPose();
    }
    @Override
    public Color getRenderColor(TwoDAnimatedTexRGB256Entity animatable, float partialTick, int packedLight) {
        if (animatable.isStartFrameFading() && animatable.getLifetime() > animatable.getMaxLifeTime() - animatable.getStartTotalFrame()) {
//            printInGameMsg("startAlpha: "+ ((animatable.getMaxLifetime() - animatable.getLifetime()) * (240f / animatable.getStartTotalFrame()) + 15));
            return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), (int) ( (animatable.getMaxLifeTime() - animatable.getLifetime()) * (240f / animatable.getStartTotalFrame()) ) + 15);
        }
        else {
            if (animatable.isEndFrameFading() && animatable.getLifetime() <= animatable.getEndTotalFrame()) {
//                printInGameMsg("endAlpha: " + (animatable.getLifetime() * (240f / animatable.getEndTotalFrame()) + 15) );
                return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), (int) (animatable.getLifetime() * (240f / animatable.getEndTotalFrame())) + 15);
            }
            else {
                return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), 255);
            }
        }
    }
}
