package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HitEffModel extends AnimatedGeoModel<HitEffEntity> {
    @Override
    public ResourceLocation getModelLocation(HitEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(HitEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(HitEffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_ANIMATION_FILE_RESOURCE;
    }
}
