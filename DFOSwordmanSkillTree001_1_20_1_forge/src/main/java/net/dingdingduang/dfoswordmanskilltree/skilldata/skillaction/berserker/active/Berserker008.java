package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.custom.SwordSlashCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.Blademaster033;
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
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

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
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.isPlayerBeingHit;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.getSkillsInCooldownClientTimerOverlayInstance;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker008 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_CROSS_SLASH;

    public static HashMap<LivingEntity, Boolean> BERSERKER08_SHOULD_TRIGGER_ADDITIONAL_SLASH = new HashMap<>();

    public static final float BERSERKER08_SLASH_FROM_LEFT_TO_RIGHT_Z_DEG = 270f;
    public static final float BERSERKER08_SLASH_UPWARD_Z_DEG = 180f;
    public static final float BERSERKER08_SLASH_ADDITIONAL_SLASH_DOWNWARD_Z_DEG = 45f;

    public static final String BERSERKER08_SLASH_FROM_LEFT_TO_RIGHT_ANIMATION = "drawsword_slash2";
    public static final String BERSERKER08_SLASH_UPWARD_ANIMATION = "sword_act_katana_slash4";
    public static final String BERSERKER08_ADDITIONAL_SLASH_DOWNWARD_ANIMATION = "holding_up_sword_air_end4";

    public static final String BERSERKER08_CLIENT_ACTION_TRIGGERED_ADDITIONAL_ATTACK = "BERSERKER08_CLIENT_ACTION_TRIGGERED_ADDITIONAL_ATTACK";

    public static final int BERSERKER08_EFF_ENTITY_LIFETIME = 60;

    //Client
    public static boolean triggeredFinalSlash = false;
    public static final int availableRespondingTicks = 14;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        double skillDamageMultiplier = getCPlayerSkillDamage();

        int otherSkillLevel = ClientSkillMethods.queryCPlayerCurrentSkillLevel(BerserkerSkillTreeInitialization.BERSERKER_POWER_OF_BLOOD);

        Double TotalHPConsumptionPercentage;
        Double PowerOfBloodMultiplier;

        if (otherSkillLevel > 0) {
            TotalHPConsumptionPercentage = getID2SkillData().get(BerserkerSkillTreeInitialization.BERSERKER_POWER_OF_BLOOD).getValueMultiplierList_01().get(otherSkillLevel - 1);
            PowerOfBloodMultiplier = getID2SkillData().get(BerserkerSkillTreeInitialization.BERSERKER_POWER_OF_BLOOD).getDamageMultiplierList_01().get(otherSkillLevel - 1);
        }
        else {
            TotalHPConsumptionPercentage = 0D;
            PowerOfBloodMultiplier = 0D;
        }

        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);

        Float finalDmg = (float) ( ( (DamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_2 * TotalHPConsumptionPercentage + PowerOfBloodMultiplier) * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        Double AdditionalDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double AdditionalConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);

        Float finalAdditionalDmg = (float) ( ( (AdditionalDamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_2 * TotalHPConsumptionPercentage + PowerOfBloodMultiplier) * getEntityAttackDamage(getClientPlayer()) + AdditionalConstantDamage) * skillDamageMultiplier);

        Double AORRaidus = skillData.getEffectArea().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (TotalHPConsumptionPercentage.floatValue() * 100)),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (AdditionalDamageMultiplier.floatValue() * 100)),
                String.format("%s", AdditionalConstantDamage),
                String.format("%s", finalAdditionalDmg),
                String.format("%s", AORRaidus)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker008::getSkillDescription);

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
                            if (!triggeredFinalSlash) {
                                float damageTickSpeedMultiplier = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(getClientPlayer())[1];
                                int correctRespondingTicks = (int) (availableRespondingTicks * damageTickSpeedMultiplier);

                                HashMap<String, Integer> clientCD = getSkillsInCooldownClientTimerOverlayInstance().getPlayerSkill2CooldownTicks();
                                if (clientCD.get(SkillID2) > correctRespondingTicks) {
                                    isPassed = false;
                                }
                            }
                            else {
                                isPassed = false;
                            }
                        }
                        else {
                            triggeredFinalSlash = false;
                        }
                    }
                    return isPassed;
                }
        );
//        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);



        if (isClientSide()) {
            return;
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
                    Predicate<Entity> condition;

                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);

                    float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                    float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                    float damageTickSpeedMultiplier = entityAnimationArr[1];

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    float ExtraDamageMultiplier = 0;
                    float ExtraAOEMultiplier = 1f;
                    float PowerOfBloodTriggerBleedingChance = 0;
                    float PowerOfBloodBleedingDamageMultiplier = 0.01f;
                    if (entity1 instanceof ServerPlayer sp1) {
                        condition = defaultSkillTargetCondition(sp1);
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                        }


                        SkillDataJson otherSkillData;
                        int otherSkillLevel;
                        //Power of Blood
                        otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BerserkerSkillTreeInitialization.BERSERKER_POWER_OF_BLOOD);
                        if (otherSkillLevel > 0) {
                            otherSkillData = getID2SkillData().get(BerserkerSkillTreeInitialization.BERSERKER_POWER_OF_BLOOD);
                            otherSkillLevel = otherSkillLevel - 1;
                            float TotalHPConsumptionPercentage = otherSkillData.getValueMultiplierList_01().get(otherSkillLevel).floatValue();
                            float PowerOfBloodDamageMultiplier = otherSkillData.getDamageMultiplierList_01().get(otherSkillLevel).floatValue();
                            ExtraDamageMultiplier = ExtraDamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_2 * TotalHPConsumptionPercentage + PowerOfBloodDamageMultiplier;
                            PowerOfBloodTriggerBleedingChance = otherSkillData.getValueUsage_01().get(otherSkillLevel).floatValue();
                            PowerOfBloodBleedingDamageMultiplier = otherSkillData.getDamageMultiplierList_02().get(otherSkillLevel).floatValue();

                            float PowerOfBloodAOEMultiplier = otherSkillData.getValueMultiplierList_02().get(otherSkillLevel).floatValue();
                            ExtraAOEMultiplier = ExtraAOEMultiplier + PowerOfBloodAOEMultiplier;

                            float currentEntityHealth = EntityMethods.getLivingEntityCurrentHP(entity1);
                            float MaxEntityHealth = EntityMethods.getLivingEntityMaxHP(entity1);
                            float hpShouldReduce = MaxEntityHealth * TotalHPConsumptionPercentage;

                            EntityMethods.setLivingEntityCurrentHP(entity1, MathMethods.max(0.01f, currentEntityHealth - hpShouldReduce));

                        }

                        //thirsty
                        float ThirstyDamageMultiplierBonus = ServerSkillMethods.getEntityStateAmount(entity1, Berserker002.Berserker002_GORE_CROSS_SKILL_DMG_BOOST) * 0.01f;
                        ExtraDamageMultiplier = ExtraDamageMultiplier + ThirstyDamageMultiplierBonus;

                        //BloodLeechSkill
                        otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BerserkerSkillTreeInitialization.BERSERKER_BLOOD_LEECH);
                        if (otherSkillLevel > 0) {
                            otherSkillData = getID2SkillData().get(BerserkerSkillTreeInitialization.BERSERKER_BLOOD_LEECH);
                            otherSkillLevel = otherSkillLevel - 1;
                            float BloodLeechDmgMultiplier = otherSkillData.getDamageMultiplierList_01().get(otherSkillLevel).floatValue();
                            ExtraDamageMultiplier = ExtraDamageMultiplier + BloodLeechDmgMultiplier;
                        }

//                            int EXFlowingUpperSlashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_UPWARD_SLASH_UPGRADE);
//                            if (EXFlowingUpperSlashUpgradeLVL > 0) {
//                                SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_UPWARD_SLASH_UPGRADE);
//                                ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXFlowingUpperSlashUpgradeLVL - 1).floatValue();
//                            }
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }

                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue() * ExtraAOEMultiplier;

                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    float AdditionalDamageBase = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
                    float AdditionalDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    float distance = skill1.getDistance().get(playerSkillLVL).floatValue();

                    float finalPowerOfBloodTriggerBleedingChance = PowerOfBloodTriggerBleedingChance;
                    boolean isSkillActionActive = SBSTickEventMethods.hasSkillActionRunning(entity1, SkillID);

                    if (isSkillActionActive) {
//                        printInGameMsg("active");
                        if (BERSERKER08_SHOULD_TRIGGER_ADDITIONAL_SLASH.containsKey(entity1)) {
//                            printInGameMsg("detect!");
                            BERSERKER08_SHOULD_TRIGGER_ADDITIONAL_SLASH.put(entity1, DfoSwordmanSkillTreeConstants.CONSTANT_BOOLEAN_TRUE);
                        }
                    }
                    else {
//                        printInGameMsg("start");

                        if (entity1 instanceof ServerPlayer sp1) {
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BERSERKER08_SLASH_FROM_LEFT_TO_RIGHT_ANIMATION, entityAnimationSpeedMultiplier);
                        }

                        incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, 1);

                        //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                        //start Actioning

                        int dmgTicks1 = (int) (2 * damageTickSpeedMultiplier);
                        int endTicks1 = (int) (5 * damageTickSpeedMultiplier);
                        int dmgTicks2 = (int) (7 * damageTickSpeedMultiplier);
                        int endTicks2 = (int) (11 * damageTickSpeedMultiplier);
                        int additionalAttackTickApply = (int) (4 * damageTickSpeedMultiplier);

                        int dmgTicks3 = (int) (11.5f * damageTickSpeedMultiplier);
                        int endTicks3 = (int) (availableRespondingTicks * damageTickSpeedMultiplier);

                        int finalEffEntityLifetime = (int) (BERSERKER08_EFF_ENTITY_LIFETIME * damageMultiplier);

                        float finalAoeRange = aoeRange;
                        float finalEntityScale = finalAoeRange * 0.5f;
                        float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
                        float finalAdditionalSlashDmg = (float) ( (AdditionalDamageMultiplier * getEntityAttackDamage(entity1) + AdditionalDamageBase) * skillDamageMultiplier);
                        int actionPeriod = 1;

                        float finalPowerOfBloodSlashBleedingDamage = finalDmg * PowerOfBloodBleedingDamageMultiplier;
                        float finalPowerOfBloodAdditionalSlashBleedingDamage = finalAdditionalSlashDmg * PowerOfBloodBleedingDamageMultiplier;

                        HashSet<LivingEntity> damagedGroup = new HashSet<>();

                        IntegerValueHolder DelayTicksHolder = new IntegerValueHolder(0);
                        IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
                        BooleanValueHolder FromLeftToRightSlashModelSpawnedYet = new BooleanValueHolder(false);
                        BooleanValueHolder FromUpwardSlashModelSpawnedYet = new BooleanValueHolder(false);
                        BooleanValueHolder IsAdditionalSlashTriggered = new BooleanValueHolder(false);
                        ArrayList<Entity> EntityTBRList = new ArrayList<>();

                        BooleanValueHolder IsFirstSkillDone = new BooleanValueHolder(false);
                        BooleanValueHolder TriggeredAdditionalAttack = new BooleanValueHolder(false);

                        MethodAction action =
                                (entity2) ->  {
                                    int updatedTick = ElapsedTicks.getIntCounter();
                                    int delayTicks = DelayTicksHolder.getIntCounter();
                                    float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);

                                    Predicate<Entity> finalCondition = condition.and(DfoSwordmanSkillMethods.getSectorCondition(entity2, 60f, aoeRange));

                                    if (updatedTick <= endTicks3 + delayTicks && !isLivingEntityActionInterrupted(entity2) ) {
                                        if (updatedTick >= dmgTicks1 && updatedTick <= endTicks1) {
                                            if (!FromLeftToRightSlashModelSpawnedYet.getBooleanVal()) {
                                                FromLeftToRightSlashModelSpawnedYet.setBooleanVal(true);
                                                //play skill sound at target location
                                                DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1f);
                                                DfoSwdSoundMethods.PlayRandomSwordUpperSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);

                                                Entity effEntity = GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, triggeredEntityFacingAngle, BERSERKER08_SLASH_FROM_LEFT_TO_RIGHT_Z_DEG, finalEntityScale, BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[0], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[1], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[2], 0f, 1.4f, 0f, finalEffEntityLifetime + dmgTicks2,
                                                        GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, false, false, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);

                                                EntityTBRList.add(effEntity);
                                            }

                                            //start damage
                                            for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, finalCondition)) {
                                                if (tempEntity instanceof LivingEntity tempLivingEntity && !damagedGroup.contains(tempLivingEntity)) {
                                                    damagedGroup.add(tempLivingEntity);
                                                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1f);

                                                    float pushXZStrength = EntityMethods.calculatePushEntityStrength(0.15f, entity2, tempLivingEntity);
                                                    EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityFacingAngle, pushXZStrength, 0, true);

                                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                                    if (finalPowerOfBloodTriggerBleedingChance > ExtraMathMethods.randomFloat()) {
                                                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalPowerOfBloodSlashBleedingDamage);
                                                    }
                                                }
                                            }

                                            if (updatedTick == endTicks1) {
                                                if (entity2 instanceof ServerPlayer sp1) {
                                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BERSERKER08_SLASH_UPWARD_ANIMATION, entityAnimationSpeedMultiplier);
                                                }
                                                damagedGroup.clear();
                                            }
                                        }
//                                    else if (updatedTick >= dmgTicks2 && updatedTick <= endTicks2) {
                                        else if (updatedTick >= dmgTicks2 && updatedTick <= endTicks2) {
                                            if (!FromUpwardSlashModelSpawnedYet.getBooleanVal()) {
                                                FromUpwardSlashModelSpawnedYet.setBooleanVal(true);
                                                //play skill sound at target location
                                                DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1f);
                                                DfoSwdSoundMethods.PlayRandomSwordUpperSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);

                                                Entity effEntity = GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, triggeredEntityFacingAngle, BERSERKER08_SLASH_UPWARD_Z_DEG, finalEntityScale, BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[0], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[1], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[2], 0f, 1.4f, 0f, finalEffEntityLifetime,
                                                        GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, false, false, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);

                                                EntityTBRList.add(effEntity);
                                            }

                                            //start damage
                                            for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, finalCondition)) {
                                                if (tempEntity instanceof LivingEntity tempLivingEntity && !damagedGroup.contains(tempLivingEntity)) {
                                                    damagedGroup.add(tempLivingEntity);

                                                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);

                                                    float pushXZStrength = EntityMethods.calculatePushEntityStrength(0.15f, entity2, tempLivingEntity);
                                                    EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityFacingAngle, pushXZStrength, 0, true);

                                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                                    if (finalPowerOfBloodTriggerBleedingChance > ExtraMathMethods.randomFloat()) {
                                                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalPowerOfBloodSlashBleedingDamage);
                                                    }
                                                }
                                            }

                                            if (updatedTick == endTicks2 - additionalAttackTickApply) {
                                                BERSERKER08_SHOULD_TRIGGER_ADDITIONAL_SLASH.put(entity2, DfoSwordmanSkillTreeConstants.CONSTANT_BOOLEAN_FALSE);
                                            }
                                            if (updatedTick == endTicks2) {
                                                IsFirstSkillDone.setBooleanVal(true);
                                                incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
//                                                SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                                            }
                                        }
                                        else if (updatedTick >= dmgTicks3 + delayTicks && updatedTick <= endTicks3 + delayTicks) {
                                            if (!IsAdditionalSlashTriggered.getBooleanVal()) {
                                                if (DfoSwordmanSkillTreeConstants.CONSTANT_BOOLEAN_TRUE.equals(BERSERKER08_SHOULD_TRIGGER_ADDITIONAL_SLASH.get(entity2)) ) {
                                                    TriggeredAdditionalAttack.setBooleanVal(true);
                                                    damagedGroup.clear();
                                                    DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1f);
                                                    DfoSwdSoundMethods.PlayRandomSwordUpperSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);

                                                    IsFirstSkillDone.setBooleanVal(false);
                                                    incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, 1);
                                                    IsAdditionalSlashTriggered.setBooleanVal(true);
                                                    DelayTicksHolder.setIntCounter(updatedTick - dmgTicks3);
                                                    //TODO generate
                                                    Vector3f triggeredEntityPos = getEntityPos(entity2);
                                                    Vector3f TargetPolarLoc = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, distance, triggeredEntityFacingAngle);

//                                                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256FromPos1ToPos2(entity2, BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[0], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[1], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[2], 1f, 18, triggeredEntityPos, targetPos, GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, BERSERKER08_CLIENT_ACTION_TRIGGERED_ADDITIONAL_ATTACK,
//                                                            3, 2, 12, true, true, true, null, 777, true);

//                                                    Entity effEntity = GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, triggeredEntityFacingAngle, BERSERKER08_SLASH_ADDITIONAL_SLASH_DOWNWARD_Z_DEG, 1, BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[0], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[1], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[2], 0f, 1.4f, 0f, 18,
//                                                            GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);

                                                    SwordSlashCustomEntity effEntity;

                                                    GeoModelSpawnMethods.GenerateCustomSlashWithTargetLoc(entity2, triggeredEntityFacingAngle, BERSERKER08_SLASH_FROM_LEFT_TO_RIGHT_Z_DEG, finalEntityScale, BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[0], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[1], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[2], triggeredEntityPos.x(), triggeredEntityPos.y() + 1.4f, triggeredEntityPos.z(), 30,
                                                            GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE_WITHOUT_START_FRAME, 3, 2, 12, false, false, GeoAnimationAndTextureName.NO_ANIMATION_CUSTOM_SWORD_SLASH_Z12_XPR15, false, true, TargetPolarLoc.x(), TargetPolarLoc.y() + 1.4f, TargetPolarLoc.z(), DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);

                                                    GeoModelSpawnMethods.GenerateCustomSlashWithTargetLoc(entity2, triggeredEntityFacingAngle, BERSERKER08_SLASH_UPWARD_Z_DEG, finalEntityScale, BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[0], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[1], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[2], triggeredEntityPos.x(), triggeredEntityPos.y() + 1.4f, triggeredEntityPos.z(), 30,
                                                            GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE_WITHOUT_START_FRAME, 3, 2, 12, false, false, GeoAnimationAndTextureName.NO_ANIMATION_CUSTOM_SWORD_SLASH_Z12_XPR15, false, true, TargetPolarLoc.x(), TargetPolarLoc.y() + 1.4f, TargetPolarLoc.z(), DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);

                                                    effEntity = GeoModelSpawnMethods.GenerateCustomSlashWithTargetLoc(entity2, triggeredEntityFacingAngle, BERSERKER08_SLASH_ADDITIONAL_SLASH_DOWNWARD_Z_DEG, finalEntityScale, BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[0], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[1], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[2], triggeredEntityPos.x(), triggeredEntityPos.y() + 1.4f, triggeredEntityPos.z(), 30,
                                                            GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, false, false, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, false, true, TargetPolarLoc.x(), TargetPolarLoc.y() + 1.4f, TargetPolarLoc.z(), DfoSwordmanSkillTreeConstants.NO_ANIMATION, false);
                                                    effEntity.setEntityServerAction(Berserker008_EffDamageAndMovingServerAction(entity2, weaponType, triggeredEntityFacingAngle, finalAoeRange, finalAdditionalSlashDmg, finalPowerOfBloodTriggerBleedingChance, finalPowerOfBloodAdditionalSlashBleedingDamage, condition.and(SBConditions.isNotTargetEntity(entity2)), damagedGroup, EntityTBRList), 2);
                                                    effEntity.setClientActionName(BERSERKER08_CLIENT_ACTION_TRIGGERED_ADDITIONAL_ATTACK);
                                                    addEntityToWorld(getEntityLevel(entity2), effEntity);

                                                    if (entity2 instanceof ServerPlayer sp1) {
                                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BERSERKER08_ADDITIONAL_SLASH_DOWNWARD_ANIMATION, entityAnimationSpeedMultiplier);
                                                    }
                                                }
                                            }
                                        }

                                        //update val
                                        ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
                                    }
                                    else {
                                        if (!IsFirstSkillDone.getBooleanVal()) {
                                            incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                                        }
                                        if (!TriggeredAdditionalAttack.getBooleanVal()) {
                                            for (Entity entityTBR : EntityTBRList) {
                                                EntityMethods.RemoveEntity(entityTBR);
                                            }
                                        }
                                        EntityTBRList.clear();
                                        SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                                    }
                                };
                        //Actioning ends
                        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

                        //start loop actioning with fixed period ticks
                        SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
                    }
                };

        tempSkillData.setClearSkillAction(Berserker008::Berserker008_Clean_Method);

        tempSkillData.setActiveSkillAction1(tempSkillAction);
    }

    public static void Berserker008_Clean_Method(LivingEntity entity1) {
        BERSERKER08_SHOULD_TRIGGER_ADDITIONAL_SLASH.remove(entity1);
    }

    public static MethodEntityAction Berserker008_EffDamageAndMovingServerAction(LivingEntity dealer, int weaponType, float triggeredEntityFacingAngle, float finalAoeRange, float finalDmg, float finalPowerOfBloodAdditionalSlashBleedingDamage, float finalPowerOfBloodTriggerBleedingChance, Predicate<Entity> condition, HashSet<LivingEntity> damagedGroup, ArrayList<Entity> EntityTBRList) {
        return (slashEntity) -> {
            SwordSlashCustomEntity swdCustomEntity = (SwordSlashCustomEntity) slashEntity;
            int CurrentLifeTime = swdCustomEntity.getMaxLifetime() - swdCustomEntity.getLifetime();
            float distPercent = 1f * CurrentLifeTime / swdCustomEntity.getMaxLifetime();
            float newX = swdCustomEntity.getStartPosX() + (swdCustomEntity.getTargetPosX() - swdCustomEntity.getStartPosX()) * distPercent;
            float newY = swdCustomEntity.getStartPosY() + (swdCustomEntity.getTargetPosY() - swdCustomEntity.getStartPosY()) * distPercent;
            float newZ = swdCustomEntity.getStartPosZ() + (swdCustomEntity.getTargetPosZ() - swdCustomEntity.getStartPosZ()) * distPercent;
            EntityMethods.moveEntityToXYZ(swdCustomEntity, newX, newY, newZ);

//            if (CurrentLifeTime == 4) {
            if (CurrentLifeTime == 2) {
                for (Entity entityTBR: EntityTBRList) {
                    EntityMethods.RemoveEntity(entityTBR);
                }
                EntityTBRList.clear();
            }

            //start damage
            for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(slashEntity, finalAoeRange, condition)) {
                if (tempEntity instanceof LivingEntity tempLivingEntity && !damagedGroup.contains(tempLivingEntity)) {
                    damagedGroup.add(tempLivingEntity);
                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(dealer), getEntityX(dealer), getEntityY(dealer), getEntityZ(dealer), 0.8f, 1.0f);

                    float pushXZStrength = EntityMethods.calculatePushEntityStrength(0.5f, dealer, tempLivingEntity);
                    EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityFacingAngle, pushXZStrength, 0, true);

                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(dealer, tempLivingEntity, finalDmg, false);

                    if (finalPowerOfBloodTriggerBleedingChance > ExtraMathMethods.randomFloat()) {
                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(dealer, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalPowerOfBloodAdditionalSlashBleedingDamage);
                    }
                }
            }
        };
    }

    //client action
    public static void ClientMethodInit(HashMap<String, MethodEntityAction> clientMethodMap) {
        clientMethodMap.put(BERSERKER08_CLIENT_ACTION_TRIGGERED_ADDITIONAL_ATTACK, Berserker008::Berserker008_ClientAction_TriggeredAdditionalAttack);
    }

    public static void Berserker008_ClientAction_TriggeredAdditionalAttack(Entity entity) {
        SwordSlashCustomEntity SlashEntity = (SwordSlashCustomEntity) entity;
        triggeredFinalSlash = true;
        SlashEntity.setClientActionName(DfoSwordmanSkillTreeConstants.NO_ANIMATION);
    }
}
