package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster003;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import org.joml.Vector3f;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.*;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getClientPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.getSkillsInCooldownClientTimerOverlayInstance;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster007 {
    public static final String BLADEMASTER_0_7_STAB_ANIM = "stab1";
    //Server
    public static HashMap<LivingEntity, Integer> BLADEMASTER007_HashMapTriggeredTimes;

    //Client
    private static int BLADEMASTER007_ClientHashMapTriggeredTimes;


    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);
        int ConsecutiveTimes = skillData.getValueUsage_02().get(correctSkillLevel).intValue();

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d", ConsecutiveTimes)
        );
    }

    public static void init() {
//        String SkillID = "BLADEMASTER_0_7";
        String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_STAB;
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster007::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement(
                (SkillID2) -> {
                    boolean isInCD = isCPlayerSkillInCD(SkillID2);
                    boolean isInAction = isPlayerInAction();
                    boolean isChanneling = isPlayerInChannelingState();
                    boolean isOnGround = isPlayerOnGround();
                    boolean isPlayerBeingHit = isPlayerBeingHit();

                    boolean isPassed = !isInAction && !isChanneling && isOnGround && !isPlayerBeingHit;
                    if (isPassed) {
                        if (isInCD) {
                            HashMap<String, Integer> clientCD = getSkillsInCooldownClientTimerOverlayInstance().getPlayerSkill2CooldownTicks();
                            int currentSkillLevel = getClientPlayerSkillID2lvlMap().get(BlademasterSkillTreeInitialization.BLADEMASTER_STAB);
                            int additionalAttacks = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_STAB).getDamageList_02().get(currentSkillLevel - 1).intValue();
                            if (clientCD.get(SkillID2) > 25 || BLADEMASTER007_ClientHashMapTriggeredTimes > additionalAttacks) {
                                isPassed = false;
                            }
                            else {
//                                setClientPlayerInAction(Constants.ACTION_ON);
                                BLADEMASTER007_ClientHashMapTriggeredTimes++;
//                                NetworkingSendMsgMethods.SendPlayerStateDataFromClientSideToServer(DfoSwordmanSkillTreeConstants.CUMULATIVE_ACTION, BLADEMASTER007_ClientHashMapTriggeredTimes);
                            }
                        }
                        else {
//                            setClientPlayerInAction(Constants.ACTION_ON);
                            BLADEMASTER007_ClientHashMapTriggeredTimes = 0;
//                            NetworkingSendMsgMethods.SendPlayerStateDataFromClientSideToServer(DfoSwordmanSkillTreeConstants.CUMULATIVE_ACTION, BLADEMASTER007_ClientHashMapTriggeredTimes);
                        }
                    }
                    return isPassed;
                }
        );
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);

        if (isClientSide()) {
            BLADEMASTER007_ClientHashMapTriggeredTimes = 0;
            return;
        }

        //server init
        if (BLADEMASTER007_HashMapTriggeredTimes == null) {
            BLADEMASTER007_HashMapTriggeredTimes = new HashMap<>();
        }



        //====================================
        //setup skill action
        MethodAction tempSkillAction =
                (entity1) -> {
                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);

                    int playerSkillLVL = 0;
                    float aoeRange;
                    float damageBase;
                    float damageMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

                    //ASSUME BLADEMASTER007_HashMapTotalDistTraveled never be null
                    if (BLADEMASTER007_HashMapTriggeredTimes.containsKey(entity1)) {
                        BLADEMASTER007_HashMapTriggeredTimes.put(entity1, BLADEMASTER007_HashMapTriggeredTimes.get(entity1)+1);
                    }
                    else {
                        BLADEMASTER007_HashMapTriggeredTimes.put(entity1, 1);
                    }
                    //IMPORTANT! setup action on to prevent casting other skills while this action
                    //being performed
//                    setLivingEntityIsInAction(entity1, Constants.ACTION_ON);
                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    boolean shouldActivateLightsabreMultipleHits = false;
                    float ExtraDamageMultiplier = 0;
                    if (entity1 instanceof ServerPlayer sp1) {
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                        }
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER_0_7_STAB_ANIM);
                        if (weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE && ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY) >= Blademaster003.BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_1_LVL) {
                            shouldActivateLightsabreMultipleHits = true;
                        }

                        int EXStabUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_STAB_UPGRADE);
                        if (EXStabUpgradeLVL > 0) {
                            SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_STAB_UPGRADE);
                            ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXStabUpgradeLVL - 1).floatValue();
                        }
                    }
                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    //start Actioning

                    float entityFacingAngle = getEntityHorizontalFacingDeg(entity1);

                    Vector3f tempFrontLoc = new Vector3f(
                            Vector3fMethods.PolarProjectionFromXHorizon(0.7f, entityFacingAngle),
                            0,
                            Vector3fMethods.PolarProjectionFromZHorizon(0.7f, entityFacingAngle));

                    GeoModelSpawnMethods.GenerateEffStabEntity(entity1, entityFacingAngle, 30f,
                            GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_STAB, GeoAnimationAndTextureName.EFF_ENTITY_ANIMATION_NAME_STAB, 2f,
                            8, true, tempFrontLoc.x(), tempFrontLoc.y()+1.4f, tempFrontLoc.z(), true);

                    if (shouldActivateLightsabreMultipleHits) {
                        float newX = Vector3fMethods.PolarProjectionFromXHorizon(1f, entityFacingAngle);
                        float newZ = Vector3fMethods.PolarProjectionFromZHorizon(1f, entityFacingAngle);

                        GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity1, entityFacingAngle, 0, 1, 255, 255, 255, newX, 1.4f, newZ, 8,
                                "textures/entity/skilleff/blademaster/stab_lightsabre_laser/", 6, 1, 1, false, false, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);

                        GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity1, entityFacingAngle, 90, 1, 255, 255, 255, newX, 1.4f, newZ, 8,
                                "textures/entity/skilleff/blademaster/stab_lightsabre_laser/", 6, 1, 1, false, false, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);
                    }

                    int animTicks = 6;
//                    int delayTicks = 10;
                    float finalDist = skill1.getValueUsage_01().get(playerSkillLVL).floatValue();
                    float finalAoeRange = aoeRange;
                    float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
                    int actionPeriod = 1;
                    float finalDistShouldTravelEachTime = finalDist / animTicks / actionPeriod;
                    boolean finalShouldActivateLightsabreMultipleHits = shouldActivateLightsabreMultipleHits;

                    HashSet<LivingEntity> damagedLivingEntityGroup = new HashSet<>();
                    IntegerValueHolder ElapsedTicksHolder = new IntegerValueHolder(0);

                    MethodAction action =
                            (entity2) ->  {
                                boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                                if (isActionInterrupted) {
                                    BLADEMASTER007_HashMapTriggeredTimes.put(entity2, 11);
                                }
                                int ElapsedTicks = ElapsedTicksHolder.getIntCounter() + actionPeriod;
                                ElapsedTicksHolder.setIntCounter(ElapsedTicks);
                                if (ElapsedTicks < animTicks && !isActionInterrupted) {
                                    float entity2FacingAngle = getEntityHorizontalFacingDeg(entity2);
                                    //spawn model
                                    if (ElapsedTicks == 2) {
                                        //play skill sound at target location
                                        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
//                                        PlayRandomSwordWieldingSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                    }

                                    //start damage
                                    if (ElapsedTicks % 2 == 0) {
                                        Vector3f tempVec = new Vector3f(
                                                Vector3fMethods.PolarProjectionFromXHorizon(2f * finalDistShouldTravelEachTime, entity2FacingAngle),
                                                0,
                                                Vector3fMethods.PolarProjectionFromZHorizon(2f * finalDistShouldTravelEachTime, entity2FacingAngle) );

                                        Predicate<Entity> condition;
                                        if (entity2 instanceof ServerPlayer sp1) {
                                            //update client
                                            DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, tempVec, true);
                                            condition = defaultSkillTargetCondition(sp1);
                                        }
                                        else {
                                            condition = defaultSkillTargetCondition();
                                        }
                                        setEntityDeltaMovement(entity2, getEntityDeltaMovement(entity2).add(tempVec.x(), 0f, tempVec.z()));

                                        //Sector damage cond
                                        Vector3f triggeredEntityPos = getEntityPos(entity2);
                                        float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);
                                        Predicate<Entity> SectorDmgCond = (entity) -> {
                                            Vector3f tempVecBehindTriggerEntity = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, -0.5f, triggeredEntityFacingAngle);
//                                            float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontal(getEntityPos(entity), tempVecBehindTriggerEntity));
                                            float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(tempVecBehindTriggerEntity, getEntityPos(entity)));
                                            float range = 75f; //150 deg
//                                            printInGameMsg("sector deg: "+sectorDeg);
//                                            printInGameMsg("+: "+MathMethods.RoundAngle(triggeredEntityFacingAngle + range));
//                                            printInGameMsg("-: "+MathMethods.RoundAngle(triggeredEntityFacingAngle - range));
//                                            printInGameMsg("pass?: " + Vector3fMethods.IsAngleDegBetweenAB(sectorDeg, MathMethods.RoundAngle(triggeredEntityFacingAngle + range), MathMethods.RoundAngle(triggeredEntityFacingAngle - range)));

                                            if (!Vector3fMethods.IsAngleDegBetweenAB(sectorDeg, MathMethods.RoundAngle(triggeredEntityFacingAngle + range), MathMethods.RoundAngle(triggeredEntityFacingAngle - range))) {
                                                return false;
                                            }

                                            return DistanceBetweenEntities(entity, entity2) <= finalAoeRange;
                                        };
                                        for (Entity tempEntity: GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition.and(SectorDmgCond))) {
                                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                                if (!damagedLivingEntityGroup.contains(tempLivingEntity)) {
                                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
//                                                    ParticleMethods.GenerateHitParticle(getEntityX(entity2), getEntityY(entity2)+1.4f, getEntityZ(entity2), 10);

                                                    if (finalShouldActivateLightsabreMultipleHits) {
                                                        float halvedFinalDmg = 0.5f * finalDmg;
                                                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, halvedFinalDmg, false);
                                                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, halvedFinalDmg, false);
                                                        DfoStatusGeneralMethods.ApplyShockStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.GENERAL_DURATION_TICKS_FOR_SHOCK_STATUS, finalDmg, DfoStatusGeneralMethods.GENERAL_MAX_INFLICT_TIMES_FOR_SHOCK_STATUS, false, SkillID);
                                                    }

//                                                    DfoSwdSoundMethods.PlayRandomSwordHitSound(getEntityLevel(entity2), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity), getEntityZ(tempLivingEntity), 0.8f, 1.0f);
                                                    DfoSwdSoundMethods.PlayWeaponTypeStabHitSound(weaponType, getEntityLevel(entity2), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity), getEntityZ(tempLivingEntity), 0.8f, 1.0f);
                                                    damagedLivingEntityGroup.add(tempLivingEntity);
                                                }

                                                float pushXZStrength = calculatePushEntityStrength(2f * finalDistShouldTravelEachTime, entity2, tempLivingEntity);
                                                pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityFacingAngle, pushXZStrength, 0, true);
                                            }
                                        }
                                    }


                                }
                                else {
                                    incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, -1);
//                                    SendPacketIsInActionToClientSideFromServer(entity2, Constants.ACTION_OFF);
                                    SBSTickEventMethods.setSkillActionDone(entity2, SkillID);

//                                    if (BLADEMASTER007_HashMapPassedTicks.get(entity2) >= animTicks + delayTicks) {
//                                        //IMPORTANT! set action off to allow entity to be able to cast skills
//                                        SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
//                                    }
//                                    else {
//                                        BLADEMASTER007_HashMapPassedTicks.put(entity2, BLADEMASTER007_HashMapPassedTicks.get(entity2)+actionPeriod);
//                                    }
                                }
                            };
                    //Actioning ends
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

                    //start loop actioning with fixed period ticks
                    SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
                };

        tempSkillData.setActiveSkillAction1(tempSkillAction);


        //=============================================
        //IMPORTANT: clear value from hashmap once an entity's action is complete
        MethodAction tempSkillCleanAction = (entity1) -> {
            BLADEMASTER007_HashMapTriggeredTimes.remove(entity1);
        };

        tempSkillData.setClearSkillAction(tempSkillCleanAction);
    }
}
