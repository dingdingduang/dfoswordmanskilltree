package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SlaughterscapeSwordModel extends GeoModel<SlaughterscapeSwordEntity> {
    @Override
    public ResourceLocation getModelResource(SlaughterscapeSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(SlaughterscapeSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(SlaughterscapeSwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
