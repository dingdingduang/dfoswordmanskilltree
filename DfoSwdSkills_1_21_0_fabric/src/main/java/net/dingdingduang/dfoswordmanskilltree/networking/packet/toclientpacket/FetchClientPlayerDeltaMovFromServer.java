package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.somebasicskills.Constants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.codec.PacketCodecs;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityDeltaMovement;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.setEntityDeltaMovement;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchClientPlayerDeltaMovFromServer(float x1, float y1, float z1, boolean isAdd) implements CustomPayload {
    public static final CustomPayload.Id<FetchClientPlayerDeltaMovFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_client_player_delta_mov_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchClientPlayerDeltaMovFromServer> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT,
            FetchClientPlayerDeltaMovFromServer::x1,
            PacketCodecs.FLOAT,
            FetchClientPlayerDeltaMovFromServer::y1,
            PacketCodecs.FLOAT,
            FetchClientPlayerDeltaMovFromServer::z1,
            PacketCodecs.BOOL,
            FetchClientPlayerDeltaMovFromServer::isAdd,
            FetchClientPlayerDeltaMovFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchClientPlayerDeltaMovFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
                    //do on main thread

                    if (getClientPlayer() == null) { return; }
                    if (data.isAdd()) {
                        setEntityDeltaMovement(getClientPlayer(), getEntityDeltaMovement(getClientPlayer()).add(data.x1(), data.y1(), data.z1()));
                    }
                    else {
                        setEntityDeltaMovement(getClientPlayer(), data.x1(), data.y1(), data.z1());
                    }
                });
    }
}
