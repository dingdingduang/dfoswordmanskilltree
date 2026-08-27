package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block;

import net.dingdingduang.dfoswordmanskilltree.util.client.DfoClientPlayerActionOverlayTimer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockEntity;

//public class TiltBlock extends BaseEntityBlock {
public class TiltBlock extends Block implements EntityBlock {
    private static TiltBlockState tiltBlockState;
//    private final StateDefinition<Block, BlockState> stateDefinition;

    public TiltBlock(BlockBehaviour.Properties properties) {
        super(properties);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
//        this.stateDefinition = builder.create(TiltBlock::getDefaultTiltBlockState, TiltBlockState::new);
//        tiltBlockState = (TiltBlockState) this.stateDefinition.any();
        tiltBlockState = (TiltBlockState) builder.create(TiltBlock::getDefaultTiltBlockState, TiltBlockState::new).any();
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
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> pBlockEntityType) {
//        return level.isClientSide ? createTickerHelper(pBlockEntityType, TiltBlockEntitiesRegistry.TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT.get(), TiltBlockEntity::lifeTimeTick) : null;
//    }
//
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        if (blockState instanceof TiltBlockState tiltBlockState1) {
            TiltBlockEntity tempTiltBlockEntity = new TiltBlockEntity(blockPos, blockState, tiltBlockState1);
            DfoClientPlayerActionOverlayTimer.addClientTiltBlockEntityAction(blockPos.hashCode(), tempTiltBlockEntity);
            return tempTiltBlockEntity;
        }

        return null;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pCollisionContext) {
        return Shapes.empty();
    }

//    @Override
//    public RenderShape getRenderShape(BlockState pState) {
//        return RenderShape.MODEL;
//    }
}
