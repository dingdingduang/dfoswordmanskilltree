package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DrawSwordQiModel extends AnimatedGeoModel<DrawSwordQiEntity> {
    @Override
    public ResourceLocation getModelLocation(DrawSwordQiEntity object) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(DrawSwordQiEntity object) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DrawSwordQiEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_ANIMATION_FILE_RESOURCE;
    }
}
