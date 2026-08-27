package net.dingdingduang.dfoswordmanskilltree.bus;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import org.joml.Quaternionf;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class DfoSwdLivingEntityRenderPreEvent {
    private static HashMap<LivingEntity, Float> PreviousEntityXRotDegree = new HashMap<>();
    private static HashMap<LivingEntity, Integer> PreviousEntityClientTickCounter = new HashMap<>();
//    private static HashSet<LivingEntity> LivingEntityTBR = new HashSet<>();

    public static void DfoSwdRendererPre(LivingEntity entity, float partialTick, MatrixStack matrixStack) {
        Float FloatPreviousDeg = PreviousEntityXRotDegree.get(entity);
        int currentEntityRotClientTick;

        if (FloatPreviousDeg != null) {
            if ( (currentEntityRotClientTick = PreviousEntityClientTickCounter.get(entity)) < 15 || !EntityMethods.isEntityRFFarFromGround(entity, 0.1f) ) {
                matrixStack.push();

                int degree = (EntityMethods.getEntityTickCount(entity) % 360) * 30 % 360;
                float trueDegree;
                float previousD = FloatPreviousDeg;
                if (previousD > degree) {
                    trueDegree = Vector3fMethods.lerp1D(partialTick, previousD, degree + 360f) % 360;
                } else {
                    trueDegree = Vector3fMethods.lerp1D(partialTick, previousD, degree);
                }
//            previousD = degree;
                PreviousEntityXRotDegree.put(entity, (float) degree);
                PreviousEntityClientTickCounter.put(entity, currentEntityRotClientTick + 1);
                float deg2rad = MathMethods.AngleToRadians(trueDegree);

//            float deg2rad = MathMethods.AngleToRadians(degree);
                float tempDeg2Rad = 0.25f * deg2rad;
                float percent = Math.abs(MathMethods.sin(tempDeg2Rad));
                float actualTranslationY = EntityMethods.getEntityRegistryDefinedHeight(entity) * percent;

//            event.getPoseStack().translate(entity.getBbWidth() / 2, actualTranslationY, entity.getBbWidth() / 2);
                matrixStack.translate(0, actualTranslationY, 0);

//            Quaternionf rotatorY = new Quaternionf();
//            QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(test1 % 360), 0f, -1f, 0f);

                Quaternionf rotatorX = new Quaternionf();
                QuatRotationByAxis(rotatorX, deg2rad, 1.0f, 0f, 0f);
//            rotatorY.mul(rotatorX);

//            event.getPoseStack().mulPose(rotatorY);
                matrixStack.multiply(rotatorX);

//            event.getPoseStack().translate(-entity.getBbWidth() / 2, -actualTranslationY, -entity.getBbWidth() / 2);
                matrixStack.translate(0, -actualTranslationY, 0);

                matrixStack.push();
            }
            else {
//            LivingEntityTBR.remove(entity);
                PreviousEntityXRotDegree.remove(entity);
                PreviousEntityClientTickCounter.remove(entity);
            }
        }
    }

    public static void DfoSwdRendererPost(LivingEntity renderedEntity, MatrixStack matrixStack) {
        Float FloatPreviousDeg = PreviousEntityXRotDegree.get(renderedEntity);
        if (FloatPreviousDeg != null) {
            matrixStack.pop();
            matrixStack.pop();
        }
//        if (LivingEntityTBR.contains(entity)) {
//            PreviousEntityXRotDegree.remove(entity);
//        }
    }

    public static HashMap<LivingEntity, Float> getPreviousEntityXRotDegree() { return PreviousEntityXRotDegree; }
//    public static void setPreviousEntityXRotDegree(HashMap<LivingEntity, Float> previousEntityXRotDegree) { PreviousEntityXRotDegree = previousEntityXRotDegree; }
//    public static void removePreviousEntityXRotDegree(LivingEntity entity) { PreviousEntityXRotDegree.remove(entity); }
    public static void addPreviousEntityXRotDegree(LivingEntity entity) {
        PreviousEntityClientTickCounter.put(entity, 0);
        PreviousEntityXRotDegree.put(entity, (float) EntityMethods.getEntityTickCount(entity));
    }
//    public static void removePreviousEntityXRotDegree(Entity entity) {
//        if (entity instanceof LivingEntity livingEntity) {
//            LivingEntityTBR.add(livingEntity);
//        }
//    }
    public static void addPreviousEntityXRotDegree(Entity entity) {
        if (entity instanceof LivingEntity entity1) {
            PreviousEntityClientTickCounter.put(entity1, 0);
            PreviousEntityXRotDegree.put(entity1, (float) EntityMethods.getEntityTickCount(entity));
        }
    }
}
