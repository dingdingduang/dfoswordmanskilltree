package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.handeff;

import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HandEffModel extends GeoModel<HandEffEntity> {
    @Override
    public ResourceLocation getModelResource(HandEffEntity object) {
        return GlobalClientMaps.getReusableResourceByStringID(object.getGeoModelFilePathName());
    }

    @Override
    public ResourceLocation getTextureResource(HandEffEntity object) {
        return GlobalClientMaps.getReusableResourceByStringID(object.getTextureFilePathName());
    }

    @Override
    public ResourceLocation getAnimationResource(HandEffEntity animatable) {
        return GlobalClientMaps.getReusableResourceByStringID(animatable.getAnimationFilePath());
    }
}
