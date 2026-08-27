package net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwoDAnimatedTex64Model extends AnimatedGeoModel<TwoDAnimatedTex64Entity> {
    @Override
    public Identifier getModelLocation(TwoDAnimatedTex64Entity object) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_ANIMATED_64_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(TwoDAnimatedTex64Entity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(TwoDAnimatedTex64Entity animatable) {
        return DfoSwdGeneralGeoEntityResources.TWO_D_64_PX_ANIMATION_FILE_RESOURCE;
    }
}
