package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.somebasicskills.networking.NetworkingGeneralMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBPlayerConfigFileInitHelper;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchClientPlayerResetPacketWhenRespawnFromServer() implements CustomPayload {
    public static final CustomPayload.Id<FetchClientPlayerResetPacketWhenRespawnFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_client_player_reset_packet_when_respawn_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchClientPlayerResetPacketWhenRespawnFromServer> STREAM_CODEC = NetworkingGeneralMethods.tuple(
            FetchClientPlayerResetPacketWhenRespawnFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchClientPlayerResetPacketWhenRespawnFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
                    //do on main thread

                    SBPlayerConfigFileInitHelper tempConfigStateHelper = new SBPlayerConfigFileInitHelper(null);
                    tempConfigStateHelper.SBPlayerUnstuckRequestOnClient();
                });
    }
}
