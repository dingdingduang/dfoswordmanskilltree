package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.providers.DfoGeneralDamageProvider;
import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.damagesrc.providers.DfoGeneralDamageTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = DfoSwordmanSkillTreeConstants.MOD_ID)
public class DfoSwdGatherDataEvent {
    @SubscribeEvent
    public static void DfoSwordmanGatherDataEventOnClient(GatherDataEvent.Client event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> providerCompletableFuture = event.getLookupProvider();

        event.getGenerator().addProvider(true, new DfoGeneralDamageProvider(packOutput, providerCompletableFuture));
        event.getGenerator().addProvider(true, new DfoGeneralDamageTagsProvider(packOutput, providerCompletableFuture));
    }

    @SubscribeEvent
    public static void DfoSwordmanGatherDataEventOnServer(GatherDataEvent.Server event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> providerCompletableFuture = event.getLookupProvider();

        event.getGenerator().addProvider(true, new DfoGeneralDamageProvider(packOutput, providerCompletableFuture));
        event.getGenerator().addProvider(true, new DfoGeneralDamageTagsProvider(packOutput, providerCompletableFuture));
    }

//    @SubscribeEvent
//    public static void DfoSwordmanGatherDataEventClient(GatherDataEvent.Client event) {
//        event.createProvider(new RegistrySetBuilder()
//                .add(Registries.DAMAGE_TYPE, bootstrap -> {
//                    bootstrap.register(DfoGeneralDamageTypes.SHOCK_DAMAGE_TYPE, new DamageType(DfoGeneralDamageTypes.SHOCK_DAMAGE_TYPE.location().getPath(),
//                            DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
//                            0,
//                            DamageEffects.HURT,
//                            DeathMessageType.DEFAULT)
//                    );
//                })
//                .add(Registries.DAMAGE_TYPE, bootstrap -> {
//                    bootstrap.register(DfoGeneralDamageTypes.BLEEDING_DAMAGE_TYPE, new DamageType(DfoGeneralDamageTypes.BLEEDING_DAMAGE_TYPE.location().getPath(),
//                            DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
//                            0,
//                            DamageEffects.HURT,
//                            DeathMessageType.DEFAULT)
//                    );
//                })
//    );
//    }
}
