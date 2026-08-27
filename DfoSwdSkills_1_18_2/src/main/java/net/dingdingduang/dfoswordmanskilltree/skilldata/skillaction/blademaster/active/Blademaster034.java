package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster010;
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
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import modified.org.joml.Vector3f;

import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.HashSetGroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.*;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster034 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_SENSE_SLASH;

    public static final String BLADEMASTER_034_PREPARATION = BlademasterSkillTreeInitialization.BLADEMASTER_SENSE_SLASH + "_PREPARATION";
    public static final String BLADEMASTER_034_SLASH_ACTION = "BLADEMASTER_034_SLASH_ACTION";
    public static final String BLADEMASTER_034_ZENITH_OF_SWORD_ART_SWORD_QI_ACTION = "BLADEMASTER_034_ZENITH_OF_SWORD_ART_SWORD_QI_ACTION";

    public static final String BLADEMASTER_034_DRAW_SWORD_QI_START_ANIMATION_NAME = "drawsword_start2";
    public static final String BLADEMASTER_034_DRAW_SWORD_QI_CHANNELING_ANIMATION_NAME = "drawsword_channeling2";
    public static final String BLADEMASTER_034_DRAW_SWORD_QI_SLASH_ANIMATION_NAME = "drawsword_slash2";

    public static final String BLADEMASTER_034_DRAW_SWORD_SLASH_BLUE_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_large_sword_qi/draw_sword_eff/";
//    public static final String BLADEMASTER_034_DRAW_LARGE_SWORD_QI_BLUE_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_large_sword_qi/large_sword_qi/";
//    public static final String BLADEMASTER_034_CHARGING_EFF_1_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_large_sword_qi/charging_eff1/";
    public static final String BLADEMASTER_034_CHARGING_EFF_2_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_large_sword_qi/charging_eff2/";

//    public static final String BLADEMASTER_034_HIT_EFF_0_TEXTURE_PATH = "textures/entity/skilleff/common/hit_damage/";
//    public static final String BLADEMASTER_034_HIT_EFF_1_TEXTURE_PATH = "textures/entity/skilleff/blademaster/sense_slash/hit_eff1/";
//    public static final String BLADEMASTER_034_HIT_EFF_2_TEXTURE_PATH = "textures/entity/skilleff/blademaster/sense_slash/hit_eff2/";

    public static final int BLADEMASTER_034_DAMAGE_INTERVAL = 1;
    public static final float BLADEMASTER_034_ZANBATO_DAMAGE_MULTIPLIER = 0.5f;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double AOE = skillData.getEffectArea().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);
        Double LastSlashDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double LastSlashConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);
        Float LastSlashFinalDmg = (float) ( (LastSlashDamageMultiplier * getEntityAttackDamage(getClientPlayer()) + LastSlashConstantDamage) * skillDamageMultiplier);
        Double MaxChannelingTime = skillData.getValueUsage_01().get(correctSkillLevel);
        Double MaxDamageMultiplierBonus = skillData.getDamageMultiplierList_03().get(correctSkillLevel);
        int InitialSlashCount = skillData.getValueUsage_02().get(correctSkillLevel).intValue();
        Double MaxSlashCountMultiplierBonus = skillData.getValueMultiplierList_01().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%s", AOE),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (LastSlashDamageMultiplier.floatValue() * 100)),
                String.format("%s", LastSlashConstantDamage),
                String.format("%s", LastSlashFinalDmg),
                String.format("%ss", MaxChannelingTime),
                String.format("%d%%", (int) (MaxDamageMultiplierBonus.floatValue() * 100)),
                String.format("%d", InitialSlashCount),
                String.format("%d%%", (int) (MaxSlashCountMultiplierBonus.floatValue() * 100))
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster034::getSkillDescription);

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
                    int stationaryTime = 20;
                    int playerSkillLevel = 0;
                    Predicate<Entity> condition;

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);
                    if (entity1 instanceof ServerPlayer sp1) {
                        playerSkillLevel = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                        if (playerSkillLevel <= 0) { return; }
                        playerSkillLevel = playerSkillLevel - 1;
                        float stationarySec = skill1.getStationaryTime().get(playerSkillLevel).floatValue();
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_034_DRAW_SWORD_QI_START_ANIMATION_NAME, 1f / stationarySec);
                        DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardKidnapFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                        stationaryTime = (int) (stationarySec * 20);
                        condition = defaultSkillTargetCondition(sp1);
                    } else {
                        condition = defaultSkillTargetCondition();
                    }

                    float aoeRange = skill1.getEffectArea().get(playerSkillLevel).floatValue();

                    //IMPORTANT! setup action on to prevent casting other skills while this action
                    //being performed
                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    int actionPeriod = 5;
                    int channelingTickDelay = 5;
                    final int finalStationaryTime = stationaryTime;
                    IntegerValueHolder ElapsedTick = new IntegerValueHolder(0);
                    IntegerValueHolder RemainingTicksBeforeInitiate = new IntegerValueHolder(stationaryTime);
                    BooleanValueHolder channelingSpawnEffect = new BooleanValueHolder(false);
                    BooleanValueHolder beforeInitiateActionExecutedYet = new BooleanValueHolder(false);
                    IntegerValueHolder TotalChannelingTicksHolder = new IntegerValueHolder(0);

                    MethodAction action = (entity2) -> {
                        int updatedTick = ElapsedTick.getIntCounter();

                        boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                        if (isActionInterrupted) {
                            if (entity2 instanceof ServerPlayer sp1) {
                                DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                            }

                            incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                            SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_034_PREPARATION);
                        }
                        else {
                            int RemainingTicks = RemainingTicksBeforeInitiate.getIntCounter();
                            if (RemainingTicks > 0) {
                                RemainingTicksBeforeInitiate.setIntCounter(RemainingTicks - actionPeriod);
                            }
                            else {
                                if (!beforeInitiateActionExecutedYet.getBooleanVal()) {
                                    beforeInitiateActionExecutedYet.setBooleanVal(true);
                                    updatedTick = updatedTick - finalStationaryTime;
                                }

                                int channelingTotalTicks = TotalChannelingTicksHolder.getIntCounter();

                                if (!ServerSkillMethods.isLivingEntityInChannelingAction(entity2)) {
                                    SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_034_PREPARATION);
//                                    if (entity2 instanceof ServerPlayer sp1) {
//                                        NetworkingFetchMsgMethods.FetchPlayerResetLastKeyActionFromServer(sp1, BLADEMASTER_034_PREPARATION);
//                                    }


                                    Blademaster034_executeSenseSlashAction(entity2, channelingTotalTicks);
                                }
                                else if (updatedTick <= channelingTotalTicks + channelingTickDelay) {
                                    TotalChannelingTicksHolder.setIntCounter(channelingTotalTicks + actionPeriod);

                                    //channeling entity and sound
                                    if (channelingTotalTicks == 5) {
                                        DfoSwdSoundMethods.PlayBlademaster027DrawSwordStartSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 2f);
                                    }
                                    if (!channelingSpawnEffect.getBooleanVal()) {
                                        if (entity2 instanceof ServerPlayer sp1) {
                                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER_034_DRAW_SWORD_QI_CHANNELING_ANIMATION_NAME);
                                        }
                                        channelingSpawnEffect.setBooleanVal(true);
//                                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity2, 255, 255, 255, 2f, 0, 1.4f, 0, 900, BLADEMASTER_034_CHARGING_EFF_1_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
//                                                DfoSwordmanSkillTreeConstants.NO_ANIMATION,4, 0, 16, true, false, false, DfoSwordmanSkillMethods.RemoveChannelingEffEntity(entity2), 5, true);
                                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity2, 255, 255, 255, 2f, 0, 1.4f, 0, 900, BLADEMASTER_034_CHARGING_EFF_2_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                                DfoSwordmanSkillTreeConstants.NO_ANIMATION,0, 0, 9, false, false, false, DfoSwordmanSkillMethods.RemoveChannelingEffEntity(entity2), 5, true);
                                    }

                                    if (DfoSkillTreeItemMethods.getWeaponType(entity2) == DfoSkillTreeItemMethods.IS_AVERAGE_SHORT_SWORD) {
                                        Vector3f triggeredEntityPos = getEntityPos(entity2);
                                        Vector3f targetCenter = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, 2f, getEntityHorizontalFacingDeg(entity2));
                                        Vector3f targetEntityLoc;
                                        float angleBtn;
                                        float newX, newZ;

                                        for (Entity tempEntity: HashSetGroupEnumEntitiesInRangeFilter(entity2, targetCenter, aoeRange, 2, aoeRange, condition)) {
                                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
//                                                printInGameMsg("pulling: "+tempLivingEntity.getName().getString());
                                                targetEntityLoc = getEntityPos(tempLivingEntity);
                                                angleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(targetEntityLoc, targetCenter);
                                                newX = Vector3fMethods.PolarProjectionFromXHorizon(0.3f, angleBtn);
                                                newZ = Vector3fMethods.PolarProjectionFromZHorizon(0.3f, angleBtn);
                                                pushEntityWithDeltaValueXYZ(tempLivingEntity, newX, 0, newZ, true);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        ElapsedTick.setIntCounter(updatedTick + actionPeriod);
                    };
                    SBSTickEventMethods.setMethodActionTimer(entity1, BLADEMASTER_034_PREPARATION, actionPeriod, action);
                };

        tempSkillData.setChannelingSkillAction1(tempChannelingAction);

        tempSkillData.setActiveSkillAction1(DfoSwordmanSkillMethods::DoNothing);
    }

    public static void Blademaster034_executeSenseSlashAction(LivingEntity entity2, int totalChannelingTicks) {
        float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity2);
        float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity2);
        float entityAnimationSpeedMultiplier = entityAnimationArr[0];
        float damageTickSpeedMultiplier = entityAnimationArr[1];

        Predicate<Entity> condition;

        int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity2);
        //Apply Shock
        boolean isWieldingLightsabre = weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE;
        boolean isWieldingKatana = weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA; //inflict bleeding then explode
        boolean isWieldingBludgeon = weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON;
        boolean isWieldingZanbato = weaponType == DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO;
        float ZanbatoMaxChargeableSkillDamageMultiplier = 0f;
        int playerSkillLVL = 0;

        SkillDataJson skill1 = getID2SkillData().get(SkillID);

        boolean shouldTriggerSwordQi;
        float ZenithOfSwordArtSwordQiDamageMultiplier;
        float ZenithOfSwordArtHighestTargetHPExtraDamageMultiplier;
        if (entity2 instanceof ServerPlayer sp1) {
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                if (playerSkillLVL <= 0) { return; }
                playerSkillLVL = playerSkillLVL - 1;
            }
//            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_034_DRAW_SWORD_QI_SLASH_ANIMATION_NAME, entityAnimationSpeedMultiplier);
            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_034_DRAW_SWORD_QI_SLASH_ANIMATION_NAME, entityAnimationSpeedMultiplier * 4f);
            condition = defaultSkillTargetCondition(sp1);

            int ZenithOfSwordArtLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART);
            shouldTriggerSwordQi = ZenithOfSwordArtLVL >= Blademaster036.BLADEMASTER036_ZENITH_OF_SWORD_ART_BONUS_EFFECT_4_LVL;
            if (shouldTriggerSwordQi) {
                ZenithOfSwordArtSwordQiDamageMultiplier = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART).getDamageList_01().get(ZenithOfSwordArtLVL - 1).floatValue();
                ZenithOfSwordArtHighestTargetHPExtraDamageMultiplier = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART).getDamageMultiplierList_03().get(ZenithOfSwordArtLVL - 1).floatValue();
            }
            else {
                ZenithOfSwordArtHighestTargetHPExtraDamageMultiplier = 0;
                ZenithOfSwordArtSwordQiDamageMultiplier = 0;
            }

            if (isWieldingZanbato) {
                int ZanbatoMasteryLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);
                if (ZanbatoMasteryLevel >= Blademaster010.BLADEMASTER010_ZANBATO_MASTERY_BONUS_EFFECT_4_LVL) {
                    SkillDataJson ZanbatoMasterySkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);
                    ZanbatoMaxChargeableSkillDamageMultiplier = ZanbatoMasterySkillData.getDamageMultiplierList_03().get(ZanbatoMasteryLevel - 1).floatValue();
                }
            }
        }
        else {
            shouldTriggerSwordQi = false;
            ZenithOfSwordArtHighestTargetHPExtraDamageMultiplier = 0;
            ZenithOfSwordArtSwordQiDamageMultiplier = 0;
            condition = defaultSkillTargetCondition();
        }

        int StunDurationTicks = (int) (skill1.getDuration().get(playerSkillLVL).floatValue() * 20);

        float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
        float damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
        float damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();

        float LastSlashConstantDamage = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
        float LastSlashDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue();

        int MaxChannelingTime = (int) (skill1.getValueUsage_01().get(playerSkillLVL).floatValue() * 20);
        float MaxDamageMultiplierBonus = skill1.getDamageMultiplierList_03().get(playerSkillLVL).floatValue();
        int InitialSlashCount = skill1.getValueUsage_02().get(playerSkillLVL).intValue() * ( (isWieldingLightsabre || isWieldingKatana) ? 2 : 1 );
        float MaxSlashCountMultiplierBonus = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue() + ZanbatoMaxChargeableSkillDamageMultiplier;

        int finalizeChannelingTicks = Math.min(MaxChannelingTime, totalChannelingTicks);
        float channelingCurrentPercentage = 1f * finalizeChannelingTicks / MaxChannelingTime;
        float ExtraDamageMultiplier = 1f + MaxDamageMultiplierBonus * channelingCurrentPercentage + ((isWieldingZanbato) ? BLADEMASTER_034_ZANBATO_DAMAGE_MULTIPLIER : 0);

        int finalTotalSlashCount = (int) (InitialSlashCount * (1f + MaxSlashCountMultiplierBonus * channelingCurrentPercentage));

        int actionPeriod = 2;

        int startDmgTicks = (int) (5 * damageTickSpeedMultiplier);

        double dealerAttackDamage = getEntityAttackDamage(entity2);
        float finalDmg = (float) ((damageMultiplier * dealerAttackDamage + damageBase) * skillDamageMultiplier) * ExtraDamageMultiplier;
        float finalLastSlashDmg = (float) ((LastSlashDamageMultiplier * dealerAttackDamage + LastSlashConstantDamage) * skillDamageMultiplier) * ExtraDamageMultiplier;

        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 1f, 1f);
        DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 2f);

        float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity2);
//        GeoModelSpawnMethods.Generate2DAnimatedTexCustomFlatEntity256(entity1, triggeredEntityHorizontalFacingAngle, 255, 255, 255, aoeRange * 0.4f - 0.1f, 0, DfoSwordmanSkillTreeConstants.MC_DEFAULT_EYE_HEIGHT, 0, (int) (7 * damageTickSpeedMultiplier) + 1, false, 0, 0, 0, 0, 0, 0,
//                BLADEMASTER_034_DRAW_SWORD_SLASH_BLUE_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
//                DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, entityAnimationSpeedMultiplier, false, (int) (4 * damageTickSpeedMultiplier), (int) (4 * damageTickSpeedMultiplier), 7, false, false, true, null, 777, true);

        GeoModelSpawnMethods.GenerateDrawSwordQiEntity(entity2, triggeredEntityHorizontalFacingAngle, 0f, aoeRange * 0.4f - 0.1f, aoeRange, 0, 0, DfoSwordmanSkillTreeConstants.MC_DEFAULT_EYE_HEIGHT, 0, (int) (7 * damageTickSpeedMultiplier) + 1,
                BLADEMASTER_034_DRAW_SWORD_SLASH_BLUE_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                entityAnimationSpeedMultiplier, (int) (4 * damageTickSpeedMultiplier), (int) (4 * damageTickSpeedMultiplier), 7, false, false, true, null, 777, true);

        MethodAction action = Blademaster034_SlashBeforeDamageAction(weaponType, isWieldingLightsabre, isWieldingKatana, isWieldingBludgeon, StunDurationTicks, condition, startDmgTicks, aoeRange, finalDmg, finalLastSlashDmg, actionPeriod, finalTotalSlashCount, shouldTriggerSwordQi, ZenithOfSwordArtSwordQiDamageMultiplier, ZenithOfSwordArtHighestTargetHPExtraDamageMultiplier);

        //start loop actioning with fixed period ticks
        SBSTickEventMethods.setMethodActionTimer(entity2, SkillID, actionPeriod, action);
    }

    public static MethodAction Blademaster034_SlashBeforeDamageAction(int weaponType, boolean isWieldingLightsabre, boolean isWieldingKatana, boolean isWieldingBludgeon, int StunDurationTicks, Predicate<Entity> condition, int startDmgTicks, float aoeRange, float finalDmg, float finalLastSlashDmg, int actionPeriod, int totalSlashCount, boolean shouldTriggerSwordQi, float SwordQiDamageMultiplier, float ExtraDamageMultiplierOnTargetWithHighestHP) {
        IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
        BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
        BooleanValueHolder ExecuteSlashActionYet = new BooleanValueHolder(false);

        MethodAction action =
                (entity3) -> {
                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity3);

                    int updatedTick = ElapsedTicks.getIntCounter();

                    if (!isActionInterrupted && updatedTick >= startDmgTicks && !ExecuteSlashActionYet.getBooleanVal()) {
                        ExecuteSlashActionYet.setBooleanVal(true);
                        Vector3f triggeredEntityPos = getEntityPos(entity3);
                        DfoSwdSoundMethods.PlayBlademaster027DrawSwordSlashSound(getEntityLevel(entity3), triggeredEntityPos.x(), triggeredEntityPos.y(), triggeredEntityPos.z(), 0.8f, 2f);
                        Vector3f targetCenter = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, 2f, getEntityHorizontalFacingDeg(entity3));

                        HashSet<Entity> deadEntityToBeRemovedFromDamgedGroupSet = new HashSet<>();
                        HashSet<Entity> damagedGroup = HashSetGroupEnumEntitiesInRangeFilter(entity3, targetCenter, aoeRange, 2, aoeRange, condition);

                        String slashActionID = BLADEMASTER_034_SLASH_ACTION + getEntityTickCount(entity3);
                        MethodAction slashAction = Blademaster034_SlashStartDamageAction(weaponType, isWieldingLightsabre, isWieldingKatana, isWieldingBludgeon,
                                StunDurationTicks, finalDmg, finalLastSlashDmg, new IntegerValueHolder(totalSlashCount), deadEntityToBeRemovedFromDamgedGroupSet, damagedGroup, triggeredEntityPos, shouldTriggerSwordQi, SwordQiDamageMultiplier, new TypeValueHolder<>(null), ExtraDamageMultiplierOnTargetWithHighestHP, slashActionID);

                        SBSTickEventMethods.setMethodActionTimer(entity3, slashActionID, BLADEMASTER_034_DAMAGE_INTERVAL, slashAction);
                    } else {
                        if (!SendActionOffPacket.getBooleanVal()) {
                            if (isActionInterrupted){
                                if (entity3 instanceof ServerPlayer sp1) {
                                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                                }
                                SendActionOffPacket.setBooleanVal(true);
                                incrementEntityStateByAmountX(entity3, Constants.IS_IN_ACTION, -1);
                                SBSTickEventMethods.setSkillActionDone(entity3, SkillID);
                            }
                            else{
                                if (updatedTick > startDmgTicks) {
                                    if (entity3 instanceof ServerPlayer sp1) {
                                        DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
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

    public static MethodAction Blademaster034_SlashStartDamageAction(int weaponType, boolean isWieldingLightsabre, boolean isWieldingKatana, boolean isWieldingBludgeon, int StunDurationTicks, float finalDmg, float finalLastSlashDmg, IntegerValueHolder RemainingSlashTimesHolder, HashSet<Entity> entityTBR, HashSet<Entity> damagedGroup, Vector3f originTriggeredEntityPos, boolean shouldTriggerSwordQi, float SwordQiDamageMultiplier, TypeValueHolder<LivingEntity> highestHPTarget, float ExtraDamageMultiplierOnTargetWithHighestHP, String ActionID) {
        return (entity4) -> {
            int RemainingSlashCount = RemainingSlashTimesHolder.getIntCounter();

            if (RemainingSlashCount > 0) {
//                boolean isLastSlash = (RemainingSlashCount == 1);
                if (RemainingSlashCount == 1) {
                    for (Entity tempEntity : damagedGroup) {
                        if (tempEntity instanceof LivingEntity tempLivingEntity && SBConditions.isEntityAlive(tempLivingEntity)) {
                            if (tempLivingEntity.equals(highestHPTarget.getAnyTypeValue())) {
                                float highestHPTargetFinalDamage = finalLastSlashDmg * (1f + ExtraDamageMultiplierOnTargetWithHighestHP);
                                GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 4f, 4, 4, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, GeoAnimationAndTextureName.EFF_HIT_EFF_ADVANCE_ARR[ExtraMathMethods.randomInt(3)], true, true);

                                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity4, tempLivingEntity, highestHPTargetFinalDamage, false);

                                if (isWieldingLightsabre) {
                                    DfoStatusGeneralMethods.ApplyShockStatusToTargetLivingEntity(entity4, tempLivingEntity, DfoStatusGeneralMethods.GENERAL_DURATION_TICKS_FOR_SHOCK_STATUS, highestHPTargetFinalDamage, DfoStatusGeneralMethods.GENERAL_MAX_INFLICT_TIMES_FOR_SHOCK_STATUS, true, SkillID);
                                } else if (isWieldingKatana) {
                                    DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity4, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, highestHPTargetFinalDamage);
                                } else if (isWieldingBludgeon) {
                                    DfoStatusGeneralMethods.ApplyStunStatusToTargetLivingEntity(entity4, tempLivingEntity, StunDurationTicks, 0.1f);
                                }
                            }
                            else {
                                GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 2f, 4, 4, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, GeoAnimationAndTextureName.EFF_HIT_EFF_ADVANCE_ARR[ExtraMathMethods.randomInt(3)], true, true);

                                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity4, tempLivingEntity, finalLastSlashDmg, false);

                                if (isWieldingLightsabre) {
                                    DfoStatusGeneralMethods.ApplyShockStatusToTargetLivingEntity(entity4, tempLivingEntity, DfoStatusGeneralMethods.GENERAL_DURATION_TICKS_FOR_SHOCK_STATUS, finalLastSlashDmg, DfoStatusGeneralMethods.GENERAL_MAX_INFLICT_TIMES_FOR_SHOCK_STATUS, true, SkillID);
                                } else if (isWieldingKatana) {
                                    DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity4, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalLastSlashDmg);
                                } else if (isWieldingBludgeon) {
                                    DfoStatusGeneralMethods.ApplyStunStatusToTargetLivingEntity(entity4, tempLivingEntity, StunDurationTicks, 0.1f);
                                }
                            }

                            DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, EntityMethods.getEntityLevel(entity4), EntityMethods.getEntityX(tempLivingEntity), EntityMethods.getEntityY(tempLivingEntity), EntityMethods.getEntityZ(tempLivingEntity), 0.8f, 1.0f);

                            float correctDamagedEntityAngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(originTriggeredEntityPos, getEntityPos(tempLivingEntity));
                            float pushStrength = EntityMethods.calculatePushEntityStrength(0.05f, entity4, tempLivingEntity);
                            EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, correctDamagedEntityAngleBtn, pushStrength, pushStrength * 0.5f, true);
                        } else {
                            entityTBR.add(tempEntity);
                        }
                    }

                    //Zenith of Sword Art Bonus
                    if (shouldTriggerSwordQi) {
                        String ZenithOfSwordArtSwordQiActionID = BLADEMASTER_034_ZENITH_OF_SWORD_ART_SWORD_QI_ACTION + getEntityTickCount(entity4);
                        float SwordQiDamage = finalLastSlashDmg * SwordQiDamageMultiplier;
                        MethodAction slashAction = Blademaster034_ZenithOfSwordArtBonus_SwordQiDamageAction(SwordQiDamage, new IntegerValueHolder(5), entityTBR, damagedGroup, ZenithOfSwordArtSwordQiActionID);

                        SBSTickEventMethods.setMethodActionTimer(entity4, ZenithOfSwordArtSwordQiActionID, 30, slashAction);
                    }
                }
                else {
                    float maxHP = Float.MIN_VALUE;
                    float tempEntityHP;
                    LivingEntity targetWithHighestHP = null;
                    for (Entity tempEntity : damagedGroup) {
                        if (tempEntity instanceof LivingEntity tempLivingEntity && SBConditions.isEntityAlive(tempLivingEntity)) {
                            GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 1f, 4, 4, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, GeoAnimationAndTextureName.EFF_HIT_EFF_ADVANCE_ARR[ExtraMathMethods.randomInt(3)], true, true);

                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity4, tempLivingEntity, finalDmg, false);

                            DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, EntityMethods.getEntityLevel(entity4), EntityMethods.getEntityX(tempLivingEntity), EntityMethods.getEntityY(tempLivingEntity), EntityMethods.getEntityZ(tempLivingEntity), 0.8f, 1.0f);

                            if ( (tempEntityHP = getLivingEntityCurrentHP(tempLivingEntity)) > maxHP) {
                                maxHP = tempEntityHP;
                                targetWithHighestHP = tempLivingEntity;
                            }

                            float correctDamagedEntityAngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(originTriggeredEntityPos, getEntityPos(tempLivingEntity));
                            float pushStrength = EntityMethods.calculatePushEntityStrength(0.05f, entity4, tempLivingEntity);
                            EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, correctDamagedEntityAngleBtn, pushStrength, pushStrength * 0.5f, true);
                        } else {
                            entityTBR.add(tempEntity);
                        }
                    }
                    highestHPTarget.setAnyTypeValue(targetWithHighestHP);
                }

                if (!entityTBR.isEmpty()) {
                    for (Entity entityToBeRemoved : entityTBR) {
                        damagedGroup.remove(entityToBeRemoved);
                    }
                    entityTBR.clear();
                }
            }
            else {
                SBSTickEventMethods.setSkillActionDone(entity4, ActionID);
            }
            RemainingSlashTimesHolder.setIntCounter(RemainingSlashCount - 1);
        };
    }

    public static MethodAction Blademaster034_ZenithOfSwordArtBonus_SwordQiDamageAction(float finalLastSlashDmg, IntegerValueHolder RemainingSwordQiDamageTimesHolder, HashSet<Entity> entityTBR, HashSet<Entity> damagedGroup, String ActionID) {
        return (entity5) -> {
            int RemainingSlashCount = RemainingSwordQiDamageTimesHolder.getIntCounter();

            if (RemainingSlashCount > 0) {
                for (Entity tempEntity : damagedGroup) {
                    if (tempEntity instanceof LivingEntity tempLivingEntity && SBConditions.isEntityAlive(tempLivingEntity)) {
                        GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 0.6f, 4, 4, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, GeoAnimationAndTextureName.EFF_HIT_EFF_ADVANCE_ARR[ExtraMathMethods.randomInt(3)], true, true);

                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity5, tempLivingEntity, finalLastSlashDmg, false);

                        DfoSwdSoundMethods.PlayKatanaStabHitSound(getEntityLevel(entity5), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity), getEntityZ(tempLivingEntity), 0.5f, 0.5f);
                    } else {
                        entityTBR.add(tempEntity);
                    }
                }

                if (!entityTBR.isEmpty()) {
                    for (Entity entityToBeRemoved : entityTBR) {
                        damagedGroup.remove(entityToBeRemoved);
                    }
                    entityTBR.clear();
                }
            }
            else {
                SBSTickEventMethods.setSkillActionDone(entity5, ActionID);
            }
            RemainingSwordQiDamageTimesHolder.setIntCounter(RemainingSlashCount - 1);
        };
    }
}
