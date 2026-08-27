package net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistryName;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class ChannelingIconModel extends GeoModel<ChannelingIconEntity> {
    @Override
    public Identifier getModelResource(ChannelingIconEntity object, GeoRenderer<ChannelingIconEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_ICON_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(ChannelingIconEntity object, GeoRenderer<ChannelingIconEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.NO_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(ChannelingIconEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.CHANNELING_ICON_ANIMATION_FILE_RESOURCE;
    }
}
