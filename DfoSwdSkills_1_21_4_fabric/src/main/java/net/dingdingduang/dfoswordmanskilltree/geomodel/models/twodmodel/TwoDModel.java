package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodel;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class TwoDModel extends GeoModel<TwoDEntity> {
    @Override
    public Identifier getModelResource(TwoDEntity object, GeoRenderer<TwoDEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(TwoDEntity object, GeoRenderer<TwoDEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(TwoDEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
