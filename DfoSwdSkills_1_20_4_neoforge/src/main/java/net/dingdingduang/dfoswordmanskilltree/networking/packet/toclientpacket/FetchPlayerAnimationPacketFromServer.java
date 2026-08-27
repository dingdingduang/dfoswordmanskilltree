package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods.SetPlayerAnimationClient;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchPlayerAnimationPacketFromServer(String AnimFileName, String AnimActionName) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_player_animation_packet_from_server");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public FetchPlayerAnimationPacketFromServer(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readUtf());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.AnimFileName);
        buf.writeUtf(this.AnimActionName);
    }

    public static void handle(final FetchPlayerAnimationPacketFromServer data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
                    //do on main thread

                    if (getClientPlayer() == null) { return; }
                    SetPlayerAnimationClient(getClientPlayer(), data.AnimFileName(), data.AnimActionName());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.packetHandler().disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
