package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.codec.PacketCodecs;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchPlayerKeyboardKidnapFromServer(byte Type) implements CustomPayload {
    public static final CustomPayload.Id<FetchPlayerKeyboardKidnapFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_player_keyboard_kidnap_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchPlayerKeyboardKidnapFromServer> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.BYTE,
            FetchPlayerKeyboardKidnapFromServer::Type,
            FetchPlayerKeyboardKidnapFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchPlayerKeyboardKidnapFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
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
                });
    }
}
