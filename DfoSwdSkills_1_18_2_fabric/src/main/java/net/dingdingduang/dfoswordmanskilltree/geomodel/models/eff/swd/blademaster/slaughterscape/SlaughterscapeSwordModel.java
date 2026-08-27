package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SlaughterscapeSwordModel extends AnimatedGeoModel<SlaughterscapeSwordEntity> {
    @Override
    public Identifier getModelLocation(SlaughterscapeSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(SlaughterscapeSwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.TEMPEST_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(SlaughterscapeSwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
