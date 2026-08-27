package net.dingdingduang.dfoswordmanskilltree.particle;

import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.dingdingduang.dfoswordmanskilltree.util.BlockVec3;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlockState;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import org.joml.Vector3f;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftInstance;
import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getTemporaryBlockStateMap;

public class ParticleMethods {
    public static ParticleEngine getMinecraftParticleEngine() {
        return getMinecraftInstance().particleEngine;
    }

    public static void addParticleToEngine(Particle particle) {
        getMinecraftParticleEngine().add(particle);
    }

    @OnlyIn(Dist.CLIENT)
    public static void createTerrainParticleAtLoc(Level pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, float alpha, float scale, int lifeTimeTicks, BlockState blockState, BlockPos blockPos) {
        if (pLevel instanceof ClientLevel cLevel) {
            Particle blockParticle = new TerrainParticle(cLevel, pX, pY, pZ, 0, 0, 0, blockState, blockPos);
            blockParticle.setParticleSpeed(pXSpeed, pYSpeed, pZSpeed);
            blockParticle.scale(scale);
//        smokeParticle.setAlpha(alpha);
            if (lifeTimeTicks != -1) {
                blockParticle.setLifetime(lifeTimeTicks);
            }

            addParticleToEngine(blockParticle);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void createParticleByTypeAtLoc(SimpleParticleType particleType, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, float alpha, float scale, int lifeTimeTicks) {
        Particle tempParticleType = getMinecraftParticleEngine().createParticle(particleType, pX, pY, pZ, 0, 0, 0);
        if (tempParticleType == null) { return; }
        tempParticleType.setParticleSpeed(pXSpeed, pYSpeed, pZSpeed);
        tempParticleType.scale(scale);
//        tempParticleType.setAlpha(alpha);
        if (lifeTimeTicks != -1) {
            tempParticleType.setLifetime(lifeTimeTicks);
        }

        addParticleToEngine(tempParticleType);
    }

    @OnlyIn(Dist.CLIENT)
    public static void createParticleByTypeAtLoc(SimpleParticleType particleType, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, float alpha, float scale, int lifeTimeTicks, float redRatio, float greenRatio, float blueRatio) {
        Particle tempParticleType = getMinecraftParticleEngine().createParticle(particleType, pX, pY, pZ, 0, 0, 0);
        if (tempParticleType == null) { return; }
        tempParticleType.setParticleSpeed(pXSpeed, pYSpeed, pZSpeed);
        tempParticleType.scale(scale);
        tempParticleType.setColor(redRatio, greenRatio, blueRatio);
//        tempParticleType.setAlpha(alpha);
        if (lifeTimeTicks != -1) {
            tempParticleType.setLifetime(lifeTimeTicks);
        }

        addParticleToEngine(tempParticleType);
    }



    //============================================
    //Particle Utility
    @OnlyIn(Dist.CLIENT)
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

    @OnlyIn(Dist.CLIENT)
    public static void CreateTerrainParticleToWhereEntityStandMethodReference(Entity entity) {
        ParticleMethods.createTerrainParticleToWhereEntityStand(entity, ExtraMathMethods.randomInt(2)+1);
    }

    @OnlyIn(Dist.CLIENT)
    public static void createTiltTerrainParticle(Level level, int particleMaxNumber, BlockState blockState, BlockPos blockPos) {
        for (int i = 0; i < particleMaxNumber; i += ExtraMathMethods.randomInt(4)) {
            double x = blockPos.getX() + (i % 2);
            double z = blockPos.getZ() + 1 - (i % 2);

            createTerrainParticleAtLoc(level, x, blockPos.getY() + 1, z,
                    (ExtraMathMethods.randomFloat() - 0.5f) * 0.3f, ExtraMathMethods.randomFloat() * 0.5f, (ExtraMathMethods.randomFloat() - 0.5f) * 0.3f,
                    1, 1, 10 + ExtraMathMethods.randomInt(60), blockState, blockPos);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void GenerateGroupOfSmokeParticles(Level pLevel, double pX, double pY, double pZ, int number) {
        BlockPos blockPos = new BlockPos.MutableBlockPos(pX, pY, pZ);
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

    @OnlyIn(Dist.CLIENT)
    //TODO: a test but concurrent modification, wtf
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

    @OnlyIn(Dist.CLIENT)
    public static void GenerateTestParticle(double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        createParticleByTypeAtLoc(ParticleTypes.EXPLOSION, pX, pY, pZ,
                0, 0, 0, 0.33f, 1f, -1);
    }

    //_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    @OnlyIn(Dist.CLIENT)
    public static void GenerateBeneficialBuffParticle(float pX, float pY, float pZ, float pXSpeed, float pYSpeed, float pZSpeed) {
        createParticleByTypeAtLoc(ParticleTypes.WHITE_ASH, pX, pY, pZ,
                pXSpeed, pYSpeed, pZSpeed,
                0.33f, 1.7f, 36, 0.2f, 0.6f, 0.2f);
    }


    //_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    @OnlyIn(Dist.CLIENT)
    public static void GenerateGlowParticle(float posX, float posY, float posZ, float previousPosX, float previousPosY, float previousPosZ, int number) {
        for (int i = 0; i < number; i++) {
            createParticleByTypeAtLoc(ParticleTypes.WHITE_ASH, posX, posY, posZ,
                    (ExtraMathMethods.randomFloat() - 0.5f) * 0.2f + (previousPosX - posX) * 3f, -0.1f, (ExtraMathMethods.randomFloat() - 0.5f) * 0.2f + (previousPosZ - posZ) * 3f,
                    0.33f, 1.5f, -1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void GenerateGlowParticleAtEntityLoc(Entity entity, int number) {
        GenerateGlowParticle(getEntityX(entity), getEntityY(entity), getEntityZ(entity), getEntityPreviousX(entity), getEntityPreviousY(entity), getEntityPreviousZ(entity), number);
    }


    //_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    @OnlyIn(Dist.CLIENT)
    public static void GenerateHitParticle(float pX, float pY, float pZ, int number) {
        for (int i = 0; i < number; i++) {
            createParticleByTypeAtLoc(ParticleTypes.WHITE_ASH, pX, pY, pZ,
                    (ExtraMathMethods.randomFloat() - 0.5f) * 0.2f, (ExtraMathMethods.randomFloat() - 0.5f) * 0.2f, (ExtraMathMethods.randomFloat() - 0.5f) * 0.2f,
                    0.33f, 1.5f, -1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void GenerateHitParticle(Vector3f vec, int number) {
        GenerateHitParticle(vec.x(), vec.y(), vec.z(), number);
    }

    public static void GenerateWhiteParticle(double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, float alpha, float scale, int lifeTimeTicks, float redRatio, float greenRatio, float blueRatio) {
        createParticleByTypeAtLoc(ParticleTypes.WHITE_ASH, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, alpha, scale, lifeTimeTicks, redRatio, greenRatio, blueRatio);
    }

    public static void GenerateGeoTrailingPlaneParticle(int HandEffEntityID, double posX, double posY, double posZ, int lifeTimeTicks) {
        Particle tempParticleType = getMinecraftParticleEngine().createParticle(MCParticleRegistry.GEO_TRAIL_PLANE_PARTICLE_TYPE.get(), posX, posY, posZ, 0, 0, HandEffEntityID);
        if (tempParticleType == null) { return; }

        if (lifeTimeTicks != -1) {
            tempParticleType.setLifetime(lifeTimeTicks);
        }

        addParticleToEngine(tempParticleType);
    }
}
