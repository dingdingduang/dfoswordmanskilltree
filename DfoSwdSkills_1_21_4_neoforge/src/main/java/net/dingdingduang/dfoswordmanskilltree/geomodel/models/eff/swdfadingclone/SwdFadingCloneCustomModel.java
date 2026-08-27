package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class SwdFadingCloneCustomModel extends GeoModel<SwdFadingCloneCustomEntity> {
    @Override
    public ResourceLocation getModelResource(SwdFadingCloneCustomEntity object, GeoRenderer<SwdFadingCloneCustomEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SWD_FADING_CLONE_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(SwdFadingCloneCustomEntity object, GeoRenderer<SwdFadingCloneCustomEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SWD_FADING_CLONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(SwdFadingCloneCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SWD_FADING_CLONE_ANIMATION_FILE_RESOURCE;
    }

//    @Override
//    public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
//        getArm(humanoidArm).translateAndRotate(poseStack);
//    }
}
