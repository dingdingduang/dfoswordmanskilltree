package net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.somebasicskills.networking.StreamCodecMoreMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.joml.Vector3f;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;
import static net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods.SetupCircleTiltBlocksZone;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record SendTiltBlockActionPacketToServer(float vec3x, float vec3y, float vec3z, float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks, boolean shouldResetBlockState, boolean hasRandomizedLifeTimeTicks, boolean hasSound, boolean hasParticle) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SendTiltBlockActionPacketToServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "send_tilt_block_action_packet_to_server") );

    public static final StreamCodec<ByteBuf, SendTiltBlockActionPacketToServer> STREAM_CODEC = StreamCodecMoreMethods.composite(
            ByteBufCodecs.FLOAT,
            SendTiltBlockActionPacketToServer::vec3x,
            ByteBufCodecs.FLOAT,
            SendTiltBlockActionPacketToServer::vec3y,
            ByteBufCodecs.FLOAT,
            SendTiltBlockActionPacketToServer::vec3z,
            ByteBufCodecs.FLOAT,
            SendTiltBlockActionPacketToServer::radius,
            ByteBufCodecs.INT,
            SendTiltBlockActionPacketToServer::lifeTimeTicks,
            ByteBufCodecs.FLOAT,
            SendTiltBlockActionPacketToServer::bouncingHeight,
            ByteBufCodecs.INT,
            SendTiltBlockActionPacketToServer::bouncingTotalTimeTicks,
            ByteBufCodecs.BOOL,
            SendTiltBlockActionPacketToServer::shouldResetBlockState,
            ByteBufCodecs.BOOL,
            SendTiltBlockActionPacketToServer::hasRandomizedLifeTimeTicks,
            ByteBufCodecs.BOOL,
            SendTiltBlockActionPacketToServer::hasSound,
            ByteBufCodecs.BOOL,
            SendTiltBlockActionPacketToServer::hasParticle,
            SendTiltBlockActionPacketToServer::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SendTiltBlockActionPacketToServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread
                    ServerPlayer sp1 = (ServerPlayer) context.player();

                    //do shiet on server side
                    SetupCircleTiltBlocksZone(new Vector3f(data.vec3x(), data.vec3y(), data.vec3z()), getEntityLevel(sp1),
                            data.radius(), data.lifeTimeTicks(), data.bouncingHeight(), data.bouncingTotalTimeTicks(),
                            data.shouldResetBlockState(), data.hasRandomizedLifeTimeTicks(), data.hasSound(), data.hasParticle());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
