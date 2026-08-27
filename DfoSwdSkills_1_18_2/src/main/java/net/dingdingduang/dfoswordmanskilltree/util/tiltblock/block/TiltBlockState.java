package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block;

import com.google.common.collect.ImmutableMap;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import modified.org.joml.Quaternionf;
import modified.org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.util.BlockVec3;

import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getTemporaryBlockStateMap;

public class TiltBlockState extends BlockState {
    private Vector3f translate;
    private Quaternionf rotation;
    private float dist;
    private float totalDist;
    private float bouncingHeight;
    private int bouncingTimeTicks;
    private int maxLifeTimeTicks;

    public TiltBlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> propertyMap, MapCodec<BlockState> mapCodec) {
        super(block, propertyMap, mapCodec);
    }

    public void setupTiltBlock(BlockVec3 blockPos, BlockState originalBlockState, Vector3f translate, Quaternionf rotation, float dist, float totalDist, float bouncingHeight, int bouncingTimeTicks, int maxLifeTime) {
        getTemporaryBlockStateMap().put(blockPos.hashCode(), originalBlockState);
        this.translate = translate;
        this.rotation = rotation;
        this.dist = dist;
        this.totalDist = totalDist;
        this.bouncingHeight = bouncingHeight;
        this.bouncingTimeTicks = bouncingTimeTicks;
        this.maxLifeTimeTicks = maxLifeTime;
    }

    public Vector3f getTranslate() { return this.translate; }
    public void setTranslate(Vector3f a) { this.translate = a; }
    public Quaternionf getRotation() { return this.rotation; }
    public void setRotation(Quaternionf a) { this.rotation = a; }
    public float getDist() { return this.dist; }
    public void setDist(float dist) { this.dist = dist; }
    public float getTotalDist() { return this.totalDist; }
    public void setTotalDist(float totalDist) { this.totalDist = totalDist; }
    public float getBouncingHeight() { return this.bouncingHeight; }
    public void setBouncingHeight(float a) { this.bouncingHeight = a; }
    public int getBouncingTimeTicks() { return this.bouncingTimeTicks; }
    public void setBouncingTimeTicks(int bouncingTimeTicks) { this.bouncingTimeTicks = bouncingTimeTicks; }
    public int getMaxLifeTimeTicks() { return this.maxLifeTimeTicks; }
    public void setMaxLifeTimeTicks(int a) { this.maxLifeTimeTicks = a; }

//    @Override
//    public boolean hasBlockEntity() {
//        return true;
//    }
//
//    @Override
//    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState neighborState, Direction dir) {
//        return false;
//    }
//
//    @Override
//    public boolean supportsExternalFaceHiding() {
//        return false;
//    }
//
//    @Override
//    public int getLightEmission(BlockGetter level, BlockPos blockPos) {
//        BlockVec3 tempBV3 = new BlockVec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
//        return getTemporaryBlockStateMap().containsKey(tempBV3.hashCode()) ? getTemporaryBlockStateMap().get(tempBV3.hashCode()).getLightEmission(level, blockPos) : this.owner.getLightEmission(this, level, blockPos);
//    }
//
//    @Override
//    public VoxelShape getShape(BlockGetter pLevel, BlockPos pPos) {
//        return Shapes.empty();
//    }
//
//    @Override
//    public VoxelShape getShape(BlockGetter pLevel, BlockPos pPos, CollisionContext pCollisionContext) {
//        return Shapes.empty();
//    }

//    @Override
//    public boolean hasBlockEntity() {
//        printInGameMsg("has blockentity?: " + super.hasBlockEntity());
//        return true;
//    }

    @Override
    public VoxelShape getCollisionShape(BlockGetter pLevel, BlockPos pPos) {
//        BlockVec3 tempBV3 = new BlockVec3(pPos.getX(), pPos.getY(), pPos.getZ());
//        BlockState blockState = getTemporaryBlockStateMap().get(tempBV3.hashCode());
//
//        if (blockState == null) {
//            return Shapes.empty();
//        }
//
//        return blockState.getCollisionShape(pLevel, pPos, CollisionContext.empty());
        return Shapes.block();
    }

    @Override
    public VoxelShape getCollisionShape(BlockGetter plevel, BlockPos pPos, CollisionContext pCollisionContext) {
//        BlockVec3 tempBV3 = new BlockVec3(pPos.getX(), pPos.getY(), pPos.getZ());
//        BlockState blockState = getTemporaryBlockStateMap().get(tempBV3.hashCode());
//
//        if (blockState == null) {
//            return Shapes.empty();
//        }
//
//        return blockState.getCollisionShape(plevel, pPos, pCollisionContext);
        return Shapes.block();
    }

//    @Override
//    public VoxelShape getVisualShape(BlockGetter plevel, BlockPos pPos, CollisionContext pCollisionContext) {
//        return Shapes.empty();
//    }

    @Override
    public RenderShape getRenderShape() {
        return RenderShape.INVISIBLE;
    }
}
