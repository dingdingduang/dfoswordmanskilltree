package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwdFadingCloneCustomModel extends AnimatedGeoModel<SwdFadingCloneCustomEntity> {
    @Override
    public ResourceLocation getModelLocation(SwdFadingCloneCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWD_FADING_CLONE_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(SwdFadingCloneCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWD_FADING_CLONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SwdFadingCloneCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SWD_FADING_CLONE_ANIMATION_FILE_RESOURCE;
    }

//    @Override
//    public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
//        getArm(humanoidArm).translateAndRotate(poseStack);
//    }
}
