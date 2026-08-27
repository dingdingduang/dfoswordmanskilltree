package net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket;

import net.dingdingduang.somebasicskills.networking.NetworkingGeneralMethods;
import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.somebasicskills.Constants;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.network.codec.PacketCodecs;
import org.joml.Vector3f;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;
import static net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods.SetupCircleTiltBlocksZone;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record SendTiltBlockActionPacketToServer(float vec3x, float vec3y, float vec3z, float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks, boolean shouldResetBlockState, boolean hasRandomizedLifeTimeTicks, boolean hasSound, boolean hasParticle) implements CustomPayload {
    public static final CustomPayload.Id<SendTiltBlockActionPacketToServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "send_tilt_block_action_packet_to_server") );

    public static final PacketCodec<RegistryByteBuf, SendTiltBlockActionPacketToServer> STREAM_CODEC = NetworkingGeneralMethods.tuple(
            PacketCodecs.FLOAT,
            SendTiltBlockActionPacketToServer::vec3x,
            PacketCodecs.FLOAT,
            SendTiltBlockActionPacketToServer::vec3y,
            PacketCodecs.FLOAT,
            SendTiltBlockActionPacketToServer::vec3z,
            PacketCodecs.FLOAT,
            SendTiltBlockActionPacketToServer::radius,
            PacketCodecs.INTEGER,
            SendTiltBlockActionPacketToServer::lifeTimeTicks,
            PacketCodecs.FLOAT,
            SendTiltBlockActionPacketToServer::bouncingHeight,
            PacketCodecs.INTEGER,
            SendTiltBlockActionPacketToServer::bouncingTotalTimeTicks,
            PacketCodecs.BOOLEAN,
            SendTiltBlockActionPacketToServer::shouldResetBlockState,
            PacketCodecs.BOOLEAN,
            SendTiltBlockActionPacketToServer::hasRandomizedLifeTimeTicks,
            PacketCodecs.BOOLEAN,
            SendTiltBlockActionPacketToServer::hasSound,
            PacketCodecs.BOOLEAN,
            SendTiltBlockActionPacketToServer::hasParticle,
            SendTiltBlockActionPacketToServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final SendTiltBlockActionPacketToServer data, final ServerPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.server().execute(() -> {
                    //do on main thread
                    ServerPlayerEntity sp1 = context.player();

                    //do shiet on server side
                    SetupCircleTiltBlocksZone(new Vector3f(data.vec3x(), data.vec3y(), data.vec3z()), getEntityLevel(sp1),
                            data.radius(), data.lifeTimeTicks(), data.bouncingHeight(), data.bouncingTotalTimeTicks(),
                            data.shouldResetBlockState(), data.hasRandomizedLifeTimeTicks(), data.hasSound(), data.hasParticle());
                });
    }
}
