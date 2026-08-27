package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DrawSwordQiModel extends GeoModel<DrawSwordQiEntity> {
    @Override
    public Identifier getModelResource(DrawSwordQiEntity object) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(DrawSwordQiEntity object) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(DrawSwordQiEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_ANIMATION_FILE_RESOURCE;
    }
}
