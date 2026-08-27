package net.dingdingduang.dfoswordmanskilltree.sbanimation;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.SpeedModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import modified.org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class SBPlayerAnimatorMethods {
    //    private static SpeedModifier PlayerAnimationSpeedModifier = new SpeedModifier(1f);
//    private static int PlayerAnimationModifierHashCode = -1;
    //Client Variables
//    private static HashMap<AbstractClientPlayer, SpeedModifier> PlayerAnimationSpeedModifierMap = new HashMap<>();
    private static HashMap<AbstractClientPlayer, SBPlayerAnimatorClientPlayerData> PlayerAnimationSpeedModifierMap = new HashMap<>();

//    private static String PlayerLastAnimationName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;
//    private static KeyframeAnimationPlayer PlayerLastAnimPlayer = null;

    //===========================
    //client side
    public static void PlayDfoSwordmanSkillAnimationClient(Player cp1, String animationName) {
        SetPlayerAnimationClient(cp1, "dfoswordmananimation", animationName);
    }

    public static void PlayDfoSwordmanSlashAnimationClient(Player cp1, String animationName) {
        SetPlayerAnimationClient(cp1, "slash_twohanded", animationName);
    }

    //server side
    public static void PlayDfoSwordmanSkillAnimationServer(ServerPlayer sp1, String AnimationActionName) {
        DfoSwdNetworkingFetchMsgMethods.FetchPlayerAnimationFromServer(sp1, "dfoswordmananimation", AnimationActionName);
    }

    public static void PlayDfoSwordmanSlashAnimationServer(ServerPlayer sp1, String AnimationActionName) {
        DfoSwdNetworkingFetchMsgMethods.FetchPlayerAnimationFromServer(sp1, "slash_twohanded", AnimationActionName);
    }

    public static void PlayDfoSwordmanAnimationServer(ServerPlayer sp1, String AnimationFileName, String AnimationActionName) {
        DfoSwdNetworkingFetchMsgMethods.FetchPlayerAnimationFromServer(sp1, AnimationFileName, AnimationActionName);
    }

    //to all players
    public static void PlayDfoSwdAnimToAllClientPlayers(ServerPlayer sp1, String AnimationFileName, String AnimationActionName) {
        Vector3f sp1Vec = getEntityPos(sp1), sp2Vec;
        for (ServerPlayer sp2: getMinecraftServerPlayerList()) {
            sp2Vec = getEntityPos(sp2);
            float tempDist = Vector3fMethods.Vec3fDistanceTo(sp1Vec, sp2Vec);
            //play anim if player in 10 block radius
            if (tempDist <= 25f) {
//                DfoSwordmanNetworkingMsgInitialization.sendToPlayer(
//                        new FetchPlayerAnimationPacketForAllPlayersFromServer(sp1.hashCode(), AnimationFileName, AnimationActionName), sp2);
                DfoSwdNetworkingFetchMsgMethods.FetchPlayerAnimationForAllPlayerFromServer(sp2, AnimationFileName, AnimationActionName, getEntityHashID(sp1));
            }
        }
    }

    public static void PlayDfoSwdAnimWithSpeedModifierToAllClientPlayers(ServerPlayer sp1, String AnimationFileName, String AnimationActionName, float speed) {
        Vector3f sp1Vec = getEntityPos(sp1), sp2Vec;
        for (ServerPlayer sp2: getMinecraftServerPlayerList()) {
            sp2Vec = getEntityPos(sp2);
            float tempDist = Vector3fMethods.Vec3fDistanceTo(sp1Vec, sp2Vec);
            //play anim if player in 10 block radius
            if (tempDist <= 25f) {
                DfoSwdNetworkingFetchMsgMethods.FetchPlayerAnimationWithSpeedModifierForAllPlayerFromServer(sp2, AnimationFileName, AnimationActionName, getEntityHashID(sp1), speed);
            }
        }
    }

    public static void PlayDfoSwdSkillAnimToAllClientPlayers(ServerPlayer sp1, String AnimationActionName) {
        PlayDfoSwdAnimToAllClientPlayers(sp1, "dfoswordmananimation", AnimationActionName);
    }

    public static void PlayDfoSwdSlashAnimToAllClientPlayers(ServerPlayer sp1, String AnimationActionName) {
        PlayDfoSwdAnimToAllClientPlayers(sp1, "slash_twohanded", AnimationActionName);
    }

    public static void PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(ServerPlayer sp1, String AnimationActionName, float speed) {
        PlayDfoSwdAnimWithSpeedModifierToAllClientPlayers(sp1, "dfoswordmananimation", AnimationActionName, speed);
    }

    public static void PlayDfoSwdSlashAnimithSpeedModifierToAllClientPlayers(ServerPlayer sp1, String AnimationActionName, float speed) {
        PlayDfoSwdAnimWithSpeedModifierToAllClientPlayers(sp1, "slash_twohanded", AnimationActionName, speed);
    }



    //===========================================
    public static void SetPlayerAnimationClient(Player a, String blockbenchAnimationFileName, String AnimationName) {
        //Get the animation for that player

        ModifierLayer<IAnimation> animation1 = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) a).get(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, blockbenchAnimationFileName));
//        IAnimation animation = PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) a).get(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, blockbenchAnimationFileName));

        if (animation1 != null) {
            //You can set an animation from anywhere ON THE CLIENT
            //Do not attempt to do this on a server, that will only fail
            KeyframeAnimation loc1 = PlayerAnimationRegistry.getAnimation(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, AnimationName));
            if (loc1 == null) {
//                printInGameMsg("animation name doesnt exist");
                return;
            }
            KeyframeAnimationPlayer tempAnim1 = new KeyframeAnimationPlayer(loc1);
            animation1.setAnimation(tempAnim1);

//            if (PlayerAnimationSpeedModifier == null) {
//                PlayerAnimationSpeedModifier = new SpeedModifier(1f);
//                animation1.addModifier(PlayerAnimationSpeedModifier, 0);
//            }

            if (!PlayerAnimationSpeedModifierMap.containsKey((AbstractClientPlayer) a)) { PlayerAnimationSpeedModifierMap.put((AbstractClientPlayer) a, new SBPlayerAnimatorClientPlayerData()); }
            SBPlayerAnimatorClientPlayerData tempClientPlayerData = PlayerAnimationSpeedModifierMap.get((AbstractClientPlayer) a);
            SpeedModifier PlayerAnimationSpeedModifier = tempClientPlayerData.getPlayerAnimationSpeedModifier();
            if (tempClientPlayerData.getPlayerAnimationModifierHashCode() != animation1.hashCode()) {
                tempClientPlayerData.setPlayerAnimationModifierHashCode(animation1.hashCode());
                animation1.addModifier(PlayerAnimationSpeedModifier, 0);
            }
            PlayerAnimationSpeedModifier.speed = 1f;

            tempClientPlayerData.setPlayerLastAnimationName(AnimationName);
            tempClientPlayerData.setPlayerLastAnimPlayer(tempAnim1);

            //You might use animation.replaceAnimationWithFade(); to create fade effect instead of sudden change
            //See javadoc for details
        }
//        else {
//            printInGameMsg("animation file doesnt exist");
//        }
    }



    public static void SetPlayerAnimationByEntityID(int entityID, String blockbenchAnimationFileName, String AnimationName) {
        if (getEntityByIntID(getClientLevel(), entityID) instanceof AbstractClientPlayer absPlayer) {
            ModifierLayer<IAnimation> animation1 = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(absPlayer).get(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, blockbenchAnimationFileName));
            if (animation1 != null) {
                setEntityBodyHorizontalFacingDeg(absPlayer, getEntityHorizontalFacingDeg(absPlayer));
                KeyframeAnimation loc1 = PlayerAnimationRegistry.getAnimation(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, AnimationName));
                if (loc1 == null) {
                    return;
                }
                KeyframeAnimationPlayer tempAnim1 = new KeyframeAnimationPlayer(loc1);
                animation1.setAnimation(tempAnim1);

//                if (PlayerAnimationSpeedModifier == null) {
//                    PlayerAnimationSpeedModifier = new SpeedModifier(1f);
//                    animation1.addModifier(PlayerAnimationSpeedModifier, 0);
//                }

                if (!PlayerAnimationSpeedModifierMap.containsKey(absPlayer)) { PlayerAnimationSpeedModifierMap.put(absPlayer, new SBPlayerAnimatorClientPlayerData()); }
                SBPlayerAnimatorClientPlayerData tempClientPlayerData = PlayerAnimationSpeedModifierMap.get(absPlayer);
                SpeedModifier PlayerAnimationSpeedModifier = tempClientPlayerData.getPlayerAnimationSpeedModifier();
                if (tempClientPlayerData.getPlayerAnimationModifierHashCode() != animation1.hashCode()) {
                    tempClientPlayerData.setPlayerAnimationModifierHashCode(animation1.hashCode());
                    animation1.addModifier(PlayerAnimationSpeedModifier, 0);
                }
                PlayerAnimationSpeedModifier.speed = 1f;

                tempClientPlayerData.setPlayerLastAnimationName(AnimationName);
                tempClientPlayerData.setPlayerLastAnimPlayer(tempAnim1);
            }
        }
    }

    public static void SetPlayerAnimationWithSpeedModifierByEntityID(int entityID, String blockbenchAnimationFileName, String AnimationName, float speed) {
        if (getEntityByIntID(getClientLevel(), entityID) instanceof AbstractClientPlayer absPlayer) {
            ModifierLayer<IAnimation> animation1 = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(absPlayer).get(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, blockbenchAnimationFileName));
            if (animation1 != null) {
                setEntityBodyHorizontalFacingDeg(absPlayer, getEntityHorizontalFacingDeg(absPlayer));
                KeyframeAnimation loc1 = PlayerAnimationRegistry.getAnimation(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, AnimationName));
                if (loc1 == null) {
                    return;
                }
                KeyframeAnimationPlayer tempAnim1 = new KeyframeAnimationPlayer(loc1);
                animation1.setAnimation(tempAnim1);

//                if (PlayerAnimationSpeedModifier == null) {
//                    PlayerAnimationSpeedModifier = new SpeedModifier(speed);
//                    animation1.addModifier(PlayerAnimationSpeedModifier, 0);
//                }

                if (!PlayerAnimationSpeedModifierMap.containsKey(absPlayer)) { PlayerAnimationSpeedModifierMap.put(absPlayer, new SBPlayerAnimatorClientPlayerData()); }
                SBPlayerAnimatorClientPlayerData tempClientPlayerData = PlayerAnimationSpeedModifierMap.get(absPlayer);
                SpeedModifier PlayerAnimationSpeedModifier = tempClientPlayerData.getPlayerAnimationSpeedModifier();
                if (tempClientPlayerData.getPlayerAnimationModifierHashCode() != animation1.hashCode()) {
                    tempClientPlayerData.setPlayerAnimationModifierHashCode(animation1.hashCode());
                    animation1.addModifier(PlayerAnimationSpeedModifier, 0);
                }
                PlayerAnimationSpeedModifier.speed = speed;

                tempClientPlayerData.setPlayerLastAnimationName(AnimationName);
                tempClientPlayerData.setPlayerLastAnimPlayer(tempAnim1);
            }
        }
    }

    public static String getPlayerLastAnimationName(AbstractClientPlayer abstractClientPlayer) {
        if (PlayerAnimationSpeedModifierMap.containsKey(abstractClientPlayer)) {
            return PlayerAnimationSpeedModifierMap.get(abstractClientPlayer).getPlayerLastAnimationName();
        }
        return DfoSwordmanSkillTreeConstants.NO_ANIMATION;
    }
    public static String getPlayerLastAnimationName(Player abstractClientPlayer) {
        return getPlayerLastAnimationName((AbstractClientPlayer) abstractClientPlayer);
    }

    public static KeyframeAnimationPlayer getPlayerLastAnimPlayer(AbstractClientPlayer abstractClientPlayer) {
        if (PlayerAnimationSpeedModifierMap.containsKey(abstractClientPlayer)) {
            return PlayerAnimationSpeedModifierMap.get(abstractClientPlayer).getPlayerLastAnimPlayer();
        }
        return null;
    }
    public static KeyframeAnimationPlayer getPlayerLastAnimPlayer(Player abstractClientPlayer) {
        return getPlayerLastAnimPlayer((AbstractClientPlayer) abstractClientPlayer);
    }

//Optional TODO:
//new event when player plays animation? or do what mowzie would do for geckolib animation?
//    public static void SetPlayerAnimationServer(Player a, String blockbenchAnimationFileName, String AnimationName) {
//
//    }
}
