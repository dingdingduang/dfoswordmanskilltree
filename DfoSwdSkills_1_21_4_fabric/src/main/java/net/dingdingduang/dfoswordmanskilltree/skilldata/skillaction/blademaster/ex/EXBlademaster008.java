package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.ex;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;

import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class EXBlademaster008 {
    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double PhysicDamageAbsorption = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Double MagicDamageAbsorption = skillData.getValueMultiplierList_02().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (PhysicDamageAbsorption.floatValue() * 100)),
                String.format("%d%%", (int) (MagicDamageAbsorption.floatValue() * 100))
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.EXBLADEMASTER_GUARD_UPGRADE);
        tempSkillData.setTranslatableTooltipGetter(EXBlademaster008::getSkillDescription);
    }
}
