package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.flat;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class TwoDAnimated256FlatTexCustomModel extends GeoModel<TwoDAnimated256FlatTexCustomEntity> {
    @Override
    public ResourceLocation getModelResource(TwoDAnimated256FlatTexCustomEntity object, GeoRenderer<TwoDAnimated256FlatTexCustomEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_FLAT_HORIZON_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(TwoDAnimated256FlatTexCustomEntity object, GeoRenderer<TwoDAnimated256FlatTexCustomEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(TwoDAnimated256FlatTexCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_FLAT_HORIZON_ANIMATION_FILE_RESOURCE;
    }
}
