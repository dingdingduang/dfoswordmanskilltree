package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.custom;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwoDAnimated256TexCustomModel extends AnimatedGeoModel<TwoDAnimated256TexCustomEntity> {
    @Override
    public Identifier getModelLocation(TwoDAnimated256TexCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_CUSTOM_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(TwoDAnimated256TexCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(TwoDAnimated256TexCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_CUSTOM_ANIMATION_FILE_RESOURCE;
    }
}
