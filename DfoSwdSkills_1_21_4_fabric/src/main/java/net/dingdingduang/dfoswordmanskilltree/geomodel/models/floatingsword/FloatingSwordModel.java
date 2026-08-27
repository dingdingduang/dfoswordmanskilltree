package net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class FloatingSwordModel extends GeoModel<FloatingSwordEntity> {
    @Override
    public Identifier getModelResource(FloatingSwordEntity object, GeoRenderer<FloatingSwordEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.FLOATING_SWORD_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(FloatingSwordEntity object, GeoRenderer<FloatingSwordEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.FLOATING_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(FloatingSwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.FLOATING_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
