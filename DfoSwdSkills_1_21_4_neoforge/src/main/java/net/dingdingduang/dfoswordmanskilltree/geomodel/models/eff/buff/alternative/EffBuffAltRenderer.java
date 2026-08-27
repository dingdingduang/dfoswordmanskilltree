package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.alternative;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;

import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getClientPlayer;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class EffBuffAltRenderer extends GeoEntityRenderer<EffBuffAltEntity> {
    public EffBuffAltRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EffBuffAltModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(EffBuffAltEntity instance) {
        String resPath = instance.getTextureName()+( (instance.getMaxLifeTime() - instance.getLifetime()) % 20) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX;
        return GlobalClientMaps.getReusableResourceByStringID(resPath);
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID,
//                instance.getTextureName()+( (instance.getMaxLifeTime() - instance.getLifetime()) % 20)+".png"
//        );
    }

    @Override
    public RenderType getRenderType(EffBuffAltEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EntityRenderState entityRenderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        EffBuffAltEntity entity = this.animatable;

        Entity BoundEntity;
        if (entity.isBoundToEntity() && entity.getBoundEntityID() >= 0 && (BoundEntity = getEntityByIntID(getEntityLevel(getClientPlayer()), entity.getBoundEntityID())) != null) {
            Vector3f lerpVec = getEntityClientPosWithPartialTicks(BoundEntity, this.partialTick);
            moveEntityToPos(entity, lerpVec);
        }

        poseStack.pushPose();

//        Quaternionf rotatorZ = new Quaternionf();
//        QuatRotationByAxis(rotatorZ, AngleToRadians(entity.getRotationZ()), 0f, 0f, 1.0f);
//        Quaternionf rotatorX = new Quaternionf();
//        QuatRotationByAxis(rotatorX, AngleToRadians(entity.getRotationX()), 1.0f, 0f, 0f);

        Quaternionf rotatorY = new Quaternionf();
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
//        rotatorY.mul(rotatorX);

        if (entity.getEntityScale() != 1) {
            rotatorY.scale(entity.getEntityScale());
        }

        poseStack.mulPose(rotatorY);


//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, this.partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, this.partialTick, packedLight);

        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer)null, this.partialTick, packedLight);

        poseStack.popPose();
    }


    @Override
    public Color getRenderColor(EffBuffAltEntity animatable, float partialTick, int packedLight) {
//        return Color.WHITE;
        int alphaInt = animatable.getLifetime() % 24;
        if (alphaInt > 11) {
//            return Color.ofRGBA(255, 255, 255, (int) (1.0f * (24 - alphaInt) / 12 * 240) + 15);
            return Color.ofRGBA(255, 255, 255, (int) (20.0f * (24 - alphaInt)) + 15);
        }
        else {
//            return Color.ofRGBA(255, 255, 255, (int) (1.0f * alphaInt / 12 * 240) + 15);
            return Color.ofRGBA(255, 255, 255, (int) (20.0f * alphaInt) + 15);
        }
    }
}
