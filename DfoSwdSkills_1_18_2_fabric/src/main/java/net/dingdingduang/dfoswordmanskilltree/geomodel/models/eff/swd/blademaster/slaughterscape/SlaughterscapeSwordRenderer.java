package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
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
import modified.org.joml.Vector3f;
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
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.printInGameMsg;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class SlaughterscapeSwordRenderer extends GeoProjectilesRenderer<SlaughterscapeSwordEntity> {
    public SlaughterscapeSwordRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SlaughterscapeSwordModel());
        this.shadowOpacity = 0.0f;
    }

//    @Override
//    public int getSkyLightLevel(BMTempestSwordEntity pEntity, BlockPos pPos) {
//        return 15;
//    }

    @Override
    public int getBlockLight(SlaughterscapeSwordEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public Identifier getTextureLocation(SlaughterscapeSwordEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName());
    }

    @Override
    public RenderLayer getRenderType(SlaughterscapeSwordEntity animatable, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public boolean shouldRender(SlaughterscapeSwordEntity pEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        Entity player = getClientPlayer();
        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityX(player), EntityMethods.getEntityY(player), EntityMethods.getEntityZ(player), EntityMethods.getEntityX(pEntity), EntityMethods.getEntityY(pEntity), EntityMethods.getEntityZ(pEntity)) <= 32f;
    }

    @Override
    public void render(SlaughterscapeSwordEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        this.animatable = entity;
        //client move
        if (entity.hasClientNextMovingPos()) {
            if (entity.isHitOnTarget()) {
                if (entity.getClientTargetEntity() != null) {
                    Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getClientTargetEntity(), partialTick).add(entity.getAdditionalPosX(), entity.getAdditionalPosY(), entity.getAdditionalPosZ());
                    EntityMethods.moveEntityToPos(entity, lerpVec);
                }
            }
            else {
                float newX = Vector3fMethods.lerp1D(partialTick, entity.getFromPosX(), entity.getToPosX());
                float newY = Vector3fMethods.lerp1D(partialTick, entity.getFromPosY(), entity.getToPosY());
                float newZ = Vector3fMethods.lerp1D(partialTick, entity.getFromPosZ(), entity.getToPosZ());

                EntityMethods.moveEntityToXYZ(entity, newX, newY, newZ);
            }
        }

        poseStack.push();

        float clientScale = entity.getClientScale();
        float correctScale = entity.getEntityScale();
        if (clientScale < correctScale) {
            poseStack.scale(clientScale, clientScale, clientScale);
//            entity.setClientScale(clientScale + 0.025f);
            entity.setClientScale(clientScale + 0.02f);
        }
        else {
            poseStack.scale(correctScale, correctScale, correctScale);
        }

        Quaternionf rotatorY = new Quaternionf();
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);

        Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
        poseStack.multiply(mojangQuaternionY);

        poseStack.push();

        Quaternionf rotatorX = new Quaternionf();
        QuatRotationByAxis(rotatorX, AngleToRadians(getEntityVerticalFacingDeg(entity)), 1f, 0f, 0f);

        Quaternion mojangQuaternionX = new Quaternion(rotatorX.x, rotatorX.y, rotatorX.z, rotatorX.w);
        poseStack.multiply(mojangQuaternionX);

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
    }


    public Color getRenderColor(SlaughterscapeSwordEntity animatable) {
//        return Color.ofRGBA(255, 255, 255, 160);
        return Color.ofRGBA(255, 255, 255, (animatable.getLifetime() > 32) ? 180 :animatable.getLifetime() * 5 + 20);
    }
}
