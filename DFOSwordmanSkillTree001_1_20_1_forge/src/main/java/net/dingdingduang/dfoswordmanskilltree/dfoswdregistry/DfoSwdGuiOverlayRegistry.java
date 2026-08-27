package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer.getClientItemOverlayTimerOverlay;
import static net.dingdingduang.dfoswordmanskilltree.util.client.DfoClientPlayerActionOverlayTimer.getDfoClientPlayerActionOverlayTimer;
//import static net.dingdingduang.dfoswordmanskilltree.util.server.ServerOverlayTimer.getServerOverlayTimerOverlay;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class DfoSwdGuiOverlayRegistry {
    @SubscribeEvent
    public static void onDfoSwdRegisterOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "dfoswdclientitemoverlay000", getClientItemOverlayTimerOverlay());
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "dfoswdclientplayeractionoverlay000", getDfoClientPlayerActionOverlayTimer());
//        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "dfoswdserveritemoverlay000", getServerOverlayTimerOverlay());
    }
}
