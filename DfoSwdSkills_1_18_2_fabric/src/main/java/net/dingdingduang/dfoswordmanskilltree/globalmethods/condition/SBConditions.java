package net.dingdingduang.dfoswordmanskilltree.globalmethods.condition;

import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerPlayerMethods;
import net.dingdingduang.somebasicskills.sbsattributes.SBSAttributes;
import net.dingdingduang.somebasicskills.util.MethodConfigHelper;

import net.minecraft.inventory.Inventory;
import net.minecraft.entity.*;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import modified.org.joml.Vector3f;

import java.util.HashMap;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.printInGameMsg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityPos;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getCPlayerConfig2Settings;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getSPlayerConfig;

public class SBConditions {
    public static boolean isEntityInvincible(Entity a) {
        if (a instanceof LivingEntity livingEntity) {
            return SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(livingEntity, SBSAttributes.SBS_ATTRIBUTE_BENEFICIAL_STATUS_INVINCIBILITY, Constants.OP_ADDITION) > 0;
        }
        return false;
    }

    public static boolean isItemEntity(Entity a) {
        return a instanceof ItemEntity;
    }

    public static boolean isEntityAlive(Entity a) { return a.isAlive(); }

    public static boolean isLivingEntity(Entity a) {
        return a instanceof LivingEntity;
    }

    public static boolean isPlayerEntity(Entity a) {
        return a instanceof PlayerEntity;
    }

    public static boolean isEnemyEntity(Entity a) {
//        return a instanceof Monster;
        return a instanceof HostileEntity;
    }

    public static boolean hasMoreThanZeroHealth(Entity a) {
        if (a instanceof LivingEntity entity1) {
            return entity1.getHealth() > 0.01;
        }
        else {
            return a.isAlive();
        }
    }

    public static boolean hasMoreThanZeroHealth(LivingEntity a) {
        return a.getHealth() > 0.0;
    }

    public static boolean isProjectileEntity(Entity a) {
        return a instanceof ProjectileEntity;
    }

    public static boolean isMobEntity(Entity a) {
        return a instanceof MobEntity;
    }

    public static boolean isVillagerNpcEntity(Entity a) {
        return a instanceof Npc;
    }

    public static boolean isContainerEntity(Entity a) {
        return a instanceof Inventory;
    }

    public static boolean isNeutralEntity(Entity a) {
        return a instanceof Angerable;
    }

    //able to tame
    public static boolean isOwnableEntity(Entity a) {
        return a instanceof Tameable;
    }

    public static boolean isDecorationEntity(Entity a) {
        return (a instanceof ArmorStandEntity) || (a instanceof AbstractDecorationEntity);
    }

    public static boolean isSpectator(Entity a) {
        return a.isSpectator();
    }

    public static boolean isEffectEntity(Entity a) {
        return a instanceof ProjectileHelper;
    }
    //isBoss? isHostile?

    public static boolean isBackStabbing(Entity damageSourceEntity, LivingEntity target) {
        Vector3f HurtEntityLoc = getEntityPos(target);
        Vector3f DamageSourceLoc = getEntityPos(damageSourceEntity);
        float AngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(HurtEntityLoc, DamageSourceLoc);
        float AngleBtnFrom = MathMethods.RoundAngle(AngleBtn - 120);
        float AngleBtnTo = MathMethods.RoundAngle(AngleBtn + 120);
        float targetHorizontalFacingAngle = MathMethods.RoundAngle(getEntityHorizontalFacingDeg(target));

        return Vector3fMethods.IsAngleDegBetweenAB(targetHorizontalFacingAngle, AngleBtnFrom, AngleBtnTo);
    }

    public static Predicate<Entity> isNotTargetEntity(Entity target) {
        return entity -> !entity.equals(target);
    }

    //==================
    //default condition set
    public static Predicate<Entity> isEnemyCondition() {
        return entity -> isEnemyEntity(entity) && hasMoreThanZeroHealth(entity) && !isEntityInvincible(entity);
    }

    public static Predicate<Entity> isLivingEntityCondition() {
        return entity -> isLivingEntity(entity) && hasMoreThanZeroHealth(entity) && !isEntityInvincible(entity);
    }

    public static Predicate<Entity> defaultSkillTargetCondition() {
        return isLivingEntityCondition();
    }

    public static Predicate<Entity> defaultSkillTargetCondition(ServerPlayerEntity sp1) {
//        int conditionOptionIndex = 0;
//        if (getSPlayerConfig().containsKey(sp1)
//                && getSPlayerConfig().get(sp1).containsKey(Constants.SB_GENERAL_SETTING)
//                && getSPlayerConfig().get(sp1).get(Constants.SB_GENERAL_SETTING).containsKey(Constants.CONDITION_OPTION_INDEX)
//        ) {
//            conditionOptionIndex = getSPlayerConfig().get(sp1).get(Constants.SB_GENERAL_SETTING).get(Constants.CONDITION_OPTION_INDEX).getIntValue();
//        }

        int conditionOptionIndex = ServerPlayerMethods.getSPlayerConfigVal(sp1, Constants.SB_GENERAL_SETTING, Constants.CONDITION_OPTION_INDEX);
//        printInGameMsg("conditionOptionIndex: " + conditionOptionIndex);
        if (conditionOptionIndex == 1) {
            //damage allies
            return isLivingEntityCondition();
        }
        else {
            //default
            return isEnemyCondition();
        }
    }

    public static Predicate<Entity> defaultSkillTargetClientCondition() {
        int conditionOptionIndex = 0;

        HashMap<String, HashMap<String, MethodConfigHelper>> tempClientPlayerParentConfigMap = getCPlayerConfig2Settings();
        HashMap<String, MethodConfigHelper> tempClientPlayerChildConfigMap;
        MethodConfigHelper methodConfigHelper;
        if (tempClientPlayerParentConfigMap != null && (tempClientPlayerChildConfigMap = tempClientPlayerParentConfigMap.get(Constants.SB_GENERAL_SETTING)) != null  && (methodConfigHelper = tempClientPlayerChildConfigMap.get(Constants.CONDITION_OPTION_INDEX)) != null ) {
            conditionOptionIndex = methodConfigHelper.getIntValue();
        }

        if (conditionOptionIndex == 1) {
            //damage allies
            return isLivingEntityCondition();
        }
        else {
            //default
            return isEnemyCondition();
        }
    }
}
