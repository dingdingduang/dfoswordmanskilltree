package net.dingdingduang.dfoswordmanskilltree.networking;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.*;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill.FetchBlademasterSlaughterscapeForceTriggerFromServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendUpdateEntityPosPacketToServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendTiltBlockActionPacketToServer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class DfoSwdNetworkingMsgInitialization {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.newSimpleChannel(new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "swordsman_skill_network_mgr"),
                () -> "1.0",
                s -> true,
                s -> true);

        INSTANCE = net;

        //=================================
        //TEST
//        net.messageBuilder(SendTest000ToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
//                .decoder(SendTest000ToServer::new)
//                .encoder(SendTest000ToServer::toBytes)
//                .consumer(SendTest000ToServer::handle)
//                .add();
//
//        net.messageBuilder(SendTest001ToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
//                .decoder(SendTest001ToServer::new)
//                .encoder(SendTest001ToServer::toBytes)
//                .consumer(SendTest001ToServer::handle)
//                .add();



        //send shiet to server only without sending packet back to client
        //server <--- client side
//        net.messageBuilder(SendAddBtnActionToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
//                .decoder(SendAddBtnActionToServer::new)
//                .encoder(SendAddBtnActionToServer::toBytes)
//                .consumer(SendAddBtnActionToServer::handle)
//                .add();

        net.messageBuilder(SendUpdateEntityPosPacketToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SendUpdateEntityPosPacketToServer::new)
                .encoder(SendUpdateEntityPosPacketToServer::toBytes)
                .consumer(SendUpdateEntityPosPacketToServer::handle)
                .add();



        //=============================
        //server ---> client side
//        net.messageBuilder(FetchStringMsgFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(FetchStringMsgFromServer::new)
//                .encoder(FetchStringMsgFromServer::toBytes)
//                .consumer(FetchStringMsgFromServer::handle)
//                .add();

        net.messageBuilder(FetchPlayerAnimationPacketFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchPlayerAnimationPacketFromServer::new)
                .encoder(FetchPlayerAnimationPacketFromServer::toBytes)
                .consumer(FetchPlayerAnimationPacketFromServer::handle)
                .add();

        net.messageBuilder(FetchClientPlayerDeltaMovFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchClientPlayerDeltaMovFromServer::new)
                .encoder(FetchClientPlayerDeltaMovFromServer::toBytes)
                .consumer(FetchClientPlayerDeltaMovFromServer::handle)
                .add();

        net.messageBuilder(FetchClientPlayerPosFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchClientPlayerPosFromServer::new)
                .encoder(FetchClientPlayerPosFromServer::toBytes)
                .consumer(FetchClientPlayerPosFromServer::handle)
                .add();

        net.messageBuilder(FetchDfoSwdClientPlayerInitFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchDfoSwdClientPlayerInitFromServer::new)
                .encoder(FetchDfoSwdClientPlayerInitFromServer::toBytes)
                .consumer(FetchDfoSwdClientPlayerInitFromServer::handle)
                .add();

        net.messageBuilder(FetchDfoSwdClientPlayerClearFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchDfoSwdClientPlayerClearFromServer::new)
                .encoder(FetchDfoSwdClientPlayerClearFromServer::toBytes)
                .consumer(FetchDfoSwdClientPlayerClearFromServer::handle)
                .add();

        net.messageBuilder(FetchPlayerAnimationPacketForAllPlayersFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchPlayerAnimationPacketForAllPlayersFromServer::new)
                .encoder(FetchPlayerAnimationPacketForAllPlayersFromServer::toBytes)
                .consumer(FetchPlayerAnimationPacketForAllPlayersFromServer::handle)
                .add();

        net.messageBuilder(FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::new)
                .encoder(FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::toBytes)
                .consumer(FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::handle)
                .add();

//        net.messageBuilder(FetchPlayerDataAfterRespawnFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(FetchPlayerDataAfterRespawnFromServer::new)
//                .encoder(FetchPlayerDataAfterRespawnFromServer::toBytes)
//                .consumer(FetchPlayerDataAfterRespawnFromServer::handle)
//                .add();

        net.messageBuilder(FetchPlayerKeyboardFreeFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchPlayerKeyboardFreeFromServer::new)
                .encoder(FetchPlayerKeyboardFreeFromServer::toBytes)
                .consumer(FetchPlayerKeyboardFreeFromServer::handle)
                .add();

        net.messageBuilder(FetchPlayerKeyboardKidnapFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchPlayerKeyboardKidnapFromServer::new)
                .encoder(FetchPlayerKeyboardKidnapFromServer::toBytes)
                .consumer(FetchPlayerKeyboardKidnapFromServer::handle)
                .add();

        net.messageBuilder(FetchClientPlayerHorizontalFacingDegFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchClientPlayerHorizontalFacingDegFromServer::new)
                .encoder(FetchClientPlayerHorizontalFacingDegFromServer::toBytes)
                .consumer(FetchClientPlayerHorizontalFacingDegFromServer::handle)
                .add();

        net.messageBuilder(FetchUpdateClientPlayerPosFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchUpdateClientPlayerPosFromServer::new)
                .encoder(FetchUpdateClientPlayerPosFromServer::toBytes)
                .consumer(FetchUpdateClientPlayerPosFromServer::handle)
                .add();

        net.messageBuilder(FetchClientPlayerResetPacketWhenRespawnFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchClientPlayerResetPacketWhenRespawnFromServer::new)
                .encoder(FetchClientPlayerResetPacketWhenRespawnFromServer::toBytes)
                .consumer(FetchClientPlayerResetPacketWhenRespawnFromServer::handle)
                .add();

        net.messageBuilder(FetchBlademasterSlaughterscapeForceTriggerFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchBlademasterSlaughterscapeForceTriggerFromServer::new)
                .encoder(FetchBlademasterSlaughterscapeForceTriggerFromServer::toBytes)
                .consumer(FetchBlademasterSlaughterscapeForceTriggerFromServer::handle)
                .add();



        //=============================
        //server <---> client side
//        net.messageBuilder(SendQuickSlotSettingToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
//                .decoder(SendQuickSlotSettingToServer::new)
//                .encoder(SendQuickSlotSettingToServer::toBytes)
//                .consumer(SendQuickSlotSettingToServer::handle)
//                .add();
//
//        net.messageBuilder(FetchQuickSlotSettingFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(FetchQuickSlotSettingFromServer::new)
//                .encoder(FetchQuickSlotSettingFromServer::toBytes)
//                .consumer(FetchQuickSlotSettingFromServer::handle)
//                .add();

        net.messageBuilder(SendTiltBlockActionPacketToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SendTiltBlockActionPacketToServer::new)
                .encoder(SendTiltBlockActionPacketToServer::toBytes)
                .consumer(SendTiltBlockActionPacketToServer::handle)
                .add();

        net.messageBuilder(FetchTiltBlockActionPacketFromServer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FetchTiltBlockActionPacketFromServer::new)
                .encoder(FetchTiltBlockActionPacketFromServer::toBytes)
                .consumer(FetchTiltBlockActionPacketFromServer::handle)
                .add();


    }

    public static <MSG> void sendToServer(MSG message) {
         INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClient(MSG message, ServerPlayer player) {
        INSTANCE.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static <MSG> void sendToClients(PacketDistributor.PacketTarget target, MSG message) {
        INSTANCE.send(target, message);
    }

    //================================
    public static <MSG> void sendToClient(MSG message, PacketDistributor.PacketTarget packetTarget) {
        INSTANCE.send(packetTarget, message);
    }

    public static <MSG> void sendToAll(MSG message) {
        sendToClient(message, PacketDistributor.ALL.noArg());
    }

//    public static <MSG> void sendToAllPlayerWithChunk(MSG message, LevelChunk chunk) {
//        sendToClient(message, PacketDistributor.TRACKING_CHUNK.with(() -> chunk));
//    }
}
