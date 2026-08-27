package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.entityextradata;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.entity.EntityExtraSpawnData;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class FetchEntityExtraDataFromServer {
    private final static HashMap<Integer, PacketByteBuf> EntityWithExtraDataMap = new HashMap<>();

    public static Identifier ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_entity_extra_data_from_server");

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
        PacketByteBuf possiblePacketBuf = EntityWithExtraDataMap.remove(entityID);
        if (possiblePacketBuf != null) {
            try {
                if (entity instanceof EntityExtraSpawnData entityExtraSpawnData) {
                    entityExtraSpawnData.readSpawnData(possiblePacketBuf);
                }
            }
            finally {
                possiblePacketBuf.release();
            }
        }
    }

    public static void toClientHandle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //decode packet here
        final int entityID = buf.readInt();
        final PacketByteBuf entityExtraDataPacket = readExtraSpawnDataPacket(buf);

        client.execute( () -> {
            try {
                Entity entity = EntityMethods.getEntityByIntID(getClientLevel(), entityID);
                if (entity == null) {
                    PacketByteBuf tempBuf = DfoSwdNetworkingMsgInitialization.createPackage();
                    tempBuf.writeBytes(entityExtraDataPacket.copy());
                    EntityWithExtraDataMap.put(entityID, tempBuf);
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


    private static PacketByteBuf readExtraSpawnDataPacket(PacketByteBuf buf) {
        int varCount = buf.readVarInt();
        PacketByteBuf spawnDataBuffer = DfoSwdNetworkingMsgInitialization.createPackage();
        if (varCount > 0) { spawnDataBuffer.writeBytes(buf, varCount); }
        return spawnDataBuffer;
    }
}
