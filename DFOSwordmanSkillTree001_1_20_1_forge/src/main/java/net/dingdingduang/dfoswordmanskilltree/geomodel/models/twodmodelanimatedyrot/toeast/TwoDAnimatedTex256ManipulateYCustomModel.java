package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.toeast;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TwoDAnimatedTex256ManipulateYCustomModel extends GeoModel<TwoDAnimatedTex256ManipulateYCustomEntity> {
    @Override
    public ResourceLocation getModelResource(TwoDAnimatedTex256ManipulateYCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_CUSTOM_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(TwoDAnimatedTex256ManipulateYCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(TwoDAnimatedTex256ManipulateYCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_CUSTOM_ANIMATION_FILE_RESOURCE;
    }
}
