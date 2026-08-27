package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BMTempestSwordModel extends GeoModel<BMTempestSwordEntity> {
    @Override
    public Identifier getModelResource(BMTempestSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(BMTempestSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(BMTempestSwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
