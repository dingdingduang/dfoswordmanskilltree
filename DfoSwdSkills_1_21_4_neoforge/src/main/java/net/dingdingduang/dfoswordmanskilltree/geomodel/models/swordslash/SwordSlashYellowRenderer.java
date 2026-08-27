package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class SwordSlashYellowRenderer extends GeoEntityRenderer<SwordSlashYellowEntity> {
    public SwordSlashYellowRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SwordSlashYellowModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(SwordSlashYellowEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID("textures/entity/slash/yellow/separateframe/0_"+instance.getRandomModelTexture()+"_"+(8 - instance.getLifetime())+DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/yellow/0_0.png);
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/yellow/separateframe/0_"
////        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/yellow/new/separateframe/0_"
//                +instance.getRandomModelTexture()+"_"+(8 - instance.getLifetime())
//                +".png");
    }

    @Override
    public RenderType getRenderType(SwordSlashYellowEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EntityRenderState entityRenderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        SwordSlashYellowEntity entity = this.animatable;
        poseStack.pushPose();

        Quaternionf rotatorZ = new Quaternionf();
//        rotatorZ.rotationAxis(entity.getRotationZ(), 0f, 0f, 1.0f);
        QuatRotationByAxis(rotatorZ, AngleToRadians(entity.getRotationZ()), 0f, 0f, 1.0f);

        Quaternionf rotatorY = new Quaternionf();
//        rotatorY.rotationAxis(AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorZ);
        //rotatorZ mul rotatorY and rotatorY mul rotatorZ are different, rotatorY mul rotatorZ better

        poseStack.mulPose(rotatorY);

        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, this.partialTick, packedLight);

        poseStack.popPose();
    }

}
