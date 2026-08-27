package net.dingdingduang.dfoswordmanskilltree.entity;

import net.dingdingduang.dfoswordmanskilltree.entity.clientmovementhelper.ClientMovementHelper;
import net.dingdingduang.dfoswordmanskilltree.entity.clientmovementhelper.ClientMovementHelperRenderer;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockEntitiesRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.MOD_ID;
import static net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelRegistry.setupEntityTypeBuilder;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class DfoEntitiesRegistry {
    public static final EntityType<ClientMovementHelper> CLIENT_MOVEMENT_HELPER = setupEntityTypeBuilder(ClientMovementHelper::new, SpawnGroup.MISC, 0, 0, getMCResourceLocation(MOD_ID, DfoMinecraftDefaultEntitiesRegistryConstants.CLIENT_MOVEMENT_HELPER_STR_ID) );

    public static void registerRendererOnClient() {
        EntityRendererRegistry.register(DfoEntitiesRegistry.CLIENT_MOVEMENT_HELPER, ClientMovementHelperRenderer::new);
        //tilt block method
        BlockEntityRendererRegistry.register(TiltBlockEntitiesRegistry.TILT_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT, TiltBlockRenderer::new);
    }
}
