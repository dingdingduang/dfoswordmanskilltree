package net.dingdingduang.dfoswordmanskilltree.entity;

import net.dingdingduang.dfoswordmanskilltree.entity.clientmovementhelper.ClientMovementHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.MOD_ID;

public class DfoEntitiesRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<ClientMovementHelper>> CLIENT_MOVEMENT_HELPER =
            ENTITY_TYPES.register(DfoMinecraftDefaultEntitiesRegistryConstants.CLIENT_MOVEMENT_HELPER_STR_ID,
                    () -> EntityType.Builder.<ClientMovementHelper>of(ClientMovementHelper::new, MobCategory.MISC)
                            .sized(0f, 0f)
                            .setUpdateInterval(Integer.MAX_VALUE)
                            .build(new ResourceLocation(MOD_ID, DfoMinecraftDefaultEntitiesRegistryConstants.CLIENT_MOVEMENT_HELPER_STR_ID).toString()));


//    public static void register() {
//        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
//    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}