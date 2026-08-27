package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster036;
import net.dingdingduang.dfoswordmanskilltree.util.*;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.FloatValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods.PlayRandomSwordWieldingSound;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getCPlayerLastTriggeredNotChannelingActiveSkillID;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster031 {
    public static final String BLADEMASTER031_ANIMATION_START = "flow_leap_slash_start2";
    public static final String BLADEMASTER031_ANIMATION_LOOP = "flow_leap_slash_in_air";
    public static final String BLADEMASTER031_ANIMATION_SLASH = "flow_leap_slash_end3";
    public static final String BLADEMASTER031_ANIMATION_SHORT_SWORD_SLASH = "flow_leap_slash_cross_end2";
    public static final String BLADEMASTER031_ANIMATION_KATANA_SLASH = "flow_leap_slash_cross_alt_end";
    public static final String BLADEMASTER031_ANIMATION_NO_SLASH_END = "flow_leap_no_slash_end";

    public static final String BLADEMASTER030_FLOW_LEAP_SLASH_DOWNWARDS_STR_ID = BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_LEAP_SLASH + "_SLASH_DOWNWARD";

    public static final String BLADEMASTER031_SLASH_AIR_TEXUTRE = "textures/entity/skilleff/blademaster/flow_stance/leap/slash_air/";
    public static final String BLADEMASTER031_SLASH_REGULAR_TEXUTRE = "textures/entity/skilleff/blademaster/flow_stance/leap/slash_regular/";
    public static final String BLADEMASTER031_SLASH_LIGHTSABRE_TEXUTRE = "textures/entity/skilleff/blademaster/flow_stance/leap/slash_lightsabre/";
    public static final String BLADEMASTER031_SLASH_KATANA_SHORTSWORD_L2R_TEXUTRE = "textures/entity/skilleff/blademaster/flow_stance/leap/slash_l2r/";
    public static final String BLADEMASTER031_SLASH_KATANA_SHORTSWORD_R2L_TEXUTRE = "textures/entity/skilleff/blademaster/flow_stance/leap/slash_r2l/";

    public static final int BLADEMASTER031_MULTIPLE_HITS = 2;

    public static HashMap<LivingEntity, Integer> BLADEMASTER031_HashMapTriggeredTimes = new HashMap<>();
    public static HashMap<LivingEntity, Boolean> BLADEMASTER031_HashMapIsLandedYet = new HashMap<>();

    public static final String BLADEMASTER031_GROUNDQUAKE_STR_ID = "BLADEMASTER031_GROUNDQUAKE_STR_ID_";
    //Client
    private static int BLADEMASTER031_ClientHashMapTriggeredTimes = 1;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg)
        );
    }

    public static void init() {
        String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_LEAP_SLASH;
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);

        tempSkillData.setTranslatableTooltipGetter(Blademaster031::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement((SkillID2) -> {
            boolean isInCD = isCPlayerSkillInCD(SkillID2);
            boolean isInAction = isPlayerInAction();
            boolean isInStance = isPlayerInFlowStance();
            boolean isChanneling = isPlayerInChannelingState();
            boolean isOnGround = isEntityBPosRFOnGround(getClientPlayer());
            boolean isPlayerBeingHit = isPlayerBeingHit();

            boolean isLastSkillFlowLeapSlash = getCPlayerLastTriggeredNotChannelingActiveSkillID().hashCode() == BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_LEAP_SLASH.hashCode();

            boolean satisfiedInStance = isInAction && isInStance && !isInCD;
            boolean satisfiedInAirAfterLeapCond = !isOnGround && isLastSkillFlowLeapSlash && BLADEMASTER031_ClientHashMapTriggeredTimes == 1;

            boolean satisfiedInAirSlashCond = DfoSkillTreeItemMethods.getWeaponType(getClientPlayer()) == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON && !isOnGround && !isInAction && !isInCD;
            if (satisfiedInAirSlashCond) {
                BLADEMASTER031_ClientHashMapTriggeredTimes = 2;
                return true;
            }

            boolean isPassed = (satisfiedInStance || satisfiedInAirAfterLeapCond) && !isChanneling && !isPlayerBeingHit;
            if (isPassed) {
                if (isInCD) {
                    BLADEMASTER031_ClientHashMapTriggeredTimes++;
                }
                else {
                    BLADEMASTER031_ClientHashMapTriggeredTimes = 1;
                }
            }

            return isPassed;
//            return !isInCD && (isInAction && isInStance) && !isChanneling && isOnGround && !isPlayerBeingHit;
        });
//        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);



        if (isClientSide()) {
//            BLADEMASTER031_ClientHashMapTriggeredTimes = 1;
            return;
        }

        //server init
//        if (BLADEMASTER031_HashMapTriggeredTimes == null) {
//            BLADEMASTER031_HashMapTriggeredTimes = new HashMap<>();
//        }



        //action while channeling on server side
        MethodAction tempActive1Action = (entity1) -> {
            int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);

            int triggeredTimes;
            if (BLADEMASTER031_HashMapTriggeredTimes.containsKey(entity1)) {
                triggeredTimes = BLADEMASTER031_HashMapTriggeredTimes.get(entity1)+1;
                BLADEMASTER031_HashMapTriggeredTimes.put(entity1, triggeredTimes);
            }
            else {
                triggeredTimes = 1;
                BLADEMASTER031_HashMapTriggeredTimes.put(entity1, triggeredTimes);
                BLADEMASTER031_HashMapIsLandedYet.put(entity1, false);
            }

            //air slash downward

            if (triggeredTimes != 2 && !Blademaster029.IsLivingEntityInFlowStance(entity1) && weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON && !EntityMethods.isEntityFarFromGround(entity1, 0.1f)) {
//                if (triggeredTimes > 1) { BLADEMASTER031_HashMapTriggeredTimes.put(entity1, 1); }

                int playerSkillLVL = 0;
                int FlowStanceLVL = 0;
                Predicate<Entity> condition;

                ServerSkillMethods.incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, 1);
                Blademaster029.getFlowStanceOtherSkillMap().put(entity1, true);

                //play sound when guarding
                DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.7f, 1.0f);

                boolean hasZenithOfSwordArtSuperArmor;

                float ExtraDamageMultiplier = 0;
                if (entity1 instanceof ServerPlayer sp1) {
                    if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                        playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                        if (playerSkillLVL <= 0) {
                            return;
                        }
                        playerSkillLVL = playerSkillLVL - 1;
                    }
                    FlowStanceLVL = Math.max(0, ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE) - 1);
                    condition = defaultSkillTargetCondition(sp1);

                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, Blademaster012.BLADEMASTER012_ANIMATION_START_NAME);
                    hasZenithOfSwordArtSuperArmor = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART) >= Blademaster036.BLADEMASTER036_ZENITH_OF_SWORD_ART_BONUS_EFFECT_1_LVL;

                    int EXFlowingLeapSlashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_LEAP_SLASH_UPGRADE);
                    if (EXFlowingLeapSlashUpgradeLVL > 0) {
                        SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_LEAP_SLASH_UPGRADE);
                        ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXFlowingLeapSlashUpgradeLVL - 1).floatValue();
                    }
                }
                else {
                    condition = defaultSkillTargetCondition();
                    hasZenithOfSwordArtSuperArmor = true;
                }

                //==============
                //calc
                float aoeRange;
                float damageBase;
                float damageMultiplier;
                float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

                SkillDataJson skill1 = getID2SkillData().get(SkillID);

                aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue() * 2f;
                damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;
                float FlowForceDamageMultiplierBonus = ServerSkillMethods.getEntityStateAmount(entity1, Blademaster033.BLADEMASTER033_LIVING_ENTITY_BUFF_NAME) * 0.01f;

                float finalDist = skill1.getDistance().get(playerSkillLVL).floatValue();


                float entityFacingAngle = EntityMethods.getEntityHorizontalFacingDeg(entity1);
                //start damage
                Vector3f tempVec = new Vector3f(
                        Vector3fMethods.PolarProjectionFromXHorizon(finalDist, entityFacingAngle),
                        0,
                        Vector3fMethods.PolarProjectionFromZHorizon(finalDist, entityFacingAngle)
                );

                setEntitySPDeltaMovement(entity1, tempVec, true);

                float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier * (1f + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE).getValueMultiplierList_01().get(FlowStanceLVL) + FlowForceDamageMultiplierBonus ) );
                int actionPeriod = 2;

                IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);

                HashSet<LivingEntity> DamagedGroup = new HashSet<>();
                BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
                BooleanValueHolder IsLanded = new BooleanValueHolder(false);

//                printInGameMsg("execute air action");
                //air action
                int maxTicks = 600;
                int airDelayTicks = 2;
                float maxDegree = 65f;
                int effUpdateTicks = 4;

                float finalDistShouldTravelEachTime = 1.5f * actionPeriod;
                FloatValueHolder totalDistTraveled = new FloatValueHolder(0);
                FloatValueHolder tempVelocity = new FloatValueHolder(0);
                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, EntityMethods.getEntityHorizontalFacingDeg(entity1), true, 1f, 0, 1.4f, 0, 4, BLADEMASTER031_SLASH_AIR_TEXUTRE, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 1, 1, 4, true, true, true, false, null, 777, true);
                Vector3f entityOriPos = getEntityPos(entity1);

                MethodAction action = (entity2) -> {
                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                    setEntityFallDistance(entity2, 0f);
                    int updatedTick = tempTickCounter.getIntCounter();

                    if (hasZenithOfSwordArtSuperArmor && isLivingEntityInAction(entity2)) {
                        TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 30, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);
                    }

                    if (!IsLanded.getBooleanVal() && !isActionInterrupted &&
                            !isEntityBPosRFOnGround(entity2) && updatedTick < maxTicks) {
                        if (updatedTick >= airDelayTicks) {
                            int startTime = updatedTick - airDelayTicks;
                            float entity2HorizontalFacingAngle = getEntityHorizontalFacingDeg(entity2);
                            float entity2VerticalFacingAngle = MathMethods.max(maxDegree, getEntityVerticalFacingDeg(entity2));

                            if (updatedTick == airDelayTicks) {
                                //play skill sound at target location
                                PlayRandomSwordWieldingSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 1.1f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);
                            }
                            if (entity2 instanceof ServerPlayer sp1 && startTime % 500 == 0) {
//                                            SBPlayerAnimatorMethods.PlayDfoSwordmanSkillAnimationServer(sp1, "airstrike_channeling");
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, Blademaster012.BLADEMASTER012_ANIMATION_LOOP_NAME);
                            }

                            //start damage
                            if (updatedTick % 2 == 0) {
                                //update val
                                totalDistTraveled.setFloatVal(totalDistTraveled.getFloatVal() + finalDistShouldTravelEachTime);

                                Vector3f tempVec2 = Vector3fMethods.directionFromRotation(entity2HorizontalFacingAngle, entity2VerticalFacingAngle).mul(finalDistShouldTravelEachTime);

                                //move pos
                                float tempYDeltaVecAdd = tempVelocity.getFloatVal() - 0.02f;
                                tempVelocity.setFloatVal(tempYDeltaVecAdd);
                                setEntitySPDeltaMovement(entity2, tempVec2.x(), tempVec2.y() + tempYDeltaVecAdd, tempVec2.z(), false);


                                if (startTime % effUpdateTicks == 0) {
                                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity2, EntityMethods.getEntityHorizontalFacingDeg(entity2), true, 1f, 0, 1.4f, 0, 4, BLADEMASTER031_SLASH_AIR_TEXUTRE, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 1, 1, 4, true, true, true, false, null, 777, true);
                                }


                                for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, aoeRange, condition)) {
                                    if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                        if (!DamagedGroup.contains(tempLivingEntity)) {
                                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                            DfoSwdSoundMethods.PlayRandomSwordHitSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);
                                            DamagedGroup.add(tempLivingEntity);


                                            HashSet<LivingEntity> GroundQuakeDamagedGroup = new HashSet<>();
                                            GroundQuakeDamagedGroup.add(tempLivingEntity);
                                            float pushYStrength = calculatePushEntityStrength(1f + tempYDeltaVecAdd, entity2, tempLivingEntity);
                                            EntityMethods.setEntitySPDeltaMovement(tempLivingEntity, 0, -pushYStrength, 0, true);

                                            GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(tempLivingEntity, 255, 255, 255, 1f, 0, 1.4f, 0, 300, GeoAnimationAndTextureName.BLUDGEON_HIT_SPIRAL, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_CLOCKWISE_1080,
                                                    DfoSwordmanSkillTreeConstants.NO_ANIMATION,5, 5, 1, true, true, true, DfoSwordmanSkillMethods.BludgeonFlipShockwave1(BLADEMASTER031_GROUNDQUAKE_STR_ID, entity2, aoeRange * 0.5f + 0.5f, finalDmg, condition, GroundQuakeDamagedGroup), 5, true);
                                        }

                                        if (isEntityOnGround(entity2)) {
                                            pushEntityWithDeltaValue(tempLivingEntity, entity2HorizontalFacingAngle, 0, 0, false);
                                        } else {
                                            pushEntityWithDeltaValueXYZ(tempLivingEntity, tempVec2.x() + 0.1f, tempVec2.y() + 0.1f, tempVec2.z() + 0.1f, false);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (entity2 instanceof ServerPlayer sp1) {
                                tpEntityToPos(sp1, entityOriPos);
                            } else {
                                moveEntityToPos(entity2, entityOriPos);
                            }
                        }

                    }
                    else {
                        if (!SendActionOffPacket.getBooleanVal()) {
                            IsLanded.setBooleanVal(true);
                            //landed
                            if (!isActionInterrupted) {
                                //damage
                                for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, aoeRange, condition)) {
                                    if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                        int multipleHitCounter;
                                        if (!DamagedGroup.contains(tempLivingEntity)) {
                                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                            DfoSwdSoundMethods.PlayRandomSwordHitSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);
                                            DamagedGroup.add(tempLivingEntity);

                                            HashSet<LivingEntity> GroundQuakeDamagedGroup = new HashSet<>();
                                            GroundQuakeDamagedGroup.add(tempLivingEntity);
                                            float pushYStrength = calculatePushEntityStrength(1f, entity2, tempLivingEntity);
                                            EntityMethods.setEntitySPDeltaMovement(tempLivingEntity, 0, -pushYStrength, 0, true);

                                            GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(tempLivingEntity, 255, 255, 255, 1f, 0, 1.4f, 0, 300, GeoAnimationAndTextureName.BLUDGEON_HIT_SPIRAL, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_CLOCKWISE_1080,
                                                    DfoSwordmanSkillTreeConstants.NO_ANIMATION, 5, 5, 1, true, true, true, DfoSwordmanSkillMethods.BludgeonFlipShockwave1(BLADEMASTER031_GROUNDQUAKE_STR_ID, entity2, aoeRange * 0.5f + 0.5f, finalDmg, condition, GroundQuakeDamagedGroup), 5, true);
                                        }
                                    }
                                }

                                //reset delta
                                setEntitySPDeltaMovement(entity2, 0, 0, 0, false);

                                setEntityFallDistance(entity2, 0f);
                            }

                            if (entity2 instanceof ServerPlayer sp1) {
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, Blademaster012.BLADEMASTER012_ANIMATION_END_NAME);
                            }
                            SendActionOffPacket.setBooleanVal(true);
                            ServerSkillMethods.incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);

                            SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER030_FLOW_LEAP_SLASH_DOWNWARDS_STR_ID);
                        }
                    }
                    //update val
                    tempTickCounter.setIntCounter(updatedTick + actionPeriod);
                };

                SBSTickEventMethods.setMethodActionTimerWithCleanAction(entity1, BLADEMASTER030_FLOW_LEAP_SLASH_DOWNWARDS_STR_ID, actionPeriod, action, Blademaster031::CleanFunc, true);
            }
            //Leap start
            else if (Blademaster029.IsLivingEntityInFlowStance(entity1)) {
                if (triggeredTimes > 1) { BLADEMASTER031_HashMapTriggeredTimes.put(entity1, 1); }

                ServerSkillMethods.incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, 1);
                Blademaster029.deactivateLivingEntityFlowStance(entity1);
                Blademaster029.getFlowStanceOtherSkillMap().put(entity1, true);

                int playerSkillLVL = 0;

                SkillDataJson skill1 = getID2SkillData().get(SkillID);

                boolean hasZenithOfSwordArtSuperArmor;

                if (entity1 instanceof ServerPlayer sp1) {
                    if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                        playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                        if (playerSkillLVL <= 0) {
                            return;
                        }
                        playerSkillLVL = playerSkillLVL - 1;
                    }

                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER031_ANIMATION_START);

                    hasZenithOfSwordArtSuperArmor = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART) >= Blademaster036.BLADEMASTER036_ZENITH_OF_SWORD_ART_BONUS_EFFECT_1_LVL;
                }
                else {
                    hasZenithOfSwordArtSuperArmor = true;
                }
                float finalDist = skill1.getDistance().get(playerSkillLVL).floatValue();

                float entityFacingAngle = EntityMethods.getEntityHorizontalFacingDeg(entity1);
                //start damage
                Vector3f tempVec = new Vector3f(
                        Vector3fMethods.PolarProjectionFromXHorizon(finalDist, entityFacingAngle),
                        0.6f,
                        Vector3fMethods.PolarProjectionFromZHorizon(finalDist, entityFacingAngle));

                setEntitySPDeltaMovement(entity1, tempVec, true);

                int actionPeriod2 = 4;
                IntegerValueHolder tempTickCounter2 = new IntegerValueHolder(0);
                MethodAction action2 = (entity2) -> {
                    boolean isActionInterrupted = ServerSkillMethods.isLivingEntityActionInterrupted(entity2);
                    boolean contActioning = tempTickCounter2.getIntCounter() == 0 || !EntityMethods.isEntityFarFromGround(entity2, 0.1f);

                    if (hasZenithOfSwordArtSuperArmor && isLivingEntityInAction(entity2)) {
                        TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 30, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);
                    }

                    //leap over with no slash
                    if (!isActionInterrupted) {
                        if (!contActioning) {
                            //play animation
                            if (entity2 instanceof ServerPlayer sp1) {
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER031_ANIMATION_NO_SLASH_END);
                            }

                            //play sound when stop guarding
                            DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 2.0f);
                            //stop loop action
                            ServerSkillMethods.incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);

                            SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                            //recast flow stance
//                        getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE).getActiveSkillAction1().executeAction(entity2);
                        } else {
                            int currentPassedTicks = tempTickCounter2.getIntCounter();

                            //play animation
                            if ((currentPassedTicks - 8) % 500 == 0 && entity2 instanceof ServerPlayer sp1) {
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER031_ANIMATION_LOOP);
                            }

                        }
                    }
                    else {
                        incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                        SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                    }

                    tempTickCounter2.setIntCounter(tempTickCounter2.getIntCounter() + actionPeriod2);
                };

                SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod2, action2);
            }

            //start slash
            if (triggeredTimes == 2) {
                //play sound when guarding
                int playerSkillLVL = 0;
                int FlowStanceLVL = 0;
                Predicate<Entity> condition;

                DfoSwdSoundMethods.PlaySwordGuardStartSound(getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.7f, 2.0f);

                float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                float damageTickSpeedMultiplier = entityAnimationArr[1];


                boolean isWieldingLightsabre = weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE;
                boolean isWieldingKatana = weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA;
                boolean isWieldingShortSword = weaponType == DfoSkillTreeItemMethods.IS_AVERAGE_SHORT_SWORD;
                boolean isWieldingKatanaOrShortSword = isWieldingKatana || isWieldingShortSword;
                boolean isWieldingBludgeon = weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON;

                SkillDataJson skill1 = getID2SkillData().get(SkillID);

                float ExtraDamageMultiplier = 0;
                if (entity1 instanceof ServerPlayer sp1) {
                    if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                        playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                        if (playerSkillLVL <= 0) {
                            return;
                        }
                        playerSkillLVL = playerSkillLVL - 1;
                    }

                    if (isWieldingKatana) {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER031_ANIMATION_KATANA_SLASH, entityAnimationSpeedMultiplier);
                    }
                    else if (isWieldingShortSword) {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER031_ANIMATION_SHORT_SWORD_SLASH, entityAnimationSpeedMultiplier);
                    }
                    else {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER031_ANIMATION_SLASH, entityAnimationSpeedMultiplier);
                    }

                    FlowStanceLVL = Math.max(0, ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE) - 1);

                    int EXFlowingLeapSlashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_LEAP_SLASH_UPGRADE);
                    if (EXFlowingLeapSlashUpgradeLVL > 0) {
                        SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_LEAP_SLASH_UPGRADE);
                        ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXFlowingLeapSlashUpgradeLVL - 1).floatValue();
                    }

                    condition = defaultSkillTargetCondition(sp1);
                }
                else {
                    condition = defaultSkillTargetCondition();
                }

                //==============
                //calc
                float aoeRange;
                float damageBase;
                float damageMultiplier;
                float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

                aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;
                float FlowForceDamageMultiplierBonus = ServerSkillMethods.getEntityStateAmount(entity1, Blademaster033.BLADEMASTER033_LIVING_ENTITY_BUFF_NAME) * 0.01f;

                int startDmgTicks = (int) (3 * damageTickSpeedMultiplier);
                int endDmgTicks = (int) (6 * damageTickSpeedMultiplier);

                float finalAoeRange = aoeRange * ((isWieldingKatanaOrShortSword) ? 1.5f: 1);
                float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier * (1f + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE).getValueMultiplierList_01().get(FlowStanceLVL) + FlowForceDamageMultiplierBonus) );
                int actionPeriod = 2;

                IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
                BooleanValueHolder spawnEffect = new BooleanValueHolder(false);
                BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
//                HashSet<LivingEntity> DamagedGroup = new HashSet<>();
                HashMap<LivingEntity, Integer> EntityDamagedGroup2DmgCount = new HashMap<>();

                MethodAction action = (entity2) -> {
                    int currentPassedTicks = tempTickCounter.getIntCounter();

                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);

                    if (!isActionInterrupted && currentPassedTicks >= startDmgTicks && currentPassedTicks <= endDmgTicks) {
                        float entity2FacingAngle = getEntityHorizontalFacingDeg(entity2);
                        //spawn model
                        if (!spawnEffect.getBooleanVal()) {
                            spawnEffect.setBooleanVal(true);
                            //play skill sound at target location
                            DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                            DfoSwdSoundMethods.PlayBlademaster005TripleSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);

                            final int[] slashColorArr = BlademasterSkillTreeInitialization.BLADEMASTER_SLASH_COLOR[weaponType + 1];
                            if (isWieldingLightsabre) {
                                GeoModelSpawnMethods.GenerateCustomSlashTotalFrameBoundToEntity(entity2, entity2FacingAngle, 0f, 1, true, 1, slashColorArr[0], slashColorArr[1], slashColorArr[2], 0f, 1.4f, 0f, Math.max(12, endDmgTicks - startDmgTicks),
                                        BLADEMASTER031_SLASH_LIGHTSABRE_TEXUTRE, 3, 3, 9, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);
                            }
                            else if (isWieldingKatanaOrShortSword) {
                                GeoModelSpawnMethods.GenerateCustomSlashTotalFrameBoundToEntity(entity2, entity2FacingAngle, 0f, 90f, true, 1.5f, slashColorArr[0], slashColorArr[1], slashColorArr[2], 0f, 1.4f, 0f, Math.max(8, endDmgTicks - startDmgTicks),
                                        BLADEMASTER031_SLASH_KATANA_SHORTSWORD_L2R_TEXUTRE, 1, 2, 4, true, true, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Z_48, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);
                                GeoModelSpawnMethods.GenerateCustomSlashTotalFrameBoundToEntity(entity2, entity2FacingAngle, 0f, 315f, true, 1.5f, slashColorArr[0], slashColorArr[1], slashColorArr[2], 0f, 1.4f, 0f, Math.max(8, endDmgTicks - startDmgTicks),
                                        BLADEMASTER031_SLASH_KATANA_SHORTSWORD_R2L_TEXUTRE, 1, 2, 4, true, true, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Z_48, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);
                            }
                            else {
                                GeoModelSpawnMethods.GenerateCustomSlashTotalFrameBoundToEntity(entity2, entity2FacingAngle, 0f, 1, true, 1, slashColorArr[0], slashColorArr[1], slashColorArr[2], 0f, 1.4f, 0f, Math.max(8, endDmgTicks - startDmgTicks),
                                        BLADEMASTER031_SLASH_REGULAR_TEXUTRE, 3, 3, 4, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);
                            }
                        }

                        //Sector damage cond
//                        Vector3f triggeredEntityPos = getEntityPos(entity2);
//                        float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);
//                        Vector3f tempVecBehindTriggerEntity = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, -0.5f, triggeredEntityFacingAngle);
//                        Predicate<Entity> SectorDmgCond = (entity) -> {
//                            float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontal(tempVecBehindTriggerEntity, getEntityPos(entity)));
//                            float range = 75f; //150 deg
//
//                            if (!Vector3fMethods.IsAngleDegBetweenAB(sectorDeg, MathMethods.RoundAngle(triggeredEntityFacingAngle + range), MathMethods.RoundAngle(triggeredEntityFacingAngle - range))) {
//                                return false;
//                            }
//
//                            return DistanceBetweenEntities(entity, entity2) <= finalAoeRange;
//                        };
//                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition.and(SectorDmgCond))) {
                        Integer damageCount;
                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition)) {
                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                damageCount = EntityDamagedGroup2DmgCount.get(tempLivingEntity);
                                if (damageCount == null) {
                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

//                                                    DfoSwdSoundMethods.PlayRandomSwordHitSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);

                                    if (isWieldingLightsabre) {
                                        DfoStatusGeneralMethods.ApplyShockStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.GENERAL_DURATION_TICKS_FOR_SHOCK_STATUS, finalDmg, DfoStatusGeneralMethods.GENERAL_MAX_INFLICT_TIMES_FOR_SHOCK_STATUS, false, SkillID);
                                    }
                                    EntityDamagedGroup2DmgCount.put(tempLivingEntity, 1);

                                    if (isWieldingBludgeon) {
                                        HashSet<LivingEntity> GroundQuakeDamagedGroup = new HashSet<>();
                                        GroundQuakeDamagedGroup.add(tempLivingEntity);
                                        float pushYStrength = calculatePushEntityStrength(1f, entity2, tempLivingEntity);
                                        EntityMethods.setEntitySPDeltaMovement(tempLivingEntity, 0, pushYStrength, 0, true);

                                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(tempLivingEntity, 255, 255, 255, 1f, 0, 1.4f, 0, 300, GeoAnimationAndTextureName.BLUDGEON_HIT_SPIRAL, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_CLOCKWISE_1080,
                                                DfoSwordmanSkillTreeConstants.NO_ANIMATION,5, 5, 1, true, true, true, DfoSwordmanSkillMethods.BludgeonFlipShockwave1(BLADEMASTER031_GROUNDQUAKE_STR_ID, entity2, finalAoeRange + 0.5f, finalDmg, condition, GroundQuakeDamagedGroup), 5, true);
                                    }
                                }
                                else if (isWieldingKatanaOrShortSword && damageCount < BLADEMASTER031_MULTIPLE_HITS){
                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);

                                    if (isWieldingKatana) {
                                        DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg);
                                    }
                                    EntityDamagedGroup2DmgCount.put(tempLivingEntity, damageCount + 1);
                                }


                                Vector3f pushDelta = new Vector3f(
                                        Vector3fMethods.PolarProjectionFromXHorizon(0.15f, entity2FacingAngle),
                                        0,
                                        Vector3fMethods.PolarProjectionFromZHorizon(0.15f, entity2FacingAngle));

                                if (tempLivingEntity instanceof ServerPlayer sp1) {
                                    //update client
                                    DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, pushDelta, true);
                                }
                                setEntityDeltaMovement(tempLivingEntity, getEntityDeltaMovement(tempLivingEntity).add(pushDelta.x(), 0f, pushDelta.z()));
                            }
                        }
                    } else {
                        if (isActionInterrupted) {
                            SendActionOffPacket.setBooleanVal(true);
                            incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                            SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                        }
                        else {
                            if (!SendActionOffPacket.getBooleanVal() && currentPassedTicks >= endDmgTicks + 1) {
                                SendActionOffPacket.setBooleanVal(true);
                                incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                            }
                            if (currentPassedTicks >= endDmgTicks) {
                                SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                            }
                        }
                    }

                    tempTickCounter.setIntCounter(tempTickCounter.getIntCounter() + actionPeriod);
                };

                //start loop action
                SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
            }

            //Cancel skill
//            else {
//                SBSTickEventMethods.setSkillActionDone(entity1, SkillID);
//            }
        };

        tempSkillData.setActiveSkillAction1(tempActive1Action);

        MethodAction tempSkillCleanAction = (entity1) -> {
            BLADEMASTER031_HashMapTriggeredTimes.remove(entity1);
            BLADEMASTER031_HashMapIsLandedYet.remove(entity1);
            Blademaster029.getFlowStanceOtherSkillMap().remove(entity1);
            //IMPORTANT! set action off to allow entity to be able to cast skills
//            SendPacketIsInActionToClientSideFromServer(entity1, Constants.ACTION_OFF);
        };
        tempSkillData.setClearSkillAction(tempSkillCleanAction);
    }

    public static void CleanFunc(LivingEntity entity1) {
        BLADEMASTER031_HashMapTriggeredTimes.remove(entity1);
        Blademaster029.getFlowStanceOtherSkillMap().remove(entity1);
    }
}
