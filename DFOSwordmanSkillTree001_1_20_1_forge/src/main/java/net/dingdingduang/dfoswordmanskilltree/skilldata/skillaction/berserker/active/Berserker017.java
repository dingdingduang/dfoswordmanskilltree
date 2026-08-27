package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
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
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker017 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_BLOOD_SWORD_THRUST;

    public static final String BERSERKER17_BLOOD_SWORD_ATTACK_ANIMATION = "berserker_blood_sword_attack";
    public static final String BERSERKER17_BLOOD_SWORD_EXPLOSION_ANIMATION = "berserker_blood_sword_explosion";

    public static final String BERSERKER17_BLOOD_SWORD_EXPLOSION_TEXTURE_PATH = "textures/entity/skilleff/berserker/blood_sword_thrust/explosion/";

    public static final String BERSERKER17_EXPLOSION_DAMAGE_ACTION = "BERSERKER17_EXPLOSION_DAMAGE_ACTION";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        double skillDamageMultiplier = getCPlayerSkillDamage();

//        int otherSkillLevel = ClientSkillMethods.queryCPlayerCurrentSkillLevel(BerserkerSkillTreeInitialization.BERSERKER_POWER_OF_BLOOD);

        Double TotalHPConsumptionPercentage = skillData.getValueMultiplierList_01().get(correctSkillLevel);

        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);

        Float finalDmg = (float) ( ( (DamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_3 * TotalHPConsumptionPercentage) * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        Double ExplosionDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double ExplosionConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);

        Float finalExplosionDmg = (float) ( ( (ExplosionDamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_3 * TotalHPConsumptionPercentage) * getEntityAttackDamage(getClientPlayer()) + ExplosionConstantDamage) * skillDamageMultiplier);

        Double AOE = skillData.getEffectArea().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (TotalHPConsumptionPercentage * 100)),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (ExplosionDamageMultiplier.floatValue() * 100)),
                String.format("%s", ExplosionConstantDamage),
                String.format("%s", finalExplosionDmg),
                String.format("%s", AOE)

        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker017::getSkillDescription);

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
                    float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                    float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                    float damageTickSpeedMultiplier = entityAnimationArr[1];

                    int playerSkillLVL = 0;
                    float aoeRange;
                    float damageBase;
                    float damageMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);
                    Predicate<Entity> condition;

                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    float ExtraDamageMultiplier = 0;
                    if (entity1 instanceof ServerPlayer sp1) {
                        playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                        if (playerSkillLVL <= 0) { return; }
                        playerSkillLVL = playerSkillLVL - 1;

                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BERSERKER17_BLOOD_SWORD_ATTACK_ANIMATION, entityAnimationSpeedMultiplier);
                        condition = defaultSkillTargetCondition(sp1);

//                        int EXConsecutiveSlashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_CONSECUTIVE_SLASH_UPGRADE);
//                        if (EXConsecutiveSlashUpgradeLVL > 0) {
//                            SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_CONSECUTIVE_SLASH_UPGRADE);
//                            ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXConsecutiveSlashUpgradeLVL - 1).floatValue();
//                            additionalAttacks = additionalAttacks + EX_SkillData.getValueUsage_01().get(EXConsecutiveSlashUpgradeLVL - 1).intValue();
//                        }
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }

                    float TotalHPConsumptionPercentage = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();
                    ExtraDamageMultiplier = ExtraDamageMultiplier + TotalHPConsumptionPercentage * BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_3;

                    float currentEntityHealth = EntityMethods.getLivingEntityCurrentHP(entity1);
                    float MaxEntityHealth = EntityMethods.getLivingEntityMaxHP(entity1);
                    float hpShouldReduce = MaxEntityHealth * TotalHPConsumptionPercentage;
                    EntityMethods.setLivingEntityCurrentHP(entity1, MathMethods.max(0.01f, currentEntityHealth - hpShouldReduce));

//                    float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity1);
                    //move pos
//                    float newX = Vector3fMethods.PolarProjectionFromXHorizon(0.6f, triggeredEntityHorizontalFacingAngle);
//                    float newZ = Vector3fMethods.PolarProjectionFromZHorizon(0.6f, triggeredEntityHorizontalFacingAngle);
//
//                    setEntitySPDeltaMovement(entity1, newX, 0f, newZ, true);

                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();

                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    int startDmgTicks = (int) (7 * damageTickSpeedMultiplier);
                    int endDmgTicks = (int) (12 * damageTickSpeedMultiplier);
                    float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);

                    float ExplosionDamageBase = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
                    float ExplosionDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    float finalExplosionDmg = (float) ( (ExplosionDamageMultiplier * getEntityAttackDamage(entity1) + ExplosionDamageBase) * skillDamageMultiplier);

                    int actionPeriod = 1;

                    HashSet<LivingEntity> damagedGroup = new HashSet<>();
                    BooleanValueHolder spawnEffect = new BooleanValueHolder(false);
                    IntegerValueHolder ElapsedTicksHolder = new IntegerValueHolder(0);

                    MethodAction action =
                            (entity2) ->  {
                                boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);

                                int updatedTick = ElapsedTicksHolder.getIntCounter();
                                ElapsedTicksHolder.setIntCounter(updatedTick + actionPeriod);

                                if (!isActionInterrupted && updatedTick >= startDmgTicks && updatedTick <= endDmgTicks) {
                                    float currentTriggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity2);
                                    //spawn model
                                    if (!spawnEffect.getBooleanVal()) {
                                        spawnEffect.setBooleanVal(true);
                                        //play skill sound at target location
                                        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
//                                        DfoSwdSoundMethods.PlayBlademaster005TripleSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);

//                                        Vector3f tempFrontLoc = new Vector3f(
//                                                Vector3fMethods.PolarProjectionFromXHorizon(0.7f, currentTriggeredEntityHorizontalFacingAngle),
//                                                1.4f,
//                                                Vector3fMethods.PolarProjectionFromZHorizon(0.7f, currentTriggeredEntityHorizontalFacingAngle));
//
//                                        GeoModelSpawnMethods.GenerateEffStabEntity(entity2, currentTriggeredEntityHorizontalFacingAngle, 30f,
//                                                GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_STAB, GeoAnimationAndTextureName.EFF_ENTITY_ANIMATION_NAME_STAB, DfoSwordmanSkillMethods.getEntityScaleByAOE(aoeRange),
//                                                8, true, tempFrontLoc.x(), tempFrontLoc.y(), tempFrontLoc.z(), true);
                                    }


                                    //Sector damage cond
                                    Predicate<Entity> SectorDmgCond = DfoSwordmanSkillMethods.getSectorCondition(entity2, 75f, aoeRange);

                                    for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, aoeRange * 0.5f, condition.and(SectorDmgCond))) {
                                        if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                            if (!damagedGroup.contains(tempLivingEntity)) {
                                                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                                DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, EntityMethods.getEntityLevel(entity2), EntityMethods.getEntityX(entity2), EntityMethods.getEntityY(entity2), EntityMethods.getEntityZ(entity2), 0.8f, 1.0f);
                                                damagedGroup.add(tempLivingEntity);

                                                float pushXZStrength = EntityMethods.calculatePushEntityStrength(0.35f, entity2, tempLivingEntity);
                                                EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, currentTriggeredEntityHorizontalFacingAngle, pushXZStrength, 0, true);
                                            }
                                        }
                                    }
                                }
                                else {
                                    if (isActionInterrupted) {
                                        incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                                        SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                                    }
                                    else {
                                        if (updatedTick >= endDmgTicks) {
                                            incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                                            SBSTickEventMethods.setSkillActionDone(entity2, SkillID);

                                            String ExplosionDmgActionID = BERSERKER17_EXPLOSION_DAMAGE_ACTION + getEntityTickCount(entity2);
                                            int explosionDamageActionPeriod = 2;
                                            MethodAction explosionDamageAction = Berserker017_DealerBloodSwordExplosionAction(entity2, finalExplosionDmg, aoeRange, condition.and(SBConditions.isNotTargetEntity(entity2)), actionPeriod, ExplosionDmgActionID);
                                            SBSTickEventMethods.setMethodActionTimer(entity2, ExplosionDmgActionID, explosionDamageActionPeriod, explosionDamageAction);
                                        }
                                    }
                                }
                            };
                    //Actioning ends
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

                    //start loop actioning with fixed period ticks
                    SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
                };


        tempSkillData.setActiveSkillAction1(tempSkillAction);
    }

    public static MethodAction Berserker017_DealerBloodSwordExplosionAction(LivingEntity triggeredEntity, float finalDmg, float finalAOE, Predicate<Entity> finalCondition, int actionPeriod, String ActionID) {
        IntegerValueHolder ElapsedTicksHolder = new IntegerValueHolder(0);
        float triggeredEntityHorizontalFacingDeg = getEntityHorizontalFacingDeg(triggeredEntity);
        Vector3f tempFrontLoc = new Vector3f(
                Vector3fMethods.PolarProjectionFromXHorizon(1.6f, triggeredEntityHorizontalFacingDeg),
                1.6f,
                Vector3fMethods.PolarProjectionFromZHorizon(1.6f, triggeredEntityHorizontalFacingDeg));

        Entity effEntity = GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256(triggeredEntity,255, 255, 255, DfoSwordmanSkillMethods.getEntityScaleByAOE(finalAOE), tempFrontLoc.x(), tempFrontLoc.y(), tempFrontLoc.z(), 20, false,
                0, 0, 0, 0, 0, 0, BERSERKER17_BLOOD_SWORD_EXPLOSION_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                DfoSwordmanSkillTreeConstants.NO_ANIMATION, false, 0, 0, 20, false, false, true, null, 777, true);

        if (triggeredEntity instanceof ServerPlayer sp1) {
            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BERSERKER17_BLOOD_SWORD_EXPLOSION_ANIMATION, 1);
        }

//        Vector3f dmgCenter = getEntityPos(effEntity);

        HashSet<LivingEntity> damagedGroup = new HashSet<>();

        return (dealer) -> {
            int ElapsedTicks = ElapsedTicksHolder.getIntCounter();
            ElapsedTicksHolder.setIntCounter(ElapsedTicks + actionPeriod);

            if (ElapsedTicks <= 20) {
                //start damage
                for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(effEntity, finalAOE, finalCondition)) {
                    if (tempEntity instanceof LivingEntity tempLivingEntity && !damagedGroup.contains(tempLivingEntity)) {
                        damagedGroup.add(tempLivingEntity);

                        DfoSwdSoundMethods.PlayBooDongHitSound(getEntityLevel(dealer), getEntityX(tempEntity), getEntityY(tempEntity), getEntityZ(tempEntity), 0.8f, 1.0f);

                        float pushXZStrength = EntityMethods.calculatePushEntityStrength(0.15f, dealer, tempLivingEntity);
                        EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityHorizontalFacingDeg, pushXZStrength, 0, true);

                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(dealer, tempLivingEntity, finalDmg, false);

                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(dealer, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg * DfoStatusGeneralMethods.BLEEDING_DEFAULT_DAMAGE_MULTIPLIER);
                    }
                }
            }
            else {
                SBSTickEventMethods.setSkillActionDone(dealer, ActionID);
            }
        };
    }
}
