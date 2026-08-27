package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.providers.DfoGeneralDamageProvider;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.providers.DfoGeneralDamageTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = DfoSwordmanSkillTreeConstants.MOD_ID)
public class DfoSwdGatherDataEvent {
    @SubscribeEvent
    public static void DfoSwordmanGatherDataEvent(GatherDataEvent event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> providerCompletableFuture = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        event.getGenerator().addProvider(event.includeServer(), new DfoGeneralDamageProvider(packOutput, providerCompletableFuture));
        event.getGenerator().addProvider(event.includeServer(), new DfoGeneralDamageTagsProvider(packOutput, providerCompletableFuture, existingFileHelper));
    }
}
