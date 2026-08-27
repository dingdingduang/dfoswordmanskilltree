package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.providers;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.DfoGeneralDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class DfoGeneralDamageTagsProvider extends DamageTypeTagsProvider {
    public DfoGeneralDamageTagsProvider(PackOutput pPackOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(pPackOutput, provider, DfoSwordmanSkillTreeConstants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
//        this.tag(DamageTypeTags.BYPASSES_ARMOR).addOptional(DfoGeneralDamageTypes.SHOCK_DAMAGE_TYPE.location()).addOptional(DfoGeneralDamageTypes.BLEEDING_DAMAGE_TYPE.location());
//        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).addOptional(DfoGeneralDamageTypes.SHOCK_DAMAGE_TYPE.location()).addOptional(DfoGeneralDamageTypes.BLEEDING_DAMAGE_TYPE.location());
        this.tag(DamageTypeTags.BYPASSES_ARMOR).addOptional(DfoGeneralDamageTypes.SHOCK_DAMAGE_TYPE.location());
        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).addOptional(DfoGeneralDamageTypes.SHOCK_DAMAGE_TYPE.location());
        this.tag(DamageTypeTags.BYPASSES_SHIELD).addOptional(DfoGeneralDamageTypes.SHOCK_DAMAGE_TYPE.location()).addOptional(DfoGeneralDamageTypes.BLEEDING_DAMAGE_TYPE.location());
        this.tag(DamageTypeTags.BYPASSES_COOLDOWN).addOptional(DfoGeneralDamageTypes.SHOCK_DAMAGE_TYPE.location()).addOptional(DfoGeneralDamageTypes.BLEEDING_DAMAGE_TYPE.location());
        this.tag(DamageTypeTags.BYPASSES_EFFECTS).addOptional(DfoGeneralDamageTypes.SHOCK_DAMAGE_TYPE.location()); // EFFECTS like resistance
    }
}
