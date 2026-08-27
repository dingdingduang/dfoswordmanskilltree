package net.dingdingduang.dfoswordmanskilltree.util;

import java.util.function.Consumer;

public class MathMethods {
    public static final float PI = (float) Math.PI;
    public static final float ONE_OVER_FOUR_PI = ((float) Math.PI / 4F);
    public static final float HALF_PI = ((float) Math.PI / 2F);
    public static final float TWO_PI = ((float) Math.PI * 2F);
    public static final float RAD_TO_DEG = (180F / (float) Math.PI);
    public static final float DEG_TO_RAD = ((float) Math.PI / 180F);
//    private static final float SIN_SCALE = 10430.378F;
    private static final float[] SIN = (float[]) ArrayMake(new float[65536], (floatArray) -> {
        for(int findex = 0; findex < floatArray.length; ++findex) {
            floatArray[findex] = (float) Math.sin((double) findex * Math.PI * (double) 2.0F / (double) 65536.0F);
        }
    });
    private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
    private static final double[] ASIN_TAB = new double[257];
    private static final double[] COS_TAB = new double[257];

    private static <T> T ArrayMake(T pObject, Consumer<T> pConsumer) {
        pConsumer.accept(pObject);
        return pObject;
    }

    public static double fastInvSqrt(double pNumber) {
        double a = (double) 0.5F * pNumber;
        long b = Double.doubleToRawLongBits(pNumber);
        b = 6910469410427058090L - (b >> 1);
        pNumber = Double.longBitsToDouble(b);
        pNumber *= (double)1.5F - a * pNumber * pNumber;
        return pNumber;
    }

    public static float sin(float pValue) {
        return SIN[(int)(pValue * 10430.378F) & '\uffff'];
//        return SIN[(int)(RoundRadians(pValue) * 10430.378F) & '\uffff'];
    }

    public static float cos(float pValue) {
        return SIN[(int)(pValue * 10430.378F + 16384.0F) & '\uffff'];
//        return SIN[(int)(RoundRadians(pValue) * 10430.378F + 16384.0F) & '\uffff'];
    }

    public static float cosFromSin(float sin, float angle) {
//            return sin(angle + HALF_PI);
        // sin(x)^2 + cos(x)^2 = 1
        float cos = sqrt(1.0f - sin * sin);
        float a = angle + HALF_PI;
        float b = a - (int)(a / TWO_PI) * TWO_PI;
        if (b < 0.0)
            b = TWO_PI + b;
        if (b >= PI)
            return -cos;
        return cos;
    }

    public static double atan2(double pY, double pX) {
//        double distSquare = pX * pX + pY * pY;
//        if (Double.isNaN(distSquare)) {
//            return Double.NaN;
//        } else {
//            boolean isYNeg = pY < (double)0.0F;
//            if (isYNeg) {
//                pY = -pY;
//            }
//
//            boolean isXNeg = pX < (double)0.0F;
//            if (isXNeg) {
//                pX = -pX;
//            }
//
//            boolean findLongerLength = pY > pX;
//            if (findLongerLength) {
//                double tempDouble = pX;
//                pX = pY;
//                pY = tempDouble;
//            }
//
//            double fastInvSqrtNumber = fastInvSqrt(distSquare);
//            pX *= fastInvSqrtNumber;
//            pY *= fastInvSqrtNumber;
//            double fracBiasPY = FRAC_BIAS + pY;
//            int doubleTabArrayIndex = (int) Double.doubleToRawLongBits(fracBiasPY);
//            double AsinTabIndex = ASIN_TAB[doubleTabArrayIndex];
//            double CosTabIndex = COS_TAB[doubleTabArrayIndex];
//            double newPY = fracBiasPY - FRAC_BIAS;
//            double a = pY * CosTabIndex - pX * newPY;
//            double b = ((double)6.0F + a * a) * a * 0.16666666666666666;
//            double c = AsinTabIndex + b;
//
//            if (findLongerLength) {
//                c = (Math.PI / 2D) - c;
//            }
//
//            if (isXNeg) {
//                c = Math.PI - c;
//            }
//
//            if (isYNeg) {
//                c = -c;
//            }
//
//            return c;
//        }

//        return org.joml.Math.fastAtan2(pY, pX);
        return jomlMathFastAtan2(pY, pX);
    }

    /**
     * https://math.stackexchange.com/questions/1098487/atan2-faster-approximation/1105038#answer-1105038
     */
    public static double jomlMathFastAtan2(double y, double x) {
        double ax = x >= 0.0 ? x : -x, ay = y >= 0.0 ? y : -y;
        double a = min(ax, ay) / max(ax, ay);
        double s = a * a;
        double r = ((-0.0464964749 * s + 0.15931422) * s - 0.327622764) * s * a + a;
        if (ay > ax)
            r = 1.57079637 - r;
        if (x < 0.0)
            r = 3.14159274 - r;
        return y >= 0 ? r : -r;
    }

    public static float lerpDegreeAngle(float time, float fromAngle, float toAngle) {
        float difference = (toAngle - fromAngle) % 360;
        float distance = (2f * difference) % 360 - difference;
        return fromAngle + time * distance;
    }

    public static float lerpRadiansAngle(float time, float fromAngle, float toAngle) {
        float difference = (toAngle - fromAngle) % MathMethods.TWO_PI;
        float distance = (2f * difference) % MathMethods.TWO_PI - difference;
        return fromAngle + time * distance;
    }

//    public static float AngleToRadians(float angleDeg) { return angleDeg * DEG_TO_RAD; }
//    public static float AngleToDegrees(float angleRadians) { return angleRadians * RAD_TO_DEG; }

    public static float RoundAngle(float angleDeg) {
        if (angleDeg < 0) {
            return 360 - ( (-1 * angleDeg) % 360 );
        }
        return angleDeg % 360;
    }

    public static float RoundRadians(float radians) {
        if (radians < 0) {
            return MathMethods.TWO_PI - ( (-1 * radians) % MathMethods.TWO_PI );
        }
        return radians % MathMethods.TWO_PI;
    }

    public static float AngleToRadians(float angleDeg) {
        return RoundAngle(angleDeg) * DEG_TO_RAD;
    }

    public static float AngleToDegrees(float angleRadians) {
        return RoundRadians(angleRadians) * RAD_TO_DEG;
    }

    public static float AngleToRadiansNoRound(float angleDeg) {
        return angleDeg * DEG_TO_RAD;
    }

    public static float AngleToDegreesNoRound(float angleRadians) {
        return angleRadians * RAD_TO_DEG;
    }

    public static float sqrt(float pValue) {
        return (float) Math.sqrt(pValue);
    }

    public static int floor(float pValue) {
        int tempVal = (int) pValue;
        return pValue < (float) tempVal ? tempVal - 1 : tempVal;
    }

    public static int floor(double pValue) {
        int tempVal = (int) pValue;
        return pValue < (double) tempVal ? tempVal - 1 : tempVal;
    }

    public static int ceil(float pValue) {
        int tempVal = (int) pValue;
        return pValue > (float) tempVal ? tempVal + 1 : tempVal;
    }

    public static int ceil(double pValue) {
        int tempVal = (int) pValue;
        return pValue > (double) tempVal ? tempVal + 1 : tempVal;
    }

    public static boolean isEven(int a) {
        return (a & 1) == 0;
    }

    //https://www.reddit.com/r/gamedev/comments/n7na0/fast_approximation_to_mathpow/?rdt=50750
    //martinus fast pow
//    public static float BetterFastPow(final float a, final float b) {
//        // exponentiation by squaring
//        float r = 1f;
//        short exp = (short) b;
//        float base = a;
//        while (exp != 0) {
//            if ((exp & 1) != 0) {
//                r *= base;
//            }
//            base *= base;
//            exp >>= 1;
//        }
//
//        final float b_faction = b - (short) b;
//        final int tmp = Float.floatToIntBits(a);
//        final int tmp2 = (int) (b_faction * (tmp - 1065353216)) + 1065353216;
//        return r * Float.intBitsToFloat(tmp2);
//    }
//
//    public static double BetterFastPow(final double a, final double b) {
//        // exponentiation by squaring
//        double r = 1.0;
//        int exp = (int) b;
//        double base = a;
//        while (exp != 0) {
//            if ((exp & 1) != 0) {
//                r *= base;
//            }
//            base *= base;
//            exp >>= 1;
//        }
//
//        // use the IEEE 754 trick for the fraction of the exponent
//        final double b_faction = b - (int) b;
//        final long tmp = Double.doubleToLongBits(a);
//        final long tmp2 = (long) (b_faction * (tmp - 4607182418800017408L)) + 4607182418800017408L;
//        return r * Double.longBitsToDouble(tmp2);
//    }

    public static float min(float a, float b) { return a < b ? a : b; }
    public static double min(double a, double b) { return a < b ? a : b; }
    public static float max(float a, float b) { return a > b ? a : b; }
    public static double max(double a, double b) { return a > b ? a : b; }
}
