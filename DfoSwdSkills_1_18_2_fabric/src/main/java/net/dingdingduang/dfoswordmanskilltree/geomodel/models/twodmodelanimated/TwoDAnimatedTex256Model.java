package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwoDAnimatedTex256Model extends AnimatedGeoModel<TwoDAnimatedTex256Entity> {
    @Override
    public Identifier getModelLocation(TwoDAnimatedTex256Entity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_256_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(TwoDAnimatedTex256Entity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(TwoDAnimatedTex256Entity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
