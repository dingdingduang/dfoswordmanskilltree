package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.PhysicalDamageSource;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.sbsattributes.PermAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityAttackDamage;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster009 {
    public static final int BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_1_LVL = 3;
    public static final int BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_2_LVL = 5;
    public static final int BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_3_LVL = 7;
    public static final int BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_4_LVL = 9;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        //LVL 3
        String ColorCodeLVL3 = (currentSkillLevel >= 3) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        Double AttackDamageMultiplier = skillData.getValueUsage_01().get(correctSkillLevel);
        Double BleedingApplyChance = skillData.getValueUsage_02().get(correctSkillLevel);

        //LVL 5
        String ColorCodeLVL5 = (currentSkillLevel >= 5) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        int ConsecutiveAdditionalAttacks = skillData.getDamageList_01().get(correctSkillLevel).intValue();

        //LVL 7
        String ColorCodeLVL7 = (currentSkillLevel >= 7) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        int DrawSwordAdditionalAttacks = skillData.getDamageList_02().get(correctSkillLevel).intValue();
        int SwordDanceAdditionalAttacks = skillData.getDamageMultiplierList_03().get(correctSkillLevel).intValue();
        Double AnimationSpeedBonus = skillData.getValueMultiplierList_01().get(correctSkillLevel);

        //LVL 9
        Double BleedingBonusDamageMultiplier = skillData.getValueMultiplierList_02().get(correctSkillLevel);
        String ColorCodeLVL9 = (currentSkillLevel >= 9) ? DfoSwordmanSkillTreeConstants.COLOR_TEAL: DfoSwordmanSkillTreeConstants.COLOR_GRAY;
        int BleedingStacks = skillData.getDamageList_03().get(correctSkillLevel).intValue();
        Double BleedingExplosionDamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (AttackDamageMultiplier.floatValue() * 100)),
                ColorCodeLVL3,
                String.format("%d%%", (int) (BleedingApplyChance.floatValue() * 100)),
                ColorCodeLVL5,
                String.format("%d", ConsecutiveAdditionalAttacks),
                ColorCodeLVL7,
                String.format("%d", DrawSwordAdditionalAttacks),
                String.format("%d", SwordDanceAdditionalAttacks),
                String.format("%d%%", (int) (AnimationSpeedBonus.floatValue() * 100)),
                ColorCodeLVL9,
                String.format("%d%%", (int) (BleedingBonusDamageMultiplier.floatValue() * 100)),
                String.format("%d", BleedingStacks),
                String.format("%d%%", (int) (BleedingExplosionDamageMultiplier.floatValue() * 100))
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY);
        tempSkillData.setTranslatableTooltipGetter(Blademaster009::getSkillDescription);
        
        MethodAction tempAct =
                (entity1) -> {
                    double amount;
                    int playerSkillLVL = 0;
                    SkillDataJson skill1 = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY);

                    if (entity1 instanceof ServerPlayer sp1) {
                        playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY);
                        if (playerSkillLVL <= 0) {
                            PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, 0, Constants.OP_MULTIPLY_BASE, BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY, null);
                            return;
                        }
                        playerSkillLVL = playerSkillLVL - 1;
                    }
                    amount = skill1.getValueUsage_01().get(playerSkillLVL);

                    PermAttributesDistributor.applyPermAttackDMGToEntity(entity1, amount, Constants.OP_MULTIPLY_BASE, BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY, null);
                };


        tempSkillData.setPassiveStatusWhenLoginAction(tempAct);
        tempSkillData.setPressAddBtnAction(tempAct);
        tempSkillData.setPressSubBtnAction(tempAct);
    }

    public static void Blademaster009_KatanaTriggerDamageWhenDealingAnyPhysicalDamage(int damageSourceEntityWeaponType, LivingEntity damageSourceEntity, LivingEntity target, float damageAmount) {
        if (damageSourceEntity instanceof ServerPlayer sp1 && damageSourceEntityWeaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA) {
            int katanaMasteryLvl = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY);
            boolean isBleedingKatanaPassiveActivated = katanaMasteryLvl >= BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_1_LVL;
            if (isBleedingKatanaPassiveActivated) {
                float katanaMasteryBleedingTriggerChance = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY).getValueUsage_02().get(katanaMasteryLvl - 1).floatValue();
                if (ExtraMathMethods.randomFloat() < katanaMasteryBleedingTriggerChance) {
                    DfoStatusGeneralMethods.ApplyBleedingStatusToTargetLivingEntity(damageSourceEntity, target, DfoStatusGeneralMethods.BLEEDING_TOTAL_DURATION_TICKS, damageAmount * DfoStatusGeneralMethods.BLEEDING_DEFAULT_DAMAGE_MULTIPLIER);
                }
            }
        }
    }

    public static float Blademaster009_KatanaBoostDamageWhenDealingAnyPhysicalDamageToEnemiesHaveBleedingStatus(int damageSourceEntityWeaponType, LivingEntity damageSourceEntity, LivingEntity target, float originalIncomingDMG) {
        float incomingDMG = originalIncomingDMG;
        if (damageSourceEntity instanceof ServerPlayer sp1 && damageSourceEntityWeaponType == DfoSkillTreeItemMethods.IS_FAST_KATANA) {
            int katanaMasteryLvl = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY);
            boolean isBleedingKatanaPassiveDamageBoostActivated = katanaMasteryLvl >= BLADEMASTER009_KATANA_MASTERY_BONUS_EFFECT_4_LVL;
            if (isBleedingKatanaPassiveDamageBoostActivated && DfoStatusGeneralMethods.isTargetInBleedingStatus(target)) {
                SkillDataJson skillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_KATANA_MASTERY);
                int correctSkillLevel = katanaMasteryLvl - 1;
                float katanaMasteryDamageBoostOnBleedingEntitiesMultiplier = 1f + skillData.getValueMultiplierList_02().get(correctSkillLevel).floatValue();
                incomingDMG = incomingDMG * katanaMasteryDamageBoostOnBleedingEntitiesMultiplier;

                int BleedingStacks = skillData.getDamageList_03().get(correctSkillLevel).intValue();
                float BleedingExplosionDamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel).floatValue();
                float finalDmg = (float) (BleedingExplosionDamageMultiplier * getEntityAttackDamage(damageSourceEntity) );

                DfoStatusGeneralMethods.ApplyBleedingExplosionToTargetLivingEntity(damageSourceEntity, target, BleedingStacks, finalDmg);
            }
        }
        return incomingDMG;
    }
}
