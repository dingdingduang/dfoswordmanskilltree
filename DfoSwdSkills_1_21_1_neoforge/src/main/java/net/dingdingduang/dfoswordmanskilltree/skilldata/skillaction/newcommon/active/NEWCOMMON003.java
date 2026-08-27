package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.newcommon.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.anexampleskilltree.commonskilltree.CommonSkillInitialization;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.HashMap;

import static net.dingdingduang.somebasicskills.globalmethods.EffectMethods.applyEffectToLivingEntity;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isEntityChannellingInterrupted;
import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.PlaySBSChannelingFailedSoundAtLocation;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues.getSLivingEntityState;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class NEWCOMMON003 {
    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double duration = skillData.getDuration().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%ss", duration)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(CommonSkillInitialization.COMMON_003_LEAP);
        tempSkillData.setTranslatableTooltipGetter(NEWCOMMON003::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);

        //action while channeling on server side
        MethodAction tempChannelingAction = (entity1) -> {
            int actionPeriod = 5;
            //play buff on sound
            DfoSwdSoundMethods.PlayChannelingStartSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);

            int playerSkillLVL = 0;
            SkillDataJson skill1 = getID2SkillData().get(CommonSkillInitialization.COMMON_003_LEAP);
            if (entity1 instanceof ServerPlayer sp1) {
                if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(CommonSkillInitialization.COMMON_003_LEAP)) {
                    playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(CommonSkillInitialization.COMMON_003_LEAP);
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
                    SBSTickEventMethods.setSkillActionDone(entity2, CommonSkillInitialization.COMMON_003_LEAP);
                    //[!] remove entity here?
                }

                if ( (currentPassedTicks - 5) % 500 == 0 && entity2 instanceof ServerPlayer sp1) {
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_LOOPING);
                }
            };

            //start loop action
            SBSTickEventMethods.setMethodActionTimer(entity1, CommonSkillInitialization.COMMON_003_LEAP, actionPeriod, action);
        };

        MethodAction tempChannelingFinalAction =
                (entity1) -> {
                    //condition, if skill is interrupted, do not execute final action
                    if (!isEntityChannellingInterrupted(entity1)) {
                        int duration;
                        int playerSkillLVL = 0;
                        SkillDataJson skill1 = getID2SkillData().get(CommonSkillInitialization.COMMON_003_LEAP);
                        if (entity1 instanceof ServerPlayer sp1) {
                            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(CommonSkillInitialization.COMMON_003_LEAP)) {
                                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(CommonSkillInitialization.COMMON_003_LEAP);
                                if (playerSkillLVL <= 0) {
                                    return;
                                }
                                playerSkillLVL = playerSkillLVL - 1;
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_END);
                            }
                        }
                        GeoModelSpawnMethods.GenerateCustomChannelingIconEntity(entity1, 1f, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_HEIGHT, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_LIFETIME, skill1.getResLocBtn(), true, true);

                        duration = (int) (skill1.getDuration().get(playerSkillLVL).floatValue() * 20);
                        applyEffectToLivingEntity(entity1, new MobEffectInstance(MobEffects.JUMP, duration, playerSkillLVL, false, false));

                        //play buff on sound
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
}
