package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class FancySwordModel extends GeoModel<FancySwordEntity> {
    @Override
    public ResourceLocation getModelResource(FancySwordEntity object, GeoRenderer<FancySwordEntity> geoRenderer) {
        return getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, object.getGeoModelName());
    }

    @Override
    public ResourceLocation getTextureResource(FancySwordEntity object, GeoRenderer<FancySwordEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.FANCY_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(FancySwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.FANCY_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
