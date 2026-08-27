package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.BlockMethods;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;
import net.minecraft.world.World;

import modified.org.joml.Quaternionf;
import modified.org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;

public class TiltBlockRenderer implements BlockEntityRenderer<TiltBlockEntity> {
    private final BlockRenderManager blockRenderDispatcher;

    public TiltBlockRenderer(BlockEntityRendererFactory.Context context) {
        this.blockRenderDispatcher = context.getRenderManager();
    }

//    @Override
//    public boolean shouldRender(TiltBlockEntity pBlockEntity, Vec3 pCameraPos) {
//        return Vec3.atCenterOf(pBlockEntity.getBlockPos()).closerThan(pCameraPos, this.getViewDistance());
//    }

    @Override
    public void render(TiltBlockEntity blockEntity, float partialTicks, MatrixStack poseStack, VertexConsumerProvider multiBufferSource, int lightColor, int overlayColor) {
        int blockEntityCurrentLifeTime = blockEntity.getLifeTimeTicks(), blockEntityMaxLifeTime = blockEntity.getMaxLifeTimeTicks();

        int bouncingTransitionTicks = Math.round((blockEntity.getDist() / blockEntity.getTotalDist()) * blockEntity.getBouncingTimeTicks());
        float tempBouncingVaryHeight = 0.0f;
        if (blockEntityCurrentLifeTime >= bouncingTransitionTicks) {
            int blockEntityCurrentBouncingTime = blockEntityCurrentLifeTime - bouncingTransitionTicks;

            int stabilizationAnimTicks = 5;
            Vector3f translate = blockEntity.getTranslate();
            Quaternionf rotate = blockEntity.getRotation();
            if ( (blockEntityMaxLifeTime <= blockEntityCurrentLifeTime + stabilizationAnimTicks) ) {
                float alpha = ExtraMathMethods.clamp(((1.0f * (blockEntityCurrentLifeTime + stabilizationAnimTicks) - blockEntityMaxLifeTime)) / stabilizationAnimTicks, 0, 1);
                translate = blockEntity.getTranslate().lerp(new Vector3f(0, 0, 0), alpha);
                Quaternionf rotator = new Quaternionf();
                rotator.fromAxisAngleRad(1, 1, 1, 0);
                rotate = blockEntity.getRotation().slerp(rotator, alpha, new Quaternionf());
            }
            else if (blockEntityCurrentBouncingTime <= stabilizationAnimTicks) {
                float alpha = ExtraMathMethods.clamp((1.0f * blockEntityCurrentBouncingTime) / stabilizationAnimTicks, 0, 1);
                Vector3f origin = new Vector3f(0, 0, 0);
                translate = origin.lerp(blockEntity.getTranslate(), alpha);
                Quaternionf rotator = new Quaternionf();
                rotator.fromAxisAngleRad(1, 1, 1, 0);
                rotate = rotator.slerp(blockEntity.getRotation(), alpha, new Quaternionf());
            }

            int bouncingTotalTicks = blockEntity.getBouncingTimeTicks(), bouncingMidTicks = bouncingTotalTicks / 2;

            if (blockEntityCurrentBouncingTime <= bouncingMidTicks) {
                tempBouncingVaryHeight = blockEntity.getBouncingHeight() * (1.0f * blockEntityCurrentBouncingTime / bouncingMidTicks);
            }
            else if (blockEntityCurrentBouncingTime <= bouncingTotalTicks) {
                tempBouncingVaryHeight = blockEntity.getBouncingHeight() * (1 - (1.0f * (blockEntityCurrentBouncingTime - bouncingMidTicks) / (bouncingTotalTicks - bouncingMidTicks)));
            }

            float height = blockEntity.getDist() / blockEntity.getTotalDist() * 0.25f+0.05f;
            float varyHeight = height * (1 - 1.0f * blockEntityCurrentBouncingTime / (blockEntityMaxLifeTime-bouncingTransitionTicks)) + (-0.15f * (1 - 1.0f * blockEntityCurrentBouncingTime / (blockEntityMaxLifeTime-bouncingTransitionTicks)));

            poseStack.push();
            Quaternion mcQuaternion = new Quaternion(rotate.x, rotate.y, rotate.z, rotate.w);
            poseStack.multiply(mcQuaternion);
            poseStack.translate(translate.x(), translate.y() + tempBouncingVaryHeight + varyHeight, translate.z());

            World entityLevel = blockEntity.getWorld();
            if (entityLevel != null) {
//                this.blockRenderDispatcher.renderBlock();

//                this.blockRenderDispatcher.renderDamage(blockEntity.getOriginalBlockState(), blockEntity.getPos().up(), entityLevel, poseStack,
//                        multiBufferSource.getBuffer(RenderLayer.getCutout()), EmptyModelData.INSTANCE);
                this.blockRenderDispatcher.renderDamage(blockEntity.getOriginalBlockState(), BlockMethods.getUpperBlockPos(blockEntity.getPos()), entityLevel, poseStack,
                        multiBufferSource.getBuffer(RenderLayer.getCutout()));
            }

            poseStack.pop();
        }
        else {
            poseStack.push();

            World entityLevel = blockEntity.getWorld();
            if (entityLevel != null) {
                this.blockRenderDispatcher.renderDamage(blockEntity.getOriginalBlockState(), BlockMethods.getUpperBlockPos(blockEntity.getPos()), entityLevel, poseStack,
                        multiBufferSource.getBuffer(RenderLayer.getCutout()));
            }

            poseStack.pop();
        }
    }
}
