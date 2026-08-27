package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwordSlashWhiteModel extends AnimatedGeoModel<SwordSlashWhiteEntity> {
    @Override
    public Identifier getModelLocation(SwordSlashWhiteEntity object) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(SwordSlashWhiteEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_PURPLE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(SwordSlashWhiteEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
