package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.skill;

import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.Blademaster037;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientLevel;

public class FetchBlademasterSlaughterscapeForceTriggerFromServer {
    private final int OwnerID;

    public FetchBlademasterSlaughterscapeForceTriggerFromServer(int ownerID) {
        this.OwnerID = ownerID;
    }

    public FetchBlademasterSlaughterscapeForceTriggerFromServer(FriendlyByteBuf buf) {
        this.OwnerID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.OwnerID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
            if (getClientLevel() == null) { return; }

            Entity owner = EntityMethods.getEntityByIntID(getClientLevel(), this.OwnerID);
            if (owner != null) {
                ArrayList<SlaughterscapeSwordEntity> slaughterscapeSwordEntitiesArr = Blademaster037.getBlademaster037ClientSwordEntityArr(owner);
                if (slaughterscapeSwordEntitiesArr != null) {
                    for (SlaughterscapeSwordEntity slaughterscapeSwordEntity : slaughterscapeSwordEntitiesArr) {
                        slaughterscapeSwordEntity.setLifetime(Blademaster037.BLADEMASTER_037_FINAL_ANIMATION_TICKS);
                    }
                }
            }
//            slaughterscapeSwordEntitiesArr.clear();




//            for (Blademaster037ExtraSkillDataStorage extraSkillDataStorage: Blademaster037.getBlademaster037ClientSideActionId2Storage().values()) {
//                ArrayList<SlaughterscapeSwordEntity> slaughterscapeSwordEntitiesArr = extraSkillDataStorage.getSwordEntityArr();
//                if (slaughterscapeSwordEntitiesArr != null) {
//                    for (SlaughterscapeSwordEntity slaughterscapeSwordEntity : slaughterscapeSwordEntitiesArr) {
//                        slaughterscapeSwordEntity.setLifetime(Blademaster037.BLADEMASTER_037_FINAL_ANIMATION_TICKS);
//                    }
//
////                    slaughterscapeSwordEntitiesArr.clear();
//                }
//            }
        });

        return true;
    }
}
