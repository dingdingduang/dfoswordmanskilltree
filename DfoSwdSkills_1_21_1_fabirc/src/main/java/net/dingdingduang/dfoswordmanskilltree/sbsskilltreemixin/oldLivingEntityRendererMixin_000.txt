package net.dingdingduang.dfoswordmanskilltree.sbsskilltreemixin;

import net.dingdingduang.dfoswordmanskilltree.bus.DfoSwdLivingEntityRenderPreEvent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class DfoSwdSkillLivingEntityRendererMixin <T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
    protected DfoSwdSkillLivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "render*", at = @At("HEAD"))
    private void DfoSwdLivingEntityRendererPre(T livingEntity, float pEntityYaw, float pPartialTicks, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int pPackedLight, CallbackInfo info) {
        DfoSwdLivingEntityRenderPreEvent.DfoSwdRendererPre(livingEntity, pPartialTicks, matrixStack);
    }

    @Inject(method = "render*", at = @At("TAIL"))
    private void DfoSwdLivingEntityRendererPost(T livingEntity, float pEntityYaw, float pPartialTicks, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int pPackedLight, CallbackInfo info) {
        DfoSwdLivingEntityRenderPreEvent.DfoSwdRendererPost(livingEntity, matrixStack);
    }

//    @Override
//    public M getModel() {
//        return null;
//    }
//
//    @Override
//    public Identifier getTexture(T entity) {
//        return null;
//    }
}
