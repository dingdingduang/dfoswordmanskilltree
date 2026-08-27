package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import org.jetbrains.annotations.Nullable;

public class MagicDamageSource extends DamageSource {
    private int ElementDmgType = DfoSwordmanSkillTreeConstants.ELEMENT_DAMAGE_TYPE_NO_ELEMENT;

    private Entity DirectEntity = null;
    private Entity CausingEntity = null;

//    @Override
//    public Entity getDirectEntity() {
//        return this.DirectEntity;
//    }
//
//    @Override
//    public Entity getEntity() {
//        return this.CausingEntity;
//    }

    @Nullable
    public Entity getSource() {
        return this.DirectEntity;
    }

    @Nullable
    public Entity getAttacker() {
        return this.CausingEntity;
    }

    public MagicDamageSource(String DamageTypeStrID, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity) {
        super(DamageTypeStrID);
        this.DirectEntity = pDirectEntity;
        this.CausingEntity = pCausingEntity;
    }

    public MagicDamageSource(String DamageTypeStrID, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity, int Element) {
        super(DamageTypeStrID);
        this.DirectEntity = pDirectEntity;
        this.CausingEntity = pCausingEntity;
        this.ElementDmgType = Element;
    }

    public void setElementDamageType(int dmgType) { this.ElementDmgType = dmgType; }
    public int getElementDmgType() { return this.ElementDmgType; }
}
