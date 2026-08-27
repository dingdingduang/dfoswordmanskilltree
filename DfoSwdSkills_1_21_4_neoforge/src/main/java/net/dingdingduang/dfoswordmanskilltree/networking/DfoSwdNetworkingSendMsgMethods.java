package net.dingdingduang.dfoswordmanskilltree.networking;

import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendUpdateEntityPosPacketToServer;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket.SendTiltBlockActionPacketToServer;

public class DfoSwdNetworkingSendMsgMethods {
    public static void SendTiltBlockActionFromClient(float vecX, float vecY, float vecZ,
                                                     float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks,
                                                     boolean shouldResetBlockState, boolean isRandomizedLifeTime, boolean hasSound, boolean hasParticle
    ) {
        DfoSwdNetworkingMsgInitialization.sendToServer(new SendTiltBlockActionPacketToServer(
                vecX, vecY, vecZ,
                radius, lifeTimeTicks, bouncingHeight, bouncingTotalTimeTicks,
                shouldResetBlockState, isRandomizedLifeTime, hasSound, hasParticle)
        );
    }

    public static void SendUpdateEntityPosPacketFromClient(float vecX, float vecY, float vecZ, int entityID) {
        DfoSwdNetworkingMsgInitialization.sendToServer(new SendUpdateEntityPosPacketToServer(vecX, vecY, vecZ, entityID) );
    }
}
