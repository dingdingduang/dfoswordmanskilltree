package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.util.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class SwordSlashWhiteRenderer extends GeoEntityRenderer<SwordSlashWhiteEntity> {
    public SwordSlashWhiteRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SwordSlashWhiteModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(SwordSlashWhiteEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID("textures/entity/slash/white/0_"+instance.getRandomModelTexture()+"_"+(12 - instance.getLifetime())+DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/white/0_"
//                +instance.getRandomModelTexture()+"_"+(12 - instance.getLifetime())
//                +".png");
    }

    @Override
    public RenderType getRenderType(SwordSlashWhiteEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(SwordSlashWhiteEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
//        moveEntityToXYZ(entity, entity.getX(), entity.getY()+0.1f, entity.getZ());
//        printInGameMsg("==========");
//        printInGameMsg("tickcount: "+entity.tickCount);
//        printInGameMsg("partialTick: "+partialTick);

        float entityFacingAngle = getEntityHorizontalFacingDeg(entity);
        if (entity.hasTargetLocXYZ()) {
            float distPercent = ((12-entity.getLifetime()) + partialTick) / 12;
            float newX = entity.getStartPosX() + (entity.getTargetPosX() - entity.getStartPosX()) * distPercent;
            float newY = entity.getStartPosY() + (entity.getTargetPosY() - entity.getStartPosY()) * distPercent;
            float newZ = entity.getStartPosZ() + (entity.getTargetPosZ() - entity.getStartPosZ()) * distPercent;
            moveEntityToXYZ(entity, newX, newY, newZ);
        }
        else if (entity.isBoundToEntity()) {
            if (entity.getOwner() != null) {
                entityFacingAngle = getEntityHorizontalFacingDeg(entity.getOwner());
                Vector3f lerpVec = getEntityClientPosWithPartialTicks(entity.getOwner(), partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
                moveEntityToPos(entity, lerpVec);
            }
        }
        poseStack.pushPose();

        Quaternionf rotatorZ = new Quaternionf();
        QuatRotationByAxis(rotatorZ, AngleToRadians(entity.getRotationZ()), 0f, 0f, 1.0f);

        Quaternionf rotatorY = new Quaternionf();
//        rotatorY.rotationAxis(AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
//        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        QuatRotationByAxis(rotatorY, AngleToRadians(entityFacingAngle), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorZ);
        //rotatorZ mul rotatorY and rotatorY mul rotatorZ are different, rotatorY mul rotatorZ better

        if (entity.getEntityScale() != 1) {
            rotatorY.scale(entity.getEntityScale());
        }

        poseStack.mulPose(rotatorY);

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, entityYaw, partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, entityYaw, partialTick, packedLight);

        if (entity.getLifetime() < 8) {
            this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);
        }
        else {
            this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);
        }

        poseStack.popPose();
    }


    @Override
    public Color getRenderColor(SwordSlashWhiteEntity animatable, float partialTick, int packedLight) {
//        return Color.ofRGBA(255, 255, 255, (int) (1.0f * animatable.getLifetime() / 12 * 240) + 15);
//        return Color.WHITE;
//        if (animatable.getLifetime() >= 8) {
//            return Color.ofRGBA(255, 255, 255, (12 - animatable.getLifetime()) * 60 + 15);
//        }
//        else {
//            return Color.ofRGBA(255, 255, 255, animatable.getLifetime() * 30 + 15);
//        }
        if (animatable.getLifetime() >= 8) {
            return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), (12 - animatable.getLifetime()) * 60 + 15);
        }
        else {
            return Color.ofRGBA(animatable.getColorRed(), animatable.getColorGreen(), animatable.getColorBlue(), animatable.getLifetime() * 30 + 15);
        }
    }
}
