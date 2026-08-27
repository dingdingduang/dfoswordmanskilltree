package net.dingdingduang.dfoswordmanskilltree.particle;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MCParticleRegistry {
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DfoSwordmanSkillTreeConstants.MOD_ID);

    //    public static final RegistryObject<SimpleParticleType> PARTICLE_EXTENSION = PARTICLE_TYPES.register("dfo_particle_extension", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GEO_TRAIL_PLANE_PARTICLE_TYPE = PARTICLE_TYPES.register("dfo_geo_trail_plane_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
