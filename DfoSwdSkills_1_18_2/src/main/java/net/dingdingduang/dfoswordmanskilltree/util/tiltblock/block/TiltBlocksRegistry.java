package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TiltBlocksRegistry {
    public static final Material TiltBlockMaterial = (new Material.Builder(MaterialColor.NONE)).nonSolid().noCollider().replaceable().build();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DfoSwordmanSkillTreeConstants.MOD_ID);

//    public static final RegistryObject<TiltBlock> TILT_BLOCK_REGISTRY_OBJECT = BLOCKS.register("tilt_block", () -> new TiltBlock(BlockBehaviour.Properties.of(Material.AIR)));
    public static final RegistryObject<TiltBlock> TILT_BLOCK_REGISTRY_OBJECT = BLOCKS.register("tilt_block", () -> new TiltBlock(BlockBehaviour.Properties.of(TiltBlockMaterial)));
}
