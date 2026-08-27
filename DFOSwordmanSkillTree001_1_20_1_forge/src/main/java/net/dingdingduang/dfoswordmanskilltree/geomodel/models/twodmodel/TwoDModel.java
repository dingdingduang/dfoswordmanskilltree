package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodel;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TwoDModel extends GeoModel<TwoDEntity> {
    @Override
    public ResourceLocation getModelResource(TwoDEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(TwoDEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(TwoDEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
