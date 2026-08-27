package net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import software.bernie.geckolib.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityHorizontalFacingDeg;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;

public class SwordSlashPurpleRenderer extends GeoEntityRenderer<SwordSlashPurpleEntity> {
    public SwordSlashPurpleRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SwordSlashPurpleModel());
        this.shadowOpacity = 0.0f;
    }

    @Override
    public Identifier getTextureLocation(SwordSlashPurpleEntity instance) {
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
    public RenderLayer getRenderType(SwordSlashPurpleEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EntityRenderState entityRenderState, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        SwordSlashPurpleEntity entity = this.animatable;
        poseStack.push();

        Quaternionf rotatorZ = new Quaternionf();
        QuatRotationByAxis(rotatorZ, AngleToRadians(entity.getRotationZ()), 0f, 0f, 1.0f);

        Quaternionf rotatorY = new Quaternionf();
//        rotatorY.rotationAxis(AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        QuatRotationByAxis(rotatorY, AngleToRadians(getEntityHorizontalFacingDeg(entity)), 0f, -1.0f, 0f);
        rotatorY.mul(rotatorZ);
        //rotatorZ mul rotatorY and rotatorY mul rotatorZ are different, rotatorY mul rotatorZ better

        poseStack.multiply(rotatorY);

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, this.partialTick, packedLight);

//        this.defaultRender(poseStack, entity, bufferSource, RenderLayer.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer)null, this.partialTick, packedLight);

        if (entity.getLifetime() < 8) {
            this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, this.partialTick, packedLight);
        }
        else {
            this.defaultRender(poseStack, entity, bufferSource, RenderLayer.getEntityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, this.partialTick, packedLight);
        }

        poseStack.pop();
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
