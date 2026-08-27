package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchClientPlayerHorizontalFacingDegFromServer(float HorizontalFacingDeg) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchClientPlayerHorizontalFacingDegFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_client_player_horizontal_facing_deg_from_server") );

    public static final StreamCodec<ByteBuf, FetchClientPlayerHorizontalFacingDegFromServer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            FetchClientPlayerHorizontalFacingDegFromServer::HorizontalFacingDeg,
            FetchClientPlayerHorizontalFacingDegFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchClientPlayerHorizontalFacingDegFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread

                    Entity player = getClientPlayer();
                    if (player == null) { return; }

                    EntityMethods.setEntityHorizontalFacingDeg(player, data.HorizontalFacingDeg());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
