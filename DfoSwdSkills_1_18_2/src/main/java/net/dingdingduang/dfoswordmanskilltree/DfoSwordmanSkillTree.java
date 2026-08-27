package net.dingdingduang.dfoswordmanskilltree;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.DfoSwdGuiOverlayRegistry;

import net.dingdingduang.somebasicskills.keyboard.SomeBasicSkillTreeScreenKeySetting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.dingdingduang.dfoswordmanskilltree.entity.DfoEntitiesRegistry;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistry;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.SoundRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlocksRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockEntitiesRegistry;
import software.bernie.geckolib3.GeckoLib;

@Mod(DfoSwordmanSkillTreeConstants.MOD_ID)
public class DfoSwordmanSkillTree {
    public DfoSwordmanSkillTree() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GeckoLib.initialize();
        MinecraftForge.EVENT_BUS.register(this);

        DfoEntitiesRegistry.register(modEventBus);
//        EffectRegistry.register(modEventBus);
        SoundRegistry.register(modEventBus);
        GeoModelRegistry.register(modEventBus);
//        TestEntityRegistry.register(modEventBus);

        DfoSwdNetworkingMsgInitialization.register();
        //ground crack
        TiltBlocksRegistry.BLOCKS.register(modEventBus);
        TiltBlockEntitiesRegistry.BLOCK_ENTITIES.register(modEventBus);

        //initialization
        DistExecutor.unsafeRunWhenOn(
                Dist.CLIENT,
                () -> {
                    return () -> {
                        modEventBus.addListener(DfoSwdGuiOverlayRegistry::registerDfoSwdOverlays);
                    };
                }
        );
    }
}
