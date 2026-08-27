package net.dingdingduang.dfoswordmanskilltree.sbsskilltreemixin;

import net.dingdingduang.dfoswordmanskilltree.entity.EntityExtraSpawnData;
import net.dingdingduang.dfoswordmanskilltree.networking.packet.toclientpacket.entityextradata.FetchEntityExtraDataFromServer;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class DfoSwdEntityExtraSpawnMixin {
    //use custompayload?
    @ModifyArgs(method = "onEntitySpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;addEntity(Lnet/minecraft/entity/Entity;)V"))
    private void DfoSwdEntityExtraSpawnData(Args args) {
        Entity entity = args.get(0);
        FetchEntityExtraDataFromServer.processWhenEntitySpawn(entity);
//        if (entity instanceof EntityExtraSpawnData) {
//            FetchEntityExtraDataFromServer.processWhenEntitySpawn(entity);
//        }
    }

//    @ModifyArg(method = "onEntitySpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;addEntity(Lnet/minecraft/entity/Entity;)V"))
//    private Entity DfoSwdEntityExtraSpawnData(Entity entity) {
//        FetchEntityExtraDataFromServer.processWhenEntitySpawn(entity);
////        if (entity instanceof EntityExtraSpawnData entityExtraSpawnData) {
////            FetchEntityExtraDataFromServer.processWhenEntitySpawn(entity);
////        }
//        return entity;
//    }
}
