package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.handeff;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;

import java.util.Map;

public class HandEffGetModelPartMethods {
    public static String HEAD = "head";
    public static String BODY = "body";
    public static String LEFT_ARM = "left_arm";
    public static String RIGHT_ARM = "right_arm";
    public static String LEFT_LEG = "left_leg";
    public static String RIGHT_LEG = "right_leg";

    public static Map<String, HandEffGetModelPartMethodHelper> getModelPartMethodHelperMap = Map.of(
            HEAD, HandEffGetModelPartMethods::getHeadPart,
            BODY, HandEffGetModelPartMethods::getBodyPart,
            LEFT_ARM, HandEffGetModelPartMethods::getLeftArmPart,
            RIGHT_ARM, HandEffGetModelPartMethods::getRightArmPart,
            LEFT_LEG, HandEffGetModelPartMethods::getLeftLegPart,
            RIGHT_LEG, HandEffGetModelPartMethods::getRightLegPart
    );

    public static ModelPart getModelPart(String modelPartName, HumanoidModel<?> humanoidModel) {
        HandEffGetModelPartMethodHelper methodHelper = getModelPartMethodHelperMap.get(modelPartName);
        if (methodHelper == null) {
            return getLeftLegPart(humanoidModel);
        }
        else {
            return methodHelper.getModelPart(humanoidModel);
        }
    }

    public static ModelPart getHeadPart(HumanoidModel<?> humanoidModel) { return humanoidModel.head; }
    public static ModelPart getBodyPart(HumanoidModel<?> humanoidModel) { return humanoidModel.body; }
    public static ModelPart getLeftArmPart(HumanoidModel<?> humanoidModel) { return humanoidModel.leftArm; }
    public static ModelPart getRightArmPart(HumanoidModel<?> humanoidModel) { return humanoidModel.rightArm; }
    public static ModelPart getLeftLegPart(HumanoidModel<?> humanoidModel) { return humanoidModel.leftLeg; }
    public static ModelPart getRightLegPart(HumanoidModel<?> humanoidModel) { return humanoidModel.rightLeg; }
}
