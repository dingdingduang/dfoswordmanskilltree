package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ChannelingBarModel extends GeoModel<ChannelingBarEntity> {
    @Override
    public Identifier getModelResource(ChannelingBarEntity object) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_BAR_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(ChannelingBarEntity object) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_BAR_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(ChannelingBarEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_BAR_ANIMATION_FILE_RESOURCE;
    }
}
