package net.dingdingduang.dfoswordmanskilltree.bus;

import net.dingdingduang.dfoswordmanskilltree.util.client.DfoClientPlayerActionOverlayTimer;
import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class DfoSwdClientTickEvent {
    public static void registerClientTickEvent() {
        ClientTickEvents.START_CLIENT_TICK.register(DfoSwdClientTickEvent::resetInputMovementImpluse);
        ClientTickEvents.END_CLIENT_TICK.register(DfoSwdClientTickEvent::resetInputMovementImpluse);
    }

    private static void resetInputMovementImpluse(MinecraftClient client) {
        if (!KeyboardMethods.getKeyMappingsClientPlayerNotAbleToMove().isEmpty()) {
            for (var keyMapping: KeyboardMethods.getKeyMappingsClientPlayerNotAbleToMove()) {
                keyMapping.setPressed(false);
            }
            DfoClientPlayerActionOverlayTimer.resetPlayerMovementInput();
        }
    }
}
