package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.getLivingEntityIsInAction;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isLivingEntityActionInterrupted;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster039 {
    public static String SkillID = BlademasterSkillTreeInitialization.SWORDMAN_NORMAL_ATTACK;

    //Server
    public static HashMap<LivingEntity, String> SWORDMAN_NORMAL_ATTACK_HashMapCurrentAttackSet = new HashMap<>();
    public static HashMap<String, MethodAction> SWORDMAN_NORMAL_ATTACK_HashMapCurrentAttackSetAction = new HashMap<>();
    public static HashMap<LivingEntity, Integer> SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes = new HashMap<>();

    public final static String[] SWORDMAN_ATTACK_ANIMATION = {"consecutive_slash5", "sword_act_shortsword_slash1", "upper_slash2", "consecutive_slash5"};
    public final static float[] SWORDMAN_ATTACK_SLASH_EFF_Z_ROT = {90f, 270f, 135f, 270f};

    public static void clearAction(LivingEntity entity) {
        SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.remove(entity);
    }

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        double skillDamageMultiplier = getCPlayerSkillDamage();

        Double FirstHiDamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double FirstHitConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        Float FirstHitFinalDmg = (float) ( (FirstHiDamageMultiplier * getEntityAttackDamage(getClientPlayer()) + FirstHitConstantDamage) * skillDamageMultiplier);

        Double SecondHiDamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double SecondHitConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        Float SecondHitFinalDmg = (float) ( (SecondHiDamageMultiplier * getEntityAttackDamage(getClientPlayer()) + SecondHitConstantDamage) * skillDamageMultiplier);

        Double ThirdHiDamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ThirdHitConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        Float ThirdHitFinalDmg = (float) ( (ThirdHiDamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ThirdHitConstantDamage) * skillDamageMultiplier);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (FirstHiDamageMultiplier.floatValue() * 100)),
                String.format("%s", FirstHitConstantDamage),
                String.format("%s", FirstHitFinalDmg),
                String.format("%d%%", (int) (SecondHiDamageMultiplier.floatValue() * 100)),
                String.format("%s", SecondHitConstantDamage),
                String.format("%s", SecondHitFinalDmg),
                String.format("%d%%", (int) (ThirdHiDamageMultiplier.floatValue() * 100)),
                String.format("%s", ThirdHitConstantDamage),
                String.format("%s", ThirdHitFinalDmg)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster039::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);

        if (isClientSide()) {
            return;
        }

        //====================================
        //setup skill action
        MethodAction tempSkillAction =
                (entity1) -> {
                    String AnimationSetName = SWORDMAN_NORMAL_ATTACK_HashMapCurrentAttackSet.getOrDefault(entity1, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
                    MethodAction action = SWORDMAN_NORMAL_ATTACK_HashMapCurrentAttackSetAction.get(AnimationSetName);
                    if (action == null) {
                        NormalAttackActionSetup(entity1);
                    }
                    else {
                        action.executeAction(entity1);
                    }
                };

        tempSkillData.setActiveSkillAction1(tempSkillAction);
    }

    public static void NormalAttackActionSetup(LivingEntity entity0) {
        //identify weapon type
        int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity0);
        if (weaponType == DfoSkillTreeItemMethods.IS_HOLDING_NOTHING) {
            return;
        }

        String[] weaponAnimArr = SWORDMAN_ATTACK_ANIMATION;
        int actualAnimationShouldPlayIndex = weaponAnimArr.length - 1;

        int playerSkillLVL = 0;
        float aoeRange;

        //ASSUME SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes never be null
        if (!SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.containsKey(entity0)) {
            SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.put(entity0, 0);
        }

        SkillDataJson skill1 = getID2SkillData().get(SkillID);
        if (entity0 instanceof ServerPlayer sp1) {
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID) - 1;
            }
        }

        aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();

        float FirstHitDamageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
        float FirstHitDamageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();

        float SecondHitDamageBase = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
        float SecondHitDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue();

        float ThirdHitDamageBase = skill1.getDamageList_03().get(playerSkillLVL).floatValue();
        float ThirdHitDamageMultiplier = skill1.getDamageMultiplierList_03().get(playerSkillLVL).floatValue();

        float[] DamageConstList = {FirstHitDamageBase, SecondHitDamageBase, ThirdHitDamageBase};
        float[] DamageMultiplierList = {FirstHitDamageMultiplier, SecondHitDamageMultiplier, ThirdHitDamageMultiplier};

        //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
        //start Actioning

        Predicate<Entity> condition;
        float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity0);
        float finalAspd = entityAnimationArr[0];
        float finalAnimationSpeed = entityAnimationArr[1];

        //play animation and update condition
        int triggeredTimes = SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.get(entity0);
        //reset trigger times
        if (triggeredTimes - actualAnimationShouldPlayIndex >= actualAnimationShouldPlayIndex) {
            SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.put(entity0, actualAnimationShouldPlayIndex);
        }
        if (entity0 instanceof ServerPlayer sp1) {
            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, weaponAnimArr[SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.get(entity0) % actualAnimationShouldPlayIndex], finalAspd);

            condition = defaultSkillTargetCondition(sp1);
        }
        else {
            condition = defaultSkillTargetCondition();
        }

        int actionPeriod = 1;
        float[] effEntityZRotArr = SWORDMAN_ATTACK_SLASH_EFF_Z_ROT;
        MethodAction action = NormalAttackPrepAction(entity0, SkillID, actionPeriod, weaponType, finalAnimationSpeed, DamageConstList[triggeredTimes % actualAnimationShouldPlayIndex], DamageMultiplierList[triggeredTimes % actualAnimationShouldPlayIndex], aoeRange, effEntityZRotArr, condition);
        SBSTickEventMethods.setMethodActionTimerWithCleanAction(entity0, SkillID, actionPeriod, action, Blademaster039::clearAction, true);
    }

    //============
    public static MethodAction NormalAttackPrepAction(LivingEntity entity1, String SkillID, int actionPeriod, int weaponType, float finalAnimationSpeed, float damageBase, float damageMultiplier, float aoeRange, float[] effEntityZRotArr, Predicate<Entity> condition) {
        int animTicks = (int) (8 * finalAnimationSpeed);
        int delayClearTicks = (int) (4 * finalAnimationSpeed) + 1;
        int damage1tick = (int) (3 * finalAnimationSpeed);
        float finalDmg = (damageMultiplier * (float) getEntityAttackDamage(entity1) + damageBase);

        int triggeredTimes = SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.get(entity1) % 3;
        int slashEntitySpawnTick1 = (int) (2 * finalAnimationSpeed);

        IntegerValueHolder ElapsedTicksHolder = new IntegerValueHolder(0);

        MethodAction action =
                (entity2) ->  {
                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                    int currentTick = ElapsedTicksHolder.getIntCounter() + actionPeriod;
                    ElapsedTicksHolder.setIntCounter(currentTick);
                    if (!isActionInterrupted && getLivingEntityIsInAction(entity2) <= 0 && currentTick < animTicks + delayClearTicks) {
                        float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);

                        //play wieldingSound
                        if (currentTick == slashEntitySpawnTick1) {
                            DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.7f, ExtraMathMethods.randomFloat()*0.2f+0.9f);
                        }

                        if (currentTick <= animTicks) {
                            setEntityBodyHorizontalFacingDeg(entity2, triggeredEntityFacingAngle);
                            //lightsabre
                            //start damage
                            if (currentTick == damage1tick) {
                                NormalAttackDamageAction(entity2, weaponType, triggeredEntityFacingAngle, aoeRange + 1f, finalDmg, condition);
                                SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.put(entity2, SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.get(entity2) + 1);
                            }

                            //spawn slash entity
                            if (triggeredTimes == 0) {
                                if (currentTick == slashEntitySpawnTick1) {
                                    GeoModelSpawnMethods.GenerateRGBSlashBoundToEntity(entity2, triggeredEntityFacingAngle, effEntityZRotArr[0], 1f,255, 255, 102, 0, 1f, 0, true);
                                }
                            }
                            else if (triggeredTimes == 1) {
                                if (currentTick == slashEntitySpawnTick1) {
                                    GeoModelSpawnMethods.GenerateRGBSlashBoundToEntity(entity2, triggeredEntityFacingAngle, effEntityZRotArr[1],1f, 255, 255, 102, 0, 1f, 0, true);
                                }
                            }
                            else {
                                if (currentTick == slashEntitySpawnTick1) {
                                    GeoModelSpawnMethods.GenerateRGBSlashBoundToEntity(entity2, triggeredEntityFacingAngle, effEntityZRotArr[2], 1f,255, 255, 102, 0, 1f, 0, true);
                                }
                            }
                        }

                        //start moving around
                        float newX = Vector3fMethods.PolarProjectionFromXHorizon(0.3f, triggeredEntityFacingAngle);
                        float newZ = Vector3fMethods.PolarProjectionFromZHorizon(0.3f, triggeredEntityFacingAngle);
                        Vector3f tempTriggeredEntityDeltaVec = getEntityDeltaMovement(entity2);
                        float newDeltaX = (newX > 0) ? MathMethods.min(tempTriggeredEntityDeltaVec.x(), newX) : MathMethods.max(tempTriggeredEntityDeltaVec.x(), newX);
                        float newDeltaZ = (newZ > 0) ? MathMethods.min(tempTriggeredEntityDeltaVec.x(), newZ) : MathMethods.max(tempTriggeredEntityDeltaVec.z(), newZ);

                        setEntitySPDeltaMovement(entity2, newDeltaX, MathMethods.min(tempTriggeredEntityDeltaVec.y(), 0f), newDeltaZ, false);
                    }
                    else {
                        if (!isActionInterrupted) {
                            //reset delta
                            setEntitySPDeltaMovement(entity2, 0, 0, 0, false);
                        }

                        SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                    }
                };

        return action;
    }

    private static void NormalAttackDamageAction(LivingEntity entity2, int weaponType, float triggeredEntityFacingAngle, float aoeRange, float finalDmg, Predicate<Entity> condition) {
        boolean playHitSound = false;
        //Sector damage cond
        Vector3f triggeredEntityPos = getEntityPos(entity2);
        Predicate<Entity> SectorDmgCond = (entity) -> {
//                                    Vector3f effectedEntityPos = getEntityPos(entity);
//                                    float AngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(effectedEntityPos, triggeredEntityPos);

            Vector3f tempVec = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, -0.5f, triggeredEntityFacingAngle);

//                                    float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontal(getEntityPos(entity), triggeredEntityPos));
            //flip when range > 90?
            float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(getEntityPos(entity), tempVec));
            float range = 105f; //210 deg

            if (!Vector3fMethods.IsAngleDegBetweenAB(sectorDeg, MathMethods.RoundAngle(triggeredEntityFacingAngle + range), MathMethods.RoundAngle(triggeredEntityFacingAngle - range))) {
                return false;
            }

            return DistanceBetweenEntities(entity, entity2) <= aoeRange;
        };
        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, aoeRange, condition.and(SectorDmgCond))) {
            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                if (!playHitSound) {
                    playHitSound = true;
                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                }

                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
                //stay in air
                float additionalDeltaY = (isEntityFarFromGround(tempEntity, 0.5f)) ? 0f : 0.15f;
                float pushXZStrength = 0f;

                float pushYStrength = calculatePushEntityStrengthTargetOnly(MathMethods.max(getEntityDeltaY(tempLivingEntity), additionalDeltaY), tempLivingEntity);
                pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityFacingAngle, pushXZStrength, pushYStrength, false);

                GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 1f, 4, 4, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, GeoAnimationAndTextureName.EFF_HIT_GENERAL, true, true);
            }
        }
    }

    public static HashMap<LivingEntity, Integer> SwordmanGetNormalAttackTriggerTimeMap() { return SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes; }
    public static HashMap<LivingEntity, String> SwordmanGetNormalAttackAnimationSetMap() { return SWORDMAN_NORMAL_ATTACK_HashMapCurrentAttackSet; }
    public static void RegisterAttackAnimationSet(String AnimationSetName, MethodAction AnimationSetAction) {
        SWORDMAN_NORMAL_ATTACK_HashMapCurrentAttackSetAction.put(AnimationSetName, AnimationSetAction);
    }
}
