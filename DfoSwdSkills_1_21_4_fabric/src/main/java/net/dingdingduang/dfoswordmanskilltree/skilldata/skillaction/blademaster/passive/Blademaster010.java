package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.sbsattributes.PermAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster010 {
    public static final int BLADEMASTER010_ZANBATO_MASTERY_BONUS_EFFECT_1_LVL = 3;
    public static final int BLADEMASTER010_ZANBATO_MASTERY_BONUS_EFFECT_2_LVL = 5;
    public static final int BLADEMASTER010_ZANBATO_MASTERY_BONUS_EFFECT_3_LVL = 7;
    public static final int BLADEMASTER010_ZANBATO_MASTERY_BONUS_EFFECT_4_LVL = 9;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double AttackDamageMultiplier = skillData.getValueUsage_01().get(correctSkillLevel);

        //LVL 3
        String ColorCodeLVL3 = (currentSkillLevel >= 3) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        Double StrikeFromAirDamageInterval = skillData.getDamageList_01().get(correctSkillLevel);
        Double MaximumChargeableForSAAQuickDraw = skillData.getDamageMultiplierList_01().get(correctSkillLevel);

        //LVL 5
        String ColorCodeLVL5 = (currentSkillLevel >= 5) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        Double StunApplyChance = skillData.getValueUsage_02().get(correctSkillLevel);
        Double StunDuration = skillData.getDuration().get(correctSkillLevel);

        //LVL 7
        String ColorCodeLVL7 = (currentSkillLevel >= 7) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        Double ChargeDamageMultiplier = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double ChargeArmorDestructionArmorReductionMultiplier = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Double ChargeArmorDestructionArmorReductionDuration = skillData.getValueMultiplierList_02().get(correctSkillLevel);

        //LVL 9
        String ColorCodeLVL9 = (currentSkillLevel >= 9) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        Double ChargeableDamageMultiplier = skillData.getDamageMultiplierList_03().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (AttackDamageMultiplier.floatValue() * 100)),
                ColorCodeLVL3,
                String.format("%ss", StrikeFromAirDamageInterval),
                String.format("%d%%", (int) (MaximumChargeableForSAAQuickDraw.floatValue() * 100)),
                ColorCodeLVL5,
                String.format("%d%%", (int) (StunApplyChance.floatValue() * 100)),
                String.format("%ss", StunDuration),
                ColorCodeLVL7,
                String.format("%d%%", (int) (ChargeDamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (ChargeArmorDestructionArmorReductionMultiplier.floatValue() * 100)),
                String.format("%ss", ChargeArmorDestructionArmorReductionDuration),
                ColorCodeLVL9,
                String.format("%d%%", (int) (ChargeableDamageMultiplier.floatValue() * 100))
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);
        tempSkillData.setTranslatableTooltipGetter(Blademaster010::getSkillDescription);
        
        MethodAction tempAct =
                (entity1) -> {
                    double amount;
                    int playerSkillLVL = 0;
                    SkillDataJson skill1 = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);

                    if (entity1 instanceof ServerPlayerEntity sp1) {
                        if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY)) {
                            playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY);
                            if (playerSkillLVL <= 0) { return; }
                            playerSkillLVL = playerSkillLVL - 1;
                        }
                    }
                    amount = skill1.getValueUsage_01().get(playerSkillLVL);

                    PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, amount, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_ZANBATO_MASTERY, null);
                };


        tempSkillData.setPassiveStatusWhenLoginAction(tempAct);
        tempSkillData.setPressAddBtnAction(tempAct);
        tempSkillData.setPressSubBtnAction(tempAct);
    }
}
