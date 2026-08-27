package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer;

import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;

public class RegistryDfoSwdSkillEffLayerEvent {
    public static void registerOnClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
//            if (entityType == EntityType.PLAYER) {
//                this.playerRegistrations++;
//            }

            if (entityRenderer instanceof PlayerEntityRenderer) {
                registrationHelper.register(new SkillEffLayer<>((PlayerEntityRenderer) entityRenderer));
            }
        });
    }
}
