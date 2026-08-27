package net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket;

import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;

import net.dingdingduang.somebasicskills.Constants;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.codec.PacketCodecs;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record SendUpdateEntityPosPacketToServer(float vec3x, float vec3y, float vec3z, int entityID) implements CustomPayload {
    public static final CustomPayload.Id<SendUpdateEntityPosPacketToServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "send_update_entity_pos_packet_to_server") );

    public static final PacketCodec<RegistryByteBuf, SendUpdateEntityPosPacketToServer> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT,
            SendUpdateEntityPosPacketToServer::vec3x,
            PacketCodecs.FLOAT,
            SendUpdateEntityPosPacketToServer::vec3y,
            PacketCodecs.FLOAT,
            SendUpdateEntityPosPacketToServer::vec3z,
            PacketCodecs.INTEGER,
            SendUpdateEntityPosPacketToServer::entityID,
            SendUpdateEntityPosPacketToServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final SendUpdateEntityPosPacketToServer data, final ServerPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.server().execute(() -> {
                    //do on main thread
                    ServerPlayerEntity sp1 = context.player();

                    //do shiet on server side
                    Entity entity = EntityMethods.getEntityByIntID(EntityMethods.getEntityLevel(sp1), data.entityID());

                    if (entity != null) {
                        EntityMethods.moveEntityToXYZ(entity, data.vec3x(), data.vec3y(), data.vec3z());
                    }
                });
    }
}
