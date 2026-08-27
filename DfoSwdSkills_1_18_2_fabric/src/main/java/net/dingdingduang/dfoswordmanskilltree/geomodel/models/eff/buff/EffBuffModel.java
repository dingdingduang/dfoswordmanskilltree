package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EffBuffModel extends AnimatedGeoModel<EffBuffEntity> {
    @Override
    public Identifier getModelLocation(EffBuffEntity object) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(EffBuffEntity object) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(EffBuffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_ANIMATION_FILE_RESOURCE;
    }
}
