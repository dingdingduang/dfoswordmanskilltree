package net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;

import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class SendUpdateEntityPosPacketToServer {
    public static Identifier ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "send_update_entity_pos_packet_to_server");

    public static PacketByteBuf setupPacket(float vecX, float vecY, float vecZ, int entityID) {
        PacketByteBuf packetToBeSent = DfoSwdNetworkingMsgInitialization.createPackage();
        packetToBeSent.writeFloat(vecX);
        packetToBeSent.writeFloat(vecY);
        packetToBeSent.writeFloat(vecZ);
        packetToBeSent.writeInt(entityID);
        return packetToBeSent;
    }

    public static void toServerHandle(MinecraftServer server, ServerPlayerEntity serverPlayer, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        final float vec3x = buf.readFloat();
        final float vec3y = buf.readFloat();
        final float vec3z = buf.readFloat();
        final int entityID = buf.readInt();

        server.execute( () -> {
            //start action
            Entity entity = EntityMethods.getEntityByIntID(EntityMethods.getEntityLevel(serverPlayer), entityID);

            if (entity != null) {
                EntityMethods.moveEntityToXYZ(entity, vec3x, vec3y, vec3z);
            }
        });
    }
}
