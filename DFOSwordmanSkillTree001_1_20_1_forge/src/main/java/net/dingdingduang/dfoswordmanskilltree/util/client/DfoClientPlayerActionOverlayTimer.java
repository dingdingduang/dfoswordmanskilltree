package net.dingdingduang.dfoswordmanskilltree.util.client;

import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods;
import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.block.entity.TiltBlockEntity;
import net.dingdingduang.somebasicskills.globalvalues.GlobalClientValuesInit;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.KeyboardMethods;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.dingdingduang.somebasicskills.globalmethods.ClientPlayerMethods;
import net.dingdingduang.somebasicskills.sbsattributes.statusquery.AttributeClientPlayerStatusQueryMethods;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;

@OnlyIn(Dist.CLIENT)
public class DfoClientPlayerActionOverlayTimer implements IGuiOverlay {
    private final static DfoClientPlayerActionOverlayTimer DFO_CLIENT_PLAYER_ACTION_OVERLAY_TIMER = new DfoClientPlayerActionOverlayTimer();

    private int previousForgeGuiTick = -1;

    private ArrayList<MethodEntityAction> ClientPlayerDeltaMovementOneTimeAction = new ArrayList<>();

    private HashMap<Integer, TiltBlockEntity> ClientBlockPosHash2TiltBlockEntity = new HashMap<>();
    private HashSet<Integer> removeTiltBlockEntitySet = new HashSet<>();


    @Override
    public void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Player localPlayer;
        if (GlobalClientValuesInit.isGlobalClientValuesInitialized() && (localPlayer = getClientPlayer()) != null) {
            int currentForgeGuiTick = forgeGui.getGuiTicks();
            if (currentForgeGuiTick - this.previousForgeGuiTick != 0) {
                if (KeyboardMethods.isRefreshKeyboardKidnapState() && getMinecraftInstance().screen == null) {
                    KeyboardMethods.setRefreshKeyboardKidnapState(false);
                    KeyboardMethods.RefreshKidnapKeyboardSetting();
//                    printInGameMsg("refreshed!");
                }

                //0 delta action
                if (AttributeClientPlayerStatusQueryMethods.getCPlayerTemporaryAbnormalStatusImmobilizationBase() > 0 && ClientPlayerMethods.getClientPlayerStateAmount(DfoSwordmanSkillTreeConstants.IS_STATIONARY) > 0) {
                    Vector3f vector3f = EntityMethods.getEntityDeltaMovement(localPlayer);
                    EntityMethods.setEntityDeltaMovement(localPlayer, 0, MathMethods.min(0, vector3f.y()), 0);
                }

                //one time delta action
                if (!ClientPlayerDeltaMovementOneTimeAction.isEmpty()) {
                    for (MethodEntityAction oneTimeAction : ClientPlayerDeltaMovementOneTimeAction) {
                        oneTimeAction.executeAction(localPlayer);
                    }
                    ClientPlayerDeltaMovementOneTimeAction.clear();
                }

                for (HashMap.Entry<Integer, TiltBlockEntity> tempMapEntry: ClientBlockPosHash2TiltBlockEntity.entrySet()) {
                    if (TiltBlockMethods.TiltBlockEntityAction(tempMapEntry.getValue())) {
                        removeTiltBlockEntitySet.add(tempMapEntry.getKey());
                    }
                }
                if (!removeTiltBlockEntitySet.isEmpty()) {
                    for (Integer tempTBRTiltBlockEntity: removeTiltBlockEntitySet) {
                        ClientBlockPosHash2TiltBlockEntity.remove(tempTBRTiltBlockEntity);
                    }
                    removeTiltBlockEntitySet.clear();
                }

                //tilt block
//                boolean isComplete = false;
//                for (TiltBlockEntityMethodAction tiltBlockEntityMethodAction: ClientTiltBlockEntityMethodAction) {
//                    isComplete = tiltBlockEntityMethodAction.executeAction();
////                    printInGameMsg("looping");
//                    if (isComplete) {
//                        CompleteTiltBlockEntityMethodAction.add(tiltBlockEntityMethodAction);
//                    }
//                }
//
//                if (isComplete) {
//                    printInGameMsg("complete");
//                    for (TiltBlockEntityMethodAction tbrAction: CompleteTiltBlockEntityMethodAction) {
//                        ClientTiltBlockEntityMethodAction.remove(tbrAction);
//                    }
//                    CompleteTiltBlockEntityMethodAction.clear();
//                }
            }
            if (!KeyboardMethods.isRefreshKeyboardKidnapState() && getMinecraftInstance().screen != null) {
                KeyboardMethods.setRefreshKeyboardKidnapState(true);
                KeyboardMethods.RefreshFreeKeyboardSetting();
            }

            this.previousForgeGuiTick = currentForgeGuiTick;
        }
    }

//    public static void DfoClientActionOverlayTimerInit() {
//        DfoClientActionOverlayTimer tempClientOverlay = getDfoClientPlayerActionOverlayTimer();
//        tempClientOverlay.setActive(true);
//    }

    public static DfoClientPlayerActionOverlayTimer getDfoClientPlayerActionOverlayTimer() {
        return DFO_CLIENT_PLAYER_ACTION_OVERLAY_TIMER;
    }

    public ArrayList<MethodEntityAction> getClientPlayerDeltaMovementOneTimeAction() { return ClientPlayerDeltaMovementOneTimeAction; }
    public void setClientPlayerDeltaMovementOneTimeAction(ArrayList<MethodEntityAction> clientPlayerDeltaMovementOneTimeAction) { this.ClientPlayerDeltaMovementOneTimeAction = clientPlayerDeltaMovementOneTimeAction; }
    public static void addClientPlayerDelatMovementOneTimeAction(MethodEntityAction action) { getDfoClientPlayerActionOverlayTimer().ClientPlayerDeltaMovementOneTimeAction.add(action); }

    public static void addClientTiltBlockEntityAction(int blockPosID, TiltBlockEntity tempTiltBlockEntity) { getDfoClientPlayerActionOverlayTimer().ClientBlockPosHash2TiltBlockEntity.put(blockPosID, tempTiltBlockEntity); }

//    public boolean isActive() { return this.ActiveCounter > 0; }
//    public void setActive(boolean active) {
//        if (active) {
//            this.ActiveCounter++;
//        }
//        else {
//            this.ActiveCounter--;
//        }
//    }
}
