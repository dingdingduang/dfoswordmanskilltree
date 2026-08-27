package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.sbsattributes.PermAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.level.ServerPlayer;

import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster003 {
    public static final int BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_1_LVL = 3;
    public static final int BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_2_LVL = 5;
    public static final int BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_3_LVL = 7;
    public static final int BLADEMASTER003_LIGHTSABRE_MASTERY_BONUS_EFFECT_4_LVL = 9;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        //LVL 3
        String ColorCodeLVL3 = (currentSkillLevel >= 3) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        Double SkillDamageMultiplier = skillData.getValueUsage_01().get(correctSkillLevel);
        Double ShockApplyChance = skillData.getValueUsage_02().get(correctSkillLevel);

        //LVL 5
        String ColorCodeLVL5 = (currentSkillLevel >= 5) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        int ConsecutiveAdditionalAttacks = skillData.getDamageList_01().get(correctSkillLevel).intValue();

        //LVL 7
        String ColorCodeLVL7 = (currentSkillLevel >= 7) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        int DrawSwordAdditionalAttacks = skillData.getDamageList_02().get(correctSkillLevel).intValue();
        int SwordDanceAdditionalAttacks = skillData.getDamageMultiplierList_03().get(correctSkillLevel).intValue();
        Double AnimationSpeedBonus = skillData.getValueMultiplierList_01().get(correctSkillLevel);

        //LVL9
        String ColorCodeLVL9 = (currentSkillLevel >= 9) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        int DragonSlashAdditionalAttacks = skillData.getDamageList_03().get(correctSkillLevel).intValue();

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (SkillDamageMultiplier.floatValue() * 100)),
                ColorCodeLVL3,
                String.format("%d%%", (int) (ShockApplyChance.floatValue() * 100)),
                ColorCodeLVL5,
                String.format("%d", ConsecutiveAdditionalAttacks),
                ColorCodeLVL7,
                String.format("%d", DrawSwordAdditionalAttacks),
                String.format("%d", SwordDanceAdditionalAttacks),
                String.format("%d%%", (int) (AnimationSpeedBonus.floatValue() * 100)),
                ColorCodeLVL9,
                String.format("%d", DragonSlashAdditionalAttacks)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY);
        tempSkillData.setTranslatableTooltipGetter(Blademaster003::getSkillDescription);
        
        MethodAction tempAct =
                (entity1) -> {
                    double amount;
                    int playerSkillLVL = 0;
                    SkillDataJson skill1 = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY);

                    if (entity1 instanceof ServerPlayer sp1) {
                        playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY);
                        if (playerSkillLVL <= 0) {
                            PermAttributesDistributor.applyPermSkillDmgToEntity(entity1, 0, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY, null);
                            return;
                        }
                        playerSkillLVL = playerSkillLVL - 1;
                    }
                    amount = skill1.getValueUsage_01().get(playerSkillLVL);

                    PermAttributesDistributor.applyPermSkillDmgToEntity(entity1, amount, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_MASTERY, null);
                };


        tempSkillData.setPassiveStatusWhenLoginAction(tempAct);
        tempSkillData.setPressAddBtnAction(tempAct);
        tempSkillData.setPressSubBtnAction(tempAct);
    }
}
