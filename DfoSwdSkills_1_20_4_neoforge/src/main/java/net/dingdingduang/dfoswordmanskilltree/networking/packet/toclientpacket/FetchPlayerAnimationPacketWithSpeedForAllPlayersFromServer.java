package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.sbanimation.SBPlayerAnimatorMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getPacketFailedTranslatableComponent;

public record FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer(int AnimatedEntityID, String AnimFileName, String AnimActionName, float AnimationSpeed) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_player_animation_packet_with_speed_for_all_players_from_server");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readUtf(), buf.readUtf(), buf.readFloat());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.AnimatedEntityID);
        buf.writeUtf(this.AnimFileName);
        buf.writeUtf(this.AnimActionName);
        buf.writeFloat(this.AnimationSpeed);
    }
    public static void handle(final FetchPlayerAnimationPacketWithSpeedForAllPlayersFromServer data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
                    //do on main thread

                    if (getClientLevel() == null) { return; }
                    SBPlayerAnimatorMethods.SetPlayerAnimationWithSpeedModifierByEntityID(data.AnimatedEntityID(), data.AnimFileName(), data.AnimActionName(), data.AnimationSpeed());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.packetHandler().disconnect(getPacketFailedTranslatableComponent(e.getMessage()));
                    return null;
                });
    }
}
