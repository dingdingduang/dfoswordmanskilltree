package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.newcommon;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.newcommon.active.NEWCOMMON003;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.newcommon.active.NEWCOMMON004;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.newcommon.active.NEWCOMMON007;

public class NewCommonSkillInitialization {
//    public static final String COMMON_003_LEAP = "COMMON_0_3";
//    public static final String COMMON_004_ANCIENT_MEMORY = "COMMON_0_4";
//    public static final String COMMON_007_INDOMITABLE_SPIRIT = "COMMON_0_7";

//    public static final String COMMON_101_GENERAL_ARMOR_MASTERY = "COMMON_1_1";
//    public static final String COMMON_102_CLOTH_ARMOR_MASTERY = "COMMON_1_2";
//    public static final String COMMON_103_HEAVY_ARMOR_MASTERY = "COMMON_1_3";
//    public static final String COMMON_104_LEATHER_ARMOR_MASTERY = "COMMON_1_4";
//    public static final String COMMON_105_LIGHT_ARMOR_MASTERY = "COMMON_1_5";
//    public static final String COMMON_106_PLATE_ARMOR_MASTERY = "COMMON_1_6";

    public static void NewCommonSkillInit() {
        //Active
        NEWCOMMON003.init();
        NEWCOMMON004.init();
        NEWCOMMON007.init();


        //Passive
//        COMMON101.init();
//        COMMON102.init();
//        COMMON103.init();
//        COMMON104.init();
//        COMMON105.init();
//        COMMON106.init();
    }
}
