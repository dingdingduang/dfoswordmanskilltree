package net.dingdingduang.dfoswordmanskilltree.networking;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.*;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill.FetchBlademasterSlaughterscapeForceTriggerFromServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendTiltBlockActionPacketToServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendUpdateEntityPosPacketToServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DfoSwdNetworkingEventRegister {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar myRegistrar = event.registrar(DfoSwordmanSkillTreeConstants.MOD_ID);

        registerToServerPacketConfiguration(myRegistrar);
        registerToClientPacketConfiguration(myRegistrar);
    }

    private static void registerToServerPacketConfiguration(PayloadRegistrar payloadRegistrar) {
        payloadRegistrar.playToServer(SendTiltBlockActionPacketToServer.TYPE, SendTiltBlockActionPacketToServer.STREAM_CODEC, SendTiltBlockActionPacketToServer::handle);
        payloadRegistrar.playToServer(SendUpdateEntityPosPacketToServer.TYPE, SendUpdateEntityPosPacketToServer.STREAM_CODEC, SendUpdateEntityPosPacketToServer::handle);
    }

    private static void registerToClientPacketConfiguration(PayloadRegistrar payloadRegistrar) {
        payloadRegistrar.playToClient(FetchBlademasterSlaughterscapeForceTriggerFromServer.TYPE, FetchBlademasterSlaughterscapeForceTriggerFromServer.STREAM_CODEC, FetchBlademasterSlaughterscapeForceTriggerFromServer::handle);
        payloadRegistrar.playToClient(FetchClientPlayerDeltaMovFromServer.TYPE, FetchClientPlayerDeltaMovFromServer.STREAM_CODEC, FetchClientPlayerDeltaMovFromServer::handle);
        payloadRegistrar.playToClient(FetchClientPlayerHorizontalFacingDegFromServer.TYPE, FetchClientPlayerHorizontalFacingDegFromServer.STREAM_CODEC, FetchClientPlayerHorizontalFacingDegFromServer::handle);
        payloadRegistrar.playToClient(FetchClientPlayerPosFromServer.TYPE, FetchClientPlayerPosFromServer.STREAM_CODEC, FetchClientPlayerPosFromServer::handle);
        payloadRegistrar.playToClient(FetchClientPlayerResetPacketWhenRespawnFromServer.TYPE, FetchClientPlayerResetPacketWhenRespawnFromServer.STREAM_CODEC, FetchClientPlayerResetPacketWhenRespawnFromServer::handle); // packet without specific data
        payloadRegistrar.playToClient(FetchDfoSwdClientPlayerClearFromServer.TYPE, FetchDfoSwdClientPlayerClearFromServer.STREAM_CODEC, FetchDfoSwdClientPlayerClearFromServer::handle); // packet without specific data
        payloadRegistrar.playToClient(FetchDfoSwdClientPlayerInitFromServer.TYPE, FetchDfoSwdClientPlayerInitFromServer.STREAM_CODEC, FetchDfoSwdClientPlayerInitFromServer::handle); // packet without specific data
        payloadRegistrar.playToClient(FetchPlayerAnimationPacketForAllPlayersFromServer.TYPE, FetchPlayerAnimationPacketForAllPlayersFromServer.STREAM_CODEC, FetchPlayerAnimationPacketForAllPlayersFromServer::handle);
        payloadRegistrar.playToClient(FetchPlayerAnimationPacketFromServer.TYPE, FetchPlayerAnimationPacketFromServer.STREAM_CODEC, FetchPlayerAnimationPacketFromServer::handle);
        payloadRegistrar.playToClient(FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer.TYPE, FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer.STREAM_CODEC, FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::handle);
        payloadRegistrar.playToClient(FetchPlayerKeyboardFreeFromServer.TYPE, FetchPlayerKeyboardFreeFromServer.STREAM_CODEC, FetchPlayerKeyboardFreeFromServer::handle);
        payloadRegistrar.playToClient(FetchPlayerKeyboardKidnapFromServer.TYPE, FetchPlayerKeyboardKidnapFromServer.STREAM_CODEC, FetchPlayerKeyboardKidnapFromServer::handle);
        payloadRegistrar.playToClient(FetchTiltBlockActionPacketFromServer.TYPE, FetchTiltBlockActionPacketFromServer.STREAM_CODEC, FetchTiltBlockActionPacketFromServer::handle);
        payloadRegistrar.playToClient(FetchUpdateClientPlayerPosFromServer.TYPE, FetchUpdateClientPlayerPosFromServer.STREAM_CODEC, FetchUpdateClientPlayerPosFromServer::handle);
    }
}
