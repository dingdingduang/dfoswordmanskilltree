package net.dingdingduang.dfoswordmanskilltree.sbsskilltreemixin;

import net.dingdingduang.dfoswordmanskilltree.bus.DfoSwordmanLivingEntityEvent;
import net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl.DfoPressKeyEvent;
import net.dingdingduang.somebasicskills.globalmethods.MixinMethodCallHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(MixinMethodCallHelper.class)
public abstract class DfoSBSEventInitializationMixin {
//    @Inject(method = "Lnet/dingdingduang/somebasicskills/globalmethods/MixinMethodCallHelper;ExtraGuiOverlayAction(Lnet/minecraft/client/util/math/MatrixStack;F)V",
//            at = {@At(value = "TAIL")},
//            remap = true)
//    public void DfoSBSGuiMixin(CallbackInfo ci) {
//
//    }

    @Inject(method = "Lnet/dingdingduang/somebasicskills/globalmethods/MixinMethodCallHelper;KeyEventExtraAction(IIII)V",
            at = {@At(value = "TAIL")},
            remap = false
    )
    public void DfoSBSKeyboardInjectMixin(CallbackInfo ci) {
        DfoPressKeyEvent.dfoSwdInputEvent();
    }

    @ModifyArgs(method = "LivingEntityOnHurtAction", at = @At(value = "INVOKE", target = "Lnet/dingdingduang/somebasicskills/globalmethods/MixinMethodCallHelper;LivingEntityOnHurtExtraAction(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/damage/DamageSource;F)F"))
    private void SBSonArmorToDamage(Args args) {
        LivingEntity beingAttackedEntity = args.get(0);
        DamageSource dmgSrc = args.get(1);
        float incomingDMG = args.get(2);
        float finalDamage = DfoSwordmanLivingEntityEvent.DfoSwordLivingEntityBeforeGettingHurtEvent(beingAttackedEntity, dmgSrc, incomingDMG);

        args.set(2, finalDamage);
    }

    @ModifyArgs(method = "LivingEntityTakeKnockBackAction", at = @At(value = "INVOKE", target = "Lnet/dingdingduang/somebasicskills/globalmethods/MixinMethodCallHelper;LivingEntityTakeKnockBackExtraAction(Lnet/minecraft/entity/LivingEntity;Z)Z"))
    private void SBSonLivingEntityKnockBackExtraActionMixin(Args args) {
        LivingEntity beingAttackedEntity = args.get(0);
        boolean finalShouldCancel = args.get(1);
        if (!finalShouldCancel) {
            finalShouldCancel = DfoSwordmanLivingEntityEvent.onDfoSwdShouldCancelLivingKnockBack(beingAttackedEntity);

//            if (finalShouldCancel) {
//                printInGameMsg("canceled");
//            }
            args.set(1, finalShouldCancel);
        }
    }
}
