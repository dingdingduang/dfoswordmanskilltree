package net.dingdingduang.dfoswordmanskilltree.globalmethods;

import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import org.joml.Vector3f;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods.PolarProjectionFromRotation;
import static net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods.PolarProjectionFromVecHorizon;

public class EntityGroupMethods {
//    private static int GetGroupEntitiesMethodGotCalledPerServerTickTimes = 0;
    private final static ArrayList<Entity> emptyList = new ArrayList<Entity>();

    private static ConcurrentHashMap<Entity, Byte> currentEntitiesInClientLevel = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Entity, Byte> currentEntitiesInServerLevel = new ConcurrentHashMap<>();

//    public static int getGetGroupEntitiesMethodGotCalledPerServerTickTimes() {
//        return GetGroupEntitiesMethodGotCalledPerServerTickTimes;
//    }
//
//    public static void setGetGroupEntitiesMethodGotCalledPerServerTickTimes(int getGroupEntitiesMethodGotCalledPerServerTickTimes) {
//        GetGroupEntitiesMethodGotCalledPerServerTickTimes = getGroupEntitiesMethodGotCalledPerServerTickTimes;
//    }

    public static AABB getAABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new AABB(x1, y1, z1, x2, y2, z2);
    }

    //TODO concurrentexception
//    public static List<Entity> getEntitiesInRect(Entity pEntity, AABB pBoundingBox, Predicate<? super Entity> pPredicate) {
////        if (pEntity == null) {
////            return emptyList;
////        }
////        return getEntityLevel(pEntity).getEntities(pEntity, pBoundingBox, pPredicate);
//        try {
//            if (pEntity == null) {
//                return emptyList;
//            }
//            return getEntityLevel(pEntity).getEntities(pEntity, pBoundingBox, pPredicate);
//        }
//        catch (ConcurrentModificationException e) {
//            System.out.println("concurrent modification exception" + e);
//        }
//
//        return emptyList;
//    }

    public static List<Entity> getEntitiesInRect(Entity pEntity, AABB pBoundingBox, Predicate<? super Entity> pPredicate) {
        if (pEntity == null) { return emptyList; }
        List<Entity> tempList = new ArrayList<>();
    //        try {
    //            int ownerID = getEntityHashID(pEntity);
    //            for (Entity tempEntity: currentEntitiesInServerLevel.values()) {
    //                if (getEntityHashID(tempEntity) != ownerID && pBoundingBox.intersects(tempEntity.getBoundingBox()) && pPredicate.test(tempEntity)) {
    //                    tempList.add(tempEntity);
    //                }
    //            }
    //        }
    //        catch (ConcurrentModificationException e) {
    //            System.out.println("concurrent modification exception" + e);
    //        }
        int ownerID = getEntityHashID(pEntity);
        for (Entity tempEntity: ((getEntityLevelIsClient(pEntity)) ? currentEntitiesInClientLevel: currentEntitiesInServerLevel).keySet()) {
            if (getEntityHashID(tempEntity) != ownerID && pBoundingBox.intersects(tempEntity.getBoundingBox()) && pPredicate.test(tempEntity)) {
                tempList.add(tempEntity);
            }
        }

        return tempList;
    }

    public static HashSet<Entity> getHashSetEntitiesInRect(Entity pEntity, AABB pBoundingBox, Predicate<? super Entity> pPredicate) {
        HashSet<Entity> tempList = new HashSet<>();

        int ownerID = getEntityHashID(pEntity);
        for (Entity tempEntity: currentEntitiesInServerLevel.keySet()) {
            if (getEntityHashID(tempEntity) != ownerID && pBoundingBox.intersects(tempEntity.getBoundingBox()) && pPredicate.test(tempEntity)) {
                tempList.add(tempEntity);
            }
        }

        return tempList;
    }

    public static List<Entity> getEntitiesInRect(Level worldLevel, AABB pBoundingBox, Predicate<? super Entity> pPredicate) {
        try {
            return worldLevel.getEntities((Entity) null, pBoundingBox, pPredicate);
        }
        catch (ConcurrentModificationException e) {
            System.out.println("concurrent modification exception" + e);
        }

        return emptyList;
    }

    //ConcurrentModificationException, not sure how to fix,
    //for some mysterious reason if a lot mob in target zone, the game will crash
    //pretty sure I didn't add/remove element from the list or iterator while it being iterated

    //option1 CopyOnWriteArrayList collection synchronize list, option2 concurrent hashmap

    public static void MoveAllEntitiesInEntityGroup(List<Entity> a, Vector3f b, boolean resetDelta) {
        for (Entity tempEntity: a) {
            if (tempEntity == null) { continue; }
            if (tempEntity instanceof LivingEntity tempLivingEntity) {

                moveEntityToPos(tempLivingEntity, b);
                if (resetDelta) {
                    setEntityDeltaMovement(tempLivingEntity, 0, 0, 0);
                }
            }
        }
    }

    public static void MoveAllEntitiesInEntityGroupHorizon(List<Entity> a, float dist, float horizontalDeg, boolean resetDelta) {
        for (Entity tempEntity: a) {
            if (tempEntity == null) { continue; }
            if (tempEntity instanceof LivingEntity tempLivingEntity) {

                moveEntityToPos(tempLivingEntity, PolarProjectionFromVecHorizon(getEntityPos(tempLivingEntity), dist, horizontalDeg));
                if (resetDelta) {
                    setEntityDeltaMovement(tempLivingEntity, 0, 0, 0);
                }
            }
        }
    }

    public static void MoveAllEntitiesInEntityGroupFromRotation(List<Entity> a, float dist, float horizontalDeg, float verticalDeg, boolean resetDelta) {
        for (Entity tempEntity: a) {
            if (tempEntity == null) { continue; }
            if (tempEntity instanceof LivingEntity tempLivingEntity) {

                moveEntityToPos(tempLivingEntity, PolarProjectionFromRotation(getEntityPos(tempLivingEntity), dist, horizontalDeg, verticalDeg));
                if (resetDelta) {
                    setEntityDeltaMovement(tempLivingEntity, 0, 0, 0);
                }
            }
        }
    }

    //generate simple square zone 1x1 3x3 5x5?
    //use entity's distanceTo method if you want circular zone
    public static List<Entity> GroupEnumEntitiesInRange(Entity loc, double range) {
        double x1 = getEntityX(loc) - range;
        double y1 = getEntityY(loc) - 1;
//        double y1 = getEntityY(loc) - range;
        double z1 = getEntityZ(loc) - range;
        double x2 = getEntityX(loc) + range;
//        double y2 = getEntityY(loc) + range;
        double y2 = getEntityY(loc) + 2;
        double z2 = getEntityZ(loc) + range;
//        AABB rectRange = new AABB(x1, y1, z1, x2, y2, z2);

//        return getServerLevel().getEntities(loc, rectRange);
        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
        return getEntitiesInRect(loc, getAABB(x1, y1, z1, x2, y2, z2), tempPredicate01);
    }

    //rectangle zone
    public static List<Entity> GroupEnumEntitiesInRange(Entity loc, double x0, double y0, double z0) {
        double x1 = getEntityX(loc) - x0;
        double y1 = getEntityY(loc) - y0;
        double z1 = getEntityZ(loc) - z0;
        double x2 = getEntityX(loc) + x0;
        double y2 = getEntityY(loc) + y0;
        double z2 = getEntityZ(loc) + z0;
//        AABB rectRange = new AABB(x1, y1, z1, x2, y2, z2);

//        return getServerLevel().getEntities(loc, rectRange);
        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
        return getEntitiesInRect(loc, getAABB(x1, y1, z1, x2, y2, z2), tempPredicate01);
    }

    //customize rectangle zone if you want
    public static List<Entity> GroupEnumEntitiesInRange(Entity loc, double x1, double y1, double z1, double x2, double y2, double z2) {
        double new_x1 = getEntityX(loc) - x1;
        double new_y1 = getEntityY(loc) - y1;
        double new_z1 = getEntityZ(loc) - z1;
        double new_x2 = getEntityX(loc) + x2;
        double new_y2 = getEntityY(loc) + y2;
        double new_z2 = getEntityZ(loc) + z2;
//        AABB rectRange = new AABB(new_x1, new_y1, new_z1, new_x2, new_y2, new_z2);

//        return getServerLevel().getEntities(loc, rectRange);
        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
        return getEntitiesInRect(loc, getAABB(new_x1, new_y1, new_z1, new_x2, new_y2, new_z2), tempPredicate01);
    }

    public static List<Entity> GroupEnumEntitiesInRange(Entity loc, AABB rect) {
//        return getServerLevel().getEntities(loc, rect);
        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
        return getEntitiesInRect(loc, rect, tempPredicate01);
    }

    //generate simple square zone 1x1 3x3 5x5?
    //use entity's distanceTo method if you want circular zone
    //example using this method:
    //List<Entity> tempEntityGroup = GroupEnumEntitiesInRangeFilter(center, range, SBConditions::isLivingEntity);
    //List<Entity> tempEntityGroup = GroupEnumEntitiesInRangeFilter(center, range, entity -> isLivingEntity(entity) && isAttackableEntity(entity) );
    //circular with distanceTo method
    //List<Entity> tempEntityGroup = GroupEnumEntitiesInRangeFilter(center, range, entity -> isEnemyEntity(entity) && (entity.distanceTo(Entity000) <= radius) );
    public static List<Entity> GroupEnumEntitiesInRangeFilter(Entity loc, double range, Predicate<? super Entity> pFilter) {
        double x1 = getEntityX(loc) - range;
        double y1 = getEntityY(loc) - 1;
//        double y1 = getEntityY(loc) - range;
        double z1 = getEntityZ(loc) - range;
        double x2 = getEntityX(loc) + range;
        double y2 = getEntityY(loc) + 3.5;
//        double y2 = getEntityY(loc) + range;
        double z2 = getEntityZ(loc) + range;
//        AABB rectRange = new AABB(x1, y1, z1, x2, y2, z2);

//        return getServerLevel().getEntities(loc, rectRange, pFilter);
        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
        return getEntitiesInRect(loc, getAABB(x1, y1, z1, x2, y2, z2), tempPredicate01.and(pFilter));
    }

    public static HashSet<Entity> HashSetGroupEnumEntitiesInRangeFilter(Entity loc, double range, Predicate<? super Entity> pFilter) {
        double x1 = getEntityX(loc) - range;
        double y1 = getEntityY(loc) - 1;
//        double y1 = getEntityY(loc) - range;
        double z1 = getEntityZ(loc) - range;
        double x2 = getEntityX(loc) + range;
        double y2 = getEntityY(loc) + 3.5;
//        double y2 = getEntityY(loc) + range;
        double z2 = getEntityZ(loc) + range;
//        AABB rectRange = new AABB(x1, y1, z1, x2, y2, z2);

//        return getServerLevel().getEntities(loc, rectRange, pFilter);
        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
        return getHashSetEntitiesInRect(loc, getAABB(x1, y1, z1, x2, y2, z2), tempPredicate01.and(pFilter));
    }

    public static HashSet<Entity> HashSetGroupEnumEntitiesInRangeFilter(Entity owner, Vector3f damageCenter, double xRadius, double yRadius, double zRadius, Predicate<? super Entity> pFilter) {
        double x1 = damageCenter.x() - xRadius;
        double y1 = damageCenter.y() - yRadius;
        double z1 = damageCenter.z() - zRadius;
        double x2 = damageCenter.x() + xRadius;
        double y2 = damageCenter.y() + yRadius;
        double z2 = damageCenter.z() + zRadius;

        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
        return getHashSetEntitiesInRect(owner, getAABB(x1, y1, z1, x2, y2, z2), tempPredicate01.and(pFilter));
    }

    //rectangle zone
    public static List<Entity> GroupEnumEntitiesInRangeFilter(Entity loc, double x0, double y0, double z0, Predicate<? super Entity> pFilter) {
        double x1 = getEntityX(loc) - x0;
        double y1 = getEntityY(loc) - y0;
        double z1 = getEntityZ(loc) - z0;
        double x2 = getEntityX(loc) + x0;
        double y2 = getEntityY(loc) + y0;
        double z2 = getEntityZ(loc) + z0;
//        AABB rectRange = new AABB(x1, y1, z1, x2, y2, z2);

//        return getServerLevel().getEntities(loc, rectRange, pFilter);
        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
        return getEntitiesInRect(loc, getAABB(x1, y1, z1, x2, y2, z2), tempPredicate01.and(pFilter));
    }

    //customize rectangle zone if you want
    public static List<Entity> GroupEnumEntitiesInRangeFilter(Entity loc, double x1, double y1, double z1, double x2, double y2, double z2, Predicate<? super Entity> pFilter) {
        double new_x1 = getEntityX(loc) - x1;
        double new_y1 = getEntityY(loc) - y1;
        double new_z1 = getEntityZ(loc) - z1;
        double new_x2 = getEntityX(loc) + x2;
        double new_y2 = getEntityY(loc) + y2;
        double new_z2 = getEntityZ(loc) + z2;
//        AABB rectRange = new AABB(new_x1, new_y1, new_z1, new_x2, new_y2, new_z2);

//        return getServerLevel().getEntities(loc, rectRange, pFilter);
        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
        return getEntitiesInRect(loc, getAABB(new_x1, new_y1, new_z1, new_x2, new_y2, new_z2), tempPredicate01.and(pFilter));
    }

    //customize rectangle zone if you want
//    public static List<Entity> GroupEnumEntitiesInRangeFilterWithCenter(Entity owner, Vector3f center, double x1, double y1, double z1, double x2, double y2, double z2, Predicate<? super Entity> pFilter) {
//        double new_x1 = center.x() - x1;
//        double new_y1 = center.y() - y1;
//        double new_z1 = center.z() - z1;
//        double new_x2 = center.x() + x2;
//        double new_y2 = center.y() + y2;
//        double new_z2 = center.z() + z2;
////        AABB rectRange = new AABB(new_x1, new_y1, new_z1, new_x2, new_y2, new_z2);
//
////        return getServerLevel().getEntities(loc, rectRange, pFilter);
//        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
//        return getEntitiesInRect(owner, getAABB(new_x1, new_y1, new_z1, new_x2, new_y2, new_z2), tempPredicate01.and(pFilter));
//    }

    public static List<Entity> GroupEnumEntitiesInRangeFilter(Entity loc, AABB rect, Predicate<? super Entity> pFilter) {
//        return getServerLevel().getEntities(loc, rect, pFilter);
        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
        return getEntitiesInRect(loc, rect, tempPredicate01.and(pFilter));
    }

    public static List<Entity> GroupEnumEntitiesInPolarProjectionRangeFilter(Entity loc, double range, Predicate<? super Entity> pFilter, float projectionDist, boolean isHorizonOnly) {
        Vector3f vec3;
        if (isHorizonOnly) {
            vec3 = Vector3fMethods.PolarProjectionFromVecHorizon(getEntityPos(loc), projectionDist, getEntityHorizontalFacingDeg(loc));
        }
        else {
            vec3 = Vector3fMethods.PolarProjectionFromRotation(getEntityPos(loc), projectionDist, getEntityHorizontalFacingDeg(loc), getEntityVerticalFacingDeg(loc));
        }
        double x1 = vec3.x() - range;
        double y1 = vec3.y() - 1;
        double z1 = vec3.z() - range;
        double x2 = vec3.x() + range;
        double y2 = vec3.y() + 2;
        double z2 = vec3.z() + range;
//        AABB rectRange = new AABB(x1, y1, z1, x2, y2, z2);

//        return getServerLevel().getEntities(loc, rectRange, pFilter);
        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
        return getEntitiesInRect(loc, getAABB(x1, y1, z1, x2, y2, z2), tempPredicate01.and(pFilter));
    }

    public static ConcurrentHashMap<Entity, Byte> getCurrentEntitiesInServerLevel() { return currentEntitiesInServerLevel; }
    public static void setCurrentEntitiesInServerLevel(ConcurrentHashMap<Entity, Byte> CurrentEntitiesInServerLevel) { currentEntitiesInServerLevel = CurrentEntitiesInServerLevel; }
    public static ConcurrentHashMap<Entity, Byte> getCurrentEntitiesInClientLevel() { return currentEntitiesInClientLevel; }
    public static void setCurrentEntitiesInClientLevel(ConcurrentHashMap<Entity, Byte> CurrentEntitiesInClientLevel) { currentEntitiesInClientLevel = CurrentEntitiesInClientLevel; }

    //Vector 3f
//    public static List<Entity> GroupEnumEntitiesInRangeFilter(Vector3f loc, double range, Predicate<? super Entity> pFilter) {
//        double x1 = loc.x() - range;
//        double y1 = loc.y() - 1;
//        double z1 = loc.z() - range;
//        double x2 = loc.x() + range;
//        double y2 = loc.y() + 2;
//        double z2 = loc.z() + range;
//        AABB rectRange = new AABB(x1, y1, z1, x2, y2, z2);
////        printInGameMsg(String.format("x %f, y %f, z %f", x1,y1,z1));
////        return getServerLevel().getEntities(loc, rectRange, pFilter);
//        Predicate<Entity> tempPredicate01 = (e1) -> { return !isEntitySpectator(e1); };
//        return getEntitiesInRect(null, rectRange, tempPredicate01.and(pFilter));
//    }
}
