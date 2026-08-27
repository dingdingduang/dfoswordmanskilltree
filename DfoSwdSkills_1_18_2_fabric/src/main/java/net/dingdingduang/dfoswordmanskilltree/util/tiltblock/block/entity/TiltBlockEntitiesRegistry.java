package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;

import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlocksRegistry;
import net.minecraft.util.registry.Registry;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class TiltBlockEntitiesRegistry {
//    public static final BlockEntityType<TiltBlockEntity> TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT = new BlockEntityType<TiltBlockEntity>(TiltBlockEntity::new, ImmutableSet.of(TiltBlocksRegistry.TILT_BLOCK_REGISTRY_OBJECT.get()), Util.fetchChoiceType(References.BLOCK_ENTITY, "tilt_block_entity")));
    public static final BlockEntityType<TiltBlockEntity> TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT = FabricBlockEntityTypeBuilder.create(TiltBlockEntity::new, TiltBlocksRegistry.TILT_BLOCK_REGISTRY_OBJECT).build();

    public static void register() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "tilt_block_entity_type"), TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT);
    }
}
