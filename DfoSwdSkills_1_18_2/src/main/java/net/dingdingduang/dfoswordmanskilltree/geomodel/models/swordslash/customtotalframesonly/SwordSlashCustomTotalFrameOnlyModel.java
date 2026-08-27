package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.customtotalframesonly;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwordSlashCustomTotalFrameOnlyModel extends AnimatedGeoModel<SwordSlashCustomTotalFrameOnlyEntity> {
    @Override
    public ResourceLocation getModelLocation(SwordSlashCustomTotalFrameOnlyEntity object) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureLocation(SwordSlashCustomTotalFrameOnlyEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_PURPLE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SwordSlashCustomTotalFrameOnlyEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
