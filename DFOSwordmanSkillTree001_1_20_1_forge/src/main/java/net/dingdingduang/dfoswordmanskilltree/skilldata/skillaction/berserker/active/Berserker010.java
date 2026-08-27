package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.custom.TwoDAnimated256TexCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.Blademaster039;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.FloatValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.TypeValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
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
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.setEntitySPDeltaMovement;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.PlaySoundAtLivingEntityLocationEX;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues.getSLivingEntityState;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker010 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_BLOOD_FRENZY;
    public static final String ChannelingActionID = SkillID + "_ChannelingActionID";

    public static final String BERSERKER010_FRENZY_ATTACK_ACTION = "BERSERKER010_FRENZY_ATTACK_ACTION";

    public static final String BERSERKER010_FRENZY_CHANNELING_START = "frenzy_channeling_start";
    public static final String BERSERKER010_FRENZY_CHANNELING_LOOPING = "frenzy_channeling_looping";
    public static final String BERSERKER010_FRENZY_CHANNELING_END = "frenzy_channeling_end";

    public static final String BERSERKER010_FRENZY_BLOOD_ORB = "textures/entity/skilleff/berserker/frenzy/blood_energy/entity/";
    public static final String BERSERKER010_FRENZY_CHARGING = "textures/entity/skilleff/berserker/frenzy/charging/";

    public static final String BERSERKER010_FRENZY_BUFF = "textures/entity/skilleff/berserker/frenzy/blood_buff2/";

    public static final int BERSERKER010_UPDATE_PERIOD = 200;

    public static final float BERSERKER010_MINIMUM_FRENZY_HP_PERCENTAGE = 0.05f;

    public static final String[] BERSERKER010_FRENZY_ATTACK_ANIMATION = {"frenzy_attack_slash_1", "frenzy_attack_slash_2", "frenzy_attack_slash_3", "frenzy_attack_slash_4", "frenzy_attack_slash_1"};
    public static final int[][] BERSERKER010_FRENZY_DAMAGE_TICK = {{2, 4}, {2, 4}, {2, 2}, {2, 3}};
    public static final float[][] BERSERKER010_FRENZY_SLASH_EFF_ROT = {{315f, 285f}, {95f, 30f}, {225f, 5f}, {60f, 5f}};

    //client
    public static final String Berserker010_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_ACTION = "Berserker010_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_ACTION";

    private static final float presetDist1 = -0.04f;
    private static final float initialVelocity = 0.1f;
    private static final float distShouldTravelDecay = 0.01f;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }

        double skillDamageMultiplier = getCPlayerSkillDamage();
        float CurrentEntityHealth = getLivingEntityCurrentHP(getClientPlayer());
        float MaxEntityHealth = getLivingEntityMaxHP(getClientPlayer());
        float DamagePercentageBasedOnRemainingHP = (MaxEntityHealth - CurrentEntityHealth) / MaxEntityHealth;

        Double TotalHPConsumptionEveryTenSecs = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Double HPRestorationPercentage = skillData.getValueMultiplierList_02().get(correctSkillLevel);
        float MaxExtraDamageMultiplierBasedOnMaxHP = skillData.getDamageMultiplierList_03().get(correctSkillLevel).floatValue();

        Double MainHandDamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double MainHandConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);

        Float finalMainHandDmg = (float) ( ( (MainHandDamageMultiplier + MaxExtraDamageMultiplierBasedOnMaxHP * DamagePercentageBasedOnRemainingHP) * getEntityAttackDamage(getClientPlayer()) + MainHandConstantDamage) * skillDamageMultiplier);

        Double OffHandDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double OffHandConstantDamage = skillData.getDamageList_02().get(correctSkillLevel);

        Float finalOffHandDmg = (float) ( ( (OffHandDamageMultiplier + MaxExtraDamageMultiplierBasedOnMaxHP * DamagePercentageBasedOnRemainingHP) * getEntityAttackDamage(getClientPlayer()) + OffHandConstantDamage) * skillDamageMultiplier);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (TotalHPConsumptionEveryTenSecs.floatValue() * 100)),
                String.format("%d%%", (int) (HPRestorationPercentage.floatValue() * 100)),
                String.format("%d%%", (int) (MaxExtraDamageMultiplierBasedOnMaxHP * 100)),
                String.format("%d%%", (int) (MainHandDamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (MaxExtraDamageMultiplierBasedOnMaxHP * 100)),
                String.format("%d%%", (int) (MainHandConstantDamage.floatValue() * 100)),
                String.format("%s", finalMainHandDmg),
                String.format("%d%%", (int) (OffHandDamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (MaxExtraDamageMultiplierBasedOnMaxHP * 100)),
                String.format("%d%%", (int) (OffHandConstantDamage.floatValue() * 100)),
                String.format("%s", finalOffHandDmg)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker010::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);

        //while channeling
        tempSkillData.setChannelingSkillAction1(Berserker010::Berserker010_ChannelingAction);
        //final action
//        tempSkillData.setActiveSkillAction1(Berserker010::Berserker010_ActivatePassive);
        tempSkillData.setActiveSkillAction1(DfoSwordmanSkillMethods::DoNothing);
    }

    public static void Berserker010_ChannelingAction(LivingEntity entity1) {
        if (SBSTickEventMethods.hasSkillActionRunning(entity1, SkillID)) {
            SBSTickEventMethods.setSkillActionDone(entity1, SkillID);
            Blademaster039.SwordmanGetNormalAttackAnimationSetMap().remove(entity1);
//            printInGameMsg("cancel");
            //TODO: remove buff action
        }
        else {
            int actionPeriod = 5;
            //play buff on sound
            DfoSwdSoundMethods.PlayChannelingStartSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);

            int playerSkillLVL = 0;
            SkillDataJson skill1 = getID2SkillData().get(SkillID);
            if (entity1 instanceof ServerPlayer sp1) {
                playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                if (playerSkillLVL <= 0) {
                    return;
                }
                playerSkillLVL = playerSkillLVL - 1;
                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER010_FRENZY_CHANNELING_START);
            }

            float channelingTime = skill1.getChannelingTime().get(playerSkillLVL).floatValue();
            int lifetime = (int) (channelingTime * 20);
            if (channelingTime != 0) {
                float channelingBarAnimationTime = 1 / channelingTime;
                GeoModelSpawnMethods.GenerateRegularChannelingBarEntity(entity1, 1f, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_HEIGHT, 0, lifetime, channelingBarAnimationTime, true);
            }

            IntegerValueHolder TotalChannelingTicksHolder = new IntegerValueHolder(lifetime);
            IntegerValueHolder tempIntegerTicker = new IntegerValueHolder(0);

            MethodAction action = (entity2) -> {
                int currentPassedTicks = tempIntegerTicker.getIntCounter();
                tempIntegerTicker.setIntCounter(currentPassedTicks + actionPeriod);
                boolean contActioning = false;
                HashMap<String, Integer> ServerLivingEntityState = getSLivingEntityState().get(entity2);
                if (ServerLivingEntityState != null) {
                    contActioning = ServerLivingEntityState.get(Constants.IS_CHANNELING) == 1;
                }
                if (currentPassedTicks == TotalChannelingTicksHolder.getIntCounter() - actionPeriod) {
                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity2, 255, 255, 255, 1f, 0, 1.4f, 0, 7, BERSERKER010_FRENZY_CHARGING, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                            DfoSwordmanSkillTreeConstants.NO_ANIMATION,7, 0, 7, false, false, true, null, 777, true);
                }
                if (!contActioning || currentPassedTicks >= TotalChannelingTicksHolder.getIntCounter()) {
                    SBSTickEventMethods.setSkillActionDone(entity2, ChannelingActionID);

                    if (!isEntityChannellingInterrupted(entity2)) {
                        if (entity2 instanceof ServerPlayer sp1) {
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER010_FRENZY_CHANNELING_END);
                        }
                        GeoModelSpawnMethods.GenerateCustomChannelingIconEntity(entity2, 1f, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_HEIGHT, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_LIFETIME, skill1.getResLocBtn(), true, true);

//                        printInGameMsg("active");
                        //play buff on sound
                        DfoSwdSoundMethods.PlayChannelingEndSound(EntityMethods.getEntityLevel(entity2), EntityMethods.getEntityX(entity2), EntityMethods.getEntityY(entity2), EntityMethods.getEntityZ(entity2), 0.6f, 1.0f);
                        Blademaster039.SwordmanGetNormalAttackAnimationSetMap().putIfAbsent(entity2, SkillID);
                        SBSTickEventMethods.setMethodActionTimerWithCleanAction(entity2, SkillID, BERSERKER010_UPDATE_PERIOD, Berserker010_PassiveMethodAction(), null, false);
                    }
                    else {
                        PlaySoundAtLivingEntityLocationEX(entity2, SoundRegistry.CHANNELING_FAILED_SOUND.get(), 0.4f, 1.0f);
                        if (SBSTickEventMethods.hasSkillActionRunning(entity2, SkillID)) {
                            SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                            Blademaster039.SwordmanGetNormalAttackAnimationSetMap().remove(entity2);
                        }
                    }
                }

                if ((currentPassedTicks - 5) % 500 == 0 && entity2 instanceof ServerPlayer sp1) {
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BERSERKER010_FRENZY_CHANNELING_LOOPING);
                }
            };

            //start loop action
            SBSTickEventMethods.setMethodActionTimer(entity1, ChannelingActionID, actionPeriod, action);
        }
    }

    public static MethodAction Berserker010_PassiveMethodAction() {
        return (entity1) -> {
            SkillDataJson skillData = getID2SkillData().get(SkillID);

            DfoStatusGeneralMethods.GenerateEffBuffEntity(entity1, BERSERKER010_UPDATE_PERIOD, BERSERKER010_FRENZY_BUFF, DfoStatusGeneralMethods.EFF_ENTITY_MOVING_UP, BERSERKER010_FRENZY_BUFF);

            int playerSkillLVL = 0;
            float TotalHPConsumptionPercentage = 0;
            if (entity1 instanceof ServerPlayer sp1) {
                playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                if (playerSkillLVL <= 0) {
                    //de activate
                    Blademaster039.SwordmanGetNormalAttackAnimationSetMap().remove(entity1);
                    SBSTickEventMethods.setSkillActionDone(entity1, SkillID);
                    return;
                }
                playerSkillLVL = playerSkillLVL - 1;
            }
            TotalHPConsumptionPercentage = TotalHPConsumptionPercentage + skillData.getValueMultiplierList_01().get(playerSkillLVL).floatValue();

            float MaxEntityHealth = EntityMethods.getLivingEntityMaxHP(entity1);
            float hpShouldReduce = MaxEntityHealth * TotalHPConsumptionPercentage;
            float currentEntityHealth = EntityMethods.getLivingEntityCurrentHP(entity1) - hpShouldReduce;
            float currentHPPercentage = currentEntityHealth / MaxEntityHealth;

//            printInGameMsg("reduced: "+currentEntityHealth);
            EntityMethods.setLivingEntityCurrentHP(entity1, MathMethods.max(0.01f, currentEntityHealth));

            if (currentHPPercentage < BERSERKER010_MINIMUM_FRENZY_HP_PERCENTAGE) {
                //de activate
                SBSTickEventMethods.setSkillActionDone(entity1, SkillID);
                Blademaster039.SwordmanGetNormalAttackAnimationSetMap().remove(entity1);
            }
        };
    }

    public static void Berserker010_Absorption(LivingEntity deadEntity) {
        LivingEntity lastAttacker = EntityMethods.getLastEntityAttacker(deadEntity);

        if (lastAttacker instanceof ServerPlayer sp1 && SBSTickEventMethods.hasSkillActionRunning(lastAttacker, SkillID)) {
            int playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);

            if (playerSkillLVL <= 0) {
                return;
            }

            playerSkillLVL = playerSkillLVL - 1;
            SkillDataJson skillData = getID2SkillData().get(SkillID);

            float HPRecoveryPercentage = skillData.getValueMultiplierList_02().get(playerSkillLVL).floatValue();

            float MaxTargetHealth = EntityMethods.getLivingEntityMaxHP(deadEntity);
            float HPAmountToRestore = HPRecoveryPercentage * MaxTargetHealth;

//            float currentEntityHealth = EntityMethods.getLivingEntityCurrentHP(lastAttacker);

            Vector3f deadEntityPos = getEntityPos(deadEntity);
            Entity effEntity = GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256(lastAttacker,255, 255, 255, 1f, 0, 0f, 0, 200, false,
                    deadEntityPos.x(), deadEntityPos.y(), deadEntityPos.z(), deadEntityPos.x(), deadEntityPos.y(), deadEntityPos.z(), BERSERKER010_FRENZY_BLOOD_ORB, GeoAnimationAndTextureName.NO_ANIMATION_TRANSLATION_Y_22_4,
                    Berserker010_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_ACTION, false, 0, 0, 6, false, false, false, Berserker010_OrbAbsorptionServerAction(new FloatValueHolder(initialVelocity), new TypeValueHolder<>(null), HPAmountToRestore), 1, false);
            moveEntityToPos(effEntity, deadEntityPos);
            addEntityToWorld(getEntityLevel(lastAttacker), effEntity);

//            EntityMethods.setLivingEntityCurrentHP(lastAttacker, ExtraMathMethods.clamp(currentEntityHealth + HPAmountToRestore, 0.01f, MaxTargetHealth));
        }
    }

    public static MethodEntityAction Berserker010_OrbAbsorptionServerAction(FloatValueHolder initialAcceleration, TypeValueHolder<Vector3f> previousTargetLoc, float HPAmountToRestore) {
        return (tempOrbEntity) -> {
            TwoDAnimated256TexCustomEntity OrbEntity = (TwoDAnimated256TexCustomEntity) tempOrbEntity;
            Entity owner = OrbEntity.getOwner();
            if (owner != null && !SBConditions.isEntityAlive(owner)) {
                owner = null;
            }
            if (owner != null) {
                Vector3f originEntityLoc = (previousTargetLoc.getAnyTypeValue() == null) ? getEntityPos(OrbEntity) : previousTargetLoc.getAnyTypeValue();
                Vector3f targetEntityLoc = getEntityPos(owner);

                float distShouldTravelEachTime = initialAcceleration.getFloatVal();
                float polarDist = presetDist1 - distShouldTravelEachTime;
                float orbHorizontalFacingAngle = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(originEntityLoc, targetEntityLoc);
                float orbVerticalFacingAngle = Vector3fMethods.convertNormalizedVerticalAngleToMCVerticalFacingAngle(MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationVerticalInRad(originEntityLoc, targetEntityLoc) * MathMethods.RAD_TO_DEG));
                initialAcceleration.setFloatVal(distShouldTravelEachTime - distShouldTravelDecay);

                Vector3f tpLoc = Vector3fMethods.PolarProjectionFromRotation(originEntityLoc, polarDist, orbHorizontalFacingAngle, orbVerticalFacingAngle);

                moveEntityToPos(OrbEntity, tpLoc);

                previousTargetLoc.setAnyTypeValue(tpLoc);

                if (Vector3fMethods.Vec3fDistanceTo(tpLoc, targetEntityLoc) < 0.3f) {
                    RemoveEntity(OrbEntity);
                    if (owner instanceof LivingEntity livingEntity) {
                        float currentEntityHealth = getLivingEntityCurrentHP(livingEntity);
                        float MaxEntityHealth = getLivingEntityMaxHP(livingEntity);
//                        printInGameMsg("restored: "+HPAmountToRestore);
//                        printInGameMsg("currentHP: "+currentEntityHealth);
//                        printInGameMsg("MaxHP: "+MaxEntityHealth);
                        setLivingEntityCurrentHP(livingEntity, ExtraMathMethods.clamp(currentEntityHealth + HPAmountToRestore, 0.01f, MaxEntityHealth));
                    }

                    //TODO: play recovery Sound
                    GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(owner, 255, 255, 255, 1f, 0, 1.4f, 0, 12, GeoAnimationAndTextureName.EFF_RECOVERY_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                            DfoSwordmanSkillTreeConstants.NO_ANIMATION,0, 0, 12, false, false, true, null, 777, true);
                }
            }
        };
    }

    public static void ClientMethodInit(HashMap<String, MethodEntityAction> clientMethodMap) {
        clientMethodMap.put(Berserker010_SWORD_EFF_CLIENT_MOVING_TOWARDS_TARGET_ACTION, Berserker010::Berserker010_ClientOrbChasingActionInit);
    }

    public static void Berserker010_ClientOrbChasingActionInit(Entity movingObj) {
        TwoDAnimated256TexCustomEntity OrbEntity = (TwoDAnimated256TexCustomEntity) movingObj;
        MethodEntityAction entityAction = OrbEntity.getClientMethodEntityLoopAction();

        if (entityAction == null) {
            OrbEntity.setIsBoundToEntity(true);
            OrbEntity.setHasTargetLocXYZBoolean(true);
            OrbEntity.setClientTranslucentEmissiveWhenCustomizePosAction(true);
            entityAction = Berserker010_BloodOrbChasingOwnerClientAction(new FloatValueHolder(initialVelocity), new IntegerValueHolder(-1), new TypeValueHolder<>(null), new BooleanValueHolder(false));
            OrbEntity.setClientMethodEntityLoopAction(entityAction);
        }

        entityAction.executeAction(OrbEntity);
    }

    //ClientMethod
    public static MethodEntityAction Berserker010_BloodOrbChasingOwnerClientAction(FloatValueHolder initialAcceleration, IntegerValueHolder previousTickCount, TypeValueHolder<Vector3f> previousTargetLoc, BooleanValueHolder isDoneHolder) {
        return (tempOrbEntity) -> {
            TwoDAnimated256TexCustomEntity OrbEntity = (TwoDAnimated256TexCustomEntity) tempOrbEntity;
            int currentSwordEntityElapsedLifeTime = OrbEntity.getMaxLifetime() - OrbEntity.getLifetime();

            if (currentSwordEntityElapsedLifeTime != previousTickCount.getIntCounter()) {
                previousTickCount.setIntCounter(currentSwordEntityElapsedLifeTime);

                Entity target = OrbEntity.getOwner();
                if (target != null && !SBConditions.isEntityAlive(target)) {
                    target = null;
                }
                if (target != null) {
                    Vector3f originEntityLoc = (previousTargetLoc.getAnyTypeValue() == null) ? getEntityPos(OrbEntity) : previousTargetLoc.getAnyTypeValue();
                    Vector3f targetEntityLoc = getEntityPos(target);

                    float distShouldTravelEachTime = initialAcceleration.getFloatVal();
                    float polarDist = presetDist1 - distShouldTravelEachTime;
                    float orbHorizontalFacingAngle = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(originEntityLoc, targetEntityLoc);
                    float orbVerticalFacingAngle = Vector3fMethods.convertNormalizedVerticalAngleToMCVerticalFacingAngle(MathMethods.RoundAngle(Vector3fMethods.AngleFromLocationToLocationVerticalInRad(originEntityLoc, targetEntityLoc) * MathMethods.RAD_TO_DEG));
                    initialAcceleration.setFloatVal(distShouldTravelEachTime - distShouldTravelDecay);

                    Vector3f tpLoc = Vector3fMethods.PolarProjectionFromRotation(originEntityLoc, polarDist, orbHorizontalFacingAngle, orbVerticalFacingAngle);

                    OrbEntity.setStartPosX(originEntityLoc.x());
                    OrbEntity.setStartPosY(originEntityLoc.y());
                    OrbEntity.setStartPosZ(originEntityLoc.z());
                    OrbEntity.setTargetPosX(tpLoc.x());
                    OrbEntity.setTargetPosY(tpLoc.y());
                    OrbEntity.setTargetPosZ(tpLoc.z());

                    previousTargetLoc.setAnyTypeValue(tpLoc);

                    if (Vector3fMethods.Vec3fDistanceTo(tpLoc, targetEntityLoc) < 0.3f) {
                        isDoneHolder.setBooleanVal(true);
                        OrbEntity.setIsBoundToEntity(false);
                        OrbEntity.setHasTargetLocXYZBoolean(false);
                        OrbEntity.setClientActionName(DfoSwordmanSkillTreeConstants.NO_ANIMATION);
                    }
                }
            }
        };
    }

    //============
    public static void FrenzyNormalAttackRegister() {
        Blademaster039.RegisterAttackAnimationSet(SkillID, Berserker010::FrenzyAttackActionSetup);
    }

    public static void Berserker010_ResetUponRespawn(LivingEntity respawnEntity) {
        if (SBSTickEventMethods.hasSkillActionRunning(respawnEntity, SkillID)) {
            SBSTickEventMethods.setSkillActionDone(respawnEntity, SkillID);
        }
        Blademaster039.SwordmanGetNormalAttackAnimationSetMap().remove(respawnEntity);
    }

    public static void FrenzyAttackActionSetup(LivingEntity entity0) {
        //identify weapon type
        int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity0);
        if (weaponType == DfoSkillTreeItemMethods.IS_HOLDING_NOTHING) {
            return;
        }

        String[] weaponAnimArr = BERSERKER010_FRENZY_ATTACK_ANIMATION;
        int actualAnimationShouldPlayIndex = weaponAnimArr.length - 1;

        int playerSkillLVL = 0;
        float aoeRange;
        HashMap<LivingEntity, Integer> SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes = Blademaster039.SwordmanGetNormalAttackTriggerTimeMap();

        //ASSUME SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes never be null
        if (!SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.containsKey(entity0)) {
            SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.put(entity0, 0);
        }

        SkillDataJson skill1 = getID2SkillData().get(SkillID);

        float ExtraDamageMultiplier = 0f;
        if (entity0 instanceof ServerPlayer sp1) {
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID) - 1;
            }
        }

        float CurrentEntityHealth = getLivingEntityCurrentHP(entity0);
        float MaxEntityHealth = getLivingEntityMaxHP(entity0);
        float DamagePercentageBasedOnRemainingHP = (MaxEntityHealth - CurrentEntityHealth) / MaxEntityHealth;

        float MaxExtraDamageMultiplierBasedOnMaxHP = skill1.getDamageMultiplierList_03().get(playerSkillLVL).floatValue() * DamagePercentageBasedOnRemainingHP;
        ExtraDamageMultiplier = ExtraDamageMultiplier + MaxExtraDamageMultiplierBasedOnMaxHP;

        aoeRange = skill1.getEffectArea().get(playerSkillLVL).floatValue();

        float MainHandDamageBase = skill1.getDamageList_01().get(playerSkillLVL).floatValue();
        float MainHandDamageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

        float OffHandDamageBase = skill1.getDamageList_02().get(playerSkillLVL).floatValue();
        float OffHandDamageMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue() + ExtraDamageMultiplier;

        //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
        //start Actioning

        Predicate<Entity> condition;
        float[] entityAnimationArr = DfoSwordmanSkillMethods.getEntityAnimationSpeedMultiplierArr(entity0);
        float finalAspd = entityAnimationArr[0];
        float finalAnimationSpeed = entityAnimationArr[1];

        //play animation and update condition
        int triggeredTimes = SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.get(entity0);
        //reset trigger times
        if (triggeredTimes - actualAnimationShouldPlayIndex >= actualAnimationShouldPlayIndex) {
            SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.put(entity0, actualAnimationShouldPlayIndex);
        }
        if (entity0 instanceof ServerPlayer sp1) {
            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, weaponAnimArr[SWORDMAN_NORMAL_ATTACK_HashMapTriggeredTimes.get(entity0) % actualAnimationShouldPlayIndex], finalAspd);

            condition = defaultSkillTargetCondition(sp1);
        }
        else {
            condition = defaultSkillTargetCondition();
        }

        float finalMainHandDmgDealt = (MainHandDamageMultiplier * (float) getEntityAttackDamage(entity0) + MainHandDamageBase);
        float finalOffHandDmgDealt = (OffHandDamageMultiplier * (float) getEntityAttackDamage(entity0) + OffHandDamageBase);

        int actionPeriod = 1;
        MethodAction action = FrenzyAttackPrepAction(entity0, actionPeriod, weaponType, finalAnimationSpeed, finalMainHandDmgDealt, finalOffHandDmgDealt, aoeRange, condition);
        SBSTickEventMethods.setMethodActionTimerWithCleanAction(entity0, BERSERKER010_FRENZY_ATTACK_ACTION, actionPeriod, action, Blademaster039::clearAction, true);
    }

    public static MethodAction FrenzyAttackPrepAction(LivingEntity entity1, int actionPeriod, int weaponType, float finalAnimationSpeed, float finalMainHandDmgDealt, float finalOffHandDmgDealt, float aoeRange, Predicate<Entity> condition) {
        HashMap<LivingEntity, Integer> AttackTriggerTimeMap = Blademaster039.SwordmanGetNormalAttackTriggerTimeMap();

        int TotalTriggeredTimes = AttackTriggerTimeMap.get(entity1);
        int triggeredTimes = TotalTriggeredTimes % 4;

        int[] FrenzyDamageTick = BERSERKER010_FRENZY_DAMAGE_TICK[triggeredTimes];
        float[] FrenzySlashEffZRot = BERSERKER010_FRENZY_SLASH_EFF_ROT[triggeredTimes];

        int damage1tick = (int) (FrenzyDamageTick[0] * finalAnimationSpeed);
        int playWieldingSound = (int) ((FrenzyDamageTick[0] + 1) * finalAnimationSpeed);

        int damage2tick = (int) (FrenzyDamageTick[1] * finalAnimationSpeed);
        int animTicks = damage2tick + 1;
        int delayClearTicks = 20;

        int slashEntitySpawnTick1 = Math.max( (int) ((FrenzyDamageTick[0] - 1) * finalAnimationSpeed), 1);
        int slashEntitySpawnTick2 = Math.max( (int) ((FrenzyDamageTick[1] - 1) * finalAnimationSpeed), 1);

        IntegerValueHolder ElapsedTicksHolder = new IntegerValueHolder(0);

        MethodAction action =
                (entity2) ->  {
                    boolean isActionInterrupted = isLivingEntityActionInterrupted(entity2);
                    int currentTick = ElapsedTicksHolder.getIntCounter() + actionPeriod;
                    ElapsedTicksHolder.setIntCounter(currentTick);
                    if (!isActionInterrupted && getLivingEntityIsInAction(entity2) <= 0 && currentTick < animTicks + delayClearTicks) {
                        float triggeredEntityFacingAngle = getEntityHorizontalFacingDeg(entity2);

                        //play wieldingSound
                        if (currentTick == playWieldingSound) {
                            DfoSwdSoundMethods.PlayWeaponTypeWieldingSound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.7f, ExtraMathMethods.randomFloat()*0.2f+0.9f);
                        }

                        if (currentTick <= animTicks) {
                            setEntityBodyHorizontalFacingDeg(entity2, triggeredEntityFacingAngle);
                            //lightsabre
                            //start damage
                            if (currentTick == damage1tick) {
                                FrenzyAttackDamageAction(entity2, weaponType, triggeredEntityFacingAngle, aoeRange + 1f, finalMainHandDmgDealt, condition);
                                AttackTriggerTimeMap.put(entity2, TotalTriggeredTimes + 1);
                            }
                            if (currentTick == damage2tick) {
                                FrenzyAttackDamageAction(entity2, weaponType, triggeredEntityFacingAngle, aoeRange + 1f, finalOffHandDmgDealt, condition);
                            }

                            //spawn slash entity
                            if (currentTick == slashEntitySpawnTick1) {
//                                GeoModelSpawnMethods.GenerateRGBSlashBoundToEntity(entity2, triggeredEntityFacingAngle, FrenzySlashEffZRot[0], 1f,175, 238, 238, 0, 1f, 0, true);
                                GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, triggeredEntityFacingAngle, FrenzySlashEffZRot[0], 1.2f, BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[0], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[1], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[2], 0f, 1.4f, 0f, Math.max(18, animTicks),
                                        GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);
                            }

                            if (currentTick == slashEntitySpawnTick2) {
//                                GeoModelSpawnMethods.GenerateRGBSlashBoundToEntity(entity2, triggeredEntityFacingAngle, FrenzySlashEffZRot[1], 1f,175, 238, 238, 0, 1f, 0, true);
                                GeoModelSpawnMethods.GenerateCustomSlashBoundToEntity(entity2, triggeredEntityFacingAngle, FrenzySlashEffZRot[1], 1.2f, BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[0], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[1], BerserkerSkillTreeInitialization.BERSERKER_SLASH_COLOR[2], 0f, 1.4f, 0f, Math.max(18, animTicks),
                                        GeoAnimationAndTextureName.EFF_ENTITY_TEX_BLADEMASTER_FANCY_SLASH_WHITE, 3, 2, 12, true, true, GeoAnimationAndTextureName.EFF_SLASH_ANIMATION_NAME_NEG60_2_POS15, DfoSwordmanSkillTreeConstants.NO_ANIMATION, true);
                            }
                        }

                        //start moving around
//                        float newX = Vector3fMethods.PolarProjectionFromXHorizon(0.3f, triggeredEntityFacingAngle);
//                        float newZ = Vector3fMethods.PolarProjectionFromZHorizon(0.3f, triggeredEntityFacingAngle);
//                        Vector3f tempTriggeredEntityDeltaVec = getEntityDeltaMovement(entity2);
//                        float newDeltaX = (newX > 0) ? MathMethods.min(tempTriggeredEntityDeltaVec.x(), newX) : MathMethods.max(tempTriggeredEntityDeltaVec.x(), newX);
//                        float newDeltaZ = (newZ > 0) ? MathMethods.min(tempTriggeredEntityDeltaVec.x(), newZ) : MathMethods.max(tempTriggeredEntityDeltaVec.z(), newZ);
//
//                        setEntitySPDeltaMovement(entity2, newDeltaX, tempTriggeredEntityDeltaVec.y(), newDeltaZ, false);
                    }
                    else {
                        if (!isActionInterrupted) {
                            //reset delta
                            setEntitySPDeltaMovement(entity2, 0, 0, 0, false);
                        }

                        SBSTickEventMethods.setSkillActionDone(entity2, BERSERKER010_FRENZY_ATTACK_ACTION);
                    }
                };

        return action;
    }

    //slash
    private static void FrenzyAttackDamageAction(LivingEntity entity2, int weaponType, float triggeredEntityFacingAngle, float aoeRange, float finalDmg, Predicate<Entity> condition) {
        boolean playHitSound = false;
        //Sector damage cond
        Predicate<Entity> SectorDmgCond = DfoSwordmanSkillMethods.getSectorCondition(entity2, 60f, aoeRange);
        for (Entity tempEntity : GroupEnumEntitiesInRangeFilter(entity2, aoeRange, condition.and(SectorDmgCond))) {
            if (tempEntity instanceof LivingEntity tempLivingEntity) {
                if (!playHitSound) {
                    playHitSound = true;
                    DfoSwdSoundMethods.PlayWeaponTypeHitEntitySound(weaponType, getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                }

                DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity2, tempLivingEntity, finalDmg, false);
                //stay in air
                float additionalDeltaY = (isEntityFarFromGround(tempEntity, 0.5f)) ? 0f : 0.15f;
                float pushXZStrength = 0f;

                float pushYStrength = calculatePushEntityStrengthTargetOnly(MathMethods.max(getEntityDeltaY(tempLivingEntity), additionalDeltaY), tempLivingEntity);
                pushEntityWithDeltaValue(tempLivingEntity, triggeredEntityFacingAngle, pushXZStrength, pushYStrength, false);

                GeoModelSpawnMethods.Generate2DHitEff(tempLivingEntity, ExtraMathMethods.randomFloat(360f), 1f, 4, 4, ExtraMathMethods.randomFloat(0.4f) - 0.2f, 1.1f + ExtraMathMethods.randomFloat(), ExtraMathMethods.randomFloat(0.4f) - 0.2f, GeoAnimationAndTextureName.EFF_HIT_GENERAL, true, true);
            }
        }
    }
}
