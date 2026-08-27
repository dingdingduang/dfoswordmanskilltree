package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;

public class FetchClientPlayerPosFromServer {
    private final float x1;
    private final float y1;
    private final float z1;

    public FetchClientPlayerPosFromServer(float x, float y, float z) {
        this.x1 = x;
        this.y1 = y;
        this.z1 = z;
    }

    public FetchClientPlayerPosFromServer(FriendlyByteBuf buf) {
        this.x1 = buf.readFloat();
        this.y1 = buf.readFloat();
        this.z1 = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(this.x1);
        buf.writeFloat(this.y1);
        buf.writeFloat(this.z1);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
            if (getClientPlayer() == null) { return; }
//            moveEntityToXYZ(getClientPlayer(), this.x1, this.y1, this.z1);
            setEntityPosXYZ(getClientPlayer(), this.x1, this.y1, this.z1);
        });

        return true;
    }
}
