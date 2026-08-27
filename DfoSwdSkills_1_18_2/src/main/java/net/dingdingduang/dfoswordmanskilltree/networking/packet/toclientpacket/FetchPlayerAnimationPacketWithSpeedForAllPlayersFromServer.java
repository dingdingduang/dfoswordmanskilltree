package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;

public class FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer {
    private final int AnimatedEntityID;
    private final String AnimFileName;
    private final String AnimActionName;
    private final float AnimationSpeed;

    public FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer(int animatedEntityID, String animFileName, String animActionName, float animationSpeed) {
        this.AnimatedEntityID = animatedEntityID;
        this.AnimFileName = animFileName;
        this.AnimActionName = animActionName;
        this.AnimationSpeed = animationSpeed;
    }

    public FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer(FriendlyByteBuf buf) {
        this.AnimatedEntityID = buf.readInt();
        this.AnimFileName = buf.readUtf();
        this.AnimActionName = buf.readUtf();
        this.AnimationSpeed = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.AnimatedEntityID);
        buf.writeUtf(this.AnimFileName);
        buf.writeUtf(this.AnimActionName);
        buf.writeFloat(this.AnimationSpeed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
            if (getClientLevel() == null) { return; }
            SBPlayerAnimatorMethods.SetPlayerAnimationWithSpeedModifierByEntityID(this.AnimatedEntityID, this.AnimFileName, this.AnimActionName, this.AnimationSpeed);
        });

        return true;
    }
}
