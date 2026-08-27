package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
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
import software.bernie.geckolib.object.Color;

import java.util.HashMap;
import java.util.Set;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftInstance;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.printInGameMsg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getLivingEntityTickCount;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class SkillEffLayer {
    private static final HashMap<LivingEntity, HashMap<String, BipedEntityModel<BipedEntityRenderState>>> EntitySkillModelMap = new HashMap<>();
    private static final float WeaponHandOffsetY = 9.55F;

    private static final String KATANA_STR_ID = "katanaoffhandlayer";
    private static final EntityModelLayer KATANA_OFFHAND_TEXTURE = new EntityModelLayer(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/secret_sword_art/offhand_layer/katana/katana_0.png"), KATANA_STR_ID);
//    private final KatanaModel<BipedEntityRenderState> katanaOffhandModel = new KatanaModel<>(KatanaModel.createLeftOffHandLayer().createModel());

    private static final String LIGHTSABRE_STR_ID = "lightsabreoffhandlayer";
    public static final EntityModelLayer LIGHTSABRE_OFFHAND_TEXTURE = new EntityModelLayer(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/secret_sword_art/offhand_layer/lightsabre/lightsabre_0.png"), LIGHTSABRE_STR_ID);
//    private final LightsabreModel<BipedEntityRenderState> lightsabreOffhandModel = new LightsabreModel<>(LightsabreModel.createLeftOffHandLayer().createModel());

    private static Set<String> KatanaActionNameSet = Set.of(
            "sword_act_katana_slash4to1", "sword_act_katana_slash1to2", "sword_act_katana_slash2to3", "sword_act_katana_slash3to4", "sword_act_katana_1"
    );

    private static Set<String> LightsabreActionNameSet = Set.of(
            "sword_act3to1_slash", "sword_act1to2_slash", "sword_act2to3_slash", "sword_act1"
    );

    public static void modelInitialization() {
//        katanaOffhandModel.setTempLeftHand(humanoidModel.leftArm);
    }

    public static void displayLayerModel(LivingEntity livingEntity, BipedEntityModel<BipedEntityRenderState> layerModel) {
        var entityRenderer = getMinecraftInstance().getEntityRenderDispatcher().getRenderer(livingEntity);

        if (entityRenderer instanceof LivingEntityRenderer<?, ?, ?> livingRenderer) {
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

    public static void render(MatrixStack pPoseStack, VertexConsumerProvider bufferSource, LivingEntity pLivingEntity) {

        //Blademaster021 offhand weapon
        if (pLivingEntity instanceof PlayerEntity player && DfoSkillTreeItemMethods.isOffhandEmpty(pLivingEntity)) {
            pPoseStack.push();

            if (SBPlayerAnimatorMethods.getPlayerLastAnimPlayer(player) != null && SBPlayerAnimatorMethods.getPlayerLastAnimPlayer(player).isActive()) {
                //init
                ModelPart bone;
                boolean SecretSwordActShouldRenderOffhandWeaponKatana = false;
                String LastPlayerAnimName = SBPlayerAnimatorMethods.getPlayerLastAnimationName(player);

                if (KatanaActionNameSet.contains(LastPlayerAnimName)) {
                    SecretSwordActShouldRenderOffhandWeaponKatana = true;

                    HashMap<String, BipedEntityModel<BipedEntityRenderState>> livingEntityAvailableSkillModel = EntitySkillModelMap.get(pLivingEntity);
                    if (livingEntityAvailableSkillModel == null) {
                        livingEntityAvailableSkillModel = new HashMap<>();
                        EntitySkillModelMap.put(pLivingEntity, livingEntityAvailableSkillModel);
                    }

                    KatanaModel<BipedEntityRenderState> katanaOffhandModel = (KatanaModel) livingEntityAvailableSkillModel.get(KATANA_STR_ID);
                    if (katanaOffhandModel == null) {
                        katanaOffhandModel = new KatanaModel<>(KatanaModel.createLeftOffHandLayer().createModel());
                        livingEntityAvailableSkillModel.put(KATANA_STR_ID, katanaOffhandModel);
                    }

                    displayLayerModel(pLivingEntity, katanaOffhandModel);
                    bone = katanaOffhandModel.getTempLeftHand();
//                    bone.render(pPoseStack, bufferSource.getBuffer(RenderLayer.getItemEntityTranslucentCull(KATANA_OFFHAND_TEXTURE.id())), LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);
                    if (bone != null) {
                        setupTranslation(pPoseStack, bufferSource, pLivingEntity, bone, katanaOffhandModel, KATANA_OFFHAND_TEXTURE, 0.5f, 0f);
                    }
                }
                if (!SecretSwordActShouldRenderOffhandWeaponKatana) {
                    if (LightsabreActionNameSet.contains(LastPlayerAnimName)) {

                        HashMap<String, BipedEntityModel<BipedEntityRenderState>> livingEntityAvailableSkillModel = EntitySkillModelMap.get(pLivingEntity);
                        if (livingEntityAvailableSkillModel == null) {
                            livingEntityAvailableSkillModel = new HashMap<>();
                            EntitySkillModelMap.put(pLivingEntity, livingEntityAvailableSkillModel);
                        }

                        LightsabreModel<BipedEntityRenderState> lightsabreOffhandModel = (LightsabreModel) livingEntityAvailableSkillModel.get(LIGHTSABRE_STR_ID);
                        if (lightsabreOffhandModel == null) {
                            lightsabreOffhandModel = new LightsabreModel<>(LightsabreModel.createLeftOffHandLayer().createModel());
                            livingEntityAvailableSkillModel.put(LIGHTSABRE_STR_ID, lightsabreOffhandModel);
                        }

                        displayLayerModel(pLivingEntity, lightsabreOffhandModel);
                        bone = lightsabreOffhandModel.getTempLeftHand();
                        if (bone != null) {
                            setupTranslation(pPoseStack, bufferSource, pLivingEntity, bone, lightsabreOffhandModel, LIGHTSABRE_OFFHAND_TEXTURE, 0.25f, 0.3f);
                        }
                    }
                }
            }

            pPoseStack.pop();
        }

    }

    public static void setupTranslation(MatrixStack pPoseStack, VertexConsumerProvider bufferSource, LivingEntity pLivingEntity, ModelPart bone, BipedEntityModel<BipedEntityRenderState> humanoidModel, EntityModelLayer modelLayerLocation, float scale, float glint) {
        pPoseStack.push();
        pPoseStack.translate(bone.pivotX * DfoSwordmanSkillTreeConstants.OneOver16, bone.pivotY * DfoSwordmanSkillTreeConstants.OneOver16, bone.pivotZ * DfoSwordmanSkillTreeConstants.OneOver16);

        setupRotation(pPoseStack, bufferSource, pLivingEntity, bone, humanoidModel, modelLayerLocation, scale, glint);
        pPoseStack.pop();
    }

    public static void setupRotation(MatrixStack pPoseStack, VertexConsumerProvider bufferSource, LivingEntity pLivingEntity, ModelPart bone, BipedEntityModel<BipedEntityRenderState> humanoidModel, EntityModelLayer modelLayerLocation, float scale, float glint) {
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

    public static void finalRenderItem(MatrixStack pPoseStack, VertexConsumerProvider bufferSource, LivingEntity pLivingEntity, ModelPart bone, BipedEntityModel<BipedEntityRenderState> humanoidModel, EntityModelLayer modelLayerLocation, float scale, float glint) {
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

//        if (scale != 1.0F) {
//            rotator.scale(scale);
//        }

        pPoseStack.multiply(rotator, 0, 0, 0);
        if (scale != 1.0F) {
            pPoseStack.scale(scale, scale, scale);
        }

        float alpha = ( (1.0F - glint) + glint * (MathMethods.cos(getLivingEntityTickCount(pLivingEntity) * 0.1F)));
        Color color = Color.ofARGB(alpha, 1, 1, 1);
        humanoidModel.render(pPoseStack, bufferSource.getBuffer(RenderLayer.getItemEntityTranslucentCull(modelLayerLocation.id())), LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, color.getColor());

//        bone.render(pPoseStack, bufferSource.getBuffer(RenderLayer.getItemEntityTranslucentCull(modelLayerLocation.id())), LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);

        pPoseStack.pop();

        pPoseStack.pop();
    }
}
