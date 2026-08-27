package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.simplemcsword;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class SimpleMCSwordCustomModel extends GeoModel<SimpleMCSwordCustomEntity> {
    @Override
    public Identifier getModelResource(SimpleMCSwordCustomEntity object, GeoRenderer<SimpleMCSwordCustomEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(SimpleMCSwordCustomEntity object, GeoRenderer<SimpleMCSwordCustomEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(SimpleMCSwordCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SIMPLE_MC_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
