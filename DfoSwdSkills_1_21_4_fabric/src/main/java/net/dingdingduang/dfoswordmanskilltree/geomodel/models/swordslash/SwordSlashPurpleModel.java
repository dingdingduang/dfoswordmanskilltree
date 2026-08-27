package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class SwordSlashPurpleModel extends GeoModel<SwordSlashPurpleEntity> {
    @Override
    public Identifier getModelResource(SwordSlashPurpleEntity object, GeoRenderer<SwordSlashPurpleEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(SwordSlashPurpleEntity object, GeoRenderer<SwordSlashPurpleEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_PURPLE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(SwordSlashPurpleEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
