package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.entity.DfoGeneralEntitySpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.handeff.HandEffGetModelPartMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientparticlehelper.ClientParticleHelperEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.MainHandConfigurationMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.Blademaster035;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.registries.SoundRegistry;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.printInGameMsg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityZ;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isEntityChannellingInterrupted;
import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.PlaySoundAtLivingEntityLocationEX;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues.getSLivingEntityState;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor.*;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker002 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_BLOODTHIRSTY;

    public static final String Berserker002_GORE_CROSS_SKILL_DMG_BOOST = "Berserker002_GORE_CROSS_SKILL_DMG_BOOST";
    public static final String Berserker002_MOUNTAINOUS_WHEEL_SKILL_DMG_BOOST = "Berserker002_MOUNTAINOUS_WHEEL_SKILL_DMG_BOOST";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        double skillDamageMultiplier = getCPlayerSkillDamage();

        Double duration = skillData.getDuration().get(correctSkillLevel);

        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);

        Float finalDmg = (float) ( ( DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        Double AOE = skillData.getEffectArea().get(correctSkillLevel);

        Double TotalHPPercentageConsumption = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Double AtkDmgMultiplier = skillData.getValueMultiplierList_02().get(correctSkillLevel);
        Double GoreCrossDmgMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double MountainousWheelDmgMultiplier = skillData.getDamageMultiplierList_03().get(correctSkillLevel);


        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%ss", duration),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%s", AOE),
                String.format("%d%%", (int) (TotalHPPercentageConsumption.floatValue() * 100)),
                String.format("%d%%", (int) (AtkDmgMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (GoreCrossDmgMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (MountainousWheelDmgMultiplier.floatValue() * 100))
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker002::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);

        //channeling final action
        MethodAction tempChannelingFinalAction = (entity1) -> {
                    //condition, if skill is interrupted, do not execute final action
                    if (!isEntityChannellingInterrupted(entity1)) {
                        int playerSkillLVL = 0;
                        SkillDataJson skill1 = getID2SkillData().get(SkillID);
                        float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);
                        Predicate<Entity> condition;

                        float ExtraDamageMultiplier = 0;
                        if (entity1 instanceof ServerPlayer sp1) {
                            playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                            if (playerSkillLVL <= 0) {
                                return;
                            }
                            playerSkillLVL = playerSkillLVL - 1;
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_END);
                            condition = defaultSkillTargetCondition(sp1);
                        }
                        else {
                            condition = defaultSkillTargetCondition();
                        }
                        String GeoModelPath = Blademaster035.BLADEMASTER_035_GEO_MODEL_PATH_PREFIX + 0 + Blademaster035.BLADEMASTER_035_GEO_MODEL_PATH_POSTFIX;
                        String TexturePath = Blademaster035.BLADEMASTER_035_TEXUTRE_PATH_PREFIX + 0 + Blademaster035.BLADEMASTER_035_TEXUTRE_PATH_POSTFIX;
                        String AnimationFilePath = "animations/generaldfoswords.animation.json";
//                        DfoGeneralEntitySpawnMethods.SetupHandEffEntity(entity1, 600, 0.25f, GeoModelPath, TexturePath, AnimationFilePath, DfoSwordmanSkillTreeConstants.NO_ANIMATION, HandEffGetModelPartMethods.LEFT_ARM, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);
                        DfoGeneralEntitySpawnMethods.SetupHandEffEntityWithTrailing(entity1, 600, 0.25f, GeoModelPath, TexturePath, AnimationFilePath, DfoSwordmanSkillTreeConstants.NO_ANIMATION, HandEffGetModelPartMethods.LEFT_ARM, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                2f, 10, 10, BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[0], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[1], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[2], 125,true);
//                        printInGameMsg("right?: " + MainHandConfigurationMethods.isMainArmRight(entity1));

                        int GoreCrossSkillDMGBoost = (int) (skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue() * 100);
                        ServerSkillMethods.setEntityStateAmountNoPacket(entity1, Berserker002_GORE_CROSS_SKILL_DMG_BOOST, GoreCrossSkillDMGBoost);
                        int MountainousWheelSkillDMGBoost = (int) (skill1.getDamageMultiplierList_03().get(playerSkillLVL).floatValue() * 100);
                        ServerSkillMethods.setEntityStateAmountNoPacket(entity1, Berserker002_MOUNTAINOUS_WHEEL_SKILL_DMG_BOOST, MountainousWheelSkillDMGBoost);

                        GeoModelSpawnMethods.GenerateCustomChannelingIconEntity(entity1, 1f, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_HEIGHT, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_LIFETIME, skill1.getResLocBtn(), true, true);

                        int duration2Ticks = (int) (skill1.getDuration().get(playerSkillLVL).floatValue() * 20);
                        float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();

                        Vector3f triggeredEntityPos = getEntityPos(entity1);
                        DfoGeneralEntitySpawnMethods.SetupClientParticleHelperBoundToEntity(entity1, 0, 0, duration2Ticks, 0, 0.5f, 0, DfoSwordmanSkillTreeConstants.NO_ANIMATION, Berserker002_ParticleBuff, null, 777, true);
                        DfoGeneralEntitySpawnMethods.SetupClientParticleHelperNoLoc(entity1, 0, aoeRange, 1, triggeredEntityPos.x(), triggeredEntityPos.y(), triggeredEntityPos.z(), DfoSwordmanSkillTreeConstants.NO_ANIMATION, Berserker002_Discharge, null, 777, true);

                        float TotalHPConsumption = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();

                        float AttackDamageMultiplier = skill1.getValueMultiplierList_02().get(playerSkillLVL).floatValue();

                        applyTimedAttackDMGToEntity(entity1, duration2Ticks, AttackDamageMultiplier, Constants.OP_MULTIPLY_BASE, false, SkillID, Berserker02_HPConsumptionPercentage(TotalHPConsumption), null);

                        DfoSwdSoundMethods.PlayChannelingEndSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);

                        float damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                        float damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                        float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);

                        //start damage
                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity1, aoeRange, condition)) {
                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity1, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg);
                            }
                        }
                    }
                    else {
                        PlaySoundAtLivingEntityLocationEX(entity1, SoundRegistry.CHANNELING_FAILED_SOUND.get(), 0.4f, 1.0f);
                    }
                };

        //while channeling
        tempSkillData.setChannelingSkillAction1(DfoSwordmanSkillMethods::DoNothing);
        //final action
        tempSkillData.setActiveSkillAction1(tempChannelingFinalAction);

    }

    public static void Berserker02_SpecificSkillsDmgMultiplierRemoval(LivingEntity entity) {
        ServerSkillMethods.setEntityStateAmountNoPacket(entity, Berserker002_GORE_CROSS_SKILL_DMG_BOOST, 0);
        ServerSkillMethods.setEntityStateAmountNoPacket(entity, Berserker002_MOUNTAINOUS_WHEEL_SKILL_DMG_BOOST, 0);
    }

    public static MethodAction Berserker02_HPConsumptionPercentage(float TotalHPConsumptionPercentage) {
        return (target) -> {
            if (MathMethods.isEven(getEntityTickCount(target))) {
                float MaxEntityHealth = EntityMethods.getLivingEntityMaxHP(target);
                float hpShouldReduce = MaxEntityHealth * TotalHPConsumptionPercentage;
                float currentEntityHealth = EntityMethods.getLivingEntityCurrentHP(target) - hpShouldReduce;

                EntityMethods.setLivingEntityCurrentHP(target, MathMethods.max(0.01f, currentEntityHealth));
            }
        };
    }

    //client

    public static final String Berserker002_ParticleBuff = "Berserker002_ParticleBuff";
    public static final String Berserker002_Discharge = "Berserker002_Discharge";

    public static void Berserker002_ParticleRegistry(HashMap<String, MethodEntityAction> particleMap) {
        particleMap.put(Berserker002_ParticleBuff, Berserker002::CreateParticleBufMethodReference);
        particleMap.put(Berserker002_Discharge, Berserker002::CreateDischargeMethodReference);
    }

    public static void CreateParticleBufMethodReference(Entity entity) {
        ClientParticleHelperEntity clientParticleHelperEntity = (ClientParticleHelperEntity) entity;
        if (MathMethods.isEven(getEntityTickCount(entity))) {
            Vector3f center = getEntityPos(clientParticleHelperEntity);
            Vector3f ownerDelta = null;
            if (clientParticleHelperEntity.getOwner() != null) {
                ownerDelta = getEntityDeltaMovement(clientParticleHelperEntity.getOwner());
            }

            Vector3f randPos, randomDelta;
            for (int i = 0; i < 10; i++) {
                float randHorizontalFacing = ExtraMathMethods.randomFloat(360f);
                float randVerticalFacing = ExtraMathMethods.randomFloat(180f) - 90f;
                float randomDist = ExtraMathMethods.randomFloat() + 0.5f;
                int lifetime = (int) (randomDist * 5);
                randPos = Vector3fMethods.PolarProjectionFromRotation(center, randomDist, randHorizontalFacing, randVerticalFacing);
                randomDelta = Vector3fMethods.PolarProjectionFromRotation(randomDist * -0.1f, randHorizontalFacing, randVerticalFacing);
                if (ownerDelta != null) {
                    randomDelta = randomDelta.add(ownerDelta);
                }
                ParticleMethods.GenerateWhiteParticle(randPos.x(), randPos.y() + 1.4f, randPos.z(),
                        randomDelta.x(), randomDelta.y(), randomDelta.z(),
                        0.33f, 0.3f + ExtraMathMethods.randomFloat(), lifetime, 1f, 0.08f, 0.24f);
            }
        }
    }

    public static void CreateDischargeMethodReference(Entity entity) {
        ClientParticleHelperEntity clientParticleHelperEntity = (ClientParticleHelperEntity) entity;
        Vector3f fromPos = getEntityPos(clientParticleHelperEntity);
        float aoeRange = getEntityVerticalFacingDeg(entity);

        Vector3f randomDelta;
        for (int i = 0; i < 200; i++) {
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(0.1f + aoeRange * ExtraMathMethods.randomFloat() * 0.1f, ExtraMathMethods.randomFloat(360), ExtraMathMethods.randomFloat(60) - 30);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 1.5f + ExtraMathMethods.randomFloat() * 2, 30, 0.86f, 0.08f, 0.24f);
        }
    }
}
