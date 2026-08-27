package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.entityextradata;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.entity.EntityExtraSpawnData;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchEntityExtraDataFromServer(int entityID, NbtCompound extraDataNBT) implements CustomPayload {
    private final static HashMap<Integer, NbtCompound> EntityWithExtraDataMap = new HashMap<>();

    public static final CustomPayload.Id<FetchEntityExtraDataFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_entity_extra_data_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchEntityExtraDataFromServer> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            FetchEntityExtraDataFromServer::entityID,
            PacketCodecs.NBT_COMPOUND,
            FetchEntityExtraDataFromServer::extraDataNBT,
            FetchEntityExtraDataFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static PacketByteBuf setupPacket(Entity entityWithExtraData) {
        PacketByteBuf packetToBeSent = DfoSwdNetworkingMsgInitialization.createPackage();
        packetToBeSent.writeInt(EntityMethods.getEntityHashID(entityWithExtraData));

        EntityExtraSpawnData entityExtraSpawnData = (EntityExtraSpawnData) entityWithExtraData;

        PacketByteBuf spawnDataBuffer = DfoSwdNetworkingMsgInitialization.createPackage();

        entityExtraSpawnData.writeSpawnData(spawnDataBuffer);
        packetToBeSent.writeVarInt(spawnDataBuffer.readableBytes());
        packetToBeSent.writeBytes(spawnDataBuffer);

        spawnDataBuffer.release();

        return packetToBeSent;
    }

    public static void processWhenEntitySpawn(Entity entity) {
        int entityID = EntityMethods.getEntityHashID(entity);
        NbtCompound possibleNBTExtraData = EntityWithExtraDataMap.remove(entityID);
        if (possibleNBTExtraData != null) {
            PacketByteBuf possiblePacketBuf = DfoSwdNetworkingMsgInitialization.createPackage();
            try {
                possiblePacketBuf.writeNbt(possibleNBTExtraData);
                if (entity instanceof EntityExtraSpawnData entityExtraSpawnData) {
                    entityExtraSpawnData.readSpawnData(possiblePacketBuf);
                }
            }
            finally {
                possiblePacketBuf.release();
            }
        }
    }

    public static void handle(final FetchEntityExtraDataFromServer data, final ClientPlayNetworking.Context context) {
        //decode packet here

        context.client().execute(() -> {
            //do on main thread

            PacketByteBuf entityExtraDataPacket = DfoSwdNetworkingMsgInitialization.createPackage();
            entityExtraDataPacket.writeNbt(data.extraDataNBT());
            try {
                Entity entity = EntityMethods.getEntityByIntID(getClientLevel(), data.entityID());
                if (entity == null) {
                    EntityWithExtraDataMap.put(data.entityID(), data.extraDataNBT());
                    entityExtraDataPacket.release();
                    return;
                }
                if (entity instanceof EntityExtraSpawnData entityExtraSpawnData) {
                    entityExtraSpawnData.readSpawnData(entityExtraDataPacket);
                }
            }
            finally {
                entityExtraDataPacket.release();
            }
        });
    }
}
