package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.somebasicskills.networking.NetworkingGeneralMethods;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer;
import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchDfoSwdClientPlayerInitFromServer() implements CustomPayload {
    public static final CustomPayload.Id<FetchDfoSwdClientPlayerInitFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_dfo_swd_client_player_init_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchDfoSwdClientPlayerInitFromServer> STREAM_CODEC = NetworkingGeneralMethods.tuple(
            FetchDfoSwdClientPlayerInitFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchDfoSwdClientPlayerInitFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
                    //do on main thread

                    ClientItemOverlayTimer.ClientItemOverlayTimerInit();
                    ParticleRegistry.ParticleRegistryInit();
                    GeoModelClientMethodsRegistry.GeoModelClientActionNameMapRegistryInit();
//            DfoSwdClientMethodsRegistry.ClientActionNameMapRegistryInit();
                });
    }
}
