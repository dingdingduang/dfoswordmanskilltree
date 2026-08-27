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

public record FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer(int AnimatedEntityID, String AnimFileName, String AnimActionName, float AnimationSpeed) implements CustomPayload {
    public static final CustomPayload.Id<FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_player_animation_packet_with_speed_for_all_players_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::AnimatedEntityID,
            PacketCodecs.STRING,
            FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::AnimFileName,
            PacketCodecs.STRING,
            FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::AnimActionName,
            PacketCodecs.FLOAT,
            FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::AnimationSpeed,
            FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
                    //do on main thread

                    if (getClientLevel() == null) { return; }
                    SBPlayerAnimatorMethods.SetPlayerAnimationWithSpeedModifierByEntityID(data.AnimatedEntityID(), data.AnimFileName(), data.AnimActionName(), data.AnimationSpeed());
                });
    }
}
