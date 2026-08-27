package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.customtotalframesonly;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import modified.org.joml.Quaternionf;
import modified.org.joml.Vector3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import net.minecraft.util.math.Quaternion;
import net.minecraft.client.render.OverlayTexture;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import java.util.Collections;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.COLOR_1_OVER_255_PERCENT;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.printInGameMsg;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class SwordSlashCustomTotalFrameOnlyRenderer extends GeoProjectilesRenderer<SwordSlashCustomTotalFrameOnlyEntity> {
    public SwordSlashCustomTotalFrameOnlyRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SwordSlashCustomTotalFrameOnlyModel());
        this.shadowOpacity = 0.0f;
    }

    @Override
    public Identifier getTextureLocation(SwordSlashCustomTotalFrameOnlyEntity instance) {
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName()+
//                "_"+(instance.getMaxLifetime() - instance.getLifetime())
//                +".png");
        if (instance.getClientTotalFrameIndexHelper() == null) {
//            ArrayList<Integer> tempIntArrayList = new ArrayList<>();
//            for (int i = instance.getMaxLifetime(); i > 0; i--) {
//
//            }
//            int[] tempIndexHelperArr = tempIntArrayList.stream().filter(i -> i != null).mapToInt(i -> i).toArray();
//            instance.setClientTotalFrameIndexHelper(tempIndexHelperArr);

            int totalTickTime = instance.getMaxLifetime();
            int totalFrame = instance.getTotalFrameCount();
            int[] tempIndexHelperArr = new int[totalTickTime];
            float roundNum = 1.0f * totalTickTime / totalFrame;

            for (int i = totalTickTime; i >= 1; i--) {
                tempIndexHelperArr[totalTickTime - i] = (int) (totalFrame - (i / roundNum));
            }

            instance.setClientTotalFrameIndexHelper(tempIndexHelperArr);
        }

//        int targetFrame;
//        //start frame tex path
//        if (instance.getLifetime() > instance.getMaxLifetime() - instance.getStartTotalFrame()) {
//            targetFrame = instance.getMaxLifetime() - instance.getLifetime();
//        } //end frame tex path
//        else if (instance.getLifetime() <= instance.getEndTotalFrame()) {
//            targetFrame = instance.getStartTotalFrame() + instance.getLoopTotalFrame() + instance.getEndTotalFrame() - instance.getLifetime();
//        }
//        else {
//            int clientLoopFrameCounter = instance.getClientLoopFrameCounter();
//            targetFrame = instance.getStartTotalFrame() + clientLoopFrameCounter;
//            instance.setClientLoopFrameCounter( (clientLoopFrameCounter + 1) % instance.getLoopTotalFrame());
//        }
//
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName() + targetFrame + ".png");
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName() + instance.getClientTotalFrameIndexHelper()[instance.getMaxLifetime() - instance.getLifetime()] + DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
    }

    @Override
    public RenderLayer getRenderType(SwordSlashCustomTotalFrameOnlyEntity animatable, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void render(SwordSlashCustomTotalFrameOnlyEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        this.animatable = entity;
        //client move
        float entityFacingAngle = EntityMethods.getEntityHorizontalFacingDeg(entity);
        if (entity.hasTargetLocXYZ()) {
            float distPercent = (entity.getMaxLifetime() - entity.getLifetime() + partialTick) / entity.getMaxLifetime();
            float newX = entity.getStartPosX() + (entity.getTargetPosX() - entity.getStartPosX()) * distPercent;
            float newY = entity.getStartPosY() + (entity.getTargetPosY() - entity.getStartPosY()) * distPercent;
            float newZ = entity.getStartPosZ() + (entity.getTargetPosZ() - entity.getStartPosZ()) * distPercent;
            EntityMethods.moveEntityToXYZ(entity, newX, newY, newZ);
        }
        else if (entity.isBoundToEntity()) {
            if (entity.getOwner() != null) {
                if (entity.isAlwaysFaceToOwnerHorizontalFacingAngle()) {
                    entityFacingAngle = EntityMethods.getEntityHorizontalFacingDeg(entity.getOwner());
                }
//                printInGameMsg(String.format("client pos x: %.3f, y: %.3f, z: %.3f", ownerPos.x(), ownerPos.y(), ownerPos.z() ));
                Vector3f lerpVec = EntityMethods.getEntityClientPosWithPartialTicks(entity.getOwner(), partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
                EntityMethods.moveEntityToPos(entity, lerpVec);
//                entity.setIsPreviousClientPosExist(true);
//                entity.setPreviousPosX(lerpVec.x());
//                entity.setPreviousPosY(lerpVec.y());
//                entity.setPreviousPosZ(lerpVec.z());

//                moveEntityToPos(entity, Vector3fMethods.convertMCVec3fToVector3f(entity.getOwner().getPosition(partialTick)).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ()));
            }
        }
        poseStack.push();
        //rotate x,
        float entityVerticalDeg = EntityMethods.getEntityVerticalFacingDeg(entity);
        if (entityVerticalDeg != 0) {
            Quaternionf rotatorX = new Quaternionf();
            QuatRotationByAxis(rotatorX, AngleToRadians(entityVerticalDeg), 1.0f, 0f, 0f);
            Quaternion mojangQuaternionX = new Quaternion(rotatorX.x, rotatorX.y, rotatorX.z, rotatorX.w);
        poseStack.multiply(mojangQuaternionX);
        }

        // rotate Z Y
        poseStack.push();
        Quaternionf rotatorZ = new Quaternionf();
        QuatRotationByAxis(rotatorZ, AngleToRadians(entity.getRotationZ()), 0f, 0f, 1.0f);

        Quaternionf rotatorY = new Quaternionf();
//        rotatorY.rotationAxis(AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
//        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        QuatRotationByAxis(rotatorY, AngleToRadians(entityFacingAngle), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorZ);
        //rotatorZ mul rotatorY and rotatorY mul rotatorZ are different, rotatorY mul rotatorZ better

        Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
        poseStack.multiply(mojangQuaternionY);
        float scale = entity.getEntityScale();
        if (scale != 1) {
            poseStack.scale(scale, scale, scale);
        }

        var predicate = new AnimationEvent<>(entity, 0, 0, partialTick,
                false, Collections.singletonList(new EntityModelData()));

        this.modelProvider.setCustomAnimations(entity, getInstanceId(entity), predicate);
        RenderSystem.setShaderTexture(0, getTextureLocation(entity));

        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
        Color color = getRenderColor(entity);
        super.render(model, entity, partialTick, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.DEFAULT_UV, color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

        poseStack.pop();

        poseStack.pop();
    }


    public Color getRenderColor(SwordSlashCustomTotalFrameOnlyEntity animatable) {
//        return Color.WHITE;
        if (animatable.isStartFrameFading() && animatable.getLifetime() > animatable.getMaxLifetime() - animatable.getStartTotalFrame()) {
//            printInGameMsg("startAlpha: "+ ((animatable.getMaxLifetime() - animatable.getLifetime()) * (240f / animatable.getStartTotalFrame()) + 15));
            return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), (int) ( (animatable.getMaxLifetime() - animatable.getLifetime()) * (240f / animatable.getStartTotalFrame()) ) + 15);
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
