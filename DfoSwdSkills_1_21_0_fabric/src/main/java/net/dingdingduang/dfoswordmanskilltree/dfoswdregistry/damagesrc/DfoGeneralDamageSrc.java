package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc;

import com.google.common.collect.Sets;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.DfoSwdDamageSourceRegistry;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class DfoGeneralDamageSrc {
//    public static final int PHYSICAL_DAMAGE_MOB_ATTACK_STR_HASH_ID = DamageTypes.MOB_ATTACK.location().getPath().hashCode();
//    public static final int PHYSICAL_DAMAGE_PLAYER_ATTACK_STR_HASH_ID = DamageTypes.PLAYER_ATTACK.location().getPath().hashCode();
//    public static final int MAGICAL_DAMAGE_PLAYER_ATTACK_STR_HASH_ID = DamageTypes.MAGIC.location().getPath().hashCode();
//    public static final int PHYSICAL_DAMAGE_MOB_ATTACK_STR_HASH_ID = "mob_attack".hashCode();
//    public static final int PHYSICAL_DAMAGE_PLAYER_ATTACK_STR_HASH_ID = "player_attack".hashCode();
//    public static final int MAGICAL_DAMAGE_PLAYER_ATTACK_STR_HASH_ID = "magic".hashCode();
    public static final Set<String> PHYSICAL_DAMAGE_STR_SET = Set.of("mob", "player", "trident", "explosion", "explosion.player", "arrow");
    public static final Set<String> MAGICAL_DAMAGE_STR_SET = Set.of("magic", "sonic_boom", "lightningBolt");

//    public static final HashSet<String> a = Maps.of();

    public static final String SHOCK_DAMAGE_TYPE_STR_ID = "dfo_light_shock";
    public static final String BLEEDING_DAMAGE_TYPE_STR_ID = "dfo_neutral_abnormal_bleeding";
//    public static RegistryEntry<DamageType> SHOCK_DAMAGE_TYPE_HOLDER = null;
//    public static RegistryEntry<DamageType> BLEEDING_DAMAGE_TYPE_HOLDER = null;
//
//    public static RegistryEntry<DamageType> PHYSICAL_DAMAGE_TYPE_HOLDER = null;
//    public static RegistryEntry<DamageType> MAGIC_DAMAGE_TYPE_HOLDER = null;
//
//    private static final HashSet<TagKey<DamageType>> IgnoreAllBindTagSet = newIgnoreAllTagKeyHashSet();
//    private static final HashSet<TagKey<DamageType>> IgnoreCooldownBindTagSet = newIgnoreCooldownTagKeyHashSet();

//    private static HashSet<TagKey<DamageType>> newIgnoreAllTagKeyHashSet() {
//        HashSet<TagKey<DamageType>> bindTags = Sets.newHashSetWithExpectedSize(5);
//        bindTags.add(DamageTypeTags.BYPASSES_ARMOR);
//        bindTags.add(DamageTypeTags.BYPASSES_ENCHANTMENTS);
//        bindTags.add(DamageTypeTags.BYPASSES_SHIELD);
//        bindTags.add(DamageTypeTags.BYPASSES_COOLDOWN);
//        bindTags.add(DamageTypeTags.BYPASSES_EFFECTS);
//        return bindTags;
//    }
//
//    private static HashSet<TagKey<DamageType>> newIgnoreCooldownTagKeyHashSet() {
//        HashSet<TagKey<DamageType>> bindTags = Sets.newHashSetWithExpectedSize(2);
//        bindTags.add(DamageTypeTags.BYPASSES_SHIELD);
//        bindTags.add(DamageTypeTags.BYPASSES_COOLDOWN);
//        return bindTags;
//    }

//    private static RegistryEntry<DamageType> setupShockDamageTypeHolder(RegistryEntry<DamageType> damageTypeHolder) {
//        try {
//            ((RegistryEntry.Reference<DamageType>) damageTypeHolder).setTags(IgnoreAllBindTagSet);
//        }
//        catch (Exception ignored) {}
//
//        return damageTypeHolder;
//    }
//
//    private static RegistryEntry<DamageType> setupBleedingDamageTypeHolder(RegistryEntry<DamageType> damageTypeHolder) {
//        try {
//            ((RegistryEntry.Reference<DamageType>) damageTypeHolder).setTags(IgnoreCooldownBindTagSet);
//        }
//        catch (Exception ignored) {}
//
//        return damageTypeHolder;
//    }
//
//    public static void initializeClientDamageType1201(LivingEntity clientPlayer) {
//        if (SHOCK_DAMAGE_TYPE_HOLDER == null) {
//            SHOCK_DAMAGE_TYPE_HOLDER = setupShockDamageTypeHolder(getEntityLevel(clientPlayer).getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DfoSwdDamageSourceRegistry.SHOCK_DAMAGE_TYPE));
//        }
//        if (BLEEDING_DAMAGE_TYPE_HOLDER == null) {
//            BLEEDING_DAMAGE_TYPE_HOLDER = setupBleedingDamageTypeHolder(getEntityLevel(clientPlayer).getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DfoSwdDamageSourceRegistry.BLEEDING_DAMAGE_TYPE));
//        }
//        if (PHYSICAL_DAMAGE_TYPE_HOLDER == null) { PHYSICAL_DAMAGE_TYPE_HOLDER = clientPlayer.getDamageSources().mobAttack(clientPlayer).getTypeRegistryEntry(); }
//        if (MAGIC_DAMAGE_TYPE_HOLDER == null) { MAGIC_DAMAGE_TYPE_HOLDER = clientPlayer.getDamageSources().indirectMagic(clientPlayer, clientPlayer).getTypeRegistryEntry(); }
//    }

    public static DamageSource lightDmgShockStatus(World entityWorldLevel, Entity pCausingEntity, Entity pDirectEntity) {
        IgnoreDamageCalcEventDamageSource ignoreDamageCalcEventDamageSource = new IgnoreDamageCalcEventDamageSource(DfoSwdDamageSourceRegistry.getDamageTypeReferenceKey(entityWorldLevel, DfoSwdDamageSourceRegistry.SHOCK_DAMAGE_TYPE), pCausingEntity, pDirectEntity);
        ignoreDamageCalcEventDamageSource.setElementDmgType(DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_LIGHT);
        return ignoreDamageCalcEventDamageSource;
    }

    public static void LivingEntityDamageTargetWithShock(World entityWorldLevel, LivingEntity dealer, LivingEntity target, float amount) {
        EntityMethods.addKnockbackResist(target);
//        int iframeTime = GetEntityInvulnerableTime(target);
//        EntityMethods.SetEntityInvulnerableTime(target, 0);

        EntityMethods.DealerDamageTarget(lightDmgShockStatus(entityWorldLevel, dealer, dealer), target, amount);
        EntityMethods.removeKnockbackResist(target);

//        EntityMethods.SetEntityInvulnerableTime(target, iframeTime);
//        target.hurt(lightDmgShockStatus(dealer, dealer, dealer), amount);
    }

    public static DamageSource bleedingDmgShockStatus(World entityWorldLevel, Entity pCausingEntity, Entity pDirectEntity) {
        IgnoreDamageCalcEventDamageSource ignoreDamageCalcEventDamageSource = new IgnoreDamageCalcEventDamageSource(DfoSwdDamageSourceRegistry.getDamageTypeReferenceKey(entityWorldLevel, DfoSwdDamageSourceRegistry.BLEEDING_DAMAGE_TYPE), pCausingEntity, pDirectEntity);
        ignoreDamageCalcEventDamageSource.setElementDmgType(DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_NO_ELEMENT);
        return ignoreDamageCalcEventDamageSource;
    }

    public static void LivingEntityDamageTargetWithBleeding(World entityWorldLevel, LivingEntity dealer, LivingEntity target, float amount) {
//        int iframeTime = GetEntityInvulnerableTime(target);
//        EntityMethods.SetEntityInvulnerableTime(target, 0);
        EntityMethods.addKnockbackResist(target);
        EntityMethods.DealerDamageTarget(bleedingDmgShockStatus(entityWorldLevel, dealer, dealer), target, amount);
        EntityMethods.removeKnockbackResist(target);
//        EntityMethods.SetEntityInvulnerableTime(target, iframeTime);
    }

    public static DamageSource getPhysicalDamageSource(LivingEntity dealer, Entity indirectEntity, int DfoDamageType) {
        return new PhysicalDamageSource(dealer.getDamageSources().mobAttack(dealer).getTypeRegistryEntry(), dealer, indirectEntity, DfoDamageType);
    }

    public static DamageSource getMagicalDamageSource(LivingEntity dealer, Entity indirectEntity, int DfoDamageType) {
        return new MagicDamageSource(dealer.getDamageSources().indirectMagic(indirectEntity, dealer).getTypeRegistryEntry(), dealer, indirectEntity, DfoDamageType);
    }

    //TODO: CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(serverplayer, itemstack, itemstack.getDamageValue() + amount);, getDamageValue -> nbttag "Damage"
    //or call itemstack hurt method
    public static void LivingEntityDamageTargetWithPhysicDMG(LivingEntity dealer, LivingEntity target, float amount, boolean ignoreIframe) {
        EntityMethods.addKnockbackResist(target);
        int iframeTime = GetEntityInvulnerableTime(target);
//        if (iframeTime > 0 && !ignoreIframe) { return; }
        EntityMethods.SetEntityInvulnerableTime(target, 0);

        //TODO: query current element or others
        target.damage(getPhysicalDamageSource(dealer, dealer, DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_NO_ELEMENT), amount);

        removeKnockbackResist(target);
        EntityMethods.SetEntityInvulnerableTime(target, iframeTime);
    }

    public static void LivingEntityDamageTargetWithPhysicDMG(LivingEntity dealer, Entity indirectEntity, LivingEntity target, float amount, boolean ignoreIframe) {
        EntityMethods.addKnockbackResist(target);
        int iframeTime = GetEntityInvulnerableTime(target);
//        if (iframeTime > 0 && !ignoreIframe) { return; }
        EntityMethods.SetEntityInvulnerableTime(target, 0);

        //TODO: query current element or others
        target.damage(getPhysicalDamageSource(dealer, indirectEntity, DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_NO_ELEMENT), amount);

        removeKnockbackResist(target);
        EntityMethods.SetEntityInvulnerableTime(target, iframeTime);
    }

    public static void LivingEntityDamageTargetWithPhysicDMG(LivingEntity dealer, LivingEntity target, float amount, int damageType, boolean ignoreIframe) {
        EntityMethods.addKnockbackResist(target);
        int iframeTime = GetEntityInvulnerableTime(target);
//        if (iframeTime > 0 && !ignoreIframe) { return; }
        EntityMethods.SetEntityInvulnerableTime(target, 0);

        target.damage(getPhysicalDamageSource(dealer, dealer, damageType), amount);

        removeKnockbackResist(target);
        EntityMethods.SetEntityInvulnerableTime(target, iframeTime);
    }

    public static void LivingEntityDamageTargetWithMagicDMG(LivingEntity dealer, LivingEntity target, float amount, boolean ignoreIframe) {
        EntityMethods.addKnockbackResist(target);
        int iframeTime = GetEntityInvulnerableTime(target);
//        if (iframeTime > 0 && !ignoreIframe) { return; }
        EntityMethods.SetEntityInvulnerableTime(target, 0);

        target.damage(getMagicalDamageSource(dealer, dealer, DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_NO_ELEMENT), amount);

        removeKnockbackResist(target);
        EntityMethods.SetEntityInvulnerableTime(target, iframeTime);
    }

    public static void LivingEntityDamageTargetWithMagicDMG(LivingEntity dealer, LivingEntity target, float amount, int damageType, boolean ignoreIframe) {
        EntityMethods.addKnockbackResist(target);
        int iframeTime = GetEntityInvulnerableTime(target);
//        if (iframeTime > 0 && !ignoreIframe) { return; }
        EntityMethods.SetEntityInvulnerableTime(target, 0);

        target.damage(getMagicalDamageSource(dealer, dealer, damageType), amount);

        removeKnockbackResist(target);
        EntityMethods.SetEntityInvulnerableTime(target, iframeTime);
    }
}
