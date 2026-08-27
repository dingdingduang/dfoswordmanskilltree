package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodel;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwoDModel extends AnimatedGeoModel<TwoDEntity> {
    @Override
    public ResourceLocation getModelLocation(TwoDEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(TwoDEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TwoDEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
