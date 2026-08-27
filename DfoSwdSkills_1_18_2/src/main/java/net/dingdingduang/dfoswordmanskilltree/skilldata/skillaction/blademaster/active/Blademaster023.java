package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone.SwdFadingCloneCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.custom.TwoDAnimated256TexCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.BlockMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster003;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster008;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster036;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.FloatValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.TypeValueHolder;
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
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import modified.org.joml.Vector3f;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization.BLADEMASTER_SHORT_SWORD_MASTERY;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getClientPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.getSkillsInCooldownClientTimerOverlayInstance;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster023 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_DRAGON_SLASH;

    public static final String[] BLADEMASTER023_CONSECUTIVE_SLASH_ANIM = {"sword_act2_slash", "sword_act_katana_slash1", "sword_act_katana_slash2", "sword_act_katana_slash3", "sword_act1", "sword_act_katana_slash4"};
    public static final float[] BLADEMASTER023_CONSECUTIVE_SLASH_ROT_L = {300f, 255f, 75f, 255f, 90f, 5f};
    public static final float[] BLADEMASTER023_CONSECUTIVE_SLASH_ROT_R = {225f, 285f, 15f, 285f, 45f, 225f};

    public static final String BLADEMASTER023_DRAGON_FACE_TEXUTRE = "textures/entity/skilleff/blademaster/dragon_slash/dragon_face/";
    public static final String BLADEMASTER023_SPEED_LINE_TEXUTRE = "textures/entity/skilleff/blademaster/dragon_slash/speed_line/";
//    public static final String BLADEMASTER023_DASH_DIRT_TEXUTRE = "textures/entity/skilleff/blademaster/dragon_slash/dash_eff/";
    public static final String BLADEMASTER023_SHORT_SWORD_TORNADO_TEXUTRE = "textures/entity/skilleff/blademaster/dragon_slash/tornado/";

    //tornado constants
    private static final float distShouldTravelDecay = 0.001f;
    private static final float presetDist1 = 0.5f;
    private static final int maxLifetime = 200;
    private static final float interval = 1f * maxLifetime * 0.02f;
    private static final float tornadoAoeRange = 3f;

    //Server
    public static HashMap<LivingEntity, Integer> BLADEMASTER023_HashMapTriggeredTimes;
    public static HashMap<LivingEntity, Integer> BLADEMASTER023_HashMapTotalDelayTicks;

    //Client
    public static int BLADEMASTER023_ClientHashMapTriggeredTimes;

    public static final String BLADEMASTER_023_FADINGCLONE_CLIENT_ACTION = "BLADEMASTER_023_FADINGCLONE_CLIENT_ACTION";
    public static final String BLADEMASTER_023_TORNADO_MOVEMENT_CLIENT_ACTION = "BLADEMASTER_023_TORNADO_MOVEMENT_CLIENT_ACTION";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);
        Double UpperSlashDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double UpperSlashConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);
        Float UpperSlashFinalDmg = (float) ( (UpperSlashDamageMultiplier * getEntityAttackDamage(getClientPlayer()) + UpperSlashConstantDamage) * skillDamageMultiplier);
        int ConsecutiveTimes = skillData.getValueUsage_01().get(correctSkillLevel).intValue();

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (UpperSlashDamageMultiplier.floatValue() * 100)),
                String.format("%s", UpperSlashConstantDamage),
                String.format("%s", UpperSlashFinalDmg),
                String.format("%d", ConsecutiveTimes)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster023::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement(
                (SkillID2) -> {
                    boolean isInCD = isCPlayerSkillInCD(SkillID2);
                    boolean isInAction = isPlayerInAction();
                    boolean isChanneling = isPlayerInChannelingState();
                    boolean isOnGround = isPlayerOnGround();
                    boolean isPlayerBeingHit = isPlayerBeingHit();

                    boolean isPassed = !isInAction && !isChanneling && isOnGround && !isPlayerBeingHit;
                    if (isPassed) {
                        if (isInCD) {
                            int ClientPlayerWeaponType = DfoSkillTreeItemMethods.getWeaponType(getClientPlayer());
                            int currentSkillLevel = getClientPlayerSkillID2lvlMap().get(BlademasterSkillTreeInitialization.BLADEMASTER_CONSECUTIVE_SLASH);
                            int consecutive_TotalSlashCount = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_CONSECUTIVE_SLASH).getValueUsage_01().get(currentSkillLevel - 1).intValue();
                            int additionalAttacks = 0;
                            int otherSkillLevel;
                            //is wielding lightsabre
                            if (ClientPlayerWeaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE && (otherSkillLevel = ClientSkillMethods.queryCPlayerCurrentSkillLevel(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY)) >= Blademaster003.BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_4_LVL) {
                                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY).getDamageList_03().get(otherSkillLevel - 1).intValue();
                            }
//                            else if (ClientPlayerWeaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA && (otherSkillLevel = ClientSkillMethods.queryCPlayerCurrentSkillLevel(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY)) >= 5) {
//                                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY).getDamageList_01().get(otherSkillLevel - 1).intValue();
//                            }

                            //ex bonus
                            int EXRagingDragonSlashUpgradeLVL = ClientSkillMethods.queryCPlayerCurrentSkillLevel(BlademasterSkillTreeInitialization.EXBLADEMASTER_RAGING_DRAGON_SLASH_UPGRADE);
                            if (EXRagingDragonSlashUpgradeLVL > 0) {
                                SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_RAGING_DRAGON_SLASH_UPGRADE);
                                additionalAttacks = additionalAttacks + EX_SkillData.getValueUsage_01().get(EXRagingDragonSlashUpgradeLVL - 1).intValue();
                            }

                            HashMap<String, Integer> clientCD = getSkillsInCooldownClientTimerOverlayInstance().getPlayerSkill2CooldownTicks();
                            if (clientCD.get(SkillID2) > 30 || BLADEMASTER023_ClientHashMapTriggeredTimes > consecutive_TotalSlashCount + additionalAttacks) {
                                isPassed = false;
                            }
                            else {
                                BLADEMASTER023_ClientHashMapTriggeredTimes++;
                            }
                        }
                        else {
                            BLADEMASTER023_ClientHashMapTriggeredTimes = 1;
                        }
                    }
                    return isPassed;
                }
        );
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);

        if (isClientSide()) {
            BLADEMASTER023_ClientHashMapTriggeredTimes = 0;
            return;
        }

        //server init
        if (BLADEMASTER023_HashMapTriggeredTimes == null) {
            BLADEMASTER023_HashMapTriggeredTimes = new HashMap<>();
            BLADEMASTER023_HashMapTotalDelayTicks = new HashMap<>();
        }



        //====================================
        //setup skill action
        MethodAction tempSkillAction =
                (entity1) -> {
                    int playerSkillLVL = 0;
                    float aoeRange;
                    float damageBase, damageUpwardSlashBase;
                    float damageMultiplier, damageUpwardSlashMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

                    //ASSUME BLADEMASTER023_HashMap never be null
                    int triggeredTimes;
                    if (BLADEMASTER023_HashMapTriggeredTimes.containsKey(entity1)) {
                        triggeredTimes = BLADEMASTER023_HashMapTriggeredTimes.get(entity1) + 1;
                        BLADEMASTER023_HashMapTriggeredTimes.put(entity1, triggeredTimes);
                    }
                    else {
                        BLADEMASTER023_HashMapTriggeredTimes.put(entity1, 1);
                        triggeredTimes = 1;
                    }
                    if (!BLADEMASTER023_HashMapTotalDelayTicks.containsKey(entity1)) {
                        BLADEMASTER023_HashMapTotalDelayTicks.put(entity1, 20);
                    }
                    //IMPORTANT! setup action on to prevent casting other skills while this action
                    //being performed
                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);
                    int additionalAttacks = 0;
                    int otherSkillLevel;
                    boolean isWieldingShortSword = weaponType == DfoSkillTreeItemMethods.IS_AVERAGE_SHORT_SWORD;
                    boolean isWieldingLightsabre = weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE;
                    int tornadoCount = 0;

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    float ExtraDamageMultiplier = 0;

                    if (entity1 instanceof ServerPlayer sp1) {
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                        }

                        //is wielding lightsabre
                        if (isWieldingLightsabre && (otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY)) >= Blademaster003.BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_4_LVL) {
                            additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY).getDamageList_03().get(otherSkillLevel - 1).intValue();
                        }
//                            else if (weaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA && (otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY)) >= 9) {
//                                additionalAttacks = additionalAttacks + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY).getDamageList_01().get(otherSkillLevel - 1).intValue();
//                            }

                        //shortsword tornado
                        if (isWieldingShortSword && (otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BLADEMASTER_SHORT_SWORD_MASTERY)) >= Blademaster008.BLADEMASTER008_SHORT_SWORD_MASTERY_BONUS_EFFECT_4_LVL) {
                            tornadoCount = tornadoCount + getID2SkillData().get(BLADEMASTER_SHORT_SWORD_MASTERY).getValueMultiplierList_02().get(otherSkillLevel - 1).intValue();
                        }

                        otherSkillLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART);
                        if (otherSkillLevel >= Blademaster036.BLADEMASTER036_ZENITH_OF_SWORD_ART_BONUS_EFFECT_2_LVL) {
                            tornadoCount = tornadoCount + getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART).getValueUsage_02().get(otherSkillLevel - 1).intValue();
                        }

                        int EXRagingDragonSlashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_RAGING_DRAGON_SLASH_UPGRADE);
                        if (EXRagingDragonSlashUpgradeLVL > 0) {
                            SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_RAGING_DRAGON_SLASH_UPGRADE);
                            ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXRagingDragonSlashUpgradeLVL - 1).floatValue();
                            additionalAttacks = additionalAttacks + EX_SkillData.getValueUsage_01().get(EXRagingDragonSlashUpgradeLVL - 1).intValue();
                        }
                    }
                    //return?
                    int total_slashCount = skill1.getValueUsage_01().get(playerSkillLVL).intValue() + additionalAttacks;
                    //if client trigger times dont match server side
                    if (triggeredTimes > total_slashCount) {
                        BLADEMASTER023_HashMapTotalDelayTicks.put(entity1, BLADEMASTER023_HashMapTotalDelayTicks.get(entity1) + 30);
                        return;
                    }

                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;
                    damageUpwardSlashBase = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
                    damageUpwardSlashMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    Predicate<Entity> condition;
                    float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                    float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                    float damageTickSpeedMultiplier = entityAnimationArr[1];
                    int consecutive_slashArr_index = 0;

                    IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);

                    //play animation
                    if (entity1 instanceof ServerPlayer sp1) {
                        if (triggeredTimes == 1) {
                            //play anim from noaction state
                            consecutive_slashArr_index = BLADEMASTER023_CONSECUTIVE_SLASH_ANIM.length - 2;
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER023_CONSECUTIVE_SLASH_ANIM[consecutive_slashArr_index], entityAnimationSpeedMultiplier);
                        }
                        else {
                            if (triggeredTimes == total_slashCount) {
                                //upper slash
                                consecutive_slashArr_index = BLADEMASTER023_CONSECUTIVE_SLASH_ANIM.length - 1;
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER023_CONSECUTIVE_SLASH_ANIM[consecutive_slashArr_index], entityAnimationSpeedMultiplier);
                            }
                            else {
                                consecutive_slashArr_index = (triggeredTimes - 1) % 4;
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER023_CONSECUTIVE_SLASH_ANIM[consecutive_slashArr_index], entityAnimationSpeedMultiplier);
                            }
                        }
                        condition = defaultSkillTargetCondition(sp1);
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }

                    int startDmgTicks = (int) (2 * damageTickSpeedMultiplier);
                    int endDmgTicks = (int) (8 * damageTickSpeedMultiplier);
//                    int delayTicks = 20;
                    int delayTicks = BLADEMASTER023_HashMapTotalDelayTicks.get(entity1);
                    float finalAoeRange = aoeRange;
                    float finalDist = skill1.getDistance().get(playerSkillLVL).floatValue();
                    float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
                    float finalUpperSlashDmg = (float) ((damageUpwardSlashMultiplier * getEntityAttackDamage(entity1) + damageUpwardSlashBase) * skillDamageMultiplier);
                    int actionPeriod = 2;
//                    int actionPeriod = 1;
                    float finalDistShouldTravelEachTime = finalDist / Math.max(1, endDmgTicks - startDmgTicks);
                    int finalConsecutiveSlashArrIndex = consecutive_slashArr_index;


                    float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity1);
                    //move pos
//                    Vector3f tempVec = new Vector3f(
//                            Vector3fMethods.PolarProjectionFromXHorizon(1, triggeredEntityHorizontalFacingAngle),
//                            0,
//                            Vector3fMethods.PolarProjectionFromZHorizon(1, triggeredEntityHorizontalFacingAngle));
//
//                    setEntitySPDeltaMovement(entity1, getEntityDeltaMovement(entity1).add(tempVec.x(), 0f, tempVec.z()), true);

                    if (triggeredTimes == 1) {
                        DfoSwdSoundMethods.PlayDragonStartSlashSound(getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.8f, 1.0f);
                    }
                    else if (triggeredTimes == total_slashCount) {
                        DfoSwdSoundMethods.PlayDragonEndSlashSound(getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.8f, 1.0f);
                    }

                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, triggeredEntityHorizontalFacingAngle, true, 1f, 0, 1.6f, 0, endDmgTicks + 1, BLADEMASTER023_DRAGON_FACE_TEXUTRE, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Z_24, 3, 2, 2, false, false, false, false, null, 777, true);
                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, triggeredEntityHorizontalFacingAngle, true, 1f, 0, 1.6f, 0, endDmgTicks + 1, BLADEMASTER023_DRAGON_FACE_TEXUTRE, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_XP_8, 3, 2, 2, false, false, false, false, null, 777, true);
                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, triggeredEntityHorizontalFacingAngle, true, 1f, 0, 1.6f, 0, endDmgTicks + 1, BLADEMASTER023_DRAGON_FACE_TEXUTRE, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_XN_8, 3, 2, 2, false, false, false, false, null, 777, true);
                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, triggeredEntityHorizontalFacingAngle, true, 1f, 0, 1.6f, 0, endDmgTicks + 1, BLADEMASTER023_SPEED_LINE_TEXUTRE, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 1, 1, 4, false, false, false, true, null, 777, true);
//                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity1, triggeredEntityHorizontalFacingAngle, 1f, 0, 2f, 0, endDmgTicks + 1, BLADEMASTER023_DASH_DIRT_TEXUTRE, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 1, 1, 8, false, false, false, false, true);

                    for (int i = 0; i < tornadoCount; i++) {
                        Blademaster023_GenerateTornado(entity1, condition, finalDmg * 0.5f, "tornadotest" + ExtraMathMethods.randomInt(1000));
                    }

//                    int fadingCloneInterval = (int) ((startDmgTicks) * 0.5f);
                    for (int i = 0; i <= endDmgTicks + 1; i = i + startDmgTicks) {
                        GeoModelSpawnMethods.GenerateCustomFadingCloneEntity(entity1, triggeredEntityHorizontalFacingAngle, 0, 1, 255, 255, 224, 0, 0, 0, 0, 40 + startDmgTicks + i,
                                GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, true, 4, 8, 8, true, true, BLADEMASTER023_CONSECUTIVE_SLASH_ANIM[finalConsecutiveSlashArrIndex], entityAnimationSpeedMultiplier, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                BLADEMASTER_023_FADINGCLONE_CLIENT_ACTION, true, false, true);
                    }

                    HashSet<LivingEntity> damagedGroup = new HashSet<>();
                    BooleanValueHolder spawnEffect = new BooleanValueHolder(false);
                    BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);

                    MethodAction action =
                            (entity2) ->  {
                                boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                                if (isActionInterrupted) {
                                    BLADEMASTER023_HashMapTriggeredTimes.put(entity2, DfoSwordmanSkillTreeConstants.CONSTANT_INTEGER_777);
                                }

                                int updatedTick = ElapsedTicks.getIntCounter();

                                if (!isActionInterrupted && updatedTick >= startDmgTicks && updatedTick <= endDmgTicks) {
                                    TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 40, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);

                                    float entity2FacingAngle = getEntityHorizontalFacingDeg(entity2);
                                    //spawn model
                                    if (!spawnEffect.getBooleanVal()) {
                                        DfoSwdSoundMethods.PlayDragonNormalSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                        spawnEffect.setBooleanVal(true);
                                        //play skill sound at target location
                                        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
//                                        GeoModelSpawnMethods.GenerateYellowSlash(entity2, getEntityHorizontalFacingDeg(entity2), 135f);
//                                            GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, entity2FacingAngle, 45f, 1, 255, 255, 255, 0f, 1.4f, 0f, 180,
//                                                    GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 5, 1, 12, true, true, "slash_start");
                                        final int[] slashColorArr = BlademasterSkillTreeInitialization.BLADEMASTER_SLASH_COLOR[weaponType + 1];
                                        GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, entity2FacingAngle, BLADEMASTER023_CONSECUTIVE_SLASH_ROT_L[finalConsecutiveSlashArrIndex], 1, slashColorArr[0], slashColorArr[1], slashColorArr[2], 0f, 1.4f, 0f, Math.max(18, endDmgTicks-startDmgTicks),
                                                GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, ParticleRegistry.CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, true);
                                        GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, entity2FacingAngle, BLADEMASTER023_CONSECUTIVE_SLASH_ROT_R[finalConsecutiveSlashArrIndex], 1, slashColorArr[0], slashColorArr[1], slashColorArr[2], 0f, 1.4f, 0f, Math.max(18, endDmgTicks-startDmgTicks),
                                                GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, ParticleRegistry.CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, true);

                                        //TODO: Particle
//                                        GeoModelSpawnMethods.GenerateYellowSlash(entity2, entity2FacingAngle, 135f);
                                    }

                                    Vector3f tempVec = new Vector3f(
                                            Vector3fMethods.PolarProjectionFromXHorizon(finalDistShouldTravelEachTime, entity2FacingAngle),
                                            0f,
                                            Vector3fMethods.PolarProjectionFromZHorizon(finalDistShouldTravelEachTime, entity2FacingAngle));

                                    setEntitySPDeltaMovement(entity2, getEntityDeltaMovement(entity2).add(tempVec.x(), 0f, tempVec.z()), false);

                                    //Sector damage cond
                                    Vector3f triggeredEntityPos = getEntityPos(entity2);
                                    float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);
                                    Vector3f tempVecBehindTriggerEntity = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, -0.5f, triggeredEntityFacingAngle);
                                    Predicate<Entity> SectorDmgCond = (entity) -> {
                                        float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontal(tempVecBehindTriggerEntity, getEntityPos(entity)));
                                        float range = 75f; //150 deg

                                        if (!Vector3fMethods.IsAngleDegBetweenAB(sectorDeg, MathMethods.RoundAngle(triggeredEntityFacingAngle + range), MathMethods.RoundAngle(triggeredEntityFacingAngle - range))) {
                                            return false;
                                        }

                                        return DistanceBetweenEntities(entity, entity2) <= finalAoeRange;
                                    };
                                    for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition.and(SectorDmgCond))) {
                                        if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                            if (!damagedGroup.contains(tempLivingEntity)) {
                                                DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, EntityMethods.getEntityLevel(entity2), EntityMethods.getEntityX(entity2), EntityMethods.getEntityY(entity2), EntityMethods.getEntityZ(entity2), 0.8f, 1.0f);
                                                damagedGroup.add(tempLivingEntity);
                                                GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 4f, 4, 4, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, GeoAnimationAndTextureName.EFF_HIT_EFF_ADVANCE_ARR[ExtraMathMethods.randomInt(3)], true, true);

                                                if (triggeredTimes == total_slashCount) {
                                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalUpperSlashDmg, false);
                                                    float pushXZStrength = EntityMethods.calculatePushEntityStrength(finalDistShouldTravelEachTime, entity2, tempLivingEntity);
                                                    float pushYStrength = EntityMethods.calculatePushEntityStrengthTargetOnly(0.65f, tempLivingEntity);
                                                    EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, entity2FacingAngle, pushXZStrength, pushYStrength, true);
                                                }
                                                else {
                                                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
                                                    float pushXZStrength = EntityMethods.calculatePushEntityStrength(finalDistShouldTravelEachTime, entity2, tempLivingEntity);
                                                    EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, entity2FacingAngle, pushXZStrength, 0, true);
                                                }

                                                if (isWieldingLightsabre) {
                                                    DfoStatusGeneralMethods.ApplyShockStatusToTargetLivingEntity(entity2, tempLivingEntity, DfoStatusGeneralMethods.GENERAL_DURATION_TICKS_FOR_SHOCK_STATUS, finalDmg, DfoStatusGeneralMethods.GENERAL_MAX_INFLICT_TIMES_FOR_SHOCK_STATUS, true, SkillID);
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    if (isActionInterrupted) {
                                        SendActionOffPacket.setBooleanVal(true);
                                        incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                                        SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                                    }
                                    else {
                                        if (!SendActionOffPacket.getBooleanVal() && updatedTick >= endDmgTicks + actionPeriod) {
                                            SendActionOffPacket.setBooleanVal(true);
                                            incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
//                                            SendPacketIsInActionToClientSideFromServer(entity2, Constants.ACTION_OFF);
                                        }
                                        if (updatedTick >= endDmgTicks + delayTicks) {
                                            SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                                        }
                                    }
                                }



                                //update val
                                ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
                            };
                    //Actioning ends
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

                    //start loop actioning with fixed period ticks
                    SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
                };

        tempSkillData.setActiveSkillAction1(tempSkillAction);


        //=============================================
        //IMPORTANT: clear value from hashmap once an entity's action is complete
        MethodAction tempSkillCleanAction = (entity1) -> {
            BLADEMASTER023_HashMapTriggeredTimes.remove(entity1);
            BLADEMASTER023_HashMapTotalDelayTicks.remove(entity1);
        };

        tempSkillData.setClearSkillAction(tempSkillCleanAction);
    }

    //tp HououinSeia @e[type=dfoswordmanskilltree:dfoswordtwodtexcustomtwofivesix,sort=nearest,limit=1]
    public static void Blademaster023_GenerateTornado(LivingEntity entity1, Predicate<Entity> cond, float finalDmg, String ActionID) {
        HashSet<Entity> damagedGroup = new HashSet<>();

        FloatValueHolder tornadoRandomFacingAngle = new FloatValueHolder(ExtraMathMethods.randomFloat(360f));
        FloatValueHolder initialAcceleration = new FloatValueHolder(0.1f);
        TypeValueHolder<Entity> targetHolder = new TypeValueHolder<>(null);
        TypeValueHolder<Vector3f> previousTargetLoc = new TypeValueHolder<>(null);
        Predicate<Entity> finalCondition = cond.and((entity -> !entity.equals(entity1) && !damagedGroup.contains(entity)));

        TwoDAnimated256TexCustomEntity tornadoEntity = GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256(entity1, 255, 255, 255, 1f, 0, 1.6f, 0, maxLifetime,
                false, 0, 0, 0, 0, 0, 0, BLADEMASTER023_SHORT_SWORD_TORNADO_TEXUTRE, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                BLADEMASTER_023_TORNADO_MOVEMENT_CLIENT_ACTION, false, 4, 1, 3, false, false, false, null, 777, false);
        tornadoEntity.setHorizontalFacingAngle(tornadoRandomFacingAngle.getFloatVal());
        EntityMethods.addEntityToWorld(getEntityLevel(entity1), tornadoEntity);

        MethodAction tornadoChasingAction = (tornadoOwner) -> {
            if (SBConditions.isEntityAlive(tornadoEntity)) {
                Entity target = targetHolder.getAnyTypeValue();
                if (target != null && !SBConditions.isEntityAlive(target)) { target = null; }
                int currentTornadoLifeTime = maxLifetime - tornadoEntity.getLifetime();
                float distShouldTravelEachTime = initialAcceleration.getFloatVal();
                float polarDist = presetDist1 - distShouldTravelEachTime;
                float tornadoHorizontalFacingAngle = tornadoRandomFacingAngle.getFloatVal();
                initialAcceleration.setFloatVal(distShouldTravelEachTime - (distShouldTravelDecay * interval));

                Vector3f originTornadoLoc = (previousTargetLoc.getAnyTypeValue() == null) ? EntityMethods.getEntityPos(tornadoEntity) : previousTargetLoc.getAnyTypeValue();

                if (target == null) {
                    if (!getEntityLevel(tornadoEntity).getBlockState(new BlockPos((int) originTornadoLoc.x(), (int) (originTornadoLoc.y() - 1.61f), (int) originTornadoLoc.z())).isAir()) {
                        target = EntityMethods.findHorizontalNearbyEntityFromCenterEntityLoc(tornadoEntity, 16, finalCondition);
                        targetHolder.setAnyTypeValue(target);
                    }
                } else {
                    if (Math.abs(EntityMethods.getEntityY(target) - EntityMethods.getEntityY(tornadoEntity) + 1.59f) > 1f) {
                        target = null;
                        targetHolder.setAnyTypeValue(null);
                    }
                }

                int iframeTime;
                for (Entity entity: GroupEnumEntitiesInRangeFilter(tornadoEntity, tornadoAoeRange, 1, tornadoAoeRange, tornadoAoeRange, 1, tornadoAoeRange, finalCondition)) {
                    if (entity.distanceTo(tornadoEntity) <= tornadoAoeRange) {
                        damagedGroup.add(entity);
                        //damage action
                        if (entity instanceof LivingEntity livingEntity) {
                            //damage action
                            iframeTime = GetEntityInvulnerableTime(livingEntity);
                            EntityMethods.SetEntityInvulnerableTime(livingEntity, 0);
                            LivingEntityDamageTarget(tornadoOwner, livingEntity, finalDmg);
                            EntityMethods.SetEntityInvulnerableTime(livingEntity, iframeTime);
                            DfoSwdSoundMethods.PlayBooDongHitSound(getEntityLevel(entity), getEntityX(entity), getEntityY(entity), getEntityZ(entity), 0.7f, 1f);
                        }
                    }
                }

                if (damagedGroup.contains(target)) {
                    target = null;
                    targetHolder.setAnyTypeValue(null);
                }

                Vector3f tpLoc = Vector3fMethods.PolarProjectionFromVecHorizon(originTornadoLoc, (target == null) ? 0.05f : polarDist, tornadoHorizontalFacingAngle);


                float yPos = 0f;

                int blockX = (int) tpLoc.x();
                int blockY = (int) (tpLoc.y() - 1.59f);
                int blockZ = (int) tpLoc.z();
                BlockPos tpLocBlockPos = new BlockPos(blockX, blockY, blockZ);
                BlockState tpLocBlockState = getEntityLevel(tornadoEntity).getBlockState(tpLocBlockPos);

                boolean isStuck = false;
                if (!tpLocBlockState.isAir()) {
                    if (!BlockMethods.isBlockPassable(tpLocBlockState.getBlock())) {
                        if (getEntityLevel(tornadoEntity).getBlockState(new BlockPos(blockX, blockY + 1, blockZ)).isAir()) {
                            yPos = 1f;
                        }
                        //stuck delete entity
                        else {
                            isStuck = true;
                        }
                    }
                } else if (getEntityLevel(tornadoEntity).getBlockState(new BlockPos(blockX, blockY - 1, blockZ)).isAir()) {
                    yPos = -1f;
                }

                if (!isStuck) {
                    moveEntityToXYZ(tornadoEntity, tpLoc.x(), tpLoc.y() + yPos, tpLoc.z());
                    previousTargetLoc.setAnyTypeValue(new Vector3f(tpLoc.x(), tpLoc.y() + yPos, tpLoc.z()));
                }

                if (target != null) {
                    Vector3f targetLoc = EntityMethods.getEntityBlockPos(target);
                    float angleBetweenTornadoAndTarget = Vector3fMethods.AngleFromLocationToLocationHorizontal(originTornadoLoc, targetLoc);

                    float time = 0.01f + 0.99f * currentTornadoLifeTime / maxLifetime;
                    float newHorizontalDegree = MathMethods.RoundAngle(MathMethods.lerpDegreeAngle(time, tornadoHorizontalFacingAngle, angleBetweenTornadoAndTarget));
                    tornadoRandomFacingAngle.setFloatVal(newHorizontalDegree % 360);
                }
            }
            else {
//                printInGameMsg("tornado Action done.");
                SBSTickEventMethods.setSkillActionDone(tornadoOwner, ActionID);
            }
        };
        SBSTickEventMethods.setMethodActionTimer(entity1, ActionID, 1, tornadoChasingAction);
    }

    //client
    public static void Blademaster023_ClientMethodInit(HashMap<String, MethodEntityAction> clientMethodMap) {
        clientMethodMap.put(BLADEMASTER_023_FADINGCLONE_CLIENT_ACTION, Blademaster023::Blademaster023_FadingCloneClientAction);
        clientMethodMap.put(BLADEMASTER_023_TORNADO_MOVEMENT_CLIENT_ACTION, Blademaster023::Blademaster023_ClientTornadoMovement);
    }

    public static void Blademaster023_ClientTornadoMovement(Entity tornado) {
        TwoDAnimated256TexCustomEntity helper = (TwoDAnimated256TexCustomEntity) tornado;
        MethodEntityAction entityAction = helper.getClientMethodEntityLoopAction();
        Entity owner;

        if (entityAction == null) {
            HashSet<Entity> damagedGroup = new HashSet<>();
            Predicate<Entity> clientCond = SBConditions.defaultSkillTargetClientCondition();
            if ((owner = helper.getOwner()) != null) {
                clientCond = clientCond.and(entity -> !entity.equals(owner) && !damagedGroup.contains(entity));
            }
            Predicate<Entity> finalCondition = clientCond;
            entityAction = Blademaster023_ClientTornadoEntityAction(new FloatValueHolder(helper.getHorizontalFacingAngle()), new FloatValueHolder(0.1f), new IntegerValueHolder(-1), new TypeValueHolder<>(null), new TypeValueHolder<>(null), damagedGroup, finalCondition);
            helper.setClientMethodEntityLoopAction(entityAction);
        }

        entityAction.executeAction(tornado);
    }

    public static MethodEntityAction Blademaster023_ClientTornadoEntityAction(FloatValueHolder tornadoRandomFacingAngle, FloatValueHolder initialAcceleration, IntegerValueHolder previousTickCount, TypeValueHolder<Entity> targetHolder, TypeValueHolder<Vector3f> previousTargetLoc, HashSet<Entity> damagedGroup, Predicate<Entity> finalCondition) {
        return (tornado) -> {
            if (SBConditions.isEntityAlive(tornado)) {
//                int currentTornadoLifeTime = EntityMethods.getEntityTickCount(tornado);
                int currentTornadoLifeTime = maxLifetime - ((TwoDAnimated256TexCustomEntity) tornado).getLifetime();
                if (currentTornadoLifeTime != previousTickCount.getIntCounter()) {
                    previousTickCount.setIntCounter(currentTornadoLifeTime);
                    Entity target = targetHolder.getAnyTypeValue();
                    if (target != null && !SBConditions.isEntityAlive(target)) { target = null; }
                    float distShouldTravelEachTime = initialAcceleration.getFloatVal();
                    float polarDist = presetDist1 - distShouldTravelEachTime;
                    float tornadoHorizontalFacingAngle = tornadoRandomFacingAngle.getFloatVal();
                    initialAcceleration.setFloatVal(distShouldTravelEachTime - (distShouldTravelDecay * interval));

                    Vector3f originTornadoLoc = (previousTargetLoc.getAnyTypeValue() == null) ? EntityMethods.getEntityPos(tornado) : previousTargetLoc.getAnyTypeValue();

                    if (target == null) {
                        if (!getEntityLevel(tornado).getBlockState(new BlockPos((int) originTornadoLoc.x(), (int) (originTornadoLoc.y() - 1.61f), (int) originTornadoLoc.z())).isAir()) {
                            target = EntityMethods.findHorizontalNearbyEntityFromCenterEntityLoc(tornado, 16, finalCondition);
                            targetHolder.setAnyTypeValue(target);
                        }
                    } else {
                        if (Math.abs(EntityMethods.getEntityY(target) - EntityMethods.getEntityY(tornado) + 1.59f) > 1f) {
                            target = null;
                            targetHolder.setAnyTypeValue(null);
                        }
                    }

                    for (Entity entity : GroupEnumEntitiesInRangeFilter(tornado, tornadoAoeRange, 1, tornadoAoeRange, tornadoAoeRange, 1, tornadoAoeRange, finalCondition)) {
                        if (entity.distanceTo(tornado) <= tornadoAoeRange) {
                            damagedGroup.add(entity);
                            //damage action
                        }
                    }

                    if (damagedGroup.contains(target)) {
                        target = null;
                        targetHolder.setAnyTypeValue(null);
                    }

                    Vector3f tpLoc = Vector3fMethods.PolarProjectionFromVecHorizon(originTornadoLoc, (target == null) ? 0.05f : polarDist, tornadoHorizontalFacingAngle);


                    float yPos = 0f;

                    int blockX = (int) tpLoc.x();
                    int blockY = (int) (tpLoc.y() - 1.59f);
                    int blockZ = (int) tpLoc.z();
                    BlockPos tpLocBlockPos = new BlockPos(blockX, blockY, blockZ);
                    BlockState tpLocBlockState = getEntityLevel(tornado).getBlockState(tpLocBlockPos);

                    boolean isStuck = false;
                    if (!tpLocBlockState.isAir()) {
                        if (!BlockMethods.isBlockPassable(tpLocBlockState.getBlock())) {
                            if (getEntityLevel(tornado).getBlockState(new BlockPos(blockX, blockY + 1, blockZ)).isAir()) {
                                yPos = 1f;
                            }
                            //stuck delete entity
                            else {
                                isStuck = true;
                            }
                        }
                    } else if (getEntityLevel(tornado).getBlockState(new BlockPos(blockX, blockY - 1, blockZ)).isAir()) {
                        yPos = -1f;
                    }

                    if (!isStuck) {
                        TwoDAnimated256TexCustomEntity twoDTornado = (TwoDAnimated256TexCustomEntity) tornado;
                        twoDTornado.setTargetLoc(originTornadoLoc.x(), originTornadoLoc.y() + yPos, originTornadoLoc.z(),
                                tpLoc.x(), tpLoc.y() + yPos, tpLoc.z(), true);
                        twoDTornado.setIsBoundToEntity(true);

//                        moveEntityToXYZ(tornado, tpLoc.x(), tpLoc.y() + yPos, tpLoc.z());
                        previousTargetLoc.setAnyTypeValue(new Vector3f(tpLoc.x(), tpLoc.y() + yPos, tpLoc.z()));
                    }


                    if (target != null) {
                        Vector3f targetLoc = EntityMethods.getEntityBlockPos(target);
                        float angleBetweenTornadoAndTarget = Vector3fMethods.AngleFromLocationToLocationHorizontal(originTornadoLoc, targetLoc);

                        float time = 0.01f + 0.99f * currentTornadoLifeTime / maxLifetime;
                        float newHorizontalDegree = MathMethods.RoundAngle(MathMethods.lerpDegreeAngle(time, tornadoHorizontalFacingAngle, angleBetweenTornadoAndTarget));
                        tornadoRandomFacingAngle.setFloatVal(newHorizontalDegree % 360);
                    }
                }
            }
        };
    }

    public static void Blademaster023_FadingCloneClientAction(Entity entity) {
        if (entity instanceof SwdFadingCloneCustomEntity swdFadingCloneCustomEntity) {
//            float ratio = 1f * (swdFadingCloneCustomEntity.getMaxLifetime() - swdFadingCloneCustomEntity.getLifetime()) / swdFadingCloneCustomEntity.getMaxLifetime();
            if (swdFadingCloneCustomEntity.getLifetime() == 40) {
                float animationSpeedModifier = swdFadingCloneCustomEntity.getGeoAnimationSpeedModifier();
                int currentAnimationTick = (int) (animationSpeedModifier * (swdFadingCloneCustomEntity.getMaxLifetime() - swdFadingCloneCustomEntity.getLifetime()));
                swdFadingCloneCustomEntity.setColorAlpha(64);
                swdFadingCloneCustomEntity.setIsBoundToEntity(false);
                float pausedAnimationSpeedModifier = 0.0001f;
                swdFadingCloneCustomEntity.setGeoAnimationSpeedModifier(pausedAnimationSpeedModifier);
                float offset = (currentAnimationTick) * (1f / pausedAnimationSpeedModifier);
                swdFadingCloneCustomEntity.getAnimationController().setTickOffset(-offset);
                Entity owner = swdFadingCloneCustomEntity.getOwner();
                if (owner != null) {
                    EntityMethods.setEntityHorizontalFacingDeg(swdFadingCloneCustomEntity, EntityMethods.getEntityHorizontalFacingDeg(owner));
                }
                swdFadingCloneCustomEntity.setClientActionName(DfoSwordmanSkillTreeConstants.NO_ANIMATION);
            }
        }
    }
}
