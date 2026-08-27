package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class DfoSwdDamageSourceRegistry {
//    public static final RegistryKey<DamageType> CUSTOM_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, getMCResourceLocation("tutorial", "custom_damage_type"));
    public static final RegistryKey<DamageType> SHOCK_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, DfoGeneralDamageSrc.SHOCK_DAMAGE_TYPE_STR_ID));
    public static final RegistryKey<DamageType> BLEEDING_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, DfoGeneralDamageSrc.BLEEDING_DAMAGE_TYPE_STR_ID));

//    public static DamageSource getDamageSourceByRegistryKey(World world, RegistryKey<DamageType> key) {
//        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
//    }

    public static RegistryEntry.Reference<DamageType> getDamageTypeReferenceKey(World world, RegistryKey<DamageType> key) {
        return world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key);
    }
}
