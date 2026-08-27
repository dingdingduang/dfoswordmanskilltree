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

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchDfoSwdClientPlayerInitFromServer() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchDfoSwdClientPlayerInitFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_dfo_swd_client_player_init_from_server") );

    public static final StreamCodec<ByteBuf, FetchDfoSwdClientPlayerInitFromServer> STREAM_CODEC = StreamCodecMoreMethods.composite(
            FetchDfoSwdClientPlayerInitFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchDfoSwdClientPlayerInitFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread

                    ClientItemOverlayTimer.ClientItemOverlayTimerInit();
                    ParticleRegistry.ParticleRegistryInit();
                    GeoModelClientMethodsRegistry.GeoModelClientActionNameMapRegistryInit();
//            DfoSwdClientMethodsRegistry.ClientActionNameMapRegistryInit();
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
