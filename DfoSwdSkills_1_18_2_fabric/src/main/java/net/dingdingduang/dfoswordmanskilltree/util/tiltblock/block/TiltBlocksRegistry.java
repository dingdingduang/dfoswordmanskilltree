package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class TiltBlocksRegistry {
//    public static final Material TiltBlockMaterial = (new Material.Builder(MapColor.CLEAR)).notSolid().allowsMovement().replaceable().build();
    public static final Material TiltBlockMaterial = new FabricMaterialBuilder(MapColor.CLEAR).notSolid().allowsMovement().replaceable().build();

//    public static final RegistryObject<TiltBlock> TILT_BLOCK_REGISTRY_OBJECT = BLOCKS.register("tilt_block", () -> new TiltBlock(BlockBehaviour.Properties.of(Material.AIR)));
    public static final TiltBlock TILT_BLOCK_REGISTRY_OBJECT = new TiltBlock(AbstractBlock.Settings.of(TiltBlockMaterial));
//    public static final TiltBlock TILT_BLOCK_REGISTRY_OBJECT = new FabricBlockSettings(AbstractBlock.Settings.of(TiltBlockMaterial));

    public static void register() {
        Registry.register(Registry.BLOCK, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "tilt_block"), TILT_BLOCK_REGISTRY_OBJECT);
    }
}
