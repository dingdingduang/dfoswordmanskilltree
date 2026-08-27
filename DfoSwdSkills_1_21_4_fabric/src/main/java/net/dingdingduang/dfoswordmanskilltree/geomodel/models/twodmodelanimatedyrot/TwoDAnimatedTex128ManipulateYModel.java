package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class TwoDAnimatedTex128ManipulateYModel extends GeoModel<TwoDAnimatedTex128ManipulateYEntity> {
    @Override
    public Identifier getModelResource(TwoDAnimatedTex128ManipulateYEntity object, GeoRenderer<TwoDAnimatedTex128ManipulateYEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_Y_ROT_ONLY_128_PX_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(TwoDAnimatedTex128ManipulateYEntity object, GeoRenderer<TwoDAnimatedTex128ManipulateYEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(TwoDAnimatedTex128ManipulateYEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
