package net.dingdingduang.dfoswordmanskilltree.bus;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer;

import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, value = Dist.CLIENT)
public class DfoSwdClientPlayerEvent {
    @SubscribeEvent
    public static void DfoSwdStopWhenClientPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event) {
//        KeyboardMethods.ClientKeybindingsStorageFileWriteTo();
        ClientItemOverlayTimer.getClientItemOverlayTimerOverlay().setActive(false);
        GlobalClientMaps.getTemporaryBlockStateMap().clear();
        ParticleRegistry.ParticleMapClear();
        GeoModelClientMethodsRegistry.GeoModelClientActionNameMapClear();
        GlobalClientMaps.getReusableResourcesMap().clear();

//        DfoSwdClientMethodsRegistry.ClientActionNameMapClear();
    }

    @SubscribeEvent
    public static void DfoSwdReadDataWhenClientPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        KeyboardMethods.KeyboardMethodsInit();
//        KeyboardMethods.ClientKeybindingsStorageFileReadFrom();

        //init client damagetype
        DfoGeneralDamageSrc.initializeClientDamageType1201(event.getPlayer());
    }

    //===========================
    //prevent player from using item
//    @SubscribeEvent
//    public static void DfoSwdStopForestallPotentialChangingItemHotbarBehavior(ScreenEvent.KeyPressed.Pre event) {
//        //0-9, [!] might change due to version changing: 81 keydrop, 70 key swap item,
//
//        if ( (event.getKeyCode() > 48 && event.getKeyCode() <= 57) ||
//                (event.getKeyCode() == 81) ||
//                (event.getKeyCode() == 70) ||
//                (event.getKeyCode() == 0) ||
//                (event.getKeyCode() == 1) ||
//                (event.getKeyCode() == 32) ) {
//            if (event.getScreen() instanceof AbstractContainerScreen) {
//                printInGameMsg("keycode: "+event.getKeyCode());
//                //TODO: check if player in action/weak action
//                printInGameMsg("key is pressed!");
////            event.setCanceled(true);
//            }
//        }
//    }
}
