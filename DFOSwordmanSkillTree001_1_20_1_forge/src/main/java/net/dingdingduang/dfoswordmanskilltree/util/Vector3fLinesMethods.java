package net.dingdingduang.dfoswordmanskilltree.util;

import net.minecraft.world.entity.Entity;

import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityPos;

public class Vector3fLinesMethods {
    public static Vector3f getDirectionPoint(Vector3f startP, Vector3f endP) {
        return new Vector3f(endP).sub(startP);
    }

    public static Vector3f getNormalizedDirectionPoint(Vector3f startP, Vector3f endP) {
        return getDirectionPoint(startP, endP).normalize();
    }

    public static Vector3f findClosestPointOnLine(Vector3f A, Vector3f NormalizedDirectionVec, Vector3f Q) {
        //Vector from A to Q
        Vector3f AQ = new Vector3f(Q).sub(A);

        //Compute scalar projection t
        float t = AQ.dot(NormalizedDirectionVec);

        return new Vector3f(A).add(NormalizedDirectionVec.mul(t, new Vector3f()));
    }

    public static Vector3f findClosestVectorToLine(Vector3f A, Vector3f NormalizedDirectionVec, List<Vector3f> points) {
        Vector3f closestVector = null;
        float minDistance = Float.MAX_VALUE;

        for (Vector3f point : points) {
            //Find the closest point on the line to this vector
            Vector3f closestPoint = findClosestPointOnLine(A, NormalizedDirectionVec, point);

            //Compute squared distance to avoid sqrt()
            float distanceSquared = point.distanceSquared(closestPoint);

            //Update if this point is the closest so far
            if (distanceSquared < minDistance) {
                minDistance = distanceSquared;
                closestVector = point;
            }
        }

        return closestVector;
    }

    public static Vector3f findClosestVectorToLine(Vector3f StartP, Vector3f EndP, List<Entity> entities, float minDistance) {
        Vector3f closestVector = null;
        Vector3f NormalizedDirectionVec = getDirectionPoint(EndP, StartP).normalize();

        for (Entity entity: entities) {
            Vector3f point = getEntityPos(entity);

            //Find the closest point on the line to this vector
            Vector3f closestPoint = findClosestPointOnLine(StartP, NormalizedDirectionVec, point);

            //Compute squared distance to avoid sqrt()
            float distanceSquared = point.distanceSquared(closestPoint);

            //Update if this point is the closest so far
            if (distanceSquared < minDistance) {
                minDistance = distanceSquared;
                closestVector = point;
            }
        }

        return closestVector;
    }

    public static LivingEntity findClosestVectorToLineWithLivingEntity(LivingEntity triggeredEntity, Vector3f StartP, Vector3f EndP, Set<Entity> entities, float preMinDistance, Predicate<Entity> condition) {
        LivingEntity closestLivingEntity = null;
        float minDistance = preMinDistance;
        Vector3f NormalizedDirectionVec = getDirectionPoint(EndP, StartP).normalize();

        for (Entity entity: entities) {
            if (entity instanceof LivingEntity livingEntity && triggeredEntity.hasLineOfSight(livingEntity) && condition.test(livingEntity)) {
                Vector3f point = getEntityPos(livingEntity);

                //Find the closest point on the line to this vector
                Vector3f closestPoint = findClosestPointOnLine(StartP, NormalizedDirectionVec, point);

                float distance = Vector3fMethods.Vec3fDistanceTo(point, closestPoint);
//                printInGameMsg(livingEntity.getName().getString()+", dist: "+distance);

                //Update if this point is the closest so far
                if (distance < minDistance) {
                    minDistance = distance;
                    closestLivingEntity = livingEntity;
                }
            }
        }

        return closestLivingEntity;
    }
}
