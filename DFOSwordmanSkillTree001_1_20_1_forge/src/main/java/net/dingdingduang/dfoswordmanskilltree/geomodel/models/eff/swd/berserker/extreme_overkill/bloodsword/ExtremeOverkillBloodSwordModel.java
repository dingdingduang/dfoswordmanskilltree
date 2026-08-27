package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.berserker.extreme_overkill.bloodsword;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ExtremeOverkillBloodSwordModel extends GeoModel<ExtremeOverkillBloodSwordEntity> {
    @Override
    public ResourceLocation getModelResource(ExtremeOverkillBloodSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.EXTREME_OVERKILL_BLOOD_SWORD_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(ExtremeOverkillBloodSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.EXTREME_OVERKILL_BLOOD_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(ExtremeOverkillBloodSwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.EXTREME_OVERKILL_BLOOD_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
