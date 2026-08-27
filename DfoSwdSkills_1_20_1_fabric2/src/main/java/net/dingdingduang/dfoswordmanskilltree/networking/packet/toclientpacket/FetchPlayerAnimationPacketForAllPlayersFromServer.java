package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class FetchPlayerAnimationPacketForAllPlayersFromServer {
    public static Identifier ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_player_animation_packet_for_all_players_from_server");

    public static PacketByteBuf setupPacket(int SenderEntityID, String AnimFileName, String AnimActionName) {
        PacketByteBuf packetToBeSent = DfoSwdNetworkingMsgInitialization.createPackage();
        packetToBeSent.writeInt(SenderEntityID);
        packetToBeSent.writeString(AnimFileName);
        packetToBeSent.writeString(AnimActionName);
        return packetToBeSent;
    }

    public static void toClientHandle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //decode packet here
        final int SenderEntityID = buf.readInt();
        final String AnimFileName = buf.readString();
        final String AnimActionName = buf.readString();

        client.execute( () -> {
            //start action
            if (getClientLevel() == null) { return; }
            SBPlayerAnimatorMethods.SetPlayerAnimationByEntityID(SenderEntityID, AnimFileName, AnimActionName);
        });
    }
}
