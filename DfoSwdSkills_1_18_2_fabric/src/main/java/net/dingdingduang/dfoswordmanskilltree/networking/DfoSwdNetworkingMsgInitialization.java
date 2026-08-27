package net.dingdingduang.dfoswordmanskilltree.networking;

import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.*;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.entityextradata.FetchEntityExtraDataFromServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill.FetchBlademasterSlaughterscapeForceTriggerFromServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendUpdateEntityPosPacketToServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendTiltBlockActionPacketToServer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.S2CPlayChannelEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftServerPlayerList;

public class DfoSwdNetworkingMsgInitialization {
    public static void registerClientPacket() {
        ClientPlayNetworking.registerGlobalReceiver(FetchEntityExtraDataFromServer.ID, FetchEntityExtraDataFromServer::toClientHandle);

        ClientPlayNetworking.registerGlobalReceiver(FetchBlademasterSlaughterscapeForceTriggerFromServer.ID, FetchBlademasterSlaughterscapeForceTriggerFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchClientPlayerDeltaMovFromServer.ID, FetchClientPlayerDeltaMovFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchClientPlayerHorizontalFacingDegFromServer.ID, FetchClientPlayerHorizontalFacingDegFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchClientPlayerPosFromServer.ID, FetchClientPlayerPosFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchClientPlayerResetPacketWhenRespawnFromServer.ID, FetchClientPlayerResetPacketWhenRespawnFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchDfoSwdClientPlayerClearFromServer.ID, FetchDfoSwdClientPlayerClearFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchPlayerAnimationPacketForAllPlayersFromServer.ID, FetchPlayerAnimationPacketForAllPlayersFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchPlayerAnimationPacketFromServer.ID, FetchPlayerAnimationPacketFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer.ID, FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchPlayerKeyboardFreeFromServer.ID, FetchPlayerKeyboardFreeFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchPlayerKeyboardKidnapFromServer.ID, FetchPlayerKeyboardKidnapFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchTiltBlockActionPacketFromServer.ID, FetchTiltBlockActionPacketFromServer::toClientHandle);
        ClientPlayNetworking.registerGlobalReceiver(FetchUpdateClientPlayerPosFromServer.ID, FetchUpdateClientPlayerPosFromServer::toClientHandle);
    }

    public static void registerServerPacket() {
        ServerPlayNetworking.registerGlobalReceiver(SendTiltBlockActionPacketToServer.ID, SendTiltBlockActionPacketToServer::toServerHandle);
        ServerPlayNetworking.registerGlobalReceiver(SendUpdateEntityPosPacketToServer.ID, SendUpdateEntityPosPacketToServer::toServerHandle);
    }

    public static PacketByteBuf createPackage() {
        return PacketByteBufs.create();
    }

    public static void sendToServer(Identifier packetID, PacketByteBuf dataBuf) {
        ClientPlayNetworking.send(packetID, dataBuf);
    }

    public static void sendToPlayer(Identifier packetID, PacketByteBuf dataBuf, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, packetID, dataBuf);
    }

    public static void sendToAllPlayers(Identifier packetID, PacketByteBuf dataBuf) {
        for (ServerPlayerEntity sp1: getMinecraftServerPlayerList()) {
            ServerPlayNetworking.send(sp1, packetID, dataBuf);
        }
    }
}
