package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.trail;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class TwoDAnimatedTexRGB256Model extends GeoModel<TwoDAnimatedTexRGB256Entity> {
    @Override
    public ResourceLocation getModelResource(TwoDAnimatedTexRGB256Entity object, GeoRenderer<TwoDAnimatedTexRGB256Entity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_RGB_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(TwoDAnimatedTexRGB256Entity object, GeoRenderer<TwoDAnimatedTexRGB256Entity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(TwoDAnimatedTexRGB256Entity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
