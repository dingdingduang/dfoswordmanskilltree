package net.dingdingduang.dfoswordmanskilltree.networking;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.*;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill.FetchBlademasterSlaughterscapeForceTriggerFromServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendTiltBlockActionPacketToServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendUpdateEntityPosPacketToServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

@Mod.EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DfoSwdNetworkingEventRegister {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar myRegistrar = event.registrar(DfoSwordmanSkillTreeConstants.MOD_ID);

        registerToServerPacketConfiguration(myRegistrar);
        registerToClientPacketConfiguration(myRegistrar);
    }

    private static void registerToServerPacketConfiguration(IPayloadRegistrar payloadRegistrar) {
        payloadRegistrar.play(SendTiltBlockActionPacketToServer.ID, SendTiltBlockActionPacketToServer::new, handler -> handler.server(SendTiltBlockActionPacketToServer::handle));
        payloadRegistrar.play(SendUpdateEntityPosPacketToServer.ID, SendUpdateEntityPosPacketToServer::new, handler -> handler.server(SendUpdateEntityPosPacketToServer::handle));
    }

    private static void registerToClientPacketConfiguration(IPayloadRegistrar payloadRegistrar) {
        payloadRegistrar.play(FetchBlademasterSlaughterscapeForceTriggerFromServer.ID, FetchBlademasterSlaughterscapeForceTriggerFromServer::new, handler -> handler.client(FetchBlademasterSlaughterscapeForceTriggerFromServer::handle));
        payloadRegistrar.play(FetchClientPlayerDeltaMovFromServer.ID, FetchClientPlayerDeltaMovFromServer::new, handler -> handler.client(FetchClientPlayerDeltaMovFromServer::handle));
        payloadRegistrar.play(FetchClientPlayerHorizontalFacingDegFromServer.ID, FetchClientPlayerHorizontalFacingDegFromServer::new, handler -> handler.client(FetchClientPlayerHorizontalFacingDegFromServer::handle));
        payloadRegistrar.play(FetchClientPlayerPosFromServer.ID, FetchClientPlayerPosFromServer::new, handler -> handler.client(FetchClientPlayerPosFromServer::handle));
        payloadRegistrar.play(FetchClientPlayerResetPacketWhenRespawnFromServer.ID, FetchClientPlayerResetPacketWhenRespawnFromServer::new, handler -> handler.client(FetchClientPlayerResetPacketWhenRespawnFromServer::handle));
        payloadRegistrar.play(FetchDfoSwdClientPlayerClearFromServer.ID, FetchDfoSwdClientPlayerClearFromServer::new, handler -> handler.client(FetchDfoSwdClientPlayerClearFromServer::handle));
        payloadRegistrar.play(FetchDfoSwdClientPlayerInitFromServer.ID, FetchDfoSwdClientPlayerInitFromServer::new, handler -> handler.client(FetchDfoSwdClientPlayerInitFromServer::handle));
        payloadRegistrar.play(FetchPlayerAnimationPacketForAllPlayersFromServer.ID, FetchPlayerAnimationPacketForAllPlayersFromServer::new, handler -> handler.client(FetchPlayerAnimationPacketForAllPlayersFromServer::handle));
        payloadRegistrar.play(FetchPlayerAnimationPacketFromServer.ID, FetchPlayerAnimationPacketFromServer::new, handler -> handler.client(FetchPlayerAnimationPacketFromServer::handle));
        payloadRegistrar.play(FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer.ID, FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::new, handler -> handler.client(FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::handle));
        payloadRegistrar.play(FetchPlayerKeyboardFreeFromServer.ID, FetchPlayerKeyboardFreeFromServer::new, handler -> handler.client(FetchPlayerKeyboardFreeFromServer::handle));
        payloadRegistrar.play(FetchPlayerKeyboardKidnapFromServer.ID, FetchPlayerKeyboardKidnapFromServer::new, handler -> handler.client(FetchPlayerKeyboardKidnapFromServer::handle));
        payloadRegistrar.play(FetchTiltBlockActionPacketFromServer.ID, FetchTiltBlockActionPacketFromServer::new, handler -> handler.client(FetchTiltBlockActionPacketFromServer::handle));
        payloadRegistrar.play(FetchUpdateClientPlayerPosFromServer.ID, FetchUpdateClientPlayerPosFromServer::new, handler -> handler.client(FetchUpdateClientPlayerPosFromServer::handle));
    }
}
