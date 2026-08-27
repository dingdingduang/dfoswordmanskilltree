package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_block_eff;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class SwdTempestBlockEffModel extends GeoModel<SwdTempestBlockEffEntity> {
    @Override
    public Identifier getModelResource(SwdTempestBlockEffEntity object, GeoRenderer<SwdTempestBlockEffEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(SwdTempestBlockEffEntity object, GeoRenderer<SwdTempestBlockEffEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(SwdTempestBlockEffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_BLOCK_EFF_ANIMATION_FILE_RESOURCE;
    }
}
