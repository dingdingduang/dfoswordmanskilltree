package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.client.RenderProperties;
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

import java.nio.FloatBuffer;
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

    private static HashMap<String, CompoundTag> ItemCompoundTagCache = new HashMap<String, CompoundTag>();

    public SwdFadingCloneCustomRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SwdFadingCloneCustomModel());

        this.shadowRadius = 0.0f;
    }

    @Override
    public int getBlockLightLevel(SwdFadingCloneCustomEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(SwdFadingCloneCustomEntity instance) {
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
    public RenderType getRenderType(SwdFadingCloneCustomEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void render(SwdFadingCloneCustomEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
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
        poseStack.pushPose();

        Quaternionf rotatorX = new Quaternionf();
        QuaternionHelper.QuatRotationByAxis(rotatorX, MathMethods.AngleToRadians(entity.getRotationX()), 1.0f, 0f, 0f);

        Quaternionf rotatorY = new Quaternionf();
        QuaternionHelper.QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorX);

        Quaternion mojangQuaternion = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
        poseStack.mulPose(mojangQuaternion);
        float scale = entity.getEntityScale();
        if (scale != 1) {
            poseStack.scale(scale, scale, scale);
        }

        var predicate = new AnimationEvent<>(entity, 0, 0, partialTick,
                false, Collections.singletonList(new EntityModelData()));

        this.modelProvider.setCustomAnimations(entity, getInstanceId(entity), predicate);

        RenderSystem.setShaderTexture(0, getTextureLocation(entity));

        //this will update bone first
        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
        Color color = getRenderColor(entity);
        super.render(model, entity, partialTick, RenderType.entityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, getPackedOverlay(entity, 0), color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

        if (entity.isRenderOffhandWeapon()) {
            Optional<GeoBone> tempLeftArmBone = model.getBone("left_arm");

            if (tempLeftArmBone.isPresent()) {
                GeoBone bone = tempLeftArmBone.get();

                ItemTransforms.TransformType pDisplayContext = ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND;
                renderHoldingItemTranslate(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, entity.getOwnerOffhandWeaponStrID(), LeftHandPivotX, ItemLeftHandOffsetX, ItemLeftHandOffsetY, ItemLeftHandOffsetZ);
            }
        }
        if (entity.isRenderMainHandWeapon()) {
//        Optional<GeoBone> tempRightArmBone = this.getGeoModel().getBone("right_arm");
            Optional<GeoBone> tempRightArmBone = model.getBone("right_arm");

            if (tempRightArmBone.isPresent()) {
                GeoBone bone = tempRightArmBone.get();

                ItemTransforms.TransformType pDisplayContext = ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
                renderHoldingItemTranslate(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, entity.getOwnerMainHandWeaponStrID(), RightHandPivotX, ItemRightHandOffsetX, ItemRightHandOffsetY, ItemRightHandOffsetZ);
            }
        }

        poseStack.popPose();
    }

    public void renderHoldingItemTranslate(SwdFadingCloneCustomEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, GeoBone bone, ItemTransforms.TransformType pDisplayContext, String weaponNBTID, float rotateAroundX, float offsetX, float offsetY, float offsetZ) {
        poseStack.pushPose();


    //        poseStack.translate( (bone.getPivotX() + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotY() + testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotZ() + testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16 );
    //        poseStack.translate( (bone.getPivotX() + ItemRightHandOffsetX) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotY()) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotZ() + ItemRightHandOffsetZ) * DfoSwordmanSkillTreeConstants.OneOver16 );

        poseStack.translate( (bone.getPivotX()) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotY()) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotZ()) * DfoSwordmanSkillTreeConstants.OneOver16 );


        //new
        renderHoldingItemApplyGeoTranslationAndRotation(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, rotateAroundX, offsetX, offsetY, offsetZ);
    //        finalRenderItemOnHand(entity, partialTick, poseStack, bufferSource, packedLight, bone);

        poseStack.popPose();
    }

    public void renderHoldingItemApplyGeoTranslationAndRotation(SwdFadingCloneCustomEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, GeoBone bone, ItemTransforms.TransformType pDisplayContext, String weaponNBTID, float rotateAroundX, float offsetX, float offsetY, float offsetZ) {
        poseStack.pushPose();

//        poseStack.translate( (bone.getPositionX() + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPositionY() + testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (-bone.getPositionZ() + testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16 );

//        poseStack.translate(bone.getPositionX() * DfoSwordmanSkillTreeConstants.OneOver16, bone.getPositionY() * DfoSwordmanSkillTreeConstants.OneOver16, -bone.getPositionZ() * DfoSwordmanSkillTreeConstants.OneOver16);
        poseStack.translate(bone.getPositionX() * DfoSwordmanSkillTreeConstants.OneOver16, bone.getPositionY() * DfoSwordmanSkillTreeConstants.OneOver16, -bone.getPositionZ() * DfoSwordmanSkillTreeConstants.OneOver16);

        renderHoldingItemApplyGeoTranslationAndRotation2(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, rotateAroundX, offsetX, offsetY, offsetZ);

        poseStack.popPose();
    }

    public void renderHoldingItemApplyGeoTranslationAndRotation2(SwdFadingCloneCustomEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, GeoBone bone, ItemTransforms.TransformType pDisplayContext, String weaponNBTID, float rotateAroundX, float offsetX, float offsetY, float offsetZ) {
        poseStack.pushPose();
        //holding item

//        poseStack.translate((testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16);

//        printInGameMsg(String.format("newXRot: %.3f, newYRot: %.3f, newZRot: %.3f", bone.getRotationX(), bone.getRotationY(), bone.getRotationZ()));
        if (bone.getRotationZ() != 0) {
//            poseStack.mulPose(Axis.ZP.rotation(MathMethods.AngleToDegrees(bone.getRotationZ())));

            Quaternionf rotator = new Quaternionf();
            Vector3f AxisZN = new Vector3f(0, 0, -1);
            rotator.rotationAxis(bone.getRotationZ(), AxisZN);

//            poseStack.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);

            PoseStack.Pose pose = poseStack.last();
            Matrix4f poseMatrix4fTBR = pose.pose();
            Matrix3f poseNormalR = pose.normal();
            modified.org.joml.Matrix4f jomlPoseMatrix4fTBR = new modified.org.joml.Matrix4f();
            modified.org.joml.Matrix3f jomlPoseNormalR = new modified.org.joml.Matrix3f();

            //rotate around
            FloatBuffer tempFloatBuf1 = FloatBuffer.allocate(16);
            poseMatrix4fTBR.store(tempFloatBuf1);
            jomlPoseMatrix4fTBR.setMatrix4fByFloatBuffer(tempFloatBuf1);
            jomlPoseMatrix4fTBR.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);
            tempFloatBuf1 = jomlPoseMatrix4fTBR.getMatrix4fFloatBuffer();
            poseMatrix4fTBR.load(tempFloatBuf1);

            //rotate
            FloatBuffer tempFloatBuf2 = FloatBuffer.allocate(9);
            poseNormalR.store(tempFloatBuf2);
            jomlPoseNormalR.setMatrix3fByFloatBuffer(tempFloatBuf2);
            jomlPoseNormalR.rotate(rotator);
            tempFloatBuf2 = jomlPoseNormalR.getMatrix3fFloatBuffer();
            poseNormalR.load(tempFloatBuf2);
        }

        if (bone.getRotationY() != 0) {
//            poseStack.mulPose(Axis.YP.rotation(MathMethods.AngleToDegrees(bone.getRotationY())));

            Quaternionf rotator = new Quaternionf();
            Vector3f AxisYP = new Vector3f(0, 1, 0);
            rotator.rotationAxis(bone.getRotationY(), AxisYP);
//            poseStack.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);

            PoseStack.Pose pose = poseStack.last();
            Matrix4f poseMatrix4fTBR = pose.pose();
            Matrix3f poseNormalR = pose.normal();
            modified.org.joml.Matrix4f jomlPoseMatrix4fTBR = new modified.org.joml.Matrix4f();
            modified.org.joml.Matrix3f jomlPoseNormalR = new modified.org.joml.Matrix3f();

            //rotate around
            FloatBuffer tempFloatBuf1 = FloatBuffer.allocate(16);
            poseMatrix4fTBR.store(tempFloatBuf1);
            jomlPoseMatrix4fTBR.setMatrix4fByFloatBuffer(tempFloatBuf1);
            jomlPoseMatrix4fTBR.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);
            tempFloatBuf1 = jomlPoseMatrix4fTBR.getMatrix4fFloatBuffer();
            poseMatrix4fTBR.load(tempFloatBuf1);

            //rotate
            FloatBuffer tempFloatBuf2 = FloatBuffer.allocate(9);
            poseNormalR.store(tempFloatBuf2);
            jomlPoseNormalR.setMatrix3fByFloatBuffer(tempFloatBuf2);
            jomlPoseNormalR.rotate(rotator);
            tempFloatBuf2 = jomlPoseNormalR.getMatrix3fFloatBuffer();
            poseNormalR.load(tempFloatBuf2);
        }

        if (bone.getRotationX() != 0) {
//            poseStack.mulPose(Axis.XP.rotation(MathMethods.AngleToDegrees(bone.getRotationX())));

            Quaternionf rotator = new Quaternionf();
            Vector3f AxisXN = new Vector3f(-1, 0, 0);
            rotator.rotationAxis(bone.getRotationX(), AxisXN);

//            poseStack.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);

            PoseStack.Pose pose = poseStack.last();
            Matrix4f poseMatrix4fTBR = pose.pose();
            Matrix3f poseNormalR = pose.normal();
            modified.org.joml.Matrix4f jomlPoseMatrix4fTBR = new modified.org.joml.Matrix4f();
            modified.org.joml.Matrix3f jomlPoseNormalR = new modified.org.joml.Matrix3f();

            //rotate around
            FloatBuffer tempFloatBuf1 = FloatBuffer.allocate(16);
            poseMatrix4fTBR.store(tempFloatBuf1);
            jomlPoseMatrix4fTBR.setMatrix4fByFloatBuffer(tempFloatBuf1);
            jomlPoseMatrix4fTBR.rotateAround(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);
            tempFloatBuf1 = jomlPoseMatrix4fTBR.getMatrix4fFloatBuffer();
            poseMatrix4fTBR.load(tempFloatBuf1);

            //rotate
            FloatBuffer tempFloatBuf2 = FloatBuffer.allocate(9);
            poseNormalR.store(tempFloatBuf2);
            jomlPoseNormalR.setMatrix3fByFloatBuffer(tempFloatBuf2);
            jomlPoseNormalR.rotate(rotator);
            tempFloatBuf2 = jomlPoseNormalR.getMatrix3fFloatBuffer();
            poseNormalR.load(tempFloatBuf2);
        }

//        renderHoldingItemTranslate(entity, partialTick, poseStack, bufferSource, packedLight, bone);
        finalRenderItemOnHand(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, offsetX, offsetY, offsetZ);


        poseStack.popPose();
    }

    public void finalRenderItemOnHand(SwdFadingCloneCustomEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, GeoBone bone, ItemTransforms.TransformType pDisplayContext, String weaponNBTID, float offsetX, float offsetY, float offsetZ) {
        poseStack.pushPose();

        finalRenderItemOnHand2(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, offsetX, offsetY, offsetZ);

        poseStack.popPose();
    }

//    public static int abc1 = 0;
//    public static int abc2 = 0;
    public void finalRenderItemOnHand2(SwdFadingCloneCustomEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, GeoBone bone, ItemTransforms.TransformType pDisplayContext, String weaponNBTID, float offsetX, float offsetY, float offsetZ) {
        poseStack.pushPose();
//        poseStack.translate( (testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16 );

//        poseStack.translate(ItemRightHandOffsetX * DfoSwordmanSkillTreeConstants.OneOver16, ItemRightHandOffsetY * DfoSwordmanSkillTreeConstants.OneOver16, ItemRightHandOffsetZ * DfoSwordmanSkillTreeConstants.OneOver16);
//        poseStack.translate((ItemRightHandOffsetX + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY + ItemRightHandOffsetY) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ + ItemRightHandOffsetZ) * DfoSwordmanSkillTreeConstants.OneOver16);

        poseStack.translate(offsetX * DfoSwordmanSkillTreeConstants.OneOver16, offsetY * DfoSwordmanSkillTreeConstants.OneOver16, offsetZ * DfoSwordmanSkillTreeConstants.OneOver16);
//        poseStack.translate((testPressKeyTranslateRot.tempFloatNewX + offsetX) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY + offsetY) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ + offsetZ) * DfoSwordmanSkillTreeConstants.OneOver16);

        ItemRenderer tempItemRenderer = getMinecraftInstance().getItemRenderer();
        ItemModelShaper itemModelShaper = tempItemRenderer.getItemModelShaper();
        if (!ItemCompoundTagCache.containsKey(weaponNBTID)) {
            CompoundTag tempItemTag = new CompoundTag();
            tempItemTag.putString("id", weaponNBTID);
            ItemCompoundTagCache.put(weaponNBTID, tempItemTag);
        }
        //holding item
        ItemStack itemHolding = ItemStack.of(ItemCompoundTagCache.get(weaponNBTID));
        itemHolding.setCount(1);

        if (entity.getOwner() instanceof LivingEntity livingEntityOwner) {
            if (bone.getName().equals("right_arm")) {
                itemHolding = livingEntityOwner.getMainHandItem();
            }
            else if (bone.getName().equals("left_arm")) {
                itemHolding = livingEntityOwner.getOffhandItem();
            }
        }
        BakedModel itemModel = itemModelShaper.getItemModel(itemHolding);

//        BakedModel itemModel = getDupModel(itemModelShaper, itemHolding, (int) this.getInstanceId(entity));

//        tempItemRenderer.render(sword, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, true, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY, itemModel);

        int NO_OVERLAY = OverlayTexture.NO_OVERLAY;

//        if (entity.getOwner() instanceof LivingEntity livingEntityOwner) {
//            tempItemRenderer.renderStatic(livingEntityOwner, livingEntityOwner.getMainHandItem(), ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false, poseStack, bufferSource, getClientLevel(), packedLight, NO_OVERLAY, 42);
//        }

        if (!itemHolding.isEmpty()) {
            poseStack.pushPose();

            Quaternionf rotator;
            Quaternion mojangQuaternion;

            rotator = new Quaternionf();
            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(90), Vector3fMethods.getAxisZN());
            mojangQuaternion = new Quaternion(rotator.x, rotator.y, rotator.z, rotator.w);
            poseStack.mulPose(mojangQuaternion);

            rotator = new Quaternionf();
            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(-45), Vector3fMethods.getAxisYP());
            mojangQuaternion = new Quaternion(rotator.x, rotator.y, rotator.z, rotator.w);
            poseStack.mulPose(mojangQuaternion);

            rotator = new Quaternionf();
            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(-90), Vector3fMethods.getAxisXN());
            mojangQuaternion = new Quaternion(rotator.x, rotator.y, rotator.z, rotator.w);
            poseStack.mulPose(mojangQuaternion);

            poseStack.translate(0, -0.5F, 0.5F);
            if (!itemModel.isCustomRenderer() && (!itemHolding.is(Items.TRIDENT))) {
//                boolean isFabulous;
//                if (!pDisplayContext.firstPerson() && itemHolding.getItem() instanceof BlockItem) {
//                    Block block = ((BlockItem) itemHolding.getItem()).getBlock();
//                    isFabulous = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
//                } else {
//                    isFabulous = true;
//                }

//                for(BakedModel model : itemModel.getRenderPasses(itemHolding, isFabulous)) {
//                    for(RenderType rendertype : model.getRenderTypes(itemHolding, isFabulous)) {
//                        VertexConsumer vertexconsumer;
//                        if (isFabulous) {
//                            vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferSource, rendertype, true, itemHolding.hasFoil());
//                        } else {
//                            vertexconsumer = ItemRenderer.getFoilBuffer(bufferSource, rendertype, true, itemHolding.hasFoil());
//                        }
//
//                        tempItemRenderer.renderModelLists(model, itemHolding, packedLight, NO_OVERLAY, poseStack, vertexconsumer);
////                        tempRenderModelLists(tempItemRenderer, model, itemHolding, packedLight, NO_OVERLAY, poseStack, vertexconsumer);
//                    }
//                }

//                tempItemRenderer.renderModelLists(itemModel, itemHolding, packedLight, NO_OVERLAY, poseStack, ItemRenderer.getFoilBufferDirect(bufferSource, Sheets.translucentCullBlockSheet(), true, itemHolding.hasFoil()));
                tempRenderModelLists(entity, itemModel, itemHolding, packedLight, NO_OVERLAY, poseStack, ItemRenderer.getFoilBufferDirect(bufferSource, Sheets.translucentCullBlockSheet(), true, itemHolding.hasFoil()));
            }
            else {
                RenderProperties.get(itemHolding).getItemStackRenderer().renderByItem(itemHolding, pDisplayContext, poseStack, bufferSource, packedLight, NO_OVERLAY);
            }

            poseStack.popPose();
        }

        poseStack.popPose();
    }

    public void tempRenderModelLists(SwdFadingCloneCustomEntity entity, BakedModel pModel, ItemStack pStack, int pCombinedLight, int pCombinedOverlay, PoseStack pMatrixStack, VertexConsumer pBuffer) {
        Random random = new Random();

        for(Direction direction : Direction.values()) {
            random.setSeed(42L);
            tempRenderQuadList(entity, pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, direction, random), pStack, pCombinedLight, pCombinedOverlay);
//            itemRenderer.renderQuadList(pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, direction, randomsource), pStack, pCombinedLight, pCombinedOverlay);
        }

        random.setSeed(42L);
        tempRenderQuadList(entity, pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, (Direction)null, random), pStack, pCombinedLight, pCombinedOverlay);
//        itemRenderer.renderQuadList(pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, (Direction)null, randomsource), pStack, pCombinedLight, pCombinedOverlay);
    }

    public void tempRenderQuadList(SwdFadingCloneCustomEntity entity, PoseStack pPoseStack, VertexConsumer pBuffer, List<BakedQuad> pQuads, ItemStack pItemStack, int pCombinedLight, int pCombinedOverlay) {
        pPoseStack.pushPose();
        PoseStack.Pose posestack$pose = pPoseStack.last();

        for(BakedQuad bakedquad : pQuads) {
            int i = -1;

            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;

//            pBuffer.putBulkData(posestack$pose, bakedquad, f, f1, f2, 1.0F, pCombinedLight, pCombinedOverlay, true);


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

            pBuffer.putBulkData(posestack$pose, bakedquad, f, f1, f2, alpha, pCombinedLight, pCombinedOverlay, true);
        }
        pPoseStack.popPose();
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
