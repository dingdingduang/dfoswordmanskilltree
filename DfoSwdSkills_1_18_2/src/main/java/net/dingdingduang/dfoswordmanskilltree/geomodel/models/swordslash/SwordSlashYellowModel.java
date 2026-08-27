package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;

import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwordSlashYellowModel extends AnimatedGeoModel<SwordSlashYellowEntity> {
    @Override
    public ResourceLocation getModelLocation(SwordSlashYellowEntity object) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(SwordSlashYellowEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_YELLOW_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SwordSlashYellowEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
