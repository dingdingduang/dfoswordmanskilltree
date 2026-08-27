package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.custom;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TwoDAnimated256TexCustomModel extends GeoModel<TwoDAnimated256TexCustomEntity> {
    @Override
    public Identifier getModelResource(TwoDAnimated256TexCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_CUSTOM_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(TwoDAnimated256TexCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(TwoDAnimated256TexCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_CUSTOM_ANIMATION_FILE_RESOURCE;
    }
}
