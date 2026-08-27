package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer(int AnimatedEntityID, String AnimFileName, String AnimActionName, float AnimationSpeed) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_player_animation_packet_with_speed_for_all_players_from_server") );

    public static final StreamCodec<ByteBuf, FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::AnimatedEntityID,
            ByteBufCodecs.STRING_UTF8,
            FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::AnimFileName,
            ByteBufCodecs.STRING_UTF8,
            FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::AnimActionName,
            ByteBufCodecs.FLOAT,
            FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::AnimationSpeed,
            FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread

                    if (getClientLevel() == null) { return; }
                    SBPlayerAnimatorMethods.SetPlayerAnimationWithSpeedModifierByEntityID(data.AnimatedEntityID(), data.AnimFileName(), data.AnimActionName(), data.AnimationSpeed());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
