package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DrawSwordQiModel extends AnimatedGeoModel<DrawSwordQiEntity> {
    @Override
    public Identifier getModelLocation(DrawSwordQiEntity object) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(DrawSwordQiEntity object) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(DrawSwordQiEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_ANIMATION_FILE_RESOURCE;
    }
}
