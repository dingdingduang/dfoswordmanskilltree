package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface DfoGeneralDamageTypes {
    ResourceKey<DamageType> SHOCK_DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, DfoGeneralDamageSrc.SHOCK_DAMAGE_TYPE_STR_ID));
    ResourceKey<DamageType> BLEEDING_DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, DfoGeneralDamageSrc.BLEEDING_DAMAGE_TYPE_STR_ID));
}
