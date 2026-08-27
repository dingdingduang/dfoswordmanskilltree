package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.groundquake;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EffGroundQuakeModel extends AnimatedGeoModel<EffGroundQuakeEntity> {
    @Override
    public Identifier getModelLocation(EffGroundQuakeEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(EffGroundQuakeEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(EffGroundQuakeEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_ANIMATION_FILE_RESOURCE;
    }
}
