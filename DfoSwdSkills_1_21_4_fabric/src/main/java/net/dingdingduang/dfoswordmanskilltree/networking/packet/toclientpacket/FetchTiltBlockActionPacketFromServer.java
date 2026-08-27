package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.somebasicskills.networking.NetworkingGeneralMethods;
import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.somebasicskills.Constants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import net.minecraft.network.codec.PacketCodecs;
import org.joml.Vector3f;

import net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchTiltBlockActionPacketFromServer(float vec3x, float vec3y, float vec3z, float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks, boolean shouldResetBlockState, boolean hasRandomizedLifeTimeTicks, boolean hasSound, boolean hasParticle) implements CustomPayload {
    public static final CustomPayload.Id<FetchTiltBlockActionPacketFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_tilt_block_action_packet_to_server") );

    public static final PacketCodec<RegistryByteBuf, FetchTiltBlockActionPacketFromServer> STREAM_CODEC = NetworkingGeneralMethods.tuple(
            PacketCodecs.FLOAT,
            FetchTiltBlockActionPacketFromServer::vec3x,
            PacketCodecs.FLOAT,
            FetchTiltBlockActionPacketFromServer::vec3y,
            PacketCodecs.FLOAT,
            FetchTiltBlockActionPacketFromServer::vec3z,
            PacketCodecs.FLOAT,
            FetchTiltBlockActionPacketFromServer::radius,
            PacketCodecs.INTEGER,
            FetchTiltBlockActionPacketFromServer::lifeTimeTicks,
            PacketCodecs.FLOAT,
            FetchTiltBlockActionPacketFromServer::bouncingHeight,
            PacketCodecs.INTEGER,
            FetchTiltBlockActionPacketFromServer::bouncingTotalTimeTicks,
            PacketCodecs.BOOLEAN,
            FetchTiltBlockActionPacketFromServer::shouldResetBlockState,
            PacketCodecs.BOOLEAN,
            FetchTiltBlockActionPacketFromServer::hasRandomizedLifeTimeTicks,
            PacketCodecs.BOOLEAN,
            FetchTiltBlockActionPacketFromServer::hasSound,
            PacketCodecs.BOOLEAN,
            FetchTiltBlockActionPacketFromServer::hasParticle,
            FetchTiltBlockActionPacketFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchTiltBlockActionPacketFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
                    //do on main thread
                    if (getClientLevel() == null) { return; }
                    TiltBlockMethods.SetupCircleTiltBlocksZone(new Vector3f(data.vec3x(), data.vec3y(), data.vec3z()), getClientLevel(),
                            data.radius(), data.lifeTimeTicks(), data.bouncingHeight(), data.bouncingTotalTimeTicks(),
                            data.shouldResetBlockState(), data.hasRandomizedLifeTimeTicks(), data.hasSound(), data.hasParticle());
                });
    }
}
