package net.dingdingduang.dfoswordmanskilltree;

import net.dingdingduang.dfoswordmanskilltree.bus.DfoSwdEntityGroupEvent;

import net.dingdingduang.dfoswordmanskilltree.entity.DfoEntitiesRegistry;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistry;
import net.fabricmc.api.ModInitializer;

import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.SoundRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlocksRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockEntitiesRegistry;
import software.bernie.geckolib3.GeckoLib;

public class DfoSwordmanSkillTree implements ModInitializer {
    public static final String MOD_ID = DfoSwordmanSkillTreeConstants.MOD_ID;

    public void onInitialize() {
        GeckoLib.initialize();

        new DfoEntitiesRegistry();

        new TiltBlocksRegistry();
        new TiltBlockEntitiesRegistry();
        TiltBlocksRegistry.register();
        TiltBlockEntitiesRegistry.register();

        new GeoModelRegistry();
        new SoundRegistry();

        DfoSwdNetworkingMsgInitialization.registerClientPacket();
        DfoSwdNetworkingMsgInitialization.registerServerPacket();
        DfoSwdEntityGroupEvent.registerServerTickEvent();
    }
}
