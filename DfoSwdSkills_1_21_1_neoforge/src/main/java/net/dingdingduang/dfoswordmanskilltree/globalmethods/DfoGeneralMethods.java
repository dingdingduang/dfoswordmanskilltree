package net.dingdingduang.dfoswordmanskilltree.globalmethods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DfoGeneralMethods {
    private static ConcurrentHashMap<ServerPlayer, Byte> currentServerPlayerMap = new ConcurrentHashMap<>();

    public static Minecraft getMinecraftInstance() { return Minecraft.getInstance(); }
    public static Entity getClientCameraEntity() { return getMinecraftInstance().getCameraEntity(); }
//    public static float getClientCameraEntityHorizontalFacingAngle() {
//        Entity CamEntity = getClientCameraEntity();
//        if (CamEntity == null) {
//            if (getClientPlayer() != null) {
//                return EntityMethods.getEntityHorizontalFacingDeg(getClientPlayer());
//            }
//            else { return 0f; }
//        }
//
//        return (net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.isCamMirror()) ? EntityMethods.getEntityHorizontalFacingDeg(CamEntity) - 180f: EntityMethods.getEntityHorizontalFacingDeg(CamEntity);
//    }

    public static float getMinecraftInstancePartialTick(boolean getFreeze) { return getMinecraftInstance().getTimer().getGameTimeDeltaPartialTick(getFreeze); }

    public static Player getClientPlayer() { return getMinecraftInstance().player; }

    public static ServerLevel getServerLevel() { return getMinecraftServerInstance().getLevel(Level.OVERWORLD); }

    public static ClientLevel getClientLevel() { return getMinecraftInstance().level; }

    public static boolean isLevelClientSide(Level level) {
        return level.isClientSide();
    }

    public static boolean isClientSide() { return getMinecraftServerInstance() == null; }

    public static void printInGameMsg(String a) { getMinecraftInstance().gui.getChat().addMessage(Component.literal(a)); }

    //server methods
    public static MinecraftServer getMinecraftServerInstance() { return ServerLifecycleHooks.getCurrentServer(); }

    public static List<ServerPlayer> getMinecraftServerPlayerList() {
//        return getMinecraftServerInstance().getPlayerList().getPlayers();
        List<ServerPlayer> tempPlayerList = new ArrayList<ServerPlayer>();
        try {
            for (ServerPlayer tempSP : currentServerPlayerMap.keySet()) {
                tempPlayerList.add(tempSP);
            }
        }
        catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException: "+ e);
        }
        return tempPlayerList;
    }

    public static ConcurrentHashMap<ServerPlayer, Byte> getCurrentServerPlayerMap() { return currentServerPlayerMap; }
    public static void setCurrentServerPlayerMap(ConcurrentHashMap<ServerPlayer, Byte> CurrentServerPlayerMap) { currentServerPlayerMap = CurrentServerPlayerMap; }

//    public static ServerPlayer getMinecraftServerPlayerByUUID(long mostSigBits, long leastSigBits) {
//        return getMinecraftServerInstance().getPlayerList().getPlayer(new UUID(mostSigBits, leastSigBits));
//    }
}
