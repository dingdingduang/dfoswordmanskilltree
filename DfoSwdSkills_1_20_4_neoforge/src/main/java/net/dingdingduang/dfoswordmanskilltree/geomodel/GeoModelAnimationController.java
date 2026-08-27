package net.dingdingduang.dfoswordmanskilltree.geomodel;

import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;

import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;

public class GeoModelAnimationController<T extends GeoAnimatable> extends AnimationController<T> {
//    private float TimeDelayTicks;

    public GeoModelAnimationController(T animatable, String name, int transitionLength, AnimationStateHandler<T> animationHandler, float timeDelayTicks) {
        super(animatable, name, transitionLength, animationHandler);
//        this.TimeDelayTicks = timeDelayTicks;
    }

    public void setTickOffset(double a) { this.tickOffset = a; }

//    public double getTransitionLength() { return this.transitionLength; }
//    public void setTransitionLength(double transitionLength) { this.transitionLength = transitionLength; }
//    public void adjustedTick(double a) { this.adjustTick(a); }
//    public void setShouldResetTick(boolean shouldResetTick) { this.shouldResetTick = shouldResetTick; }

//    public void playAnimation(T animatable, RawAnimation animation) {
//        this.forceAnimationReset();
//        setAnimation(animation);
//        this.currentAnimation = this.animationQueue.poll();
//        this.isJustStarting = true;
//        this.adjustTick(animatable.getTick(animatable) + getMinecraftInstance().getPartialTick());
//        this.transitionLength = 0;
//    }

//    @Override
//    protected double adjustTick(double tick) {
//        if (this.shouldResetTick) {
//            if (this.getAnimationState() == State.TRANSITIONING) {
//                this.tickOffset = tick;
//            }
//            else if (this.getAnimationState() != State.STOPPED) {
//                this.tickOffset += this.transitionLength;
//            }
//            this.shouldResetTick = false;
//        }
//
//        float adjustedTick = (float) (this.animationSpeedModifier.apply(this.animatable) * (float) MathMethods.max( tick - this.tickOffset, 0) + TimeDelayTicks);
//        if (this.currentAnimation != null && this.currentAnimation.loopType() == Animation.LoopType.LOOP) {
//            adjustedTick = adjustedTick % (float) this.currentAnimation.animation().length();
//        }
//        if (adjustedTick == this.TimeDelayTicks) { this.isJustStarting = true; }
//        return adjustedTick;
//    }
}
