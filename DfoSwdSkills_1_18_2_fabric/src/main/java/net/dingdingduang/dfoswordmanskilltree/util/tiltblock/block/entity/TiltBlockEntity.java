package net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity;

import modified.org.joml.Quaternionf;
import modified.org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.util.BlockVec3;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlockState;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getTemporaryBlockStateMap;

public class TiltBlockEntity extends BlockEntity {
    private Vector3f translate;
    private Quaternionf rotation;
    private BlockState originalBlockState;
    private float dist;
    private float totalDist;
    private float bouncingHeight;
    private int bouncingTimeTicks;
    private int maxLifeTimeTicks;
    private int lifeTimeTicks = 0;

    public TiltBlockEntity(BlockPos blockPos, BlockState originalBlockState) {
        super(TiltBlockEntitiesRegistry.TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT, blockPos, originalBlockState);
    }

    public TiltBlockEntity(BlockPos blockPos, BlockState blockState, TiltBlockState tiltBlockState) {
        super(TiltBlockEntitiesRegistry.TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT, blockPos, blockState);

        BlockVec3 tempBV3 = new BlockVec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        this.originalBlockState = getTemporaryBlockStateMap().get(tempBV3.hashCode());
        this.bouncingHeight = tiltBlockState.getBouncingHeight();
        this.translate = tiltBlockState.getTranslate();
        this.dist = tiltBlockState.getDist();
        this.totalDist = tiltBlockState.getTotalDist();
        this.rotation = tiltBlockState.getRotation();
        this.bouncingTimeTicks = tiltBlockState.getBouncingTimeTicks();
        this.maxLifeTimeTicks = tiltBlockState.getMaxLifeTimeTicks();
    }

    public BlockState getOriginalBlockState() { return this.originalBlockState; }
    public void setOriginalBlockState(BlockState a) { this.originalBlockState = a; }
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
    public int getLifeTimeTicks() { return this.lifeTimeTicks; }
    public void setLifeTimeTicks(int a) { this.lifeTimeTicks = a; }
    public int getMaxLifeTimeTicks() { return this.maxLifeTimeTicks; }
    public void setMaxLifeTimeTicks(int a) { this.maxLifeTimeTicks = a; }

//    @OnlyIn(Dist.CLIENT)
//    public static void lifeTimeTick(World level, BlockPos blockPos, BlockState blockState, TiltBlockEntity tiltBlockEntity) {
//        if (tiltBlockEntity.getOriginalBlockState().shouldSpawnParticlesOnBreak() && tiltBlockEntity.getMaxLifeTimeTicks() - tiltBlockEntity.getLifeTimeTicks() < 10) {
//            Particle blockParticle = new TerrainParticle((ClientLevel)level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0, 0, tiltBlockEntity.getOriginalBlockState(), blockPos);
//            blockParticle.setParticleSpeed((ExtraMathMethods.randomFloat() - 0.5D) * 0.3D, ExtraMathMethods.randomFloat() * 0.5D, (ExtraMathMethods.randomFloat() - 0.5D) * 0.3D);
//            blockParticle.setLifetime(10 + ExtraMathMethods.randomInt(60));
//
//            ParticleMethods.addParticleToEngine(blockParticle);
//        }
//
//        if (tiltBlockEntity.getLifeTimeTicks() > tiltBlockEntity.getMaxLifeTimeTicks()) {
//            level.removeBlockEntity(blockPos);
//            BlockVec3 tempBV3 = new BlockVec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
//            getTemporaryBlockStateMap().remove(tempBV3.hashCode());
//            level.setBlock(blockPos, tiltBlockEntity.getOriginalBlockState(), 0);
//        }
//        tiltBlockEntity.setLifeTimeTicks(tiltBlockEntity.getLifeTimeTicks() + 1);
//    }
}
