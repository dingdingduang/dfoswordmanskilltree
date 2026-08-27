package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi.DrawSwordQiEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster003;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster009;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster011;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
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
import net.dingdingduang.somebasicskills.networking.NetworkingFetchMsgMethods;
import net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.HashSetGroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getClientPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.getSkillsInCooldownClientTimerOverlayInstance;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster006 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_DRAW_SWORD_QI;
    public static final String BLADEMASTER_006_PREPARATION = BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_DRAW_SWORD_QI + "_PREPARATION";
    public static final String BLADEMASTER_006_BLUDGEON_SHOCKWAVE = "BLADEMASTER_006_BLUDGEON_SHOCKWAVE";
    public static final String BLADEMASTER_006_SHORT_SWORD_QI_EXTEND_ACTION = "BLADEMASTER_006_SHORT_SWORD_QI_EXTEND_ACTION";

    public static final String BLADEMASTER_006_DRAW_SWORD_QI_START_ANIMATION_NAME = "drawsword_start2";
    public static final String BLADEMASTER_006_DRAW_SWORD_QI_CHANNELING_ANIMATION_NAME = "drawsword_channeling2";
    public static final String BLADEMASTER_006_DRAW_SWORD_QI_SLASH_ANIMATION_NAME = "drawsword_slash2";

    public static final String BLADEMASTER_006_DRAW_SWORD_QI_BLUE_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_sword_qi/blue/";
    public static final String BLADEMASTER_006_DRAW_SWORD_QI_RED_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_sword_qi/red/";
    public static final String BLADEMASTER_006_DRAW_SWORD_QI_BLUE_SHORTSWORD_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_sword_qi/blue_shortsword/";

    public static final float BLADEMASTER_006_DECAY_RATIO = 0.5f;

    public static final int BLADEMASTER_006_ZANBATO_MAX_CHARGING_TICKS = 40;
    public static final int BLADEMASTER_006_ZANBATO_TICKS_PLAY_CHARGING_MAX_OUT = BLADEMASTER_006_ZANBATO_MAX_CHARGING_TICKS - 5;

    //Server
    public static HashMap<LivingEntity, Integer> BLADEMASTER006_HashMapTriggeredTimes;
    public static HashMap<LivingEntity, Integer> BLADEMASTER006_HashMapRemainingTickBeforeInitiate;

//    public static final int BLADEMASTER006_PreparationTime = 10;

    //Client
    public static int BLADEMASTER006_ClientHashMapTriggeredTimes;
//    public static float BLADEMASTER006_ClientAOE = 5f;
//
//    public static String BLADEMASTER_006_INCREASING_SWORD_QI_SHORT_SWORD_METHOD = "blademaster006increasingswordqishortswordmethod";

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
        Double PreparationTime = skillData.getStationaryTime().get(correctSkillLevel);
        int ConsecutiveTimes = skillData.getValueUsage_01().get(correctSkillLevel).intValue();

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%s", AOE),
                String.format("%ss", PreparationTime),
                String.format("%d", ConsecutiveTimes)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster006::getSkillDescription);

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
                            int currentSkillLevel = getClientPlayerSkillID2lvlMap().get(SkillID);
                            int consecutive_TotalSlashCount = getID2SkillData().get(SkillID).getValueUsage_01().get(currentSkillLevel - 1).intValue();
                            int additionalAttacks = 0;
                            int otherSkillLevel;
                            //is wielding lightsabre
                            if (ClientPlayerWeaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE && (otherSkillLevel = ClientSkillMethods.queryCPlayerCurrentSkillLevel(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY)) >= Blademaster003.BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_2_LVL) {
                                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY).getDamageList_02().get(otherSkillLevel - 1).intValue();
                            }
                            //is wielding katana
                            else if (ClientPlayerWeaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA && (otherSkillLevel = ClientSkillMethods.queryCPlayerCurrentSkillLevel(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY)) >= Blademaster009.BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_2_LVL) {
                                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY).getDamageList_02().get(otherSkillLevel - 1).intValue();
                            }

                            int exAdditionalStationaryTicks = 0;
                            int EXDrawSwordQiUpgradeLVL = ClientSkillMethods.queryCPlayerCurrentSkillLevel(BlademasterSkillTreeInitialization.EXBLADEMASTER_DRAW_SWORD_QI_UPGRADE);
                            if (EXDrawSwordQiUpgradeLVL > 0) {
                                SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_DRAW_SWORD_QI_UPGRADE);
                                additionalAttacks = additionalAttacks + EX_SkillData.getValueUsage_01().get(EXDrawSwordQiUpgradeLVL - 1).intValue();
                                exAdditionalStationaryTicks = (int) (20 * EX_SkillData.getStationaryTime().get(EXDrawSwordQiUpgradeLVL - 1).floatValue());
                            }

                            int stationaryTime = (int) (20 * getID2SkillData().get(SkillID).getStationaryTime().get(currentSkillLevel - 1).floatValue()) + exAdditionalStationaryTicks;
                            int maxTimeWaiting = (int) (2.5f * stationaryTime);

                            HashMap<String, Integer> clientCD = getSkillsInCooldownClientTimerOverlayInstance().getPlayerSkill2CooldownTicks();

                            if ((BLADEMASTER006_ClientHashMapTriggeredTimes == 1 && (clientCD.get(SkillID2) < stationaryTime || clientCD.get(SkillID2) > maxTimeWaiting) ) || (BLADEMASTER006_ClientHashMapTriggeredTimes != 1 && clientCD.get(SkillID2) > maxTimeWaiting) || (BLADEMASTER006_ClientHashMapTriggeredTimes >= consecutive_TotalSlashCount + additionalAttacks) ) {
                                isPassed = false;
                            }
                            else {
                                BLADEMASTER006_ClientHashMapTriggeredTimes++;
                            }
                        }
                        else {
                            BLADEMASTER006_ClientHashMapTriggeredTimes = 1;
                        }
                    }

                    return isPassed;
                }
        );
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);

        if (isClientSide()) {
            BLADEMASTER006_ClientHashMapTriggeredTimes = 0;
            return;
        }

        //server init
        if (BLADEMASTER006_HashMapTriggeredTimes == null) {
            BLADEMASTER006_HashMapTriggeredTimes = new HashMap<>();
            BLADEMASTER006_HashMapRemainingTickBeforeInitiate = new HashMap<>();
        }



        //====================================
        //setup skill action
        MethodAction tempChannelingAction =
                (entity1) -> {
                    int tempTotalTriggeredTimes = 1;

                    float stationarySec;
                    int stationaryTime = 20;

                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);
                    boolean isWieldingZanbato = weaponType == DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO;

                    if (BLADEMASTER006_HashMapTriggeredTimes.containsKey(entity1)) {
                        Integer tempInt1 = BLADEMASTER006_HashMapRemainingTickBeforeInitiate.get(entity1);
                        if (tempInt1 != null) {
                            int tempIntPrim1 = tempInt1;
                            if (tempIntPrim1 <= 0) {
                                int totalTriggeredTimes = BLADEMASTER006_HashMapTriggeredTimes.get(entity1);
                                //if is interrupted
                                if (totalTriggeredTimes >= 777) { return; }
                                tempTotalTriggeredTimes = totalTriggeredTimes + 1;
                                BLADEMASTER006_HashMapTriggeredTimes.put(entity1, tempTotalTriggeredTimes);
                                if (entity1 instanceof ServerPlayer sp1) {
                                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardKidnapFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                                }
                            }
                        }
                    }
                    else {
                        if (entity1 instanceof ServerPlayer sp1) {
                            stationarySec = getID2SkillData().get(SkillID).getStationaryTime().get(getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID) - 1).floatValue();
//                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER_006_DRAW_SWORD_QI_START_ANIMATION_NAME);
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_006_DRAW_SWORD_QI_START_ANIMATION_NAME, 1f / stationarySec);
                            DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardKidnapFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);

                            int exAdditionalStationaryTicks = 0;
                            int EXDrawSwordQiUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_DRAW_SWORD_QI_UPGRADE);
                            if (EXDrawSwordQiUpgradeLVL > 0) {
                                SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_DRAW_SWORD_QI_UPGRADE);
//                                additionalAttacks = additionalAttacks + EX_SkillData.getValueUsage_01().get(EXDrawSwordQiUpgradeLVL - 1).intValue();
                                exAdditionalStationaryTicks = (int) (20 * EX_SkillData.getStationaryTime().get(EXDrawSwordQiUpgradeLVL - 1).floatValue());
                            }
                            stationaryTime = (int) (stationarySec * 20) + exAdditionalStationaryTicks;
                        }
                        BLADEMASTER006_HashMapTriggeredTimes.put(entity1, 1);
//                        BLADEMASTER006_HashMapRemainingTickBeforeInitiate.put(entity1, BLADEMASTER006_PreparationTime);
                        BLADEMASTER006_HashMapRemainingTickBeforeInitiate.put(entity1, stationaryTime);
                    }
                    //IMPORTANT! setup action on to prevent casting other skills while this action
                    //being performed
                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    int actionPeriod = 5;
                    int channelingTickDelay = 5;
                    final int finalTriggeredTimes = tempTotalTriggeredTimes;
                    final int finalStationaryTime = stationaryTime;
                    IntegerValueHolder ElapsedTick = new IntegerValueHolder(0);
                    BooleanValueHolder channelingSpawnEffect = new BooleanValueHolder(false);
                    BooleanValueHolder beforeInitiateActionExecutedYet = new BooleanValueHolder(false);
                    IntegerValueHolder TotalChannelingTicksHolder = new IntegerValueHolder(0);

                    MethodAction action = (entity2) -> {
                        Integer tempInt2 = BLADEMASTER006_HashMapRemainingTickBeforeInitiate.get(entity2);
                        int updatedTick = ElapsedTick.getIntCounter();

                        if (tempInt2 != null) {
                            int tempIntPrim2 = tempInt2;

                            boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                            if (isActionInterrupted) {
                                if (entity2 instanceof ServerPlayer sp1) {
                                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                                }
                                incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                                BLADEMASTER006_HashMapTriggeredTimes.put(entity2, DfoSwordmanSkillTreeConstants.CONSTANT_INTEGER_777);
                                SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_006_PREPARATION);
                            }
                            else {
                                if (tempIntPrim2 > 0) {
                                    BLADEMASTER006_HashMapRemainingTickBeforeInitiate.put(entity2, tempIntPrim2 - actionPeriod);
                                }
                                else {
                                    if (!beforeInitiateActionExecutedYet.getBooleanVal()) {
                                        beforeInitiateActionExecutedYet.setBooleanVal(true);
                                        updatedTick = updatedTick - finalStationaryTime;
                                    }
                                    if (isWieldingZanbato) {
                                        int channelingTotalTicks = TotalChannelingTicksHolder.getIntCounter();
                                        boolean shouldInterrupt = false;

                                        if (!ServerSkillMethods.isLivingEntityInChannelingAction(entity2) || (shouldInterrupt = ( DfoSkillTreeItemMethods.getWeaponType(entity2)) != DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO )  ) {
                                            SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_006_PREPARATION);
                                            if (shouldInterrupt) {
                                                if (entity2 instanceof ServerPlayer sp1) {
                                                    NetworkingFetchMsgMethods.FetchPlayerResetLastKeyActionFromServer(sp1, BLADEMASTER_006_PREPARATION);
                                                }
                                            }
//                                            printInGameMsg("execute charging!!!");

                                            Blademaster006_executeDrawSwordQiAction(entity2, 0, channelingTotalTicks);
                                        }
                                        else if (updatedTick <= channelingTotalTicks + channelingTickDelay) {
                                            TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 30, 0.1f, Constants.OP_ADDITION, false, BLADEMASTER_006_PREPARATION, null, null);
                                            TotalChannelingTicksHolder.setIntCounter(channelingTotalTicks + actionPeriod);

//                                            printInGameMsg("channelingTotalTicksPrim: "+channelingTotalTicksPrim);
                                            if (channelingTotalTicks == BLADEMASTER_006_ZANBATO_TICKS_PLAY_CHARGING_MAX_OUT) {
                                                DfoSwdSoundMethods.PlayMaxChannelingReadySound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1f);
                                            }
                                            if (!channelingSpawnEffect.getBooleanVal()) {
                                                if (entity2 instanceof ServerPlayer sp1) {
                                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER_006_DRAW_SWORD_QI_CHANNELING_ANIMATION_NAME);
                                                }
                                                channelingSpawnEffect.setBooleanVal(true);
                                                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity2, 255, 255, 255, 1f, 0, 1.4f, 0, BLADEMASTER_006_ZANBATO_MAX_CHARGING_TICKS, GeoAnimationAndTextureName.EFF_ENTITY_TEX_COMMON_CHARGING_ALL, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                                        DfoSwordmanSkillTreeConstants.NO_ANIMATION, BLADEMASTER_006_ZANBATO_MAX_CHARGING_TICKS, 0, 18, false, false, true, DfoSwordmanSkillMethods.RemoveChannelingEffEntity(entity2), 5, true);
                                            }
                                        }
                                    }
                                    else {
                                        Blademaster006_executeDrawSwordQiAction(entity2, finalTriggeredTimes, 0);
                                        SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_006_PREPARATION);
                                    }
                                }
                            }
                        }

                        ElapsedTick.setIntCounter(updatedTick + actionPeriod);
                    };
                    SBSTickEventMethods.setMethodActionTimer(entity1, BLADEMASTER_006_PREPARATION, actionPeriod, action);
                };

        tempSkillData.setChannelingSkillAction1(tempChannelingAction);

        tempSkillData.setActiveSkillAction1(DfoSwordmanSkillMethods::DoNothing);
    }

    public static void Blademaster006_cleanSkillAction(LivingEntity entity1) {
        if (!isLivingEntityInChannelingAction(entity1)) {
            BLADEMASTER006_HashMapTriggeredTimes.remove(entity1);
            BLADEMASTER006_HashMapRemainingTickBeforeInitiate.remove(entity1);
        }
    }

    public static void Blademaster006_executeDrawSwordQiAction(LivingEntity entity1, int triggeredTimes, int totalChargedTicks) {
        float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

        Predicate<Entity> condition;
        float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
        float entityAnimationSpeedMultiplier = entityAnimationArr[0];
        float damageTickSpeedMultiplier = entityAnimationArr[1];

        int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);
//        boolean isWieldingLightsabre = weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE;
        boolean isWieldingKatana = weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA;
        boolean isWieldingShortSword = weaponType == DfoSkillTreeItemMethods.IS_AVERAGE_SHORT_SWORD;
        boolean isWieldingBludgeon = weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON;
        boolean shouldTriggerShockwave = false;
        int additionalAttacks = 0;
        int otherSkillLevel;
        int playerSkillLVL = 0;
        int ShortSwordMasteryLevel = 0;
        int BludgeonMasteryLevel = 0;
        int ZanbatoMasteryLevel = 0;
        float chargedDamageMultiplier;

        SkillDataJson skill1 = getID2SkillData().get(SkillID);

        float ExtraDamageMultiplier = 0;
        boolean hasExDecayRatioDamage = false;
        float ExDecayRatioDamage = 0;
        if (entity1 instanceof ServerPlayer sp1) {
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                if (playerSkillLVL <= 0) { return; }
                playerSkillLVL = playerSkillLVL - 1;
            }

            //is wielding lightsabre
            if (weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE && (otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY)) >= Blademaster003.BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_2_LVL) {
                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY).getDamageList_02().get(otherSkillLevel - 1).intValue();
            }
            //is wielding katana
            else if (isWieldingKatana && (otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY)) >= Blademaster009.BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_2_LVL) {
                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY).getDamageList_02().get(otherSkillLevel - 1).intValue();
            }

            ShortSwordMasteryLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_SHORT_SWORD_MASTERY);
            ZanbatoMasteryLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);

            if (isWieldingBludgeon && (BludgeonMasteryLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY) ) > 0) {
//                BludgeonMasteryLevel = BludgeonMasteryLevel - 1;
                shouldTriggerShockwave = true;
            }

            int EXDrawSwordQiUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_DRAW_SWORD_QI_UPGRADE);
            if (EXDrawSwordQiUpgradeLVL > 0) {
                SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_DRAW_SWORD_QI_UPGRADE);
                ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXDrawSwordQiUpgradeLVL - 1).floatValue();
                additionalAttacks = additionalAttacks + EX_SkillData.getValueUsage_01().get(EXDrawSwordQiUpgradeLVL - 1).intValue();
                hasExDecayRatioDamage = true;
                ExDecayRatioDamage = EX_SkillData.getValueMultiplierList_01().get(EXDrawSwordQiUpgradeLVL - 1).floatValue();
            }

            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_006_DRAW_SWORD_QI_SLASH_ANIMATION_NAME, entityAnimationSpeedMultiplier);
            condition = defaultSkillTargetCondition(sp1);
        }
        else {
            condition = defaultSkillTargetCondition();
        }

        int consecutive_totalSlashCount = skill1.getValueUsage_01().get(playerSkillLVL).intValue();
        int total_slashCount = consecutive_totalSlashCount + additionalAttacks;
        // if times doesn't match with server side
        if (triggeredTimes > total_slashCount) {
            if (entity1 instanceof ServerPlayer sp1) {
                DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
            }
            incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, -1);
            BLADEMASTER006_HashMapTriggeredTimes.put(entity1, DfoSwordmanSkillTreeConstants.CONSTANT_INTEGER_777);
            return;
        }

        if (ZanbatoMasteryLevel >= 3) {
            float tempChargingPercent = 1f * Math.min(BLADEMASTER_006_ZANBATO_MAX_CHARGING_TICKS, totalChargedTicks) / BLADEMASTER_006_ZANBATO_MAX_CHARGING_TICKS;
            SkillDataJson ZanbatoMasterySkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);
            chargedDamageMultiplier = ZanbatoMasterySkillData.getDamageMultiplierList_01().get(ZanbatoMasteryLevel - 1).floatValue() * tempChargingPercent;
            if (ZanbatoMasteryLevel >= 9) {
                chargedDamageMultiplier = chargedDamageMultiplier + ZanbatoMasterySkillData.getDamageMultiplierList_03().get(ZanbatoMasteryLevel - 1).floatValue();
            }
        }
        else {
            chargedDamageMultiplier = 0;
        }

        float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
        float damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
        float damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

        int actionPeriod = 2;

        int startDmgTicks = (int) (4 * damageTickSpeedMultiplier);
        int endDmgTicks = (int) (6 * damageTickSpeedMultiplier);

        int delayTicks = 30;
        float tempDecayRatio = (hasExDecayRatioDamage) ? ExtraMathMethods.clamp(1 - ExDecayRatioDamage, 0, 1) : BLADEMASTER_006_DECAY_RATIO;
        float decayRatio = (float) Math.pow(tempDecayRatio, triggeredTimes - 1);
        float finalDmg = (float) (( (damageMultiplier + chargedDamageMultiplier) * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier) * decayRatio;

        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 1f, 1f);
        DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.8f, 2f);

        DrawSwordQiEntity SwordQiEffEntity;
        MethodAction action;
        if (isWieldingShortSword && ShortSwordMasteryLevel >= 7) {
            float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity1);
            float newX = Vector3fMethods.PolarProjectionFromXHorizon(0.5f, triggeredEntityFacingAngle);
            float newZ = Vector3fMethods.PolarProjectionFromZHorizon(0.5f, triggeredEntityFacingAngle);
            float shortSwordDrawSwordAoe = 2 * aoeRange;
            SwordQiEffEntity = GeoModelSpawnMethods.GenerateDrawSwordQiEntity(entity1, getEntityHorizontalFacingDeg(entity1), 0f,aoeRange * 0.4f, aoeRange, 2f, newX, 1.6f, newZ, 60,
                    BLADEMASTER_006_DRAW_SWORD_QI_BLUE_SHORTSWORD_TEXTURE_PATH, GeoAnimationAndTextureName.Rotate_2D_FLAT_ENTITY_360_CLOCKWISE_IN_5_TICKS,
                    entityAnimationSpeedMultiplier, 7, 20, 1, true, true, false, null, 777, true);

            action = Blademaster006_shortSwordQiDamageAction(entity1, SwordQiEffEntity, condition, startDmgTicks, endDmgTicks, delayTicks, aoeRange, finalDmg, actionPeriod);

//            SwordQiEffEntity = GeoModelSpawnMethods.GenerateDrawSwordQiEntity(entity1, getEntityHorizontalFacingDeg(entity1), 0f,shortSwordDrawSwordAoe * 0.4f, shortSwordDrawSwordAoe, 2f, newX, 1.6f, newZ, 60,
//                    BLADEMASTER_006_DRAW_SWORD_QI_BLUE_SHORTSWORD_TEXTURE_PATH, GeoAnimationAndTextureName.Rotate_2D_FLAT_ENTITY_360_CLOCKWISE_IN_5_TICKS,
//                    entityAnimationSpeedMultiplier, 7, 20, 1, true, true, false, null, 777, true);
//
//            action = Blademaster006_shortSwordQiDamageAction(entity1, SwordQiEffEntity, condition, startDmgTicks, endDmgTicks, delayTicks, shortSwordDrawSwordAoe, finalDmg, actionPeriod);
        }
        else {
            final int finalBludgeonMasteryLevel = BludgeonMasteryLevel;

            GeoModelSpawnMethods.GenerateDrawSwordQiEntity(entity1, getEntityHorizontalFacingDeg(entity1), 0f,aoeRange * 0.4f, aoeRange, 2f, 0, 1.6f, 0, endDmgTicks + 1,
                    (isWieldingKatana) ? BLADEMASTER_006_DRAW_SWORD_QI_RED_TEXTURE_PATH : BLADEMASTER_006_DRAW_SWORD_QI_BLUE_TEXTURE_PATH, GeoAnimationAndTextureName.Rotate_2D_FLAT_ENTITY_360_CLOCKWISE_IN_5_TICKS,
                    entityAnimationSpeedMultiplier, 3, 3, 3, false, true, true, DfoSwordmanSkillMethods.RemoveEffEntityIfActionInterrupted(entity1), 5, true);

            action = Blademaster006_normalSwordQiDamageAction(finalBludgeonMasteryLevel, shouldTriggerShockwave, condition, startDmgTicks, endDmgTicks, delayTicks, aoeRange, finalDmg, actionPeriod);
        }

        //start loop actioning with fixed period ticks
        SBSTickEventMethods.setMethodActionTimerWithCleanAction(entity1, SkillID, actionPeriod, action, Blademaster006::Blademaster006_cleanSkillAction, true);
    }

    public static MethodAction Blademaster006_shortSwordQiDamageAction(LivingEntity dealer, DrawSwordQiEntity SwordQiEffEntity, Predicate<Entity> condition, int startDmgTicks, int endDmgTicks, int delayTicks, float aoeRange, float finalDmg, int actionPeriod) {
        HashSet<LivingEntity> damagedGroup = new HashSet<>();
        IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
        BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);

        MethodAction action =
                (entity3) -> {
                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity3);
//                    if (isActionInterrupted) {
//                        BLADEMASTER006_HashMapTriggeredTimes.put(entity3, DfoSwordmanSkillTreeConstants.CONSTANT_INTEGER_777);
//                    }
                    int updatedTick = ElapsedTicks.getIntCounter();

                    if (!isActionInterrupted && updatedTick >= startDmgTicks && updatedTick <= endDmgTicks) {
                        Vector3f triggeredEntityPos = getEntityPos(entity3);
                        float ExtendSwordQiAoe = aoeRange * (1f + (1f * (SwordQiEffEntity.getMaxLifetime() - SwordQiEffEntity.getLifetime())) / SwordQiEffEntity.getMaxLifetime() );

                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity3, ExtendSwordQiAoe, condition)) {
                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                if (!damagedGroup.contains(tempLivingEntity)) {
                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity3, tempLivingEntity, finalDmg, false);

                                    DfoSwdSoundMethods.PlayShortSwordHitSound(EntityMethods.getEntityLevel(entity3), EntityMethods.getEntityX(entity3), EntityMethods.getEntityY(entity3), EntityMethods.getEntityZ(entity3), 0.8f, 1.0f);
                                    damagedGroup.add(tempLivingEntity);

                                    float correctDamagedEntityAngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(triggeredEntityPos, getEntityPos(tempLivingEntity));
                                    float pushStrength = EntityMethods.calculatePushEntityStrength(0.35f, entity3, tempLivingEntity);
                                    EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, correctDamagedEntityAngleBtn, pushStrength, 0, true);
                                }
                            }
                        }
                    }
                    else {
                        if (isActionInterrupted) {
                            BLADEMASTER006_HashMapTriggeredTimes.put(entity3, DfoSwordmanSkillTreeConstants.CONSTANT_INTEGER_777);
                            if (entity3 instanceof ServerPlayer sp1) {
                                DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                            }
                            SendActionOffPacket.setBooleanVal(true);
                            incrementEntityStateByAmountX(entity3, Constants.IS_IN_ACTION, -1);
                            SBSTickEventMethods.setSkillActionDone(entity3, SkillID);
                        } else {
                            if (!SendActionOffPacket.getBooleanVal() && updatedTick >= endDmgTicks + actionPeriod) {
                                if (entity3 instanceof ServerPlayer sp1) {
                                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                                }
                                SendActionOffPacket.setBooleanVal(true);
                                incrementEntityStateByAmountX(entity3, Constants.IS_IN_ACTION, -1);
//                                            SendPacketIsInActionToClientSideFromServer(entity3, Constants.ACTION_OFF);
                                Blademaster006_shortSwordQiExtendDamageAction(dealer, SwordQiEffEntity, condition, aoeRange, finalDmg * 0.5f, BLADEMASTER_006_SHORT_SWORD_QI_EXTEND_ACTION + ExtraMathMethods.randomInt(100));
                            }
                            if (updatedTick >= endDmgTicks + delayTicks) {
                                SBSTickEventMethods.setSkillActionDone(entity3, SkillID);
                            }
                        }
                    }


                    //update val
                    ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
                };
        return action;
    }

    public static void Blademaster006_shortSwordQiExtendDamageAction(LivingEntity dealer, DrawSwordQiEntity SwordQiEffEntity, Predicate<Entity> condition, float aoeRange, float finalDmg, String ActionID) {
        HashSet<LivingEntity> damagedGroup = new HashSet<>();
//        Sets.difference()
        Predicate<Entity> isNotDealer = (entity) -> !entity.equals(dealer);
        Predicate<Entity> finalCondition = condition.and(isNotDealer);

        MethodAction action =
                (entity3) -> {
                    if (SwordQiEffEntity.getLifetime() > 0) {
                        float ExtendSwordQiAoe = aoeRange * (1f + (2f * (SwordQiEffEntity.getMaxLifetime() - SwordQiEffEntity.getLifetime())) / SwordQiEffEntity.getMaxLifetime() );
                        Vector3f triggeredSwordQiEntityPos = getEntityPos(SwordQiEffEntity);
                        HashSet<Entity> differenceSetOfInnerOuterCircle = HashSetGroupEnumEntitiesInRangeFilter(SwordQiEffEntity, ExtendSwordQiAoe, finalCondition);
                        differenceSetOfInnerOuterCircle.removeAll(HashSetGroupEnumEntitiesInRangeFilter(SwordQiEffEntity, MathMethods.max(0f, ExtendSwordQiAoe - 3f), finalCondition));

//                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(SwordQiEffEntity, ExtendSwordQiAoe, finalCondition)) {
                        for (Entity tempEntity : differenceSetOfInnerOuterCircle) {
                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                if (!damagedGroup.contains(tempLivingEntity)) {
                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity3, tempLivingEntity, finalDmg, false);

                                    DfoSwdSoundMethods.PlayWaveSwordHitSound(EntityMethods.getEntityLevel(entity3), EntityMethods.getEntityX(entity3), EntityMethods.getEntityY(entity3), EntityMethods.getEntityZ(entity3), 0.8f, 1.0f);
                                    damagedGroup.add(tempLivingEntity);

                                    float correctDamagedEntityAngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(triggeredSwordQiEntityPos, getEntityPos(tempLivingEntity));
                                    float pushStrength = EntityMethods.calculatePushEntityStrength(0.35f, entity3, tempLivingEntity);
                                    EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, correctDamagedEntityAngleBtn, pushStrength, 0, true);
                                }
                            }
                        }
                    }
                    else {
                        SBSTickEventMethods.setSkillActionDone(entity3, ActionID);
                    }
                };

        SBSTickEventMethods.setMethodActionTimerWithCleanAction(dealer, ActionID, 1, action, null, true);
    }

    public static MethodAction Blademaster006_normalSwordQiDamageAction(int BludgeonMasteryLevel, boolean shouldTriggerShockwave, Predicate<Entity> condition, int startDmgTicks, int endDmgTicks, int delayTicks, float aoeRange, float finalDmg, int actionPeriod) {
        HashSet<LivingEntity> damagedGroup = new HashSet<>();
        IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
        BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);

        float BludgeonShockwaveDamageMultiplier = (shouldTriggerShockwave) ? getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY).getValueMultiplierList_02().get(Math.max(0, BludgeonMasteryLevel - 1)).floatValue() : 0;

        MethodAction action =
                (entity3) -> {
                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity3);
//                    if (isActionInterrupted) {
//                        BLADEMASTER006_HashMapTriggeredTimes.put(entity3, DfoSwordmanSkillTreeConstants.CONSTANT_INTEGER_777);
//                    }
                    int updatedTick = ElapsedTicks.getIntCounter();

                    if (!isActionInterrupted && updatedTick >= startDmgTicks && updatedTick <= endDmgTicks) {
                        Vector3f triggeredEntityPos = getEntityPos(entity3);

                        int weaponType2 = DfoSkillTreeItemMethods.getWeaponType(entity3);
                        boolean isWieldingLightsabre = weaponType2 == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE;
                        boolean isWieldingKatana = weaponType2 == DfoSkillTreeItemMethods.IS_FAST_KATANA;
                        boolean isWieldingBludgeon = weaponType2 == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON;
                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity3, aoeRange, condition)) {
                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                if (!damagedGroup.contains(tempLivingEntity)) {
                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity3, tempLivingEntity, finalDmg, false);

                                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType2, EntityMethods.getEntityLevel(entity3), EntityMethods.getEntityX(entity3), EntityMethods.getEntityY(entity3), EntityMethods.getEntityZ(entity3), 0.8f, 1.0f);
                                    damagedGroup.add(tempLivingEntity);

                                    if (isWieldingLightsabre) {
                                        DfoStatusGeneralMethods.ApplyShockStatusToTargetLivingEntity(entity3, tempLivingEntity, DfoStatusGeneralMethods.GENERAL_DURATION_TICKS_FOR_SHOCK_STATUS, finalDmg, DfoStatusGeneralMethods.GENERAL_MAX_INFLICT_TIMES_FOR_SHOCK_STATUS, true, SkillID);
                                    }
                                    else if (isWieldingKatana) {
                                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity3, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg);
                                    }

                                    if (isWieldingBludgeon && shouldTriggerShockwave && BludgeonMasteryLevel >= Blademaster011.BLADEMASTER011_BLUDGEON_MASTERY_BONUS_EFFECT_2_LVL) {
                                        float pushYStrength = MathMethods.max(0.5f, calculatePushEntityStrength(1f, entity3, tempLivingEntity));
                                        EntityMethods.setEntitySPDeltaMovement(tempLivingEntity, 0f, pushYStrength, 0f, true);

                                        HashSet<LivingEntity> GroundQuakeDamagedGroup = new HashSet<>();
//                                        GroundQuakeDamagedGroup.add(tempLivingEntity);
                                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(tempLivingEntity, 135,206,250, 1f, 0, 1.4f, 0, 300, GeoAnimationAndTextureName.BLUDGEON_HIT_SPIRAL, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_CLOCKWISE_1080,
                                                DfoSwordmanSkillTreeConstants.NO_ANIMATION, 5, 5, 1, true, true, true, DfoSwordmanSkillMethods.BludgeonFlipShockwave1(BLADEMASTER_006_BLUDGEON_SHOCKWAVE, entity3, 2f, finalDmg * BludgeonShockwaveDamageMultiplier, condition, GroundQuakeDamagedGroup), 5, true);
                                    }
                                    else {
                                        float correctDamagedEntityAngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(triggeredEntityPos, getEntityPos(tempLivingEntity));
                                        float pushStrength = EntityMethods.calculatePushEntityStrength(0.35f, entity3, tempLivingEntity);
                                        EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, correctDamagedEntityAngleBtn, pushStrength, 0, true);
                                    }
                                }
                            }
                        }
                    } else {
                        if (isActionInterrupted) {
                            BLADEMASTER006_HashMapTriggeredTimes.put(entity3, DfoSwordmanSkillTreeConstants.CONSTANT_INTEGER_777);
                            if (entity3 instanceof ServerPlayer sp1) {
                                DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                            }
                            SendActionOffPacket.setBooleanVal(true);
                            incrementEntityStateByAmountX(entity3, Constants.IS_IN_ACTION, -1);
                            SBSTickEventMethods.setSkillActionDone(entity3, SkillID);
                        } else {
                            if (!SendActionOffPacket.getBooleanVal() && updatedTick >= endDmgTicks + actionPeriod) {
                                if (entity3 instanceof ServerPlayer sp1) {
                                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                                }
                                SendActionOffPacket.setBooleanVal(true);
                                incrementEntityStateByAmountX(entity3, Constants.IS_IN_ACTION, -1);
//                                            SendPacketIsInActionToClientSideFromServer(entity3, Constants.ACTION_OFF);
                            }
                            if (updatedTick >= endDmgTicks + delayTicks) {
                                SBSTickEventMethods.setSkillActionDone(entity3, SkillID);
                            }
                        }
                    }


                    //update val
                    ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
                };

        return action;
    }
}
