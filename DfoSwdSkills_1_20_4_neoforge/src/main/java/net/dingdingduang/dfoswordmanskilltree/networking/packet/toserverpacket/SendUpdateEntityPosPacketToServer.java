package net.dingdingduang.dfoswordmanskilltree.networking.packet.toserverpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record SendUpdateEntityPosPacketToServer(float vec3x, float vec3y, float vec3z, int entityID) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "send_update_entity_pos_packet_to_server");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public SendUpdateEntityPosPacketToServer(FriendlyByteBuf buf) {
        this(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(this.vec3x);
        buf.writeFloat(this.vec3y);
        buf.writeFloat(this.vec3z);
        buf.writeInt(this.entityID);
    }

    public static void handle(final SendUpdateEntityPosPacketToServer data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
                    //do on main thread
                    if (context.player().isPresent()) {
                        ServerPlayer sp1 = (ServerPlayer) context.player().get();

                        //do shiet on server side
                        Entity entity = EntityMethods.getEntityByIntID(EntityMethods.getEntityLevel(sp1), data.entityID());

                        if (entity != null) {
                            EntityMethods.moveEntityToXYZ(entity, data.vec3x(), data.vec3y(), data.vec3z());
                        }
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.packetHandler().disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
