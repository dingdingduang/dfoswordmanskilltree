package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import modified.org.joml.Quaternionf;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import com.mojang.math.Quaternion;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import java.util.Collections;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.COLOR_1_OVER_255_PERCENT;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class BMTempestSwordRenderer extends GeoProjectilesRenderer<BMTempestSwordEntity> {
    public BMTempestSwordRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BMTempestSwordModel());
        this.shadowRadius = 0.0f;
    }

//    @Override
//    public int getSkyLightLevel(BMTempestSwordEntity pEntity, BlockPos pPos) {
//        return 15;
//    }

    @Override
    public int getBlockLightLevel(BMTempestSwordEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(BMTempestSwordEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName());
    }

    @Override
    public RenderType getRenderType(BMTempestSwordEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public boolean shouldRender(BMTempestSwordEntity pEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
//        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityPos(getClientPlayer()), EntityMethods.getEntityPos(pEntity)) <= 32f;
        Entity player = getClientPlayer();
        return Vector3fMethods.Vec3fDistanceTo(EntityMethods.getEntityX(player), EntityMethods.getEntityY(player), EntityMethods.getEntityZ(player), EntityMethods.getEntityX(pEntity), EntityMethods.getEntityY(pEntity), EntityMethods.getEntityZ(pEntity)) <= 32f;
    }

    @Override
    public void render(BMTempestSwordEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
        //client move
        if (entity.hasClientNextMovingPos()) {
            float newX = Vector3fMethods.lerp1D(partialTick, entity.getFromPosX(), entity.getToPosX());
            float newY = Vector3fMethods.lerp1D(partialTick, entity.getFromPosY(), entity.getToPosY());
            float newZ = Vector3fMethods.lerp1D(partialTick, entity.getFromPosZ(), entity.getToPosZ());

            EntityMethods.moveEntityToXYZ(entity, newX, newY, newZ);
        }

        poseStack.pushPose();

        Quaternionf rotatorY = new Quaternionf();
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);

        Quaternion mojangQuaternionY = new Quaternion(rotatorY.x, rotatorY.y, rotatorY.z, rotatorY.w);
        poseStack.mulPose(mojangQuaternionY);
        float scale = entity.getEntityScale();
        if (scale != 1) {
            poseStack.scale(scale, scale, scale);
        }

        var predicate = new AnimationEvent<>(entity, 0, 0, partialTick,
                false, Collections.singletonList(new EntityModelData()));

        this.modelProvider.setCustomAnimations(entity, getInstanceId(entity), predicate);
        RenderSystem.setShaderTexture(0, getTextureLocation(entity));
//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);
        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
        Color color = getRenderColor(entity);
        super.render(model, entity, partialTick, RenderType.entityTranslucent(getTextureLocation(entity)), poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.NO_OVERLAY, color.getRed() * COLOR_1_OVER_255_PERCENT, color.getGreen() * COLOR_1_OVER_255_PERCENT, color.getBlue() * COLOR_1_OVER_255_PERCENT, color.getAlpha() * COLOR_1_OVER_255_PERCENT);

        poseStack.popPose();
    }


    public Color getRenderColor(BMTempestSwordEntity animatable) {
        return Color.ofRGBA(255, 255, 255, 160);
    }
}
