package net.dingdingduang.dfoswordmanskilltree.bus;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;

import static net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods.getLivingEntityCorrespondingStatus;
import static net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods.getLivingEntityPreviousEffectMap;

@EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID)
public class DfoSwdEntityGroupEvent {
    @SubscribeEvent
    public static void DfoSwdAddEntityIfJoinLevel(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            EntityGroupMethods.getCurrentEntitiesInServerLevel().put(event.getEntity(), DfoSwordmanSkillTreeConstants.CONSTANT_BYTE_0);
            if (event.getEntity() instanceof ServerPlayer sp1) {
                DfoGeneralMethods.getCurrentServerPlayerMap().put(sp1, DfoSwordmanSkillTreeConstants.CONSTANT_BYTE_0);
            }
        }
        else {
            EntityGroupMethods.getCurrentEntitiesInClientLevel().put(event.getEntity(), DfoSwordmanSkillTreeConstants.CONSTANT_BYTE_0);
        }
    }

    @SubscribeEvent
    public static void DfoSwdRemoveEntityIfLeaveLevel(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            EntityGroupMethods.getCurrentEntitiesInServerLevel().remove(event.getEntity());
            if (event.getEntity() instanceof ServerPlayer sp1) {
                DfoGeneralMethods.getCurrentServerPlayerMap().remove(sp1);
            }
            else {
                if (event.getEntity() instanceof LivingEntity entity) {
                    getLivingEntityCorrespondingStatus().remove(entity);
                    getLivingEntityPreviousEffectMap().remove(entity);
                }
            }
        }
        else {
            EntityGroupMethods.getCurrentEntitiesInClientLevel().remove(event.getEntity());
        }
    }
}
