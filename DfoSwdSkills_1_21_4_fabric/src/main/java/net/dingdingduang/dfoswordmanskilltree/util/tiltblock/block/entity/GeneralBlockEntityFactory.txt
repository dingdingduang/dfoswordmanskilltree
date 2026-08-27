package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public interface GeneralBlockEntityFactory<T extends BlockEntity> {
    T create(BlockPos pos, BlockState state);
}
