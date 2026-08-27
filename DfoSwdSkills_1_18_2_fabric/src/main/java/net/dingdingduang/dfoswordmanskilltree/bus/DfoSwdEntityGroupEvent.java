package net.dingdingduang.dfoswordmanskilltree.bus;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods;

import net.dingdingduang.somebasicskills.event.SBServerPlayerFabricEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import static net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods.getLivingEntityCorrespondingStatus;
import static net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods.getLivingEntityPreviousEffectMap;

public class DfoSwdEntityGroupEvent {
    public static void registerClientTickEvent() {
        ClientEntityEvents.ENTITY_LOAD.register(DfoSwdEntityGroupEvent::EntityJoinWorldClientAction);
        ClientEntityEvents.ENTITY_UNLOAD.register(DfoSwdEntityGroupEvent::EntityLeaveWorldClientAction);
    }

    public static void registerServerTickEvent() {
        ServerEntityEvents.ENTITY_LOAD.register(DfoSwdEntityGroupEvent::EntityJoinWorldServerAction);
        ServerEntityEvents.ENTITY_UNLOAD.register(DfoSwdEntityGroupEvent::EntityLeaveWorldServerAction);
    }

    private static void EntityJoinWorldClientAction(Entity entity, ClientWorld clientWorld) {
        EntityGroupMethods.getCurrentEntitiesInClientLevel().put(entity, DfoSwordmanSkillTreeConstants.CONSTANT_BYTE_0);
    }

    private static void EntityLeaveWorldClientAction(Entity entity, ClientWorld clientWorld) {
        EntityGroupMethods.getCurrentEntitiesInClientLevel().remove(entity);
    }

    private static void EntityJoinWorldServerAction(Entity entity, ServerWorld serverWorld) {
        EntityGroupMethods.getCurrentEntitiesInServerLevel().put(entity, DfoSwordmanSkillTreeConstants.CONSTANT_BYTE_0);
        if (entity instanceof ServerPlayerEntity sp1) {
            DfoGeneralMethods.getCurrentServerPlayerMap().put(sp1, DfoSwordmanSkillTreeConstants.CONSTANT_BYTE_0);
        }
    }

    private static void EntityLeaveWorldServerAction(Entity entity, ServerWorld serverWorld) {
        EntityGroupMethods.getCurrentEntitiesInServerLevel().remove(entity);
        if (entity instanceof LivingEntity livingEntity) {
            getLivingEntityCorrespondingStatus().remove(livingEntity);
            getLivingEntityPreviousEffectMap().remove(livingEntity);
            if (livingEntity instanceof ServerPlayerEntity sp1) {
                DfoGeneralMethods.getCurrentServerPlayerMap().remove(sp1);
            }
        }
    }
}
