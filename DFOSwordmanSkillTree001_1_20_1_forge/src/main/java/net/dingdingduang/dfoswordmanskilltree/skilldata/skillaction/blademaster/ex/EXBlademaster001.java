package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.ex;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;

import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class EXBlademaster001 {
    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double DamageMultiplierBonus = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        int AdditionalAttackBonus = skillData.getValueUsage_01().get(correctSkillLevel).intValue();

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplierBonus.floatValue() * 100)),
                String.format("%d", AdditionalAttackBonus)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_CONSECUTIVE_SLASH_UPGRADE);
        tempSkillData.setTranslatableTooltipGetter(EXBlademaster001::getSkillDescription);
    }
}
