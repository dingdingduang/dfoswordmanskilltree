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
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues;
import net.dingdingduang.somebasicskills.networking.NetworkingFetchMsgMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.dingdingduang.somebasicskills.util.MethodConfigHelper;
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
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getCPlayerConfig2Settings;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.*;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster027 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_DRAW_LARGE_SWORD_QI;

    public static final String BLADEMASTER_027_PREPARATION = BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_DRAW_LARGE_SWORD_QI + "_PREPARATION";
    public static final String BLADEMASTER_027_NORMAL_SWORD_QI_EXTENDED_ACTION = "BLADEMASTER_027_CLIENT_NORMAL_SWORD_QI_EXTENDED_ACTION";

    public static final String BLADEMASTER_027_DRAW_SWORD_QI_START_ANIMATION_NAME = "drawsword_start2";
    public static final String BLADEMASTER_027_DRAW_SWORD_QI_CHANNELING_ANIMATION_NAME = "drawsword_channeling2";
    public static final String BLADEMASTER_027_DRAW_SWORD_QI_SLASH_ANIMATION_NAME = "drawsword_slash2";

    public static final String BLADEMASTER_027_DRAW_SWORD_SLASH_BLUE_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_large_sword_qi/draw_sword_eff/";
    public static final String BLADEMASTER_027_DRAW_LARGE_SWORD_QI_BLUE_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_large_sword_qi/large_sword_qi/";
    public static final String BLADEMASTER_027_CHARGING_EFF_1_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_large_sword_qi/charging_eff1/";
    public static final String BLADEMASTER_027_CHARGING_EFF_2_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_large_sword_qi/charging_eff2/";
//    public static final String BLADEMASTER_027_CHARGING_EFF_3_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_large_sword_qi/charging_eff3/";

    //Client
//    public static final String BLADEMASTER_027_CLIENT_CHARGING_CHANGING_COLOR_WHEN_MAX_OUT = "BLADEMASTER_027_CLIENT_CHARGING_CHANGING_COLOR_WHEN_MAX_OUT";
//    public static final String BLADEMASTER_027_CLIENT_INCREASING_SWORD_QI_SCALE_METHOD = "BLADEMASTER_027_CLIENT_INCREASING_SWORD_QI_SCALE_METHOD";
//    public static final String BLADEMASTER_027_CLIENT_INCREASING_SWORD_QI_SCALE_SHORT_SWORD_QI_ACTION = "BLADEMASTER_027_CLIENT_INCREASING_SWORD_QI_SCALE_SHORT_SWORD_QI_ACTION";

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
        Double ExtendedSwordQiDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double ExtendedSwordQiConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);
        Float ExtendedSwordQiFinalDmg = (float) ( (ExtendedSwordQiDamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ExtendedSwordQiConstantDamage) * skillDamageMultiplier);
        Double MaxChannelingTime = skillData.getValueUsage_01().get(correctSkillLevel);
        Double MaxDamageMultiplierBonus = skillData.getValueUsage_02().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%s", AOE),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (ExtendedSwordQiDamageMultiplier.floatValue() * 100)),
                String.format("%s", ExtendedSwordQiConstantDamage),
                String.format("%s", ExtendedSwordQiFinalDmg),
                String.format("%ss", MaxChannelingTime),
                String.format("%d%%", (int) (MaxDamageMultiplierBonus.floatValue() * 100))
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster027::getSkillDescription);

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

                    if (entity1 instanceof ServerPlayer sp1) {
                        float stationarySec = getID2SkillData().get(SkillID).getStationaryTime().get(getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID) - 1).floatValue();
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_027_DRAW_SWORD_QI_START_ANIMATION_NAME, 1f / stationarySec);
                        DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardKidnapFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                        stationaryTime = (int) (stationarySec * 20);
                    }

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
                            SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_027_PREPARATION);
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
                                    SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_027_PREPARATION);
//                                    if (entity2 instanceof ServerPlayer sp1) {
//                                        NetworkingFetchMsgMethods.FetchPlayerResetLastKeyActionFromServer(sp1, BLADEMASTER_027_PREPARATION);
//                                    }


                                    Blademaster027_executeDrawSwordQiAction(entity2, channelingTotalTicks);
                                }
                                else if (updatedTick <= channelingTotalTicks + channelingTickDelay) {
                                    TotalChannelingTicksHolder.setIntCounter(channelingTotalTicks + actionPeriod);

                                    //channeling entity and sound
                                    if (channelingTotalTicks == 5) {
                                        DfoSwdSoundMethods.PlayBlademaster027DrawSwordStartSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1f);
                                    }
                                    if (!channelingSpawnEffect.getBooleanVal()) {
                                        if (entity2 instanceof ServerPlayer sp1) {
                                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER_027_DRAW_SWORD_QI_CHANNELING_ANIMATION_NAME);
                                        }
                                        channelingSpawnEffect.setBooleanVal(true);
                                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity2, 255, 255, 255, 2f, 0, 1.4f, 0, 900, BLADEMASTER_027_CHARGING_EFF_1_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                                DfoSwordmanSkillTreeConstants.NO_ANIMATION,4, 0, 16, true, false, false, DfoSwordmanSkillMethods.RemoveChannelingEffEntity(entity2), 5, true);
                                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity2, 255, 255, 255, 2f, 0, 1.4f, 0, 900, BLADEMASTER_027_CHARGING_EFF_2_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                                DfoSwordmanSkillTreeConstants.NO_ANIMATION,0, 0, 9, false, false, false, DfoSwordmanSkillMethods.RemoveChannelingEffEntity(entity2), 5, true);
//                                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity2, 255, 255, 255, 1f, 0, 1.4f, 0, 900, BLADEMASTER_027_CHARGING_EFF_3_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
//                                                DfoSwordmanSkillTreeConstants.NO_ANIMATION,0, 0, 10, false, false, false, DfoSwordmanSkillMethods.RemoveChannelingEffEntity(entity2), 5, true);
                                    }
                                }
                            }
                        }

                        ElapsedTick.setIntCounter(updatedTick + actionPeriod);
                    };
                    SBSTickEventMethods.setMethodActionTimer(entity1, BLADEMASTER_027_PREPARATION, actionPeriod, action);
                };

        tempSkillData.setChannelingSkillAction1(tempChannelingAction);

        tempSkillData.setActiveSkillAction1(DfoSwordmanSkillMethods::DoNothing);
    }

    public static void Blademaster027_executeDrawSwordQiAction(LivingEntity entity1, int totalChannelingTicks) {
        float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

        int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);
        //Apply Shock
        boolean isWieldingLightsabre = weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE;
        boolean isWieldingKatana = weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA; //TODO: inflict bleeding then explode
        boolean isWieldingShortSword = weaponType == DfoSkillTreeItemMethods.IS_AVERAGE_SHORT_SWORD;
        boolean isWieldingZanbato = weaponType == DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO;
        int playerSkillLVL = 0;

        boolean shouldTriggerLigthSabreShock = false;
        boolean shouldTriggerKatanaBleeding = false;
        boolean shouldTriggerShortSwordExtendSwordQi = false;

        SkillDataJson skill1 = getID2SkillData().get(SkillID);

        boolean shootVertically;
        float EntityVerticalRotation;

        float ZanbatoMaxChargeableSkillDamageMultiplier = 0f;
        if (entity1 instanceof ServerPlayer sp1) {
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                if (playerSkillLVL <= 0) { return; }
                playerSkillLVL = playerSkillLVL - 1;

                //is wielding lightsabre
                if (isWieldingLightsabre && ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY) > 9) {
                    shouldTriggerLigthSabreShock = true;
                }
                //is wielding katana
                else if (isWieldingKatana && ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY) > 9) {
                    shouldTriggerKatanaBleeding = true;
                }
                else if (isWieldingShortSword && ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_SHORT_SWORD_MASTERY) > 9) {
                    shouldTriggerShortSwordExtendSwordQi = true;
                }
                else if (isWieldingZanbato) {
                    int ZanbatoMasteryLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);
                    if (ZanbatoMasteryLevel >= 9) {
                        SkillDataJson ZanbatoMasterySkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);
                        ZanbatoMaxChargeableSkillDamageMultiplier = ZanbatoMasterySkillData.getDamageMultiplierList_03().get(ZanbatoMasteryLevel - 1).floatValue();
                    }
                }
            }
            shootVertically = getSPlayerShootsVertically(sp1);
            if (shootVertically) {
                EntityVerticalRotation = getEntityVerticalFacingDeg(entity1);
            }
            else {
                EntityVerticalRotation = 0f;
            }
        }
        else {
            shootVertically = false;
            EntityVerticalRotation = 0f;
        }

        float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
        float damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
        float damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();

        float ExtendedSwordQiConstantDamage = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
        float ExtendedSwordQiDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue();

        int MaxChannelingTime = (int) (skill1.getValueUsage_01().get(playerSkillLVL).floatValue() * 20);
        float MaxDamageMultiplierBonus = skill1.getValueUsage_02().get(playerSkillLVL).floatValue() + ZanbatoMaxChargeableSkillDamageMultiplier;

        int finalizeChannelingTicks = (shouldTriggerShortSwordExtendSwordQi) ? MaxChannelingTime : Math.min(MaxChannelingTime, totalChannelingTicks);
        float AoeScaleMultiplier = MaxDamageMultiplierBonus * (1f * finalizeChannelingTicks / MaxChannelingTime) + 0.5f;
        float AoeScaleMultiplierWithBase = 1f + AoeScaleMultiplier;


        Predicate<Entity> condition;
        float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
        float entityAnimationSpeedMultiplier = entityAnimationArr[0];
        float damageTickSpeedMultiplier = entityAnimationArr[1];

        //play animation
        if (entity1 instanceof ServerPlayer sp1) {
            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_027_DRAW_SWORD_QI_SLASH_ANIMATION_NAME, entityAnimationSpeedMultiplier);
            condition = defaultSkillTargetCondition(sp1);
        } else {
            condition = defaultSkillTargetCondition();
        }
        int actionPeriod = 2;

        int startDmgTicks = (int) (4 * damageTickSpeedMultiplier);
        int endDmgTicks = (int) (6 * damageTickSpeedMultiplier);

        double dealerAttackDamage = getEntityAttackDamage(entity1);
        float tempFinalDmg = (float) ((damageMultiplier * dealerAttackDamage + damageBase) * skillDamageMultiplier) * AoeScaleMultiplierWithBase;
        float finalDmg = (shouldTriggerShortSwordExtendSwordQi) ? tempFinalDmg * 0.75f : tempFinalDmg;
        float finalExtendedSwordQiDmg = (shouldTriggerShortSwordExtendSwordQi) ? finalDmg : (float) ((ExtendedSwordQiDamageMultiplier * dealerAttackDamage + ExtendedSwordQiConstantDamage) * skillDamageMultiplier) * AoeScaleMultiplierWithBase;

        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 1f, 1f);
        DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.8f, 2f);

        float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity1);
//        GeoModelSpawnMethods.Generate2DAnimatedTexCustomFlatEntity256(entity1, triggeredEntityHorizontalFacingAngle, 255, 255, 255, aoeRange * 0.4f - 0.1f, 0, DfoSwordmanSkillTreeConstants.MC_DEFAULT_EYE_HEIGHT, 0, (int) (7 * damageTickSpeedMultiplier) + 1, false, 0, 0, 0, 0, 0, 0,
//                BLADEMASTER_027_DRAW_SWORD_SLASH_BLUE_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
//                DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, entityAnimationSpeedMultiplier, false, (int) (4 * damageTickSpeedMultiplier), (int) (4 * damageTickSpeedMultiplier), 7, false, false, true, null, 777, true);

        GeoModelSpawnMethods.GenerateDrawSwordQiEntity(entity1, triggeredEntityHorizontalFacingAngle, EntityVerticalRotation, aoeRange * 0.4f - 0.1f, aoeRange, 0, 0, DfoSwordmanSkillTreeConstants.MC_DEFAULT_EYE_HEIGHT, 0, (int) (7 * damageTickSpeedMultiplier) + 1,
                BLADEMASTER_027_DRAW_SWORD_SLASH_BLUE_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                entityAnimationSpeedMultiplier, (int) (4 * damageTickSpeedMultiplier), (int) (4 * damageTickSpeedMultiplier), 7, false, false, true, null, 777, true);

        DrawSwordQiEntity SwordQiEffEntity = GeoModelSpawnMethods.GenerateDrawSwordQiEntity(entity1, triggeredEntityHorizontalFacingAngle, EntityVerticalRotation, aoeRange * 0.4f, aoeRange, AoeScaleMultiplier, 0, DfoSwordmanSkillTreeConstants.MC_DEFAULT_EYE_HEIGHT, 0, (int) (10 * AoeScaleMultiplierWithBase),
                BLADEMASTER_027_DRAW_LARGE_SWORD_QI_BLUE_TEXTURE_PATH, GeoAnimationAndTextureName.Rotate_2D_FLAT_ENTITY_360_CLOCKWISE_IN_5_TICKS,
                entityAnimationSpeedMultiplier, 8, 8, 8, true, true, false, null, 777, true);

        if (shootVertically) {
            Vector3f damageCenter = getEntityPos(SwordQiEffEntity);
            Predicate<Entity> isEntityInRotationRange = DfoSwordmanSkillMethods.getShootBaseOnVerticalRotationCond(triggeredEntityHorizontalFacingAngle, EntityVerticalRotation, damageCenter, 7.5f);
            condition = condition.and(isEntityInRotationRange);
        }

        MethodAction action = Blademaster027_normalSwordQiDamageAction(weaponType, shouldTriggerLigthSabreShock, shouldTriggerKatanaBleeding, SwordQiEffEntity, shootVertically, condition, startDmgTicks, endDmgTicks, aoeRange, AoeScaleMultiplierWithBase, finalDmg, finalExtendedSwordQiDmg, actionPeriod);

        //start loop actioning with fixed period ticks
        SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
    }

    public static void Blademaster027_shortSwordQiExtendDamageAction(LivingEntity dealer, DrawSwordQiEntity SwordQiEffEntity, boolean shootVertically, Predicate<Entity> condition, float aoeRange, float maxAoeScaleMultiplier, float finalDmg, String ActionID) {
        HashSet<LivingEntity> damagedGroup = new HashSet<>();
//        printInGameMsg("max maxAoeScaleMultiplier: "+maxAoeScaleMultiplier+", range: "+aoeRange);

        Predicate<Entity> isNotDealer = (entity) -> !entity.equals(dealer);
        Predicate<Entity> finalCondition = condition.and(isNotDealer);

        MethodAction action =
                (entity3) -> {
                    if (SwordQiEffEntity.getLifetime() > 0) {
                        float ExtendSwordQiAoe = aoeRange * maxAoeScaleMultiplier * (1f * (SwordQiEffEntity.getMaxLifetime() - SwordQiEffEntity.getLifetime()) / SwordQiEffEntity.getMaxLifetime());
//                        printInGameMsg("current extendedRange: "+ExtendSwordQiAoe);
                        Vector3f triggeredSwordQiEntityPos = getEntityPos(SwordQiEffEntity);
                        HashSet<Entity> differenceSetOfInnerOuterCircle = (shootVertically) ? HashSetGroupEnumEntitiesInRangeFilter(SwordQiEffEntity, triggeredSwordQiEntityPos, ExtendSwordQiAoe, ExtendSwordQiAoe, ExtendSwordQiAoe, finalCondition) : HashSetGroupEnumEntitiesInRangeFilter(SwordQiEffEntity, ExtendSwordQiAoe, finalCondition) ;
                        float InnerExtendSwordQiAoe = MathMethods.max(0f, ExtendSwordQiAoe - 3f);
                        differenceSetOfInnerOuterCircle.removeAll((shootVertically) ? HashSetGroupEnumEntitiesInRangeFilter(SwordQiEffEntity, triggeredSwordQiEntityPos, InnerExtendSwordQiAoe, InnerExtendSwordQiAoe, InnerExtendSwordQiAoe, finalCondition) : HashSetGroupEnumEntitiesInRangeFilter(SwordQiEffEntity, InnerExtendSwordQiAoe, finalCondition) );

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

    public static MethodAction Blademaster027_normalSwordQiDamageAction(int weaponType, boolean isWieldingLightsabre, boolean isWieldingKatana, DrawSwordQiEntity SwordQiEffEntity, boolean shootVertically, Predicate<Entity> condition, int startDmgTicks, int endDmgTicks, float aoeRange, float maxAoeScaleMultiplier, float finalDmg, float finalExtendedSwordQiDmg, int actionPeriod) {
        HashSet<LivingEntity> damagedGroup = new HashSet<>();
        IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
        BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);

        BooleanValueHolder PlaySlashSoundYet = new BooleanValueHolder(false);

        MethodAction action =
                (entity3) -> {
                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity3);

                    int updatedTick = ElapsedTicks.getIntCounter();

                    if (!isActionInterrupted && updatedTick >= startDmgTicks && updatedTick <= endDmgTicks) {
                        Vector3f triggeredEntityPos = getEntityPos(entity3);

                        if (!PlaySlashSoundYet.getBooleanVal()) {
                            PlaySlashSoundYet.setBooleanVal(true);
                            DfoSwdSoundMethods.PlayBlademaster027DrawSwordSlashSound(getEntityLevel(entity3), triggeredEntityPos.x(), triggeredEntityPos.y(), triggeredEntityPos.z(), 0.8f, 1f);
                        }

                        for (Entity tempEntity : (shootVertically) ? GroupEnumEntitiesInRangeFilter(entity3, aoeRange, aoeRange, aoeRange, condition) : GroupEnumEntitiesInRangeFilter(entity3, aoeRange, condition) ) {
                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                if (!damagedGroup.contains(tempLivingEntity)) {
                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity3, tempLivingEntity, finalDmg, false);

                                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, EntityMethods.getEntityLevel(entity3), EntityMethods.getEntityX(entity3), EntityMethods.getEntityY(entity3), EntityMethods.getEntityZ(entity3), 0.8f, 1.0f);
                                    damagedGroup.add(tempLivingEntity);

                                    if (isWieldingLightsabre) {
                                        DfoStatusGeneralMethods.ApplyShockStatusToTargetLivingEntity(entity3, tempLivingEntity, DfoStatusGeneralMethods.GENERAL_DURATION_TICKS_FOR_SHOCK_STATUS, finalDmg, DfoStatusGeneralMethods.GENERAL_MAX_INFLICT_TIMES_FOR_SHOCK_STATUS, true, SkillID);
                                    }
                                    else if (isWieldingKatana) {
                                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity3, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg);
                                    }

                                    float correctDamagedEntityAngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(triggeredEntityPos, getEntityPos(tempLivingEntity));
                                    float pushStrength = EntityMethods.calculatePushEntityStrength(0.35f, entity3, tempLivingEntity);
                                    EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, correctDamagedEntityAngleBtn, pushStrength, 0, true);
                                }
                            }
                        }
                    } else {
                        if (isActionInterrupted) {
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
                                SBSTickEventMethods.setSkillActionDone(entity3, SkillID);

                                Blademaster027_shortSwordQiExtendDamageAction(entity3, SwordQiEffEntity, shootVertically, condition, aoeRange, maxAoeScaleMultiplier, finalExtendedSwordQiDmg, BLADEMASTER_027_NORMAL_SWORD_QI_EXTENDED_ACTION + ExtraMathMethods.randomInt(100));
                            }
                        }
                    }


                    //update val
                    ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
                };

        return action;
    }



    //========== Configuration
    private static boolean getSPlayerShootsVertically(ServerPlayer sp1) {
        HashMap<ServerPlayer, HashMap<String, HashMap<String, MethodConfigHelper>>> AllSPConfigMap = getSPlayerConfig();
        HashMap<String, HashMap<String, MethodConfigHelper>> SPSkillConfigCollectionsMap;
        HashMap<String, MethodConfigHelper> SPSkillConfigSubMap;
        MethodConfigHelper CurrentConfig;
        if (AllSPConfigMap != null &&
                (SPSkillConfigCollectionsMap = AllSPConfigMap.get(sp1)) != null &&
                (SPSkillConfigSubMap = SPSkillConfigCollectionsMap.get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_CONFIG)) != null &&
                (CurrentConfig = SPSkillConfigSubMap.get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_SHOOT_BASES_ON_PLAYER_VERTICAL_FACING_ANGLE_CONFIG)) != null) {
            return CurrentConfig.getIntValue() != 0;
        }

        return true;
    }

    public static void ClientConfigInit() {
        HashMap<String, MethodConfigHelper> GeneralSetting = new HashMap<String, MethodConfigHelper>();
        GeneralSetting.put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_SHOOT_BASES_ON_PLAYER_VERTICAL_FACING_ANGLE_CONFIG, new MethodConfigHelper(GlobalClientPlayerValues::DefaultSettingOnOff, 0, true));
        getCPlayerConfig2Settings().put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_CONFIG, GeneralSetting);
    }

    public static void ServerConfigInit(HashMap<String, HashMap<String, MethodConfigHelper>> SPlayerConfigMap) {
        getSConfig().add(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_CONFIG);
        getSConfig().add(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_SHOOT_BASES_ON_PLAYER_VERTICAL_FACING_ANGLE_CONFIG);
        HashMap<String, MethodConfigHelper> GeneralSetting = new HashMap<String, MethodConfigHelper>();
        GeneralSetting.put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_SHOOT_BASES_ON_PLAYER_VERTICAL_FACING_ANGLE_CONFIG, new MethodConfigHelper(null, 0, true));
        SPlayerConfigMap.put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_CONFIG, GeneralSetting);
    }

    public static void ServerConfigAfterInit(ServerPlayer sp1, HashMap<String, HashMap<String, MethodConfigHelper>> SPlayerConfigMap) {
        //sync to client
        int val = SPlayerConfigMap.get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_CONFIG).get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_SHOOT_BASES_ON_PLAYER_VERTICAL_FACING_ANGLE_CONFIG).getIntValue();
        NetworkingFetchMsgMethods.FetchConfigKeyValToClientSide(sp1, DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_CONFIG, DfoSwordmanSkillTreeConstants.BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_SHOOT_BASES_ON_PLAYER_VERTICAL_FACING_ANGLE_CONFIG, val);
    }
}
