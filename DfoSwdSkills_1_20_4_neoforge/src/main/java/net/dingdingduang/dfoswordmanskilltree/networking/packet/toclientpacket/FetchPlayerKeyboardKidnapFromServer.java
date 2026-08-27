package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchPlayerKeyboardKidnapFromServer(byte Type) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_player_keyboard_kidnap_from_server");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public FetchPlayerKeyboardKidnapFromServer(FriendlyByteBuf buf) {
        this(buf.readByte());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeByte(this.Type);
    }

    public static void handle(final FetchPlayerKeyboardKidnapFromServer data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
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
                    context.packetHandler().disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
