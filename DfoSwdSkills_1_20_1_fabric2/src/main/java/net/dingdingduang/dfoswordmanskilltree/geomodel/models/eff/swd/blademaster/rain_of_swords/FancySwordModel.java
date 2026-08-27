package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class FancySwordModel extends GeoModel<FancySwordEntity> {
    @Override
    public Identifier getModelResource(FancySwordEntity object) {
        //TODO: [!]
        return getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, object.getGeoModelName());
    }

    @Override
    public Identifier getTextureResource(FancySwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.FANCY_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(FancySwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.FANCY_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
