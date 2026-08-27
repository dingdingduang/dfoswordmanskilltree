package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.util.Identifier;

import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwordSlashYellowModel extends AnimatedGeoModel<SwordSlashYellowEntity> {
    @Override
    public Identifier getModelLocation(SwordSlashYellowEntity object) {
        return DfoSwdGeneralGeoEntityResources.SLASH_NEW_2_MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureLocation(SwordSlashYellowEntity object) {
        return DfoSwdGeneralGeoEntityResources.SWORD_SLASH_YELLOW_TEXTURE_PNG_RESOURCE;
    }

    @Override
    public Identifier getAnimationFileLocation(SwordSlashYellowEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.SLASH_ANIMATION_FILE_RESOURCE;
    }
}
