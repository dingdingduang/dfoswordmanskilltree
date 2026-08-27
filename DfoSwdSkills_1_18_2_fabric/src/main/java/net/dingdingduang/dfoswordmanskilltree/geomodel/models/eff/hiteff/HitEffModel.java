package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HitEffModel extends AnimatedGeoModel<HitEffEntity> {
    @Override
    public Identifier getModelLocation(HitEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(HitEffEntity object) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(HitEffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.HIT_EFF_ANIMATION_FILE_RESOURCE;
    }
}
