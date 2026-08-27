package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HitEffModel extends GeoModel<HitEffEntity> {
    @Override
    public ResourceLocation getModelResource(HitEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(HitEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(HitEffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_ANIMATION_FILE_RESOURCE;
    }
}
