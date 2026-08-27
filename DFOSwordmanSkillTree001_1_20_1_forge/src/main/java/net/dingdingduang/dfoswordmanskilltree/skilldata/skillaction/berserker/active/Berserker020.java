package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.entity.DfoGeneralEntitySpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientparticlehelper.ClientParticleHelperEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.registries.SoundRegistry;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isEntityChannellingInterrupted;
import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.PlaySoundAtLivingEntityLocationEX;
import static net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor.applyTimedAttackDMGToEntity;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker020 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_BLOODY_WRAITH;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        Double TotalHPConsumptionPercentage = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Float finalDmg = (float) ( ( (DamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_8 * TotalHPConsumptionPercentage) * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);
        Double AOE = skillData.getEffectArea().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%s", finalDmg),
                String.format("%s", AOE),
                String.format("%d%%", (int) (TotalHPConsumptionPercentage.floatValue() * 100))
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker020::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);

        MethodAction tempSkillAction = (entity1) -> {
                    //condition, if skill is interrupted, do not execute final action
                    if (!isEntityChannellingInterrupted(entity1)) {
                        int playerSkillLVL = 0;
                        SkillDataJson skill1 = getID2SkillData().get(SkillID);
                        float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);
                        Predicate<Entity> condition;

                        float ExtraDamageMultiplier = 0;
                        if (entity1 instanceof ServerPlayer sp1) {
                            playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                            if (playerSkillLVL <= 0) {
                                return;
                            }
                            playerSkillLVL = playerSkillLVL - 1;
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_END);
                            condition = defaultSkillTargetCondition(sp1);
                        }
                        else {
                            condition = defaultSkillTargetCondition();
                        }
                        float TotalHPConsumption = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();
                        ExtraDamageMultiplier = ExtraDamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_8 * TotalHPConsumption;

                        float MaxEntityHealth = EntityMethods.getLivingEntityMaxHP(entity1);
                        float hpShouldReduce = MaxEntityHealth * TotalHPConsumption;
                        float currentEntityHealth = EntityMethods.getLivingEntityCurrentHP(entity1) - hpShouldReduce;

                        EntityMethods.setLivingEntityCurrentHP(entity1, MathMethods.max(0.01f, currentEntityHealth));

                        float aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();

                        Vector3f triggeredEntityPos = getEntityPos(entity1);
                        float triggeredEntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity1);
                        DfoGeneralEntitySpawnMethods.SetupClientParticleHelperBoundToEntity(entity1, triggeredEntityHorizontalFacingAngle, aoeRange, 5, 0, 0.5f, 0, DfoSwordmanSkillTreeConstants.NO_ANIMATION, Berserker020_BoundToEntityDischarge, null, 777, true);
                        DfoGeneralEntitySpawnMethods.SetupClientParticleHelperNoLoc(entity1, 0, aoeRange, 1, triggeredEntityPos.x(), triggeredEntityPos.y(), triggeredEntityPos.z(), DfoSwordmanSkillTreeConstants.NO_ANIMATION, Berserker020_Discharge, null, 777, true);

                        GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256(entity1,255, 255, 255, DfoSwordmanSkillMethods.getEntityScaleByAOE(aoeRange), 0, 1.6f, 0, 20, false,
                                0, 0, 0, 0, 0, 0, Berserker017.BERSERKER17_BLOOD_SWORD_EXPLOSION_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                DfoSwordmanSkillTreeConstants.NO_ANIMATION, false, 0, 0, 20, false, false, true, null, 777, true);

                        float damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                        float damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                        float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);

                        //start damage
                        Vector3f targetPos;
                        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity1, aoeRange, condition)) {
                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                targetPos = getEntityPos(tempLivingEntity);
                                DfoSwdSoundMethods.PlayBooDongHitSound(getEntityLevel(entity1), targetPos.x(), targetPos.y(), targetPos.z(), 0.8f, 1f);

                                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity1, tempLivingEntity, finalDmg, false);

                                DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(entity1, tempLivingEntity, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, finalDmg * DfoStatusGeneralMethods.BLEEDING_DEFAULT_DAMAGE_MULTIPLIER_2);

                                float pushXZStrength = EntityMethods.calculatePushEntityStrength(1.5f, entity1, tempLivingEntity);
                                float angleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(triggeredEntityPos, targetPos);
                                EntityMethods.pushEntityWithDeltaValue(tempLivingEntity, angleBtn, pushXZStrength, 0, true);
                            }
                        }
                    }
                    else {
                        PlaySoundAtLivingEntityLocationEX(entity1, SoundRegistry.CHANNELING_FAILED_SOUND.get(), 0.4f, 1.0f);
                    }
                };

        tempSkillData.setActiveSkillAction1(tempSkillAction);
    }

    //client
    public static final String Berserker020_BoundToEntityDischarge = "Berserker020_BoundToEntityDischarge";
    public static final String Berserker020_Discharge = "Berserker020_Discharge";

    public static void Berserker020_ParticleRegistry(HashMap<String, MethodEntityAction> particleMap) {
        particleMap.put(Berserker020_BoundToEntityDischarge, Berserker020::CreateParticleBoundToEntityDischargeMethodReference);
        particleMap.put(Berserker020_Discharge, Berserker020::CreateDischargeMethodReference);
    }

    public static final float Berserker020_PARTICLE_RED_COLOR_RATIO = 0.5f;
    public static final int Berserker020_PARTICLE_MAX_PARTICLE_NUM_PER_LOOP = 8;
    public static final float Berserker020_PARTICLE_INTERVAL = 1f / Berserker020_PARTICLE_MAX_PARTICLE_NUM_PER_LOOP;

    public static void CreateParticleBoundToEntityDischargeMethodReference(Entity entity) {
        ClientParticleHelperEntity clientParticleHelperEntity = (ClientParticleHelperEntity) entity;
        float AOE = getEntityVerticalFacingDeg(clientParticleHelperEntity);
        Vector3f center = getEntityPos(clientParticleHelperEntity);

        Vector3f ownerDelta = null;
        if (clientParticleHelperEntity.getOwner() != null) {
            ownerDelta = getEntityDeltaMovement(clientParticleHelperEntity.getOwner());
        }

        float particleHelperHorizontalFacingAngle = getEntityHorizontalFacingDeg(clientParticleHelperEntity);
        setEntityHorizontalFacingDeg(clientParticleHelperEntity, particleHelperHorizontalFacingAngle + 5f);

        Vector3f deltaVec;
        float ratio, remainingRedColorRatio, velocity;
        int lifetime;
        for (int loopA = 0; loopA < 360; loopA = loopA + 60) {
            for (int i = 0; i < Berserker020_PARTICLE_MAX_PARTICLE_NUM_PER_LOOP; i++) {
                ratio = i * Berserker020_PARTICLE_INTERVAL;
                remainingRedColorRatio = 0.5f * ratio;
                velocity = (Berserker020_PARTICLE_MAX_PARTICLE_NUM_PER_LOOP - i) * Berserker020_PARTICLE_INTERVAL * 2 + 0.5f + AOE * 0.5f;
                lifetime = (int) (velocity * 5);
                deltaVec = Vector3fMethods.PolarProjectionFromRotation(velocity * -0.1f, particleHelperHorizontalFacingAngle + loopA, 0);

                if (ownerDelta != null) {
                    deltaVec = deltaVec.add(ownerDelta);
                }

                ParticleMethods.GenerateWhiteParticle(center.x(), center.y() + 1.4f, center.z(),
                        deltaVec.x(), deltaVec.y(), deltaVec.z(),
                        0.33f, Berserker020_PARTICLE_MAX_PARTICLE_NUM_PER_LOOP * (Berserker020_PARTICLE_MAX_PARTICLE_NUM_PER_LOOP - i) * ratio + 1f, lifetime, Berserker020_PARTICLE_RED_COLOR_RATIO + remainingRedColorRatio, 0.08f * ratio, 0.24f * ratio);

                ratio = i * Berserker020_PARTICLE_INTERVAL;
                remainingRedColorRatio = 0.5f * ratio;
                velocity = (Berserker020_PARTICLE_MAX_PARTICLE_NUM_PER_LOOP - i) * Berserker020_PARTICLE_INTERVAL * 2 + 0.5f + AOE * 0.5f;
                lifetime = (int) (velocity * 5);
                deltaVec = Vector3fMethods.PolarProjectionFromRotation(velocity * -0.1f, (particleHelperHorizontalFacingAngle - 10f * (clientParticleHelperEntity.getMaxLifetime() - clientParticleHelperEntity.getLifetime())) - loopA, 0);

                if (ownerDelta != null) {
                    deltaVec = deltaVec.add(ownerDelta);
                }

                ParticleMethods.GenerateWhiteParticle(center.x(), center.y() + 1.4f, center.z(),
                        deltaVec.x(), deltaVec.y(), deltaVec.z(),
                        0.33f, Berserker020_PARTICLE_MAX_PARTICLE_NUM_PER_LOOP * (Berserker020_PARTICLE_MAX_PARTICLE_NUM_PER_LOOP - i) * ratio + 1f, lifetime, Berserker020_PARTICLE_RED_COLOR_RATIO + remainingRedColorRatio, 0.08f * ratio, 0.24f * ratio);
            }
        }
    }

    public static void CreateDischargeMethodReference(Entity entity) {
        ClientParticleHelperEntity clientParticleHelperEntity = (ClientParticleHelperEntity) entity;
        Vector3f fromPos = getEntityPos(clientParticleHelperEntity);

        float aoeRange = getEntityVerticalFacingDeg(entity);

        Vector3f randomDelta;
        for (int i = 0; i < 50; i++) {
            randomDelta = Vector3fMethods.PolarProjectionFromRotation(0.1f + aoeRange * ExtraMathMethods.randomFloat() * 0.1f, ExtraMathMethods.randomFloat(360), ExtraMathMethods.randomFloat(60) - 30);
            ParticleMethods.GenerateWhiteParticle(fromPos.x(), fromPos.y()+1.4f, fromPos.z(),
                    randomDelta.x(), randomDelta.y(), randomDelta.z(),
                    0.33f, 8f, 30, 0.86f, 0.08f, 0.24f);
        }
    }
}
