package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
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
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.HashSetGroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster038 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_ULTIMATE_SWORD_ART_FLASH_CUT;

    public static final String BLADEMASTER_038_PREPARATION = BlademasterSkillTreeInitialization.BLADEMASTER_ULTIMATE_SWORD_ART_FLASH_CUT + "_PREPARATION";
    public static final String BLADEMASTER_038_SLASH_ACTION = BlademasterSkillTreeInitialization.BLADEMASTER_ULTIMATE_SWORD_ART_FLASH_CUT + "_BLADEMASTER_038_SLASH_ACTION";
    public static final String BLADEMASTER_038_SWORD_QI_DAMAGE_ACTION = BlademasterSkillTreeInitialization.BLADEMASTER_ULTIMATE_SWORD_ART_FLASH_CUT + "BLADEMASTER_038_SWORD_QI_DAMAGE_ACTION";

    public static final String BLADEMASTER_038_FLASH_CUT_START_ANIMATION_NAME = "ultimate_sword_art_flash_cut_start";
    public static final String BLADEMASTER_038_FLASH_CUT_CHANNELING_ANIMATION_NAME = "ultimate_sword_art_flash_cut_stance";
    public static final String BLADEMASTER_038_FLASH_CUT_SLASH_ANIMATION_NAME = "ultimate_sword_art_flash_cut_slash0";

    public static final String BLADEMASTER_038_DRAW_SWORD_SLASH_BLUE_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_large_sword_qi/draw_sword_eff/";
    public static final String BLADEMASTER_038_CHARGING_EFF_2_TEXTURE_PATH = "textures/entity/skilleff/blademaster/draw_large_sword_qi/charging_eff2/";

    public static final String BLADEMASTER_038_HIT_EFF_0_TEXTURE_PATH = "textures/entity/skilleff/blademaster/flash_cut/hit_eff/";
//    public static final String BLADEMASTER_038_AOE_EFF_0_TEXTURE_PATH = "textures/entity/skilleff/blademaster/flash_cut/bar/";
    public static final String BLADEMASTER_038_AOE_EFF_0_TEXTURE_PATH = "textures/entity/skilleff/blademaster/flash_cut/bar2/";

    public static final int BLADEMASTER_038_DAMAGE_INTERVAL = 1;
    public static final int BLADEMASTER_038_SWORD_QI_DAMAGE_INTERVAL_TICK = 15;
//    public static final float BLADEMASTER_038_ZANBATO_DAMAGE_MULTIPLIER = 0.5f;

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
        Double SwordQiDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double SwordQiConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);
        Float SwordQiFinalDmg = (float) ( (SwordQiDamageMultiplier * getEntityAttackDamage(getClientPlayer()) + SwordQiConstantDamage) * skillDamageMultiplier);

        Double PreparationTime = skillData.getChannelingTime().get(correctSkillLevel);
        int TotalSlashCount = skillData.getValueUsage_01().get(correctSkillLevel).intValue();
        int SwordQiMaxDamageCount = skillData.getValueUsage_02().get(correctSkillLevel).intValue();

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%s", AOE),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (SwordQiDamageMultiplier.floatValue() * 100)),
                String.format("%s", SwordQiConstantDamage),
                String.format("%s", SwordQiFinalDmg),
                String.format("%ss", PreparationTime),
                String.format("%d", TotalSlashCount),
                String.format("%d", SwordQiMaxDamageCount)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster038::getSkillDescription);

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
                    int playerSkillLevel = 0;

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);
                    if (entity1 instanceof ServerPlayer sp1) {
                        playerSkillLevel = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                        if (playerSkillLevel <= 0) { return; }
                        playerSkillLevel = playerSkillLevel - 1;
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_038_FLASH_CUT_START_ANIMATION_NAME, 1f);
                        DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardKidnapFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                    }

                    int channelingTotalTimeTicks = (int) (skill1.getChannelingTime().get(playerSkillLevel).floatValue() * 20);

                    //IMPORTANT! setup action on to prevent casting other skills while this action
                    //being performed
                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    int actionPeriod = 5;
                    int channelingTickDelay = 5;
                    IntegerValueHolder ElapsedTick = new IntegerValueHolder(0);
                    IntegerValueHolder RemainingTicksBeforeInitiate = new IntegerValueHolder(channelingTotalTimeTicks);
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
                            SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_038_PREPARATION);
                        }
                        else {
                            int RemainingTicks = RemainingTicksBeforeInitiate.getIntCounter();
                            if (RemainingTicks > 0) {
                                RemainingTicksBeforeInitiate.setIntCounter(RemainingTicks - actionPeriod);
                            }
                            else {
                                if (!beforeInitiateActionExecutedYet.getBooleanVal()) {
                                    beforeInitiateActionExecutedYet.setBooleanVal(true);
                                    updatedTick = updatedTick - channelingTotalTimeTicks;
                                }

                                int channelingTotalTicks = TotalChannelingTicksHolder.getIntCounter();

                                if (!ServerSkillMethods.isLivingEntityInChannelingAction(entity2)) {
                                    SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER_038_PREPARATION);

                                    Blademaster038_executeFlashCutAction(entity2, channelingTotalTicks);
                                }
                                else if (updatedTick <= channelingTotalTicks + channelingTickDelay) {
                                    TotalChannelingTicksHolder.setIntCounter(channelingTotalTicks + actionPeriod);

                                    //channeling entity and sound
                                    if (channelingTotalTicks == 5) {
                                        DfoSwdSoundMethods.PlayBlademaster027DrawSwordStartSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 4f);
                                    }
                                    if (!channelingSpawnEffect.getBooleanVal()) {
                                        if (entity2 instanceof ServerPlayer sp1) {
                                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER_038_FLASH_CUT_CHANNELING_ANIMATION_NAME);
                                        }
                                        channelingSpawnEffect.setBooleanVal(true);

                                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity2, 255, 255, 255, 2f, 0, 1.4f, 0, 11, Blademaster033.BLADEMASTER033_TEXTURE_CHARGING_COMPLETE, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                                DfoSwordmanSkillTreeConstants.NO_ANIMATION,11, 0, 11, false, false, true, null, 777, true);
                                    }
                                }
                            }
                        }

                        ElapsedTick.setIntCounter(updatedTick + actionPeriod);
                    };
                    SBSTickEventMethods.setMethodActionTimer(entity1, BLADEMASTER_038_PREPARATION, actionPeriod, action);
                };

        tempSkillData.setChannelingSkillAction1(tempChannelingAction);

        tempSkillData.setActiveSkillAction1(DfoSwordmanSkillMethods::DoNothing);
    }

    public static void Blademaster038_executeFlashCutAction(LivingEntity entity2, int totalChannelingTicks) {
        float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity2);

        int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity2);
        int playerSkillLVL = 0;

        Predicate<Entity> condition;
        SkillDataJson skill1 = getID2SkillData().get(SkillID);

        float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity2);
        float entityAnimationSpeedMultiplier = entityAnimationArr[0];
        float damageTickSpeedMultiplier = entityAnimationArr[1];

        if (entity2 instanceof ServerPlayer sp1) {
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                if (playerSkillLVL <= 0) { return; }
                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER_038_FLASH_CUT_SLASH_ANIMATION_NAME, 0.5f * entityAnimationSpeedMultiplier);
                playerSkillLVL = playerSkillLVL - 1;
            }
            condition = defaultSkillTargetCondition(sp1);
        }
        else {
            condition = defaultSkillTargetCondition();
        }

        float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();

        float damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
        float damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();

        float SwordQiSlashConstantDamage = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
        float SwordQiSlashDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue();

        int TotalSlashCount = skill1.getValueUsage_01().get(playerSkillLVL).intValue();
        int PeriodicallySlashCount = skill1.getValueUsage_02().get(playerSkillLVL).intValue();;

        int actionPeriod = 2;

        int startDmgTicks = (int) (5 * damageTickSpeedMultiplier);

        double dealerAttackDamage = getEntityAttackDamage(entity2);
        float finalDmg = (float) ((damageMultiplier * dealerAttackDamage + damageBase) * skillDamageMultiplier);
        float finalSwordQiSlashDmg = (float) ((SwordQiSlashDamageMultiplier * dealerAttackDamage + SwordQiSlashConstantDamage) * skillDamageMultiplier);

        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 1f, 1f);
        DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 2f);

        MethodAction action = Blademaster038_SlashBeforeDamageAction(weaponType, condition, startDmgTicks, aoeRange, finalDmg, finalSwordQiSlashDmg, actionPeriod, TotalSlashCount, PeriodicallySlashCount);

        //start loop actioning with fixed period ticks
        SBSTickEventMethods.setMethodActionTimer(entity2, SkillID, actionPeriod, action);
    }

    public static MethodAction Blademaster038_SlashBeforeDamageAction(int weaponType, Predicate<Entity> condition, int startDmgTicks, float aoeRange, float finalDmg, float finalLastSlashDmg, int actionPeriod, int totalSlashCount, int periodicallySlashCount) {
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
                        float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity3);
                        DfoSwdSoundMethods.PlayBlademaster027DrawSwordSlashSound(getEntityLevel(entity3), triggeredEntityPos.x(), triggeredEntityPos.y(), triggeredEntityPos.z(), 1f, 4f);

                        //position
                        Vector3f tempPos, targetPos = null;
                        float correctDist = 0;
                        float distShouldReach = aoeRange * 2f;
                        for (float tempDist = 0; tempDist <= distShouldReach; tempDist = tempDist + 0.5f) {
                            tempPos = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, tempDist, triggeredEntityHorizontalFacingAngle);
//                            if (BlockMethods.isPosVecAirBlock(getEntityLevel(entity3), tempPos)) {
                            if (isBlockPosAirOnly(entity3, tempPos)) {
                                targetPos = tempPos;
                                correctDist = tempDist;
                            }
                            else {
                                break;
                            }
                        }
//                        boolean isTargetPosNull = false;
                        if (targetPos == null) {
//                            isTargetPosNull = true;
                            targetPos = triggeredEntityPos;
                        }
                        float midDist = Vector3fMethods.Vec3fDistanceTo(triggeredEntityPos, targetPos) * 0.5f;
                        Vector3f targetCenter = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, midDist, triggeredEntityHorizontalFacingAngle);

                        GeoModelSpawnMethods.GenerateCustomFadingCloneEntityXYZ(entity3, triggeredEntityHorizontalFacingAngle, 0, 1, 255, 255, 224, 128, targetCenter.x(), targetCenter.y(), targetCenter.z(), 0, 0, 0, 40,
                                GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, false, 4, 8, 8, true, true, BLADEMASTER_038_FLASH_CUT_SLASH_ANIMATION_NAME, 3f, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                DfoSwordmanSkillTreeConstants.NO_ANIMATION, true, true, true);

                        GeoModelSpawnMethods.GenerateDrawSwordQiEntityXYZ(entity3, triggeredEntityHorizontalFacingAngle, 0f, aoeRange * 0.4f - 0.1f, aoeRange, 0, targetCenter.x(), targetCenter.y() + DfoSwordmanSkillTreeConstants.MC_DEFAULT_EYE_HEIGHT, targetCenter.z(), 7,
                                BLADEMASTER_038_DRAW_SWORD_SLASH_BLUE_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                1, 0, 7, 7, false, false, true, null, 777, true);

                        float percent = correctDist / distShouldReach;
                        float scale = aoeRange * 0.4f * percent;

                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256(entity3, triggeredEntityHorizontalFacingAngle, scale, targetCenter.x(), targetCenter.y() + DfoSwordmanSkillTreeConstants.MC_DEFAULT_EYE_HEIGHT, targetCenter.z(), 18,
                                BLADEMASTER_038_AOE_EFF_0_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 0, 0, 9, true, false, false, true,  null, 777,true);

                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256(entity3, triggeredEntityHorizontalFacingAngle, 2f, targetCenter.x(), targetCenter.y() + 1.6f, targetCenter.z(), 3,
                                Blademaster025.BLADEMASTER025_SWORD_SLASH_TEXTURE, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 0, 3, 3, true, true, true, true, null, 777, true);


                        HashSet<Entity> deadEntityToBeRemovedFromDamgedGroupSet = new HashSet<>();
                        float fromAngle = MathMethods.RoundAngle(triggeredEntityHorizontalFacingAngle - 80f);
                        float toAngle = MathMethods.RoundAngle(triggeredEntityHorizontalFacingAngle + 80f);

//                        Predicate<Entity> startPosCond = DfoSwordmanSkillMethods.getSectorCondition(entity3, triggeredEntityPos, fromAngle, toAngle, distShouldReach);
//                        Predicate<Entity> targetPosCond = DfoSwordmanSkillMethods.getSectorCondition(entity3, targetPos, toAngle, fromAngle, distShouldReach);
                        Predicate<Entity> startPosCond = DfoSwordmanSkillMethods.getSectorCondition(entity3, triggeredEntityPos, toAngle, fromAngle, distShouldReach);
                        Predicate<Entity> targetPosCond = DfoSwordmanSkillMethods.getSectorCondition(entity3, targetPos, fromAngle, toAngle, distShouldReach);

                        HashSet<Entity> startPosGroup = HashSetGroupEnumEntitiesInRangeFilter(entity3, triggeredEntityPos, distShouldReach, 2.5f, distShouldReach, condition.and(startPosCond));
                        HashSet<Entity> targetPosGroup = HashSetGroupEnumEntitiesInRangeFilter(entity3, targetPos, distShouldReach, 2.5f, distShouldReach, condition.and(targetPosCond));

                        startPosGroup.retainAll(targetPosGroup);

//                        if (!isTargetPosNull) {
//                            int TotalMovingTicks = Math.min(1, (int) (correctDist / distShouldReach * 10));
//                            DfoGeneralEntitySpawnMethods.SetupClientMovementHelper(entity3, TotalMovingTicks, triggeredEntityPos, targetPos, DfoSwordmanSkillTreeConstants.NO_ANIMATION, null, 777, true);
//                        }
                        tpEntityToPos(entity3, targetPos);

                        String slashActionID = BLADEMASTER_038_SLASH_ACTION + getEntityTickCount(entity3);
                        MethodAction slashAction = Blademaster038_SlashStartDamageAction(weaponType, finalDmg, finalLastSlashDmg, new IntegerValueHolder(totalSlashCount), periodicallySlashCount, deadEntityToBeRemovedFromDamgedGroupSet, startPosGroup, triggeredEntityPos, slashActionID);

                        SBSTickEventMethods.setMethodActionTimer(entity3, slashActionID, BLADEMASTER_038_DAMAGE_INTERVAL, slashAction);
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

    public static MethodAction Blademaster038_SlashStartDamageAction(int weaponType, float finalDmg, float finalLastSlashDmg, IntegerValueHolder RemainingSlashTimesHolder, int periodicallySlashCount, HashSet<Entity> entityTBR, HashSet<Entity> damagedGroup, Vector3f originTriggeredEntityPos, String ActionID) {
        return (entity4) -> {
            int RemainingSlashCount = RemainingSlashTimesHolder.getIntCounter();

            if (RemainingSlashCount > 0) {
//                boolean isLastSlash = (RemainingSlashCount == 1);
                if (RemainingSlashCount == 1) {
                    for (Entity tempEntity : damagedGroup) {
                        if (tempEntity instanceof LivingEntity tempLivingEntity && SBConditions.isEntityAlive(tempLivingEntity)) {
                            GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 6f, 5, 5, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, BLADEMASTER_038_HIT_EFF_0_TEXTURE_PATH, true, true);

                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity4, tempLivingEntity, finalLastSlashDmg, false);

                            DfoSwdSoundMethods.PlayZanbatoHitSound(getEntityLevel(entity4), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity), getEntityZ(tempLivingEntity), 1f, 2f);

                            float correctDamagedEntityAngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(originTriggeredEntityPos, getEntityPos(tempLivingEntity));
                            float pushStrength = calculatePushEntityStrength(0.05f, entity4, tempLivingEntity);
                            pushEntityWithDeltaValue(tempLivingEntity, correctDamagedEntityAngleBtn, pushStrength, pushStrength * 0.5f, true);
                        } else {
                            entityTBR.add(tempEntity);
                        }
                    }

                    String slashActionID = BLADEMASTER_038_SWORD_QI_DAMAGE_ACTION + getEntityTickCount(entity4);
                    MethodAction slashAction = Blademaster038_SwordQiDamageAction(weaponType, finalLastSlashDmg, new IntegerValueHolder(periodicallySlashCount), entityTBR, damagedGroup, slashActionID);

                    SBSTickEventMethods.setMethodActionTimer(entity4, slashActionID, BLADEMASTER_038_SWORD_QI_DAMAGE_INTERVAL_TICK, slashAction);
                }
                else {
                    for (Entity tempEntity : damagedGroup) {
                        if (tempEntity instanceof LivingEntity tempLivingEntity && SBConditions.isEntityAlive(tempLivingEntity)) {
                            GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 4f, 5, 5, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, BLADEMASTER_038_HIT_EFF_0_TEXTURE_PATH, true, true);

                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity4, tempLivingEntity, finalDmg, false);

                            DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity4), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity), getEntityZ(tempLivingEntity), 0.8f, 1.0f);

                            float correctDamagedEntityAngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(originTriggeredEntityPos, getEntityPos(tempLivingEntity));
                            float pushStrength = calculatePushEntityStrength(0.05f, entity4, tempLivingEntity);
                            pushEntityWithDeltaValue(tempLivingEntity, correctDamagedEntityAngleBtn, pushStrength, pushStrength * 0.5f, true);
                        } else {
                            entityTBR.add(tempEntity);
                        }
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
                SBSTickEventMethods.setSkillActionDone(entity4, ActionID);
            }
            RemainingSlashTimesHolder.setIntCounter(RemainingSlashCount - 1);
        };
    }

    public static MethodAction Blademaster038_SwordQiDamageAction(int weaponType, float finalLastSlashDmg, IntegerValueHolder RemainingSwordQiDamageTimesHolder, HashSet<Entity> entityTBR, HashSet<Entity> damagedGroup, String ActionID) {
        return (entity5) -> {
            int RemainingSlashCount = RemainingSwordQiDamageTimesHolder.getIntCounter();

            if (RemainingSlashCount > 0) {
                for (Entity tempEntity : damagedGroup) {
                    if (tempEntity instanceof LivingEntity tempLivingEntity && SBConditions.isEntityAlive(tempLivingEntity)) {
                        GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 1.2f, 4, 4, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, GeoAnimationAndTextureName.EFF_HIT_EFF_ADVANCE_ARR[ExtraMathMethods.randomInt(3)], true, true);

                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity5, tempLivingEntity, finalLastSlashDmg, false);

                        DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity5), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity), getEntityZ(tempLivingEntity), 0.8f, 1.0f);
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
