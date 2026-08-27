package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.somebasicskills.Constants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.codec.PacketCodecs;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods.SetPlayerAnimationClient;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchPlayerAnimationPacketFromServer(String AnimFileName, String AnimActionName) implements CustomPayload {
    public static final CustomPayload.Id<FetchPlayerAnimationPacketFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_player_animation_packet_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchPlayerAnimationPacketFromServer> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING,
            FetchPlayerAnimationPacketFromServer::AnimFileName,
            PacketCodecs.STRING,
            FetchPlayerAnimationPacketFromServer::AnimActionName,
            FetchPlayerAnimationPacketFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchPlayerAnimationPacketFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
                    //do on main thread

                    if (getClientPlayer() == null) { return; }
                    SetPlayerAnimationClient(getClientPlayer(), data.AnimFileName(), data.AnimActionName());
                });
    }
}
