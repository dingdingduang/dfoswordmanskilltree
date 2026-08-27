package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.sbsattributes.PermAttributesDistributor;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.LivingEntity;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods.GroupEnumEntitiesInRangeFilter;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions.defaultSkillTargetCondition;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Blademaster033 {
    public static final String BLADEMASTER033_LIVING_ENTITY_BUFF_NAME = "flow_force";

    //Texture
//    private static final String BLADEMASTER033_TEXTURE_CHARGING_EFF = "textures/entity/skilleff/blademaster/flow_stance/buff_force/charging_eff/";
    public static final String BLADEMASTER033_TEXTURE_CHARGING_COMPLETE = "textures/entity/skilleff/blademaster/flow_stance/buff_force/charge_complete/";

    private static HashMap<LivingEntity, Integer> BLADEMASTER033_HashMapTriggeredTimes = new HashMap<>();

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        Double FlowingSkillDamageMultiplier = skillData.getValueMultiplierList_01().get(correctSkillLevel);
        Double PhysicalCriticalChance = skillData.getValueMultiplierList_02().get(correctSkillLevel);
        Double Duration = skillData.getDuration().get(correctSkillLevel);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", (int) (FlowingSkillDamageMultiplier.floatValue() * 100)),
                String.format("%d%%", (int) (PhysicalCriticalChance.floatValue() * 100)),
                String.format("%ss", Duration)
        );
    }

    public static void init() {
        String SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_FLOW_FORCE;
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);

        tempSkillData.setTranslatableTooltipGetter(Blademaster033::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement((SkillID2) -> {
            boolean isInCD = isCPlayerSkillInCD(SkillID2);
            boolean isInAction = isPlayerInAction();
            boolean isInStance = isPlayerInFlowStance();
            boolean isChanneling = isPlayerInChannelingState();
            boolean isOnGround = isPlayerOnGround();
            boolean isPlayerBeingHit = isPlayerBeingHit();

            return !isInCD && (isInAction && isInStance) && !isChanneling && isOnGround && !isPlayerBeingHit;
        });
//        tempSkillData.setClientCondReqPassedAction(DfoSwordmanSkillMethods::defaultSkillClientConditionPassedAction);



        if (isClientSide()) {
            return;
        }



        //action while channeling on server side
        MethodAction tempActive1Action = (entity1) -> {
            if (Blademaster029.IsLivingEntityInFlowStance(entity1)) {
                DfoSwdSoundMethods.PlayChannelingEndSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.7f, 2f);

                Integer triggeredTimes = BLADEMASTER033_HashMapTriggeredTimes.get(entity1);
                if (triggeredTimes == null) {
                    BLADEMASTER033_HashMapTriggeredTimes.put(entity1, 1);
                }
                else {
                    BLADEMASTER033_HashMapTriggeredTimes.put(entity1, triggeredTimes + 1);
                }
                //play sound when guarding
                DfoSwdSoundMethods.PlaySwordGuardStartSound(getEntityLevel(entity1), getEntityX(entity1), getEntityY(entity1), getEntityZ(entity1), 0.7f, 5.0f);

//                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity1, 255, 255, 255, 1f, 0, 1.4f, 0, 20, BLADEMASTER033_TEXTURE_CHARGING_EFF, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
//                        1, 1, 6, true, true, false, null, 777, true);
                GeoModelSpawnMethods.Generate2DAnimatedTexCustomEntity256BoundToEntity(entity1, 255, 255, 255, 1f, 0, 1.4f, 0, 11, BLADEMASTER033_TEXTURE_CHARGING_COMPLETE, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                        DfoSwordmanSkillTreeConstants.NO_ANIMATION,11, 0, 11, false, false, true, null, 777, true);
                //==============
                //calc
                int playerSkillLVL = 0;
                int flowSkillDamageMultiplier;
                double physicalCriticalChance;
                int duration;

                SkillDataJson skill1 = getID2SkillData().get(SkillID);
                //future plan, character panel info
                if (entity1 instanceof ServerPlayerEntity sp1) {
                    if (getGlobalPlayerSkillID2lvlMap().get(sp1).containsKey(SkillID)) {
                        playerSkillLVL = getGlobalPlayerSkillID2lvlMap().get(sp1).get(SkillID);
                        if (playerSkillLVL <= 0) {
                            return;
                        }
                        playerSkillLVL = playerSkillLVL - 1;
                    }
                }
                flowSkillDamageMultiplier = (int) (skill1.getValueMultiplierList_01().get(playerSkillLVL) * 100);
                physicalCriticalChance = skill1.getValueMultiplierList_02().get(playerSkillLVL);
                duration = (int) (skill1.getDuration().get(playerSkillLVL) * 20);

//                ServerSkillMethods.incrementEntityStateByAmountXNoPacket(entity1, BLADEMASTER033_LIVING_ENTITY_BUFF_NAME, flowSkillDamageMultiplier);
                ServerSkillMethods.setEntityStateAmountNoPacket(entity1, BLADEMASTER033_LIVING_ENTITY_BUFF_NAME, flowSkillDamageMultiplier);

                PermAttributesDistributor.applyPermPhysicCritChanceToEntity(entity1, physicalCriticalChance, Constants.OP_ADDITION, SkillID, null);

                IntegerValueHolder tempTickCounter = new IntegerValueHolder(0);
                int actionPeriod = 10;

                MethodAction action = (entity2) -> {
                    int currentPassedTicks = tempTickCounter.getIntCounter();

                    if (currentPassedTicks > duration) {
                        ServerSkillMethods.setEntityStateAmountNoPacket(entity2, BLADEMASTER033_LIVING_ENTITY_BUFF_NAME, 0);
                        PermAttributesDistributor.applyPermPhysicCritChanceToEntity(entity2, 0, Constants.OP_ADDITION, SkillID, null);
                        BLADEMASTER033_HashMapTriggeredTimes.put(entity2, BLADEMASTER033_HashMapTriggeredTimes.get(entity2) - 1);
                        SBSTickEventMethods.setSkillActionDone(entity2, SkillID);
                    }

                    tempTickCounter.setIntCounter(currentPassedTicks + actionPeriod);
                };

                //start loop action
                SBSTickEventMethods.setMethodActionTimer(entity1, SkillID, actionPeriod, action);

                GeoModelSpawnMethods.GenerateCustomChannelingIconEntity(entity1, 1f, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_HEIGHT, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_LIFETIME, skill1.getResLocBtn(),true, true);
            }
            //Cancel skill
//            else {
//                SBSTickEventMethods.setSkillActionDone(entity1, SkillID);
//            }
        };

        tempSkillData.setActiveSkillAction1(tempActive1Action);

        MethodAction tempSkillCleanAction = (entity1) -> {
            BLADEMASTER033_HashMapTriggeredTimes.remove(entity1);
        };
        tempSkillData.setClearSkillAction(tempSkillCleanAction);
    }
}
