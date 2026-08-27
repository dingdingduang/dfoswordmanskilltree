package net.dingdingduang.dfoswordmanskilltree.util.tiltblock;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.BlockMethods;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.*;

import modified.org.joml.Quaternionf;
import modified.org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleMethods;
import net.dingdingduang.dfoswordmanskilltree.util.BlockVec3;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlock;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.TiltBlockState;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getTemporaryBlockStateMap;

public class TiltBlockMethods {
    private static boolean BlockStateIsInAirConditions(BlockState blockState) {
        Block tempBlock = blockState.getBlock();
        return blockState.isAir() || (tempBlock instanceof HorizontalConnectingBlock || tempBlock instanceof FluidBlock || tempBlock instanceof ConnectingBlock);
    }

    private static boolean BlockStateIsNotInAirConditions(BlockState blockState) {
        return !blockState.isAir() && !BlockMethods.isBlockPassable(blockState.getBlock());
    }

    public static boolean TiltBlockEntityAction(TiltBlockEntity tiltBlockEntity) {
        BlockPos blockPos = tiltBlockEntity.getPos();

        ClientWorld level = getClientLevel();
//            if (tiltBlockEntity.getOriginalBlockState().shouldSpawnParticlesOnBreak() && tiltBlockEntity.getMaxLifeTimeTicks() - tiltBlockEntity.getLifeTimeTicks() < 10) {
        if (tiltBlockEntity.getMaxLifeTimeTicks() - tiltBlockEntity.getLifeTimeTicks() < 10) {
//                printInGameMsg("lifetime: "+ tiltBlockEntity.getLifeTimeTicks());
            Particle blockParticle = new BlockDustParticle(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0, 0, tiltBlockEntity.getOriginalBlockState(), blockPos);
            ParticleMethods.setParticleSpeed(blockParticle, (ExtraMathMethods.randomFloat() - 0.5D) * 0.3D, ExtraMathMethods.randomFloat() * 0.5D, (ExtraMathMethods.randomFloat() - 0.5D) * 0.3D);
            ParticleMethods.setParticleLifeTime(blockParticle, 10 + ExtraMathMethods.randomInt(60));

            ParticleMethods.addParticleToEngine(blockParticle);
        }

        if (tiltBlockEntity.getLifeTimeTicks() > tiltBlockEntity.getMaxLifeTimeTicks()) {
            level.removeBlockEntity(blockPos);
            BlockVec3 tempBV3 = new BlockVec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            getTemporaryBlockStateMap().remove(tempBV3.hashCode());
            BlockMethods.setBlock(level, blockPos, tiltBlockEntity.getOriginalBlockState(), 0);
            return true;
        }
        tiltBlockEntity.setLifeTimeTicks(tiltBlockEntity.getLifeTimeTicks() + 1);
        return false;
    }

    public static void SetupTiltBlockZone(World pLevel, Vector3f targetLoc, float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks, boolean shouldResetBlockState, boolean hasRandomizedLifeTimeTicks, int minX, int maxX, int minZ, int maxZ) {
        float tempRandomFloat = ExtraMathMethods.randomFloat();
        tempRandomFloat = (tempRandomFloat > 0.5) ? 1 : -1;

        double currentTargetLocHeight = targetLoc.y();

        float x1, z1, dist;

        for (int blockX = minX; blockX <= maxX; blockX++) {
            for (int blockZ = minZ; blockZ <= maxZ; blockZ++) {
                x1 = Math.abs((blockX + 0.5f) - targetLoc.x());
                z1 = Math.abs((blockZ + 0.5f) - targetLoc.z());
                dist = MathMethods.sqrt(x1 * x1 + z1 * z1);

                if ( MathMethods.floor(dist) % 2 == 0) { tempRandomFloat = 1 * tempRandomFloat; }
                else { tempRandomFloat = -1 * tempRandomFloat;}

                if (dist <= radius) {
                    BlockPos tempBlockPos = BlockMethods.getMutableBlockPos(blockX, currentTargetLocHeight, blockZ);
                    BlockState tempBlockState = pLevel.getBlockState(tempBlockPos);
                    BlockPos tempUpperBlockPos = BlockMethods.getUpperBlockPos(tempBlockPos);
                    BlockState tempUpperBlockState = pLevel.getBlockState(tempUpperBlockPos);

                    if (BlockStateIsNotInAirConditions(tempUpperBlockState)) {
                        BlockPos aboveTwoBp = BlockMethods.getUpperBlockPos(tempUpperBlockPos);
                        BlockState aboveTwoState = pLevel.getBlockState(aboveTwoBp);

                        if (BlockStateIsInAirConditions(aboveTwoState)) {
                            currentTargetLocHeight++;
                            tempBlockPos = tempUpperBlockPos;
                            tempBlockState = tempUpperBlockState;
                        }
                        else {
                            tempBlockPos = BlockMethods.getMutableBlockPos(blockX, targetLoc.y(), blockZ);
                            tempBlockState = pLevel.getBlockState(tempBlockPos);
                            tempUpperBlockPos = BlockMethods.getUpperBlockPos(tempBlockPos);
                            tempUpperBlockState = pLevel.getBlockState(tempUpperBlockPos);

                            if (BlockStateIsNotInAirConditions(tempUpperBlockState)) {
                                aboveTwoBp = BlockMethods.getUpperBlockPos(tempUpperBlockPos);
                                aboveTwoState = pLevel.getBlockState(aboveTwoBp);

                                if (BlockStateIsInAirConditions(aboveTwoState)) {
                                    currentTargetLocHeight = tempBlockPos.getY() + 1;
                                    tempBlockPos = tempUpperBlockPos;
                                    tempBlockState = tempUpperBlockState;
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    else {
                        if (!isLevelClientSide(pLevel) && BlockMethods.isBlockPassable(tempUpperBlockState.getBlock())) {
                            BlockMethods.destroyBlock(pLevel, tempUpperBlockPos);
                        }
                    }

                    if (BlockStateIsInAirConditions(tempBlockState)) {
                        BlockPos belowBp = BlockMethods.getBelowBlockPos(tempBlockPos);
                        BlockState belowState = pLevel.getBlockState(belowBp);

                        if (BlockStateIsNotInAirConditions(belowState)) {
                            currentTargetLocHeight--;
                            tempBlockPos = belowBp;
                            tempBlockState = belowState;
                        }
                        else {
                            tempBlockPos = BlockMethods.getMutableBlockPos(blockX, targetLoc.y(), blockZ);
                            tempBlockState = pLevel.getBlockState(tempBlockPos);

                            if (BlockStateIsInAirConditions(tempBlockState)) {
                                belowBp = BlockMethods.getBelowBlockPos(tempBlockPos);
                                belowState = pLevel.getBlockState(belowBp);

                                if (BlockStateIsNotInAirConditions(belowState)) {
                                    currentTargetLocHeight = tempBlockPos.getY() - 1;
                                    tempBlockPos = belowBp;
                                    tempBlockState = belowState;
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }

                    if (isLevelClientSide(pLevel)) {
                        BlockVec3 tempBV3 = new BlockVec3(tempBlockPos.getX(), tempBlockPos.getY(), tempBlockPos.getZ());
                        if (shouldResetBlockState && tempBlockState instanceof TiltBlockState && BlockMethods.isEntityBlock(tempBlockState.getBlock()) && getTemporaryBlockStateMap().containsKey(tempBV3.hashCode())) {
                            pLevel.removeBlockEntity(tempBlockPos);
                            //reset block
                            tempBlockState = getTemporaryBlockStateMap().get(tempBV3.hashCode());
                            BlockMethods.setBlock(pLevel, tempBlockPos, tempBlockState, 0);
                            getTemporaryBlockStateMap().remove(tempBV3.hashCode());
                        }
                        if (BlockStateIsInAirConditions(tempBlockState) || tempBlockState instanceof TiltBlockState || BlockMethods.isEntityBlock(tempBlockState.getBlock())) {
                            continue;
                        }

                        Vector3f translator = new Vector3f(0, MathMethods.max(0.0F, (dist / radius) - 0.5F) * 0.5F, 0);

                        float angle = (tempRandomFloat * (dist / radius) * (MathMethods.PI / 12) ) ;
                        Quaternionf rotator = new Quaternionf();
                        rotator.fromAxisAngleRad(1, 1, 1, angle);
                        if (radius <= 1.5) {
                            float tempAngle = (ExtraMathMethods.randomFloat() * 10F - 5F) * MathMethods.DEG_TO_RAD;
                            float tempW = modified.org.joml.Math.cosFromSin(MathMethods.sin(angle * 0.5f), tempAngle * 0.5f);
                            Quaternionf tempQuaternionf = new Quaternionf((ExtraMathMethods.randomFloat() * 10F - 5F) * MathMethods.DEG_TO_RAD,
                                    (ExtraMathMethods.randomFloat() * 20F - 10F) * MathMethods.DEG_TO_RAD,
                                    (ExtraMathMethods.randomFloat() * 10F - 5F) * MathMethods.DEG_TO_RAD, tempW);
                            rotator.mul(tempQuaternionf);
                        }
//                        float testAngle = (float) (AngleFromLocationToLocationHorizontal(new Vector3f(blockX + 0.5f,
//                        (float) targetLoc.currentTargetLocHeight(), blockZ + 0.5f), new Vector3f((float) targetLoc.x(),
//                        (float) targetLoc.currentTargetLocHeight(), (float) targetLoc.z())));
//                        rotator.fromAxisAngleRad(0.5f, 0.5f, 0.5f, testAngle);

                        TiltBlockState tempTiltBlockState = TiltBlock.getDefaultTiltBlockState(null);

                        tempTiltBlockState.setupTiltBlock(new BlockVec3(tempBlockPos.getX(), tempBlockPos.getY(), tempBlockPos.getZ()),
                                tempBlockState, translator, rotator, dist, radius, bouncingHeight, bouncingTotalTimeTicks,
                                (hasRandomizedLifeTimeTicks) ? (20 + ExtraMathMethods.randomInt(lifeTimeTicks)) : lifeTimeTicks);

                        BlockMethods.setBlock(pLevel, tempBlockPos, tempTiltBlockState, 0);
//                        TiltBlockEntity tempTiltBlockEntity = new TiltBlockEntity(tempBlockPos, tempBlockState, tempTiltBlockState);
//                        pLevel.getChunkAt(tempBlockPos).addAndRegisterBlockEntity(tempTiltBlockEntity);
//                        DfoClientActionOverlayTimer.addClientTiltBlockEntityAction(tempBlockPos.hashCode(), tempTiltBlockEntity);
//                        pLevel.setBlockEntity(tempTiltBlockEntity);

//                        tempTiltBlockEntity.setLevel(pLevel);
//                        tempTiltBlockEntity.clearRemoved();
//                        pLevel.getChunkAt(tempBlockPos).getBlockEntities().put(tempBlockPos.immutable(), tempTiltBlockEntity);
//                        DfoClientActionOverlayTimer.addClientTiltBlockEntityAction(TiltBlockEntityAction(tempTiltBlockEntity));

                        ParticleMethods.createTiltTerrainParticle(pLevel, 4, tempBlockState, tempBlockPos);
                    }
                }
            }
        }
    }

    public static void SetupCircleTiltBlocksZone(Vector3f targetLocation, World pLevel, float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks, boolean shouldResetBlockState, boolean hasRandomizedLifeTimeTicks, boolean hasSound, boolean hasParticle) {
        Vector3f targetLocBlockCenter = new Vector3f(MathMethods.floor(targetLocation.x()) + 0.5F, MathMethods.floor(targetLocation.y()), MathMethods.floor(targetLocation.z()) + 0.5F);

        BlockPos blockPos = BlockMethods.getMutableBlockPos(targetLocBlockCenter.x(), targetLocBlockCenter.y(), targetLocBlockCenter.z());
        BlockState originBlockState = pLevel.getBlockState(blockPos);

        //north neg Z, east pos X, south pos Z, west neg X, NE -z+x, NW -z-x, SE +z+x, SW +z-x
        if (BlockStateIsInAirConditions(originBlockState)) {
            boolean isFoundCenter = false;

            BlockPos tempBlockPos = BlockMethods.getMutableBlockPos(targetLocBlockCenter.x(), targetLocBlockCenter.y(), targetLocBlockCenter.z()-1);
            BlockState tempBlockState = pLevel.getBlockState(tempBlockPos);
            if (!tempBlockState.isAir()) {
                isFoundCenter = true;
                targetLocBlockCenter = targetLocBlockCenter.add(0, 0, -1);
            }

            if (!isFoundCenter) {
                tempBlockPos = BlockMethods.getMutableBlockPos(targetLocBlockCenter.x() + 1, targetLocBlockCenter.y(), targetLocBlockCenter.z());
                tempBlockState = pLevel.getBlockState(tempBlockPos);
                if (!tempBlockState.isAir()) {
                    isFoundCenter = true;
                    targetLocBlockCenter = targetLocBlockCenter.add(1, 0, 0);
                }

                if (!isFoundCenter) {
                    tempBlockPos = BlockMethods.getMutableBlockPos(targetLocBlockCenter.x(), targetLocBlockCenter.y(), targetLocBlockCenter.z() + 1);
                    tempBlockState = pLevel.getBlockState(tempBlockPos);
                    if (!tempBlockState.isAir()) {
                        isFoundCenter = true;
                        targetLocBlockCenter = targetLocBlockCenter.add(0, 0, 1);
                    }

                    if (!isFoundCenter) {
                        tempBlockPos = BlockMethods.getMutableBlockPos(targetLocBlockCenter.x() - 1, targetLocBlockCenter.y(), targetLocBlockCenter.z());
                        tempBlockState = pLevel.getBlockState(tempBlockPos);
                        if (!tempBlockState.isAir()) {
                            isFoundCenter = true;
                            targetLocBlockCenter = targetLocBlockCenter.add(-1, 0, 0);
                        }

                        if (!isFoundCenter) {
                            tempBlockPos = BlockMethods.getMutableBlockPos(targetLocBlockCenter.x() + 1, targetLocBlockCenter.y(), targetLocBlockCenter.z() - 1);
                            tempBlockState = pLevel.getBlockState(tempBlockPos);
                            if (!tempBlockState.isAir()) {
                                isFoundCenter = true;
                                targetLocBlockCenter = targetLocBlockCenter.add(1, 0, -1);
                            }

                            if (!isFoundCenter) {
                                tempBlockPos = BlockMethods.getMutableBlockPos(targetLocBlockCenter.x() - 1, targetLocBlockCenter.y(), targetLocBlockCenter.z() - 1);
                                tempBlockState = pLevel.getBlockState(tempBlockPos);
                                if (!tempBlockState.isAir()) {
                                    isFoundCenter = true;
                                    targetLocBlockCenter = targetLocBlockCenter.add(-1, 0, -1);
                                }

                                if (!isFoundCenter) {
                                    tempBlockPos = BlockMethods.getMutableBlockPos(targetLocBlockCenter.x() + 1, targetLocBlockCenter.y(), targetLocBlockCenter.z() + 1);
                                    tempBlockState = pLevel.getBlockState(tempBlockPos);
                                    if (!tempBlockState.isAir()) {
                                        isFoundCenter = true;
                                        targetLocBlockCenter = targetLocBlockCenter.add(1, 0, 1);
                                    }

                                    if (!isFoundCenter) {
                                        tempBlockPos = BlockMethods.getMutableBlockPos(targetLocBlockCenter.x() - 1, targetLocBlockCenter.y(), targetLocBlockCenter.z() + 1);
                                        tempBlockState = pLevel.getBlockState(tempBlockPos);
                                        if (!tempBlockState.isAir()) {
                                            isFoundCenter = true;
                                            targetLocBlockCenter = targetLocBlockCenter.add(-1, 0, 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!isFoundCenter) {
                return;
            }
        }

        if (radius < 1.0) {
            radius = 1.0f;
        }

        if (!isLevelClientSide(pLevel)) {
            DfoSwdNetworkingFetchMsgMethods.FetchTiltBlockActionFromServer(
                    targetLocBlockCenter.x(), targetLocBlockCenter.y(), targetLocBlockCenter.z(),
                    radius, lifeTimeTicks, bouncingHeight, bouncingTotalTimeTicks,
                    shouldResetBlockState, hasRandomizedLifeTimeTicks, hasSound, hasParticle
            );
//            DfoSwordmanNetworkingMsgInitialization.sendToAllPlayerWithChunk(new FetchTiltBlockActionPacketFromServer(targetLocBlockCenter.x(), targetLocBlockCenter.y(), targetLocBlockCenter.z(),
//                    radius, lifeTimeTicks, bouncingHeight, bouncingTotalTimeTicks,
//                    shouldResetBlockState, hasRandomizedLifeTimeTicks, hasSound, hasParticle), pLevel.getChunkAt(blockPos));
        }

        int minX = MathMethods.floor(targetLocBlockCenter.x() - radius);
        int maxX = MathMethods.ceil(targetLocBlockCenter.x() + radius);
        int minZ = MathMethods.floor(targetLocBlockCenter.z() - radius);
        int maxZ = MathMethods.ceil(targetLocBlockCenter.z() + radius);

        SetupTiltBlockZone(pLevel, targetLocBlockCenter, radius, lifeTimeTicks, bouncingHeight, bouncingTotalTimeTicks, shouldResetBlockState, hasRandomizedLifeTimeTicks, minX, maxX, minZ, maxZ);

        if (isLevelClientSide(pLevel)) {
            if (hasSound) {
                DfoSwdSoundMethods.PlayGroundQuakeSound(targetLocBlockCenter, 0.5F, ExtraMathMethods.randomFloat()*0.4f+0.8f);
            }

            if (hasParticle) {
                ParticleMethods.GenerateGroupOfSmokeParticles(pLevel, targetLocBlockCenter.x(), targetLocBlockCenter.y(), targetLocBlockCenter.z(), (int) (radius * 3));
            }
        }
    }
}
