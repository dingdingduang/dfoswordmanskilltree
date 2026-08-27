package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EffBuffModel extends AnimatedGeoModel<EffBuffEntity> {
    @Override
    public ResourceLocation getModelLocation(EffBuffEntity object) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(EffBuffEntity object) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EffBuffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_ANIMATION_FILE_RESOURCE;
    }
}
