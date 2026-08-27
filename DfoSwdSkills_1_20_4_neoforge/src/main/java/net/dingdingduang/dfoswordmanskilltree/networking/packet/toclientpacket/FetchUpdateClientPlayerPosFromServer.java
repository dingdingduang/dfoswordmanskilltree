package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchUpdateClientPlayerPosFromServer(float x1, float y1, float z1, boolean resetDelta) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_update_client_player_pos_from_server");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public FetchUpdateClientPlayerPosFromServer(FriendlyByteBuf buf) {
        this(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readBoolean());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(x1());
        buf.writeFloat(y1());
        buf.writeFloat(z1());
        buf.writeBoolean(resetDelta());
    }

    public static void handle(final FetchUpdateClientPlayerPosFromServer data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
                    //do on main thread

                    if (getClientPlayer() == null) { return; }
                    if (data.resetDelta()) {
                        setEntityDeltaMovement(getClientPlayer(), 0, 0, 0);
                    }
                    setEntityPosXYZ(getClientPlayer(), data.x1(), data.y1(), data.z1());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.packetHandler().disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
