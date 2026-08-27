package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.Blademaster036;
import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.LivingEntity;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.setClientPlayerInAction;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isLivingEntityInAction;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getCPlayerLastTriggeredNotChannelingActiveSkillID;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getCPlayerState;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster029 {
    public static final String BLADEMASTER029_ANIMATION_START = "flow_stance_start2";
    public static final String BLADEMASTER029_ANIMATION_CHANNELING = "flow_stance_channeling2";
    public static final String BLADEMASTER029_ANIMATION_END = "flow_stance_end2";

//    public static HashMap<LivingEntity, Integer> BLADEMASTER029_HashMapPassedTicks;
    public static HashMap<LivingEntity, Boolean> BLADEMASTER029_HashmapStanceActive = new HashMap<>();
    public static HashMap<LivingEntity, Boolean> BLADEMASTER029_HashmapStanceOtherFlowSkill = new HashMap<>();

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double FlowDamageMultiplier = skillData.getValueMultiplierList_01().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (FlowDamageMultiplier.floatValue() * 100))
        );
    }

    public static void init() {
        String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_STANCE;
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);

        tempSkillData.setTranslatableTooltipGetter(Blademaster029::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement((SkillID2) -> {
            boolean isInCD = isCPlayerSkillInCD(SkillID2);
            boolean isInAction = isPlayerInAction();
            boolean isInStance = isPlayerInFlowStance();
            boolean isChanneling = isPlayerInChannelingState();
            boolean isOnGround = isPlayerOnGround();
            boolean isPlayerBeingHit = isPlayerBeingHit();

            int lastSkillIDHashCode = getCPlayerLastTriggeredNotChannelingActiveSkillID().hashCode();
            boolean isConsecutiveSlash = lastSkillIDHashCode == BlademasterSkillTreeInitialization.BLADEMASTER_CONSECUTIVE_SLASH.hashCode();
            boolean isCounterStrike = lastSkillIDHashCode == BlademasterSkillTreeInitialization.BLADEMASTER_COUNTER_STRIKE.hashCode();
            boolean isLastSkillIDSatisfied = isConsecutiveSlash || isCounterStrike;

            return !isInCD && (!isInAction || isInStance || isLastSkillIDSatisfied) && !isChanneling && isOnGround && !isPlayerBeingHit;
        });
        tempSkillData.setClientCondReqPassedAction((SkillID2) -> {
            if (getCPlayerState().get(DfoSwordmanSkillTreeConstants.IN_FLOW_STANCE) == null || getCPlayerState().get(DfoSwordmanSkillTreeConstants.IN_FLOW_STANCE) == 0) {
                KeyboardMethods.KidnapClientPlayerMovementKeybindings();
                setClientPlayerInAction(Constants.ACTION_ON, SkillID2);
                getCPlayerState().put(DfoSwordmanSkillTreeConstants.IN_FLOW_STANCE, Constants.ACTION_ON);
            }
        });



        if (isClientSide()) {
            return;
        }

        //init
//        if (BLADEMASTER029_HashmapStanceActive == null) {
////            BLADEMASTER029_HashMapPassedTicks = new HashMap<>();
//            BLADEMASTER029_HashmapStanceActive = new HashMap<>();
//            BLADEMASTER029_HashmapStanceOtherFlowSkill = new HashMap<>();
//        }



        //action while channeling on server side
        MethodAction tempActive1Action = (entity1) -> {
            int actionPeriod = 4;

            if (!BLADEMASTER029_HashmapStanceActive.containsKey(entity1)) {
                ServerSkillMethods.incrementEntityStateByAmountXNoPacket(entity1, Constants.IS_IN_ACTION, 1);
                ServerSkillMethods.incrementEntityStateByAmountXNoPacket(entity1, DfoSwordmanSkillTreeConstants.IN_FLOW_STANCE, 1);
                IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
                BLADEMASTER029_HashmapStanceActive.put(entity1, true);
                //play sound when cast stance
                DfoSwdSoundMethods.PlaySwordGuardStartSound(getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.7f, 2f);

                boolean hasZenithOfSwordArtSuperArmor;
                if (entity1 instanceof ServerPlayerEntity sp1) {
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER029_ANIMATION_START);
                    hasZenithOfSwordArtSuperArmor = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_SWORD_ART) >= Blademaster036.BLADEMASTER036_ZENITH_OF_SWORD_ART_BONUS_EFFECT_1_LVL;
                }
                else {
                    hasZenithOfSwordArtSuperArmor = true;
                }

                MethodAction action = (entity2) -> {
//                    boolean contActioning = ServerSkillMethods.getEntityStateAmount(entity2, Constants.ACTION_INTERRUPTED) == 0 && BLADEMASTER029_HashmapStanceActive.containsKey(entity2);
                    boolean contActioning = !ServerSkillMethods.isLivingEntityActionInterrupted(entity2) && BLADEMASTER029_HashmapStanceActive.containsKey(entity2);
                    if (!contActioning) {
                        //play animation
                        Boolean activatedOtherFlowSkill = BLADEMASTER029_HashmapStanceOtherFlowSkill.get(entity2);
                        if (activatedOtherFlowSkill == null) {
                            if (entity2 instanceof ServerPlayerEntity sp1) {
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER029_ANIMATION_END);
                                DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                            }
                        }

                        //play sound when stop stance
                        DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity2), getEntityX(entity2), getEntityY(entity2), getEntityZ(entity2), 0.8f, 2f);
                        //stop loop action
                        ServerSkillMethods.incrementEntityStateByAmountX(entity2, Constants.IS_IN_ACTION, -1);
                        ServerSkillMethods.incrementEntityStateByAmountX(entity2, DfoSwordmanSkillTreeConstants.IN_FLOW_STANCE, -1);
                        BLADEMASTER029_HashmapStanceActive.remove(entity2);
                        SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                    }
                    else {
                        int currentPassedTicks = tempTickCounter.getIntCounter();

                        if (hasZenithOfSwordArtSuperArmor && isLivingEntityInAction(entity2)) {
                            TimedAttributesDistributor.applyTimedBeneficialStatusSuperArmorToEntity(entity2, 30, 0.1f, Constants.OP_ADDITION, false, SkillID, null, null);
                        }

                        //play animation
                        if ((currentPassedTicks - 8) % 500 == 0 && entity2 instanceof ServerPlayerEntity sp1) {
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER029_ANIMATION_CHANNELING);
                        }
                    }

                    tempTickCounter.setIntCounter(tempTickCounter.getIntCounter() + actionPeriod);
                };

                //start loop action
                SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
            }
            else {
                //play animation
                if (entity1 instanceof ServerPlayerEntity sp1) {
                    Boolean activatedOtherFlowSkill = BLADEMASTER029_HashmapStanceOtherFlowSkill.get(entity1);
                    if (activatedOtherFlowSkill == null) {
                        SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, BLADEMASTER029_ANIMATION_END);
                        DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapMovementKeys);
                    }
                }
                //play sound when stop guarding
                DfoSwdSoundMethods.PlaySwordGuardEndSound(getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.8f, 2f);
                //stop loop action
                ServerSkillMethods.incrementEntityStateByAmountX(entity1, Constants.IS_IN_ACTION, -1);
                ServerSkillMethods.incrementEntityStateByAmountX(entity1, DfoSwordmanSkillTreeConstants.IN_FLOW_STANCE, -1);

                BLADEMASTER029_HashmapStanceActive.remove(entity1);
                SBSTickEventMethods.setSkillActionDone(entity1, SkillID);
            }
        };

        tempSkillData.setActiveSkillAction1(tempActive1Action);

//        MethodAction tempSkillCleanAction = (entity1) -> {
//            BLADEMASTER029_HashmapStanceActive.remove(entity1);
//
//            //IMPORTANT! set action off to allow entity to be able to cast skills
////            SendPacketIsInActionToClientSideFromServer(entity1, Constants.ACTION_OFF);
//        };
//        tempSkillData.setClearSkillAction(tempSkillCleanAction);
    }

    public static boolean IsLivingEntityInFlowStance(LivingEntity entity) {
        return BLADEMASTER029_HashmapStanceActive.containsKey(entity);
    }
    public static void deactivateLivingEntityFlowStance(LivingEntity entity) {
        BLADEMASTER029_HashmapStanceActive.remove(entity);
    }
    public static void cleanFlowStanceMap(LivingEntity entity) {
        BLADEMASTER029_HashmapStanceActive.remove(entity);
        BLADEMASTER029_HashmapStanceOtherFlowSkill.remove(entity);
    }

    public static HashMap<LivingEntity, Boolean> getFlowStanceOtherSkillMap() { return BLADEMASTER029_HashmapStanceOtherFlowSkill; }
}
