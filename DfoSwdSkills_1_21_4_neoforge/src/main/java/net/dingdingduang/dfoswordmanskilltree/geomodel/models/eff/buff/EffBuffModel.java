package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class EffBuffModel extends GeoModel<EffBuffEntity> {
    @Override
    public ResourceLocation getModelResource(EffBuffEntity object, GeoRenderer<EffBuffEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(EffBuffEntity object, GeoRenderer<EffBuffEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(EffBuffEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_ANIMATION_FILE_RESOURCE;
    }
}
