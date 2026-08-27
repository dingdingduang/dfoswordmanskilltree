package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.providers;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageSrc;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DfoGeneralDamageProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, DfoGeneralDamageProvider::DamageProviderBootstrapContextRegister);

    public DfoGeneralDamageProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, REGISTRY_SET_BUILDER, Set.of(DfoSwordmanSkillTreeConstants.MOD_ID));
    }

    public static void DamageProviderBootstrapContextRegister(BootstrapContext<DamageType> ctx) {
        ctx.register(DfoGeneralDamageTypes.SHOCK_DAMAGE_TYPE, new DamageType(DfoGeneralDamageSrc.SHOCK_DAMAGE_TYPE_STR_ID, 0.0F));
        ctx.register(DfoGeneralDamageTypes.BLEEDING_DAMAGE_TYPE, new DamageType(DfoGeneralDamageSrc.BLEEDING_DAMAGE_TYPE_STR_ID, 0.0F));
    }
}
