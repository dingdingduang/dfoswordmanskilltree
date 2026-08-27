package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistryName;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChannelingBarModel extends AnimatedGeoModel<ChannelingBarEntity> {
    @Override
    public ResourceLocation getModelLocation(ChannelingBarEntity object) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_BAR_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(ChannelingBarEntity object) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_BAR_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ChannelingBarEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_BAR_ANIMATION_FILE_RESOURCE;
    }
}
