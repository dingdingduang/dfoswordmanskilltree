package net.dingdingduang.dfoswordmanskilltree.particle;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.BlockMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;

import net.minecraft.block.BlockState;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.Particle;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dingdingduang.dfoswordmanskilltree.util.BlockVec3;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlockState;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import org.joml.Vector3f;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftInstance;
import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getTemporaryBlockStateMap;

public class ParticleMethods {
    public static ParticleManager getMinecraftParticleEngine() {
        return getMinecraftInstance().particleManager;
    }

    public static void addParticleToEngine(Particle particle) {
        getMinecraftParticleEngine().addParticle(particle);
    }

    public static void createTerrainParticleAtLoc(World pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, float alpha, float scale, int lifeTimeTicks, BlockState blockState, BlockPos blockPos) {
        if (pLevel instanceof ClientWorld cLevel) {
            Particle blockParticle = new BlockDustParticle(cLevel, pX, pY, pZ, 0, 0, 0, blockState, blockPos);
            setParticleSpeed(blockParticle, pXSpeed, pYSpeed, pZSpeed);
            blockParticle.scale(scale);
//        smokeParticle.setAlpha(alpha);
            if (lifeTimeTicks != -1) {
                setParticleLifeTime(blockParticle, lifeTimeTicks);
            }

            addParticleToEngine(blockParticle);
        }
    }

    public static void createParticleByTypeAtLoc(DefaultParticleType particleType, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, float alpha, float scale, int lifeTimeTicks) {
        Particle tempParticleType = generateParticle(particleType, pX, pY, pZ);
        if (tempParticleType == null) { return; }
        setParticleSpeed(tempParticleType, pXSpeed, pYSpeed, pZSpeed);
        tempParticleType.scale(scale);
//        tempParticleType.setAlpha(alpha);
        if (lifeTimeTicks != -1) {
            setParticleLifeTime(tempParticleType, lifeTimeTicks);
        }

        addParticleToEngine(tempParticleType);
    }

    public static void createParticleByTypeAtLoc(DefaultParticleType particleType, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, float alpha, float scale, int lifeTimeTicks, float redRatio, float greenRatio, float blueRatio) {
        Particle tempParticleType = generateParticle(particleType, pX, pY, pZ);
        if (tempParticleType == null) { return; }
        setParticleSpeed(tempParticleType, pXSpeed, pYSpeed, pZSpeed);
        tempParticleType.scale(scale);
        tempParticleType.setColor(redRatio, greenRatio, blueRatio);
//        tempParticleType.setAlpha(alpha);
        if (lifeTimeTicks != -1) {
            setParticleLifeTime(tempParticleType, lifeTimeTicks);
        }

        addParticleToEngine(tempParticleType);
    }



    //============================================
    //Particle Utility
    public static void createTerrainParticleToWhereEntityStand(Entity entity, int particleMaxNumber) {
        int x = Math.round(getEntityX(entity));
        int y = MathMethods.floor(getEntityY(entity));
        int z = Math.round(getEntityZ(entity));

        BlockPos blockPos = new BlockPos(x, y-1, z);
        BlockState blockState = getEntityLevel(entity).getBlockState(blockPos);
        int loopTime = 1;
        while (blockState.isAir() && loopTime <= 3) {
            blockPos = new BlockPos(x, y - 1 - loopTime, z);
            blockState = getEntityLevel(entity).getBlockState(blockPos);
            loopTime++;
        }
        if (!blockState.isAir()) {
            for (int i = 0; i < particleMaxNumber; i += ExtraMathMethods.randomInt(4)) {
                double pX = blockPos.getX() + (i % 2);
                double pZ = blockPos.getZ() + 1 - (i % 2);

                createTerrainParticleAtLoc(getEntityLevel(entity), pX, y, pZ,
                        (ExtraMathMethods.randomFloat() - 0.5f) * 0.3f, ExtraMathMethods.randomFloat() * 0.3f, (ExtraMathMethods.randomFloat() - 0.5f) * 0.3f,
                        1, 1, 10 + ExtraMathMethods.randomInt(60), blockState, blockPos);
            }
        }
//        else {
//            printInGameMsg("is air!");
//        }
    }

    public static void CreateTerrainParticleToWhereEntityStandMethodReference(Entity entity) {
        ParticleMethods.createTerrainParticleToWhereEntityStand(entity, ExtraMathMethods.randomInt(2)+1);
    }

    public static void createTiltTerrainParticle(World level, int particleMaxNumber, BlockState blockState, BlockPos blockPos) {
        for (int i = 0; i < particleMaxNumber; i += ExtraMathMethods.randomInt(4)) {
            double x = blockPos.getX() + (i % 2);
            double z = blockPos.getZ() + 1 - (i % 2);

            createTerrainParticleAtLoc(level, x, blockPos.getY() + 1, z,
                    (ExtraMathMethods.randomFloat() - 0.5f) * 0.3f, ExtraMathMethods.randomFloat() * 0.5f, (ExtraMathMethods.randomFloat() - 0.5f) * 0.3f,
                    1, 1, 10 + ExtraMathMethods.randomInt(60), blockState, blockPos);
        }
    }

    public static void GenerateGroupOfSmokeParticles(World pLevel, double pX, double pY, double pZ, int number) {
        BlockPos blockPos = BlockMethods.getMutableBlockPos(pX, pY, pZ);
        BlockState blockState = pLevel.getBlockState(blockPos);

        if (blockState instanceof TiltBlockState) {
            BlockVec3 tempBV3 = new BlockVec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            blockState = getTemporaryBlockStateMap().get(tempBV3.hashCode());
        }

        for (int i = 0; i < number; i ++) {
            createTerrainParticleAtLoc(pLevel, pX + (ExtraMathMethods.randomFloat() - 0.5f), pY, pZ + (ExtraMathMethods.randomFloat() - 0.5f),
                    (ExtraMathMethods.randomFloat() - 0.5f) * 0.3f, (ExtraMathMethods.randomFloat()) * 0.5f, (ExtraMathMethods.randomFloat() - 0.5f) * 0.3f,
                    1F, 1F, 50 + Math.round(ExtraMathMethods.randomFloat()*20), blockState, blockPos);

            createParticleByTypeAtLoc(ParticleTypes.CAMPFIRE_COSY_SMOKE, pX + (ExtraMathMethods.randomFloat() - 0.5f) * 0.5f, pY + 1.5f, pZ + (ExtraMathMethods.randomFloat() - 0.5f) * 0.5f,
                    (ExtraMathMethods.randomFloat() - 0.5f) * 0.1f, ExtraMathMethods.randomFloat() * 0.05f, (ExtraMathMethods.randomFloat() - 0.5f) * 0.1f, 0.33f, ExtraMathMethods.randomFloat(1.0f)+0.5f, -1);
        }
    }

    public static void GenerateSwordStabParticles(float pX, float pY, float pZ, float length, float horizontalDeg) {
        Vector3f origin_pos = new Vector3f(pX, pY, pZ), tempVec, tempVecCenter;
        Vector3f target_pos = Vector3fMethods.PolarProjectionFromVecHorizon(origin_pos, length, horizontalDeg);
        float reverseDeg = horizontalDeg - 180f, leftDeg = horizontalDeg + 90f, rightDeg = horizontalDeg - 90f;
//        float currentLength = length;

        for (float tempL = 0; tempL < length; tempL = tempL + 0.1f) {
            float tempPercent = tempL / length;
//            printInGameMsg("percent: "+tempPercent);
//            printInGameMsg("length: "+tempL);
            tempVecCenter = Vector3fMethods.PolarProjectionFromVecHorizon(origin_pos, (length - tempPercent*length) + 0.25f, horizontalDeg);
            for (float i = -90f; i < 91f; i++) {
                tempVec = Vector3fMethods.PolarProjectionFromRotation(tempVecCenter, tempPercent*length, leftDeg, i);
                createParticleByTypeAtLoc(ParticleTypes.SNOWFLAKE, tempVec.x(), tempVec.y(), tempVec.z(),
                        0, 0, 0, 0.33f, 1f, -1);
                tempVec = Vector3fMethods.PolarProjectionFromRotation(tempVecCenter, tempPercent*length, rightDeg, i);
                createParticleByTypeAtLoc(ParticleTypes.SNOWFLAKE, tempVec.x(), tempVec.y(), tempVec.z(),
                        0, 0, 0, 0.33f, 1f, -1);
            }
        }
//        tempVec = Vector3fMethods.PolarProjectionFromVecHorizon(origin_pos, tempPercent*length, rightDeg);
        createParticleByTypeAtLoc(ParticleTypes.SNOWFLAKE, target_pos.x(), target_pos.y(), target_pos.z(),
                0, 0, 0, 0.33f, 1f, -1);

//        createParticleByTypeAtLoc(ParticleTypes.EXPLOSION, pX, pY, pZ,
//                0, 0, 0, 0.33f, 1f, -1);
    }

    public static void GenerateTestParticle(double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        createParticleByTypeAtLoc(ParticleTypes.EXPLOSION, pX, pY, pZ,
                0, 0, 0, 0.33f, 1f, -1);
    }

    //_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    public static void GenerateBeneficialBuffParticle(float pX, float pY, float pZ, float pXSpeed, float pYSpeed, float pZSpeed) {
        createParticleByTypeAtLoc(getDefaultMCWhiteAshParticle(), pX, pY, pZ,
                pXSpeed, pYSpeed, pZSpeed,
                0.33f, 1.7f, 36, 0.2f, 0.6f, 0.2f);
    }


    //_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    public static void GenerateGlowParticle(float posX, float posY, float posZ, float previousPosX, float previousPosY, float previousPosZ, int number) {
        for (int i = 0; i < number; i++) {
            createParticleByTypeAtLoc(getDefaultMCWhiteAshParticle(), posX, posY, posZ,
                    (ExtraMathMethods.randomFloat() - 0.5f) * 0.2f + (previousPosX - posX) * 3f, -0.1f, (ExtraMathMethods.randomFloat() - 0.5f) * 0.2f + (previousPosZ - posZ) * 3f,
                    0.33f, 1.5f, -1);
        }
    }

    public static void GenerateGlowParticleAtEntityLoc(Entity entity, int number) {
        GenerateGlowParticle(getEntityX(entity), getEntityY(entity), getEntityZ(entity), getEntityPreviousX(entity), getEntityPreviousY(entity), getEntityPreviousZ(entity), number);
    }


    //_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    public static void GenerateHitParticle(float pX, float pY, float pZ, int number) {
        for (int i = 0; i < number; i++) {
            createParticleByTypeAtLoc(getDefaultMCWhiteAshParticle(), pX, pY, pZ,
                    (ExtraMathMethods.randomFloat() - 0.5f) * 0.2f, (ExtraMathMethods.randomFloat() - 0.5f) * 0.2f, (ExtraMathMethods.randomFloat() - 0.5f) * 0.2f,
                    0.33f, 1.5f, -1);
        }
    }

    public static DefaultParticleType getDefaultMCWhiteAshParticle() { return ParticleTypes.WHITE_ASH; }
    public static void GenerateHitParticle(Vector3f vec, int number) { GenerateHitParticle(vec.x(), vec.y(), vec.z(), number); }
    public static void setParticleSpeed(Particle particle, double dx, double dy, double dz) { particle.setVelocity(dx, dy, dz); }
    public static void setParticleLifeTime(Particle particle, int lifetime) { particle.setMaxAge(lifetime); }
    public static Particle generateParticle(DefaultParticleType particleType, double pX, double pY, double pZ) {
        return getMinecraftParticleEngine().addParticle(particleType, pX, pY, pZ, 0, 0, 0);
    }
}
