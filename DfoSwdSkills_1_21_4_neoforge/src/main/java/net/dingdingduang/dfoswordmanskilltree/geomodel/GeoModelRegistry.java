package net.dingdingduang.dfoswordmanskilltree.geomodel;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar.ChannelingBarEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar.ChannelingIconEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.EffBuffEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.alternative.EffBuffAltEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.groundquake.EffGroundQuakeEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff.HitEffEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.simplemcsword.SimpleMCSwordCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.stab.EffStabEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair.EffStrikeFromAirEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi.DrawSwordQiEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords.FancySwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_block_eff.SwdTempestBlockEffEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword.BMTempestSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone.SwdFadingCloneCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target.TargetZoneSelectionEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword.FloatingSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientmovementhelper.ClientMovementHelperEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.SwordSlashPurpleEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.SwordSlashWhiteEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.SwordSlashYellowEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.custom.SwordSlashCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.customtotalframesonly.SwordSlashCustomTotalFrameOnlyEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodel.TwoDEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.TwoDAnimatedTex128Entity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.TwoDAnimatedTex256Entity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.TwoDAnimatedTex64Entity;

import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.custom.TwoDAnimated256TexCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.flat.TwoDAnimated256FlatTexCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.TwoDAnimatedTex128ManipulateYEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.TwoDAnimatedTex256ManipulateYEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.TwoDAnimatedTex64ManipulateYEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.movingpos.TwoDAnimatedTex256ManipulateYCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.trail.TwoDAnimatedTexRGB256Entity;
import net.dingdingduang.somebasicskills.globalmethods.GeneralMethods;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class GeoModelRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, DfoSwordmanSkillTreeConstants.MOD_ID);

    private static ResourceKey<EntityType<?>> registerEntityTypeResourceKey(String namespace, String entityID) {
//        ResourceLocation.parse()
        return ResourceKey.create(Registries.ENTITY_TYPE, GeneralMethods.getMCResourceLocation(namespace, entityID));
    }

    //==========
    public static final DeferredHolder<EntityType<?>, EntityType<ClientMovementHelperEntity>> CLIENT_MOVEMENT_HELPER_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.CLIENT_MOVEMENT_HELPER_STR_ID,
                    () -> setupEntityTypeBuilder(ClientMovementHelperEntity::new, MobCategory.MISC, 0, 0, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.CLIENT_MOVEMENT_HELPER_STR_ID)));

    //========================================================
    public static final DeferredHolder<EntityType<?>, EntityType<TwoDEntity>> TWODENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_2D_64PX_ICON_NBT_NAME,
                    () -> setupEntityTypeBuilder(TwoDEntity::new, MobCategory.MISC, 0.5f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_64PX_ICON_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<TwoDAnimatedTex64Entity>> TWO_D_ANIMATED_TEXTURE_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_2D_ANIMATED_64PX_NBT_NAME,
                    () -> setupEntityTypeBuilder(TwoDAnimatedTex64Entity::new, MobCategory.MISC, 0.5f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_64PX_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<TwoDAnimatedTex128Entity>> TWO_D_ANIMATED_TEXTURE_128_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_2D_ANIMATED_128PX_NBT_NAME,
                    () -> setupEntityTypeBuilder(TwoDAnimatedTex128Entity::new, MobCategory.MISC, 0.5f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_128PX_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<TwoDAnimatedTex256Entity>> TWO_D_ANIMATED_TEXTURE_256_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_2D_ANIMATED_256PX_NBT_NAME,
                    () -> setupEntityTypeBuilder(TwoDAnimatedTex256Entity::new, MobCategory.MISC, 0.5f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_256PX_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<TwoDAnimated256TexCustomEntity>> TWO_D_ANIMATED_TEXTURE_256_ENTITY_CUSTOM =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_NBT_NAME,
                    () -> setupEntityTypeBuilder(TwoDAnimated256TexCustomEntity::new, MobCategory.MISC, 0.5f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<TwoDAnimatedTex64ManipulateYEntity>> TWO_D_ANIMATED_TEXTURE_64_X_ONLY_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_2D_ANIMATED_64PX_FIXED_YAXIS_NBT_NAME,
                    () -> setupEntityTypeBuilder(TwoDAnimatedTex64ManipulateYEntity::new, MobCategory.MISC, 0.5f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_64PX_FIXED_YAXIS_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<TwoDAnimatedTex128ManipulateYEntity>> TWO_D_ANIMATED_TEXTURE_128_X_ONLY_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_2D_ANIMATED_128PX_FIXED_YAXIS_NBT_NAME,
                    () -> setupEntityTypeBuilder(TwoDAnimatedTex128ManipulateYEntity::new, MobCategory.MISC, 0.5f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_128PX_FIXED_YAXIS_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<TwoDAnimatedTex256ManipulateYEntity>> TWO_D_ANIMATED_TEXTURE_256_X_ONLY_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_2D_ANIMATED_256PX_FIXED_YAXIS_NBT_NAME,
                    () -> setupEntityTypeBuilder(TwoDAnimatedTex256ManipulateYEntity::new, MobCategory.MISC, 0.5f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_256PX_FIXED_YAXIS_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<TwoDAnimatedTex256ManipulateYCustomEntity>> TWO_D_ANIMATED_TEXTURE_256_CUSTOM_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_TOTAL_FRAME_ONLY_NBT_NAME,
                    () -> setupEntityTypeBuilder(TwoDAnimatedTex256ManipulateYCustomEntity::new, MobCategory.MISC, 0.5f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_TOTAL_FRAME_ONLY_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<TwoDAnimated256FlatTexCustomEntity>> TWO_D_ANIMATED_TEXTURE_256_FLAT_CUSTOM_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_FLAT_NBT_NAME,
                    () -> setupEntityTypeBuilder(TwoDAnimated256FlatTexCustomEntity::new, MobCategory.MISC, 0.5f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_FLAT_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<TwoDAnimatedTexRGB256Entity>> ENTITY_2D_256_RGB_CUSTOM =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_2D_256PX_COLOR_CUSTOM_NBT_NAME,
                    () -> setupEntityTypeBuilder(TwoDAnimatedTexRGB256Entity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_256PX_COLOR_CUSTOM_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<FloatingSwordEntity>> FLOATINGSWORD =
            ENTITY_TYPES.register(GeoModelRegistryName.SWD_FLOATING_SWORD,
                    () -> setupEntityTypeBuilder(FloatingSwordEntity::new, MobCategory.MISC, 0.5f, 0, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_FLOATING_SWORD) ));


    public static final DeferredHolder<EntityType<?>, EntityType<SwordSlashYellowEntity>> SLASH_YELLOW_DUMMY =
            ENTITY_TYPES.register(GeoModelRegistryName.SWD_SLASH_YELLOW,
                    () -> setupEntityTypeBuilder(SwordSlashYellowEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_SLASH_YELLOW) ));


    public static final DeferredHolder<EntityType<?>, EntityType<SwordSlashPurpleEntity>> SLASH_PURPLE_DUMMY =
            ENTITY_TYPES.register(GeoModelRegistryName.SWD_SLASH_PURPLE,
                    () -> setupEntityTypeBuilder(SwordSlashPurpleEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_SLASH_PURPLE) ));


    public static final DeferredHolder<EntityType<?>, EntityType<SwordSlashWhiteEntity>> SLASH_WHITE_DUMMY =
            ENTITY_TYPES.register(GeoModelRegistryName.SWD_SLASH_WHITE,
                    () -> setupEntityTypeBuilder(SwordSlashWhiteEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_SLASH_WHITE) ));


    public static final DeferredHolder<EntityType<?>, EntityType<SwordSlashCustomEntity>> SLASH_CUSTOM_DUMMY =
            ENTITY_TYPES.register(GeoModelRegistryName.SWD_FANCY_SLASH_CUSTOM,
                    () -> setupEntityTypeBuilder(SwordSlashCustomEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_FANCY_SLASH_CUSTOM) ));


    public static final DeferredHolder<EntityType<?>, EntityType<SwordSlashCustomTotalFrameOnlyEntity>> SLASH_CUSTOM_TOTAL_FRAME_ONLY_DUMMY =
            ENTITY_TYPES.register(GeoModelRegistryName.SWD_FANCY_SLASH_CUSTOM_TOTAL_FRAME_ONLY,
                    () -> setupEntityTypeBuilder(SwordSlashCustomTotalFrameOnlyEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_FANCY_SLASH_CUSTOM_TOTAL_FRAME_ONLY) ));




    //============================================
    //effect
    public static final DeferredHolder<EntityType<?>, EntityType<TargetZoneSelectionEntity>> EFF_TARGET_ZONE_SELECTION =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_TARGET_ZONE,
                    () -> setupEntityTypeBuilder(TargetZoneSelectionEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_TARGET_ZONE) ));

    public static final DeferredHolder<EntityType<?>, EntityType<HitEffEntity>> EFF_HIT =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_HIT,
                    () -> setupEntityTypeBuilder(HitEffEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_HIT) ));

    public static final DeferredHolder<EntityType<?>, EntityType<EffBuffEntity>> EFF_BUFF_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_BUFF,
                    () -> setupEntityTypeBuilder(EffBuffEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_BUFF) ));

    public static final DeferredHolder<EntityType<?>, EntityType<EffBuffAltEntity>> EFF_BUFF_ALT_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_BUFF_ALT,
                    () -> setupEntityTypeBuilder(EffBuffAltEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_BUFF_ALT) ));

    public static final DeferredHolder<EntityType<?>, EntityType<EffStabEntity>> EFF_STAB_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_STAB,
                    () -> setupEntityTypeBuilder(EffStabEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_STAB) ));

    public static final DeferredHolder<EntityType<?>, EntityType<EffStrikeFromAirEntity>> EFF_STRIKE_FROM_AIR_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_STRIKE_FROM_AIR,
                    () -> setupEntityTypeBuilder(EffStrikeFromAirEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_STRIKE_FROM_AIR) ));

    public static final DeferredHolder<EntityType<?>, EntityType<EffGroundQuakeEntity>> EFF_GROUND_QUAKE_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_GROUND_QUAKE,
                    () -> setupEntityTypeBuilder(EffGroundQuakeEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_GROUND_QUAKE) ));

    public static final DeferredHolder<EntityType<?>, EntityType<SwdFadingCloneCustomEntity>> EFF_SWD_FADING_CLONE_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_SWD_FADING_CLONE_ENTITY_NBT_NAME,
                    () -> setupEntityTypeBuilder(SwdFadingCloneCustomEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_FADING_CLONE_ENTITY_NBT_NAME) ));

    public static final DeferredHolder<EntityType<?>, EntityType<ChannelingBarEntity>> EFF_CHANNELING_BAR =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_CHANNELING_BAR,
                    () -> setupEntityTypeBuilder(ChannelingBarEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_CHANNELING_BAR) ));

    public static final DeferredHolder<EntityType<?>, EntityType<ChannelingIconEntity>> EFF_CHANNELING_ICON =
            ENTITY_TYPES.register(GeoModelRegistryName.ENTITY_CHANNELING_ICON,
                    () -> setupEntityTypeBuilder(ChannelingIconEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_CHANNELING_ICON) ));

    public static final DeferredHolder<EntityType<?>, EntityType<SimpleMCSwordCustomEntity>> EFF_SIMPLE_MC_SWORD =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_SWD_SIMPLE_MC_SWORD,
                    () -> setupEntityTypeBuilder(SimpleMCSwordCustomEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_SIMPLE_MC_SWORD) ));

    public static final DeferredHolder<EntityType<?>, EntityType<SwdTempestBlockEffEntity>> EFF_TEMPEST_BLOCK_EFF =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_SWD_TEMPEST_BLOCK,
                    () -> setupEntityTypeBuilder(SwdTempestBlockEffEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_TEMPEST_BLOCK) ));

    public static final DeferredHolder<EntityType<?>, EntityType<BMTempestSwordEntity>> EFF_TEMPEST_SWORD_EFF =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_SWD_TEMPEST_SWORD,
                    () -> setupEntityTypeBuilder(BMTempestSwordEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_TEMPEST_SWORD) ));

    public static final DeferredHolder<EntityType<?>, EntityType<DrawSwordQiEntity>> EFF_DRAW_SWORD_QI_EFF =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_SWD_DRAW_SWORD_QI,
                    () -> setupEntityTypeBuilder(DrawSwordQiEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_DRAW_SWORD_QI) ));

    public static final DeferredHolder<EntityType<?>, EntityType<FancySwordEntity>> EFF_SWD_FANCY_SWORD =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_SWD_FANCY_SWORD,
                    () -> setupEntityTypeBuilder(FancySwordEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_FANCY_SWORD) ));

    public static final DeferredHolder<EntityType<?>, EntityType<SlaughterscapeSwordEntity>> EFF_SWD_SLAUGHTERSCAPE_ENTITY =
            ENTITY_TYPES.register(GeoModelRegistryName.EFF_SWD_SLAUGHTER_SCAPE_SWORD,
                    () -> setupEntityTypeBuilder(SlaughterscapeSwordEntity::new, MobCategory.MISC, 1.0f, 0f, registerEntityTypeResourceKey(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_SLAUGHTER_SCAPE_SWORD)  ) );

    public static <T extends Entity> EntityType<T> setupEntityTypeBuilder(EntityType.EntityFactory<T> EntityNewMethodReference, MobCategory EntityCategory, float width, float height, ResourceKey<EntityType<?>> namespace_pathID) {
        return EntityType.Builder.of(EntityNewMethodReference, EntityCategory).sized(width, height).setUpdateInterval(Integer.MAX_VALUE).setShouldReceiveVelocityUpdates(false).build(namespace_pathID);
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
