package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues;
import net.dingdingduang.somebasicskills.registries.SoundRegistry;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.LivingEntity;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods.PlayerSBSCastingFailedSound;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isEntityChannellingInterrupted;
import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.PlaySoundAtLivingEntityLocationEX;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues.getSLivingEntityState;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor.applyTimedAttackDMGToEntity;
import static net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor.applyTimedPhysicCritChanceToEntity;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster017 {
    public static final String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_OVERDRIVE;
    public static final String BuffSkillID = BlademasterSkillTreeInitialization.BLADEMASTER_OVERDRIVE+"_BUFF";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double duration = skillData.getDuration().get(correctSkillLevel);
        int AttackDamageMultiplier = (int) (skillData.getValueUsage_01().get(correctSkillLevel) * 100);
        int PhysicalCritChance = (int) (skillData.getValueUsage_02().get(correctSkillLevel) * 100);
        int WeaponConsumptionRate = (int) (skillData.getValueMultiplierList_01().get(correctSkillLevel) * 100);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%ss", duration),
                String.format("%d%%", AttackDamageMultiplier),
                String.format("%d%%", PhysicalCritChance),
                String.format("%d%%", WeaponConsumptionRate)
        );
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Blademaster017::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement(DfoSwordmanSkillMethods::defaultSkillClientCondition);

        //action while channeling on server side
        MethodAction tempChannelingAction = (entity1) -> {
            int actionPeriod = 5;
            //play buff on sound
            DfoSwdSoundMethods.PlayChannelingStartSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);

            int playerSkillLVL = 0;
            SkillDataJson skill1 = getID2SkillData().get(SkillID);
            if (entity1 instanceof ServerPlayerEntity sp1) {
                if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                    playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                    if (playerSkillLVL <= 0) {
                        return;
                    }
                    playerSkillLVL = playerSkillLVL - 1;
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_START);
                }
            }
            float channelingTime = skill1.getChannelingTime().get(playerSkillLVL).floatValue();
            if (channelingTime != 0) {
                float channelingBarAnimationTime = 1 / channelingTime;
                int lifetime = (int) (channelingTime * 20);
                GeoModelSpawnMethods.GenerateRegularChannelingBarEntity(entity1, 1f, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_HEIGHT, 0, lifetime, channelingBarAnimationTime, true);
            }

            IntegerValueHolder tempIntegerTicker = new IntegerValueHolder(0);
            MethodAction action = (entity2) -> {
                int currentPassedTicks = tempIntegerTicker.getIntCounter();
                tempIntegerTicker.setIntCounter(currentPassedTicks + actionPeriod);
                boolean contActioning = false;
                HashMap<String, Integer> ServerLivingEntityState = getSLivingEntityState().get(entity2);
                if (ServerLivingEntityState != null) {
                    contActioning = ServerLivingEntityState.get(Constants.IS_CHANNELING) == 1;
                }
                if (!contActioning) {
                    SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                    //[!] remove entity here?
                }

                if ( (currentPassedTicks - 5) % 500 == 0 && entity2 instanceof ServerPlayerEntity sp1) {
                    SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_LOOPING);
                }
            };

            //start loop action
            SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);
        };

        //channeling final action
        MethodAction tempChannelingFinalAction = (entity1) -> {
                    //condition, if skill is interrupted, do not execute final action
                    if (!isEntityChannellingInterrupted(entity1)) {
                        double attackDamageMultiplier;
                        double phyCritChance;
                        ServerSkillMethods.setEntityStateAmountNoPacket(entity1, BuffSkillID, 1);

                        int playerSkillLVL = 0;
                        SkillDataJson skill1 = getID2SkillData().get(SkillID);
                        if (entity1 instanceof ServerPlayerEntity sp1) {
                            if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                                playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                                if (playerSkillLVL <= 0) {
                                    return;
                                }
                                playerSkillLVL = playerSkillLVL - 1;
                                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_END);
                            }
                        }
                        GeoModelSpawnMethods.GenerateCustomChannelingIconEntity(entity1, 1f, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_HEIGHT, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_LIFETIME, skill1.getResLocBtn(), true, true);

                        int duration2Ticks = (int) (skill1.getDuration().get(playerSkillLVL).floatValue() * 20);
                        attackDamageMultiplier = skill1.getValueUsage_01().get(playerSkillLVL);
                        phyCritChance = skill1.getValueUsage_02().get(playerSkillLVL);

                        DfoStatusGeneralMethods.GenerateEffBuffEntity(entity1, duration2Ticks, DfoStatusGeneralMethods.ATTACK_BUFF_TEXTURE, DfoStatusGeneralMethods.EFF_ENTITY_MOVING_UP, DfoStatusGeneralMethods.ATTACK_BUFF_TEXTURE);
                        applyTimedAttackDMGToEntity(entity1, duration2Ticks, attackDamageMultiplier, Constants.OP_MULTIPLY_BASE, false, SkillID, null, null);
                        applyTimedPhysicCritChanceToEntity(entity1, duration2Ticks, phyCritChance, Constants.OP_ADDITION, false, SkillID, null, Blademaster017::Blademaster017_ResetOverdrive);

                        DfoSwdSoundMethods.PlayChannelingEndSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);
                    }
                    else {
                        DfoSwdSoundMethods.PlayerSBSCastingFailedSound(entity1);
                    }
                };

        //while channeling
        tempSkillData.setChannelingSkillAction1(tempChannelingAction);
        //final action
        tempSkillData.setActiveSkillAction1(tempChannelingFinalAction);

    }

    public static boolean Blademaster017_OverdriveBuffActive(LivingEntity tempLivingEntity) {
        return ServerSkillMethods.getEntityStateAmount(tempLivingEntity, BuffSkillID) == 1;
    }

    public static void Blademaster017_ResetOverdrive(LivingEntity tempLivingEntity) {
        GlobalServerLivingEntityValues.removeStateFromLivingEntity(tempLivingEntity, BuffSkillID);
//        ServerSkillMethods.setEntityStateAmountNoPacket(tempLivingEntity, BuffSkillID, 0);
    }

    public static void Blademaster017_LivingEntityEventOverdriveDurability(LivingEntity damageSourceLivingEntity) {
        if (Blademaster017.Blademaster017_OverdriveBuffActive(damageSourceLivingEntity) && damageSourceLivingEntity instanceof ServerPlayerEntity sp1) {
            int overdriveSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_OVERDRIVE);
            float itemExtraDamageMultiplier = getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_OVERDRIVE).getValueMultiplierList_01().get(overdriveSkillLVL - 1).floatValue();

            int weaponMaintenanceSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, BlademasterSkillTreeInitialization.BLADEMASTER_WEAPON_MAINTENANCE);
            float itemExtraDamageMultiplierReduction = (weaponMaintenanceSkillLVL > 0) ? getID2SkillData().get(BlademasterSkillTreeInitialization.BLADEMASTER_WEAPON_MAINTENANCE).getValueMultiplierList_01().get(weaponMaintenanceSkillLVL - 1).floatValue(): 0;

            float actualItemDamageMultiplier = itemExtraDamageMultiplier - itemExtraDamageMultiplierReduction;
//                    printInGameMsg("actual mul: "+actualItemDamageMultiplier);
            if (actualItemDamageMultiplier > 0) {
                int damageItemAmount = (int) ((actualItemDamageMultiplier) + ExtraMathMethods.randomFloat(actualItemDamageMultiplier));
                DfoSkillTreeItemMethods.ItemStackHurtAndBreakMainHandItem(sp1, damageItemAmount);
//                        printInGameMsg("damage item: "+damageItemAmount);
            }
        }
    }
}
