package net.dingdingduang.dfoswordmanskilltree;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.DfoSwdGuiOverlayRegistry;

import net.dingdingduang.dfoswordmanskilltree.particle.MCParticleRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;

import net.dingdingduang.dfoswordmanskilltree.entity.DfoEntitiesRegistry;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistry;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.SoundRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlocksRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockEntitiesRegistry;

@Mod(DfoSwordmanSkillTreeConstants.MOD_ID)
public class DfoSwordmanSkillTree {
    public DfoSwordmanSkillTree() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GeckoLib.initialize();

        DfoEntitiesRegistry.register(modEventBus);
//        EffectRegistry.register(modEventBus);
        SoundRegistry.register(modEventBus);
        GeoModelRegistry.register(modEventBus);

        DfoSwdNetworkingMsgInitialization.register();
        //ground crack
        TiltBlocksRegistry.BLOCKS.register(modEventBus);
        TiltBlockEntitiesRegistry.BLOCK_ENTITIES.register(modEventBus);

        MCParticleRegistry.register(modEventBus);

        //GUI TODO: replace with clienttickerevent
        //initialization
        modEventBus.addListener(DfoSwdGuiOverlayRegistry::onDfoSwdRegisterOverlays);
    }
}
