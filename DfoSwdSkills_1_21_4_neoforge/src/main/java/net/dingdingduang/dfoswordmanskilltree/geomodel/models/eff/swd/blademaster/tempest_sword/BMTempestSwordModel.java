package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class BMTempestSwordModel extends GeoModel<BMTempestSwordEntity> {
    @Override
    public ResourceLocation getModelResource(BMTempestSwordEntity object, GeoRenderer<BMTempestSwordEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(BMTempestSwordEntity object, GeoRenderer<BMTempestSwordEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(BMTempestSwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
