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

public class FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer {
    public static Identifier ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_player_animation_packet_with_speed_for_all_players_from_server");

    public static PacketByteBuf setupPacket(int AnimatedEntityID, String AnimFileName, String AnimActionName, float AnimationSpeed) {
        PacketByteBuf packetToBeSent = DfoSwdNetworkingMsgInitialization.createPackage();
        packetToBeSent.writeInt(AnimatedEntityID);
        packetToBeSent.writeString(AnimFileName);
        packetToBeSent.writeString(AnimActionName);
        packetToBeSent.writeFloat(AnimationSpeed);
        return packetToBeSent;
    }

    public static void toClientHandle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //decode packet here
        final int AnimatedEntityID = buf.readInt();
        final String AnimFileName = buf.readString();
        final String AnimActionName = buf.readString();
        final float AnimationSpeed =  buf.readFloat();

        client.execute( () -> {
            //start action
            if (getClientLevel() == null) { return; }
            SBPlayerAnimatorMethods.SetPlayerAnimationWithSpeedModifierByEntityID(AnimatedEntityID, AnimFileName, AnimActionName, AnimationSpeed);
        });
    }
}
