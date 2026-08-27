package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods.SetPlayerAnimationClient;

public class FetchPlayerAnimationPacketFromServer {
    private final String AnimFileName;
    private final String AnimActionName;

    public FetchPlayerAnimationPacketFromServer(String animFileName, String animActionName) {
        this.AnimFileName = animFileName;
        this.AnimActionName = animActionName;
    }

    public FetchPlayerAnimationPacketFromServer(FriendlyByteBuf buf) {
        this.AnimFileName = buf.readUtf();
        this.AnimActionName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.AnimFileName);
        buf.writeUtf(this.AnimActionName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
            if (getClientPlayer() == null) { return; }
            SetPlayerAnimationClient(getClientPlayer(), this.AnimFileName, this.AnimActionName);
        });

        return true;
    }
}
