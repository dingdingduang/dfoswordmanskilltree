package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.flat;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwoDAnimated256FlatTexCustomModel extends AnimatedGeoModel<TwoDAnimated256FlatTexCustomEntity> {
    @Override
    public Identifier getModelLocation(TwoDAnimated256FlatTexCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_FLAT_HORIZON_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(TwoDAnimated256FlatTexCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.DRAW_SWORD_QI_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(TwoDAnimated256FlatTexCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_FLAT_HORIZON_ANIMATION_FILE_RESOURCE;
    }
}
