package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChannelingIconModel extends AnimatedGeoModel<ChannelingIconEntity> {
    @Override
    public ResourceLocation getModelLocation(ChannelingIconEntity object) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_ICON_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(ChannelingIconEntity object) {
        return DfoSwdGeneralGeoEntityResources.NO_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ChannelingIconEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_ICON_ANIMATION_FILE_RESOURCE;
    }
}
