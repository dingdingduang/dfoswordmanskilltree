package net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.util.InputUtil;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.ExtraGeneralConfigRegistry;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target.TargetZoneSelectionEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.somebasicskills.keyboard.KeyMappingInit;

import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.somebasicskills.Constants;

import modified.org.joml.Vector3f;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftInstance;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.printInGameMsg;

//these all assume minecraft classes are all initialized first
public class KeyboardMethods {
    private static final String Splitor = ":";
    private static HashMap<KeyBinding, KeyHelper> keymapping2keyhelper = new HashMap<>();
    private static HashSet<KeyBinding> keyMappingsClientPlayerNotAbleToMove = new HashSet<>();
    private static HashSet<KeyBinding> keyMappingsClientPlayerNotAbleToSwitchItems = new HashSet<>();

    private static GameOptions MCDefaultOptions = getMinecraftInstance().options;

    private static Set<KeyBinding> StunStatusKeyMappingMoveImmutableSet = Set.of(
            MCDefaultOptions.forwardKey,
            MCDefaultOptions.leftKey,
            MCDefaultOptions.backKey,
            MCDefaultOptions.rightKey,
            MCDefaultOptions.jumpKey,
            MCDefaultOptions.sneakKey,
            MCDefaultOptions.dropKey,
            MCDefaultOptions.useKey,
            MCDefaultOptions.attackKey,
            MCDefaultOptions.sprintKey
    );

    private static Set<KeyBinding> StunStatusKeyMappingHotbarImmutableSet = Set.of(
            MCDefaultOptions.hotbarKeys[0], MCDefaultOptions.hotbarKeys[1], MCDefaultOptions.hotbarKeys[2],
            MCDefaultOptions.hotbarKeys[3], MCDefaultOptions.hotbarKeys[4], MCDefaultOptions.hotbarKeys[5], MCDefaultOptions.hotbarKeys[6],
            MCDefaultOptions.hotbarKeys[7], MCDefaultOptions.hotbarKeys[8], MCDefaultOptions.swapHandsKey, MCDefaultOptions.dropKey
    );

    private static Map<KeyBinding, Integer> MovementKeyMappingImmutableMap = Map.of(
            MCDefaultOptions.forwardKey, 0,
            MCDefaultOptions.leftKey, 1,
            MCDefaultOptions.backKey, 2,
            MCDefaultOptions.rightKey, 3,
            MCDefaultOptions.jumpKey, 4,
            MCDefaultOptions.sneakKey, 5
            );
    private static final MethodEntityAction[] ClientMovementEntityAction = {
            KeyboardMethods::movingUp,
            KeyboardMethods::movingLeft,
            KeyboardMethods::movingDown,
            KeyboardMethods::movingRight,
            KeyboardMethods::increasingHeight,
            KeyboardMethods::decreasingHeight
    };

    private static KeyBinding[] MCOptionsArr = new KeyBinding[]{
            MCDefaultOptions.hotbarKeys[0], MCDefaultOptions.hotbarKeys[1], MCDefaultOptions.hotbarKeys[2],
            MCDefaultOptions.hotbarKeys[3], MCDefaultOptions.hotbarKeys[4], MCDefaultOptions.hotbarKeys[5], MCDefaultOptions.hotbarKeys[6],
            MCDefaultOptions.hotbarKeys[7], MCDefaultOptions.hotbarKeys[8], MCDefaultOptions.swapHandsKey,
            MCDefaultOptions.dropKey, MCDefaultOptions.useKey, MCDefaultOptions.attackKey,
            MCDefaultOptions.forwardKey, MCDefaultOptions.leftKey, MCDefaultOptions.backKey, MCDefaultOptions.rightKey,
            MCDefaultOptions.jumpKey, MCDefaultOptions.sneakKey, MCDefaultOptions.sprintKey
    };
    private static int HotBarKeyKidnapCounter = 0;
    private static int MovementKeyKidnapCounter = 0;

    private static boolean RefreshKeyboardKidnapState = false;

    private static final int UnknownKeyCode = -1;
    private static InputUtil.Key UnknownKey = InputUtil.Type.KEYSYM.createFromCode(UnknownKeyCode);

    public static HashMap<KeyBinding, KeyHelper> getKeymapping2keyhelper() { return keymapping2keyhelper; }

    private static InputUtil.Key getBoundKey(KeyBinding keyBinding) {
        return KeyBindingHelper.getBoundKeyOf(keyBinding);
    }

    //====================
    //simulate moving
    //====================
//    public static int getClientPlayerPressingMovementKeyDirection() {
////        int foundMovementKeyCode = -1;
////        int foundMovementKeyAction = -1;
//        int foundDirection = -1;
//        int tempKeyCode, tempKeyAction;
//        KeyHelper tempKeyHelper;
//        for (Map.Entry<KeyMapping, Integer> movementKeyMapping2direction: MovementKeyMappingImmutableMap.entrySet()) {
//            tempKeyHelper = keymapping2keyhelper.get(movementKeyMapping2direction.getKey());
//            tempKeyCode = tempKeyHelper.getKeyVal();
//            tempKeyAction = KeyMappingInit.getKeyAction(tempKeyHelper.getKeyVal());
//            if (tempKeyCode != -1 && tempKeyAction > 0) {
////                foundMovementKeyCode = tempKeyCode;
//                foundDirection = movementKeyMapping2direction.getValue();
////                foundMovementKeyAction = tempKeyAction;
//            }
//        }
//
//        return foundDirection;
//    }

    public static int[] getClientPlayerPressingMovementKeyDirectionArr() {
//        int foundMovementKeyCode = -1;
//        int foundMovementKeyAction = -1;
        int foundDirection = -1;
        int[] tempDirectionArr = {-1, -1, -1, -1, -1, -1};
        int tempKeyCode, tempKeyAction;
        KeyHelper tempKeyHelper;
        for (Map.Entry<KeyBinding, Integer> movementKeyMapping2direction: MovementKeyMappingImmutableMap.entrySet()) {
            tempKeyHelper = keymapping2keyhelper.get(movementKeyMapping2direction.getKey());
            tempKeyCode = tempKeyHelper.getKeyVal();
            tempKeyAction = KeyMappingInit.getKeyAction(tempKeyHelper.getKeyVal());
            if (tempKeyCode != -1 && tempKeyAction > 0) {
//                foundMovementKeyCode = tempKeyCode;
                foundDirection = movementKeyMapping2direction.getValue();
                tempDirectionArr[foundDirection] = foundDirection;

//                foundMovementKeyAction = tempKeyAction;
            }
        }

        return tempDirectionArr;
    }

    public static MethodEntityAction[] getClientMovementEntityAction() {
        return ClientMovementEntityAction;
    }

    public static void movingUp(Entity entityToBeMoved) {
        Entity player = DfoGeneralMethods.getClientPlayer();
        if (player != null) {
            TargetZoneSelectionEntity targetZone = (TargetZoneSelectionEntity) entityToBeMoved;
//            Vector3f entityLoc = EntityMethods.getEntityPos(targetZone);
            Vector3f entityLoc = new Vector3f(targetZone.getTargetPosX(), targetZone.getTargetPosY(), targetZone.getTargetPosZ());
            Vector3f tempVec = Vector3fMethods.PolarProjectionFromVecHorizon(entityLoc, ExtraGeneralConfigRegistry.getTargetZoneSelectionSpeedOnClientSide(), EntityMethods.getEntityHorizontalFacingDeg(player));
            targetZone.setTargetLoc(tempVec.x(), tempVec.y(), tempVec.z());
        }
    }

    public static void movingLeft(Entity entityToBeMoved) {
        Entity player = DfoGeneralMethods.getClientPlayer();
        if (player != null) {
            TargetZoneSelectionEntity targetZone = (TargetZoneSelectionEntity) entityToBeMoved;
//            Vector3f entityLoc = EntityMethods.getEntityPos(targetZone);
            Vector3f entityLoc = new Vector3f(targetZone.getTargetPosX(), targetZone.getTargetPosY(), targetZone.getTargetPosZ());
            Vector3f tempVec = Vector3fMethods.PolarProjectionFromVecHorizon(entityLoc, ExtraGeneralConfigRegistry.getTargetZoneSelectionSpeedOnClientSide(), EntityMethods.getEntityHorizontalFacingDeg(player) + 270f);
            targetZone.setTargetLoc(tempVec.x(), tempVec.y(), tempVec.z());
        }
    }

    public static void movingDown(Entity entityToBeMoved) {
        Entity player = DfoGeneralMethods.getClientPlayer();
        if (player != null) {
            TargetZoneSelectionEntity targetZone = (TargetZoneSelectionEntity) entityToBeMoved;
//            Vector3f entityLoc = EntityMethods.getEntityPos(targetZone);
            Vector3f entityLoc = new Vector3f(targetZone.getTargetPosX(), targetZone.getTargetPosY(), targetZone.getTargetPosZ());
            Vector3f tempVec = Vector3fMethods.PolarProjectionFromVecHorizon(entityLoc, ExtraGeneralConfigRegistry.getTargetZoneSelectionSpeedOnClientSide(), EntityMethods.getEntityHorizontalFacingDeg(player) + 180f);
            targetZone.setTargetLoc(tempVec.x(), tempVec.y(), tempVec.z());
        }
    }

    public static void movingRight(Entity entityToBeMoved) {
        Entity player = DfoGeneralMethods.getClientPlayer();
        if (player != null) {
            TargetZoneSelectionEntity targetZone = (TargetZoneSelectionEntity) entityToBeMoved;
//            Vector3f entityLoc = EntityMethods.getEntityPos(targetZone);
            Vector3f entityLoc = new Vector3f(targetZone.getTargetPosX(), targetZone.getTargetPosY(), targetZone.getTargetPosZ());
            Vector3f tempVec = Vector3fMethods.PolarProjectionFromVecHorizon(entityLoc, ExtraGeneralConfigRegistry.getTargetZoneSelectionSpeedOnClientSide(), EntityMethods.getEntityHorizontalFacingDeg(player) + 90f);
            targetZone.setTargetLoc(tempVec.x(), tempVec.y(), tempVec.z());
        }
    }

    public static void increasingHeight(Entity entityToBeMoved) {
        TargetZoneSelectionEntity targetZone = (TargetZoneSelectionEntity) entityToBeMoved;
//        targetZone.setTargetPosY(EntityMethods.getEntityY(entityToBeMoved) + ExtraGeneralConfigRegistry.getTargetZoneSelectionSpeedOnClientSide());
        targetZone.setTargetPosY(targetZone.getTargetPosY() + ExtraGeneralConfigRegistry.getTargetZoneSelectionSpeedOnClientSide());
    }

    public static void decreasingHeight(Entity entityToBeMoved) {
        TargetZoneSelectionEntity targetZone = (TargetZoneSelectionEntity) entityToBeMoved;
//        targetZone.setTargetPosY(EntityMethods.getEntityY(entityToBeMoved) - ExtraGeneralConfigRegistry.getTargetZoneSelectionSpeedOnClientSide());
        targetZone.setTargetPosY(targetZone.getTargetPosY() - ExtraGeneralConfigRegistry.getTargetZoneSelectionSpeedOnClientSide());
    }

//    public static HashMap<KeyMapping, KeyHelper> getKeymapping2keyHelperMap() { return keymapping2keyhelper; }


    //====================
    //keyboard kidnap event
    //====================
    public static int getHotBarKeyKidnapCounter() { return HotBarKeyKidnapCounter; }
    public static void setHotBarKeyKidnapCounter(int a) { HotBarKeyKidnapCounter = a; }
    public static int getMovementKeyKidnapCounter() { return MovementKeyKidnapCounter; }
    public static void setMovementKeyKidnapCounter(int a) { MovementKeyKidnapCounter = a; }

    public static boolean isRefreshKeyboardKidnapState() { return RefreshKeyboardKidnapState; }
    public static void setRefreshKeyboardKidnapState(boolean refreshKeyboardKidnapState) { RefreshKeyboardKidnapState = refreshKeyboardKidnapState; }

    public static void kidnapKeybindings(KeyBinding targetKeybinding) {
        int tempKeyCode = getBoundKey(targetKeybinding).getCode();
        if (tempKeyCode != UnknownKeyCode) {
            targetKeybinding.setPressed(false);
            keymapping2keyhelper.get(targetKeybinding).setKeyVal(tempKeyCode);
            targetKeybinding.setBoundKey(UnknownKey);
//            targetKeybinding.setBoundKey(InputUtil.UNKNOWN);
            if (StunStatusKeyMappingMoveImmutableSet.contains(targetKeybinding)) {
                keyMappingsClientPlayerNotAbleToMove.add(targetKeybinding);
            }
            else if (StunStatusKeyMappingHotbarImmutableSet.contains(targetKeybinding)) {
                keyMappingsClientPlayerNotAbleToSwitchItems.add(targetKeybinding);
            }
//            targetKeybinding.setKeyModifierAndCode();
//            printInGameMsg("1_ori: "+tempKeyCode +", now: "+targetKeybinding.getKey().getValue());
        }
    }

    public static void KeyboardMethodsInit() {
        InputUtil.Key tempKey;
        for (int i = 0; i < MCOptionsArr.length; i++) {
            tempKey = getBoundKey(MCOptionsArr[i]);
            keymapping2keyhelper.put(MCOptionsArr[i], new KeyHelper(tempKey.getTranslationKey(), tempKey.getCategory(), tempKey.getCode()));
        }
    }

    public static void KidnapClientPlayerHotBarKeybindings() {
        HotBarKeyKidnapCounter++;

        if (HotBarKeyKidnapCounter >= 1) {
            for (int i = 0; i < 9; i++) {
                kidnapKeybindings(MCDefaultOptions.hotbarKeys[i]);
            }
            kidnapKeybindings(MCDefaultOptions.swapHandsKey);
            kidnapKeybindings(MCDefaultOptions.dropKey);
            kidnapKeybindings(MCDefaultOptions.useKey);
            kidnapKeybindings(MCDefaultOptions.attackKey);
        }
    }

    public static void KidnapClientPlayerMovementKeybindings() {
        MovementKeyKidnapCounter++;
        if (MovementKeyKidnapCounter >= 1) {
            kidnapKeybindings(MCDefaultOptions.forwardKey);
            kidnapKeybindings(MCDefaultOptions.leftKey);
            kidnapKeybindings(MCDefaultOptions.backKey);
            kidnapKeybindings(MCDefaultOptions.rightKey);
            kidnapKeybindings(MCDefaultOptions.jumpKey);
            kidnapKeybindings(MCDefaultOptions.sneakKey);
            kidnapKeybindings(MCDefaultOptions.sprintKey);
        }

//        printInGameMsg("movK: "+MovementKeyKidnapCounter);
    }

//    public static void KidnapAllClientPlayerKeybindings() {
//        KidnapClientPlayerHotBarKeybindings();
//        KidnapClientPlayerMovementKeybindings();
//    }

    private static boolean isKeyCodeReleased(int keyCode, int keyAction) { return keyAction == 0; }
    private static boolean isKeyCodeDown(int keyCode, int keyAction) { return keyAction == 1; }
    private static boolean isKeyCodeHolding(int keyCode, int keyAction) { return keyAction == 2; }

    //=================================
    private static void freeKeybindings(KeyBinding targetKeybinding) {
        KeyHelper tempKeyHelper = keymapping2keyhelper.get(targetKeybinding);
        int keyCode = tempKeyHelper.getKeyVal();
        int keyAction = KeyMappingInit.getKeyAction(tempKeyHelper.getKeyVal());
        targetKeybinding.setPressed(isKeyCodeDown(keyCode, keyAction) || isKeyCodeHolding(keyCode, keyAction));
//        targetKeybinding.setPressed(!isKeyCodeReleased(keyCode, keyAction));

        if (StunStatusKeyMappingMoveImmutableSet.contains(targetKeybinding)) {
            keyMappingsClientPlayerNotAbleToMove.remove(targetKeybinding);
        }
        else if (StunStatusKeyMappingHotbarImmutableSet.contains(targetKeybinding)) {
            keyMappingsClientPlayerNotAbleToSwitchItems.remove(targetKeybinding);
        }

//        printInGameMsg("2_freebefore: "+keyCode +", freeafter: "+tempKeyHelper.getKeyType().getOrCreate(tempKeyHelper.getKeyVal()).getValue());
        targetKeybinding.setBoundKey(tempKeyHelper.getKeyType().createFromCode(tempKeyHelper.getKeyVal()));
    }

    public static void FreeClientPlayerHotBarKeybindings() {
        if (HotBarKeyKidnapCounter <= 1) {
            for (int i = 0; i < 9; i++) {
                freeKeybindings(MCDefaultOptions.hotbarKeys[i]);
            }
            freeKeybindings(MCDefaultOptions.swapHandsKey);
            freeKeybindings(MCDefaultOptions.dropKey);
            freeKeybindings(MCDefaultOptions.useKey);
            freeKeybindings(MCDefaultOptions.attackKey);
        }

        HotBarKeyKidnapCounter--;
    }

    public static void FreeClientPlayerMovementKeybindings() {
        if (MovementKeyKidnapCounter <= 1) {
            freeKeybindings(MCDefaultOptions.forwardKey);
            freeKeybindings(MCDefaultOptions.leftKey);
            freeKeybindings(MCDefaultOptions.backKey);
            freeKeybindings(MCDefaultOptions.rightKey);
            freeKeybindings(MCDefaultOptions.jumpKey);
            freeKeybindings(MCDefaultOptions.sneakKey);
            freeKeybindings(MCDefaultOptions.sprintKey);
        }

        MovementKeyKidnapCounter--;
//        printInGameMsg("movF: "+MovementKeyKidnapCounter);
    }

    public static void RefreshKidnapKeyboardSetting() {
        if (HotBarKeyKidnapCounter > 0) {
            for (int i = 0; i < 9; i++) {
                kidnapKeybindings(MCDefaultOptions.hotbarKeys[i]);
            }
            kidnapKeybindings(MCDefaultOptions.swapHandsKey);
            kidnapKeybindings(MCDefaultOptions.dropKey);
            kidnapKeybindings(MCDefaultOptions.useKey);
            kidnapKeybindings(MCDefaultOptions.attackKey);
        }

        if (MovementKeyKidnapCounter > 0) {
            kidnapKeybindings(MCDefaultOptions.forwardKey);
            kidnapKeybindings(MCDefaultOptions.leftKey);
            kidnapKeybindings(MCDefaultOptions.backKey);
            kidnapKeybindings(MCDefaultOptions.rightKey);
            kidnapKeybindings(MCDefaultOptions.jumpKey);
            kidnapKeybindings(MCDefaultOptions.sneakKey);
            kidnapKeybindings(MCDefaultOptions.sprintKey);
        }
    }

    public static void RefreshFreeKeyboardSetting() {
        if (HotBarKeyKidnapCounter > 0) {
            for (int i = 0; i < 9; i++) {
                freeKeybindings(MCDefaultOptions.hotbarKeys[i]);
            }
            freeKeybindings(MCDefaultOptions.swapHandsKey);
            freeKeybindings(MCDefaultOptions.dropKey);
            freeKeybindings(MCDefaultOptions.useKey);
            freeKeybindings(MCDefaultOptions.attackKey);
        }

        if (MovementKeyKidnapCounter > 0) {
            freeKeybindings(MCDefaultOptions.forwardKey);
            freeKeybindings(MCDefaultOptions.leftKey);
            freeKeybindings(MCDefaultOptions.backKey);
            freeKeybindings(MCDefaultOptions.rightKey);
            freeKeybindings(MCDefaultOptions.jumpKey);
            freeKeybindings(MCDefaultOptions.sneakKey);
            freeKeybindings(MCDefaultOptions.sprintKey);
        }
    }

    public static void ResetClientPlayerKeyboardKeyBinds() {
        for (KeyBinding tempKeyMapping: keymapping2keyhelper.keySet()) {
            freeKeybindings(tempKeyMapping);
        }
        HotBarKeyKidnapCounter = 0;
        MovementKeyKidnapCounter = 0;
    }

    public static boolean isAllKeysGottenKidnapped() { return HotBarKeyKidnapCounter > 0 && MovementKeyKidnapCounter > 0; }
    public static boolean isHotBarKeysGottenKidnapped() { return HotBarKeyKidnapCounter > 0; }
    public static boolean isMovementKeyGottenKidnapped() { return MovementKeyKidnapCounter > 0; }

    //===========================
    //file io
    public static void ClientKeybindingsStorageFileReadFrom() {
        String PlayerSkillDataFolderDirection = "config/"+ Constants.MOD_ID+"/";
        String tempPath;

        tempPath = PlayerSkillDataFolderDirection + "/" + "cache" + "/keybindings_000.cache";

        String tempLine;
        File tempFile = new File(tempPath);
        if (tempFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(tempPath))) {
                while ((tempLine = reader.readLine()) != null) {
                    String[] keyValuePair = tempLine.split(Splitor, 2);

                    if (keyValuePair.length > 1) {
                        int index = Integer.parseInt(keyValuePair[0]);
                        int keyCode = Integer.parseInt(keyValuePair[1]);
                        keymapping2keyhelper.get(MCOptionsArr[index]).setKeyVal(keyCode);
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ClientKeybindingsStorageFileWriteTo() {
        try {
            String PlayerSkillDataFolderDirection = "config/"+ Constants.MOD_ID+"/";

            String tempPathStr = PlayerSkillDataFolderDirection +"/"+ "cache";

            Files.createDirectories(Paths.get(tempPathStr));

            tempPathStr = tempPathStr +"/keybindings_000.cache";

            FileWriter tempFileWriter = new FileWriter(tempPathStr);
            BufferedWriter tempBufferedWriter = new BufferedWriter(tempFileWriter);

            for (int i = 0; i < MCOptionsArr.length; i++) {
                if (keymapping2keyhelper.get(MCOptionsArr[i]) != null) {
                    tempBufferedWriter.write(i + Splitor + keymapping2keyhelper.get(MCOptionsArr[i]).getKeyVal());
                    tempBufferedWriter.newLine();
                }
            }

            tempBufferedWriter.flush();
            tempBufferedWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashSet<KeyBinding> getKeyMappingsClientPlayerNotAbleToMove() {
        return keyMappingsClientPlayerNotAbleToMove;
    }

    public static HashSet<KeyBinding> getKeyMappingsClientPlayerNotAbleToSwitchItems() {
        return keyMappingsClientPlayerNotAbleToSwitchItems;
    }

//    public static void registerKeyboardMethodsMouseScrollingEvent() {
//        ScreenKeyboardEvents.sc
//        ScreenMouseEvents.beforeMouseScroll()
//    }

    //=====================
    //client keyboard event
//    @OnlyIn(Dist.CLIENT)
//    @Mod.EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, value = Dist.CLIENT)
//    public static class KeyboardMethodsEvents {
//        @SubscribeEvent
//        public static void KeyboardMethodsMouseScrollingEvent(InputEvent.MouseScrollEvent event) {
//            if (HotBarKeyKidnapCounter > 0) {
//                if (getMinecraftInstance().screen == null) {
//                    event.setCanceled(true);
//                }
//            }
//        }
//
//        @SubscribeEvent
//        public static void KeyboardMethodsMouseBtnPressedEvent(ScreenEvent.MouseClickedEvent.Pre event) {
//            if (HotBarKeyKidnapCounter > 0 && event.getScreen() instanceof AbstractContainerScreen<?> tempScreen && tempScreen.getSlotUnderMouse() != null) {
//                event.setCanceled(true);
//            }
//        }
//
//        @SubscribeEvent
//        public static void DfoSwdStopForestallPotentialChangingItemHotBarBehavior(ScreenEvent.KeyboardKeyPressedEvent.Pre event) {
//            //0-9, [!] might change due to version changing: 81 dropKey, 70 key swap item,
//
//            if ( (event.getKeyCode() > 48 && event.getKeyCode() <= 57) ||
//                    (event.getKeyCode() == 81) ||
//                    (event.getKeyCode() == 70) ||
//                    (event.getKeyCode() == 0)
//            ) {
//                if (HotBarKeyKidnapCounter > 0 && event.getScreen() instanceof AbstractContainerScreen<?> tempScreen && tempScreen.getSlotUnderMouse() != null) {
//                    event.setCanceled(true);
//                }
//            }
//        }
//    }
}
