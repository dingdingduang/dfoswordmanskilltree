package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodel;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwoDModel extends AnimatedGeoModel<TwoDEntity> {
    @Override
    public Identifier getModelLocation(TwoDEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(TwoDEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(TwoDEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
