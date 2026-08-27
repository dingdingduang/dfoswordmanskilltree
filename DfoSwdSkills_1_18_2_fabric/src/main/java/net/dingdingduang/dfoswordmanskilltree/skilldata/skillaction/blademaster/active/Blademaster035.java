package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords.FancySwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword.BMTempestSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster036;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
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

import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import modified.org.joml.Vector3f;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.HashSetGroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods.SetupCircleTiltBlocksZone;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.*;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster035 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_RAIN_OF_FALLING_SWORDS;

    public static final String BLADEMASTER_035_PREPARATION = BlademasterSkillTreeInitialization.BLADEMASTER_RAIN_OF_FALLING_SWORDS + "_PREPARATION";
    public static final String BLADEMASTER_035_SLASH_ACTION = "BLADEMASTER_035_SLASH_ACTION";
    public static final String BLADEMASTER_035_ZENITH_OF_SWORD_ART_THROWS_HUGE_SWORD_ACTION = "BLADEMASTER_035_ZENITH_OF_SWORD_ART_THROWS_HUGE_SWORD_ACTION";

    public static final String BLADEMASTER_035_GEO_MODEL_PATH_PREFIX = "geo/sword";
    public static final String BLADEMASTER_035_GEO_MODEL_PATH_POSTFIX = ".geo.json";
    public static final String BLADEMASTER_035_TEXUTRE_PATH_PREFIX = "textures/entity/skilleff/blademaster/rain_of_swords/swords/sword";
    public static final String BLADEMASTER_035_TEXUTRE_PATH_POSTFIX = ".png";
    public static final String BLADEMASTER_035_ANIMATION_NAME = "noaction_ground_sword_0";
    public static final String BLADEMASTER_035_CHARGING_EFF_TEXTURE_PATH = "textures/entity/skilleff/common/charging/loop/";

    public static final String BLADEMASTER_035_TARGET_ZONE = "textures/entity/skilleff/blademaster/rain_of_swords/target_zone/";

    public static final String BLADEMASTER_035_FALLING_HIT_ON_GROUND_EFF_0 = "textures/entity/skilleff/blademaster/rain_of_swords/falling_eff_0/";
    public static final String BLADEMASTER_035_FALLING_ON_AIR_EFF_0 = "textures/entity/skilleff/blademaster/rain_of_swords/falling_eff_1/";

    public static final int BLADEMASTER_035_DAMAGE_INTERVAL = 1;
    public static final int BLADEMASTER_035_SWORD_DROPPING_DELAY_TICKS = 10;
    public static final float BLADEMASTER_035_SWORD_MAX_DROPPING_HEIGHT = 8f;

//    public static final float BLADEMASTER_035_SWORD_DIST_SHOULD_DESCEND_EACH_TIME = 1.5f;
    public static final float BLADEMASTER_035_SWORD_DIST_SHOULD_DESCEND_EACH_TIME = 1.5f;
    public static final float BLADEMASTER_035_SWORD_HEIGHT_OFFSET = -0.5f;

    public static final String BLADEMASTER035_GROUND_SWORD_EFF_CLIENT_MOVING_ACTION = "BLADEMASTER035_GROUND_SWORD_EFF_CLIENT_MOVING_ACTION";
    public static final String BLADEMASTER035_FANCY_SWORD_EFF_CLIENT_MOVING_ACTION = "BLADEMASTER035_FANCY_SWORD_EFF_CLIENT_MOVING_ACTION";

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
        tempSkillData.setTranslatableTooltipGetter(Blademaster035::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);

        if (isClientSide()) {
            return;
        }

        //server init
        //DoNothing()



        //====================================
        //setup skill action
        MethodAction tempChannelingAction =
                (entity1) -> {
                    if (entity1 instanceof ServerPlayerEntity sp1) {
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID) <= 0) { return; }
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_START, 1f);
                        DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardKidnapFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                    }

                    //IMPORTANT! setup action on to prevent casting other skills while this action
                    //being performed
                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    int actionPeriod = 5;
                    int channelingTickDelay = 5;
                    IntegerValueHolder ElapsedTick = new IntegerValueHolder(0);
                    BooleanValueHolder channelingSpawnEffect = new BooleanValueHolder(false);
                    IntegerValueHolder TotalChannelingTicksHolder = new IntegerValueHolder(0);
                    TypeValueHolder<Entity> targetZoneEntityHolder = new TypeValueHolder<>(null);

                    MethodAction action = (entity2) -> {
                        int updatedTick = ElapsedTick.getIntCounter();

                        boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                        if (isActionInterrupted) {
                            if (entity2 instanceof ServerPlayerEntity sp1) {
                                DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                            }

                            incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                            SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_035_PREPARATION);
                        }
                        else {
                            int channelingTotalTicks = TotalChannelingTicksHolder.getIntCounter();

                            if (!ServerSkillMethods.isLivingEntityInChannelingAction(entity2)) {
                                SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_035_PREPARATION);
//                                    if (entity2 instanceof ServerPlayerEntity sp1) {
//                                        NetworkingFetchMsgMethods.FetchPlayerResetLastKeyActionFromServer(sp1, BLADEMASTER_035_PREPARATION);
//                                    }

                                Vector3f targetZoneVec;
                                Entity targetZoneEntity = targetZoneEntityHolder.getAnyTypeValue();
                                if (targetZoneEntity != null) {
                                    targetZoneVec = getEntityPos(targetZoneEntity);
                                    RemoveEntity(targetZoneEntity);
                                }
                                else {
                                    targetZoneVec = getEntityPos(entity2);
                                }
                                Blademaster035_executeFallingSwordsAction(entity2, channelingTotalTicks, targetZoneVec);
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

                                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity2, 255, 255, 255, 1f, 0, 1.4f, 0, 900, BLADEMASTER_035_CHARGING_EFF_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                            DfoSwordmanSkillTreeConstants.NO_ANIMATION,0, 0, 13, false, false, false, DfoSwordmanSkillMethods.RemoveChannelingEffEntity(entity2), 5, true);

                                    Entity tempEntity = GeoModelSpawnMethods.GenerateTargetZoneSelectionEntity(entity2, getEntityHorizontalFacingDeg(entity2), 1f, getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 900, 0, 1, 0, true, false, false,
                                            BLADEMASTER_035_TARGET_ZONE, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillMethods.RemoveChannelingEffEntity(entity2), 5, false, true);

                                    targetZoneEntityHolder.setAnyTypeValue(tempEntity);
                                }
                            }
                        }

                        ElapsedTick.setIntCounter(updatedTick + actionPeriod);
                    };
                    SBSTickEventMethods.setMethodActionTimer(entity1, BLADEMASTER_035_PREPARATION, actionPeriod, action);
                };

        tempSkillData.setChannelingSkillAction1(tempChannelingAction);

        tempSkillData.setActiveSkillAction1(DfoSwordmanSkillMethods::DoNothing);
    }

    public static void Blademaster035_executeFallingSwordsAction(LivingEntity entity2, int totalChannelingTicks, Vector3f targetZoneVec) {
        float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity2);

        int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity2);
        int playerSkillLVL = 0;
        Predicate<Entity> condition;

//        ZenithOfSwordArtLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART);
//        shouldTriggerCloneExtraSlash = ZenithOfSwordArtLVL >= 7;
//        if (shouldTriggerCloneExtraSlash) {
//            cloneExtraSlashMultiplier = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART).getDamageMultiplierList_02().get(ZenithOfSwordArtLVL - 1).floatValue();
//        }
//        else {
//            cloneExtraSlashMultiplier = 0;
//        }
        SkillDataJson skill1 = getID2SkillData().get(SkillID);

        boolean shouldTriggerZenithSwordArtBonus;
        int ZenithOfSwordArtFallingSwordsHugeSwordCount;

        float ZenithOfSwordArtFallingSwordsZanbatoDamageMultiplier;

        float ZenithOfSwordArtFallingSwordsHugeSwordDamageMultiplier;
        float ZenithOfSwordArtFallingSwordsShortSwordSlownessDuration;
        if (entity2 instanceof ServerPlayerEntity sp1) {
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                if (playerSkillLVL <= 0) { return; }
                playerSkillLVL = playerSkillLVL - 1;
            }
            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_END, 1f);
            condition = defaultSkillTargetCondition(sp1).and(SBConditions.isNotTargetEntity(entity2));

            int ZenithOfSwordArtLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART);
            if (ZenithOfSwordArtLVL >= Blademaster036.BLADEMASTER036_ZENITH_OF_SWORD_ART_BONUS_EFFECT_4_LVL) {
                shouldTriggerZenithSwordArtBonus = true;
                int correctSkillLevel = ZenithOfSwordArtLVL - 1;
                SkillDataJson ZenithOfSwordArtSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART);
                ZenithOfSwordArtFallingSwordsHugeSwordCount = ZenithOfSwordArtSkillData.getValueUsage_02().get(correctSkillLevel).intValue();

                ZenithOfSwordArtFallingSwordsZanbatoDamageMultiplier = ZenithOfSwordArtSkillData.getValueMultiplierList_02().get(correctSkillLevel).floatValue();

                ZenithOfSwordArtFallingSwordsHugeSwordDamageMultiplier = ZenithOfSwordArtSkillData.getDamageList_02().get(correctSkillLevel).floatValue();
                ZenithOfSwordArtFallingSwordsShortSwordSlownessDuration = ZenithOfSwordArtSkillData.getDuration().get(correctSkillLevel).floatValue();
            }
            else {
                shouldTriggerZenithSwordArtBonus = false;
                ZenithOfSwordArtFallingSwordsZanbatoDamageMultiplier = 0;
                ZenithOfSwordArtFallingSwordsHugeSwordCount = 0;
                ZenithOfSwordArtFallingSwordsHugeSwordDamageMultiplier = 0;
                ZenithOfSwordArtFallingSwordsShortSwordSlownessDuration = 0;
            }
        }
        else {
            shouldTriggerZenithSwordArtBonus = false;
            ZenithOfSwordArtFallingSwordsZanbatoDamageMultiplier = 0;
            ZenithOfSwordArtFallingSwordsHugeSwordCount = 0;
            ZenithOfSwordArtFallingSwordsHugeSwordDamageMultiplier = 0;
            ZenithOfSwordArtFallingSwordsShortSwordSlownessDuration = 0;
            condition = defaultSkillTargetCondition().and(SBConditions.isNotTargetEntity(entity2));
        }

        float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
        float swordHitAoeRange = skill1.getValueUsage_01().get(playerSkillLVL).floatValue();

        float damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
        float damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();

        float LastSlashConstantDamage = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
        float LastSlashDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue();

        int TotalSlashCount = skill1.getValueUsage_02().get(playerSkillLVL).intValue();

        int actionPeriod = 2;

        double dealerAttackDamage = getEntityAttackDamage(entity2);
        float finalDmg = (float) ((damageMultiplier * dealerAttackDamage + damageBase) * skillDamageMultiplier);
        float finalLastSlashDmg = (float) ((LastSlashDamageMultiplier * dealerAttackDamage + LastSlashConstantDamage) * skillDamageMultiplier);

        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 1f, 1f);
        DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 2f);

        MethodAction action = Blademaster035_SlashBeforeDamageAction(weaponType, condition, aoeRange, swordHitAoeRange, finalDmg, finalLastSlashDmg, actionPeriod, TotalSlashCount, targetZoneVec, shouldTriggerZenithSwordArtBonus, ZenithOfSwordArtFallingSwordsShortSwordSlownessDuration, ZenithOfSwordArtFallingSwordsZanbatoDamageMultiplier);

        //start loop actioning with fixed period ticks
        SBSTickEventMethods.setMethodActionTimer(entity2, SkillID, actionPeriod, action);

        //final huge sword action
        if (shouldTriggerZenithSwordArtBonus) {
            String ZenithSwordArtHugeSwordActionID = BLADEMASTER_035_ZENITH_OF_SWORD_ART_THROWS_HUGE_SWORD_ACTION + getEntityTickCount(entity2);
            int finalActionTickPeriod = 1;
            float hugeSwordFinalDamage = finalLastSlashDmg * ZenithOfSwordArtFallingSwordsHugeSwordDamageMultiplier;
            MethodAction finalAction = Blademaster035_HugeSwordDamageAction(targetZoneVec, finalActionTickPeriod, new IntegerValueHolder(0), 5, ZenithOfSwordArtFallingSwordsHugeSwordCount, aoeRange, hugeSwordFinalDamage, new HashSet<>(), new HashSet<>(), new HashSet<>(), new BooleanValueHolder(false), condition, ZenithSwordArtHugeSwordActionID);

            SBSTickEventMethods.setMethodActionTimer(entity2, ZenithSwordArtHugeSwordActionID, finalActionTickPeriod, finalAction);
        }
    }

    public static MethodAction Blademaster035_SlashBeforeDamageAction(int weaponType, Predicate<Entity> condition, float aoeRange, float swordHitAoeRange, float finalDmg, float finalLastSlashDmg, int actionPeriod, int totalSlashCount, Vector3f targetZoneVec, boolean shouldTriggerZenithSwordArtBonus, float ShortSwordSlownessDuration, float ZenithOfSwordArtFallingSwordsZanbatoDamageMultiplier) {
        IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
        BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
        BooleanValueHolder ExecuteSlashActionYet = new BooleanValueHolder(false);

        MethodAction action =
                (entity3) -> {
                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity3);

                    int updatedTick = ElapsedTicks.getIntCounter();

                    if (!isActionInterrupted && updatedTick >= BLADEMASTER_035_SWORD_DROPPING_DELAY_TICKS && !ExecuteSlashActionYet.getBooleanVal()) {
                        ExecuteSlashActionYet.setBooleanVal(true);
                        Vector3f triggeredEntityPos = getEntityPos(entity3);
                        DfoSwdSoundMethods.PlayBlademaster027DrawSwordSlashSound(getEntityLevel(entity3), triggeredEntityPos.x(), triggeredEntityPos.y(), triggeredEntityPos.z(), 0.8f, 4f);

                        Vector3f damageCenter = (targetZoneVec == null) ? Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, 2f, getEntityHorizontalFacingDeg(entity3)) : targetZoneVec;

                        String slashActionID = BLADEMASTER_035_SLASH_ACTION + getEntityTickCount(entity3);
                        MethodAction slashAction = Blademaster035_SlashStartDamageAction(weaponType, aoeRange, swordHitAoeRange, finalDmg, finalLastSlashDmg, new IntegerValueHolder(totalSlashCount), damageCenter, condition, new HashSet<>(), new HashSet<>(), shouldTriggerZenithSwordArtBonus, ShortSwordSlownessDuration, ZenithOfSwordArtFallingSwordsZanbatoDamageMultiplier, slashActionID);

                        SBSTickEventMethods.setMethodActionTimer(entity3, slashActionID, BLADEMASTER_035_DAMAGE_INTERVAL, slashAction);
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
                                if (updatedTick > BLADEMASTER_035_SWORD_DROPPING_DELAY_TICKS) {
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

    public static MethodAction Blademaster035_SlashStartDamageAction(int weaponType, float aoeRange, float swordHitAoeRange, float tempFinalDmg, float tempFinalLastSlashDmg, IntegerValueHolder RemainingSlashTimesHolder, Vector3f damageCenter, Predicate<Entity> condition, HashSet<Entity> entityTBRSet, HashSet<Entity> fallingSwordEntitySet, boolean shouldTriggerZenithOfSwordArtBonus, float ShortSwordSlownessDuration, float ZenithOfSwordArtFallingSwordsZanbatoDamageMultiplier, String ActionID) {
        boolean isWieldingLightsabre = weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE;
        boolean isWieldingKatana = weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA;
        boolean isWieldingShortSword = weaponType == DfoSkillTreeItemMethods.IS_AVERAGE_SHORT_SWORD;
        boolean isWieldingBludgeon = weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON;
        boolean isWieldingZanbato = weaponType == DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO;
        float ExtraDamageMultiplier = (shouldTriggerZenithOfSwordArtBonus && isWieldingZanbato) ? 1f + ZenithOfSwordArtFallingSwordsZanbatoDamageMultiplier: 1f;
        float finalDmg = tempFinalDmg * ExtraDamageMultiplier;
        float finalLastSlashDmg = tempFinalLastSlashDmg * ExtraDamageMultiplier;

        int ZenithOfSwordArtFallingSwordsShortSwordSlownessDurationTicks = (int) (ShortSwordSlownessDuration * 20);
        int ZenithOfSwordArtFallingSwordsBludgeonStunDurationTicks = (int) (ShortSwordSlownessDuration * 5);

        return (entity4) -> {
            int RemainingSlashCount = RemainingSlashTimesHolder.getIntCounter();

            if (RemainingSlashCount > 0) {
                Vector3f randomPoint3D = Vector3fMethods.randomVecIn3DCylinder(aoeRange, BLADEMASTER_035_SWORD_MAX_DROPPING_HEIGHT, damageCenter);

                float randomFacingAngle = ExtraMathMethods.randomFloat(360f);
                int randomTextureInt = ExtraMathMethods.randomInt(7);
                if (RemainingSlashCount == 1) {
//                    Entity tempSwordEntity = GeoModelSpawnMethods.GenerateBMTempestSwordEntity(entity4, randomFacingAngle, 4f, aoeRange, damageCenter.x(), damageCenter.y() + BLADEMASTER_035_SWORD_MAX_DROPPING_HEIGHT, damageCenter.z(),
//                            100, damageCenter.x(), damageCenter.y(), damageCenter.z(), Blademaster025.BLADEMASTER025_SWORD_TEXTURE_PATH + ExtraMathMethods.randomInt(5) + ".png", Blademaster025.BLADEMASTER025_TEMPEST_GROUND_SWORD_ANIMATION,
//                            BLADEMASTER035_GROUND_SWORD_EFF_CLIENT_MOVING_ACTION, null, 777, true);

                    Entity tempSwordEntity = GeoModelSpawnMethods.GenerateFancySwordEff(entity4, randomFacingAngle, 0f, 0f, 3f, damageCenter.x(), damageCenter.y() + BLADEMASTER_035_SWORD_MAX_DROPPING_HEIGHT, damageCenter.z(),
                            100, damageCenter.x(), damageCenter.y(), damageCenter.z(), BLADEMASTER_035_GEO_MODEL_PATH_PREFIX + randomTextureInt + BLADEMASTER_035_GEO_MODEL_PATH_POSTFIX, BLADEMASTER_035_TEXUTRE_PATH_PREFIX + randomTextureInt + BLADEMASTER_035_TEXUTRE_PATH_POSTFIX, BLADEMASTER_035_ANIMATION_NAME,
                            BLADEMASTER035_FANCY_SWORD_EFF_CLIENT_MOVING_ACTION, null, 777, true);

                    fallingSwordEntitySet.add(tempSwordEntity);

                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(tempSwordEntity, randomFacingAngle + 90f, false, 4f, 0, 0, 0, 8, BLADEMASTER_035_FALLING_ON_AIR_EFF_0, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                            0, 0, 4, false, false, false, false, Blademaster035::Blademaster035_RemoveEffEntityIfReachToGround, 5, true);

                    DfoSwdSoundMethods.PlayZanbatoWieldingSound(getEntityLevel(entity4), damageCenter.x(), damageCenter.y() + BLADEMASTER_035_SWORD_MAX_DROPPING_HEIGHT, damageCenter.z(), 0.8f, 1f);
                }
                else {
                    Entity tempSwordEntity = GeoModelSpawnMethods.GenerateBMTempestSwordEntity(entity4, randomFacingAngle, 1f, aoeRange, randomPoint3D.x(), randomPoint3D.y(), randomPoint3D.z(),
                            100, randomPoint3D.x(), damageCenter.y(), randomPoint3D.z(), Blademaster025.BLADEMASTER025_SWORD_TEXTURE_PATH + ExtraMathMethods.randomInt(5) + ".png", Blademaster025.BLADEMASTER025_TEMPEST_GROUND_SWORD_ANIMATION,
                            BLADEMASTER035_GROUND_SWORD_EFF_CLIENT_MOVING_ACTION, null, 777, true);

//                    Entity tempSwordEntity = GeoModelSpawnMethods.GenerateFancySwordEff(entity4, randomFacingAngle, 0f, 0f, 0.5f, randomPoint3D.x(), randomPoint3D.y(), randomPoint3D.z(),
//                            100, randomPoint3D.x(), damageCenter.y(), randomPoint3D.z(), BLADEMASTER_035_GEO_MODEL_PATH_PREFIX + randomTextureInt + BLADEMASTER_035_GEO_MODEL_PATH_POSTFIX, BLADEMASTER_035_TEXUTRE_PATH_PREFIX + randomTextureInt + BLADEMASTER_035_TEXUTRE_PATH_POSTFIX, BLADEMASTER_035_ANIMATION_NAME,
//                            BLADEMASTER035_FANCY_SWORD_EFF_CLIENT_MOVING_ACTION, null, 777, true);

                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(tempSwordEntity, randomFacingAngle + 90f, false, 1f, 0, 0, 0, 8, BLADEMASTER_035_FALLING_ON_AIR_EFF_0, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                            0, 0, 4, false, false, false, false, Blademaster035::Blademaster035_RemoveEffEntityIfReachToGround, 5, true);

                    fallingSwordEntitySet.add(tempSwordEntity);

                    DfoSwdSoundMethods.PlayBludgeonWieldingSound(getEntityLevel(entity4), randomPoint3D.x(), randomPoint3D.y(), randomPoint3D.z(), 0.8f, 1f);
                }
            }

            for (Entity fallingSwordEntity: fallingSwordEntitySet) {
                if (!isEntityFarFromGround(fallingSwordEntity, BLADEMASTER_035_SWORD_DIST_SHOULD_DESCEND_EACH_TIME + BLADEMASTER_035_SWORD_HEIGHT_OFFSET) && SBConditions.isEntityAlive(fallingSwordEntity)) {
                    moveEntityToXYZ(fallingSwordEntity, getEntityX(fallingSwordEntity), getEntityY(fallingSwordEntity) - BLADEMASTER_035_SWORD_DIST_SHOULD_DESCEND_EACH_TIME, getEntityZ(fallingSwordEntity));
                }
                else {
                    entityTBRSet.add(fallingSwordEntity);
                }
            }

            for (Entity triggerFallingSwordEntityAction: entityTBRSet) {
                fallingSwordEntitySet.remove(triggerFallingSwordEntityAction);

                boolean isLastSlash = fallingSwordEntitySet.isEmpty() && RemainingSlashCount <= 1;

//                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(triggerFallingSwordEntityAction, 255, 255, 255, (isLastSlash) ? 4f: 1f, 0, (isLastSlash) ? 4f: 1.6f, 0, 8, BLADEMASTER_035_FALLING_HIT_ON_GROUND_EFF_0, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
//                        DfoSwordmanSkillTreeConstants.NO_ANIMATION,8, 0, 0, false, false, false, null, 777, true);

//                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(triggerFallingSwordEntityAction, getEntityHorizontalFacingDeg(triggerFallingSwordEntityAction) + 90f, false, (isLastSlash) ? 4f: 1f, 0, (isLastSlash) ? 8f: 2f, 0, 8, BLADEMASTER_035_FALLING_HIT_ON_GROUND_EFF_0, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
//                        8, 0, 0, false, false, false, false, null, 777, true);

                Vector3f swordEntityPos = getEntityPos(triggerFallingSwordEntityAction);

                if (isLastSlash) {
                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(triggerFallingSwordEntityAction, getEntityHorizontalFacingDeg(triggerFallingSwordEntityAction) + 90f, false, 4f, 0, 8f, 0, 8, BLADEMASTER_035_FALLING_HIT_ON_GROUND_EFF_0, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                            8, 0, 0, false, false, false, false, null, 777, true);

                    float finalAoeRange = aoeRange * ((shouldTriggerZenithOfSwordArtBonus && isWieldingZanbato) ? 1.5f : 1f);
                    SetupCircleTiltBlocksZone(new Vector3f(swordEntityPos.x(), swordEntityPos.y() - 1f, swordEntityPos.z()), getEntityLevel(entity4),
                            aoeRange * 1.5f, 70, 0.6f, 5,
                            true, true, true, true);


                    for (Entity tempEntity : HashSetGroupEnumEntitiesInRangeFilter(entity4, swordEntityPos, finalAoeRange, 2, finalAoeRange, condition)) {
                        if (tempEntity instanceof LivingEntity tempLivingEntity) {
                            GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 1f, 4, 4,
                                    ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f,
                                    GeoAnimationAndTextureName.EFF_HIT_EFF_ADVANCE_ARR[ExtraMathMethods.randomInt(3)], true, true);

                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity4, tempLivingEntity, finalLastSlashDmg, false);

                            DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, EntityMethods.getEntityLevel(entity4), EntityMethods.getEntityX(tempLivingEntity), EntityMethods.getEntityY(tempLivingEntity), EntityMethods.getEntityZ(tempLivingEntity), 0.8f, 1.0f);

                            float correctDamagedEntityAngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(swordEntityPos, getEntityPos(tempLivingEntity));
                            float pushStrength = EntityMethods.calculatePushEntityStrength(0.5f, entity4, tempLivingEntity);
                            EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, correctDamagedEntityAngleBtn, pushStrength, pushStrength * 0.5f, true);

                            if (shouldTriggerZenithOfSwordArtBonus) {
                                if (isWieldingLightsabre) {
                                    DfoStatusGeneralMethods.ApplyShockStatusToTargetLivingEntity(entity4, tempLivingEntity, DfoStatusGeneralMethods.GENERAL_DURATION_TICKS_FOR_SHOCK_STATUS, finalLastSlashDmg, DfoStatusGeneralMethods.GENERAL_MAX_INFLICT_TIMES_FOR_SHOCK_STATUS, true, SkillID);
                                }
                                else if (isWieldingKatana) {
                                    DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity4, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalLastSlashDmg);
                                }
                                else if (isWieldingShortSword) {
                                    TimedAttributesDistributor.applyTimedMovementSpeedToEntity(tempLivingEntity, ZenithOfSwordArtFallingSwordsShortSwordSlownessDurationTicks, -0.75f, Constants.OP_MULTIPLY_BASE, false, SkillID, null, null);
                                }
                                else if (isWieldingBludgeon) {
                                    DfoStatusGeneralMethods.ApplyStunStatusToTargetLivingEntity(entity4, tempLivingEntity, ZenithOfSwordArtFallingSwordsBludgeonStunDurationTicks, 0.1f);
                                }
                            }
                        }
                    }
                }
                else {
                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(triggerFallingSwordEntityAction, getEntityHorizontalFacingDeg(triggerFallingSwordEntityAction) + 90f, false, 1f, 0, 2f, 0, 8, BLADEMASTER_035_FALLING_HIT_ON_GROUND_EFF_0, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                            8, 0, 0, false, false, false, false, null, 777, true);

                    for (Entity tempEntity : HashSetGroupEnumEntitiesInRangeFilter(entity4, swordEntityPos, swordHitAoeRange, 2, swordHitAoeRange, condition)) {
                        if (tempEntity instanceof LivingEntity tempLivingEntity) {
                            GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 1f, 4, 4,
                                    ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f,
                                    GeoAnimationAndTextureName.EFF_HIT_EFF_ADVANCE_ARR[ExtraMathMethods.randomInt(3)], true, true);

                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity4, tempLivingEntity,  finalDmg, false);

                            DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, EntityMethods.getEntityLevel(entity4), EntityMethods.getEntityX(tempLivingEntity), EntityMethods.getEntityY(tempLivingEntity), EntityMethods.getEntityZ(tempLivingEntity), 0.8f, 1.0f);

                            float correctDamagedEntityAngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(swordEntityPos, getEntityPos(tempLivingEntity));
                            float pushStrength = EntityMethods.calculatePushEntityStrength(0.05f, entity4, tempLivingEntity);
                            EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, correctDamagedEntityAngleBtn, pushStrength, pushStrength * 0.5f, true);
                        }
                    }
                }
            }
            if (!entityTBRSet.isEmpty()) {
                entityTBRSet.clear();
            }

            if (fallingSwordEntitySet.isEmpty() && RemainingSlashCount <= 0) {
                SBSTickEventMethods.setSkillActionDone(entity4, ActionID);
            }

            RemainingSlashTimesHolder.setIntCounter(RemainingSlashCount - 1);
        };
    }

    public static MethodAction Blademaster035_HugeSwordDamageAction(Vector3f tempDamageCenter, int actionPeriod, IntegerValueHolder ElapsedTicksHolder, int spawnAfterXTicks, int hugeSwordCount, float finalAoeZoneRange, float finalDamage, HashSet<LivingEntity> damagedGroup, HashSet<SlaughterscapeSwordEntity> movingSwordEntitySet, HashSet<SlaughterscapeSwordEntity> tempSwordEntitySetTBR, BooleanValueHolder spawnedFinalEntityYet, Predicate<Entity> condition, String ActionID) {
        return (entity5) -> {
            int ElapsedTicks = ElapsedTicksHolder.getIntCounter() + actionPeriod;
            ElapsedTicksHolder.setIntCounter(ElapsedTicks);

            if (ElapsedTicks > spawnAfterXTicks) {
                if (!spawnedFinalEntityYet.getBooleanVal()) {
                    spawnedFinalEntityYet.setBooleanVal(true);

                    Vector3f damageCenter = new Vector3f(tempDamageCenter.x(), tempDamageCenter.y() + DfoSwordmanSkillTreeConstants.MC_DEFAULT_EYE_HEIGHT, tempDamageCenter.z());

                    float randomHorizontalFacingAngle;
                    Vector3f tempHorizontalFromPos;
                    Vector3f tempHorizontalToPos;
                    SlaughterscapeSwordEntity tempSwordEntity;

                    for (int i = 0; i < hugeSwordCount; i++) {
                        randomHorizontalFacingAngle = ExtraMathMethods.randomFloat(360f);
                        tempHorizontalFromPos = Vector3fMethods.PolarProjectionFromVecHorizon(damageCenter, Blademaster037.BLADEMASTER037_MAX_SWORD_DIST_FROM_TARGET_POS, randomHorizontalFacingAngle);
                        tempHorizontalToPos = Vector3fMethods.PolarProjectionFromVecHorizon(damageCenter, 2 * Blademaster037.BLADEMASTER037_MAX_SWORD_DIST_FROM_TARGET_POS, randomHorizontalFacingAngle + 180f);

                        tempSwordEntity = GeoModelSpawnMethods.GenerateSlaughterScapeEntity(entity5, randomHorizontalFacingAngle + 180f, Blademaster037.BLADEMASTER037_LAST_SWORD_ENTITY_SCALE, 0, finalAoeZoneRange, 0, tempHorizontalFromPos.x(), tempHorizontalFromPos.y(), tempHorizontalFromPos.z(),0, 0, 0, tempHorizontalToPos.x(), tempHorizontalToPos.y(), tempHorizontalToPos.z(),
                                Blademaster037.BLADEMASTER_037_FINAL_ANIMATION_TICKS, 0, -1, Blademaster025.BLADEMASTER025_SWORD_TEXTURE_PATH + ExtraMathMethods.randomInt(5) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX, Blademaster037.BLADEMASTER_037_SHOOTING_SWORD_ANIMATION_NAME,
                                Blademaster037.BLADEMASTER037_AIR_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_POS_ACTION, null, 777, false, true);

                        movingSwordEntitySet.add(tempSwordEntity);
                    }

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
                        SBSTickEventMethods.setSkillActionDone(entity5, ActionID);
                    }
                }
            }
        };
    }

    public static void Blademaster035_RemoveEffEntityIfReachToGround(Entity fallingSwordEntityEff) {
        if (isEntityFarFromGround(fallingSwordEntityEff, BLADEMASTER_035_SWORD_DIST_SHOULD_DESCEND_EACH_TIME + BLADEMASTER_035_SWORD_HEIGHT_OFFSET)) {
            RemoveEntity(fallingSwordEntityEff);
        }
    }

    //client action
    public static void Blademaster035_ClientMethodInit(HashMap<String, MethodEntityAction> clientMethodMap) {
        clientMethodMap.put(BLADEMASTER035_GROUND_SWORD_EFF_CLIENT_MOVING_ACTION, Blademaster035::Blademaster035_ClientMovingGroundEffObjectAction);
        clientMethodMap.put(BLADEMASTER035_FANCY_SWORD_EFF_CLIENT_MOVING_ACTION, Blademaster035::Blademaster035_ClientMovingFancySwordAction);
    }

    //tp HououinSeia @e[type=dfoswordmanskilltree:effswdtempestswordeff,sort=nearest,limit=1]
    public static void Blademaster035_ClientMovingGroundEffObjectAction(Entity movingObj) {
        BMTempestSwordEntity bmTempestSwordEntity = (BMTempestSwordEntity) movingObj;

        if (!isEntityFarFromGround(bmTempestSwordEntity, BLADEMASTER_035_SWORD_DIST_SHOULD_DESCEND_EACH_TIME + BLADEMASTER_035_SWORD_HEIGHT_OFFSET)) {
            float originX = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosX(): getEntityX(bmTempestSwordEntity);
            float originY = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosY(): getEntityY(bmTempestSwordEntity);
            float originZ = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosZ(): getEntityZ(bmTempestSwordEntity);

            float newY = originY - BLADEMASTER_035_SWORD_DIST_SHOULD_DESCEND_EACH_TIME;

            bmTempestSwordEntity.setClientHasNextMovingPos(true);
            bmTempestSwordEntity.setFromToPositions(originX, originY, originZ, originX, newY, originZ);
            bmTempestSwordEntity.setPreviousToPosX(originX);
            bmTempestSwordEntity.setPreviousToPosY(newY);
            bmTempestSwordEntity.setPreviousToPosZ(originZ);
        }
        else {
            bmTempestSwordEntity.setClientHasNextMovingPos(false);
        }
    }

    public static void Blademaster035_ClientMovingFancySwordAction(Entity movingObj) {
        FancySwordEntity bmTempestSwordEntity = (FancySwordEntity) movingObj;

        if (!isEntityFarFromGround(bmTempestSwordEntity, BLADEMASTER_035_SWORD_DIST_SHOULD_DESCEND_EACH_TIME + BLADEMASTER_035_SWORD_HEIGHT_OFFSET)) {
            float originX = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosX(): getEntityX(bmTempestSwordEntity);
            float originY = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosY(): getEntityY(bmTempestSwordEntity);
            float originZ = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosZ(): getEntityZ(bmTempestSwordEntity);

            float newY = originY - BLADEMASTER_035_SWORD_DIST_SHOULD_DESCEND_EACH_TIME;

            bmTempestSwordEntity.setClientHasNextMovingPos(true);
            bmTempestSwordEntity.setFromToPositions(originX, originY, originZ, originX, newY, originZ);
            bmTempestSwordEntity.setPreviousToPosX(originX);
            bmTempestSwordEntity.setPreviousToPosY(newY);
            bmTempestSwordEntity.setPreviousToPosZ(originZ);
        }
        else {
            bmTempestSwordEntity.setClientHasNextMovingPos(false);
        }
    }
}
