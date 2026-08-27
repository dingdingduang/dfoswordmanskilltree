package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SwordSlashPurpleModel extends GeoModel<SwordSlashPurpleEntity> {
    @Override
    public ResourceLocation getModelResource(SwordSlashPurpleEntity object) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(SwordSlashPurpleEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_PURPLE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(SwordSlashPurpleEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
