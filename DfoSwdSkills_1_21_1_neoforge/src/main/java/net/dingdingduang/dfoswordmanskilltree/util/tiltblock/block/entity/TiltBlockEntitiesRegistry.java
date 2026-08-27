package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntityType;

import com.google.common.collect.ImmutableSet;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlocksRegistry;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TiltBlockEntitiesRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, DfoSwordmanSkillTreeConstants.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TiltBlockEntity>> TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT = BLOCK_ENTITIES.register("tilt_block_entity", () ->
            new BlockEntityType<TiltBlockEntity>(TiltBlockEntity::new, ImmutableSet.of(TiltBlocksRegistry.TILT_BLOCK_REGISTRY_OBJECT.value()), Util.fetchChoiceType(References.BLOCK_ENTITY, "tilt_block_entity")));
}
