package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_block_eff.SwdTempestBlockEffEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword.BMTempestSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone.SwdFadingCloneCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.FloatValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.TypeValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues;
import net.dingdingduang.somebasicskills.networking.NetworkingFetchMsgMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.dingdingduang.somebasicskills.util.MethodConfigHelper;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import org.joml.Vector3f;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods.SetupCircleTiltBlocksZone;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getCPlayerConfig2Settings;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.*;
import static net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor.applyTimedBeneficialStatusInvincibilityToEntity;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster025 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_ULTIMATE_SWORD_ART_TEMPEST;
    public static final String BLADEMASTER_0_25_DAMAGE_ACTION = "BLADEMASTER_0_25_DAMAGE_ACTION";
    public static final String BLADEMASTER_0_25_FINAL_DAMAGE_ACTION = "BLADEMASTER_0_25_FINAL_DAMAGE_ACTION";
    public static final String BLADEMASTER_0_25_FINAL_RESET_ENTITY_ACTION = "BLADEMASTER_0_25_FINAL_RESET_ENTITY_ACTION";

    public static final String BLADEMASTER025_SWORD_TEXTURE_PATH = "textures/entity/skilleff/blademaster/tempest/sword/";
    public static final String BLADEMASTER025_SWORD_SLASH_TEXTURE = "textures/entity/skilleff/blademaster/tempest/slash/";
    public static final String BLADEMASTER025_UPWARD_SLASH_TEXTURE = "textures/entity/skilleff/blademaster/tempest/finish_slash/";
    public static final String BLADEMASTER025_TEMPEST_ORBIT_EFF = "textures/entity/skilleff/blademaster/tempest/block_eff/tempest_block_effect.png";

    public static final String BLADEMASTER025_TEMPEST_UPWARD_SLASH_ANIMATION = "tempest_upward_slash";

    public static final String BLADEMASTER025_TEMPEST_ORBIT_EFF_ANIMATION = "random_rotate";
    public static final String BLADEMASTER025_TEMPEST_AIR_SWORD_ANIMATION = "floating_sword";
    public static final String BLADEMASTER025_TEMPEST_GROUND_SWORD_ANIMATION = "ground_sword0";

    public static final float BLADEMASTER025_TEMPEST_DIST_SHOULD_TRAVEL_EACH_TICK = 0.2f;
    public static final float BLADEMASTER025_TEMPEST_FLASH_EFF_REMAINING_DIST = 9f * BLADEMASTER025_TEMPEST_DIST_SHOULD_TRAVEL_EACH_TICK;
    public static final float BLADEMASTER025_TEMPEST_DEGREE_EACH_TIME_SHOULD_INCREMENT = 15;
    public static final float BLADEMASTER025_TEMPEST_MAX_AIR_HEIGHT = 6;
    public static final int BLADEMASTER025_CLONE_MOVING_TICKS = 4;
    public static final float BLADEMASTER025_CLONE_ANIMATION_MODIFIER = 2.5f;
    public static final float BLADEMASTER025_SLASH_EFF_EXTRA_ANGLE = MathMethods.AngleToDegrees((float) (Math.PI / 24));
    public static final float BLADEMASTER025_SLASH_EFF_EXTRA_VALUE_FOR_ADJACENT = (float) Math.sin(Math.PI / 24);
//    public static final int BLADEMASTER025_CLONE_MOVING_TICKS = 3;
//    public static final float BLADEMASTER025_CLONE_ANIMATION_MODIFIER = 3.334f;
    public static final int BLADEMASTER025_CLONE_ALPHA = 96;

    public static final int BLADEMASTER025_TOTAL_TICK_THRUST_ANIMATION_ENDS = 5;
    public static final float BLADEMASTER025_THRUST_LERP_TIME = 1f / BLADEMASTER025_TOTAL_TICK_THRUST_ANIMATION_ENDS;
    public static final float BLADEMASTER025_HEIGHT_DISTANCE_SHOULD_DESCEND_EACH_TIME = BLADEMASTER025_TEMPEST_MAX_AIR_HEIGHT * BLADEMASTER025_THRUST_LERP_TIME;

    public static final int[] BLADEMASTER025_DAMAGE_INTERVAL_TICK = {2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 8, 8, 8, 10, 10, 15, 20, 30};
    public static final String[] BLADEMASTER025_TEMPEST_SWORD_ITEMSTACK_COMPOUND_ID = {"wooden_sword", "stone_sword", "golden_sword", "diamond_sword", "netherite_sword"};
    //client
    public static final String BLADEMASTER025_TEMPEST_ORBIT_EFF_CLIENT_MOVING_ACTION = "BLADEMASTER025_TEMPEST_ORBIT_EFF_CLIENT_MOVING_ACTION";
    public static final String BLADEMASTER025_TEMPEST_SWORD_EFF_CLIENT_MOVING_ACTION = "BLADEMASTER025_TEMPEST_SWORD_EFF_CLIENT_MOVING_ACTION";
    public static final String BLADEMASTER025_TEMPEST_GROUND_SWORD_EFF_CLIENT_MOVING_ACTION = "BLADEMASTER025_TEMPEST_GROUND_SWORD_EFF_CLIENT_MOVING_ACTION";

//    public static final String BLADEMASTER025_TEMPEST_INIT_CLONE_CLIENT_MOVING_ACTION = "BLADEMASTER025_TEMPEST_INIT_CLONE_CLIENT_MOVING_ACTION";
    public static final String BLADEMASTER025_TEMPEST_DUPLICATE_CLONE_CLIENT_ACTION = "BLADEMASTER025_TEMPEST_DUPLICATE_CLONE_CLIENT_ACTION";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);
        //Last hit
        Double UpwardSlashDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double UpwardSlashConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);
        Float UpwardSlashFinalDmg = (float) ( (UpwardSlashDamageMultiplier * getEntityAttackDamage(getClientPlayer()) + UpwardSlashConstantDamage) * skillDamageMultiplier);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (UpwardSlashDamageMultiplier.floatValue() * 100)),
                String.format("%s", UpwardSlashConstantDamage),
                String.format("%s", UpwardSlashFinalDmg)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster025::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);

        if (isClientSide()) {
            return;
        }

        //init
        //DoNothing();


        //====================================
        //setup skill action
        MethodAction tempSkillAction =
                (entity1) -> {
                    int playerSkillLVL = 0;
                    float aoeRange;
                    float damageBase, upwardSlashDamageBase;
                    float damageMultiplier, upwardSlashDamageMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);
                    boolean shouldMoveAlongWithAnimator = false;

                    Predicate<Entity> condition;
                    SkillDataJson skill1 = getID2SkillData().get(SkillID);
                    if (entity1 instanceof ServerPlayer sp1) {
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION);
                        }
                        condition = defaultSkillTargetCondition(sp1);
                        shouldMoveAlongWithAnimator = getSPlayerShouldMoveAlongWithAnimator(sp1);
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }
                    //send action packet if this is true
                    if (shouldMoveAlongWithAnimator) {
                        incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, 1);
                        setEntityHasGravity(entity1, false);
                        if (entity1 instanceof ServerPlayer sp1) {
                            DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardKidnapFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                        }
                    }

                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();
                    upwardSlashDamageBase = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
                    upwardSlashDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue();

                    //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    //start Actioning

                    //play animation and choose condition
//                    Predicate<Entity> additionalCond = (entity -> !entity.equals(entity1) );
//                    condition = condition.and(additionalCond);

//                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);
//                    int dmgTicks = 3;
                    float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
                    float finalUpwardSlashDmg = (float) ((upwardSlashDamageMultiplier * getEntityAttackDamage(entity1) + upwardSlashDamageBase) * skillDamageMultiplier);
                    int actionPeriod = 1;
                    Predicate<Entity> finalCondition = condition;
                    IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);

                    float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity1);
                    Vector3f triggeredEntityOriginalPos = getEntityPos(entity1);

                    Vector3f targetCenterLoc = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityOriginalPos, aoeRange + 2f, triggeredEntityHorizontalFacingAngle);
                    Vector3f tempBlockEffPolarLoc;
                    HashMap<Integer, Entity> TempestActionIndex2AirObj = new HashMap<>();
                    HashMap<Integer, Entity> TempestActionIndex2GroundObj = new HashMap<>();

                    for (int i = 0; i < 12; i++) {
                        float tempEntityHorizontalFacingAngle = (triggeredEntityHorizontalFacingAngle + i * 30) % 360;
                        tempBlockEffPolarLoc = Vector3fMethods.PolarProjectionFromVecHorizon(targetCenterLoc, aoeRange, tempEntityHorizontalFacingAngle );

                        Entity AirObj = GeoModelSpawnMethods.GenerateBMSwdTempestBlockEff(entity1, tempEntityHorizontalFacingAngle, 6f, aoeRange, tempBlockEffPolarLoc.x(), tempBlockEffPolarLoc.y() + BLADEMASTER025_TEMPEST_MAX_AIR_HEIGHT, tempBlockEffPolarLoc.z(),
                                900, targetCenterLoc.x(), targetCenterLoc.y() + BLADEMASTER025_TEMPEST_MAX_AIR_HEIGHT, targetCenterLoc.z(), BLADEMASTER025_TEMPEST_ORBIT_EFF, BLADEMASTER025_TEMPEST_ORBIT_EFF_ANIMATION, BLADEMASTER025_TEMPEST_ORBIT_EFF_CLIENT_MOVING_ACTION, null, 777, true);

                        TempestActionIndex2AirObj.put(i, AirObj);
                    }

                    SwdFadingCloneCustomEntity animator = GeoModelSpawnMethods.GenerateCustomFadingCloneEntity(entity1, triggeredEntityHorizontalFacingAngle, 0, 1, 255, 255, 224, BLADEMASTER025_CLONE_ALPHA, 0, 0, 0, 900,
                            GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, false, 4, 8, 8, true, true, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_90_DEG_ANIMATION, 1f, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                            DfoSwordmanSkillTreeConstants.NO_ANIMATION, true, false, true);

                    int triggeredEntityTickID = getEntityTickCount(entity1);
                    BooleanValueHolder isntConvergedToCenterLoc = new BooleanValueHolder(true);
                    BooleanValueHolder isntGroundEntityGroupSetup = new BooleanValueHolder(true);
                    BooleanValueHolder isActionDone = new BooleanValueHolder(false);
                    BooleanValueHolder isFlashEntitySpawned = new BooleanValueHolder(false);
                    boolean finalShouldMoveAlongWithAnimator = shouldMoveAlongWithAnimator;
                    FloatValueHolder totalDistTraveled = new FloatValueHolder(0);

//                    IntegerValueHolder previousTempestSwordObjActionIndex = new IntegerValueHolder(0);

//                    float TempestCircleDiameter = aoeRange * 2;

                    MethodAction action =
                            (entity2) ->  {
                                int updatedTick = ElapsedTicks.getIntCounter();
                                if (finalShouldMoveAlongWithAnimator) {
                                    applyTimedBeneficialStatusInvincibilityToEntity(entity2, 80, 0.1, Constants.OP_ADDITION, false, SkillID, null, null);
                                }

//                                if (!isActionDone.getBooleanVal() && updatedTick < DfoSwordmanSkillTreeConstants.MAX_TICKS_FOR_ACTION && !isLivingEntityActionInterrupted(entity2)) {
                                if (!isActionDone.getBooleanVal() && updatedTick < DfoSwordmanSkillTreeConstants.MAX_TICKS_FOR_ACTION) {
                                    //spawn model
                                    float totalDistHasTraveled = totalDistTraveled.getFloatVal();

                                    if (isntConvergedToCenterLoc.getBooleanVal() && totalDistHasTraveled <= aoeRange + BLADEMASTER025_TEMPEST_DIST_SHOULD_TRAVEL_EACH_TICK) {
                                        boolean setFlashEntitySpawnTrue = false;
                                        for (Entity tempestMovingEntity: TempestActionIndex2AirObj.values()) {
                                            float distFromCenter = aoeRange - totalDistHasTraveled;
                                            float tempAngle = BLADEMASTER025_TEMPEST_DEGREE_EACH_TIME_SHOULD_INCREMENT + Vector3fMethods.AngleFromLocationToLocationHorizontal(targetCenterLoc, getEntityPos(tempestMovingEntity));
                                            float nextMovingPosX = Vector3fMethods.PolarProjectionFromXHorizonWithOrigin(targetCenterLoc.x(), distFromCenter, tempAngle);
                                            float originPosY = getEntityY(tempestMovingEntity);
                                            float nextMovingPosZ = Vector3fMethods.PolarProjectionFromZHorizonWithOrigin(targetCenterLoc.z(), distFromCenter, tempAngle);

                                            moveEntityToXYZ(tempestMovingEntity, nextMovingPosX, originPosY, nextMovingPosZ);

                                            if (!isFlashEntitySpawned.getBooleanVal() && aoeRange - totalDistHasTraveled <= BLADEMASTER025_TEMPEST_FLASH_EFF_REMAINING_DIST) {
                                                DfoSwdSoundMethods.PlayBlademaster025TempestSoundWithIndex(getEntityLevel(entity2), 2, nextMovingPosX, originPosY, nextMovingPosZ, 0.8f, 1f);
                                                setFlashEntitySpawnTrue = true;
                                                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(tempestMovingEntity, 255, 255, 255, 1f, 0, 0, 0, 11, Blademaster033.BLADEMASTER033_TEXTURE_CHARGING_COMPLETE, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                                        DfoSwordmanSkillTreeConstants.NO_ANIMATION,11, 0, 11, false, false, true, null, 777, true);
                                            }
                                        }
                                        if (setFlashEntitySpawnTrue) {
                                            isFlashEntitySpawned.setBooleanVal(true);
                                        }
                                    }
                                    else {
                                        if (isntConvergedToCenterLoc.getBooleanVal()) {
                                            isntConvergedToCenterLoc.setBooleanVal(false);

                                            HashMap<Integer, Entity> tempTempestActionIndex2AirObj = new HashMap<>();

                                            for (Map.Entry<Integer, Entity> entry: TempestActionIndex2AirObj.entrySet()) {
                                                int actionIndex = entry.getKey();
                                                float tempEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entry.getValue());

                                                Entity AirObj = GeoModelSpawnMethods.GenerateBMTempestSwordEntity(entity2, tempEntityHorizontalFacingAngle, 1f, aoeRange, targetCenterLoc.x(), targetCenterLoc.y() + BLADEMASTER025_TEMPEST_MAX_AIR_HEIGHT, targetCenterLoc.z(),
                                                         900, targetCenterLoc.x(), targetCenterLoc.y(), targetCenterLoc.z(), BLADEMASTER025_SWORD_TEXTURE_PATH + ExtraMathMethods.randomInt(5) + ".png", BLADEMASTER025_TEMPEST_AIR_SWORD_ANIMATION + ExtraMathMethods.randomInt(3), BLADEMASTER025_TEMPEST_SWORD_EFF_CLIENT_MOVING_ACTION, null, 777, true);

                                                tempTempestActionIndex2AirObj.put(actionIndex, AirObj);
                                                RemoveEntity(entry.getValue());
                                            }
                                            TempestActionIndex2AirObj.clear();
                                            TempestActionIndex2AirObj.putAll(tempTempestActionIndex2AirObj);

                                            totalDistTraveled.setFloatVal(0);
                                            totalDistHasTraveled = 0;
                                        }
                                        else {
                                            if (totalDistHasTraveled <= aoeRange + BLADEMASTER025_TEMPEST_DIST_SHOULD_TRAVEL_EACH_TICK) {
//                                            if (totalDistHasTraveled <= aoeRange) {
                                                for (Entity tempestMovingEntity: TempestActionIndex2AirObj.values()) {
                                                    float tempEntityHorizontalFacingAngle = BLADEMASTER025_TEMPEST_DEGREE_EACH_TIME_SHOULD_INCREMENT + getEntityHorizontalFacingDeg(tempestMovingEntity);
                                                    setEntityHorizontalFacingDeg(tempestMovingEntity, tempEntityHorizontalFacingAngle);

                                                    float nextMovingPosX = Vector3fMethods.PolarProjectionFromXHorizonWithOrigin(targetCenterLoc.x(), totalDistHasTraveled, tempEntityHorizontalFacingAngle);
                                                    float originPosY = getEntityY(tempestMovingEntity);
                                                    float nextMovingPosZ = Vector3fMethods.PolarProjectionFromZHorizonWithOrigin(targetCenterLoc.z(), totalDistHasTraveled, tempEntityHorizontalFacingAngle);

                                                    moveEntityToXYZ(tempestMovingEntity, nextMovingPosX, originPosY, nextMovingPosZ);

                                                    if (updatedTick % 8 == 0) {
                                                        DfoSwdSoundMethods.PlayBlademaster025TempestSoundWithIndex(getEntityLevel(entity2), 3, nextMovingPosX, originPosY, nextMovingPosZ, 0.5f, 1f);
                                                    }
                                                }

                                                //TEST
//                                                for (int actionIndex: TempestActionIndex2AirObj.keySet()) {
//                                                    BMTempestSwordEntity tempestMovingEntity = (BMTempestSwordEntity) TempestActionIndex2AirObj.get(actionIndex);
//                                                    float tempEntityHorizontalFacingAngle = BLADEMASTER025_TEMPEST_DEGREE_EACH_TIME_SHOULD_INCREMENT + getEntityHorizontalFacingDeg(tempestMovingEntity);
//                                                    setEntityHorizontalFacingDeg(tempestMovingEntity, tempEntityHorizontalFacingAngle);
//
//                                                    float nextMovingPosX = Vector3fMethods.PolarProjectionFromXHorizonWithOrigin(targetCenterLoc.x(), totalDistHasTraveled, tempEntityHorizontalFacingAngle);
//                                                    float originPosY = getEntityY(tempestMovingEntity);
//                                                    float nextMovingPosZ = Vector3fMethods.PolarProjectionFromZHorizonWithOrigin(targetCenterLoc.z(), totalDistHasTraveled, tempEntityHorizontalFacingAngle);
//
//                                                    moveEntityToXYZ(tempestMovingEntity, nextMovingPosX, originPosY, nextMovingPosZ);
//
//                                                    printInGameMsg("server: "+nextMovingPosX+", remaingintTick: "+tempestMovingEntity.getLifetime() + ", ID: " + getEntityHashID(tempestMovingEntity));
//                                                }
//
//                                                printInGameMsg("====================");
                                            }
                                            else {
                                                if (isntGroundEntityGroupSetup.getBooleanVal()) {
                                                    isntGroundEntityGroupSetup.setBooleanVal(false);
                                                    isActionDone.setBooleanVal(true);

                                                    Blademaster025_ServerGroundSetupAction(animator, entity2, triggeredEntityHorizontalFacingAngle, targetCenterLoc, finalDmg, finalUpwardSlashDmg, aoeRange, finalCondition, actionPeriod, TempestActionIndex2AirObj, TempestActionIndex2GroundObj, finalShouldMoveAlongWithAnimator);
                                                }
                                            }
                                        }
                                    }

                                    totalDistTraveled.setFloatVal(totalDistHasTraveled + BLADEMASTER025_TEMPEST_DIST_SHOULD_TRAVEL_EACH_TICK);
                                }
                                else {
                                    SBSTickEventMethods.setSkillActionDone(entity2, SkillID + triggeredEntityTickID);
                                }

                                //update val
                                ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
                            };
                    //Actioning ends
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

                    //start loop actioning with fixed period ticks
                    SBSTickEventMethods.setMethodActionTimer(entity1, SkillID + triggeredEntityTickID, actionPeriod, action);
                };

        tempSkillData.setActiveSkillAction1(tempSkillAction);
    }



    public static void Blademaster025_ServerGroundSetupAction(SwdFadingCloneCustomEntity previousAnimator, LivingEntity entity2, float initialTriggeredEntityHorizontalFacingAngle, Vector3f targetCenterLoc, float finalDmg, float finalUpwardSlashDmg, float aoeRange, Predicate<Entity> finalCondition, int actionPeriod, HashMap<Integer, Entity> TempestActionIndex2AirObj, HashMap<Integer, Entity> TempestActionIndex2GroundObj, boolean shouldMoveAlongWithAnimator) {
        Vector3f tempAirObjPos;
        Integer actionIndex;
//        Vector3f currentTriggeredEntityPos = getEntityPos(entity2);
        IntegerValueHolder closestIntegerActionIndex = new IntegerValueHolder(0);
        TypeValueHolder<Entity> previousTempestSwordObj = new TypeValueHolder<>(null);
        Vector3f animatorOriginLoc = getEntityPos(previousAnimator);

        float minDist = Float.MAX_VALUE;
        float tempFloat;
        for (Map.Entry<Integer, Entity> entry: TempestActionIndex2AirObj.entrySet()) {
            actionIndex = entry.getKey();
            tempAirObjPos = getEntityPos(entry.getValue());

            if ( (tempFloat = Vector3fMethods.DistanceBetweenVec3fHorizontal(animatorOriginLoc, tempAirObjPos)) < minDist) {
                minDist = tempFloat;
                closestIntegerActionIndex.setIntCounter(actionIndex);
            }

            Entity GroundObj = GeoModelSpawnMethods.GenerateBMTempestSwordEntity(entity2, getEntityHorizontalFacingDeg(TempestActionIndex2AirObj.get(actionIndex)), 1f, aoeRange, tempAirObjPos.x(), tempAirObjPos.y(), tempAirObjPos.z(),
                    900, targetCenterLoc.x(), targetCenterLoc.y(), targetCenterLoc.z(), BLADEMASTER025_SWORD_TEXTURE_PATH + ExtraMathMethods.randomInt(5) + ".png", BLADEMASTER025_TEMPEST_GROUND_SWORD_ANIMATION, BLADEMASTER025_TEMPEST_GROUND_SWORD_EFF_CLIENT_MOVING_ACTION, null, 777, true);

            TempestActionIndex2GroundObj.put(actionIndex, GroundObj);
        }

        previousTempestSwordObj.setAnyTypeValue(TempestActionIndex2AirObj.get(closestIntegerActionIndex.getIntCounter()));
//        moveEntityToPos(entity2, closestAirObjPos);
//        tpEntityToPos(entity2, closestAirObjPos);


        //init
        float slashScale = aoeRange * 0.75f;
        float midHeight = BLADEMASTER025_TEMPEST_MAX_AIR_HEIGHT * 0.5f;
        BooleanValueHolder isInitAnimationActive = new BooleanValueHolder(true);
        IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);

        BooleanValueHolder isWieldingAirSword = new BooleanValueHolder(true);
        BooleanValueHolder isActionDone = new BooleanValueHolder(false);
        IntegerValueHolder slashStartTickHolder = new IntegerValueHolder(BLADEMASTER025_DAMAGE_INTERVAL_TICK[BLADEMASTER025_DAMAGE_INTERVAL_TICK.length - 1]);
        IntegerValueHolder slashTickCounterHolder = new IntegerValueHolder(0);
        int triggeredEntityTickID = getEntityTickCount(entity2);
        int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity2);
        float extraEffDist = aoeRange * BLADEMASTER025_SLASH_EFF_EXTRA_VALUE_FOR_ADJACENT;

        Vector3f closestSwordVec = getEntityPos(previousTempestSwordObj.getAnyTypeValue());
        Vector3f animatorPos = getEntityPos(previousAnimator);
        TypeValueHolder<Entity> animatorHolder = new TypeValueHolder<>(null);
        TypeValueHolder<String> previousItemStackID = new TypeValueHolder<>(DfoSkillTreeItemMethods.getMainHandNBTID(entity2));

        Entity tempAnimator = GeoModelSpawnMethods.GenerateCustomFadingCloneEntityWithTargetLoc(entity2, getEntityHorizontalFacingDeg(previousAnimator), 0, 1, animatorPos.x(), animatorPos.y(), animatorPos.z(), closestSwordVec.x(), closestSwordVec.y(), closestSwordVec.z(), BLADEMASTER025_TOTAL_TICK_THRUST_ANIMATION_ENDS,
                255, 255, 224, BLADEMASTER025_CLONE_ALPHA, slashStartTickHolder.getIntCounter(),
                GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, 1, 1, slashStartTickHolder.getIntCounter() - 2, true, true, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 1f, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                DfoSwordmanSkillTreeConstants.NO_ANIMATION, true, false, true);
        RemoveEntity(previousAnimator);

        animatorHolder.setAnyTypeValue(tempAnimator);

        float aoeYMid = targetCenterLoc.y() + midHeight;
        float aoeYMaxRange = targetCenterLoc.y() + BLADEMASTER025_TEMPEST_MAX_AIR_HEIGHT;

        AABB aoeRectangle = new AABB(targetCenterLoc.x() - aoeRange, targetCenterLoc.y(), targetCenterLoc.z() - aoeRange,
                                     targetCenterLoc.x() + aoeRange, aoeYMaxRange, targetCenterLoc.z() + aoeRange
                );
        //
        MethodAction action = (entity3) -> {
            int updatedTick = ElapsedTicks.getIntCounter();
            if (shouldMoveAlongWithAnimator) {
                applyTimedBeneficialStatusInvincibilityToEntity(entity3, 80, 0.1, Constants.OP_ADDITION, false, SkillID, null, null);
            }

            if (updatedTick > DfoSwordmanSkillTreeConstants.MAX_TICKS_FOR_ACTION) { SBSTickEventMethods.setSkillActionDone(entity3, BLADEMASTER_0_25_DAMAGE_ACTION + triggeredEntityTickID); }

            Entity animator = animatorHolder.getAnyTypeValue();

            if (isInitAnimationActive.getBooleanVal()) {
                if (updatedTick <= BLADEMASTER025_TOTAL_TICK_THRUST_ANIMATION_ENDS) {
                    for (Entity TempestGroundMovingObj : TempestActionIndex2GroundObj.values()) {
                        if (!isEntityFarFromGround(TempestGroundMovingObj, BLADEMASTER025_HEIGHT_DISTANCE_SHOULD_DESCEND_EACH_TIME - 1f)) {
                            moveEntityToXYZ(TempestGroundMovingObj, getEntityX(TempestGroundMovingObj), getEntityY(TempestGroundMovingObj) - BLADEMASTER025_HEIGHT_DISTANCE_SHOULD_DESCEND_EACH_TIME, getEntityZ(TempestGroundMovingObj));
                        }
                    }
                    float distPercent = updatedTick * BLADEMASTER025_THRUST_LERP_TIME;
                    float newX = animatorPos.x() + (closestSwordVec.x() - animatorPos.x()) * distPercent;
                    float newY = animatorPos.y() + (closestSwordVec.y() - animatorPos.y()) * distPercent;
                    float newZ = animatorPos.z() + (closestSwordVec.z() - animatorPos.z()) * distPercent;
                    moveEntityToXYZ(animator, newX, newY, newZ);

                    //TODO: setup timer for client player smooth moving action?
                    if (shouldMoveAlongWithAnimator) {
                        tpEntityToPos(entity3, newX, newY, newZ);
                    }
                }
                else {
                    for (Entity TempestGroundMovingObj : TempestActionIndex2GroundObj.values()) {
                        float groundObjX = getEntityX(TempestGroundMovingObj);
                        float groundObjY = getEntityY(TempestGroundMovingObj);
                        float groundObjZ = getEntityZ(TempestGroundMovingObj);
                        SetupCircleTiltBlocksZone(new Vector3f(groundObjX, groundObjY - 1f, groundObjZ), getEntityLevel(entity3),
                                aoeRange * 0.25f, 70, 0.6f, 5,
                                true, true, false, true);
                        DfoSwdSoundMethods.PlayBlademaster025TempestSoundWithIndex(getEntityLevel(entity3), 4, groundObjX, groundObjY, groundObjZ, 0.8f, 1f);
                    }
                    Blademaster025_ServerNormalSlashAction(entity3, entity3, ExtraMathMethods.randomInt(2), targetCenterLoc, aoeRectangle, aoeYMid, weaponType, finalDmg, finalCondition);

                    isInitAnimationActive.setBooleanVal(false);
                }
            }
            else {
                int slashTick = slashTickCounterHolder.getIntCounter();
                int slashStartTick = slashStartTickHolder.getIntCounter();

                if (slashTick >= slashStartTick) {
                    //update min slash tick 4 -> 0.2s
                    if (slashStartTick > BLADEMASTER025_DAMAGE_INTERVAL_TICK[0]) {
                        slashStartTickHolder.setIntCounter(Math.max(BLADEMASTER025_DAMAGE_INTERVAL_TICK[0], BLADEMASTER025_DAMAGE_INTERVAL_TICK[TempestActionIndex2AirObj.size() + TempestActionIndex2GroundObj.size() - 1]));
                    }
                    slashTickCounterHolder.setIntCounter(0);

                    int triggeredEntityCurrentActionIndex = closestIntegerActionIndex.getIntCounter();

                    //start damage
                    if (isWieldingAirSword.getBooleanVal()) {
                        isWieldingAirSword.setBooleanVal(false);
                        int nextGroundObjActionIndex = (triggeredEntityCurrentActionIndex + 6) % 12;
                        Entity GroundObj = TempestActionIndex2GroundObj.remove(nextGroundObjActionIndex);

                        Vector3f GroundObjVec = getEntityPos(GroundObj);

                        closestIntegerActionIndex.setIntCounter((triggeredEntityCurrentActionIndex + 1) % 12);

                        BMTempestSwordEntity previousEntity = (BMTempestSwordEntity) previousTempestSwordObj.getAnyTypeValue();

                        if (previousEntity != null) {
                            Vector3f PreviousObjVec = getEntityPos(previousEntity);
                            float HorizontalAngleBetween = Vector3fMethods.AngleFromLocationToLocationHorizontal(PreviousObjVec, GroundObjVec);
                            Blademaster025_ServerNormalSlashAction(entity3, previousEntity, ExtraMathMethods.randomInt(2), targetCenterLoc, aoeRectangle, aoeYMid, weaponType, finalDmg, finalCondition);

                            String currentEntityTexture = previousEntity.getTextureName();
                            int currentItemStackId = currentEntityTexture.charAt(currentEntityTexture.length() - 5) - '0';
                            String currentItemStackStrID = BLADEMASTER025_TEMPEST_SWORD_ITEMSTACK_COMPOUND_ID[currentItemStackId];

                            Entity tempEntity = GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256(entity3, HorizontalAngleBetween, slashScale, targetCenterLoc.x(), targetCenterLoc.y() + midHeight, targetCenterLoc.z(), 12,
                                    BLADEMASTER025_SWORD_SLASH_TEXTURE, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_XP_20, 5, 5, 3, true, true, true, true, null, 777, true);
                            //Extra
                            Vector3f extraEffPos;
                            extraEffPos = Vector3fMethods.PolarProjectionFromVecHorizon(targetCenterLoc, extraEffDist, HorizontalAngleBetween + 90);
                            GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256(entity3, HorizontalAngleBetween - BLADEMASTER025_SLASH_EFF_EXTRA_ANGLE, slashScale, extraEffPos.x(), extraEffPos.y() + midHeight, extraEffPos.z(), 9,
                                    BLADEMASTER025_SWORD_SLASH_TEXTURE, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_XP_20, 5, 5, 3, true, true, true, true, null, 777, true);
                            extraEffPos = Vector3fMethods.PolarProjectionFromVecHorizon(targetCenterLoc, extraEffDist, HorizontalAngleBetween - 90);
                            GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256(entity3, HorizontalAngleBetween + BLADEMASTER025_SLASH_EFF_EXTRA_ANGLE, slashScale, extraEffPos.x(), extraEffPos.y() + midHeight, extraEffPos.z(), 9,
                                    BLADEMASTER025_SWORD_SLASH_TEXTURE, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_XP_20, 5, 5, 3, true, true, true, true, null, 777, true);

//                            RemoveEntity(animator);
//                            float VerticalAngleBtn = MathMethods.AngleToDegrees((float) Vector3fMethods.AngleFromLocationToLocationVertical(PreviousObjVec, GroundObjVec));
                            float VerticalAngleBtn = Vector3fMethods.AngleFromLocationToLocationVertical(PreviousObjVec, GroundObjVec) * MathMethods.RAD_TO_DEG;
//                            printInGameMsg("air v: "+VerticalAngleBtn);

                            String randomSlashAnimationName = Blademaster024.BLADEMASTER024_ILLUSION_SWORD_DANCE_ATTACKS[ExtraMathMethods.randomInt(Blademaster024.BLADEMASTER024_ILLUSION_SWORD_DANCE_ATTACKS.length)];
                            float cloneAnimationMultiplier = 1f / (1f * slashStartTickHolder.getIntCounter() / BLADEMASTER025_DAMAGE_INTERVAL_TICK[BLADEMASTER025_DAMAGE_INTERVAL_TICK.length - 1]) * 0.5f;
                            animator = GeoModelSpawnMethods.GenerateCustomFadingCloneEntityWithTargetLocWithItemStackStrID(tempEntity, HorizontalAngleBetween, VerticalAngleBtn, 1, PreviousObjVec.x(), PreviousObjVec.y(), PreviousObjVec.z(), GroundObjVec.x(), GroundObjVec.y(), GroundObjVec.z(), BLADEMASTER025_CLONE_MOVING_TICKS,
                                    255, 255, 224, BLADEMASTER025_CLONE_ALPHA, 65,
                                    GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, previousItemStackID.getAnyTypeValue(), currentItemStackStrID,4, 8, 8, true, true, randomSlashAnimationName, cloneAnimationMultiplier, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                    DfoSwordmanSkillTreeConstants.NO_ANIMATION, true, true, true);
                            animatorHolder.setAnyTypeValue(animator);

                            for (int i = ExtraMathMethods.randomInt(1, 3); i < BLADEMASTER025_CLONE_MOVING_TICKS; i = i + ExtraMathMethods.randomInt(1, 3)) {
                                GeoModelSpawnMethods.GenerateCustomFadingCloneEntityWithItemStackStrID(animator, HorizontalAngleBetween, VerticalAngleBtn, 1, 255, 255, 224, 0, 0, 0, 0, 80 + i,
                                        GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, previousItemStackID.getAnyTypeValue(), currentItemStackStrID, true, 9, 1, 50, true, true, randomSlashAnimationName, cloneAnimationMultiplier, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                        BLADEMASTER025_TEMPEST_DUPLICATE_CLONE_CLIENT_ACTION, true, true, true);
                            }

                            previousItemStackID.setAnyTypeValue(currentItemStackStrID);

                            if (shouldMoveAlongWithAnimator) {
                                tpEntityToPos(entity3, GroundObjVec);
                                if (entity3 instanceof ServerPlayer sp1) {
//                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, randomSlashAnimationName, BLADEMASTER025_CLONE_ANIMATION_MODIFIER);
                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, randomSlashAnimationName, 1f);
                                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerHorizontalFacing(sp1, initialTriggeredEntityHorizontalFacingAngle);
                                }
                            }
                            RemoveEntity(previousEntity);
                        }

                        previousTempestSwordObj.setAnyTypeValue(GroundObj);
                    }
                    else {
                        isWieldingAirSword.setBooleanVal(true);
                        Entity AirObj = TempestActionIndex2AirObj.remove(triggeredEntityCurrentActionIndex);

                        Vector3f AirObjVec = getEntityPos(AirObj);

                        BMTempestSwordEntity previousEntity = (BMTempestSwordEntity) previousTempestSwordObj.getAnyTypeValue();

                        if (previousEntity != null) {
                            Vector3f PreviousObjVec = getEntityPos(previousEntity);
                            float HorizontalAngleBetween = Vector3fMethods.AngleFromLocationToLocationHorizontal(PreviousObjVec, AirObjVec);
                            Blademaster025_ServerNormalSlashAction(entity3, previousEntity, ExtraMathMethods.randomInt(2), targetCenterLoc, aoeRectangle, aoeYMid, weaponType, finalDmg, finalCondition);

                            String currentEntityTexture = previousEntity.getTextureName();
                            int currentItemStackId = currentEntityTexture.charAt(currentEntityTexture.length() - 5) - '0';
                            String currentItemStackStrID = BLADEMASTER025_TEMPEST_SWORD_ITEMSTACK_COMPOUND_ID[currentItemStackId];

                            Entity tempEntity = GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256(entity3, HorizontalAngleBetween, slashScale, targetCenterLoc.x(), targetCenterLoc.y() + midHeight, targetCenterLoc.z(), 12,
                                    BLADEMASTER025_SWORD_SLASH_TEXTURE, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_XP_340, 5, 5, 3, true, true, true, true, null, 777, true);
                            //extra eff
                            Vector3f extraEffPos;
                            extraEffPos = Vector3fMethods.PolarProjectionFromVecHorizon(targetCenterLoc, extraEffDist, HorizontalAngleBetween + 90);
                            GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256(entity3, HorizontalAngleBetween - BLADEMASTER025_SLASH_EFF_EXTRA_ANGLE, slashScale, extraEffPos.x(), extraEffPos.y() + midHeight, extraEffPos.z(), 9,
                                    BLADEMASTER025_SWORD_SLASH_TEXTURE, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_XP_340, 5, 5, 3, true, true, true, true, null, 777, true);
                            extraEffPos = Vector3fMethods.PolarProjectionFromVecHorizon(targetCenterLoc, extraEffDist, HorizontalAngleBetween - 90);
                            GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256(entity3, HorizontalAngleBetween + BLADEMASTER025_SLASH_EFF_EXTRA_ANGLE, slashScale, extraEffPos.x(), extraEffPos.y() + midHeight, extraEffPos.z(), 9,
                                    BLADEMASTER025_SWORD_SLASH_TEXTURE, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_XP_340, 5, 5, 3, true, true, true, true, null, 777, true);

//                            RemoveEntity(animator);
//                            float VerticalAngleBtn = MathMethods.AngleToDegrees((float) Vector3fMethods.AngleFromLocationToLocationVertical(PreviousObjVec, AirObjVec));
                            float VerticalAngleBtn = Vector3fMethods.AngleFromLocationToLocationVertical(PreviousObjVec, AirObjVec) * MathMethods.RAD_TO_DEG;
//                            printInGameMsg("ground v: "+VerticalAngleBtn);

                            String randomSlashAnimationName = Blademaster024.BLADEMASTER024_ILLUSION_SWORD_DANCE_ATTACKS[ExtraMathMethods.randomInt(Blademaster024.BLADEMASTER024_ILLUSION_SWORD_DANCE_ATTACKS.length)];

                            animator = GeoModelSpawnMethods.GenerateCustomFadingCloneEntityWithTargetLocWithItemStackStrID(tempEntity, HorizontalAngleBetween, VerticalAngleBtn, 1, PreviousObjVec.x(), PreviousObjVec.y(), PreviousObjVec.z(), AirObjVec.x(), AirObjVec.y(), AirObjVec.z(), BLADEMASTER025_CLONE_MOVING_TICKS,
                                    255, 255, 224, BLADEMASTER025_CLONE_ALPHA, 65,
                                    GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, previousItemStackID.getAnyTypeValue(), currentItemStackStrID,4, 8, 8, true, true, randomSlashAnimationName, BLADEMASTER025_CLONE_ANIMATION_MODIFIER, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                    DfoSwordmanSkillTreeConstants.NO_ANIMATION, true, true, true);
                            animatorHolder.setAnyTypeValue(animator);

                            for (int i = ExtraMathMethods.randomInt(1, 3); i < BLADEMASTER025_CLONE_MOVING_TICKS; i = i + ExtraMathMethods.randomInt(1, 3)) {
                                GeoModelSpawnMethods.GenerateCustomFadingCloneEntityWithItemStackStrID(animator, HorizontalAngleBetween, VerticalAngleBtn, 1, 255, 255, 224, 0, 0, 0, 0, 60 + i,
                                        GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, previousItemStackID.getAnyTypeValue(), currentItemStackStrID, true, 9, 1, 50, true, true, randomSlashAnimationName, BLADEMASTER025_CLONE_ANIMATION_MODIFIER, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                        BLADEMASTER025_TEMPEST_DUPLICATE_CLONE_CLIENT_ACTION, true, true, true);
                            }

                            previousItemStackID.setAnyTypeValue(currentItemStackStrID);


                            if (shouldMoveAlongWithAnimator) {
                                tpEntityToPos(entity3, AirObjVec);
                                if (entity3 instanceof ServerPlayer sp1) {
//                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, randomSlashAnimationName, BLADEMASTER025_CLONE_ANIMATION_MODIFIER);
                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, randomSlashAnimationName, 1f);
                                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerHorizontalFacing(sp1, initialTriggeredEntityHorizontalFacingAngle);
                                }
                            }
                            RemoveEntity(previousEntity);
                        }

                        previousTempestSwordObj.setAnyTypeValue(AirObj);

                        //TODO delay X ticks to do damage action
                        if (!isActionDone.getBooleanVal() && TempestActionIndex2AirObj.isEmpty() && TempestActionIndex2GroundObj.isEmpty()) {
                            isActionDone.setBooleanVal(true);
                            SBSTickEventMethods.setSkillActionDone(entity3, BLADEMASTER_0_25_DAMAGE_ACTION + triggeredEntityTickID);
                            Blademaster025_ServerUpwardSlashAction(entity3, initialTriggeredEntityHorizontalFacingAngle, animatorOriginLoc, targetCenterLoc, weaponType, finalUpwardSlashDmg, aoeRange, finalCondition, shouldMoveAlongWithAnimator);

//                            moveEntityToPos(entity2, animatorOriginLoc);
                        }
//                        else {
//                            moveEntityToPos(entity2, AirObjVec);
//                        }
                    }
                }
                else {
                    slashTickCounterHolder.setIntCounter(slashTick + actionPeriod);
                }
            }

            //update val
            ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
        };

        SBSTickEventMethods.setMethodActionTimer(entity2, BLADEMASTER_0_25_DAMAGE_ACTION + triggeredEntityTickID, actionPeriod, action);
    }


    public static void Blademaster025_ServerNormalSlashAction(final LivingEntity entity3, final Entity indirectEntity, int index, final Vector3f center, final AABB aoeRectangle, final float midY, final int weaponType, final float finalDmg, final Predicate<Entity> condition) {
        //spawn model
        //play skill sound at target location
        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity3), center.x(), midY, center.z(), 0.8f, 1f);
        DfoSwdSoundMethods.PlayBlademaster025TempestSoundWithIndex(getEntityLevel(entity3), index, center.x(), midY, center.z(), 0.8f, 1f);

        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity3, aoeRectangle, condition)) {
            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                float entityPosX = getEntityX(tempLivingEntity);
                float entityPosZ = getEntityZ(tempLivingEntity);
                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity3, indirectEntity, tempLivingEntity, finalDmg, false);
//                LivingEntityDamageTarget(entity3, tempLivingEntity, finalDmg);
                DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity3), entityPosX, getEntityY(tempLivingEntity), entityPosZ, 0.8f, 1f);

                float entityTowardsTargetLocAngle = Vector3fMethods.AngleFromLocationToLocationHorizontal(entityPosX, entityPosZ, center.x(), center.z());
                float newDX = Vector3fMethods.PolarProjectionFromXHorizon(0.5f, entityTowardsTargetLocAngle);
                float newDZ = Vector3fMethods.PolarProjectionFromZHorizon(0.5f, entityTowardsTargetLocAngle);
                setEntitySPDeltaMovement(tempLivingEntity, newDX, 0.2f, newDZ, false);
            }
        }
    }


    public static void Blademaster025_ServerUpwardSlashAction(LivingEntity entity3, float initialTriggeredEntityHorizontalFacingAngle, Vector3f originLoc, Vector3f targetCenterLoc, int weaponType, float finalUpwardSlashDmg, float aoeRange, Predicate<Entity> finalCondition, boolean shouldMoveAlongWithAnimator) {
        GeoModelSpawnMethods.GenerateCustomFadingCloneEntityWithTargetLoc(entity3, initialTriggeredEntityHorizontalFacingAngle, 0, 1, originLoc.x(), originLoc.y(), originLoc.z(), originLoc.x(), originLoc.y(), originLoc.z(), -1,
                255, 255, 224, BLADEMASTER025_CLONE_ALPHA, 80,
                GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, 8, 8, 64, true, true, BLADEMASTER025_TEMPEST_UPWARD_SLASH_ANIMATION, 1f, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                DfoSwordmanSkillTreeConstants.NO_ANIMATION, true, false, true);

//        float HorizontalAngleBetween = Vector3fMethods.AngleFromLocationToLocationHorizontal(originLoc, targetCenterLoc);
        float scale = aoeRange * 0.75f;
        Entity tempEffEntity = GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256(entity3, initialTriggeredEntityHorizontalFacingAngle, scale, targetCenterLoc.x(), targetCenterLoc.y() + 2 * scale, targetCenterLoc.z(), 12,
                BLADEMASTER025_UPWARD_SLASH_TEXTURE, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 6, 6, 6, true, true, true, false,  null, 777,false);

        int startDmgTicks = 25;
        int endDmgTicks = 31;
        MethodAction action = DfoSwordmanSkillMethods.generateCommonSwdDamageActionWithDurationWithRange(targetCenterLoc, aoeRange, targetCenterLoc.y(), targetCenterLoc.y() + BLADEMASTER025_TEMPEST_MAX_AIR_HEIGHT, weaponType, startDmgTicks, endDmgTicks, finalUpwardSlashDmg, tempEffEntity, DfoSwdSoundMethods::PlayBlademaster025TempestUpwardSlashSound, finalCondition, 0.35f, 1.2f, true, 2, BLADEMASTER_0_25_FINAL_DAMAGE_ACTION, false, null);

        SBSTickEventMethods.setMethodActionTimer(entity3, BLADEMASTER_0_25_FINAL_DAMAGE_ACTION, 2, action);

        if (shouldMoveAlongWithAnimator) {
            tpEntityToPos(entity3, originLoc);
            if (entity3 instanceof ServerPlayer sp1) {
                DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerHorizontalFacing(sp1, initialTriggeredEntityHorizontalFacingAngle);
            }
        }

        if (shouldMoveAlongWithAnimator) {
            SBSTickEventMethods.setMethodActionTimer(entity3, BLADEMASTER_0_25_FINAL_RESET_ENTITY_ACTION, 4, Blademaster025_ServerResetEntityAndSendPacketIfNeeded(new BooleanValueHolder(false), new IntegerValueHolder(0), new IntegerValueHolder(endDmgTicks), 4));
        }
    }

    public static MethodAction Blademaster025_ServerResetEntityAndSendPacketIfNeeded(BooleanValueHolder isDone, IntegerValueHolder ElapsedTicks, IntegerValueHolder MaxTicks, int actionPeriod) {
        return (livingEntity) -> {
            int updatedTicks = ElapsedTicks.getIntCounter();
            if (updatedTicks > MaxTicks.getIntCounter() && !isDone.getBooleanVal()) {
                isDone.setBooleanVal(true);
                incrementEntityStateByAmountX(livingEntity, Constants.IS_IN_ACTION, -1);
                setEntityHasGravity(livingEntity, true);
                if (livingEntity instanceof ServerPlayer sp1) {
                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                }
                SBSTickEventMethods.setSkillActionDone(livingEntity, BLADEMASTER_0_25_FINAL_RESET_ENTITY_ACTION);
            }
            ElapsedTicks.setIntCounter(updatedTicks + actionPeriod);
        };
    }




    //client action
    public static void Blademaster025_ClientMethodInit(HashMap<String, MethodEntityAction> clientMethodMap) {
        clientMethodMap.put(BLADEMASTER025_TEMPEST_ORBIT_EFF_CLIENT_MOVING_ACTION, Blademaster025::Blademaster025_ClientMovingBlockEffObjectAction);
        clientMethodMap.put(BLADEMASTER025_TEMPEST_SWORD_EFF_CLIENT_MOVING_ACTION, Blademaster025::Blademaster025_ClientMovingAirEffObjectAction);
        clientMethodMap.put(BLADEMASTER025_TEMPEST_GROUND_SWORD_EFF_CLIENT_MOVING_ACTION, Blademaster025::Blademaster025_ClientMovingGroundEffObjectAction);
        clientMethodMap.put(BLADEMASTER025_TEMPEST_DUPLICATE_CLONE_CLIENT_ACTION, Blademaster025::Blademaster025_ClientDupCloneAction);
    }

    public static void Blademaster025_ClientMovingBlockEffObjectAction(Entity movingObj) {
        SwdTempestBlockEffEntity swdTempestBlockEffEntity = (SwdTempestBlockEffEntity) movingObj;

        float currentTotalDistHasTraveled = swdTempestBlockEffEntity.getClientTotalDistHasTraveled();
        if (currentTotalDistHasTraveled <= swdTempestBlockEffEntity.getAoeRange()) {
            float distShouldTravel = swdTempestBlockEffEntity.getAoeRange() - currentTotalDistHasTraveled;
            float originX = (swdTempestBlockEffEntity.hasClientNextMovingPos()) ? swdTempestBlockEffEntity.getPreviousToPosX(): getEntityX(swdTempestBlockEffEntity);
            float originY = (swdTempestBlockEffEntity.hasClientNextMovingPos()) ? swdTempestBlockEffEntity.getPreviousToPosY(): getEntityY(swdTempestBlockEffEntity);
            float originZ = (swdTempestBlockEffEntity.hasClientNextMovingPos()) ? swdTempestBlockEffEntity.getPreviousToPosZ(): getEntityZ(swdTempestBlockEffEntity);
            float newFacingAngle = BLADEMASTER025_TEMPEST_DEGREE_EACH_TIME_SHOULD_INCREMENT + Vector3fMethods.AngleFromLocationToLocationHorizontal(swdTempestBlockEffEntity.getTargetCenterPosX(), swdTempestBlockEffEntity.getTargetCenterPosZ(), originX, originZ);
            float newX = Vector3fMethods.PolarProjectionFromXHorizonWithOrigin(swdTempestBlockEffEntity.getTargetCenterPosX(), distShouldTravel, newFacingAngle);
            float newZ = Vector3fMethods.PolarProjectionFromZHorizonWithOrigin(swdTempestBlockEffEntity.getTargetCenterPosZ(), distShouldTravel, newFacingAngle);

            swdTempestBlockEffEntity.setClientHasNextMovingPos(true);
            swdTempestBlockEffEntity.setFromToPositions(originX, originY, originZ, newX, originY, newZ);
            swdTempestBlockEffEntity.setPreviousToPosX(newX);
            swdTempestBlockEffEntity.setPreviousToPosY(originY);
            swdTempestBlockEffEntity.setPreviousToPosZ(newZ);

            ParticleMethods.GenerateGlowParticleAtEntityLoc(swdTempestBlockEffEntity, 2);

            swdTempestBlockEffEntity.setClientTotalDistHasTraveled(currentTotalDistHasTraveled + BLADEMASTER025_TEMPEST_DIST_SHOULD_TRAVEL_EACH_TICK);
        }
        else {
            swdTempestBlockEffEntity.setClientHasNextMovingPos(false);
        }
    }

    public static void Blademaster025_ClientMovingAirEffObjectAction(Entity movingObj) {
        BMTempestSwordEntity bmTempestSwordEntity = (BMTempestSwordEntity) movingObj;

        float currentTotalDistHasTraveled = bmTempestSwordEntity.getClientTotalDistHasTraveled();
        if (currentTotalDistHasTraveled <= bmTempestSwordEntity.getAoeRange()) {
            float originX = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosX(): getEntityX(bmTempestSwordEntity);
            float originY = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosY(): getEntityY(bmTempestSwordEntity);
            float originZ = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosZ(): getEntityZ(bmTempestSwordEntity);
            float newFacingAngle = BLADEMASTER025_TEMPEST_DEGREE_EACH_TIME_SHOULD_INCREMENT + getEntityHorizontalFacingDeg(bmTempestSwordEntity);
            setEntityHorizontalFacingDeg(bmTempestSwordEntity, newFacingAngle);

            float newX = Vector3fMethods.PolarProjectionFromXHorizonWithOrigin(bmTempestSwordEntity.getTargetCenterPosX(), currentTotalDistHasTraveled + BLADEMASTER025_TEMPEST_DIST_SHOULD_TRAVEL_EACH_TICK, newFacingAngle);
            float newZ = Vector3fMethods.PolarProjectionFromZHorizonWithOrigin(bmTempestSwordEntity.getTargetCenterPosZ(), currentTotalDistHasTraveled + BLADEMASTER025_TEMPEST_DIST_SHOULD_TRAVEL_EACH_TICK, newFacingAngle);

            bmTempestSwordEntity.setClientHasNextMovingPos(true);
            bmTempestSwordEntity.setFromToPositions(originX, originY, originZ, newX, originY, newZ);
            bmTempestSwordEntity.setPreviousToPosX(newX);
            bmTempestSwordEntity.setPreviousToPosY(originY);
            bmTempestSwordEntity.setPreviousToPosZ(newZ);

            bmTempestSwordEntity.setClientTotalDistHasTraveled(currentTotalDistHasTraveled + BLADEMASTER025_TEMPEST_DIST_SHOULD_TRAVEL_EACH_TICK);
//            //Test
//            printInGameMsg("client: "+newX+", remaingintTick: "+bmTempestSwordEntity.getLifetime() + ", ID: " + getEntityHashID(bmTempestSwordEntity));
        }
        else {
            bmTempestSwordEntity.setClientHasNextMovingPos(false);
        }

        ParticleMethods.GenerateGlowParticleAtEntityLoc(bmTempestSwordEntity, 2);
    }

    //tp HououinSeia @e[type=dfoswordmanskilltree:effswdtempestswordeff,sort=nearest,limit=1]
    public static void Blademaster025_ClientMovingGroundEffObjectAction(Entity movingObj) {
        BMTempestSwordEntity bmTempestSwordEntity = (BMTempestSwordEntity) movingObj;

        int currentLifeTime = bmTempestSwordEntity.getMaxLifeTime() - bmTempestSwordEntity.getLifetime();
        if (currentLifeTime <= BLADEMASTER025_TOTAL_TICK_THRUST_ANIMATION_ENDS) {
            if (!isEntityFarFromGround(bmTempestSwordEntity, BLADEMASTER025_HEIGHT_DISTANCE_SHOULD_DESCEND_EACH_TIME - 1f)) {
                float originX = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosX(): getEntityX(bmTempestSwordEntity);
                float originY = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosY(): getEntityY(bmTempestSwordEntity);
                float originZ = (bmTempestSwordEntity.hasClientNextMovingPos()) ? bmTempestSwordEntity.getPreviousToPosZ(): getEntityZ(bmTempestSwordEntity);

                float distPercent = currentLifeTime * BLADEMASTER025_THRUST_LERP_TIME;
                float newY = originY + (bmTempestSwordEntity.getTargetCenterPosY() - originY) * distPercent;

                bmTempestSwordEntity.setClientHasNextMovingPos(true);
                bmTempestSwordEntity.setFromToPositions(originX, originY, originZ, originX, newY, originZ);
                bmTempestSwordEntity.setPreviousToPosX(originX);
                bmTempestSwordEntity.setPreviousToPosY(newY);
                bmTempestSwordEntity.setPreviousToPosZ(originZ);
            }
            else {
                bmTempestSwordEntity.setLifetime(BLADEMASTER025_TOTAL_TICK_THRUST_ANIMATION_ENDS + 1);
            }
        }
        else {
            bmTempestSwordEntity.setClientHasNextMovingPos(false);
        }
    }

    public static void Blademaster025_ClientDupCloneAction(Entity dupClone) {
        SwdFadingCloneCustomEntity swdFadingCloneCustomEntity = (SwdFadingCloneCustomEntity) dupClone;

//        int currentElapsedTick = swdFadingCloneCustomEntity.getMaxLifetime() - swdFadingCloneCustomEntity.getLifetime();
        if (swdFadingCloneCustomEntity.getLifetime() == 80) {
            float animationSpeedModifier = swdFadingCloneCustomEntity.getGeoAnimationSpeedModifier();
            int currentAnimationTick = (int) (animationSpeedModifier * (swdFadingCloneCustomEntity.getMaxLifetime() - swdFadingCloneCustomEntity.getLifetime()));
            swdFadingCloneCustomEntity.setColorAlpha(BLADEMASTER025_CLONE_ALPHA - 16);
            swdFadingCloneCustomEntity.setIsBoundToEntity(false);
            float pausedAnimationSpeedModifier = 0.0001f;
            swdFadingCloneCustomEntity.setGeoAnimationSpeedModifier(pausedAnimationSpeedModifier);
            float offset = (currentAnimationTick) * (1f / pausedAnimationSpeedModifier);
            swdFadingCloneCustomEntity.getAnimationController().setTickOffset(-offset);
            Entity owner = swdFadingCloneCustomEntity.getOwner();
            if (owner != null) {
                setEntityHorizontalFacingDeg(swdFadingCloneCustomEntity, getEntityHorizontalFacingDeg(owner));
            }
            swdFadingCloneCustomEntity.setClientActionName(DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        }
    }



    //configuration
    private static boolean getSPlayerShouldMoveAlongWithAnimator(ServerPlayer sp1) {
        try {
            return getSPlayerConfig().get(sp1).get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_CONFIG).get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_SHOULD_MOVE_ALONG_WITH_CLONE_ANIMATOR).getIntValue() != 0;
        }
        catch (Exception ignored) {

        }
        return false;
    }

    public static void ClientConfigInit() {
        HashMap<String, MethodConfigHelper> GeneralSetting = new HashMap<String, MethodConfigHelper>();
        GeneralSetting.put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_SHOULD_MOVE_ALONG_WITH_CLONE_ANIMATOR, new MethodConfigHelper(GlobalClientPlayerValues::DefaultSettingOnOff, 0, true));
        getCPlayerConfig2Settings().put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_CONFIG, GeneralSetting);
    }

    public static void ServerConfigInit(HashMap<String, HashMap<String, MethodConfigHelper>> SPlayerConfigMap) {
        getSConfig().add(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_CONFIG);
        getSConfig().add(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_SHOULD_MOVE_ALONG_WITH_CLONE_ANIMATOR);
        HashMap<String, MethodConfigHelper> GeneralSetting = new HashMap<String, MethodConfigHelper>();
        GeneralSetting.put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_SHOULD_MOVE_ALONG_WITH_CLONE_ANIMATOR, new MethodConfigHelper(null, 0, true));
        SPlayerConfigMap.put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_CONFIG, GeneralSetting);
    }

    public static void ServerConfigAfterInit(ServerPlayer sp1, HashMap<String, HashMap<String, MethodConfigHelper>> SPlayerConfigMap) {
        //sync to client
        int val = SPlayerConfigMap.get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_CONFIG).get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_SHOULD_MOVE_ALONG_WITH_CLONE_ANIMATOR).getIntValue();
        NetworkingFetchMsgMethods.FetchConfigKeyValToClientSide(sp1, DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_CONFIG, DfoSwordmanSkillTreeConstants.BLADEMASTER_0_25_TEMPEST_SHOULD_MOVE_ALONG_WITH_CLONE_ANIMATOR, val);
    }
}
