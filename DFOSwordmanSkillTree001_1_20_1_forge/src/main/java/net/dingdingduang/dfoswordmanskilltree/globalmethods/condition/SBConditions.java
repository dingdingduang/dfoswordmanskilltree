package net.dingdingduang.dfoswordmanskilltree.globalmethods.condition;

import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerPlayerMethods;
import net.dingdingduang.somebasicskills.sbsattributes.SBSAttributes;
import net.dingdingduang.somebasicskills.util.MethodConfigHelper;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
//import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.OwnableEntity;

import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.decoration.Painting;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityPos;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getCPlayerConfig2Settings;

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
        return a instanceof Player;
    }

    public static boolean isEnemyEntity(Entity a) {
//        return a instanceof Monster;
        return a instanceof Enemy;
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
        return a instanceof Projectile;
    }

    public static boolean isMobEntity(Entity a) {
        return a instanceof Mob;
    }

    public static boolean isVillagerNpcEntity(Entity a) {
        return a instanceof Npc;
    }

    public static boolean isContainerEntity(Entity a) {
        return a instanceof Container;
    }

    public static boolean isAttackableEntity(Entity a) {
        return a instanceof Attackable;
    }

    public static boolean isNeutralEntity(Entity a) {
        return a instanceof NeutralMob;
    }

    //able to tame
    public static boolean isOwnableEntity(Entity a) {
        return a instanceof OwnableEntity;
    }

    public static boolean isDecorationEntity(Entity a) {
        return (a instanceof ArmorStand) || (a instanceof HangingEntity) || (a instanceof GlowItemFrame) ||
                (a instanceof ItemFrame) || (a instanceof LeashFenceKnotEntity) || (a instanceof Painting);
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
        float AngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(HurtEntityLoc, DamageSourceLoc);
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

    public static Predicate<Entity> defaultSkillTargetCondition(ServerPlayer sp1) {
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
