package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.somebasicskills.Constants;
import net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods;
import net.dingdingduang.somebasicskills.networking.NetworkingFetchMsgMethods;
import net.dingdingduang.somebasicskills.networking.NetworkingSendMsgMethods;
import net.dingdingduang.somebasicskills.util.MethodConfigHelper;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;

import static net.dingdingduang.somebasicskills.globalvalues.GlobalClientPlayerValues.getCPlayerConfig2Settings;
import static net.dingdingduang.somebasicskills.globalvalues.GlobalServerPlayerValues.getSConfig;

public class ExtraGeneralConfigRegistry {
    public static void DefaultSettingLoopIntFrom1to20(String configName, String configDetailName, MethodConfigHelper configHelper) {
        int currentInt = configHelper.getIntValue() % 20 + 1;
        configHelper.setIntValue(currentInt);
        NetworkingSendMsgMethods.SendConfigKeyValFromClientSideToServer(configName, configDetailName, currentInt, false);
    }

    public static void ClientConfigInit() {
        HashMap<String, MethodConfigHelper> GeneralSetting = getCPlayerConfig2Settings().get(Constants.SB_GENERAL_SETTING);
        GeneralSetting.put(DfoSwordmanSkillTreeConstants.SBS_EXTRA_GENERAL_CONFIG_TARGET_ZONE_SPEED, new MethodConfigHelper(ExtraGeneralConfigRegistry::DefaultSettingLoopIntFrom1to20, 10, false));
    }

    public static void ServerConfigInit(HashMap<String, HashMap<String, MethodConfigHelper>> SPlayerConfigMap) {
        getSConfig().add(DfoSwordmanSkillTreeConstants.SBS_EXTRA_GENERAL_CONFIG_TARGET_ZONE_SPEED);
        HashMap<String, MethodConfigHelper> GeneralSetting = SPlayerConfigMap.get(Constants.SB_GENERAL_SETTING);
        GeneralSetting.put(DfoSwordmanSkillTreeConstants.SBS_EXTRA_GENERAL_CONFIG_TARGET_ZONE_SPEED, new MethodConfigHelper(null, 0, true));
    }

    public static void ServerConfigAfterInit(ServerPlayer sp1, HashMap<String, HashMap<String, MethodConfigHelper>> SPlayerConfigMap) {
        //sync to client
        int val = SPlayerConfigMap.get(Constants.SB_GENERAL_SETTING).get(DfoSwordmanSkillTreeConstants.SBS_EXTRA_GENERAL_CONFIG_TARGET_ZONE_SPEED).getIntValue();
        NetworkingFetchMsgMethods.FetchConfigKeyValToClientSide(sp1, Constants.SB_GENERAL_SETTING, DfoSwordmanSkillTreeConstants.SBS_EXTRA_GENERAL_CONFIG_TARGET_ZONE_SPEED, val);
    }


    //method
    public static float getTargetZoneSelectionSpeedOnClientSide() {
        int speedLevel = ClientPlayerMethods.getCPlayerConfigVal(Constants.SB_GENERAL_SETTING, DfoSwordmanSkillTreeConstants.SBS_EXTRA_GENERAL_CONFIG_TARGET_ZONE_SPEED);
        return ((speedLevel) == 0) ? 0.5f : speedLevel * 0.05f;
    }
}
