package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.entity.DfoGeneralEntitySpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientparticlehelper.ClientParticleHelperEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster008;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.setClientPlayerInAction;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getCPlayerState;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker003 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_BLOOD_FURY;

    public static final String Berserker003_CIRCLE_START = "textures/entity/skilleff/berserker/blood_fury/ground_circle_initial/";
    public static final String Berserker003_CIRCLE_ANIM = "textures/entity/skilleff/berserker/blood_fury/ground_circle_start/";
    public static final String Berserker003_PILLAR = "textures/entity/skilleff/berserker/blood_fury/pillar/";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }

        double skillDamageMultiplier = getCPlayerSkillDamage();
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        Double TotalHPConsumptionPercentage = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Float finalDmg = (float) ( ( (DamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_3 * TotalHPConsumptionPercentage) * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);
        Double AOE = skillData.getEffectArea().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("%d%%", (int) (TotalHPConsumptionPercentage.floatValue() * 100)),
                String.format("%s", finalDmg),
                String.format("%s", AOE)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker003::getSkillDescription);

        //Condition
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);

        tempSkillData.setClientCondReqPassedAction((SkillID2) -> {
            KeyboardMethods.KidnapClientPlayerMovementKeybindings();
        });


        if (isClientSide()) {
            return;
        }



        //====================================
        //setup skill action
        MethodAction tempSkillAction =
                (entity1) -> {
                    int playerSkillLVL = 0;

                    float aoeRange;
                    float damageBase;
                    float damageMultiplier;
                    float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(entity1);
                    Predicate<Entity> condition;

//                    int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity1);

                    incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);

                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    float ExtraDamageMultiplier = 0;
                    if (entity1 instanceof ServerPlayer sp1) {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_START, 1);
                        condition = defaultSkillTargetCondition(sp1);

                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                        }

//                        int EXUpwardSlashUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_UPPER_SLASH_UPGRADE);
//                        if (EXUpwardSlashUpgradeLVL > 0) {
//                            SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_UPPER_SLASH_UPGRADE);
//                            ExtraDamageMultiplier = ExtraDamageMultiplier + EX_SkillData.getDamageMultiplierList_01().get(EXUpwardSlashUpgradeLVL - 1).floatValue();
//                        }
                    }
                    else {
                        condition = defaultSkillTargetCondition();
                    }
                    float TotalHPConsumptionPercentage = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();
                    ExtraDamageMultiplier = ExtraDamageMultiplier + BerserkerSkillTreeInitialization.BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_3 * TotalHPConsumptionPercentage;

                    float currentEntityHealth = EntityMethods.getLivingEntityCurrentHP(entity1);
                    float MaxEntityHealth = EntityMethods.getLivingEntityMaxHP(entity1);
                    float hpShouldReduce = MaxEntityHealth * TotalHPConsumptionPercentage;
                    EntityMethods.setLivingEntityCurrentHP(entity1, MathMethods.max(0.01f, currentEntityHealth - hpShouldReduce));

                    float EntityHorizontalFacingAngle = getEntityHorizontalFacingDeg(entity1);
                    Vector3f EntityLoc = getEntityPos(entity1);

                    aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();
                    damageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
                    damageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

                    float effScale = DfoSwordmanSkillMethods.getEntityScaleByAOE(aoeRange);

                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomFlatEntity256(entity1, EntityHorizontalFacingAngle, 255, 255, 255, effScale, 0, 0.1f, 0, 20, false,
                            EntityLoc.x(), EntityLoc.y(), EntityLoc.z(),  EntityLoc.x(), EntityLoc.y(), EntityLoc.z(), Berserker003_CIRCLE_START, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 1, false, 5, 5, 1,
                            true, true, true, null, 777, true);
                    //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    //start Actioning
                    int dmgTicks = 5;
                    int dmgPeriod = 2;
                    int dmgEndTicks = 15;
//                    int dmg = 20;
                    int ResetKeyBoardTick = 10;

                    float finalAoeRange = aoeRange;
                    float finalDmg = (float) ( (damageMultiplier * getEntityAttackDamage(entity1) + damageBase) * skillDamageMultiplier);
                    int actionPeriod = 1;

                    IntegerValueHolder ElapsedTicks = new IntegerValueHolder(0);
                    BooleanValueHolder ResetKeyBoardHolder = new BooleanValueHolder(false);
                    BooleanValueHolder isSpawnedEff = new BooleanValueHolder(false);
                    BooleanValueHolder isInterruptedHolder = new BooleanValueHolder(false);

                    String actionID = SkillID + EntityMethods.getEntityTickCount(entity1);

                    MethodAction action =
                            (entity2) ->  {
                                int updatedTick = ElapsedTicks.getIntCounter();
                                if (updatedTick < dmgEndTicks) {

                                    boolean isInterrupted = ServerSkillMethods.isLivingEntityActionInterrupted(entity2);
                                    isInterruptedHolder.setBooleanVal(isInterrupted);
                                    if (!ResetKeyBoardHolder.getBooleanVal() && (isInterrupted || updatedTick == ResetKeyBoardTick)) {
                                        ResetKeyBoardHolder.setBooleanVal(true);
                                        incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);

                                        if (entity2 instanceof ServerPlayer sp1) {
                                            DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                                        }
                                    }

                                    //spawn model
                                    if (updatedTick == 2) {
                                        //play skill sound at target location
//                                        DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
//                                        DfoSwdSoundMethods.PlayRandomSwordUpperSlashSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, ExtraMathMethods.randomFloat()*0.3f+0.85f);

                                    }

                                    //start damage
                                    if (updatedTick >= dmgTicks && updatedTick % dmgPeriod == 0) {
                                        if (!isSpawnedEff.getBooleanVal()) {
                                            if (entity2 instanceof ServerPlayer sp1) {
                                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_LOOPING, 1);
                                            }
                                            GeoModelSpawnMethods.Generate2DAnimatedTexToNorthCustomEntityYRotOnly256(entity2, EntityHorizontalFacingAngle, true, effScale, 0, DfoSwordmanSkillMethods.getEntityHeightPosByAOE(aoeRange), 0, 15, false,
                                                    EntityLoc.x(), EntityLoc.y(), EntityLoc.z(), EntityLoc.x(), EntityLoc.y(), EntityLoc.z(), Berserker003_PILLAR, DfoSwordmanSkillTreeConstants.NO_ANIMATION, false, 0, 0, 5, true, false, false,
                                                    false, null, 777, true);
                                            isSpawnedEff.setBooleanVal(true);

                                            GeoModelSpawnMethods.Generate2DAnimatedTexCustomFlatEntity256(entity2, EntityHorizontalFacingAngle, 255, 255, 255, effScale, 0, 0.1f, 0, 12, false,
                                                    EntityLoc.x(), EntityLoc.y(), EntityLoc.z(),  EntityLoc.x(), EntityLoc.y(), EntityLoc.z(), Berserker003_CIRCLE_ANIM, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, DfoSwordmanSkillTreeConstants.NO_ANIMATION, 1, false, 5, 5, 6,
                                                    false, false, true, null, 777, true);
                                        }
                                        //spawn model
                                        boolean playHitSound = false;
                                        for (Entity tempEntity: GroupEnumEntitiesInRangeFilter(entity2, finalAoeRange, condition)) {
                                            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                                                if (!playHitSound) {
                                                    playHitSound = true;
                                                    DfoSwdSoundMethods.PlayBooDongHitSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                                                }
                                                pushEntityWithDeltaValueXYZ(tempLivingEntity, 0, 0.22f, 0, true);

                                                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
                                            }
                                        }

                                        DfoGeneralEntitySpawnMethods.SetupClientParticleHelperNoLoc(entity2, 0, aoeRange, 2 * dmgPeriod, EntityLoc.x(), EntityLoc.y(), EntityLoc.z(), DfoSwordmanSkillTreeConstants.NO_ANIMATION, Berserker003_PILLAR_PARTICLE, null, 777, true);
                                    }

                                    //update val
                                    ElapsedTicks.setIntCounter(updatedTick + actionPeriod);
                                }
                                else {
                                    if (!isInterruptedHolder.getBooleanVal() && entity2 instanceof ServerPlayer sp1) {
                                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_END, 1);
                                    }
                                    SBSTickEventMethods.setSkillActionDone(entity2, actionID);
                                }
                            };
                    //Actioning ends
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

                    //start loop actioning with fixed period ticks
                    SBSTickEventMethods.setMethodActionTimer(entity1, actionID, actionPeriod, action);
                };

        tempSkillData.setActiveSkillAction1(tempSkillAction);
    }

    //client
    public static final String Berserker003_PILLAR_PARTICLE = "Berserker003_Pillar";

    public static void Berserker003_ParticleRegistry(HashMap<String, MethodEntityAction> particleMap) {
        particleMap.put(Berserker003_PILLAR_PARTICLE, Berserker003::CreateBloodPillarParticleMethodReference);
    }

    public static void CreateBloodPillarParticleMethodReference(Entity entity) {
        ClientParticleHelperEntity clientParticleHelperEntity = (ClientParticleHelperEntity) entity;
        Vector3f center = getEntityPos(clientParticleHelperEntity);
        float aoeRange = getEntityVerticalFacingDeg(entity);
        Vector3f randPos;
        randPos = Vector3fMethods.PolarProjectionFromVecHorizon(center, ExtraMathMethods.randomFloat(aoeRange), ExtraMathMethods.randomFloat(360));

        Vector3f randomDelta;
        for (int i = 0; i < 30; i++) {
            float randHorizontalFacing = ExtraMathMethods.randomFloat(360f);
            float randVerticalFacing = -ExtraMathMethods.randomFloat(30f) - 60f;
            float randomDist = ExtraMathMethods.randomFloat() * aoeRange;
            int lifetime = (int) (randomDist * 5);

            randomDelta = Vector3fMethods.PolarProjectionFromRotation(randomDist * 0.1f, randHorizontalFacing, randVerticalFacing);
            ParticleMethods.GenerateWhiteParticle(randPos.x(), randPos.y(), randPos.z(),
                    randomDelta.x(), randomDelta.y() + 0.1f, randomDelta.z(),
                    0.33f, 2f + ExtraMathMethods.randomFloat() * 2, lifetime, 0.86f, 0.08f, 0.24f);
//            ParticleMethods.createParticleByTypeAtLoc(ParticleTypes.WHITE_ASH, randPos.x(), randPos.y(), randPos.z(),
//                    0, 0.5f, 0,
//                    0.33f, 1f + ExtraMathMethods.randomFloat(), lifetime, 0.86f, 0.08f, 0.24f);
        }

        randPos = Vector3fMethods.PolarProjectionFromVecHorizon(center, ExtraMathMethods.randomFloat(aoeRange), ExtraMathMethods.randomFloat(360));
        for (int i = 0; i < 30; i++) {
            float randHorizontalFacing = ExtraMathMethods.randomFloat(360f);
            float randVerticalFacing = -ExtraMathMethods.randomFloat(30f) - 60f;
            float randomDist = ExtraMathMethods.randomFloat() * aoeRange;
            int lifetime = (int) (randomDist * 5);

            randomDelta = Vector3fMethods.PolarProjectionFromRotation(randomDist * 0.1f, randHorizontalFacing, randVerticalFacing);
            ParticleMethods.GenerateWhiteParticle(randPos.x(), randPos.y(), randPos.z(),
                    randomDelta.x(), randomDelta.y() + 0.1f, randomDelta.z(),
                    0.33f, 2f + ExtraMathMethods.randomFloat() * 2, lifetime, 0.86f, 0.08f, 0.24f);
//            ParticleMethods.createParticleByTypeAtLoc(ParticleTypes.WHITE_ASH, randPos.x(), randPos.y(), randPos.z(),
//                    0, 0.5f, 0,
//                    0.33f, 1f + ExtraMathMethods.randomFloat(), lifetime, 0.86f, 0.08f, 0.24f);
        }
    }
}
