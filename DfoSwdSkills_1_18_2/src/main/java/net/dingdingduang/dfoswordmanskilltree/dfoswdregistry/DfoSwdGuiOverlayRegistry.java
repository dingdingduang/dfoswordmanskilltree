package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer.getClientItemOverlayTimerOverlay;
import static net.dingdingduang.dfoswordmanskilltree.util.client.DfoClientPlayerActionOverlayTimer.getDfoClientPlayerActionOverlayTimer;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class DfoSwdGuiOverlayRegistry {
    public static void registerDfoSwdOverlays(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> {
                    OverlayRegistry.registerOverlayAbove(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, "dfoswdclientitemoverlay000", getClientItemOverlayTimerOverlay());
                    OverlayRegistry.registerOverlayAbove(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, "dfoswdclientplayeractionoverlay000", getDfoClientPlayerActionOverlayTimer());
                }
        );
    }
}
