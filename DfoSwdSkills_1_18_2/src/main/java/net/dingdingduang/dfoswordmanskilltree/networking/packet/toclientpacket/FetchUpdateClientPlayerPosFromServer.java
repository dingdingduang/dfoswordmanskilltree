package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class FetchUpdateClientPlayerPosFromServer {
    private final float x1;
    private final float y1;
    private final float z1;
    private final boolean resetDelta;

    public FetchUpdateClientPlayerPosFromServer(float x, float y, float z, boolean ResetDelta) {
        this.x1 = x;
        this.y1 = y;
        this.z1 = z;
        this.resetDelta = ResetDelta;
    }

    public FetchUpdateClientPlayerPosFromServer(FriendlyByteBuf buf) {
        this.x1 = buf.readFloat();
        this.y1 = buf.readFloat();
        this.z1 = buf.readFloat();
        this.resetDelta = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(this.x1);
        buf.writeFloat(this.y1);
        buf.writeFloat(this.z1);
        buf.writeBoolean(this.resetDelta);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
            if (getClientPlayer() == null) { return; }
            if (this.resetDelta) {
                setEntityDeltaMovement(getClientPlayer(), 0, 0, 0);
            }
            setEntityPosXYZ(getClientPlayer(), this.x1, this.y1, this.z1);
        });

        return true;
    }
}
