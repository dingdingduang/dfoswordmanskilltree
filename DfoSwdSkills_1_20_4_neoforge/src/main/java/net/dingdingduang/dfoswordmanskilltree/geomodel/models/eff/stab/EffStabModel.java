package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.stab;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EffStabModel extends GeoModel<EffStabEntity> {
    @Override
    public ResourceLocation getModelResource(EffStabEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STAB_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(EffStabEntity object) {
        return DfoSwdGeneralGeoEntityResources.EFF_STAB_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(EffStabEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.EFF_GROUND_QUAKE_ANIMATION_FILE_RESOURCE;
    }
}
