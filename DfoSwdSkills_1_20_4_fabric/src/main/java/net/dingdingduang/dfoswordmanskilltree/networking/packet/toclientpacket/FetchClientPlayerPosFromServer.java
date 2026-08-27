package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class FetchClientPlayerPosFromServer {
    public static Identifier ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_client_player_pos_from_server");

    public static PacketByteBuf setupPacket(float x1, float y1, float z1) {
        PacketByteBuf packetToBeSent = DfoSwdNetworkingMsgInitialization.createPackage();
        packetToBeSent.writeFloat(x1);
        packetToBeSent.writeFloat(y1);
        packetToBeSent.writeFloat(z1);
        return packetToBeSent;
    }

    public static void toClientHandle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //decode packet here
        final float x1 = buf.readFloat();
        final float y1 = buf.readFloat();
        final float z1 = buf.readFloat();

        client.execute( () -> {
            //start action
            if (getClientPlayer() == null) { return; }
//            moveEntityToXYZ(getClientPlayer(), x1, y1, z1);
            setEntityPosXYZ(getClientPlayer(), x1, y1, z1);
        });
    }
}
