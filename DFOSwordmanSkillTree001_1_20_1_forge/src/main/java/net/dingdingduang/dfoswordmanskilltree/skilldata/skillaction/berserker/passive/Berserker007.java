package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.passive;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;

import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker007 {
    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double BleedingApplyChance = skillData.getValueUsage_01().get(correctSkillLevel);
        Double BleedingDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double ExtraHPConsumptionPercentage = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Double ExtraCrossSlashDamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double ExtraCrossSlashAOEMultiplier = skillData.getValueMultiplierList_02().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (BleedingApplyChance.floatValue() * 100)),
                String.format("%d%%", (int) (BleedingDamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (ExtraHPConsumptionPercentage.floatValue() * 100)),
                String.format("%d%%", (int) (ExtraCrossSlashDamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (ExtraCrossSlashAOEMultiplier.floatValue() * 100))
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(BerserkerSkillTreeInitialization.BERSERKER_POWER_OF_BLOOD);
        tempSkillData.setTranslatableTooltipGetter(Berserker007::getSkillDescription);

//        tempSkillData.setPassiveStatusWhenLoginAction(DfoSwordmanSkillMethods::DoNothing);
//        tempSkillData.setPressAddBtnAction(DfoSwordmanSkillMethods::DoNothing);
//        tempSkillData.setPressSubBtnAction(DfoSwordmanSkillMethods::DoNothing);
    }
}
