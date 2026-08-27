package net.dingdingduang.dfoswordmanskilltree.networking;

import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.*;

import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill.FetchBlademasterSlaughterscapeForceTriggerFromServer;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;

import net.minecraft.server.level.ServerPlayer;

import org.joml.Vector3f;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftServerPlayerList;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityPos;

public class DfoSwdNetworkingFetchMsgMethods {
    //init when login for client side
    public static void FetchDfoSwordmanClientPlayerInitDataFromServer(ServerPlayer sp1) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchDfoSwdClientPlayerInitFromServer(), sp1);
    }
    //clear when logout for clientSide
    public static void FetchDfoSwordmanClientPlayerClearDataFromServer(ServerPlayer sp1) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchDfoSwdClientPlayerClearFromServer(), sp1);
    }
    //do shiet after player respawn in client side
//    public static void FetchClientPlayerDataAfterRespawnFromServer(ServerPlayer sp1) {
//        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchPlayerDataAfterRespawnFromServer(), sp1);
//    }
    //reset client keyboard, 0 movement, 1 hotbar, 2 or other byte val = all
    public static void FetchClientPlayerKeyboardFreeFromServer(ServerPlayer sp1, byte type) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchPlayerKeyboardFreeFromServer(type), sp1);
    }

    public static void FetchClientPlayerKeyboardKidnapFromServer(ServerPlayer sp1, byte type) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchPlayerKeyboardKidnapFromServer(type), sp1);
    }

    public static void FetchClientPlayerHorizontalFacing(ServerPlayer sp1, float horizontalFacing) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchClientPlayerHorizontalFacingDegFromServer(horizontalFacing), sp1);
    }

    public static void FetchUpdateClientPlayerPos(ServerPlayer sp1, float x1, float y1, float z1, boolean resetDelta) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchUpdateClientPlayerPosFromServer(x1, y1, z1, resetDelta), sp1);
    }

    public static void FetchUpdateClientPlayerPos(ServerPlayer sp1, Vector3f pos, boolean resetDelta) {
        FetchUpdateClientPlayerPos(sp1, pos.x(), pos.y(), pos.z(), resetDelta);
    }

    public static void FetchClientPlayerResetPacketRequestFromServer(ServerPlayer sp1) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchClientPlayerResetPacketWhenRespawnFromServer(), sp1);
    }

    //TODO check dimension then send packet cond
    //skill packet
    public static void FetchBlademaster037SlaughterscapeForceTriggerPacketFromServer(int ownerID, float centerX, float centerY, float centerZ) {
        Vector3f sp2Vec;
        for (ServerPlayer sp2: getMinecraftServerPlayerList()) {
            sp2Vec = getEntityPos(sp2);
            float tempDist = Vector3fMethods.Vec3fDistanceTo(centerX, centerY, centerZ, sp2Vec.x(), sp2Vec.y(), sp2Vec.z());

            if (tempDist <= 32f) {
                DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchBlademasterSlaughterscapeForceTriggerFromServer(ownerID), sp2);
            }
        }
    }



    //======================
//    public static void FetchTiltBlockActionFromServer(float vecX, float vecY, float vecZ,
//                                                      float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks,
//                                                      boolean shouldResetBlockState, boolean isRandomizedLifeTime, boolean hasSound, boolean hasParticle,
//                                                      LevelChunk chunk) {
//        DfoSwdNetworkingMsgInitialization.sendToAllPlayerWithChunk(new FetchTiltBlockActionPacketFromServer(vecX, vecY, vecZ,
//                radius, lifeTimeTicks, bouncingHeight, bouncingTotalTimeTicks,
//                shouldResetBlockState, isRandomizedLifeTime, hasSound, hasParticle), chunk);
//    }

    public static void FetchTiltBlockActionFromServer(float vecX, float vecY, float vecZ,
                                                      float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks,
                                                      boolean shouldResetBlockState, boolean isRandomizedLifeTime, boolean hasSound, boolean hasParticle) {
        Vector3f sp2Vec;
        for (ServerPlayer sp2: getMinecraftServerPlayerList()) {
            sp2Vec = getEntityPos(sp2);
            float tempDist = Vector3fMethods.Vec3fDistanceTo(vecX, vecY, vecZ, sp2Vec.x(), sp2Vec.y(), sp2Vec.z());

            if (tempDist <= 32f) {
                DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchTiltBlockActionPacketFromServer(vecX, vecY, vecZ,
                        radius, lifeTimeTicks, bouncingHeight, bouncingTotalTimeTicks,
                        shouldResetBlockState, isRandomizedLifeTime, hasSound, hasParticle), sp2);
            }
        }
    }

    //==============
    //player animation
    public static void FetchPlayerAnimationFromServer(ServerPlayer sp1, String AnimationFileName, String AnimationActionName) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchPlayerAnimationPacketFromServer(AnimationFileName, AnimationActionName), sp1);
    }

    public static void FetchPlayerAnimationForAllPlayerFromServer(ServerPlayer sp1, String AnimationFileName, String AnimationActionName, int EntityID) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchPlayerAnimationPacketForAllPlayersFromServer(EntityID, AnimationFileName, AnimationActionName), sp1);
    }

    public static void FetchPlayerAnimationWithSpeedModifierForAllPlayerFromServer(ServerPlayer sp1, String AnimationFileName, String AnimationActionName, int EntityID, float speed) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer(EntityID, AnimationFileName, AnimationActionName, speed), sp1);
    }



    //==============
    //others
    public static void FetchPlayerDeltaFromServer(ServerPlayer sp1, float x1, float y1, float z1, boolean isAdd) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchClientPlayerDeltaMovFromServer(x1, y1, z1, isAdd), sp1);
    }

    public static void FetchPlayerDeltaFromServer(ServerPlayer sp1, Vector3f vec3, boolean isAdd) {
        DfoSwdNetworkingMsgInitialization.sendToPlayer(new FetchClientPlayerDeltaMovFromServer(vec3.x(), vec3.y(), vec3.z(), isAdd), sp1);
    }

//    public static void FetchPlayerHasGravityFromServer(ServerPlayer sp1, boolean hasGravity) {
//        DfoSwordmanNetworkingMsgInitialization.sendToClient(new FetchClientPlayerHasGravityFromServer(hasGravity), sp1);
//    }

    //TODO: emotecraft send animation
//    public static void FetchPlayerAnimationFromServerToAllPlayer(ServerPlayer sp1, String AnimationFileName, String AnimationActionName, Vector3f targetLoc, float dist) {
//        DfoSwordmanNetworkingMsgInitialization.sendToPlayer(new FetchPlayerAnimationPacketFromServer(AnimationFileName, AnimationActionName), sp1);
//    }
}
