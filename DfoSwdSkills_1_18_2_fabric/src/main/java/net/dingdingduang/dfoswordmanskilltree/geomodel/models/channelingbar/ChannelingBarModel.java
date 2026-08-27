package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChannelingBarModel extends AnimatedGeoModel<ChannelingBarEntity> {
    @Override
    public Identifier getModelLocation(ChannelingBarEntity object) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_BAR_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(ChannelingBarEntity object) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_BAR_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(ChannelingBarEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_BAR_ANIMATION_FILE_RESOURCE;
    }
}
