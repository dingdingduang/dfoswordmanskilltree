package net.dingdingduang.dfoswordmanskilltree.geomodel;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.resources.ResourceLocation;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class DfoSwdGeneralGeoEntityResources {
    public static final ResourceLocation NULL_ENTITY_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/nullentity.geo.json");
    public static final ResourceLocation NULL_ENTITY_TEXTURE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/nullentity_texture.png");
    public static final ResourceLocation NULL_ENTITY_ANIMATION_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/nullentity.animation.json");

    public static final ResourceLocation NO_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_NO_TEXTURE);

    public static final ResourceLocation CHANNELING_BAR_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/progressbar.geo.json");
    public static final ResourceLocation CHANNELING_BAR_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_CHANNELING_BAR_TEXTURE);
    public static final ResourceLocation CHANNELING_BAR_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/progressbar.animation.json");

    public static final ResourceLocation CHANNELING_ICON_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodentity.geo.json");
//    public static final ResourceLocation CHANNELING_ICON_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_A_TEXTURE);
    public static final ResourceLocation CHANNELING_ICON_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twodiconentity.animation.json");

    public static final ResourceLocation ONE_EFF_BUFF_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/eff_buff.geo.json");
    public static final ResourceLocation ONE_EFF_BUFF_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/common/buff/plus/0_7.png");
    public static final ResourceLocation ONE_EFF_BUFF_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/eff/buff/eff_buff.animation.json");

    public static final ResourceLocation PLURAL_EFF_BUFF_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/eff_buff_alt.geo.json");
//    public static final ResourceLocation PLURAL_EFF_BUFF_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/common/buff/plus/0_7.png");
    public static final ResourceLocation PLURAL_EFF_BUFF_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/eff/buff/eff_buff_alt.animation.json");

    public static final ResourceLocation EFF_GROUND_QUAKE_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/eff_groundquake.geo.json");
    public static final ResourceLocation EFF_GROUND_QUAKE_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/strikefromair/0.png");
    public static final ResourceLocation EFF_GROUND_QUAKE_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/eff/eff_16x1.animation.json");

    public static final ResourceLocation HIT_EFF_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtextwofivesixyrotentitycustomhorizon.geo.json");
    public static final ResourceLocation HIT_EFF_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/common/hit_damage/0.png");
    public static final ResourceLocation HIT_EFF_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twodentity_custom_horizon.animation.json");

    public static final ResourceLocation SIMPLE_MC_SWORD_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/simplemcsword.geo.json");
    public static final ResourceLocation SIMPLE_MC_SWORD_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/tempest/sword/0.png");
    public static final ResourceLocation SIMPLE_MC_SWORD_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/mcpixelsword.animation.json");

    public static final ResourceLocation EFF_STAB_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/eff_stab.geo.json");
    public static final ResourceLocation EFF_STAB_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/stab/0_0.png");
//    public static final ResourceLocation EFF_STAB_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/eff/eff_16x1.animation.json");

//    public static final ResourceLocation EFF_STRIKE_FROM_AIR_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/eff_stab.geo.json");
    public static final ResourceLocation EFF_STRIKE_FROM_AIR_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/strikefromair/0.png");
//    public static final ResourceLocation EFF_STRIKE_FROM_AIR_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/eff/eff_16x1.animation.json");

    public static final ResourceLocation DRAW_SWORD_QI_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtextwofivesixflathorizon.geo.json");
    public static final ResourceLocation DRAW_SWORD_QI_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/draw_sword_qi/blue/0.png");
    public static final ResourceLocation DRAW_SWORD_QI_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twoanimatedtextwofivesixflathorizonanimation.json");

//    public static final ResourceLocation FANCY_SWORD_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, object.getGeoModelName());
    public static final ResourceLocation FANCY_SWORD_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/rain_of_swords/swords/sword0.png");
    public static final ResourceLocation FANCY_SWORD_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/generaldfoswords.animation.json");

//    public static final ResourceLocation SLAUGHTER_SCAPE_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/simplemcsword.geo.json");
//    public static final ResourceLocation SLAUGHTER_SCAPE_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/tempest/sword/0.png");
//    public static final ResourceLocation SLAUGHTER_SCAPE_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/mcpixelsword.animation.json");

    public static final ResourceLocation TEMPEST_BLOCK_EFF_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/tempest_block_eff.geo.json");
    public static final ResourceLocation TEMPEST_BLOCK_EFF_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/tempest/block_eff/tempest_block_effect.png");
    public static final ResourceLocation TEMPEST_BLOCK_EFF_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/tempest_block_eff.animation.json");

//    public static final ResourceLocation TEMPEST_SWORD_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/simplemcsword.geo.json");
    public static final ResourceLocation TEMPEST_SWORD_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/blademaster/tempest/sword/0.png");
    public static final ResourceLocation TEMPEST_SWORD_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/tempest_sword.animation.json");

    public static final ResourceLocation SWD_FADING_CLONE_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/fadingclone.geo.json");
    public static final ResourceLocation SWD_FADING_CLONE_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/common/fadingclone/0.png");
    public static final ResourceLocation SWD_FADING_CLONE_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/clone_swd.animation.json");
    public static final ResourceLocation SWD_FADING_CLONE_MIRROR_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/clone_swd_mirror.animation.json");

    public static final ResourceLocation FLOATING_SWORD_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/mcpixelsword.geo.json");
    public static final ResourceLocation FLOATING_SWORD_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/mc_diamond_sword.png");
    public static final ResourceLocation FLOATING_SWORD_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/mcpixelsword.animation.json");

    public static final ResourceLocation SLASH_NEW_2_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/slash2.geo.json");
    public static final ResourceLocation SLASH_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/slash.animation.json");

    public static final ResourceLocation SWORD_SLASH_PURPLE_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/purple/0_0.png");
    public static final ResourceLocation SWORD_SLASH_YELLOW_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/yellow/0_0.png");

    public static final ResourceLocation SWORD_16_PX_STONE_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/mc_stone_sword.png");

    public static final ResourceLocation TWO_D_64_PX_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodentity.geo.json");
    public static final ResourceLocation TWO_D_64_PX_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twodentity.animation.json");

    public static final ResourceLocation TWO_D_ANIMATED_64_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtexsixfourentity.geo.json");
//    public static final ResourceLocation TWO_D_ANIMATED_64_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twodentity.animation.json");

    public static final ResourceLocation TWO_D_ANIMATED_128_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtexonetwoeightentity.geo.json");

    public static final ResourceLocation TWO_D_ANIMATED_256_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtextwofivesixentity.geo.json");
//    public static final ResourceLocation TWO_D_ANIMATED_256_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twodentity.animation.json");

    public static final ResourceLocation TWO_D_ANIMATED_256_CUSTOM_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtextwofivesixyrotentitycustomhorizon.geo.json");
    public static final ResourceLocation TWO_D_ANIMATED_256_CUSTOM_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twodentity_custom_horizon.animation.json");

    public static final ResourceLocation TWO_D_ANIMATED_256_FLAT_HORIZON_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtextwofivesixflathorizon.geo.json");
    public static final ResourceLocation TWO_D_ANIMATED_256_FLAT_HORIZON_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twoanimatedtextwofivesixflathorizon.animation.json");

    public static final ResourceLocation TWO_D_ANIMATED_Y_ROT_ONLY_64_PX_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtexsixfouryrotentity.geo.json");
//    public static final ResourceLocation TWO_D_ANIMATED_Y_ROT_ONLY_64_PX_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twodentity.animation.json");

    public static final ResourceLocation TWO_D_ANIMATED_Y_ROT_ONLY_128_PX_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtexonetwoeightyrotentity.geo.json");
//    public static final ResourceLocation TWO_D_ANIMATED_Y_ROT_ONLY_128_PX_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twodentity.animation.json");

    public static final ResourceLocation TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtextwofivesixyrotentity.geo.json");
//    public static final ResourceLocation TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twodentity.animation.json");

    public static final ResourceLocation TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_CUSTOM_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtextwofivesixyrotentitycustom.geo.json");
    public static final ResourceLocation TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_CUSTOM_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twodentity_custom.animation.json");

    public static final ResourceLocation TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_RGB_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/twodanimatedtextwofivesixyrotentity.geo.json");
//    public static final ResourceLocation TWO_D_ANIMATED_Y_ROT_ONLY_256_PX_RGB_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/twodentity.animation.json");

    //berserker, bloodtwister
    public static final ResourceLocation BLOOD_TWISTER_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/berserker_blood_twister.geo.json");
    public static final ResourceLocation BLOOD_TWISTER_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/berserker/blood_twister/twister/blood_twister.png");
    public static final ResourceLocation BLOOD_TWISTER_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/berserker_blood_twister.animation.json");

    //extreme overkill
    public static final ResourceLocation EXTREME_OVERKILL_BACKGROUND_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/berserker_extreme_overkill_background.geo.json");
    public static final ResourceLocation EXTREME_OVERKILL_BACKGROUND_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/berserker/extreme_overkill/background/0.png");
    public static final ResourceLocation EXTREME_OVERKILL_BACKGROUND_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/berserker_extreme_overkill_background.animation.json");

    public static final ResourceLocation EXTREME_OVERKILL_BLOOD_SWORD_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/berserker_extreme_overkill_blood_sword.geo.json");
    public static final ResourceLocation EXTREME_OVERKILL_BLOOD_SWORD_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/skilleff/berserker/extreme_overkill/background/0.png");
    public static final ResourceLocation EXTREME_OVERKILL_BLOOD_SWORD_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/berserker_extreme_overkill_blood_sword.animation.json");

//    public static final ResourceLocation A_MODEL_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "geo/progressbar.geo.json");
//    public static final ResourceLocation A_TEXTURE_PNG_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_A_TEXTURE);
//    public static final ResourceLocation A_ANIMATION_FILE_RESOURCE = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "animations/progressbar.animation.json");

}
