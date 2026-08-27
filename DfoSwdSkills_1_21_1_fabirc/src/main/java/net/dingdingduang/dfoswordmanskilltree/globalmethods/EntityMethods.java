package net.dingdingduang.dfoswordmanskilltree.globalmethods;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;

import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues;

import net.minecraft.block.BlockState;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
//import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;

public class EntityMethods {
//    public static final EntityAttributeModifier am1 = new EntityAttributeModifier(new UUID(-6409488642449977828L, -8777122800788000272L), "SBKnockbackResist", 10.0, EntityAttributeModifier.Operation.ADDITION);

    public static int getEntityTickCount(Entity a) { return a.age; }

    public static boolean isEntitySpectator(Entity a) { return a.isSpectator(); }

    public static float getLivingEntityCurrentHP(LivingEntity a) { return a.getHealth(); }
    public static void setLivingEntityCurrentHP(LivingEntity a, float amount) { a.setHealth(amount); }
    public static float getLivingEntityMaxHP(LivingEntity a) { return a.getMaxHealth(); }

    public static int getLivingEntityTickCount(LivingEntity a) { return a.age; }

    public static void EntityDamageTarget(Entity target, float amount) {
        target.damage(target.getDamageSources().generic(), amount);
    }

//    public static void LivingEntityDamageTarget(LivingEntity dealer, Entity target, float amount) {
//        target.hurt(dealer.damageSources().mobAttack(dealer), amount);
//    }

    public static void LivingEntityDamageTarget(LivingEntity dealer, LivingEntity target, float amount) {
        target.damage(target.getDamageSources().mobAttack(dealer), amount);
    }

    public static void LivingEntityMagicDamageTarget(LivingEntity dealer, Entity target, float amount) {
        target.damage(target.getDamageSources().indirectMagic(dealer, dealer), amount);
    }

    public static void DealerDamageTarget(DamageSource pSource, Entity target, float amount) {
        target.damage(pSource, amount);
    }

    public static boolean getEntityHasGravity(Entity a) { return !a.hasNoGravity(); }
    public static void setEntityHasGravity(Entity a, boolean hasGravity) { a.setNoGravity(!hasGravity); }

    public static Entity findHorizontalNearbyEntityFromCenterEntityLoc(Entity centerEntity, float maxDist, Predicate<Entity> cond) {
        float minDist = Float.MAX_VALUE;
        float entityDist;
        Entity nearbyEntity = null;
        for (Entity entity: GroupEnumEntitiesInRangeFilter(centerEntity, maxDist, 1, maxDist, maxDist, 1, maxDist, cond)) {
            if ((entityDist = entity.distanceTo(centerEntity)) < minDist) {
                nearbyEntity = entity;
                minDist = entityDist;
            }
        }
        return nearbyEntity;
    }

    public static Entity findHorizontalNearbyEntityFromCenterEntityLoc(Entity centerEntity, float maxDistX, float maxDistY, float maxDistZ, Predicate<Entity> cond) {
        float minDist = Float.MAX_VALUE;
        float entityDist;
        Entity nearbyEntity = null;
        for (Entity entity: GroupEnumEntitiesInRangeFilter(centerEntity, maxDistX, maxDistY, maxDistZ, cond)) {
            if ((entityDist = entity.distanceTo(centerEntity)) < minDist) {
                nearbyEntity = entity;
                minDist = entityDist;
            }
        }
        return nearbyEntity;
    }

//    public static LivingEntity findHorizontalNearbyLivingEntityFromCenterEntityLoc(Entity centerEntity, float maxDist, Predicate<Entity> cond) {
//        float minDist = Float.MAX_VALUE;
//        float entityDist;
//        LivingEntity nearbyEntity = null;
//        for (Entity entity: GroupEnumEntitiesInRangeFilter(centerEntity, maxDist, 1, maxDist, maxDist, 1, maxDist, cond)) {
//            if (entity instanceof LivingEntity livingEntity && (entityDist = entity.distanceTo(centerEntity)) < minDist) {
//                nearbyEntity = livingEntity;
//                minDist = entityDist;
//            }
//        }
//        return nearbyEntity;
//    }

    public static void addKnockbackResist(LivingEntity a) {
        //TODO use livingentity server map to cancel living entity knockback event if entity has the valuable
//        GlobalServerLivingEntityValues.putStateOnLivingEntity(a, DfoSwordmanSkillTreeConstants.MC_NO_KNOCKBACK, Constants.ACTION_ON);
        GlobalServerLivingEntityValues.increaseStateOnLivingEntityByX(a, DfoSwordmanSkillTreeConstants.MC_NO_KNOCKBACK, Constants.ACTION_ON);
//        if (a.getEntityAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).hasModifier(am1)) { return; }
//        a.getEntityAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).addTransientModifier(am1);
    }

    public static void removeKnockbackResist(LivingEntity entity) {
//        GlobalServerLivingEntityValues.increaseStateOnLivingEntityByX(entity, DfoSwordmanSkillTreeConstants.MC_NO_KNOCKBACK, -Constants.ACTION_ON);
        HashMap<LivingEntity, HashMap<String, Integer>> tempLivingEntityStateMap = GlobalServerLivingEntityValues.getSLivingEntityState();
        if (tempLivingEntityStateMap != null && tempLivingEntityStateMap.containsKey(entity)) {
            HashMap<String, Integer> tempStateMap = tempLivingEntityStateMap.get(entity);
            if (tempStateMap.containsKey(DfoSwordmanSkillTreeConstants.MC_NO_KNOCKBACK)) {
                int currentStateInt = tempStateMap.get(DfoSwordmanSkillTreeConstants.MC_NO_KNOCKBACK) - Constants.ACTION_ON;
                if (currentStateInt > 0) {
                    tempStateMap.put(DfoSwordmanSkillTreeConstants.MC_NO_KNOCKBACK, currentStateInt);
                }
                else {
                    tempStateMap.remove(DfoSwordmanSkillTreeConstants.MC_NO_KNOCKBACK);
                }
            }
        }


//        GlobalServerLivingEntityValues.removeStateFromLivingEntity(a, DfoSwordmanSkillTreeConstants.MC_NO_KNOCKBACK);
//        a.getEntityAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).removeModifier(am1);
    }

    //O(1) get remove
    //return true if entity has that tag
    public static boolean hasTagEntity(Entity a, String b) {
        if (applyTagToEntity(a, b)) {
            removeTagFromEntity(a, b);
            return false;
        }
        return true;
    }

    public static boolean applyTagToEntity(Entity a, String b) { return a.addCommandTag(b); }
    public static boolean removeTagFromEntity(Entity a, String b) { return a.removeCommandTag(b); }

    //TODO, to be removed
    public static void SpawnEntity(Entity a) { getEntityLevel(a).spawnEntity(a); }

    public static int GetEntityInvulnerableTime(Entity a) { return a.timeUntilRegen; }
    public static void SetEntityInvulnerableTime(Entity a, int val) { a.timeUntilRegen = val; }

    public static float getEntityHorizontalFacingDeg(Entity a) { return a.getYaw(); }
    public static void setEntityHorizontalFacingDeg(Entity a, float deg) { a.setYaw(deg); }
    public static void setEntityHorizontalFacingDeg(Entity a, double deg) { a.setYaw((float) deg); }

    public static void setEntityHeadHorizontalFacingDeg(Entity a, float deg) { a.setHeadYaw(deg); }
    public static float getEntityHeadHorizontalFacingDeg(Entity a) { return a.getHeadYaw(); }
    public static void setEntityBodyHorizontalFacingDeg(Entity a, float deg) { a.setBodyYaw(deg); }
    public static float getEntityBodyHorizontalFacingDeg(Entity a) {
        if (a instanceof LivingEntity b) {
            return b.bodyYaw;
        }
        else {
            return a.getYaw();
        }
    }

    public static float getEntityVerticalFacingDeg(Entity a) { return a.getPitch(); }
    public static void setEntityVerticalFacingDeg(Entity a, float deg) { a.setPitch(deg); }
    public static void setEntityVerticalFacingDeg(Entity a, double deg) { a.getPitch((float) deg); }

    //UUID
    public static UUID getEntityUUID(Entity a) { return a.getUuid(); }

    public static int getEntityHashID(Entity a) { return a.hashCode(); }

    //Heal
    public static void HealLivingEntity(LivingEntity a, float b) { a.heal(b); }

    public static boolean isCollisionWithBlock(Entity a) {
        int x = MathMethods.floor(getEntityX(a));
        int y = MathMethods.floor(getEntityY(a));
        int z = MathMethods.floor(getEntityZ(a));

        BlockState state = getEntityLevel(a).getBlockState(new BlockPos(x, y, z));

        return !state.isAir();
    }

    public static boolean isEntityOnGround(Entity a) { return a.isOnGround(); }

    public static boolean isEntityBPosRFOnGround(Entity a) {
        int x = MathMethods.floor(getEntityX(a) + ExtraMathMethods.randomFloat()-0.5f);
        int y = MathMethods.floor(getEntityY(a));
        int z = MathMethods.floor(getEntityZ(a) + ExtraMathMethods.randomFloat()-0.5f);

        BlockState state = getEntityLevel(a).getBlockState(new BlockPos(x, y-1, z));

        return !state.isAir();
    }

    public static boolean isEntityBPosOnGround(Entity a) {
        int x = MathMethods.floor(getEntityX(a));
        int y = MathMethods.floor(getEntityY(a));
        int z = MathMethods.floor(getEntityZ(a));

        BlockState state = getEntityLevel(a).getBlockState(new BlockPos(x, y-1, z));

        return !state.isAir();
    }

    public static boolean isEntityFarFromGround(Entity a, float dist) {
        int x = MathMethods.floor(getEntityX(a));
        int y = MathMethods.floor(getEntityY(a) - dist);
        int z = MathMethods.floor(getEntityZ(a));

        BlockState state = getEntityLevel(a).getBlockState(new BlockPos(x, y, z));

        return !state.isAir();
    }

    public static boolean isEntityRFFarFromGround(Entity a, float dist) {
        int x = MathMethods.floor(getEntityX(a) + ExtraMathMethods.randomFloat() - 0.5f);
        int y = MathMethods.floor(getEntityY(a) - dist);
        int z = MathMethods.floor(getEntityZ(a) + ExtraMathMethods.randomFloat() - 0.5f);

        BlockState state = getEntityLevel(a).getBlockState(new BlockPos(x, y, z));

        return !state.isAir();
    }

    public static boolean isBlockPosAirOnly(Entity owner, float x1, float y1, float z1) {
        int x = MathMethods.floor(x1);
        int y = MathMethods.floor(y1);
        int z = MathMethods.floor(z1);

        BlockState state = getEntityLevel(owner).getBlockState(new BlockPos(x, y, z));

        return state.isAir();
    }

    public static boolean isBlockPosAirOnly(Entity owner, Vector3f pos) {
        return isBlockPosAirOnly(owner, pos.x(), pos.y(), pos.z());
    }

    public static boolean isCollisionWithLivingEntities(double range, Entity loc, Predicate<? super Entity> condition) {
        return !GroupEnumEntitiesInRangeFilter(loc, range, range, range, condition).isEmpty();
    }

    public static void setEntityFallDistance(Entity a, float b) { a.fallDistance = b; }

    public static Vector3f getEntityPos(Entity a) {
        return Vector3fMethods.convertMCVec3fToVector3f(a.getPos());
    }

    public static Vector3f getEntityBlockPos(Entity a) {
        BlockPos entityBlockPos = a.getBlockPos();
        return new Vector3f(entityBlockPos.getX(), entityBlockPos.getY(), entityBlockPos.getZ());
    }

    public static Vector3f getEntityClientPosWithPartialTicks(Entity a, float partialTicks) {
        float currentClientEntityPosX = Vector3fMethods.lerp1D(partialTicks, getEntityPreviousX(a), getEntityX(a));
        float currentClientEntityPosY = Vector3fMethods.lerp1D(partialTicks, getEntityPreviousY(a), getEntityY(a));
        float currentClientEntityPosZ = Vector3fMethods.lerp1D(partialTicks, getEntityPreviousZ(a), getEntityZ(a));
        return new Vector3f(currentClientEntityPosX, currentClientEntityPosY, currentClientEntityPosZ);
    }

    public static void RemoveEntity(Entity a) {
        a.remove(Entity.RemovalReason.DISCARDED);
    }

    public static Entity getEntityByIntID(World level, int id) {
        if (id < 0) { return null; }
        return level.getEntityById(id);
    }

    public static void setEntityPos(Entity a, Vector3f pos) {
        a.setPosition(Vector3fMethods.convertVector3fToMCVec3(pos));
    }

    public static void setEntityPosXYZ(Entity a, double x1, double y1, double z1) {
        a.setPosition(x1, y1, z1);
    }

    public static void moveEntityToPos(Entity a, Vector3f pos) {
        a.refreshPositionAfterTeleport(Vector3fMethods.convertVector3fToMCVec3(pos));
    }

    public static void moveEntityToXYZ(Entity a, double x1, double y1, double z1) {
        a.refreshPositionAfterTeleport(x1, y1, z1);
    }

    public static void moveEntityToXYZRotation(Entity a, double x1, double y1, double z1, float horizontalFacingDeg, float verticalFacingDeg) {
        a.refreshPositionAndAngles(x1, y1, z1, horizontalFacingDeg, verticalFacingDeg);
    }

    public static void tpEntityToPos(Entity a, Vector3f pos) { a.requestTeleport(pos.x(), pos.y(), pos.z()); }
    public static void tpEntityToPos(Entity a, float x1, float y1, float z1) { a.requestTeleport(x1, y1, z1); }
//    public static void AdvanceTeleportEntityToXYZFacing(Entity a, double pX, double pY, double pZ, float HorizontalFacingAngle, float VerticalFacingAngle) {
//        ServerLevel serverLevel = getEntityServerLevel(a);
//        if (serverLevel != null) {
//            a.teleportTo(serverLevel, pX, pY, pZ, RelativeMovement.ALL, HorizontalFacingAngle, VerticalFacingAngle);
//        }
//    }

    public static boolean getEntityIsAiActive(Entity a) {
        if (a instanceof LivingEntity b) {
            return b.isLogicalSideForUpdatingMovement();
        }
        else {
            return false;
        }
    }

    public static boolean getEntityIsAiActive(LivingEntity a) {
        return a.isLogicalSideForUpdatingMovement();
    }

    public static World getEntityLevel(Entity a) { return a.getWorld(); }
    public static boolean getEntityLevelIsClient(Entity a) { return getEntityLevel(a).isClient; }
//    public static ServerLevel getEntityServerLevel(Entity a) {
//        if (getEntityLevel(a) instanceof ServerLevel serverLevel) { return serverLevel; }
//        return null;
//    }

    public static float getEntityDeltaX(Entity a) { return getEntityDeltaMovement(a).x(); }
    public static float getEntityDeltaY(Entity a) { return getEntityDeltaMovement(a).y(); }
    public static float getEntityDeltaZ(Entity a) { return getEntityDeltaMovement(a).z(); }

    public static float getEntityX(Entity a) { return (float) a.getX(); }
    public static float getEntityY(Entity a) { return (float) a.getY(); }
    public static float getEntityZ(Entity a) { return (float) a.getZ(); }

    public static float getEntityPreviousX(Entity a) { return (float) a.prevX; }
    public static float getEntityPreviousY(Entity a) { return (float) a.prevY; }
    public static float getEntityPreviousZ(Entity a) { return (float) a.prevZ; }

    public static float getEntityRegistryDefinedHeight(Entity a) { return a.getHeight(); }

    public static float calculatePushEntityStrength(float strength, LivingEntity dealer, LivingEntity target) {
//        float dealerAttackKnockBack = 1f + (float) getEntityAttackKnockBack(dealer);
//        float currentStrength = strength * dealerAttackKnockBack;
//        float targetKnockBackResistance = currentStrength * (1f - (float) getEntityKnockBackResistance(target));
//        return (targetKnockBackResistance > 0) ? targetKnockBackResistance : 0;
        if (SBSAttributeMethods.getSuperArmorAttrVal(target) > 0 || strength <= 0) { return 0; }
        return strength * (1f + (float) getEntityAttackKnockBack(dealer)) * (1f - (float) getEntityKnockBackResistance(target));
    }

    public static float calculatePushEntityStrengthTargetOnly(float strength, LivingEntity target) {
//        float targetKnockBackResistance = strength * (1f - (float) getEntityKnockBackResistance(target));
//        return (targetKnockBackResistance > 0) ? targetKnockBackResistance : 0;
        if (SBSAttributeMethods.getSuperArmorAttrVal(target) > 0 || strength <= 0) { return 0; }
        return strength * (1f - (float) getEntityKnockBackResistance(target));
    }

    public static void pushEntityWithDeltaValue(LivingEntity entity, float horizontalDegree, float XZtotalDist, float YDist, boolean isAddedVec) {
        if (!getEntityIsAiActive(entity) || (isAddedVec && XZtotalDist == 0 && YDist == 0)) { return; }

        float newX;
//        float newY;
        float newZ;
        if (XZtotalDist != 0) {
            newX = Vector3fMethods.PolarProjectionFromXHorizon(XZtotalDist, horizontalDegree);
            newZ = Vector3fMethods.PolarProjectionFromZHorizon(XZtotalDist, horizontalDegree);
        }
        else {
            newX = 0;
            newZ = 0;
        }
//        newY = YDist;

        if (entity instanceof ServerPlayerEntity sp1) {
            //update client
            DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, newX, YDist, newZ, isAddedVec);
        }
        if (isAddedVec) {
//            printInGameMsg("pushedStr: "+XZtotalDist);
//            printInGameMsg("pushed: "+newX);
//            printInGameMsg("target delta: " + getEntityDeltaMovement(entity));
            setEntityDeltaMovement(entity, getEntityDeltaMovement(entity).add(newX, YDist, newZ));
        }
        else {
            setEntityDeltaMovement(entity, newX, YDist, newZ);
        }
    }

    public static void pushEntityWithDeltaValueXYZ(LivingEntity entity, float x1, float y1, float z1, boolean isAddedVec) {
        if (!getEntityIsAiActive(entity) || isAddedVec && x1 == 0 && y1 == 0 && z1 == 0) { return; }

        if (entity instanceof ServerPlayerEntity sp1) {
            //update client
            DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, x1, y1, z1, isAddedVec);
        }
        if (isAddedVec) {
            setEntityDeltaMovement(entity, getEntityDeltaMovement(entity).add(x1, y1, z1));
        }
        else {
            setEntityDeltaMovement(entity, x1, y1, z1);
        }
    }

    public static void pushEntityWithDeltaVector(LivingEntity entity, Vector3f vector3f, boolean isAddedVec) {
        float x1 = vector3f.x();
        float y1 = vector3f.y();
        float z1 = vector3f.z();
        if (!getEntityIsAiActive(entity) || isAddedVec && x1 == 0 && y1 == 0 && z1 == 0) { return; }

        if (entity instanceof ServerPlayerEntity sp1) {
            //update client
            DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, x1, y1, z1, isAddedVec);
        }
        if (isAddedVec) {
            setEntityDeltaMovement(entity, getEntityDeltaMovement(entity).add(x1, y1, z1));
        }
        else {
            setEntityDeltaMovement(entity, x1, y1, z1);
        }
    }

    public static void setEntityDeltaMovement(LivingEntity a, float x1, float y1, float z1) {
//        a.setDeltaMovement(Vector3fMethods.convertVector3fToMCVec3(new Vector3f(x1, y1, z1)));
        a.setVelocity(x1, y1, z1);
    }

    public static void setEntityDeltaMovement(LivingEntity a, Vector3f vector) {
        a.setVelocity(Vector3fMethods.convertVector3fToMCVec3(vector));
    }

    public static void setEntitySPDeltaMovement(LivingEntity a, float x1, float y1, float z1, boolean isAdded) {
//        a.setDeltaMovement(Vector3fMethods.convertVector3fToMCVec3(new Vector3f(x1, y1, z1)));
        if (isAdded) {
            setEntityDeltaMovement(a, getEntityDeltaMovement(a).add(x1, y1, z1));
        }
        else {
            setEntityDeltaMovement(a, x1, y1, z1);
        }
        if (a instanceof ServerPlayerEntity sp1) {
            //update client
            DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, x1, y1, z1, isAdded);
        }
    }

    public static void setEntitySPDeltaMovement(LivingEntity a, Vector3f vector, boolean isAdded) {
        setEntitySPDeltaMovement(a, vector.x(), vector.y(), vector.z(), isAdded);
    }

    public static Vector3f getEntityDeltaMovement(LivingEntity a) {
        return Vector3fMethods.convertMCVec3fToVector3f(a.getVelocity());
    }

    public static void pushEntityWithDeltaValue(Entity entity, float horizontalDegree, float XZtotalDist, float YDist, boolean isAddedVec) {
        if (!getEntityIsAiActive(entity) || (isAddedVec && XZtotalDist == 0 && YDist == 0)) { return; }

        float newX;
//        float newY;
        float newZ;
        if (XZtotalDist != 0) {
            newX = Vector3fMethods.PolarProjectionFromXHorizon(XZtotalDist, horizontalDegree);
            newZ = Vector3fMethods.PolarProjectionFromZHorizon(XZtotalDist, horizontalDegree);
        }
        else {
            newX = 0;
            newZ = 0;
        }
//        newY = YDist;

        if (entity instanceof ServerPlayerEntity sp1) {
            //update client
            DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, newX, YDist, newZ, isAddedVec);
        }
        if (isAddedVec) {
//            printInGameMsg("pushedStr: "+XZtotalDist);
//            printInGameMsg("pushed: "+newX);
//            printInGameMsg("target delta: " + getEntityDeltaMovement(entity));
            setEntityDeltaMovement(entity, getEntityDeltaMovement(entity).add(newX, YDist, newZ));
        }
        else {
            setEntityDeltaMovement(entity, newX, YDist, newZ);
        }
    }

    public static void pushEntityWithDeltaValueXYZ(Entity entity, float x1, float y1, float z1, boolean isAddedVec) {
        if (!getEntityIsAiActive(entity) || isAddedVec && x1 == 0 && y1 == 0 && z1 == 0) { return; }

        if (entity instanceof ServerPlayerEntity sp1) {
            //update client
            DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, x1, y1, z1, isAddedVec);
        }
        if (isAddedVec) {
            setEntityDeltaMovement(entity, getEntityDeltaMovement(entity).add(x1, y1, z1));
        }
        else {
            setEntityDeltaMovement(entity, x1, y1, z1);
        }
    }

    public static void pushEntityWithDeltaVector(Entity entity, Vector3f vector3f, boolean isAddedVec) {
        float x1 = vector3f.x();
        float y1 = vector3f.y();
        float z1 = vector3f.z();
        if (!getEntityIsAiActive(entity) || isAddedVec && x1 == 0 && y1 == 0 && z1 == 0) { return; }

        if (entity instanceof ServerPlayerEntity sp1) {
            //update client
            DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, x1, y1, z1, isAddedVec);
        }
        if (isAddedVec) {
            setEntityDeltaMovement(entity, getEntityDeltaMovement(entity).add(x1, y1, z1));
        }
        else {
            setEntityDeltaMovement(entity, x1, y1, z1);
        }
    }

    public static void setEntityDeltaMovement(Entity a, float x1, float y1, float z1) {
//        a.setVelocity(Vector3fMethods.convertVector3fToMCVec3(new Vector3f(x1, y1, z1)));
        a.setVelocity(x1, y1, z1);
    }

    public static void setEntityDeltaMovement(Entity a, Vector3f vector) {
        a.setVelocity(Vector3fMethods.convertVector3fToMCVec3(vector));
    }

    public static void setEntitySPDeltaMovement(Entity a, float x1, float y1, float z1, boolean isAdded) {
//        a.setVelocity(Vector3fMethods.convertVector3fToMCVec3(new Vector3f(x1, y1, z1)));
        if (isAdded) {
            setEntityDeltaMovement(a, getEntityDeltaMovement(a).add(x1, y1, z1));
        }
        else {
            setEntityDeltaMovement(a, x1, y1, z1);
        }
        if (a instanceof ServerPlayerEntity sp1) {
            //update client
            DfoSwdNetworkingFetchMsgMethods.FetchPlayerDeltaFromServer(sp1, x1, y1, z1, isAdded);
        }
    }

    public static void setEntitySPDeltaMovement(Entity a, Vector3f vector, boolean isAdded) {
        setEntitySPDeltaMovement(a, vector.x(), vector.y(), vector.z(), isAdded);
    }

    public static Vector3f getEntityDeltaMovement(Entity a) {
        return Vector3fMethods.convertMCVec3fToVector3f(a.getVelocity());
    }

//    public void ResetDeltaMovementLivingEntity(LivingEntity a, float stunTime) {
//        a.xxa = 0.0F;
//        a.yya = 0.0F;
//        a.zza = 0.0F;
//        setEntityDeltaMovement(a, 0.0f, getEntityDeltaY(a), 0.0f);
//
//        //future plan: apply animation when stun if the entity has the animation defined?
//        //...
//    }

    public static boolean EntityDoesNotAffectByDeltaMovementMethod(Entity a) {
//        try
//        {
//            Class<? extends Object> clazz = a.getClass();
//
//            return clazz.getMethod(DfoSwordmanSkillTreeConstants.ENTITY_FORGE_DELTA_METHOD_NAME).getDeclaringClass().equals(clazz);
//        }
//        catch (Exception ignored) {
//
//        }

        Vector3f tempEntityDeltaVec = getEntityDeltaMovement(a);
        setEntityDeltaMovement(a ,1, 1, 1);
        Vector3f testEntityDeltaVec = getEntityDeltaMovement(a);
        if (testEntityDeltaVec.equals(tempEntityDeltaVec)) {
            return false;
        }
        else {
            setEntityDeltaMovement(a, tempEntityDeltaVec);
            return true;
        }
    }

    public static double AngleFromEntityToEntity(Entity a, Entity b) {
//        return RADTODEG * Math.atan2(getEntityZ(b)-getEntityZ(a), getEntityX(b)-getEntityX(a));
        return MathMethods.RAD_TO_DEG * Math.atan2(getEntityZ(b)-getEntityZ(a), getEntityX(b)-getEntityX(a))-90;
    }

    public static double DistanceBetweenEntitiesHorizontal(Entity a, Entity b) {
        double dx = getEntityX(b)-getEntityX(a);
        double dz = getEntityZ(b)-getEntityZ(a);
        return MathMethods.sqrt((float) (dx*dx+dz*dz));
    }

    public static float DistanceBetweenEntities(Entity a, Entity b) {
//        return a.distanceTo(b);
//        return getEntityPos(a).distance(getEntityPos(b));
        float newX = getEntityX(a) - getEntityX(b);
        float newY = getEntityY(a) - getEntityY(b);
        float newZ = getEntityZ(a) - getEntityZ(b);
        return MathMethods.sqrt(newX * newX + newY * newY + newZ * newZ);
    }

    //TODO: concurrent Exception
    public static void addEntityToWorld(World worldLevel, Entity entity) {
        worldLevel.spawnEntity(entity);
    }

//    public static void GenerateBeneficialParticleAtEntityPos(LivingEntity livingEntity) {
//        Vector3f entityPos = getEntityPos(livingEntity), deltaMov = getEntityDeltaMovement(livingEntity);
//        Vector3f particlePos, particleDeltaPos;
//        for (int i = 0; i < 5; i++) {
//            particlePos = Vector3fMethods.randomVecIn2DCircle(1f, entityPos);
////            particleDeltaPos = Vector3fMethods.PolarProjectionFromVecHorizon(particlePos, 0.2f, Vector3fMethods.AngleFromLocationToLocationHorizontal(entityPos, particlePos)).sub(particlePos);
//            particleDeltaPos = Vector3fMethods.PolarProjectionFromVecHorizon(particlePos, 0.015f, Vector3fMethods.AngleFromLocationToLocationHorizontal(entityPos, particlePos)).sub(particlePos);
//            ParticleMethods.GenerateBeneficialBuffParticle(particlePos.x(), particlePos.y()+0.15f, particlePos.z(),
//                    deltaMov.x()+particleDeltaPos.x(), deltaMov.y()+0.18f, deltaMov.z()+particleDeltaPos.z());
//        }

//        SpecialEffEntitySpawnMethods.GenerateEffEntityHelper(livingEntity, ParticleRegistry.BENEFICIAL_PARTICLE, 4, 0, 0, 0, true, livingEntity);


//        GeoModelSpawnMethods.GenerateEffBuffAltEntity(livingEntity, "textures/entity/skilleff/common/buff/plus/", "buff_moving_up", 3f, 20, 0, 0, 0, false, null);
//    }


    //==============================
    //MC default attr
    public static double getEntityAttackSpeed(LivingEntity entity) {
        EntityAttributeInstance a = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
        if (a != null) {
//            printInGameMsg("dmg: "+a.getValue());
            return a.getValue();
        }
        else {
            return 0.0;
        }
    }

    public static double getEntityMovementSpeed(LivingEntity entity) {
        EntityAttributeInstance a = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (a != null) {
//            printInGameMsg("dmg: "+a.getValue());
            return a.getValue();
        }
        else {
            return 0.0;
        }
    }

    public static double getEntityAttackDamage(LivingEntity entity) {
        EntityAttributeInstance a = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (a != null) {
            return a.getValue();
        }
        else {
            return 0.0;
        }
    }

    public static double getEntityArmor(LivingEntity entity) {
        EntityAttributeInstance a = entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        if (a != null) {
            return a.getValue();
        }
        else {
            return 0.0;
        }
    }

    public static double getEntityAttackKnockBack(LivingEntity entity) {
        EntityAttributeInstance a = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
        if (a != null) {
            return a.getValue();
        }
        else {
            return 0.0;
        }
    }

    public static double getEntityKnockBackResistance(LivingEntity entity) {
        EntityAttributeInstance a = entity.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
        if (a != null) {
            return a.getValue();
        }
        else {
            return 0.0;
        }
    }
}
