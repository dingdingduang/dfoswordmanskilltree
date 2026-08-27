package net.dingdingduang.dfoswordmanskilltree.bus;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods.getLivingEntityCorrespondingStatus;
import static net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods.getLivingEntityPreviousEffectMap;

@Mod.EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID)
public class DfoSwdEntityGroupEvent {
    @SubscribeEvent
    public static void DfoSwdAddEntityIfJoinLevel(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide()) {
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
    public static void DfoSwdRemoveEntityIfLeaveLevel(EntityLeaveWorldEvent event) {
        if (!event.getWorld().isClientSide()) {
            EntityGroupMethods.getCurrentEntitiesInServerLevel().remove(event.getEntity());
            if (event.getEntity() instanceof LivingEntity livingEntity) {
                getLivingEntityCorrespondingStatus().remove(livingEntity);
                getLivingEntityPreviousEffectMap().remove(livingEntity);
                if (livingEntity instanceof ServerPlayer sp1) {
                    DfoGeneralMethods.getCurrentServerPlayerMap().remove(sp1);
                }
            }
        }
        else {
            EntityGroupMethods.getCurrentEntitiesInClientLevel().remove(event.getEntity());
        }
    }
}
