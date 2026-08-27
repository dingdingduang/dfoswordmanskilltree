package net.dingdingduang.dfoswordmanskilltree.particle;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegistryProviderEvent {
    @SubscribeEvent
    public static void onRegisterParticleProvidersEvent(RegisterParticleProvidersEvent event) {
//        event.registerSpriteSet(PARTICLE_EXTENSION.get(), ParticleExtension.ParticleExtensionSprite::new);
        event.registerSpriteSet(MCParticleRegistry.GEO_TRAIL_PLANE_PARTICLE_TYPE.get(), GeoTrailPlaneParticle.GeoTrailPlaneParticleSprite::new);
    }
}
