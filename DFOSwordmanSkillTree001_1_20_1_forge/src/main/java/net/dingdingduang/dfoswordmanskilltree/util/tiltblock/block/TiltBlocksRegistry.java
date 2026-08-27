package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TiltBlocksRegistry {
    public static final BlockBehaviour.Properties TiltBlockMaterial = BlockBehaviour.Properties.of().noCollission().noLootTable().air();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DfoSwordmanSkillTreeConstants.MOD_ID);

//    public static final RegistryObject<TiltBlock> TILT_BLOCK_REGISTRY_OBJECT = BLOCKS.register("tilt_block", () -> new TiltBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<TiltBlock> TILT_BLOCK_REGISTRY_OBJECT = BLOCKS.register("tilt_block", () -> new TiltBlock(TiltBlockMaterial));
}
