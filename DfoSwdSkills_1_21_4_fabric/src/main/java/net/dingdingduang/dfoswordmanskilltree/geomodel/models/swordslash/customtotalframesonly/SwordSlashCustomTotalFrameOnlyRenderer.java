package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.customtotalframesonly;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class SwordSlashCustomTotalFrameOnlyRenderer extends GeoEntityRenderer<SwordSlashCustomTotalFrameOnlyEntity> {
    public SwordSlashCustomTotalFrameOnlyRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SwordSlashCustomTotalFrameOnlyModel());
        this.shadowOpacity = 0.0f;
    }

    @Override
    public Identifier getTextureLocation(SwordSlashCustomTotalFrameOnlyEntity instance) {
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName()+
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
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName() + targetFrame + ".png");
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName() + instance.getClientTotalFrameIndexHelper()[instance.getMaxLifetime() - instance.getLifetime()] + DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName() + instance.getClientTotalFrameIndexHelper()[instance.getMaxLifetime() - instance.getLifetime()] + ".png");
    }

    @Override
    public RenderLayer getRenderType(SwordSlashCustomTotalFrameOnlyEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EntityRenderState entityRenderState, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        SwordSlashCustomTotalFrameOnlyEntity entity = this.animatable;
        //client move
        float entityFacingAngle = EntityMethods.getEntityHorizontalFacingDeg(entity);
        if (entity.hasTargetLocXYZ()) {
            float distPercent = (entity.getMaxLifetime() - entity.getLifetime() + this.partialTick) / entity.getMaxLifetime();
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
                Vector3f lerpVec = EntityMethods.getEntityClientPosWithPartialTicks(entity.getOwner(), this.partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
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
            poseStack.multiply(rotatorX);
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
            this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, this.partialTick, packedLight);
        }

        poseStack.pop();

        poseStack.pop();
    }


    @Override
    public Color getRenderColor(SwordSlashCustomTotalFrameOnlyEntity animatable, float partialTick, int packedLight) {
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
