package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive;

import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isClientSide;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.getSkillsInCooldownClientTimerOverlayInstance;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster016 {
    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double BuffDuration = skillData.getDuration().get(correctSkillLevel);
        Double ASPDMultiplier = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Double MOVMultiplier = skillData.getValueMultiplierList_02().get(correctSkillLevel);
        Double PhyCritMultiplier = skillData.getValueUsage_01().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%ss", BuffDuration),
                String.format("%d%%", (int) (ASPDMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (MOVMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (PhyCritMultiplier.floatValue() * 100))
        );
    }


    private static boolean SetupPassiveClientCondition(String SkillID2) {
        boolean isInCD = getSkillsInCooldownClientTimerOverlayInstance().isClientPlayerSkillInCD(SkillID2);
//                    boolean isInAction = false;
//                    if (getCPlayerState().containsKey(Constants.IS_IN_ACTION)) {
//                        isInAction = getCPlayerState().get(Constants.IS_IN_ACTION) == Constants.ACTION_ON;
//                    }
//                    boolean isChanneling = false;
//                    if (getCPlayerState().containsKey(Constants.IS_CHANNELING)) {
//                        isChanneling = getCPlayerState().get(Constants.IS_CHANNELING) == Constants.ACTION_ON;
//                    }
//                    boolean isOnGround = true;
//                    if (getMinecraftInstance().player != null) {
//                        isOnGround = getMinecraftInstance().player.onGround();
//                    }
//                    boolean isPlayerBeingHit = isPlayerBeingHit();

//                    return !isInCD && !isInAction && !isChanneling && isOnGround;
        return !isInCD;
    }

    private static void ObtainTempBuffStatus(LivingEntity entity1) {
        float apsd_bonus;
        float smov_bonus;
        float phy_crit_bonus;
        int duration;
        int playerSkillLVL = 0;
        SkillDataJson skill1 = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_WEAPON_SWAP);
        //future plan, character panel info
        if (entity1 instanceof ServerPlayer sp1) {
            //Assume getGlobalPlayerSkillID2lvlMap().get(sp1) is not null because it is initialized when player login
            //and is null when player logout
            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_WEAPON_SWAP)) {
                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_WEAPON_SWAP);
                playerSkillLVL = Math.max(1, playerSkillLVL) - 1;
            }
        }
        apsd_bonus = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();
        smov_bonus = skill1.getValueMultiplierList_02().get(playerSkillLVL).floatValue();
        phy_crit_bonus = skill1.getValueUsage_01().get(playerSkillLVL).floatValue();
        duration = (int) (skill1.getDuration().get(playerSkillLVL) * 20);

//                    for (int i = 0; i < 6; i++) {
////                        GeoModelSpawnMethods.GenerateEffBuffEntity(entity1, "textures/entity/skilleff/common/buff/plus/", "buff_moving_up", 2, duration, 0, 0, 0, true, entity1);
//                        GeoModelSpawnMethods.GenerateEffBuffEntity(entity1, "textures/entity/skilleff/common/buff/plus/", "buff_moving_up", 1.5f, duration, 0, 0, 0, true, entity1);
//                    }
        GeoModelSpawnMethods.GenerateEffBuffAltEntity(entity1, GeoAnimationAndTextureName.EFF_BUFF_COMMON_PLUS, "buff_moving_up", true, 1.2f, duration, 0, 0, 0, true, entity1, true);

//                    TimedAttributesDistributor.applyTimedAttackSpeedToEntity(entity1, duration, apsd_bonus, Constants.OP_MULTIPLY_BASE, false, SkillID, EntityMethods::GenerateBeneficialParticleAtEntityPos, null);
        TimedAttributesDistributor.applyTimedAttackSpeedToEntity(entity1, duration, apsd_bonus, Constants.OP_MULTIPLY_BASE, false, BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_WEAPON_SWAP, null, null);
        TimedAttributesDistributor.applyTimedMovementSpeedToEntity(entity1, duration, smov_bonus, Constants.OP_MULTIPLY_BASE, false, BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_WEAPON_SWAP, null, null);
        TimedAttributesDistributor.applyTimedPhysicCritChanceToEntity(entity1, duration, phy_crit_bonus, Constants.OP_ADDITION, false, BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_WEAPON_SWAP, null, null);
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_WEAPON_SWAP);
        tempSkillData.setTranslatableTooltipGetter(Blademaster016::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement(Blademaster016::SetupPassiveClientCondition);

        if (isClientSide()) {
            return;
        }

        tempSkillData.setPassiveSkillAction1(Blademaster016::ObtainTempBuffStatus);
    }
}
