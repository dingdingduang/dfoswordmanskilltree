package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.simplemcsword;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SimpleMCSwordCustomModel extends AnimatedGeoModel<SimpleMCSwordCustomEntity> {
    @Override
    public Identifier getModelLocation(SimpleMCSwordCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(SimpleMCSwordCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(SimpleMCSwordCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
