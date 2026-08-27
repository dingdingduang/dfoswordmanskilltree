package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.bus.DfoSwordmanLivingEntityEvent;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.MagicDamageSource;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster008;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster010;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.networking.NetworkingFetchMsgMethods;
import net.dingdingduang.somebasicskills.sbsattributes.SBSAttributes;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import modified.org.joml.Vector3f;

import java.util.HashMap;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods.Generate2DAnimatedTexEntity64;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInPolarProjectionRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.getCPlayerSkillDamage;
import static net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods.getTimedAttributeValueFromLivingEntity;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getCPlayerState;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues.getSLivingEntityState;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.getSkillsInCooldownClientTimerOverlayInstance;
import static net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor.applyTimedBeingAttackedToEntity;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster001 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_GUARD;

    public static HashMap<LivingEntity, Integer> BLADEMASTER001_HashMapPassedTicks;
//    public static HashMap<LivingEntity, Integer> BLADEMASTER001_TriggeredPerfectGuard;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double PhysicDamageAbsorption = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Double MagicDamageAbsorption = skillData.getValueMultiplierList_02().get(correctSkillLevel);
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ConstantDamage = skillData.getDamageList_01().get(correctSkillLevel);
        double skillDamageMultiplier = getCPlayerSkillDamage();
        Float finalDmg = (float) ( (DamageMultiplier * getEntityAttackDamage(getClientPlayer()) + ConstantDamage) * skillDamageMultiplier);

//        String PhysicDamageAbsorptionString = String.format("%d%%", (int) (PhysicDamageAbsorption.floatValue() * 100));
//        String MagicDamageAbsorptionString = String.format("%d%%", (int) (MagicDamageAbsorption.floatValue() * 100));
//        String DamageMultiplierString = String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100));
//        String ConstantDamageString = String.format("%s", ConstantDamage);
//        String FinalDamageString = String.format("%s%%", finalDmg);
//
//        return String.format(getLocalizationText(skillData.getTranslatableTextDescription()),
//                PhysicDamageAbsorptionString,
//                MagicDamageAbsorptionString,
//                DamageMultiplierString,
//                ConstantDamageString,
//                FinalDamageString );
        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (PhysicDamageAbsorption.floatValue() * 100)),
                String.format("%d%%", (int) (MagicDamageAbsorption.floatValue() * 100)),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100)),
                String.format("%s", ConstantDamage),
                String.format("? + %s%%", finalDmg) );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);

        tempSkillData.setTranslatableTooltipGetter(Blademaster001::getSkillDescription);
        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement(
                (SkillID2) -> {
                    boolean isInCD = getSkillsInCooldownClientTimerOverlayInstance().isClientPlayerSkillInCD(SkillID2);
                    boolean isInAction = isPlayerInAction();
                    boolean isChanneling = isPlayerInChannelingState();
                    boolean isPlayerBeingHit = isPlayerBeingHit();

                    return !isInCD && !isInAction && !isChanneling && !isPlayerBeingHit;
                }
        );



        if (isClientSide()) {
            return;
        }

        //init
        if (BLADEMASTER001_HashMapPassedTicks == null) {
            BLADEMASTER001_HashMapPassedTicks = new HashMap<>();
        }



        //action while channeling on server side
        MethodAction tempChannelingAction = (entity1) -> {
            int actionPeriod = 2;
            getSLivingEntityState().get(entity1).put(DfoSwordmanSkillTreeConstants.IS_GUARDING, Constants.ACTION_ON);

            BLADEMASTER001_HashMapPassedTicks.put(entity1, 0);
            incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);
            //play sound when guarding
            DfoSwdSoundMethods.PlaySwordGuardStartSound(getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.8f, 1.0f);

            int randomInt = (ExtraMathMethods.randomFloat() > 0.5) ? 1 : 2;
            String AnimationStartName = String.format("guard%d_start",randomInt);
            String AnimationChannelingName = String.format("guard%d_channeling",randomInt);
            String AnimationEndName = String.format("guard%d_end",randomInt);
            //play animation
            if (entity1 instanceof ServerPlayer sp1) {
//                SBPlayerAnimatorMethods.PlayDfoSwordmanSkillAnimationServer(sp1, AnimationStartName);
                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, AnimationStartName);
            }

            MethodAction action = (entity2) -> {
//                boolean contActioning = ServerSkillMethods.getEntityStateAmount(entity2, Constants.IS_CHANNELING) != 0;
                boolean contActioning = false;
                HashMap<LivingEntity, HashMap<String, Integer>> ServerLivingEntityState = getSLivingEntityState();
                if (ServerLivingEntityState.containsKey(entity2) && ServerLivingEntityState.get(entity2) != null) {
                    contActioning = ServerLivingEntityState.get(entity2).get(Constants.IS_CHANNELING) == 1;
                }
                if (!contActioning) {
                    //play animation
                    if (entity2 instanceof ServerPlayer sp1) {
//                        SBPlayerAnimatorMethods.PlayDfoSwordmanSkillAnimationServer(sp1, AnimationEndName);
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, AnimationEndName);
                    }
                    //play sound when stop guarding
                    DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 1.0f);
                    //stop loop action
//                    getSLivingEntityState().get(entity2).put(DfoSwordmanSkillTreeConstants.IS_GUARDING, Constants.ACTION_OFF);
                    getSLivingEntityState().get(entity2).remove(DfoSwordmanSkillTreeConstants.IS_GUARDING);
                    SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                }
                else {
                    int currentPassedTicks = BLADEMASTER001_HashMapPassedTicks.get(entity2);
                    //play animation
                    if (currentPassedTicks % 500 == 0 && entity2 instanceof ServerPlayer sp1) {
//                    if (currentPassedTicks % 4 == 0 && entity2 instanceof ServerPlayer sp1) {
//                        SBPlayerAnimatorMethods.PlayDfoSwordmanSkillAnimationServer(sp1, AnimationChannelingName);
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, AnimationChannelingName);
                    }
                }
                BLADEMASTER001_HashMapPassedTicks.put(entity2, BLADEMASTER001_HashMapPassedTicks.getOrDefault(entity2, 0) + actionPeriod);
            };

            //start loop action
            SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
        };

        //while channeling
        tempSkillData.setChannelingSkillAction1(tempChannelingAction);
        //no final action
        tempSkillData.setActiveSkillAction1(DfoSwordmanSkillMethods::DoNothing);

        MethodAction tempSkillCleanAction = (entity1) -> {
            BLADEMASTER001_HashMapPassedTicks.remove(entity1);
            //IMPORTANT! set action off to allow entity to be able to cast skills
//            SendPacketIsInActionToClientSideFromServer(entity1, Constants.ACTION_OFF);

            incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, -1);
//            if (BLADEMASTER001_HashMapPassedTicks.size() <= 0) {
//                BLADEMASTER001_HashMapPassedTicks = new HashMap<>();
//            }
        };
        tempSkillData.setClearSkillAction(tempSkillCleanAction);

        //======================
        //set reflection action when trigger perfect guarding
        //owner caster
//        MethodAction reflectionAction2 = (owner) -> {
//            Predicate<? super Entity> tempCond = SBConditions.defaultConditionCheck();
//            for (Entity a: GroupEnumEntitiesInPolarProjectionRangeFilter(owner, 1.2f, tempCond, 0.5f, true)) {
//                if (a instanceof LivingEntity target) {
//                    LivingEntityDamageTarget(owner, target, )
//                }
//            }
//        };
//        tempSkillData.setActiveSkillAction2(reflectionAction2);
    }

    public static int getEntityGuardTicks(LivingEntity entity) { return BLADEMASTER001_HashMapPassedTicks.getOrDefault(entity, 11); }
    public static void setEntityGuardTicks(LivingEntity entity, int ticks) { BLADEMASTER001_HashMapPassedTicks.put(entity, ticks); }

    public static float Blademaster001_GuardAction(HashMap<String, Integer> LivingEntityState, LivingEntity entity, Entity damageSourceEntity, LivingEntity damageSourceEntityOwner, DamageSource dmgSrc, float originalIncomingDMG) {
        float incomingDMG = originalIncomingDMG;
        Predicate<Entity> guardCond;
        SkillDataJson tempSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_GUARD);
        int GuardLVL = 0;
        double physicalDmgBlockPercent = 0, magicalDmgBlockPercent = 0;
        int weaponType = DfoSkillTreeItemMethods.getWeaponType(entity);
        boolean isWieldingShortSword = weaponType == DfoSkillTreeItemMethods.IS_AVERAGE_SHORT_SWORD;
        boolean isWieldingZanbato = weaponType == DfoSkillTreeItemMethods.IS_VERY_SLOW_ZANBATO;
        boolean shouldTriggerStun = false;
        float StunChance = 0f;
        int StunDurationTicks = 0;

        if (entity instanceof ServerPlayer sp1) {
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(BlademasterSkillTreeInitialization.BLADEMASTER_GUARD)) {
                GuardLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(BlademasterSkillTreeInitialization.BLADEMASTER_GUARD);
                if (GuardLVL > 0) {
                    GuardLVL = GuardLVL - 1; // index out of boundary
                }
            }
            guardCond = defaultSkillTargetCondition(sp1);

            if (isWieldingShortSword) {
                int ShortSwordMasteryLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_SHORT_SWORD_MASTERY);
                if (ShortSwordMasteryLevel >= Blademaster008.BLADEMASTER008_SHORT_SWORD_MASTERY_BONUS_EFFECT_1_LVL) {
                    shouldTriggerStun = true;
                    SkillDataJson ShortSwordMasterySkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_SHORT_SWORD_MASTERY);
                    int correctSkillLVL = ShortSwordMasteryLevel - 1;
                    StunChance = ShortSwordMasterySkillData.getValueUsage_02().get(correctSkillLVL).floatValue();
                    StunDurationTicks = (int) (20 * ShortSwordMasterySkillData.getDuration().get(correctSkillLVL).floatValue());
                }
            }
            else if (isWieldingZanbato) {
                int ZanbatoMasteryLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);
                if (ZanbatoMasteryLevel >= Blademaster010.BLADEMASTER010_ZANBATO_MASTERY_BONUS_EFFECT_2_LVL) {
                    shouldTriggerStun = true;
                    SkillDataJson ZanbatoMasterySkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);
                    int correctSkillLVL = ZanbatoMasteryLevel - 1;
                    StunChance = ZanbatoMasterySkillData.getValueUsage_02().get(correctSkillLVL).floatValue();
                    StunDurationTicks = (int) (20 * ZanbatoMasterySkillData.getDuration().get(correctSkillLVL).floatValue());
                }
            }

            int EXGuardUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_GUARD_UPGRADE);
            if (EXGuardUpgradeLVL > 0) {
                EXGuardUpgradeLVL = EXGuardUpgradeLVL - 1;
                SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_GUARD_UPGRADE);
                physicalDmgBlockPercent = physicalDmgBlockPercent + EX_SkillData.getValueMultiplierList_01().get(EXGuardUpgradeLVL);
                magicalDmgBlockPercent = magicalDmgBlockPercent + EX_SkillData.getValueMultiplierList_02().get(EXGuardUpgradeLVL);
            }
        }
        else {
            guardCond = defaultSkillTargetCondition();
        }

        physicalDmgBlockPercent = physicalDmgBlockPercent + tempSkillData.getValueMultiplierList_01().get(GuardLVL);
        magicalDmgBlockPercent = magicalDmgBlockPercent + tempSkillData.getValueMultiplierList_01().get(GuardLVL);

        Vector3f HurtEntityLoc = getEntityPos(entity);
        Vector3f DamageSourceLoc = getEntityPos(damageSourceEntity);
        float AngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(DamageSourceLoc, HurtEntityLoc);
//                    printInGameMsg("VvvvvvvvvvvvvvvvvvV");
//                    printInGameMsg("dmgedEntity Angle: " + MathMethods.RoundAngle(entity.getYRot()));
//                    printInGameMsg("Source Angle: " + MathMethods.RoundAngle(damageSourceEntity.getYRot()));
//                    printInGameMsg("Between Angle: " + AngleBtn);

        float entityFacingAngle = getEntityHorizontalFacingDeg(entity);
        float tempD = MathMethods.RoundAngle(AngleBtn - 180f); //front for -180 deg
        float range = 70f; //140 deg
//                    printInGameMsg("result: " + tempD);

        //front
        if (tempD <= MathMethods.RoundAngle(entityFacingAngle) + range
                && tempD >= MathMethods.RoundAngle(entityFacingAngle) - range) {
//                        printInGameMsg("YES!!!");
            //play sound when guarding
            DfoSwdSoundMethods.PlaySwordGuardingSound(getEntityLevel(entity), getEntityX(entity), getEntityY(entity), getEntityZ(entity), 0.6f, 1.0f);
            int isPerfectGuard = Blademaster001.getEntityGuardTicks(entity);
//                        printInGameMsg("ticks: "+ isPerfectGuard);
            if (isPerfectGuard < 11) {
//                            printInGameMsg("perfect!");

                physicalDmgBlockPercent += 0.25;
                magicalDmgBlockPercent += 0.25;
                //reflection
                //play sound when guarding
                DfoSwdSoundMethods.PlaySwordReflectionSound(getEntityLevel(entity), getEntityX(entity), getEntityY(entity), getEntityZ(entity), 0.6f, 1.0f);

                float newXPercent = Vector3fMethods.PolarProjectionFromXHorizon(1f, entityFacingAngle);
                float newZPercent = Vector3fMethods.PolarProjectionFromZHorizon(1f, entityFacingAngle);
                float newX = 2f * newXPercent;
                float newZ = 2f * newZPercent;

                Generate2DAnimatedTexEntity64(entity, "textures/entity/skilleff/blademaster/guard_reflection/", 8, newX, 0f, newZ, true);
//                            Generate2DAnimatedTexEntity64(entity, "textures/skillicons/sword/blademaster/blademaster_0_1.png", 100, newX, 1f, newZ);

                newX = 0.15f * newXPercent;
                newZ = 0.15f * newZPercent;
//                                Predicate<? super Entity> tempCond = SBConditions.defaultConditionCheck();
//                                Predicate<? super Entity> tempCond = SBConditions.defaultSkillTargetCondition();

                for (Entity a : GroupEnumEntitiesInPolarProjectionRangeFilter(entity, 1.2f, guardCond, 0.5f, true)) {
                    if (a instanceof LivingEntity target) {
                        setEntityDeltaMovement(target, newX, 0, newZ);
//                                        LivingEntityDamageTarget(entity, target, (incomingDMG / 2) +
//                                                (float) ((getEntityAttackDamage(entity) * tempSkillData.getDamageMultiplierList_01().get(GuardLVL))
//                                                        + (tempSkillData.getDamageList_01().get(GuardLVL))));
                        DfoGeneralDamageSrc.LivingEntityDamageTargetWithPhysicDMG(entity, target,
                                (float) ((getEntityAttackDamage(entity) * tempSkillData.getDamageMultiplierList_01().get(GuardLVL))
                                        + (tempSkillData.getDamageList_01().get(GuardLVL))),
                                false);

                        if (shouldTriggerStun && ExtraMathMethods.randomFloat() < StunChance) {
                            DfoStatusGeneralMethods.ApplyStunStatusToTargetLivingEntity(entity, damageSourceEntityOwner, StunDurationTicks, 0.1f);
                        }
                    }
                }
                Blademaster001.setEntityGuardTicks(entity, DfoSwordmanSkillTreeConstants.CONSTANT_INTEGER_777);
            }

            String dmgSrcMsgStrID = dmgSrc.getMsgId();
            if (DfoGeneralDamageSrc.MAGICAL_DAMAGE_STR_SET.contains(dmgSrcMsgStrID) || dmgSrc instanceof MagicDamageSource magicDamageSource) {
                incomingDMG = (float) (incomingDMG * (1.0 - ExtraMathMethods.clamp(magicalDmgBlockPercent, 0, 1)));
//                                event.setAmount(incomingDMG);
            }
            else {
//                            printInGameMsg("current reduction dmg percent: " + (1.0 - ExtraMathMethods.clamp(physicalDmgBlockPercent, 0, 1)));
                incomingDMG = (float) (incomingDMG * (1.0 - ExtraMathMethods.clamp(physicalDmgBlockPercent, 0, 1)));
//                                event.setAmount(incomingDMG);
            }
            //TEST
//                        event.setAmount(0);
        }
        else {
//            printInGameMsg("guard failed!!!");

//            boolean shouldExecute = damageSourceEntityOwner == null || !isLivingEntityActionInterrupted(damageSourceEntityOwner);
            boolean shouldExecute = !isLivingEntityActionInterrupted(damageSourceEntityOwner);

            if (shouldExecute) {
                if (entity instanceof ServerPlayer sp1) {
                    //To client packet
                    NetworkingFetchMsgMethods.FetchPlayerStateToClientSide(sp1, Constants.IS_CHANNELING, 0);

                    NetworkingFetchMsgMethods.FetchPlayerIsImmobilizedBooleanFromServer(sp1, true);
                    if (getTimedAttributeValueFromLivingEntity(entity, SBSAttributes.SBS_ATTRIBUTE_BEING_ATTACKED, Constants.OP_ADDITION) < 8) {
                        applyTimedBeingAttackedToEntity(entity, 10, 9f, Constants.OP_ADDITION, false, DfoSwordmanSkillTreeConstants.BEING_ATTACKED, null,
                                DfoSwordmanLivingEntityEvent::PlayerBeingHitLambdaFunc);

                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
                    }

                    //Counter Strike Packet
                    Blademaster004.Blademaster004_SendPacketIfMeetConditionsToClientSide(damageSourceEntity, sp1);
                }
                if (LivingEntityState.containsKey(Constants.IS_CHANNELING)) {
                    LivingEntityState.put(Constants.IS_CHANNELING, 0);
                    LivingEntityState.put(Constants.CHANNELING_INTERRUPTED, 1);
                    LivingEntityState.put(Constants.ACTION_INTERRUPTED, 1);
                }
            }
        }

        return incomingDMG;
    }
}
