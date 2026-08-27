package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.asura.AsuraSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.BerserkerSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.BlademasterSkillTreeInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.newcommon.NewCommonSkillInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.soulbender.SoulbenderSkillTreeInitialization;

public class DfoSwordmanSkillTreeInitialization {
    public static void DfoSwordmanSkillInit() {
//        AsuraSkillTreeInitialization.AsuraSkillInit();
//
//        BerserkerSkillTreeInitialization.BerserkerSkillInit();
//
        BlademasterSkillTreeInitialization.BlademasterSkillInit();
//
//        SoulbenderSkillTreeInitialization.SoulbenderSkillInit();

        NewCommonSkillInitialization.NewCommonSkillInit();
    }
}
