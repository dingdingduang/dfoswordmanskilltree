package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.providers.DfoGeneralDamageProvider;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.providers.DfoGeneralDamageTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

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
