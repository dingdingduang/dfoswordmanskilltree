package net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FloatingSwordModel extends AnimatedGeoModel<FloatingSwordEntity> {
    @Override
    public ResourceLocation getModelLocation(FloatingSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.FLOATING_SWORD_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(FloatingSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.FLOATING_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FloatingSwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.FLOATING_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
