package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class IgnoreDamageCalcEventDamageSource extends DamageSource {
    private int ElementDmgType = DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_NO_ELEMENT;

    private Entity DirectEntity = null;
    private Entity CausingEntity = null;

    @Override
    public Entity getDirectEntity() {
        return this.DirectEntity;
    }

    @Override
    public Entity getEntity() {
        return this.CausingEntity;
    }

    public IgnoreDamageCalcEventDamageSource(String DamageTypeStrID, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity) {
        super(DamageTypeStrID);
        this.DirectEntity = pDirectEntity;
        this.CausingEntity = pCausingEntity;
    }

    public int getElementDmgType() { return this.ElementDmgType; }
    public void setElementDmgType(int dmgType) { this.ElementDmgType = dmgType; }
}
