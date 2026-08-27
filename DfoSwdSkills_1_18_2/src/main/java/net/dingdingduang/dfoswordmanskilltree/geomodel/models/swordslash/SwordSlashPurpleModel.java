package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwordSlashPurpleModel extends AnimatedGeoModel<SwordSlashPurpleEntity> {
    @Override
    public ResourceLocation getModelLocation(SwordSlashPurpleEntity object) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(SwordSlashPurpleEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_PURPLE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SwordSlashPurpleEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
