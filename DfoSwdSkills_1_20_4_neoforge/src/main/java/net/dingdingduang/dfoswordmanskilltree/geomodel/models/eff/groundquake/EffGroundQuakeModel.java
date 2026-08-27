package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.groundquake;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EffGroundQuakeModel extends GeoModel<EffGroundQuakeEntity> {
    @Override
    public ResourceLocation getModelResource(EffGroundQuakeEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(EffGroundQuakeEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(EffGroundQuakeEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_ANIMATION_FILE_RESOURCE;
    }
}
