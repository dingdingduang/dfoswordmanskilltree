package net.dingdingduang.dfoswordmanskilltree.entity;

import net.dingdingduang.dfoswordmanskilltree.entity.clientmovementhelper.ClientMovementHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistryName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.handeff.HandEffEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientparticlehelper.ClientParticleHelperEntity;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

import java.util.function.Function;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.printInGameMsg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class DfoGeneralEntitySpawnMethods {
    public static Entity MCLoadEntityRecursive(CompoundTag pCompound, Level pLevel, Function<Entity, Entity> pEntityFunction) {
        return EntityType.loadEntityRecursive(pCompound, pLevel, pEntityFunction);
    }

    public static ClientMovementHelper SetupClientMovementHelper(Entity owner, int maxLifetime,
                                                                 boolean hasTargetLoc, float startX, float startY, float startZ,
                                                                 float targetX, float targetY, float targetZ,
                                                                 String ClientActionName, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + DfoMinecraftDefaultEntitiesRegistryConstants.CLIENT_MOVEMENT_HELPER_STR_ID;

        CompoundTag nbtEntityTag = GeoModelSpawnMethods.getEntityIDTag(entityID);

        ClientMovementHelper Entity000 = (ClientMovementHelper) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, startX, startY, startZ, 0f, 0f);
            return tempEntity;
        });

        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setOwner(owner);
        if (hasTargetLoc) {
            Entity000.setTargetLoc(startX, startY, startZ, targetX, targetY, targetZ, true);
        }
        Entity000.setClientActionName(ClientActionName);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static ClientParticleHelperEntity SetupClientParticleHelper(Entity owner, float horizontalFacing, float verticalFacing, int maxLifetime, boolean isBoundToEntity,
                                                                       boolean hasTargetLoc, float startX, float startY, float startZ,
                                                                       float targetX, float targetY, float targetZ,
                                                                       String ClientActionName, String ParticleActionName, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.CLIENT_PARTICLE_HELPER_STR_ID;

        CompoundTag nbtEntityTag = GeoModelSpawnMethods.getEntityIDTag(entityID);

        ClientParticleHelperEntity Entity000 = (ClientParticleHelperEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, startX, startY, startZ, horizontalFacing, verticalFacing);
            return tempEntity;
        });

        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setOwner(owner);
        if (isBoundToEntity) {
            Entity000.setIsBoundToEntity(isBoundToEntity);
            Entity000.setTargetPosX(targetX);
            Entity000.setTargetPosY(targetY);
            Entity000.setTargetPosZ(targetZ);
        }
        if (hasTargetLoc) {
            Entity000.setTargetLoc(startX, startY, startZ, targetX, targetY, targetZ, true);
        }
        Entity000.setClientActionName(ClientActionName);
        Entity000.setParticleName(ParticleActionName);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static ClientParticleHelperEntity SetupClientParticleHelperNoLoc(Entity owner, float horizontalFacing, float verticalFacing, int maxLifetime, float x, float y, float z,
                                                                            String ClientActionName, String ParticleActionName, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        return SetupClientParticleHelper(owner, horizontalFacing, verticalFacing, maxLifetime, false, false, x, y, z, x, y, z, ClientActionName, ParticleActionName, serverEntityAction, serverEntityActionTickPeriod, addEntityToWorldRightAway);
    }

    public static ClientParticleHelperEntity SetupClientParticleHelperBoundToEntity(Entity owner, float horizontalFacing, float verticalFacing, int maxLifetime, float additionalX, float additionalY, float additionalZ,
                                                                                    String ClientActionName, String ParticleActionName, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        return SetupClientParticleHelper(owner, horizontalFacing, verticalFacing, maxLifetime, true, false, getEntityX(owner), getEntityY(owner), getEntityZ(owner), additionalX, additionalY, additionalZ, ClientActionName, ParticleActionName, serverEntityAction, serverEntityActionTickPeriod, addEntityToWorldRightAway);
    }

    public static ClientParticleHelperEntity SetupClientParticleHelperFromLocAToLocB(Entity owner, float horizontalFacing, float verticalFacing, int maxLifetime, float startX, float startY, float startZ,
                                                                                    float targetX, float targetY, float targetZ,
                                                                                    String ClientActionName, String ParticleActionName, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        return SetupClientParticleHelper(owner, horizontalFacing, verticalFacing, maxLifetime, false, true, startX, startY, startZ, targetX, targetY, targetZ, ClientActionName, ParticleActionName, serverEntityAction, serverEntityActionTickPeriod, addEntityToWorldRightAway);
    }

    public static ClientMovementHelper SetupClientMovementHelper(Entity owner, int maxLifetime,
                                                                 Vector3f startPos, Vector3f targetPos,
                                                                 String ClientActionName, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        return SetupClientMovementHelper(owner, maxLifetime, true, startPos.x(), startPos.y(), startPos.z(), targetPos.x(), targetPos.y(), targetPos.z(), ClientActionName, serverEntityAction, serverEntityActionTickPeriod, addEntityToWorldRightAway);
    }

    public static HandEffEntity SetupHandEffEntity(Entity owner, int maxLifetime, float scale, String GeoModelFilePath, String TextureFilePath,
                                                   String AnimationFilePath, String AnimationName, String ModelPartName, String ClientActionName, String ShouldRenderFuncName,
                                                   boolean addEntityToWorldRightAway) {


        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.HAND_EFF_ENTITY_NBT_ID;

        CompoundTag nbtEntityTag = GeoModelSpawnMethods.getEntityIDTag(entityID);

        HandEffEntity Entity000 = (HandEffEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner), getEntityY(owner), getEntityZ(owner), 0, 0);
            return tempEntity;
        });

        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setOwner(owner);
        Entity000.setEntityScale(scale);
        Entity000.setGeoModelFilePathName(GeoModelFilePath);
        Entity000.setTextureFilePathName(TextureFilePath);
        Entity000.setAnimationFilePath(AnimationFilePath);
        Entity000.setAnimationName(AnimationName);
        Entity000.setModelPartName(ModelPartName);
        Entity000.setClientActionName(ClientActionName);
        Entity000.setShouldRenderFuncName(ShouldRenderFuncName);
//        Entity000.setParticleName(ParticleActionName);
//        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static HandEffEntity SetupHandEffEntityWithTrailing(Entity owner, int maxLifetime, float scale, String GeoModelFilePath, String TextureFilePath,
                                                               String AnimationFilePath, String AnimationName, String ModelPartName, String ClientActionName, String ShouldRenderFuncName,
                                                               float trailingParticleLength, int trailingParticleMaxLifeTime, int TrailingParticleStartToFadeAtXTicks, int trailingParticleColorRed, int trailingParticleColorGreen, int trailingParticleColorBlue, int trailingParticleColorAlpha, boolean addEntityToWorldRightAway) {


        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.HAND_EFF_ENTITY_NBT_ID;

        CompoundTag nbtEntityTag = GeoModelSpawnMethods.getEntityIDTag(entityID);

        HandEffEntity Entity000 = (HandEffEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner), getEntityY(owner), getEntityZ(owner), 0, 0);
            return tempEntity;
        });

        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setOwner(owner);
        Entity000.setEntityScale(scale);
        Entity000.setGeoModelFilePathName(GeoModelFilePath);
        Entity000.setTextureFilePathName(TextureFilePath);
        Entity000.setAnimationFilePath(AnimationFilePath);
        Entity000.setAnimationName(AnimationName);
        Entity000.setModelPartName(ModelPartName);
        Entity000.setClientActionName(ClientActionName);
        Entity000.setShouldRenderFuncName(ShouldRenderFuncName);
//        Entity000.setParticleName(ParticleActionName);
//        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);

        Entity000.setHasTrailingParticle(true);
        Entity000.setTrailingParticleLength(trailingParticleLength);
        Entity000.setTrailingParticleMaxLifetime(trailingParticleMaxLifeTime);
        Entity000.setTrailingParticleStartFadingAtRemainingTicks(TrailingParticleStartToFadeAtXTicks);
        Entity000.setTrailingParticleColorRed(trailingParticleColorRed * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL);
        Entity000.setTrailingParticleColorGreen(trailingParticleColorGreen * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL);
        Entity000.setTrailingParticleColorBlue(trailingParticleColorBlue * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL);
        Entity000.setTrailingParticleColorAlpha(trailingParticleColorAlpha * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static HandEffEntity SetupHandEffEntityWithTrailingParticleOnly(Entity owner, int maxLifetime, float scale, String ModelPartName, String ClientActionName, String ShouldRenderFuncName,
                                                               float trailingParticleLength, int trailingParticleMaxLifeTime, int TrailingParticleStartToFadeAtXTicks, int trailingParticleColorRed, int trailingParticleColorGreen, int trailingParticleColorBlue, int trailingParticleColorAlpha, boolean addEntityToWorldRightAway) {


        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.HAND_EFF_ENTITY_NBT_ID;

        CompoundTag nbtEntityTag = GeoModelSpawnMethods.getEntityIDTag(entityID);

        HandEffEntity Entity000 = (HandEffEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner), getEntityY(owner), getEntityZ(owner), 0, 0);
            return tempEntity;
        });

        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setOwner(owner);
        Entity000.setEntityScale(scale);
        Entity000.setGeoModelFilePathName(GeoAnimationAndTextureName.NULL_ENTITY_GEO_MODEL_PATH);
        Entity000.setTextureFilePathName(GeoAnimationAndTextureName.NULL_ENTITY_TEXTURE_PATH);
        Entity000.setAnimationFilePath(GeoAnimationAndTextureName.NULL_ENTITY_ANIMATION_FILE_PATH);
        Entity000.setAnimationName(DfoSwordmanSkillTreeConstants.NO_ANIMATION);
        Entity000.setModelPartName(ModelPartName);
        Entity000.setClientActionName(ClientActionName);
        Entity000.setShouldRenderFuncName(ShouldRenderFuncName);
//        Entity000.setParticleName(ParticleActionName);
//        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);

        Entity000.setHasTrailingParticle(true);
        Entity000.setTrailingParticleLength(trailingParticleLength);
        Entity000.setTrailingParticleMaxLifetime(trailingParticleMaxLifeTime);
        Entity000.setTrailingParticleStartFadingAtRemainingTicks(TrailingParticleStartToFadeAtXTicks);
        Entity000.setTrailingParticleColorRed(trailingParticleColorRed * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL);
        Entity000.setTrailingParticleColorGreen(trailingParticleColorGreen * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL);
        Entity000.setTrailingParticleColorBlue(trailingParticleColorBlue * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL);
        Entity000.setTrailingParticleColorAlpha(trailingParticleColorAlpha * DfoSwordmanSkillTreeConstants.COLOR_255_RECIPROCAL);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }
}
