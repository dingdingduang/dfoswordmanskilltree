package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.passive;

import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isClientSide;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker014 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_BLOOD_MEMORY;

    public static final String BERSERKER014_BUFF_ATTACK_STR_ID = SkillID + "_BUFF_ATTACK";
    public static final String BERSERKER014_BUFF_PHYSICAL_CRIT_STR_ID = SkillID + "_BUFF_PHYSICAL_CRIT";
    public static final String BERSERKER014_DE_BUFF_STR_ID = SkillID + "_DE_BUFF";

    public static HashMap<LivingEntity, Integer> BERSERKER014_HashMapBuffTriggeredTimes;
    public static HashMap<LivingEntity, Integer> BERSERKER014_HashMapDeBuffTriggeredTimes;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        int TriggerChance = (int) (skillData.getValueUsage_02().get(correctSkillLevel) * 100);
        Double duration = skillData.getDuration().get(correctSkillLevel);
        int MaxStacks = skillData.getValueUsage_01().get(correctSkillLevel).intValue();
        int AttackDamageMultiplier = (int) (skillData.getDamageMultiplierList_01().get(correctSkillLevel) * 100);
        int PhysicalAttackChanceBonus = (int) (skillData.getDamageMultiplierList_02().get(correctSkillLevel) * 100);
        int MaxHPReductionMultiplier = (int) (skillData.getDamageMultiplierList_03().get(correctSkillLevel) * 100);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", TriggerChance),
                String.format("%ss", duration),
                String.format("%d", MaxStacks),
                String.format("%d%%", AttackDamageMultiplier),
                String.format("%d%%", PhysicalAttackChanceBonus),
                String.format("-%d%%", MaxHPReductionMultiplier)
        );
    }

    public static void Berserker014_ApplyBuffAndDeBuff(LivingEntity dealer, LivingEntity target) {
        int playerSkillLVL = 0;
        SkillDataJson skillData = getID2SkillData().get(SkillID);
        if (dealer instanceof ServerPlayer sp1) {
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                if (playerSkillLVL <= 0) {
                    return;
                }
                playerSkillLVL = playerSkillLVL - 1;
            }
        }

        float TriggerChance = skillData.getValueUsage_02().get(playerSkillLVL).floatValue();
        if (TriggerChance >= ExtraMathMethods.randomFloat()) {
            int duration2Ticks = (int) (skillData.getDuration().get(playerSkillLVL).floatValue() * 20);
            int MaxStacks = skillData.getValueUsage_01().get(playerSkillLVL).intValue();
            float AttackDamageMultiplier = skillData.getDamageMultiplierList_01().get(playerSkillLVL).floatValue();
            float PhysicalAttackChanceBonus = skillData.getDamageMultiplierList_02().get(playerSkillLVL).floatValue();
            //apply buff
            Integer TriggeredTimes;
            int PrimitiveIntTriggeredTimes;

            TriggeredTimes = BERSERKER014_HashMapBuffTriggeredTimes.get(dealer);
            if (TriggeredTimes == null) {
                PrimitiveIntTriggeredTimes = 1;
                BERSERKER014_HashMapBuffTriggeredTimes.put(dealer, PrimitiveIntTriggeredTimes);
            }
            else {
                PrimitiveIntTriggeredTimes = BERSERKER014_HashMapBuffTriggeredTimes.get(dealer);
                PrimitiveIntTriggeredTimes = Math.min(MaxStacks, PrimitiveIntTriggeredTimes + 1);
                BERSERKER014_HashMapBuffTriggeredTimes.put(dealer, PrimitiveIntTriggeredTimes);
            }

            TimedAttributesDistributor.applyTimedAttackDMGToEntity(dealer, duration2Ticks, PrimitiveIntTriggeredTimes * AttackDamageMultiplier, Constants.OP_MULTIPLY_BASE, false, BERSERKER014_BUFF_ATTACK_STR_ID, null, null);
            TimedAttributesDistributor.applyTimedPhysicCritChanceToEntity(dealer, duration2Ticks, PrimitiveIntTriggeredTimes * PhysicalAttackChanceBonus, Constants.OP_ADDITION, false, BERSERKER014_BUFF_PHYSICAL_CRIT_STR_ID, null, Berserker014::Berserker014_CleanBuffAction);

            DfoStatusGeneralMethods.GenerateEffBuffEntity(dealer, duration2Ticks, DfoStatusGeneralMethods.ATTACK_BUFF_TEXTURE, DfoStatusGeneralMethods.EFF_ENTITY_MOVING_UP, DfoStatusGeneralMethods.ATTACK_BUFF_TEXTURE);

            //apply debuff
            TriggeredTimes = BERSERKER014_HashMapDeBuffTriggeredTimes.get(dealer);
            if (TriggeredTimes == null) {
                PrimitiveIntTriggeredTimes = 1;
                BERSERKER014_HashMapDeBuffTriggeredTimes.put(dealer, PrimitiveIntTriggeredTimes);
            }
            else {
                PrimitiveIntTriggeredTimes = BERSERKER014_HashMapDeBuffTriggeredTimes.get(dealer);
                PrimitiveIntTriggeredTimes = Math.min(MaxStacks, PrimitiveIntTriggeredTimes + 1);
                BERSERKER014_HashMapDeBuffTriggeredTimes.put(dealer, PrimitiveIntTriggeredTimes);
            }

            float HPReductionMultiplier = skillData.getDamageMultiplierList_03().get(playerSkillLVL).floatValue();

            TimedAttributesDistributor.applyTimedMaxHealthToEntity(target, duration2Ticks, -PrimitiveIntTriggeredTimes * HPReductionMultiplier, Constants.OP_MULTIPLY_BASE, false, BERSERKER014_DE_BUFF_STR_ID, null, Berserker014::Berserker014_CleanDeBuffAction);
            DfoStatusGeneralMethods.GenerateEffBuffEntity(target, duration2Ticks, DfoStatusGeneralMethods.BLEEDING_ICON_TEXTURE_PATH, DfoStatusGeneralMethods.EFF_ENTITY_MOVING_DOWN, DfoStatusGeneralMethods.ARMOR_DEBUFF_TEXTURE);
        }
    }

    public static void Berserker014_CleanBuffAction(LivingEntity owner) {
        BERSERKER014_HashMapBuffTriggeredTimes.remove(owner);
    }

    public static void Berserker014_CleanDeBuffAction(LivingEntity target) {
        BERSERKER014_HashMapDeBuffTriggeredTimes.remove(target);
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker014::getSkillDescription);

        if (isClientSide()) { return; }

        //server init
        if (BERSERKER014_HashMapBuffTriggeredTimes == null) {
            BERSERKER014_HashMapBuffTriggeredTimes = new HashMap<>();
            BERSERKER014_HashMapDeBuffTriggeredTimes = new HashMap<>();
        }
    }
}
