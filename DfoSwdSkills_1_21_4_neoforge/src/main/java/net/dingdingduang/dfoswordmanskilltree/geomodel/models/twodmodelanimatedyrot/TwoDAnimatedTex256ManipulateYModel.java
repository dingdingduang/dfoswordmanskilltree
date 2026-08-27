package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class TwoDAnimatedTex256ManipulateYModel extends GeoModel<TwoDAnimatedTex256ManipulateYEntity> {
    @Override
    public ResourceLocation getModelResource(TwoDAnimatedTex256ManipulateYEntity object, GeoRenderer<TwoDAnimatedTex256ManipulateYEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(TwoDAnimatedTex256ManipulateYEntity object, GeoRenderer<TwoDAnimatedTex256ManipulateYEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(TwoDAnimatedTex256ManipulateYEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
