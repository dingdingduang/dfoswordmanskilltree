package net.dingdingduang.dfoswordmanskilltree.bus;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.DfoSwdGlobalServerValue;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID)
public class DfoSwdServerPlayerEvent {
    //load on server side when player login
    @SubscribeEvent
    public static void DfoSwdStartInitWhenPlayerLogin(PlayerEvent.PlayerLoggedInEvent e) {
        if (e.getEntity() instanceof ServerPlayer sp1) {
            if (!DfoSwdGlobalServerValue.isDfoSwdGlobalValInit()) {
//                ParticleRegistry.ParticleRegistryInit();
                DfoSwdGlobalServerValue.setDfoSwdGlobalValInit(true);
            }

            DfoSwdNetworkingFetchMsgMethods.FetchDfoSwordmanClientPlayerInitDataFromServer(sp1);
        }
    }


//    //clear data when player logout
//    @SubscribeEvent
//    public static void DfoSwdStopWhenPlayerLogout(PlayerEvent.PlayerLoggedOutEvent e) {
//        if (e.getEntity() instanceof ServerPlayer sp1) {
//            //getClientItemOverlayTimerOverlay().setActive(false);
//            DfoSwordmanNetworkingFetchMsgMethods.FetchDfoSwordmanClientPlayerClearDataFromServer(sp1);
//        }
//        //save Skill Data When Player Logout?
//    }

//    reset stat when player respawn
//    @SubscribeEvent
//    public static void DfoSwdResetSBSStatusWhenPlayerRespawn(PlayerEvent.PlayerRespawnEvent e) {
//        if (e.getEntity() instanceof ServerPlayer sp1) {
//            //check player passive skills
////            DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerDataAfterRespawnFromServer(sp1);
//            HashMap<LivingEntity, HashMap<String, Integer>> ServerLivingEntityState = getSLivingEntityState();
//            HashMap<String, Integer> LivingEntityState;
//            //guard
//            if (ServerLivingEntityState != null && ServerLivingEntityState.containsKey(sp1) && (LivingEntityState = ServerLivingEntityState.get(sp1)) != null) {
//                LivingEntityState.remove(DfoSwordmanSkillTreeConstants.IS_GUARDING);
//                KeyboardMethods.setHotBarKeyKidnapCounter(0);
//                KeyboardMethods.setMovementKeyKidnapCounter(0);
//                Blademaster029.cleanFlowStanceMap(sp1);
//            }
//        }
//    }


}
