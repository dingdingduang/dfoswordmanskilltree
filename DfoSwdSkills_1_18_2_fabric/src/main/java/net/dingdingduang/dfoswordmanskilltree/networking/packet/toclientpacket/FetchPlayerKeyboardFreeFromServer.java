package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class FetchPlayerKeyboardFreeFromServer {
    public static Identifier ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_player_keyboard_free_from_server");

    public static PacketByteBuf setupPacket(byte Type) {
        PacketByteBuf packetToBeSent = DfoSwdNetworkingMsgInitialization.createPackage();
        packetToBeSent.writeByte(Type);
        return packetToBeSent;
    }

    public static void toClientHandle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //decode packet here
        final byte Type = buf.readByte();

        client.execute( () -> {
            //start action
            switch (Type) {
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
