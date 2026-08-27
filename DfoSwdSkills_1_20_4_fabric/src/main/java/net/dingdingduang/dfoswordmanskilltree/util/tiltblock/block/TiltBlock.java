package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block;

import net.dingdingduang.dfoswordmanskilltree.util.client.DfoClientPlayerActionOverlayTimer;

import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

//public class TiltBlock extends BaseEntityBlock {
public class TiltBlock extends Block implements BlockEntityProvider {
    private static TiltBlockState tiltBlockState;
//    private final StateDefinition<Block, BlockState> stateDefinition;

    public TiltBlock(AbstractBlock.Settings properties) {
        super(properties);

        StateManager.Builder<Block, BlockState> builder = new StateManager.Builder<>(this);
        tiltBlockState = (TiltBlockState) builder.build(TiltBlock::getDefaultTiltBlockState, TiltBlockState::new).getDefaultState();
    }

    public static TiltBlockState getDefaultTiltBlockState(Block block) {
        return tiltBlockState;
    }

//    @Override
//    protected ImmutableMap<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> pShapeGetter) {
//        return this.stateDefinition.getPossibleStates().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), pShapeGetter));
//    }

//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World level, BlockState blockState, BlockEntityType<T> pBlockEntityType) {
//        return level.isClientSide ? createTickerHelper(pBlockEntityType, TiltBlockEntitiesRegistry.TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT.get(), TiltBlockEntity::lifeTimeTick) : null;
//    }
//
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        if (blockState instanceof TiltBlockState tiltBlockState1) {
            TiltBlockEntity tempTiltBlockEntity = new TiltBlockEntity(blockPos, blockState, tiltBlockState1);
            DfoClientPlayerActionOverlayTimer.addClientTiltBlockEntityAction(blockPos.hashCode(), tempTiltBlockEntity);
            return tempTiltBlockEntity;
        }

        return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState pState, BlockView pLevel, BlockPos pPos, ShapeContext pCollisionContext) {
        return VoxelShapes.empty();
    }

//    @Override
//    public VoxelShape getCollisionShape(BlockState pState, BlockView pLevel, BlockPos pPos, ShapeContext pCollisionContext) {
//        return VoxelShapes.fullCube();
//    }

//    @Override
//    public RenderShape getRenderShape(BlockState pState) {
//        return RenderShape.MODEL;
//    }
}
