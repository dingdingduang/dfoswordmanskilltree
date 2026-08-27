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

public class Blademaster011 {
    public static final int BLADEMASTER011_BLUDGEON_MASTERY_BONUS_EFFECT_1_LVL = 3;
    public static final int BLADEMASTER011_BLUDGEON_MASTERY_BONUS_EFFECT_2_LVL = 5;
    public static final int BLADEMASTER011_BLUDGEON_MASTERY_BONUS_EFFECT_3_LVL = 7;
    public static final int BLADEMASTER011_BLUDGEON_MASTERY_BONUS_EFFECT_4_LVL = 9;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double AttackSpeedMultiplier = skillData.getValueUsage_01().get(correctSkillLevel);

        //LVL 3
        String ColorCodeLVL3 = (currentSkillLevel >= 3) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        Double BackstepShockwave = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double StunApplyChance = skillData.getValueUsage_02().get(correctSkillLevel);
        Double StunDuration = skillData.getDuration().get(correctSkillLevel);

        //LVL 5
        String ColorCodeLVL5 = (currentSkillLevel >= 5) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        Double AerialSlashShockWaveDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double DrawSwordDamageMultiplier = skillData.getValueMultiplierList_02().get(correctSkillLevel);

        //LVL 7
        String ColorCodeLVL7 = (currentSkillLevel >= 7) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        Double ChargeForwardExtraDamageMultiplier = skillData.getDamageMultiplierList_03().get(correctSkillLevel);
        Double ChargeDownwardShockwaveMultiplier = skillData.getDamageList_01().get(correctSkillLevel);
        Double IllusionDanceShockwaveExtraDamageMultiplier = skillData.getDamageList_02().get(correctSkillLevel);

        //LVL 9
        String ColorCodeLVL9 = (currentSkillLevel >= 9) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        Double StrikeFromAirAOEDmgMultiplier = skillData.getDamageList_03().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (AttackSpeedMultiplier.floatValue() * 100)),
                ColorCodeLVL3,
                String.format("%d%%", (int) (BackstepShockwave.floatValue() * 100)),
                String.format("%d%%", (int) (StunApplyChance.floatValue() * 100)),
                String.format("%ss", StunDuration),
                ColorCodeLVL5,
                String.format("%d%%", (int) (AerialSlashShockWaveDamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (DrawSwordDamageMultiplier.floatValue() * 100)),
                ColorCodeLVL7,
                String.format("%d%%", (int) (ChargeForwardExtraDamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (ChargeDownwardShockwaveMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (IllusionDanceShockwaveExtraDamageMultiplier.floatValue() * 100)),
                ColorCodeLVL9,
                String.format("%d%%", (int) (StrikeFromAirAOEDmgMultiplier.floatValue() * 100))
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY);
        tempSkillData.setTranslatableTooltipGetter(Blademaster011::getSkillDescription);
        
        MethodAction tempAct =
                (entity1) -> {
                    double amount;
                    int playerSkillLVL = 0;
                    SkillDataJson skill1 = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY);

                    if (entity1 instanceof ServerPlayer sp1) {
                        playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY);
                        if (playerSkillLVL <= 0) {
                            PermAttributesDistributor.applyPermAttackSpeedToEntity(entity1, 0, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY, null);
                            return;
                        }
                        playerSkillLVL = playerSkillLVL - 1;
                    }
                    amount = skill1.getValueUsage_01().get(playerSkillLVL);

                    PermAttributesDistributor.applyPermAttackSpeedToEntity(entity1, amount, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY, null);
                };


        tempSkillData.setPassiveStatusWhenLoginAction(tempAct);
        tempSkillData.setPressAddBtnAction(tempAct);
        tempSkillData.setPressSubBtnAction(tempAct);
    }
}
