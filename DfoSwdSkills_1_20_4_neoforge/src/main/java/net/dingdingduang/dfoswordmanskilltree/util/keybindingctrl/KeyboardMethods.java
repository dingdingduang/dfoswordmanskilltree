package net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl;

import com.mojang.blaze3d.platform.InputConstants;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.ExtraGeneralConfigRegistry;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target.TargetZoneSelectionEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.dingdingduang.somebasicskills.keyboard.KeyMappingInit;

import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.Entity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.somebasicskills.Constants;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.joml.Vector3f;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getMinecraftInstance;

//these all assume minecraft classes are all initialized first
public class KeyboardMethods {
    private static final String Splitor = ":";
    private static HashMap<KeyMapping, KeyHelper> keymapping2keyhelper = new HashMap<>();

    private static Options MCDefaultOptions = getMinecraftInstance().options;
    private static Map<KeyMapping, Integer> MovementKeyMappingImmutableMap = Map.of(
            MCDefaultOptions.keyUp, 0,
            MCDefaultOptions.keyLeft, 1,
            MCDefaultOptions.keyDown, 2,
            MCDefaultOptions.keyRight, 3,
            MCDefaultOptions.keyJump, 4,
            MCDefaultOptions.keyShift, 5
            );
    private static final MethodEntityAction[] ClientMovementEntityAction = {
            KeyboardMethods::movingUp,
            KeyboardMethods::movingLeft,
            KeyboardMethods::movingDown,
            KeyboardMethods::movingRight,
            KeyboardMethods::increasingHeight,
            KeyboardMethods::decreasingHeight
    };

    private static KeyMapping[] MCOptionsArr = new KeyMapping[]{
            MCDefaultOptions.keyHotbarSlots[0], MCDefaultOptions.keyHotbarSlots[1], MCDefaultOptions.keyHotbarSlots[2],
            MCDefaultOptions.keyHotbarSlots[3], MCDefaultOptions.keyHotbarSlots[4], MCDefaultOptions.keyHotbarSlots[5], MCDefaultOptions.keyHotbarSlots[6],
            MCDefaultOptions.keyHotbarSlots[7], MCDefaultOptions.keyHotbarSlots[8], MCDefaultOptions.keySwapOffhand,
            MCDefaultOptions.keyDrop, MCDefaultOptions.keyUse, MCDefaultOptions.keyAttack,
            MCDefaultOptions.keyUp, MCDefaultOptions.keyLeft, MCDefaultOptions.keyDown, MCDefaultOptions.keyRight,
            MCDefaultOptions.keyJump, MCDefaultOptions.keyShift, MCDefaultOptions.keySprint
    };
    private static int HotBarKeyKidnapCounter = 0;
    private static int MovementKeyKidnapCounter = 0;

    private static boolean RefreshKeyboardKidnapState = false;

    private static final int UnknownKeyCode = -1;
    private static InputConstants.Key UnknownKey = InputConstants.Type.KEYSYM.getOrCreate(UnknownKeyCode);

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
        for (Map.Entry<KeyMapping, Integer> movementKeyMapping2direction: MovementKeyMappingImmutableMap.entrySet()) {
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

    private static void kidnapKeybindings(KeyMapping targetKeybinding) {
        int tempKeyCode = targetKeybinding.getKey().getValue();
        if (tempKeyCode != UnknownKeyCode) {
            targetKeybinding.setDown(false);
            keymapping2keyhelper.get(targetKeybinding).setKeyVal(tempKeyCode);
            targetKeybinding.setKey(UnknownKey);
        }
    }

    public static void KeyboardMethodsInit() {
        InputConstants.Key tempKey;
        for (int i = 0; i < MCOptionsArr.length; i++) {
            tempKey = MCOptionsArr[i].getKey();
            keymapping2keyhelper.put(MCOptionsArr[i], new KeyHelper(tempKey.getName(), tempKey.getType(), tempKey.getValue()));
        }
    }

    public static void KidnapClientPlayerHotBarKeybindings() {
        HotBarKeyKidnapCounter++;

        if (HotBarKeyKidnapCounter >= 1) {
            for (int i = 0; i < 9; i++) {
                kidnapKeybindings(MCDefaultOptions.keyHotbarSlots[i]);
            }
            kidnapKeybindings(MCDefaultOptions.keySwapOffhand);
            kidnapKeybindings(MCDefaultOptions.keyDrop);
            kidnapKeybindings(MCDefaultOptions.keyUse);
            kidnapKeybindings(MCDefaultOptions.keyAttack);
        }
    }

    public static void KidnapClientPlayerMovementKeybindings() {
        MovementKeyKidnapCounter++;
        if (MovementKeyKidnapCounter >= 1) {
            kidnapKeybindings(MCDefaultOptions.keyUp);
            kidnapKeybindings(MCDefaultOptions.keyLeft);
            kidnapKeybindings(MCDefaultOptions.keyDown);
            kidnapKeybindings(MCDefaultOptions.keyRight);
            kidnapKeybindings(MCDefaultOptions.keyJump);
            kidnapKeybindings(MCDefaultOptions.keyShift);
            kidnapKeybindings(MCDefaultOptions.keySprint);
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
    private static void freeKeybindings(KeyMapping targetKeybinding) {
        KeyHelper tempKeyHelper = keymapping2keyhelper.get(targetKeybinding);
        int keyCode = tempKeyHelper.getKeyVal();
        int keyAction = KeyMappingInit.getKeyAction(tempKeyHelper.getKeyVal());
        targetKeybinding.setDown(isKeyCodeDown(keyCode, keyAction) || isKeyCodeHolding(keyCode, keyAction));
//        targetKeybinding.setDown(!isKeyCodeReleased(keyCode, keyAction));

        targetKeybinding.setKey(tempKeyHelper.getKeyType().getOrCreate(tempKeyHelper.getKeyVal()));
    }

    public static void FreeClientPlayerHotBarKeybindings() {
        if (HotBarKeyKidnapCounter <= 1) {
            for (int i = 0; i < 9; i++) {
                freeKeybindings(MCDefaultOptions.keyHotbarSlots[i]);
            }
            freeKeybindings(MCDefaultOptions.keySwapOffhand);
            freeKeybindings(MCDefaultOptions.keyDrop);
            freeKeybindings(MCDefaultOptions.keyUse);
            freeKeybindings(MCDefaultOptions.keyAttack);
        }

        HotBarKeyKidnapCounter--;
    }

    public static void FreeClientPlayerMovementKeybindings() {
        if (MovementKeyKidnapCounter <= 1) {
            freeKeybindings(MCDefaultOptions.keyUp);
            freeKeybindings(MCDefaultOptions.keyLeft);
            freeKeybindings(MCDefaultOptions.keyDown);
            freeKeybindings(MCDefaultOptions.keyRight);
            freeKeybindings(MCDefaultOptions.keyJump);
            freeKeybindings(MCDefaultOptions.keyShift);
            freeKeybindings(MCDefaultOptions.keySprint);
        }

        MovementKeyKidnapCounter--;
//        printInGameMsg("movF: "+MovementKeyKidnapCounter);
    }

    public static void RefreshKidnapKeyboardSetting() {
        if (HotBarKeyKidnapCounter > 0) {
            for (int i = 0; i < 9; i++) {
                kidnapKeybindings(MCDefaultOptions.keyHotbarSlots[i]);
            }
            kidnapKeybindings(MCDefaultOptions.keySwapOffhand);
            kidnapKeybindings(MCDefaultOptions.keyDrop);
            kidnapKeybindings(MCDefaultOptions.keyUse);
            kidnapKeybindings(MCDefaultOptions.keyAttack);
        }

        if (MovementKeyKidnapCounter > 0) {
            kidnapKeybindings(MCDefaultOptions.keyUp);
            kidnapKeybindings(MCDefaultOptions.keyLeft);
            kidnapKeybindings(MCDefaultOptions.keyDown);
            kidnapKeybindings(MCDefaultOptions.keyRight);
            kidnapKeybindings(MCDefaultOptions.keyJump);
            kidnapKeybindings(MCDefaultOptions.keyShift);
            kidnapKeybindings(MCDefaultOptions.keySprint);
        }
    }

    public static void RefreshFreeKeyboardSetting() {
        if (HotBarKeyKidnapCounter > 0) {
            for (int i = 0; i < 9; i++) {
                freeKeybindings(MCDefaultOptions.keyHotbarSlots[i]);
            }
            freeKeybindings(MCDefaultOptions.keySwapOffhand);
            freeKeybindings(MCDefaultOptions.keyDrop);
            freeKeybindings(MCDefaultOptions.keyUse);
            freeKeybindings(MCDefaultOptions.keyAttack);
        }

        if (MovementKeyKidnapCounter > 0) {
            freeKeybindings(MCDefaultOptions.keyUp);
            freeKeybindings(MCDefaultOptions.keyLeft);
            freeKeybindings(MCDefaultOptions.keyDown);
            freeKeybindings(MCDefaultOptions.keyRight);
            freeKeybindings(MCDefaultOptions.keyJump);
            freeKeybindings(MCDefaultOptions.keyShift);
            freeKeybindings(MCDefaultOptions.keySprint);
        }
    }

    public static void ResetClientPlayerKeyboardKeyBinds() {
        for (KeyMapping tempKeyMapping: keymapping2keyhelper.keySet()) {
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

    //=====================
    //client keyboard event
    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, value = Dist.CLIENT)
    public static class KeyboardMethodsEvents {
        @SubscribeEvent
        public static void KeyboardMethodsMouseScrollingEvent(InputEvent.MouseScrollingEvent event) {
            if (HotBarKeyKidnapCounter > 0) {
                if (getMinecraftInstance().screen == null) {
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void KeyboardMethodsMouseBtnPressedEvent(ScreenEvent.MouseButtonPressed.Pre event) {
            if (HotBarKeyKidnapCounter > 0 && event.getScreen() instanceof AbstractContainerScreen<?> tempScreen && tempScreen.getSlotUnderMouse() != null) {
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void DfoSwdStopForestallPotentialChangingItemHotBarBehavior(ScreenEvent.KeyPressed.Pre event) {
            //0-9, [!] might change due to version changing: 81 keydrop, 70 key swap item,

            if ( (event.getKeyCode() > 48 && event.getKeyCode() <= 57) ||
                    (event.getKeyCode() == 81) ||
                    (event.getKeyCode() == 70) ||
                    (event.getKeyCode() == 0)
            ) {
                if (HotBarKeyKidnapCounter > 0 && event.getScreen() instanceof AbstractContainerScreen<?> tempScreen && tempScreen.getSlotUnderMouse() != null) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
