package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.bus.DfoSwordmanLivingEntityEvent;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.MagicDamageSource;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues;
import net.dingdingduang.somebasicskills.networking.NetworkingFetchMsgMethods;
import net.dingdingduang.somebasicskills.registries.SoundRegistry;
import net.dingdingduang.somebasicskills.sbsattributes.SBSAttributes;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods.getTimedAttributeValueFromLivingEntity;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isEntityChannellingInterrupted;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isLivingEntityActionInterrupted;
import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.PlaySBSChannelingFailedSoundAtLocation;
import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.PlaySoundAtLivingEntityLocationEX;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues.getSLivingEntityState;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor.*;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster002 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_AUTO_GUARD;
    public static final String BuffSkillID = BlademasterSkillTreeInitialization.BLADEMASTER_AUTO_GUARD + "_BUFF";
    public static final String IsBuffActionActiveSTRID = BlademasterSkillTreeInitialization.BLADEMASTER_AUTO_GUARD + "_BUFF_ACTIONING";

    public static final String AutoGuardIconFlash = "textures/entity/skilleff/blademaster/autoguard/";

    public static final int BuffActionTotalTicks = 40;

    public static HashMap<LivingEntity, Boolean> BLADEMASTER002_HashMapIsTriggeredAtFirstTime = new HashMap<>();

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double duration = skillData.getDuration().get(correctSkillLevel);
        int TriggerChance = (int) (skillData.getValueMultiplierList_01().get(correctSkillLevel) * 100);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%ss", duration),
                String.format("%d%%", TriggerChance)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster002::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);

        //action while channeling on server side
        MethodAction tempChannelingAction = (entity1) -> {
            int actionPeriod = 5;
            //play buff on sound
            DfoSwdSoundMethods.PlayChannelingStartSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);

            int playerSkillLVL = 0;
            SkillDataJson skill1 = getID2SkillData().get(SkillID);
            if (entity1 instanceof ServerPlayer sp1) {
                if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                    playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                    if (playerSkillLVL <= 0) {
                        return;
                    }
                    playerSkillLVL = playerSkillLVL - 1;
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_START);
                }
            }
            float channelingTime = skill1.getChannelingTime().get(playerSkillLVL).floatValue();
            if (channelingTime != 0) {
                float channelingBarAnimationTime = 1 / channelingTime;
                int lifetime = (int) (channelingTime * 20);
                GeoModelSpawnMethods.GenerateRegularChannelingBarEntity(entity1, 1f, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_HEIGHT, 0, lifetime, channelingBarAnimationTime, true);
            }

            IntegerValueHolder tempIntegerTicker = new IntegerValueHolder(0);
            MethodAction action = (entity2) -> {
                int currentPassedTicks = tempIntegerTicker.getIntCounter();
                tempIntegerTicker.setIntCounter(currentPassedTicks + actionPeriod);
                boolean contActioning = false;
                HashMap<String, Integer> ServerLivingEntityState = getSLivingEntityState().get(entity2);
                if (ServerLivingEntityState != null) {
                    contActioning = ServerLivingEntityState.get(Constants.IS_CHANNELING) == 1;
                }
                if (!contActioning) {
                    SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                    //[!] remove entity here?
                }
                else {
                    if ((currentPassedTicks - 5) % 500 == 0 && entity2 instanceof ServerPlayer sp1) {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_LOOPING);
                    }
                }
            };

            //start loop action
            SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
        };

        //channeling final action
        MethodAction tempChannelingFinalAction = (entity1) -> {
                    //condition, if skill is interrupted, do not execute final action
                    if (!isEntityChannellingInterrupted(entity1)) {
                        ServerSkillMethods.setEntityStateAmountNoPacket(entity1, BuffSkillID, 1);

                        int playerSkillLVL = 0;
                        SkillDataJson skill1 = getID2SkillData().get(SkillID);
                        int AdditionalTriggerChance = 0;
                        if (entity1 instanceof ServerPlayer sp1) {
                            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                                if (playerSkillLVL <= 0) {
                                    return;
                                }
                                playerSkillLVL = playerSkillLVL - 1;
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_END);
                            }
                            int EXAutoGuardUpgradeLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.EXBLADEMASTER_AUTO_GUARD_UPGRADE);
                            if (EXAutoGuardUpgradeLVL > 0) {
                                SkillDataJson EX_SkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_AUTO_GUARD_UPGRADE);
                                AdditionalTriggerChance = (int) (100 * EX_SkillData.getValueMultiplierList_01().get(EXAutoGuardUpgradeLVL - 1).floatValue());
                            }
                        }
                        GeoModelSpawnMethods.GenerateCustomChannelingIconEntity(entity1, 1f, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_HEIGHT, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_LIFETIME, skill1.getResLocBtn(), true, true);

                        int duration2Ticks = (int) (skill1.getDuration().get(playerSkillLVL).floatValue() * 20);
                        int TriggerChance = (int) (skill1.getValueMultiplierList_01().get(playerSkillLVL) * 100) + AdditionalTriggerChance;

                        ServerSkillMethods.setEntityStateAmountNoPacket(entity1, BuffSkillID, TriggerChance);

                        IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
                        int actionPeriod = 10;

                        MethodAction action = (entity2) -> {
                            int currentPassedTicks = tempTickCounter.getIntCounter();

                            if (currentPassedTicks > duration2Ticks) {
//                                ServerSkillMethods.setEntityStateAmountNoPacket(entity2, BuffSkillID, 0);
                                SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                                Blademaster002_ResetAutoGuard(entity2);
                            }

                            tempTickCounter.setIntCounter(currentPassedTicks + actionPeriod);
                        };

                        //start loop action
                        SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);

                        DfoSwdSoundMethods.PlayChannelingEndSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);
                    }
                    else {
                        PlaySBSChannelingFailedSoundAtLocation(entity1.level(), entity1.getX(), entity1.getY(), entity1.getZ(), 0.4f, 1.0f);
                    }
                };

        //while channeling
        tempSkillData.setChannelingSkillAction1(tempChannelingAction);
        //final action
        tempSkillData.setActiveSkillAction1(tempChannelingFinalAction);

    }

    public static int Blademaster002_getAutoGuardBuffTriggerChance(LivingEntity tempLivingEntity) {
        return ServerSkillMethods.getEntityStateAmount(tempLivingEntity, BuffSkillID);
    }

    public static void Blademaster002_ResetAutoGuard(LivingEntity tempLivingEntity) {
        GlobalServerLivingEntityValues.removeStateFromLivingEntity(tempLivingEntity, BuffSkillID);
//        ServerSkillMethods.setEntityStateAmountNoPacket(tempLivingEntity, BuffSkillID, 0);
    }

    public static void Blademaster002_ResetAutoGuardIsTriggeredFirstly(LivingEntity entity) {
        BLADEMASTER002_HashMapIsTriggeredAtFirstTime.remove(entity);
    }

    public static HashMap<LivingEntity, Boolean> getBLADEMASTER002_HashMapIsTriggeredAtFirstTime() { return BLADEMASTER002_HashMapIsTriggeredAtFirstTime; }

    public static float Blademaster002_triggerGuard(LivingEntity entity, Entity damageSourceEntity, LivingEntity damageSourceEntityOwner, float damage, DamageSource dmgSrc, HashMap<String, Integer> LivingEntityState){
        Boolean triggeredGuardAtFirstTime = BLADEMASTER002_HashMapIsTriggeredAtFirstTime.get(entity);
        if (triggeredGuardAtFirstTime == null) { return damage; }

        float incomingDMG = damage;

        String GuardSkillID = BlademasterSkillTreeInitialization.BLADEMASTER_GUARD;
        SkillDataJson tempSkillData = getID2SkillData().get(GuardSkillID);
        int GuardLVL = 0;
        double physicalDmgBlockPercent = 0, magicalDmgBlockPercent = 0;
        if (entity instanceof ServerPlayer sp1) {
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(GuardSkillID)) {
                GuardLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(GuardSkillID);
                if (GuardLVL > 0) {
                    GuardLVL = GuardLVL - 1; // index out of boundary
                }
                else {
                    return incomingDMG;
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

        physicalDmgBlockPercent = physicalDmgBlockPercent + tempSkillData.getValueMultiplierList_01().get(GuardLVL);
        magicalDmgBlockPercent = magicalDmgBlockPercent + tempSkillData.getValueMultiplierList_01().get(GuardLVL);

        Vector3f HurtEntityLoc = getEntityPos(entity);
        Vector3f DamageSourceLoc = getEntityPos(damageSourceEntity);
        float AngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(DamageSourceLoc, HurtEntityLoc);

        float entityFacingAngle = getEntityHorizontalFacingDeg(entity);
        float tempD = MathMethods.RoundAngle(AngleBtn - 180f); //front for -180 deg
        float range = 70f; //140 deg

        if (triggeredGuardAtFirstTime ||
                (tempD <= MathMethods.RoundAngle(entityFacingAngle) + range
                        && tempD >= MathMethods.RoundAngle(entityFacingAngle) - range)
        ) {
            if (triggeredGuardAtFirstTime) {
                BLADEMASTER002_HashMapIsTriggeredAtFirstTime.put(entity, DfoSwordmanSkillTreeConstants.CONSTANT_BOOLEAN_FALSE);
            }
            //play sound when guarding
            DfoSwdSoundMethods.PlaySwordGuardingSound(getEntityLevel(entity), getEntityX(entity), getEntityY(entity), getEntityZ(entity), 0.6f, 1.0f);

            String dmgSrcMsgStrID = dmgSrc.getMsgId();
            if (DfoGeneralDamageSrc.MAGICAL_DAMAGE_STR_SET.contains(dmgSrcMsgStrID) || dmgSrc instanceof MagicDamageSource magicDamageSource) {
                incomingDMG = (float) (incomingDMG * (1.0 - ExtraMathMethods.clamp(magicalDmgBlockPercent, 0, 1)));
            } else {
                incomingDMG = (float) (incomingDMG * (1.0 - ExtraMathMethods.clamp(physicalDmgBlockPercent, 0, 1)));
            }
        }
        else {

            boolean shouldExecute = damageSourceEntityOwner == null || !isLivingEntityActionInterrupted(damageSourceEntityOwner);

            if (shouldExecute) {
                if (entity instanceof ServerPlayer sp1) {
                    //To client packet
                    NetworkingFetchMsgMethods.FetchPlayerStateToClientSide(sp1, Constants.IS_CHANNELING, 0);

                    NetworkingFetchMsgMethods.FetchPlayerIsImmobilizedBooleanFromServer(sp1, true);
                    if (getTimedAttributeValueFromLivingEntity(entity, SBSAttributes.SBS_ATTRIBUTE_BEING_ATTACKED, Constants.OP_ADDITION) < 8) {
                        applyTimedBeingAttackedToEntity(entity, 8, 7f, Constants.OP_ADDITION, false, DfoSwordmanSkillTreeConstants.BEING_ATTACKED, null,
                                DfoSwordmanLivingEntityEvent::PlayerBeingHitLambdaFunc);

                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.NO_ANIMATION);
                    }

                    //Counter Strike Packet
                    Blademaster004.Blademaster004_SendPacketIfMeetConditionsToClientSide(damageSourceEntity, sp1);
                }
                if (LivingEntityState.containsKey(Constants.IS_CHANNELING)) {
                    LivingEntityState.put(Constants.IS_CHANNELING, 0);
                    LivingEntityState.put(Constants.CHANNELING_INTERRUPTED, 1);
                }
            }
        }
        return incomingDMG;
    }

    public static Boolean Blademaster_checkWhetherToTriggerAutoGuard(LivingEntity entity, Entity damageSourceEntity, boolean isEntityInAction, boolean isEntityChanneling) {
        int autoGuardTriggerChance = Blademaster002_getAutoGuardBuffTriggerChance(entity);
        Boolean triggeredGuardAtFirstTime = getBLADEMASTER002_HashMapIsTriggeredAtFirstTime().get(entity);

        if (!isEntityInAction && !isEntityChanneling && autoGuardTriggerChance > 0 && ExtraMathMethods.randomInt(100) < autoGuardTriggerChance) {
            ServerSkillMethods.incrementEntityStateByAmountX(entity, Constants.IS_IN_ACTION, 1);

            if (triggeredGuardAtFirstTime == null) {
                Blademaster002.getBLADEMASTER002_HashMapIsTriggeredAtFirstTime().put(entity, DfoSwordmanSkillTreeConstants.CONSTANT_BOOLEAN_TRUE);
                triggeredGuardAtFirstTime = DfoSwordmanSkillTreeConstants.CONSTANT_BOOLEAN_TRUE;
            }
            IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
            int actionPeriod = 5;

            float AngleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(getEntityPos(entity), getEntityPos(damageSourceEntity));

            EntityMethods.setEntityHorizontalFacingDeg(entity, AngleBtn);
            int randomInt = (ExtraMathMethods.randomFloat() > 0.5) ? 1 : 2;
            String AnimationChannelingName = String.format("guard%d_channeling",randomInt);
            if (entity instanceof ServerPlayer sp1) {
                DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerHorizontalFacing(sp1, AngleBtn);
                //TODO: [?] add this because need to send inaction packet from server
                NetworkingFetchMsgMethods.FetchPlayerStateToClientSide(sp1,  Constants.IS_IN_ACTION, Constants.ACTION_ON);

                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimWithSpeedModifierToAllClientPlayers(sp1, AnimationChannelingName, 15f);
            }

            GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity, 255, 255, 255, 1f, 0, 2.5f, 0, 8, Blademaster002.AutoGuardIconFlash, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                    DfoSwordmanSkillTreeConstants.NO_ANIMATION, 8, 0, 8, false, false, true, null, 777, true);
            GeoModelSpawnMethods.GenerateCustomFadingCloneEntity(entity, AngleBtn, 0, 1, 255, 255, 224, 150, 0, 0, 0, 20,
                    GeoAnimationAndTextureName.EFF_ENTITY_TEX_FADING_CLONE, false, 4, 8, 8, true, true, AnimationChannelingName, 1f, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                    DfoSwordmanSkillTreeConstants.NO_ANIMATION, true, false, true);

            MethodAction action = (entity2) -> {
                int currentPassedTicks = tempTickCounter.getIntCounter();

                if (currentPassedTicks > Blademaster002.BuffActionTotalTicks) {
                    ServerSkillMethods.incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                    SBSTickEventMethods.setSkillActionDone(entity2, Blademaster002.IsBuffActionActiveSTRID);
                }
                else {
                    tempTickCounter.setIntCounter(currentPassedTicks + actionPeriod);
                }
            };
            float tempD = Vector3fMethods.AngleFromLocationToLocationHorizontal(getEntityPos(damageSourceEntity), getEntityPos(entity));
            float newX = Vector3fMethods.PolarProjectionFromXHorizon(1f, tempD);
            float newZ = Vector3fMethods.PolarProjectionFromZHorizon(1f, tempD);

            setEntitySPDeltaMovement(entity, newX, 0, newZ, true);

            //start loop action
            SBSTickEventMethods.setMethodActionTimerWithCleanAction(entity, Blademaster002.IsBuffActionActiveSTRID, actionPeriod, action, Blademaster002::Blademaster002_ResetAutoGuardIsTriggeredFirstly, false);
        }

        return triggeredGuardAtFirstTime;
    }
}
