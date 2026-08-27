package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.sbsattributes.PermAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster014 {
    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double AttackDamageBonus = skillData.getDamageList_01().get(correctSkillLevel);
        Double AttackDamageMultiplierBonus = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double SkillDamageBonus = skillData.getValueUsage_01().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%s", AttackDamageBonus),
                String.format("%d%%", (int) (AttackDamageMultiplierBonus.floatValue() * 100)),
                String.format("%d%%", (int) (SkillDamageBonus.floatValue() * 100))
        );
    }


    private static void ObtainPermBuffStatus(LivingEntity entity1) {
        int playerSkillLVL = 0;
        SkillDataJson skill1 = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_WIELDING_WEAPON);

        if (entity1 instanceof ServerPlayer sp1) {
            playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_WIELDING_WEAPON);
            if (playerSkillLVL <= 0) {
                PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, 0, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_WIELDING_WEAPON, null);
                PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_WIELDING_WEAPON, null);
                PermAttributesDistributor.applyPermSkillDmgToEntity(entity1, 0, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_WIELDING_WEAPON, null);
                return;
            }
            playerSkillLVL = playerSkillLVL - 1;
        }
        Double AttackDamageBonus = skill1.getDamageList_01().get(playerSkillLVL);
        Double AttackDamageMultiplierBonus = skill1.getDamageMultiplierList_01().get(playerSkillLVL);
        Double SkillDamageBonus = skill1.getValueUsage_01().get(playerSkillLVL);

        PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, AttackDamageBonus, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_WIELDING_WEAPON, null);
        PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, AttackDamageMultiplierBonus, Constants.OP_MULTIPLY_BASE, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_WIELDING_WEAPON, null);
        PermAttributesDistributor.applyPermSkillDmgToEntity(entity1, SkillDamageBonus, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_WIELDING_WEAPON, null);
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_ZENITH_OF_WIELDING_WEAPON);
        tempSkillData.setTranslatableTooltipGetter(Blademaster014::getSkillDescription);

        tempSkillData.setPassiveStatusWhenLoginAction(Blademaster014::ObtainPermBuffStatus);
        tempSkillData.setPressAddBtnAction(Blademaster014::ObtainPermBuffStatus);
        tempSkillData.setPressSubBtnAction(Blademaster014::ObtainPermBuffStatus);
    }
}
