package net.dingdingduang.dfoswordmanskilltree.entity;

import net.dingdingduang.dfoswordmanskilltree.entity.clientmovementhelper.ClientMovementHelper;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistryName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

import java.util.function.Function;

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

    public static ClientMovementHelper SetupClientMovementHelper(Entity owner, int maxLifetime,
                                                                 Vector3f startPos, Vector3f targetPos,
                                                                 String ClientActionName, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        return SetupClientMovementHelper(owner, maxLifetime, true, startPos.x(), startPos.y(), startPos.z(), targetPos.x(), targetPos.y(), targetPos.z(), ClientActionName, serverEntityAction, serverEntityActionTickPeriod, addEntityToWorldRightAway);
    }
}
