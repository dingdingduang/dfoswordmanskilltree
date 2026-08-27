package net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import org.joml.Vector3f;

import java.util.function.Supplier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;
import static net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods.SetupCircleTiltBlocksZone;

public class SendTiltBlockActionPacketToServer {
    private final float vec3x;
    private final float vec3y;
    private final float vec3z;
    private final float radius;
    private final int lifeTimeTicks;
    private final float bouncingHeight;
    private final int bouncingTotalTimeTicks;
    private final boolean shouldResetBlockState;
    private final boolean hasRandomizedLifeTimeTicks;
    private final boolean hasSound;
    private final boolean hasParticle;

    public SendTiltBlockActionPacketToServer(float vecX, float vecY, float vecZ, float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks,
                                             boolean shouldResetBlockState, boolean isRandomizedLifeTime, boolean hasSound, boolean hasParticle) {
        this.vec3x = vecX;
        this.vec3y = vecY;
        this.vec3z = vecZ;
        this.radius = radius;
        this.lifeTimeTicks = lifeTimeTicks;
        this.bouncingHeight = bouncingHeight;
        this.bouncingTotalTimeTicks = bouncingTotalTimeTicks;
        this.shouldResetBlockState = shouldResetBlockState;
        this.hasRandomizedLifeTimeTicks = isRandomizedLifeTime;
        this.hasSound = hasSound;
        this.hasParticle = hasParticle;
    }

    public SendTiltBlockActionPacketToServer(FriendlyByteBuf buf) {
        this.vec3x = buf.readFloat();
        this.vec3y = buf.readFloat();
        this.vec3z = buf.readFloat();
        this.radius = buf.readFloat();
        this.lifeTimeTicks = buf.readInt();
        this.bouncingHeight = buf.readFloat();
        this.bouncingTotalTimeTicks = buf.readInt();
        this.shouldResetBlockState = buf.readBoolean();
        this.hasRandomizedLifeTimeTicks = buf.readBoolean();
        this.hasSound = buf.readBoolean();
        this.hasParticle = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(this.vec3x);
        buf.writeFloat(this.vec3y);
        buf.writeFloat(this.vec3z);
        buf.writeFloat(this.radius);
        buf.writeInt(this.lifeTimeTicks);
        buf.writeFloat(this.bouncingHeight);
        buf.writeInt(this.bouncingTotalTimeTicks);
        buf.writeBoolean(this.shouldResetBlockState);
        buf.writeBoolean(this.hasRandomizedLifeTimeTicks);
        buf.writeBoolean(this.hasSound);
        buf.writeBoolean(this.hasParticle);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sp1 = ctx.get().getSender();
            if (sp1 == null) { return; }

            //do shiet on server side
            SetupCircleTiltBlocksZone(new Vector3f(this.vec3x, this.vec3y, this.vec3z), getEntityLevel(sp1),
                    this.radius, this.lifeTimeTicks, this.bouncingHeight, this.bouncingTotalTimeTicks,
                    this.shouldResetBlockState, this.hasRandomizedLifeTimeTicks, this.hasSound, this.hasParticle);
        });

        return true;
    }
}
