package net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientmovementhelper;

import net.dingdingduang.dfoswordmanskilltree.geomodel.DfoSwdGeneralGeoEntityResources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class ClientMovementHelperEntityModel extends GeoModel<ClientMovementHelperEntity> {
    @Override
    public ResourceLocation getModelResource(ClientMovementHelperEntity object, GeoRenderer<ClientMovementHelperEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.NULL_ENTITY_MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(ClientMovementHelperEntity object, GeoRenderer<ClientMovementHelperEntity> geoRenderer) {
        return DfoSwdGeneralGeoEntityResources.NULL_ENTITY_TEXTURE_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(ClientMovementHelperEntity animatable) {
        return DfoSwdGeneralGeoEntityResources.NULL_ENTITY_ANIMATION_RESOURCE;
    }
}
