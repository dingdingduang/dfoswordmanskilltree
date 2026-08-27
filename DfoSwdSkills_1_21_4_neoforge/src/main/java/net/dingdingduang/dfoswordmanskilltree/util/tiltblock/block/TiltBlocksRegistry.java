package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.somebasicskills.globalmethods.GeneralMethods;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class TiltBlocksRegistry {
    public static final BlockBehaviour.Properties TiltBlockMaterial = BlockBehaviour.Properties.of().noCollission().noLootTable().air().setId(
            ResourceKey.create(
                    Registries.BLOCK, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "tilt_block")
            )
    );

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, DfoSwordmanSkillTreeConstants.MOD_ID);

//    public static final RegistryObject<TiltBlock> TILT_BLOCK_REGISTRY_OBJECT = BLOCKS.register("tilt_block", () -> new TiltBlock(BlockBehaviour.Properties.of()));
    public static final Holder<Block> TILT_BLOCK_REGISTRY_OBJECT = BLOCKS.register("tilt_block", () -> new TiltBlock(TiltBlockMaterial));
}
