package net.dingdingduang.dfoswordmanskilltree.networking;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class DfoSwdNetworkingMsgInitialization {
    public static void sendToServer(CustomPacketPayload payload) {
        net.neoforged.neoforge.network.PacketDistributor.SERVER.with(null).send(payload);
    }

    public static void sendToPlayer(CustomPacketPayload payload, ServerPlayer player) {
        net.neoforged.neoforge.network.PacketDistributor.PLAYER.with(player).send(payload);
    }
}