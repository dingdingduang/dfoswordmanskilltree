package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class TwoDAnimatedTex64ManipulateYModel extends GeoModel<TwoDAnimatedTex64ManipulateYEntity> {
    @Override
    public Identifier getModelResource(TwoDAnimatedTex64ManipulateYEntity object, GeoRenderer<TwoDAnimatedTex64ManipulateYEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_Y_ROT_ONLY_64_PX_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(TwoDAnimatedTex64ManipulateYEntity object, GeoRenderer<TwoDAnimatedTex64ManipulateYEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(TwoDAnimatedTex64ManipulateYEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
