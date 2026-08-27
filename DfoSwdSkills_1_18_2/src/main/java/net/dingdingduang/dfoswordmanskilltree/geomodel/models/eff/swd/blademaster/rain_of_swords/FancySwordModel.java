package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class FancySwordModel extends AnimatedGeoModel<FancySwordEntity> {
    @Override
    public ResourceLocation getModelLocation(FancySwordEntity object) {
        return getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, object.getGeoModelName());
    }

    @Override
    public ResourceLocation getTextureLocation(FancySwordEntity object) {
        return DfoSwdGeneralGeoEntityResources.FANCY_SWORD_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FancySwordEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.FANCY_SWORD_ANIMATION_FILE_RESOURCE;
    }
}
