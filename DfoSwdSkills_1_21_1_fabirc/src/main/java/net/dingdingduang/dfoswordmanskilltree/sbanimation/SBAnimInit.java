package net.dingdingduang.dfoswordmanskilltree.sbanimation;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.client.network.AbstractClientPlayerEntity;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class SBAnimInit {
    public static void onClientSetupForDfoSwordmanAnimationInit() {
        InitializeAnimationFile("dfoswordmananimation");
        InitializeAnimationFile("slash_twohanded");
    }

    //This method will set your mods animation into the library.
    private static IAnimation registerPlayerAnimation(AbstractClientPlayerEntity player) {
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
