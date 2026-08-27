package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.registries.SoundRegistry;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;

import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isEntityChannellingInterrupted;
import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.PlaySoundAtLivingEntityLocationEX;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues.getSLivingEntityState;
import static net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor.*;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker018 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_RAMPAGE;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double duration = skillData.getDuration().get(correctSkillLevel);
        int SkillDmgMultiplier = (int) (skillData.getValueMultiplierList_01().get(correctSkillLevel) * 100);
        int ExtraDmgReceivedMultiplier = (int) (skillData.getValueMultiplierList_02().get(correctSkillLevel) * 100);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%ss", duration),
                String.format("%d%%", SkillDmgMultiplier),
                String.format("%d%%", ExtraDmgReceivedMultiplier)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker018::getSkillDescription);

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
                playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                if (playerSkillLVL <= 0) {
                    return;
                }
                playerSkillLVL = playerSkillLVL - 1;
                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_START);
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

                if ( (currentPassedTicks - 5) % 500 == 0 && entity2 instanceof ServerPlayer sp1) {
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_LOOPING);
                }
            };

            //start loop action
            SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
        };

        //channeling final action
        MethodAction tempChannelingFinalAction = (entity1) -> {
                    //condition, if skill is interrupted, do not execute final action
                    if (!isEntityChannellingInterrupted(entity1)) {
                        int playerSkillLVL = 0;
                        SkillDataJson skill1 = getID2SkillData().get(SkillID);
                        if (entity1 instanceof ServerPlayer sp1) {
                            playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                            if (playerSkillLVL <= 0) {
                                return;
                            }
                            playerSkillLVL = playerSkillLVL - 1;
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_END);
                        }
                        GeoModelSpawnMethods.GenerateCustomChannelingIconEntity(entity1, 1f, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_HEIGHT, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_LIFETIME, skill1.getResLocBtn(), true, true);

                        int duration2Ticks = (int) (skill1.getDuration().get(playerSkillLVL).floatValue() * 20);

                        DfoStatusGeneralMethods.GenerateEffBuffEntity(entity1, duration2Ticks, DfoStatusGeneralMethods.ATTACK_BUFF_TEXTURE, DfoStatusGeneralMethods.EFF_ENTITY_MOVING_UP, DfoStatusGeneralMethods.ATTACK_BUFF_TEXTURE);
                        DfoStatusGeneralMethods.GenerateEffBuffEntity(entity1, duration2Ticks, DfoStatusGeneralMethods.ARMOR_DEBUFF_TEXTURE, DfoStatusGeneralMethods.EFF_ENTITY_MOVING_DOWN, DfoStatusGeneralMethods.ARMOR_DEBUFF_TEXTURE);

                        float SkillDmgMultiplier = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();
                        float ExtraDmgReceivedMultiplier = skill1.getValueMultiplierList_02().get(playerSkillLVL).floatValue();

                        applyTimedSkillDmgToEntity(entity1, duration2Ticks, SkillDmgMultiplier, Constants.OP_MULTIPLY_BASE, false, SkillID, null, null);
                        applyTimedExtraDamageReceivedToEntity(entity1, duration2Ticks, ExtraDmgReceivedMultiplier, Constants.OP_MULTIPLY_BASE, false, SkillID, null, null);

                        DfoSwdSoundMethods.PlayChannelingEndSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);
                    }
                    else {
                        PlaySoundAtLivingEntityLocationEX(entity1, SoundRegistry.CHANNELING_FAILED_SOUND.get(), 0.4f, 1.0f);
                    }
                };

        //while channeling
        tempSkillData.setChannelingSkillAction1(tempChannelingAction);
        //final action
        tempSkillData.setActiveSkillAction1(tempChannelingFinalAction);
    }
}
