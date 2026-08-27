package net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;

import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record SendUpdateEntityPosPacketToServer(float vec3x, float vec3y, float vec3z, int entityID) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SendUpdateEntityPosPacketToServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "send_update_entity_pos_packet_to_server") );

    public static final StreamCodec<ByteBuf, SendUpdateEntityPosPacketToServer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            SendUpdateEntityPosPacketToServer::vec3x,
            ByteBufCodecs.FLOAT,
            SendUpdateEntityPosPacketToServer::vec3y,
            ByteBufCodecs.FLOAT,
            SendUpdateEntityPosPacketToServer::vec3z,
            ByteBufCodecs.INT,
            SendUpdateEntityPosPacketToServer::entityID,
            SendUpdateEntityPosPacketToServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SendUpdateEntityPosPacketToServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread
                    ServerPlayer sp1 = (ServerPlayer) context.player();

                    //do shiet on server side
                    Entity entity = EntityMethods.getEntityByIntID(EntityMethods.getEntityLevel(sp1), data.entityID());

                    if (entity != null) {
                        EntityMethods.moveEntityToXYZ(entity, data.vec3x(), data.vec3y(), data.vec3z());
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
