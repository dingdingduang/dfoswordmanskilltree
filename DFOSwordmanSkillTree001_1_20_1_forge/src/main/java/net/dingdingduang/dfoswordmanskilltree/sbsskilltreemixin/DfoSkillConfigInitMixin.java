package net.dingdingduang.dfoswordmanskilltree.sbsskilltreemixin;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.bus.DfoSwdLivingEntityRenderPreEvent;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.ExtraGeneralConfigRegistry;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityGroupMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.condition.SBConditions;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingFetchMsgMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active.Berserker002;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active.Berserker010;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active.Berserker013;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.*;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.event.SBPlayerConfigFileInitHelper;
import net.dingdingduang.somebasicskills.util.MethodConfigHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.function.Predicate;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getCPlayerConfig2Settings;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerLivingEntityValues.getSLivingEntityState;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getSPlayerConfig;

@Mixin(SBPlayerConfigFileInitHelper.class)
public abstract class DfoSkillConfigInitMixin {
    @Shadow private ServerPlayer configSP1;

    @Inject(method = "Lnet/dingdingduang/somebasicskills/event/SBPlayerConfigFileInitHelper;SBClientConfigInitMixinHelper()V",
            at = {@At(value = "TAIL")},
            remap = false)
    public void DfoSwdSkillClientConfigInit(CallbackInfo ci) {
        ExtraGeneralConfigRegistry.ClientConfigInit();
        Blademaster020.ClientConfigInit();
        Blademaster024.ClientConfigInit();
        Blademaster025.ClientConfigInit();
        Blademaster027.ClientConfigInit();
    }

    @Inject(method = "Lnet/dingdingduang/somebasicskills/event/SBPlayerConfigFileInitHelper;SBPlayerUnstuckRequestClientMixinHelper()V",
            at = {@At(value = "TAIL")},
            remap = false)
    public void DfoSwdSkillTreeResetStateClientInitAfter(CallbackInfo ci) {
        //client
        KeyboardMethods.setHotBarKeyKidnapCounter(0);
        KeyboardMethods.setMovementKeyKidnapCounter(0);
        DfoSwdLivingEntityRenderPreEvent.getPreviousEntityXRotDegree().clear();
        Blademaster037.Blademaster037_resetClientMap(getClientPlayer());
    }

    @Inject(method = "Lnet/dingdingduang/somebasicskills/event/SBPlayerConfigFileInitHelper;SBServerConfigInitMixinHelper()V",
            at = {@At(value = "TAIL")},
            remap = false)
    public void DfoSwdSkillServerConfigInit(CallbackInfo ci) {
//        if (getSPlayerConfig().containsKey(configSP1)) {
//            HashMap<String, HashMap<String, MethodConfigHelper>> tempConfigMap = getSPlayerConfig().get(this.configSP1);
//            Blademaster020.ServerConfigInit(tempConfigMap);
//        }
        HashMap<String, HashMap<String, MethodConfigHelper>> tempConfigMap = getSPlayerConfig().get(this.configSP1);
        ExtraGeneralConfigRegistry.ServerConfigInit(tempConfigMap);
        Blademaster020.ServerConfigInit(tempConfigMap);
        Blademaster024.ServerConfigInit(tempConfigMap);
        Blademaster025.ServerConfigInit(tempConfigMap);
        Blademaster027.ServerConfigInit(tempConfigMap);
    }

    @Inject(method = "Lnet/dingdingduang/somebasicskills/event/SBPlayerConfigFileInitHelper;SBServerConfigAfterInitMixinHelper()V",
            at = {@At(value = "TAIL")},
            remap = false)
    public void DfoSwdSkillServerConfigInitAfter(CallbackInfo ci) {
//        if (getSPlayerConfig().containsKey(configSP1)) {
//            HashMap<String, HashMap<String, MethodConfigHelper>> tempConfigMap = getSPlayerConfig().get(this.configSP1);
//            Blademaster020.ServerConfigAfterInit(configSP1, tempConfigMap);
//        }
        HashMap<String, HashMap<String, MethodConfigHelper>> tempConfigMap = getSPlayerConfig().get(this.configSP1);
        ExtraGeneralConfigRegistry.ServerConfigAfterInit(this.configSP1, tempConfigMap);
        Blademaster020.ServerConfigAfterInit(this.configSP1, tempConfigMap);
        Blademaster024.ServerConfigAfterInit(this.configSP1, tempConfigMap);
        Blademaster025.ServerConfigAfterInit(this.configSP1, tempConfigMap);
        Blademaster027.ServerConfigAfterInit(this.configSP1, tempConfigMap);
    }

    @Inject(method = "Lnet/dingdingduang/somebasicskills/event/SBPlayerConfigFileInitHelper;SBPlayerUnstuckRequestServerMixinHelper()V",
            at = {@At(value = "TAIL")},
            remap = false)
    public void DfoSwdSkillTreeResetStateInitAfter(CallbackInfo ci) {
        HashMap<LivingEntity, HashMap<String, Integer>> ServerLivingEntityState = getSLivingEntityState();
        HashMap<String, Integer> LivingEntityState;
        //guard
        if (ServerLivingEntityState != null && ServerLivingEntityState.containsKey(this.configSP1) && (LivingEntityState = ServerLivingEntityState.get(this.configSP1)) != null) {
            LivingEntityState.remove(DfoSwordmanSkillTreeConstants.IS_GUARDING);
        }
        Blademaster029.cleanFlowStanceMap(this.configSP1);
        Blademaster030.getBLADEMASTER030_HashMapTriggeredTimes().remove(this.configSP1);
        Blademaster017.Blademaster017_ResetOverdrive(this.configSP1);
        Blademaster002.Blademaster002_ResetAutoGuard(this.configSP1);
        Blademaster004.Blademaster004_resetDataWhenRespawn(this.configSP1);
        Blademaster037.Blademaster037_resetServerMap(this.configSP1);

        Berserker010.Berserker010_ResetUponRespawn(this.configSP1);
        Berserker002.Berserker02_SpecificSkillsDmgMultiplierRemoval(this.configSP1);
        Berserker013.Berserker013_resetEntityState(this.configSP1);
        DfoSwdNetworkingFetchMsgMethods.FetchClientPlayerResetPacketRequestFromServer(this.configSP1);
    }



    //=====================
    //config action overwrite

    //Server
//    @Inject(method = "Lnet/dingdingduang/somebasicskills/event/SBPlayerConfigFileInitHelper;setLockOnFacingAngle()V",
//            at = {@At(value = "HEAD")},
//            remap = false)
//    public void DfoSwdSkillLockOnMethod(CallbackInfo ci) {
//        int range = getSPlayerConfig().get(configSP1).get(Constants.SB_GENERAL_SETTING).get(Constants.SB_GENERAL_SETTING_LOCK_ON_RANGE).getIntValue();
//
//        Predicate<Entity> cond = SBConditions.defaultSkillTargetCondition(configSP1);
//
//        float minDist = Float.MAX_VALUE;
//        Entity minEntity = null;
//        for (Entity entity: EntityGroupMethods.GroupEnumEntitiesInRangeFilter(configSP1, range, 1, range, cond)) {
//            float currentEntityDist = entity.distanceTo(configSP1);
//            if (entity.distanceTo(configSP1) < minDist) {
//                minDist = currentEntityDist;
//                minEntity = entity;
//            }
//        }
//
//        if (minEntity != null) {
//            EntityMethods.setEntityHorizontalFacingDeg(configSP1, Vector3fMethods.AngleFromLocationToLocationHorizontalNormal(EntityMethods.getEntityPos(configSP1), EntityMethods.getEntityPos(minEntity)) );
//        }
//    }

    //Client
    @Inject(method = "Lnet/dingdingduang/somebasicskills/event/SBPlayerConfigFileInitHelper;setLockOnFacingAngle()F",
            at = {@At(value = "HEAD")},
            remap = false, cancellable = true)
    public void DfoSwdSkillLockOnMethod(CallbackInfoReturnable<Float> cir) {
        int range = getCPlayerConfig2Settings().get(Constants.SB_GENERAL_SETTING).get(Constants.SB_GENERAL_SETTING_LOCK_ON_RANGE).getIntValue();

        Predicate<Entity> cond = SBConditions.defaultSkillTargetClientCondition();

        float minDist = Float.MAX_VALUE;
        Entity triggeredEntity = getClientPlayer();
        Entity minEntity = null;
//        printInGameMsg("size?: "+getMinecraftInstance().level.getEntities(triggeredEntity, EntityGroupMethods.getAABB(EntityMethods.getEntityX(getClientPlayer())+range, EntityMethods.getEntityY(getClientPlayer())+1, EntityMethods.getEntityZ(getClientPlayer())+range, EntityMethods.getEntityX(getClientPlayer())-range, EntityMethods.getEntityY(getClientPlayer())-1, EntityMethods.getEntityZ(getClientPlayer())-range), cond).size());
        for (Entity entity: EntityGroupMethods.GroupEnumEntitiesInRangeFilter(triggeredEntity, range, 1, range, cond)) {
            float currentEntityDist = entity.distanceTo(triggeredEntity);
            if (entity.distanceTo(triggeredEntity) < minDist) {
                minDist = currentEntityDist;
                minEntity = entity;
            }
        }

        if (minEntity != null) {
            float facingAngle = Vector3fMethods.AngleFromLocationToLocationHorizontalInRoundedDegree(EntityMethods.getEntityPos(triggeredEntity), EntityMethods.getEntityPos(minEntity));
//            EntityMethods.setEntityHorizontalFacingDeg(triggeredEntity, facingAngle);
            cir.setReturnValue(facingAngle);
        }
        else {
            cir.setReturnValue(EntityMethods.getEntityHorizontalFacingDeg(triggeredEntity));
        }
    }
}
