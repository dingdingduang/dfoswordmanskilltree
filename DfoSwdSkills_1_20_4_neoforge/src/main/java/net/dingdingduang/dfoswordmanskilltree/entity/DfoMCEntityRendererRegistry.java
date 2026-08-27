package net.dingdingduang.dfoswordmanskilltree.entity;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.entity.clientmovementhelper.ClientMovementHelperRenderer;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockEntitiesRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DfoMCEntityRendererRegistry {
    @SubscribeEvent
    public static void MCEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DfoEntitiesRegistry.CLIENT_MOVEMENT_HELPER.value(), ClientMovementHelperRenderer::new);
        //tilt block method
        event.registerBlockEntityRenderer(TiltBlockEntitiesRegistry.TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT.value(), TiltBlockRenderer::new);
    }
}
