package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.alternative;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EffBuffAltModel extends AnimatedGeoModel<EffBuffAltEntity> {
    @Override
    public ResourceLocation getModelLocation(EffBuffAltEntity object) {
        return DfoSwdGeneralGeoEntityResources.PLURAL_EFF_BUFF_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(EffBuffAltEntity object) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EffBuffAltEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.PLURAL_EFF_BUFF_ANIMATION_FILE_RESOURCE;
    }
}
