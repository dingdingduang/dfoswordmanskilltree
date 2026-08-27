package net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendUpdateEntityPosPacketToServer {
    private final float vec3x;
    private final float vec3y;
    private final float vec3z;
    private final int entityID;

    public SendUpdateEntityPosPacketToServer(float vecX, float vecY, float vecZ, int entityID) {
        this.vec3x = vecX;
        this.vec3y = vecY;
        this.vec3z = vecZ;
        this.entityID = entityID;
    }

    public SendUpdateEntityPosPacketToServer(FriendlyByteBuf buf) {
        this.vec3x = buf.readFloat();
        this.vec3y = buf.readFloat();
        this.vec3z = buf.readFloat();
        this.entityID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(this.vec3x);
        buf.writeFloat(this.vec3y);
        buf.writeFloat(this.vec3z);
        buf.writeInt(this.entityID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sp1 = ctx.get().getSender();
            if (sp1 == null) { return; }

            //do shiet on server side
            Entity entity = EntityMethods.getEntityByIntID(EntityMethods.getEntityLevel(sp1), this.entityID);

            if (entity != null) {
                EntityMethods.moveEntityToXYZ(entity, this.vec3x, this.vec3y, this.vec3z);
            }
        });

        return true;
    }
}
