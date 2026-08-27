package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.active.*;
//import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.ex.*;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.berserker.passive.*;

public class BerserkerSkillTreeInitialization {
    public static int[] BERSERKER_SLASH_COLOR = {220, 20, 60};
//    public static String BERSERKER_HIT_EFF_A = "textures/entity/skilleff/berserker/hit_eff/a/";
//    public static String BERSERKER_HIT_EFF_B = "textures/entity/skilleff/berserker/hit_eff/b/";
//    public static String[] BERSERKER_HIT_EFF_ARR = {BERSERKER_HIT_EFF_A, BERSERKER_HIT_EFF_B};
    public static String[] BERSERKER_HIT_EFF_ARR = {"textures/entity/skilleff/berserker/hit_eff/a/", "textures/entity/skilleff/berserker/hit_eff/b/"};
    public static int BERSERKER_HIT_EFF_FRAMES = 10;

    public static String BERSERKER_CROSS_SLASH = "BERSERKER_0_8";
    public static String BERSERKER_DOWNWARD_STRIKE = "BERSERKER_0_9";

    public static String BERSERKER_POWER_OF_BLOOD = "BERSERKER_0_7";
    public static String BERSERKER_BERSERKERS_BLOOD = "BERSERKER_0_1";

    public static String BERSERKER_BLOOD_FRENZY = "BERSERKER_0_10";
    public static String BERSERKER_BLOODLUST_GRIP = "BERSERKER_0_4";

    public static String BERSERKER_DERANGE = "BERSERKER_0_6";
    public static String BERSERKER_DENIAL_OF_DEATH = "BERSERKER_0_5";

    public static String BERSERKER_BLOOD_FURY = "BERSERKER_0_3";
    public static String BERSERKER_BLOODTHIRSTY = "BERSERKER_0_2";

    public static String BERSERKER_BLOOD_SWORD_THRUST = "BERSERKER_0_17";
    public static String BERSERKER_BLOODY_TWISTER = "BERSERKER_0_11";

    public static String BERSERKER_OUTRAGE_BREAK = "BERSERKER_0_12";

    public static String BERSERKER_EXTREME_OVERKILL = "BERSERKER_0_13";
    public static String BERSERKER_BLOOD_MEMORY = "BERSERKER_0_14";

    public static String BERSERKER_BLOODY_WRAITH = "BERSERKER_0_20";
    public static String BERSERKER_BLOODY_BURST = "BERSERKER_0_15";

    public static String BERSERKER_RAMPAGE = "BERSERKER_0_18";
    public static String BERSERKER_DEATH_GRIP = "BERSERKER_0_16";

    public static String BERSERKER_BLODD_RIVEN = "BERSERKER_0_21";
    public static String BERSERKER_BLOOD_LEECH = "BERSERKER_0_19";

    public static String EXBERSERKER_CROSS_SLASH_UPGRADE = "EXBERSERKER_0_2";
    public static String EXBERSERKER_DOWNWARD_STRIKE_UPGRADE = "EXBERSERKER_0_5";
    public static String EXBERSERKER_BERSERKS_BLOOD_UPGRADE = "EXBERSERKER_0_1";
    public static String EXBERSERKER_BLOODLUST_GRIP_UPGRADE = "EXBERSERKER_0_7";
    public static String EXBERSERKER_DERANGE_UPGRADE = "EXBERSERKER_0_4";
    public static String EXBERSERKER_DENIAL_OF_DEATH_UPGRADE = "EXBERSERKER_0_3";
    public static String EXBERSERKER_BLOOD_FURY = "EXBERSERKER_0_8";
    public static String EXBERSERKER_BLOOD_SWORD_UPGRADE = "EXBERSERKER_0_10";
    public static String EXBERSERKER_BLOODY_TWISTER_UPGRADE = "EXBERSERKER_0_6";
    public static String EXBERSERKER_OUTRAGE_BREAK_UPGRADE = "EXBERSERKER_0_9";

    public static final int BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_2 = 2;
    public static final int BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_3 = 3;
    public static final int BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_5 = 5;
    public static final int BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_8 = 8;
    public static final int BERSERKER_TOTAL_HP_CONSUMPTION_MULTIPLIER_10 = 10;

    public static void BerserkerSkillInit() {
        Berserker008.init();
        Berserker009.init();

        Berserker007.init();
        Berserker001.init();

        Berserker010.init();
        Berserker004.init();

        Berserker006.init();
        Berserker005.init();

        Berserker003.init();
        Berserker002.init();

        Berserker017.init();
        Berserker011.init();

        Berserker012.init();

        Berserker013.init();
        Berserker014.init();

        Berserker020.init();
        Berserker015.init();

        Berserker018.init();
        Berserker016.init();

        //TODO: 021
        Berserker019.init();
    }
}
