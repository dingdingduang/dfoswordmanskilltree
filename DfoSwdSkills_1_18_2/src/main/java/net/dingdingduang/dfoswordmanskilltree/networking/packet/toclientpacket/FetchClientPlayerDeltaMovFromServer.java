package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityDeltaMovement;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.setEntityDeltaMovement;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;

public class FetchClientPlayerDeltaMovFromServer {
    private final float x1;
    private final float y1;
    private final float z1;
    private final boolean isAdd;

    public FetchClientPlayerDeltaMovFromServer(float x, float y, float z, boolean isAdd) {
        this.x1 = x;
        this.y1 = y;
        this.z1 = z;
        this.isAdd = isAdd;
    }

    public FetchClientPlayerDeltaMovFromServer(FriendlyByteBuf buf) {
        this.x1 = buf.readFloat();
        this.y1 = buf.readFloat();
        this.z1 = buf.readFloat();
        this.isAdd = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(this.x1);
        buf.writeFloat(this.y1);
        buf.writeFloat(this.z1);
        buf.writeBoolean(this.isAdd);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
            if (getClientPlayer() == null) { return; }
            if (this.isAdd) {
                setEntityDeltaMovement(getClientPlayer(), getEntityDeltaMovement(getClientPlayer()).add(this.x1, this.y1, this.z1));
            }
            else {
                setEntityDeltaMovement(getClientPlayer(), this.x1, this.y1, this.z1);
            }
        });

        return true;
    }
}
