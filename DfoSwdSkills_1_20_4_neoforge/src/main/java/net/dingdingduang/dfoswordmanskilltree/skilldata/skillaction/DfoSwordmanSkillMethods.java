package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.util.*;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.somebasicskills.Constants;

import org.joml.Vector3f;

import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods.SetupCircleTiltBlocksZone;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.setClientPlayerInAction;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isLivingEntityActionInterrupted;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.*;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;

public class DfoSwordmanSkillMethods {
    //Condition for client side checking
    public static boolean isPlayerInAction() {
        boolean isInAction = false;
        if (getCPlayerState() != null) {
            Integer State = getCPlayerState().get(Constants.IS_IN_ACTION);
            if (State != null) {
                isInAction = State != Constants.ACTION_OFF;
            }
        }
        return isInAction;
    }

    public static boolean isPlayerInFlowStance() {
        boolean isInFlowStance = false;
        if (getCPlayerState() != null) {
            Integer State = getCPlayerState().get(DfoSwordmanSkillTreeConstants.IN_FLOW_STANCE);
            if (State != null) {
                isInFlowStance = State != Constants.ACTION_OFF;
            }
        }
        return isInFlowStance;
    }

    public static boolean isPlayerInChannelingState() {
        boolean isChanneling = false;
        if (getCPlayerState() != null) {
            Integer State = getCPlayerState().get(Constants.IS_CHANNELING);
            if (State != null) {
                isChanneling = State != Constants.ACTION_OFF;
            }
        }
        return isChanneling;
    }

    public static boolean isPlayerOnGround() {
        boolean isOnGround = true;
        if (getClientPlayer() != null) {
            isOnGround = isEntityOnGround(getClientPlayer());

        }
        return isOnGround;
    }

    public static boolean isPlayerBeingHit() {
        return getCPlayerIsImmobilized();
    }

    //extra
    public static boolean isPlayerActionInterrupted() {
        boolean isActionInterrupted = false;
        if (getCPlayerState() != null) {
            Integer State = getCPlayerState().get(Constants.ACTION_INTERRUPTED);
            if (State != null) {
                isActionInterrupted = State != Constants.ACTION_OFF;
            }
        }
        return isActionInterrupted;
    }

    public static void defaultSkillClientConditionPassedAction(String SkillID) {
        setClientPlayerInAction(Constants.ACTION_ON, SkillID);
    }

    public static boolean defaultSkillClientCondition(String SkillID) {
        boolean isInCD = isCPlayerSkillInCD(SkillID);
        boolean isInAction = isPlayerInAction();
        boolean isChanneling = isPlayerInChannelingState();
        boolean isOnGround = isPlayerOnGround();
        boolean isPlayerBeingHit = isPlayerBeingHit();

        return !isInCD && !isInAction && !isChanneling && isOnGround && !isPlayerBeingHit;
    }

//    public static boolean defaultSkillClientCondition(String SkillID) {
//        boolean isInCD = getSkillsInCooldownClientTimerOverlayInstance().isClientPlayerSkillInCD(SkillID);
//        boolean isInAction = false;
//        if (getCPlayerState().containsKey(Constants.IS_IN_ACTION)) {
//            isInAction = getCPlayerState().get(Constants.IS_IN_ACTION) == Constants.ACTION_ON;
//        }
//        boolean isChanneling = false;
//        if (getCPlayerState().containsKey(Constants.IS_CHANNELING)) {
//            isChanneling = getCPlayerState().get(Constants.IS_CHANNELING) == Constants.ACTION_ON;
//        }
//        boolean isOnGround = true;
//        if (getMinecraftInstance().player != null) {
//            isOnGround = getMinecraftInstance().player.onGround();
//        }
//
//        return !isInCD && !isInAction && !isChanneling && isOnGround;
//    }

    public static float[] getEntityAnimationSpeedMultiplierArr(LivingEntity entity1) {
        float entityAnimationSpeedMultiplier = ExtraMathMethods.clamp((float) EntityMethods.getEntityAttackSpeed(entity1) * 0.5f, 0.5f, 5f);
        float damageTickSpeedMultiplier;
        if (entityAnimationSpeedMultiplier >= 1) {
            damageTickSpeedMultiplier = 1f - (entityAnimationSpeedMultiplier - 1f) * 0.2f;
            entityAnimationSpeedMultiplier = 1f + (entityAnimationSpeedMultiplier - 1f) * 0.2f;
        }
        else {
            damageTickSpeedMultiplier = 3f - 2f * entityAnimationSpeedMultiplier;
        }
        return new float[] {entityAnimationSpeedMultiplier, damageTickSpeedMultiplier};
    }

    public static float[] getEntityAnimationSpeedMultiplierArr(LivingEntity entity1, float additionalMultiplier) {
        float entityAnimationSpeedMultiplier = ExtraMathMethods.clamp((float) EntityMethods.getEntityAttackSpeed(entity1) * additionalMultiplier * 0.5f, 0.5f, 5f);
        float damageTickSpeedMultiplier;
        if (entityAnimationSpeedMultiplier >= 1) {
            damageTickSpeedMultiplier = 1f - (entityAnimationSpeedMultiplier - 1f) * 0.2f;
            entityAnimationSpeedMultiplier = 1f + (entityAnimationSpeedMultiplier - 1f) * 0.2f;
        }
        else {
            damageTickSpeedMultiplier = 3f - 2f * entityAnimationSpeedMultiplier;
        }
        return new float[] {entityAnimationSpeedMultiplier, damageTickSpeedMultiplier};
    }

//    public static float getEntityDamageTickSpeedMultiplier(float entityAnimationSpeedMultiplier) {
//        float damageTickSpeedMultiplier;
//        if (entityAnimationSpeedMultiplier >= 1) {
//            damageTickSpeedMultiplier = 1f - (entityAnimationSpeedMultiplier - 1f) * 0.2f;
//        }
//        else {
//            damageTickSpeedMultiplier = 3f - 2f * entityAnimationSpeedMultiplier;
//        }
//        return damageTickSpeedMultiplier;
//    }

    public static Predicate<Entity> getSectorCondition(LivingEntity dealer, float sectorRange, float finalAoeRange) {
        Vector3f triggeredEntityPos = getEntityPos(dealer);
        float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(dealer);
        Vector3f tempVecBehindTriggerEntity = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityPos, -0.5f, triggeredEntityFacingAngle);

        return (entity) -> {
            float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontal(tempVecBehindTriggerEntity, getEntityPos(entity)));

            if (!Vector3fMethods.IsAngleDegBetweenAB(sectorDeg, MathMethods.RoundAngle(triggeredEntityFacingAngle + sectorRange), MathMethods.RoundAngle(triggeredEntityFacingAngle - sectorRange))) {
                return false;
            }

            return DistanceBetweenEntities(entity, dealer) <= finalAoeRange;
        };
    }

    public static Predicate<Entity> getSectorCondition(LivingEntity dealer, Vector3f pos, float fromAngle, float toAngle, float finalAoeRange) {
        Vector3f dealerPos = getEntityPos(dealer);
        return (entity) -> {
            Vector3f targetEntityPos = getEntityPos(entity);
            float sectorDeg = MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationHorizontal(dealerPos, targetEntityPos));

            if (!Vector3fMethods.IsAngleDegBetweenAB(sectorDeg, MathMethods.RoundAngle(fromAngle), MathMethods.RoundAngle(toAngle))) {
                return false;
            }

            return Vector3fMethods.Vec3fDistanceTo(targetEntityPos, pos) <= finalAoeRange;
        };
    }

    public static void executeCommonSwdPhysicalDamageAction(final int weaponType, final LivingEntity entity2, final float finalAoeRange, final float finalDmg, final Entity effEntity, final MethodSoundAction playSoundWhenEffSpawn, final Predicate<Entity> condition, final float sectorRange, final BooleanValueHolder spawnEffect, final HashSet<LivingEntity> DamagedGroup, final float pushStrengthXZ, final float pushStrengthY, final boolean isDeltaAdded, final MethodAction damagedGroupAction) {
        float entity2FacingAngle = getEntityHorizontalFacingDeg(entity2);
        //spawn model
        if (!spawnEffect.getBooleanVal()) {
            spawnEffect.setBooleanVal(true);
            //play skill sound at target location
            DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
            if (playSoundWhenEffSpawn != null) {
                playSoundWhenEffSpawn.executeSoundAction(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
            }

            if (effEntity != null) {
                EntityMethods.setEntityHorizontalFacingDeg(effEntity, entity2FacingAngle);
                addEntityToWorld(getEntityLevel(entity2), effEntity);
            }
        }

        //Sector damage cond
        Predicate<Entity> SectorDmgCond;
        if (sectorRange >= 360f) {
            SectorDmgCond = (entity -> true);
        }
        else {
            SectorDmgCond = getSectorCondition(entity2, sectorRange, finalAoeRange);
        }
        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition.and(SectorDmgCond))) {
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

                        EntityMethods.pushEntityWithDeltaValueXYZ(tempLivingEntity, pushDelta.x(), pushDelta.y(), pushDelta.z(), isDeltaAdded);
                    }
                    if (damagedGroupAction != null) {
                        damagedGroupAction.executeAction(tempLivingEntity);
                    }
                }
            }
        }
    }

    public static MethodAction generateCommonSwdDamageActionWithDuration(final int weaponType, final int startDmgTicks, final int endDmgTicks, final float finalAoeRange, final float finalDmg, final Entity effEntity, final MethodSoundAction playSoundWhenEffSpawn, final Predicate<Entity> condition, final float sectorRange, final float pushStrengthXZ, final float pushStrengthY, final boolean isDeltaAdded, final int actionPeriod, final String SkillID, final boolean shouldSendInActionPacket, final MethodAction damagedGroupAction) {
        IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
        BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
        BooleanValueHolder spawnEffect = new BooleanValueHolder(false);
        HashSet<LivingEntity> DamagedGroup = new HashSet<>();

        return (entity2) -> {
            int currentPassedTicks = tempTickCounter.getIntCounter();

            boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);

            if (!isActionInterrupted && currentPassedTicks >= startDmgTicks && currentPassedTicks <= endDmgTicks) {
                executeCommonSwdPhysicalDamageAction(weaponType, entity2, finalAoeRange, finalDmg, effEntity, playSoundWhenEffSpawn, condition, sectorRange, spawnEffect, DamagedGroup, pushStrengthXZ, pushStrengthY, isDeltaAdded, damagedGroupAction);
            }
            else {
                if (!SendActionOffPacket.getBooleanVal() && currentPassedTicks >= endDmgTicks + 1) {
                    SendActionOffPacket.setBooleanVal(true);
                    if (shouldSendInActionPacket) {
                        ServerSkillMethods.incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                    }
                }
                if (currentPassedTicks >= endDmgTicks) {
                    SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                }
            }

            tempTickCounter.setIntCounter(tempTickCounter.getIntCounter() + actionPeriod);
        };
    }


    //==========
    public static void executeCommonSwdPhysicalDamageActionWithCenterRange(final Vector3f center, final float xzRange, final float minYRange, final float maxYRange, final int weaponType, final LivingEntity entity2, final float finalDmg, final Entity effEntity, final MethodSoundAction playSoundWhenEffSpawn, final Predicate<Entity> condition, final BooleanValueHolder spawnEffect, final HashSet<LivingEntity> DamagedGroup, final float pushStrengthXZ, final float pushStrengthY, final boolean isDeltaAdded, final MethodAction damagedGroupAction) {
        float entity2FacingAngle = getEntityHorizontalFacingDeg(entity2);
        //spawn model
        if (!spawnEffect.getBooleanVal()) {
            spawnEffect.setBooleanVal(true);
            //play skill sound at target location
            DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
            if (playSoundWhenEffSpawn != null) {
                playSoundWhenEffSpawn.executeSoundAction(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
            }

            if (effEntity != null) {
                addEntityToWorld(getEntityLevel(entity2), effEntity);
            }
        }

        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, EntityGroupMethods.getAABB(center.x() - xzRange, minYRange, center.z() - xzRange, center.x() + xzRange, maxYRange, center.z() + xzRange), condition)) {
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

                        EntityMethods.pushEntityWithDeltaValueXYZ(tempLivingEntity, pushDelta.x(), pushDelta.y(), pushDelta.z(), isDeltaAdded);
                    }
                    if (damagedGroupAction != null) {
                        damagedGroupAction.executeAction(tempLivingEntity);
                    }
                }
            }
        }
    }

    public static MethodAction generateCommonSwdDamageActionWithDurationWithRange(final Vector3f center, final float xzRange, final float minYRange, final float maxYRange, final int weaponType, final int startDmgTicks, final int endDmgTicks, final float finalDmg, final Entity effEntity, final MethodSoundAction playSoundWhenEffSpawn, final Predicate<Entity> condition, final float pushStrengthXZ, final float pushStrengthY, final boolean isDeltaAdded, final int actionPeriod, final String SkillID, final boolean shouldSendInActionPacket, final MethodAction damagedGroupAction) {
        IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
        BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
        BooleanValueHolder spawnEffect = new BooleanValueHolder(false);
        HashSet<LivingEntity> DamagedGroup = new HashSet<>();

        return (entity2) -> {
            int currentPassedTicks = tempTickCounter.getIntCounter();

            boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);

            if (!isActionInterrupted && currentPassedTicks >= startDmgTicks && currentPassedTicks <= endDmgTicks) {
                executeCommonSwdPhysicalDamageActionWithCenterRange(center, xzRange, minYRange, maxYRange, weaponType, entity2, finalDmg, effEntity, playSoundWhenEffSpawn, condition, spawnEffect, DamagedGroup, pushStrengthXZ, pushStrengthY, isDeltaAdded, damagedGroupAction);
            }
            else {
                if (!SendActionOffPacket.getBooleanVal() && currentPassedTicks >= endDmgTicks + 1) {
                    SendActionOffPacket.setBooleanVal(true);
                    if (shouldSendInActionPacket) {
                        ServerSkillMethods.incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                    }
                }
                if (currentPassedTicks >= endDmgTicks) {
                    SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                }
            }

            tempTickCounter.setIntCounter(tempTickCounter.getIntCounter() + actionPeriod);
        };
    }


    public static MethodAction generateCommonSwdDamageActionOneTimeWithXYZDelayTick(final float targetLocX, final float targetLocY, final float targetLocZ, final int weaponType, final int delayTicks, final float finalAoeRange, final float finalDmg, final Entity effEntity, final MethodSoundAction playSoundWhenEffSpawn, final Predicate<Entity> condition, final float sectorRange, final float pushStrengthXZ, final float pushStrengthY, final boolean isDeltaAdded, final int actionPeriod, final String SkillID, final boolean shouldSendInActionPacket, final MethodAction targetLocAction, final MethodAction damagedGroupAction) {
        IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
        BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
        BooleanValueHolder spawnEffect = new BooleanValueHolder(false);
        HashSet<LivingEntity> DamagedGroup = new HashSet<>();

        return (entity2) -> {
            int currentPassedTicks = tempTickCounter.getIntCounter();

            boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);

            if (!isActionInterrupted && currentPassedTicks >= delayTicks) {
                Vector3f triggeredEntityLoc = getEntityPos(entity2);
                Vector3f targetLoc = new Vector3f(targetLocX, targetLocY, targetLocZ);
                float distOffset = triggeredEntityLoc.distance(targetLoc);
                if (distOffset > 0.1f) {
                    float trueAOE = finalAoeRange + distOffset;
                    Predicate<Entity> trueCond = (target) -> EntityMethods.getEntityPos(target).distance(targetLoc) <= finalAoeRange;

                    executeCommonSwdPhysicalDamageAction(weaponType, entity2, trueAOE, finalDmg, effEntity, playSoundWhenEffSpawn, condition.and(trueCond), sectorRange, spawnEffect, DamagedGroup, pushStrengthXZ, pushStrengthY, isDeltaAdded, damagedGroupAction);
                }
                else {
                    executeCommonSwdPhysicalDamageAction(weaponType, entity2, finalAoeRange, finalDmg, effEntity, playSoundWhenEffSpawn, condition, sectorRange, spawnEffect, DamagedGroup, pushStrengthXZ, pushStrengthY, isDeltaAdded, damagedGroupAction);
                }
                SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                if (!SendActionOffPacket.getBooleanVal()) {
                    SendActionOffPacket.setBooleanVal(true);
                    if (shouldSendInActionPacket) {
                        ServerSkillMethods.incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                    }
                }
                if (targetLocAction != null) {
                    targetLocAction.executeAction(entity2);
                }
            }

            tempTickCounter.setIntCounter(tempTickCounter.getIntCounter() + actionPeriod);
        };
    }


    public static MethodAction generateCommonPhysicalDamageActionOneTimeAtEntityLocWithDelayTick(final float polarDist, final int weaponType, final int delayTicks, final float finalAoeRange, final float finalDmg, final Entity effEntity, final MethodSoundAction playSoundWhenEffSpawn, final Predicate<Entity> condition, final float sectorRange, final float pushStrengthXZ, final float pushStrengthY, final boolean isDeltaAdded, final int actionPeriod, final String SkillID, final boolean shouldSendInActionPacket, final MethodAction targetLocAction, final MethodAction damagedGroupAction) {
        IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
        BooleanValueHolder SendActionOffPacket = new BooleanValueHolder(false);
        BooleanValueHolder spawnEffect = new BooleanValueHolder(false);
        HashSet<LivingEntity> DamagedGroup = new HashSet<>();

        return (entity2) -> {
            int currentPassedTicks = tempTickCounter.getIntCounter();

            boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);

            if (!isActionInterrupted && currentPassedTicks >= delayTicks) {
                Vector3f triggeredEntityLoc = getEntityPos(entity2);
                Vector3f targetLoc = Vector3fMethods.PolarProjectionFromVecHorizon(triggeredEntityLoc, polarDist, getEntityHorizontalFacingDeg(entity2));

                float trueAOE = finalAoeRange + polarDist;
                Predicate<Entity> trueCond = (target) -> EntityMethods.getEntityPos(target).distance(targetLoc) <= finalAoeRange;

                executeCommonSwdPhysicalDamageAction(weaponType, entity2, trueAOE, finalDmg, effEntity, playSoundWhenEffSpawn, condition.and(trueCond), sectorRange, spawnEffect, DamagedGroup, pushStrengthXZ, pushStrengthY, isDeltaAdded, damagedGroupAction);

                SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                if (!SendActionOffPacket.getBooleanVal()) {
                    SendActionOffPacket.setBooleanVal(true);
                    if (shouldSendInActionPacket) {
                        ServerSkillMethods.incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                    }
                }

                if (targetLocAction != null) {
                    targetLocAction.executeAction(entity2);
                }
            }

            tempTickCounter.setIntCounter(tempTickCounter.getIntCounter() + actionPeriod);
        };
    }

    public static MethodAction BludgeonShockwaveEffMethodAction(final Vector3f tempTriggeredEntityPosVec, final float finalAoeRange) {
        return (triggeredEntity) -> {
            SetupCircleTiltBlocksZone(new Vector3f(tempTriggeredEntityPosVec.x(), tempTriggeredEntityPosVec.y() - 1f, tempTriggeredEntityPosVec.z()), getEntityLevel(triggeredEntity),
                    finalAoeRange, 70, 0.5f, 4,
                    true, true, true, false);
        };
    }

    public static MethodAction BludgeonShockwaveEffMethodAction(final Entity entity, final float finalAoeRange) {
        return (triggeredEntity) -> {
            SetupCircleTiltBlocksZone(getEntityPos(entity).add(0f, -1f, 0f), getEntityLevel(triggeredEntity),
                    finalAoeRange, 70, 0.5f, 4,
                    true, true, true, false);
        };
    }

    public static MethodEntityAction BludgeonFlipShockwave1(final String ActionID, final LivingEntity Dealer, final float finalAoeRange, final float finalDmg, final Predicate<Entity> condition, final HashSet<LivingEntity> DamagedGroup) {
        return BludgeonFlipShockwaveEntityAction(ActionID, new IntegerValueHolder(0), Dealer, finalAoeRange, finalDmg, condition, DamagedGroup);
    }

    public static MethodAction BludgeonFlipShockwaveDamageAction(final LivingEntity Dealer, final float finalAoeRange, final float finalDmg, final Predicate<Entity> condition, final HashSet<LivingEntity> DamagedGroup, final String ActionID, BooleanValueHolder isPushed) {
        return (entity2) -> {
            for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition.and( entity -> !entity.equals(Dealer)))) {
                if (tempEntity instanceof LivingEntity tempLivingEntity) {
                    if (!DamagedGroup.contains(tempLivingEntity)) {
                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(Dealer, tempLivingEntity, finalDmg, false);

                        DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(DfoSkillTreeItemMethods.IS_SLOW_BLUDGEON, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 2.0f);
                        DamagedGroup.add(tempLivingEntity);
                    }
                    if (!isPushed.getBooleanVal()) {
                        isPushed.setBooleanVal(true);
                        float pushYStrength = MathMethods.max(0.35f, calculatePushEntityStrength(0.7f, Dealer, tempLivingEntity));
                        EntityMethods.setEntitySPDeltaMovement(tempLivingEntity, 0, pushYStrength, 0, false);
                    }
                }
            }
            SBSTickEventMethods.setSkillActionDone(entity2, ActionID);
        };
    }

    public static MethodEntityAction BludgeonFlipShockwaveEntityAction(String ActionID, IntegerValueHolder tempIntTickHolder, final LivingEntity Dealer, final float finalAoeRange, final float finalDmg, final Predicate<Entity> condition, final HashSet<LivingEntity> DamagedGroup) {
        return (tempEntity) -> {
            int currentTicker = tempIntTickHolder.getIntCounter();
            if (currentTicker > 1) {
                Entity owner;
                if (tempEntity instanceof ProjectileHelper helper && (owner = helper.getOwner()) != null) {
                    if (!SBConditions.isEntityAlive(owner)) {
                        EntityMethods.RemoveEntity(helper);
                    }
                    else if (EntityMethods.isEntityRFFarFromGround(owner, 0.2f)) {
//                    printInGameMsg("dist?: "+EntityMethods.isEntityFarFromGround(helper.getOwner(), 0.2f));
                        Vector3f tempOwnerVec = EntityMethods.getEntityPos(owner);
                        SetupCircleTiltBlocksZone(new Vector3f(tempOwnerVec.x(), tempOwnerVec.y() - 1f, tempOwnerVec.z()), EntityMethods.getEntityLevel(owner),
                                finalAoeRange, 70, 0.3f, 3,
                                true, true, true, false);

                        EntityMethods.RemoveEntity(helper);

                        final String ActionIDWithEntityID = ActionID + EntityMethods.getEntityHashID(tempEntity);
                        SBSTickEventMethods.setMethodActionTimerWithCleanAction(Dealer, ActionIDWithEntityID, 1, BludgeonFlipShockwaveDamageAction(Dealer, finalAoeRange + 1f, finalDmg, condition, DamagedGroup, ActionIDWithEntityID, new BooleanValueHolder(false)), null, true);
                    }
                }
            } else {
                tempIntTickHolder.setIntCounter(currentTicker + 1);
            }
        };
    }


    public static MethodEntityAction RemoveChannelingEffEntity(final LivingEntity channelingEntity) {
        return  (effEntity) -> {
            if (!ServerSkillMethods.isLivingEntityInChannelingAction(channelingEntity) || isLivingEntityActionInterrupted(channelingEntity)) {
                EntityMethods.RemoveEntity(effEntity);
            }
        };
    }

    public static MethodEntityAction RemoveEffEntityIfActionInterrupted(LivingEntity owner) {
        return (effEntity) -> {
            if (isLivingEntityActionInterrupted(owner)) {
                EntityMethods.RemoveEntity(effEntity);
            }
        };
    }

    public static Predicate<Entity> getShootBaseOnVerticalRotationCond(float EntityHorizontalFacingAngle, float EntityVerticalRotation, Vector3f damageCenter, float errorDegRange) {
//        float fromVerticalDeg = MathMethods.RoundAngle(EntityVerticalRotation - errorDegRange);
//        float toVerticalDeg = MathMethods.RoundAngle(EntityVerticalRotation + errorDegRange);

        return (target) -> {
            if (target instanceof ServerPlayer) { return false; }
            Vector3f targetPosLow = getEntityPos(target);
            Vector3f targetPosMid = getEntityPos(target).add(0, 1.6f, 0);
            Vector3f targetPosHigh = getEntityPos(target).add(0, 3.2f, 0);
            Vector3f[] targetPosLMH = {targetPosLow, targetPosMid, targetPosHigh};
//            Vector3f[] targetPosLMH = {targetPosLow};

            boolean isMeetCond = false;
//            printInGameMsg("loop start");
            for (Vector3f targetPos: targetPosLMH) {
                float opposite = Math.abs(damageCenter.y() - targetPos.y());
//                printInGameMsg("vertical rot: " + EntityVerticalRotation);
//            float thetaDeg = 90 - Math.abs(EntityVerticalRotation);
                float thetaDeg = Math.abs(EntityVerticalRotation);
//                printInGameMsg("thetaDeg: " + thetaDeg);
                float thetaRad = MathMethods.AngleToRadians(thetaDeg);
                float sinTheta = MathMethods.sin(thetaRad);
                float hypotenuse = (sinTheta == 0) ? opposite : opposite / MathMethods.sin(thetaRad);
                float HorizontalAngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(damageCenter, targetPos);

                Vector3f guesstimationVector3f = (hypotenuse == 0) ? new Vector3f(damageCenter.x(), targetPos.y(), damageCenter.z()) : Vector3fMethods.PolarProjectionFromRotation(damageCenter, hypotenuse, HorizontalAngleBtn, EntityVerticalRotation);


                Vector3f currentDamageLine_p1 = Vector3fMethods.PolarProjectionFromRotation(damageCenter, hypotenuse, EntityHorizontalFacingAngle, EntityVerticalRotation);
                Vector3f currentDamageLine_p2 = Vector3fMethods.PolarProjectionFromRotation(currentDamageLine_p1, 1f, EntityHorizontalFacingAngle + 90f, 0f);


                float[] IntersectedPoint2D = Vector2fMethods.LineIntersect(
                        damageCenter.x(), damageCenter.z(), guesstimationVector3f.x(), guesstimationVector3f.z(),
                        currentDamageLine_p1.x(), currentDamageLine_p1.z(), currentDamageLine_p2.x(), currentDamageLine_p2.z()
                );
//                float[] IntersectedPoint2D = Vector2fMethods.LineIntersect(
//                        damageCenter.z(), damageCenter.x(), guesstimationVector3f.z(), guesstimationVector3f.x(),
//                        currentDamageLine_p1.z(), currentDamageLine_p1.x(), currentDamageLine_p2.z(), currentDamageLine_p2.x()
//                );
                if (IntersectedPoint2D != null) {
                    Vector3f predictCorrectTargetPos = new Vector3f(IntersectedPoint2D[0], targetPos.y(), IntersectedPoint2D[1]);

//                    Entity AirObj = GeoModelSpawnMethods.GenerateBMSwdTempestBlockEff(target, 0f, 3f, 0, guesstimationVector3f.x(), guesstimationVector3f.y(), guesstimationVector3f.z(),
//                            160, guesstimationVector3f.x(), guesstimationVector3f.y(), guesstimationVector3f.z(), Blademaster025.BLADEMASTER025_TEMPEST_ORBIT_EFF, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, null, 777, true);
//                    Entity AirObj2 = GeoModelSpawnMethods.GenerateBMSwdTempestBlockEff(target, 0f, 6f, 0, predictCorrectTargetPos.x(), predictCorrectTargetPos.y(), predictCorrectTargetPos.z(),
//                            160, predictCorrectTargetPos.x(), predictCorrectTargetPos.y(), predictCorrectTargetPos.z(), Blademaster025.BLADEMASTER025_TEMPEST_ORBIT_EFF, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, null, 777, true);

//                    printInGameMsg("loop end: "+ (Vector3fMethods.Vec3fDistanceTo(predictCorrectTargetPos, targetPos) < 4f));
                    return Vector3fMethods.Vec3fDistanceTo(predictCorrectTargetPos, targetPos) < 4f;
                }
            }
//            printInGameMsg("loop end");
            return false;
        };
    }

    public static void DoNothing(LivingEntity entity1) {

    }
}
