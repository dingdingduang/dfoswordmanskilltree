package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.networking.DfoSwdNetworkingMsgInitialization;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.Blademaster037;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class FetchBlademasterSlaughterscapeForceTriggerFromServer {
    public static Identifier ID = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "fetch_blademaster_slaughterscape_force_trigger_from_server");

    public static PacketByteBuf setupPacket(int OwnerID) {
        PacketByteBuf packetToBeSent = DfoSwdNetworkingMsgInitialization.createPackage();
        packetToBeSent.writeInt(OwnerID);
        return packetToBeSent;
    }

    public static void toClientHandle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //decode packet here
        final int OwnerID = buf.readInt();

        client.execute( () -> {
            //start action
            Entity owner = EntityMethods.getEntityByIntID(getClientLevel(), OwnerID);
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
