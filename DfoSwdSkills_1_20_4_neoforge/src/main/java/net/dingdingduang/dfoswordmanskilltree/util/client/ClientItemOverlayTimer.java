package net.dingdingduang.dfoswordmanskilltree.util.client;

import com.google.common.collect.Multimap;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.somebasicskills.globalmethods.ClientSkillMethods;
import net.dingdingduang.somebasicskills.gui.overlay.SkillsInCooldownClientTimerOverlay;
import net.dingdingduang.somebasicskills.networking.NetworkingSendMsgMethods;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJson;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.somebasicskills.skilldata.SkillDataInitialization.getID2SkillData;

@OnlyIn(Dist.CLIENT)
public class ClientItemOverlayTimer implements IGuiOverlay {
    private final static ClientItemOverlayTimer CLIENT_OVERLAY_TIMER_OVERLAY = new ClientItemOverlayTimer();

    private int previousForgeGuiTick = -1;
    private int currentForgeGuiTick = -1;

    private boolean isActive = false;
    private Inventory ClientPlayerInventory;

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

    private void executeLightsabreTrainingAction(String SkillID, Multimap<Attribute, AttributeModifier> tempMap) {
//        printInGameMsg("has skillID: "+ifClientPlayerHasSkill(SkillID));
//        printInGameMsg("map empty?: "+tempMap.get(Attributes.ATTACK_SPEED).isEmpty());

        if (ifClientPlayerHasSkill(SkillID) && !tempMap.get(Attributes.ATTACK_SPEED).isEmpty()) {
            float sum = 0f;
            for (AttributeModifier tempMod : tempMap.get(Attributes.ATTACK_SPEED)) {
                sum += (float) tempMod.getAmount();
            }
//                int index = (int) (ExtraMathMethods.clamp(sum, -3f, 1f)) + 3;
//                printInGameMsg("sum2: "+(sum * 2f + 0.9f));
            int index = (int) (ExtraMathMethods.clamp(sum * 2f + 0.9f, -6f, 2f) + 6f);
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

    @Override
    public void render(ExtendedGui forgeGui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (this.isActive && getClientPlayer() != null) {
            this.currentForgeGuiTick = forgeGui.getGuiTicks();
            if (this.currentForgeGuiTick - this.previousForgeGuiTick != 0) {
//                if (this.IsLightsabreTrainingActive && !ifClientPlayerHasSkill(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING)) {
//                    printInGameMsg("here d2");
//                    NetworkingSendMsgMethods.SendSkillPassiveActionFromClientSideToServer(BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING);
//                }

                this.ClientPlayerInventory = getClientPlayer().getInventory();
                int currentSelectedHotBarItemIndex = this.ClientPlayerInventory.selected;
                ItemStack itemStack = ClientPlayerInventory.getItem(CurrentHotBarHoldingItemIndex);
//                ItemStack previousHekdItemStack;
                if (this.CurrentHotBarHoldingItemIndex != this.PreviousHotBarHeldItemIndex) {
                    Multimap<Attribute, AttributeModifier> tempMap = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND);
                    //check if current item has attack and is not previous item
                    if (!tempMap.get(Attributes.ATTACK_DAMAGE).isEmpty() || (itemStack.getTag() != null && itemStack.getTag().contains("Damage", 99)) ) {
                        //init
                        String SkillID;
                        this.isCurrentHotBarHeldItemWeapon = true;
                        //Lightsabre Training
                        SkillID = BlademasterSkillTreeInitialization.BLADEMASTER_LIGHTSABRE_TRAINING;
                        executeLightsabreTrainingAction(SkillID, tempMap);



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
        Inventory getPlayerInventory = getClientItemOverlayTimerOverlay().getClientPlayerInventory();
        ItemStack HoldingItemStack = getPlayerInventory.getItem(currentHotBarHoldingItemIndex);
        if (HoldingItemStack.isEmpty()) { return false; }
        Multimap<Attribute, AttributeModifier> tempMap = HoldingItemStack.getAttributeModifiers(EquipmentSlot.MAINHAND);

        return !tempMap.get(Attributes.ATTACK_DAMAGE).isEmpty() || (HoldingItemStack.getTag() != null && HoldingItemStack.getTag().contains("Damage", 99));
    }

    public static boolean getClientPlayerOffhandHoldingItemIsWeapon() {
        int currentHotBarHoldingItemIndex = Inventory.SLOT_OFFHAND;
        Inventory getPlayerInventory = getClientItemOverlayTimerOverlay().getClientPlayerInventory();
        ItemStack HoldingItemStack = getPlayerInventory.getItem(currentHotBarHoldingItemIndex);
        if (HoldingItemStack.isEmpty()) { return false; }
        Multimap<Attribute, AttributeModifier> tempMap = HoldingItemStack.getAttributeModifiers(EquipmentSlot.MAINHAND);

        return !tempMap.get(Attributes.ATTACK_DAMAGE).isEmpty() || (HoldingItemStack.getTag() != null && HoldingItemStack.getTag().contains("Damage", 99));
    }

    public Inventory getClientPlayerInventory() { return this.ClientPlayerInventory; }
    public void setClientPlayerInventory(Inventory clientPlayerInventory) { this.ClientPlayerInventory = clientPlayerInventory; }

    public boolean isActive() { return this.isActive; }
    public void setActive(boolean active) { this.isActive = active; }
}
