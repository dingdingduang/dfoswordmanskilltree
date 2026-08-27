package net.dingdingduang.dfoswordmanskilltree.entity;

import net.minecraft.network.PacketByteBuf;

public interface EntityExtraSpawnData {
    void writeSpawnData(PacketByteBuf buf);

    void readSpawnData(PacketByteBuf buf);
}
