package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;

public class FetchDfoSwdClientPlayerInitFromServer {
    public FetchDfoSwdClientPlayerInitFromServer() {

    }

    public FetchDfoSwdClientPlayerInitFromServer(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
            ClientItemOverlayTimer.ClientItemOverlayTimerInit();
            ParticleRegistry.ParticleRegistryInit();
            GeoModelClientMethodsRegistry.GeoModelClientActionNameMapRegistryInit();
//            DfoSwdClientMethodsRegistry.ClientActionNameMapRegistryInit();


        });

        return true;
    }
}
