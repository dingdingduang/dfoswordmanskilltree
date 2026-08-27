package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class EffStrikeFromAirModel extends GeoModel<EffStrikeFromAirEntity> {
    @Override
    public ResourceLocation getModelResource(EffStrikeFromAirEntity object, GeoRenderer<EffStrikeFromAirEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.EFF_STAB_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(EffStrikeFromAirEntity object, GeoRenderer<EffStrikeFromAirEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.EFF_STRIKE_FROM_AIR_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(EffStrikeFromAirEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_ANIMATION_FILE_RESOURCE;
    }
}
