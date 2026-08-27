package net.dingdingduang.dfoswordmanskilltree.sbsskilltreemixin;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.somebasicskills.skilldata.SkillDataJsonReader;
import net.dingdingduang.dfoswordmanskilltree.skilldata.resourcelocationjson.DFOSwordmanJsonResouceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkillDataJsonReader.class)
public abstract class DfoSkillDataMixin {
    @Inject(method = "Lnet/dingdingduang/somebasicskills/skilldata/SkillDataJsonReader;SBSkillJsonMixinHelper()V",
            at = {@At(value = "TAIL")},
            remap = false)
    public void DfoSkillDataJsonInit(CallbackInfo ci) {
//        SkillDataJsonReader.setupSkillDataList(SkillDataJsonReader.readSkillDataJson(DFOSwordmanJsonResouceLocation.SKILLDATA_ASURA, false), DfoSwordmanSkillTreeConstants.MOD_ID);

//        SkillDataJsonReader.setupSkillDataList(SkillDataJsonReader.readSkillDataJson(DFOSwordmanJsonResouceLocation.SKILLDATA_BERSERKER, false), DfoSwordmanSkillTreeConstants.MOD_ID);

        SkillDataJsonReader.setupSkillDataList(SkillDataJsonReader.readSkillDataJson(DFOSwordmanJsonResouceLocation.SKILLDATA_BLADEMASTER, false), DfoSwordmanSkillTreeConstants.MOD_ID);

//        SkillDataJsonReader.setupSkillDataList(SkillDataJsonReader.readSkillDataJson(DFOSwordmanJsonResouceLocation.SKILLDATA_SOULBENDER, false), DfoSwordmanSkillTreeConstants.MOD_ID);
    }
}
