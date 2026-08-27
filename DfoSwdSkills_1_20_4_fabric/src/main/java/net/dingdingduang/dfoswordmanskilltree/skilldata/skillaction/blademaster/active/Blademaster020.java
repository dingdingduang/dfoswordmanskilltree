package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.SwordSlashWhiteEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.BlockMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster011;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster036;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues;
import net.dingdingduang.somebasicskills.networking.NetworkingFetchMsgMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.dingdingduang.somebasicskills.util.MethodConfigHelper;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.joml.Vector3f;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isLivingEntityActionInterrupted;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.*;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster020 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_BACKSTEP_SLASH;
    public static final String BLADEMASTER_0_20_BACKSLASH_ANIM = "flow_leap_slash_cross_end1";
    public static final String BLADEMASTER_0_20_BACKSLASH_ACTION = "BACKSLASH_ACTION_";

    public static final String BLADEMASTER_0_20_BACKSLASH_BLUDGEON_SHOCKWAVE = "BLADEMASTER_0_20_BACKSLASH_BLUDGEON_SHOCKWAVE";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);
        //Sword Qi
        Double SwordQiDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double SwordQiConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);
        Float SwordQiFinalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (SwordQiDamageMultiplier.floatValue() * 100)),
                String.format("%s", SwordQiConstantDamage),
                String.format("%s", SwordQiFinalDmg)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster020::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement( (entity) -> {
            boolean isInCD = isCPlayerSkillInCD(SkillID);
//            boolean isInAction = isPlayerInAction();
            boolean isChanneling = isPlayerInChannelingState();
            boolean isOnGround = isPlayerOnGround();
            boolean isPlayerBeingHit = isPlayerBeingHit();
            boolean isBackStepping = false;
            if (getCPlayerState().containsKey(Constants.IS_BACKSTEPPING)) { isBackStepping = getCPlayerState().get(Constants.IS_BACKSTEPPING) == Constants.ACTION_ON; }

//            printInGameMsg("cd? "+ isInCD);
//            printInGameMsg("stepping? "+ isBackStepping);
//            printInGameMsg("isChanneling? "+ isChanneling);
//            printInGameMsg("isOnGround? "+ isOnGround);
//            printInGameMsg("isPlayerBeingHit? "+ isPlayerBeingHit);
//            return !isInCD && !isInAction && !isChanneling && !isOnGround && !isPlayerBeingHit;
            return !isInCD && isBackStepping && !isChanneling && !isOnGround && !isPlayerBeingHit;
        });



        if (isClientSide()) {
            return;
        }

        //init
        getSConfig().add(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_SHOOT_HORIZONTALLY_CONFIG);




        //====================================
        //setup skill action
        MethodAction tempSkillAction =
                (entity1) -> {
                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);

                    boolean isWieldingBludgeon = weaponType == DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON;
                    boolean shouldTriggerBludgeonShockwave;
                    float additionalBludgeonDamageMultiplier = 0f;

                    int playerSkillLVL = 0;
                    float aoeRange;
                    float swordQiDistShouldTravel;
                    float damageBase;
                    float damageMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);

                    Predicate<Entity> condition;

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);
                    if (entity1 instanceof ServerPlayerEntity sp1) {
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                        }
                        int BludgeonMasteryLevel;
                        if (isWieldingBludgeon && (BludgeonMasteryLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY) ) >= Blademaster011.BLADEMASTER011_BLUDGEON_MASTERY_BONUS_EFFECT_1_LVL) {
                            shouldTriggerBludgeonShockwave = true;
                            additionalBludgeonDamageMultiplier = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY).getDamageMultiplierList_01().get(BludgeonMasteryLevel - 1).floatValue();
                        }
                        else {
                            shouldTriggerBludgeonShockwave = false;
                        }
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER_0_20_BACKSLASH_ANIM);
                        condition = defaultSkillTargetCondition(sp1).and(SBConditions.isNotTargetEntity(entity1));
                    }
                    else {
                        shouldTriggerBludgeonShockwave = false;
                        condition = defaultSkillTargetCondition().and(SBConditions.isNotTargetEntity(entity1));
                    }
                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                    swordQiDistShouldTravel = skill1.getDistance().get(playerSkillLVL).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + additionalBludgeonDamageMultiplier;

                    //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    //start Actioning

                    int dmgTicks = 3;
                    int animTicks = 12;
                    float finalDmg = (float) ((damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
                    int actionPeriod = 1;

                    IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
//                    HashSet<LivingEntity> damagedGroup = new HashSet<>();

                    MethodAction action =
                            (entity2) ->  {
                                int updatedTick = ElapsedTicks.getIntCounter();

                                if (updatedTick < animTicks && !isLivingEntityActionInterrupted(entity2)) {
                                    //spawn model
                                    if (updatedTick == 2) {
                                        //play skill sound at target location
                                        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat()*0.3f+0.85f);

                                        //shoot sword qi if has zenith of sword art
                                        boolean shootHorizontally;
                                        boolean hasZenithOfSwordArt;
                                        if (entity2 instanceof ServerPlayerEntity sp1) {
                                            shootHorizontally = getSPlayerShootsHorizontally(sp1);
                                            hasZenithOfSwordArt = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART) >= Blademaster036.BLADEMASTER036_ZENITH_OF_SWORD_ART_BONUS_EFFECT_1_LVL;
                                        }
                                        else {
                                            shootHorizontally = true;
                                            hasZenithOfSwordArt = true;
                                        }

                                        float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);

                                        final int[] slashColorArr = BlademasterSkillTreeInitialization.BLADEMASTER_SLASH_COLOR[weaponType + 1];
                                        GeoModelSpawnMethods.GenerateCustomSlashTotalFrameBoundToEntity(entity2, triggeredEntityFacingAngle, 0f, ExtraMathMethods.randomFloat(10) - 5f, true, 1f, slashColorArr[0], slashColorArr[1], slashColorArr[2], 0f, 1.6f, 0f, 17,
                                                GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 17, true, true, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);

                                        if (hasZenithOfSwordArt) {
                                            Vector3f entityOriginPos = getEntityPos(entity2);

                                            Vector3f TargetLoc;
                                            float triggeredEntityVerticalFacingAngle;
                                            if (shootHorizontally) {
                                                TargetLoc = Vector3fMethods.PolarProjectionFromVecHorizon(entityOriginPos, swordQiDistShouldTravel, triggeredEntityFacingAngle);
                                                triggeredEntityVerticalFacingAngle = 0f;
                                            }
                                            else {
                                                triggeredEntityVerticalFacingAngle = getEntityVerticalFacingDeg(entity2);
                                                TargetLoc = Vector3fMethods.PolarProjectionFromRotation(entityOriginPos, swordQiDistShouldTravel, triggeredEntityFacingAngle, triggeredEntityVerticalFacingAngle);
                                            }
                                            float extraYPos = 1.2f;
                                            SwordSlashWhiteEntity BackslashSwordQiLeft = GeoModelSpawnMethods.GenerateRGBSlashWithTargetLoc(entity2, triggeredEntityFacingAngle, ExtraMathMethods.randomFloat(20)+25f, 1f,255, 255, 255,
                                                    0, extraYPos, 0, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, TargetLoc.x(), TargetLoc.y() + extraYPos, TargetLoc.z(), true);

                                            SwordSlashWhiteEntity BackslashSwordQiRight = GeoModelSpawnMethods.GenerateRGBSlashWithTargetLoc(entity2, triggeredEntityFacingAngle, ExtraMathMethods.randomFloat(20)+295f, 1f,255, 255, 255,
                                                    0, extraYPos, 0, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, TargetLoc.x(), TargetLoc.y() + extraYPos, TargetLoc.z(), true);

                                            HashSet<LivingEntity> SwordQiDmgGroup = new HashSet<LivingEntity>();
                                            //VVVVVVVVVVVVVVV
                                            String BackSlashQiActionStringID = BLADEMASTER_0_20_BACKSLASH_ACTION + getEntityHashID(entity2) + ExtraMathMethods.randomInt(100);
                                            MethodAction BackSlashQiAction = (backSlashEntityOwner) -> {
                                                //next position
                                                float distPercent = (12f - BackslashSwordQiLeft.getLifetime()) / 12;
                                                float newX = BackslashSwordQiLeft.getStartPosX() + (BackslashSwordQiLeft.getTargetPosX() - BackslashSwordQiLeft.getStartPosX()) * distPercent;
                                                float newY = BackslashSwordQiLeft.getStartPosY() + (BackslashSwordQiLeft.getTargetPosY() - BackslashSwordQiLeft.getStartPosY()) * distPercent;
                                                float newZ = BackslashSwordQiLeft.getStartPosZ() + (BackslashSwordQiLeft.getTargetPosZ() - BackslashSwordQiLeft.getStartPosZ()) * distPercent;
//                                            Vector3f tempVec = new Vector3f(newX, BackslashSwordQiLeft.getStartPosY(), newZ);
                                                Vector3f tempVec = new Vector3f(newX, newY, newZ);

                                                //check collision
                                                int x = MathMethods.floor(tempVec.x() + 0.5f);
                                                int y = MathMethods.floor(tempVec.y());
                                                int z = MathMethods.floor(tempVec.z() + 0.5f);

                                                BlockState state = getEntityLevel(BackslashSwordQiLeft).getBlockState(new BlockPos(x, y, z));

                                                boolean isRemoved = false;
                                                if (SBConditions.isEntityAlive(BackslashSwordQiLeft) && (state.isAir() || BlockMethods.isBlockPassable(state.getBlock()))) {
                                                    moveEntityToXYZ(BackslashSwordQiLeft, tempVec.x(), tempVec.y(), tempVec.z());
                                                    moveEntityToXYZ(BackslashSwordQiRight, tempVec.x(), tempVec.y(), tempVec.z());
                                                } else {
                                                    isRemoved = true;
                                                    RemoveEntity(BackslashSwordQiLeft);
                                                    RemoveEntity(BackslashSwordQiRight);
                                                }

                                                if (!isRemoved) {
                                                    boolean playHitSound = false;
                                                    Vector3f tempTargetDeltaVec;
                                                    for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(BackslashSwordQiLeft, aoeRange, condition)) {
                                                        if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                                            if (SwordQiDmgGroup.contains(tempLivingEntity)) {
                                                                continue;
                                                            }
                                                            if (!playHitSound) {
                                                                playHitSound = true;
                                                                DfoSwdSoundMethods.PlayKatanaHitSound(getEntityLevel(BackslashSwordQiLeft), getEntityX(BackslashSwordQiLeft), getEntityY(BackslashSwordQiLeft), getEntityZ(BackslashSwordQiLeft), 0.8f, 1.0f);
                                                            }

                                                            float currentTargetPushStrength = calculatePushEntityStrength(0.4f, entity2, tempLivingEntity);

                                                            if (currentTargetPushStrength > 0) {
                                                                if (shootHorizontally) {
                                                                    tempTargetDeltaVec = Vector3fMethods.PolarProjectionFromVecHorizon(Vector3fMethods.getOriginVector(), currentTargetPushStrength + 0.1f, triggeredEntityFacingAngle);
                                                                } else {
                                                                    tempTargetDeltaVec = Vector3fMethods.PolarProjectionFromRotation(Vector3fMethods.getOriginVector(), currentTargetPushStrength + 0.2f, triggeredEntityFacingAngle, triggeredEntityVerticalFacingAngle);
                                                                }
                                                                pushEntityWithDeltaVector(tempLivingEntity, tempTargetDeltaVec, true);
                                                            }

                                                            DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(backSlashEntityOwner, tempLivingEntity, finalDmg, false);
                                                            SwordQiDmgGroup.add(tempLivingEntity);
                                                        }
                                                    }
                                                } else {
                                                    SBSTickEventMethods.setSkillActionDone(backSlashEntityOwner, BackSlashQiActionStringID);
                                                }
                                            };

                                            SBSTickEventMethods.setMethodActionTimer(entity2, BackSlashQiActionStringID, actionPeriod, BackSlashQiAction);
                                        }
                                    }

                                    //start damage
                                    if (updatedTick == dmgTicks) {
                                        boolean playHitSound = false;

                                        float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);

                                        for (Entity tempEntity: GroupEnumEntitiesInRangeFilter(entity2, aoeRange, condition)) {
                                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                                if (!playHitSound) {
                                                    playHitSound = true;
                                                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                                }
                                                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);

                                                float currentTargetXZPushStrength = calculatePushEntityStrength(0.3f, entity2, tempLivingEntity);

                                                if (currentTargetXZPushStrength > 0) {
                                                    pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityFacingAngle, currentTargetXZPushStrength, 0, true);
                                                }

                                                if (shouldTriggerBludgeonShockwave) {
                                                    float pushYStrength = calculatePushEntityStrength(0.6f, entity2, tempLivingEntity);
                                                    setEntitySPDeltaMovement(tempLivingEntity, 0, pushYStrength, 0, true);

                                                    HashSet<LivingEntity> GroundQuakeDamagedGroup = new HashSet<>();
                                                    GroundQuakeDamagedGroup.add(tempLivingEntity);
                                                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(tempLivingEntity, 255, 255, 255, 1f, 0, 1.4f, 0, 300, GeoAnimationAndTextureName.BLUDGEON_HIT_SPIRAL, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_CLOCKWISE_1080,
                                                            DfoSwordmanSkillTreeConstants.NO_ANIMATION,5, 5, 1, true, true, true, DfoSwordmanSkillMethods.BludgeonFlipShockwave1(BLADEMASTER_0_20_BACKSLASH_BLUDGEON_SHOCKWAVE, entity2, aoeRange + 0.5f, finalDmg, condition, GroundQuakeDamagedGroup), 5, true);
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
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
    }



    //=================
    //skill configurations
    private static boolean getSPlayerShootsHorizontally(ServerPlayerEntity sp1) {
        try {
            return getSPlayerConfig().get(sp1).get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_CONFIG).get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_SHOOT_HORIZONTALLY_CONFIG).getIntValue() != 0;
        }
        catch (Exception ignored) {

        }
        return true;
    }


    public static void ClientConfigInit() {
        HashMap<String, MethodConfigHelper> GeneralSetting = new HashMap<String, MethodConfigHelper>();
        GeneralSetting.put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_SHOOT_HORIZONTALLY_CONFIG, new MethodConfigHelper(GlobalClientPlayerValues::DefaultSettingOnOff, 0, true));
        getCPlayerConfig2Settings().put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_CONFIG, GeneralSetting);
    }

    public static void ServerConfigInit(HashMap<String, HashMap<String, MethodConfigHelper>> SPlayerConfigMap) {
        getSConfig().add(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_CONFIG);
        getSConfig().add(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_SHOOT_HORIZONTALLY_CONFIG);
        HashMap<String, MethodConfigHelper> GeneralSetting = new HashMap<String, MethodConfigHelper>();
        GeneralSetting.put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_SHOOT_HORIZONTALLY_CONFIG, new MethodConfigHelper(null, 0, true));
        SPlayerConfigMap.put(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_CONFIG, GeneralSetting);
    }

    public static void ServerConfigAfterInit(ServerPlayerEntity sp1, HashMap<String, HashMap<String, MethodConfigHelper>> SPlayerConfigMap) {
        //sync to client
        int val = SPlayerConfigMap.get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_CONFIG).get(DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_SHOOT_HORIZONTALLY_CONFIG).getIntValue();
        NetworkingFetchMsgMethods.FetchConfigKeyValToClientSide(sp1, DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_CONFIG, DfoSwordmanSkillTreeConstants.BLADEMASTER_0_20_BACKSTEP_SLASH_SHOOT_HORIZONTALLY_CONFIG, val);
    }
}
