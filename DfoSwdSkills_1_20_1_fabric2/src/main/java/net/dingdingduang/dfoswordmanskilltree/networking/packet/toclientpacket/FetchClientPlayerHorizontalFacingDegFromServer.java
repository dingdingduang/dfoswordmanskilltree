package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class FetchClientPlayerHorizontalFacingDegFromServer {
    public static Identifier ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_client_player_horizontal_facing_deg_from_server");

    public static PacketByteBuf setupPacket(float HorizontalFacingDeg) {
        PacketByteBuf packetToBeSent = DfoSwdNetworkingMsgInitialization.createPackage();
        packetToBeSent.writeFloat(HorizontalFacingDeg);
        return packetToBeSent;
    }

    public static void toClientHandle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //decode packet here
        final float HorizontalFacingDeg = buf.readFloat();

        client.execute( () -> {
            //start action
            Entity player = getClientPlayer();
            if (player == null) { return; }

//            Entity camEntity = getClientCameraEntity();
//            if (camEntity != null) {
//                Vector3f playerPos = getEntityPos(player);
//                Vector3f camPos = getEntityPos(camEntity);
//                float camHorizontalFacing = getEntityHorizontalFacingDeg(camEntity);
//                float angleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(camPos, playerPos);
//                float diff = MathMethods.RoundAngle(this.HorizontalFacingDeg) - MathMethods.RoundAngle(angleBtn);
//                EntityMethods.setEntityHorizontalFacingDeg(player, camHorizontalFacing + diff);
//            }
            EntityMethods.setEntityHorizontalFacingDeg(player, HorizontalFacingDeg);
        });
    }
}
