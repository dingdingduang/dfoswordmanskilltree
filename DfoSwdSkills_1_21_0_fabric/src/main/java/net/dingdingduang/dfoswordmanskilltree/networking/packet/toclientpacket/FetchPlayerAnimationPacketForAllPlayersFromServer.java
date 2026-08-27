package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.codec.PacketCodecs;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchPlayerAnimationPacketForAllPlayersFromServer(int SenderEntityID, String AnimFileName, String AnimActionName) implements CustomPayload {
    public static final CustomPayload.Id<FetchPlayerAnimationPacketForAllPlayersFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_player_animation_packet_for_all_players_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchPlayerAnimationPacketForAllPlayersFromServer> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            FetchPlayerAnimationPacketForAllPlayersFromServer::SenderEntityID,
            PacketCodecs.STRING,
            FetchPlayerAnimationPacketForAllPlayersFromServer::AnimFileName,
            PacketCodecs.STRING,
            FetchPlayerAnimationPacketForAllPlayersFromServer::AnimActionName,
            FetchPlayerAnimationPacketForAllPlayersFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchPlayerAnimationPacketForAllPlayersFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
                    //do on main thread
                    if (getClientLevel() == null) { return; }
                    SBPlayerAnimatorMethods.SetPlayerAnimationByEntityID(data.SenderEntityID(), data.AnimFileName(), data.AnimActionName());
                });
    }
}
