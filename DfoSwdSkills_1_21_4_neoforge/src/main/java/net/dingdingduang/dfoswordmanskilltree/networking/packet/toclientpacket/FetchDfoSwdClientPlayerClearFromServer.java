package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.somebasicskills.networking.StreamCodecMoreMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer;
import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getTemporaryBlockStateMap;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchDfoSwdClientPlayerClearFromServer() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchDfoSwdClientPlayerClearFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_dfo_swd_client_player_clear_from_server") );

    public static final StreamCodec<ByteBuf, FetchDfoSwdClientPlayerClearFromServer> STREAM_CODEC = StreamCodecMoreMethods.composite(
            FetchDfoSwdClientPlayerClearFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchDfoSwdClientPlayerClearFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread

                    ClientItemOverlayTimer.getClientItemOverlayTimerOverlay().setActive(false);
                    getTemporaryBlockStateMap().clear();
                    ParticleRegistry.ParticleMapClear();
                    GeoModelClientMethodsRegistry.GeoModelClientActionNameMapClear();
//            DfoSwdClientMethodsRegistry.ClientActionNameMapClear();
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
