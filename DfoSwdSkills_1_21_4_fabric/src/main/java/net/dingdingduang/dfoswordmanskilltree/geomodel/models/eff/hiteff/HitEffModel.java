package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class HitEffModel extends GeoModel<HitEffEntity> {
    @Override
    public Identifier getModelResource(HitEffEntity object, GeoRenderer<HitEffEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(HitEffEntity object, GeoRenderer<HitEffEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(HitEffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_ANIMATION_FILE_RESOURCE;
    }
}
