package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.somebasicskills.event.SBPlayerConfigFileInitHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FetchClientPlayerResetPacketWhenRespawnFromServer {
    public FetchClientPlayerResetPacketWhenRespawnFromServer() {

    }

    public FetchClientPlayerResetPacketWhenRespawnFromServer(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side

            SBPlayerConfigFileInitHelper tempConfigStateHelper = new SBPlayerConfigFileInitHelper(null);
            tempConfigStateHelper.SBPlayerUnstuckRequestOnClient();
        });

        return true;
    }
}
