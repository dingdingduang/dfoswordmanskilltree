package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.player.Player;
import modified.org.joml.Quaternionf;
import modified.org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.models.KatanaModel;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.models.LightsabreModel;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.Blademaster021;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;

import java.nio.FloatBuffer;
import java.util.Set;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftInstance;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getLivingEntityTickCount;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class SkillEffLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final float WeaponHandOffsetY = 9.55F;

    private final ModelLayerLocation KATANA_OFFHAND_TEXTURE = new ModelLayerLocation(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/secret_sword_art/offhand_layer/katana/katana_0.png"), "katanaoffhandlayer");
    private final KatanaModel<LivingEntity> katanaOffhandModel;

    public static final ModelLayerLocation LIGHTSABRE_OFFHAND_TEXTURE = new ModelLayerLocation(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/secret_sword_art/offhand_layer/lightsabre/lightsabre_0.png"), "lightsabreoffhandlayer");
    private final LightsabreModel<LivingEntity> lightsabreOffhandModel;

    private static Set<String> KatanaActionNameSet = Set.of(
            "sword_act_katana_slash4to1", "sword_act_katana_slash1to2", "sword_act_katana_slash2to3", "sword_act_katana_slash3to4", "sword_act_katana_1"
    );

    private static Set<String> LightsabreActionNameSet = Set.of(
            "sword_act3to1_slash", "sword_act1to2_slash", "sword_act2to3_slash", "sword_act1"
    );

    public SkillEffLayer(LivingEntityRenderer<T, M> pRenderer) {
        super(pRenderer);
        this.katanaOffhandModel = new KatanaModel<>(KatanaModel.createLeftOffHandLayer().bakeRoot());
        this.lightsabreOffhandModel = new LightsabreModel<>(LightsabreModel.createLeftOffHandLayer().bakeRoot());
    }

    public static void displayLayerModel(LivingEntity livingEntity, HumanoidModel<LivingEntity> layerModel) {
//        EntityRenderer<? super AbstractClientPlayer> a = getMinecraftInstance().getEntityRenderDispatcher().getRenderer((AbstractClientPlayer) getClientPlayer());
//        if (a instanceof PlayerRenderer pr1) {
//            EntityModel<AbstractClientPlayer>
//            pr1.addLayer(pr1);
//        }

        EntityRenderer<? super LivingEntity> entityRenderer = getMinecraftInstance().getEntityRenderDispatcher().getRenderer(livingEntity);
        if (entityRenderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
            EntityModel<?> entityModel = livingRenderer.getModel();

            if (entityModel instanceof HumanoidModel<?> humanoidModel) {
//                humanoidModel.copyPropertiesTo(layerModel);

                if (layerModel instanceof KatanaModel<?> katanaModel) {
                    katanaModel.setTempLeftHand(humanoidModel.leftArm);
//                    katanaModel.leftArmPose = humanoidModel.leftArmPose;
//                    katanaModel.leftArm.copyFrom(humanoidModel.leftArm);
                }
                else if (layerModel instanceof LightsabreModel<?> lightsabreModel) {
                    lightsabreModel.setTempLeftHand(humanoidModel.leftArm);
                }

//                layerModel.rightArmPose = humanoidModel.rightArmPose;
//                layerModel.rightArm.copyFrom(humanoidModel.rightArm);
            }
        }
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource bufferSource, int packedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        pPoseStack.pushPose();

        //Blademaster021 offhand weapon
        if (pLivingEntity instanceof Player player && DfoSkillTreeItemMethods.isOffhandEmpty(pLivingEntity)) {
            if (SBPlayerAnimatorMethods.getPlayerLastAnimPlayer(player) != null && SBPlayerAnimatorMethods.getPlayerLastAnimPlayer(player).isActive()) {
                //init
                ModelPart bone;
                boolean SecretSwordActShouldRenderOffhandWeaponKatana = false;
                String LastPlayerAnimName = SBPlayerAnimatorMethods.getPlayerLastAnimationName(player);

                if (KatanaActionNameSet.contains(LastPlayerAnimName)) {
                    SecretSwordActShouldRenderOffhandWeaponKatana = true;

                    displayLayerModel(pLivingEntity, this.katanaOffhandModel);
                    bone = this.katanaOffhandModel.getTempLeftHand();
                    if (bone != null) {
                        setupTranslation(pPoseStack, bufferSource, pLivingEntity, bone, this.katanaOffhandModel, KATANA_OFFHAND_TEXTURE, 1.0f, 0f);
                    }
                }
                if (!SecretSwordActShouldRenderOffhandWeaponKatana) {
                    if (LightsabreActionNameSet.contains(LastPlayerAnimName)) {
                        displayLayerModel(pLivingEntity, this.lightsabreOffhandModel);
                        bone = this.lightsabreOffhandModel.getTempLeftHand();
                        if (bone != null) {
                            setupTranslation(pPoseStack, bufferSource, pLivingEntity, bone, this.lightsabreOffhandModel, LIGHTSABRE_OFFHAND_TEXTURE, 0.5f, 0.3f);
                        }
                    }
                }
            }
        }



        pPoseStack.popPose();
    }

    public void setupTranslation(PoseStack pPoseStack, MultiBufferSource bufferSource, T pLivingEntity, ModelPart bone, HumanoidModel<LivingEntity> humanoidModel, ModelLayerLocation modelLayerLocation, float scale, float glint) {
        pPoseStack.pushPose();
        pPoseStack.translate(bone.x * DfoSwordmanSkillTreeConstants.OneOver16, bone.y * DfoSwordmanSkillTreeConstants.OneOver16, bone.z * DfoSwordmanSkillTreeConstants.OneOver16);

        setupRotation(pPoseStack, bufferSource, pLivingEntity, bone, humanoidModel, modelLayerLocation, scale, glint);
        pPoseStack.popPose();
    }

    public void setupRotation(PoseStack pPoseStack, MultiBufferSource bufferSource, T pLivingEntity, ModelPart bone, HumanoidModel<LivingEntity> humanoidModel, ModelLayerLocation modelLayerLocation, float scale, float glint) {
        pPoseStack.pushPose();

        Quaternionf rotator;
        if (bone.zRot != 0) {
            rotator = new Quaternionf();
            Vector3f AxisZN = new Vector3f(0, 0, 1);
            rotator.rotationAxis(bone.zRot, AxisZN);

//            pPoseStack.rotateAround(rotator, 0, 0, 0);
            PoseStack.Pose pose = pPoseStack.last();
            Matrix4f poseMatrix4fTBR = pose.pose();
            Matrix3f poseNormalR = pose.normal();
            modified.org.joml.Matrix4f jomlPoseMatrix4fTBR = new modified.org.joml.Matrix4f();
            modified.org.joml.Matrix3f jomlPoseNormalR = new modified.org.joml.Matrix3f();

            //rotate around
            FloatBuffer tempFloatBuf1 = FloatBuffer.allocate(16);
            poseMatrix4fTBR.store(tempFloatBuf1);
            jomlPoseMatrix4fTBR.setMatrix4fByFloatBuffer(tempFloatBuf1);
            jomlPoseMatrix4fTBR.rotateAround(rotator, 0, 0, 0);
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

        if (bone.yRot != 0) {
            rotator = new Quaternionf();
            Vector3f AxisYP = new Vector3f(0, 1, 0);
            rotator.rotationAxis(bone.yRot, AxisYP);
//            pPoseStack.rotateAround(rotator, 0, testPressKeyTranslateRot.tempFloatNewRotY, 0);

//            pPoseStack.rotateAround(rotator, 0, 0, 0);
            PoseStack.Pose pose = pPoseStack.last();
            Matrix4f poseMatrix4fTBR = pose.pose();
            Matrix3f poseNormalR = pose.normal();
            modified.org.joml.Matrix4f jomlPoseMatrix4fTBR = new modified.org.joml.Matrix4f();
            modified.org.joml.Matrix3f jomlPoseNormalR = new modified.org.joml.Matrix3f();

            //rotate around
            FloatBuffer tempFloatBuf1 = FloatBuffer.allocate(16);
            poseMatrix4fTBR.store(tempFloatBuf1);
            jomlPoseMatrix4fTBR.setMatrix4fByFloatBuffer(tempFloatBuf1);
            jomlPoseMatrix4fTBR.rotateAround(rotator, 0, 0, 0);
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

        if (bone.xRot != 0) {
            rotator = new Quaternionf();
            Vector3f AxisXN = new Vector3f(1, 0, 0);
            rotator.rotationAxis(bone.xRot, AxisXN);
//            pPoseStack.rotateAround(rotator, testPressKeyTranslateRot.tempFloatNewRotX, 0, 0);

//            pPoseStack.rotateAround(rotator, 0, 0, 0);
            PoseStack.Pose pose = pPoseStack.last();
            Matrix4f poseMatrix4fTBR = pose.pose();
            Matrix3f poseNormalR = pose.normal();
            modified.org.joml.Matrix4f jomlPoseMatrix4fTBR = new modified.org.joml.Matrix4f();
            modified.org.joml.Matrix3f jomlPoseNormalR = new modified.org.joml.Matrix3f();

            //rotate around
            FloatBuffer tempFloatBuf1 = FloatBuffer.allocate(16);
            poseMatrix4fTBR.store(tempFloatBuf1);
            jomlPoseMatrix4fTBR.setMatrix4fByFloatBuffer(tempFloatBuf1);
            jomlPoseMatrix4fTBR.rotateAround(rotator, 0, 0, 0);
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

        finalRenderItem(pPoseStack, bufferSource, pLivingEntity, bone, humanoidModel, modelLayerLocation, scale, glint);
        pPoseStack.popPose();
    }

    public void finalRenderItem(PoseStack pPoseStack, MultiBufferSource bufferSource, T pLivingEntity, ModelPart bone, HumanoidModel<LivingEntity> humanoidModel, ModelLayerLocation modelLayerLocation, float scale, float glint) {
        pPoseStack.pushPose();


//        pPoseStack.translate(testPressKeyTranslateRot.tempFloatNewX * DfoSwordmanSkillTreeConstants.OneOver16, WeaponHandOffsetY * DfoSwordmanSkillTreeConstants.OneOver16, testPressKeyTranslateRot.tempFloatNewZ * DfoSwordmanSkillTreeConstants.OneOver16);
        pPoseStack.translate(0, WeaponHandOffsetY * DfoSwordmanSkillTreeConstants.OneOver16, 0);

        pPoseStack.pushPose();

        Quaternionf rotator;

//        rotator = new Quaternionf();
//        Vector3f AxisZN = new Vector3f(0, 0, -1);
//        rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(testPressKeyTranslateRot.tempFloatNewRotZ), AxisZN);
//        pPoseStack.rotateAround(rotator, 0, 0, 0);
//
//        rotator = new Quaternionf();
//        Vector3f AxisYP = new Vector3f(0, 1, 0);
//        rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(testPressKeyTranslateRot.tempFloatNewRotY), AxisYP);
//        pPoseStack.rotateAround(rotator, 0, 0, 0);
//
//        rotator = new Quaternionf();
//        Vector3f AxisXN = new Vector3f(-1, 0, 0);
//        rotator.rotationAxis(MathMethods.AngleToRadiansNoRound(testPressKeyTranslateRot.tempFloatNewRotX), AxisXN);
//        pPoseStack.rotateAround(rotator, 0, 0, 0);

        rotator = new Quaternionf();
        Vector3f AxisZN = new Vector3f(0, 0, -1);
        rotator.rotationAxis(MathMethods.HALF_PI, AxisZN);
//        pPoseStack.rotateAround(rotator, 0, 0, 0);
        PoseStack.Pose pose = pPoseStack.last();
        Matrix4f poseMatrix4fTBR = pose.pose();
        Matrix3f poseNormalR = pose.normal();
        modified.org.joml.Matrix4f jomlPoseMatrix4fTBR = new modified.org.joml.Matrix4f();
        modified.org.joml.Matrix3f jomlPoseNormalR = new modified.org.joml.Matrix3f();

        //rotate around
        FloatBuffer tempFloatBuf1 = FloatBuffer.allocate(16);
        poseMatrix4fTBR.store(tempFloatBuf1);
        jomlPoseMatrix4fTBR.setMatrix4fByFloatBuffer(tempFloatBuf1);
        jomlPoseMatrix4fTBR.rotateAround(rotator, 0, 0, 0);
        tempFloatBuf1 = jomlPoseMatrix4fTBR.getMatrix4fFloatBuffer();
        poseMatrix4fTBR.load(tempFloatBuf1);

        //rotate
        FloatBuffer tempFloatBuf2 = FloatBuffer.allocate(9);
        poseNormalR.store(tempFloatBuf2);
        jomlPoseNormalR.setMatrix3fByFloatBuffer(tempFloatBuf2);
        jomlPoseNormalR.rotate(rotator);
        tempFloatBuf2 = jomlPoseNormalR.getMatrix3fFloatBuffer();
        poseNormalR.load(tempFloatBuf2);

//        rotator = new Quaternionf();
//        Vector3f AxisYP = new Vector3f(0, 1, 0);
//        rotator.rotationAxis(0, AxisYP);
//        pPoseStack.rotateAround(rotator, 0, 0, 0);

        rotator = new Quaternionf();
        Vector3f AxisXN = new Vector3f(-1, 0, 0);
        rotator.rotationAxis(-MathMethods.HALF_PI, AxisXN);

        if (scale != 1.0F) {
            rotator.scale(scale);
        }

//        pPoseStack.rotateAround(rotator, 0, 0, 0);
        pose = pPoseStack.last();
        poseMatrix4fTBR = pose.pose();
        poseNormalR = pose.normal();
        jomlPoseMatrix4fTBR = new modified.org.joml.Matrix4f();
        jomlPoseNormalR = new modified.org.joml.Matrix3f();

        //rotate around
        tempFloatBuf1 = FloatBuffer.allocate(16);
        poseMatrix4fTBR.store(tempFloatBuf1);
        jomlPoseMatrix4fTBR.setMatrix4fByFloatBuffer(tempFloatBuf1);
        jomlPoseMatrix4fTBR.rotateAround(rotator, 0, 0, 0);
        tempFloatBuf1 = jomlPoseMatrix4fTBR.getMatrix4fFloatBuffer();
        poseMatrix4fTBR.load(tempFloatBuf1);

        //rotate
        tempFloatBuf2 = FloatBuffer.allocate(9);
        poseNormalR.store(tempFloatBuf2);
        jomlPoseNormalR.setMatrix3fByFloatBuffer(tempFloatBuf2);
        jomlPoseNormalR.rotate(rotator);
        tempFloatBuf2 = jomlPoseNormalR.getMatrix3fFloatBuffer();
        poseNormalR.load(tempFloatBuf2);


        humanoidModel.renderToBuffer(pPoseStack, bufferSource.getBuffer(RenderType.entityTranslucentCull(modelLayerLocation.getModel())), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, ( (1.0F - glint) + glint * (MathMethods.cos(getLivingEntityTickCount(pLivingEntity) * 0.1F))));


        pPoseStack.popPose();

        pPoseStack.popPose();
    }
}
