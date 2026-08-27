package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class TwoDAnimatedTex64Model extends GeoModel<TwoDAnimatedTex64Entity> {
    @Override
    public Identifier getModelResource(TwoDAnimatedTex64Entity object, GeoRenderer<TwoDAnimatedTex64Entity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_64_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(TwoDAnimatedTex64Entity object, GeoRenderer<TwoDAnimatedTex64Entity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(TwoDAnimatedTex64Entity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
