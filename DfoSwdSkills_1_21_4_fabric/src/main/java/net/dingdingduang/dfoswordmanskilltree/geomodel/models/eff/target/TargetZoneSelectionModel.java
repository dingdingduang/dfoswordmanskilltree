package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class TargetZoneSelectionModel extends GeoModel<TargetZoneSelectionEntity> {
    @Override
    public Identifier getModelResource(TargetZoneSelectionEntity object, GeoRenderer<TargetZoneSelectionEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_FLAT_HORIZON_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(TargetZoneSelectionEntity object, GeoRenderer<TargetZoneSelectionEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(TargetZoneSelectionEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_FLAT_HORIZON_ANIMATION_FILE_RESOURCE;
    }
}
