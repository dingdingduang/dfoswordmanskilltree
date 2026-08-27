package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.entity.DfoGeneralEntitySpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.handeff.HandEffGetModelPartMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.MainHandConfigurationMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster003;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster009;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.ClientSkillMethods;
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
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getClientPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.getSkillsInCooldownClientTimerOverlayInstance;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster005 {
    public static final String[] BLADEMASTER_0_5_CONSECUTIVE_SLASH_ANIM = {"consecutive_slash1_instant", "consecutive_slash2_instant", "consecutive_slash3_instant", "consecutive_slash4_instant", "consecutive_slash5_instant", "holding_up_sword_air_end", "consecutive_slash1", "upper_slash_instant2"};
    public static final float[] BLADEMASTER_0_5_CONSECUTIVE_SLASH_ROT = {40f, 315f, 50f, 270f, 90f, 45f, 40f, 135f};
    //Server
    public static HashMap<LivingEntity, Integer> BLADEMASTER005_HashMapTriggeredTimes;
    public static HashMap<LivingEntity, Integer> BLADEMASTER005_HashMapTotalDelayTicks;

    //Client
    public static int BLADEMASTER005_ClientHashMapTriggeredTimes;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);
        int ConsecutiveTimes = skillData.getValueUsage_01().get(correctSkillLevel).intValue();

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d", ConsecutiveTimes)
        );
    }

    public static void init() {
        String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_CONSECUTIVE_SLASH;
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster005::getSkillDescription);

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
                            int ClientPlayerWeaponType = DfoSkillTreeItemMethods.getWeaponType(getClientPlayer());
                            int currentSkillLevel = getClientPlayerSkillID2lvlMap().get(BlademasterSkillTreeInitialization.BLADEMASTER_CONSECUTIVE_SLASH);
                            int consecutive_TotalSlashCount = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_CONSECUTIVE_SLASH).getValueUsage_01().get(currentSkillLevel - 1).intValue();
                            int additionalAttacks = 0;
                            int otherSkillLevel;
                            //is wielding lightsabre
                            if (ClientPlayerWeaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE && (otherSkillLevel = ClientSkillMethods.queryCPlayerCurrentSkillLevel(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY)) >= Blademaster003.BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_2_LVL) {
                                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY).getDamageList_01().get(otherSkillLevel - 1).intValue();
                            }
                            //is wielding katana
                            else if (ClientPlayerWeaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA && (otherSkillLevel = ClientSkillMethods.queryCPlayerCurrentSkillLevel(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY)) >= Blademaster009.BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_2_LVL) {
                                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY).getDamageList_01().get(otherSkillLevel - 1).intValue();
                            }

                            int EXConsecutiveSlashUpgradeLVL = ClientSkillMethods.queryCPlayerCurrentSkillLevel(BlademasterSkillTreeInitialization.EXBLADEMASTER_CONSECUTIVE_SLASH_UPGRADE);
                            if (EXConsecutiveSlashUpgradeLVL > 0) {
                                SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_CONSECUTIVE_SLASH_UPGRADE);
                                additionalAttacks = additionalAttacks + EX_SkillData.getValueUsage_01().get(EXConsecutiveSlashUpgradeLVL - 1).intValue();
                            }

                            HashMap<String, Integer> clientCD = getSkillsInCooldownClientTimerOverlayInstance().getPlayerSkill2CooldownTicks();
                            if (clientCD.get(SkillID2) > 25 || BLADEMASTER005_ClientHashMapTriggeredTimes >= consecutive_TotalSlashCount + additionalAttacks) {
                                isPassed = false;
                            }
                            else {
//                                setClientPlayerInAction(Constants.ACTION_ON);
                                BLADEMASTER005_ClientHashMapTriggeredTimes++;
//                                NetworkingSendMsgMethods.SendPlayerStateDataFromClientSideToServer(DfoSwordmanSkillTreeConstants.CUMULATIVE_ACTION, BLADEMASTER005_ClientHashMapTriggeredTimes);
                            }
                        }
                        else {
//                            setClientPlayerInAction(Constants.ACTION_ON);
                            BLADEMASTER005_ClientHashMapTriggeredTimes = 1;
//                            NetworkingSendMsgMethods.SendPlayerStateDataFromClientSideToServer(DfoSwordmanSkillTreeConstants.CUMULATIVE_ACTION, BLADEMASTER005_ClientHashMapTriggeredTimes);
                        }
                    }
                    return isPassed;
                }
        );
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);

        if (isClientSide()) {
            BLADEMASTER005_ClientHashMapTriggeredTimes = 0;
            return;
        }

        //server init
        if (BLADEMASTER005_HashMapTriggeredTimes == null) {
            BLADEMASTER005_HashMapTriggeredTimes = new HashMap<>();
            BLADEMASTER005_HashMapTotalDelayTicks = new HashMap<>();
        }



        //====================================
        //setup skill action
        MethodAction tempSkillAction =
                (entity1) -> {
                    int playerSkillLVL = 0;
                    float aoeRange;
                    float damageBase;
                    float damageMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

                    //ASSUME BLADEMASTER005_HashMap never be null
                    int triggeredTimes;
                    if (BLADEMASTER005_HashMapTriggeredTimes.containsKey(entity1)) {
                        triggeredTimes = BLADEMASTER005_HashMapTriggeredTimes.get(entity1) + 1;
                        BLADEMASTER005_HashMapTriggeredTimes.put(entity1, triggeredTimes);
                    }
                    else {
                        BLADEMASTER005_HashMapTriggeredTimes.put(entity1, 1);
                        triggeredTimes = 1;
                    }
                    if (!BLADEMASTER005_HashMapTotalDelayTicks.containsKey(entity1)) {
                        BLADEMASTER005_HashMapTotalDelayTicks.put(entity1, 20);
                    }
                    //IMPORTANT! setup action on to prevent casting other skills while this action
                    //being performed
                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);
                    int additionalAttacks = 0;
                    int otherSkillLevel;

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    float ExtraDamageMultiplier = 0;
                    if (entity1 instanceof ServerPlayer sp1) {
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;

                            //is wielding lightsabre
                            if (weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE && (otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY)) >= Blademaster003.BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_2_LVL) {
                                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY).getDamageList_01().get(otherSkillLevel - 1).intValue();
                            }
                            //is wielding katana
                            else if (weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA && (otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY)) >= Blademaster009.BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_2_LVL) {
                                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY).getDamageList_01().get(otherSkillLevel - 1).intValue();
                            }

                            int EXConsecutiveSlashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_CONSECUTIVE_SLASH_UPGRADE);
                            if (EXConsecutiveSlashUpgradeLVL > 0) {
                                SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_CONSECUTIVE_SLASH_UPGRADE);
                                ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXConsecutiveSlashUpgradeLVL - 1).floatValue();
                                additionalAttacks = additionalAttacks + EX_SkillData.getValueUsage_01().get(EXConsecutiveSlashUpgradeLVL - 1).intValue();
                            }
                        }
                    }
                    //return?
                    int consecutive_totalSlashCount = skill1.getValueUsage_01().get(playerSkillLVL).intValue();
                    int total_slashCount = consecutive_totalSlashCount + additionalAttacks;
                    //if client trigger times dont match server side
                    if (triggeredTimes > total_slashCount) {
                        BLADEMASTER005_HashMapTotalDelayTicks.put(entity1, BLADEMASTER005_HashMapTotalDelayTicks.get(entity1) + 30);
                        return;
                    }

                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    Predicate<Entity> condition;
                    float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                    float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                    float damageTickSpeedMultiplier = entityAnimationArr[1];
                    int consecutive_slashArr_index = 0;

                    //play animation
                    if (entity1 instanceof ServerPlayer sp1) {
                        if (triggeredTimes == 1) {
                            //play anim from noaction state
                            consecutive_slashArr_index = BLADEMASTER_0_5_CONSECUTIVE_SLASH_ANIM.length - 2;
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_0_5_CONSECUTIVE_SLASH_ANIM[consecutive_slashArr_index], entityAnimationSpeedMultiplier);
                        }
                        else {
                            if (triggeredTimes == total_slashCount) {
                                if (triggeredTimes == consecutive_totalSlashCount) {
                                    //downward slash
                                    consecutive_slashArr_index = BLADEMASTER_0_5_CONSECUTIVE_SLASH_ANIM.length - 3;
                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_0_5_CONSECUTIVE_SLASH_ANIM[consecutive_slashArr_index], entityAnimationSpeedMultiplier);
                                }
                                else {
                                    //upper slash
                                    consecutive_slashArr_index = BLADEMASTER_0_5_CONSECUTIVE_SLASH_ANIM.length - 1;
                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_0_5_CONSECUTIVE_SLASH_ANIM[consecutive_slashArr_index], entityAnimationSpeedMultiplier);
                                }
                            }
                            else {
                                consecutive_slashArr_index = (triggeredTimes - 1) % 5;
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_0_5_CONSECUTIVE_SLASH_ANIM[consecutive_slashArr_index], entityAnimationSpeedMultiplier);
                            }
                        }
                        condition = defaultSkillTargetCondition(sp1);
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }

                    int startDmgTicks = (int) (2 * damageTickSpeedMultiplier);
                    int endDmgTicks = (int) (6 * damageTickSpeedMultiplier);
//                    int delayTicks = 20;
                    int delayTicks = BLADEMASTER005_HashMapTotalDelayTicks.get(entity1);
                    float finalAoeRange = aoeRange;
                    float finalDist = skill1.getDistance().get(playerSkillLVL).floatValue();
                    float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
//                    int actionPeriod = 2;
//                    float finalDistShouldTravelEachTime = actionPeriod * finalDist / Math.max(1, endDmgTicks - startDmgTicks);
                    int actionPeriod = 1;
                    float finalDistShouldTravelEachTime = finalDist / Math.max(1, endDmgTicks - startDmgTicks);
//                    printInGameMsg("finalDist: "+finalDist);
//                    printInGameMsg("travel: "+finalDistShouldTravelEachTime);
                    int finalConsecutiveSlashArrIndex = consecutive_slashArr_index;

                    HashSet<LivingEntity> damagedGroup = new HashSet<>();
                    BooleanValueHolder spawnEffect = new BooleanValueHolder(false);
                    BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
                    IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);

                    MethodAction action =
                            (entity2) ->  {
                                boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                                if (isActionInterrupted) {
                                    BLADEMASTER005_HashMapTriggeredTimes.put(entity2, DfoSwordmanSkillTreeConstants.CONSTANT_INTEGER_777);
                                }

                                int updatedTick = ElapsedTicks.getIntCounter();

                                if (!isActionInterrupted && updatedTick >= startDmgTicks && updatedTick <= endDmgTicks) {
                                    float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);
                                    //spawn model
                                    if (!spawnEffect.getBooleanVal()) {
                                        spawnEffect.setBooleanVal(true);
                                        //play skill sound at target location
                                        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                        DfoSwdSoundMethods.PlayBlademaster005TripleSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
//                                        GeoModelSpawnMethods.GenerateYellowSlash(entity2, getEntityHorizontalFacingDeg(entity2), 135f);
//                                            GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, triggeredEntityFacingAngle, 45f, 1, 255, 255, 255, 0f, 1.4f, 0f, 180,
//                                                    GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 5, 1, 12, true, true, "slash_start");
                                        final int[] slashColorArr = BlademasterSkillTreeInitialization.BLADEMASTER_SLASH_COLOR[weaponType + 1];
                                        GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, triggeredEntityFacingAngle, BLADEMASTER_0_5_CONSECUTIVE_SLASH_ROT[finalConsecutiveSlashArrIndex], 1, slashColorArr[0], slashColorArr[1], slashColorArr[2], 0f, 1.4f, 0f, Math.max(18, endDmgTicks-startDmgTicks),
                                                GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, ParticleRegistry.CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, true);

//                                        DfoGeneralEntitySpawnMethods.SetupHandEffEntityWithTrailingParticleOnly(entity2, endDmgTicks + 1, 1, MainHandConfigurationMethods.getHandEffMainHandPart(entity2), DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
//                                                2f, 10, 10, slashColorArr[0], slashColorArr[1], slashColorArr[2], 200,true);

                                        //TODO: Particle
//                                        GeoModelSpawnMethods.GenerateYellowSlash(entity2, triggeredEntityFacingAngle, 135f);
                                    }

                                    //move pos
                                    Vector3f tempVec = new Vector3f(
                                            Vector3fMethods.PolarProjectionFromXHorizon(finalDistShouldTravelEachTime, triggeredEntityFacingAngle),
                                            0,
                                            Vector3fMethods.PolarProjectionFromZHorizon(finalDistShouldTravelEachTime, triggeredEntityFacingAngle));

                                    setEntitySPDeltaMovement(entity2, getEntityDeltaMovement(entity2).add(tempVec.x(), 0f, tempVec.z()), false);

                                    //Sector damage cond
                                    Vector3f triggeredEntityPos = getEntityPos(entity2);
                                    Vector3f tempVecBehindTriggerEntity = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, -0.5f, triggeredEntityFacingAngle);
                                    Predicate<Entity> SectorDmgCond = (entity) -> {
                                        float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(tempVecBehindTriggerEntity, getEntityPos(entity)));
                                        float range = 75f; //150 deg

                                        if (!Vector3fMethods.IsAngleDegBetweenAB(sectorDeg, MathMethods.RoundAngle(triggeredEntityFacingAngle + range), MathMethods.RoundAngle(triggeredEntityFacingAngle - range))) {
                                            return false;
                                        }

                                        return DistanceBetweenEntities(entity, entity2) <= finalAoeRange;
                                    };
                                    for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition.and(SectorDmgCond))) {
                                        if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                            if (!damagedGroup.contains(tempLivingEntity)) {
                                                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
//
                                                DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, EntityMethods.getEntityLevel(entity2), EntityMethods.getEntityX(entity2), EntityMethods.getEntityY(entity2), EntityMethods.getEntityZ(entity2), 0.8f, 1.0f);
                                                damagedGroup.add(tempLivingEntity);

                                                if (triggeredTimes == total_slashCount) {
                                                    if (consecutive_totalSlashCount == total_slashCount) {
                                                        float pushStrength = EntityMethods.calculatePushEntityStrength(finalDist * 1.5f, entity2, tempLivingEntity);
                                                        EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityFacingAngle, pushStrength, 0, true);
                                                    }
                                                    else {
                                                        float pushXZStrength = EntityMethods.calculatePushEntityStrength(finalDistShouldTravelEachTime, entity2, tempLivingEntity);
                                                        float pushYStrength = EntityMethods.calculatePushEntityStrengthTargetOnly(0.65f, tempLivingEntity);
                                                        EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityFacingAngle, pushXZStrength, pushYStrength, true);
                                                    }
                                                }
                                                else {
                                                    float pushXZStrength = EntityMethods.calculatePushEntityStrength(finalDist, entity2, tempLivingEntity);
                                                    EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityFacingAngle, pushXZStrength, 0, true);
                                                }
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
                                        if (!SendActionOffPacket.getBooleanVal() && updatedTick >= endDmgTicks + actionPeriod) {
                                            SendActionOffPacket.setBooleanVal(true);
                                            incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
//                                            SendPacketIsInActionToClientSideFromServer(entity2, Constants.ACTION_OFF);
                                        }
                                        if (updatedTick >= endDmgTicks + delayTicks) {
                                            SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                                        }
                                    }
                                }


                                //update val
                                ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
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
            BLADEMASTER005_HashMapTriggeredTimes.remove(entity1);
            BLADEMASTER005_HashMapTotalDelayTicks.remove(entity1);

//            if (BLADEMASTER005_HashMapPassedTicks.size() <= 0) {
//                BLADEMASTER005_HashMapTotalDelayTicks = new HashMap<>();
//                BLADEMASTER005_HashMapDamagedEntityGroup = new HashMap<>();
//                BLADEMASTER005_HashMapTriggeredTimes = new HashMap<>();
//                BLADEMASTER005_HashMapPassedTicks = new HashMap<>();
//            }
        };

        tempSkillData.setClearSkillAction(tempSkillCleanAction);
    }
}
