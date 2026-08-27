package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchPlayerKeyboardKidnapFromServer(byte Type) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchPlayerKeyboardKidnapFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_player_keyboard_kidnap_from_server") );

    public static final StreamCodec<ByteBuf, FetchPlayerKeyboardKidnapFromServer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE,
            FetchPlayerKeyboardKidnapFromServer::Type,
            FetchPlayerKeyboardKidnapFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchPlayerKeyboardKidnapFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread

                    switch (data.Type()) {
                        case 0:
                            KeyboardMethods.KidnapClientPlayerMovementKeybindings();
                            break;
                        case 1:
                            KeyboardMethods.KidnapClientPlayerHotBarKeybindings();
                            break;
                        default:
//                    printInGameMsg("before kidnap all1: "+KeyboardMethods.getMovementKeyKidnapCounter());
//                    printInGameMsg("before kidnap all2: "+KeyboardMethods.getHotBarKeyKidnapCounter());
                            KeyboardMethods.KidnapClientPlayerMovementKeybindings();
                            KeyboardMethods.KidnapClientPlayerHotBarKeybindings();
//                    printInGameMsg("after kidnap all1: "+KeyboardMethods.getMovementKeyKidnapCounter());
//                    printInGameMsg("after kidnap all2: "+KeyboardMethods.getHotBarKeyKidnapCounter());
                            break;
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
