package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.custom;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwoDAnimated256TexCustomModel extends AnimatedGeoModel<TwoDAnimated256TexCustomEntity> {
    @Override
    public ResourceLocation getModelLocation(TwoDAnimated256TexCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_CUSTOM_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(TwoDAnimated256TexCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TwoDAnimated256TexCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_CUSTOM_ANIMATION_FILE_RESOURCE;
    }
}
