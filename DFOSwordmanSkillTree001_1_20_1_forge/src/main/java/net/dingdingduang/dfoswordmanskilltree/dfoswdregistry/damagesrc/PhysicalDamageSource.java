package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class PhysicalDamageSource extends DamageSource {
    private int ElementDmgType = DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_NO_ELEMENT;
//    private boolean triggerExtraDamageEventWhenHurtEntity = true;

    public PhysicalDamageSource(Holder<DamageType> pType, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity) {
        super(pType, pDirectEntity, pCausingEntity);
    }

    public PhysicalDamageSource(Holder<DamageType> pType, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity, int Element) {
        super(pType, pDirectEntity, pCausingEntity);
        this.ElementDmgType = Element;
    }

//    public PhysicalDamageSource(Holder<DamageType> pType, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity, int Element, boolean shouldTriggerExtraDamageEventWhenHurtEntity) {
//        super(pType, pDirectEntity, pCausingEntity);
//        this.ElementDmgType = Element;
//        this.triggerExtraDamageEventWhenHurtEntity = shouldTriggerExtraDamageEventWhenHurtEntity;
//    }

//    public PhysicalDamageSource(DamageSource src, ) {
//        super(src.type());
//    }

    public void setElementDamageType(int dmgType) { this.ElementDmgType = dmgType; }
    public int getElementDmgType() { return this.ElementDmgType; }

//    public boolean isTriggerExtraDamageEventWhenHurtEntity() { return this.triggerExtraDamageEventWhenHurtEntity; }
//    public void setTriggerExtraDamageEventWhenHurtEntity(boolean triggerExtraDamageEventWhenHurtEntity) { this.triggerExtraDamageEventWhenHurtEntity = triggerExtraDamageEventWhenHurtEntity; }
}
