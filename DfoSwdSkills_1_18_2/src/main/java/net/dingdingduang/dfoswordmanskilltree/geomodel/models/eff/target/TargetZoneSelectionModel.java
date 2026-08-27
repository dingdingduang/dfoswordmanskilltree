package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TargetZoneSelectionModel extends AnimatedGeoModel<TargetZoneSelectionEntity> {
    @Override
    public ResourceLocation getModelLocation(TargetZoneSelectionEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_FLAT_HORIZON_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(TargetZoneSelectionEntity object) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TargetZoneSelectionEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_FLAT_HORIZON_ANIMATION_FILE_RESOURCE;
    }
}
