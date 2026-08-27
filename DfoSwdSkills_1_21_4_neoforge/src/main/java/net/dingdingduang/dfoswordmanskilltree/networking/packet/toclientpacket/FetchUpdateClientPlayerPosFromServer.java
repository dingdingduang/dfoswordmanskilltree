package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchUpdateClientPlayerPosFromServer(float x1, float y1, float z1, boolean resetDelta) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchUpdateClientPlayerPosFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_update_client_player_pos_from_server") );

    public static final StreamCodec<ByteBuf, FetchUpdateClientPlayerPosFromServer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            FetchUpdateClientPlayerPosFromServer::x1,
            ByteBufCodecs.FLOAT,
            FetchUpdateClientPlayerPosFromServer::y1,
            ByteBufCodecs.FLOAT,
            FetchUpdateClientPlayerPosFromServer::z1,
            ByteBufCodecs.BOOL,
            FetchUpdateClientPlayerPosFromServer::resetDelta,
            FetchUpdateClientPlayerPosFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchUpdateClientPlayerPosFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread

                    if (getClientPlayer() == null) { return; }
                    if (data.resetDelta()) {
                        setEntityDeltaMovement(getClientPlayer(), 0, 0, 0);
                    }
                    setEntityPosXYZ(getClientPlayer(), data.x1(), data.y1(), data.z1());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
