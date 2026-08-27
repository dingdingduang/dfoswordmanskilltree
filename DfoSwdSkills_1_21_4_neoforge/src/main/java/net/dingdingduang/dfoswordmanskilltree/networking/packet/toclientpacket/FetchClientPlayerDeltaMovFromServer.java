package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityDeltaMovement;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.setEntityDeltaMovement;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchClientPlayerDeltaMovFromServer(float x1, float y1, float z1, boolean isAdd) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchClientPlayerDeltaMovFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_client_player_delta_mov_from_server") );

    public static final StreamCodec<ByteBuf, FetchClientPlayerDeltaMovFromServer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            FetchClientPlayerDeltaMovFromServer::x1,
            ByteBufCodecs.FLOAT,
            FetchClientPlayerDeltaMovFromServer::y1,
            ByteBufCodecs.FLOAT,
            FetchClientPlayerDeltaMovFromServer::z1,
            ByteBufCodecs.BOOL,
            FetchClientPlayerDeltaMovFromServer::isAdd,
            FetchClientPlayerDeltaMovFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchClientPlayerDeltaMovFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread

                    if (getClientPlayer() == null) { return; }
                    if (data.isAdd()) {
                        setEntityDeltaMovement(getClientPlayer(), getEntityDeltaMovement(getClientPlayer()).add(data.x1(), data.y1(), data.z1()));
                    }
                    else {
                        setEntityDeltaMovement(getClientPlayer(), data.x1(), data.y1(), data.z1());
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
