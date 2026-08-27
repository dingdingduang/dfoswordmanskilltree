package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;

import java.util.Set;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class DfoGeneralDamageSrc {
//    public static final int PHYSICAL_DAMAGE_MOB_ATTACK_STR_HASH_ID = DamageTypes.MOB_ATTACK.location().getPath().hashCode();
//    public static final int PHYSICAL_DAMAGE_PLAYER_ATTACK_STR_HASH_ID = DamageTypes.PLAYER_ATTACK.location().getPath().hashCode();
//    public static final int MAGICAL_DAMAGE_PLAYER_ATTACK_STR_HASH_ID = DamageTypes.MAGIC.location().getPath().hashCode();
//    public static final int PHYSICAL_DAMAGE_MOB_ATTACK_STR_HASH_ID = "mob_attack".hashCode();
//    public static final int PHYSICAL_DAMAGE_PLAYER_ATTACK_STR_HASH_ID = "player_attack".hashCode();
//    public static final int MAGICAL_DAMAGE_PLAYER_ATTACK_STR_HASH_ID = "magic".hashCode();
    public static final Set<String> PHYSICAL_DAMAGE_STR_SET = Set.of("dfophysicaldmg", "mob", "player", "trident", "explosion", "explosion.player", "arrow");
    public static final Set<String> MAGICAL_DAMAGE_STR_SET = Set.of("dfomagicaldmg","magic", "sonic_boom", "lightningBolt");

    public static final String DFO_PHYSICAL_DAMAGE_SOURCE_ID = "dfophysicaldmg";
    public static final String DFO_MAGICAL_DAMAGE_SOURCE_ID = "dfomagicaldmg";

    public static final String SHOCK_DAMAGE_TYPE_STR_ID = "dfo_light_shock";
    public static final String BLEEDING_DAMAGE_TYPE_STR_ID = "dfo_neutral_abnormal_bleeding";

    public static DamageSource lightDmgShockStatus(World entityWorldLevel, Entity pCausingEntity, Entity pDirectEntity) {
        IgnoreDamageCalcEventDamageSource ignoreDamageCalcEventDamageSource = new IgnoreDamageCalcEventDamageSource(SHOCK_DAMAGE_TYPE_STR_ID, pCausingEntity, pDirectEntity);
        ignoreDamageCalcEventDamageSource.setElementDmgType(DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_LIGHT);
        return ignoreDamageCalcEventDamageSource;
    }

    public static void LivingEntityDamageTargetWithShock(World entityWorldLevel, LivingEntity dealer, LivingEntity target, float amount) {
        EntityMethods.addKnockbackResist(target);
        int iframeTime = GetEntityInvulnerableTime(target);
        EntityMethods.SetEntityInvulnerableTime(target, 0);

        EntityMethods.DealerDamageTarget(lightDmgShockStatus(entityWorldLevel, dealer, dealer), target, amount);
        EntityMethods.removeKnockbackResist(target);

        EntityMethods.SetEntityInvulnerableTime(target, iframeTime);
        target.damage(lightDmgShockStatus(EntityMethods.getEntityLevel(dealer), dealer, dealer), amount);
    }

    public static DamageSource bleedingDmgShockStatus(World entityWorldLevel, Entity pCausingEntity, Entity pDirectEntity) {
        IgnoreDamageCalcEventDamageSource ignoreDamageCalcEventDamageSource = new IgnoreDamageCalcEventDamageSource(BLEEDING_DAMAGE_TYPE_STR_ID, pCausingEntity, pDirectEntity);
        ignoreDamageCalcEventDamageSource.setElementDmgType(DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_NO_ELEMENT);
        return ignoreDamageCalcEventDamageSource;
    }

    public static void LivingEntityDamageTargetWithBleeding(World entityWorldLevel, LivingEntity dealer, LivingEntity target, float amount) {
        int iframeTime = GetEntityInvulnerableTime(target);
        EntityMethods.SetEntityInvulnerableTime(target, 0);
        EntityMethods.addKnockbackResist(target);
        EntityMethods.DealerDamageTarget(bleedingDmgShockStatus(entityWorldLevel, dealer, dealer), target, amount);
        EntityMethods.removeKnockbackResist(target);
        EntityMethods.SetEntityInvulnerableTime(target, iframeTime);
    }

    public static DamageSource getPhysicalDamageSource(LivingEntity dealer, Entity indirectEntity, int DfoDamageType) {
        return new PhysicalDamageSource(DFO_PHYSICAL_DAMAGE_SOURCE_ID, dealer, indirectEntity, DfoDamageType);
    }

    public static DamageSource getMagicalDamageSource(LivingEntity dealer, Entity indirectEntity, int DfoDamageType) {
        return new MagicDamageSource(DFO_MAGICAL_DAMAGE_SOURCE_ID, dealer, indirectEntity, DfoDamageType);
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
