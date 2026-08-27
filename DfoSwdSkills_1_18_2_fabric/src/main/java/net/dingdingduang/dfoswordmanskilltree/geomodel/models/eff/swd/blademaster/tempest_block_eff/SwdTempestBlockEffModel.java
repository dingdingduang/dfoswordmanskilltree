package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_block_eff;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwdTempestBlockEffModel extends AnimatedGeoModel<SwdTempestBlockEffEntity> {
    @Override
    public Identifier getModelLocation(SwdTempestBlockEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(SwdTempestBlockEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(SwdTempestBlockEffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_ANIMATION_FILE_RESOURCE;
    }
}
