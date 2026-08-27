package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.Blademaster037;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.ArrayList;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchBlademasterSlaughterscapeForceTriggerFromServer(int OwnerID) implements CustomPacketPayload {
    public static final ResourceLocation ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_blademaster_slaughterscape_force_trigger_from_server");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public FetchBlademasterSlaughterscapeForceTriggerFromServer(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.OwnerID);
    }

    public static void handle(final FetchBlademasterSlaughterscapeForceTriggerFromServer data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
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
                    context.packetHandler().disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
