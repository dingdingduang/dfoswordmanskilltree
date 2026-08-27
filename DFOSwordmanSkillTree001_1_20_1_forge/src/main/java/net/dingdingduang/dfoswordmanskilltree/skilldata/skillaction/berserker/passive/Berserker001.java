package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.passive;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.BooleanValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.sbsattributes.PermAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker001 {
    public static final int BERSERKER001_UPDATE_PERIOD = 20;
//    public static final int BERSERKER001_UPDATE_TIMED_TICKS = BERSERKER001_UPDATE_PERIOD + 5;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double MinimumHPPercentageToActivate = skillData.getValueUsage_01().get(correctSkillLevel);
        Double AttackDamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double AttackSpeedMultiplier = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Double MovementSpeedMultiplier = skillData.getValueMultiplierList_02().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (MinimumHPPercentageToActivate.floatValue() * 100)),
                String.format("%d%%", (int) (AttackDamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (AttackSpeedMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (MovementSpeedMultiplier.floatValue() * 100))
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD);
        tempSkillData.setTranslatableTooltipGetter(Berserker001::getSkillDescription);

        tempSkillData.setPassiveStatusWhenLoginAction(Berserker001::Berserker001_ActivatePassive);
        tempSkillData.setPressAddBtnAction(Berserker001::Berserker001_ActivatePassive);
        tempSkillData.setPressSubBtnAction(Berserker001::Berserker001_ActivatePassive);
    }

    public static void Berserker001_ActivatePassive(LivingEntity entity0) {
        int playerSkillLVL = 0;
        if (entity0 instanceof ServerPlayer sp1) {
            playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD);
            if (playerSkillLVL <= 0) {
                SBSTickEventMethods.setSkillActionDone(entity0, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD);
                return;
            }
        }
        if (SBSTickEventMethods.hasSkillActionRunning(entity0, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD)) {
            SBSTickEventMethods.setMethodActionTimerWithCleanAction(entity0, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, BERSERKER001_UPDATE_PERIOD, Berserker001_PassiveMethodAction(new BooleanValueHolder(false)), null, false);
        }
    }

    public static MethodAction Berserker001_PassiveMethodAction(BooleanValueHolder isPassiveActiveHolder) {
        return (entity1) -> {
            SkillDataJson skillData = getID2SkillData().get(BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD);

            int playerSkillLVL = 0;
            float RaiseMinimumHPPercentage = 0f;
            if (entity1 instanceof ServerPlayer sp1) {
                playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD);
                if (playerSkillLVL <= 0) {
                    PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null);
                    PermAttributesDistributor.applyPermAttackSpeedToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null);
                    PermAttributesDistributor.applyPermMovementSpeedToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null);
                    SBSTickEventMethods.setSkillActionDone(entity1, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD);
                    return;
                }
                playerSkillLVL = playerSkillLVL - 1;
            }
            boolean isPassiveActive = isPassiveActiveHolder.getBooleanVal();


            float currentEntityHealth = EntityMethods.getLivingEntityCurrentHP(entity1);
            float MaxEntityHealth = EntityMethods.getLivingEntityMaxHP(entity1);
            float currentHPPercentage = currentEntityHealth / MaxEntityHealth;
            float MinimumHPPercentageToActivate = skillData.getValueUsage_01().get(playerSkillLVL).floatValue() + RaiseMinimumHPPercentage;

            if (currentHPPercentage < MinimumHPPercentageToActivate) {
                isPassiveActiveHolder.setBooleanVal(true);
                float portion = (MinimumHPPercentageToActivate - currentHPPercentage) / MinimumHPPercentageToActivate;
                float AttackDamageMultiplier = skillData.getDamageMultiplierList_01().get(playerSkillLVL).floatValue() * portion;
                float AttackSpeedMultiplier = skillData.getValueMultiplierList_01().get(playerSkillLVL).floatValue() * portion;
                float MovementSpeedMultiplier = skillData.getValueMultiplierList_02().get(playerSkillLVL).floatValue() * portion;

//            TimedAttributesDistributor.applyTimedAttackDMGToEntity(entity1, BERSERKER007_UPDATE_TIMED_TICKS, AttackDamageMultiplier, Constants.OP_MULTIPLY_BASE, false, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null, null);
//            TimedAttributesDistributor.applyTimedAttackSpeedToEntity(entity1, BERSERKER007_UPDATE_TIMED_TICKS, AttackSpeedMultiplier, Constants.OP_MULTIPLY_BASE, false, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null, null);
//            TimedAttributesDistributor.applyTimedMovementSpeedToEntity(entity1, BERSERKER007_UPDATE_TIMED_TICKS, MovementSpeedMultiplier, Constants.OP_MULTIPLY_BASE, false, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null, null);

                PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, AttackDamageMultiplier, Constants.OP_MULTIPLY_BASE, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null);
                PermAttributesDistributor.applyPermAttackSpeedToEntity(entity1, AttackSpeedMultiplier, Constants.OP_MULTIPLY_BASE, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null);
                PermAttributesDistributor.applyPermMovementSpeedToEntity(entity1, MovementSpeedMultiplier, Constants.OP_MULTIPLY_BASE, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null);
            }
            else if (isPassiveActive) {
                isPassiveActiveHolder.setBooleanVal(false);
                PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null);
                PermAttributesDistributor.applyPermAttackSpeedToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null);
                PermAttributesDistributor.applyPermMovementSpeedToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, BerserkerSkillTreeInitialization.BERSERKER_BERSERKERS_BLOOD, null);
            }
        };
    }
}
