package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.stab;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EffStabModel extends AnimatedGeoModel<EffStabEntity> {
    @Override
    public ResourceLocation getModelLocation(EffStabEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STAB_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(EffStabEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STAB_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EffStabEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_ANIMATION_FILE_RESOURCE;
    }
}
