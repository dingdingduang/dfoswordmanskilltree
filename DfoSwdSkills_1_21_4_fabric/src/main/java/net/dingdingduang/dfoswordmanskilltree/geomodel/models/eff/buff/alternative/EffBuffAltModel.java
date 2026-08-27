package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.alternative;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class EffBuffAltModel extends GeoModel<EffBuffAltEntity> {
    @Override
    public Identifier getModelResource(EffBuffAltEntity object, GeoRenderer<EffBuffAltEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.PLURAL_EFF_BUFF_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(EffBuffAltEntity object, GeoRenderer<EffBuffAltEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.ONE_EFF_BUFF_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(EffBuffAltEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.PLURAL_EFF_BUFF_ANIMATION_FILE_RESOURCE;
    }
}
