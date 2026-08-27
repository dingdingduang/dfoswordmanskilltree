package net.dingdingduang.dfoswordmanskilltree;

import net.minecraft.resources.ResourceLocation;

public final class DfoSwordmanSkillTreeConstants {
    public static final String MOD_ID = "dfoswordmanskilltree";

    public static final ResourceLocation NullRendererResourceLoc = new ResourceLocation(MOD_ID, "textures/entity/blank_texture.png");

    //numeric values
    public static final float BASE_CRIT_CHANCE = 0.05f;
    public static final float BASE_CRIT_DAMAGE = 0.5f;
    public static final float MC_DEFAULT_EYE_HEIGHT = 1.62f;


//    public static final String ENTITY_FORGE_DELTA_METHOD_NAME = "setDeltaMovement";

    public static final String PNG_POSTFIX = ".png";

    public static final String MC_NO_KNOCKBACK = "MC_NO_KNOCKBACK";
    public static final String BEING_ATTACKED = "BEING_ATTACKED";
//    public static final String[] BeingHitAnimation = {"being_hit1, being_hit2"};
    public static final String NO_ANIMATION = "noaction";

//    public static final int MAX_RENDER_BRIGHTNESS = 0xF000F0; //15728880

    public static final int REGULAR_CHANNELING_ICON_LIFETIME = 40;
    public static final float REGULAR_CHANNELING_ICON_HEIGHT = 2.5f;
//    public static final float REGULAR_CHANNELING_ICON_PADDING = 4f;

    public static final String RAISE_UP_LEFT_HAND_90_DEG_ANIMATION = "raise_left_hand";
    public static final String RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_START = "raise_left_hand_start1";
    public static final String RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_LOOPING = "raise_left_hand_channeling1";
    public static final String RAISE_UP_LEFT_HAND_90_DEG_ANIMATION_END = "raise_left_hand_end1";
    public static final String RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_START = "raise_left_hand_start2";
    public static final String RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_LOOPING = "raise_left_hand_channeling2";
    public static final String RAISE_UP_LEFT_HAND_180_DEG_ANIMATION_END = "raise_left_hand_end2";

    public static final String RAISE_UP_RIGHT_HAND_90_DEG_ANIMATION = "raise_right_hand";
    public static final String RAISE_UP_RIGHT_HAND_90_DEG_ANIMATION_START = "raise_right_hand_start1";
    public static final String RAISE_UP_RIGHT_HAND_90_DEG_ANIMATION_LOOPING = "raise_right_hand_channeling1";
    public static final String RAISE_UP_RIGHT_HAND_90_DEG_ANIMATION_END = "raise_right_hand_end1";
    public static final String RAISE_UP_RIGHT_HAND_180_DEG_ANIMATION_START = "raise_right_hand_start2";
    public static final String RAISE_UP_RIGHT_HAND_180_DEG_ANIMATION_LOOPING = "raise_right_hand_channeling2";
    public static final String RAISE_UP_RIGHT_HAND_180_DEG_ANIMATION_END = "raise_right_hand_end2";

    public static final float OneOver16 = 0.0625f;

//    public static final String IS_IMMUNE_FALLING_DMG = "IS_IMMUNE_FALLING_DMG";

    //DMG type
    public static final String IS_MAGIC = "magic";

    //skill
    public static final String IN_FLOW_STANCE = "IN_FLOW_STANCE";
    public static final String IS_GUARDING = "IS_GUARDING";
    public static final String IS_CHARGING = "IS_CHARGING";
    public static final String IS_STATIONARY = "IS_STATIONARY";

    public static final float COLOR_255_RECIPROCAL = 1f / 255f;

    public static final int MAX_TICKS_FOR_ACTION = 2400;

    public static final Integer CONSTANT_INTEGER_777 = 777;
    public static final Integer CONSTANT_INTEGER_N_777 = -777;
    public static final Byte CONSTANT_BYTE_0 = 0;

    public static final Boolean CONSTANT_BOOLEAN_TRUE = true;
    public static final Boolean CONSTANT_BOOLEAN_FALSE = false;


    //Dmg type Constants
    public static final byte ELEMENT_DAMAGE_TYPE_NO_ELEMENT = 0;
    public static final byte ELEMENT_DAMAGE_TYPE_LIGHT = 1;
    public static final byte ELEMENT_DAMAGE_TYPE_DARK = 2;
    public static final byte ELEMENT_DAMAGE_TYPE_FIRE = 3;
    public static final byte ELEMENT_DAMAGE_TYPE_WATER = 4;

    public static final String COLOR_GRAY = "|cFF8a8a8a";
    public static final String COLOR_TEAL = "|cFF3dc6db";

    //Keyboard
//    public static final Boolean KidnapMovementKeys = true;
//    public static final Boolean KidnapHotBarKeys = false;
    public static final byte KidnapMovementKeys = 0;
    public static final byte KidnapHotBarKeys = 1;
    public static final byte KidnapAllKeys = 2;


    //Configuration
    public static final String SBS_EXTRA_GENERAL_CONFIG_TARGET_ZONE_SPEED = "somebasicskills.gui.general.setting.adjusttargetzonespeedlevel";

    public static final String BLADEMASTER_0_20_BACKSTEP_SLASH_CONFIG = "dfoswordmanskilltree.skill.config.blademaster020";
    public static final String BLADEMASTER_0_20_BACKSTEP_SLASH_SHOOT_HORIZONTALLY_CONFIG = "dfoswordmanskilltree.skill.config.blademaster020.shoothorizontally";
    public static final String BLADEMASTER_0_24_ILLUSION_SWORD_DANCE_CONFIG = "dfoswordmanskilltree.skill.config.blademaster024";
//    public static final String BLADEMASTER_0_24_ILLUSION_SWORD_DANCE_SHOOT_HORIZONTALLY_CONFIG = "dfoswordmanskilltree.skill.config.blademaster024.shoothorizontally";
    public static final String BLADEMASTER_0_24_ILLUSION_SWORD_DANCE_TOGGLE_OFF_FORCE_TERMINATE_ACTION_WHEN_SWITCH_WEAPON_CONFIG = "dfoswordmanskilltree.skill.config.blademaster024.toggleoffforcetoterminate";

    public static final String BLADEMASTER_0_25_TEMPEST_CONFIG = "dfoswordmanskilltree.skill.config.blademaster025";
    public static final String BLADEMASTER_0_25_TEMPEST_SHOULD_MOVE_ALONG_WITH_CLONE_ANIMATOR = "dfoswordmanskilltree.skill.config.blademaster025.shouldmovealongwithclonenanimator";

    public static final String BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_CONFIG = "dfoswordmanskilltree.skill.config.blademaster027";
    public static final String BLADEMASTER_0_27_QUICK_DRAW_LARGE_SWORD_QI_SHOOT_BASES_ON_PLAYER_VERTICAL_FACING_ANGLE_CONFIG = "dfoswordmanskilltree.skill.config.blademaster027.shootbasedoncharacterverticalfacingdegree";
}
