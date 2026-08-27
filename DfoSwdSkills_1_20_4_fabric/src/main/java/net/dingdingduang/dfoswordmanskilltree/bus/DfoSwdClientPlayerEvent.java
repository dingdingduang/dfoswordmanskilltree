package net.dingdingduang.dfoswordmanskilltree.bus;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer;

import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getTemporaryBlockStateMap;

public class DfoSwdClientPlayerEvent {
    public static void register() {
//        ClientPlayConnectionEvents.JOIN.register(DfoSwdClientPlayerEvent::PlayerJoinActionOnClient);
        ClientPlayConnectionEvents.JOIN.register(DfoSwdClientPlayerEvent::DfoSwdInitWhenPlayerLogin);
        ClientPlayConnectionEvents.DISCONNECT.register(DfoSwdClientPlayerEvent::PlayerDisconnectActionOnClient);
    }

    private static void PlayerDisconnectActionOnClient(ClientPlayNetworkHandler handler, MinecraftClient client) {
        ClientItemOverlayTimer.getClientItemOverlayTimerOverlay().setActive(false);
        getTemporaryBlockStateMap().clear();
        ParticleRegistry.ParticleMapClear();
        GeoModelClientMethodsRegistry.GeoModelClientActionNameMapClear();
    }

//    private static void PlayerJoinActionOnClient(ClientPlayNetworkHandler handler, PacketSender packetSender, MinecraftClient client) {
//        KeyboardMethods.KeyboardMethodsInit();
//    }

    private static void DfoSwdInitWhenPlayerLogin(ClientPlayNetworkHandler handler, PacketSender packetSender, MinecraftClient client) {
        ClientItemOverlayTimer.ClientItemOverlayTimerInit();
        ParticleRegistry.ParticleRegistryInit();
        KeyboardMethods.KeyboardMethodsInit();
        GeoModelClientMethodsRegistry.GeoModelClientActionNameMapRegistryInit();
    }
}
