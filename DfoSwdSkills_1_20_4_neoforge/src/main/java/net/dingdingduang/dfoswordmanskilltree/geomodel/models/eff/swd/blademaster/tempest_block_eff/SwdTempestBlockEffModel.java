package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_block_eff;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SwdTempestBlockEffModel extends GeoModel<SwdTempestBlockEffEntity> {
    @Override
    public ResourceLocation getModelResource(SwdTempestBlockEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(SwdTempestBlockEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(SwdTempestBlockEffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_ANIMATION_FILE_RESOURCE;
    }
}
