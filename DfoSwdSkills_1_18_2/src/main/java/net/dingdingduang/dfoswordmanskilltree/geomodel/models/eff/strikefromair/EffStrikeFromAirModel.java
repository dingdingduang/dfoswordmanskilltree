package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EffStrikeFromAirModel extends AnimatedGeoModel<EffStrikeFromAirEntity> {
    @Override
    public ResourceLocation getModelLocation(EffStrikeFromAirEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STAB_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(EffStrikeFromAirEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STRIKE_FROM_AIR_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EffStrikeFromAirEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_ANIMATION_FILE_RESOURCE;
    }
}
