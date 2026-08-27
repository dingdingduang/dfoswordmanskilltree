package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

public class MagicDamageSource extends DamageSource {
    private int ElementDmgType = DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_NO_ELEMENT;

    public MagicDamageSource(RegistryEntry<DamageType> pType, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity) {
        super(pType, pDirectEntity, pCausingEntity);
    }

    public MagicDamageSource(RegistryEntry<DamageType> pType, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity, int Element) {
        super(pType, pDirectEntity, pCausingEntity);
        this.ElementDmgType = Element;
    }

    public void setElementDamageType(int dmgType) { this.ElementDmgType = dmgType; }
    public int getElementDmgType() { return this.ElementDmgType; }
}
