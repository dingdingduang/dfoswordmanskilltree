package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster036;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster032 {
    private static final String BLADEMASTER032_ANIMATION_START = "flow_upper_slash_start2";

    //Texture
    private static final String BLADEMASTER032_TEXTURE_SWORD_QI = "textures/entity/skilleff/blademaster/flow_stance/upper_slash/qi/";
    private static final String BLADEMASTER032_TEXTURE_UPPER_SLASH = "textures/entity/skilleff/blademaster/flow_stance/upper_slash/slash/";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);
        int MultipleHits = skillData.getValueUsage_01().get(correctSkillLevel).intValue();
        Double ReducedDamageMultiplier = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Double BonusDamageMultiplier = skillData.getValueMultiplierList_02().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                MultipleHits,
                String.format("%d%%", (int) (ReducedDamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (BonusDamageMultiplier.floatValue() * 100))
        );
    }

    public static void init() {
        String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_UPPER_SLASH;
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);

        tempSkillData.setTranslatableTooltipGetter(Blademaster032::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement((SkillID2) -> {
            boolean isInCD = isCPlayerSkillInCD(SkillID2);
            boolean isInAction = isPlayerInAction();
            boolean isInStance = isPlayerInFlowStance();
            boolean isChanneling = isPlayerInChannelingState();
            boolean isOnGround = isPlayerOnGround();
            boolean isPlayerBeingHit = isPlayerBeingHit();

            return !isInCD && (isInAction && isInStance) && !isChanneling && isOnGround && !isPlayerBeingHit;
        });
//        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);



        if (isClientSide()) {
            return;
        }



        //action while channeling on server side
        MethodAction tempActive1Action = (entity1) -> {
            if (Blademaster029.IsLivingEntityInFlowStance(entity1)) {
                ServerSkillMethods.incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, 1);
                Blademaster029.deactivateLivingEntityFlowStance(entity1);
                Blademaster029.getFlowStanceOtherSkillMap().put(entity1, true);

                //play sound when guarding
                DfoSwdSoundMethods.PlaySwordGuardStartSound(getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.7f, 3.0f);

                float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                float damageTickSpeedMultiplier = entityAnimationArr[1];

                //==============
                //calc
                Predicate<Entity> condition;
                int FlowStanceLVL;

                int playerSkillLVL = 0;
                float aoeRange;
                float damageBase;
                float damageMultiplier;
                float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

                int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);

                SkillDataJson skill1 = getID2SkillData().get(SkillID);

                boolean hasZenithOfSwordArtSuperArmor;

                float ExtraDamageMultiplier = 0;
                if (entity1 instanceof ServerPlayer sp1) {
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER032_ANIMATION_START, entityAnimationSpeedMultiplier);
                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);

                    if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                        playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                        if (playerSkillLVL <= 0) {
                            return;
                        }
                        playerSkillLVL = playerSkillLVL - 1;
                    }

                    hasZenithOfSwordArtSuperArmor = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART) >= Blademaster036.BLADEMASTER036_ZENITH_OF_SWORD_ART_BONUS_EFFECT_1_LVL;
                    condition = defaultSkillTargetCondition(sp1);
                    FlowStanceLVL = Math.max(0, ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE) - 1);

                    int EXFlowingUpperSlashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_UPWARD_SLASH_UPGRADE);
                    if (EXFlowingUpperSlashUpgradeLVL > 0) {
                        SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_UPWARD_SLASH_UPGRADE);
                        ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXFlowingUpperSlashUpgradeLVL - 1).floatValue();
                    }
                }
                else {
                    hasZenithOfSwordArtSuperArmor = false;
                    FlowStanceLVL = 0;
                    condition = defaultSkillTargetCondition();
                }
                aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                int MultipleHits = skill1.getValueUsage_01().get(playerSkillLVL).intValue();
                boolean isWieldingLightsabre = weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE;
                boolean isWieldingKatana = weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA;
                boolean isWieldingEitherLightsabreOrKatana = isWieldingLightsabre || isWieldingKatana;
                float ReducedDamageMultiplier = (isWieldingEitherLightsabreOrKatana) ? -skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue() : 0f;
                float BonusDamageMultiplier = (!isWieldingEitherLightsabreOrKatana) ? skill1.getValueMultiplierList_02().get(playerSkillLVL).floatValue() : 0f;
                float FlowForceDamageMultiplierBonus = ServerSkillMethods.getEntityStateAmount(entity1, Blademaster033.BLADEMASTER033_LIVING_ENTITY_BUFF_NAME) * 0.01f;

                float finalDist = skill1.getDistance().get(playerSkillLVL).floatValue();

                float entityFacingAngle = EntityMethods.getEntityHorizontalFacingDeg(entity1);
                //start damage

                int startDmgTicks = (int) (2 * damageTickSpeedMultiplier);
                int endDmgTicks = (int) (6 * damageTickSpeedMultiplier);
                float finalAoeRange = aoeRange;
                float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier * (1f + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE).getValueMultiplierList_01().get(FlowStanceLVL) + ReducedDamageMultiplier + BonusDamageMultiplier + FlowForceDamageMultiplierBonus ) );
                int actionPeriod = 2;

                IntegerValueHolder tempCurrentHitCounter = new IntegerValueHolder((isWieldingEitherLightsabreOrKatana) ? MultipleHits - 1 : 0);
                int multipleHitDMGInterval = Math.max(1, (int) (4 * damageTickSpeedMultiplier));
                IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
                BooleanValueHolder spawnEffect = new BooleanValueHolder(false);
                BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
                HashSet<LivingEntity> DamagedGroup = new HashSet<>();

//                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity1, 255, 255, 255, 1f, 0, 1.4f, 0, Math.max(10, (endDmgTicks - startDmgTicks) * 2), BLADEMASTER032_TEXTURE_SWORD_QI, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
//                        DfoSwordmanSkillTreeConstants.NO_ANIMATION,5, 0, 5, false, false, true, null, 777, true);
//                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity1, 255, 255, 255, 1f, 0, 1.4f, 0, Math.max(8, (endDmgTicks - startDmgTicks) * 2), BLADEMASTER032_TEXTURE_UPPER_SLASH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
//                        DfoSwordmanSkillTreeConstants.NO_ANIMATION,4, 0, 4, false, false, true, null, 777, true);

                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, entityFacingAngle, false, 1f, 0, 1.4f, 0, Math.max(10, (endDmgTicks - startDmgTicks) * 2), BLADEMASTER032_TEXTURE_SWORD_QI,
                        DfoSwordmanSkillTreeConstants.NO_ANIMATION, 5, 0, 5, true, false, true, false, null, 777, true);
                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, entityFacingAngle, false, 1f, 0, 1.4f, 0, Math.max(8, (endDmgTicks - startDmgTicks) * 2), BLADEMASTER032_TEXTURE_UPPER_SLASH,
                        DfoSwordmanSkillTreeConstants.NO_ANIMATION, 4, 0, 4, true, false, true, false, null, 777, true);

                MethodAction action = (entity2) -> {
                    int currentPassedTicks = tempTickCounter.getIntCounter();

                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);

                    if (hasZenithOfSwordArtSuperArmor && isLivingEntityInAction(entity2)) {
                        TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 30, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);
                    }

                    if (!isActionInterrupted && currentPassedTicks >= startDmgTicks && currentPassedTicks <= endDmgTicks) {
                        float entity2FacingAngle = getEntityHorizontalFacingDeg(entity2);
                        //spawn model
                        if (!spawnEffect.getBooleanVal()) {
                            spawnEffect.setBooleanVal(true);
                            //play skill sound at target location
                            DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                            DfoSwdSoundMethods.PlayBlademaster005TripleSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 2.0f);

                            final int[] slashColorArr = BlademasterSkillTreeInitialization.BLADEMASTER_SLASH_COLOR[weaponType + 1];
                            GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, entity2FacingAngle, 135f, 1, slashColorArr[0], slashColorArr[1], slashColorArr[2], 0f, 1.4f, 0f, Math.max(18, endDmgTicks-startDmgTicks),
                                    GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);

                            float finalYDist = finalDist * ((weaponType == DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO) ? 0.8f: 1);

                            Vector3f tempVec = new Vector3f(
                                    Vector3fMethods.PolarProjectionFromXHorizon(0.1f, entityFacingAngle),
                                    finalYDist,
                                    Vector3fMethods.PolarProjectionFromZHorizon(0.1f, entityFacingAngle));

                            if (entity2 instanceof ServerPlayer sp1) {
                                DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, tempVec, true);
                            }
                            setEntityDeltaMovement(entity2, getEntityDeltaMovement(entity2).add(tempVec));
                        }

                        //Sector damage cond
//                        Vector3f triggeredEntityPos = getEntityPos(entity2);
//                        float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);
//                        Vector3f tempVecBehindTriggerEntity = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, -0.5f, triggeredEntityFacingAngle);
//                        Predicate<Entity> SectorDmgCond = (entity) -> {
//                            float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontal(tempVecBehindTriggerEntity, getEntityPos(entity)));
//                            float range = 75f; //150 deg
//
//                            if (!Vector3fMethods.IsAngleDegBetweenAB(sectorDeg, MathMethods.RoundAngle(triggeredEntityFacingAngle + range), MathMethods.RoundAngle(triggeredEntityFacingAngle - range))) {
//                                return false;
//                            }
//
//                            return DistanceBetweenEntities(entity, entity2) <= finalAoeRange;
//                        };
//                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition.and(SectorDmgCond))) {
                        int multipleHitCounter;
                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition)) {
                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                if (!DamagedGroup.contains(tempLivingEntity)) {
                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

//                                                    DfoSwdSoundMethods.PlayRandomSwordHitSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                    DfoSwdSoundMethods.PlayWeaponTypeStabHitSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                    DamagedGroup.add(tempLivingEntity);

                                    Vector3f pushDelta = new Vector3f(
                                            Vector3fMethods.PolarProjectionFromXHorizon(0.1f, entity2FacingAngle),
                                            finalDist,
                                            Vector3fMethods.PolarProjectionFromZHorizon(0.1f, entity2FacingAngle));

                                    if (tempLivingEntity instanceof ServerPlayer sp1) {
                                        //update client
                                        DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, pushDelta, true);
                                    }
                                    if (isWieldingKatana) {
                                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg);
                                    }

                                    setEntityDeltaMovement(tempLivingEntity, getEntityDeltaMovement(tempLivingEntity).add(pushDelta));
                                }
                                else if ( (multipleHitCounter = tempCurrentHitCounter.getIntCounter()) > 0 && currentPassedTicks % multipleHitDMGInterval == 0) {
                                    tempCurrentHitCounter.setIntCounter(multipleHitCounter - 1);

                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
                                    if (isWieldingKatana) {
                                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg);
                                    }

                                    DfoSwdSoundMethods.PlayWeaponTypeStabHitSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                }
                            }
                        }
                    }
                    else {
                        if (isActionInterrupted) {
                            SendActionOffPacket.setBooleanVal(true);
                            incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                            SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                        }
                        else {
                            if (!SendActionOffPacket.getBooleanVal() && currentPassedTicks >= endDmgTicks + 1) {
                                SendActionOffPacket.setBooleanVal(true);
                                incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                            }
                            if (currentPassedTicks >= endDmgTicks) {
                                SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                            }
                        }
                    }

                    tempTickCounter.setIntCounter(tempTickCounter.getIntCounter() + actionPeriod);
                };

                //start loop action
                SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
            }
            //Cancel skill
//            else {
//                SBSTickEventMethods.setSkillActionDone(entity1, SkillID);
//            }
        };

        tempSkillData.setActiveSkillAction1(tempActive1Action);

        MethodAction tempSkillCleanAction = (entity1) -> {
            Blademaster029.getFlowStanceOtherSkillMap().remove(entity1);
            //IMPORTANT! set action off to allow entity to be able to cast skills
//            SendPacketIsInActionToClientSideFromServer(entity1, Constants.ACTION_OFF);
        };
        tempSkillData.setClearSkillAction(tempSkillCleanAction);
    }
}
