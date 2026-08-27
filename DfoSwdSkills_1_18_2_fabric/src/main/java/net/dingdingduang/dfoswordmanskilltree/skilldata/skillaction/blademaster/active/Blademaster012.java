package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair.EffStrikeFromAirEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster010;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster011;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.FloatValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.TypeValueHolder;
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

import modified.org.joml.Vector3f;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods.GenerateEffGroundQuakeEntity;
import static net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods.GenerateEffStrikeFromAirEntity;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.*;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods.SetupCircleTiltBlocksZone;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster012 {
    public static final String BLADEMASTER012_ANIMATION_START_NAME = "airstrike_start";
    public static final String BLADEMASTER012_ANIMATION_LOOP_NAME = "airstrike_channeling";
    public static final String BLADEMASTER012_ANIMATION_END_NAME = "airstrike_end";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);
        Double AOE = skillData.getEffectArea().get(correctSkillLevel);
        //Sword Qi
        Double GroundQuakeDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double GroundQuakeConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);
        Float GroundQuakeFinalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%s", AOE),
                String.format("%d%%", (int) (GroundQuakeDamageMultiplier.floatValue() * 100)),
                String.format("%s", GroundQuakeConstantDamage),
                String.format("%s", GroundQuakeFinalDmg)
        );
    }

    public static void init() {
        String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_AIRSTRIKE;
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster012::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement(
                (SkillID2) -> {
                    boolean isInCD = isCPlayerSkillInCD(SkillID2);
                    boolean isInAction = isPlayerInAction();
                    boolean isChanneling = isPlayerInChannelingState();
                    boolean isOnGround = isPlayerOnGround();
                    boolean isPlayerBeingHit = isPlayerBeingHit();

                    return !isInCD && !isInAction && !isChanneling && !isOnGround && !isPlayerBeingHit;
                }
        );
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);

        if (isClientSide()) {
            return;
        }

        //====================================
        //setup skill action
        MethodAction tempSkillAction =
                (entity1) -> {
                    int playerSkillLVL = 0;
                    float aoeRange, groundAoeRange;
                    float damageBase, groundDamageBase;
                    float damageMultiplier, groundDamageMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);
                    boolean isWieldingBludgeon = weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON;
                    float BludgeonMasteryLastHitExtraDamageAndAOEMultiplier = 0f;
                    boolean isWieldingZanbato = weaponType == DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO;
                    boolean shouldTriggerZanbatoBonus = false;
                    int ZanbatoMasteryResetDamageGroupEveryXTicks = 777;
                    Predicate<? super Entity> condition;

                    FloatValueHolder TotalDistTraveledHolder = new FloatValueHolder(0f);
                    HashSet<LivingEntity> damagedLivingEntityGroup = new HashSet<>();
                    IntegerValueHolder ElapsedTicksHolder = new IntegerValueHolder(0);
                    TypeValueHolder<EffStrikeFromAirEntity> AirStrikeEffEntity = new TypeValueHolder<>(null);
                    IntegerValueHolder ZanbatoMasteryResetTicksHolder = new IntegerValueHolder(0);

                    //IMPORTANT! setup action on to prevent casting other skills while this action
                    //being performed
                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);
//                    setLivingEntityIsInAction(entity1, Constants.ACTION_ON);

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    float ExtraDamageMultiplier = 0;
                    float ExtraGroundDamageMultiplier = 0;
                    float ExtraGroundAoeMultiplier = 0;
                    float AltitudePercentageReduction = 0;
                    if (entity1 instanceof ServerPlayerEntity sp1) {
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                        }
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER012_ANIMATION_START_NAME);
                        condition = defaultSkillTargetCondition(sp1);

                        int BludgeonMasteryLevel;
                        int ZanbatoMasteryLevel;
                        if (isWieldingBludgeon && (BludgeonMasteryLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY) ) >= Blademaster011.BLADEMASTER011_BLUDGEON_MASTERY_BONUS_EFFECT_4_LVL ) {
                            BludgeonMasteryLastHitExtraDamageAndAOEMultiplier = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY).getDamageList_03().get(BludgeonMasteryLevel - 1).floatValue();
                        }
                        else if (isWieldingZanbato && (ZanbatoMasteryLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY) ) >= Blademaster010.BLADEMASTER010_ZANBATO_MASTERY_BONUS_EFFECT_1_LVL) {
                            shouldTriggerZanbatoBonus = true;
                            ZanbatoMasteryResetDamageGroupEveryXTicks = (int) (20 * getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY).getDamageList_01().get(ZanbatoMasteryLevel - 1));
                        }

                        int EXStrikeFromAirUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_STRIKE_FROM_THE_AIR_UPGRADE);
                        if (EXStrikeFromAirUpgradeLVL > 0) {
                            SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_STRIKE_FROM_THE_AIR_UPGRADE);
                            ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXStrikeFromAirUpgradeLVL - 1).floatValue();
                            ExtraGroundDamageMultiplier = ExtraGroundDamageMultiplier + EX_SkillData.getDamageMultiplierList_02().get(EXStrikeFromAirUpgradeLVL - 1).floatValue();
                            ExtraGroundAoeMultiplier = ExtraGroundAoeMultiplier + EX_SkillData.getValueMultiplierList_01().get(EXStrikeFromAirUpgradeLVL - 1).floatValue();
                            AltitudePercentageReduction = AltitudePercentageReduction + EX_SkillData.getValueMultiplierList_02().get(EXStrikeFromAirUpgradeLVL - 1).floatValue();
                        }
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }
                    aoeRange = skill1.getEffectArea().get(0).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + BludgeonMasteryLastHitExtraDamageAndAOEMultiplier + ExtraDamageMultiplier;
                    groundAoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue() * (1f + BludgeonMasteryLastHitExtraDamageAndAOEMultiplier + ExtraGroundAoeMultiplier);
                    groundDamageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    groundDamageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + BludgeonMasteryLastHitExtraDamageAndAOEMultiplier + ExtraGroundDamageMultiplier;

                    //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    //start Actioning
                    setEntitySPDeltaMovement(entity1, 0, 0, 0, false);

                    int effUpdateTicks = 8;
                    float maxDegree = 45f;

                    Vector3f entityOriPos = getEntityPos(entity1);
                    int animTicks = (int) (skill1.getDuration().get(playerSkillLVL) * 20);
                    int delayTicks = 6;
                    float distShouldTravelEachTime = skill1.getValueUsage_01().get(playerSkillLVL).floatValue();
                    float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
                    float finalGroundDmg = (float) ((groundDamageMultiplier * getEntityAttackDamage(entity1) + groundDamageBase) * skillDamageMultiplier);
                    float pushStrength = skill1.getValueUsage_02().get(playerSkillLVL).floatValue();
                    int actionPeriod = 2;
                    float finalDistShouldTravelEachTime = distShouldTravelEachTime * actionPeriod;

                    boolean finalShouldTriggerZanbatoBonus = shouldTriggerZanbatoBonus;
                    int finalZanbatoMasteryResetDamageGroupEveryXTicks = ZanbatoMasteryResetDamageGroupEveryXTicks;
                    float minDistanceTravelToTriggerGroundQuake = 4f * ExtraMathMethods.clamp(1 - AltitudePercentageReduction, 0, 1);
//                    Predicate<? super Entity> condition = entity -> isEnemyEntity(entity) && hasMoreThanZeroHealth(entity);
                    MethodAction action =
                            (entity2) ->  {
                                boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                                setEntityFallDistance(entity2, 0f);
                                int ElapsedTicks = ElapsedTicksHolder.getIntCounter() + actionPeriod;
                                ElapsedTicksHolder.setIntCounter(ElapsedTicks);
                                if (!isActionInterrupted && !isEntityBPosRFOnGround(entity2) && ElapsedTicks < animTicks) {

                                    if (ElapsedTicks >= delayTicks) {
                                        int startTime = ElapsedTicks - delayTicks;
                                        float entity2HorizontalFacingAngle = getEntityHorizontalFacingDeg(entity2);
//                                        float entity2VerticalFacingAngle = MathMethods.min(-maxDegree, getEntityVerticalFacingDeg(entity2));
                                        float entity2VerticalFacingAngle = MathMethods.max(maxDegree, getEntityVerticalFacingDeg(entity2));
//                                        float entity2VerticalFacingAngle = ExtraMathMethods.clamp(getEntityVerticalFacingDeg(entity2), maxDegree, 90f);
//                                        float entity2VerticalFacingAngle = getEntityVerticalFacingDeg(entity2);

                                        if (ElapsedTicks == delayTicks) {
                                            //play skill sound at target location
                                            DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 1.1f, ExtraMathMethods.randomFloat()*0.3f+0.85f);
                                        }
                                        if (entity2 instanceof ServerPlayerEntity sp1 && startTime % 500 == 0) {
//                                            SBPlayerAnimatorMethods.PlayDfoSwordmanSkillAnimationServer(sp1, "airstrike_channeling");
//                                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, "airstrike_channeling");
                                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER012_ANIMATION_LOOP_NAME);
                                        }

                                        //start damage
                                        if (ElapsedTicks % 2 == 0) {
                                            if (finalShouldTriggerZanbatoBonus) {
                                                TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 40, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);

                                                int ZanbatoResetTicks;
                                                if ((ZanbatoResetTicks = ZanbatoMasteryResetTicksHolder.getIntCounter() + finalZanbatoMasteryResetDamageGroupEveryXTicks) < ElapsedTicks) {
                                                    ZanbatoMasteryResetTicksHolder.setIntCounter(ZanbatoResetTicks);
                                                    damagedLivingEntityGroup.clear();
                                                }
                                            }
                                            //update val
                                            TotalDistTraveledHolder.setFloatVal(TotalDistTraveledHolder.getFloatVal() + finalDistShouldTravelEachTime);

                                            Vector3f tempVec = Vector3fMethods.directionFromRotation(entity2HorizontalFacingAngle, entity2VerticalFacingAngle).mul(finalDistShouldTravelEachTime);


                                            if (startTime % effUpdateTicks == 0) {
                                                String TexPath;
                                                float scale;
                                                if (ElapsedTicks < 27) {
                                                    TexPath = "textures/entity/skilleff/blademaster/strikefromair/0.png";
                                                    scale = 2f;
                                                }
                                                else {
                                                    TexPath = "textures/entity/skilleff/blademaster/stab/0_3.png";
                                                    scale = 3f;
                                                }
                                                EffStrikeFromAirEntity strikeFromAirEntity = GenerateEffStrikeFromAirEntity(entity2, MathMethods.RoundAngle(entity2HorizontalFacingAngle), MathMethods.RoundAngle(entity2VerticalFacingAngle),
                                                        TexPath, "stab", scale,
                                                        effUpdateTicks, tempVec.x(), tempVec.y() + 1f, tempVec.z(), true);

                                                AirStrikeEffEntity.setAnyTypeValue(strikeFromAirEntity);
                                            }
                                            //move pos
                                            setEntitySPDeltaMovement(entity2, tempVec.x(), tempVec.y(), tempVec.z(), false);

                                            EffStrikeFromAirEntity tempStrikeFromAirEntity = AirStrikeEffEntity.getAnyTypeValue();
                                            if ( tempStrikeFromAirEntity != null) {
                                                moveEntityToXYZ(tempStrikeFromAirEntity, getEntityX(tempStrikeFromAirEntity)+tempVec.x(), getEntityY(tempStrikeFromAirEntity)+tempVec.y(), getEntityZ(tempStrikeFromAirEntity)+tempVec.z());
                                            }


                                            for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, aoeRange, condition)) {
                                                if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                                    if (!damagedLivingEntityGroup.contains(tempLivingEntity)) {
                                                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                                        DfoSwdSoundMethods.PlayWeaponTypeStabHitSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat()*0.3f+0.85f);
                                                        damagedLivingEntityGroup.add(tempLivingEntity);
                                                    }

                                                    if (isEntityOnGround(entity2)) {
                                                        setEntitySPDeltaMovement(tempLivingEntity, 0, 0, 0, false);
                                                    }
                                                    else {
                                                        setEntitySPDeltaMovement(tempLivingEntity, tempVec.x()+0.1f, tempVec.y()+0.1f, tempVec.z()+0.1f, true);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        if (entity2 instanceof ServerPlayerEntity sp1) {
                                            tpEntityToPos(sp1, entityOriPos);
                                        }
                                        else {
                                            moveEntityToPos(entity2, entityOriPos);
                                        }
                                    }
                                }
                                else {
                                    //final damage
                                    if (!isActionInterrupted) {
                                        //reset delta
                                        setEntitySPDeltaMovement(entity2, 0, 0, 0, false);

                                        //start dmg
                                        float totalDistHasTraveled = TotalDistTraveledHolder.getFloatVal();
                                        if (totalDistHasTraveled > minDistanceTravelToTriggerGroundQuake) {
                                            float finalDmgBonusMultiplier = ExtraMathMethods.clamp((totalDistHasTraveled - minDistanceTravelToTriggerGroundQuake) * 0.0625f, 0f, 1f);
                                            float finalGroundAoeRange = groundAoeRange + finalDmgBonusMultiplier * 0.5f * groundAoeRange;
                                            finalDmgBonusMultiplier = 1f + finalDmgBonusMultiplier;

                                            Vector3f tempOwnerVec = getEntityPos(entity2);
                                            GenerateEffGroundQuakeEntity(entity2, ExtraMathMethods.randomFloat() * 360f, 180f,
                                                    "textures/entity/skilleff/common/groundquake/", "groundquake", finalGroundAoeRange * 1.5f,
                                                    8, 0, 0.5f, 0, true);

                                            Predicate<Entity> groundDamageCond = entity -> DistanceBetweenEntities(entity, entity2) <= finalGroundAoeRange;

                                            SetupCircleTiltBlocksZone(new Vector3f(tempOwnerVec.x(), tempOwnerVec.y() - 1f, tempOwnerVec.z()), getEntityLevel(entity2),
                                                    finalGroundAoeRange + 0.25f, 100, 0.3f * finalDmgBonusMultiplier, 4,
                                                    true, true, true, true);

                                            //final ground damage
                                            for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalGroundAoeRange, groundDamageCond.and(condition))) {
                                                if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalGroundDmg * finalDmgBonusMultiplier, false);

                                                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);

                                                    //push entities away horizontally
                                                    float angle = Vector3fMethods.AngleFromLocationToLocationHorizontal(tempOwnerVec, getEntityPos(tempEntity));
                                                    float pushXZStrength = calculatePushEntityStrength(pushStrength, entity2, tempLivingEntity);
                                                    float pushYStrength = calculatePushEntityStrengthTargetOnly(pushStrength + 0.2f * finalDmgBonusMultiplier, tempLivingEntity);
                                                    pushEntityWithDeltaValue(tempLivingEntity, angle, pushXZStrength, pushYStrength, true);
                                                }
                                            }

                                            setEntityFallDistance(entity2, 0f);
                                        }
                                    }
                                    //start clean up
//                                    HashMap<String, Integer> entity2State = getSLivingEntityState().get(entity2);
//                                    if (entity2State.containsKey(DfoSwordmanSkillTreeConstants.IS_IMMUNE_FALLING_DMG)) {
//                                        int tempInt = entity2State.get(DfoSwordmanSkillTreeConstants.IS_IMMUNE_FALLING_DMG) - 1;
//                                        entity2State.put(DfoSwordmanSkillTreeConstants.IS_IMMUNE_FALLING_DMG, tempInt);
//                                        if (tempInt <= 0) {
////                                            entity2State.put(DfoSwordmanSkillTreeConstants.IS_IMMUNE_FALLING_DMG, 0);
//                                            entity2State.remove(DfoSwordmanSkillTreeConstants.IS_IMMUNE_FALLING_DMG);
//                                        }
//                                    }

                                    if (entity2 instanceof ServerPlayerEntity sp1) {
//                                        SBPlayerAnimatorMethods.PlayDfoSwordmanSkillAnimationServer(sp1, "airstrike_end");
//                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, "airstrike_end");
                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER012_ANIMATION_END_NAME);
                                    }
//                                    SendPacketIsInActionToClientSideFromServer(entity2, Constants.ACTION_OFF);
                                    incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
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
