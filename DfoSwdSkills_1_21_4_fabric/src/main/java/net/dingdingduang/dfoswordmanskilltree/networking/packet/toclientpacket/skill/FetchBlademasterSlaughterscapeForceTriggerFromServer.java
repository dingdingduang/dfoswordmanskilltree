package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill;

import net.minecraft.network.RegistryByteBuf;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.Blademaster037;

import net.dingdingduang.somebasicskills.Constants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.entity.Entity;
import net.minecraft.network.codec.PacketCodecs;

import java.util.ArrayList;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public record FetchBlademasterSlaughterscapeForceTriggerFromServer(int OwnerID) implements CustomPayload {
    public static final CustomPayload.Id<FetchBlademasterSlaughterscapeForceTriggerFromServer> TYPE = new CustomPayload.Id<>(getMCResourceLocation(Constants.MOD_ID, "fetch_blademaster_slaughterscape_force_trigger_from_server") );

    public static final PacketCodec<RegistryByteBuf, FetchBlademasterSlaughterscapeForceTriggerFromServer> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            FetchBlademasterSlaughterscapeForceTriggerFromServer::OwnerID,
            FetchBlademasterSlaughterscapeForceTriggerFromServer::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return TYPE;
    }

    public static void handle(final FetchBlademasterSlaughterscapeForceTriggerFromServer data, final ClientPlayNetworking.Context context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.client().execute(() -> {
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
                });
    }
}
