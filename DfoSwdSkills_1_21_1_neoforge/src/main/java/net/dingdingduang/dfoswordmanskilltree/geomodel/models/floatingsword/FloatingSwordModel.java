package net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FloatingSwordModel extends GeoModel<FloatingSwordEntity> {
    @Override
    public ResourceLocation getModelResource(FloatingSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.FLOATING_SWORD_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(FloatingSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.FLOATING_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(FloatingSwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.FLOATING_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
