package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.berserker.bloodtwister;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BloodTwisterModel extends GeoModel<BloodTwisterEntity> {
    @Override
    public ResourceLocation getModelResource(BloodTwisterEntity object) {
        return DfoSwdGeneralGeoEntityResources.BLOOD_TWISTER_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(BloodTwisterEntity object) {
        return DfoSwdGeneralGeoEntityResources.BLOOD_TWISTER_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(BloodTwisterEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.BLOOD_TWISTER_ANIMATION_FILE_RESOURCE;
    }
}
