package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegistryLayerEvent {
    @SubscribeEvent
    public static void humanoidLayerRendererRegister(EntityRenderersEvent.AddLayers event) {
//        for (EntityRenderer<? extends Entity> tempEntityRenderer: event.getContext().getEntityRenderDispatcher().renderers.values()) {
//            if (tempEntityRenderer instanceof LivingEntityRenderer<? extends LivingEntity, ? extends EntityModel<LivingEntity>> livingEntityRenderer && livingEntityRenderer.getModel() instanceof HumanoidModel<?> humanoidModel) {
//                RenderLayer<LivingEntity, EntityModel<LivingEntity>> tempKatanaLayer = new KatanaLayer<>(livingEntityRenderer, new KatanaModel(KatanaModel.createLeftOffHandLayer().bakeRoot()));
//
//                livingEntityRenderer.addLayer(tempKatanaLayer);
//            }
//        }

//        for (EntityRenderer<? extends Entity> tempEntityRenderer: event.getContext().getEntityRenderDispatcher().renderers.values()) {
//            if (tempEntityRenderer instanceof LivingEntityRenderer<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity>> livingEntityRenderer && livingEntityRenderer.getModel() instanceof HumanoidModel<?> humanoidModel) {
//
//                livingEntityRenderer.addLayer(new KatanaLayer<>(livingEntityRenderer, new KatanaModel<>(KatanaModel.createLeftOffHandLayer().bakeRoot())));
//            }
//        }

//        for (EntityRenderer<? extends Entity> tempEntityRenderer: event.getContext().getEntityRenderDispatcher().renderers.values()) {
//            if (tempEntityRenderer instanceof PlayerRenderer playerRenderer) {
//
//                playerRenderer.addLayer(new KatanaLayer<>(playerRenderer, new KatanaModel<>(KatanaModel.createLeftOffHandLayer().bakeRoot())));
//            }
//        }

        for (var skinType : event.getSkins()) {
            EntityRenderer<? extends Player> renderer = event.getSkin(skinType);

            if (renderer instanceof PlayerRenderer playerRenderer) {
                playerRenderer.addLayer(new SkillEffLayer<>(playerRenderer) );
            }
        }
    }
}