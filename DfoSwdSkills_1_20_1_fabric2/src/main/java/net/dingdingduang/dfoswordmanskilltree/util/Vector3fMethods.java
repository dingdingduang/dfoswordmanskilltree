package net.dingdingduang.dfoswordmanskilltree.util;

import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class Vector3fMethods {
    private static final Vector3f OriginVector = new Vector3f(0, 0, 0);
    private static final Vector3f AxisZN = new Vector3f(0, 0, -1);
    private static final Vector3f AxisYP = new Vector3f(0, 1, 0);
    private static final Vector3f AxisXN = new Vector3f(-1, 0, 0);

//    private static final int[] QUADRANT_CLOCKWISE_ARR = new int[]{0, 3, 1, 2};
//    private static final int[][] QUADRANT2SIGN = new int[][]{
////            {1, 1, 1, 1},
////            {-1, -1, -1, -1},
////            {-1, -1, -1, -1},
////            {1, 1, 1, 1}
//            {-1, -1, -1, -1},
//            {-1, -1, -1, -1},
//            {1, 1, 1, 1},
//            {1, 1, 1, 1}
//    };

//    public static int getQuadrantFromPoint(int x1, int y1) { return QUADRANT_CLOCKWISE_ARR[(x1 >>> 31) | ((y1 >>> 30) & 0x2)]; }
//    public static int getQuadrantFromPoint(float x1, float y1) { return QUADRANT_CLOCKWISE_ARR[(Float.floatToRawIntBits(x1) >>> 31) | ((Float.floatToRawIntBits(y1) >>> 30) & 0x2)]; }
//    public static int[] getSignByQuadrant(int index) { return QUADRANT2SIGN[index]; }
//    public static int[] getSignQuadrantByDegree(float deg) { return getSignByQuadrant((int) deg / 90); }

    public static float DistanceBetweenVec3fHorizontal(Vector3f a, Vector3f b) {
//        float dx = b.x() - a.x();
//        float dz = b.z() - a.z();
//        return (float) Math.sqrt( (dx*dx+dz*dz) );

        //Rough + Fast
        float dx = b.x() - a.x();
        float dz = b.z() - a.z();
        return MathMethods.sqrt( (dx*dx+dz*dz) );
    }

    public static boolean IsAngleRadBetweenAB(float angleRad, float A, float B) {
        if(A <= B) {
            if(B - A <= MathMethods.PI) {
                return A <= angleRad && angleRad <= B;
            }
            else {
                return B <= angleRad || angleRad <= A;
            }
        }
        else {
            if(A - B <= MathMethods.PI) {
                return B <= angleRad && angleRad <= A;
            }
            else {
                return A <= angleRad || angleRad <= B;
            }
        }
    }

    public static boolean IsAngleDegBetweenAB(float angleDeg, float A, float B) {
        if(A <= B) {
            if(B - A <= 180f) {
                return A <= angleDeg && angleDeg <= B;
            }
            else {
                return B <= angleDeg || angleDeg <= A;
            }
        }
        else {
            if(A - B <= 180f) {
                return B <= angleDeg && angleDeg <= A;
            }
            else {
                return A <= angleDeg || angleDeg <= B;
            }
        }
    }

    public static float AngleFromLocationToLocationHorizontal(Vector3f a, Vector3f b) {
//        return MathMethods.RAD_TO_DEG * Math.atan2(b.z() - a.z(), b.x() - a.x());
//        return MathMethods.RAD_TO_DEG * Math.atan2(b.z() - a.z(), b.x() - a.x())-90;
//        return MathMethods.RAD_TO_DEG * MathMethods.atan2(b.z() - a.z(), b.x() - a.x())-90;
//        return MathMethods.RoundAngle((float) (MathMethods.RAD_TO_DEG * Math.atan2(b.z() - a.z(), b.x() - a.x())-90) );

        float denominator = b.x() - a.x();
        if (denominator == 0) { denominator =  0.01f; }
        return MathMethods.RoundAngle((float) (MathMethods.RAD_TO_DEG * MathMethods.atan2(b.z() - a.z(), denominator) - 90) );
    }

    public static float AngleFromLocationToLocationHorizontal(float x1, float z1, float x2, float z2) {
        float denominator = x2 - x1;
        if (denominator == 0) { denominator =  0.01f; }
        return MathMethods.RoundAngle((float) (MathMethods.RAD_TO_DEG * MathMethods.atan2(z2 - z1, denominator) - 90) );
    }

    public static double AngleFromLocationToLocationHorizontalNormal(Vector3f a, Vector3f b) {
//        return MathMethods.RAD_TO_DEG * MathMethods.atan2(b.z() - a.z(), b.x() - a.x());
        float denominator = b.x() - a.x();
        if (denominator == 0) { denominator =  0.01f; }
        return MathMethods.RAD_TO_DEG * Math.atan2(b.z() - a.z(), denominator)-90;
    }

//    public static double AngleFromLocationToLocation(float x1, float x2, float y1, float y2) {
//        float denominator = x2 - x1;
//        if (denominator == 0) { denominator =  0.01f; }
////        return MathMethods.RAD_TO_DEG * MathMethods.atan2(y2 - y1, denominator);
//        return MathMethods.RoundAngle((float) (MathMethods.RAD_TO_DEG * MathMethods.atan2(y2 - y1, denominator)));
//    }

    //return verticalDegree in radians
    public static float AngleFromLocationToLocationVertical(Vector3f a, Vector3f b) {
        float sign = (b.y() - a.y()) >= 0 ? -1 : 1;
        float adjacent = DistanceBetweenVec3fHorizontal(a, b);
        float hypotenuse = PreciseDistanceBetweenVecPoints(a, b);
        if (hypotenuse == 0) { return 0; }
        return sign * (float) Math.acos(adjacent / hypotenuse);
    }

    public static float PreciseDistanceBetweenVecPoints(Vector3f a, Vector3f b) {
        return a.distance(b);
    }

    public static Vector3f PolarProjectionFromVecHorizon(Vector3f origin, float dist, float directionDeg) {
//        float newX = origin.x() + dist*( (float) Math.sin(-directionDeg*MathMethods.DEG_TO_RAD));
//        float newZ = origin.z() + dist*( (float) Math.cos(-directionDeg*MathMethods.DEG_TO_RAD));

        //Rough + Fast
        float newX = origin.x() + dist*( MathMethods.sin(-directionDeg*MathMethods.DEG_TO_RAD));
        float newZ = origin.z() + dist*( MathMethods.cos(-directionDeg*MathMethods.DEG_TO_RAD));

        return new Vector3f(newX, origin.y(), newZ);
    }

    public static float PolarProjectionFromXHorizon(float dist, float directionDeg) {
        return dist * MathMethods.sin(-directionDeg*MathMethods.DEG_TO_RAD);
    }

    public static float PolarProjectionFromXHorizonWithOrigin(float originX, float dist, float directionDeg) {
        return originX + dist * MathMethods.sin(-directionDeg*MathMethods.DEG_TO_RAD);
    }

    public static float PolarProjectionFromZHorizon(float dist, float directionDeg) {
        return dist * MathMethods.cos(-directionDeg*MathMethods.DEG_TO_RAD);
    }

    public static float PolarProjectionFromZHorizonWithOrigin(float originZ, float dist, float directionDeg) {
        return originZ + dist * MathMethods.cos(-directionDeg*MathMethods.DEG_TO_RAD);
    }

    public static Vector3f PolarProjectionFromVecHorizon(float dist, float directionDeg) {
        float newX = dist*( MathMethods.sin(-directionDeg*MathMethods.DEG_TO_RAD));
        float newZ = dist*( MathMethods.cos(-directionDeg*MathMethods.DEG_TO_RAD));

        return new Vector3f(newX, 0, newZ);
    }

//    public static Vec3 PolarProjectionFromRotation2(Vec3 origin, double dist, double horizontalRotationDeg, double verticalRotationDeg) {
//        double newX = getVec3X(origin) + dist*Math.sin(-horizontalRotationDeg*MathMethods.DEG_TO_RAD);
//        double newZ = getVec3Z(origin) + dist*Math.cos(-horizontalRotationDeg*MathMethods.DEG_TO_RAD);
//
//        double newY = getVec3Y(origin) + dist*Math.cos(-verticalRotationDeg*MathMethods.DEG_TO_RAD);
//
//        return new Vec3(newX, newY, newZ);
//    }

    public static Vector3f PolarProjectionFromRotation(Vector3f origin, float dist, float horizontalRotationDeg, float verticalRotationDeg) {
//        float hr1 = (float) Math.cos(-horizontalRotationDeg * MathMethods.DEG_TO_RAD - MathMethods.PI);
//        float hr2 = (float) Math.sin(-horizontalRotationDeg * MathMethods.DEG_TO_RAD - MathMethods.PI);
//        float vr1 = (float) -Math.cos(-verticalRotationDeg * MathMethods.DEG_TO_RAD);
//        float vr2 = (float) Math.sin(-verticalRotationDeg * MathMethods.DEG_TO_RAD);
//        return new Vector3f(origin.x()+dist*(hr2 * vr1), origin.y()+dist*vr2, origin.z()+dist*(hr1 * vr1));

        //Rough + Fast
        float hr1 = MathMethods.cos(-horizontalRotationDeg * MathMethods.DEG_TO_RAD - MathMethods.PI);
        float hr2 = MathMethods.sin(-horizontalRotationDeg * MathMethods.DEG_TO_RAD - MathMethods.PI);
        float vr1 = -MathMethods.cos(-verticalRotationDeg * MathMethods.DEG_TO_RAD); //flip 180 deg horizontally?
        float vr2 = MathMethods.sin(-verticalRotationDeg * MathMethods.DEG_TO_RAD);
        return new Vector3f(origin.x()+dist*(hr2 * vr1), origin.y()+dist*vr2, origin.z()+dist*(hr1 * vr1));
    }

    public static Vector3f PolarProjectionFromRotation(float dist, float horizontalRotationDeg, float verticalRotationDeg) {
        //Rough + Fast
        float hr1 = MathMethods.cos(-horizontalRotationDeg * MathMethods.DEG_TO_RAD - MathMethods.PI);
        float hr2 = MathMethods.sin(-horizontalRotationDeg * MathMethods.DEG_TO_RAD - MathMethods.PI);
        float vr1 = -MathMethods.cos(-verticalRotationDeg * MathMethods.DEG_TO_RAD); //flip 180 deg horizontally?
        float vr2 = MathMethods.sin(-verticalRotationDeg * MathMethods.DEG_TO_RAD);
        return new Vector3f(dist*(hr2 * vr1), dist*vr2, dist*(hr1 * vr1));
    }

    public static Vector3f directionFromRotation(float horizontalRotationDeg, float verticalRotationDeg) {
//        float hr1 = (float) Math.cos(-horizontalRotationDeg * MathMethods.DEG_TO_RAD - MathMethods.PI);
//        float hr2 = (float) Math.sin(-horizontalRotationDeg * MathMethods.DEG_TO_RAD - MathMethods.PI);
//        float vr1 = (float) -Math.cos(-verticalRotationDeg * MathMethods.DEG_TO_RAD);
//        float vr2 = (float) Math.sin(-verticalRotationDeg * MathMethods.DEG_TO_RAD);
//        return new Vector3f((hr2 * vr1), vr2, (hr1 * vr1));

        //Rough + Fast
        float hr1 = MathMethods.cos(-horizontalRotationDeg * MathMethods.DEG_TO_RAD - MathMethods.PI);
        float hr2 = MathMethods.sin(-horizontalRotationDeg * MathMethods.DEG_TO_RAD - MathMethods.PI);
        float vr1 = -MathMethods.cos(-verticalRotationDeg * MathMethods.DEG_TO_RAD);
        float vr2 = MathMethods.sin(-verticalRotationDeg * MathMethods.DEG_TO_RAD);
        return new Vector3f((hr2 * vr1), vr2, (hr1 * vr1));
    }

    public static float Vec3fDistanceTo(Vector3f a, Vector3f b) {
        float newX = a.x() - b.x();
        float newY = a.y() - b.y();
        float newZ = a.z() - b.z();
        return MathMethods.sqrt(newX * newX + newY * newY + newZ * newZ);
    }

    public static float Vec3fDistanceTo(float ax, float ay, float az, float bx, float by, float bz) {
        float newX = ax - bx;
        float newY = ay - by;
        float newZ = az - bz;
        return MathMethods.sqrt(newX * newX + newY * newY + newZ * newZ);
    }

    public static Vector3f scale(Vector3f vector, float scaleX, float scaleY, float scaleZ) { return new Vector3f(vector.x() * scaleX, vector.y() * scaleY, vector.z() * scaleZ); }
    public static Vector3f scale(Vector3f vector, float scale) { return new Vector3f(vector.x() * scale, vector.y() * scale, vector.z() * scale); }

    public static float lerp1D(float time, float from, float to) {
        return from + time * (to - from);
    }

    public static Vector3f randomVecIn2DRect(float minX, float maxX, float fixedY, float minZ, float maxZ) {
        return new Vector3f(ExtraMathMethods.randomFloat(minX, maxX), fixedY, ExtraMathMethods.randomFloat(minZ, maxZ));
    }

    public static Vector3f randomVecIn2DCircle(float radius, Vector3f center) {
        return PolarProjectionFromVecHorizon(center, ExtraMathMethods.randomFloat(radius), ExtraMathMethods.randomFloat(360f));
    }

    public static Vector3f randomVecIn3DCube(float minX, float maxX, float minY, float maxY, float minZ, float maxZ) {
        return new Vector3f(ExtraMathMethods.randomFloat(minX, maxX), ExtraMathMethods.randomFloat(minY, maxY), ExtraMathMethods.randomFloat(minZ, maxZ));
    }

    public static Vector3f randomIn3DSphere(float radius, Vector3f center) {
        return PolarProjectionFromRotation(center, ExtraMathMethods.randomFloat(radius), ExtraMathMethods.randomFloat(360f), ExtraMathMethods.randomFloat(360f));
    }

    public static Vector3f randomVecIn3DCylinder(float radius, float cylinderHeight, Vector3f center) {
        Vector3f randomYCenter = new Vector3f(center.x(), center.y()+ExtraMathMethods.randomFloat(2) - 1 + cylinderHeight, center.z());
        return PolarProjectionFromVecHorizon(randomYCenter, ExtraMathMethods.randomFloat(radius), ExtraMathMethods.randomFloat(360f));
    }

    public static Vec3d convertVector3fToMCVec3(Vector3f vector) { return new Vec3d(vector.x(), vector.y(), vector.z()); }
    public static Vector3f convertMCVec3fToVector3f(Vec3d vector) { return new Vector3f((float) vector.getX(), (float) vector.getY(), (float) vector.getZ()); }

    public static Vector3f getOriginVector() { return OriginVector; }
    public static Vector3f getAxisXN() { return AxisXN; }
    public static Vector3f getAxisYP() { return AxisYP; }
    public static Vector3f getAxisZN() { return AxisZN; }
}
