package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.neoforged.neoforge.client.model.data.ModelData;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;

public class TiltBlockRenderer implements BlockEntityRenderer<TiltBlockEntity> {
    private final BlockRenderDispatcher blockRenderDispatcher;

    public TiltBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
    }

//    @Override
//    public boolean shouldRender(TiltBlockEntity pBlockEntity, Vec3 pCameraPos) {
//        return Vec3.atCenterOf(pBlockEntity.getBlockPos()).closerThan(pCameraPos, this.getViewDistance());
//    }

    @Override
    public void render(TiltBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int lightColor, int overlayColor) {
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

            poseStack.pushPose();
            poseStack.mulPose(rotate);
            poseStack.translate(translate.x(), translate.y() + tempBouncingVaryHeight + varyHeight, translate.z());

            this.blockRenderDispatcher.renderBreakingTexture(blockEntity.getOriginalBlockState(), blockEntity.getBlockPos().above(), blockEntity.getLevel(), poseStack,
                    multiBufferSource.getBuffer(RenderType.cutout()), ModelData.EMPTY);

            poseStack.popPose();
        }
        else {
            poseStack.pushPose();

            this.blockRenderDispatcher.renderBreakingTexture(blockEntity.getOriginalBlockState(), blockEntity.getBlockPos().above(), blockEntity.getLevel(), poseStack,
                    multiBufferSource.getBuffer(RenderType.cutout()), ModelData.EMPTY);

            poseStack.popPose();
        }
    }
}
