package net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.*;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.ex.*;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.passive.*;

public class BlademasterSkillTreeInitialization {
    public static final int[][] BLADEMASTER_SLASH_COLOR = {
            {255, 255, 255},
            {255, 255, 255},
            {255, 255, 255},
            {255, 255, 224},
            {220, 20, 60},
            {175, 238, 238}
    };

    public static final String BLADEMASTER_UPPER_SLASH = "BLADEMASTER_0_19";
    public static final String BLADEMASTER_GUARD = "BLADEMASTER_0_1";
    public static final String BLADEMASTER_STAB = "BLADEMASTER_0_7";

    public static final String BLADEMASTER_AIRSTRIKE = "BLADEMASTER_0_12";
    public static final String BLADEMASTER_AERIAL_SLASH = "BLADEMASTER_0_13";
    public static final String BLADEMASTER_QUICK_WEAPON_SWAP = "BLADEMASTER_0_16";

    public static final String BLADEMASTER_SECRET_SWORD_ART = "BLADEMASTER_0_21";
    public static final String BLADEMASTER_BACKSTEP_SLASH = "BLADEMASTER_0_20";
    public static final String BLADEMASTER_CONSECUTIVE_SLASH = "BLADEMASTER_0_5";
    public static final String BLADEMASTER_LIGHTSABRE_MASTERY = "BLADEMASTER_0_3";
    public static final String BLADEMASTER_KATANA_MASTERY = "BLADEMASTER_0_9";
    public static final String BLADEMASTER_SHORT_SWORD_MASTERY = "BLADEMASTER_0_8";
    public static final String BLADEMASTER_BLUDGEON_MASTERY = "BLADEMASTER_0_11";
    public static final String BLADEMASTER_ZANBATO_MASTERY = "BLADEMASTER_0_10";
    public static final String BLADEMASTER_ZENITH_OF_WIELDING_WEAPON = "BLADEMASTER_0_14";
    public static final String BLADEMASTER_LIGHTSABRE_TRAINING = "BLADEMASTER_0_15";

    //flow
    public static final String BLADEMASTER_FLOW_STANCE = "BLADEMASTER_0_29";
    public static final String BLADEMASTER_FLOW_CLASH = "BLADEMASTER_0_30";
    public static final String BLADEMASTER_FLOW_LEAP_SLASH = "BLADEMASTER_0_31";
    public static final String BLADEMASTER_FLOW_UPPER_SLASH = "BLADEMASTER_0_32";
    public static final String BLADEMASTER_FLOW_FORCE = "BLADEMASTER_0_33";

    //overdrive
    public static final String BLADEMASTER_OVERDRIVE = "BLADEMASTER_0_17";
    public static final String BLADEMASTER_WEAPON_MAINTENANCE = "BLADEMASTER_0_18";

    public static final String BLADEMASTER_AUTO_GUARD = "BLADEMASTER_0_2";
    public static final String BLADEMASTER_CHARGE = "BLADEMASTER_0_22";
    public static final String BLADEMASTER_COUNTER_STRIKE = "BLADEMASTER_0_4";
    public static final String BLADEMASTER_QUICK_DRAW_SWORD_QI = "BLADEMASTER_0_6";

    //45
    public static final String BLADEMASTER_DRAGON_SLASH = "BLADEMASTER_0_23";
    public static final String BLADEMASTER_ILLUSION_SWORD_DANCE = "BLADEMASTER_0_24";

    public static final String BLADEMASTER_ULTIMATE_SWORD_ART_TEMPEST = "BLADEMASTER_0_25";
    public static final String BLADEMASTER_ULTIMATE_SWORD_ART_IRON_STRIKE = "BLADEMASTER_0_26";

    public static final String BLADEMASTER_QUICK_DRAW_LARGE_SWORD_QI = "BLADEMASTER_0_27";
    public static final String BLADEMASTER_CHARGE_BURST = "BLADEMASTER_0_28";

    public static final String BLADEMASTER_SENSE_SLASH = "BLADEMASTER_0_34";
    public static final String BLADEMASTER_RAIN_OF_FALLING_SWORDS = "BLADEMASTER_0_35";

    public static final String BLADEMASTER_ULTIMATE_SWORD_ART_TELEKINETIC_SWORDS = "BLADEMASTER_0_37";
    public static final String BLADEMASTER_ZENITH_OF_SWORD_ART = "BLADEMASTER_0_36";
    public static final String BLADEMASTER_ULTIMATE_SWORD_ART_FLASH_CUT = "BLADEMASTER_0_38";

    public static final String SWORDMAN_NORMAL_ATTACK = "BLADEMASTER_0_39";

    //EX
    public static final String EXBLADEMASTER_UPPER_SLASH_UPGRADE = "EXBLADEMASTER_0_9";
    public static final String EXBLADEMASTER_GUARD_UPGRADE = "EXBLADEMASTER_0_8";
    public static final String EXBLADEMASTER_STAB_UPGRADE = "EXBLADEMASTER_0_7";
    public static final String EXBLADEMASTER_STRIKE_FROM_THE_AIR_UPGRADE = "EXBLADEMASTER_0_5";
    public static final String EXBLADEMASTER_CONSECUTIVE_SLASH_UPGRADE = "EXBLADEMASTER_0_1";
    public static final String EXBLADEMASTER_FLOWING_CLASH_UPGRADE = "EXBLADEMASTER_0_12";
    public static final String EXBLADEMASTER_FLOWING_LEAP_SLASH_UPGRADE = "EXBLADEMASTER_0_13";
    public static final String EXBLADEMASTER_FLOWING_UPWARD_SLASH_UPGRADE = "EXBLADEMASTER_0_14";
    public static final String EXBLADEMASTER_COUNTER_STRIKE_UPGRADE = "EXBLADEMASTER_0_2";
    public static final String EXBLADEMASTER_AUTO_GUARD_UPGRADE = "EXBLADEMASTER_0_3";
    public static final String EXBLADEMASTER_CHARGE_UPGRADE = "EXBLADEMASTER_0_11";
    public static final String EXBLADEMASTER_DRAW_SWORD_QI_UPGRADE = "EXBLADEMASTER_0_6";
    public static final String EXBLADEMASTER_RAGING_DRAGON_SLASH_UPGRADE = "EXBLADEMASTER_0_4";
    public static final String EXBLADEMASTER_ILLUSION_SWORD_DANCE_UPGRADE = "EXBLADEMASTER_0_10";

    public static void BlademasterSkillInit() {
        //Active
        Blademaster019.init();
        Blademaster001.init();
        Blademaster007.init();
        Blademaster039.init();

        Blademaster012.init();
        Blademaster013.init(); //Passive
        Blademaster016.init(); //Passive

        Blademaster021.init();
        Blademaster020.init();
        Blademaster005.init();
        Blademaster003.init(); //lightsabre
        Blademaster009.init(); //katana
        Blademaster008.init(); //shortsword
        Blademaster011.init(); //bludgeon
        Blademaster010.init(); //zanbato
        Blademaster014.init();
        Blademaster015.init();

        //flow skill
        Blademaster029.init();
        Blademaster030.init();
        Blademaster031.init();
        Blademaster032.init();
        Blademaster033.init();

        //buff
        Blademaster017.init();
        Blademaster018.init();

        Blademaster002.init();
        Blademaster022.init();
        Blademaster004.init();
        Blademaster006.init();

        //45
        Blademaster023.init();
        Blademaster024.init();

        //60
        Blademaster025.init();
        Blademaster026.init();

        //70
        Blademaster027.init();
        Blademaster028.init();

        //80
        Blademaster034.init();
        Blademaster035.init();

        //90
        Blademaster037.init();
        Blademaster036.init();
        Blademaster038.init();

        //100 ex skills
        EXBlademaster009.init();
        EXBlademaster008.init();
        EXBlademaster007.init();
        EXBlademaster005.init();
        EXBlademaster001.init();
        EXBlademaster012.init();
        EXBlademaster013.init();
        EXBlademaster014.init();
        EXBlademaster002.init();
        EXBlademaster003.init();
        EXBlademaster011.init();
        EXBlademaster006.init();
        EXBlademaster004.init();
        EXBlademaster010.init();
    }
}
