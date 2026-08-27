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
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.*;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.networking.NetworkingFetchMsgMethods;
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

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster022 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_CHARGE;
    public static final String BLADEMASTER022_CHARGE_SLASH_STR_ID = BlademasterSkillTreeInitialization.BLADEMASTER_CHARGE + "_SLASH_UPWARD";
    public static final String BLADEMASTER022_CHARGE_SLASH_DOWNWARD_ADDITIONAL_ATTACK_STR_ID = BlademasterSkillTreeInitialization.BLADEMASTER_CHARGE + "_SLASH_DOWNWARD";
    public static final String BLADEMASTER022_CHARGE_SLASH_DOWNWARD_ADDITIONAL_ATTACK_BLUDGEON_SHOCKWAVE_STR_ID = BlademasterSkillTreeInitialization.BLADEMASTER_CHARGE + "_SLASH_DOWNWARD_BLUDGEON_SHOCKWAVE";

    public static final String BLADEMASTER022_ANIMATION_START = "charge_start";
    public static final String BLADEMASTER022_ANIMATION_LOOP = "charge_looping";
    public static final String BLADEMASTER022_ANIMATION_SLASH = "charge_end";
    public static final String BLADEMASTER022_ANIMATION_SLASH_DOWNWARD_CHANNELING = "charge_slash_downward_channeling";
    public static final String BLADEMASTER022_ANIMATION_SLASH_DOWNWARD = "charge_slash_downward";

    //model texture
    public static final String BLADEMASTER022_CHARGE_FORWARD_EFF = "textures/entity/skilleff/blademaster/charge/dash_eff/";
    public static final String BLADEMASTER022_CHARGE_FORWARD_EFF2 = "textures/entity/skilleff/blademaster/charge/dash_eff2/";
    public static final String BLADEMASTER022_CHARGE_ENERGY_CHARGE_EFF = "textures/entity/skilleff/blademaster/charge/energy_charge/";
    public static final String BLADEMASTER022_CHARGE_SLASH_UPWARD = "textures/entity/skilleff/blademaster/charge/slash_upward/";
    public static final String BLADEMASTER022_CHARGE_SLASH_DOWNWARD = "textures/entity/skilleff/blademaster/charge/slash_downward/";
    public static final String BLADEMASTER022_CHARGE_SLASH_DOWNWARD_EFF = "textures/entity/skilleff/blademaster/charge/slash_downward_eff/";

    //Server
    public static HashMap<LivingEntity, Integer> BLADEMASTER022_HashMapChannelingTotalTicks = new HashMap<>();
    public static HashMap<LivingEntity, Boolean> BLADEMASTER022_HashMapShouldFreeKeyboard = new HashMap<>();

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double ChargeDamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ChargeConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Float ChargeFinalDmg = (float) ( (ChargeDamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ChargeConstantDamage) * skillDamageMultiplier);

        Double DamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        int LightsabreMultipleHit = skillData.getValueUsage_02().get(correctSkillLevel).intValue();
        int KatanaMultipleHit = skillData.getValueMultiplierList_01().get(correctSkillLevel).intValue();

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (ChargeDamageMultiplier.floatValue() * 100)),
                String.format("%s", ChargeConstantDamage),
                String.format("%s", ChargeFinalDmg),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d", LightsabreMultipleHit),
                String.format("%d", KatanaMultipleHit)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster022::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);

        if (isClientSide()) {
            return;
        }



        //====================================
        //setup skill action
        MethodAction tempChannelingAction =
                (entity1) -> {
                    Predicate<Entity> condition;
                    float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                    float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                    float damageTickSpeedMultiplier = entityAnimationArr[1];

                    int playerSkillLVL = 0;
                    float aoeRange;
                    float damageBase;
                    float damageMultiplier;
                    float upperSlashDamageBase;
                    float upperSlashDamageMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

                    BLADEMASTER022_HashMapChannelingTotalTicks.put(entity1, 0);
                    BLADEMASTER022_HashMapShouldFreeKeyboard.put(entity1, DfoSwordmanSkillTreeConstants.CONSTANT_BOOLEAN_FALSE);
                    //IMPORTANT! setup action on to prevent casting other skills while this action
                    //being performed
                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);
                    int additionalAttacks = 0;
                    boolean isSlowBludgeon = weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON;
                    float ChargeExtraAttackDamageMultiplier = 0f;
//                    int otherSkillLevel;

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    int ZenithOfSwordArtLevel, UltimateSwordArtTempestLevel;
                    boolean shouldTriggerTempestUpwardSlash;

                    float ExtraDamageMultiplier = 0;
                    if (entity1 instanceof ServerPlayer sp1) {
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                        }

                        int otherSkillLevel, correctSkillLevel;
                        SkillDataJson skillData;

                        //is wielding lightsabre
//                            if (weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE && (otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY)) >= Blademaster003.BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_2_LVL) {
                        if (weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE && ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY) >= Blademaster003.BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_2_LVL) {
//                                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY).getDamageList_01().get(otherSkillLevel - 1).intValue();
                            additionalAttacks = additionalAttacks + skill1.getValueUsage_02().get(playerSkillLVL).intValue();
                        }
                        //is wielding katana
//                            else if (weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA && (otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY)) >= Blademaster009.BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_2_LVL) {
                        else if (weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA && ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY) >= Blademaster009.BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_2_LVL) {
//                                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY).getDamageList_01().get(otherSkillLevel - 1).intValue();
                            additionalAttacks = additionalAttacks + skill1.getValueMultiplierList_01().get(playerSkillLVL).intValue();
                        }
                        else if (weaponType == DfoSkillTreeItemMethods.IS_AVERAGE_SHORT_SWORD && ((otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_SHORT_SWORD_MASTERY)) >= Blademaster008.BLADEMASTER008_SHORT_SWORD_MASTERY_BONUS_EFFECT_2_LVL) ) {
                            ChargeExtraAttackDamageMultiplier = ChargeExtraAttackDamageMultiplier + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_SHORT_SWORD_MASTERY).getDamageMultiplierList_01().get(otherSkillLevel - 1).floatValue();
                        }
                        else if (isSlowBludgeon) {
                            if ((otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY)) >= Blademaster011.BLADEMASTER011_BLUDGEON_MASTERY_BONUS_EFFECT_3_LVL) {
                                correctSkillLevel = otherSkillLevel - 1;
                                skillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY);
                                ChargeExtraAttackDamageMultiplier = ChargeExtraAttackDamageMultiplier + skillData.getDamageMultiplierList_03().get(correctSkillLevel).floatValue();
                            }
                        }

                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER022_ANIMATION_START, entityAnimationSpeedMultiplier);
                        condition = defaultSkillTargetCondition(sp1);

                        ZenithOfSwordArtLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART);
                        UltimateSwordArtTempestLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ULTIMATE_SWORD_ART_TEMPEST);
                        shouldTriggerTempestUpwardSlash = ZenithOfSwordArtLevel >= Blademaster036.BLADEMASTER036_ZENITH_OF_SWORD_ART_BONUS_EFFECT_2_LVL && UltimateSwordArtTempestLevel > 0;

                        int EXChargeUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_CHARGE_UPGRADE);
                        if (EXChargeUpgradeLVL > 0) {
                            SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_CHARGE_UPGRADE);
                            ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXChargeUpgradeLVL - 1).floatValue();
                        }
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                        ZenithOfSwordArtLevel = 0;
                        UltimateSwordArtTempestLevel = 0;
                        shouldTriggerTempestUpwardSlash = false;
                    }
                    int total_slashCount = skill1.getValueUsage_01().get(playerSkillLVL).intValue() + additionalAttacks;

                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ChargeExtraAttackDamageMultiplier + ExtraDamageMultiplier;
                    upperSlashDamageBase = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
                    upperSlashDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue() + ChargeExtraAttackDamageMultiplier + ExtraDamageMultiplier;

                    int delayTicks = 20;
                    int startDmgTicks = (int) (2 * damageTickSpeedMultiplier);
                    int endDmgTicks = (int) (6 * damageTickSpeedMultiplier);
                    int resetInterval = Math.max(1, (int) (1f * (endDmgTicks - startDmgTicks) / total_slashCount));
                    IntegerValueHolder previousTriggeredResetGroupTickHelper = new IntegerValueHolder(startDmgTicks);

                    float finalAoeRange = aoeRange;
                    float finalDist = skill1.getDistance().get(playerSkillLVL).floatValue();
                    float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
                    float finalUpperSlashDmg = (float) ((upperSlashDamageMultiplier * getEntityAttackDamage(entity1) + upperSlashDamageBase) * skillDamageMultiplier);

                    int actionPeriod = 1;
                    float finalDistShouldTravelEachTime = finalDist / Math.max(1, endDmgTicks - startDmgTicks);

                    TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity1, endDmgTicks, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);

                    HashSet<LivingEntity> damagedGroup = new HashSet<>();
                    IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
                    BooleanValueHolder spawnEffect = new BooleanValueHolder(false);
                    BooleanValueHolder isChargeActionOver = new BooleanValueHolder(false);
                    BooleanValueHolder channelingSpawnEffect = new BooleanValueHolder(false);

                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, getEntityHorizontalFacingDeg(entity1), true, 1f, 0, 1.4f, 0, Math.max(9, endDmgTicks + 1), BLADEMASTER022_CHARGE_FORWARD_EFF, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 3, 3, 8, true, true, true, true,  null, 777,true);
                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, getEntityHorizontalFacingDeg(entity1), true, 1f, 0, 1.4f, 0, Math.max(9, endDmgTicks + 1), BLADEMASTER022_CHARGE_FORWARD_EFF2, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 3, 3, 4, true, true, true, false,  null, 777,true);

                    MethodAction action =
                            (entity2) ->  {
                                boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);

                                int updatedTick = tempTickCounter.getIntCounter();
                                boolean isChargeActionOverYet = isChargeActionOver.getBooleanVal();
                                boolean isChanneling = isLivingEntityInChannelingAction(entity2);

                                if (!isChargeActionOverYet && !isActionInterrupted && updatedTick >= startDmgTicks && updatedTick <= endDmgTicks) {
                                    if (updatedTick == previousTriggeredResetGroupTickHelper.getIntCounter() + resetInterval) {
                                        previousTriggeredResetGroupTickHelper.setIntCounter(previousTriggeredResetGroupTickHelper.getIntCounter() + resetInterval);
                                        damagedGroup.clear();
                                    }
                                    if ((updatedTick - startDmgTicks) % 500 == 0 && entity2 instanceof ServerPlayer sp1) {
                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER022_ANIMATION_LOOP);
                                    }

                                    float entity2FacingAngle = getEntityHorizontalFacingDeg(entity2);
                                    //spawn model
                                    if (!spawnEffect.getBooleanVal()) {
                                        spawnEffect.setBooleanVal(true);
                                        //play skill sound at target location
                                        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                        DfoSwdSoundMethods.PlayWeaponTypeStabHitSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 0.5f);

                                        GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, entity2FacingAngle, 210f, 1, 255, 255, 255, 0f, 1.4f, 0f, Math.max(28, endDmgTicks-startDmgTicks),
                                                GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_FLAME, 5, 1, 22, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, ParticleRegistry.CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, true);
                                    }

                                    //move pos
                                    float newX = Vector3fMethods.PolarProjectionFromXHorizon(finalDistShouldTravelEachTime, entity2FacingAngle);
                                    float newZ = Vector3fMethods.PolarProjectionFromZHorizon(finalDistShouldTravelEachTime, entity2FacingAngle);
//                                    printInGameMsg("newX: "+newX);
//                                    printInGameMsg("newZ: "+newZ);
                                    Vector3f currentEntityDeltaMov = getEntityDeltaMovement(entity2).add(newX, 0f, newZ);
                                    setEntitySPDeltaMovement(entity2, currentEntityDeltaMov, false);

                                    //Sector damage cond
//                                        Vector3f triggeredEntityPos = getEntityPos(entity2);
//                                        float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);
//                                        Vector3f tempVecBehindTriggerEntity = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, -0.5f, triggeredEntityFacingAngle);
//                                        Predicate<Entity> SectorDmgCond = (entity) -> {
//                                            float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontal(tempVecBehindTriggerEntity, getEntityPos(entity)));
//                                            float range = 75f; //150 deg
//
//                                            if (!Vector3fMethods.IsAngleDegBetweenAB(sectorDeg, MathMethods.RoundAngle(triggeredEntityFacingAngle + range), MathMethods.RoundAngle(triggeredEntityFacingAngle - range))) {
//                                                return false;
//                                            }
//
//                                            return DistanceBetweenEntities(entity, entity2) <= finalAoeRange;
//                                        };
                                    for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition)) {
                                        if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                            if (!damagedGroup.contains(tempLivingEntity)) {
                                                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
//
                                                DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, EntityMethods.getEntityLevel(entity2), EntityMethods.getEntityX(entity2), EntityMethods.getEntityY(entity2), EntityMethods.getEntityZ(entity2), 0.8f, 1.0f);
                                                damagedGroup.add(tempLivingEntity);

//                                                    float pushXZStrength = EntityMethods.calculatePushEntityStrength(finalDistShouldTravelEachTime * (endDmgTicks - updatedTick + startDmgTicks), entity2, tempLivingEntity);
//                                                    EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, entity2FacingAngle, pushXZStrength, 0f, true);
                                            }
//                                                EntityMethods.pushEntityWithDeltaValueXYZ(tempLivingEntity, newX, 0f, newZ, true);
                                            EntityMethods.pushEntityWithDeltaValueXYZ(tempLivingEntity, currentEntityDeltaMov.x(), currentEntityDeltaMov.y(), currentEntityDeltaMov.z(), false);
                                        }
                                    }
                                }
                                else {
                                    if (!isChargeActionOverYet) {
                                        if (updatedTick >= endDmgTicks + actionPeriod) {
                                            isChargeActionOver.setBooleanVal(true);

                                            if (entity2 instanceof ServerPlayer sp1) {
                                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER022_ANIMATION_SLASH);
                                                if (isChanneling) {
                                                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardKidnapFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                                                    BLADEMASTER022_HashMapShouldFreeKeyboard.put(sp1, DfoSwordmanSkillTreeConstants.CONSTANT_BOOLEAN_TRUE);
                                                }
                                            }

//                                            Entity tempEffEntity = GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, getEntityHorizontalFacingDeg(entity2), 180f, 1, 255, 255, 255, 0f, 1.4f, 0f, Math.max(18, endDmgTicks - startDmgTicks),
//                                                    GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, ParticleRegistry.CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, true);

                                            float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity2);
                                            Entity tempEffEntity = GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity2, triggeredEntityHorizontalFacingAngle, true, 1f, 0, 1.4f, 0, Math.max(5, endDmgTicks - startDmgTicks), BLADEMASTER022_CHARGE_SLASH_UPWARD, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 3, 3, 5, true, true, true, false, null, 777, false);

                                            MethodAction slashUpwardAction = DfoSwordmanSkillMethods.generateCommonSwdDamageActionWithDuration(weaponType, startDmgTicks, endDmgTicks, finalAoeRange * 1.5f, finalUpperSlashDmg, tempEffEntity, DfoSwdSoundMethods::PlayBlademaster005TripleSlashSound, condition, 361f, 0.2f, 0.7f, false, actionPeriod, BLADEMASTER022_CHARGE_SLASH_STR_ID, false, null);
                                            SBSTickEventMethods.setMethodActionTimer(entity2, BLADEMASTER022_CHARGE_SLASH_STR_ID, actionPeriod, slashUpwardAction);

                                            //zenith of sword art bonus
                                            if (shouldTriggerTempestUpwardSlash) {
                                                Vector3f triggeredEntityOriginalPos = getEntityPos(entity2);

                                                SkillDataJson ZenithOfSwordArtSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART);
                                                float ZenithSwordArtTempestDamageMultiplier = ZenithOfSwordArtSkillData.getDamageMultiplierList_01().get(ZenithOfSwordArtLevel - 1).floatValue();

                                                SkillDataJson tempestSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ULTIMATE_SWORD_ART_TEMPEST);
                                                int correctTempestSkillLVL = UltimateSwordArtTempestLevel - 1;
                                                float tempestSkillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity2);
                                                float tempestAoe = tempestSkillData.getEffectArea().get(correctTempestSkillLVL).floatValue() * 0.5f;
                                                float tempestUpwardSlashDamageBase = tempestSkillData.getDamageList_02().get(correctTempestSkillLVL).floatValue();
                                                float tempestUpwardSlashDamageMultiplier = tempestSkillData.getDamageMultiplierList_02().get(correctTempestSkillLVL).floatValue();
                                                float tempestUpwardSlashDmg = (float) ((tempestUpwardSlashDamageMultiplier * getEntityAttackDamage(entity2) + tempestUpwardSlashDamageBase) * tempestSkillDamageMultiplier) * ZenithSwordArtTempestDamageMultiplier;

                                                float tempestUpwardSlashScale = tempestAoe * 0.75f;

                                                Vector3f targetCenterLoc = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityOriginalPos, tempestAoe + 2f, triggeredEntityHorizontalFacingAngle);

                                                GeoModelSpawnMethods.GenerateCustomFadingCloneEntityWithTargetLoc(entity2, triggeredEntityHorizontalFacingAngle, 0, 1, triggeredEntityOriginalPos.x(), triggeredEntityOriginalPos.y(), triggeredEntityOriginalPos.z(), triggeredEntityOriginalPos.x(), triggeredEntityOriginalPos.y(), triggeredEntityOriginalPos.z(), -1,
                                                        255, 255, 224, Blademaster025.BLADEMASTER025_CLONE_ALPHA, 80,
                                                        GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, 8, 8, 64, true, true, Blademaster025.BLADEMASTER025_TEMPEST_UPWARD_SLASH_ANIMATION, 2f, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                                        DfoSwordmanSkillTreeConstants.NO_ANIMATION, true, false, true);

                                                Entity tempTempestEffEntity = GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256(entity2, triggeredEntityHorizontalFacingAngle, tempestUpwardSlashScale, targetCenterLoc.x(), targetCenterLoc.y() + 2 * tempestUpwardSlashScale, targetCenterLoc.z(), 12,
                                                        Blademaster025.BLADEMASTER025_UPWARD_SLASH_TEXTURE, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 6, 6, 6, true, true, true, false,  null, 777,false);

                                                int tempestStartTicks = 11;
                                                int tempestEndTicks = 15;
                                                String tempestUpwardSlashStrID = BLADEMASTER022_CHARGE_SLASH_UPWARD + Blademaster025.SkillID + getEntityTickCount(entity2);
                                                MethodAction tempestUpwardSlashAction = DfoSwordmanSkillMethods.generateCommonSwdDamageActionWithDurationWithRange(targetCenterLoc, tempestAoe, targetCenterLoc.y(), targetCenterLoc.y() + Blademaster025.BLADEMASTER025_TEMPEST_MAX_AIR_HEIGHT, weaponType, tempestStartTicks, tempestEndTicks, tempestUpwardSlashDmg, tempTempestEffEntity, DfoSwdSoundMethods::PlayBlademaster025TempestUpwardSlashSound, condition, 0.35f, 1.2f, true, 2, tempestUpwardSlashStrID, false, null);

                                                SBSTickEventMethods.setMethodActionTimer(entity2, tempestUpwardSlashStrID, 2, tempestUpwardSlashAction);
                                            }
                                        }
                                    }
                                    else {
                                        Integer channelingTotalTicks;
                                        int channelingTotalTicksPrim;
                                        boolean shouldInterrupt = false;
                                        int channelingWeaponType;
                                        //the place where start channeling
//                                        TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity1, endDmgTicks, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);
                                        if (!ServerSkillMethods.isLivingEntityInChannelingAction(entity2) || (shouldInterrupt = ( (channelingWeaponType = DfoSkillTreeItemMethods.getWeaponType(entity2)) != DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON && channelingWeaponType != DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO ) ) ) {
                                            SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                                            if (shouldInterrupt) {
                                                if (entity2 instanceof ServerPlayer sp1) {
                                                    NetworkingFetchMsgMethods.FetchPlayerResetLastKeyActionFromServer(sp1, SkillID);
                                                }
                                            }
                                        } else if ((channelingTotalTicks = BLADEMASTER022_HashMapChannelingTotalTicks.get(entity2)) != null && updatedTick <= (channelingTotalTicksPrim = channelingTotalTicks) + endDmgTicks + delayTicks) {
                                            TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 30, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);
                                            BLADEMASTER022_HashMapChannelingTotalTicks.put(entity2, channelingTotalTicksPrim + actionPeriod);

                                            if (channelingTotalTicksPrim == 7) {
                                                DfoSwdSoundMethods.PlayMaxChannelingReadySound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1f);
                                            }
                                            if (!channelingSpawnEffect.getBooleanVal()) {
                                                if (entity2 instanceof ServerPlayer sp1) {
                                                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER022_ANIMATION_SLASH_DOWNWARD_CHANNELING);
                                                }
                                                channelingSpawnEffect.setBooleanVal(true);
                                                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity2, 255, 255, 255, 1f, 0, 1.4f, 0, 12, BLADEMASTER022_CHARGE_ENERGY_CHARGE_EFF, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                                        DfoSwordmanSkillTreeConstants.NO_ANIMATION,12, 0, 12, false, false, false, DfoSwordmanSkillMethods.RemoveChannelingEffEntity(entity2), 2, true);
                                            }
                                        }
                                    }
                                }



                                //update val
                                tempTickCounter.setIntCounter(updatedTick + actionPeriod);
                            };
                    //Actioning ends
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

                    //start loop actioning with fixed period ticks
                    SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
                };



        //channeling final action
        MethodAction tempChannelingFinalAction = (entity1) -> {
            int channelingTotalTicks = BLADEMASTER022_HashMapChannelingTotalTicks.get(entity1);
            Boolean shouldFreeKeyboard = BLADEMASTER022_HashMapShouldFreeKeyboard.get(entity1);
            BLADEMASTER022_HashMapChannelingTotalTicks.remove(entity1);
            BLADEMASTER022_HashMapShouldFreeKeyboard.remove(entity1);
//            printInGameMsg("channelingTotalTicks: "+channelingTotalTicks);

            incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, -1);
            if (entity1 instanceof ServerPlayer sp1 && shouldFreeKeyboard.equals(DfoSwordmanSkillTreeConstants.CONSTANT_BOOLEAN_TRUE)) {
                DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
            }
            //slash downward start
            if (channelingTotalTicks >= 1) {
                int playerSkillLVL = 0;
                float aoeRange;
                float damageBase;
                float damageMultiplier;
                float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

                boolean isFullCharged = channelingTotalTicks >= 7;

                boolean shouldActivateAddtionalDownwardSlash = false;

                float BludgeonMasteryShockwaveAttackDamageMultiplier = 0f;


                float ZanbatoMasteryAttackDamageMultiplier = 0f;

                MethodAction damagedGroupAction = null;


                int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);
                boolean isSlowBludgeon = weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON;
                boolean isSlowestZanbato = weaponType == DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO;

                SkillDataJson skill1 = getID2SkillData().get(SkillID);

                float ExtraDamageMultiplier = 0;
                if (entity1 instanceof ServerPlayer sp1) {
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER022_ANIMATION_SLASH_DOWNWARD);

                    if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                        playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                        if (playerSkillLVL <= 0) { return; }
                        playerSkillLVL = playerSkillLVL - 1;
                    }

                    int otherSkillLevel, correctSkillLevel;
                    SkillDataJson skillData;

                    if (isSlowBludgeon) {
                        if ((otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY)) >= Blademaster011.BLADEMASTER011_BLUDGEON_MASTERY_BONUS_EFFECT_3_LVL) {
                            shouldActivateAddtionalDownwardSlash = true;
                            correctSkillLevel = otherSkillLevel - 1;
                            skillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY);
                            BludgeonMasteryShockwaveAttackDamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel).floatValue();

                            int stunDuration = (int) (skillData.getDuration().get(correctSkillLevel).floatValue() * 20) * 3;
                            damagedGroupAction = DfoStatusGeneralMethods.AppliesStunAction(entity1, stunDuration);
                        }
                    }
                    else if (isSlowestZanbato) {
                        if ((otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY)) >= Blademaster010.BLADEMASTER010_ZANBATO_MASTERY_BONUS_EFFECT_3_LVL) {
                            shouldActivateAddtionalDownwardSlash = true;
                            correctSkillLevel = otherSkillLevel - 1;
                            skillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);
                            ZanbatoMasteryAttackDamageMultiplier = ZanbatoMasteryAttackDamageMultiplier + skillData.getDamageMultiplierList_02().get(correctSkillLevel).floatValue();
                            if (otherSkillLevel >= 9) {
                                ZanbatoMasteryAttackDamageMultiplier = ZanbatoMasteryAttackDamageMultiplier + skillData.getDamageMultiplierList_03().get(correctSkillLevel).floatValue();
                            }
                            float ZanbatoArmorDestructionPercent = skillData.getValueMultiplierList_01().get(correctSkillLevel).floatValue();
                            int ZanabatoArmorDestructionDuration = (int) (skillData.getValueMultiplierList_02().get(correctSkillLevel).floatValue() * 20);

                            damagedGroupAction = Blademaster022_ZanbatoAppliesArmorDestructionAction(ZanbatoArmorDestructionPercent, ZanabatoArmorDestructionDuration);
                        }
                    }

                    int EXChargeUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_CHARGE_UPGRADE);
                    if (EXChargeUpgradeLVL > 0) {
                        SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_CHARGE_UPGRADE);
                        ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXChargeUpgradeLVL - 1).floatValue();
                    }
                }

                if (shouldActivateAddtionalDownwardSlash) {
                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;
//                    TimedAttributesDistributor.applyTimedArmorToEntity();
                    if (isFullCharged && isSlowestZanbato) {
                        damageMultiplier = damageMultiplier + ZanbatoMasteryAttackDamageMultiplier;
                    }

                    Predicate<Entity> condition;
                    float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                    float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                    float damageTickSpeedMultiplier = entityAnimationArr[1];

                    //play animation
                    if (entity1 instanceof ServerPlayer sp1) {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER022_ANIMATION_SLASH_DOWNWARD, entityAnimationSpeedMultiplier);
                        condition = defaultSkillTargetCondition(sp1);
                    } else {
                        condition = defaultSkillTargetCondition();
                    }

                    int startDmgTicks = (int) (2 * damageTickSpeedMultiplier);
                    int endDmgTicks = (int) (6 * damageTickSpeedMultiplier);

                    float finalAoeRange = aoeRange;
                    float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);

                    int actionPeriod = 1;

//                    Entity tempEffEntity = GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity1, getEntityHorizontalFacingDeg(entity1), 0f, 1, 255, 255, 255, 0f, 1.4f, 0f, Math.max(18, endDmgTicks - startDmgTicks),
//                            GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, ParticleRegistry.CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, false);

                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, EntityMethods.getEntityHorizontalFacingDeg(entity1), true, 1f, 0, 2.1f, 0, Math.max(5, endDmgTicks - startDmgTicks ), BLADEMASTER022_CHARGE_SLASH_DOWNWARD, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Z_36, 3, 3, 5, true, true, true, false, null, 777, true);

                    Entity tempEffEntity = GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity1, 255, 255, 255, 1f, 0, 2.1f, 0, Math.max(5, endDmgTicks - startDmgTicks), BLADEMASTER022_CHARGE_SLASH_DOWNWARD_EFF, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Z_48,
                            DfoSwordmanSkillTreeConstants.NO_ANIMATION,5, 0, 5, false, false, true, null, 777, false);

                    MethodAction slashDownwardAction = DfoSwordmanSkillMethods.generateCommonSwdDamageActionWithDuration(weaponType, startDmgTicks, endDmgTicks, finalAoeRange * 1.5f, finalDmg, tempEffEntity, DfoSwdSoundMethods.getPlayWeaponTypeWieldingSoundAction(weaponType), condition, 80f, 0.3f, 0f, true, actionPeriod, BLADEMASTER022_CHARGE_SLASH_DOWNWARD_ADDITIONAL_ATTACK_STR_ID, false, damagedGroupAction);
                    SBSTickEventMethods.setMethodActionTimer(entity1, BLADEMASTER022_CHARGE_SLASH_DOWNWARD_ADDITIONAL_ATTACK_STR_ID, actionPeriod, slashDownwardAction);

                    if (isFullCharged && isSlowBludgeon) {
                        Vector3f tempTriggeredEntityPosVec = Vector3fMethods.PolarProjectionFromVecHorizon(getEntityPos(entity1), 1.6f, getEntityHorizontalFacingDeg(entity1));
                        MethodAction slashDownwardBludgeonShockwaveAction = DfoSwordmanSkillMethods.generateCommonSwdDamageActionOneTimeWithXYZDelayTick(tempTriggeredEntityPosVec.x(), tempTriggeredEntityPosVec.y(), tempTriggeredEntityPosVec.z(), weaponType, endDmgTicks, finalAoeRange * 1.5f, finalDmg * (1f + BludgeonMasteryShockwaveAttackDamageMultiplier), null, DfoSwdSoundMethods::PlayBludgeonStabHitSound, condition, 80f, 0.3f, 0f, true, actionPeriod, BLADEMASTER022_CHARGE_SLASH_DOWNWARD_ADDITIONAL_ATTACK_BLUDGEON_SHOCKWAVE_STR_ID, false, DfoSwordmanSkillMethods.BludgeonShockwaveEffMethodAction(tempTriggeredEntityPosVec, finalAoeRange * 1.5f), damagedGroupAction);
                        SBSTickEventMethods.setMethodActionTimer(entity1, BLADEMASTER022_CHARGE_SLASH_DOWNWARD_ADDITIONAL_ATTACK_BLUDGEON_SHOCKWAVE_STR_ID, actionPeriod, slashDownwardBludgeonShockwaveAction);
                    }
                }
            }
        };

        tempSkillData.setChannelingSkillAction1(tempChannelingAction);

        tempSkillData.setActiveSkillAction1(tempChannelingFinalAction);


        //=============================================
        //IMPORTANT: clear value from hashmap once an entity's action is complete
//        MethodAction tempSkillCleanAction = (entity1) -> {
//            BLADEMASTER022_HashMapPassedTicks.remove(entity1);
//        };
//
//        tempSkillData.setClearSkillAction(tempSkillCleanAction);
    }

    public static MethodAction Blademaster022_ZanbatoAppliesArmorDestructionAction(final float armorReductionMultiplier, final int totalTicks) {
        return (target) -> {
            DfoStatusGeneralMethods.GenerateEffBuffEntity(target, totalTicks, DfoStatusGeneralMethods.ARMOR_DEBUFF_TEXTURE, DfoStatusGeneralMethods.EFF_ENTITY_MOVING_DOWN, DfoStatusGeneralMethods.ARMOR_DEBUFF_TEXTURE);
            TimedAttributesDistributor.applyTimedArmorToEntity(target, totalTicks, -armorReductionMultiplier, Constants.OP_MULTIPLY_BASE, false, SkillID, null, null);
        };
    }
}
