package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Identifier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;

import java.util.Map;
import java.util.Optional;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;

public class SwdFadingCloneCustomRenderer extends GeoEntityRenderer<SwdFadingCloneCustomEntity> {
//    private final float ItemLeftHandOffsetX = 28F;
//    private final float ItemLeftHandOffsetY = -4.5F;
//    private final float ItemLeftHandOffsetZ = 0F;
//
//    private final float ItemRightHandOffsetX = 4.0F;
//    private final float ItemRightHandOffsetY = -4.5F;
//    private final float ItemRightHandOffsetZ = 0F;

    private final float ItemLeftHandOffsetX = 12F;
    private final float ItemLeftHandOffsetY = -7F;
    private final float ItemLeftHandOffsetZ = 10F;
    private final float[] ItemLeftHandOffsetArr = {ItemLeftHandOffsetX, ItemLeftHandOffsetY, ItemLeftHandOffsetZ};

    private final float ItemLeftHandOffsetZRot = 165F;

    private final float ItemRightHandOffsetX = -12F;
    private final float ItemRightHandOffsetY = -9.5F;
    private final float ItemRightHandOffsetZ = 1F;
    private final float[] ItemRightHandOffsetArr = {ItemRightHandOffsetX, ItemRightHandOffsetY, ItemRightHandOffsetZ};

    private final float ItemRightHandOffsetZRot = -105F;

    private final float LeftHandPivotX = 12F;
    private final float RightHandPivotX = -12F;
//    private final float RightHandPivotY = 0F;
//    private final float RightHandPivotZ = 0F;

    private final Map<String, ItemConvertible> Blademaster025_sword_tex_to_sword = Map.of(
            "wooden_sword", Items.WOODEN_SWORD,
            "stone_sword", Items.STONE_SWORD,
            "golden_sword", Items.GOLDEN_SWORD,
            "diamond_sword", Items.DIAMOND_SWORD,
            "netherite_sword", Items.NETHERITE_SWORD
    );

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
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName() + targetFrame + ".png");

        return DfoSwdGeneralGeoEntityResources.SWD_FADING_CLONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public RenderLayer getRenderType(SwdFadingCloneCustomEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public boolean shouldRender(SwdFadingCloneCustomEntity pEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        Entity player = getClientPlayer();
        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityX(player), EntityMethods.getEntityY(player), EntityMethods.getEntityZ(player), EntityMethods.getEntityX(pEntity), EntityMethods.getEntityY(pEntity), EntityMethods.getEntityZ(pEntity)) <= 32f;
    }

    @Override
    public void render(EntityRenderState entityRenderState, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        SwdFadingCloneCustomEntity entity = this.animatable;
        //client move
        int currentLifeTime;
        if (entity.hasTargetLocXYZ() && (currentLifeTime = entity.getMaxLifetime() - entity.getLifetime()) <= entity.getTotalTicksThatRequiredToMoveToTargetLoc()) {
            float distPercent = ExtraMathMethods.clamp((currentLifeTime + this.partialTick) / entity.getTotalTicksThatRequiredToMoveToTargetLoc(), 0, 1);
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

        Quaternionf rotatorX = new Quaternionf();
        QuaternionHelper.QuatRotationByAxis(rotatorX, MathMethods.AngleToRadians(entity.getRotationX()), 1.0f, 0f, 0f);

        Quaternionf rotatorY = new Quaternionf();
        QuaternionHelper.QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorX);

        if (entity.getEntityScale() != 1) {
            rotatorY.scale(entity.getEntityScale());
        }

        poseStack.multiply(rotatorY);

        //this will update bone first
//        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), (VertexConsumer)null, entityYaw, partialTick, packedLight);

        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, this.partialTick, packedLight);
//        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), (VertexConsumer)null, this.partialTick, packedLight);

        if (entity.isRenderOffhandWeapon()) {
            Optional<GeoBone> tempLeftArmBone = this.model.getBone("left_arm");

            if (tempLeftArmBone.isPresent()) {
                GeoBone bone = tempLeftArmBone.get();

                ModelTransformationMode pDisplayContext = ModelTransformationMode.THIRD_PERSON_LEFT_HAND;
                renderHoldingItemTranslate(entity, this.partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, entity.getOwnerOffhandWeaponStrID(), LeftHandPivotX, false);
            }
        }
        if (entity.isRenderMainHandWeapon()) {
//        Optional<GeoBone> tempRightArmBone = this.getGeoModel().getBone("right_arm");
            Optional<GeoBone> tempRightArmBone = this.model.getBone("right_arm");

            if (tempRightArmBone.isPresent()) {
                GeoBone bone = tempRightArmBone.get();

                ModelTransformationMode pDisplayContext = ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
                renderHoldingItemTranslate(entity, this.partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, entity.getOwnerMainHandWeaponStrID(), RightHandPivotX, true);
            }
        }

//        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, partialTick, packedLight);

//        if (entity.getLifetime() > entity.getMaxLifetime() - entity.getStartTotalFrame()) {
//            this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, partialTick, packedLight);
//        }
//        else {
//            this.defaultRender(poseStack, entity, bufferSource, RenderLayer.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);
//        }

        poseStack.pop();
    }

    public void renderHoldingItemTranslate(SwdFadingCloneCustomEntity entity, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, GeoBone bone, ModelTransformationMode pDisplayContext, String weaponNBTID, float rotateAroundX, boolean isRightHand) {
        poseStack.push();


    //        poseStack.translate( (bone.getPivotX() + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotY() + testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotZ() + testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16 );
    //        poseStack.translate( (bone.getPivotX() + ItemRightHandOffsetX) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotY()) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotZ() + ItemRightHandOffsetZ) * DfoSwordmanSkillTreeConstants.OneOver16 );

        poseStack.translate( (bone.getPivotX()) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotY()) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPivotZ()) * DfoSwordmanSkillTreeConstants.OneOver16 );


        //new
        renderHoldingItemApplyGeoTranslationAndRotation(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, rotateAroundX, isRightHand);
    //        finalRenderItemOnHand(entity, partialTick, poseStack, bufferSource, packedLight, bone);

        poseStack.pop();
    }

    public void renderHoldingItemApplyGeoTranslationAndRotation(SwdFadingCloneCustomEntity entity, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, GeoBone bone, ModelTransformationMode pDisplayContext, String weaponNBTID, float rotateAroundX, boolean isRightHand) {
        poseStack.push();

//        poseStack.translate( (bone.getPosX() + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (bone.getPosY() + testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (-bone.getPosZ() + testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16 );

//        poseStack.translate(bone.getPosX() * DfoSwordmanSkillTreeConstants.OneOver16, bone.getPosY() * DfoSwordmanSkillTreeConstants.OneOver16, -bone.getPosZ() * DfoSwordmanSkillTreeConstants.OneOver16);
        poseStack.translate(bone.getPosX() * DfoSwordmanSkillTreeConstants.OneOver16, bone.getPosY() * DfoSwordmanSkillTreeConstants.OneOver16, -bone.getPosZ() * DfoSwordmanSkillTreeConstants.OneOver16);

        renderHoldingItemApplyGeoTranslationAndRotation2(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, rotateAroundX, isRightHand);

        poseStack.pop();
    }

    public void renderHoldingItemApplyGeoTranslationAndRotation2(SwdFadingCloneCustomEntity entity, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, GeoBone bone, ModelTransformationMode pDisplayContext, String weaponNBTID, float rotateAroundX, boolean isRightHand) {
        poseStack.push();
        //holding item

//        poseStack.translate((testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16);

//        printInGameMsg(String.format("newXRot: %.3f, newYRot: %.3f, newZRot: %.3f", bone.getRotX(), bone.getRotY(), bone.getRotZ()));
        if (bone.getRotZ() != 0) {
//            poseStack.multiply(Axis.ZP.rotation(MathMethods.AngleToDegrees(bone.getRotZ())));

            Quaternionf rotator = new Quaternionf();
            rotator.rotationAxis(bone.getRotZ(), Vector3fMethods.getAxisZN());
//            rotator.rotationAxis(bone.getRotZ()+MathMethods.AngleToRadiansNoRound(testPressKeyTranslateRot.tempFloatNewRotZ), AxisZN);
//            poseStack.rotateAround(rotator, testPressKeyTranslateRot.tempFloatNewX * DfoSwordmanSkillTreeConstants.OneOver16, testPressKeyTranslateRot.tempFloatNewY * DfoSwordmanSkillTreeConstants.OneOver16, testPressKeyTranslateRot.tempFloatNewZ * DfoSwordmanSkillTreeConstants.OneOver16);
//            poseStack.rotateAround(rotator, (RightHandPivotX + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, testPressKeyTranslateRot.tempFloatNewY * DfoSwordmanSkillTreeConstants.OneOver16, testPressKeyTranslateRot.tempFloatNewZ * DfoSwordmanSkillTreeConstants.OneOver16);
            poseStack.multiply(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);
        }

        if (bone.getRotY() != 0) {
//            poseStack.multiply(Axis.YP.rotation(MathMethods.AngleToDegrees(bone.getRotY())));

            Quaternionf rotator = new Quaternionf();
            rotator.rotationAxis(bone.getRotY(), Vector3fMethods.getAxisYP());
//            rotator.rotationAxis(bone.getRotY() + MathMethods.AngleToRadiansNoRound(testPressKeyTranslateRot.tempFloatNewRotY), AxisYP);
//            poseStack.rotateAround(rotator, (RightHandPivotX + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, testPressKeyTranslateRot.tempFloatNewY * DfoSwordmanSkillTreeConstants.OneOver16, testPressKeyTranslateRot.tempFloatNewZ * DfoSwordmanSkillTreeConstants.OneOver16);
            poseStack.multiply(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);
        }

        if (bone.getRotX() != 0) {
//            poseStack.multiply(Axis.XP.rotation(MathMethods.AngleToDegrees(bone.getRotX())));

            Quaternionf rotator = new Quaternionf();
            rotator.rotationAxis(bone.getRotX(), Vector3fMethods.getAxisXN());
//            rotator.rotationAxis(bone.getRotX() + MathMethods.AngleToRadiansNoRound(testPressKeyTranslateRot.tempFloatNewRotX), AxisXN);
//            poseStack.rotateAround(rotator, (RightHandPivotX + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, testPressKeyTranslateRot.tempFloatNewY * DfoSwordmanSkillTreeConstants.OneOver16, testPressKeyTranslateRot.tempFloatNewZ * DfoSwordmanSkillTreeConstants.OneOver16);
            poseStack.multiply(rotator, rotateAroundX * DfoSwordmanSkillTreeConstants.OneOver16, 0, 0);
        }

//        renderHoldingItemTranslate(entity, partialTick, poseStack, bufferSource, packedLight, bone);
        finalRenderItemOnHand(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, isRightHand);


        poseStack.pop();
    }

    public void finalRenderItemOnHand(SwdFadingCloneCustomEntity entity, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, GeoBone bone, ModelTransformationMode pDisplayContext, String weaponNBTID, boolean isRightHand) {
        poseStack.push();

        finalRenderItemOnHand2(entity, partialTick, poseStack, bufferSource, packedLight, bone, pDisplayContext, weaponNBTID, isRightHand);

        poseStack.pop();
    }

//    public static int abc1 = 0;
//    public static int abc2 = 0;
    public void finalRenderItemOnHand2(SwdFadingCloneCustomEntity entity, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, GeoBone bone, ModelTransformationMode pDisplayContext, String weaponNBTID, boolean isRightHand) {
        poseStack.push();
//        poseStack.translate( (testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ) * DfoSwordmanSkillTreeConstants.OneOver16 );

//        poseStack.translate(ItemRightHandOffsetX * DfoSwordmanSkillTreeConstants.OneOver16, ItemRightHandOffsetY * DfoSwordmanSkillTreeConstants.OneOver16, ItemRightHandOffsetZ * DfoSwordmanSkillTreeConstants.OneOver16);
//        poseStack.translate((ItemRightHandOffsetX + testPressKeyTranslateRot.tempFloatNewX) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY + ItemRightHandOffsetY) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ + ItemRightHandOffsetZ) * DfoSwordmanSkillTreeConstants.OneOver16);

        float[] leftOrRightOffSetArr = isRightHand ? ItemRightHandOffsetArr : ItemLeftHandOffsetArr;
        poseStack.translate(leftOrRightOffSetArr[0] * DfoSwordmanSkillTreeConstants.OneOver16, leftOrRightOffSetArr[1] * DfoSwordmanSkillTreeConstants.OneOver16, leftOrRightOffSetArr[2] * DfoSwordmanSkillTreeConstants.OneOver16);

//        poseStack.translate((testPressKeyTranslateRot.tempFloatNewX + leftOrRightOffSetArr[0]) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewY + leftOrRightOffSetArr[1]) * DfoSwordmanSkillTreeConstants.OneOver16, (testPressKeyTranslateRot.tempFloatNewZ + leftOrRightOffSetArr[2]) * DfoSwordmanSkillTreeConstants.OneOver16);

//        ItemRenderer tempItemRenderer = getMinecraftInstance().getItemRenderer();
        //holding item
        ItemStack itemHolding = ItemStack.EMPTY;
        ItemConvertible tempItemLike = Blademaster025_sword_tex_to_sword.get(weaponNBTID);
        if (tempItemLike != null) {
            itemHolding = new ItemStack(tempItemLike);
        }
        itemHolding.setCount(1);

        if (entity.getOwner() instanceof LivingEntity livingEntityOwner) {
            if (bone.getName().equals("right_arm")) {
                itemHolding = livingEntityOwner.getMainHandStack();
            }
            else if (bone.getName().equals("left_arm")) {
                itemHolding = livingEntityOwner.getOffHandStack();
            }
        }
//        Identifier itemModelResLoc = itemHolding.get(DataComponents.ITEM_MODEL);
//        ModelManager modelMgr = getMinecraftInstance().getModelManager();
//        ItemModel tempItemModel = modelMgr.getItemModel(itemModelResLoc);
//        ModelBakery modelBakery = modelMgr.getModelBakery();


//        BakedModel itemModel = itemModelShaper.getItemModel(itemHolding);
//        tempItemModel.bake(itemModelResLoc);
//        BakedModel itemModel = modelMgr.getModel(new ModelResourceLocation(itemModelResLoc, "map=false"));

//        BakedModel itemModel = getDupModel(itemModelShaper, itemHolding, (int) this.getInstanceId(entity));

//        tempItemRenderer.render(sword, ModelTransformationMode.THIRD_PERSON_LEFT_HAND, true, poseStack, bufferSource, packedLight, OverlayTexture.DEFAULT_UV, itemModel);

        int NO_OVERLAY = OverlayTexture.DEFAULT_UV;

//        if (entity.getOwner() instanceof LivingEntity livingEntityOwner) {
//            tempItemRenderer.renderStatic(livingEntityOwner, livingEntityOwner.getMainHandItem(), ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, false, poseStack, bufferSource, getClientLevel(), packedLight, NO_OVERLAY, 42);
//        }

        if (!itemHolding.isEmpty()) {
            poseStack.push();

//            Quaternionf rotator;
//
//            rotator = new Quaternionf();
//            Vector3f AxisZN = new Vector3f(0, 0, -1);
//            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(90), AxisZN);
//            poseStack.multiply(rotator);
//
//            rotator = new Quaternionf();
//            Vector3f AxisYP = new Vector3f(0, 1, 0);
//            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(-45), AxisYP);
//            poseStack.multiply(rotator);
//
//            rotator = new Quaternionf();
//            Vector3f AxisXN = new Vector3f(-1, 0, 0);
//            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(-90), AxisXN);
//            poseStack.multiply(rotator);


            //new set
            Quaternionf rotator = new Quaternionf();
            Vector3f AxisXN = new Vector3f(-1, 0, 0);
            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound((isRightHand) ? ItemRightHandOffsetZRot : ItemLeftHandOffsetZRot), AxisXN);
            poseStack.multiply(rotator);

            //test
//            Quaternionf rotator;
//
//            rotator = new Quaternionf();
//            Vector3f AxisZN = new Vector3f(0, 0, -1);
//            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(testPressKeyTranslateRot.tempFloatNewRotX), AxisZN);
//            poseStack.multiply(rotator);
//
//            rotator = new Quaternionf();
//            Vector3f AxisYP = new Vector3f(0, 1, 0);
//            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(testPressKeyTranslateRot.tempFloatNewRotY), AxisYP);
//            poseStack.multiply(rotator);
//
//            rotator = new Quaternionf();
//            Vector3f AxisXN = new Vector3f(-1, 0, 0);
//            rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(testPressKeyTranslateRot.tempFloatNewRotZ), AxisXN);
//            poseStack.multiply(rotator);

//            poseStack.translate(0, -0.5F, 0.5F);

//            tempRenderModelLists(entity, itemModel, itemHolding, packedLight, NO_OVERLAY, poseStack, bufferSource.getBuffer(Sheets.translucentItemSheet()) );

            getMinecraftInstance().getItemRenderer().renderItem(itemHolding, pDisplayContext, packedLight, NO_OVERLAY, poseStack, bufferSource, null, 42);

//            tempItemModel.update(new ItemStackRenderState(), itemHolding, tempItemModelRes, pDisplayContext, getClientLevel(), null, 0);

            poseStack.pop();
        }

        poseStack.pop();
    }

//    public void tempRenderModelLists(SwdFadingCloneCustomEntity entity, BakedModel pModel, ItemStack pStack, int pCombinedLight, int pCombinedOverlay, MatrixStack pMatrixStack, VertexConsumer pBuffer) {
//        RandomSource randomsource = RandomSource.create();
//
//        for(Direction direction : Direction.values()) {
//            randomsource.setSeed(42L);
//            tempRenderQuadList(entity, pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, direction, randomsource), pStack, pCombinedLight, pCombinedOverlay);
////            itemRenderer.renderQuadList(pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, direction, randomsource), pStack, pCombinedLight, pCombinedOverlay);
//        }
//
//        randomsource.setSeed(42L);
//        tempRenderQuadList(entity, pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, (Direction)null, randomsource), pStack, pCombinedLight, pCombinedOverlay);
////        itemRenderer.renderQuadList(pMatrixStack, pBuffer, pModel.getQuads((BlockState)null, (Direction)null, randomsource), pStack, pCombinedLight, pCombinedOverlay);
//    }
//
//    public void tempRenderQuadList(SwdFadingCloneCustomEntity entity, MatrixStack pPoseStack, VertexConsumer pBuffer, List<BakedQuad> pQuads, ItemStack pItemStack, int pCombinedLight, int pCombinedOverlay) {
//        pPoseStack.push();
//        PoseStack.Pose posestack$pose = pPoseStack.last();
//
//        for(BakedQuad bakedquad : pQuads) {
//            int i = -1;
//
//            float f = (float)(i >> 16 & 255) / 255.0F;
//            float f1 = (float)(i >> 8 & 255) / 255.0F;
//            float f2 = (float)(i & 255) / 255.0F;
//
////            pBuffer.putBulkData(posestack$pose, bakedquad, f, f1, f2, 1.0F, pCombinedLight, pCombinedOverlay, true);
//
//
//            float alpha;
//
//            if (entity.isStartFrameFading() && entity.getLifetime() > entity.getMaxLifetime() - entity.getStartTotalFrame()) {
//                alpha = ((entity.getMaxLifetime() - entity.getLifetime()) * ((float) Math.max(0, entity.getColorAlpha()) / entity.getStartTotalFrame()) + 15) * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL;
//            }
//            else {
//                if (entity.isEndFrameFading() && entity.getLifetime() <= entity.getEndTotalFrame()) {
//                    alpha = ((entity.getLifetime() * ((float) Math.max(0, entity.getColorAlpha()) / entity.getEndTotalFrame())) + 15f) * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL;
//                }
//                else {
//                    alpha = entity.getColorAlpha() * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL;
//                }
//            }
//
//            pBuffer.putBulkData(posestack$pose, bakedquad, f, f1, f2, alpha, pCombinedLight, pCombinedOverlay, true);
//        }
//        pPoseStack.pop();
//    }

    @Override
    public Color getRenderColor(SwdFadingCloneCustomEntity animatable, float partialTick, int packedLight) {
        if (animatable.isStartFrameFading() && animatable.getLifetime() > animatable.getMaxLifetime() - animatable.getStartTotalFrame()) {
//            return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), (int) ( (animatable.getMaxLifetime() - animatable.getLifetime()) * (Math.max(0, animatable.getColorAlpha()) / animatable.getStartTotalFrame()) ) + 15);
            return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), (animatable.getMaxLifetime() - animatable.getLifetime()) * (Math.max(0, animatable.getColorAlpha()) / animatable.getStartTotalFrame()) + 15);
        }
        else {
            if (animatable.isEndFrameFading() && animatable.getLifetime() <= animatable.getEndTotalFrame()) {
//                return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), (int) (animatable.getLifetime() * (Math.max(0, animatable.getColorAlpha()) / animatable.getEndTotalFrame())) + 15);
                return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), animatable.getLifetime() * (Math.max(0, animatable.getColorAlpha()) / animatable.getEndTotalFrame()) + 15);
            }
            else {
                return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), animatable.getColorAlpha());
            }
        }
    }
}
