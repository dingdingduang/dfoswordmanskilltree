package net.dingdingduang.dfoswordmanskilltree.sbsskilltreemixin;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillTreeInitialization;
import net.dingdingduang.somebasicskills.skilldata.SkillDataSkillTreeInitialization;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkillDataSkillTreeInitialization.class)
public class DfoSkillActionMixin {
    @Inject(method = "Lnet/dingdingduang/somebasicskills/skilldata/SkillDataSkillTreeInitialization;SBSkillActionMixinHelper()V",
            at = {@At(value = "TAIL")},
            remap = true)
    public void DfoSkillActionInit(CallbackInfo ci) {
        DfoSwordmanSkillTreeInitialization.DfoSwordmanSkillInit();
    }
}
