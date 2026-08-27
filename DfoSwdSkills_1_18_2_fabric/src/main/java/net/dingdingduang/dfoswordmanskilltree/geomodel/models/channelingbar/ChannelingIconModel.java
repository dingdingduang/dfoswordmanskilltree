package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChannelingIconModel extends AnimatedGeoModel<ChannelingIconEntity> {
    @Override
    public Identifier getModelLocation(ChannelingIconEntity object) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_ICON_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(ChannelingIconEntity object) {
        return DfoSwdGeneralGeoEntityResources.NO_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(ChannelingIconEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_ICON_ANIMATION_FILE_RESOURCE;
    }
}
