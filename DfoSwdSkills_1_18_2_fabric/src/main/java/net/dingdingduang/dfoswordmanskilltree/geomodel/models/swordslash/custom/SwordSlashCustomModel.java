package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.custom;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwordSlashCustomModel extends AnimatedGeoModel<SwordSlashCustomEntity> {
    @Override
    public Identifier getModelLocation(SwordSlashCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(SwordSlashCustomEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_PURPLE_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(SwordSlashCustomEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
