package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues;
import net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import modified.org.joml.Vector3f;

import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isClientSide;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.setClientPlayerInAction;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster004 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_COUNTER_STRIKE;

    //TODO: [!] server side timer that record counter strike cd to stop sending extra packet when receiving any damage?
    //or use existing method action and loop inside?
    public static final String BLADEMASTER004_SLASH_UPWARD_ANIMATION = "counter_strike_upper_slash";

//    public static final String BLADEMASTER004_COUNTER_STRIKE = "IS_SATISFIED_BLADEMASTER004_COUNTER_STRIKE";
    public static final String BLADEMASTER004_COUNTER_STRIKE_PACKET_ID = BlademasterSkillTreeInitialization.BLADEMASTER_COUNTER_STRIKE + "_SEND_PACKET_IS_SATISFIED_BLADEMASTER004_COUNTER_STRIKE";
    public static final String BLADEMASTER004_COUNTER_STRIKE_COOLDOWN_ID = BlademasterSkillTreeInitialization.BLADEMASTER_COUNTER_STRIKE + "_COOLDOWN";

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
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster004::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement( (ClientSkillID) -> {
            boolean isInCD = isCPlayerSkillInCD(SkillID);
            boolean isInAction = isPlayerInAction();
            boolean isChanneling = isPlayerInChannelingState();
            boolean isOnGround = isPlayerOnGround();
            boolean isPlayerBeingHit = isPlayerBeingHit();
            Integer CounterStrikeStatus = GlobalClientPlayerValues.getCPlayerState().get(BLADEMASTER004_COUNTER_STRIKE_PACKET_ID);
            boolean ifSatisfiedCounterStrikeCond = CounterStrikeStatus != null && CounterStrikeStatus == Constants.ACTION_ON;

            return !isInCD && !isInAction && !isChanneling && isOnGround && isPlayerBeingHit && ifSatisfiedCounterStrikeCond;
        });

        tempSkillData.setClientCondReqPassedAction( (ClientSkillID) -> {
            setClientPlayerInAction(Constants.ACTION_ON, ClientSkillID);
            GlobalClientPlayerValues.getCPlayerState().put(BLADEMASTER004_COUNTER_STRIKE_PACKET_ID, Constants.ACTION_OFF);
//            GlobalClientPlayerValues.getCPlayerState().remove(BLADEMASTER004_COUNTER_STRIKE_PACKET_ID);
        });



        if (isClientSide()) {
            return;
        }



        //====================================
        //setup skill action
        MethodAction tempSkillAction =
                (entity1) -> {
                    int playerSkillLVL = 0;
                    float aoeRange;
                    float floatingMultiplier;
                    float damageBase;
                    float damageMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);
                    Predicate<Entity> condition;

//                    float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity1) + 180f;
//                    EntityMethods.setEntityHorizontalFacingDeg(entity1, triggeredEntityHorizontalFacingAngle);
//                    EntityMethods.AdvanceTeleportEntityToXYZFacing(entity1, getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), triggeredEntityHorizontalFacingAngle, 0f);

                    float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity1) + 180f;

                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);

                    float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity1);
                    float entityAnimationSpeedMultiplier = entityAnimationArr[0];
                    float damageTickSpeedMultiplier = entityAnimationArr[1];

                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);
                    setEntityStateAmountNoPacket(entity1, BLADEMASTER004_COUNTER_STRIKE_COOLDOWN_ID, 1);

                    float pXSpeed = Vector3fMethods.PolarProjectionFromXHorizon(0.22f, triggeredEntityHorizontalFacingAngle);
                    float pZSpeed = Vector3fMethods.PolarProjectionFromZHorizon(0.22f, triggeredEntityHorizontalFacingAngle);

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    int InvincibilityDurationTicks = 0;
                    if (entity1 instanceof ServerPlayerEntity sp1) {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, BLADEMASTER004_SLASH_UPWARD_ANIMATION, entityAnimationSpeedMultiplier);
                        condition = defaultSkillTargetCondition(sp1);
                        DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, pXSpeed, 0f, pZSpeed, true);
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                        }

                        int EXInvincibilityDurationUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_COUNTER_STRIKE_UPGRADE);
                        if (EXInvincibilityDurationUpgradeLVL > 0) {
                            SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_COUNTER_STRIKE_UPGRADE);
                            InvincibilityDurationTicks = (int) (20 * EX_SkillData.getDuration().get(EXInvincibilityDurationUpgradeLVL - 1).floatValue());
                        }
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }
                    setEntityDeltaMovement(entity1, getEntityDeltaMovement(entity1).add(pXSpeed, 0f, pZSpeed));

                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                    floatingMultiplier = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();

                    //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    //start Actioning
                    int dmgTicks = (int) (5 * damageTickSpeedMultiplier);
                    int animTicks = (int) (10 * damageTickSpeedMultiplier);
                    double finalAoeRange = aoeRange;
                    float finalFloatingHeight = floatingMultiplier;
                    float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
                    int ActionDelayTicks = (int) (SBSAttributeMethods.getCooldownAttrVal(entity1, skill1.getCooldownTime().get(playerSkillLVL).floatValue()) * 20);
                    int actionPeriod = 1;

                    if (InvincibilityDurationTicks > 0) {
                        TimedAttributesDistributor.applyTimedBeneficialStatusInvincibilityToEntity(entity1, InvincibilityDurationTicks, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);
                    }

                    IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
                    BooleanValueHolder shouldResetAction = new BooleanValueHolder(true);
                    MethodAction action = (entity2) ->  {
                                int updatedTick = ElapsedTicks.getIntCounter();

                                TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 20, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);
                                if (updatedTick < animTicks) {
                                    //start damage
                                    if (updatedTick == dmgTicks) {
                                        //play skill sound at target location
                                        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                        DfoSwdSoundMethods.PlayRandomSwordUpperSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 2f);

                                        final int[] slashColorArr = BlademasterSkillTreeInitialization.BLADEMASTER_SLASH_COLOR[weaponType + 1];
                                        GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, getEntityHorizontalFacingDeg(entity2) + 180f, 135f, 1, slashColorArr[0], slashColorArr[1], slashColorArr[2], 0f, 1.4f, 0f, Math.max(18, animTicks-dmgTicks),
                                                GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, ParticleRegistry.CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, true);

                                        //spawn model
                                        boolean playHitSound = false;
                                        for (Entity tempEntity: GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition)) {
                                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                                if (!playHitSound) {
                                                    playHitSound = true;
                                                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                                }
                                                pushEntityWithDeltaValueXYZ(tempLivingEntity, 0, finalFloatingHeight, 0, true);

                                                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
                                            }
                                        }
                                    }
                                }
                                else {
                                    if (shouldResetAction.getBooleanVal()) {
                                        shouldResetAction.setBooleanVal(false);
                                        incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);

                                        if (entity2 instanceof ServerPlayerEntity sp1) {
//                                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 1f);
                                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
                                        }
                                    }
                                    if (updatedTick > ActionDelayTicks) {
                                        setEntityStateAmountNoPacket(entity2, BLADEMASTER004_COUNTER_STRIKE_COOLDOWN_ID, 0);

                                        SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
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
    }

    public static void Blademaster004_SendPacketIfMeetConditionsToClientSide(Entity damageSourceEntity, ServerPlayerEntity hurtPlayerEntity) {
        if (damageSourceEntity != null && ServerSkillMethods.getEntityStateAmount(hurtPlayerEntity, BLADEMASTER004_COUNTER_STRIKE_COOLDOWN_ID) == Constants.ACTION_OFF && ServerSkillMethods.getEntityStateAmount(hurtPlayerEntity, BLADEMASTER004_COUNTER_STRIKE_PACKET_ID) == Constants.ACTION_OFF) {
            Vector3f HurtEntityLoc = getEntityPos(hurtPlayerEntity);
            Vector3f DamageSourceLoc = getEntityPos(damageSourceEntity);
            float AngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(DamageSourceLoc, HurtEntityLoc);

            float entityFacingAngle = getEntityHorizontalFacingDeg(hurtPlayerEntity);
            float tempD = MathMethods.RoundAngle(AngleBtn - 180f); //front for -180 deg
            float range = 70f; //140 deg

            int CounterStrikeActivationCD = 40;
            int actionPeriod = 5;

            //front
            if (!(tempD <= MathMethods.RoundAngle(entityFacingAngle) + range && tempD >= MathMethods.RoundAngle(entityFacingAngle) - range)) {
                ServerSkillMethods.setEntityStateAmount(hurtPlayerEntity, BLADEMASTER004_COUNTER_STRIKE_PACKET_ID, Constants.ACTION_ON);

                //Timer to reset
                IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
                MethodAction action = (entity2) ->  {
                    int updatedTick = ElapsedTicks.getIntCounter();

                    if (updatedTick > CounterStrikeActivationCD) {
                        ServerSkillMethods.setEntityStateAmount(entity2, BLADEMASTER004_COUNTER_STRIKE_PACKET_ID, Constants.ACTION_OFF);
                    }

                    ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
                };

                SBSTickEventMethods.setMethodActionTimer(hurtPlayerEntity, BLADEMASTER004_COUNTER_STRIKE_PACKET_ID, actionPeriod, action);
            }
        }
    }

    public static void Blademaster004_resetDataWhenRespawn(LivingEntity entity) {
        ServerSkillMethods.setEntityStateAmount(entity, BLADEMASTER004_COUNTER_STRIKE_PACKET_ID, Constants.ACTION_OFF);
        setEntityStateAmountNoPacket(entity, BLADEMASTER004_COUNTER_STRIKE_COOLDOWN_ID, Constants.ACTION_OFF);
    }
}
