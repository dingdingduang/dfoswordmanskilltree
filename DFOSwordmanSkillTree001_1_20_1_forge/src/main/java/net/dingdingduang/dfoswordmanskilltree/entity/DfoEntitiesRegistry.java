package net.dingdingduang.dfoswordmanskilltree.entity;

import net.dingdingduang.dfoswordmanskilltree.entity.clientmovementhelper.ClientMovementHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.MOD_ID;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class DfoEntitiesRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<EntityType<ClientMovementHelper>> CLIENT_MOVEMENT_HELPER =
            ENTITY_TYPES.register(DfoMinecraftDefaultEntitiesRegistryConstants.CLIENT_MOVEMENT_HELPER_STR_ID,
                    () -> EntityType.Builder.<ClientMovementHelper>of(ClientMovementHelper::new, MobCategory.MISC)
                            .sized(0f, 0f)
                            .setUpdateInterval(Integer.MAX_VALUE)
                            .build(getMCResourceLocation(MOD_ID, DfoMinecraftDefaultEntitiesRegistryConstants.CLIENT_MOVEMENT_HELPER_STR_ID).toString()));


//    public static void register() {
//        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
//    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}