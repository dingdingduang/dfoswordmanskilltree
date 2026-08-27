package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.passive;

import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.sbsattributes.PermAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker019 {
    private static String SkillID = BerserkerSkillTreeInitialization.BERSERKER_BLOOD_LEECH;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }

        Double LifeStealMultiplierBonus = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Double ExtraDamageOnBleedingEntitiesMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);
        Double GoreCrossDmgMultiplierBonus = skillData.getDamageMultiplierList_02().get(correctSkillLevel);
        Double MountainousWheelDmgMultiplierBonus = skillData.getDamageMultiplierList_03().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (LifeStealMultiplierBonus.floatValue() * 100)),
                String.format("%d%%", (int) (ExtraDamageOnBleedingEntitiesMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (GoreCrossDmgMultiplierBonus.floatValue() * 100)),
                String.format("%d%%", (int) (MountainousWheelDmgMultiplierBonus.floatValue() * 100))
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker019::getSkillDescription);
        
        MethodAction tempAct =
                (entity1) -> {
                    double amount;
                    int playerSkillLVL = 0;
                    SkillDataJson skill1 = getID2SkillData().get(SkillID);

                    if (entity1 instanceof ServerPlayer sp1) {
                        playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                        if (playerSkillLVL <= 0) {
                            PermAttributesDistributor.applyPermLifeStealToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, SkillID, null);
                            return;
                        }
                        playerSkillLVL = playerSkillLVL - 1;
                    }
                    amount = skill1.getValueMultiplierList_01().get(playerSkillLVL);

                    PermAttributesDistributor.applyPermLifeStealToEntity(entity1, amount, Constants.OP_MULTIPLY_BASE, SkillID, null);
                };


        tempSkillData.setPassiveStatusWhenLoginAction(tempAct);
        tempSkillData.setPressAddBtnAction(tempAct);
        tempSkillData.setPressSubBtnAction(tempAct);
    }

    public static float Berserker019_DamageBoostWhenDealingAnyPhysicalDamageToEnemiesWithBleedingStatus(LivingEntity damageSourceEntity, LivingEntity target, float originalIncomingDMG) {
        float incomingDMG = originalIncomingDMG;
        if (damageSourceEntity instanceof ServerPlayer sp1) {
            int skillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
            if (skillLVL > 0 && DfoStatusGeneralMethods.isTargetInBleedingStatus(target)) {
                SkillDataJson skillData = getID2SkillData().get(SkillID);
                int correctSkillLevel = skillLVL - 1;
                float DamageBoostOnBleedingEntitiesMultiplier = 1f + skillData.getDamageMultiplierList_01().get(correctSkillLevel).floatValue();
                incomingDMG = incomingDMG * DamageBoostOnBleedingEntitiesMultiplier;
            }
        }
        return incomingDMG;
    }
}
