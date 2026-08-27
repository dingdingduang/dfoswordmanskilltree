package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class EffStrikeFromAirRenderer extends GeoEntityRenderer<EffStrikeFromAirEntity> {
    public EffStrikeFromAirRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EffStrikeFromAirModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(EffStrikeFromAirEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName());
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName());
    }

    @Override
    public RenderType getRenderType(EffStrikeFromAirEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EntityRenderState entityRenderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        EffStrikeFromAirEntity entity = this.animatable;
        float entityFacingAngle = getEntityHorizontalFacingDeg(entity);
        if (entity.getOwner() != null) {
            entityFacingAngle = getEntityHorizontalFacingDeg(entity.getOwner());
//                printInGameMsg(String.format("client pos x: %.3f, y: %.3f, z: %.3f", ownerPos.x(), ownerPos.y(), ownerPos.z() ));
            Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getOwner(), this.partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
            moveEntityToPos(entity, lerpVec);
//                entity.setIsPreviousClientPosExist(true);
//                entity.setPreviousPosX(lerpVec.x());
//                entity.setPreviousPosY(lerpVec.y());
//                entity.setPreviousPosZ(lerpVec.z());

//                moveEntityToPos(entity, Vector3fMethods.convertMCVec3fToVector3f(entity.getOwner().getPosition(partialTick)).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ()));
        }

        poseStack.pushPose();

//        Quaternionf rotatorZ = new Quaternionf();
//        QuatRotationByAxis(rotatorZ, AngleToRadians(entity.getRotationZ()), 0f, 0f, 1.0f);
        Quaternionf rotatorX = new Quaternionf();
        QuatRotationByAxis(rotatorX, AngleToRadians(entity.getRotationX()), 1.0f, 0f, 0f);

        Quaternionf rotatorY = new Quaternionf();
        QuatRotationByAxis(rotatorY, AngleToRadians(entityFacingAngle), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorX);

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
    public Color getRenderColor(EffStrikeFromAirEntity animatable, float partialTick, int packedLight) {
//        return Color.WHITE;
        return Color.ofRGBA(255, 255, 255, (int) (1.0f * animatable.getLifetime() / 12 * 240) + 15 );
    }
}
