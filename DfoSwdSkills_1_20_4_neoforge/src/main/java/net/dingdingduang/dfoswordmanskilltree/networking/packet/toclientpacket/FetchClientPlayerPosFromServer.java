package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchClientPlayerPosFromServer(float x1, float y1, float z1) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_client_player_pos_from_server");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public FetchClientPlayerPosFromServer(FriendlyByteBuf buf) {
        this(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(this.x1);
        buf.writeFloat(this.y1);
        buf.writeFloat(this.z1);
    }

    public static void handle(final FetchClientPlayerPosFromServer data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
                    //do on main thread

                    if (getClientPlayer() == null) { return; }
//            moveEntityToXYZ(getClientPlayer(), this.x1, this.y1, this.z1);
                    setEntityPosXYZ(getClientPlayer(), data.x1(), data.y1(), data.z1());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.packetHandler().disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
