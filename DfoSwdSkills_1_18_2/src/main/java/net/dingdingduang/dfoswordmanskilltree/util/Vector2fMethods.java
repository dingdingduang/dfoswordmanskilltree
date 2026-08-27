package net.dingdingduang.dfoswordmanskilltree.util;

public class Vector2fMethods {
    //https://stackoverflow.com/questions/5184815/java-intersection-point-of-a-polygon-and-line
//    public static float[] intersect(float line1_x1, float line1_y1, float line1_x2, float line1_y2,
//                                    float line2_x1, float line2_y1, float line2_x2, float line2_y2) {
//        // calc slope
//        //y = ax + b
//        float line1_slope_numerator = line1_y1 - line1_y2;
//        float line1_slope_denominator = line1_x1 - line1_x2;
//        float line2_slope_numerator = line2_y1 - line2_y2;
//        float line2_slope_denominator = line2_x1 - line2_x2;
//
//        float line1_slope = (line1_slope_denominator == 0) ? 0 : line1_slope_numerator / line1_slope_denominator;
//        float line2_slope = (line2_slope_denominator == 0) ? 0 : line2_slope_numerator / line2_slope_denominator;
//
//        // calc intercept
//        float line1_y_constant = line1_y1 - (line1_slope * line1_x1);
//        float line2_y_constant = line2_y1 - (line2_slope * line2_x1);
//
//        float intersect_point_x = (line2_y_constant - line1_y_constant) / (line1_slope - line2_slope);
//        float intersect_point_y = line1_slope * intersect_point_x + line1_y_constant;
//
//        return new float[]{intersect_point_x, intersect_point_y};
//    }

    //https://jvm-gaming.org/t/fastest-linesintersect-method/35387/7
    public static float[] LineIntersect(double line1_x1, double line1_y1, double line1_x2, double line1_y2,
                                        double line2_x1, double line2_y1, double line2_x2, double line2_y2) {
        double denominator = (line2_y2 - line2_y1) * (line1_x2 - line1_x1) - (line2_x2 - line2_x1) * (line1_y2 - line1_y1);
        if (denominator == 0.0) { // Lines are parallel.
            return null;
        }
        double ua = ((line2_x2 - line2_x1) * (line1_y1 - line2_y1) - (line2_y2 - line2_y1) * (line1_x1 - line2_x1)) / denominator;

        return new float[]{(float) (line1_x1 + ua * (line1_x2 - line1_x1)), (float) (line1_y1 + ua * (line1_y2 - line1_y1))};
    }

    public static float[] LineSegmentIntersect(double line1_x1, double line1_y1, double line1_x2, double line1_y2,
                                        double line2_x1, double line2_y1, double line2_x2, double line2_y2) {
        double denominator = (line2_y2 - line2_y1) * (line1_x2 - line1_x1) - (line2_x2 - line2_x1) * (line1_y2 - line1_y1);
        if (denominator == 0.0) { // Lines are parallel.
            return null;
        }

        //check collision
        double ua = ((line2_x2 - line2_x1) * (line1_y1 - line2_y1) - (line2_y2 - line2_y1) * (line1_x1 - line2_x1)) / denominator;
		double ub = ((line1_x2 - line1_x1) * (line1_y1 - line2_y1) - (line1_y2 - line1_y1) * (line1_x1 - line2_x1)) / denominator;
		if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {
			// Get the intersection point.
			return new float[]{(float) (line1_x1 + ua * (line1_x2 - line1_x1)), (float) (line1_y1 + ua * (line1_y2 - line1_y1))};
		}

		return null;
    }
}
