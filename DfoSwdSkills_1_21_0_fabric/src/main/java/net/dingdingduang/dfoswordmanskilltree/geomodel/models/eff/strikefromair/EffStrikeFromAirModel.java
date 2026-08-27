package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class EffStrikeFromAirModel extends GeoModel<EffStrikeFromAirEntity> {
    @Override
    public Identifier getModelResource(EffStrikeFromAirEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STAB_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(EffStrikeFromAirEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STRIKE_FROM_AIR_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(EffStrikeFromAirEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_ANIMATION_FILE_RESOURCE;
    }
}
