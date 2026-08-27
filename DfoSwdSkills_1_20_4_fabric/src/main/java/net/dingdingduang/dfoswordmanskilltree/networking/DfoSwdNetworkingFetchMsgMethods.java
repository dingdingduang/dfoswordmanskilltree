package net.dingdingduang.dfoswordmanskilltree.networking;

import net.dingdingduang.dfoswordmanskilltree.entity.EntityExtraSpawnData;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.*;

import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.entityextradata.FetchEntityExtraDataFromServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill.FetchBlademasterSlaughterscapeForceTriggerFromServer;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.joml.Vector3f;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftServerPlayerList;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityPos;

public class DfoSwdNetworkingFetchMsgMethods {
    public static void FetchEntityExtraSpawnDataFromServer(Entity entity) {
        if (entity instanceof EntityExtraSpawnData) {
            DfoSwdNetworkingMsgInitialization.sendToAllPlayers(FetchEntityExtraDataFromServer.ID, FetchEntityExtraDataFromServer.setupPacket(entity));
        }
    }


    //init when login for client side
//    public static void FetchDfoSwordmanClientPlayerInitDataFromServer(ServerPlayerEntity sp1) {
//        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchDfoSwdClientPlayerInitFromServer.ID, FetchDfoSwdClientPlayerInitFromServer.setupPacket(), sp1);
//    }
    //clear when logout for clientSide
    public static void FetchDfoSwordmanClientPlayerClearDataFromServer(ServerPlayerEntity sp1) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchDfoSwdClientPlayerClearFromServer.ID, FetchDfoSwdClientPlayerClearFromServer.setupPacket(), sp1);
    }
    //do shiet after player respawn in client side
//    public static void FetchClientPlayerDataAfterRespawnFromServer(ServerPlayerEntity sp1) {
//        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchPlayerDataAfterRespawnFromServer.ID, FetchPlayerDataAfterRespawnFromServer.setupPacket(), sp1);
//    }
    //reset client keyboard, 0 movement, 1 hotbar, 2 or other byte val = all
    public static void FetchClientPlayerKeyboardFreeFromServer(ServerPlayerEntity sp1, byte type) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchPlayerKeyboardFreeFromServer.ID, FetchPlayerKeyboardFreeFromServer.setupPacket(type), sp1);
    }

    public static void FetchClientPlayerKeyboardKidnapFromServer(ServerPlayerEntity sp1, byte type) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchPlayerKeyboardKidnapFromServer.ID, FetchPlayerKeyboardKidnapFromServer.setupPacket(type), sp1);
    }

    public static void FetchClientPlayerHorizontalFacing(ServerPlayerEntity sp1, float horizontalFacing) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchClientPlayerHorizontalFacingDegFromServer.ID, FetchClientPlayerHorizontalFacingDegFromServer.setupPacket(horizontalFacing), sp1);
    }

    public static void FetchUpdateClientPlayerPos(ServerPlayerEntity sp1, float x1, float y1, float z1, boolean resetDelta) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchUpdateClientPlayerPosFromServer.ID, FetchUpdateClientPlayerPosFromServer.setupPacket(x1, y1, z1, resetDelta), sp1);
    }

    public static void FetchUpdateClientPlayerPos(ServerPlayerEntity sp1, Vector3f pos, boolean resetDelta) {
        FetchUpdateClientPlayerPos(sp1, pos.x(), pos.y(), pos.z(), resetDelta);
    }

    public static void FetchClientPlayerResetPacketRequestFromServer(ServerPlayerEntity sp1) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchClientPlayerResetPacketWhenRespawnFromServer.ID, FetchClientPlayerResetPacketWhenRespawnFromServer.setupPacket(), sp1);
    }

    //TODO check dimension then send packet cond
    //skill packet
    public static void FetchBlademaster037SlaughterscapeForceTriggerPacketFromServer(int ownerID, float centerX, float centerY, float centerZ) {
        Vector3f sp2Vec;
        for (ServerPlayerEntity sp2: getMinecraftServerPlayerList()) {
            sp2Vec = getEntityPos(sp2);
            float tempDist = Vector3fMethods.Vec3fDistanceTo(centerX, centerY, centerZ, sp2Vec.x(), sp2Vec.y(), sp2Vec.z());

            if (tempDist <= 32f) {
                DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchBlademasterSlaughterscapeForceTriggerFromServer.ID, FetchBlademasterSlaughterscapeForceTriggerFromServer.setupPacket(ownerID), sp2);
            }
        }
    }



    //======================
//    public static void FetchTiltBlockActionFromServer(float vecX, float vecY, float vecZ,
//                                                      float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks,
//                                                      boolean shouldResetBlockState, boolean isRandomizedLifeTime, boolean hasSound, boolean hasParticle,
//                                                      LevelChunk chunk) {
//        DfoSwdNetworkingMsgInitialization.sendToAllPlayerWithChunk(FetchTiltBlockActionPacketFromServer.ID, FetchTiltBlockActionPacketFromServer.setupPacket(vecX, vecY, vecZ,
//                radius, lifeTimeTicks, bouncingHeight, bouncingTotalTimeTicks,
//                shouldResetBlockState, isRandomizedLifeTime, hasSound, hasParticle), chunk);
//    }

    public static void FetchTiltBlockActionFromServer(float vecX, float vecY, float vecZ,
                                                      float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks,
                                                      boolean shouldResetBlockState, boolean isRandomizedLifeTime, boolean hasSound, boolean hasParticle) {
        Vector3f sp2Vec;
        for (ServerPlayerEntity sp2: getMinecraftServerPlayerList()) {
            sp2Vec = getEntityPos(sp2);
            float tempDist = Vector3fMethods.Vec3fDistanceTo(vecX, vecY, vecZ, sp2Vec.x(), sp2Vec.y(), sp2Vec.z());

            if (tempDist <= 32f) {
                DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchTiltBlockActionPacketFromServer.ID, FetchTiltBlockActionPacketFromServer.setupPacket(
                        vecX, vecY, vecZ,
                        radius, lifeTimeTicks, bouncingHeight, bouncingTotalTimeTicks,
                        shouldResetBlockState, isRandomizedLifeTime, hasSound, hasParticle), sp2);
            }
        }
    }

    //==============
    //player animation
    public static void FetchPlayerAnimationFromServer(ServerPlayerEntity sp1, String AnimationFileName, String AnimationActionName) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchPlayerAnimationPacketFromServer.ID, FetchPlayerAnimationPacketFromServer.setupPacket(AnimationFileName, AnimationActionName), sp1);
    }

    public static void FetchPlayerAnimationForAllPlayerFromServer(ServerPlayerEntity sp1, String AnimationFileName, String AnimationActionName, int EntityID) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchPlayerAnimationPacketForAllPlayersFromServer.ID, FetchPlayerAnimationPacketForAllPlayersFromServer.setupPacket(EntityID, AnimationFileName, AnimationActionName), sp1);
    }

    public static void FetchPlayerAnimationWithSpeedModifierForAllPlayerFromServer(ServerPlayerEntity sp1, String AnimationFileName, String AnimationActionName, int EntityID, float speed) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer.ID, FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer.setupPacket(EntityID, AnimationFileName, AnimationActionName, speed), sp1);
    }



    //==============
    //others
    public static void FetchPlayerDeltaFromServer(ServerPlayerEntity sp1, float x1, float y1, float z1, boolean isAdd) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchClientPlayerDeltaMovFromServer.ID, FetchClientPlayerDeltaMovFromServer.setupPacket(x1, y1, z1, isAdd), sp1);
    }

    public static void FetchPlayerDeltaFromServer(ServerPlayerEntity sp1, Vector3f vec3, boolean isAdd) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchClientPlayerDeltaMovFromServer.ID, FetchClientPlayerDeltaMovFromServer.setupPacket(vec3.x(), vec3.y(), vec3.z(), isAdd), sp1);
    }

    public static void FetchPlayerPosFromServer(ServerPlayerEntity sp1, float x1, float y1, float z1) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(FetchClientPlayerPosFromServer.ID, FetchClientPlayerPosFromServer.setupPacket(x1, y1, z1), sp1);
    }

    public static void FetchPlayerPosFromServer(ServerPlayerEntity sp1, Vector3f vec3) {
        FetchPlayerPosFromServer(sp1, vec3.x(), vec3.y(),vec3.z());
    }

//    public static void FetchPlayerHasGravityFromServer(ServerPlayerEntity sp1, boolean hasGravity) {
//        DfoSwordmanNetworkingMsgInitialization.sendToClient(FetchClientPlayerHasGravityFromServer.ID, FetchClientPlayerHasGravityFromServer.setupPacket(hasGravity), sp1);
//    }

    //TODO: emotecraft send animation
//    public static void FetchPlayerAnimationFromServerToAllPlayer(ServerPlayerEntity sp1, String AnimationFileName, String AnimationActionName, Vector3f targetLoc, float dist) {
//        DfoSwordmanNetworkingMsgInitialization.sendToPlayer(FetchPlayerAnimationPacketFromServer.ID, FetchPlayerAnimationPacketFromServer.setupPacket(AnimationFileName, AnimationActionName), sp1);
//    }
}
