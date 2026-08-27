package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchClientPlayerPosFromServer(float x1, float y1, float z1) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchClientPlayerPosFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_client_player_pos_from_server") );

    public static final StreamCodec<ByteBuf, FetchClientPlayerPosFromServer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            FetchClientPlayerPosFromServer::x1,
            ByteBufCodecs.FLOAT,
            FetchClientPlayerPosFromServer::y1,
            ByteBufCodecs.FLOAT,
            FetchClientPlayerPosFromServer::z1,
            FetchClientPlayerPosFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchClientPlayerPosFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread

                    if (getClientPlayer() == null) { return; }
//            moveEntityToXYZ(getClientPlayer(), this.x1, this.y1, this.z1);
                    setEntityPosXYZ(getClientPlayer(), data.x1(), data.y1(), data.z1());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
