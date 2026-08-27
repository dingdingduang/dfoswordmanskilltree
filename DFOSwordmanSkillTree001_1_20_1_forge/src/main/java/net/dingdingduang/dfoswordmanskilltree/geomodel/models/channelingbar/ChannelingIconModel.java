package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ChannelingIconModel extends GeoModel<ChannelingIconEntity> {
    @Override
    public ResourceLocation getModelResource(ChannelingIconEntity object) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_ICON_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(ChannelingIconEntity object) {
        return DfoSwdGeneralGeoEntityResources.NO_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(ChannelingIconEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_ICON_ANIMATION_FILE_RESOURCE;
    }
}
