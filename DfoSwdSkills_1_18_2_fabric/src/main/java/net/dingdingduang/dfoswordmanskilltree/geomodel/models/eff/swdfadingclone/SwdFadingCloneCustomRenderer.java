package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;

import net.minecraft.util.math.*;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.block.BlockState;

import org.lwjgl.system.MemoryStack;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import modified.org.joml.Quaternionf;
import modified.org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.COLOR_1_OVER_255_PERCENT;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;

public class SwdFadingCloneCustomRenderer extends GeoProjectilesRenderer<SwdFadingCloneCustomEntity> {
//    private final float ItemLeftHandOffsetX = -4.0F;
    private final float ItemLeftHandOffsetX = 28F;
    private final float ItemLeftHandOffsetY = -4.5F;
    private final float ItemLeftHandOffsetZ = 0F;

    private final float ItemRightHandOffsetX = 4.0F;
    private final float ItemRightHandOffsetY = -4.5F;
    private final float ItemRightHandOffsetZ = 0F;

    private final float LeftHandPivotX = 12F;
    private final float RightHandPivotX = -12F;
//    private final float RightHandPivotY = 0F;
//    private final float RightHandPivotZ = 0F;

    private static HashMap<String, NbtCompound> ItemCompoundTagCache = new HashMap<String, NbtCompound>();

    public SwdFadingCloneCustomRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SwdFadingCloneCustomModel());

        this.shadowOpacity = 0.0f;
    }

    @Override
    public int getBlockLight(SwdFadingCloneCustomEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public Identifier getTextureLocation(SwdFadingCloneCustomEntity instance) {
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
//        String resID = instance.getTextureName() + targetFrame + DfoSwordmanSkillTreeConstants.PNG_POSTFIX;
////        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName() + targetFrame + ".png");
//        return GlobalClientMaps.getReusableResourceByStringID(resID);
        return DfoSwdGeneralGeoEntityResources.SWD_FADING_CLONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public boolean shouldRender(SwdFadingCloneCustomEntity pEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        Entity player = getClientPlayer();
        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityX(player), EntityMethods.getEntityY(player), EntityMethods.getEntityZ(player), EntityMethods.getEntityX(pEntity), EntityMethods.getEntityY(pEntity), EntityMethods.getEntityZ(pEntity)) <= 32f;
    }

    @Override
    public RenderLayer getRenderType(SwdFadingCloneCustomEntity animatable, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void render(SwdFadingCloneCustomEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        this.animatable = entity;
        //client move
        int currentLifeTime;
        if (entity.hasTargetLocXYZ() && (currentLifeTime = entity.getMaxLifetime() - entity.getLifetime()) <= entity.getTotalTicksThatRequiredToMoveToTargetLoc()) {
            float distPercent = ExtraMathMethods.clamp((currentLifeTime + partialTick) / entity.getTotalTicksThatRequiredToMoveToTargetLoc(), 0, 1);
            float newX = entity.getStartPosX() + (entity.getTargetPosX() - entity.getStartPosX()) * distPercent;
            float newY = entity.getStartPosY() + (entity.getTargetPosY() - entity.getStartPosY()) * distPercent;
            float newZ = entity.getStartPosZ() + (entity.getTargetPosZ() - entity.getStartPosZ()) * distPercent;
            moveEntityToXYZ(entity, newX, newY, newZ);
        }
        else if (entity.isBoundToEntity()) {
            if (entity.getOwner() != null) {
                Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getOwner(), partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
                moveEntityToPos(entity, lerpVec);
            }
        }
        poseStack.push();

        Quaternionf rotatorX = new Quaternionf();
        QuaternionHelper.QuatRotationByAxis(rotatorX, MathMethods.AngleToRadians(entity.getRotationX()), 1.0f, 0f, 0f);

        Quaternionf rotatorY = new Quaternionf();
        QuaternionHelper.QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorX);

        Quaternion mojangQuaternion = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
        poseStack.multiply(mojangQuaternion);
        float scale = entity.getEntityScale();
        if (scale != 1) {
            poseStack.scale(scale, scale, scale);
        }

        var predicate = new AnimationEvent<>(entity, 0, 0, partialTick,
                false, DfoSwdGeneralGeoEntityResources.EMPTY_ANIMATION_EXTRA_DATA);

        this.modelProvider.setCustomAnimations(entity, getInstanceId(entity), predicate);

        //this will update bone first
        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));

        RenderSystem.setShaderTexture(0, getTextureLocation(entity));

        if (entity.isRenderOffhandWeapon()) {
            Optional<GeoBone> tempLeftArmBone = model.getBone("left_arm");

            if (tempLeftArmBone.isPresent()) {
                GeoBone bone = tempLeftArmBone.get();

                ModelTransformation.Mode pDisplayContext = ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND;
                renderHoldingItemTranslate(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, entity.getOwnerOffhandWeaponStrID(), LeftHandPivotX, ItemLeftHandOffsetX, ItemLeftHandOffsetY, ItemLeftHandOffsetZ);
            }
        }
        if (entity.isRenderMainHandWeapon()) {
//        Optional<GeoBone> tempRightArmBone = this.getGeoModel().getBone("right_arm");
            Optional<GeoBone> tempRightArmBone = model.getBone("right_arm");

            if (tempRightArmBone.isPresent()) {
                GeoBone bone = tempRightArmBone.get();

                ModelTransformation.Mode pDisplayContext = ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND;
                renderHoldingItemTranslate(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, entity.getOwnerMainHandWeaponStrID(), RightHandPivotX, ItemRightHandOffsetX, ItemRightHandOffsetY, ItemRightHandOffsetZ);
            }
        }

        Color color = getRenderColor(entity);
        super.render(model, entity, partialTick, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, getPackedOverlay(entity, 0), color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);
        poseStack.pop();
    }

    public void renderHoldingItemTranslate(SwdFadingCloneCustomEntity entity, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, GeoBone bone, ModelTransformation.Mode pDisplayContext, String weaponNBTID, float rotateAroundX, float offsetX, float offsetY, float offsetZ) {
        poseStack.push();


    //        poseStack.translate( (bone.getPivotX() + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotY() + testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotZ() + testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16 );
    //        poseStack.translate( (bone.getPivotX() + ItemRightHandOffsetX) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotY()) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotZ() + ItemRightHandOffsetZ) * DfoSwordmanSkillTreeConstants.OneOver16 );

        poseStack.translate( (bone.getPivotX()) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotY()) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotZ()) * DfoSwordmanSkillTreeConstants.OneOver16 );


        //new
        renderHoldingItemApplyGeoTranslationAndRotation(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, rotateAroundX, offsetX, offsetY, offsetZ);
    //        finalRenderItemOnHand(entity, partialTick, poseStack, bufferSource, packedLight, bone);

        poseStack.pop();
    }

    public void renderHoldingItemApplyGeoTranslationAndRotation(SwdFadingCloneCustomEntity entity, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, GeoBone bone, ModelTransformation.Mode pDisplayContext, String weaponNBTID, float rotateAroundX, float offsetX, float offsetY, float offsetZ) {
        poseStack.push();

//        poseStack.translate( (bone.getPositionX() + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPositionY() + testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (-bone.getPositionZ() + testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16 );

//        poseStack.translate(bone.getPositionX() * DfoSwordmanSkillTreeConstants.OneOver16, bone.getPositionY() * DfoSwordmanSkillTreeConstants.OneOver16, -bone.getPositionZ() * DfoSwordmanSkillTreeConstants.OneOver16);
        poseStack.translate(bone.getPositionX() * DfoSwordmanSkillTreeConstants.OneOver16, bone.getPositionY() * DfoSwordmanSkillTreeConstants.OneOver16, -bone.getPositionZ() * DfoSwordmanSkillTreeConstants.OneOver16);

        renderHoldingItemApplyGeoTranslationAndRotation2(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, rotateAroundX, offsetX, offsetY, offsetZ);

        poseStack.pop();
    }

    public void renderHoldingItemApplyGeoTranslationAndRotation2(SwdFadingCloneCustomEntity entity, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, GeoBone bone, ModelTransformation.Mode pDisplayContext, String weaponNBTID, float rotateAroundX, float offsetX, float offsetY, float offsetZ) {
        poseStack.push();
        //holding item

//        poseStack.translate((testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16);

//        printInGameMsg(String.format("newXRot: %.3f, newYRot: %.3f, newZRot: %.3f", bone.getRotationX(), bone.getRotationY(), bone.getRotationZ()));
        if (bone.getRotationZ() != 0) {
//            poseStack.multiply(Axis.ZP.rotation(MathMethods.AngleToDegrees(bone.getRotationZ())));

            Quaternionf rotator = new Quaternionf();
            Vector3f AxisZN = new Vector3f(0, 0, -1);
            rotator.rotationAxis(bone.getRotationZ(), AxisZN);

//            poseStack.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);

            MatrixStack.Entry pose = poseStack.peek();
            Matrix4f poseMatrix4fTBR = pose.getPositionMatrix();
            Matrix3f poseNormalR = pose.getNormalMatrix();
            modified.org.joml.Matrix4f jomlPoseMatrix4fTBR = new modified.org.joml.Matrix4f();
            modified.org.joml.Matrix3f jomlPoseNormalR = new modified.org.joml.Matrix3f();

            //rotate around
            FloatBuffer tempFloatBuf1 = FloatBuffer.allocate(16);
            poseMatrix4fTBR.readColumnMajor(tempFloatBuf1);
            jomlPoseMatrix4fTBR.setMatrix4fByFloatBuffer(tempFloatBuf1);
            jomlPoseMatrix4fTBR.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);
            tempFloatBuf1 = jomlPoseMatrix4fTBR.getMatrix4fFloatBuffer();
            poseMatrix4fTBR.writeColumnMajor(tempFloatBuf1);

            //rotate
            FloatBuffer tempFloatBuf2 = FloatBuffer.allocate(9);
            poseNormalR.readColumnMajor(tempFloatBuf2);
            jomlPoseNormalR.setMatrix3fByFloatBuffer(tempFloatBuf2);
            jomlPoseNormalR.rotate(rotator);
            tempFloatBuf2 = jomlPoseNormalR.getMatrix3fFloatBuffer();
            poseNormalR.writeColumnMajor(tempFloatBuf2);
        }

        if (bone.getRotationY() != 0) {
//            poseStack.multiply(Axis.YP.rotation(MathMethods.AngleToDegrees(bone.getRotationY())));

            Quaternionf rotator = new Quaternionf();
            Vector3f AxisYP = new Vector3f(0, 1, 0);
            rotator.rotationAxis(bone.getRotationY(), AxisYP);
//            poseStack.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);

            MatrixStack.Entry pose = poseStack.peek();
            Matrix4f poseMatrix4fTBR = pose.getPositionMatrix();
            Matrix3f poseNormalR = pose.getNormalMatrix();
            modified.org.joml.Matrix4f jomlPoseMatrix4fTBR = new modified.org.joml.Matrix4f();
            modified.org.joml.Matrix3f jomlPoseNormalR = new modified.org.joml.Matrix3f();

            //rotate around
            FloatBuffer tempFloatBuf1 = FloatBuffer.allocate(16);
            poseMatrix4fTBR.readColumnMajor(tempFloatBuf1);
            jomlPoseMatrix4fTBR.setMatrix4fByFloatBuffer(tempFloatBuf1);
            jomlPoseMatrix4fTBR.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);
            tempFloatBuf1 = jomlPoseMatrix4fTBR.getMatrix4fFloatBuffer();
            poseMatrix4fTBR.writeColumnMajor(tempFloatBuf1);

            //rotate
            FloatBuffer tempFloatBuf2 = FloatBuffer.allocate(9);
            poseNormalR.readColumnMajor(tempFloatBuf2);
            jomlPoseNormalR.setMatrix3fByFloatBuffer(tempFloatBuf2);
            jomlPoseNormalR.rotate(rotator);
            tempFloatBuf2 = jomlPoseNormalR.getMatrix3fFloatBuffer();
            poseNormalR.writeColumnMajor(tempFloatBuf2);
        }

        if (bone.getRotationX() != 0) {
//            poseStack.multiply(Axis.XP.rotation(MathMethods.AngleToDegrees(bone.getRotationX())));

            Quaternionf rotator = new Quaternionf();
            Vector3f AxisXN = new Vector3f(-1, 0, 0);
            rotator.rotationAxis(bone.getRotationX(), AxisXN);

//            poseStack.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);

            MatrixStack.Entry pose = poseStack.peek();
            Matrix4f poseMatrix4fTBR = pose.getPositionMatrix();
            Matrix3f poseNormalR = pose.getNormalMatrix();
            modified.org.joml.Matrix4f jomlPoseMatrix4fTBR = new modified.org.joml.Matrix4f();
            modified.org.joml.Matrix3f jomlPoseNormalR = new modified.org.joml.Matrix3f();

            //rotate around
            FloatBuffer tempFloatBuf1 = FloatBuffer.allocate(16);
            poseMatrix4fTBR.readColumnMajor(tempFloatBuf1);
            jomlPoseMatrix4fTBR.setMatrix4fByFloatBuffer(tempFloatBuf1);
            jomlPoseMatrix4fTBR.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);
            tempFloatBuf1 = jomlPoseMatrix4fTBR.getMatrix4fFloatBuffer();
            poseMatrix4fTBR.writeColumnMajor(tempFloatBuf1);

            //rotate
            FloatBuffer tempFloatBuf2 = FloatBuffer.allocate(9);
            poseNormalR.readColumnMajor(tempFloatBuf2);
            jomlPoseNormalR.setMatrix3fByFloatBuffer(tempFloatBuf2);
            jomlPoseNormalR.rotate(rotator);
            tempFloatBuf2 = jomlPoseNormalR.getMatrix3fFloatBuffer();
            poseNormalR.writeColumnMajor(tempFloatBuf2);
        }

//        renderHoldingItemTranslate(entity, partialTick, poseStack, bufferSource, packedLight, bone);
        finalRenderItemOnHand(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, offsetX, offsetY, offsetZ);


        poseStack.pop();
    }

    public void finalRenderItemOnHand(SwdFadingCloneCustomEntity entity, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, GeoBone bone, ModelTransformation.Mode pDisplayContext, String weaponNBTID, float offsetX, float offsetY, float offsetZ) {
        poseStack.push();

        finalRenderItemOnHand2(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, offsetX, offsetY, offsetZ);

        poseStack.pop();
    }

//    public static int abc1 = 0;
//    public static int abc2 = 0;
    public void finalRenderItemOnHand2(SwdFadingCloneCustomEntity entity, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, GeoBone bone, ModelTransformation.Mode pDisplayContext, String weaponNBTID, float offsetX, float offsetY, float offsetZ) {
        poseStack.push();
//        poseStack.translate( (testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16 );

//        poseStack.translate(ItemRightHandOffsetX * DfoSwordmanSkillTreeConstants.OneOver16, ItemRightHandOffsetY * DfoSwordmanSkillTreeConstants.OneOver16, ItemRightHandOffsetZ * DfoSwordmanSkillTreeConstants.OneOver16);
//        poseStack.translate((ItemRightHandOffsetX + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY + ItemRightHandOffsetY) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ + ItemRightHandOffsetZ) * DfoSwordmanSkillTreeConstants.OneOver16);

        poseStack.translate(offsetX * DfoSwordmanSkillTreeConstants.OneOver16, offsetY * DfoSwordmanSkillTreeConstants.OneOver16, offsetZ * DfoSwordmanSkillTreeConstants.OneOver16);
//        poseStack.translate((testPressKeyTranslateRot.tempFloatNewX + offsetX) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY + offsetY) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ + offsetZ) * DfoSwordmanSkillTreeConstants.OneOver16);

        ItemRenderer tempItemRenderer = getMinecraftInstance().getItemRenderer();
        ItemModels itemModelShaper = tempItemRenderer.getModels();
        if (!ItemCompoundTagCache.containsKey(weaponNBTID)) {
            NbtCompound tempItemTag = new NbtCompound();
            tempItemTag.putString("id", weaponNBTID);
            ItemCompoundTagCache.put(weaponNBTID, tempItemTag);
        }
        //holding item
        ItemStack itemHolding = ItemStack.fromNbt(ItemCompoundTagCache.get(weaponNBTID));
        itemHolding.setCount(1);

//        if (entity.getOwner() instanceof LivingEntity livingEntityOwner) {
//            if (bone.getName().equals("right_arm")) {
//                itemHolding = livingEntityOwner.getMainHandStack();
//            }
//            else if (bone.getName().equals("left_arm")) {
//                itemHolding = livingEntityOwner.getOffHandStack();
//            }
//        }
        BakedModel itemModel = itemModelShaper.getModel(itemHolding);
//        BakedModel itemModel = itemModelShaper.getModelManager().getMissingModel();

//        BakedModel itemModel = getDupModel(itemModelShaper, itemHolding, (int) this.getInstanceId(entity));

//        tempItemRenderer.render(sword, ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, true, poseStack, bufferSource, packedLight, OverlayTexture.DEFAULT_UV, itemModel);

        int NO_OVERLAY = OverlayTexture.DEFAULT_UV;

//        if (entity.getOwner() instanceof LivingEntity livingEntityOwner) {
//            tempItemRenderer.renderStatic(livingEntityOwner, livingEntityOwner.getMainHandItem(), ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, false, poseStack, bufferSource, getClientLevel(), packedLight, NO_OVERLAY, 42);
//        }

        if (!itemHolding.isEmpty()) {
//            printInGameMsg("nbt tag: "+weaponNBTID.toString());
            poseStack.push();

            Quaternionf rotator;
            Quaternion mojangQuaternion;

            rotator = new Quaternionf();
            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(90), Vector3fMethods.getAxisZN());
            mojangQuaternion = new Quaternion(rotator.x, rotator.y, rotator.z, rotator.w);
            poseStack.multiply(mojangQuaternion);

            rotator = new Quaternionf();
            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(-45), Vector3fMethods.getAxisYP());
            mojangQuaternion = new Quaternion(rotator.x, rotator.y, rotator.z, rotator.w);
            poseStack.multiply(mojangQuaternion);

            rotator = new Quaternionf();
            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(-90), Vector3fMethods.getAxisXN());
            mojangQuaternion = new Quaternion(rotator.x, rotator.y, rotator.z, rotator.w);
            poseStack.multiply(mojangQuaternion);

            poseStack.translate(0, -0.5F, 0.5F);

//            tempItemRenderer.renderItem(itemHolding, pDisplayContext, packedLight, NO_OVERLAY, poseStack, bufferSource, 42);
//            tempItemRenderer.renderBakedItemModel(itemModel, itemHolding, packedLight, NO_OVERLAY, poseStack, ItemRenderer.getFoilBufferDirect(bufferSource, Sheets.translucentCullBlockSheet(), true, itemHolding.hasFoil()));
            tempRenderModelLists(entity, itemModel, itemHolding, packedLight, NO_OVERLAY, poseStack, ItemRenderer.getDirectItemGlintConsumer(bufferSource, TexturedRenderLayers.getEntityTranslucentCull(), true, itemHolding.hasGlint()));

            poseStack.pop();
        }

        poseStack.pop();
    }

    public void tempRenderModelLists(SwdFadingCloneCustomEntity entity, BakedModel pModel, ItemStack pStack, int pCombinedLight, int pCombinedOverlay, MatrixStack pMatrixStack, VertexConsumer pBuffer) {
        Random random = new Random();

        for(Direction direction : Direction.values()) {
            random.setSeed(42L);
            tempRenderQuadList(entity, pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, direction, random), pStack, pCombinedLight, pCombinedOverlay);
//            itemRenderer.renderQuadList(pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, direction, randomsource), pStack, pCombinedLight, pCombinedOverlay);
//            renderTempBakedItemQuads(pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, direction, random), pStack, pCombinedLight, pCombinedOverlay);
        }

        random.setSeed(42L);
        tempRenderQuadList(entity, pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, (Direction)null, random), pStack, pCombinedLight, pCombinedOverlay);
//        itemRenderer.renderQuadList(pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, (Direction)null, randomsource), pStack, pCombinedLight, pCombinedOverlay);
//        renderTempBakedItemQuads(pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, null, random), pStack, pCombinedLight, pCombinedOverlay);
    }

//    public void renderTempBakedItemQuads(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, ItemStack stack, int light, int overlay) {
//        boolean bl = !stack.isEmpty();
//        MatrixStack.Entry entry = matrices.peek();
//
//        for(BakedQuad bakedQuad : quads) {
//            int i = -1;
//
//            float f = (float)(i >> 16 & 255) / 255.0F;
//            float g = (float)(i >> 8 & 255) / 255.0F;
//            float h = (float)(i & 255) / 255.0F;
//            vertices.quad(entry, bakedQuad, f, g, h, light, overlay);
//        }
//    }

    public void tempRenderQuadList(SwdFadingCloneCustomEntity entity, MatrixStack pPoseStack, VertexConsumer pBuffer, List<BakedQuad> pQuads, ItemStack pItemStack, int pCombinedLight, int pCombinedOverlay) {
        pPoseStack.push();
        MatrixStack.Entry pose = pPoseStack.peek();

        for(BakedQuad bakedquad : pQuads) {
            int i = -1;

            float f = (float)(i >> 16 & 255) / 255F;
            float f1 = (float)(i >> 8 & 255) / 255F;
            float f2 = (float)(i & 255) / 255F;

//            pBuffer.putBulkData(pose, bakedquad, f, f1, f2, 1.0F, pCombinedLight, pCombinedOverlay, true);


            float alpha;

            if (entity.isStartFrameFading() && entity.getLifetime() > entity.getMaxLifetime() - entity.getStartTotalFrame()) {
                alpha = ((entity.getMaxLifetime() - entity.getLifetime()) * ((float) Math.max(0, entity.getColorAlpha()) / entity.getStartTotalFrame()) + 15) * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL;
            }
            else {
                if (entity.isEndFrameFading() && entity.getLifetime() <= entity.getEndTotalFrame()) {
                    alpha = ((entity.getLifetime() * ((float) Math.max(0, entity.getColorAlpha()) / entity.getEndTotalFrame())) + 15f) * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL;
                }
                else {
                    alpha = entity.getColorAlpha() * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL;
                }
            }

            tempQuad(pBuffer, pose, bakedquad, DfoSwordmanSkillTreeConstants.QuadDefaultMaxBrightness, f, f1, f2, alpha, new int[]{pCombinedLight, pCombinedLight, pCombinedLight, pCombinedLight}, pCombinedOverlay, true);
//            pBuffer.quad(pose, bakedquad, new float[]{1.0F, 1.0F, 1.0F, 1.0F}, f, f1, f2, alpha, new int[]{pCombinedLight, pCombinedLight, pCombinedLight, pCombinedLight}, pCombinedOverlay, true);
        }
        pPoseStack.pop();
    }

    public void tempQuad(VertexConsumer pBuffer, MatrixStack.Entry matrixEntry, BakedQuad quad, float[] brightnesses, float red, float green, float blue, float alpha, int[] lights, int overlay, boolean useQuadColorData) {
        float[] fs = new float[]{brightnesses[0], brightnesses[1], brightnesses[2], brightnesses[3]};
        int[] is = new int[]{lights[0], lights[1], lights[2], lights[3]};
        int[] js = quad.getVertexData();
        Vec3i vec3i = quad.getFace().getVector();
        Vec3f vec3f = new Vec3f((float) vec3i.getX(), (float) vec3i.getY(), (float) vec3i.getZ());
        Matrix4f matrix4f = matrixEntry.getPositionMatrix();
        Matrix3f matrix3f = matrixEntry.getNormalMatrix();
        vec3f.transform(matrix3f);
        int vertexSize = VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL.getVertexSizeInteger();
        int j = js.length / vertexSize;

        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            ByteBuffer byteBuffer = memoryStack.malloc(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL.getVertexSize());
            IntBuffer intBuffer = byteBuffer.asIntBuffer();

            for(int k = 0; k < j; ++k) {
                intBuffer.clear();
                intBuffer.put(js, k * 8, 8);
                float f = byteBuffer.getFloat(0);
                float g = byteBuffer.getFloat(4);
                float h = byteBuffer.getFloat(8);
                float o;
                float p;
                float q;
                float finalAlpha;
                if (useQuadColorData) {
                    float l = (float) (byteBuffer.get(12) & 255) / 255F;
                    float m = (float) (byteBuffer.get(13) & 255) / 255F;
                    float n = (float) (byteBuffer.get(14) & 255) / 255F;
                    float byteBufferAlpha = (float) (byteBuffer.get(15) & 255) / 255F;
                    o = l * fs[k] * red;
                    p = m * fs[k] * green;
                    q = n * fs[k] * blue;
                    finalAlpha = byteBufferAlpha * alpha;
                }
                else {
                    o = fs[k] * red;
                    p = fs[k] * green;
                    q = fs[k] * blue;
                    finalAlpha = alpha;
                }

                int r = is[k];
                float m = byteBuffer.getFloat(16);
                float n = byteBuffer.getFloat(20);
                Vector4f vector4f = new Vector4f(f, g, h, 1.0F);
                vector4f.transform(matrix4f);
                applyBakedNormals(vec3f, byteBuffer, matrix3f);
                pBuffer.vertex(vector4f.getX(), vector4f.getY(), vector4f.getZ(), o, p, q, finalAlpha, m, n, overlay, r, vec3f.getX(), vec3f.getY(), vec3f.getZ());
            }
        }
    }

    public void applyBakedNormals(Vec3f generated, ByteBuffer data, Matrix3f normalTransform) {
        byte nx = data.get(28);
        byte ny = data.get(29);
        byte nz = data.get(30);

        if (nx != 0 || ny != 0 || nz != 0) {
            generated.set((float) nx / 127F, (float) ny / 127F, (float) nz / 127F);
            generated.transform(normalTransform);
        }
    }

    public Color getRenderColor(SwdFadingCloneCustomEntity animatable) {
        if (animatable.isStartFrameFading() && animatable.getLifetime() > animatable.getMaxLifetime() - animatable.getStartTotalFrame()) {
            return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), (animatable.getMaxLifetime() - animatable.getLifetime()) * (Math.max(0, animatable.getColorAlpha()) / animatable.getStartTotalFrame()) + 15);
        }
        else {
            if (animatable.isEndFrameFading() && animatable.getLifetime() <= animatable.getEndTotalFrame()) {
                return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), animatable.getLifetime() * (Math.max(0, animatable.getColorAlpha()) / animatable.getEndTotalFrame()) + 15);
            }
            else {
                return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), animatable.getColorAlpha());
            }
        }
    }
}
