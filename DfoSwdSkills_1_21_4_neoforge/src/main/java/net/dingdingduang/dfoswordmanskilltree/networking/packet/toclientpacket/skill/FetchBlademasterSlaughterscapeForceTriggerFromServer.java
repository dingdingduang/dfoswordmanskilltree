package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill;

import io.netty.buffer.ByteBuf;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.Blademaster037;

import net.dingdingduang.somebasicskills.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchBlademasterSlaughterscapeForceTriggerFromServer(int OwnerID) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FetchBlademasterSlaughterscapeForceTriggerFromServer> TYPE = new CustomPacketPayload.Type<>(getMCResourceLocation(Constants.MOD_ID, "fetch_blademaster_slaughterscape_force_trigger_from_server") );

    public static final StreamCodec<ByteBuf, FetchBlademasterSlaughterscapeForceTriggerFromServer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            FetchBlademasterSlaughterscapeForceTriggerFromServer::OwnerID,
            FetchBlademasterSlaughterscapeForceTriggerFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final FetchBlademasterSlaughterscapeForceTriggerFromServer data, final IPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
                    //do on main thread

                    if (getClientLevel() == null) { return; }

                    Entity owner = EntityMethods.getEntityByIntID(getClientLevel(), data.OwnerID());
                    if (owner != null) {
                        ArrayList<SlaughterscapeSwordEntity> slaughterscapeSwordEntitiesArr = Blademaster037.getBlademaster037ClientSwordEntityArr(owner);
                        if (slaughterscapeSwordEntitiesArr != null) {
                            for (SlaughterscapeSwordEntity slaughterscapeSwordEntity : slaughterscapeSwordEntitiesArr) {
                                slaughterscapeSwordEntity.setLifetime(Blademaster037.BLADEMASTER_037_FINAL_ANIMATION_TICKS);
                            }
                        }
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
