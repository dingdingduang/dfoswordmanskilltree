package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TwoDAnimatedTex64ManipulateYModel extends GeoModel<TwoDAnimatedTex64ManipulateYEntity> {
    @Override
    public ResourceLocation getModelResource(TwoDAnimatedTex64ManipulateYEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_Y_ROT_ONLY_64_PX_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(TwoDAnimatedTex64ManipulateYEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(TwoDAnimatedTex64ManipulateYEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
