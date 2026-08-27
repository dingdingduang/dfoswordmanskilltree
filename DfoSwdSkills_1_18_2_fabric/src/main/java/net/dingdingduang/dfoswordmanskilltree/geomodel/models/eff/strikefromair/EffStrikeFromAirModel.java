package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EffStrikeFromAirModel extends AnimatedGeoModel<EffStrikeFromAirEntity> {
    @Override
    public Identifier getModelLocation(EffStrikeFromAirEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STAB_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(EffStrikeFromAirEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STRIKE_FROM_AIR_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(EffStrikeFromAirEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_ANIMATION_FILE_RESOURCE;
    }
}
