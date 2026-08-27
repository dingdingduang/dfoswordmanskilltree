package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class DfoTickEventRegistry {
    public static void registerClientEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(DfoTickEventRegistry::DFOSwdOnClientTickEvent);
    }

    private static void DFOSwdOnClientTickEvent(MinecraftClient client) {
        Entity playerEntity = client.player;
        if (playerEntity == null) { return; }
        int gameTick = EntityMethods.getEntityTickCount(playerEntity);
        DfoSwdGuiOverlayRegistry.registerFabricClientOverlaysFinal(gameTick);
    }
}
