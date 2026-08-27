package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.GeoModelClientMethodsRegistry;
import net.dingdingduang.dfoswordmanskilltree.particle.ParticleRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.client.ClientItemOverlayTimer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps.getTemporaryBlockStateMap;

public class FetchDfoSwdClientPlayerClearFromServer {
    public FetchDfoSwdClientPlayerClearFromServer() {

    }

    public FetchDfoSwdClientPlayerClearFromServer(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
            ClientItemOverlayTimer.getClientItemOverlayTimerOverlay().setActive(false);
            getTemporaryBlockStateMap().clear();
            ParticleRegistry.ParticleMapClear();
            GeoModelClientMethodsRegistry.GeoModelClientActionNameMapClear();
//            DfoSwdClientMethodsRegistry.ClientActionNameMapClear();
        });

        return true;
    }
}
