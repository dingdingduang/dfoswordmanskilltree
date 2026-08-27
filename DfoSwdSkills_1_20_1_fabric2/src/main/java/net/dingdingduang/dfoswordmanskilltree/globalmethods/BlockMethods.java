package net.dingdingduang.dfoswordmanskilltree.globalmethods;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMethods {
    public static void setBlock(World level, BlockPos bp, BlockState bs, int flag) {
        level.setBlockState(bp, bs, flag);
    }

    public static boolean isEntityBlock(Block block) {
        return block instanceof BlockEntityProvider;
    }

    public static BlockPos getUpperBlockPos(BlockPos bp) {
        return bp.up();
    }

    public static BlockPos getBelowBlockPos(BlockPos bp) {
        return bp.down();
    }

    public static void destroyBlock(World level, BlockPos bp) {
        level.breakBlock(bp, true);
    }

    public static boolean isBlockPassable(Block block) {
        return block instanceof PlantBlock || block instanceof SnowBlock || block instanceof PowderSnowBlock || block instanceof FluidBlock;
    }

    public static BlockPos getMutableBlockPos(double x, double y, double z) {
        return new BlockPos.Mutable(x, y, z);
    }
}
