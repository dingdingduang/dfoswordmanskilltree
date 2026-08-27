package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FetchPlayerKeyboardFreeFromServer {
    private final byte Type;

    public FetchPlayerKeyboardFreeFromServer(byte type) {
        this.Type = type;
    }

    public FetchPlayerKeyboardFreeFromServer(FriendlyByteBuf buf) {
        this.Type = buf.readByte();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeByte(this.Type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
//            printInGameMsg("before free movement1: "+KeyboardMethods.getMovementKeyKidnapCounter());
//            printInGameMsg("before free hotbar2: "+KeyboardMethods.getHotBarKeyKidnapCounter());
            switch (this.Type) {
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

        return true;
    }
}
