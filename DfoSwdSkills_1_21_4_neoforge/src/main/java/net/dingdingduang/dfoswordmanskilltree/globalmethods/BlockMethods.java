package net.dingdingduang.dfoswordmanskilltree.globalmethods;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

public class BlockMethods {
    public static void setBlock(Level level, BlockPos bp, BlockState bs, int flag) { level.setBlock(bp, bs, flag); }

    public static boolean isEntityBlock(Block block) { return block instanceof EntityBlock; }

    public static BlockPos getUpperBlockPos(BlockPos bp) { return bp.above(); }

    public static BlockPos getBelowBlockPos(BlockPos bp) { return bp.below(); }

    public static void destroyBlock(Level level, BlockPos bp) { level.destroyBlock(bp, true); }

    public static boolean isBlockPassable(Block block) {
        return block instanceof BushBlock || block instanceof SnowLayerBlock || block instanceof PowderSnowBlock || block instanceof LiquidBlock;
    }

    public static BlockPos getMutableBlockPos(double x, double y, double z) { return new BlockPos.MutableBlockPos(x, y, z); }
}
