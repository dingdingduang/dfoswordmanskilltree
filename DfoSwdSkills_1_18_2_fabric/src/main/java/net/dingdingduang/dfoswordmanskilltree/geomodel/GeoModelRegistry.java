package net.dingdingduang.dfoswordmanskilltree.geomodel;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar.ChannelingBarEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar.ChannelingBarRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar.ChannelingIconEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar.ChannelingIconRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.EffBuffEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.EffBuffRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.alternative.EffBuffAltEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.alternative.EffBuffAltRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.groundquake.EffGroundQuakeEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.groundquake.EffGroundQuakeRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff.HitEffEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff.HitEffRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.simplemcsword.SimpleMCSwordCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.simplemcsword.SimpleMCSwordCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.stab.EffStabEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.stab.EffStabRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair.EffStrikeFromAirEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair.EffStrikeFromAirRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi.DrawSwordQiEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi.DrawSwordQiRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords.FancySwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords.FancySwordRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_block_eff.SwdTempestBlockEffEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_block_eff.SwdTempestBlockEffRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword.BMTempestSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword.BMTempestSwordRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone.SwdFadingCloneCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone.SwdFadingCloneCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target.TargetZoneSelectionEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target.TargetZoneSelectionRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword.FloatingSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword.FloatingSwordRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.*;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.custom.SwordSlashCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.custom.SwordSlashCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.customtotalframesonly.SwordSlashCustomTotalFrameOnlyEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.customtotalframesonly.SwordSlashCustomTotalFrameOnlyRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodel.TwoDEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodel.TwoDRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.*;

import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.custom.TwoDAnimated256TexCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.custom.TwoDAnimated256TexCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.flat.TwoDAnimated256FlatTexCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.flat.TwoDAnimated256FlatTexCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.*;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.movingpos.TwoDAnimatedTex256ManipulateYCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.movingpos.TwoDAnimatedTex256ManipulateYCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.trail.TwoDAnimatedTexRGB256Entity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.trail.TwoDAnimatedTexRGB256Renderer;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class GeoModelRegistry {
    //========================================================
    public static final EntityType<TwoDEntity> TWODENTITY = setupEntityTypeBuilder(TwoDEntity::new, SpawnGroup.MISC, 0.5f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_64PX_ICON_NBT_NAME) );

    public static final EntityType<TwoDAnimatedTex64Entity> TWO_D_ANIMATED_TEXTURE_ENTITY = setupEntityTypeBuilder(TwoDAnimatedTex64Entity::new, SpawnGroup.MISC, 0.5f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_64PX_NBT_NAME) );

    public static final EntityType<TwoDAnimatedTex128Entity> TWO_D_ANIMATED_TEXTURE_128_ENTITY = setupEntityTypeBuilder(TwoDAnimatedTex128Entity::new, SpawnGroup.MISC, 0.5f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_128PX_NBT_NAME) );

    public static final EntityType<TwoDAnimatedTex256Entity> TWO_D_ANIMATED_TEXTURE_256_ENTITY = setupEntityTypeBuilder(TwoDAnimatedTex256Entity::new, SpawnGroup.MISC, 0.5f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_256PX_NBT_NAME) );

    public static final EntityType<TwoDAnimated256TexCustomEntity> TWO_D_ANIMATED_TEXTURE_256_ENTITY_CUSTOM = setupEntityTypeBuilder(TwoDAnimated256TexCustomEntity::new, SpawnGroup.MISC, 0.5f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_NBT_NAME) );

    public static final EntityType<TwoDAnimatedTex64ManipulateYEntity> TWO_D_ANIMATED_TEXTURE_64_X_ONLY_ENTITY = setupEntityTypeBuilder(TwoDAnimatedTex64ManipulateYEntity::new, SpawnGroup.MISC, 0.5f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_64PX_FIXED_YAXIS_NBT_NAME) );

    public static final EntityType<TwoDAnimatedTex128ManipulateYEntity> TWO_D_ANIMATED_TEXTURE_128_X_ONLY_ENTITY = setupEntityTypeBuilder(TwoDAnimatedTex128ManipulateYEntity::new, SpawnGroup.MISC, 0.5f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_128PX_FIXED_YAXIS_NBT_NAME) );

    public static final EntityType<TwoDAnimatedTex256ManipulateYEntity> TWO_D_ANIMATED_TEXTURE_256_X_ONLY_ENTITY = setupEntityTypeBuilder(TwoDAnimatedTex256ManipulateYEntity::new, SpawnGroup.MISC, 0.5f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_ANIMATED_256PX_FIXED_YAXIS_NBT_NAME) );

    public static final EntityType<TwoDAnimatedTex256ManipulateYCustomEntity> TWO_D_ANIMATED_TEXTURE_256_CUSTOM_ENTITY = setupEntityTypeBuilder(TwoDAnimatedTex256ManipulateYCustomEntity::new, SpawnGroup.MISC, 0.5f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_TOTAL_FRAME_ONLY_NBT_NAME) );

    public static final EntityType<TwoDAnimated256FlatTexCustomEntity> TWO_D_ANIMATED_TEXTURE_256_FLAT_CUSTOM_ENTITY = setupEntityTypeBuilder(TwoDAnimated256FlatTexCustomEntity::new, SpawnGroup.MISC, 0.5f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_FLAT_NBT_NAME) );

    public static final EntityType<TwoDAnimatedTexRGB256Entity> ENTITY_2D_256_RGB_CUSTOM = setupEntityTypeBuilder(TwoDAnimatedTexRGB256Entity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_2D_256PX_COLOR_CUSTOM_NBT_NAME) );

    public static final EntityType<FloatingSwordEntity> FLOATINGSWORD = setupEntityTypeBuilder(FloatingSwordEntity::new, SpawnGroup.MISC, 0.5f, 0, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_FLOATING_SWORD) );


    public static final EntityType<SwordSlashYellowEntity> SLASH_YELLOW_DUMMY = setupEntityTypeBuilder(SwordSlashYellowEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_SLASH_YELLOW) );


    public static final EntityType<SwordSlashPurpleEntity> SLASH_PURPLE_DUMMY = setupEntityTypeBuilder(SwordSlashPurpleEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_SLASH_PURPLE) );


    public static final EntityType<SwordSlashWhiteEntity> SLASH_WHITE_DUMMY = setupEntityTypeBuilder(SwordSlashWhiteEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_SLASH_WHITE) );


    public static final EntityType<SwordSlashCustomEntity> SLASH_CUSTOM_DUMMY = setupEntityTypeBuilder(SwordSlashCustomEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_FANCY_SLASH_CUSTOM) );

    public static final EntityType<SwordSlashCustomTotalFrameOnlyEntity> SLASH_CUSTOM_TOTAL_FRAME_ONLY_DUMMY = setupEntityTypeBuilder(SwordSlashCustomTotalFrameOnlyEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.SWD_FANCY_SLASH_CUSTOM_TOTAL_FRAME_ONLY) );




    //============================================
    //effect
    public static final EntityType<TargetZoneSelectionEntity> EFF_TARGET_ZONE_SELECTION = setupEntityTypeBuilder(TargetZoneSelectionEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_TARGET_ZONE) );

    public static final EntityType<HitEffEntity> EFF_HIT = setupEntityTypeBuilder(HitEffEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_HIT) );

    public static final EntityType<EffBuffEntity> EFF_BUFF_ENTITY = setupEntityTypeBuilder(EffBuffEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_BUFF) );

    public static final EntityType<EffBuffAltEntity> EFF_BUFF_ALT_ENTITY = setupEntityTypeBuilder(EffBuffAltEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_BUFF_ALT) );

    public static final EntityType<EffStabEntity> EFF_STAB_ENTITY = setupEntityTypeBuilder(EffStabEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_STAB) );

    public static final EntityType<EffStrikeFromAirEntity> EFF_STRIKE_FROM_AIR_ENTITY = setupEntityTypeBuilder(EffStrikeFromAirEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_STRIKE_FROM_AIR) );

    public static final EntityType<EffGroundQuakeEntity> EFF_GROUND_QUAKE_ENTITY = setupEntityTypeBuilder(EffGroundQuakeEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_GROUND_QUAKE) );

    public static final EntityType<SwdFadingCloneCustomEntity> EFF_SWD_FADING_CLONE_ENTITY = setupEntityTypeBuilder(SwdFadingCloneCustomEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_FADING_CLONE_ENTITY_NBT_NAME) );

    public static final EntityType<ChannelingBarEntity> EFF_CHANNELING_BAR = setupEntityTypeBuilder(ChannelingBarEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_CHANNELING_BAR) );

    public static final EntityType<ChannelingIconEntity> EFF_CHANNELING_ICON = setupEntityTypeBuilder(ChannelingIconEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.ENTITY_CHANNELING_ICON) );

    public static final EntityType<SimpleMCSwordCustomEntity> EFF_SIMPLE_MC_SWORD = setupEntityTypeBuilder(SimpleMCSwordCustomEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_SIMPLE_MC_SWORD) );

    public static final EntityType<SwdTempestBlockEffEntity> EFF_TEMPEST_BLOCK_EFF = setupEntityTypeBuilder(SwdTempestBlockEffEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_TEMPEST_BLOCK) );

    public static final EntityType<BMTempestSwordEntity> EFF_TEMPEST_SWORD_EFF = setupEntityTypeBuilder(BMTempestSwordEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_TEMPEST_SWORD) );

    public static final EntityType<DrawSwordQiEntity> EFF_DRAW_SWORD_QI_EFF = setupEntityTypeBuilder(DrawSwordQiEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_DRAW_SWORD_QI) );

    public static final EntityType<FancySwordEntity> EFF_SWD_FANCY_SWORD = setupEntityTypeBuilder(FancySwordEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_FANCY_SWORD) );

    public static final EntityType<SlaughterscapeSwordEntity> EFF_SWD_SLAUGHTERSCAPE_ENTITY = setupEntityTypeBuilder(SlaughterscapeSwordEntity::new, SpawnGroup.MISC, 1.0f, 0f, getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, GeoModelRegistryName.EFF_SWD_SLAUGHTER_SCAPE_SWORD)  );

    public static <T extends Entity> EntityType<T> setupEntityTypeBuilder(EntityType.EntityFactory<T> EntityNewMethodReference, SpawnGroup EntityCategory, float width, float height, Identifier namespace_pathID) {
//        var entityBuilder = EntityType.Builder.create(EntityNewMethodReference, EntityCategory).setDimensions(width, height).trackingTickInterval(Integer.MAX_VALUE);
        var abc = FabricEntityTypeBuilder.create(EntityCategory, EntityNewMethodReference).dimensions(new EntityDimensions(width, height, false))
                .trackedUpdateRate(Integer.MAX_VALUE)
                .forceTrackedVelocityUpdates(false).fireImmune().build();

//        EntityType<T> entityType = Registry.register(Registry.ENTITY_TYPE, namespace_pathID, entityBuilder.build(namespace_pathID.getPath()));
        EntityType<T> entityType = Registry.register(Registry.ENTITY_TYPE, namespace_pathID, abc );

//        return entityBuilder;
        return entityType;
    }

    public static void registerRendererOnClient() {
        EntityRendererRegistry.register(GeoModelRegistry.TWODENTITY, TwoDRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_ENTITY, TwoDAnimatedTex64Renderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_128_ENTITY, TwoDAnimatedTex128Renderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_256_ENTITY, TwoDAnimatedTex256Renderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_256_ENTITY_CUSTOM, TwoDAnimated256TexCustomRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_64_X_ONLY_ENTITY, TwoDAnimatedTex64ManipulateYRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_128_X_ONLY_ENTITY, TwoDAnimatedTex128ManipulateYRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_256_X_ONLY_ENTITY, TwoDAnimatedTex256ManipulateYRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_256_CUSTOM_ENTITY, TwoDAnimatedTex256ManipulateYCustomRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_256_FLAT_CUSTOM_ENTITY, TwoDAnimated256FlatTexCustomRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.FLOATINGSWORD, FloatingSwordRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.SLASH_YELLOW_DUMMY, SwordSlashYellowRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.SLASH_PURPLE_DUMMY, SwordSlashPurpleRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.SLASH_WHITE_DUMMY, SwordSlashWhiteRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.SLASH_CUSTOM_DUMMY, SwordSlashCustomRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.SLASH_CUSTOM_TOTAL_FRAME_ONLY_DUMMY, SwordSlashCustomTotalFrameOnlyRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.ENTITY_2D_256_RGB_CUSTOM, TwoDAnimatedTexRGB256Renderer::new);



        //=======================
        //effect entity
        EntityRendererRegistry.register(GeoModelRegistry.EFF_TARGET_ZONE_SELECTION, TargetZoneSelectionRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_HIT, HitEffRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_BUFF_ENTITY, EffBuffRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_BUFF_ALT_ENTITY, EffBuffAltRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_STAB_ENTITY, EffStabRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_STRIKE_FROM_AIR_ENTITY, EffStrikeFromAirRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_GROUND_QUAKE_ENTITY, EffGroundQuakeRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_SWD_FADING_CLONE_ENTITY, SwdFadingCloneCustomRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_CHANNELING_BAR, ChannelingBarRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_CHANNELING_ICON, ChannelingIconRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_SIMPLE_MC_SWORD, SimpleMCSwordCustomRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_TEMPEST_BLOCK_EFF, SwdTempestBlockEffRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_TEMPEST_SWORD_EFF, BMTempestSwordRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_DRAW_SWORD_QI_EFF, DrawSwordQiRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_SWD_FANCY_SWORD, FancySwordRenderer::new);
        EntityRendererRegistry.register(GeoModelRegistry.EFF_SWD_SLAUGHTERSCAPE_ENTITY, SlaughterscapeSwordRenderer::new);
    }
}
