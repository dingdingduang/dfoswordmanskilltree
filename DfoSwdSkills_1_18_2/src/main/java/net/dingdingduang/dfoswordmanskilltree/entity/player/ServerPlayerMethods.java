package net.dingdingduang.dfoswordmanskilltree.entity.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftServerInstance;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityUUID;

public class ServerPlayerMethods {
    public static UUID getPlayerUUID(Player a) {
        return a.getUUID();
    }

    public static ServerPlayer ConvertClientPlayerToServerPlayer(Player a) {
        return getMinecraftServerInstance().getPlayerList().getPlayer(getEntityUUID(a));
    }

//    public static int getSPCurrentSelectedItemHotBarIndex(ServerPlayer sp1) { return sp1.getInventory().selected; }
//    public static int getSPCurrentWeaponTypeID(ServerPlayer sp1) {
//
//    }
}
