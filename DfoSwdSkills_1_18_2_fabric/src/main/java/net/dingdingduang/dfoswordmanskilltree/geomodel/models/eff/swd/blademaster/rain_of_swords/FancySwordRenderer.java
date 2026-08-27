package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword.BMTempestSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Identifier;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import modified.org.joml.Quaternionf;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import net.minecraft.util.math.Quaternion;
import net.minecraft.client.render.OverlayTexture;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import java.util.Collections;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.COLOR_1_OVER_255_PERCENT;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityVerticalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class FancySwordRenderer extends GeoProjectilesRenderer<FancySwordEntity> {
    public FancySwordRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FancySwordModel());
        this.shadowOpacity = 0.0f;
    }

    @Override
    public int getBlockLight(FancySwordEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public Identifier getTextureLocation(FancySwordEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName());
    }

    @Override
    public RenderLayer getRenderType(FancySwordEntity animatable, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public boolean shouldRender(FancySwordEntity pEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
//        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityPos(getClientPlayer()), EntityMethods.getEntityPos(pEntity)) <= 32f;
        Entity player = getClientPlayer();
        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityX(player), EntityMethods.getEntityY(player), EntityMethods.getEntityZ(player), EntityMethods.getEntityX(pEntity), EntityMethods.getEntityY(pEntity), EntityMethods.getEntityZ(pEntity)) <= 32f;
    }

    @Override
    public void render(FancySwordEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        this.animatable = entity;
        //client move
        if (entity.hasClientNextMovingPos()) {
            float newX = Vector3fMethods.lerp1D(partialTick, entity.getFromPosX(), entity.getToPosX());
            float newY = Vector3fMethods.lerp1D(partialTick, entity.getFromPosY(), entity.getToPosY());
            float newZ = Vector3fMethods.lerp1D(partialTick, entity.getFromPosZ(), entity.getToPosZ());

            EntityMethods.moveEntityToXYZ(entity, newX, newY, newZ);
        }

        poseStack.push();

        Quaternionf rotatorY = new Quaternionf();
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);

        Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
        poseStack.multiply(mojangQuaternionY);
        float scale = entity.getEntityScale();
        if (scale != 1) {
            poseStack.scale(scale, scale, scale);
        }

        poseStack.push();

        Quaternionf rotatorX = new Quaternionf();
        QuatRotationByAxis(rotatorX, AngleToRadians(getEntityVerticalFacingDeg(entity)), 1f, 0f, 0f);

        Quaternion mojangQuaternionX = new Quaternion(rotatorX.x, rotatorX.y, rotatorX.z, rotatorX.w);
        poseStack.multiply(mojangQuaternionX);

        poseStack.push();

        Quaternionf rotatorZ = new Quaternionf();
        QuatRotationByAxis(rotatorZ, AngleToRadians(entity.getRotationZ()), 0f, 0f, 1f);

        Quaternion mojangQuaternionZ = new Quaternion(rotatorZ.x, rotatorZ.y, rotatorZ.z, rotatorZ.w);
        poseStack.multiply(mojangQuaternionZ);

        var predicate = new AnimationEvent<>(entity, 0, 0, partialTick,
                false, Collections.singletonList(new EntityModelData()));

        this.modelProvider.setCustomAnimations(entity, getInstanceId(entity), predicate);
        RenderSystem.setShaderTexture(0, getTextureLocation(entity));

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);
        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
        Color color = getRenderColor(entity);
        super.render(model, entity, partialTick, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.DEFAULT_UV, color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

        poseStack.pop();

        poseStack.pop();

        poseStack.pop();
    }


    public Color getRenderColor(FancySwordEntity animatable) {
        return Color.ofRGBA(255, 255, 255, 160);
    }
}
