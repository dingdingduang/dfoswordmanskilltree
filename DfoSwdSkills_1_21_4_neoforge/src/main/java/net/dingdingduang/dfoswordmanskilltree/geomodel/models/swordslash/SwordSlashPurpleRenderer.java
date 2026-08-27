package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

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
import software.bernie.geckolib.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class SwordSlashPurpleRenderer extends GeoEntityRenderer<SwordSlashPurpleEntity> {
    public SwordSlashPurpleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SwordSlashPurpleModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(SwordSlashPurpleEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID("textures/entity/slash/purple/sworddance1/0_"+instance.getRandomModelTexture()+"_"+(12 - instance.getLifetime())+DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/purple/0_0.png);
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/purple/sworddance1/0_"
//                +instance.getRandomModelTexture()+"_"+(12 - instance.getLifetime())
//                +".png");
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/white/0_"
//                +instance.getRandomModelTexture()+"_"+(12 - instance.getLifetime())
//                +".png");
    }

    @Override
    public RenderType getRenderType(SwordSlashPurpleEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EntityRenderState entityRenderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        SwordSlashPurpleEntity entity = this.animatable;
        poseStack.pushPose();

        Quaternionf rotatorZ = new Quaternionf();
        QuatRotationByAxis(rotatorZ, AngleToRadians(entity.getRotationZ()), 0f, 0f, 1.0f);

        Quaternionf rotatorY = new Quaternionf();
//        rotatorY.rotationAxis(AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorZ);
        //rotatorZ mul rotatorY and rotatorY mul rotatorZ are different, rotatorY mul rotatorZ better

        poseStack.mulPose(rotatorY);

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, this.partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, this.partialTick, packedLight);

        if (entity.getLifetime() < 8) {
            this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, this.partialTick, packedLight);
        }
        else {
            this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, this.partialTick, packedLight);
        }

        poseStack.popPose();
    }


    @Override
    public Color getRenderColor(SwordSlashPurpleEntity animatable, float partialTick, int packedLight) {
//        return Color.ofRGBA(255, 255, 255, (int) (1.0f * animatable.getLifetime() / 12 * 240) + 15);
//        return Color.WHITE;
//        if (animatable.getLifetime() >= 8) {
//            return Color.ofRGBA(255, 255, 255, (int) ((1.0f * (12 - animatable.getLifetime()) * 0.25f) * 240) + 15);
//        }
//        else {
//            return Color.ofRGBA(255, 255, 255, (int) (1.0f * animatable.getLifetime() / 8 * 240) + 15);
//        }
        if (animatable.getLifetime() >= 8) {
            return Color.ofRGBA(255, 255, 255, (12 - animatable.getLifetime()) * 60 + 15);
        }
        else {
            return Color.ofRGBA(255, 255, 255, animatable.getLifetime() * 30 + 15);
        }
    }
}
