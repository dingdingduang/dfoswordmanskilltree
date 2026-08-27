package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.berserker.extreme_overkill.background;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ExtremeOverkillBackgroundModel extends GeoModel<ExtremeOverkillBackgroundEntity> {
    @Override
    public ResourceLocation getModelResource(ExtremeOverkillBackgroundEntity object) {
        return DfoSwdGeneralGeoEntityResources.EXTREME_OVERKILL_BACKGROUND_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(ExtremeOverkillBackgroundEntity object) {
        return DfoSwdGeneralGeoEntityResources.EXTREME_OVERKILL_BACKGROUND_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(ExtremeOverkillBackgroundEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.EXTREME_OVERKILL_BACKGROUND_ANIMATION_FILE_RESOURCE;
    }
}
