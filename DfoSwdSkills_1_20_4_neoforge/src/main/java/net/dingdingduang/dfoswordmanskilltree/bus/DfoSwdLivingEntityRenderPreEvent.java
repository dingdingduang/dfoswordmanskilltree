package net.dingdingduang.dfoswordmanskilltree.bus;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import org.joml.Quaternionf;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

//@OnlyIn(Dist.CLIENT)
//@Mod.EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, value = Dist.CLIENT)
public class DfoSwdLivingEntityRenderPreEvent {
    private static final HashMap<LivingEntity, Float> PreviousEntityXRotDegree = new HashMap<>();
    private static final HashMap<LivingEntity, Integer> PreviousEntityClientTickCounter = new HashMap<>();
//    private static HashSet<LivingEntity> LivingEntityTBR = new HashSet<>();

    @SubscribeEvent
    public static void DfoSwdRendererPre(RenderLivingEvent.Pre<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity> > event) {
        LivingEntity entity = event.getEntity();
        Float FloatPreviousDeg = PreviousEntityXRotDegree.get(entity);
        int currentEntityRotClientTick;

        if (FloatPreviousDeg != null) {
            if ( (currentEntityRotClientTick = PreviousEntityClientTickCounter.get(entity)) < 15 || !EntityMethods.isEntityRFFarFromGround(entity, 0.1f) ) {
                event.getPoseStack().pushPose();

                int degree = (EntityMethods.getEntityTickCount(entity) % 360) * 30 % 360;
                float trueDegree;
                float previousD = FloatPreviousDeg;
                if (previousD > degree) {
                    trueDegree = Vector3fMethods.lerp1D(event.getPartialTick(), previousD, degree + 360f) % 360;
                } else {
                    trueDegree = Vector3fMethods.lerp1D(event.getPartialTick(), previousD, degree);
                }
//            previousD = degree;
                PreviousEntityXRotDegree.put(entity, (float) degree);
                PreviousEntityClientTickCounter.put(entity, currentEntityRotClientTick + 1);
                float deg2rad = MathMethods.AngleToRadians(trueDegree);

//            float deg2rad = MathMethods.AngleToRadians(degree);
                float tempDeg2Rad = 0.25f * deg2rad;
                float percent = Math.abs(MathMethods.sin(tempDeg2Rad));
                float actualtranslationY = entity.getBbHeight() * percent;

//            event.getPoseStack().translate(entity.getBbWidth() / 2, actualtranslationY, entity.getBbWidth() / 2);
                event.getPoseStack().translate(0, actualtranslationY, 0);

//            Quaternionf rotatorY = new Quaternionf();
//            QuatRotationByAxis(rotatorY, MathMethods.AngleToRadians(test1 % 360), 0f, -1f, 0f);

                Quaternionf rotatorX = new Quaternionf();
                QuatRotationByAxis(rotatorX, deg2rad, 1.0f, 0f, 0f);
//            rotatorY.mul(rotatorX);

//            event.getPoseStack().mulPose(rotatorY);
                event.getPoseStack().mulPose(rotatorX);

//            event.getPoseStack().translate(-entity.getBbWidth() / 2, -actualtranslationY, -entity.getBbWidth() / 2);
                event.getPoseStack().translate(0, -actualtranslationY, 0);

                event.getPoseStack().pushPose();
            }
            else {
//            LivingEntityTBR.remove(entity);
                PreviousEntityXRotDegree.remove(entity);
                PreviousEntityClientTickCounter.remove(entity);
            }
        }
    }

    @SubscribeEvent
    public static void DfoSwdRendererPost(RenderLivingEvent.Post<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity> > event) {
        LivingEntity entity = event.getEntity();
        Float FloatPreviousDeg = PreviousEntityXRotDegree.get(entity);
        if (FloatPreviousDeg != null) {
            event.getPoseStack().popPose();
            event.getPoseStack().popPose();
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
