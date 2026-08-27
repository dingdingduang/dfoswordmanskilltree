package net.dingdingduang.dfoswordmanskilltree.networking;

import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.*;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.entityextradata.FetchEntityExtraDataFromServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill.FetchBlademasterSlaughterscapeForceTriggerFromServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendUpdateEntityPosPacketToServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendTiltBlockActionPacketToServer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftServerPlayerList;

public class DfoSwdNetworkingMsgInitialization {
    public static void registerClientPacket() {
        PayloadTypeRegistry.playS2C().register(FetchEntityExtraDataFromServer.TYPE, FetchEntityExtraDataFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchEntityExtraDataFromServer.TYPE, FetchEntityExtraDataFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchBlademasterSlaughterscapeForceTriggerFromServer.TYPE, FetchBlademasterSlaughterscapeForceTriggerFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchBlademasterSlaughterscapeForceTriggerFromServer.TYPE, FetchBlademasterSlaughterscapeForceTriggerFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchClientPlayerDeltaMovFromServer.TYPE, FetchClientPlayerDeltaMovFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchClientPlayerDeltaMovFromServer.TYPE, FetchClientPlayerDeltaMovFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchClientPlayerHorizontalFacingDegFromServer.TYPE, FetchClientPlayerHorizontalFacingDegFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchClientPlayerHorizontalFacingDegFromServer.TYPE, FetchClientPlayerHorizontalFacingDegFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchClientPlayerPosFromServer.TYPE, FetchClientPlayerPosFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchClientPlayerPosFromServer.TYPE, FetchClientPlayerPosFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchClientPlayerResetPacketWhenRespawnFromServer.TYPE, FetchClientPlayerResetPacketWhenRespawnFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchClientPlayerResetPacketWhenRespawnFromServer.TYPE, FetchClientPlayerResetPacketWhenRespawnFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchDfoSwdClientPlayerClearFromServer.TYPE, FetchDfoSwdClientPlayerClearFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchDfoSwdClientPlayerClearFromServer.TYPE, FetchDfoSwdClientPlayerClearFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchPlayerAnimationPacketForAllPlayersFromServer.TYPE, FetchPlayerAnimationPacketForAllPlayersFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchPlayerAnimationPacketForAllPlayersFromServer.TYPE, FetchPlayerAnimationPacketForAllPlayersFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchPlayerAnimationPacketFromServer.TYPE, FetchPlayerAnimationPacketFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchPlayerAnimationPacketFromServer.TYPE, FetchPlayerAnimationPacketFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer.TYPE, FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer.TYPE, FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchPlayerKeyboardFreeFromServer.TYPE, FetchPlayerKeyboardFreeFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchPlayerKeyboardFreeFromServer.TYPE, FetchPlayerKeyboardFreeFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchPlayerKeyboardKidnapFromServer.TYPE, FetchPlayerKeyboardKidnapFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchPlayerKeyboardKidnapFromServer.TYPE, FetchPlayerKeyboardKidnapFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchTiltBlockActionPacketFromServer.TYPE, FetchTiltBlockActionPacketFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchTiltBlockActionPacketFromServer.TYPE, FetchTiltBlockActionPacketFromServer::handle);

        PayloadTypeRegistry.playS2C().register(FetchUpdateClientPlayerPosFromServer.TYPE, FetchUpdateClientPlayerPosFromServer.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FetchUpdateClientPlayerPosFromServer.TYPE, FetchUpdateClientPlayerPosFromServer::handle);
    }

    public static void registerServerPacket() {
        PayloadTypeRegistry.playC2S().register(SendTiltBlockActionPacketToServer.TYPE, SendTiltBlockActionPacketToServer.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SendTiltBlockActionPacketToServer.TYPE, SendTiltBlockActionPacketToServer::handle);

        PayloadTypeRegistry.playC2S().register(SendUpdateEntityPosPacketToServer.TYPE, SendUpdateEntityPosPacketToServer.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SendUpdateEntityPosPacketToServer.TYPE, SendUpdateEntityPosPacketToServer::handle);
    }

    public static PacketByteBuf createPackage() {
        return PacketByteBufs.create();
    }


    public static void sendToServer(CustomPayload customPacket) {
        ClientPlayNetworking.send(customPacket);
    }

    public static void sendToPlayer(CustomPayload customPacket, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, customPacket);
    }

    public static void sendToAllPlayers(CustomPayload customPacket) {
        for (ServerPlayerEntity sp1: getMinecraftServerPlayerList()) {
            ServerPlayNetworking.send(sp1, customPacket);
        }
    }
}
