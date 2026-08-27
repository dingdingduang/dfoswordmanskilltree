package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.DfoEntityClientActionRegistry;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.entity.DfoGeneralEntitySpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.entity.clientmovementhelper.ClientMovementHelper;
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
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.TypeValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import modified.org.joml.Vector3f;

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

public class Blademaster028 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_CHARGE_BURST;

//    public static final String BLADEMASTER028_SLASH_1_ANIMATION = "drawsword_slash2";
//    public static final String BLADEMASTER028_SLASH_2_ANIMATION = "stab1";
//    public static final String BLADEMASTER028_SLASH_UPWARD_ANIMATION = "upper_slash2";

    public static final String BLADEMASTER028_CHARGE_BUSTER_ANIMATION = "charge_buster_2";

    public static final String BLADEMASTER028_CHARGING_TEXTURE = "textures/entity/skilleff/blademaster/charge_buster/charging/";
    public static final String BLADEMASTER028_DASH_1_TEXTURE = "textures/entity/skilleff/blademaster/charge_buster/dash1/";
    public static final String BLADEMASTER028_FRONT_COVER_TEXTURE = "textures/entity/skilleff/blademaster/charge_buster/front_cover/";
    public static final String BLADEMASTER028_SLASH_1_TEXTURE = "textures/entity/skilleff/blademaster/charge_buster/slash1/";
    public static final String BLADEMASTER028_SLASH_2_TEXTURE = "textures/entity/skilleff/blademaster/charge_buster/slash2/";
    public static final String BLADEMASTER028_SLASH_3_TEXTURE = "textures/entity/skilleff/blademaster/charge_buster/slash3/";
    public static final String BLADEMASTER028_UPWARD_SLASH_TEXTURE = "textures/entity/skilleff/blademaster/charge_buster/upward_slash/";

    public static final int BLADEMASTER028_SLASH_1_START_DAMAGE_TICK = 2;
    public static final int BLADEMASTER028_SLASH_1_END_DAMAGE_TICK = 7;
    public static final int BLADEMASTER028_SLASH_2_START_DAMAGE_TICK = 17;
    public static final int BLADEMASTER028_SLASH_2_END_DAMAGE_TICK = 22;
    public static final int BLADEMASTER028_SLASH_3_START_DAMAGE_TICK = 27;
    public static final int BLADEMASTER028_SLASH_3_END_DAMAGE_TICK = 34;

    public static final int BLADEMASTER028_SLASH_BACK_FORTH_TOTAL_MOVING_TICKS = 10;
    public static final int BLADEMASTER028_SLASH_2_MOVING_TICK = 15;
    public static final int BLADEMASTER028_ANIMATION_END_TICKS = 40;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double Distance = skillData.getDistance().get(correctSkillLevel);

        double skillDamageMultiplier = getCPlayerSkillDamage();

        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

        Double UpwardSlashDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double UpwardSlashConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);
        Float UpwardSlashFinalDmg = (float) ( (UpwardSlashDamageMultiplier * getEntityAttackDamage(getClientPlayer()) + UpwardSlashConstantDamage) * skillDamageMultiplier);

        Double TriggerChance = skillData.getValueUsage_01().get(correctSkillLevel);
        Double Duration = skillData.getDuration().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%s", Distance),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%d%%", (int) (UpwardSlashDamageMultiplier.floatValue() * 100)),
                String.format("%s", UpwardSlashConstantDamage),
                String.format("%s", UpwardSlashFinalDmg),
                String.format("%s", (int) (TriggerChance.floatValue() * 100)),
                String.format("%ss", Duration)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster028::getSkillDescription);

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
                    float distance;
                    float aoeRange;
                    float damageBase, UpwardSlashDamageBase;
                    float damageMultiplier, UpwardSlashDamageMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);
                    float StunChance;
                    int StunDuration;
                    Predicate<Entity> condition;

                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);

                    float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                    float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                    float damageTickSpeedMultiplier = entityAnimationArr[1];

                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);
                    setEntityHasGravity(entity1, false);

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);
                    //future plan, character panel info
                    if (entity1 instanceof ServerPlayerEntity sp1) {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER028_CHARGE_BUSTER_ANIMATION, entityAnimationSpeedMultiplier);
                        condition = defaultSkillTargetCondition(sp1);
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;

                            DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardKidnapFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                        }
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }

                    distance = skill1.getDistance().get(playerSkillLVL).floatValue();
                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();
                    UpwardSlashDamageBase = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
                    UpwardSlashDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue();
                    StunChance = skill1.getValueUsage_01().get(playerSkillLVL).floatValue();
                    StunDuration = (int) (skill1.getDuration().get(playerSkillLVL).floatValue() * 20);

                    float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
                    float UpwardSlashFinalDmg = (float) ( (UpwardSlashDamageMultiplier * getEntityAttackDamage(entity1) + UpwardSlashDamageBase) * skillDamageMultiplier);

                    float entityOriginHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity1);
                    Vector3f startPos = getEntityPos(entity1);
                    Vector3f endPos = Vector3fMethods.PolarProjectionFromVecHorizon(startPos, distance, entityOriginHorizontalFacingAngle);
                    TypeValueHolder<Vector3f> startPosHolder = new TypeValueHolder<>(startPos);
                    TypeValueHolder<Vector3f> endPosHolder = new TypeValueHolder<>(endPos);

//                    Vector3f frontPos = new Vector3f(
//                            Vector3fMethods.lerp1D(0.1f, startPos.x(), endPos.x()),
//                            Vector3fMethods.lerp1D(0.1f, startPos.y(), endPos.y()),
//                            Vector3fMethods.lerp1D(0.1f, startPos.z(), endPos.z())
//                    );
//                    Vector3f middlePos = new Vector3f(
//                            Vector3fMethods.lerp1D(0.5f, startPos.x(), endPos.x()),
//                            Vector3fMethods.lerp1D(0.5f, startPos.y(), endPos.y()),
//                            Vector3fMethods.lerp1D(0.5f, startPos.z(), endPos.z())
//                    );

                    int actionPeriod = 1;
                    int Slash1_StartDamageTick = (int) (BLADEMASTER028_SLASH_1_START_DAMAGE_TICK * damageTickSpeedMultiplier);
                    int Slash1_EndDamageTick = (int) (BLADEMASTER028_SLASH_1_END_DAMAGE_TICK * damageTickSpeedMultiplier);
                    int Slash2_StartDamageTick = (int) (BLADEMASTER028_SLASH_2_START_DAMAGE_TICK * damageTickSpeedMultiplier);
                    int Slash2_EndDamageTick = (int) (BLADEMASTER028_SLASH_2_END_DAMAGE_TICK * damageTickSpeedMultiplier);
                    int Slash3_StartDamageTick = (int) (BLADEMASTER028_SLASH_3_START_DAMAGE_TICK * damageTickSpeedMultiplier);
                    int Slash3_EndDamageTick = (int) (BLADEMASTER028_SLASH_3_END_DAMAGE_TICK * damageTickSpeedMultiplier);
                    int AnimationEndTicks = (int) (BLADEMASTER028_ANIMATION_END_TICKS * damageTickSpeedMultiplier);

                    int TotalMovingTicks = (int) (BLADEMASTER028_SLASH_BACK_FORTH_TOTAL_MOVING_TICKS * damageTickSpeedMultiplier);
                    int MovingBackTick = (int) (BLADEMASTER028_SLASH_2_MOVING_TICK * damageTickSpeedMultiplier);

                    final int[] Slash_StartDamageTickArr = {Slash1_StartDamageTick, Slash2_StartDamageTick, Slash3_StartDamageTick};
                    final int[] Slash_EndDamageTickArr = {Slash1_EndDamageTick, Slash2_EndDamageTick, Slash3_EndDamageTick};

                    HashSet<LivingEntity> damagedLivingEntityGroup = new HashSet<>();
                    IntegerValueHolder DamageTickIndexHolder = new IntegerValueHolder(0);
                    IntegerValueHolder StartDamageTick = new IntegerValueHolder(Slash1_StartDamageTick);
                    IntegerValueHolder EndDamageTick = new IntegerValueHolder(Slash1_EndDamageTick);
                    TypeValueHolder<Entity> ClientMovementHelperHolder = new TypeValueHolder<>(null);
                    BooleanValueHolder isCollidedWithBlock = new BooleanValueHolder(false);

                    Entity clientMovementHelper = DfoGeneralEntitySpawnMethods.SetupClientMovementHelper(entity1, TotalMovingTicks, startPos, endPos, DfoEntityClientActionRegistry.STOP_MOVING_IF_NOT_IN_ACTION_OR_INTERRUPTED, Blademaster028_MovingServerAction(entityOriginHorizontalFacingAngle, distance, isCollidedWithBlock, startPosHolder, endPosHolder), 1, true);
                    ClientMovementHelperHolder.setAnyTypeValue(clientMovementHelper);

                    var EntityDimensionResourceKey = getEntityLevel(entity1).getDimension();

                    IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
                    MethodAction action =
                            (entity2) ->  {
                                int updatedTick = ElapsedTicks.getIntCounter();
                                if (updatedTick < AnimationEndTicks && !isLivingEntityActionInterrupted(entity2) && EntityDimensionResourceKey.equals(getEntityLevel(entity2).getDimension())) {
                                    if (updatedTick == MovingBackTick) {
                                        Vector3f tempStartPos = startPosHolder.getAnyTypeValue(), tempEndPos = endPosHolder.getAnyTypeValue();
                                        Entity tempClientMovementHelper = DfoGeneralEntitySpawnMethods.SetupClientMovementHelper(entity2, TotalMovingTicks, tempEndPos, tempStartPos, DfoEntityClientActionRegistry.STOP_MOVING_IF_NOT_IN_ACTION_OR_INTERRUPTED, Blademaster028_MovingServerAction(entityOriginHorizontalFacingAngle, distance, isCollidedWithBlock, endPosHolder, startPosHolder), 1, true);
                                        ClientMovementHelperHolder.setAnyTypeValue(tempClientMovementHelper);
                                        isCollidedWithBlock.setBooleanVal(false);
                                    }

                                    if (updatedTick >= StartDamageTick.getIntCounter() && updatedTick <= EndDamageTick.getIntCounter()) {
                                        TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 40, 0.1, Constants.OP_ADDITION, false, SkillID, null, null);
                                        int DamageTickIndex = DamageTickIndexHolder.getIntCounter();
                                        if (DamageTickIndex == Slash_StartDamageTickArr.length - 1) {
                                            //spawn model
                                            if (updatedTick == StartDamageTick.getIntCounter()) {
                                                damagedLivingEntityGroup.clear();
                                                float entityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity2);
                                                //play skill sound at target location
                                                DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                                DfoSwdSoundMethods.PlayRandomSwordUpperSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat()*0.3f+0.85f);

                                                int tempLifetime = EndDamageTick.getIntCounter() - StartDamageTick.getIntCounter() + 1;
                                                GeoModelSpawnMethods.GenerateCustomSlashTotalFrameBoundToEntity(entity2, entityHorizontalFacingAngle, 0f, ExtraMathMethods.randomFloat(10) - 5f, true, 2f, 255, 255, 255, 0f, 4f, 0f, tempLifetime,
                                                        BLADEMASTER028_SLASH_3_TEXTURE, 3, 2, 4, true, true, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Z_48, ParticleRegistry.CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, true);
                                                GeoModelSpawnMethods.GenerateCustomSlashTotalFrameBoundToEntity(entity2, entityHorizontalFacingAngle, 0f, ExtraMathMethods.randomFloat(10) - 5f, true, 2f, 255, 255, 255, 0f, 4f, 0f, tempLifetime,
                                                        BLADEMASTER028_UPWARD_SLASH_TEXTURE, 3, 2, 4, true, true, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Z_48, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);
                                            }

                                            Blademaster028_UpwardSlashAction(weaponType, entity2, 1.5f * aoeRange, UpwardSlashFinalDmg,
                                                    condition, damagedLivingEntityGroup, 0.3f, 1f, StunChance, StunDuration);
                                        }
                                        else {
                                            //spawn model
                                            boolean isEvenIndex = MathMethods.isEven(DamageTickIndex);
                                            if (updatedTick == StartDamageTick.getIntCounter()) {
                                                damagedLivingEntityGroup.clear();
                                                //play skill sound at target location
                                                DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                                DfoSwdSoundMethods.PlayRandomSwordUpperSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat()*0.3f+0.85f);

                                                float extraHorizontalFacingDegree = (isEvenIndex) ? 180f : 0f;
                                                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity2, entityOriginHorizontalFacingAngle + extraHorizontalFacingDegree, false, 1f, 0, 1.4f, 0, TotalMovingTicks, BLADEMASTER028_DASH_1_TEXTURE,
                                                        DfoSwordmanSkillTreeConstants.NO_ANIMATION, 4, 4, 7, false, true, true, true, null, 777, true);
                                                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity(entity2, entityOriginHorizontalFacingAngle + extraHorizontalFacingDegree + 180f, false, 1f, 0, 1.4f, 0, TotalMovingTicks, BLADEMASTER028_FRONT_COVER_TEXTURE,
                                                        DfoSwordmanSkillTreeConstants.NO_ANIMATION, 5, 5, 5, true, true, true, false, null, 777, true);
                                                GeoModelSpawnMethods.GenerateCustomSlashTotalFrameBoundToEntity(entity2, entityOriginHorizontalFacingAngle + 180f, 0f, ExtraMathMethods.randomFloat(10) - 5f, false, 1, 255, 255, 255, 0f, 1.4f, 0f, TotalMovingTicks,
                                                        isEvenIndex ? BLADEMASTER028_SLASH_2_TEXTURE : BLADEMASTER028_SLASH_1_TEXTURE, 3, 2, 4, true, true, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Z_24, ParticleRegistry.CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, true);
                                            }

                                            int remainingTicks;
                                            ClientMovementHelper tempClientMovementHelper = (ClientMovementHelper) ClientMovementHelperHolder.getAnyTypeValue();
                                            if (tempClientMovementHelper == null) {
                                                remainingTicks = 0;
                                            }
                                            else {
                                                remainingTicks = tempClientMovementHelper.getLifetime();
                                                if (remainingTicks == 0) {
                                                    ClientMovementHelperHolder.setAnyTypeValue(null);
                                                }
                                            }

                                            TypeValueHolder<Vector3f> fromHolder;
                                            TypeValueHolder<Vector3f> toHolder;
                                            if (isEvenIndex) {
                                                fromHolder = startPosHolder;
                                                toHolder = endPosHolder;
                                            }
                                            else {
                                                fromHolder = endPosHolder;
                                                toHolder = startPosHolder;
                                            }
                                            Blademaster028_NormalSlashAction(weaponType, entity2, aoeRange, finalDmg,
                                                    condition, damagedLivingEntityGroup, StunChance, StunDuration,
                                                    remainingTicks, TotalMovingTicks, fromHolder, toHolder, isCollidedWithBlock);
                                        }

                                        if (updatedTick == EndDamageTick.getIntCounter() && DamageTickIndex <= Slash_StartDamageTickArr.length - 1) {
                                            DamageTickIndex = DamageTickIndex + 1;
                                            DamageTickIndexHolder.setIntCounter(DamageTickIndex);

                                            if (DamageTickIndex <= Slash_StartDamageTickArr.length - 1) {
                                                StartDamageTick.setIntCounter(Slash_StartDamageTickArr[DamageTickIndex]);
                                                EndDamageTick.setIntCounter(Slash_EndDamageTickArr[DamageTickIndex]);
                                            }
                                        }
                                    }

                                    //update val
                                    ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
                                }
                                else {
                                    Entity tempClientMovementHelper = ClientMovementHelperHolder.getAnyTypeValue();
                                    if (tempClientMovementHelper != null) {
                                        RemoveEntity(tempClientMovementHelper);
                                        ClientMovementHelperHolder.setAnyTypeValue(null);
                                    }
                                    incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                                    if (entity2 instanceof ServerPlayerEntity sp1) {
                                        DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                                    }
                                    setEntityHasGravity(entity2, true);

                                    SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                                }
                            };
                    //Actioning ends
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

                    //start loop actioning with fixed period ticks
                    SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
                };

        tempSkillData.setActiveSkillAction1(tempSkillAction);
    }

    public static void Blademaster028_NormalSlashAction(final int weaponType, final LivingEntity entity2, final float finalAoeRange, final float finalDmg,
                                                        final Predicate<Entity> condition, final HashSet<LivingEntity> DamagedGroup, final float stunTriggerChance, final int stunDurationTicks,
                                                        final int remainingMovingTicks, final int TotalMovingTicks, TypeValueHolder<Vector3f> fromPosHolder, TypeValueHolder<Vector3f> toPosHolder, final BooleanValueHolder isCollidedWithBlock) {
        Vector3f entityNextPos, startPos = fromPosHolder.getAnyTypeValue(), endPos = toPosHolder.getAnyTypeValue();
        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition)) {
            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                if (!DamagedGroup.contains(tempLivingEntity)) {
                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                    DamagedGroup.add(tempLivingEntity);

                    if (stunTriggerChance >= ExtraMathMethods.randomFloat()) {
                        DfoStatusGeneralMethods.ApplyStunStatusToTargetLivingEntity(entity2, tempLivingEntity, stunDurationTicks, 0.1f);
                    }
                }

                if (!isCollidedWithBlock.getBooleanVal() && EntityDoesNotAffectByDeltaMovementMethod(tempLivingEntity)) {
                    setEntityDeltaMovement(tempLivingEntity, 0, 0, 0);

                    float tempPercent = MathMethods.min(0.8f, 1f * (TotalMovingTicks - remainingMovingTicks) / TotalMovingTicks + 0.2f);
                    entityNextPos = new Vector3f(
                            Vector3fMethods.lerp1D(tempPercent, startPos.x(), endPos.x()),
                            Vector3fMethods.lerp1D(tempPercent, startPos.y(), endPos.y()),
                            Vector3fMethods.lerp1D(tempPercent, startPos.z(), endPos.z())
                    );

                    if (isBlockPosAirOnly(tempLivingEntity, entityNextPos)) {
                        moveEntityToPos(tempLivingEntity, entityNextPos);
                        if (tempLivingEntity instanceof ServerPlayerEntity sp1) {
                            DfoSwdNetworkingFetchMsgMethods.FetchUpdateClientPlayerPos(sp1, entityNextPos, true);
                        }
                    }
                }
            }
        }
    }

    public static void Blademaster028_UpwardSlashAction(final int weaponType, final LivingEntity entity2, final float finalAoeRange, final float finalDmg,
                                                        final Predicate<Entity> condition, final HashSet<LivingEntity> DamagedGroup, final float pushStrengthXZ, final float pushStrengthY, final float stunTriggerChance, final int stunDurationTicks) {
        float entity2FacingAngle = getEntityHorizontalFacingDeg(entity2);
        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition.and(DfoSwordmanSkillMethods.getSectorCondition(entity2, 60f, finalAoeRange)))) {
            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                if (!DamagedGroup.contains(tempLivingEntity)) {
                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                    DamagedGroup.add(tempLivingEntity);

                    if (pushStrengthXZ != 0 || pushStrengthY != 0) {
                        Vector3f pushDelta = new Vector3f(
                                Vector3fMethods.PolarProjectionFromXHorizon(pushStrengthXZ, entity2FacingAngle),
                                pushStrengthY,
                                Vector3fMethods.PolarProjectionFromZHorizon(pushStrengthXZ, entity2FacingAngle) );

                        EntityMethods.pushEntityWithDeltaValueXYZ(tempLivingEntity, pushDelta.x(), pushDelta.y(), pushDelta.z(), false);
                    }

                    if (stunTriggerChance >= ExtraMathMethods.randomFloat()) {
                        DfoStatusGeneralMethods.ApplyStunStatusToTargetLivingEntity(entity2, tempLivingEntity, stunDurationTicks, 0.1f);
                    }
                }
            }
        }
    }

    public static MethodEntityAction Blademaster028_MovingServerAction(float originHorizontalFacingAngle, float totalDistShouldTravel, BooleanValueHolder isCollidedWithBlock, TypeValueHolder<Vector3f> fromPosHolder, TypeValueHolder<Vector3f> toPosHolder) {
        return (clientMovingEntity) -> {
            ClientMovementHelper clientMovementHelper = (ClientMovementHelper) clientMovingEntity;
            if (!clientMovementHelper.hasTargetLocXYZ()) {
                Entity mover = clientMovementHelper.getOwner();
                isCollidedWithBlock.setBooleanVal(true);
                if (mover != null) {
                    Vector3f endPos = getEntityPos(mover);
                    toPosHolder.setAnyTypeValue(endPos);
                    Vector3f startPos = Vector3fMethods.PolarProjectionFromVecHorizon(endPos, totalDistShouldTravel, originHorizontalFacingAngle + 180f);
                    fromPosHolder.setAnyTypeValue(startPos);
                }
                clientMovementHelper.setEntityServerAction(null, 777);
            }
        };
    }
}
