package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.stab;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class EffStabModel extends GeoModel<EffStabEntity> {
    @Override
    public Identifier getModelResource(EffStabEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STAB_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(EffStabEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STAB_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(EffStabEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_ANIMATION_FILE_RESOURCE;
    }
}
