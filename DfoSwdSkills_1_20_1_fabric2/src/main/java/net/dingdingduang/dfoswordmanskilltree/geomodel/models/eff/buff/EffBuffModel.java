package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class EffBuffModel extends GeoModel<EffBuffEntity> {
    @Override
    public Identifier getModelResource(EffBuffEntity object) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(EffBuffEntity object) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(EffBuffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_ANIMATION_FILE_RESOURCE;
    }
}
