package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class TrueDamageSource extends DamageSource {
    private int ElementDmgType = DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_NO_ELEMENT;

    public TrueDamageSource(Holder<DamageType> pType, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity) {
        super(pType, pDirectEntity, pCausingEntity);
    }

    public int getElementDmgType() { return this.ElementDmgType; }
    public void setElementDmgType(int dmgType) { this.ElementDmgType = dmgType; }
}
