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
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor.*;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker006 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_DERANGE;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double duration = skillData.getDuration().get(correctSkillLevel);
        Double ArmorReductionBase = skillData.getValueUsage_01().get(correctSkillLevel);
        int ArmorReductionMultiplier = (int) (skillData.getValueMultiplierList_01().get(correctSkillLevel) * 100);
        int ArmorToughnessReductionMultiplier = (int) (skillData.getValueMultiplierList_02().get(correctSkillLevel) * 100);
        int AttackDamageMultiplier = (int) (skillData.getDamageMultiplierList_01().get(correctSkillLevel) * 100);
        int AttackSpeedMultiplier = (int) (skillData.getDamageMultiplierList_02().get(correctSkillLevel) * 100);
        int MovementSpeedMultiplier = (int) (skillData.getDamageMultiplierList_03().get(correctSkillLevel) * 100);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%ss", duration),
                String.format("%s", ArmorReductionBase),
                String.format("%d%%", ArmorReductionMultiplier),
                String.format("%d%%", ArmorToughnessReductionMultiplier),
                String.format("%d%%", AttackDamageMultiplier),
                String.format("%d%%", AttackSpeedMultiplier),
                String.format("%d%%", MovementSpeedMultiplier)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker006::getSkillDescription);

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

                        float ArmorReductionBase = skill1.getValueUsage_01().get(playerSkillLVL).floatValue();
                        float ArmorReductionMultiplier = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();
                        float ArmorToughnessReductionMultiplier = skill1.getValueMultiplierList_02().get(playerSkillLVL).floatValue();
                        float AttackDamageMultiplier = skill1.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();
                        float AttackSpeedMultiplier = skill1.getDamageMultiplierList_02().get(playerSkillLVL).floatValue();
                        float MovementSpeedMultiplier = skill1.getDamageMultiplierList_03().get(playerSkillLVL).floatValue();

                        applyTimedArmorToEntity(entity1, duration2Ticks, -ArmorReductionBase, Constants.OP_ADDITION, false, SkillID, null, null);
                        applyTimedArmorToEntity(entity1, duration2Ticks, -ArmorReductionMultiplier, Constants.OP_MULTIPLY_BASE, false, SkillID, null, null);
                        applyTimedArmorToughnessToEntity(entity1, duration2Ticks, -ArmorToughnessReductionMultiplier, Constants.OP_MULTIPLY_BASE, false, SkillID, null, null);
                        applyTimedAttackDMGToEntity(entity1, duration2Ticks, AttackDamageMultiplier, Constants.OP_MULTIPLY_BASE, false, SkillID, null, null);
                        applyTimedAttackSpeedToEntity(entity1, duration2Ticks, AttackSpeedMultiplier, Constants.OP_MULTIPLY_BASE, false, SkillID, null, null);
                        applyTimedMovementSpeedToEntity(entity1, duration2Ticks, MovementSpeedMultiplier, Constants.OP_MULTIPLY_BASE, false, SkillID, null, null);

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
