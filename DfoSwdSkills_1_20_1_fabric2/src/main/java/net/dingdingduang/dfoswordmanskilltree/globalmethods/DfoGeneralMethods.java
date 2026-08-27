package net.dingdingduang.dfoswordmanskilltree.globalmethods;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DfoGeneralMethods {
    private static ConcurrentHashMap<ServerPlayerEntity, Byte> currentServerPlayerMap = new ConcurrentHashMap<>();

    public static MinecraftClient getMinecraftInstance() { return MinecraftClient.getInstance(); }
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

    public static PlayerEntity getClientPlayer() { return getMinecraftInstance().player; }

    public static ClientWorld getClientLevel() { return getMinecraftInstance().world; }

    public static boolean isLevelClientSide(World level) { return level.isClient(); }

//    public static boolean isClientSide() { return getMinecraftServerInstance() == null; }
    public static boolean isClientSide() { return false; }

    public static void printInGameMsg(String a) {
        net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.printInGameMsg(a);
    }

    //server methods
    public static MinecraftServer getMinecraftServerInstance(Entity a) { return a.getServer(); }

    public static List<ServerPlayerEntity> getMinecraftServerPlayerList() {
//        return getMinecraftServerInstance().getPlayerList().getPlayers();
        List<ServerPlayerEntity> tempPlayerList = new ArrayList<ServerPlayerEntity>();
        try {
            for (ServerPlayerEntity tempSP : currentServerPlayerMap.keySet()) {
                tempPlayerList.add(tempSP);
            }
        }
        catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException: "+ e);
        }
        return tempPlayerList;
    }

    public static ConcurrentHashMap<ServerPlayerEntity, Byte> getCurrentServerPlayerMap() { return currentServerPlayerMap; }
    public static void setCurrentServerPlayerMap(ConcurrentHashMap<ServerPlayerEntity, Byte> CurrentServerPlayerMap) { currentServerPlayerMap = CurrentServerPlayerMap; }

//    public static ServerPlayerEntity getMinecraftServerPlayerByUUID(long mostSigBits, long leastSigBits) {
//        return getMinecraftServerInstance().getPlayerList().getPlayer(new UUID(mostSigBits, leastSigBits));
//    }
}
