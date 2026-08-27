package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.tonorth;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TwoDAnimatedTex256ToNorthYOnlyCustomModel extends GeoModel<TwoDAnimatedTex256ToNorthYOnlyCustomEntity> {
    @Override
    public ResourceLocation getModelResource(TwoDAnimatedTex256ToNorthYOnlyCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_CUSTOM_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(TwoDAnimatedTex256ToNorthYOnlyCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(TwoDAnimatedTex256ToNorthYOnlyCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_CUSTOM_ANIMATION_FILE_RESOURCE;
    }
}
