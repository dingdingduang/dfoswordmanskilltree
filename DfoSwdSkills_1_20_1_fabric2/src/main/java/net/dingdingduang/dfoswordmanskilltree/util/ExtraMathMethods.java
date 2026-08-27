package net.dingdingduang.dfoswordmanskilltree.util;

import java.util.concurrent.ThreadLocalRandom;

public class ExtraMathMethods {
    public static int clamp(int val, int min, int max) {
        return val < min ? min : Math.min(val, max);
    }

    public static float clamp(float val, float min, float max) {
//        return val < min ? min : Math.min(val, max);
        return val < min ? min : MathMethods.min(val, max);
    }

    public static double clamp(double val, double min, double max) {
//        return val < min ? min : Math.min(val, max);
        return val < min ? min : MathMethods.min(val, max);
    }

    //Random
    public static float randomFloat() {
        return ThreadLocalRandom.current().nextFloat();
    }

    public static float randomFloat(float bound) {
        return ThreadLocalRandom.current().nextFloat(bound);
    }

    public static float randomFloat(float from, float to) {
        return ThreadLocalRandom.current().nextFloat(from, to);
    }

    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    public static int randomInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public static int randomInt(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }

//    public static int[] getEvenlyDistributedIntArr(int preTotalTickTime, int totalCountToBeDistributed, int offSet) {
//        int totalTickTime = preTotalTickTime - offSet;
//        float roundNum = 1.0f * totalTickTime / totalCountToBeDistributed;
//        int[] tempIndexHelperArr = new int[totalTickTime];
//
//        for (int i = totalTickTime; i >= 1; i--) {
//            tempIndexHelperArr[totalTickTime - i] = (int) (totalCountToBeDistributed - (i / roundNum)) + offSet;
//        }
//        return tempIndexHelperArr;
//    }
}
