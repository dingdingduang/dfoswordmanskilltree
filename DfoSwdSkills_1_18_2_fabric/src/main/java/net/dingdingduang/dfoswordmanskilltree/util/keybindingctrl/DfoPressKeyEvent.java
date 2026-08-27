package net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer;
import net.dingdingduang.somebasicskills.globalmethods.ClientSkillMethods;
import net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay;
import net.dingdingduang.somebasicskills.networking.NetworkingSendMsgMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftInstance;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class DfoPressKeyEvent {
    public static final KeyBinding SWAP_OFFHAND = getMinecraftInstance().options.swapHandsKey;

    public static void dfoSwdInputEvent() {
        MinecraftClient minecraft = getMinecraftInstance();
        if (minecraft.player == null || minecraft.currentScreen != null) return;

        if (SWAP_OFFHAND.isPressed()) {
            executeQuickSwapFAction(BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_WEAPON_SWAP);
        }
    }

    private static void executeQuickSwapFAction(String SkillID) {
        //if not in cd
//        if (ClientItemOverlayTimer.getClientPlayerCurrentHoldingItemIsWeapon() && getID2SkillData().containsKey(SkillID) && ClientSkillMethods.queryCPlayerCurrentSkillLevel(SkillID) > 0) {
        if (ClientItemOverlayTimer.getClientPlayerOffhandHoldingItemIsWeapon() && getID2SkillData().containsKey(SkillID) && ClientSkillMethods.queryCPlayerCurrentSkillLevel(SkillID) > 0) {
            SkillDataJson skill1 = getID2SkillData().get(SkillID);
            boolean passedConditionRequirement = true;
            if (skill1.getClientConditionRequirement() != null) {
                passedConditionRequirement = skill1.getClientConditionRequirement().executeAction(SkillID);
            }
            if (passedConditionRequirement) {
                if ((skill1.isPassiveType() || skill1.isBothType()) && skill1.getPassiveSkillAction1() != null) {
                    NetworkingSendMsgMethods.SendSkillPassiveActionFromClientSideToServer(SkillID);
                    SkillsInCooldownClientTimerOverlay ClientCDTimer = SkillsInCooldownClientTimerOverlay.getSkillsInCooldownClientTimerOverlayInstance();
                    ClientCDTimer.setCooldownTimer(true, SkillID);
                }
            }
        }
    }
}
