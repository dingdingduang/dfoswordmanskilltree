package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class SwordSlashYellowRenderer extends GeoEntityRenderer<SwordSlashYellowEntity> {
    public SwordSlashYellowRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SwordSlashYellowModel());
        this.shadowOpacity = 0.0f;
    }

    @Override
    public Identifier getTextureLocation(SwordSlashYellowEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID("textures/entity/slash/yellow/separateframe/0_"+instance.getRandomModelTexture()+"_"+(8 - instance.getLifetime())+DfoSwordmanSkillTreeConstants.PNG_POSTFIX);
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/yellow/0_0.png);
//        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/yellow/separateframe/0_"
////        return new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/entity/slash/yellow/new/separateframe/0_"
//                +instance.getRandomModelTexture()+"_"+(8 - instance.getLifetime())
//                +".png");
    }

    @Override
    public RenderLayer getRenderType(SwordSlashYellowEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(SwordSlashYellowEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        this.animatable = entity;
        poseStack.push();

        Quaternionf rotatorZ = new Quaternionf();
//        rotatorZ.rotationAxis(entity.getRotationZ(), 0f, 0f, 1.0f);
        QuatRotationByAxis(rotatorZ, AngleToRadians(entity.getRotationZ()), 0f, 0f, 1.0f);

        Quaternionf rotatorY = new Quaternionf();
//        rotatorY.rotationAxis(AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorZ);
        //rotatorZ mul rotatorY and rotatorY mul rotatorZ are different, rotatorY mul rotatorZ better

        poseStack.multiply(rotatorY);

        this.defaultRender(poseStack, entity, bufferSource, null, (VertexConsumer) null, entityYaw, partialTick, packedLight);

        poseStack.pop();
    }

}
