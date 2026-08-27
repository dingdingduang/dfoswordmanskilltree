package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.entity.Entity;
import net.minecraft.network.codec.PacketCodecs;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchClientPlayerHorizontalFacingDegFromServer(float HorizontalFacingDeg) implements CustomPayload {
    public static final CustomPayload.Id<FetchClientPlayerHorizontalFacingDegFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_client_player_horizontal_facing_deg_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchClientPlayerHorizontalFacingDegFromServer> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT,
            FetchClientPlayerHorizontalFacingDegFromServer::HorizontalFacingDeg,
            FetchClientPlayerHorizontalFacingDegFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchClientPlayerHorizontalFacingDegFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
                    //do on main thread

                    Entity player = getClientPlayer();
                    if (player == null) { return; }

                    EntityMethods.setEntityHorizontalFacingDeg(player, data.HorizontalFacingDeg());
                });
    }
}
