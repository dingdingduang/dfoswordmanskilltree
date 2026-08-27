package net.dingdingduang.dfoswordmanskilltree.sbanimation;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;

import net.minecraft.client.player.AbstractClientPlayer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SBAnimInit {
    @SubscribeEvent
    public static void onClientSetupForDfoSwordmanAnimationInit(FMLClientSetupEvent event)
    {
//        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
//                new ResourceLocation("sblib", "cto"),
//                42,
//                SBAnimInit::registerPlayerAnimation);

//        InitializeAnimationFile("raisehand");
        InitializeAnimationFile("dfoswordmananimation");
        InitializeAnimationFile("slash_twohanded");
    }

    //This method will set your mods animation into the library.
    private static IAnimation registerPlayerAnimation(AbstractClientPlayer player) {
        //This will be invoked for every new player
        return new ModifierLayer<>();
    }

    private static void InitializeAnimationFile(String blockbenchAnimationFileName) {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, blockbenchAnimationFileName),
                40,
                SBAnimInit::registerPlayerAnimation);
    }

    private static void InitializeAnimationFile(String blockbenchAnimationFileName, int priority) {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, blockbenchAnimationFileName),
                priority,
                SBAnimInit::registerPlayerAnimation);
    }
}
