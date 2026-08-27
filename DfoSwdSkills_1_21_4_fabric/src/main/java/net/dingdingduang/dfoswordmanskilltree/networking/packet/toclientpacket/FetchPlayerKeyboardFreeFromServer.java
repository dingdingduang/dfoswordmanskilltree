package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.codec.PacketCodecs;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchPlayerKeyboardFreeFromServer(byte Type) implements CustomPayload {
    public static final CustomPayload.Id<FetchPlayerKeyboardFreeFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_player_keyboard_free_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchPlayerKeyboardFreeFromServer> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.BYTE,
            FetchPlayerKeyboardFreeFromServer::Type,
            FetchPlayerKeyboardFreeFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchPlayerKeyboardFreeFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
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
                });
    }
}
