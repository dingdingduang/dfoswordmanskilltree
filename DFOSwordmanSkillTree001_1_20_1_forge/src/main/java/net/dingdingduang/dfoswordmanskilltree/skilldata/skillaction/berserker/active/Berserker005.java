package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfostatus.DfoStatusGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoAnimationAndTextureName;
import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSwdSoundMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.typevalueholders.IntegerValueHolder;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBSTickEventMethods;
import net.dingdingduang.somebasicskills.globalmethods.LocaleLanguageMethods;
import net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods;
import net.dingdingduang.somebasicskills.registries.SoundRegistry;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.dingdingduang.somebasicskills.util.MethodAction;

import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods.setClientPlayerInAction;
import static net.dingdingduang.somebasicskills.globalmethods.ServerSkillMethods.isEntityChannellingInterrupted;
import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.PlaySoundAtLivingEntityLocationEX;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getClientPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues.getSLivingEntityState;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getGlobalPlayerSkillID2lvlMap;
import static net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay.isCPlayerSkillInCD;
import static net.dingdingduang.somebasicskills.sbsattributes.TimedAttributesDistributor.*;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class Berserker005 {
    public static final String SkillID = BerserkerSkillTreeInitialization.BERSERKER_DENIAL_OF_DEATH;

    public static final String BERSERKER005_DENIAL_OF_DEATH_TEXTURE_PATH = "textures/entity/skilleff/berserker/denial_of_death/";

    private static String getSkillDescription(SkillDataJson skillData, int currentSkillLevel) {
        int correctSkillLevel = 0;
        if (currentSkillLevel > 0) {
            correctSkillLevel = currentSkillLevel - 1;
        }
        int MinimumHPRequirement = (int) (skillData.getValueUsage_01().get(correctSkillLevel) * 100);
        Double duration = skillData.getDuration().get(correctSkillLevel);
        int HPRecovery = (int) (skillData.getValueMultiplierList_01().get(correctSkillLevel) * 100);
        Double ArmorBoostBase = skillData.getValueUsage_02().get(correctSkillLevel);
        int ArmorBoostMultiplier = (int) (skillData.getValueMultiplierList_02().get(correctSkillLevel) * 100);

        return LocaleLanguageMethods.getTranslatableDescriptionWithArgs(skillData.getTranslatableTextDescription(),
                String.format("%d%%", MinimumHPRequirement),
                String.format("%ss", duration),
                String.format("%d%%", HPRecovery),
                String.format("%s", ArmorBoostBase),
                String.format("%d%%", ArmorBoostMultiplier)
        );
    }

    public static boolean SkillClientCondition(String SkillID) {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        float currentEntityHealth = EntityMethods.getLivingEntityCurrentHP(getClientPlayer());
        float MaxEntityHealth = EntityMethods.getLivingEntityMaxHP(getClientPlayer());
        float currentHPPercentage = currentEntityHealth / MaxEntityHealth;
        int currentSkillLevel = getClientPlayerSkillID2lvlMap().get(BerserkerSkillTreeInitialization.BERSERKER_DENIAL_OF_DEATH) - 1;
        float MinimumHPPercentageToActivate = tempSkillData.getValueUsage_01().get(currentSkillLevel).floatValue();

        if (currentHPPercentage < MinimumHPPercentageToActivate) {
            boolean isInCD = isCPlayerSkillInCD(SkillID);
            boolean isInAction = isPlayerInAction();
            boolean isChanneling = isPlayerInChannelingState();
            boolean isOnGround = isPlayerOnGround();
            boolean isPlayerBeingHit = isPlayerBeingHit();

            return !isInCD && !isInAction && !isChanneling && isOnGround && !isPlayerBeingHit;
        }
        return false;
    }

    public static void init() {
        SkillDataJson tempSkillData = getID2SkillData().get(SkillID);
        tempSkillData.setTranslatableTooltipGetter(Berserker005::getSkillDescription);

        //condition: check to see if qualify before activate skill
        tempSkillData.setClientConditionRequirement(Berserker005::SkillClientCondition);

        //action while channeling on server side
        MethodAction tempChannelingAction = (entity1) -> {
            int actionPeriod = 5;
            //play buff on sound
            DfoSwdSoundMethods.PlayChannelingStartSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);

            int playerSkillLVL = 0;
            SkillDataJson skill1 = getID2SkillData().get(SkillID);
            if (entity1 instanceof ServerPlayer sp1) {
                playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                if (playerSkillLVL <= 0) {
                    return;
                }
                playerSkillLVL = playerSkillLVL - 1;
                SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_START);
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

                if ( (currentPassedTicks - 5) % 500 == 0 && entity2 instanceof ServerPlayer sp1) {
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
                        int playerSkillLVL = 0;
                        SkillDataJson skill1 = getID2SkillData().get(SkillID);
                        if (entity1 instanceof ServerPlayer sp1) {
                            playerSkillLVL = ServerSkillMethods.querySPlayerCurrentSkillLevel(sp1, SkillID);
                            if (playerSkillLVL <= 0) {
                                return;
                            }
                            playerSkillLVL = playerSkillLVL - 1;
                            SBPlayerAnimatorMethods.PlayDfoSwdSkillAnimToAllClientPlayers(sp1, DfoSwordmanSkillTreeConstants.RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_END);
                        }
                        GeoModelSpawnMethods.GenerateCustomChannelingIconEntity(entity1, 1f, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_HEIGHT, 0, DfoSwordmanSkillTreeConstants.REGULAR_CHANNELING_ICON_LIFETIME, skill1.getResLocBtn(), true, true);

                        GeoModelSpawnMethods.Generate2DAnimatedTexToNorthCustomEntityYRotOnly256BoundToEntity(entity1, EntityMethods.getEntityHorizontalFacingDeg(entity1), true, 2f, 0, 1.6f, 0, 18, BERSERKER005_DENIAL_OF_DEATH_TEXTURE_PATH, DfoSwordmanSkillTreeConstants.NO_ANIMATION,
                                0, 0, 18, true, false, false, false, null, 777, true);

                        int duration2Ticks = (int) (skill1.getDuration().get(playerSkillLVL).floatValue() * 20);

                        DfoStatusGeneralMethods.GenerateEffBuffEntity(entity1, duration2Ticks, DfoStatusGeneralMethods.ARMOR_BUFF_TEXTURE, DfoStatusGeneralMethods.EFF_ENTITY_MOVING_UP, DfoStatusGeneralMethods.ARMOR_BUFF_TEXTURE);
                        DfoStatusGeneralMethods.GenerateEffBuffEntity(entity1, duration2Ticks, GeoAnimationAndTextureName.EFF_BUFF_COMMON_PLUS, DfoStatusGeneralMethods.EFF_ENTITY_MOVING_UP, GeoAnimationAndTextureName.EFF_BUFF_COMMON_PLUS);

                        float HPRecoveryEverySecond = skill1.getValueMultiplierList_01().get(playerSkillLVL).floatValue();

                        float ArmorBoostBase = skill1.getValueUsage_01().get(playerSkillLVL).floatValue();
                        float ArmorBoostMultiplier = skill1.getValueMultiplierList_02().get(playerSkillLVL).floatValue();

                        applyTimedArmorToEntity(entity1, duration2Ticks, ArmorBoostBase, Constants.OP_ADDITION, false, SkillID, Berserker005_HPRecoveryAction(HPRecoveryEverySecond), null);
                        applyTimedArmorToEntity(entity1, duration2Ticks, ArmorBoostMultiplier, Constants.OP_MULTIPLY_BASE, false, SkillID, null, null);

                        DfoSwdSoundMethods.PlayChannelingEndSound(EntityMethods.getEntityLevel(entity1), EntityMethods.getEntityX(entity1), EntityMethods.getEntityY(entity1), EntityMethods.getEntityZ(entity1), 0.6f, 1.0f);
                    }
                    else {
                        PlaySoundAtLivingEntityLocationEX(entity1, SoundRegistry.CHANNELING_FAILED_SOUND.get(), 0.4f, 1.0f);
                    }
                };

        //while channeling
        tempSkillData.setChannelingSkillAction1(tempChannelingAction);
        //final action
        tempSkillData.setActiveSkillAction1(tempChannelingFinalAction);

    }

    public static MethodAction Berserker005_HPRecoveryAction(float HPRecoveryEverySecond) {
        return (livingEntity) -> {
//            if (MathMethods.isEven(EntityMethods.getEntityTickCount(livingEntity))) {
//            }
            float MaxEntityHealth = EntityMethods.getLivingEntityMaxHP(livingEntity);
            float hpShouldIncrease = MaxEntityHealth * HPRecoveryEverySecond * 0.5f;
            float currentEntityHealth = EntityMethods.getLivingEntityCurrentHP(livingEntity) + hpShouldIncrease;

            EntityMethods.setLivingEntityCurrentHP(livingEntity, MathMethods.max(0, currentEntityHealth));
        };
    }
}
