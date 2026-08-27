package net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientparticlehelper;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ClientParticleHelperEntityModel extends GeoModel<ClientParticleHelperEntity> {
    @Override
    public ResourceLocation getModelResource(ClientParticleHelperEntity object) {
        return DfoSwdGeneralGeoEntityResources.NULL_ENTITY_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(ClientParticleHelperEntity object) {
        return DfoSwdGeneralGeoEntityResources.NULL_ENTITY_TEXTURE_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(ClientParticleHelperEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.NULL_ENTITY_ANIMATION_RESOURCE;
    }
}
