package net.dingdingduang.dfoswordmanskilltree;

import net.dingdingduang.dfoswordmanskilltree.bus.DfoSwdClientPlayerEvent;
import net.dingdingduang.dfoswordmanskilltree.bus.DfoSwdEntityGroupEvent;
import net.dingdingduang.dfoswordmanskilltree.bus.DfoSwdClientTickEvent;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.DfoTickEventRegistry;
import net.dingdingduang.dfoswordmanskilltree.entity.DfoEntitiesRegistry;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistry;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBAnimInit;
import net.fabricmc.api.ClientModInitializer;

public class DfoSwordmanSkillTreeClientInitialization implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GeoModelRegistry.registerRendererOnClient();
        DfoEntitiesRegistry.registerRendererOnClient();
        DfoTickEventRegistry.registerClientEvent();
        DfoSwdClientPlayerEvent.register();
        DfoSwdEntityGroupEvent.registerClientTickEvent();
        SBAnimInit.onClientSetupForDfoSwordmanAnimationInit();
//        RegistryDfoSwdSkillEffLayerEvent.registerOnClient();

        DfoSwdClientTickEvent.registerClientTickEvent();
    }
}
