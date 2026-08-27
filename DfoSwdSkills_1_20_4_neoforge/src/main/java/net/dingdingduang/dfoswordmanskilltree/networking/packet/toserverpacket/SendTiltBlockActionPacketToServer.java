package net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.joml.Vector3f;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;
import static net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods.SetupCircleTiltBlocksZone;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record SendTiltBlockActionPacketToServer(float vec3x, float vec3y, float vec3z, float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks, boolean shouldResetBlockState, boolean hasRandomizedLifeTimeTicks, boolean hasSound, boolean hasParticle) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "send_tilt_block_action_packet_to_server");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public SendTiltBlockActionPacketToServer(FriendlyByteBuf buf) {
        this(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readFloat(), buf.readInt(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(vec3x());
        buf.writeFloat(vec3y());
        buf.writeFloat(vec3z());
        buf.writeFloat(radius());
        buf.writeInt(lifeTimeTicks());
        buf.writeFloat(bouncingHeight());
        buf.writeInt(bouncingTotalTimeTicks());
        buf.writeBoolean(shouldResetBlockState());
        buf.writeBoolean(hasRandomizedLifeTimeTicks());
        buf.writeBoolean(hasSound());
        buf.writeBoolean(hasParticle());
    }

    public static void handle(final SendTiltBlockActionPacketToServer data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
                    //do on main thread
                    if (context.player().isPresent()) {
                        ServerPlayer sp1 = (ServerPlayer) context.player().get();

                        //do shiet on server side
                        SetupCircleTiltBlocksZone(new Vector3f(data.vec3x(), data.vec3y(), data.vec3z()), getEntityLevel(sp1),
                                data.radius(), data.lifeTimeTicks(), data.bouncingHeight(), data.bouncingTotalTimeTicks(),
                                data.shouldResetBlockState(), data.hasRandomizedLifeTimeTicks(), data.hasSound(), data.hasParticle());
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.packetHandler().disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
