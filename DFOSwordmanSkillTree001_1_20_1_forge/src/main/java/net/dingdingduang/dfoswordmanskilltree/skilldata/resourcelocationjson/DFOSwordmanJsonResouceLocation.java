package net.dingdingduang.dfoswordmanskilltree.skilldata.resourcelocationjson;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.resources.ResourceLocation;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class DFOSwordmanJsonResouceLocation {
    public static final ResourceLocation SKILLDATA_ASURA = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "skilldata/asuraskill.json");
    public static final ResourceLocation SKILLDATA_BERSERKER = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "skilldata/berserkerskill.json");
    public static final ResourceLocation SKILLDATA_BLADEMASTER = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "skilldata/blademasterskill.json");
    public static final ResourceLocation SKILLDATA_SOULBENDER = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "skilldata/soulbenderskill.json");
}
