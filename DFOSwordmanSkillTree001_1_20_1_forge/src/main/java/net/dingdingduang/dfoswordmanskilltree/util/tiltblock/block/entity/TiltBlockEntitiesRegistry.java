package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity;

import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.google.common.collect.ImmutableSet;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlocksRegistry;

public class TiltBlockEntitiesRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DfoSwordmanSkillTreeConstants.MOD_ID);

//    public static final RegistryObject<BlockEntityType<TiltBlockEntity>> TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT = BLOCK_ENTITIES.register("tilt_block_entity", () ->
//            new GeneralBlockEntityType<TiltBlockEntity>(TiltBlockEntity::new, ImmutableSet.of(TiltBlocksRegistry.TILT_BLOCK_REGISTRY_OBJECT.get()), Util.fetchChoiceType(References.BLOCK_ENTITY, "tilt_block_entity")));
    public static final RegistryObject<BlockEntityType<TiltBlockEntity>> TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT = BLOCK_ENTITIES.register("tilt_block_entity", () ->
            new BlockEntityType<TiltBlockEntity>(TiltBlockEntity::new, ImmutableSet.of(TiltBlocksRegistry.TILT_BLOCK_REGISTRY_OBJECT.get()), Util.fetchChoiceType(References.BLOCK_ENTITY, "tilt_block_entity") ));
}
