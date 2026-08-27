package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.render.LightmapTextureManager;

import net.minecraft.entity.player.PlayerEntity;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.models.KatanaModel;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.models.LightsabreModel;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Set;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftInstance;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getLivingEntityTickCount;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class SkillEffLayer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final float WeaponHandOffsetY = 9.55F;

    private final EntityModelLayer KATANA_OFFHAND_TEXTURE = new EntityModelLayer(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/secret_sword_art/offhand_layer/katana/katana_0.png"), "katanaoffhandlayer");
    private final KatanaModel<LivingEntity> katanaOffhandModel;

    public static final EntityModelLayer LIGHTSABRE_OFFHAND_TEXTURE = new EntityModelLayer(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/secret_sword_art/offhand_layer/lightsabre/lightsabre_0.png"), "lightsabreoffhandlayer");
    private final LightsabreModel<LivingEntity> lightsabreOffhandModel;

    public SkillEffLayer(LivingEntityRenderer<T, M> pRenderer) {
        super(pRenderer);
        this.katanaOffhandModel = new KatanaModel<>(KatanaModel.createLeftOffHandLayer().createModel());
        this.lightsabreOffhandModel = new LightsabreModel<>(LightsabreModel.createLeftOffHandLayer().createModel());
    }

    private static Set<String> KatanaActionNameSet = Set.of(
            "sword_act_katana_slash4to1", "sword_act_katana_slash1to2", "sword_act_katana_slash2to3", "sword_act_katana_slash3to4", "sword_act_katana_1"
    );

    private static Set<String> LightsabreActionNameSet = Set.of(
            "sword_act3to1_slash", "sword_act1to2_slash", "sword_act2to3_slash", "sword_act1"
    );

    public static void displayLayerModel(LivingEntity livingEntity, BipedEntityModel<LivingEntity> layerModel) {
        EntityRenderer<? super LivingEntity> entityRenderer = getMinecraftInstance().getEntityRenderDispatcher().getRenderer(livingEntity);
        if (entityRenderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
            EntityModel<?> entityModel = livingRenderer.getModel();

            if (entityModel instanceof BipedEntityModel<?> humanoidModel) {
                if (layerModel instanceof KatanaModel<?> katanaModel) {
                    katanaModel.setTempLeftHand(humanoidModel.leftArm);
                }
                else if (layerModel instanceof LightsabreModel<?> lightsabreModel) {
                    lightsabreModel.setTempLeftHand(humanoidModel.leftArm);
                }
            }
        }
    }

    @Override
    public void render(MatrixStack pPoseStack, VertexConsumerProvider bufferSource, int packedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        pPoseStack.push();

        //Blademaster021 offhand weapon
        if (pLivingEntity instanceof PlayerEntity player && DfoSkillTreeItemMethods.isOffhandEmpty(pLivingEntity)) {
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



        pPoseStack.pop();
    }

    public void setupTranslation(MatrixStack pPoseStack, VertexConsumerProvider bufferSource, T pLivingEntity, ModelPart bone, BipedEntityModel<LivingEntity> humanoidModel, EntityModelLayer modelLayerLocation, float scale, float glint) {
        pPoseStack.push();
        pPoseStack.translate(bone.pivotX * DfoSwordmanSkillTreeConstants.OneOver16, bone.pivotY * DfoSwordmanSkillTreeConstants.OneOver16, bone.pivotZ * DfoSwordmanSkillTreeConstants.OneOver16);

        setupRotation(pPoseStack, bufferSource, pLivingEntity, bone, humanoidModel, modelLayerLocation, scale, glint);
        pPoseStack.pop();
    }

    public void setupRotation(MatrixStack pPoseStack, VertexConsumerProvider bufferSource, T pLivingEntity, ModelPart bone, BipedEntityModel<LivingEntity> humanoidModel, EntityModelLayer modelLayerLocation, float scale, float glint) {
        pPoseStack.push();

        Quaternionf rotator;
        if (bone.roll != 0) {
            rotator = new Quaternionf();
            Vector3f AxisZN = new Vector3f(0, 0, 1);
            rotator.rotationAxis(bone.roll, AxisZN);
            pPoseStack.multiply(rotator, 0, 0, 0);
        }

        if (bone.yaw != 0) {
            rotator = new Quaternionf();
            Vector3f AxisYP = new Vector3f(0, 1, 0);
            rotator.rotationAxis(bone.yaw, AxisYP);
            pPoseStack.multiply(rotator, 0, 0, 0);
        }

        if (bone.pitch != 0) {
            rotator = new Quaternionf();
            Vector3f AxisXN = new Vector3f(1, 0, 0);
            rotator.rotationAxis(bone.pitch, AxisXN);
            pPoseStack.multiply(rotator, 0, 0, 0);
        }

        finalRenderItem(pPoseStack, bufferSource, pLivingEntity, bone, humanoidModel, modelLayerLocation, scale, glint);
        pPoseStack.pop();
    }

    public void finalRenderItem(MatrixStack pPoseStack, VertexConsumerProvider bufferSource, T pLivingEntity, ModelPart bone, BipedEntityModel<LivingEntity> humanoidModel, EntityModelLayer modelLayerLocation, float scale, float glint) {
        pPoseStack.push();


//        pPoseStack.translate(testPressKeyTranslateRot.tempFloatNewX * DfoSwordmanSkillTreeConstants.OneOver16, WeaponHandOffsetY * DfoSwordmanSkillTreeConstants.OneOver16, testPressKeyTranslateRot.tempFloatNewZ * DfoSwordmanSkillTreeConstants.OneOver16);
        pPoseStack.translate(0, WeaponHandOffsetY * DfoSwordmanSkillTreeConstants.OneOver16, 0);

        pPoseStack.push();

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
        pPoseStack.multiply(rotator, 0, 0, 0);

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

        pPoseStack.multiply(rotator, 0, 0, 0);

        humanoidModel.render(pPoseStack, bufferSource.getBuffer(RenderLayer.getEntityTranslucentCull(modelLayerLocation.getId())), LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, ( (1.0F - glint) + glint * (MathMethods.cos(getLivingEntityTickCount(pLivingEntity) * 0.1F))));


        pPoseStack.pop();

        pPoseStack.pop();
    }
}
