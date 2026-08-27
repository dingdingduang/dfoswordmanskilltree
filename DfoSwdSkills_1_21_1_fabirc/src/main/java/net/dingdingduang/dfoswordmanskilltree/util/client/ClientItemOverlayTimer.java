package net.dingdingduang.dfoswordmanskilltree.util.client;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.somebasicskills.globalmethods.ClientSkillMethods;
import net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay;
import net.dingdingduang.somebasicskills.networking.NetworkingSendMsgMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

public class ClientItemOverlayTimer {
    private final static ClientItemOverlayTimer CLIENT_OVERLAY_TIMER_OVERLAY = new ClientItemOverlayTimer();

    private int previousForgeGuiTick = -1;
    private int currentForgeGuiTick = -1;

    private boolean isActive = false;
    private PlayerInventory ClientPlayerInventory;

    private int CurrentHotBarHoldingItemIndex = 0;
    private int PreviousHotBarHeldItemIndex = 0;
    private boolean isCurrentHotBarHeldItemWeapon = false;
    private boolean isPreviousHotBarHeldItemWeapon = false;

    //Lightsabre Training
    private boolean IsLightsabreTrainingActive = false;

    private static boolean ifClientPlayerHasSkill(String SkillID) {
        return getID2SkillData().containsKey(SkillID) && ClientSkillMethods.queryCPlayerCurrentSkillLevel(SkillID) > 0;
    }

    public static void setLightsabreTrainingActive(boolean a) { CLIENT_OVERLAY_TIMER_OVERLAY.IsLightsabreTrainingActive = a; }

    private void executeLightsabreTrainingAction(String SkillID, AttributeModifiersComponent tempMap) {
//        printInGameMsg("has skillID: "+ifClientPlayerHasSkill(SkillID));
//        printInGameMsg("map empty?: "+tempMap.get(EntityAttributes.GENERIC_ATTACK_SPEED).isEmpty());

        boolean hasWeapon = false;
        float tempAspdSum = 0f;
        RegistryEntry<EntityAttribute> weaponAttr;
        for (AttributeModifiersComponent.Entry tempItemEntry: tempMap.modifiers()) {
            weaponAttr = tempItemEntry.attribute();
            if (!hasWeapon && weaponAttr.equals(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
                hasWeapon = true;
            }
            else if (weaponAttr.equals(EntityAttributes.GENERIC_ATTACK_SPEED) && tempItemEntry.slot().equals(AttributeModifierSlot.MAINHAND) ) {
                tempAspdSum += (float) tempItemEntry.modifier().value();
            }
        }

        if (ifClientPlayerHasSkill(SkillID) && hasWeapon) {
            int index = (int) (ExtraMathMethods.clamp(tempAspdSum * 2f + 0.9f, -6f, 2f) + 6f);
            int weaponType = DfoSkillTreeItemMethods.WeaponTypeArr[index];
//            printInGameMsg("weapontype: "+weaponType);

            if (weaponType == DfoSkillTreeItemMethods.IS_VERY_FAST_LIGHTSABRE) {
                this.IsLightsabreTrainingActive = true;
//                printInGameMsg("active");
                NetworkingSendMsgMethods.SendSkillPassiveActionTwoFromClientSideToServer(SkillID);
                return;
            }
        }
        if (this.IsLightsabreTrainingActive) {
//            printInGameMsg("here d1");
            NetworkingSendMsgMethods.SendSkillPassiveActionFromClientSideToServer(SkillID);
        }
    }

    //=====================================
    private void executeQuickSwapAction(String SkillID) {
        //if not in cd
        if (this.isPreviousHotBarHeldItemWeapon && ifClientPlayerHasSkill(SkillID)) {
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

    public void render(int gameWorldTick) {
        if (this.isActive && getClientPlayer() != null) {
            this.currentForgeGuiTick = gameWorldTick;
            if (this.currentForgeGuiTick - this.previousForgeGuiTick != 0) {
//                if (this.IsLightsabreTrainingActive && !ifClientPlayerHasSkill(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING)) {
//                    printInGameMsg("here d2");
//                    NetworkingSendMsgMethods.SendSkillPassiveActionFromClientSideToServer(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING);
//                }

                this.ClientPlayerInventory = getClientPlayer().getInventory();
                int currentSelectedHotBarItemIndex = this.ClientPlayerInventory.selectedSlot;
                ItemStack itemStack = ClientPlayerInventory.getStack(CurrentHotBarHoldingItemIndex);
//                ItemStack previousHekdItemStack;
                if (this.CurrentHotBarHoldingItemIndex != this.PreviousHotBarHeldItemIndex) {
                    boolean hasAtk = false;
                    AttributeModifiersComponent itemEntry = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
                    List<AttributeModifiersComponent.Entry> itemEntryList = itemEntry.modifiers();
                    for (AttributeModifiersComponent.Entry tempItemEntry: itemEntryList) {
                        if (tempItemEntry.attribute().equals(EntityAttributes.GENERIC_ATTACK_DAMAGE) && tempItemEntry.slot().equals(AttributeModifierSlot.MAINHAND) ) {
                            hasAtk = true;
                            break;
                        }
                    }

                    //check if current item has attack and is not previous item
                    if (hasAtk) {
                        //init
                        String SkillID;
                        this.isCurrentHotBarHeldItemWeapon = true;
                        //Lightsabre Training
                        SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING;
                        executeLightsabreTrainingAction(SkillID, itemEntry);



                        //Quick Weapon Swap
                        SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_QUICK_WEAPON_SWAP;
                        executeQuickSwapAction(SkillID);






                        this.isPreviousHotBarHeldItemWeapon = this.isCurrentHotBarHeldItemWeapon;
                        this.PreviousHotBarHeldItemIndex = this.CurrentHotBarHoldingItemIndex;
                    } else {
                        this.isCurrentHotBarHeldItemWeapon = false;
                    }
                }
                if (currentSelectedHotBarItemIndex != this.CurrentHotBarHoldingItemIndex) {
                    this.isPreviousHotBarHeldItemWeapon = this.isCurrentHotBarHeldItemWeapon;
                    this.PreviousHotBarHeldItemIndex = this.CurrentHotBarHoldingItemIndex;
                    this.CurrentHotBarHoldingItemIndex = currentSelectedHotBarItemIndex;
                }
            }
            this.previousForgeGuiTick = this.currentForgeGuiTick;
        }
    }

    public static void ClientItemOverlayTimerInit() {
        ClientItemOverlayTimer tempClientOverlay = getClientItemOverlayTimerOverlay();
//        tempClientOverlay.setClientPlayerInventory(getClientPlayer().getInventory());
        tempClientOverlay.setActive(true);
    }

    public static ClientItemOverlayTimer getClientItemOverlayTimerOverlay() {
        return CLIENT_OVERLAY_TIMER_OVERLAY;
    }

//    public int getClientPlayerCurrentHotBarIndex() { return CurrentHotBarHoldingItemIndex; }

    public static boolean getClientPlayerCurrentHoldingItemIsWeapon() {
        int currentHotBarHoldingItemIndex = getClientItemOverlayTimerOverlay().CurrentHotBarHoldingItemIndex;
        PlayerInventory getPlayerInventory = getClientItemOverlayTimerOverlay().getClientPlayerInventory();
        ItemStack HoldingItemStack = getPlayerInventory.getStack(currentHotBarHoldingItemIndex);
        if (HoldingItemStack.isEmpty()) { return false; }

        boolean hasWeaponOnMainHand = false;
        AttributeModifiersComponent attributeModifiersComponent = HoldingItemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
        List<AttributeModifiersComponent.Entry> itemEntryList = attributeModifiersComponent.modifiers();
        for (AttributeModifiersComponent.Entry tempItemEntry: itemEntryList) {
            if (tempItemEntry.attribute().equals(EntityAttributes.GENERIC_ATTACK_DAMAGE) && tempItemEntry.slot().equals(AttributeModifierSlot.MAINHAND) ) {
                hasWeaponOnMainHand = true;
                break;
            }
        }

        return hasWeaponOnMainHand;
    }

    public static boolean getClientPlayerOffhandHoldingItemIsWeapon() {
        int currentHotBarHoldingItemIndex = PlayerInventory.OFF_HAND_SLOT;
        PlayerInventory getPlayerInventory = getClientItemOverlayTimerOverlay().getClientPlayerInventory();
        ItemStack HoldingItemStack = getPlayerInventory.getStack(currentHotBarHoldingItemIndex);
        if (HoldingItemStack.isEmpty()) { return false; }

        boolean hasWeaponOnOffHand = false;
        AttributeModifiersComponent attributeModifiersComponent = HoldingItemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
        List<AttributeModifiersComponent.Entry> itemEntryList = attributeModifiersComponent.modifiers();
        for (AttributeModifiersComponent.Entry tempItemEntry: itemEntryList) {
            if (tempItemEntry.attribute().equals(EntityAttributes.GENERIC_ATTACK_DAMAGE) && tempItemEntry.slot().equals(AttributeModifierSlot.MAINHAND) ) {
                hasWeaponOnOffHand = true;
                break;
            }
//            printInGameMsg("attr: "+tempItemEntry.attribute().getRegisteredName()+", val: "+ tempItemEntry.modifier().amount());
        }

        return hasWeaponOnOffHand;
    }

    public PlayerInventory getClientPlayerInventory() { return this.ClientPlayerInventory; }
    public void setClientPlayerInventory(PlayerInventory clientPlayerInventory) { this.ClientPlayerInventory = clientPlayerInventory; }

    public boolean isActive() { return this.isActive; }
    public void setActive(boolean active) { this.isActive = active; }
}
