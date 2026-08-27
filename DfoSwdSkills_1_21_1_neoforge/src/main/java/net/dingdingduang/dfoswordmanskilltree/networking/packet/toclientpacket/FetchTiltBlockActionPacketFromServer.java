package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.somebasicskills.networking.StreamCodecMoreMethods;
import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchTiltBlockActionPacketFromServer(float vec3x, float vec3y, float vec3z, float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks, boolean shouldResetBlockState, boolean hasRandomizedLifeTimeTicks, boolean hasSound, boolean hasParticle) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchTiltBlockActionPacketFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_tilt_block_action_packet_to_server") );

    public static final StreamCodec<ByteBuf, FetchTiltBlockActionPacketFromServer> STREAM_CODEC = StreamCodecMoreMethods.composite(
            ByteBufCodecs.FLOAT,
            FetchTiltBlockActionPacketFromServer::vec3x,
            ByteBufCodecs.FLOAT,
            FetchTiltBlockActionPacketFromServer::vec3y,
            ByteBufCodecs.FLOAT,
            FetchTiltBlockActionPacketFromServer::vec3z,
            ByteBufCodecs.FLOAT,
            FetchTiltBlockActionPacketFromServer::radius,
            ByteBufCodecs.INT,
            FetchTiltBlockActionPacketFromServer::lifeTimeTicks,
            ByteBufCodecs.FLOAT,
            FetchTiltBlockActionPacketFromServer::bouncingHeight,
            ByteBufCodecs.INT,
            FetchTiltBlockActionPacketFromServer::bouncingTotalTimeTicks,
            ByteBufCodecs.BOOL,
            FetchTiltBlockActionPacketFromServer::shouldResetBlockState,
            ByteBufCodecs.BOOL,
            FetchTiltBlockActionPacketFromServer::hasRandomizedLifeTimeTicks,
            ByteBufCodecs.BOOL,
            FetchTiltBlockActionPacketFromServer::hasSound,
            ByteBufCodecs.BOOL,
            FetchTiltBlockActionPacketFromServer::hasParticle,
            FetchTiltBlockActionPacketFromServer::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchTiltBlockActionPacketFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread
                    if (getClientLevel() == null) { return; }
                    TiltBlockMethods.SetupCircleTiltBlocksZone(new Vector3f(data.vec3x(), data.vec3y(), data.vec3z()), getClientLevel(),
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
