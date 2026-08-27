package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods.SetPlayerAnimationClient;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchPlayerAnimationPacketFromServer(String AnimFileName, String AnimActionName) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchPlayerAnimationPacketFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_player_animation_packet_from_server") );

    public static final StreamCodec<ByteBuf, FetchPlayerAnimationPacketFromServer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            FetchPlayerAnimationPacketFromServer::AnimFileName,
            ByteBufCodecs.STRING_UTF8,
            FetchPlayerAnimationPacketFromServer::AnimActionName,
            FetchPlayerAnimationPacketFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchPlayerAnimationPacketFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread

                    if (getClientPlayer() == null) { return; }
                    SetPlayerAnimationClient(getClientPlayer(), data.AnimFileName(), data.AnimActionName());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
