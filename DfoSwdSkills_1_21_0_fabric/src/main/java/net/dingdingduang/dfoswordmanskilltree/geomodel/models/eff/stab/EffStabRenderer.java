package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.stab;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.util.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class EffStabRenderer extends GeoEntityRenderer<EffStabEntity> {
    public EffStabRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new EffStabModel());
        this.shadowOpacity = 0.0f;
    }

    @Override
    public Identifier getTextureLocation(EffStabEntity instance) {
        String resPath = instance.getTextureName() + instance.getRandomModelTexture() + "_"  + (instance.getMaxLifeTime() - instance.getLifetime()) + DfoSwordmanSkillTreeConstants.PNG_POSTFIX;
        return GlobalClientMaps.getReusableResourceByStringID(resPath);
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID,
//                instance.getTextureName()+instance.getRandomModelTexture()+"_"
//                        +(instance.getMaxLifeTime() - instance.getLifetime())+".png"
//        );
    }

    @Override
    public RenderLayer getRenderType(EffStabEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EffStabEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        this.animatable = entity;

        //client move
        if (entity.isBoundToEntity() && entity.getOwner() != null) {
            Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getOwner(), partialTick).add(entity.getAdditionalPosX(), entity.getAdditionalPosY(), entity.getAdditionalPosZ());
            moveEntityToPos(entity, lerpVec);
        }

        poseStack.push();

//        Quaternionf rotatorZ = new Quaternionf();
//        QuatRotationByAxis(rotatorZ, AngleToRadians(entity.getRotationZ()), 0f, 0f, 1.0f);
        Quaternionf rotatorX = new Quaternionf();
        QuatRotationByAxis(rotatorX, AngleToRadians(entity.getRotationX()), 1.0f, 0f, 0f);

        Quaternionf rotatorY = new Quaternionf();
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorX);

        if (entity.getEntityScale() != 1) {
            rotatorY.scale(entity.getEntityScale());
        }

        poseStack.multiply(rotatorY);


//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, entityYaw, partialTick, packedLight);

        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), (VertexConsumer)null, entityYaw, partialTick, packedLight);

        poseStack.pop();
    }


    @Override
    public Color getRenderColor(EffStabEntity animatable, float partialTick, int packedLight) {
//        return Color.WHITE;
        return Color.ofRGBA(255, 255, 255, (int) (1.0f * animatable.getLifetime() / 12 * 240) + 15 );
    }
}
