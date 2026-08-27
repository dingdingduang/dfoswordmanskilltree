package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.movingpos;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwoDAnimatedTex256ManipulateYCustomModel extends AnimatedGeoModel<TwoDAnimatedTex256ManipulateYCustomEntity> {
    @Override
    public Identifier getModelLocation(TwoDAnimatedTex256ManipulateYCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_CUSTOM_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(TwoDAnimatedTex256ManipulateYCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(TwoDAnimatedTex256ManipulateYCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_CUSTOM_ANIMATION_FILE_RESOURCE;
    }
}
