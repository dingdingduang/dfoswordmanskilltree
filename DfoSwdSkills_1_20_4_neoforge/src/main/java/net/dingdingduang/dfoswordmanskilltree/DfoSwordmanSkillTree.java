package net.dingdingduang.dfoswordmanskilltree;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.DfoSwdGuiOverlayRegistry;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import software.bernie.geckolib.GeckoLib;
import net.neoforged.neoforge.common.NeoForge;

import net.dingdingduang.dfoswordmanskilltree.entity.DfoEntitiesRegistry;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistry;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.SoundRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlocksRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockEntitiesRegistry;

@Mod(DfoSwordmanSkillTreeConstants.MOD_ID)
public class DfoSwordmanSkillTree {
    public DfoSwordmanSkillTree(IEventBus modEventBus) {
        GeckoLib.initialize(modEventBus);
//        NeoForge.EVENT_BUS.register(this);

        DfoEntitiesRegistry.register(modEventBus);
        SoundRegistry.register(modEventBus);
        GeoModelRegistry.register(modEventBus);

//        DfoSwdNetworkingEventRegister.register(modEventBus);
        //ground crack
        TiltBlocksRegistry.BLOCKS.register(modEventBus);
        TiltBlockEntitiesRegistry.BLOCK_ENTITIES.register(modEventBus);

        //GUI TODO: replace with clienttickerevent
        //initialization
        modEventBus.addListener(DfoSwdGuiOverlayRegistry::onDfoSwdRegisterOverlays);
    }
}
