package net.dingdingduang.dfoswordmanskilltree.particle;

import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import java.util.HashMap;

public class ParticleRegistry {
//    public static String HIT_PARTICLE = "hitparticle";
//    public static String BENEFICIAL_PARTICLE = "beneficialparticle";
    public static String CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND = "createterrainparticletowhereentitystand";

    private static HashMap<String, MethodEntityAction> ParticleName2Action = new HashMap<>();

    public static HashMap<String, MethodEntityAction> getParticleName2Action() { return ParticleName2Action; }
    public static void setParticleName2Action(HashMap<String, MethodEntityAction> particleName2Action) { ParticleName2Action = particleName2Action; }

    public static void ParticleRegistryInit() {
        ParticleName2Action.put(CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, ParticleMethods::CreateTerrainParticleToWhereEntityStandMethodReference);
//        if (ParticleName2Action == null) {
//            ParticleName2Action.put(HIT_PARTICLE, (entity) -> {
//                ParticleMethods.GenerateHitParticle(getEntityPos(entity), ExtraMathMethods.randomInt(5) + 5);
//            });
//            ParticleName2Action.put(BENEFICIAL_PARTICLE, (entity) -> {
//                Vector3f entityPos = getEntityPos(entity), deltaMov = getEntityDeltaMovement(entity);
//                Vector3f particlePos, particleDeltaPos;
//                for (int i = 0; i < 3; i++) {
//                    particlePos = Vector3fMethods.randomVecIn2DCircle(1f, entityPos);
////            particleDeltaPos = Vector3fMethods.PolarProjectionFromVecHorizon(particlePos, 0.2f, Vector3fMethods.AngleFromLocationToLocationHorizontal(entityPos, particlePos)).sub(particlePos);
//                    particleDeltaPos = Vector3fMethods.PolarProjectionFromVecHorizon(particlePos, 0.015f, Vector3fMethods.AngleFromLocationToLocationHorizontal(entityPos, particlePos)).sub(particlePos);
//                    ParticleMethods.GenerateBeneficialBuffParticle(particlePos.x(), particlePos.y() + 0.15f, particlePos.z(),
//                            deltaMov.x() + particleDeltaPos.x(), deltaMov.y() + 0.18f, deltaMov.z() + particleDeltaPos.z());
//                }
//            });
//            ParticleName2Action.put(CREATE_TERRAIN_PARTICLE_TO_WHERE_ENTITY_STAND, (entity) -> {
//                ParticleMethods.createTerrainParticleToWhereEntityStand(entity, ExtraMathMethods.randomInt(2)+1);
//            });
//        }
    }

    public static void ParticleMapClear() {
        ParticleName2Action.clear();
    }

    public static MethodEntityAction getParticleMethod(String particleMethodName) {
        return ParticleName2Action.get(particleMethodName);
    }
}
