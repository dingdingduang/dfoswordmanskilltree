package net.dingdingduang.dfoswordmanskilltree.sbanimation;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;

import net.minecraft.client.player.AbstractClientPlayer;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

@EventBusSubscriber(modid = DfoSwordmanSkillTreeConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
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
                getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, blockbenchAnimationFileName),
                40,
                SBAnimInit::registerPlayerAnimation);
    }

    private static void InitializeAnimationFile(String blockbenchAnimationFileName, int priority) {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, blockbenchAnimationFileName),
                priority,
                SBAnimInit::registerPlayerAnimation);
    }
}
