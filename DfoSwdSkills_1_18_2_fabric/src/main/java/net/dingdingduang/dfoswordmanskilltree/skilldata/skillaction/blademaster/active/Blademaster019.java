package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster008;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;

import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isLivingEntityActionInterrupted;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster019 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_UPPER_SLASH;
    public static final String SHORT_SWORD_ADDITIONAL_UPWARD_SLASH = SkillID + "_UPWARD_SLASH";

    public static final String BLADEMASTER019_SLASH_UPWARD_ANIMATION = "upper_slash2";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster019::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);



        if (isClientSide()) {
            return;
        }



        //====================================
        //setup skill action
        MethodAction tempSkillAction =
                (entity1) -> {
                    int playerSkillLVL = 0;

                    float aoeRange;
                    float floatingMultiplier;
                    float damageBase;
                    float damageMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);
                    Predicate<Entity> condition;

                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);
                    boolean isWieldingShortSword = weaponType == DfoSkillTreeItemMethods.IS_AVERAGE_SHORT_SWORD;
                    boolean shouldTriggerSwordQi = false;

                    float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                    float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                    float damageTickSpeedMultiplier = entityAnimationArr[1];

                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity1);
                    float pXSpeed = Vector3fMethods.PolarProjectionFromXHorizon(0.22f, triggeredEntityFacingAngle);
                    float pZSpeed = Vector3fMethods.PolarProjectionFromZHorizon(0.22f, triggeredEntityFacingAngle);

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    float ExtraDamageMultiplier = 0;
                    if (entity1 instanceof ServerPlayerEntity sp1) {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER019_SLASH_UPWARD_ANIMATION, entityAnimationSpeedMultiplier);
                        condition = defaultSkillTargetCondition(sp1);
                        DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, pXSpeed, 0f, pZSpeed, true);
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                        }

                        if (isWieldingShortSword) {
                            if (ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_SHORT_SWORD_MASTERY) >= Blademaster008.BLADEMASTER008_SHORT_SWORD_MASTERY_BONUS_EFFECT_1_LVL) {
                                shouldTriggerSwordQi = true;
                            }
                        }

                        int EXUpwardSlashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_UPPER_SLASH_UPGRADE);
                        if (EXUpwardSlashUpgradeLVL > 0) {
                            SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_UPPER_SLASH_UPGRADE);
                            ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXUpwardSlashUpgradeLVL - 1).floatValue();
                        }
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }
                    setEntityDeltaMovement(entity1, getEntityDeltaMovement(entity1).add(pXSpeed, 0f, pZSpeed));

                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                    floatingMultiplier = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    //start Actioning
                    boolean finalShouldTriggerSwordQi = shouldTriggerSwordQi;

                    int dmgTicks = (int) (3 * damageTickSpeedMultiplier);
                    int animTicks = (int) (5 * damageTickSpeedMultiplier);
                    double finalAoeRange = aoeRange;
                    float finalFloatingHeight = 0.5f * floatingMultiplier;
                    float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
                    int actionPeriod = 1;

                    IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
                    MethodAction action =
                            (entity2) ->  {
                                int updatedTick = ElapsedTicks.getIntCounter();
                                if (updatedTick < animTicks  && !isLivingEntityActionInterrupted(entity2) ) {
                                    TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 40, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);

                                    //spawn model
                                    if (updatedTick == 2) {
                                        //play skill sound at target location
                                        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                        DfoSwdSoundMethods.PlayRandomSwordUpperSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat()*0.3f+0.85f);

                                        GeoModelSpawnMethods.GenerateYellowSlash(entity2, getEntityHorizontalFacingDeg(entity2), 135f, true);
                                    }

                                    //start damage
                                    if (updatedTick == dmgTicks) {
                                        //spawn model
                                        boolean playHitSound = false;
                                        for (Entity tempEntity: GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition)) {
                                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                                if (!playHitSound) {
                                                    playHitSound = true;
                                                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                                }
                                                pushEntityWithDeltaValueXYZ(tempLivingEntity, 0, finalFloatingHeight, 0, true);

                                                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
                                            }
                                        }

                                        if (finalShouldTriggerSwordQi) {
                                            IntegerValueHolder tempIntTicker = new IntegerValueHolder(-5);
                                            Entity tempEntity = GeoModelSpawnMethods.GenerateCustomFadingCloneEntity(entity2, triggeredEntityFacingAngle, 0, 1, 255, 255, 224, 150, 0, 0, 0, 20,
                                                    GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, false, 4, 8, 8, true, true, BLADEMASTER019_SLASH_UPWARD_ANIMATION, entityAnimationSpeedMultiplier, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                                    DfoSwordmanSkillTreeConstants.NO_ANIMATION, true, false, true);

                                            String tempShortSwordSwordQiUpwardSlashID = SHORT_SWORD_ADDITIONAL_UPWARD_SLASH + ExtraMathMethods.randomInt(100);
                                            MethodAction shortSwordQiAction = (entity3) -> {
                                                int currentSwordQiTick = tempIntTicker.getIntCounter();

                                                if (currentSwordQiTick == 0) {
                                                    GeoModelSpawnMethods.GenerateYellowSlash(entity3, getEntityHorizontalFacingDeg(tempEntity), 135f, true);
                                                }
                                                if (currentSwordQiTick == dmgTicks) {
                                                    DfoSwdSoundMethods.PlayShortSwordWieldingSound(getEntityLevel(entity3), getEntityX(tempEntity), getEntityY(tempEntity), getEntityZ(tempEntity), 0.7f, ExtraMathMethods.randomFloat() * 0.2f + 0.9f);

                                                    DfoSwordmanSkillMethods.executeCommonSwdPhysicalDamageAction(weaponType, entity3, aoeRange * 1.2f, finalDmg * 0.5f, null,
                                                            DfoSwdSoundMethods::PlayRandomSwordUpperSlashSound, condition, 360f, new BooleanValueHolder(true), new HashSet<>(), 0.15f, 0.6f, true, null);

                                                    SBSTickEventMethods.setSkillActionDone(entity3, tempShortSwordSwordQiUpwardSlashID);
                                                }
                                                tempIntTicker.setIntCounter(currentSwordQiTick + 1);
                                            };
                                            SBSTickEventMethods.setMethodActionTimer(entity2, tempShortSwordSwordQiUpwardSlashID, actionPeriod, shortSwordQiAction);
                                        }
                                    }

                                    //update val
                                    ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
                                }
                                else {
                                    //IMPORTANT! set action off to allow entity to be able to cast skills
//                                    SendPacketIsInActionToClientSideFromServer(entity2, Constants.ACTION_OFF);
                                    incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);

                                    //reset animation
//                                    if (entity2 instanceof ServerPlayerEntity sp1) {
//                                        SBPlayerAnimatorMethods.PlayDfoSwordmanSkillAnimationServer(sp1, "noaction");
//                                    }

                                    SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                                }
                            };
                    //Actioning ends
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

                    //start loop actioning with fixed period ticks
                    SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
                };

        tempSkillData.setActiveSkillAction1(tempSkillAction);
    }
}
