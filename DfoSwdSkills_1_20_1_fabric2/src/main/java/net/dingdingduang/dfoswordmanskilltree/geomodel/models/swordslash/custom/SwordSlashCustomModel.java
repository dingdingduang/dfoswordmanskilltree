package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.custom;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SwordSlashCustomModel extends GeoModel<SwordSlashCustomEntity> {
    @Override
    public Identifier getModelResource(SwordSlashCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(SwordSlashCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_PURPLE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(SwordSlashCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
