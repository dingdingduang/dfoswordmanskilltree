package net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import modified.org.joml.Vector3f;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;
import static net.dingdingduang.dfoswordmanskilltree.util.tiltblock.TiltBlockMethods.SetupCircleTiltBlocksZone;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class SendTiltBlockActionPacketToServer {
    public static Identifier ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "send_tilt_block_action_packet_to_server");

    public static PacketByteBuf setupPacket(float vec3x, float vec3y, float vec3z, float radius, int lifeTimeTicks, float bouncingHeight, int bouncingTotalTimeTicks, boolean shouldResetBlockState, boolean hasRandomizedLifeTimeTicks, boolean hasSound, boolean hasParticle) {
        PacketByteBuf packetToBeSent = DfoSwdNetworkingMsgInitialization.createPackage();
        packetToBeSent.writeFloat(vec3x);
        packetToBeSent.writeFloat(vec3y);
        packetToBeSent.writeFloat(vec3z);
        packetToBeSent.writeFloat(radius);
        packetToBeSent.writeInt(lifeTimeTicks);
        packetToBeSent.writeFloat(bouncingHeight);
        packetToBeSent.writeInt(bouncingTotalTimeTicks);
        packetToBeSent.writeBoolean(shouldResetBlockState);
        packetToBeSent.writeBoolean(hasRandomizedLifeTimeTicks);
        packetToBeSent.writeBoolean(hasSound);
        packetToBeSent.writeBoolean(hasParticle);
        return packetToBeSent;
    }

    public static void toServerHandle(MinecraftServer server, ServerPlayerEntity serverPlayer, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //decode packet here
        final float vec3x = buf.readFloat();
        final float vec3y = buf.readFloat();
        final float vec3z = buf.readFloat();
        final float radius = buf.readFloat();
        final int lifeTimeTicks = buf.readInt();
        final float bouncingHeight = buf.readFloat();
        final int bouncingTotalTimeTicks = buf.readInt();
        final boolean shouldResetBlockState = buf.readBoolean();
        final boolean hasRandomizedLifeTimeTicks = buf.readBoolean();
        final boolean hasSound = buf.readBoolean();
        final boolean hasParticle = buf.readBoolean();

        server.execute( () -> {
            //start action
            SetupCircleTiltBlocksZone(new Vector3f(vec3x, vec3y, vec3z), getEntityLevel(serverPlayer),
                    radius, lifeTimeTicks, bouncingHeight, bouncingTotalTimeTicks,
                    shouldResetBlockState, hasRandomizedLifeTimeTicks, hasSound, hasParticle);

        });
    }
}
