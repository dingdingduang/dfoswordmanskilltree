package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import static net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer.getClientItemOverlayTimerOverlay;
import static net.dingdingduang.dfoswordmanskilltree.util.client.DfoClientPlayerActionOverlayTimer.getDfoClientPlayerActionOverlayTimer;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class DfoSwdGuiOverlayRegistry {
    private static final ResourceLocation DFO_SWD_CLIENT_TIMER_OVERLAY_000 = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "dfoswdclientitemoverlay000");
    private static final ResourceLocation DFO_SWD_CLIENT_PLAYER_ACTION_OVERLAY_001 = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "dfoswdclientplayeractionoverlay000");

    public static void onDfoSwdRegisterOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_BAR, DFO_SWD_CLIENT_TIMER_OVERLAY_000, getClientItemOverlayTimerOverlay());
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_BAR, DFO_SWD_CLIENT_PLAYER_ACTION_OVERLAY_001, getDfoClientPlayerActionOverlayTimer());
//        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "dfoswdserveritemoverlay000", getServerOverlayTimerOverlay());
    }
}
