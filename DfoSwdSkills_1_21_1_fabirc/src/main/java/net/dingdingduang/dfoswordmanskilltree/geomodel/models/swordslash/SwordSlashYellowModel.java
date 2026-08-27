package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;

import software.bernie.geckolib.model.GeoModel;

public class SwordSlashYellowModel extends GeoModel<SwordSlashYellowEntity> {
    @Override
    public Identifier getModelResource(SwordSlashYellowEntity object) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(SwordSlashYellowEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_YELLOW_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(SwordSlashYellowEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
