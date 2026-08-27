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
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.Blademaster029;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster036;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.FloatValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.ClientSkillMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.ElapsedTicksHolder;
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
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.getSkillsInCooldownClientTimerOverlayInstance;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker009 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_DOWNWARD_STRIKE;

    //5 ticks
    public static final String BERSERKER09_SLASH_DOWNWARD_PREPARATION_IN_MID_ANIMATION = "holding_up_sword_air_start2";
    public static final String BERSERKER09_SLASH_DOWNWARD_PREPARATION_LOOPING_IN_MID_ANIMATION = "holding_up_sword_air_channeling";

    public static final String BERSERKER09_SLASH_DOWNWARD_START_ANIMATION = "holding_up_sword_air_end2";
    public static final String BERSERKER09_SLASH_DOWNWARD_LOOPING_ANIMATION = "holding_up_sword_air_end2_looping";
    public static final String BERSERKER09_SLASH_DOWNWARD_END_ANIMATION = "holding_up_sword_air_end2_looping_end";

    public static final int BERSERKER09_IN_MID_AIR_MULTIPLE_HIT_ACTION_PERIOD = 10;
    public static final float BERSERKER09_IN_MID_AIR_MULTIPLE_HIT_AOE_RADIUS = 1.8f;

    public static final float BERSERKER09_GROUND_WAVE_COUNT = 8;
    public static final float BERSERKER09_GROUND_WAVE_FACING_DEGREE = 360f / BERSERKER09_GROUND_WAVE_COUNT;
    public static final int BERSERKER09_WAVE_LIFETIME = 20;
    public static final float BERSERKER09_WAVE_AOE_RADIUS = 2f;
    public static final int BERSERKER09_WAVE_DAMAGE_PERIOD = 2;

    public static final String BERSERKER09_GROUND_DAMAGE_ACTION = "BERSERKER09_GROUND_DAMAGE_ACTION";

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

        Double GroundWaveDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double GroundWaveConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);

        Float finalAdditionalDmg = (float) ( ( (GroundWaveDamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_2 * TotalHPConsumptionPercentage + PowerOfBloodMultiplier) * getEntityAttackDamage(getClientPlayer()) + GroundWaveConstantDamage) * skillDamageMultiplier);

        Double GroundAOE = skillData.getEffectArea().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (TotalHPConsumptionPercentage * 100)),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (GroundWaveDamageMultiplier.floatValue() * 100)),
                String.format("%s", GroundWaveConstantDamage),
                String.format("%s", finalAdditionalDmg),
                String.format("%s", GroundAOE)

        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker009::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);

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

//                    float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
//                    float entityAnimationSpeedMultiplier = entityAnimationArr[0];
//                    float damageTickSpeedMultiplier = entityAnimationArr[1];

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    float ExtraDamageMultiplier = 0;
                    float ExtraAOEMultiplier = 1f;
                    float PowerOfBloodTriggerBleedingChance = 0;
                    float PowerOfBloodBleedingDamageMultiplier = 0.01f;
                    boolean ShouldShootBloodWave = false;
                    if (entity1 instanceof ServerPlayer sp1) {
                        condition = defaultSkillTargetCondition(sp1);
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER09_SLASH_DOWNWARD_PREPARATION_IN_MID_ANIMATION);
                        playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                        if (playerSkillLVL <= 0) { return; }
                        playerSkillLVL = playerSkillLVL - 1;

                        SkillDataJson otherSkillData;
                        int otherSkillLevel;
                        //Power of Blood
                        otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BerserkerSkillTreeInitialization.BERSERKER_POWER_OF_BLOOD);
                        if (otherSkillLevel > 0) {
                            ShouldShootBloodWave = true;
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

                        float ThirstyDamageMultiplierBonus = ServerSkillMethods.getEntityStateAmount(entity1, Berserker002.Berserker002_MOUNTAINOUS_WHEEL_SKILL_DMG_BOOST) * 0.01f;
                        ExtraDamageMultiplier = ExtraDamageMultiplier + ThirstyDamageMultiplierBonus;

                        //BloodLeechSkill
                        otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BerserkerSkillTreeInitialization.BERSERKER_BLOOD_LEECH);
                        if (otherSkillLevel > 0) {
                            otherSkillData = getID2SkillData().get(BerserkerSkillTreeInitialization.BERSERKER_BLOOD_LEECH);
                            otherSkillLevel = otherSkillLevel - 1;
                            float BloodLeechDmgMultiplier = otherSkillData.getDamageMultiplierList_02().get(otherSkillLevel).floatValue();
                            ExtraDamageMultiplier = ExtraDamageMultiplier + BloodLeechDmgMultiplier;
                        }
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }
                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue() * ExtraAOEMultiplier;

                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);

                    float GroundDamageBase = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
                    float GroundDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    float finalGroundWaveSlashDmg = (float) ( (GroundDamageMultiplier * getEntityAttackDamage(entity1) + GroundDamageBase) * skillDamageMultiplier);

                    float finalDist = skill1.getDistance().get(playerSkillLVL).floatValue();

                    float finalPowerOfBloodTriggerBleedingChance = PowerOfBloodTriggerBleedingChance;
                    float finalPowerOfBloodBleedingDamage = finalDmg * PowerOfBloodBleedingDamageMultiplier;
                    float finalPowerOfBloodBleedingWaveDamage = finalGroundWaveSlashDmg * PowerOfBloodBleedingDamageMultiplier;
                    boolean finalShouldShootBloodWave = ShouldShootBloodWave;

                    float entityFacingAngle = EntityMethods.getEntityHorizontalFacingDeg(entity1);
                    float deltaY = 0.7f;
                    //start damage
//                    Vector3f tempVec = new Vector3f(
//                            Vector3fMethods.PolarProjectionFromXHorizon(finalDist, entityFacingAngle),
//                            deltaY,
//                            Vector3fMethods.PolarProjectionFromZHorizon(finalDist, entityFacingAngle));

                    Vector3f tempVec = new Vector3f(
                            Vector3fMethods.PolarProjectionFromXHorizon(finalDist, entityFacingAngle),
                            deltaY,
                            Vector3fMethods.PolarProjectionFromZHorizon(finalDist, entityFacingAngle));

                    setEntitySPDeltaMovement(entity1, tempVec, true);

                    int actionPeriod = 1;
                    HashSet<LivingEntity> damagedGroup = new HashSet<>();
                    IntegerValueHolder ElapsedTicksHolder = new IntegerValueHolder(0);
                    BooleanValueHolder isDamageActionActiveHolder = new BooleanValueHolder(false);

                    MethodAction action2 = (entity2) -> {
                        int ElapsedTicks = ElapsedTicksHolder.getIntCounter();
                        float currentDealerDeltaY = EntityMethods.getEntityDeltaY(entity2);
                        boolean isDamageActionActive = isDamageActionActiveHolder.getBooleanVal();
                        boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                        boolean contActioning = ElapsedTicks <= 2 || !EntityMethods.isEntityRFFarFromGround(entity2, 0.01f);

                        if (isDamageActionActive) {
                            TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 30, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);

                            float currentDealerDeltaX = EntityMethods.getEntityDeltaX(entity2);
                            float currentDealerDeltaZ = EntityMethods.getEntityDeltaZ(entity2);
                            float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);
                            // in air multiple hit action
                            for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, BERSERKER09_IN_MID_AIR_MULTIPLE_HIT_AOE_RADIUS, condition)) {
                                if (tempEntity instanceof LivingEntity tempLivingEntity && !damagedGroup.contains(tempLivingEntity)) {
                                    damagedGroup.add(tempLivingEntity);
                                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1f);

                                    float pushXStrength = EntityMethods.calculatePushEntityStrength(currentDealerDeltaX, entity2, tempLivingEntity);
                                    float pushYStrength = EntityMethods.calculatePushEntityStrength(currentDealerDeltaY, entity2, tempLivingEntity);
                                    float pushZStrength = EntityMethods.calculatePushEntityStrength(currentDealerDeltaZ, entity2, tempLivingEntity);
                                    float newX = Vector3fMethods.PolarProjectionFromXHorizon(pushXStrength, triggeredEntityFacingAngle);
                                    float newY = Vector3fMethods.PolarProjectionFromXHorizon(pushYStrength, triggeredEntityFacingAngle);
                                    float newZ = Vector3fMethods.PolarProjectionFromZHorizon(pushZStrength, triggeredEntityFacingAngle);
                                    EntityMethods.pushEntityWithDeltaValueXYZ(tempLivingEntity, newX, newY, newZ, false);

                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                    if (finalPowerOfBloodTriggerBleedingChance > ExtraMathMethods.randomFloat()) {
                                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalPowerOfBloodBleedingDamage);
                                    }
                                }
                            }

                            if (ElapsedTicks % BERSERKER09_IN_MID_AIR_MULTIPLE_HIT_ACTION_PERIOD == 0) {
                                damagedGroup.clear();
                            }
                        }

                        //leap over with downward strike
                        if (!isActionInterrupted) {
                            if (!contActioning) {
                                //play animation
                                if (entity2 instanceof ServerPlayer sp1) {
                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER09_SLASH_DOWNWARD_END_ANIMATION);
                                }

                                DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 4f);
                                //stop loop action
                                incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);

                                SBSTickEventMethods.setSkillActionDone(entity2, SkillID);

                                //ground damage action
                                if (finalShouldShootBloodWave) {
                                    String GroupDmgActionID = BERSERKER09_GROUND_DAMAGE_ACTION + getEntityTickCount(entity2);
                                    MethodAction groundDamageAction = Berserker009_DealerLandedAction(entity2, finalGroundWaveSlashDmg, aoeRange, finalPowerOfBloodTriggerBleedingChance, finalPowerOfBloodBleedingWaveDamage, condition.and(SBConditions.isNotTargetEntity(entity2)), GroupDmgActionID);
                                    SBSTickEventMethods.setMethodActionTimer(entity2, GroupDmgActionID, BERSERKER09_WAVE_DAMAGE_PERIOD, groundDamageAction);
                                }
                            } else {
                                //play animation
                                if (entity2 instanceof ServerPlayer sp1) {
                                    if(currentDealerDeltaY <= 0) {
                                        isDamageActionActiveHolder.setBooleanVal(true);
                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER09_SLASH_DOWNWARD_LOOPING_ANIMATION);
                                    }
                                    else if ((ElapsedTicks - 8) % 500 == 0) {
                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER09_SLASH_DOWNWARD_PREPARATION_LOOPING_IN_MID_ANIMATION);
                                    } else if (ElapsedTicks == 4) {
                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER09_SLASH_DOWNWARD_START_ANIMATION);
                                    }
                                }

                            }
                        }
                        else {
                            incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                            SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                        }

                        ElapsedTicksHolder.setIntCounter(ElapsedTicks + actionPeriod);
                    };

                    SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action2);
                };

//        tempSkillData.setClearSkillAction(Berserker009::Berserker009_Clean_Method);

        tempSkillData.setActiveSkillAction1(tempSkillAction);
    }

    public static MethodAction Berserker009_DealerLandedAction(LivingEntity triggeredEntity, float finalDmg, float finalAOE, float finalPowerOfBloodTriggerBleedingChance, float finalPowerOfBloodBleedingWaveDamage, Predicate<Entity> finalCondition, String ActionID) {
        IntegerValueHolder ElapsedTicksHolder = new IntegerValueHolder(0);
        Vector3f centerPos = getEntityPos(triggeredEntity);
        Vector3f targetPos;
        float triggeredEntityHorizontalFacingDeg = getEntityHorizontalFacingDeg(triggeredEntity);
        SwordSlashCustomEntity effEntity;
        HashMap<SwordSlashCustomEntity, HashSet<LivingEntity>> WaveDamageGroups = new HashMap<>();
        for (int i = 0; i < BERSERKER09_GROUND_WAVE_COUNT; i++) {
            float WaveEffFacingDeg = BERSERKER09_GROUND_WAVE_FACING_DEGREE * i + triggeredEntityHorizontalFacingDeg;
            targetPos = Vector3fMethods.PolarProjectionFromVecHorizon(centerPos, finalAOE, WaveEffFacingDeg);
            effEntity = GeoModelSpawnMethods.GenerateCustomSlashWithTargetLoc(triggeredEntity, WaveEffFacingDeg, 0, finalAOE * 0.4f, BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[0], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[1], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[2], centerPos.x(), centerPos.y(), centerPos.z(), BERSERKER09_WAVE_LIFETIME,
                    GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, DfoSwordmanSkillTreeConstants.NO_ANIMATION, false, true, targetPos.x(), targetPos.y(), targetPos.z(), ParticleRegistry.CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, true);

            WaveDamageGroups.put(effEntity, new HashSet<>());
        }

        return (dealer) -> {
            int ElapsedTicks = ElapsedTicksHolder.getIntCounter();
            ElapsedTicksHolder.setIntCounter(ElapsedTicks + BERSERKER09_WAVE_DAMAGE_PERIOD);

            if (ElapsedTicks < BERSERKER09_WAVE_LIFETIME) {
                for (SwordSlashCustomEntity waveEntity: WaveDamageGroups.keySet()) {
                    HashSet<LivingEntity> damagedGroup = WaveDamageGroups.get(waveEntity);
                    float waveEntityHorizontalFacingAngle = getEntityBodyHorizontalFacingDeg(waveEntity);
                    //start damage
                    for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(waveEntity, BERSERKER09_WAVE_AOE_RADIUS, finalCondition)) {
                        if (tempEntity instanceof LivingEntity tempLivingEntity && !damagedGroup.contains(tempLivingEntity)) {
                            damagedGroup.add(tempLivingEntity);

                            DfoSwdSoundMethods.PlayWaveSwordHitSound(getEntityLevel(dealer), getEntityX(tempEntity), getEntityY(tempEntity), getEntityZ(tempEntity), 0.8f, 1.0f);

                            float pushXZStrength = EntityMethods.calculatePushEntityStrength(0.15f, dealer, tempLivingEntity);
                            EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, waveEntityHorizontalFacingAngle, pushXZStrength, 0, true);

                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(dealer, tempLivingEntity, finalDmg, false);

                            if (finalPowerOfBloodTriggerBleedingChance > ExtraMathMethods.randomFloat()) {
                                DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(dealer, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalPowerOfBloodBleedingWaveDamage);
                            }
                        }
                    }
                }
            }
            else {
                for (SwordSlashCustomEntity waveEntity: WaveDamageGroups.keySet()) {
                    RemoveEntity(waveEntity);
                }
                SBSTickEventMethods.setSkillActionDone(dealer, ActionID);
            }
        };
    }
}
