package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.dingdingduang.somebasicskills.event.SBPlayerConfigFileInitHelper;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class FetchClientPlayerResetPacketWhenRespawnFromServer {
    public static Identifier ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_client_player_reset_packet_when_respawn_from_server");

    public static PacketByteBuf setupPacket() {
        return DfoSwdNetworkingMsgInitialization.createPackage();
    }

    public static void toClientHandle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //decode packet here

        client.execute( () -> {
            //start action
            SBPlayerConfigFileInitHelper tempConfigStateHelper = new SBPlayerConfigFileInitHelper(null);
            tempConfigStateHelper.SBPlayerUnstuckRequestOnClient();
        });
    }
}
