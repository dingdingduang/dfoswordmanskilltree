package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer;
import net.dingdingduang.dfoswordmanskilltree.util.client.DfoClientPlayerActionOverlayTimer;

public class DfoSwdGuiOverlayRegistry {
    private static final ClientItemOverlayTimer ClientItemOverlay = ClientItemOverlayTimer.getClientItemOverlayTimerOverlay();
    private static final DfoClientPlayerActionOverlayTimer DfoClientPlayerActionOverlay = DfoClientPlayerActionOverlayTimer.getDfoClientPlayerActionOverlayTimer();

    public static void registerFabricClientOverlaysFinal(int gameTick) {
        ClientItemOverlay.render(gameTick);
        DfoClientPlayerActionOverlay.render(gameTick);
    }
}
