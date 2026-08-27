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
import net.minecraft.network.codec.PacketCodecs;

import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getTemporaryBlockStateMap;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchDfoSwdClientPlayerClearFromServer() implements CustomPayload {
    public static final CustomPayload.Id<FetchDfoSwdClientPlayerClearFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_dfo_swd_client_player_clear_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchDfoSwdClientPlayerClearFromServer> STREAM_CODEC = NetworkingGeneralMethods.tuple(
            FetchDfoSwdClientPlayerClearFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchDfoSwdClientPlayerClearFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
                    //do on main thread

                    ClientItemOverlayTimer.getClientItemOverlayTimerOverlay().setActive(false);
                    getTemporaryBlockStateMap().clear();
                    ParticleRegistry.ParticleMapClear();
                    GeoModelClientMethodsRegistry.GeoModelClientActionNameMapClear();
//            DfoSwdClientMethodsRegistry.ClientActionNameMapClear();
                });
    }
}
