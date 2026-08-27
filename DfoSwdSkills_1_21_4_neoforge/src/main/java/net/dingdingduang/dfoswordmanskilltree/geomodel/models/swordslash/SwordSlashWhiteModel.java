package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class SwordSlashWhiteModel extends GeoModel<SwordSlashWhiteEntity> {
    @Override
    public ResourceLocation getModelResource(SwordSlashWhiteEntity object, GeoRenderer<SwordSlashWhiteEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(SwordSlashWhiteEntity object, GeoRenderer<SwordSlashWhiteEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_PURPLE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(SwordSlashWhiteEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
