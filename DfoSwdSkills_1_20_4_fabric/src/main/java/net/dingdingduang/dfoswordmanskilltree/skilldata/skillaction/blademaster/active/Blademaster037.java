package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.FloatValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.TypeValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster037 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_ULTIMATE_SWORD_ART_TELEKINETIC_SWORDS;

    public static final String BLADEMASTER_037_PREPARATION = BlademasterSkillTreeInitialization.BLADEMASTER_ULTIMATE_SWORD_ART_TELEKINETIC_SWORDS + "_PREPARATION";
    public static final String BLADEMASTER_037_SLASH_ACTION = "BLADEMASTER_037_SLASH_ACTION";
    public static final String BLADEMASTER_037_FINAL_DAMAGE_ACTION = "BLADEMASTER_037_FINAL_DAMAGE_ACTION";

    //    public static final String BLADEMASTER_037_SHOOTING_SWORD_ANIMATION_STOP_NAME = "shooting_sword0";
    public static final String BLADEMASTER_037_SHOOTING_SWORD_ANIMATION_NAME = "shooting_sword2";

    public static final int BLADEMASTER_037_SWORD_DROPPING_DELAY_TICKS = 10;
    public static final float BLADEMASTER_037_SWORD_HEIGHT_INTERVAL = 2.5f;
    public static final float BLADEMASTER_037_SWORD_MAX_DIST_FROM_OWNER = 8f;
    public static final int BLADEMASTER_037_FINAL_ACTION_DAMAGE_RESET_MAX_TIMES = 7;

    public static HashMap<LivingEntity, ArrayList<SlaughterscapeSwordEntity>> BLADEMASTER_037_ALL_SWORD_LIST;
    public static HashMap<LivingEntity, IntegerValueHolder> BLADEMASTER_037_SKILL_TIMER;

//    public static final int BLADEMASTER_037_TOTAL_SWORD_PER_LAYER = 20;
    public static final int BLADEMASTER_037_FINAL_ANIMATION_TICKS = 300;
    public static final float BLADEMASTER037_DIST_SHOULD_TRAVEL_EACH_TICK = Blademaster025.BLADEMASTER025_TEMPEST_DIST_SHOULD_TRAVEL_EACH_TICK;

    public static final String BLADEMASTER037_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_ACTION = "BLADEMASTER037_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_ACTION";
    public static final String BLADEMASTER037_GROUND_SWORD_EFF_CLIENT_MOVING_CLOCKWISE_ACTION = "BLADEMASTER037_GROUND_SWORD_EFF_CLIENT_MOVING_RIGHT_ACTION";

    public static final String BLADEMASTER037_AIR_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_POS_ACTION = "BLADEMASTER037_AIR_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_POS_ACTION";
//    public static final float BLADEMASTER037_MIN_SWORD_DIST_FROM_TARGET_POS = 2f;
    public static final float BLADEMASTER037_MAX_SWORD_DIST_FROM_TARGET_POS = 16f;
    public static final float BLADEMASTER037_LAST_SWORD_ENTITY_SCALE = 4f;


    public static final float BLADEMASTER037_INITIAL_VELOCITY = 0.1f;

    //client constant val
    private static final float distShouldTravelDecay = 0.001f;
    private static final float presetDist1 = 0.5f;
    private static final float interval = 1f * BLADEMASTER_037_FINAL_ANIMATION_TICKS * 0.02f;
    private static final int resetClientDamageGroupTick = 10;

    private static HashMap<Entity, ArrayList<SlaughterscapeSwordEntity>> BLADEMASTER_037_CLIENT_SWORD_ENTITY_MAP2ARR = new HashMap<>();

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double AOE = skillData.getEffectArea().get(correctSkillLevel);
        Double HIT_AOE = skillData.getValueUsage_01().get(correctSkillLevel);

        double skillDamageMultiplier = getCPlayerSkillDamage();
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);
        Double LastSlashDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double LastSlashConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);
        Float LastSlashFinalDmg = (float) ( (LastSlashDamageMultiplier * getEntityAttackDamage(getClientPlayer()) + LastSlashConstantDamage) * skillDamageMultiplier);

        Double PreparationTime = skillData.getChannelingTime().get(correctSkillLevel);
        int InitialSlashCount = skillData.getValueUsage_02().get(correctSkillLevel).intValue();

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%s", AOE),
                String.format("%s", HIT_AOE),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (LastSlashDamageMultiplier.floatValue() * 100)),
                String.format("%s", LastSlashConstantDamage),
                String.format("%s", LastSlashFinalDmg),
                String.format("%ss", PreparationTime),
                String.format("%d", InitialSlashCount)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster037::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement( (correctSkillID) -> {
            boolean isInCD = isCPlayerSkillInCD(correctSkillID);
            if (isInCD) {
//                printInGameMsg("Case A");
                ArrayList<SlaughterscapeSwordEntity> SwordEntityArr = getBlademaster037ClientSwordEntityArr(getClientPlayer());
                if (SwordEntityArr != null) {
                    if (!SwordEntityArr.isEmpty()) {
                        boolean shouldTrigger = true;
                        for (SlaughterscapeSwordEntity slaughterscapeSwordEntity : SwordEntityArr) {
                            if (slaughterscapeSwordEntity.getClientTotalDistHasTraveled() <= BLADEMASTER_037_SWORD_MAX_DIST_FROM_OWNER) {
                                shouldTrigger = false;
                                break;
                            }
                        }
                        if (shouldTrigger) {
                            for (SlaughterscapeSwordEntity slaughterscapeSwordEntity : SwordEntityArr) {
                                slaughterscapeSwordEntity.setLifetime(BLADEMASTER_037_FINAL_ANIMATION_TICKS);
                            }
                            SwordEntityArr.clear();
                        }
                        return shouldTrigger;
                    }
                }
                return false;
            }
            else {
//                printInGameMsg("Case B");
                ArrayList<SlaughterscapeSwordEntity> SwordEntityArr = getBlademaster037ClientSwordEntityArr(getClientPlayer());
                if (SwordEntityArr != null) {
                    if (!SwordEntityArr.isEmpty()) {
                        boolean shouldTrigger = true;
                        for (SlaughterscapeSwordEntity slaughterscapeSwordEntity : SwordEntityArr) {
                            if (slaughterscapeSwordEntity.getClientTotalDistHasTraveled() <= BLADEMASTER_037_SWORD_MAX_DIST_FROM_OWNER) {
                                shouldTrigger = false;
                                break;
                            }
                        }
                        if (shouldTrigger) {
                            for (SlaughterscapeSwordEntity slaughterscapeSwordEntity : SwordEntityArr) {
                                slaughterscapeSwordEntity.setLifetime(BLADEMASTER_037_FINAL_ANIMATION_TICKS);
                            }
                            SwordEntityArr.clear();
                        }
                    }
                }

                boolean isInAction = isPlayerInAction();
                boolean isChanneling = isPlayerInChannelingState();
                boolean isOnGround = isPlayerOnGround();
                boolean isPlayerBeingHit = isPlayerBeingHit();
                return !isInAction && !isChanneling && isOnGround && !isPlayerBeingHit;
            }
        });
//        tempSkillData.setClientCondReqPassedAction((SkillID) -> {
//            ArrayList<SlaughterscapeSwordEntity> SwordEntityArr = getBlademaster037ClientSwordEntityArr(getClientPlayer());
//            if (SwordEntityArr != null) {
//                if (SwordEntityArr.isEmpty()) {
//                    setClientPlayerInAction(Constants.ACTION_ON, SkillID);
//                }
//            }
//        });

        if (isClientSide()) {
            return;
        }

        //server init
        if (BLADEMASTER_037_ALL_SWORD_LIST == null) {
            BLADEMASTER_037_ALL_SWORD_LIST = new HashMap<>();
            BLADEMASTER_037_SKILL_TIMER = new HashMap<>();
        }



        //====================================
        //setup skill action
        MethodAction tempChannelingAction =
                (entity1) -> {
                    int playerSkillLVL = 0;
                    if (entity1 instanceof ServerPlayerEntity sp1) {
                        playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                        if (playerSkillLVL <= 0) { return; }
                        playerSkillLVL = playerSkillLVL - 1;
                    }
                    IntegerValueHolder currentTimer = BLADEMASTER_037_SKILL_TIMER.get(entity1);
                    if (currentTimer != null) {
                        ArrayList<SlaughterscapeSwordEntity> tempSwordList = BLADEMASTER_037_ALL_SWORD_LIST.get(entity1);
                        if (tempSwordList != null) {
                            boolean shouldTrigger = true;
                            for (SlaughterscapeSwordEntity slaughterscapeSwordEntity : tempSwordList) {
                                if (slaughterscapeSwordEntity.getServerDistHasTraveled() <= BLADEMASTER_037_SWORD_MAX_DIST_FROM_OWNER) {
                                    shouldTrigger = false;
                                    break;
                                }
                            }
                            if (shouldTrigger) {
                                for (SlaughterscapeSwordEntity slaughterscapeSwordEntity : tempSwordList) {
                                    slaughterscapeSwordEntity.setLifetime(BLADEMASTER_037_FINAL_ANIMATION_TICKS);
                                }

                                SkillDataJson skill1 = getID2SkillData().get(SkillID);
                                currentTimer.setIntCounter((int) (skill1.getDuration().get(playerSkillLVL) * 20));
                            }
                        }
                        return;
                    }

                    if (entity1 instanceof ServerPlayerEntity sp1) {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_START, 1f);
                        DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardKidnapFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                    }

                    //IMPORTANT! setup action on to prevent casting other skills while this action
                    //being performed
//                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);
                    incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, 1);

                    int actionPeriod = 5;
                    int channelingTickDelay = 5;
                    IntegerValueHolder ElapsedTick = new IntegerValueHolder(0);
                    BooleanValueHolder channelingSpawnEffect = new BooleanValueHolder(false);
                    IntegerValueHolder TotalChannelingTicksHolder = new IntegerValueHolder(0);
                    IntegerValueHolder FinalElapsedTickHolder = new IntegerValueHolder(0);
                    BLADEMASTER_037_SKILL_TIMER.put(entity1, FinalElapsedTickHolder);

                    MethodAction action = (entity2) -> {
                        int updatedTick = ElapsedTick.getIntCounter();

                        boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                        if (isActionInterrupted) {
                            if (entity2 instanceof ServerPlayerEntity sp1) {
                                DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                            }

                            incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                            SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_037_PREPARATION);
                        }
                        else {
                            int channelingTotalTicks = TotalChannelingTicksHolder.getIntCounter();

                            if (!ServerSkillMethods.isLivingEntityInChannelingAction(entity2)) {
                                SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_037_PREPARATION);

                                Blademaster037_executeSwordZoneAction(entity2, FinalElapsedTickHolder);
                            }
                            else if (updatedTick <= channelingTotalTicks + channelingTickDelay) {
                                TotalChannelingTicksHolder.setIntCounter(channelingTotalTicks + actionPeriod);

                                //channeling entity and sound
                                if (channelingTotalTicks == 5) {
                                    DfoSwdSoundMethods.PlayBlademaster027DrawSwordStartSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 4f);
                                }
                                if (!channelingSpawnEffect.getBooleanVal()) {
                                    if (entity2 instanceof ServerPlayerEntity sp1) {
                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_LOOPING);
                                    }
                                    channelingSpawnEffect.setBooleanVal(true);

                                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity2, 255, 255, 255, 1f, 0, 1.4f, 0, 900, GeoAnimationAndTextureName.EFF_COMMON_CHARGING_LOOPING, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                            DfoSwordmanSkillTreeConstants.NO_ANIMATION,0, 0, 13, false, false, false, DfoSwordmanSkillMethods.RemoveChannelingEffEntity(entity2), 5, true);
                                }
                            }
                        }

                        ElapsedTick.setIntCounter(updatedTick + actionPeriod);
                    };
                    SBSTickEventMethods.setMethodActionTimer(entity1, BLADEMASTER_037_PREPARATION, actionPeriod, action);
                };

        tempSkillData.setChannelingSkillAction1(tempChannelingAction);

        tempSkillData.setActiveSkillAction1(DfoSwordmanSkillMethods::DoNothing);
    }

    public static void Blademaster037_executeSwordZoneAction(LivingEntity entity2, IntegerValueHolder ElapsedTicksHolder) {
        int playerSkillLVL = 0;

        SkillDataJson skill1 = getID2SkillData().get(SkillID);

        if (entity2 instanceof ServerPlayerEntity sp1) {
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                if (playerSkillLVL <= 0) { return; }
                playerSkillLVL = playerSkillLVL - 1;
            }
            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_END, 1f);
        }

        float swordHitAoeRange = skill1.getValueUsage_01().get(playerSkillLVL).floatValue();

        int TotalSlashCount = skill1.getValueUsage_02().get(playerSkillLVL).intValue();
        int SkillDurationTicks = (int) (skill1.getDuration().get(playerSkillLVL).floatValue() * 20);

        int actionPeriod = 2;

        DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 4f);

        ArrayList<SlaughterscapeSwordEntity> tempSwordArrList = new ArrayList<>();
        BLADEMASTER_037_ALL_SWORD_LIST.put(entity2, tempSwordArrList);
        MethodAction action = Blademaster037_SlashBeforeDamageAction(ElapsedTicksHolder, swordHitAoeRange, actionPeriod, TotalSlashCount, SkillDurationTicks, tempSwordArrList);

        //start loop actioning with fixed period ticks
        SBSTickEventMethods.setMethodActionTimer(entity2, SkillID, actionPeriod, action);
    }

    public static MethodAction Blademaster037_SlashBeforeDamageAction(IntegerValueHolder ElapsedTicksHolder, float swordHitAoeRange, int actionPeriod, int swordCountPerLayer, int skillDurationTicks, ArrayList<SlaughterscapeSwordEntity> swordList) {
        IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
        BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
        BooleanValueHolder CompleteChannelingYet = new BooleanValueHolder(false);

        MethodAction action =
                (entity3) -> {
                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity3);

                    int updatedTick = ElapsedTicks.getIntCounter();

                    if (!isActionInterrupted && updatedTick >= BLADEMASTER_037_SWORD_DROPPING_DELAY_TICKS && !CompleteChannelingYet.getBooleanVal()) {
                        CompleteChannelingYet.setBooleanVal(true);
                        Vector3f triggeredEntityPos = getEntityPos(entity3);
                        DfoSwdSoundMethods.PlayBlademaster027DrawSwordSlashSound(getEntityLevel(entity3), triggeredEntityPos.x(), triggeredEntityPos.y(), triggeredEntityPos.z(), 0.8f, 4f);

                        String slashActionID = BLADEMASTER_037_SLASH_ACTION + getEntityTickCount(entity3);

                        float degreeShouldFacing = 360f / swordCountPerLayer;
                        float degreeShouldIncrementEachTime = degreeShouldFacing * 0.25f;

                        SlaughterscapeSwordEntity tempEntity;
                        float ownerPosX = getEntityX(entity3), ownerPosY = getEntityY(entity3), ownerPosZ = getEntityZ(entity3);
                        for (int i = 0; i < swordCountPerLayer; i++) {
                            int tempPosID = i * 3;
                            tempEntity = GeoModelSpawnMethods.GenerateSlaughterScapeEntity(entity3, i * degreeShouldFacing, 1f, degreeShouldIncrementEachTime, swordHitAoeRange, 1.1f + ExtraMathMethods.randomFloat(), ownerPosX, ownerPosY, ownerPosZ, 0, 2f, 0, ownerPosX, ownerPosY, ownerPosZ,
                                    skillDurationTicks + BLADEMASTER_037_FINAL_ANIMATION_TICKS, tempPosID, -1, Blademaster025.BLADEMASTER025_SWORD_TEXTURE_PATH + ExtraMathMethods.randomInt(5) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX, Blademaster025.BLADEMASTER025_TEMPEST_GROUND_SWORD_ANIMATION,
                                    BLADEMASTER037_GROUND_SWORD_EFF_CLIENT_MOVING_CLOCKWISE_ACTION, null, 777, true, true);

                            swordList.add(tempEntity);

                            tempEntity = GeoModelSpawnMethods.GenerateSlaughterScapeEntity(entity3, i * degreeShouldFacing, 1f, -degreeShouldIncrementEachTime, swordHitAoeRange, 1.1f + ExtraMathMethods.randomFloat(), ownerPosX, ownerPosY, ownerPosZ, 0, 2f + BLADEMASTER_037_SWORD_HEIGHT_INTERVAL, 0, ownerPosX, ownerPosY, ownerPosZ,
                                    skillDurationTicks + BLADEMASTER_037_FINAL_ANIMATION_TICKS, tempPosID + 1, -1, Blademaster025.BLADEMASTER025_SWORD_TEXTURE_PATH + ExtraMathMethods.randomInt(5) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX, Blademaster025.BLADEMASTER025_TEMPEST_GROUND_SWORD_ANIMATION,
                                    BLADEMASTER037_GROUND_SWORD_EFF_CLIENT_MOVING_CLOCKWISE_ACTION, null, 777, true, true);

                            swordList.add(tempEntity);

                            tempEntity = GeoModelSpawnMethods.GenerateSlaughterScapeEntity(entity3, i * degreeShouldFacing, 1f, degreeShouldIncrementEachTime, swordHitAoeRange, 1.1f + ExtraMathMethods.randomFloat(), ownerPosX, ownerPosY, ownerPosZ,0, 2f + BLADEMASTER_037_SWORD_HEIGHT_INTERVAL * 2, 0, ownerPosX, ownerPosY, ownerPosZ,
                                    skillDurationTicks + BLADEMASTER_037_FINAL_ANIMATION_TICKS, tempPosID + 2, -1, Blademaster025.BLADEMASTER025_SWORD_TEXTURE_PATH + ExtraMathMethods.randomInt(5) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX, Blademaster025.BLADEMASTER025_TEMPEST_GROUND_SWORD_ANIMATION,
                                    BLADEMASTER037_GROUND_SWORD_EFF_CLIENT_MOVING_CLOCKWISE_ACTION, null, 777, true, true);

                            swordList.add(tempEntity);
                        }

                        MethodAction slashAction = Blademaster037_SlashStartDamageAction(ElapsedTicksHolder, skillDurationTicks, swordList, new FloatValueHolder(0), new FloatValueHolder(0), slashActionID);

                        SBSTickEventMethods.setMethodActionTimer(entity3, slashActionID, 1, slashAction);
                    } else {
                        if (!SendActionOffPacket.getBooleanVal()) {
                            if (isActionInterrupted){
                                if (entity3 instanceof ServerPlayerEntity sp1) {
                                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_END);
                                }
                                SendActionOffPacket.setBooleanVal(true);
                                incrementEntityStateByAmountX(entity3, Constants.IS_IN_ACTION, -1);
                                SBSTickEventMethods.setSkillActionDone(entity3, SkillID);
                            }
                            else{
                                if (updatedTick > BLADEMASTER_037_SWORD_DROPPING_DELAY_TICKS) {
                                    if (entity3 instanceof ServerPlayerEntity sp1) {
                                        DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_END);
                                    }

                                    SendActionOffPacket.setBooleanVal(true);
                                    incrementEntityStateByAmountX(entity3, Constants.IS_IN_ACTION, -1);
                                    SBSTickEventMethods.setSkillActionDone(entity3, SkillID);
                                }
                            }
                        }
                    }


                    //update val
                    ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
                };

        return action;
    }

    public static MethodAction Blademaster037_SlashStartDamageAction(IntegerValueHolder ElapsedTicks, int totalDurationTicks, ArrayList<SlaughterscapeSwordEntity> swordList, FloatValueHolder previousDegHolder, FloatValueHolder minDegHolder, String ActionID) {
        return (entity4) -> {
            if (SBConditions.isEntityAlive(entity4)) {
                int currentElapsedTicks = ElapsedTicks.getIntCounter() + 1;
                ElapsedTicks.setIntCounter(currentElapsedTicks);
                if (currentElapsedTicks > totalDurationTicks) {
                    SBSTickEventMethods.setSkillActionDone(entity4, ActionID);
                    DfoSwdNetworkingFetchMsgMethods.FetchBlademaster037SlaughterscapeForceTriggerPacketFromServer(getEntityHashID(entity4), getEntityX(entity4), getEntityY(entity4), getEntityZ(entity4));
                    BLADEMASTER_037_ALL_SWORD_LIST.remove(entity4);
                    BLADEMASTER_037_SKILL_TIMER.remove(entity4);

                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity4);

                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity4);
                    int playerSkillLVL = 0;

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);
                    Predicate<Entity> condition;

                    if (entity4 instanceof ServerPlayerEntity sp1) {
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                        }
                        condition = defaultSkillTargetCondition(sp1).and(SBConditions.isNotTargetEntity(entity4));
                    }
                    else {
                        condition = defaultSkillTargetCondition().and(SBConditions.isNotTargetEntity(entity4));
                    }

                    float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
//                    float swordHitAoeRange = skill1.getValueUsage_01().get(playerSkillLVL).floatValue();

                    float damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    float damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();

                    float LastSlashConstantDamage = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
                    float LastSlashDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue();

                    double dealerAttackDamage = getEntityAttackDamage(entity4);
                    float finalDmg = (float) ((damageMultiplier * dealerAttackDamage + damageBase) * skillDamageMultiplier);
                    float finalLastSlashDmg = (float) ((LastSlashDamageMultiplier * dealerAttackDamage + LastSlashConstantDamage) * skillDamageMultiplier);

                    for (SlaughterscapeSwordEntity slaughterscapeSwordEntity: swordList) {
                        slaughterscapeSwordEntity.setLifetime(BLADEMASTER_037_FINAL_ANIMATION_TICKS);
                        slaughterscapeSwordEntity.setEntityServerAction(Blademaster037_FinalDamageAction(entity4,
                                        new FloatValueHolder(BLADEMASTER037_INITIAL_VELOCITY),
                                        new TypeValueHolder<Vector3f>(null), new TypeValueHolder<Entity>(null), new IntegerValueHolder(-1),
                                        new IntegerValueHolder(0),
                                        new HashSet<>(), condition, weaponType, finalDmg
                                ), 1);
                    }

                    String finalActionID = BLADEMASTER_037_FINAL_DAMAGE_ACTION + getEntityTickCount(entity4);
                    int finalActionTickPeriod = 1;
                    MethodAction finalAction = Blademaster037_LastFinalDamageAction(swordList, finalActionTickPeriod, new IntegerValueHolder(0), aoeRange, finalLastSlashDmg, new HashSet<>(), new HashSet<>(), new HashSet<>(), new BooleanValueHolder(false), condition, finalActionID);

                    SBSTickEventMethods.setMethodActionTimer(entity4, finalActionID, finalActionTickPeriod, finalAction);
                }
                //update pos
                else {
                    float ownerX = getEntityX(entity4);
                    float ownerY = getEntityY(entity4);
                    float ownerZ = getEntityZ(entity4);

                    for (SlaughterscapeSwordEntity SwordEntity : swordList) {
                        float currentTotalDistHasTraveled = SwordEntity.getServerDistHasTraveled();
                        if (currentTotalDistHasTraveled <= BLADEMASTER_037_SWORD_MAX_DIST_FROM_OWNER) {
                            float newFacingAngle = SwordEntity.getDegreeShouldIncrementEachTime() + getEntityHorizontalFacingDeg(SwordEntity);

                            setEntityHorizontalFacingDeg(SwordEntity, newFacingAngle);

                            float newX = Vector3fMethods.PolarProjectionFromXHorizonWithOrigin(ownerX, currentTotalDistHasTraveled + BLADEMASTER037_DIST_SHOULD_TRAVEL_EACH_TICK, newFacingAngle) + SwordEntity.getAdditionalPosX();
                            float newY = ownerY + SwordEntity.getAdditionalPosY();
                            float newZ = Vector3fMethods.PolarProjectionFromZHorizonWithOrigin(ownerZ, currentTotalDistHasTraveled + BLADEMASTER037_DIST_SHOULD_TRAVEL_EACH_TICK, newFacingAngle) + SwordEntity.getAdditionalPosZ();

                            moveEntityToXYZ(SwordEntity, newX, newY, newZ);

                            if (currentTotalDistHasTraveled < BLADEMASTER037_DIST_SHOULD_TRAVEL_EACH_TICK) {
                                previousDegHolder.setFloatVal(SwordEntity.getDegreeShouldIncrementEachTime());
                                minDegHolder.setFloatVal(SwordEntity.getDegreeShouldIncrementEachTime() * 0.5f);
                            }

                            SwordEntity.setServerDistHasTraveled(currentTotalDistHasTraveled + BLADEMASTER037_DIST_SHOULD_TRAVEL_EACH_TICK);
                        } else {
                            float newFacingAngle;
                            float previousRotationDeg = previousDegHolder.getFloatVal();
                            if (previousRotationDeg > minDegHolder.getFloatVal()) {
                                previousRotationDeg = previousRotationDeg - 0.1f;
                                previousDegHolder.setFloatVal(previousRotationDeg);
                                newFacingAngle = previousRotationDeg + getEntityHorizontalFacingDeg(SwordEntity);
                            } else {
                                newFacingAngle = minDegHolder.getFloatVal() + getEntityHorizontalFacingDeg(SwordEntity);
                            }

                            setEntityHorizontalFacingDeg(SwordEntity, newFacingAngle);

                            float newX = Vector3fMethods.PolarProjectionFromXHorizonWithOrigin(ownerX, BLADEMASTER_037_SWORD_MAX_DIST_FROM_OWNER, newFacingAngle) + SwordEntity.getAdditionalPosX();
                            float newY = ownerY + SwordEntity.getAdditionalPosY();
                            float newZ = Vector3fMethods.PolarProjectionFromZHorizonWithOrigin(ownerZ, BLADEMASTER_037_SWORD_MAX_DIST_FROM_OWNER, newFacingAngle) + SwordEntity.getAdditionalPosZ();

                            moveEntityToXYZ(SwordEntity, newX, newY, newZ);
                        }
                    }
                }
            }
            else {
                SBSTickEventMethods.setSkillActionDone(entity4, ActionID);
            }
        };
    }

    public static MethodAction Blademaster037_LastFinalDamageAction(ArrayList<SlaughterscapeSwordEntity> arrTBR, int actionPeriod, IntegerValueHolder ElapsedTicksHolder, float finalAoeZoneRange, float finalDamage, HashSet<LivingEntity> damagedGroup, HashSet<SlaughterscapeSwordEntity> movingSwordEntitySet, HashSet<SlaughterscapeSwordEntity> tempSwordEntitySetTBR, BooleanValueHolder spawnedFinalEntityYet, Predicate<Entity> condition, String ActionID) {
        return (entity5) -> {
            int ElapsedTicks = ElapsedTicksHolder.getIntCounter() + actionPeriod;
            ElapsedTicksHolder.setIntCounter(ElapsedTicks);
            //find damage center
//            Vector3f damageCenter;
//            Entity nearestEntity = findHorizontalNearbyEntityFromCenterEntityLoc(entity5, 16, 16, 16, condition);
//            if (nearestEntity == null) {
//                damageCenter = null;
//            }
//            else {
//                damageCenter = getEntityPos(nearestEntity);
//            }

            if (ElapsedTicks > 250) {
                if (!spawnedFinalEntityYet.getBooleanVal()) {
                    spawnedFinalEntityYet.setBooleanVal(true);

                    Vector3f damageCenter;
                    Entity nearestEntity = findHorizontalNearbyEntityFromCenterEntityLoc(entity5, 16, 16, 16, condition);
                    if (nearestEntity == null) {
                        damageCenter = null;
                    } else {
                        damageCenter = getEntityPos(nearestEntity);
                    }
                    if (damageCenter == null) {
                        damageCenter = getEntityPos(entity5);
                    }

                    damageCenter = damageCenter.add(0, DfoSwordmanSkillTreeConstants.MC_DEFAULT_EYE_HEIGHT, 0);
                    float randomHorizontalFacingAngle = ExtraMathMethods.randomFloat(360f);

                    Vector3f tempHorizontalFromPosA = Vector3fMethods.PolarProjectionFromVecHorizon(damageCenter, BLADEMASTER037_MAX_SWORD_DIST_FROM_TARGET_POS, randomHorizontalFacingAngle - 45f);
                    Vector3f tempHorizontalToPosA = Vector3fMethods.PolarProjectionFromVecHorizon(damageCenter, 2 * BLADEMASTER037_MAX_SWORD_DIST_FROM_TARGET_POS, randomHorizontalFacingAngle + 135f);
                    Vector3f tempHorizontalFromPosB = Vector3fMethods.PolarProjectionFromVecHorizon(damageCenter, BLADEMASTER037_MAX_SWORD_DIST_FROM_TARGET_POS, randomHorizontalFacingAngle);
                    Vector3f tempHorizontalToPosB = Vector3fMethods.PolarProjectionFromVecHorizon(damageCenter, 2 * BLADEMASTER037_MAX_SWORD_DIST_FROM_TARGET_POS, randomHorizontalFacingAngle + 180f);
                    Vector3f tempHorizontalFromPosC = Vector3fMethods.PolarProjectionFromVecHorizon(damageCenter, BLADEMASTER037_MAX_SWORD_DIST_FROM_TARGET_POS, randomHorizontalFacingAngle + 45f);
                    Vector3f tempHorizontalToPosC = Vector3fMethods.PolarProjectionFromVecHorizon(damageCenter, 2 * BLADEMASTER037_MAX_SWORD_DIST_FROM_TARGET_POS, randomHorizontalFacingAngle + 225f);

                    SlaughterscapeSwordEntity tempSwordEntity;

                    tempSwordEntity = GeoModelSpawnMethods.GenerateSlaughterScapeEntity(entity5, randomHorizontalFacingAngle + 135f, BLADEMASTER037_LAST_SWORD_ENTITY_SCALE, 0, finalAoeZoneRange, 0, tempHorizontalFromPosA.x(), tempHorizontalFromPosA.y(), tempHorizontalFromPosA.z(),0, 0, 0, tempHorizontalToPosA.x(), tempHorizontalToPosA.y(), tempHorizontalToPosA.z(),
                            BLADEMASTER_037_FINAL_ANIMATION_TICKS, 0, -1, Blademaster025.BLADEMASTER025_SWORD_TEXTURE_PATH + ExtraMathMethods.randomInt(5) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX, BLADEMASTER_037_SHOOTING_SWORD_ANIMATION_NAME,
                            BLADEMASTER037_AIR_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_POS_ACTION, null, 777, false, true);

                    movingSwordEntitySet.add(tempSwordEntity);

                    tempSwordEntity = GeoModelSpawnMethods.GenerateSlaughterScapeEntity(entity5, randomHorizontalFacingAngle + 180f, BLADEMASTER037_LAST_SWORD_ENTITY_SCALE, 0, finalAoeZoneRange, 0, tempHorizontalFromPosB.x(), tempHorizontalFromPosB.y(), tempHorizontalFromPosB.z(),0, 0, 0, tempHorizontalToPosB.x(), tempHorizontalToPosB.y(), tempHorizontalToPosB.z(),
                            BLADEMASTER_037_FINAL_ANIMATION_TICKS, 0, -1, Blademaster025.BLADEMASTER025_SWORD_TEXTURE_PATH + ExtraMathMethods.randomInt(5) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX, BLADEMASTER_037_SHOOTING_SWORD_ANIMATION_NAME,
                            BLADEMASTER037_AIR_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_POS_ACTION, null, 777, false, true);

                    movingSwordEntitySet.add(tempSwordEntity);

                    tempSwordEntity = GeoModelSpawnMethods.GenerateSlaughterScapeEntity(entity5, randomHorizontalFacingAngle + 225f, BLADEMASTER037_LAST_SWORD_ENTITY_SCALE, 0, finalAoeZoneRange, 0, tempHorizontalFromPosC.x(), tempHorizontalFromPosC.y(), tempHorizontalFromPosC.z(),0, 0, 0, tempHorizontalToPosC.x(), tempHorizontalToPosC.y(), tempHorizontalToPosC.z(),
                            BLADEMASTER_037_FINAL_ANIMATION_TICKS, 0, -1, Blademaster025.BLADEMASTER025_SWORD_TEXTURE_PATH + ExtraMathMethods.randomInt(5) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX, BLADEMASTER_037_SHOOTING_SWORD_ANIMATION_NAME,
                            BLADEMASTER037_AIR_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_POS_ACTION, null, 777, false, true);

                    movingSwordEntitySet.add(tempSwordEntity);

                    DfoSwdSoundMethods.PlayZanbatoWieldingSound(getEntityLevel(entity5), damageCenter.x(), damageCenter.y(), damageCenter.z(), 1f, 2f);
                }
                else {
                    for (SlaughterscapeSwordEntity LastActionSwordEntity: movingSwordEntitySet) {
                        int currentElapsedTicks = LastActionSwordEntity.getMaxLifeTime() - LastActionSwordEntity.getLifetime();
                        if (currentElapsedTicks <= 20) {
                            float originX = (LastActionSwordEntity.hasClientNextMovingPos()) ? LastActionSwordEntity.getPreviousToPosX() : getEntityX(LastActionSwordEntity);
                            float originY = (LastActionSwordEntity.hasClientNextMovingPos()) ? LastActionSwordEntity.getPreviousToPosY() : getEntityY(LastActionSwordEntity);
                            float originZ = (LastActionSwordEntity.hasClientNextMovingPos()) ? LastActionSwordEntity.getPreviousToPosZ() : getEntityZ(LastActionSwordEntity);
                            float targetPosX = LastActionSwordEntity.getTargetCenterPosX();
                            float targetPosY = LastActionSwordEntity.getTargetCenterPosY();
                            float targetPosZ = LastActionSwordEntity.getTargetCenterPosZ();

                            float percent = currentElapsedTicks / 20f;
                            float newX = Vector3fMethods.lerp1D(percent, originX, targetPosX);
                            float newY = Vector3fMethods.lerp1D(percent, originY, targetPosY);
                            float newZ = Vector3fMethods.lerp1D(percent, originZ, targetPosZ);

                            moveEntityToXYZ(LastActionSwordEntity, newX, newY, newZ);

                            for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(LastActionSwordEntity, finalAoeZoneRange, 4, finalAoeZoneRange, condition)) {
                                if (tempEntity instanceof LivingEntity tempLivingEntity && !damagedGroup.contains(tempLivingEntity)) {
                                    damagedGroup.add(tempLivingEntity);
                                    GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 3f, 4, 4,
                                            ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f,
                                            GeoAnimationAndTextureName.EFF_HIT_EFF_ADVANCE_ARR[ExtraMathMethods.randomInt(3)], true, true);

                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity5, LastActionSwordEntity, tempLivingEntity, finalDamage, false);

                                    DfoSwdSoundMethods.PlayZanbatoStabHitSound(getEntityLevel(entity5), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity), getEntityZ(tempLivingEntity), 1f, 1.0f);
                                }
                            }

                            DfoSwdSoundMethods.PlayZanbatoWieldingSound(getEntityLevel(entity5), newX, newY, newZ, 0.5f, 4f);
                        }
                        else {
                            tempSwordEntitySetTBR.add(LastActionSwordEntity);
                        }
                    }
                    if (!tempSwordEntitySetTBR.isEmpty()) {
                        for (SlaughterscapeSwordEntity LastActionSwordEntityTBR: tempSwordEntitySetTBR) {
                            LastActionSwordEntityTBR.setLifetime(40);
                            movingSwordEntitySet.remove(LastActionSwordEntityTBR);
                        }
                        tempSwordEntitySetTBR.clear();
                    }
                    if (movingSwordEntitySet.isEmpty()) {
                        for (SlaughterscapeSwordEntity tbrEntity: arrTBR) {
                            if (SBConditions.isEntityAlive(tbrEntity)) {
                                RemoveEntity(tbrEntity);
                            }
                        }
                        SBSTickEventMethods.setSkillActionDone(entity5, ActionID);
                    }
                }
            }
        };
    }

    //Entity action
    public static MethodEntityAction Blademaster037_FinalDamageAction(LivingEntity dealer, FloatValueHolder initialAcceleration, TypeValueHolder<Vector3f> previousTargetLoc, TypeValueHolder<Entity> targetHolder,
                                                                      IntegerValueHolder resetDamageIntervalHolder, IntegerValueHolder damageResetTimesHolder, HashSet<Entity> damagedGroup, Predicate<Entity> finalCondition,
                                                                      int weaponType, float swordHitDamage
    ) {
        return (unknownEntity) -> {
            SlaughterscapeSwordEntity SwordEntity = (SlaughterscapeSwordEntity) unknownEntity;
            Entity owner = SwordEntity.getOwner();

            int currentSwordEntityElapsedLifeTime = BLADEMASTER_037_FINAL_ANIMATION_TICKS - SwordEntity.getLifetime();

            Entity target = targetHolder.getAnyTypeValue();

            float distShouldTravelEachTime = initialAcceleration.getFloatVal();
            float polarDist = presetDist1 - distShouldTravelEachTime;
            float swordHorizontalFacingAngle = getEntityHorizontalFacingDeg(SwordEntity);
            float swordVerticalFacingAngle = getEntityVerticalFacingDeg(SwordEntity);
            initialAcceleration.setFloatVal(distShouldTravelEachTime - (distShouldTravelDecay * interval));

            Vector3f originSwordEntityLoc = (previousTargetLoc.getAnyTypeValue() == null) ? getEntityPos(SwordEntity) : previousTargetLoc.getAnyTypeValue();

            float swordHitAoe = SwordEntity.getSwordHitAoeRange();

            int resetDamageInterval = resetDamageIntervalHolder.getIntCounter();
            if (resetDamageInterval == -1) {
                if (target != null && !SBConditions.isEntityAlive(target)) {
                    targetHolder.setAnyTypeValue(null);
                    target = null;
                }
                if (target == null) {
                    target = EntityMethods.findHorizontalNearbyEntityFromCenterEntityLoc(owner, 16, 16, 16, finalCondition);
                    targetHolder.setAnyTypeValue(target);
                    // call swords back
                    if (target == null) {
                        target = owner;
                    }
                }

                for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(SwordEntity, swordHitAoe, swordHitAoe, swordHitAoe, finalCondition)) {
                    if (tempEntity instanceof LivingEntity tempLivingEntity && !damagedGroup.contains(tempLivingEntity) && Vector3fMethods.Vec3fDistanceTo(getEntityX(SwordEntity), getEntityY(SwordEntity), getEntityZ(SwordEntity), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity) + SwordEntity.getRandomHitHeightOffset(), getEntityZ(tempLivingEntity)) <= swordHitAoe) {
                        damagedGroup.add(tempLivingEntity);
                        GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 1f, 4, 4,
                                ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f,
                                GeoAnimationAndTextureName.EFF_HIT_EFF_ADVANCE_ARR[ExtraMathMethods.randomInt(3)], true, true);

                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(dealer, SwordEntity, tempLivingEntity, swordHitDamage, false);

                        DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(dealer), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity), getEntityZ(tempLivingEntity), 0.6f, 1.0f);
                    }
                }
            }

            if (damagedGroup.contains(target)) {
                target = null;
                int damageResetTimes = damageResetTimesHolder.getIntCounter();
                if (damageResetTimes < BLADEMASTER_037_FINAL_ACTION_DAMAGE_RESET_MAX_TIMES) {
                    if (resetDamageInterval < currentSwordEntityElapsedLifeTime) {
                        resetDamageIntervalHolder.setIntCounter(currentSwordEntityElapsedLifeTime + resetClientDamageGroupTick);
                    }
                    else if (currentSwordEntityElapsedLifeTime >= resetDamageInterval) {
                        target = targetHolder.getAnyTypeValue();
                        resetDamageIntervalHolder.setIntCounter(-1);
                        //random velocity?
//                                    initialAcceleration.setFloatVal(BLADEMASTER037_INITIAL_VELOCITY);
                        damagedGroup.clear();
                        damageResetTimesHolder.setIntCounter(damageResetTimes + 1);
                        swordVerticalFacingAngle = ExtraMathMethods.randomFloat(360f);
                    }
                }
            }

            Vector3f tpLoc = Vector3fMethods.PolarProjectionFromRotation(originSwordEntityLoc, polarDist, swordHorizontalFacingAngle, swordVerticalFacingAngle);
            moveEntityToPos(SwordEntity, tpLoc);

            previousTargetLoc.setAnyTypeValue(tpLoc);

            if (target != null) {
                Vector3f targetLoc = EntityMethods.getEntityBlockPos(target).add(0, SwordEntity.getRandomHitHeightOffset(), 0);
                float HorizontalAngleBetweenSwordEntityAndTarget = Vector3fMethods.AngleFromLocationToLocationHorizontal(originSwordEntityLoc, targetLoc);
                float VerticalAngleBetweenSwordEntityAngTarget = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationVertical(originSwordEntityLoc, targetLoc) * MathMethods.RAD_TO_DEG);

                float time = 0.01f + 0.99f * currentSwordEntityElapsedLifeTime / BLADEMASTER_037_FINAL_ANIMATION_TICKS;
                float newHorizontalDegree = MathMethods.RoundAngle(MathMethods.lerpDegreeAngle(time, swordHorizontalFacingAngle, HorizontalAngleBetweenSwordEntityAndTarget));
                float newVerticalDegree = MathMethods.RoundAngle(MathMethods.lerpDegreeAngle(time, MathMethods.RoundAngle(swordVerticalFacingAngle), VerticalAngleBetweenSwordEntityAngTarget));
                setEntityHorizontalFacingDeg(SwordEntity, newHorizontalDegree % 360);
                setEntityVerticalFacingDeg(SwordEntity, newVerticalDegree % 360);
            }
        };
    }

    public static MethodEntityAction Blademaster037_executeSlaughterScapeSwordDamageAction(LivingEntity dealer, int weaponType, float finalDamage, float swordHitAoe, Predicate<Entity> condition, HashSet<LivingEntity> damagedGroup, IntegerValueHolder previousTickCount, FloatValueHolder initialAcceleration, TypeValueHolder<Vector3f> previousTargetLoc) {
        return (swordEntity) -> {
            SlaughterscapeSwordEntity slaughterscapeSwordEntity = (SlaughterscapeSwordEntity) swordEntity;

            //update pos
            int currentSwordEntityElapsedLifeTime = BLADEMASTER_037_FINAL_ANIMATION_TICKS - slaughterscapeSwordEntity.getLifetime();
            if (currentSwordEntityElapsedLifeTime != previousTickCount.getIntCounter()) {
                previousTickCount.setIntCounter(currentSwordEntityElapsedLifeTime);
                LivingEntity target = slaughterscapeSwordEntity.getTargetEntity();
                if (target != null && !SBConditions.isEntityAlive(target)) { target = null; }
                float distShouldTravelEachTime = initialAcceleration.getFloatVal();
                float polarDist = presetDist1 - distShouldTravelEachTime;
                float swordHorizontalFacingAngle = getEntityHorizontalFacingDeg(slaughterscapeSwordEntity);
                float swordVerticalFacingAngle = getEntityVerticalFacingDeg(slaughterscapeSwordEntity);
                initialAcceleration.setFloatVal(distShouldTravelEachTime - (distShouldTravelDecay * interval));

                Vector3f originSwordEntityLoc = (previousTargetLoc.getAnyTypeValue() == null) ? getEntityPos(slaughterscapeSwordEntity) : previousTargetLoc.getAnyTypeValue();

                for (Entity tempEntity: GroupEnumEntitiesInRangeFilter(slaughterscapeSwordEntity, swordHitAoe, swordHitAoe, swordHitAoe, condition)) {
                    if (tempEntity instanceof LivingEntity tempLivingEntity && !damagedGroup.contains(tempLivingEntity) && Vector3fMethods.Vec3fDistanceTo(getEntityX(slaughterscapeSwordEntity), getEntityY(slaughterscapeSwordEntity), getEntityZ(slaughterscapeSwordEntity), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity) + slaughterscapeSwordEntity.getRandomHitHeightOffset(), getEntityZ(tempLivingEntity)) <= swordHitAoe) {
                        damagedGroup.add(tempLivingEntity);
                        GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 1f, 4, 4,
                                ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f,
                                GeoAnimationAndTextureName.EFF_HIT_EFF_ADVANCE_ARR[ExtraMathMethods.randomInt(3)], true, true);

                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(dealer, swordEntity, tempLivingEntity, finalDamage, false);

                        DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(dealer), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity), getEntityZ(tempLivingEntity), 0.8f, 1.0f);
                    }
                }

                if (damagedGroup.contains(target) || target == null) {
//                    slaughterscapeSwordEntity.setHitOnTarget(true);
                    slaughterscapeSwordEntity.setEntityServerAction(null, 777);
                    slaughterscapeSwordEntity.setTargetEntityID(-1);
                    slaughterscapeSwordEntity.setLifetime(32);
                }
                else {
                    Vector3f tpLoc = Vector3fMethods.PolarProjectionFromRotation(originSwordEntityLoc, polarDist, swordHorizontalFacingAngle, swordVerticalFacingAngle);
//                Vector3f tpLoc = Vector3fMethods.PolarProjectionFromVecHorizon(originSwordEntityLoc, (target == null) ? 0.05f : polarDist, swordHorizontalFacingAngle);
                    moveEntityToXYZ(slaughterscapeSwordEntity, tpLoc.x(), tpLoc.y(), tpLoc.z());

                    previousTargetLoc.setAnyTypeValue(tpLoc);

                    Vector3f targetLoc = EntityMethods.getEntityBlockPos(target).add(0, slaughterscapeSwordEntity.getRandomHitHeightOffset(), 0);
                    float HorizontalAngleBetweenSwordEntityAndTarget = Vector3fMethods.AngleFromLocationToLocationHorizontal(originSwordEntityLoc, targetLoc);
                    float VerticalAngleBetweenSwordEntityAngTarget = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationVertical(originSwordEntityLoc, targetLoc) * MathMethods.RAD_TO_DEG);

                    float time = 0.01f + 0.99f * currentSwordEntityElapsedLifeTime / BLADEMASTER_037_FINAL_ANIMATION_TICKS;
                    float newHorizontalDegree = MathMethods.RoundAngle(MathMethods.lerpDegreeAngle(time, swordHorizontalFacingAngle, HorizontalAngleBetweenSwordEntityAndTarget));
                    float newVerticalDegree = MathMethods.RoundAngle(MathMethods.lerpDegreeAngle(time, MathMethods.RoundAngle(swordVerticalFacingAngle), VerticalAngleBetweenSwordEntityAngTarget));
                    setEntityHorizontalFacingDeg(slaughterscapeSwordEntity, newHorizontalDegree % 360);
                    setEntityVerticalFacingDeg(slaughterscapeSwordEntity, newVerticalDegree % 360);
                }
            }
        };
    }

    public static void Blademaster037_executeSlaughterScapeSwordAction(LivingEntity dealer, LivingEntity target, int weaponType) {
        ArrayList<SlaughterscapeSwordEntity> SwordList = BLADEMASTER_037_ALL_SWORD_LIST.get(dealer);

        int SwordListSize;
        if (SwordList != null && (SwordListSize = SwordList.size()) != 0) {
            //TODO: reset randomSwordEntity's texture and other values
            int tempInt = ExtraMathMethods.randomInt(SwordListSize);
            SlaughterscapeSwordEntity randomSwordEntity = SwordList.get(tempInt);

            int playerSkillLVL = 0;

            SkillDataJson skill1 = getID2SkillData().get(SkillID);
            Predicate<Entity> condition;

            if (dealer instanceof ServerPlayerEntity sp1) {
                if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                    playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                    if (playerSkillLVL <= 0) { return; }
                    playerSkillLVL = playerSkillLVL - 1;
                }
                condition = defaultSkillTargetCondition(sp1).and(entity -> !entity.equals(dealer));
            }
            else {
                condition = defaultSkillTargetCondition().and(entity -> !entity.equals(dealer));
            }

            float swordHitAoeRange = skill1.getValueUsage_01().get(playerSkillLVL).floatValue();
            float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(dealer);

            float damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
            float damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();
            float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(dealer) + damageBase) * skillDamageMultiplier);

//            String ActionID = BLADEMASTER_037_SWORD_ENTITY_DAMAGE_ACTION + getEntityHashID(randomSwordEntity) + randomSwordEntity.getSwordPositionID();
            MethodEntityAction swordTickingAction = Blademaster037_executeSlaughterScapeSwordDamageAction(dealer, weaponType, finalDmg, swordHitAoeRange, condition, new HashSet<>(), new IntegerValueHolder(-1), new FloatValueHolder(BLADEMASTER037_INITIAL_VELOCITY), new TypeValueHolder<>(null));

            GeoModelSpawnMethods.GenerateSlaughterScapeEntity(dealer, getEntityBodyHorizontalFacingDeg(randomSwordEntity), 1f, 0, swordHitAoeRange, 1.1f + ExtraMathMethods.randomFloat(), getEntityX(randomSwordEntity), getEntityY(randomSwordEntity), getEntityZ(randomSwordEntity), 0, 0, 0, getEntityX(target), getEntityY(target), getEntityZ(target),
                    BLADEMASTER_037_FINAL_ANIMATION_TICKS, randomSwordEntity.getSwordPositionID(), getEntityHashID(target), randomSwordEntity.getTextureName(), BLADEMASTER_037_SHOOTING_SWORD_ANIMATION_NAME,
                    BLADEMASTER037_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_ACTION, swordTickingAction, 1, false, true);
        }
    }

    public static MethodEntityAction Blademaster037_ClientSwordChasingAction(FloatValueHolder initialAcceleration, IntegerValueHolder previousTickCount, TypeValueHolder<Vector3f> previousTargetLoc, HashSet<Entity> damagedGroup, Predicate<Entity> finalCondition) {
        return (swordEntity) -> {
            SlaughterscapeSwordEntity slaughterscapeSwordEntity = (SlaughterscapeSwordEntity) swordEntity;
            int currentSwordEntityElapsedLifeTime = BLADEMASTER_037_FINAL_ANIMATION_TICKS - slaughterscapeSwordEntity.getLifetime();
            if (currentSwordEntityElapsedLifeTime != previousTickCount.getIntCounter()) {
                previousTickCount.setIntCounter(currentSwordEntityElapsedLifeTime);
                Entity target = slaughterscapeSwordEntity.getTargetEntity();
                if (target != null && !SBConditions.isEntityAlive(target)) {
                    target = null;
                }
                float distShouldTravelEachTime = initialAcceleration.getFloatVal();
                float polarDist = presetDist1 - distShouldTravelEachTime;
                float swordHorizontalFacingAngle = getEntityHorizontalFacingDeg(slaughterscapeSwordEntity);
                float swordVerticalFacingAngle = getEntityVerticalFacingDeg(slaughterscapeSwordEntity);
                initialAcceleration.setFloatVal(distShouldTravelEachTime - (distShouldTravelDecay * interval));

                Vector3f originSwordEntityLoc = (previousTargetLoc.getAnyTypeValue() == null) ? getEntityPos(slaughterscapeSwordEntity) : previousTargetLoc.getAnyTypeValue();

                float swordHitAoe = slaughterscapeSwordEntity.getSwordHitAoeRange();

                for (Entity entity : GroupEnumEntitiesInRangeFilter(slaughterscapeSwordEntity, swordHitAoe, swordHitAoe, swordHitAoe, finalCondition)) {
//                    if (Vector3fMethods.Vec3fDistanceTo(originSwordEntityLoc.x(), originSwordEntityLoc.y(), originSwordEntityLoc.z(), getEntityX(entity), getEntityY(entity) + slaughterscapeSwordEntity.getRandomHitHeightOffset(), getEntityZ(entity)) <= swordHitAoe + 0.1f) {
                    if (Vector3fMethods.Vec3fDistanceTo(originSwordEntityLoc.x(), originSwordEntityLoc.y(), originSwordEntityLoc.z(), getEntityX(entity), getEntityY(entity) + slaughterscapeSwordEntity.getRandomHitHeightOffset(), getEntityZ(entity)) <= swordHitAoe) {
                        damagedGroup.add(entity);
                        //damage action
                    }
                }

                if (damagedGroup.contains(target)) {
                    if (!slaughterscapeSwordEntity.isHitOnTarget()) {
                        slaughterscapeSwordEntity.setHitOnTarget(true);
                        slaughterscapeSwordEntity.setClientHitEntityID(slaughterscapeSwordEntity.getTargetEntityID());
                        slaughterscapeSwordEntity.setTargetEntityID(-1);
                        if (target != null) {
                            float newXOffset = originSwordEntityLoc.x() - getEntityX(target);
                            float newYOffset = originSwordEntityLoc.y() - getEntityY(target);
                            float newZOffset = originSwordEntityLoc.z() - getEntityZ(target);

                            slaughterscapeSwordEntity.setLifetime(32);
                            slaughterscapeSwordEntity.setAdditionalPosX(newXOffset);
                            slaughterscapeSwordEntity.setAdditionalPosY(newYOffset);
                            slaughterscapeSwordEntity.setAdditionalPosZ(newZOffset);

                            //reset animation or pause
//                            slaughterscapeSwordEntity.setAnimationName(BLADEMASTER_037_SHOOTING_SWORD_ANIMATION_STOP_NAME);
                            int currentAnimationTick = slaughterscapeSwordEntity.getMaxLifeTime() - slaughterscapeSwordEntity.getLifetime();
                            float pausedAnimationSpeedModifier = 0.0001f;
                            slaughterscapeSwordEntity.setGeoAnimationSpeedModifier(pausedAnimationSpeedModifier);
                            float offset = 1f * currentAnimationTick / pausedAnimationSpeedModifier;
                            slaughterscapeSwordEntity.getAnimationController().setTickOffset(-offset);
                        }
                    }
                } else {
                    Vector3f tpLoc = Vector3fMethods.PolarProjectionFromRotation(originSwordEntityLoc, (target == null) ? 0.05f : polarDist, swordHorizontalFacingAngle, swordVerticalFacingAngle);
//                Vector3f tpLoc = Vector3fMethods.PolarProjectionFromVecHorizon(originSwordEntityLoc, (target == null) ? 0.05f : polarDist, swordHorizontalFacingAngle);
                    if (!slaughterscapeSwordEntity.hasClientNextMovingPos()) {
//                    slaughterscapeSwordEntity.setFromPosX(originSwordEntityLoc.x());
//                    slaughterscapeSwordEntity.setFromPosY(originSwordEntityLoc.y());
//                    slaughterscapeSwordEntity.setFromPosZ(originSwordEntityLoc.z());
                        slaughterscapeSwordEntity.setClientHasNextMovingPos(true);
                    }
                    slaughterscapeSwordEntity.setFromPosX(originSwordEntityLoc.x());
                    slaughterscapeSwordEntity.setFromPosY(originSwordEntityLoc.y());
                    slaughterscapeSwordEntity.setFromPosZ(originSwordEntityLoc.z());
                    slaughterscapeSwordEntity.setToPosX(tpLoc.x());
                    slaughterscapeSwordEntity.setToPosY(tpLoc.y());
                    slaughterscapeSwordEntity.setToPosZ(tpLoc.z());

                    previousTargetLoc.setAnyTypeValue(tpLoc);


                    if (target != null) {
                        Vector3f targetLoc = EntityMethods.getEntityBlockPos(target).add(0, slaughterscapeSwordEntity.getRandomHitHeightOffset(), 0);
                        float HorizontalAngleBetweenSwordEntityAndTarget = Vector3fMethods.AngleFromLocationToLocationHorizontal(originSwordEntityLoc, targetLoc);
                        float VerticalAngleBetweenSwordEntityAngTarget = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationVertical(originSwordEntityLoc, targetLoc) * MathMethods.RAD_TO_DEG);

                        float time = 0.01f + 0.99f * currentSwordEntityElapsedLifeTime / BLADEMASTER_037_FINAL_ANIMATION_TICKS;
                        float newHorizontalDegree = MathMethods.RoundAngle(MathMethods.lerpDegreeAngle(time, swordHorizontalFacingAngle, HorizontalAngleBetweenSwordEntityAndTarget));
                        float newVerticalDegree = MathMethods.RoundAngle(MathMethods.lerpDegreeAngle(time, MathMethods.RoundAngle(swordVerticalFacingAngle), VerticalAngleBetweenSwordEntityAngTarget));
                        setEntityHorizontalFacingDeg(slaughterscapeSwordEntity, newHorizontalDegree % 360);
                        setEntityVerticalFacingDeg(slaughterscapeSwordEntity, newVerticalDegree % 360);
                    }
                }
            }
        };
    }

    //================
    //client action
    //================
    public static void Blademaster037_ClientMethodInit(HashMap<String, MethodEntityAction> clientMethodMap) {
        clientMethodMap.put(BLADEMASTER037_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_ACTION, Blademaster037::Blademaster037_ClientSwordChasingInit);
        clientMethodMap.put(BLADEMASTER037_GROUND_SWORD_EFF_CLIENT_MOVING_CLOCKWISE_ACTION, Blademaster037::Blademaster037_Blademaster037_ClientMovingClockwiseInitAction);
        clientMethodMap.put(BLADEMASTER037_AIR_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_POS_ACTION, Blademaster037::Blademaster037_ClientMovingSwordActionInit);
    }

    public static void Blademaster037_ClientMovingSwordActionInit(Entity movingObj) {
        SlaughterscapeSwordEntity bmTempestSwordEntity = (SlaughterscapeSwordEntity) movingObj;
        MethodEntityAction entityAction = bmTempestSwordEntity.getClientMethodEntityLoopAction();

        if (entityAction == null) {
            bmTempestSwordEntity.setClientScale(bmTempestSwordEntity.getEntityScale());
            entityAction = Blademaster037_ClientMovingSwordAction(getEntityPos(bmTempestSwordEntity));
            bmTempestSwordEntity.setClientMethodEntityLoopAction(entityAction);
        }

        entityAction.executeAction(bmTempestSwordEntity);
    }

    public static MethodEntityAction Blademaster037_ClientMovingSwordAction(Vector3f originPos) {
        return (movingObj) -> {
            SlaughterscapeSwordEntity bmTempestSwordEntity = (SlaughterscapeSwordEntity) movingObj;

            int currentElapsedTicks = bmTempestSwordEntity.getMaxLifeTime() - bmTempestSwordEntity.getLifetime();
            if (currentElapsedTicks <= 20) {
                float previousX = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosX(): getEntityX(bmTempestSwordEntity);
                float previousY = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosY(): getEntityY(bmTempestSwordEntity);
                float previousZ = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosZ(): getEntityZ(bmTempestSwordEntity);
                float targetPosX = bmTempestSwordEntity.getTargetCenterPosX();
                float targetPosY = bmTempestSwordEntity.getTargetCenterPosY();
                float targetPosZ = bmTempestSwordEntity.getTargetCenterPosZ();

                float percent = currentElapsedTicks / 20f;
                float newX = Vector3fMethods.lerp1D(percent, originPos.x(), targetPosX);
                float newY = Vector3fMethods.lerp1D(percent, originPos.y(), targetPosY);
                float newZ = Vector3fMethods.lerp1D(percent, originPos.z(), targetPosZ);

                bmTempestSwordEntity.setClientHasNextMovingPos(true);
                bmTempestSwordEntity.setFromToPositions(previousX, previousY, previousZ, newX, newY, newZ);
                bmTempestSwordEntity.setPreviousToPosX(newX);
                bmTempestSwordEntity.setPreviousToPosY(newY);
                bmTempestSwordEntity.setPreviousToPosZ(newZ);
            } else {
                bmTempestSwordEntity.setLifetime(40);
                bmTempestSwordEntity.setClientHasNextMovingPos(false);
            }
        };
    }

    public static void Blademaster037_ClientSwordChasingInit(Entity swordEntity) {
        SlaughterscapeSwordEntity slaughterscapeSwordEntity = (SlaughterscapeSwordEntity) swordEntity;
        MethodEntityAction entityAction = slaughterscapeSwordEntity.getClientMethodEntityLoopAction();
        Entity owner;

        if (entityAction == null) {
            HashSet<Entity> damagedGroup = new HashSet<>();
            Predicate<Entity> clientCond = SBConditions.defaultSkillTargetClientCondition();
            if ((owner = slaughterscapeSwordEntity.getOwner()) != null) {
                clientCond = clientCond.and(entity -> !entity.equals(owner) && !damagedGroup.contains(entity));
            }
            Predicate<Entity> finalCondition = clientCond;
            entityAction = Blademaster037_ClientSwordChasingAction(new FloatValueHolder(BLADEMASTER037_INITIAL_VELOCITY), new IntegerValueHolder(-1), new TypeValueHolder<>(null), damagedGroup, finalCondition);
            slaughterscapeSwordEntity.setClientMethodEntityLoopAction(entityAction);
        }

        entityAction.executeAction(slaughterscapeSwordEntity);
    }

    public static void Blademaster037_Blademaster037_ClientMovingClockwiseInitAction(Entity swordEntity) {
        SlaughterscapeSwordEntity slaughterscapeSwordEntity = (SlaughterscapeSwordEntity) swordEntity;
        MethodEntityAction entityAction = slaughterscapeSwordEntity.getClientMethodEntityLoopAction();
        Entity owner;

        if (entityAction == null) {
            HashSet<Entity> damagedGroup = new HashSet<>();
            Predicate<Entity> clientCond = SBConditions.defaultSkillTargetClientCondition();
            if ((owner = slaughterscapeSwordEntity.getOwner()) != null) {
                ArrayList<SlaughterscapeSwordEntity> SwordList = BLADEMASTER_037_CLIENT_SWORD_ENTITY_MAP2ARR.get(owner);
                if (SwordList == null) {
                    SwordList = new ArrayList<>();
                    BLADEMASTER_037_CLIENT_SWORD_ENTITY_MAP2ARR.put(owner, SwordList);
                }
                SwordList.add(slaughterscapeSwordEntity);
                clientCond = clientCond.and(entity -> !entity.equals(owner) && !damagedGroup.contains(entity));
            }
            Predicate<Entity> finalCondition = clientCond;
            entityAction = Blademaster037_ClientMovingClockwiseAction(new FloatValueHolder(BLADEMASTER037_INITIAL_VELOCITY), new IntegerValueHolder(-1),
                    new TypeValueHolder<Vector3f>(null), new TypeValueHolder<Entity>(null), new IntegerValueHolder(-1),
                    new IntegerValueHolder(0), new FloatValueHolder(0), new FloatValueHolder(0), new BooleanValueHolder(false),
                    damagedGroup, finalCondition);
            slaughterscapeSwordEntity.setClientMethodEntityLoopAction(entityAction);
        }

        entityAction.executeAction(slaughterscapeSwordEntity);
    }

    public static MethodEntityAction Blademaster037_ClientMovingClockwiseAction(FloatValueHolder initialAcceleration, IntegerValueHolder previousTickCount, TypeValueHolder<Vector3f> previousTargetLoc, TypeValueHolder<Entity> targetHolder,
                                                                                IntegerValueHolder resetDamageIntervalHolder, IntegerValueHolder damageResetTimesHolder, FloatValueHolder previousRotationDegHolder, FloatValueHolder minRotationDegHolder,
                                                                                BooleanValueHolder isFinalDamageActionInitialized,
                                                                                HashSet<Entity> damagedGroup, Predicate<Entity> finalCondition) {
        return (movingObj) -> {
            SlaughterscapeSwordEntity SwordEntity = (SlaughterscapeSwordEntity) movingObj;

            Entity owner = SwordEntity.getOwner();
            float currentTotalDistHasTraveled = SwordEntity.getClientTotalDistHasTraveled();
            if (owner != null) {
                if (currentTotalDistHasTraveled <= BLADEMASTER_037_SWORD_MAX_DIST_FROM_OWNER) {
                    float originX = (SwordEntity.hasClientNextMovingPos()) ? SwordEntity.getPreviousToPosX() : getEntityX(SwordEntity);
                    float originY = (SwordEntity.hasClientNextMovingPos()) ? SwordEntity.getPreviousToPosY() : getEntityY(SwordEntity);
                    float originZ = (SwordEntity.hasClientNextMovingPos()) ? SwordEntity.getPreviousToPosZ() : getEntityZ(SwordEntity);
                    float newFacingAngle = SwordEntity.getDegreeShouldIncrementEachTime() + getEntityHorizontalFacingDeg(SwordEntity);

                    setEntityHorizontalFacingDeg(SwordEntity, newFacingAngle);

                    float newX = Vector3fMethods.PolarProjectionFromXHorizonWithOrigin(getEntityX(owner), currentTotalDistHasTraveled + BLADEMASTER037_DIST_SHOULD_TRAVEL_EACH_TICK, newFacingAngle) + SwordEntity.getAdditionalPosX();
                    float newY = getEntityY(owner) + SwordEntity.getAdditionalPosY();
                    float newZ = Vector3fMethods.PolarProjectionFromZHorizonWithOrigin(getEntityZ(owner), currentTotalDistHasTraveled + BLADEMASTER037_DIST_SHOULD_TRAVEL_EACH_TICK, newFacingAngle) + SwordEntity.getAdditionalPosZ();

                    SwordEntity.setFromToPositions(originX, originY, originZ, newX, newY, newZ);
                    SwordEntity.setPreviousToPosX(newX);
                    SwordEntity.setPreviousToPosY(newY);
                    SwordEntity.setPreviousToPosZ(newZ);

                    if (currentTotalDistHasTraveled < BLADEMASTER_037_SWORD_MAX_DIST_FROM_OWNER) {
                        SwordEntity.setClientHasNextMovingPos(true);
                        previousRotationDegHolder.setFloatVal(SwordEntity.getDegreeShouldIncrementEachTime());
                        minRotationDegHolder.setFloatVal(SwordEntity.getDegreeShouldIncrementEachTime() * 0.5f);
                    }

                    SwordEntity.setClientTotalDistHasTraveled(currentTotalDistHasTraveled + BLADEMASTER037_DIST_SHOULD_TRAVEL_EACH_TICK);
                } else {
                    if (SwordEntity.getLifetime() <= BLADEMASTER_037_FINAL_ANIMATION_TICKS) {
                        int currentSwordEntityElapsedLifeTime = BLADEMASTER_037_FINAL_ANIMATION_TICKS - SwordEntity.getLifetime();
                        if (!isFinalDamageActionInitialized.getBooleanVal()) {
                            isFinalDamageActionInitialized.setBooleanVal(true);
//                            BLADEMASTER_037_CLIENT_SWORD_ENTITY_ARR.clear();
                            BLADEMASTER_037_CLIENT_SWORD_ENTITY_MAP2ARR.remove(owner);
                            SwordEntity.setAnimationName(BLADEMASTER_037_SHOOTING_SWORD_ANIMATION_NAME);
                        }

                        if (currentSwordEntityElapsedLifeTime != previousTickCount.getIntCounter()) {
                            previousTickCount.setIntCounter(currentSwordEntityElapsedLifeTime);

                            Entity target = targetHolder.getAnyTypeValue();

                            float distShouldTravelEachTime = initialAcceleration.getFloatVal();
                            float polarDist = presetDist1 - distShouldTravelEachTime;
                            float swordHorizontalFacingAngle = getEntityHorizontalFacingDeg(SwordEntity);
                            float swordVerticalFacingAngle = getEntityVerticalFacingDeg(SwordEntity);
                            initialAcceleration.setFloatVal(distShouldTravelEachTime - (distShouldTravelDecay * interval));

                            Vector3f originSwordEntityLoc = (previousTargetLoc.getAnyTypeValue() == null) ? getEntityPos(SwordEntity) : previousTargetLoc.getAnyTypeValue();

                            float swordHitAoe = SwordEntity.getSwordHitAoeRange();

                            int resetDamageInterval = resetDamageIntervalHolder.getIntCounter();
                            if (resetDamageInterval == -1) {
                                if (target != null && !SBConditions.isEntityAlive(target)) {
                                    targetHolder.setAnyTypeValue(null);
                                    target = null;
                                }
                                if (target == null) {
                                    target = EntityMethods.findHorizontalNearbyEntityFromCenterEntityLoc(owner, 16, 16, 16, finalCondition);
                                    targetHolder.setAnyTypeValue(target);
                                    //call swords back if no targets found
                                    if (target == null) {
                                        target = owner;
                                    }
                                }

                                for (Entity entity : GroupEnumEntitiesInRangeFilter(SwordEntity, swordHitAoe, swordHitAoe, swordHitAoe, finalCondition)) {
                                    if (Vector3fMethods.Vec3fDistanceTo(originSwordEntityLoc.x(), originSwordEntityLoc.y(), originSwordEntityLoc.z(), getEntityX(entity), getEntityY(entity) + SwordEntity.getRandomHitHeightOffset(), getEntityZ(entity)) <= swordHitAoe) {
                                        damagedGroup.add(entity);
                                        //damage action
                                    }
                                }
                            }

                            if (damagedGroup.contains(target)) {
                                target = null;
                                int damageResetTimes = damageResetTimesHolder.getIntCounter();
                                if (damageResetTimes < BLADEMASTER_037_FINAL_ACTION_DAMAGE_RESET_MAX_TIMES) {
                                    if (resetDamageInterval < currentSwordEntityElapsedLifeTime) {
                                        resetDamageIntervalHolder.setIntCounter(currentSwordEntityElapsedLifeTime + resetClientDamageGroupTick);
                                    }
                                    else if (currentSwordEntityElapsedLifeTime >= resetDamageInterval) {
                                        target = targetHolder.getAnyTypeValue();
                                        resetDamageIntervalHolder.setIntCounter(-1);
                                        //random velocity?
//                                    initialAcceleration.setFloatVal(BLADEMASTER037_INITIAL_VELOCITY);
                                        damagedGroup.clear();
                                        damageResetTimesHolder.setIntCounter(damageResetTimes + 1);
                                        swordVerticalFacingAngle = ExtraMathMethods.randomFloat(360f);
                                    }
                                }
                            }
//                            Vector3f tpLoc = Vector3fMethods.PolarProjectionFromRotation(originSwordEntityLoc, (target == null) ? 0.15f : polarDist, swordHorizontalFacingAngle, swordVerticalFacingAngle);
                            Vector3f tpLoc = Vector3fMethods.PolarProjectionFromRotation(originSwordEntityLoc, polarDist, swordHorizontalFacingAngle, swordVerticalFacingAngle);
                            if (!SwordEntity.hasClientNextMovingPos()) {
                                SwordEntity.setClientHasNextMovingPos(true);
                            }
                            SwordEntity.setFromPosX(originSwordEntityLoc.x());
                            SwordEntity.setFromPosY(originSwordEntityLoc.y());
                            SwordEntity.setFromPosZ(originSwordEntityLoc.z());
                            SwordEntity.setToPosX(tpLoc.x());
                            SwordEntity.setToPosY(tpLoc.y());
                            SwordEntity.setToPosZ(tpLoc.z());

                            previousTargetLoc.setAnyTypeValue(tpLoc);

                            if (target != null) {
                                Vector3f targetLoc = EntityMethods.getEntityBlockPos(target).add(0, SwordEntity.getRandomHitHeightOffset(), 0);
                                float HorizontalAngleBetweenSwordEntityAndTarget = Vector3fMethods.AngleFromLocationToLocationHorizontal(originSwordEntityLoc, targetLoc);
                                float VerticalAngleBetweenSwordEntityAngTarget = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationVertical(originSwordEntityLoc, targetLoc) * MathMethods.RAD_TO_DEG);

                                float time = 0.01f + 0.99f * currentSwordEntityElapsedLifeTime / BLADEMASTER_037_FINAL_ANIMATION_TICKS;
                                float newHorizontalDegree = MathMethods.RoundAngle(MathMethods.lerpDegreeAngle(time, swordHorizontalFacingAngle, HorizontalAngleBetweenSwordEntityAndTarget));
                                float newVerticalDegree = MathMethods.RoundAngle(MathMethods.lerpDegreeAngle(time, MathMethods.RoundAngle(swordVerticalFacingAngle), VerticalAngleBetweenSwordEntityAngTarget));
                                setEntityHorizontalFacingDeg(SwordEntity, newHorizontalDegree % 360);
                                setEntityVerticalFacingDeg(SwordEntity, newVerticalDegree % 360);
                            }
                        }
                    }
                    else {
                        float originX = SwordEntity.getPreviousToPosX();
                        float originY = SwordEntity.getPreviousToPosY();
                        float originZ = SwordEntity.getPreviousToPosZ();

                        float newFacingAngle;
                        float previousRotationDeg = previousRotationDegHolder.getFloatVal();
                        if (previousRotationDeg > minRotationDegHolder.getFloatVal()) {
                            previousRotationDeg = previousRotationDeg - 0.1f;
                            previousRotationDegHolder.setFloatVal(previousRotationDeg);
                            newFacingAngle = previousRotationDeg + getEntityHorizontalFacingDeg(SwordEntity);
                        } else {
                            newFacingAngle = minRotationDegHolder.getFloatVal() + getEntityHorizontalFacingDeg(SwordEntity);
                        }

                        setEntityHorizontalFacingDeg(SwordEntity, newFacingAngle);

                        float newX = Vector3fMethods.PolarProjectionFromXHorizonWithOrigin(getEntityX(owner), BLADEMASTER_037_SWORD_MAX_DIST_FROM_OWNER, newFacingAngle) + SwordEntity.getAdditionalPosX();
                        float newY = getEntityY(owner) + SwordEntity.getAdditionalPosY();
                        float newZ = Vector3fMethods.PolarProjectionFromZHorizonWithOrigin(getEntityZ(owner), BLADEMASTER_037_SWORD_MAX_DIST_FROM_OWNER, newFacingAngle) + SwordEntity.getAdditionalPosZ();

                        SwordEntity.setFromToPositions(originX, originY, originZ, newX, newY, newZ);
                        SwordEntity.setPreviousToPosX(newX);
                        SwordEntity.setPreviousToPosY(newY);
                        SwordEntity.setPreviousToPosZ(newZ);
                    }
                }
            }
//        else {
            //TODO random selection disappear
//            SwordEntity.setClientHasNextMovingPos(false);
//        }
        };
    }

    public static ArrayList<SlaughterscapeSwordEntity> getBlademaster037ClientSwordEntityArr(Entity owner) {
        return BLADEMASTER_037_CLIENT_SWORD_ENTITY_MAP2ARR.get(owner);
    }

    public static void Blademaster037_resetClientMap(LivingEntity owner) {
        ArrayList<SlaughterscapeSwordEntity> tempSwordEntityArr = Blademaster037.BLADEMASTER_037_CLIENT_SWORD_ENTITY_MAP2ARR.remove(owner);

        if (tempSwordEntityArr != null) {
            for (SlaughterscapeSwordEntity entityTBR : tempSwordEntityArr) {
                if (SBConditions.isEntityAlive(entityTBR)) {
                    entityTBR.setLifetime(0);
                }
            }
        }
    }

    public static void Blademaster037_resetServerMap(LivingEntity owner) {
        ArrayList<SlaughterscapeSwordEntity> tempSwordEntityArr = Blademaster037.BLADEMASTER_037_ALL_SWORD_LIST.remove(owner);
        Blademaster037.BLADEMASTER_037_SKILL_TIMER.remove(owner);

        if (tempSwordEntityArr != null) {
            for (SlaughterscapeSwordEntity entityTBR : tempSwordEntityArr) {
                if (SBConditions.isEntityAlive(entityTBR)) {
                    entityTBR.setLifetime(0);
                    RemoveEntity(entityTBR);
                }
            }
        }
    }
}
