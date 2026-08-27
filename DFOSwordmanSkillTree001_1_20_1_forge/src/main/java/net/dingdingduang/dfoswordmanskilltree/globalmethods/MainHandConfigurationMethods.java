package net.dingdingduang.dfoswordmanskilltree.globalmethods;

import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.handeff.HandEffGetModelPartMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class MainHandConfigurationMethods {
//    public static final Map<HumanoidArm, String> MainHandMap = Map.of(
//            HumanoidArm.LEFT, HandEffGetModelPartMethods.LEFT_ARM,
//            HumanoidArm.RIGHT, HandEffGetModelPartMethods.RIGHT_ARM
//    );

    public static boolean isMainArmLeft(LivingEntity livingEntity) { return livingEntity.getMainArm() == HumanoidArm.LEFT; }
    public static boolean isMainArmRight(LivingEntity livingEntity) { return livingEntity.getMainArm() == HumanoidArm.RIGHT; }

    public static boolean isPlayerMainArmLeft(Player player) { return player.getMainArm() == HumanoidArm.LEFT; }

    public static float getCorrectZRot(LivingEntity livingEntity, float ZRot) {
        if (isMainArmRight(livingEntity)) {
            return ZRot;
        }
        else {
            return MathMethods.RoundAngle(-ZRot);
        }
    }

    public static float getCorrectZRot(Entity entity, float ZRot) {
        if (entity instanceof LivingEntity livingEntity) {
            return getCorrectZRot(livingEntity, ZRot);
        }
        return ZRot;
    }

    public static String getHandEffMainHandPart(LivingEntity livingEntity) {
        if (isMainArmLeft(livingEntity)) {
            return HandEffGetModelPartMethods.LEFT_ARM;
        }
        else {
            return HandEffGetModelPartMethods.RIGHT_ARM;
        }
    }
}
