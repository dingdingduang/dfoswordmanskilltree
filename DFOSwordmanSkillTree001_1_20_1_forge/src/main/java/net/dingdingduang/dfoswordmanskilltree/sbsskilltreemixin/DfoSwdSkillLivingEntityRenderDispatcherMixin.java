package net.dingdingduang.dfoswordmanskilltree.sbsskilltreemixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dingdingduang.dfoswordmanskilltree.bus.DfoSwdLivingEntityRenderPreEvent;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.SkillEffLayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class DfoSwdSkillLivingEntityRenderDispatcherMixin {
    @Inject(method = "render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"),
            remap = true
    )
    private <E extends Entity> void DfoEntityRendererPre(E pEntity, double pX, double pY, double pZ, float pRotationYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        if (pEntity instanceof LivingEntity livingEntity) {
            DfoSwdLivingEntityRenderPreEvent.DfoSwdRendererPre(livingEntity, pPartialTicks, pMatrixStack);
            SkillEffLayer.render(livingEntity, pPartialTicks, pRotationYaw, pPackedLight, pMatrixStack, pBuffer);
        }
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"),
            remap = true
    )
    private <E extends Entity> void DfoEntityRendererPost(E pEntity, double pX, double pY, double pZ, float pRotationYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        if (pEntity instanceof LivingEntity livingEntity) {
            DfoSwdLivingEntityRenderPreEvent.DfoSwdRendererPost(livingEntity, pMatrixStack);
        }
    }

//    @Inject(method = "render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;"),
//            remap = true
//    )
//    private <E extends Entity> void DfoEntityRendererPre2Test(E pEntity, double pX, double pY, double pZ, float pRotationYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
//        if (pEntity instanceof LivingEntity livingEntity) {
//            SkillEffLayer.render(livingEntity, pPartialTicks, pRotationYaw, pMatrixStack, pBuffer);
//        }
//    }
}
