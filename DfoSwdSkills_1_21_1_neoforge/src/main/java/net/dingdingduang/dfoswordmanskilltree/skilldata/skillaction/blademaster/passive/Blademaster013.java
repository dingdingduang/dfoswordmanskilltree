package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashSet;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster013 {
    public static final String BLADEMASTER_0_13_AERIAL_SLASH_BLUDGEON_SHOCKWAVE = "BLADEMASTER_0_13_AERIAL_SLASH_BLUDGEON_SHOCKWAVE";
//    public static final float BLADEMASTER_0_13_AERIAL_SLASH_BLUDGEON_SHOCKWAVE_AOE = 3f;

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double DamageMultiplier = skillData.getDamageMultiplierList_01().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (DamageMultiplier.floatValue() * 100))
        );
    }

    public static void init() {
        String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_AERIAL_SLASH;
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster013::getSkillDescription);
    }

    public static float Blademaster013_AerialAttackMultiplier(LivingEntity damageSourceEntityOwner, float OriginTotalMultiplier) {
        float totalMultiplier = OriginTotalMultiplier;
        if (damageSourceEntityOwner instanceof ServerPlayer sp1) {
            SkillDataJson tempSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_AERIAL_SLASH);

            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(BlademasterSkillTreeInitialization.BLADEMASTER_AERIAL_SLASH)) {
                int SkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(BlademasterSkillTreeInitialization.BLADEMASTER_AERIAL_SLASH);
                if (SkillLVL > 0) {
                    SkillLVL = SkillLVL - 1; // index out of boundary
                }

                float aerialDmgMultiplier = tempSkillData.getDamageMultiplierList_01().get(SkillLVL).floatValue();
//                Vector3f tempEntityDelta = getEntityDeltaMovement(sp1);
//                tempEntityDelta = new Vector3f(tempEntityDelta.x(), MathMethods.max(0, tempEntityDelta.y()), tempEntityDelta.z());
//                setEntitySPDeltaMovement(sp1, tempEntityDelta, false);
                totalMultiplier = totalMultiplier + aerialDmgMultiplier - 1f;
            }
        }
        return totalMultiplier;
    }

    public static void Blademaster013_AerialSlashBludgeonSlashDownwardAction(LivingEntity damageSourceEntityOwner, LivingEntity target) {
        if (damageSourceEntityOwner instanceof ServerPlayer sp1) {
            int BludgeonMasteryLevel = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY);
            if (BludgeonMasteryLevel >= Blademaster011.BLADEMASTER011_BLUDGEON_MASTERY_BONUS_EFFECT_2_LVL && !isEntityFarFromGround(target, 1) && !isEntityFarFromGround(target, 2)) {
                SkillDataJson AerialSlashSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_AERIAL_SLASH);
                int AerialSlashSkillLVL = 0;
                if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(BlademasterSkillTreeInitialization.BLADEMASTER_AERIAL_SLASH)) {
                    AerialSlashSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(BlademasterSkillTreeInitialization.BLADEMASTER_AERIAL_SLASH);
                    if (AerialSlashSkillLVL <= 0) {
                        return;
                    }
                    AerialSlashSkillLVL = AerialSlashSkillLVL - 1;
                }
                float skillDamageMultiplier = 1f + SBSAttributeMethods.getSkillDamageAttrVal(damageSourceEntityOwner);

                float aoeRange = AerialSlashSkillData.getEffectArea().get(AerialSlashSkillLVL).floatValue();
                float pushYStrength = calculatePushEntityStrength(1f, damageSourceEntityOwner, target);
                setEntitySPDeltaMovement(target, 0, -pushYStrength - 0.5f, 0, true);

                float ShockwaveDamageMultiplier = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_BLUDGEON_MASTERY).getDamageMultiplierList_02().get(BludgeonMasteryLevel - 1).floatValue();
                float finalDmg = (float) ((ShockwaveDamageMultiplier * getEntityAttackDamage(damageSourceEntityOwner)) * skillDamageMultiplier);
                Predicate<Entity> condition = defaultSkillTargetCondition(sp1).and(SBConditions.isNotTargetEntity(damageSourceEntityOwner));

//                DfoStatusGeneralMethods.ApplyStunStatusToTargetLivingEntity(damageSourceEntityOwner, target, 20, 0.1f);

                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(target, 255, 255, 255, 1f, 0, 1.4f, 0, 300, GeoAnimationAndTextureName.BLUDGEON_HIT_SPIRAL, GeoAnimationAndTextureName.ROTATE_2D_ENTITY_CLOCKWISE_1080,
                        DfoSwordmanSkillTreeConstants.NO_ANIMATION, 5, 5, 1, true, true, true, DfoSwordmanSkillMethods.BludgeonFlipShockwave1(BLADEMASTER_0_13_AERIAL_SLASH_BLUDGEON_SHOCKWAVE, damageSourceEntityOwner, aoeRange, finalDmg, condition, new HashSet<>()), 5, true);
            }
        }
    }
}
