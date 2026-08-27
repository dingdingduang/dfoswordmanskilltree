package net.dingdingduang.dfoswordmanskilltree;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.DfoSwdGatherDataEvent;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.DfoSwdGuiOverlayRegistry;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistry;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.SoundRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlocksRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockEntitiesRegistry;

@Mod(DfoSwordmanSkillTreeConstants.MOD_ID)
public class DfoSwordmanSkillTree {
    public DfoSwordmanSkillTree(IEventBus modEventBus, ModContainer modContainer) {
//        GeckoLib.initialize(modEventBus);
//        NeoForge.EVENT_BUS.register(this);

//        DfoEntitiesRegistry.register(modEventBus);
        SoundRegistry.register(modEventBus);
        GeoModelRegistry.register(modEventBus);

//        DfoSwdNetworkingEventRegister.register(modEventBus);
        //ground crack
        TiltBlocksRegistry.BLOCKS.register(modEventBus);
        TiltBlockEntitiesRegistry.BLOCK_ENTITIES.register(modEventBus);

        //GUI TODO: replace with clienttickerevent
        //initialization
        modEventBus.addListener(DfoSwdGuiOverlayRegistry::onDfoSwdRegisterOverlays);
//        modEventBus.addListener(DfoSwdGatherDataEvent::DfoSwordmanGatherDataEventOnClient);
//        modEventBus.addListener(DfoSwdGatherDataEvent::DfoSwordmanGatherDataEventOnServer);
    }
}
