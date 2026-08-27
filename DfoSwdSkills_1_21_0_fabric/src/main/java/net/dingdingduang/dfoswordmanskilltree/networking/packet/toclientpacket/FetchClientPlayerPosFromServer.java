package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.somebasicskills.Constants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.codec.PacketCodecs;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchClientPlayerPosFromServer(float x1, float y1, float z1) implements CustomPayload {
    public static final CustomPayload.Id<FetchClientPlayerPosFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_client_player_pos_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchClientPlayerPosFromServer> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT,
            FetchClientPlayerPosFromServer::x1,
            PacketCodecs.FLOAT,
            FetchClientPlayerPosFromServer::y1,
            PacketCodecs.FLOAT,
            FetchClientPlayerPosFromServer::z1,
            FetchClientPlayerPosFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchClientPlayerPosFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
                    //do on main thread

                    if (getClientPlayer() == null) { return; }
//            moveEntityToXYZ(getClientPlayer(), this.x1, this.y1, this.z1);
                    setEntityPosXYZ(getClientPlayer(), data.x1(), data.y1(), data.z1());
                });
    }
}
