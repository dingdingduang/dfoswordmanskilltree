package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Axis;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.models.CommonModel;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.models.berserker.BloodSwordModel;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.models.berserker.BloodSwordThrustExplosionModel;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.models.berserker.BloodSwordThrustSlashModel;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.handeff.HandEffEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active.Berserker017;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.models.blademaster.KatanaModel;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.models.blademaster.LightsabreModel;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;

import java.util.HashMap;
import java.util.Map;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftInstance;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.RemoveEntity;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getLivingEntityTickCount;

public class SkillEffLayer {
    private static final float WeaponHandOffsetY = 9.55F;

    private static final KatanaModel<LivingEntity> katanaOffhandModel = new KatanaModel<>(KatanaModel.createLeftOffHandLayer().bakeRoot());
    private static final LightsabreModel<LivingEntity> lightsabreOffhandModel = new LightsabreModel<>(LightsabreModel.createLeftOffHandLayer().bakeRoot());
    private static final BloodSwordModel<LivingEntity> bloodSwordOffhandModel = new BloodSwordModel<>(BloodSwordModel.createLeftOffHandLayer().bakeRoot());
    private static final BloodSwordThrustSlashModel<LivingEntity> bloodSwordThrustSlashModel = new BloodSwordThrustSlashModel<>(BloodSwordThrustSlashModel.createModel().bakeRoot());
    private static final BloodSwordThrustExplosionModel<LivingEntity> bloodSwordThrustExplosionModel = new BloodSwordThrustExplosionModel<>(BloodSwordThrustExplosionModel.createModel().bakeRoot());

    private static final Map<String, CommonModel<LivingEntity>> OffHandActionNameToAnimatedTexModelMap = Map.ofEntries(
            Map.entry(Berserker017.BERSERKER17_BLOOD_SWORD_ATTACK_ANIMATION, bloodSwordThrustSlashModel),
            Map.entry(Berserker017.BERSERKER17_BLOOD_SWORD_EXPLOSION_ANIMATION, bloodSwordThrustExplosionModel)
    );

    private static final Map<String, CommonModel<LivingEntity>> OffHandActionNameMap = Map.ofEntries(
            Map.entry("sword_act_katana_slash4to1", katanaOffhandModel),
            Map.entry("sword_act_katana_slash1to2", katanaOffhandModel),
            Map.entry("sword_act_katana_slash2to3", katanaOffhandModel),
            Map.entry("sword_act_katana_slash3to4", katanaOffhandModel),
            Map.entry("sword_act_katana_1", katanaOffhandModel),
            Map.entry("sword_act3to1_slash", lightsabreOffhandModel),
            Map.entry("sword_act1to2_slash", lightsabreOffhandModel),
            Map.entry("sword_act2to3_slash", lightsabreOffhandModel),
            Map.entry("sword_act1", lightsabreOffhandModel),
            Map.entry("frenzy_attack_slash_1", bloodSwordOffhandModel),
            Map.entry("frenzy_attack_slash_2", bloodSwordOffhandModel),
            Map.entry("frenzy_attack_slash_3", bloodSwordOffhandModel),
            Map.entry("frenzy_attack_slash_4", bloodSwordOffhandModel)
    );

    public static HumanoidModel<?> displayOffHandLayerModel(LivingEntity livingEntity, HumanoidModel<LivingEntity> layerModel) {
        EntityRenderer<? super LivingEntity> entityRenderer = getMinecraftInstance().getEntityRenderDispatcher().getRenderer(livingEntity);
        if (entityRenderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
            EntityModel<?> entityModel = livingRenderer.getModel();

            if (entityModel instanceof HumanoidModel<?> humanoidModel) {
                if (layerModel instanceof CommonModel<?> commonModel) {
                    commonModel.setTempModelBone(humanoidModel.leftArm);
                    return humanoidModel;
                }
            }
        }
        return null;
    }

    private static HashMap<LivingEntity, HandEffEntity> LivingEntityOffHandGeoModelMap = new HashMap<>();

    public static void setLivingEntityOffHandGeoModel(LivingEntity livingEntity, HandEffEntity handEffEntity) {
        HandEffEntity tempHandEffEntity = LivingEntityOffHandGeoModelMap.remove(livingEntity);
        if (tempHandEffEntity != null) {
            RemoveEntity(tempHandEffEntity);
        }
        LivingEntityOffHandGeoModelMap.put(livingEntity, handEffEntity);
    }


    private static float getBob(LivingEntity pLivingBase, float pPartialTick) {
        return pLivingBase.tickCount + pPartialTick;
    }

    private static boolean isShaking(LivingEntity pLivingEntity) {
        return pLivingEntity.isFullyFrozen();
    }

    private static float sleepDirectionToRotation(Direction pFacing) {
        switch (pFacing) {
            case SOUTH -> {
                return 90.0F;
            }
            case WEST -> {
                return 0.0F;
            }
            case NORTH -> {
                return 270.0F;
            }
            case EAST -> {
                return 180.0F;
            }
            default -> {
                return 0.0F;
            }
        }
    }


    private static float getFlipDegrees(LivingEntity pLivingEntity) {
        return 90.0F;
    }

    private static void setupAbstractClientPlayerRotations(AbstractClientPlayer pLivingEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        float f = pLivingEntityLiving.getSwimAmount(pPartialTicks);
        if (pLivingEntityLiving.isFallFlying()) {
            setupDefaultRotations(pLivingEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
            float f1 = (float)pLivingEntityLiving.getFallFlyingTicks() + pPartialTicks;
            float f2 = Mth.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
            if (!pLivingEntityLiving.isAutoSpinAttack()) {
                pPoseStack.mulPose(Axis.XP.rotationDegrees(f2 * (-90.0F - pLivingEntityLiving.getXRot())));
            }

            Vec3 vec3 = pLivingEntityLiving.getViewVector(pPartialTicks);
            Vec3 vec31 = pLivingEntityLiving.getDeltaMovementLerped(pPartialTicks);
            double d0 = vec31.horizontalDistanceSqr();
            double d1 = vec3.horizontalDistanceSqr();
            if (d0 > (double)0.0F && d1 > (double)0.0F) {
                double d2 = (vec31.x * vec3.x + vec31.z * vec3.z) / Math.sqrt(d0 * d1);
                double d3 = vec31.x * vec3.z - vec31.z * vec3.x;
                pPoseStack.mulPose(Axis.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
            }
        } else if (f > 0.0F) {
            setupDefaultRotations(pLivingEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
            float f3 = !pLivingEntityLiving.isInWater() && !pLivingEntityLiving.isInFluidType((fluidType, height) -> pLivingEntityLiving.canSwimInFluidType(fluidType)) ? -90.0F : -90.0F - pLivingEntityLiving.getXRot();
            float f4 = Mth.lerp(f, 0.0F, f3);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(f4));
            if (pLivingEntityLiving.isVisuallySwimming()) {
                pPoseStack.translate(0.0F, -1.0F, 0.3F);
            }
        } else {
            setupDefaultRotations(pLivingEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        }
    }

    private static void setupDefaultRotations(LivingEntity pLivingEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        if (isShaking(pLivingEntityLiving)) {
            pRotationYaw += (float)(Math.cos((double)pLivingEntityLiving.tickCount * (double)3.25F) * Math.PI * (double)0.4F);
        }

        if (!pLivingEntityLiving.hasPose(Pose.SLEEPING)) {
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pRotationYaw));
        }

        if (pLivingEntityLiving.deathTime > 0) {
            float f = ((float)pLivingEntityLiving.deathTime + pPartialTicks - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }

            pPoseStack.mulPose(Axis.ZP.rotationDegrees(f * getFlipDegrees(pLivingEntityLiving)));
        } else if (pLivingEntityLiving.isAutoSpinAttack()) {
            pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F - pLivingEntityLiving.getXRot()));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(((float)pLivingEntityLiving.tickCount + pPartialTicks) * -75.0F));
        } else if (pLivingEntityLiving.hasPose(Pose.SLEEPING)) {
            Direction direction = pLivingEntityLiving.getBedOrientation();
            float f1 = direction != null ? sleepDirectionToRotation(direction) : pRotationYaw;
            pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(getFlipDegrees(pLivingEntityLiving)));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(270.0F));
        } else if (LivingEntityRenderer.isEntityUpsideDown(pLivingEntityLiving)) {
            pPoseStack.translate(0.0F, pLivingEntityLiving.getBbHeight() + 0.1F, 0.0F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }

    private static void scale(LivingEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    public static void render(LivingEntity pLivingEntity, float pPartialTicks, float pRotationYaw, int pPackedLight, PoseStack pPoseStack, MultiBufferSource bufferSource) {
        pPoseStack.pushPose();

        if (pLivingEntity instanceof Player player) {
            KeyframeAnimationPlayer keyframeAnim;
            if ((keyframeAnim = SBPlayerAnimatorMethods.getPlayerLastAnimPlayer(player)) != null && keyframeAnim.isActive()) {
                //init
                ModelPart bone;
                String LastPlayerAnimName = SBPlayerAnimatorMethods.getPlayerLastAnimationName(player);

                //Blademaster021 offhand weapon
                if (DfoSkillTreeItemMethods.isOffhandEmpty(pLivingEntity)) {
                    CommonModel<LivingEntity> OffHandModel = OffHandActionNameMap.get(LastPlayerAnimName);
                    if (OffHandModel != null) {
                        setupMCDefaultRender(pLivingEntity, pPartialTicks, OffHandModel, pPoseStack, bufferSource, keyframeAnim, false);
                    }
                }

                CommonModel<LivingEntity> OffHandAnimatedModel = OffHandActionNameToAnimatedTexModelMap.get(LastPlayerAnimName);
                if (OffHandAnimatedModel != null) {
                    setupMCDefaultRender(pLivingEntity, pPartialTicks, OffHandAnimatedModel, pPoseStack, bufferSource, keyframeAnim, true);
                }
            }
        }



        pPoseStack.popPose();
    }

    public static void setupMCDefaultRender(LivingEntity pLivingEntity, float pPartialTicks, CommonModel<LivingEntity> skillModel, PoseStack pPoseStack, MultiBufferSource bufferSource, KeyframeAnimationPlayer keyframeAnim, boolean hasAnimatedTex) {
        HumanoidModel<LivingEntity> humanoidModel = (HumanoidModel<LivingEntity>) displayOffHandLayerModel(pLivingEntity, skillModel);
        if (humanoidModel != null) {

            pPoseStack.pushPose();

            humanoidModel.attackTime = pLivingEntity.getAttackAnim(pPartialTicks);
            boolean shouldSit = pLivingEntity.isPassenger() && pLivingEntity.getVehicle() != null && pLivingEntity.getVehicle().shouldRiderSit();
            humanoidModel.riding = shouldSit;
            humanoidModel.young = pLivingEntity.isBaby();
            float f = Mth.rotLerp(pPartialTicks, pLivingEntity.yBodyRotO, pLivingEntity.yBodyRot);
            float f1 = Mth.rotLerp(pPartialTicks, pLivingEntity.yHeadRotO, pLivingEntity.yHeadRot);
            float f2 = f1 - f;
            if (shouldSit && pLivingEntity.getVehicle() instanceof LivingEntity livingentity) {
                f = Mth.rotLerp(pPartialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
                f2 = f1 - f;
                float f3 = Mth.wrapDegrees(f2);
                if (f3 < -85.0F) {
                    f3 = -85.0F;
                }

                if (f3 >= 85.0F) {
                    f3 = 85.0F;
                }

                f = f1 - f3;
                if (f3 * f3 > 2500.0F) {
                    f += f3 * 0.2F;
                }

                f2 = f1 - f;
            }

            float f6 = Mth.lerp(pPartialTicks, pLivingEntity.xRotO, pLivingEntity.getXRot());
            if (LivingEntityRenderer.isEntityUpsideDown(pLivingEntity)) {
                f6 *= -1.0F;
                f2 *= -1.0F;
            }

            if (pLivingEntity.hasPose(Pose.SLEEPING)) {
                Direction direction = pLivingEntity.getBedOrientation();
                if (direction != null) {
                    float f4 = pLivingEntity.getEyeHeight(Pose.STANDING) - 0.1F;
                    pPoseStack.translate((float)(-direction.getStepX()) * f4, 0.0F, (float)(-direction.getStepZ()) * f4);
                }
            }

            float f7 = getBob(pLivingEntity, pPartialTicks);
            if (pLivingEntity instanceof AbstractClientPlayer abstractClientPlayer) {
                setupAbstractClientPlayerRotations(abstractClientPlayer, pPoseStack, f7, f, pPartialTicks);
            }
            else {
                setupDefaultRotations(pLivingEntity, pPoseStack, f7, f, pPartialTicks);
            }
            pPoseStack.scale(-1.0F, -1.0F, 1.0F);
            scale(pLivingEntity, pPoseStack, pPartialTicks);
            pPoseStack.translate(0.0F, -1.501F, 0.0F);
            float f8 = 0.0F;
            float f5 = 0.0F;
            if (!shouldSit && pLivingEntity.isAlive()) {
                f8 = pLivingEntity.walkAnimation.speed(pPartialTicks);
                f5 = pLivingEntity.walkAnimation.position(pPartialTicks);
                if (pLivingEntity.isBaby()) {
                    f5 *= 3.0F;
                }

                if (f8 > 1.0F) {
                    f8 = 1.0F;
                }
            }

            humanoidModel.prepareMobModel(pLivingEntity, f5, f8, pPartialTicks);
            humanoidModel.setupAnim(pLivingEntity, f5, f8, f7, f2, f6);

            if (!SBConditions.isSpectator(pLivingEntity)) {
                ModelPart bone = skillModel.getTempModelBone();
                if (hasAnimatedTex) {
                    setupTranslation(pPoseStack, bufferSource, pLivingEntity, bone, skillModel, skillModel.getModelTextureByArrayIndex(keyframeAnim.getCurrentTick()), skillModel.getScale(), skillModel.getGlint());
                }
                else {
                    setupTranslation(pPoseStack, bufferSource, pLivingEntity, bone, skillModel, skillModel.getModelTexture(), skillModel.getScale(), skillModel.getGlint());
                }
            }

            pPoseStack.popPose();
        }
    }

    public static void setupTranslation(PoseStack pPoseStack, MultiBufferSource bufferSource, LivingEntity pLivingEntity, ModelPart bone, HumanoidModel<LivingEntity> humanoidModel, ModelLayerLocation modelLayerLocation, float scale, float glint) {
        pPoseStack.pushPose();
        pPoseStack.translate(bone.x * DfoSwordmanSkillTreeConstants.OneOver16, bone.y * DfoSwordmanSkillTreeConstants.OneOver16, bone.z * DfoSwordmanSkillTreeConstants.OneOver16);

        setupRotation(pPoseStack, bufferSource, pLivingEntity, bone, humanoidModel, modelLayerLocation, scale, glint);
        pPoseStack.popPose();
    }

    public static void setupRotation(PoseStack pPoseStack, MultiBufferSource bufferSource, LivingEntity pLivingEntity, ModelPart bone, HumanoidModel<LivingEntity> humanoidModel, ModelLayerLocation modelLayerLocation, float scale, float glint) {
        pPoseStack.pushPose();

        Quaternionf rotator;
        if (bone.zRot != 0) {
            rotator = new Quaternionf();
            Vector3f AxisZN = new Vector3f(0, 0, 1);
            rotator.rotationAxis(bone.zRot, AxisZN);
            pPoseStack.rotateAround(rotator, 0, 0, 0);
//            rotator.rotationAxis(bone.zRot, AxisZN);
//            pPoseStack.rotateAround(rotator, 0, 0, testPressKeyTranslateRot.tempFloatNewRotZ);
        }

        if (bone.yRot != 0) {
            rotator = new Quaternionf();
            Vector3f AxisYP = new Vector3f(0, 1, 0);
            rotator.rotationAxis(bone.yRot, AxisYP);
//            pPoseStack.rotateAround(rotator, 0, testPressKeyTranslateRot.tempFloatNewRotY, 0);
            pPoseStack.rotateAround(rotator, 0, 0, 0);
        }

        if (bone.xRot != 0) {
            rotator = new Quaternionf();
            Vector3f AxisXN = new Vector3f(1, 0, 0);
            rotator.rotationAxis(bone.xRot, AxisXN);
//            pPoseStack.rotateAround(rotator, testPressKeyTranslateRot.tempFloatNewRotX, 0, 0);
            pPoseStack.rotateAround(rotator, 0, 0, 0);
        }

        finalRenderItem(pPoseStack, bufferSource, pLivingEntity, bone, humanoidModel, modelLayerLocation, scale, glint);
        pPoseStack.popPose();
    }

    public static void finalRenderItem(PoseStack pPoseStack, MultiBufferSource bufferSource, LivingEntity pLivingEntity, ModelPart bone, HumanoidModel<LivingEntity> humanoidModel, ModelLayerLocation modelLayerLocation, float scale, float glint) {
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
        pPoseStack.rotateAround(rotator, 0, 0, 0);

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

        pPoseStack.rotateAround(rotator, 0, 0, 0);


        humanoidModel.renderToBuffer(pPoseStack, bufferSource.getBuffer(RenderType.entityTranslucentCull(modelLayerLocation.getModel())), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, ( (1.0F - glint) + glint * (MathMethods.cos(getLivingEntityTickCount(pLivingEntity) * 0.1F))));


        pPoseStack.popPose();

        pPoseStack.popPose();
    }
}