package net.dingdingduang.dfoswordmanskilltree.geomodel;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar.ChannelingBarRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar.ChannelingIconRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.EffBuffRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.alternative.EffBuffAltRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.groundquake.EffGroundQuakeRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff.HitEffRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.simplemcsword.SimpleMCSwordCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.stab.EffStabRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair.EffStrikeFromAirRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi.DrawSwordQiRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords.FancySwordRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_block_eff.SwdTempestBlockEffRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword.BMTempestSwordRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone.SwdFadingCloneCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target.TargetZoneSelectionRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword.FloatingSwordRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.SwordSlashPurpleRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.SwordSlashWhiteRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.SwordSlashYellowRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.custom.SwordSlashCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.customtotalframesonly.SwordSlashCustomTotalFrameOnlyRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodel.TwoDRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.TwoDAnimatedTex64Renderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.TwoDAnimatedTex128Renderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.TwoDAnimatedTex256Renderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.custom.TwoDAnimated256TexCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.flat.TwoDAnimated256FlatTexCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.TwoDAnimatedTex128ManipulateYRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.TwoDAnimatedTex256ManipulateYRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.TwoDAnimatedTex64ManipulateYRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.movingpos.TwoDAnimatedTex256ManipulateYCustomRenderer;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.trail.TwoDAnimatedTexRGB256Renderer;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GeoModelRendererInit {
    @SubscribeEvent
    public static void SBGeoModelRendererInit(FMLClientSetupEvent event) {
        EntityRenderers.register(GeoModelRegistry.TWODENTITY.get(), TwoDRenderer::new);
        EntityRenderers.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_ENTITY.get(), TwoDAnimatedTex64Renderer::new);
        EntityRenderers.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_128_ENTITY.get(), TwoDAnimatedTex128Renderer::new);
        EntityRenderers.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_256_ENTITY.get(), TwoDAnimatedTex256Renderer::new);
        EntityRenderers.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_256_ENTITY_CUSTOM.get(), TwoDAnimated256TexCustomRenderer::new);
        EntityRenderers.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_64_X_ONLY_ENTITY.get(), TwoDAnimatedTex64ManipulateYRenderer::new);
        EntityRenderers.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_128_X_ONLY_ENTITY.get(), TwoDAnimatedTex128ManipulateYRenderer::new);
        EntityRenderers.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_256_X_ONLY_ENTITY.get(), TwoDAnimatedTex256ManipulateYRenderer::new);
        EntityRenderers.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_256_CUSTOM_ENTITY.get(), TwoDAnimatedTex256ManipulateYCustomRenderer::new);
        EntityRenderers.register(GeoModelRegistry.TWO_D_ANIMATED_TEXTURE_256_FLAT_CUSTOM_ENTITY.get(), TwoDAnimated256FlatTexCustomRenderer::new);
        EntityRenderers.register(GeoModelRegistry.FLOATINGSWORD.get(), FloatingSwordRenderer::new);
        EntityRenderers.register(GeoModelRegistry.SLASH_YELLOW_DUMMY.get(), SwordSlashYellowRenderer::new);
        EntityRenderers.register(GeoModelRegistry.SLASH_PURPLE_DUMMY.get(), SwordSlashPurpleRenderer::new);
        EntityRenderers.register(GeoModelRegistry.SLASH_WHITE_DUMMY.get(), SwordSlashWhiteRenderer::new);
        EntityRenderers.register(GeoModelRegistry.SLASH_CUSTOM_DUMMY.get(), SwordSlashCustomRenderer::new);
        EntityRenderers.register(GeoModelRegistry.SLASH_CUSTOM_TOTAL_FRAME_ONLY_DUMMY.get(), SwordSlashCustomTotalFrameOnlyRenderer::new);
        EntityRenderers.register(GeoModelRegistry.ENTITY_2D_256_RGB_CUSTOM.get(), TwoDAnimatedTexRGB256Renderer::new);



        //=======================
        //effect entity
        EntityRenderers.register(GeoModelRegistry.EFF_TARGET_ZONE_SELECTION.get(), TargetZoneSelectionRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_HIT.get(), HitEffRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_BUFF_ENTITY.get(), EffBuffRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_BUFF_ALT_ENTITY.get(), EffBuffAltRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_STAB_ENTITY.get(), EffStabRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_STRIKE_FROM_AIR_ENTITY.get(), EffStrikeFromAirRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_GROUND_QUAKE_ENTITY.get(), EffGroundQuakeRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_SWD_FADING_CLONE_ENTITY.get(), SwdFadingCloneCustomRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_CHANNELING_BAR.get(), ChannelingBarRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_CHANNELING_ICON.get(), ChannelingIconRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_SIMPLE_MC_SWORD.get(), SimpleMCSwordCustomRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_TEMPEST_BLOCK_EFF.get(), SwdTempestBlockEffRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_TEMPEST_SWORD_EFF.get(), BMTempestSwordRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_DRAW_SWORD_QI_EFF.get(), DrawSwordQiRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_SWD_FANCY_SWORD.get(), FancySwordRenderer::new);
        EntityRenderers.register(GeoModelRegistry.EFF_SWD_SLAUGHTERSCAPE_ENTITY.get(), SlaughterscapeSwordRenderer::new);
    }
}
