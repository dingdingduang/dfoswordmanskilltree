package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_block_eff;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwdTempestBlockEffModel extends AnimatedGeoModel<SwdTempestBlockEffEntity> {
    @Override
    public ResourceLocation getModelLocation(SwdTempestBlockEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(SwdTempestBlockEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SwdTempestBlockEffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_ANIMATION_FILE_RESOURCE;
    }
}
