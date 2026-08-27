package net.dingdingduang.dfoswordmanskilltree.geomodel.models.floatingsword;

import net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelAnimationController;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.minecraft.world.entity.EntityType;
import net.dingdingduang.dfoswordmanskilltree.entity.ProjectileHelper;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityLevel;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.isLevelClientSide;

public class FloatingSwordEntity extends ProjectileHelper implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private int lifetime = 160;

    private GeoModelAnimationController<FloatingSwordEntity> GeoAnimationController = new GeoModelAnimationController<>(this, "controller", 0, this::predicate, 0);

    public GeoModelAnimationController<FloatingSwordEntity> getAnimationController() {
        return this.GeoAnimationController;
    }

    public FloatingSwordEntity(EntityType<? extends ProjectileHelper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    public void tick() {
        if (!isLevelClientSide(getEntityLevel(this))) {
            lifetime--;
            if (lifetime <= 0) {
                EntityMethods.RemoveEntity(this);
            }
//            this.moveTo(this.position().x()+ExtraMathMethods.randomFloat()  - 0.5, this.position().y()+ExtraMathMethods.randomFloat()*0.5 - 0.25, this.position().z()+ExtraMathMethods.randomFloat() - 0.5);
        }
        else {
            return;
        }
    }

    protected <E extends FloatingSwordEntity> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("swordfloating", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(this.GeoAnimationController);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }
}
