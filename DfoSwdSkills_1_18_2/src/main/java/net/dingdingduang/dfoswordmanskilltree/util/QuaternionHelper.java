package net.dingdingduang.dfoswordmanskilltree.util;

import modified.org.joml.Quaternionf;

public class QuaternionHelper {
    public static void QuatRotationByAxis(Quaternionf quaternionf, float angle, float axisX, float axisY, float axisZ) {
        float hangle = angle * 0.5f;
        float sinAngle = MathMethods.sin(hangle);
        float invVLength = (float) MathMethods.fastInvSqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        quaternionf.set(axisX * invVLength * sinAngle,
                axisY * invVLength * sinAngle,
                axisZ * invVLength * sinAngle,
                MathMethods.cosFromSin(sinAngle, hangle));
    }
}
