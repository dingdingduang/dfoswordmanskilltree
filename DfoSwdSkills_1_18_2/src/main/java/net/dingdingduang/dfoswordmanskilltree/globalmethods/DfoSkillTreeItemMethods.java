package net.dingdingduang.dfoswordmanskilltree.globalmethods;

import com.google.common.collect.Multimap;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

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
        ItemStack itemStack = entity.getMainHandItem();
        Multimap<Attribute, AttributeModifier> tempMap = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND);

        if (!tempMap.get(Attributes.ATTACK_DAMAGE).isEmpty() || (itemStack.getTag() != null && itemStack.getTag().contains("Damage", 99) ) ) {
            if (!tempMap.get(Attributes.ATTACK_SPEED).isEmpty()) {
                return itemStack;
            }
        }
        return null;
    }

    public static void ItemStackHurtAndBreakMainHandItem(LivingEntity entity, int amount) {
        ItemStack tempWeaponItem = DfoSkillTreeItemMethods.getWeaponItemStack(entity);
        if (tempWeaponItem != null) {
            tempWeaponItem.hurtAndBreak(amount, entity, getLivingEntityHurtAndBreakConsumer());
        }
    }

    private static Consumer<LivingEntity> getLivingEntityHurtAndBreakConsumer() {
        return DfoSkillTreeItemMethods::BroadCastMainHandItemBreakEvent;
    }

    private static void BroadCastMainHandItemBreakEvent(LivingEntity entity) {
        entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    }

    public static int getWeaponType(LivingEntity entity) {
        ItemStack itemStack = entity.getMainHandItem();
        Multimap<Attribute, AttributeModifier> tempMap = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND);
        //check if current item has attack and is not previous item
//        printInGameMsg("atkdmg empty?: "+tempMap.get(Attributes.ATTACK_DAMAGE).isEmpty());
//        printInGameMsg("tag empty?: "+ (itemStack.getTag() != null && itemStack.getTag().contains("Damage", 99) ) );
        if (!tempMap.get(Attributes.ATTACK_DAMAGE).isEmpty() || (itemStack.getTag() != null && itemStack.getTag().contains("Damage", 99) ) ) {
            if (!tempMap.get(Attributes.ATTACK_SPEED).isEmpty()) {
                float sum = 0f;
                for (AttributeModifier tempMod: tempMap.get(Attributes.ATTACK_SPEED)) {
                    sum += (float) tempMod.getAmount();
                }
//                int index = (int) (ExtraMathMethods.clamp(sum, -3f, 1f)) + 3;
//                printInGameMsg("sum2: "+(sum * 2f + 0.9f));
                int index = (int) (ExtraMathMethods.clamp(sum * 2f + 0.9f, -6f, 2f) + 6f);
                return WeaponTypeArr[index];
            }
        }
        return IS_HOLDING_NOTHING;

        //TEST
//        return net.dingdingduang.dfoswordmanskilltree.test.testPressKey.tempinti1;
    }

    public static int getOffhandWeaponType(LivingEntity entity) {
        ItemStack itemStack = entity.getOffhandItem();
        Multimap<Attribute, AttributeModifier> tempMap = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND);
        //check if current item has attack and is not previous item
//        printInGameMsg("atkdmg empty?: "+tempMap.get(Attributes.ATTACK_DAMAGE).isEmpty());
//        printInGameMsg("tag empty?: "+ (itemStack.getTag() != null && itemStack.getTag().contains("Damage", 99) ) );
        if (!tempMap.get(Attributes.ATTACK_DAMAGE).isEmpty() || (itemStack.getTag() != null && itemStack.getTag().contains("Damage", 99) ) ) {
            if (!tempMap.get(Attributes.ATTACK_SPEED).isEmpty()) {
                float sum = 0f;
                for (AttributeModifier tempMod: tempMap.get(Attributes.ATTACK_SPEED)) {
                    sum += (float) tempMod.getAmount();
                }
//                int index = (int) (ExtraMathMethods.clamp(sum, -3f, 1f)) + 3;
//                printInGameMsg("sum2: "+(sum * 2f + 0.9f));
                int index = (int) (ExtraMathMethods.clamp(sum * 2f + 0.9f, -6f, 2f) + 6f);
                return WeaponTypeArr[index];
            }
        }
        return IS_HOLDING_NOTHING;
    }

    public static ItemStack getOffhandItemStack(LivingEntity entity) { return entity.getOffhandItem(); }

    public static boolean isOffhandEmpty(LivingEntity entity) { return getOffhandItemStack(entity).isEmpty(); }

    public static String getMainHandNBTID(LivingEntity entity) {
        ItemStack tempItemStack = entity.getMainHandItem();

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
        ItemStack tempItemStack = entity.getOffhandItem();

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
