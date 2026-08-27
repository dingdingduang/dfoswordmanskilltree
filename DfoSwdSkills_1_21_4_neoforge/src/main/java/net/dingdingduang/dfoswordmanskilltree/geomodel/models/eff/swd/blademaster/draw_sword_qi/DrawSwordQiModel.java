package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class DrawSwordQiModel extends GeoModel<DrawSwordQiEntity> {
    @Override
    public ResourceLocation getModelResource(DrawSwordQiEntity object, GeoRenderer<DrawSwordQiEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(DrawSwordQiEntity object, GeoRenderer<DrawSwordQiEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(DrawSwordQiEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_ANIMATION_FILE_RESOURCE;
    }
}
