package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.entity.DfoGeneralEntitySpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientparticlehelper.ClientParticleHelperEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
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

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods.SetupCircleTiltBlocksZone;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker012 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_OUTRAGE_BREAK;

    public static final String[] BERSERKER012_PILLAR_TEXTURES = {"textures/entity/skilleff/berserker/outrage_break/pillar1/", "textures/entity/skilleff/berserker/outrage_break/pillar2/"};

    //5 ticks
    public static final String BERSERKER012_SLASH_DOWNWARD_PREPARATION_IN_MID_ANIMATION = "holding_up_sword_air_start2";
    public static final String BERSERKER012_SLASH_DOWNWARD_PREPARATION_LOOPING_IN_MID_ANIMATION = "holding_up_sword_air_channeling";

    public static final String BERSERKER012_SLASH_DOWNWARD_START_ANIMATION = "holding_up_sword_air_end2";
    public static final String BERSERKER012_SLASH_DOWNWARD_LOOPING_ANIMATION = "holding_up_sword_air_end2_looping";
    public static final String BERSERKER012_SLASH_DOWNWARD_END_ANIMATION = "holding_up_sword_air_end2_looping_end";

    public static final int BERSERKER012_IN_MID_AIR_MULTIPLE_HIT_ACTION_PERIOD = 10;
    public static final float BERSERKER012_IN_MID_AIR_MULTIPLE_HIT_AOE_RADIUS = 1.8f;

    public static final float BERSERKER012_PILLAR_AOE_RADIUS = 2f;

    public static final String BERSERKER012_GROUND_DAMAGE_ACTION = "BERSERKER012_GROUND_DAMAGE_ACTION";

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
        tempSkillData.setTranslatableTooltipGetter(Berserker012::getSkillDescription);

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

                    if (entity1 instanceof ServerPlayer sp1) {
                        condition = defaultSkillTargetCondition(sp1);
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER012_SLASH_DOWNWARD_PREPARATION_IN_MID_ANIMATION);
                        playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                        if (playerSkillLVL <= 0) { return; }
                        playerSkillLVL = playerSkillLVL - 1;

//                        SkillDataJson otherSkillData;
//                        int otherSkillLevel;
//                        //Power of Blood
//                        otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BerserkerSkillTreeInitialization.BERSERKER_POWER_OF_BLOOD);
//                        if (otherSkillLevel > 0) {
//                            otherSkillData = getID2SkillData().get(BerserkerSkillTreeInitialization.BERSERKER_POWER_OF_BLOOD);
//                            otherSkillLevel = otherSkillLevel - 1;
//
//                            float ThirstyDamageMultiplierBonus = ServerSkillMethods.getEntityStateAmount(entity1, Berserker002.Berserker002_MOUNTAINOUS_WHEEL_SKILL_DMG_BOOST) * 0.01f;
//                            ExtraDamageMultiplier = ExtraDamageMultiplier + ThirstyDamageMultiplierBonus;
//                        }
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }
                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    float TotalHPConsumptionPercentage = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();
                    int TotalPillarCount = skill1.getValueUsage_01().get(playerSkillLVL).intValue();

                    float currentEntityHealth = EntityMethods.getLivingEntityCurrentHP(entity1);
                    float MaxEntityHealth = EntityMethods.getLivingEntityMaxHP(entity1);
                    float hpShouldReduce = MaxEntityHealth * TotalHPConsumptionPercentage;
                    EntityMethods.setLivingEntityCurrentHP(entity1, MathMethods.max(0.01f, currentEntityHealth - hpShouldReduce));

                    ExtraDamageMultiplier = ExtraDamageMultiplier + TotalHPConsumptionPercentage * BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_5;

                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue() * ExtraAOEMultiplier;

                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);

                    float GroundDamageBase = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
                    float GroundDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    float finalBloodPillarDmg = (float) ( (GroundDamageMultiplier * getEntityAttackDamage(entity1) + GroundDamageBase) * skillDamageMultiplier);

                    float finalDist = skill1.getDistance().get(playerSkillLVL).floatValue();

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
                            for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, BERSERKER012_IN_MID_AIR_MULTIPLE_HIT_AOE_RADIUS, condition)) {
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

                                    DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg * DfoStatusGeneralMethods.BLEEDING_DEFAULT_DAMAGE_MULTIPLIER_2);
                                }
                            }

                            if (ElapsedTicks % BERSERKER012_IN_MID_AIR_MULTIPLE_HIT_ACTION_PERIOD == 0) {
                                damagedGroup.clear();
                            }
                        }

                        //leap over with downward strike
                        if (!isActionInterrupted) {
                            if (!contActioning) {
                                //play animation
                                if (entity2 instanceof ServerPlayer sp1) {
                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER012_SLASH_DOWNWARD_END_ANIMATION);
                                }

                                DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 4f);
                                //stop loop action
                                incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);

                                SBSTickEventMethods.setSkillActionDone(entity2, SkillID);

                                //ground damage action
                                String GroupDmgActionID = BERSERKER012_GROUND_DAMAGE_ACTION + getEntityTickCount(entity2);
                                int groundDmgActionPeriod = 1;
                                MethodAction groundDamageAction = Berserker012_DealerLandedAction(entity2, groundDmgActionPeriod, finalDmg, finalBloodPillarDmg, aoeRange, TotalPillarCount, condition.and(SBConditions.isNotTargetEntity(entity2)), GroupDmgActionID);
                                SBSTickEventMethods.setMethodActionTimer(entity2, GroupDmgActionID, groundDmgActionPeriod, groundDamageAction);
                            } else {
                                //play animation
                                if (entity2 instanceof ServerPlayer sp1) {
                                    if(currentDealerDeltaY <= 0) {
                                        isDamageActionActiveHolder.setBooleanVal(true);
                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER012_SLASH_DOWNWARD_LOOPING_ANIMATION);
                                    }
                                    else if ((ElapsedTicks - 8) % 500 == 0) {
                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER012_SLASH_DOWNWARD_PREPARATION_LOOPING_IN_MID_ANIMATION);
                                    } else if (ElapsedTicks == 4) {
                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER012_SLASH_DOWNWARD_START_ANIMATION);
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

//        tempSkillData.setClearSkillAction(Berserker012::Berserker012_Clean_Method);

        tempSkillData.setActiveSkillAction1(tempSkillAction);
    }

//    public static void testPrintVec(Vector3f vector3f) {
//        printInGameMsg("x: "+ vector3f.x()+", y: "+vector3f.y()+", z: "+vector3f.z());
//    }

    public static MethodAction Berserker012_DealerLandedAction(LivingEntity triggeredEntity, int groundDmgActionPeriod, float finalGroundStrikeDmg, float finalPillarDmg, float finalAOE, int pillar_count, Predicate<Entity> finalCondition, String ActionID) {
        IntegerValueHolder ElapsedTicksHolder = new IntegerValueHolder(0);
        IntegerValueHolder TotalPillarCount = new IntegerValueHolder(pillar_count);

        Vector3f centerPos = getEntityPos(triggeredEntity);
        float triggeredEntityHorizontalFacingDeg = getEntityHorizontalFacingDeg(triggeredEntity);
        float scale = 2f;
        float height = scale * 2;
        int startEruptionTicks = 20;

        SetupCircleTiltBlocksZone(getEntityPos(triggeredEntity).add(0f, -1f, 0f), getEntityLevel(triggeredEntity),
                finalAOE, startEruptionTicks + (int) (0.2f * pillar_count * groundDmgActionPeriod), 2, 10,
                true, false, true, false);

        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(triggeredEntity, finalAOE, finalCondition)) {
            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                DfoSwdSoundMethods.PlayBooDongHitSound(getEntityLevel(triggeredEntity), getEntityX(tempEntity), getEntityY(tempEntity), getEntityZ(tempEntity), 0.8f, 1.0f);

                float pushYStrength = EntityMethods.calculatePushEntityStrength(0.7f, triggeredEntity, tempLivingEntity);
                EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, 0, 0, pushYStrength, true);

                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(triggeredEntity, tempLivingEntity, finalGroundStrikeDmg, false);

                DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(triggeredEntity, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalPillarDmg * DfoStatusGeneralMethods.BLEEDING_DEFAULT_DAMAGE_MULTIPLIER);
            }
        }

        return (dealer) -> {
            int ElapsedTicks = ElapsedTicksHolder.getIntCounter();
            ElapsedTicksHolder.setIntCounter(ElapsedTicks + groundDmgActionPeriod);

            if (TotalPillarCount.getIntCounter() > 0) {
                if (ElapsedTicks > startEruptionTicks) {
                    for (int i = 0; i < 5; i++) {
                        TotalPillarCount.setIntCounter(TotalPillarCount.getIntCounter() - 1);
                        Vector3f randomLoc = Vector3fMethods.randomVecIn2DCircle(finalAOE, centerPos);
//                        testPrintVec(randomLoc);

                        Entity effEntity = GeoModelSpawnMethods.Generate2DAnimatedTexToNorthCustomEntityYRotOnly256(dealer, triggeredEntityHorizontalFacingDeg, true, scale, 0, height, 0, 7, false,
                                randomLoc.x(), randomLoc.y(), randomLoc.z(), randomLoc.x(), randomLoc.y(), randomLoc.z(), BERSERKER012_PILLAR_TEXTURES[ExtraMathMethods.randomInt(2)], DfoSwordmanSkillTreeConstants.NO_ANIMATION, false, 0, 0, 7, true, false, false,
                                false, null, 777, false);
                        moveEntityToXYZ(effEntity, randomLoc.x(), randomLoc.y(), randomLoc.z());
                        addEntityToWorld(getEntityLevel(dealer), effEntity);

                        DfoGeneralEntitySpawnMethods.SetupClientParticleHelperNoLoc(dealer, 0, 0, 7, randomLoc.x(), randomLoc.y(), randomLoc.z(), DfoSwordmanSkillTreeConstants.NO_ANIMATION, Berserker012_PILLAR_PARTICLE, null, 777, true);

                        //start damage
                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(effEntity, BERSERKER012_PILLAR_AOE_RADIUS, finalCondition)) {
                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                DfoSwdSoundMethods.PlayBooDongHitSound(getEntityLevel(dealer), getEntityX(tempEntity), getEntityY(tempEntity), getEntityZ(tempEntity), 0.8f, 1.0f);

                                float pushYStrength = EntityMethods.calculatePushEntityStrength(0.2f, dealer, tempLivingEntity);
                                EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, 0, 0, pushYStrength, false);

                                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(dealer, tempLivingEntity, finalPillarDmg, false);

                                DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(dealer, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalPillarDmg * DfoStatusGeneralMethods.BLEEDING_DEFAULT_DAMAGE_MULTIPLIER);
                            }
                        }
                    }
                }
            }
            else {
                SBSTickEventMethods.setSkillActionDone(dealer, ActionID);
            }
        };
    }

    //client
    public static final String Berserker012_PILLAR_PARTICLE = "Berserker012_Pillar";

    public static void Berserker012_ParticleRegistry(HashMap<String, MethodEntityAction> particleMap) {
        particleMap.put(Berserker012_PILLAR_PARTICLE, Berserker012::CreateBloodPillarParticleMethodReference);
    }

    public static void CreateBloodPillarParticleMethodReference(Entity entity) {
        ClientParticleHelperEntity clientParticleHelperEntity = (ClientParticleHelperEntity) entity;
        Vector3f center = getEntityPos(clientParticleHelperEntity);

        Vector3f randomDelta;
        for (int i = 0; i < 20; i++) {
            float randHorizontalFacing = ExtraMathMethods.randomFloat(360f);
            float randVerticalFacing = -ExtraMathMethods.randomFloat(40f) - 35f;
            float randomDist = ExtraMathMethods.randomFloat() * 4f;
            int lifetime = (int) (randomDist * 5);

            randomDelta = Vector3fMethods.PolarProjectionFromRotation(randomDist * 0.1f, randHorizontalFacing, randVerticalFacing);
            ParticleMethods.GenerateWhiteParticle(center.x(), center.y(), center.z(),
                    randomDelta.x(), randomDelta.y() + 0.1f, randomDelta.z(),
                    0.33f, 1.5f + ExtraMathMethods.randomFloat(), lifetime, 0.4f, 0f, 0f);

            ParticleMethods.GenerateWhiteParticle(center.x(), center.y(), center.z(),
                    ExtraMathMethods.randomFloat(0.05f) - 0.025f, randomDelta.y() + 0.12f, ExtraMathMethods.randomFloat(0.05f) - 0.025f,
                    0.33f, 1.5f + ExtraMathMethods.randomFloat() * 2, lifetime, 0.86f, 0.08f, 0.24f);
        }
    }
}
