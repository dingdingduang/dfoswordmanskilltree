package net.dingdingduang.dfoswordmanskilltree.dfostatus;

import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.somebasicskills.event.SBSTickEventAttributeMethods;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.LivingEntity;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.alternative.EffBuffAltEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.SBSAttributeMethods;
import net.dingdingduang.somebasicskills.sbsattributes.SBSAttributes;
import net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor;
import net.dingdingduang.somebasicskills.sbsattributes.statusquery.AttributeServerPlayerStatusQueryMethods;
import net.dingdingduang.somebasicskills.util.MethodAction;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class DfoStatusGeneralMethods {
    public static final int GENERAL_MAX_INFLICT_TIMES_FOR_SHOCK_STATUS = 10;
    public static final int GENERAL_DURATION_TICKS_FOR_SHOCK_STATUS = 200;

    public static final String SHOCK_STATUS = AttributeServerPlayerStatusQueryMethods.TEMPORARY_PREFIX + AttributeServerPlayerStatusQueryMethods.ABNORMAL_STATUS_SHOCK_BASE;
    public static final String SHOCK_STATUS_DOT = SHOCK_STATUS + "DOT";
    public static final String SHOCK_STATUS_ATTRNAME_WITH_OP_POSTFIX = SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_SHOCK.getTranslationKey() + Constants.OP_ADDITION;
    public static int currentShockStatusActiveCounter = 0;

    public static final int oneSec2Tick = 20;
//    public static final int attrTimerPeriod = 10;

    public static ConcurrentHashMap<LivingEntity, HashMap<String, DfoStatusGeneralType>> LivingEntityCorrespondingStatus = new ConcurrentHashMap<>();
    public static HashMap<LivingEntity, HashMap<String, EffBuffAltEntity>> LivingEntityPreviousEffect = new HashMap<>();

//    public static int getShockStatusHashCodeID() { return AttributeServerPlayerStatusQueryMethods.ABNORMAL_STATUS_SHOCK_BASE.hashCode(); }
    public static ConcurrentHashMap<LivingEntity, HashMap<String, DfoStatusGeneralType>> getLivingEntityCorrespondingStatus() { return LivingEntityCorrespondingStatus; }
    public static void setLivingEntityCorrespondingStatus(ConcurrentHashMap<LivingEntity, HashMap<String, DfoStatusGeneralType>> livingEntityCorrespondingStatus) { LivingEntityCorrespondingStatus = livingEntityCorrespondingStatus; }



    //=========================
//    public static final String EFF_ENTITY_ANIMATION_ROTATE_AROUND_HEAD = "debuff_stun_moving_around_center";
    public static final String EFF_ENTITY_MOVING_UP = "buff_moving_up";
    public static final String EFF_ENTITY_MOVING_DOWN = "buff_moving_down";

    public static final String ATTACK_BUFF_TEXTURE = "textures/entity/skilleff/common/buff/attribute/attack/plus/";
    public static final String ATTACK_DEBUFF_TEXTURE = "textures/entity/skilleff/common/buff/attribute/attack/minus/";

    public static final String ARMOR_BUFF_TEXTURE = "textures/entity/skilleff/common/buff/attribute/armor/plus/";
    public static final String ARMOR_DEBUFF_TEXTURE = "textures/entity/skilleff/common/buff/attribute/armor/minus/";

    public static void GenerateEffBuffEntity(LivingEntity target, int durationTicks, String TexturePath, String AnimationName, String ActionID) {
        HashMap<String, EffBuffAltEntity> tempPreviousStatusEffMap;
        if (!LivingEntityPreviousEffect.containsKey(target)) {
            tempPreviousStatusEffMap = new HashMap<>();
            LivingEntityPreviousEffect.put(target, tempPreviousStatusEffMap);
        }
        else {
            tempPreviousStatusEffMap = LivingEntityPreviousEffect.get(target);
        }

        if (!tempPreviousStatusEffMap.containsKey(ActionID)) {
            tempPreviousStatusEffMap.put(ActionID, GeoModelSpawnMethods.GenerateEffBuffAltEntity(target, TexturePath, AnimationName, true, 1f, durationTicks, 0, 0, 0, true, target, true));
        }
        else {
            EffBuffAltEntity effBuffAltEntity = tempPreviousStatusEffMap.get(ActionID);
            if (effBuffAltEntity != null) {
                if (effBuffAltEntity.getLifetime() < durationTicks) {
                    EntityMethods.RemoveEntity(effBuffAltEntity);
                    tempPreviousStatusEffMap.put(ActionID, GeoModelSpawnMethods.GenerateEffBuffAltEntity(target, TexturePath, AnimationName, true, 1f, durationTicks, 0, 0, 0, true, target, true));
                }
            }
            else {
                tempPreviousStatusEffMap.put(ActionID, GeoModelSpawnMethods.GenerateEffBuffAltEntity(target, TexturePath, AnimationName, true, 1f, durationTicks, 0, 0, 0, true, target, true));
            }
        }
    }

    public static HashMap<LivingEntity, HashMap<String, EffBuffAltEntity>> getLivingEntityPreviousEffectMap() { return LivingEntityPreviousEffect; }

    public static void CleanLivingEntityPreviousEff(LivingEntity entity1, String effID) {
        HashMap<String, EffBuffAltEntity> tempPreviousStatusEff2 = LivingEntityPreviousEffect.get(entity1);
        if (tempPreviousStatusEff2 != null) {
            tempPreviousStatusEff2.remove(effID);
//            EffBuffAltEntity tempEffBufAltEntity = tempPreviousStatusEff2.remove(effID);
//            if (tempEffBufAltEntity != null) {
//                EntityMethods.RemoveEntity(tempEffBufAltEntity);
//            }
            if (tempPreviousStatusEff2.isEmpty()) {
                LivingEntityPreviousEffect.remove(entity1);
            }
        }
    }

    public static float getShockStatusDamageFromLivingEntity(LivingEntity target) {
        return (float) (SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_SHOCK, Constants.OP_ADDITION) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_SHOCK, Constants.OP_ADDITION));
    }

    public static float getShockStatusDamageMultiplier(LivingEntity dealer) {
        return 1f + (float) SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(dealer, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_SHOCK, Constants.OP_MULTIPLY_BASE);
    }

    public static void ApplyShockStatusToTargetLivingEntity(LivingEntity owner, LivingEntity target, int durationTicks, float dmgAmount, int maxInflictedTimes, boolean allowDup, String SkillID) {
//        if (LivingEntityCorrespondingStatus == null) {
//            LivingEntityCorrespondingStatus = new ConcurrentHashMap<>();
//            LivingEntityPreviousEffect = new HashMap<>();
//        }
        if (!LivingEntityCorrespondingStatus.containsKey(target)) {
            LivingEntityCorrespondingStatus.put(target, new HashMap<>());
            LivingEntityPreviousEffect.put(target, new HashMap<>());
        }
        if (dmgAmount <= 0) { return; }

        StringBuilder tempShockStatusIDBuilder = new StringBuilder();
        tempShockStatusIDBuilder.append(SHOCK_STATUS);
        if (allowDup) {
            tempShockStatusIDBuilder.append(currentShockStatusActiveCounter);
            tempShockStatusIDBuilder.append("_");
            currentShockStatusActiveCounter++;
        }
        tempShockStatusIDBuilder.append(SkillID);
        String shockStatusSkillID = tempShockStatusIDBuilder.toString();

//        float eightPercentTotalShockDmg = (float) dmgAmount * 0.8f / maxInflictedTimes;
        float eightPercentTotalShockDmg = dmgAmount * 0.8f / maxInflictedTimes;
        DfoShockStatus tempShockStatus = new DfoShockStatus(DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_LIGHT, eightPercentTotalShockDmg, maxInflictedTimes);

        LivingEntityCorrespondingStatus.get(target).put(shockStatusSkillID, tempShockStatus);

        //damage over time
//        float twentyPercentCurrentShockDmg = (float) dmgAmount * 0.2f * getShockStatusDamageMultiplier(owner);
        float twentyPercentCurrentShockDmg = dmgAmount * 0.2f * getShockStatusDamageMultiplier(owner);
        float ticks2Sec = durationTicks * 0.05f;
        float dotShockDmg = twentyPercentCurrentShockDmg / ticks2Sec;


        MethodAction shockDotDamageCleanMethodAction = (target2) -> {
            if (LivingEntityCorrespondingStatus.containsKey(target2)) {
                LivingEntityCorrespondingStatus.get(target2).remove(shockStatusSkillID);

                if (LivingEntityCorrespondingStatus.get(target2).size() <= 0) {
//                    printInGameMsg("over: "+target2.hashCode());
                    LivingEntityCorrespondingStatus.remove(target2);
                    currentShockStatusActiveCounter = 0;
                }
            }
        };

        HashMap<String, Integer> RemainingTickCountMap = SBSTickEventAttributeMethods.getServerEntityAttrNameRemainingTickCountMapFromLivingEntity(target);
        int TrueRemainingDamageShockStatusTicks = durationTicks;
        if (RemainingTickCountMap != null && RemainingTickCountMap.containsKey(SHOCK_STATUS_ATTRNAME_WITH_OP_POSTFIX)) {
            int tempRemainingDamageTicks = RemainingTickCountMap.get(SHOCK_STATUS_ATTRNAME_WITH_OP_POSTFIX);
            if (durationTicks < tempRemainingDamageTicks) {
                TrueRemainingDamageShockStatusTicks = tempRemainingDamageTicks;
            }
        }

        String ShockStatusDamageEventID = SHOCK_STATUS_DOT + EntityMethods.getEntityHashID(target);
        HashMap<String, EffBuffAltEntity> tempPreviousStatusEff = LivingEntityPreviousEffect.get(target);
        if (!tempPreviousStatusEff.containsKey(ShockStatusDamageEventID)) {
            tempPreviousStatusEff.put(ShockStatusDamageEventID, GeoModelSpawnMethods.GenerateEffBuffAltEntity(owner, "textures/entity/skilleff/common/buff/abnormal/shock/", "buff_moving_down", true, 1.5f, durationTicks, 0, 0, 0, true, target, true));
        }
        else {
            EffBuffAltEntity effBuffAltEntity = tempPreviousStatusEff.get(ShockStatusDamageEventID);
            if (effBuffAltEntity != null) {
                EntityMethods.RemoveEntity(effBuffAltEntity);
            }
            tempPreviousStatusEff.put(ShockStatusDamageEventID, GeoModelSpawnMethods.GenerateEffBuffAltEntity(owner, "textures/entity/skilleff/common/buff/abnormal/shock/", "buff_moving_down", true, 1.5f, durationTicks, 0, 0, 0, true, target, true));
        }

        MethodAction shockDotDamageMethodAction = (target2) -> {
//            int remainingTime = tempShockStatus.getRemainingTicks() - attrTimerPeriod;
//            tempShockStatus.setRemainingTicks(remainingTime);
//            if (remainingTime >= 0 && remainingTime % oneSec2Tick == 0) {
//                DfoGeneralDamageSrc.LivingEntityDamageTargetWithShock(owner, target, dotShockDmg);
//            }

//            printInGameMsg("id: "+target2.hashCode());
            float dotAmount = getShockStatusDamageFromLivingEntity(target2);
            if (dotAmount > 0.02f) {
                HashMap<String, Integer> RemainingTickCountMap2 = SBSTickEventAttributeMethods.getServerEntityAttrNameRemainingTickCountMapFromLivingEntity(target2);
                int remainingTime;

//            printInGameMsg("is null? " + (RemainingTickCountMap2 == null));
//            if (RemainingTickCountMap2 !=null ) {
//                printInGameMsg("contains? "+RemainingTickCountMap2.containsKey(SHOCK_STATUS_ATTRNAME_WITH_OP_POSTFIX));
//                if (RemainingTickCountMap2.containsKey(SHOCK_STATUS_ATTRNAME_WITH_OP_POSTFIX)) {
//                    printInGameMsg("int" + RemainingTickCountMap2.get(SHOCK_STATUS_ATTRNAME_WITH_OP_POSTFIX));
//                }
//            }
                if (RemainingTickCountMap2 != null && RemainingTickCountMap2.containsKey(SHOCK_STATUS_ATTRNAME_WITH_OP_POSTFIX) && (remainingTime = RemainingTickCountMap2.get(SHOCK_STATUS_ATTRNAME_WITH_OP_POSTFIX)) >= 0 && remainingTime % oneSec2Tick == 0) {
                    DfoGeneralDamageSrc.LivingEntityDamageTargetWithShock(EntityMethods.getEntityLevel(owner), owner, target2, dotAmount);
                }
//            printInGameMsg("current dot total amount: "+dotAmount);
            }
        };

//        MethodAction shockDotDamageCleanAction = (target2) -> {
//            HashMap<String, EffBuffAltEntity> tempPreviousStatusEff2 = LivingEntityPreviousEffect.get(target2);
//            if (tempPreviousStatusEff2 != null) {
//                tempPreviousStatusEff2.remove(ShockStatusDamageEventID);
//                if (tempPreviousStatusEff2.isEmpty()) {
//                    LivingEntityPreviousEffect.remove(target2);
//                }
//            }
//        };

//        TimedAttributesDistributor.applyTimedAbnormalStatusShockToEntity(target, durationTicks, twentyPercentCurrentShockDmg, Constants.OP_ADDITION, false, shockStatusSkillID, shockDotDamageMethodAction, shockDotDamageCleanMethodAction);
        TimedAttributesDistributor.applyTimedAbnormalStatusShockToEntity(target, durationTicks, dotShockDmg, Constants.OP_ADDITION, false, shockStatusSkillID, null, shockDotDamageCleanMethodAction);
        //[?] for some reason ID must be different for each entity to be unique
        TimedAttributesDistributor.applyTimedAbnormalStatusShockToEntity(target, TrueRemainingDamageShockStatusTicks, 0.01f, Constants.OP_ADDITION, false, ShockStatusDamageEventID, shockDotDamageMethodAction, DfoStatusGeneralMethods::ShockDotDamageCleanAction);
    }

    private static void ShockDotDamageCleanAction(LivingEntity target) {
        HashMap<String, EffBuffAltEntity> tempPreviousStatusEff2 = LivingEntityPreviousEffect.get(target);
        if (tempPreviousStatusEff2 != null) {
            String ShockStatusDamageEventID = SHOCK_STATUS_DOT + EntityMethods.getEntityHashID(target);
            tempPreviousStatusEff2.remove(ShockStatusDamageEventID);
            if (tempPreviousStatusEff2.isEmpty()) {
                LivingEntityPreviousEffect.remove(target);
            }
        }
    }



    //=====================
    // bleeding
    //=====================
    public static final String BLEEDING_STATUS_STR_ID = "BLEEDING_STATUS_STR_ID";
    public static final int BLEEDING_TOTAL_DURATION_TICKS = 60;

    public static float getTrueBleedingDamageFromOwnerAndTarget(LivingEntity owner, LivingEntity target, float totalBleedingDamage) {
        float owner_bleeding_bonus_base = (int) (20 * SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_BLEEDING, Constants.OP_ADDITION) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(owner, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_BLEEDING, Constants.OP_ADDITION));
        float owner_bleeding_bonus_multiplier = (int) (20 * SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_BLEEDING, Constants.OP_MULTIPLY_TOTAL) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(owner, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_BLEEDING, Constants.OP_MULTIPLY_TOTAL));
        float target_bleeding_resist_base = (int) (20 * SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_BLEEDING_RESIST, Constants.OP_ADDITION) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_BLEEDING_RESIST, Constants.OP_ADDITION));
        float target_bleeding_resist_multiplier = (int) (20 * SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_BLEEDING_RESIST, Constants.OP_MULTIPLY_TOTAL) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_BLEEDING_RESIST, Constants.OP_MULTIPLY_TOTAL));

        return (totalBleedingDamage + owner_bleeding_bonus_base - target_bleeding_resist_base) * (1f + owner_bleeding_bonus_multiplier - target_bleeding_resist_multiplier);
    }

    public static void ApplyBleedingStatusToTargetLivingEntity(LivingEntity owner, LivingEntity target, int durationTicks, float dmgAmount) {
        if (!LivingEntityCorrespondingStatus.containsKey(target)) {
            LivingEntityCorrespondingStatus.put(target, new HashMap<>());
            LivingEntityPreviousEffect.put(target, new HashMap<>());
        }
        if (dmgAmount <= 0) { return; }

        float previousRemainingBleedingDamage;
        int totalStacks;

        HashMap<String, DfoStatusGeneralType> TargetCorrespondingStatus = LivingEntityCorrespondingStatus.get(target);
        if (TargetCorrespondingStatus.get(BLEEDING_STATUS_STR_ID) instanceof DfoBleedingStatus previousBleedingStatus) {
            previousRemainingBleedingDamage = MathMethods.max(0, previousBleedingStatus.getTotalBleedingDamageAmount() - previousBleedingStatus.getConsumedBleedingDamageAmount());
            totalStacks = previousBleedingStatus.getTotalStacks() + 1;
        }
        else {
            totalStacks = 1;
            previousRemainingBleedingDamage = 0;
        }

        float totalBleedingDamage = getTrueBleedingDamageFromOwnerAndTarget(owner, target,previousRemainingBleedingDamage + dmgAmount) * (1f + 0.01f * totalStacks);
        if (totalBleedingDamage <= 0) { return; }

        DfoBleedingStatus tempShockStatus = new DfoBleedingStatus(DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_NO_ELEMENT, totalBleedingDamage, totalStacks);
        TargetCorrespondingStatus.put(BLEEDING_STATUS_STR_ID, tempShockStatus);

        HashMap<String, EffBuffAltEntity> tempPreviousStatusEff = LivingEntityPreviousEffect.get(target);
        if (!tempPreviousStatusEff.containsKey(BLEEDING_STATUS_STR_ID)) {
            tempPreviousStatusEff.put(BLEEDING_STATUS_STR_ID, GeoModelSpawnMethods.GenerateEffBuffAltEntity(owner, "textures/entity/skilleff/common/buff/abnormal/bleeding/", "buff_moving_down", true, 1.5f, durationTicks, 0, 0, 0, true, target, true));
        }
        else {
            EffBuffAltEntity effBuffAltEntity = tempPreviousStatusEff.get(BLEEDING_STATUS_STR_ID);
            if (effBuffAltEntity != null) {
                EntityMethods.RemoveEntity(effBuffAltEntity);
            }
            tempPreviousStatusEff.put(BLEEDING_STATUS_STR_ID, GeoModelSpawnMethods.GenerateEffBuffAltEntity(owner, "textures/entity/skilleff/common/buff/abnormal/bleeding/", "buff_moving_down", true, 1.5f, durationTicks, 0, 0, 0, true, target, true));
        }

        MethodAction bleedingDotDamageMethodAction = getBleedingDotDamageMethodAction(owner, durationTicks, tempShockStatus);

        TimedAttributesDistributor.applyTimedAbnormalStatusShockToEntity(target, durationTicks, 0.05f, Constants.OP_ADDITION, false, BLEEDING_STATUS_STR_ID, bleedingDotDamageMethodAction, DfoStatusGeneralMethods::BleedingDotDamageCleanAction);
    }

    private static MethodAction getBleedingDotDamageMethodAction(LivingEntity owner, int durationTicks, DfoBleedingStatus tempShockStatus) {
        float ticks2Secs = durationTicks * 0.05f;
        float dotAmount = tempShockStatus.getTotalBleedingDamageAmount() * 0.5f / ticks2Secs;

        MethodAction bleedingDotDamageMethodAction = (target2) -> {
            if (tempShockStatus.getTotalStacks() > 0) {
                tempShockStatus.setConsumedBleedingDamageAmount(tempShockStatus.getConsumedBleedingDamageAmount() + dotAmount);

                DfoGeneralDamageSrc.LivingEntityDamageTargetWithBleeding(EntityMethods.getEntityLevel(owner), owner, target2, dotAmount);
//                printInGameMsg("total: "+tempShockStatus.getTotalBleedingDamageAmount());
//                printInGameMsg("stack: "+tempShockStatus.getTotalStacks());
//                printInGameMsg("current dot total amount: " + dotAmount);
            }

        };
        return bleedingDotDamageMethodAction;
    }

    private static void BleedingDotDamageCleanAction(LivingEntity target) {
        HashMap<String, DfoStatusGeneralType> TargetStatusMap = LivingEntityCorrespondingStatus.get(target);
        if (TargetStatusMap != null) {
            TargetStatusMap.remove(BLEEDING_STATUS_STR_ID);

            if (TargetStatusMap.size() <= 0) {
                LivingEntityCorrespondingStatus.remove(target);
            }
        }

        HashMap<String, EffBuffAltEntity> tempPreviousStatusEff2 = LivingEntityPreviousEffect.get(target);
        if (tempPreviousStatusEff2 != null) {
            tempPreviousStatusEff2.remove(BLEEDING_STATUS_STR_ID);
            if (tempPreviousStatusEff2.isEmpty()) {
                LivingEntityPreviousEffect.remove(target);
            }
        }
    }

    public static void ApplyBleedingExplosionToTargetLivingEntity(LivingEntity owner, LivingEntity target) {
        HashMap<String, DfoStatusGeneralType> TargetCorrespondingStatus = LivingEntityCorrespondingStatus.get(target);
        if (TargetCorrespondingStatus != null && TargetCorrespondingStatus.get(BLEEDING_STATUS_STR_ID) instanceof DfoBleedingStatus previousBleedingStatus) {
            float TotalBleedingDamage = previousBleedingStatus.getTotalBleedingDamageAmount();
            previousBleedingStatus.resetWhenExploded();

            DfoGeneralDamageSrc.LivingEntityDamageTargetWithBleeding(EntityMethods.getEntityLevel(owner), owner, target, TotalBleedingDamage);

//            printInGameMsg("all damage: "+TotalBleedingDamage);
        }
    }

    public static void ApplyBleedingExplosionToTargetLivingEntity(LivingEntity owner, LivingEntity target, int explodeAtXStacks, float extraDamage) {
        HashMap<String, DfoStatusGeneralType> TargetCorrespondingStatus = LivingEntityCorrespondingStatus.get(target);
        if (TargetCorrespondingStatus != null && TargetCorrespondingStatus.get(BLEEDING_STATUS_STR_ID) instanceof DfoBleedingStatus previousBleedingStatus) {
            if (previousBleedingStatus.getTotalStacks() >= explodeAtXStacks) {
                float TotalBleedingDamage = previousBleedingStatus.getTotalBleedingDamageAmount() + extraDamage;
                previousBleedingStatus.resetWhenExploded();

                DfoGeneralDamageSrc.LivingEntityDamageTargetWithBleeding(EntityMethods.getEntityLevel(owner), owner, target, TotalBleedingDamage);

//            printInGameMsg("all damage: "+TotalBleedingDamage);
            }
        }
    }


    //=====================
    // stun
    //=====================
    public static final String STUN_STATUS_ATTRNAME_WITH_OP_POSTFIX = SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN.getTranslationKey() + Constants.OP_ADDITION;
    public static final String STUN_STATUS_STR_ID = "STUN_STATUS_STR_ID";

    public static int getTrueStunDurationFromOwnerAndTarget(LivingEntity owner, LivingEntity target, int durationTicks) {
//        double owner_stun_bonus_base = SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN, Constants.OP_ADDITION) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN, Constants.OP_ADDITION);
//        double owner_stun_bonus_multiplier = SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN, Constants.OP_MULTIPLY_TOTAL) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN, Constants.OP_MULTIPLY_TOTAL);
//        double target_stun_resist_base = SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN_RESIST, Constants.OP_ADDITION) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN_RESIST, Constants.OP_ADDITION);
//        double target_stun_resist_multiplier = SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN_RESIST, Constants.OP_MULTIPLY_TOTAL) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN_RESIST, Constants.OP_MULTIPLY_TOTAL);

        int owner_stun_bonus_base_to_20Ticks = (int) (20 * SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN, Constants.OP_ADDITION) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(owner, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN, Constants.OP_ADDITION));
        int owner_stun_bonus_multiplier_to_20Ticks = (int) (20 * SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN, Constants.OP_MULTIPLY_TOTAL) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(owner, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN, Constants.OP_MULTIPLY_TOTAL));
        int target_stun_resist_base_to_20Ticks = (int) (20 * SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN_RESIST, Constants.OP_ADDITION) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN_RESIST, Constants.OP_ADDITION));
        int target_stun_resist_multiplier_to_20Ticks = (int) (20 * SBSAttributeMethods.getTimedAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN_RESIST, Constants.OP_MULTIPLY_TOTAL) + SBSAttributeMethods.getPermAttributeValueFromLivingEntity(target, SBSAttributes.SBS_ATTRIBUTE_ABNORMAL_STATUS_STUN_RESIST, Constants.OP_MULTIPLY_TOTAL));

        return (int) ((durationTicks + owner_stun_bonus_base_to_20Ticks - target_stun_resist_base_to_20Ticks) * (1f + owner_stun_bonus_multiplier_to_20Ticks - target_stun_resist_multiplier_to_20Ticks));
    }

    public static void SetMobAi(MobEntity mob, boolean isAiOn) {
//        mob.setNoAi(!isAiOn);
        mob.setAiDisabled(!isAiOn);
//        mob.setAggressive(!isAiOn);
    }

    public static void ApplyStunStatusToTargetLivingEntity(LivingEntity owner, LivingEntity target, int durationTicks, double stunVal) {
//        if (LivingEntityCorrespondingStatus == null) {
//            LivingEntityCorrespondingStatus = new ConcurrentHashMap<>();
//            LivingEntityPreviousEffect = new HashMap<>();
//        }
//        if (!LivingEntityCorrespondingStatus.containsKey(target)) {
//            LivingEntityCorrespondingStatus.put(target, new HashMap<>());
//            LivingEntityPreviousEffect.put(target, new HashMap<>());
//        }

        HashMap<String, Integer> RemainingTickCountMap = SBSTickEventAttributeMethods.getServerEntityAttrNameRemainingTickCountMapFromLivingEntity(target);

//        float previousStunVal;
//        HashMap<String, Double> PreviousAttrValMap = SBSTickEventAttributeMethods.getServerEntityAttrNameWithOP2FinalValue(target);
//        Double previousStunValBoxing;
//        if (PreviousAttrValMap != null && (previousStunValBoxing = PreviousAttrValMap.get(STUN_STATUS_ATTRNAME_WITH_OP_POSTFIX)) != null) {
//            previousStunVal = previousStunValBoxing.floatValue();
//        }
//        else { previousStunVal = 0f; }

        int TrueRemainingDamageShockStatusTicks = getTrueStunDurationFromOwnerAndTarget(owner, target, durationTicks);
        if (RemainingTickCountMap != null && RemainingTickCountMap.containsKey(STUN_STATUS_ATTRNAME_WITH_OP_POSTFIX)) {
            int tempRemainingDamageTicks = RemainingTickCountMap.get(STUN_STATUS_ATTRNAME_WITH_OP_POSTFIX);
            if (durationTicks < tempRemainingDamageTicks) {
                TrueRemainingDamageShockStatusTicks = tempRemainingDamageTicks;
            }
        }

        if (TrueRemainingDamageShockStatusTicks > 0) {
//            LivingEntityCorrespondingStatus.get(target).put(STUN_STATUS_STR_ID, null);
            HashMap<String, EffBuffAltEntity> tempPreviousStatusEff;
            if (!LivingEntityPreviousEffect.containsKey(target)) {
                tempPreviousStatusEff = new HashMap<>();
                LivingEntityPreviousEffect.put(target, tempPreviousStatusEff);
            }
            else {
                tempPreviousStatusEff = LivingEntityPreviousEffect.get(target);
            }
//            HashMap<String, EffBuffAltEntity> tempPreviousStatusEff = LivingEntityPreviousEffect.get(target);
            boolean hasPreviousStunState;
            if (!tempPreviousStatusEff.containsKey(STUN_STATUS_STR_ID)) {
                hasPreviousStunState = false;
                tempPreviousStatusEff.put(STUN_STATUS_STR_ID, GeoModelSpawnMethods.GenerateEffBuffAltEntity(owner, "textures/entity/skilleff/common/buff/abnormal/stun/", "debuff_stun_moving_around_center", true, 1f, TrueRemainingDamageShockStatusTicks, 0, 0, 0, true, target, true));
            }
            else {
                hasPreviousStunState = true;
                EffBuffAltEntity effBuffAltEntity = tempPreviousStatusEff.get(STUN_STATUS_STR_ID);
                if (effBuffAltEntity != null) {
                    EntityMethods.RemoveEntity(effBuffAltEntity);
                }
                tempPreviousStatusEff.put(STUN_STATUS_STR_ID, GeoModelSpawnMethods.GenerateEffBuffAltEntity(owner, "textures/entity/skilleff/common/buff/abnormal/stun/", "debuff_stun_moving_around_center", true, 1f, TrueRemainingDamageShockStatusTicks, 0, 0, 0, true, target, true));
            }

            MethodAction stunCleanAction = null;
            if (target instanceof MobEntity mob) {
                if (!hasPreviousStunState) {
                    SetMobAi(mob, false);
                }
                stunCleanAction = DfoStatusGeneralMethods::MobStunCleanAction;
            }
            else if (target instanceof ServerPlayerEntity sp1) {
                if (!hasPreviousStunState) {
                    DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardKidnapFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapAllKeys);
                }
                stunCleanAction = DfoStatusGeneralMethods::ServerPlayerStunCleanAction;
            }
            TimedAttributesDistributor.applyTimedAbnormalStatusStunToEntity(target, TrueRemainingDamageShockStatusTicks, stunVal, Constants.OP_ADDITION, false, STUN_STATUS_STR_ID,  null, stunCleanAction);
        }
    }

    public static MethodAction AppliesStunAction(final LivingEntity owner, final int totalTicks) {
        return (target) -> {
            DfoStatusGeneralMethods.ApplyStunStatusToTargetLivingEntity(owner, target, totalTicks, 0.1f);
        };
    }

    public static void MobStunCleanAction(LivingEntity entity1) {
        if (entity1 instanceof MobEntity mob1) {
            CleanLivingEntityPreviousEff(mob1, STUN_STATUS_STR_ID);
            SetMobAi(mob1, true);
        }
    }

    public static void ServerPlayerStunCleanAction(LivingEntity entity1) {
//        printInGameMsg("start clean!");
        if (entity1 instanceof ServerPlayerEntity sp1) {
//            printInGameMsg("clean keyboard method.");
            CleanLivingEntityPreviousEff(sp1, STUN_STATUS_STR_ID);

            DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerKeyboardFreeFromServer(sp1, DfoSwordmanSkillTreeConstants.KidnapAllKeys);
        }
    }
}
