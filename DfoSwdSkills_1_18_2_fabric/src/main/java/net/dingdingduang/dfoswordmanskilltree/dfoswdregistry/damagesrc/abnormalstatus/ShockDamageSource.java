package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.abnormalstatus;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.IgnoreDamageCalcEventDamageSource;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class ShockDamageSource extends IgnoreDamageCalcEventDamageSource {
    public boolean isBypassArmor() {
        return true;
    }

    public boolean isBypassMagic() {
        return true;
    }

    public ShockDamageSource(String DamageTypeStrID, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity) {
        super(DamageTypeStrID, pDirectEntity, pCausingEntity);
        this.setUnblockable();
        this.bypassesArmor();
    }
}
