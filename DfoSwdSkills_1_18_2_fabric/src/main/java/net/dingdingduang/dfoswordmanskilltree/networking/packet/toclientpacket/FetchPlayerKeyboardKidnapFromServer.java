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

public class FetchPlayerKeyboardKidnapFromServer {
    public static Identifier ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_player_keyboard_kidnap_from_server");

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
