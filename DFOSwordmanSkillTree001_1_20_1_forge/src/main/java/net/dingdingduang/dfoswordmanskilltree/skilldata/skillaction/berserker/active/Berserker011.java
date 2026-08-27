package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.entity.DfoGeneralEntitySpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.BerserkerModelSpawnMethods;
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
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.Blademaster025;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
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
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues.getSLivingEntityState;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker011 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_BLOODY_TWISTER;
    public static final String ChannelingActionID = SkillID + "_ChannelingActionID";
    public static final String BERSERKER011_DISCHARGE_ACTION = "BERSERKER011_DISCHARGE_ACTION";

    public static final String BERSERKER011_BLOOD_TWISTER_UPPER_SLASH_TEXTURE = "textures/entity/skilleff/berserker/blood_twister/upward_slash/";

    public static final String BERSERKER011_BLOOD_TWISTER_UPPER_SLASH1 = "sword_act_katana_slash4";

    public static final int BERSERKER011_INITIAL_PREPARATION_TIME = 20;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }

        double skillDamageMultiplier = getCPlayerSkillDamage();

        Double HPRestorationPercentage = skillData.getValueMultiplierList_01().get(correctSkillLevel);

        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        Double TotalHPConsumptionPercentage = skillData.getValueMultiplierList_01().get(correctSkillLevel);

        Float finalDmg = (float) ( ( (DamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_5 * TotalHPConsumptionPercentage) * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        Double AreaOfEffects = skillData.getEffectArea().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (ConstantDamage.floatValue() * 100)),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (TotalHPConsumptionPercentage.floatValue() * 100)),
                String.format("%d%%", (int) (HPRestorationPercentage.floatValue() * 100)),
                String.format("%s", AreaOfEffects)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);
        tempSkillData.setTranslatableTooltipGetter(Berserker011::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);

        //while channeling
        tempSkillData.setChannelingSkillAction1(Berserker011::Berserker011_ChannelingAction);
        //final action
//        tempSkillData.setActiveSkillAction1(Berserker011::Berserker011_ActivatePassive);
        tempSkillData.setActiveSkillAction1(DfoSwordmanSkillMethods::DoNothing);
    }

    public static MethodEntityAction Berserker011_RemoveChannelingEffEntity(final LivingEntity channelingEntity) {
        return  (effEntity) -> {
            if (getEntityTickCount(effEntity) >= BERSERKER011_INITIAL_PREPARATION_TIME && !ServerSkillMethods.isLivingEntityInChannelingAction(channelingEntity) || isLivingEntityActionInterrupted(channelingEntity)) {
                EntityMethods.RemoveEntity(effEntity);
            }
        };
    }

    public static void Berserker011_ChannelingAction(LivingEntity entity1) {
        incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);
        int effPeriod = 5;
//        int findEntityActionPeriod = 2;
        int actionPeriod = 1;
        //TODO: play attracting sound
//        DfoSwdSoundMethods.PlayChannelingStartSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);

        Predicate<Entity> condition;
        int playerSkillLVL = 0;
        SkillDataJson skill1 = getID2SkillData().get(SkillID);

        float ExtraDamageMultiplier = 0;
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
        float TotalHPConsumptionPercentage = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();
        ExtraDamageMultiplier = ExtraDamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_5 * TotalHPConsumptionPercentage;

        int maxChannelingTime = (int) (skill1.getChannelingTime().get(playerSkillLVL).floatValue() * 20);

        float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();

        float preTriggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity1);
//        float newX = Vector3fMethods.PolarProjectionFromXHorizon(1f, preTriggeredEntityHorizontalFacingAngle);
//        float newZ = Vector3fMethods.PolarProjectionFromZHorizon(1f, preTriggeredEntityHorizontalFacingAngle);

        DfoGeneralEntitySpawnMethods.SetupClientParticleHelperBoundToEntity(entity1, preTriggeredEntityHorizontalFacingAngle, 0f, maxChannelingTime, 0, 1.6f, 0, DfoSwordmanSkillTreeConstants.NO_ANIMATION, Berserker011_Attract, Berserker011_RemoveChannelingEffEntity(entity1), 2, true);

        float effScale = DfoSwordmanSkillMethods.getEntityScaleByAOE(aoeRange);
        BerserkerModelSpawnMethods.GenerateBloodTwisterEff(entity1, preTriggeredEntityHorizontalFacingAngle, effScale, maxChannelingTime, 0, 1.6f, 0, Berserker011_RemoveChannelingEffEntity(entity1), 2, true);

        float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);
        float damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
        float damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

        float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);

        HashSet<LivingEntity> attractedEntities = new HashSet<>();
        HashSet<LivingEntity> entitiesTBR = new HashSet<>();
        IntegerValueHolder TotalChannelingTicksHolder = new IntegerValueHolder(maxChannelingTime);
        IntegerValueHolder tempIntegerTicker = new IntegerValueHolder(0);
        int dmgPeriod = 20;

        MethodAction action = (entity2) -> {
            int currentPassedTicks = tempIntegerTicker.getIntCounter();
            tempIntegerTicker.setIntCounter(currentPassedTicks + actionPeriod);

            if (currentPassedTicks > 2) {
                float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity2);
                Vector3f triggeredEntityPos = getEntityPos(entity2);
                Vector3f triggeredEntityPolarPos = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, 1.4f, triggeredEntityHorizontalFacingAngle);

                if ((currentPassedTicks - 5) % 500 == 0 && entity2 instanceof ServerPlayer sp1) {
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_LOOPING);
                }


                boolean isEffPeriod = false;
                if (currentPassedTicks % effPeriod == 0) {
                    isEffPeriod = true;

                    TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 20, 10, Constants.OP_ADDITION, false, SkillID, null, null);
                }

                boolean isDmgPeriod = currentPassedTicks % dmgPeriod == 0;

                for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, aoeRange, condition.and(DfoSwordmanSkillMethods.getSectorCondition(entity2, 60f, aoeRange)))) {
                    if (tempEntity instanceof LivingEntity tempLivingEntity) {
                        attractedEntities.add(tempLivingEntity);
                    }
                }

                for (LivingEntity tempLivingEntity: attractedEntities) {
                    if (SBConditions.isEntityAlive(tempLivingEntity)) {
                        Vector3f tempLivingEntityPos = getEntityPos(tempLivingEntity);
                        float horizontalDeg = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(triggeredEntityPolarPos, tempLivingEntityPos);
                        float verticalDeg = MathMethods.AngleToDegrees(Vector3fMethods.AngleFromLocationToLocationVerticalInRad(triggeredEntityPolarPos, tempLivingEntityPos));

                        if (Vector3fMethods.Vec3fDistanceTo(triggeredEntityPolarPos, tempLivingEntityPos) > 0.2f) {
                            Vector3f deltaVec = Vector3fMethods.PolarProjectionFromRotation(-0.2f, horizontalDeg, verticalDeg);
                            setEntitySPDeltaMovement(tempLivingEntity, deltaVec, false);
                        }
                        else {
                            tpEntityToPos(tempLivingEntity, triggeredEntityPolarPos);
                        }

                        if (isEffPeriod) {
                            GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(tempLivingEntity, 255, 255, 255, 1f, 0, 1.4f, 0, 7, Berserker010.BERSERKER010_FRENZY_CHARGING, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                    DfoSwordmanSkillTreeConstants.NO_ANIMATION, 7, 0, 7, false, false, true, null, 777, true);
                        }

                        if (isDmgPeriod) {
                            DfoSwdSoundMethods.PlayBooDongHitSound(getEntityLevel(entity2), tempLivingEntityPos.x(), tempLivingEntityPos.y(), tempLivingEntityPos.z(), 0.3f, 2f);

                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                            DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg);
                        }
                    }
                    else {
                        entitiesTBR.add(tempLivingEntity);
                    }
                }
                if (!entitiesTBR.isEmpty()) {
                    for (LivingEntity entityTBR: entitiesTBR) {
                        attractedEntities.remove(entityTBR);
                        entitiesTBR.clear();
                    }
                }


                boolean contActioning = false;
                HashMap<String, Integer> ServerLivingEntityState = getSLivingEntityState().get(entity2);
                if (ServerLivingEntityState != null) {
                    contActioning = ServerLivingEntityState.get(Constants.IS_CHANNELING) == 1;
                }

                if ((!contActioning && currentPassedTicks >= BERSERKER011_INITIAL_PREPARATION_TIME) || currentPassedTicks >= TotalChannelingTicksHolder.getIntCounter()) {
                    SBSTickEventMethods.setSkillActionDone(entity2, ChannelingActionID);

                    //TODO generate eff entity and sound
                    Berserker011_BloodTwisterDischarge(entity2);
                }
                else {
                    if (currentPassedTicks >= BERSERKER011_INITIAL_PREPARATION_TIME && isEntityChannellingInterrupted(entity2)) {
                        SBSTickEventMethods.setSkillActionDone(entity2, ChannelingActionID);
                        incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                        if (entity2 instanceof ServerPlayer sp1) {
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_END);
                        }
                    }
                }
            }
        };

        //start loop action
        SBSTickEventMethods.setMethodActionTimer(entity1, ChannelingActionID, actionPeriod, action);
    }

    public static void Berserker011_BloodTwisterDischarge(LivingEntity dealer) {
        TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(dealer, 20, 10, Constants.OP_ADDITION, false, SkillID, null, null);

        SkillDataJson skill1 = getID2SkillData().get(SkillID);
        Predicate<Entity> condition;
        int playerSkillLVL = 0;

        float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(dealer);
        float entityAnimationSpeedMultiplier = entityAnimationArr[0];
        float damageTickSpeedMultiplier = entityAnimationArr[1];

        float ExtraDamageMultiplier = 0;
        if (dealer instanceof ServerPlayer sp1) {
            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BERSERKER011_BLOOD_TWISTER_UPPER_SLASH1, entityAnimationSpeedMultiplier);
            condition = defaultSkillTargetCondition(sp1);

            playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY);
            if (playerSkillLVL <= 0) { return; }
            playerSkillLVL = playerSkillLVL - 1;
        }
        else {
            condition = defaultSkillTargetCondition();
        }
        float TotalHPConsumptionPercentage = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();
        ExtraDamageMultiplier = ExtraDamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_5 * TotalHPConsumptionPercentage;

        float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();

        Vector3f triggeredEntityPos = getEntityPos(dealer);
        float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(dealer);
//        float triggeredEntityVerticalFacingAngle = getEntityVerticalFacingDeg(dealer);
        float newX = Vector3fMethods.PolarProjectionFromXHorizon(0.5f, triggeredEntityHorizontalFacingAngle);
        float newZ = Vector3fMethods.PolarProjectionFromZHorizon(0.5f, triggeredEntityHorizontalFacingAngle);

        DfoGeneralEntitySpawnMethods.SetupClientParticleHelperNoLoc(dealer, triggeredEntityHorizontalFacingAngle, aoeRange, 4, triggeredEntityPos.x() + newX, triggeredEntityPos.y(), triggeredEntityPos.z() + newZ, DfoSwordmanSkillTreeConstants.NO_ANIMATION, Berserker011_Discharge, null, 777, true);

//        GeoModelSpawnMethods.Generate2DHitEff(target, 270, 1f, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_FRAMES, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_FRAMES, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.6f, ExtraMathMethods.randomFloat(0.4f) - 0.2f, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_ARR[ExtraMathMethods.randomInt(BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_ARR.length)], true, true);
//        GeoModelSpawnMethods.Generate2DHitEff(target, ExtraMathMethods.randomFloat(360f), 1f, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_FRAMES, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_FRAMES, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_ARR[ExtraMathMethods.randomInt(BerserkerSkillTreeInitialization.BERSERKER_HIT_EFF_ARR.length)], true, true);

        float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(dealer);
        float damageBase = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
        float damageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

        float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(dealer) + damageBase) * skillDamageMultiplier);

//        HashSet<LivingEntity> damagedGroup = new HashSet<>();
        IntegerValueHolder ElapsedTicksHolder = new IntegerValueHolder(0);
        int actionPeriod = 1;
        int damage1tick = (int) (3 * damageTickSpeedMultiplier);
        String ActionID = BERSERKER011_DISCHARGE_ACTION + EntityMethods.getEntityHashID(dealer);

        MethodAction dmgProcessingAction = (dealer2) -> {
            int updatedTick = ElapsedTicksHolder.getIntCounter();
            ElapsedTicksHolder.setIntCounter(updatedTick + actionPeriod);

            if (updatedTick >= damage1tick) {
                float triggeredEntityHorizontalFacing = getEntityHorizontalFacingDeg(dealer2);
                Vector3f triggeredEntityOriginalPos = getEntityPos(dealer2);
                for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(dealer2, aoeRange, condition.and(DfoSwordmanSkillMethods.getSectorCondition(dealer2, 60f, aoeRange)))) {
                    if (tempEntity instanceof LivingEntity tempLivingEntity) {
                        Vector3f tempLivingEntityPos = getEntityPos(tempLivingEntity);
                        DfoSwdSoundMethods.PlayBooDongHitSound(getEntityLevel(dealer2), tempLivingEntityPos.x(), tempLivingEntityPos.y(), tempLivingEntityPos.z(), 0.8f, 1f);

                        float pushXZStrength = EntityMethods.calculatePushEntityStrength(0.25f, dealer2, tempLivingEntity);
                        EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityHorizontalFacing, pushXZStrength, 0.7f, true);

                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(dealer2, tempLivingEntity, finalDmg, false);
                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(dealer2, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg * DfoStatusGeneralMethods.BLEEDING_DEFAULT_DAMAGE_MULTIPLIER_2);
                    }
                }
                float scale = DfoSwordmanSkillMethods.getEntityScaleByAOE(aoeRange);
                Vector3f targetCenterLoc = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityOriginalPos, aoeRange * 0.5f, triggeredEntityHorizontalFacingAngle);

                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256(dealer2, triggeredEntityHorizontalFacing, scale, targetCenterLoc.x(), targetCenterLoc.y() + 2 * scale, targetCenterLoc.z(), 12,
                        BERSERKER011_BLOOD_TWISTER_UPPER_SLASH_TEXTURE, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 6, 6, 6, true, true, true, false,  null, 777,true);

                incrementEntityStateByAmountX(dealer2, Constants.IS_IN_ACTION, -1);
                SBSTickEventMethods.setSkillActionDone(dealer2, ActionID);
            }
            else {
                if (isLivingEntityActionInterrupted(dealer2)) {
                    incrementEntityStateByAmountX(dealer2, Constants.IS_IN_ACTION, -1);
                    SBSTickEventMethods.setSkillActionDone(dealer2, ActionID);
                }
            }
        };

        SBSTickEventMethods.setMethodActionTimer(dealer, ActionID, actionPeriod, dmgProcessingAction);
    }

    //client
    public static final String Berserker011_Attract = "Berserker011_Attract";

    public static final String Berserker011_Discharge = "Berserker011_Discharge";

    public static void Berserker011_ParticleRegistry(HashMap<String, MethodEntityAction> particleMap) {
        particleMap.put(Berserker011_Discharge, Berserker011::CreateBloodTwisterGripDischargeMethodReference);
        particleMap.put(Berserker011_Attract, Berserker011::CreateBloodTwisterGripAttractMethodReference);
    }

//    float minDist = 0.5f;
//    float maxDist = 1.0f;
//    float velocity = 0.1f;
    public static void CreateBloodTwisterGripAttractMethodReference(Entity entity) {
        ClientParticleHelperEntity clientParticleHelperEntity = (ClientParticleHelperEntity) entity;
        Entity owner = clientParticleHelperEntity.getOwner();
        if (owner == null) { return; }
        Vector3f center = getEntityPos(clientParticleHelperEntity);
        float currentOwnerHorizontalFacingAngle = getEntityHorizontalFacingDeg(owner);
//        Vector3f ownerPos = getEntityPos(owner);

        Vector3f randPos, randomDelta;
        for (int i = 0; i < 60; i++) {
            float randHorizontalFacing = currentOwnerHorizontalFacingAngle + ExtraMathMethods.randomFloat(60f) - 30f;
            float randVerticalFacing = ExtraMathMethods.randomFloat(60f) - 30f;
            float randomDist = ExtraMathMethods.randomFloat() * 5 + 1;
            int lifetime = (int) (randomDist + 5);
            randPos = Vector3fMethods.PolarProjectionFromRotation(center, randomDist, randHorizontalFacing, randVerticalFacing);
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(randomDist * -0.1f, randHorizontalFacing, randVerticalFacing);

            ParticleMethods.GenerateWhiteParticle(randPos.x(), randPos.y(), randPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 1.5f + 2 * ExtraMathMethods.randomFloat(), lifetime, 0.5f, 0f, 0f);
        }
    }

    public static void CreateBloodTwisterGripDischargeMethodReference(Entity entity) {
//        printInGameMsg("1: "+getEntityLevel(entity).isClientSide);
        ClientParticleHelperEntity clientParticleHelperEntity = (ClientParticleHelperEntity) entity;
        Vector3f fromPos = getEntityPos(clientParticleHelperEntity);
        float entityHorizontalFacing = getEntityHorizontalFacingDeg(clientParticleHelperEntity);
        float aoeRange = getEntityVerticalFacingDeg(clientParticleHelperEntity);

//        printInGameMsg("x: "+fromPos.x()+", y: "+fromPos.y+", z: "+fromPos.z);

        Vector3f randomDelta;

        for (float dist = 0; dist <= aoeRange; dist = dist + 0.5f) {
            Vector3f newPos = Vector3fMethods.PolarProjectionFromVecHorizon(fromPos, dist, entityHorizontalFacing);
            for (int i = 0; i < 100; i++) {
                randomDelta = Vector3fMethods.PolarProjectionFromRotation(0.1f + ExtraMathMethods.randomFloat() * 0.2f, entityHorizontalFacing + ExtraMathMethods.randomFloat(30) - 15, ExtraMathMethods.randomFloat(45) + 15);
                ParticleMethods.GenerateWhiteParticle(newPos.x(), newPos.y(), newPos.z(),
                        randomDelta.x(), randomDelta.y(), randomDelta.z(),
                        0.33f, 1.5f + ExtraMathMethods.randomFloat() * 2, 30, 1f, 0, 0);
            }
        }

        for (int i = 0; i < 200; i++) {
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(0.1f + ExtraMathMethods.randomFloat() * 0.2f, entityHorizontalFacing + ExtraMathMethods.randomFloat(60) - 30, ExtraMathMethods.randomFloat(60) - 30);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 1.5f + ExtraMathMethods.randomFloat() * 2, 30, 0.86f, 0.08f, 0.24f);
        }
        for (int i = 0; i < 30; i++) {
//            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.07f, entityHorizontalFacing + 90, ExtraMathMethods.randomFloat(60) - 30);
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.07f, entityHorizontalFacing + 80 + ExtraMathMethods.randomFloat(10), ExtraMathMethods.randomFloat(180) - 90);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 2f + ExtraMathMethods.randomFloat() * 2, 30, 0.9f, 0.08f, 0.24f);

//            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.07f, entityHorizontalFacing - 90, ExtraMathMethods.randomFloat(60) - 30);
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.07f, entityHorizontalFacing - 80 - ExtraMathMethods.randomFloat(10), ExtraMathMethods.randomFloat(180) - 90);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 2f + ExtraMathMethods.randomFloat() * 2, 30, 0.9f, 0.08f, 0.24f);
        }
        for (int i = 0; i < 15; i++) {
//            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.1f, entityHorizontalFacing + 90, ExtraMathMethods.randomFloat(60) - 30);
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.12f, entityHorizontalFacing + 80 + ExtraMathMethods.randomFloat(10), ExtraMathMethods.randomFloat(180) - 90);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 2f + ExtraMathMethods.randomFloat() * 2, 30, 0.3f, 0, 0);

//            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.1f, entityHorizontalFacing - 90, ExtraMathMethods.randomFloat(60) - 30);
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(ExtraMathMethods.randomFloat() * 0.12f, entityHorizontalFacing - 80 - ExtraMathMethods.randomFloat(10), ExtraMathMethods.randomFloat(180) - 90);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 2f + ExtraMathMethods.randomFloat() * 2, 30, 0.3f, 0, 0);
        }
//        ParticleMethods.GenerateHitParticle(fromPos, 100);
    }
}
