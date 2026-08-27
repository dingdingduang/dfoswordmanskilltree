package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.customtotalframesonly;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class SwordSlashCustomTotalFrameOnlyModel extends GeoModel<SwordSlashCustomTotalFrameOnlyEntity> {
    @Override
    public Identifier getModelResource(SwordSlashCustomTotalFrameOnlyEntity object, GeoRenderer<SwordSlashCustomTotalFrameOnlyEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(SwordSlashCustomTotalFrameOnlyEntity object, GeoRenderer<SwordSlashCustomTotalFrameOnlyEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_PURPLE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(SwordSlashCustomTotalFrameOnlyEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
