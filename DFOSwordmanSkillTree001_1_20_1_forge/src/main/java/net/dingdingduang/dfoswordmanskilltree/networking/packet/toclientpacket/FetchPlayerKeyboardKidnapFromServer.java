package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FetchPlayerKeyboardKidnapFromServer {
    private final byte Type;

    public FetchPlayerKeyboardKidnapFromServer(byte type) {
        this.Type = type;
    }

    public FetchPlayerKeyboardKidnapFromServer(FriendlyByteBuf buf) {
        this.Type = buf.readByte();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeByte(this.Type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
            switch (this.Type) {
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

        return true;
    }
}
