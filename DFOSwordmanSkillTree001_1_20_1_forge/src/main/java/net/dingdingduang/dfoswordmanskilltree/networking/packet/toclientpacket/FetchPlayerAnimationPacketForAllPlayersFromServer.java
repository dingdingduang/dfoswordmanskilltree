package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;

public class FetchPlayerAnimationPacketForAllPlayersFromServer {
    private final int SenderEntityID;
    private final String AnimFileName;
    private final String AnimActionName;

    public FetchPlayerAnimationPacketForAllPlayersFromServer(int senderEntityID, String animFileName, String animActionName) {
        this.SenderEntityID = senderEntityID;
        this.AnimFileName = animFileName;
        this.AnimActionName = animActionName;
    }

    public FetchPlayerAnimationPacketForAllPlayersFromServer(FriendlyByteBuf buf) {
        this.SenderEntityID = buf.readInt();
        this.AnimFileName = buf.readUtf();
        this.AnimActionName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.SenderEntityID);
        buf.writeUtf(this.AnimFileName);
        buf.writeUtf(this.AnimActionName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
            if (getClientLevel() == null) { return; }
            SBPlayerAnimatorMethods.SetPlayerAnimationByEntityID(this.SenderEntityID, this.AnimFileName, this.AnimActionName);
        });

        return true;
    }
}
