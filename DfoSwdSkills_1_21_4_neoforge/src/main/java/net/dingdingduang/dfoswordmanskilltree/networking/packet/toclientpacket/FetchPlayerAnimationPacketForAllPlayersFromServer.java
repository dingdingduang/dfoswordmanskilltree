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

public record FetchPlayerAnimationPacketForAllPlayersFromServer(int SenderEntityID, String AnimFileName, String AnimActionName) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchPlayerAnimationPacketForAllPlayersFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_player_animation_packet_for_all_players_from_server") );

    public static final StreamCodec<ByteBuf, FetchPlayerAnimationPacketForAllPlayersFromServer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            FetchPlayerAnimationPacketForAllPlayersFromServer::SenderEntityID,
            ByteBufCodecs.STRING_UTF8,
            FetchPlayerAnimationPacketForAllPlayersFromServer::AnimFileName,
            ByteBufCodecs.STRING_UTF8,
            FetchPlayerAnimationPacketForAllPlayersFromServer::AnimActionName,
            FetchPlayerAnimationPacketForAllPlayersFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchPlayerAnimationPacketForAllPlayersFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread
                    if (getClientLevel() == null) { return; }
                    SBPlayerAnimatorMethods.SetPlayerAnimationByEntityID(data.SenderEntityID(), data.AnimFileName(), data.AnimActionName());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
