package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.simplemcsword;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SimpleMCSwordCustomModel extends GeoModel<SimpleMCSwordCustomEntity> {
    @Override
    public ResourceLocation getModelResource(SimpleMCSwordCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(SimpleMCSwordCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(SimpleMCSwordCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
