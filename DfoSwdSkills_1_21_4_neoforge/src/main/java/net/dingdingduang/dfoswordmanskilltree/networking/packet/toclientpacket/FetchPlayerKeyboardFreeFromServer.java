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

public record FetchPlayerKeyboardFreeFromServer(byte Type) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchPlayerKeyboardFreeFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_player_keyboard_free_from_server") );

    public static final StreamCodec<ByteBuf, FetchPlayerKeyboardFreeFromServer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE,
            FetchPlayerKeyboardFreeFromServer::Type,
            FetchPlayerKeyboardFreeFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchPlayerKeyboardFreeFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread

                    //do shiet on client side
//            printInGameMsg("before free movement1: "+KeyboardMethods.getMovementKeyKidnapCounter());
//            printInGameMsg("before free hotbar2: "+KeyboardMethods.getHotBarKeyKidnapCounter());
                    switch (data.Type()) {
                        case 0:
                            KeyboardMethods.FreeClientPlayerMovementKeybindings();
                            break;
                        case 1:
                            KeyboardMethods.FreeClientPlayerHotBarKeybindings();
                            break;
                        default:
                            KeyboardMethods.FreeClientPlayerMovementKeybindings();
                            KeyboardMethods.FreeClientPlayerHotBarKeybindings();
                            break;
                    }
//            printInGameMsg("after free movement1: "+KeyboardMethods.getMovementKeyKidnapCounter());
//            printInGameMsg("after free hotbar2: "+KeyboardMethods.getHotBarKeyKidnapCounter());

//            if (this.Type) {
//                KeyboardMethods.FreeClientPlayerMovementKeybindings();
//            }
//            else {
//                KeyboardMethods.FreeClientPlayerHotBarKeybindings();
//            }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
