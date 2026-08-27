package net.dingdingduang.dfoswordmanskilltree.globalmethods;

import com.google.common.collect.Multimap;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;
import java.util.function.Consumer;

public class DfoSkillTreeItemMethods {
    public static int IS_HOLDING_NOTHING = -1;
    public static int IS_VERY_SLOW_ZANBATO = 0;
    public static int IS_SLOW_BLUDGEON = 1;
    public static int IS_AVERAGE_SHORT_SWORD = 2;
    public static int IS_FAST_KATANA = 3;
    public static int IS_VERY_FAST_LIGHTSABRE = 4;

    //[-3, -2.5), [-2.5, -2),
    //[-2, -1.5), [-1.5, -1),
    //[-1, -0.5), [-0.5, 0),
    //[0, 0.5), [0.5, 1),
    //[1, 1.5), [1.5, 2]

    //Zanabato: (INF, 1.05)
    //Bludgeon: [1.05, 1.55)
    //short Sword: [1.55, 2.55)
    //katana: [2.55, 3.55)
    //lightsabre: [3.55, INF)
    public static int[] WeaponTypeArr = {
            IS_VERY_SLOW_ZANBATO,
            IS_SLOW_BLUDGEON,
            IS_AVERAGE_SHORT_SWORD,
            IS_AVERAGE_SHORT_SWORD,
            IS_FAST_KATANA,
            IS_FAST_KATANA,
            IS_VERY_FAST_LIGHTSABRE,
            IS_VERY_FAST_LIGHTSABRE,
            IS_VERY_FAST_LIGHTSABRE,
            IS_VERY_FAST_LIGHTSABRE
    };

    public static ItemStack getWeaponItemStack(LivingEntity entity) {
        ItemStack itemStack = entity.getMainHandStack();
        AttributeModifiersComponent attributeModifiersComponent = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);

        List<AttributeModifiersComponent.Entry> itemEntryList = attributeModifiersComponent.modifiers();
        for (AttributeModifiersComponent.Entry tempItemEntry: itemEntryList) {
            if (tempItemEntry.attribute().equals(EntityAttributes.ATTACK_DAMAGE) && tempItemEntry.slot().equals(AttributeModifierSlot.MAINHAND) ) {
                return itemStack;
            }
        }
        return null;
    }

    public static void ItemStackHurtAndBreakMainHandItem(LivingEntity entity, int amount) {
        ItemStack tempWeaponItem = DfoSkillTreeItemMethods.getWeaponItemStack(entity);
        if (tempWeaponItem != null) {
            tempWeaponItem.damage(amount, entity, EquipmentSlot.MAINHAND);
            //hurt and break
        }
    }

    public static int getWeaponType(LivingEntity entity) {
        ItemStack itemStack = entity.getMainHandStack();

        AttributeModifiersComponent attributeModifiersComponent = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
        List<AttributeModifiersComponent.Entry> itemEntryList = attributeModifiersComponent.modifiers();

        RegistryEntry<EntityAttribute> weaponAttr;
        boolean hasWeapon = false;
//        boolean isDefaultAdded = false;
        float tempAspdSum = 0;
        for (AttributeModifiersComponent.Entry tempItemEntry: itemEntryList) {
            weaponAttr = tempItemEntry.attribute();
            if (!hasWeapon && weaponAttr.equals(EntityAttributes.ATTACK_DAMAGE)) {
                hasWeapon = true;
            }
            else if (weaponAttr.equals(EntityAttributes.ATTACK_SPEED) && tempItemEntry.slot().equals(AttributeModifierSlot.MAINHAND) ) {
//                if (!isDefaultAdded) {
//                    isDefaultAdded = true;
//                    tempAspdSum += (float) tempItemEntry.attribute().value().getDefaultValue();
//                }
                tempAspdSum += (float) tempItemEntry.modifier().value();
            }
        }

        if (hasWeapon) {
//            printInGameMsg("item aspd: "+tempAspdSum);
            int index = (int) (ExtraMathMethods.clamp(tempAspdSum * 2f + 0.9f, -6f, 2f) + 6f);
            return WeaponTypeArr[index];
        }

        return IS_HOLDING_NOTHING;

        //TEST
//        return net.dingdingduang.dfoswordmanskilltree.test.testPressKey.tempinti1;
    }

    public static int getOffhandWeaponType(LivingEntity entity) {
        ItemStack itemStack = entity.getOffHandStack();

        AttributeModifiersComponent attributeModifiersComponent = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
        List<AttributeModifiersComponent.Entry> itemEntryList = attributeModifiersComponent.modifiers();

        RegistryEntry<EntityAttribute> weaponAttr;
        boolean hasWeapon = false;
//        boolean isDefaultAdded = false;
        float tempAspdSum = 0;
        for (AttributeModifiersComponent.Entry tempItemEntry: itemEntryList) {
            weaponAttr = tempItemEntry.attribute();
            if (!hasWeapon && weaponAttr.equals(EntityAttributes.ATTACK_DAMAGE)) {
                hasWeapon = true;
            }
            else if (weaponAttr.equals(EntityAttributes.ATTACK_SPEED) && tempItemEntry.slot().equals(AttributeModifierSlot.MAINHAND) ) {
//                if (!isDefaultAdded) {
//                    isDefaultAdded = true;
//                    tempAspdSum += (float) tempItemEntry.attribute().value().getDefaultValue();
//                }
                tempAspdSum += (float) tempItemEntry.modifier().value();
            }
        }

        if (hasWeapon) {
            int index = (int) (ExtraMathMethods.clamp(tempAspdSum * 2f + 0.9f, -6f, 2f) + 6f);
            return WeaponTypeArr[index];
        }

        return IS_HOLDING_NOTHING;
    }

    public static ItemStack getOffhandItemStack(LivingEntity entity) { return entity.getOffHandStack(); }

    public static boolean isOffhandEmpty(LivingEntity entity) { return getOffhandItemStack(entity).isEmpty(); }

    public static String getMainHandNBTID(LivingEntity entity) {
        ItemStack tempItemStack = entity.getMainHandStack();

//        if (tempItemStack.getTag() != null && tempItemStack.getTag().contains("id")) {
//            printInGameMsg("contains ID? :"+tempItemStack.getTag().contains("id") );
//            printInGameMsg("tag: "+tempItemStack.getTag().toString());
//            return tempItemStack.getTag().getString("id");
//        }

        if (!tempItemStack.isEmpty()) {
            return tempItemStack.getItem().toString();
        }
        return "diamond_sword";
    }

    public static String getOffHandNBTID(LivingEntity entity) {
        ItemStack tempItemStack = entity.getOffHandStack();

//        if (tempItemStack.getTag() != null && tempItemStack.getTag().contains("id")) {
//            printInGameMsg("contains ID? :"+tempItemStack.getTag().contains("id") );
//            printInGameMsg("tag: "+tempItemStack.getTag().toString());
//            return tempItemStack.getTag().getString("id");
//        }

        if (!tempItemStack.isEmpty()) {
            return tempItemStack.getItem().toString();
        }
        return "diamond_sword";
    }

    public static boolean isEntityHoldingVerySlowApsdWeaponType(LivingEntity entity) { return  getWeaponType(entity) == IS_VERY_SLOW_ZANBATO; }
    public static boolean isEntityHoldingSlowApsdWeaponType(LivingEntity entity) { return  getWeaponType(entity) == IS_SLOW_BLUDGEON; }
    public static boolean isEntityHoldingAverageApsdWeaponType(LivingEntity entity) { return  getWeaponType(entity) == IS_AVERAGE_SHORT_SWORD; }
    public static boolean isEntityHoldingFastApsdWeaponType(LivingEntity entity) { return  getWeaponType(entity) == IS_FAST_KATANA; }
    public static boolean isEntityHoldingVeryFastApsdWeaponType(LivingEntity entity) { return  getWeaponType(entity) == IS_VERY_FAST_LIGHTSABRE; }

    public static boolean isEntityMainHandHavingNoWeaponType(LivingEntity entity) { return  getWeaponType(entity) == IS_HOLDING_NOTHING; }
    public static boolean isEntityOffhandHavingNoWeaponType(LivingEntity entity) { return  getOffhandWeaponType(entity) == IS_HOLDING_NOTHING; }

    public static boolean isWeaponTypeIntVerySlowAspd(int weaponType) { return weaponType == IS_VERY_SLOW_ZANBATO; }
    public static boolean isWeaponTypeIntSlowAspd(int weaponType) { return weaponType == IS_SLOW_BLUDGEON; }
    public static boolean isWeaponTypeIntAverageAspd(int weaponType) { return weaponType == IS_AVERAGE_SHORT_SWORD; }
    public static boolean isWeaponTypeIntFastAspd(int weaponType) { return weaponType == IS_FAST_KATANA; }
    public static boolean isWeaponTypeIntVeryFastAspd(int weaponType) { return weaponType == IS_VERY_FAST_LIGHTSABRE; }
    public static boolean isWeaponTypeIntNothing(int weaponType) { return weaponType == IS_HOLDING_NOTHING; }
}
