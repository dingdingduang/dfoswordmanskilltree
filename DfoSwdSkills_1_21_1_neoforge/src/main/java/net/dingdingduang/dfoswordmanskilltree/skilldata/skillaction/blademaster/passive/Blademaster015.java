package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.sbsattributes.PermAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isClientSide;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster015 {
//    private static final String UniqueAttackDamageID = "_AttackDamage_";
//    private static final String UniqueAttackDamageMultiplierID = "_AttackDamageMultiplier_";
//    private static final String UniqueCooldownID = "_Cooldown_";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double AttackDamageBonus = skillData.getDamageList_01().get(correctSkillLevel);
        Double AttackDamageMultiplierBonus = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double SkillCooldownReductionBonus = skillData.getValueMultiplierList_01().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%s", AttackDamageBonus),
                String.format("%d%%", (int) (AttackDamageMultiplierBonus.floatValue() * 100)),
                String.format("%d%%", (int) (SkillCooldownReductionBonus.floatValue() * -100))
        );
    }

    private static void ObtainLightsabreTrainingBonus(LivingEntity entity1) {
        int playerSkillLVL = 0;
        SkillDataJson skill1 = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING);
        if (entity1 instanceof ServerPlayer sp1) {
            //and is null when player logout
            Integer level = getGlobalPlayerSkillID2lvlMap().get(sp1).get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING);
            if (level != null) {
                playerSkillLVL = level - 1;
            }
        }
        Double AttackDamageBonus = skill1.getDamageList_01().get(playerSkillLVL);
        Double AttackDamageMultiplierBonus = skill1.getDamageMultiplierList_01().get(playerSkillLVL);
        Double SkillCooldownReductionBonus = skill1.getValueMultiplierList_01().get(playerSkillLVL);

//        printInGameMsg(String.format("3: %s, %s, %s", AttackDamageBonus, AttackDamageMultiplierBonus, SkillCooldownReductionBonus));
//        PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, AttackDamageBonus, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING + UniqueAttackDamageID + EntityMethods.getEntityHashID(entity1), null);
//        PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, AttackDamageMultiplierBonus, Constants.OP_MULTIPLY_BASE, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING + UniqueAttackDamageMultiplierID + EntityMethods.getEntityHashID(entity1), null);
//        PermAttributesDistributor.applyPermCooldownToEntity(entity1, -SkillCooldownReductionBonus, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING + UniqueCooldownID + EntityMethods.getEntityHashID(entity1), null);
        PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, AttackDamageBonus, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING, null);
        PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, AttackDamageMultiplierBonus, Constants.OP_MULTIPLY_BASE, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING, null);
        PermAttributesDistributor.applyPermCooldownToEntity(entity1, -SkillCooldownReductionBonus, Constants.OP_MULTIPLY_BASE, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING, null);
    }

    private static void CleanLightsabreTrainingBonus(LivingEntity entity1) {
        ClientItemOverlayTimer.setLightsabreTrainingActive(false);
//        printInGameMsg("not active");

//        PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, 0, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING + UniqueAttackDamageID + EntityMethods.getEntityHashID(entity1), null);
//        PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING + UniqueAttackDamageMultiplierID + EntityMethods.getEntityHashID(entity1), null);
//        PermAttributesDistributor.applyPermCooldownToEntity(entity1, 0, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING + UniqueCooldownID + EntityMethods.getEntityHashID(entity1), null);
        PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, 0, Constants.OP_ADDITION, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING, null);
        PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING, null);
        PermAttributesDistributor.applyPermCooldownToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING, null);
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING);
        tempSkillData.setTranslatableTooltipGetter(Blademaster015::getSkillDescription);

        if (isClientSide()) {
            return;
        }

        tempSkillData.setPressAddBtnAction(Blademaster015::CleanLightsabreTrainingBonus);
        tempSkillData.setPressSubBtnAction(Blademaster015::CleanLightsabreTrainingBonus);
        tempSkillData.setPassiveSkillAction1(Blademaster015::CleanLightsabreTrainingBonus);
        tempSkillData.setPassiveSkillAction2(Blademaster015::ObtainLightsabreTrainingBonus);
    }
}
