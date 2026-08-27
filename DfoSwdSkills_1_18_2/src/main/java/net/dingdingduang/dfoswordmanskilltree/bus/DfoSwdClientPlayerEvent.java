package net.dingdingduang.dfoswordmanskilltree.bus;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer;

import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.minecraft.client.player.Input;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getTemporaryBlockStateMap;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, value = Dist.CLIENT)
public class DfoSwdClientPlayerEvent {
    @SubscribeEvent
    public static void DfoSwdStopWhenClientPlayerLogout(ClientPlayerNetworkEvent.LoggedOutEvent event) {
//        KeyboardMethods.ClientKeybindingsStorageFileWriteTo();
        ClientItemOverlayTimer.getClientItemOverlayTimerOverlay().setActive(false);
        getTemporaryBlockStateMap().clear();
        ParticleRegistry.ParticleMapClear();
        GeoModelClientMethodsRegistry.GeoModelClientActionNameMapClear();
//        DfoSwdClientMethodsRegistry.ClientActionNameMapClear();
    }

    @SubscribeEvent
    public static void DfoSwdReadDataWhenClientPlayerLogin(ClientPlayerNetworkEvent.LoggedInEvent event) {
        KeyboardMethods.KeyboardMethodsInit();
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


    @SubscribeEvent
    public static void dfoSwdKeyInputUpdate(MovementInputUpdateEvent event) {
        Input clientPlayerUpdatedInput = event.getInput();
        if (!KeyboardMethods.getKeyMappingsClientPlayerNotAbleToMove().isEmpty()) {
            clientPlayerUpdatedInput.forwardImpulse = 0F;
            clientPlayerUpdatedInput.leftImpulse = 0F;
            clientPlayerUpdatedInput.up = false;
            clientPlayerUpdatedInput.left = false;
            clientPlayerUpdatedInput.down = false;
            clientPlayerUpdatedInput.right = false;
            clientPlayerUpdatedInput.jumping = false;
            clientPlayerUpdatedInput.shiftKeyDown = false;
        }
    }
}
