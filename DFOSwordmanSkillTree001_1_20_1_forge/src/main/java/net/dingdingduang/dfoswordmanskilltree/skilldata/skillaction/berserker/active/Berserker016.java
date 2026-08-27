package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.entity.DfoGeneralEntitySpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientparticlehelper.ClientParticleHelperEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fLinesMethods;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.printInGameMsg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.getCurrentEntitiesInServerLevel;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues.getSLivingEntityState;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker016 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_DEATH_GRIP;
    public static final String ChannelingActionID = SkillID + "_ChannelingActionID";
    public static final String BERSERKER016_DISCHARGE_ACTION = "BERSERKER016_DISCHARGE_ACTION";

    public static final float BERSERKER016_MIN_DIST = 3f;
    public static final int BERSERKER016_HOLDING_TIME = 100;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double Duration = skillData.getDuration().get(correctSkillLevel);

        double skillDamageMultiplier = getCPlayerSkillDamage();

        Double MaxSkillDmgMultiplierPercentage = skillData.getValueMultiplierList_01().get(correctSkillLevel);

        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);

        Float finalDmg = (float) ( ( DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        Double AreaOfEffects = skillData.getEffectArea().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%s", Duration),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (ConstantDamage.floatValue() * 100)),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (MaxSkillDmgMultiplierPercentage.floatValue() * 100)),
                String.format("%s", AreaOfEffects)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);
        tempSkillData.setTranslatableTooltipGetter(Berserker016::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);

        //while channeling
        tempSkillData.setChannelingSkillAction1(Berserker016::Berserker016_ChannelingAction);
        //final action
//        tempSkillData.setActiveSkillAction1(Berserker016::Berserker016_ActivatePassive);
        tempSkillData.setActiveSkillAction1(DfoSwordmanSkillMethods::DoNothing);
    }

    public static void Berserker016_ChannelingAction(LivingEntity entity1) {
        incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);
        int effPeriod = 5;
//        int findEntityActionPeriod = 2;
        int actionPeriod = 1;
        //TODO: play attracting sound
//        DfoSwdSoundMethods.PlayChannelingStartSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);

        Predicate<Entity> condition;
        int playerSkillLVL = 0;
        SkillDataJson skill1 = getID2SkillData().get(SkillID);
        if (entity1 instanceof ServerPlayer sp1) {
            playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
            if (playerSkillLVL <= 0) {
                return;
            }
            playerSkillLVL = playerSkillLVL - 1;
            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_START);
            condition = defaultSkillTargetCondition(sp1).and(SBConditions.isNotTargetEntity(entity1)).and(EntityMethods::EntityDoesNotAffectByDeltaMovementMethod);
        }
        else {
            condition = defaultSkillTargetCondition().and(SBConditions.isNotTargetEntity(entity1)).and(EntityMethods::EntityDoesNotAffectByDeltaMovementMethod);
        }

        float channelingTime = skill1.getChannelingTime().get(playerSkillLVL).floatValue();
        int lifetime = (int) (channelingTime * 20);

        float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();

        IntegerValueHolder TotalChannelingTicksHolder = new IntegerValueHolder(lifetime);
        IntegerValueHolder tempIntegerTicker = new IntegerValueHolder(0);
        TypeValueHolder<LivingEntity> TargetHolder = new TypeValueHolder<>(null);
        BooleanValueHolder FoundEntity = new BooleanValueHolder(false);
        HashSet<LivingEntity> TargetNearbyLivingEntities = new HashSet<>();

        MethodAction action = (entity2) -> {
            LivingEntity target = TargetHolder.getAnyTypeValue();
            int currentPassedTicks = tempIntegerTicker.getIntCounter();
            tempIntegerTicker.setIntCounter(currentPassedTicks + actionPeriod);

            if (currentPassedTicks > 2) {
                if (target == null) {
//                    Vector3f startP = getEntityPos(entity2).add(0, DfoSwordmanSkillTreeConstants.MC_DEFAULT_EYE_HEIGHT, 0);
                    Vector3f startP = getEntityPos(entity2);
                    float triggeredEntityHorizontalFacing = getEntityHorizontalFacingDeg(entity2);
                    float triggeredEntityVerticalFacing = getEntityVerticalFacingDeg(entity2);
                    Vector3f endP = Vector3fMethods.PolarProjectionFromRotation(startP, 1f, triggeredEntityHorizontalFacing, triggeredEntityVerticalFacing);
                    LivingEntity nearestEntity = Vector3fLinesMethods.findClosestVectorToLineWithLivingEntity(entity2, startP, endP, getCurrentEntitiesInServerLevel().keySet(), BERSERKER016_MIN_DIST, condition.and(DfoSwordmanSkillMethods.getSectorCondition(entity2, 30f)));

                    if (nearestEntity != null) {
//                    target = livingEntity;
                        TargetHolder.setAnyTypeValue(nearestEntity);
                        TargetNearbyLivingEntities.add(nearestEntity);

                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(nearestEntity, aoeRange, condition)) {
                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                TargetNearbyLivingEntities.add(tempLivingEntity);
                            }
                        }

                        return;
                    }

                    boolean contActioning = false;
                    HashMap<String, Integer> ServerLivingEntityState = getSLivingEntityState().get(entity2);
                    if (ServerLivingEntityState != null) {
                        contActioning = ServerLivingEntityState.get(Constants.IS_CHANNELING) == 1;
                    }

                    if (!contActioning && currentPassedTicks >= TotalChannelingTicksHolder.getIntCounter()) {
//                    printInGameMsg("over because not found");
                        SBSTickEventMethods.setSkillActionDone(entity2, ChannelingActionID);

                        incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);

                        if (entity2 instanceof ServerPlayer sp1) {
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_END);
                        }
//                else {
//                    PlaySoundAtLivingEntityLocationEX(entity2, SoundRegistry.CHANNELING_FAILED_SOUND.get(), 0.4f, 1.0f);
//                }
                    }
                    else {
                        if (isEntityChannellingInterrupted(entity2)) {
                            if (entity2 instanceof ServerPlayer sp1) {
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_END);
                            }

                            incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                            SBSTickEventMethods.setSkillActionDone(entity2, ChannelingActionID);
                        }
                    }

                    if ((currentPassedTicks - 5) % 500 == 0 && entity2 instanceof ServerPlayer sp1) {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_LOOPING);
                    }
                } else {
                    if (!FoundEntity.getBooleanVal()) {
                        tempIntegerTicker.setIntCounter(0);
                        currentPassedTicks = 0;
                        FoundEntity.setBooleanVal(true);
                        if (entity2 instanceof ServerPlayer sp1) {
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_LOOPING);
                        }
                    }

                    float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity2);
                    Vector3f triggeredEntityPos = getEntityPos(entity2);
                    Vector3f frontPos = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, 1.2f, triggeredEntityHorizontalFacingAngle);

                    for (LivingEntity surroundingEntitiesNearTarget: TargetNearbyLivingEntities) {
                        tpEntityToPos(surroundingEntitiesNearTarget, frontPos);
                    }

                    DfoGeneralEntitySpawnMethods.SetupClientParticleHelperNoLoc(target, triggeredEntityHorizontalFacingAngle, 0f, 1, frontPos.x(), frontPos.y(), frontPos.z(), DfoSwordmanSkillTreeConstants.NO_ANIMATION, Berserker016_Attract, null, 777, true);

                    if (currentPassedTicks % effPeriod == 0) {
                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(target, 255, 255, 255, 1f, 0, 1.4f, 0, 7, Berserker010.BERSERKER010_FRENZY_CHARGING, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                DfoSwordmanSkillTreeConstants.NO_ANIMATION, 7, 0, 7, false, false, true, null, 777, true);

                        TimedAttributesDistributor.applyTimedKnockbackResistToEntity(entity2, 20, 10, Constants.OP_ADDITION, false, SkillID, null, null);
                        TimedAttributesDistributor.applyTimedBeneficialStatusInvincibilityToEntity(entity2, 20, 0.01, Constants.OP_ADDITION, false, SkillID, null, null);
                    }


                    boolean contActioning = false;
                    HashMap<String, Integer> ServerLivingEntityState = getSLivingEntityState().get(entity2);
                    if (ServerLivingEntityState != null) {
                        contActioning = ServerLivingEntityState.get(Constants.IS_CHANNELING) == 1;
                    }

                    if ((!contActioning && currentPassedTicks >= BERSERKER016_HOLDING_TIME) || currentPassedTicks >= TotalChannelingTicksHolder.getIntCounter()) {
                        SBSTickEventMethods.setSkillActionDone(entity2, ChannelingActionID);
                        incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
//                    printInGameMsg("action over");

                        //TODO generate eff entity and sound
//                    if (!isEntityChannellingInterrupted(entity2)) {
//                        printInGameMsg("discharge");
//                        Berserker016_BloodLustDischarge(entity2, target);
//                    }
//                    printInGameMsg("discharge");
                        Berserker016_BloodLustDischarge(entity2, target);
                    }
//                    else {
//                        if (isEntityChannellingInterrupted(entity2)) {
//                            if (entity2 instanceof ServerPlayer sp1) {
//                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_END);
//                            }
//                        }
//                    }
                }
            }
        };

        //start loop action
        SBSTickEventMethods.setMethodActionTimer(entity1, ChannelingActionID, actionPeriod, action);
    }

    public static void Berserker016_BloodLustDischarge(LivingEntity dealer, LivingEntity target) {
        SkillDataJson skill1 = getID2SkillData().get(SkillID);
        Predicate<Entity> condition;
        int playerSkillLVL = 0;

        float ExtraDamageMultiplier = 0;
        if (dealer instanceof ServerPlayer sp1) {
            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_END);
            condition = defaultSkillTargetCondition(sp1).and(SBConditions.isNotTargetEntity(dealer));

            playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY);
            if (playerSkillLVL <= 0) { return; }
            playerSkillLVL = playerSkillLVL - 1;
        }
        else {
            condition = defaultSkillTargetCondition().and(SBConditions.isNotTargetEntity(dealer));
        }

        int DurationTicks = (int) (skill1.getDuration().get(playerSkillLVL).floatValue() * 20);
        Vector3f triggeredEntityPos = getEntityPos(dealer);
        float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(dealer);
//        float triggeredEntityVerticalFacingAngle = getEntityVerticalFacingDeg(dealer);
        float newX = Vector3fMethods.PolarProjectionFromXHorizon(1f, triggeredEntityHorizontalFacingAngle);
        float newZ = Vector3fMethods.PolarProjectionFromZHorizon(1f, triggeredEntityHorizontalFacingAngle);

        DfoGeneralEntitySpawnMethods.SetupClientParticleHelperNoLoc(dealer, triggeredEntityHorizontalFacingAngle, 0, 4, triggeredEntityPos.x() + newX, triggeredEntityPos.y(), triggeredEntityPos.z() + newZ, DfoSwordmanSkillTreeConstants.NO_ANIMATION, Berserker016_Discharge, null, 777, true);

        GeoModelSpawnMethods.Generate2DHitEff(target, 270, 1f, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_FRAMES, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_FRAMES, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.6f, ExtraMathMethods.randomFloat(0.4f) - 0.2f, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_ARR[ExtraMathMethods.randomInt(BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_ARR.length)], true, true);
//        GeoModelSpawnMethods.Generate2DHitEff(target, ExtraMathMethods.randomFloat(360f), 1f, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_FRAMES, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_FRAMES, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_ARR[ExtraMathMethods.randomInt(BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_ARR.length)], true, true);

        setEntitySPDeltaMovement(target, newX, 0, newZ, false);

        float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
        float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(dealer);
        float damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
        float damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;
        float MaxSkillDamageMultiplierBonus = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();

        float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(dealer) + damageBase) * skillDamageMultiplier);

        float currentTargetBeforeTakingDmgHealth = EntityMethods.getLivingEntityCurrentHP(target);
        float MaxTargetHealth = EntityMethods.getLivingEntityMaxHP(target);

        HashSet<LivingEntity> damagedGroup = new HashSet<>();
        IntegerValueHolder ElapsedTicksHolder = new IntegerValueHolder(0);
        int actionPeriod = 2;
        int MaxActionLifeTime = 20;
        String ActionID = BERSERKER016_DISCHARGE_ACTION + EntityMethods.getEntityHashID(dealer);

        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(dealer, target, finalDmg, false);
        float currentTargetAfterTakingDmgHealth = EntityMethods.getLivingEntityCurrentHP(target);
        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(dealer, target, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg * DfoStatusGeneralMethods.BLEEDING_DEFAULT_DAMAGE_MULTIPLIER_2);

        float HPReduced = MathMethods.max(0, currentTargetBeforeTakingDmgHealth - currentTargetAfterTakingDmgHealth) / MaxTargetHealth;
        if (HPReduced > 0) {
            float ExtraSkillDmgMultiplierBonus = MaxSkillDamageMultiplierBonus * HPReduced;
            TimedAttributesDistributor.applyTimedSkillDmgToEntity(dealer, DurationTicks, ExtraSkillDmgMultiplierBonus, Constants.OP_ADDITION, false, SkillID, null, null);
        }

        MethodAction dmgProcessingAction = (dealer2) -> {
            int updatedTick = ElapsedTicksHolder.getIntCounter();
            ElapsedTicksHolder.setIntCounter(updatedTick + actionPeriod);

            if (updatedTick <= MaxActionLifeTime) {
                for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(target, aoeRange, condition)) {
                    if (tempEntity instanceof LivingEntity tempLivingEntity && !damagedGroup.contains(tempLivingEntity)) {
                        damagedGroup.add(tempLivingEntity);
                        DfoSwdSoundMethods.PlayBooDongHitSound(getEntityLevel(dealer2), getEntityX(tempLivingEntity), getEntityY(tempLivingEntity), getEntityZ(tempLivingEntity), 0.8f, 1f);

                        float pushXZStrength = EntityMethods.calculatePushEntityStrength(0.15f, dealer2, tempLivingEntity);
                        EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityHorizontalFacingAngle, pushXZStrength, 0, true);

                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(dealer2, tempLivingEntity, finalDmg, false);

//                    if (finalPowerOfBloodTriggerBleedingChance > ExtraMathMethods.randomFloat()) {
//                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(dealer2, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalPowerOfBloodSlashBleedingDamage);
//                    }
                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(dealer2, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg * DfoStatusGeneralMethods.BLEEDING_DEFAULT_DAMAGE_MULTIPLIER_2);
                    }
                }
            }
            else {
                SBSTickEventMethods.setSkillActionDone(dealer2, ActionID);
            }
        };

        SBSTickEventMethods.setMethodActionTimer(dealer, ActionID, actionPeriod, dmgProcessingAction);
    }

    //client
    public static final String Berserker016_Attract = "Berserker016_Attract";

    public static final String Berserker016_Discharge = "Berserker016_Discharge";

    public static void Berserker016_ParticleRegistry(HashMap<String, MethodEntityAction> particleMap) {
        particleMap.put(Berserker016_Discharge, Berserker016::CreateBloodlustGripDischargeMethodReference);
        particleMap.put(Berserker016_Attract, Berserker016::CreateBloodlustGripAttractMethodReference);
    }

//    float minDist = 0.5f;
//    float maxDist = 1.0f;
//    float velocity = 0.1f;
    public static void CreateBloodlustGripAttractMethodReference(Entity entity) {
        ClientParticleHelperEntity clientParticleHelperEntity = (ClientParticleHelperEntity) entity;
        Vector3f center = getEntityPos(clientParticleHelperEntity);

        Vector3f randPos, randomDelta;
        for (int i = 0; i < 20; i++) {
            float randHorizontalFacing = ExtraMathMethods.randomFloat(360f);
            float randVerticalFacing = ExtraMathMethods.randomFloat(180f) - 90f;
            float randomDist = ExtraMathMethods.randomFloat() + 1;
            int lifetime = (int) (randomDist * 5);
            randPos = Vector3fMethods.PolarProjectionFromRotation(center, randomDist, randHorizontalFacing, randVerticalFacing);
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(randomDist * -0.1f, randHorizontalFacing, randVerticalFacing);
            ParticleMethods.GenerateWhiteParticle(randPos.x(), randPos.y()+1.4f, randPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 1f + ExtraMathMethods.randomFloat(), lifetime, 0.86f, 0.08f, 0.24f);
        }
    }

    public static void CreateBloodlustGripDischargeMethodReference(Entity entity) {
//        printInGameMsg("1: "+getEntityLevel(entity).isClientSide);
        ClientParticleHelperEntity clientParticleHelperEntity = (ClientParticleHelperEntity) entity;
        Vector3f fromPos = getEntityPos(clientParticleHelperEntity);
        float entityHorizontalFacing = getEntityHorizontalFacingDeg(clientParticleHelperEntity);

//        printInGameMsg("x: "+fromPos.x()+", y: "+fromPos.y+", z: "+fromPos.z);
        Vector3f randomDelta;
        for (int i = 0; i < 80; i++) {
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(0.1f + ExtraMathMethods.randomFloat() * 0.1f, entityHorizontalFacing + ExtraMathMethods.randomFloat(60) - 30, ExtraMathMethods.randomFloat(60) - 30);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 1.5f + ExtraMathMethods.randomFloat() * 2, 30, 0.86f, 0.08f, 0.24f);
        }

        for (int i = 0; i < 60; i++) {
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.07f, entityHorizontalFacing + 80 + ExtraMathMethods.randomFloat(10), ExtraMathMethods.randomFloat(180) - 90);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 2f + ExtraMathMethods.randomFloat() * 2, 30, 0.9f, 0.08f, 0.24f);

            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.07f, entityHorizontalFacing - 80 - ExtraMathMethods.randomFloat(10), ExtraMathMethods.randomFloat(180) - 90);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 2f + ExtraMathMethods.randomFloat() * 2, 30, 0.9f, 0.08f, 0.24f);
        }

        for (int i = 0; i < 30; i++) {
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.12f, entityHorizontalFacing + 80 + ExtraMathMethods.randomFloat(10), ExtraMathMethods.randomFloat(180) - 90);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 2f + ExtraMathMethods.randomFloat() * 2, 30, 0.3f, 0, 0);

            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.12f, entityHorizontalFacing - 80 - ExtraMathMethods.randomFloat(10), ExtraMathMethods.randomFloat(180) - 90);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 2f + ExtraMathMethods.randomFloat() * 2, 30, 0.3f, 0, 0);
        }
    }
}
