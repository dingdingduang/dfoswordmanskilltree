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
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster003;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster036;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
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
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.getSkillsInCooldownClientTimerOverlayInstance;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster030 {
    //animation
    public static final String BLADEMASTER030_ANIMATION_START = "flow_clash_start3";
    public static final String BLADEMASTER030_FLOW_CLASH_ADDITIONAL_ATTACK = "flow_clash_additional_attack1";
    public static final String BLADEMASTER030_FLOW_CLASH_ADDITIONAL_ATTACK_STR_ID = BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_CLASH + "_ADDITIONAL_ATTACK";
    public static final String BLADEMASTER030_FLOW_CLASH_SLASH_DOWNWARDS_STR_ID = BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_CLASH + "_SLASH_DOWNWARD";

    //model texture
    public static final String BLADEMASTER030_FLOW_CLASH_SLASH_DOWNWARD_TEXTURE = "textures/entity/skilleff/blademaster/flow_stance/stab/slash_downward/";
    public static final String BLADEMASTER030_FLOW_CLASH_SLASH_THRUST_TEXTURE = "textures/entity/skilleff/blademaster/flow_stance/stab/slash/";
    public static final String BLADEMASTER030_FLOW_CLASH_SLASH_SWORD_TEXTURE = "textures/entity/skilleff/blademaster/flow_stance/stab/slash_zanbato/";
    public static final String BLADEMASTER030_FLOW_CLASH_SLASH_THRUST_EFFECT_TEXTURE = "textures/entity/skilleff/blademaster/flow_stance/stab/slash_zanbato_eff/";
    public static final String BLADEMASTER030_FLOW_CLASH_SLASH_ADDITIONAL_TEXTURE = "textures/entity/skilleff/blademaster/flow_stance/stab/additional_attack/";

    public static HashMap<LivingEntity, Integer> BLADEMASTER030_HashMapTriggeredTimes = new HashMap<>();

    //client val
//    private static int BLADEMASTER030_ClientHashMapTriggeredTimes = 1;
    private static boolean BLADEMASTER030_ClientHashMapHasAdditionalAttack = false;
//    private static boolean BLADEMASTER030_ClientHashMapPreviousAdditionalAttackIsTriggered = false;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        Double AdditionalAttackDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double AdditionalAttackConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);
        Float AdditionalAttackFinalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        int MultipleHits = skillData.getValueUsage_01().get(correctSkillLevel).intValue();

        Double StunApplyChance = skillData.getValueUsage_02().get(correctSkillLevel);
        Double StunDuration = skillData.getDuration().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (AdditionalAttackDamageMultiplier.floatValue() * 100)),
                String.format("%s", AdditionalAttackConstantDamage),
                String.format("%s", AdditionalAttackFinalDmg),
                MultipleHits,
                String.format("%d%%", (int) (StunApplyChance.floatValue() * 100)),
                String.format("%ss", StunDuration)
        );
    }

    public static void init() {
        String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_CLASH;
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);

        tempSkillData.setTranslatableTooltipGetter(Blademaster030::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement((SkillID2) -> {
            boolean isInCD = isCPlayerSkillInCD(SkillID2);
            boolean isInAction = isPlayerInAction();
            boolean isInStance = isPlayerInFlowStance();
            boolean isChanneling = isPlayerInChannelingState();
            boolean isOnGround = isPlayerOnGround();
            boolean isPlayerBeingHit = isPlayerBeingHit();
            int weaponType = DfoSkillTreeItemMethods.getWeaponType(getClientPlayer());
            boolean isZanbatoOrShortSword = weaponType == DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO ||  weaponType == DfoSkillTreeItemMethods.IS_AVERAGE_SHORT_SWORD;

//            boolean isPassed = ((isInAction && isInStance) || BLADEMASTER030_ClientHashMapHasAdditionalAttack) && !isChanneling && isOnGround && !isPlayerBeingHit;
            boolean satisfiedInStanceOrAdditionalAttack = ( (isInAction && isInStance && !isInCD) || BLADEMASTER030_ClientHashMapHasAdditionalAttack);
            boolean satisfiedInAirCond = (!isInAction && !isOnGround && !isInCD);
            boolean isPassed = ( (satisfiedInStanceOrAdditionalAttack && !satisfiedInAirCond) || (!satisfiedInStanceOrAdditionalAttack && satisfiedInAirCond)) && !isChanneling && !isPlayerBeingHit;
            if (isPassed) {
                if (isInCD) {
                    HashMap<String, Integer> clientCD = getSkillsInCooldownClientTimerOverlayInstance().getPlayerSkill2CooldownTicks();
                    if (isZanbatoOrShortSword && (clientCD.get(SkillID2) > 40 || !BLADEMASTER030_ClientHashMapHasAdditionalAttack) ) {
                        isPassed = false;
                    }
                    BLADEMASTER030_ClientHashMapHasAdditionalAttack = false;
                }
                else {
                    //should i check weapontype then set this to true?
                    BLADEMASTER030_ClientHashMapHasAdditionalAttack = true;
                }
            }
            return isPassed;
        });
//        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);



        if (isClientSide()) {
            return;
        }



        //TODO: when triggerTimes = 1 doesnt remove from map, skill not trigger unless switch to zanbato or short sword
        //action while channeling on server side
        MethodAction tempActive1Action = (entity1) -> {
            int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);

            int triggeredTimes;
            if (BLADEMASTER030_HashMapTriggeredTimes.containsKey(entity1)) {
                boolean isZanbatoOrShortSword = weaponType == DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO ||  weaponType == DfoSkillTreeItemMethods.IS_AVERAGE_SHORT_SWORD;
//                printInGameMsg("additional?: "+isZanbatoOrShortSword);
                if (!isZanbatoOrShortSword) { return; }
                triggeredTimes = BLADEMASTER030_HashMapTriggeredTimes.get(entity1) + 1;
                BLADEMASTER030_HashMapTriggeredTimes.put(entity1, triggeredTimes);
            }
            else {
                triggeredTimes = 1;
                BLADEMASTER030_HashMapTriggeredTimes.put(entity1, triggeredTimes);
            }
//            printInGameMsg("trigger time: "+triggeredTimes);

//            printInGameMsg("!Blademaster029.IsLivingEntityInFlowStance(entity1): "+!Blademaster029.IsLivingEntityInFlowStance(entity1));
//            printInGameMsg("!EntityMethods.isEntityFarFromGround(entity1, 0.1f): "+!EntityMethods.isEntityFarFromGround(entity1, 0.1f));
            if (!Blademaster029.IsLivingEntityInFlowStance(entity1) && !EntityMethods.isEntityFarFromGround(entity1, 0.1f)) {
                if (triggeredTimes > 1) { BLADEMASTER030_HashMapTriggeredTimes.put(entity1, 1); }
                ServerSkillMethods.incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, 1);
                Blademaster029.getFlowStanceOtherSkillMap().put(entity1, true);

                //play sound when guarding
                DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.7f, 1.0f);

//                float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
//                float entityAnimationSpeedMultiplier = entityAnimationArr[0];
//                float damageTickSpeedMultiplier = entityAnimationArr[1];

                //play animation
                if (entity1 instanceof ServerPlayer sp1) {
//                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER030_ANIMATION_START, entityAnimationSpeedMultiplier);
                }

                //==============
                //calc
                int playerSkillLVL = 0;
                float aoeRange;
                float damageBase;
                float damageMultiplier;
                float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

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
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, Blademaster012.BLADEMASTER012_ANIMATION_START_NAME);

                    int EXFlowingClashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_CLASH_UPGRADE);
                    if (EXFlowingClashUpgradeLVL > 0) {
                        SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_CLASH_UPGRADE);
                        ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXFlowingClashUpgradeLVL - 1).floatValue();
                    }
                }
                aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue() * 2f;
                damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;
                float FlowForceDamageMultiplierBonus = ServerSkillMethods.getEntityStateAmount(entity1, Blademaster033.BLADEMASTER033_LIVING_ENTITY_BUFF_NAME) * 0.01f;

                int MultipleHits = skill1.getValueUsage_01().get(playerSkillLVL).intValue();
                float StunApplyChance = skill1.getValueUsage_02().get(playerSkillLVL).floatValue();
                int StunDurationSec2Ticks = (int) (skill1.getDuration().get(playerSkillLVL) * 20);

                float finalDist = skill1.getDistance().get(playerSkillLVL).floatValue();

                Predicate<Entity> condition;

                float entityFacingAngle = EntityMethods.getEntityHorizontalFacingDeg(entity1);
                //start damage
                Vector3f tempVec = new Vector3f(
                        Vector3fMethods.PolarProjectionFromXHorizon(finalDist, entityFacingAngle),
                        0,
                        Vector3fMethods.PolarProjectionFromZHorizon(finalDist, entityFacingAngle));

                int FlowStanceLVL = 0;
                boolean hasZenithOfSwordArtSuperArmor;
                if (entity1 instanceof ServerPlayer sp1) {
                    condition = defaultSkillTargetCondition(sp1);
                    DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, tempVec, true);

                    hasZenithOfSwordArtSuperArmor = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART) >= Blademaster036.BLADEMASTER036_ZENITH_OF_SWORD_ART_BONUS_EFFECT_1_LVL;
                    FlowStanceLVL = Math.max(0, ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE) - 1);
                } else {
                    condition = defaultSkillTargetCondition();
                    hasZenithOfSwordArtSuperArmor = true;
                }
                setEntityDeltaMovement(entity1, getEntityDeltaMovement(entity1).add(tempVec.x(), 0f, tempVec.z()));

                float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier * (1f + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE).getValueMultiplierList_01().get(FlowStanceLVL) + FlowForceDamageMultiplierBonus ) ) * 0.5f;
                int actionPeriod = 2;
                int multipleHitDMGInterval = Math.max(1, (int) (4 * getEntityAnimationSpeedMultiplierArr(entity1)[1]));

                IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
                IntegerValueHolder recordLastActionOverTickCounter = new IntegerValueHolder(0);
                IntegerValueHolder tempCurrentHitCounter;
                if (weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE || weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA) {
                    tempCurrentHitCounter = new IntegerValueHolder(MultipleHits - 1);
                }
                else {
                    tempCurrentHitCounter = new IntegerValueHolder(0);
                }
                HashSet<LivingEntity> DamagedGroup = new HashSet<>();
                BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
                BooleanValueHolder IsLanded = new BooleanValueHolder(false);

//                printInGameMsg("execute air action");
                //air action
                int maxTicks = 600;
                int airDelayTicks = 2;
                float maxDegree = 45f;
                int effUpdateTicks = 10;
                int delayTicks = 20;
                float finalDistShouldTravelEachTime = 1.5f * actionPeriod;

                FloatValueHolder totalDistTraveled = new FloatValueHolder(0);
                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, EntityMethods.getEntityHorizontalFacingDeg(entity1), true, 1f, 0, 1.4f, 0, 10, BLADEMASTER030_FLOW_CLASH_SLASH_DOWNWARD_TEXTURE, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 3, 3, 5, true, true, true, false, null, 777, true);
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
                        recordLastActionOverTickCounter.setIntCounter(updatedTick);

                        //TODO: fix air loop end animation, leap -> stance? or just dont
                        if (updatedTick >= airDelayTicks) {
                            int startTime = updatedTick - airDelayTicks;
                            float entity2HorizontalFacingAngle = getEntityHorizontalFacingDeg(entity2);
//                                        float entity2VerticalFacingAngle = MathMethods.min(-maxDegree, getEntityVerticalFacingDeg(entity2));
                            float entity2VerticalFacingAngle = MathMethods.max(maxDegree, getEntityVerticalFacingDeg(entity2));
//                                        float entity2VerticalFacingAngle = ExtraMathMethods.clamp(getEntityVerticalFacingDeg(entity2), maxDegree, 90f);
//                                        float entity2VerticalFacingAngle = getEntityVerticalFacingDeg(entity2);

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
                                setEntitySPDeltaMovement(entity2, tempVec2.x(), tempVec2.y(), tempVec2.z(), false);


                                if (startTime % effUpdateTicks == 0) {
                                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity2, EntityMethods.getEntityHorizontalFacingDeg(entity2), true, 1f, 0, 1.4f, 0, 10, BLADEMASTER030_FLOW_CLASH_SLASH_DOWNWARD_TEXTURE, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 3, 3, 5, true, true, true, false, null, 777, true);
                                }

                                int multipleHitCounter;
                                for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, aoeRange, condition)) {
                                    if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                        if (!DamagedGroup.contains(tempLivingEntity)) {
                                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                            DfoSwdSoundMethods.PlayWeaponTypeStabHitSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);
                                            DamagedGroup.add(tempLivingEntity);

                                            if (weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON && StunApplyChance >= ExtraMathMethods.randomFloat()) {
                                                DfoStatusGeneralMethods.ApplyStunStatusToTargetLivingEntity(entity2, tempLivingEntity, StunDurationSec2Ticks, 1);
                                            }
                                        }
                                        else if ( (multipleHitCounter = tempCurrentHitCounter.getIntCounter()) > 0 && updatedTick % multipleHitDMGInterval == 0) {
                                            tempCurrentHitCounter.setIntCounter(multipleHitCounter - 1);

                                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                            DfoSwdSoundMethods.PlayWeaponTypeStabHitSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);
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

                                            DfoSwdSoundMethods.PlayWeaponTypeStabHitSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);
                                            DamagedGroup.add(tempLivingEntity);

                                            if (weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON && StunApplyChance >= ExtraMathMethods.randomFloat()) {
                                                DfoStatusGeneralMethods.ApplyStunStatusToTargetLivingEntity(entity2, tempLivingEntity, StunDurationSec2Ticks, 1);
                                            }
                                        }
                                        else if ( (multipleHitCounter = tempCurrentHitCounter.getIntCounter()) > 0 && updatedTick % multipleHitDMGInterval == 0) {
                                            tempCurrentHitCounter.setIntCounter(multipleHitCounter - 1);

                                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                            DfoSwdSoundMethods.PlayWeaponTypeStabHitSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);
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
                        }
                        if (updatedTick >= recordLastActionOverTickCounter.getIntCounter() + delayTicks) {
//                            SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                            SBSTickEventMethods.setSkillActionDone(entity2, BLADEMASTER030_FLOW_CLASH_SLASH_DOWNWARDS_STR_ID);
                        }
                    }
                    //update val
                    tempTickCounter.setIntCounter(updatedTick + actionPeriod);
                };

//                SBSTickEventMethods.setMethodActionTimer(entity1, BLADEMASTER030_FLOW_CLASH_SLASH_DOWNWARDS_STR_ID, actionPeriod, action);
                SBSTickEventMethods.setMethodActionTimerWithCleanAction(entity1, BLADEMASTER030_FLOW_CLASH_SLASH_DOWNWARDS_STR_ID, actionPeriod, action, Blademaster030::CleanFunc, true);
            }
            else if (Blademaster029.IsLivingEntityInFlowStance(entity1)) {
                if (triggeredTimes > 1) { BLADEMASTER030_HashMapTriggeredTimes.put(entity1, 1); }

                int playerSkillLVL = 0;
                Predicate<Entity> condition;
                int FlowStanceLVL;

                ServerSkillMethods.incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, 1);
                Blademaster029.deactivateLivingEntityFlowStance(entity1);
                Blademaster029.getFlowStanceOtherSkillMap().put(entity1, true);

                //play sound when start
                DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.8f, 1.0f);

                float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                float damageTickSpeedMultiplier = entityAnimationArr[1];

                float ExtraDamageMultiplier = 0;
                boolean shouldActivateLightsabreMultipleHits = false;
                boolean hasZenithOfSwordArtSuperArmor;
                if (entity1 instanceof ServerPlayer sp1) {
                    if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                        playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                        if (playerSkillLVL <= 0) {
                            return;
                        }
                        playerSkillLVL = playerSkillLVL - 1;
                    }

                    condition = defaultSkillTargetCondition(sp1);

                    FlowStanceLVL = Math.max(0, ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE) - 1);

                    hasZenithOfSwordArtSuperArmor = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART) >= Blademaster036.BLADEMASTER036_ZENITH_OF_SWORD_ART_BONUS_EFFECT_1_LVL;
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER030_ANIMATION_START, entityAnimationSpeedMultiplier);
                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);

                    if (weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE && ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY) >= Blademaster003.BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_1_LVL) {
                        shouldActivateLightsabreMultipleHits = true;
                    }

                    int EXFlowingClashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_CLASH_UPGRADE);
                    if (EXFlowingClashUpgradeLVL > 0) {
                        SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_CLASH_UPGRADE);
                        ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXFlowingClashUpgradeLVL - 1).floatValue();
                    }
                }
                else {
                    condition = defaultSkillTargetCondition();
                    FlowStanceLVL = 0;
                    hasZenithOfSwordArtSuperArmor = true;
                }
                SkillDataJson skill1 = getID2SkillData().get(SkillID);

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

                int MultipleHits = skill1.getValueUsage_01().get(playerSkillLVL).intValue();
                float StunApplyChance = skill1.getValueUsage_02().get(playerSkillLVL).floatValue();
                int StunDurationSec2Ticks = (int) (skill1.getDuration().get(playerSkillLVL) * 20);

                float finalDist = skill1.getDistance().get(playerSkillLVL).floatValue();

                float entityFacingAngle = EntityMethods.getEntityHorizontalFacingDeg(entity1);
                //start damage
                Vector3f tempVec = new Vector3f(
                        Vector3fMethods.PolarProjectionFromXHorizon(finalDist, entityFacingAngle),
                        0,
                        Vector3fMethods.PolarProjectionFromZHorizon(finalDist, entityFacingAngle));

                setEntitySPDeltaMovement(entity1, tempVec.x(), 0f, tempVec.z(), true);

                int startDmgTicks = (int) (2 * damageTickSpeedMultiplier);
                int endDmgTicks = (int) (6 * damageTickSpeedMultiplier);
                int multipleHitDMGInterval = Math.max(1, (endDmgTicks - startDmgTicks) / MultipleHits);
                int delayTicks = 20;
                float finalAoeRange = aoeRange;
                float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier * (1f + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE).getValueMultiplierList_01().get(FlowStanceLVL) + FlowForceDamageMultiplierBonus ) );
//                int actionPeriod = 2;
                int actionPeriod = 1;

                IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
                BooleanValueHolder spawnEffect = new BooleanValueHolder(false);
                BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
                HashSet<LivingEntity> DamagedGroup = new HashSet<>();

                IntegerValueHolder tempCurrentHitCounter;
                if (weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE || weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA) {
                    tempCurrentHitCounter = new IntegerValueHolder(MultipleHits - 1);
                }
                else {
                    tempCurrentHitCounter = new IntegerValueHolder(0);
                }

                boolean finalShouldActivateLightsabreMultipleHits = shouldActivateLightsabreMultipleHits;

                MethodAction action = (entity2) -> {
                    int currentPassedTicks = tempTickCounter.getIntCounter();

                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);

                    if (hasZenithOfSwordArtSuperArmor && isLivingEntityInAction(entity2)) {
                        TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 30, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);
                    }

                    if (!isActionInterrupted && currentPassedTicks >= startDmgTicks && currentPassedTicks <= endDmgTicks) {
                        float entity2FacingAngle = getEntityHorizontalFacingDeg(entity2);
                        //spawn model
                        if (!spawnEffect.getBooleanVal()) {
                            spawnEffect.setBooleanVal(true);
                            //play skill sound at target location
                            DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                            DfoSwdSoundMethods.PlayBlademaster005TripleSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);

//                            final int[] slashColorArr = BlademasterSkillTreeInitialization.BLADEMASTER_SLASH_COLOR[weaponType + 1];
//                            GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, entity2FacingAngle, 135f, 1, slashColorArr[0], slashColorArr[1], slashColorArr[2], 0f, 1.4f, 0f, Math.max(18, endDmgTicks-startDmgTicks),
//                                    GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);

                            GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity2, entity2FacingAngle, true, 1f, 0, 1.4f, 0, Math.max(10, 2 * (endDmgTicks - startDmgTicks)), BLADEMASTER030_FLOW_CLASH_SLASH_THRUST_TEXTURE, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Z_24, 3, 3, 6, true, true, true, true, null, 777, true);
                            GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity2, entity2FacingAngle, true, 1f, 0, 1.4f, 0, Math.max(10, 2 * (endDmgTicks - startDmgTicks)), BLADEMASTER030_FLOW_CLASH_SLASH_SWORD_TEXTURE, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Z_36, 3, 3, 7, true, true, true, true, null, 777, true);
                            GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity2, entity2FacingAngle, true, 1f, 0, 1.4f, 0, Math.max(10, 2 * (endDmgTicks - startDmgTicks)), BLADEMASTER030_FLOW_CLASH_SLASH_THRUST_EFFECT_TEXTURE, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Z_48, 3, 3, 6, true, true, true, true, null, 777, true);

                            if (finalShouldActivateLightsabreMultipleHits) {
                                float newX = Vector3fMethods.PolarProjectionFromXHorizon(1f, entity2FacingAngle);
                                float newZ = Vector3fMethods.PolarProjectionFromZHorizon(1f, entity2FacingAngle);

                                GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, entity2FacingAngle, 0, 1, 255, 255, 255, newX, 1.4f, newZ, 8,
                                        "textures/entity/skilleff/blademaster/stab_lightsabre_laser/", 6, 1, 1, false, false, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);

                                GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, entity2FacingAngle, 90, 1, 255, 255, 255, newX, 1.4f, newZ, 8,
                                        "textures/entity/skilleff/blademaster/stab_lightsabre_laser/", 6, 1, 1, false, false, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);
                            }
                        }

                        //Sector damage cond
                        Vector3f triggeredEntityPos = getEntityPos(entity2);
                        float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);
                        Vector3f tempVecBehindTriggerEntity = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, -0.5f, triggeredEntityFacingAngle);
                        Predicate<Entity> SectorDmgCond = (entity) -> {
                            float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(tempVecBehindTriggerEntity, getEntityPos(entity)));
                            float range = 75f; //150 deg

                            if (!Vector3fMethods.IsAngleDegBetweenAB(sectorDeg, MathMethods.RoundAngle(triggeredEntityFacingAngle + range), MathMethods.RoundAngle(triggeredEntityFacingAngle - range))) {
                                return false;
                            }

                            return DistanceBetweenEntities(entity, entity2) <= finalAoeRange;
                        };
                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition.and(SectorDmgCond))) {
                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                int multipleHitCounter;
                                if (!DamagedGroup.contains(tempLivingEntity)) {
                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                    DfoSwdSoundMethods.PlayWeaponTypeStabHitSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                    DamagedGroup.add(tempLivingEntity);

                                    float pushStrength;
                                    if (tempCurrentHitCounter.getIntCounter() >= 0) {
                                        pushStrength = calculatePushEntityStrength(0.05f, entity2, tempLivingEntity);
                                    }
                                    else {
                                        pushStrength = calculatePushEntityStrength(0.15f, entity2, tempLivingEntity);
                                    }
                                    pushEntityWithDeltaValue(tempLivingEntity, entity2FacingAngle, pushStrength, 0, true);

                                    if (weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON && StunApplyChance >= ExtraMathMethods.randomFloat()) {
                                        DfoStatusGeneralMethods.ApplyStunStatusToTargetLivingEntity(entity2, tempLivingEntity, StunDurationSec2Ticks, 1);
                                    }
                                }
                                else if ( (multipleHitCounter = tempCurrentHitCounter.getIntCounter()) > 0 && currentPassedTicks % multipleHitDMGInterval == 0) {
                                    tempCurrentHitCounter.setIntCounter(multipleHitCounter - 1);

                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                    DfoSwdSoundMethods.PlayWeaponTypeStabHitSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat() * 0.3f + 0.85f);

                                    float pushStrength = calculatePushEntityStrength(0.05f, entity2, tempLivingEntity);
                                    pushEntityWithDeltaValue(tempLivingEntity, entity2FacingAngle, pushStrength, 0, true);

                                    if (finalShouldActivateLightsabreMultipleHits) {
                                        DfoStatusGeneralMethods.ApplyShockStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.GENERAL_DURATION_TICKS_FOR_SHOCK_STATUS, finalDmg * 0.5f, DfoStatusGeneralMethods.GENERAL_MAX_INFLICT_TIMES_FOR_SHOCK_STATUS, true, SkillID);
                                    }
                                }
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
                                ServerSkillMethods.incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                            }
                            if (currentPassedTicks >= endDmgTicks + delayTicks) {
                                SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                            }
                        }
                    }

                    tempTickCounter.setIntCounter(tempTickCounter.getIntCounter() + actionPeriod);
                };
                //start loop action
//                SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
                SBSTickEventMethods.setMethodActionTimerWithCleanAction(entity1, SkillID, actionPeriod, action, Blademaster030::CleanFunc, true);
            }

            //execute additional attack
            if (triggeredTimes == 2) {
                ServerSkillMethods.incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, 1);
                Blademaster029.deactivateLivingEntityFlowStance(entity1);
                Blademaster029.getFlowStanceOtherSkillMap().put(entity1, true);

                float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                float damageTickSpeedMultiplier = entityAnimationArr[1];

                //play animation
                if (entity1 instanceof ServerPlayer sp1) {
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER030_FLOW_CLASH_ADDITIONAL_ATTACK, entityAnimationSpeedMultiplier);
//                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                }

                //==============
                //calc
                int playerSkillLVL = 0;
                float aoeRange;
                float damageBase;
                float damageMultiplier;
                float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

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

                    int EXFlowingClashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_CLASH_UPGRADE);
                    if (EXFlowingClashUpgradeLVL > 0) {
                        SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_FLOWING_CLASH_UPGRADE);
                        ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXFlowingClashUpgradeLVL - 1).floatValue();
                    }
                }
                aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;
                float FlowForceDamageMultiplierBonus = ServerSkillMethods.getEntityStateAmount(entity1, Blademaster033.BLADEMASTER033_LIVING_ENTITY_BUFF_NAME) * 0.01f;

                float finalDist = skill1.getDistance().get(playerSkillLVL).floatValue();

                Predicate<Entity> condition;

                float entityFacingAngle = EntityMethods.getEntityHorizontalFacingDeg(entity1);
                //start damage
                Vector3f tempVec = new Vector3f(
                        Vector3fMethods.PolarProjectionFromXHorizon(finalDist, entityFacingAngle),
                        0,
                        Vector3fMethods.PolarProjectionFromZHorizon(finalDist, entityFacingAngle));

                int FlowStanceLVL = 0;
                if (entity1 instanceof ServerPlayer sp1) {
                    condition = defaultSkillTargetCondition(sp1);
                    DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, tempVec, true);

                    FlowStanceLVL = Math.max(0, ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE) - 1);
                }
                else {
                    condition = defaultSkillTargetCondition();
                }
                setEntityDeltaMovement(entity1, getEntityDeltaMovement(entity1).add(tempVec.x(), 0f, tempVec.z()));

                int startDmgTicks = (int) (2 * damageTickSpeedMultiplier);
                int endDmgTicks = (int) (6 * damageTickSpeedMultiplier);
                float finalAoeRange = aoeRange;
                float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier * (1f + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE).getValueMultiplierList_01().get(FlowStanceLVL) + FlowForceDamageMultiplierBonus));
                int actionPeriod = 2;

                float sectorRange = 75f;

//                Entity tempEffEntity = GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity1, EntityMethods.getEntityHorizontalFacingDeg(entity1), 270f, finalAoeRange, 255, 255, 255, 0f, 1.4f, 0f, Math.max(18, endDmgTicks-startDmgTicks),
//                        GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);
                Entity tempEffEntity = GeoModelSpawnMethods.GenerateCustomSlashTotalFrameBoundToEntity(entity1, EntityMethods.getEntityHorizontalFacingDeg(entity1), 0f, 270f, true, finalAoeRange, 255, 255, 255, 0f, 1.4f, 0f, Math.max(10, 2 * (endDmgTicks - startDmgTicks)),
                        BLADEMASTER030_FLOW_CLASH_SLASH_ADDITIONAL_TEXTURE, 3, 3, 5, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NO_ACTION_NEG_Z16, DfoSwordmanSkillTreeConstants.NO_ANIMATION, false);

                MethodAction action = DfoSwordmanSkillMethods.generateCommonSwdDamageActionWithDuration(weaponType, startDmgTicks, endDmgTicks, finalAoeRange * 1.5f, finalDmg, tempEffEntity, DfoSwdSoundMethods::PlayBlademaster005TripleSlashSound, condition, sectorRange, 0.7f, 0.2f, true, actionPeriod, BLADEMASTER030_FLOW_CLASH_ADDITIONAL_ATTACK_STR_ID, true, null);

//                SBSTickEventMethods.setMethodActionTimer(entity1, BLADEMASTER030_FLOW_CLASH_ADDITIONAL_ATTACK_STR_ID, actionPeriod, action);
                SBSTickEventMethods.setMethodActionTimerWithCleanAction(entity1, BLADEMASTER030_FLOW_CLASH_ADDITIONAL_ATTACK_STR_ID, actionPeriod, action, Blademaster030::CleanFunc, true);
            }
        };

        tempSkillData.setActiveSkillAction1(tempActive1Action);

//        MethodAction tempSkillCleanAction = (entity1) -> {
//            BLADEMASTER030_HashMapTriggeredTimes.remove(entity1);
//            Blademaster029.getFlowStanceOtherSkillMap().remove(entity1);
//            //IMPORTANT! set action off to allow entity to be able to cast skills
////            SendPacketIsInActionToClientSideFromServer(entity1, Constants.ACTION_OFF);
//        };
//        tempSkillData.setClearSkillAction(tempSkillCleanAction);
    }

    public static HashMap<LivingEntity, Integer> getBLADEMASTER030_HashMapTriggeredTimes() { return BLADEMASTER030_HashMapTriggeredTimes; }

    public static void CleanFunc(LivingEntity entity1) {
        BLADEMASTER030_HashMapTriggeredTimes.remove(entity1);
        Blademaster029.getFlowStanceOtherSkillMap().remove(entity1);
    }
}
