package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BMTempestSwordModel extends AnimatedGeoModel<BMTempestSwordEntity> {
    @Override
    public ResourceLocation getModelLocation(BMTempestSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(BMTempestSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BMTempestSwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
