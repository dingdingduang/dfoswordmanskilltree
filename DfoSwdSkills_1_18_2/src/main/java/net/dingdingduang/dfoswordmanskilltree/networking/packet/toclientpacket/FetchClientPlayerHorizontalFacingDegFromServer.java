package net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket;

import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import modified.org.joml.Vector3f;

import java.util.function.Supplier;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientCameraEntity;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class FetchClientPlayerHorizontalFacingDegFromServer {
    private final float HorizontalFacingDeg;

    public FetchClientPlayerHorizontalFacingDegFromServer(float horizontalFacingDeg) {
        this.HorizontalFacingDeg = horizontalFacingDeg;
    }

    public FetchClientPlayerHorizontalFacingDegFromServer(FriendlyByteBuf buf) {
        this.HorizontalFacingDeg = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(this.HorizontalFacingDeg);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //do shiet on client side
            Entity player = getClientPlayer();
            if (player == null) { return; }

//            Entity camEntity = getClientCameraEntity();
//            if (camEntity != null) {
//                Vector3f playerPos = getEntityPos(player);
//                Vector3f camPos = getEntityPos(camEntity);
//                float camHorizontalFacing = getEntityHorizontalFacingDeg(camEntity);
//                float angleBtn = Vector3fMethods.AngleFromLocationToLocationHorizontal(camPos, playerPos);
//                float diff = MathMethods.RoundAngle(this.HorizontalFacingDeg) - MathMethods.RoundAngle(angleBtn);
//                EntityMethods.setEntityHorizontalFacingDeg(player, camHorizontalFacing + diff);
//            }
            EntityMethods.setEntityHorizontalFacingDeg(player, this.HorizontalFacingDeg);
        });

        return true;
    }
}
