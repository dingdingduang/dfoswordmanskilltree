package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.simplemcsword;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;
import net.dingdingduang.dfoswordmanskilltree.globalvalues.GlobalClientMaps;
import net.dingdingduang.dfoswordmanskilltree.util.MathMethods;
import net.dingdingduang.dfoswordmanskilltree.util.Vector3fMethods;
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

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.moveEntityToXYZ;
import static net.dingdingduang.dfoswordmanskilltree.util.MathMethods.AngleToRadians;
import static net.dingdingduang.dfoswordmanskilltree.util.QuaternionHelper.QuatRotationByAxis;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMinecraftInstance;

public class SimpleMCSwordCustomRenderer extends GeoEntityRenderer<SimpleMCSwordCustomEntity> {
    public SimpleMCSwordCustomRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SimpleMCSwordCustomModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(SimpleMCSwordCustomEntity instance) {
        return GlobalClientMaps.getReusableResourceByStringID(instance.getTextureName());
//        return GeneralMethods.getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, instance.getTextureName());
    }

    @Override
    public RenderType getRenderType(SimpleMCSwordCustomEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(EntityRenderState entityRenderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        SimpleMCSwordCustomEntity entity = this.animatable;
        //client move
        if (entity.hasTargetLocXYZ() && entity.isBoundToEntity()) {
            float lerpX = Vector3fMethods.lerp1D(this.partialTick, entity.getStartPosX(), entity.getTargetPosX());
            float lerpY = Vector3fMethods.lerp1D(this.partialTick, entity.getStartPosY(), entity.getTargetPosY());
            float lerpZ = Vector3fMethods.lerp1D(this.partialTick, entity.getStartPosZ(), entity.getTargetPosZ());
            moveEntityToXYZ(entity, lerpX, lerpY, lerpZ);
        }
        else if (entity.hasTargetLocXYZ()) {
            float distPercent = (entity.getMaxLifeTime() - entity.getLifetime() + this.partialTick) / entity.getMaxLifeTime();
            float newX = entity.getStartPosX() + (entity.getTargetPosX() - entity.getStartPosX()) * distPercent;
            float newY = entity.getStartPosY() + (entity.getTargetPosY() - entity.getStartPosY()) * distPercent;
            float newZ = entity.getStartPosZ() + (entity.getTargetPosZ() - entity.getStartPosZ()) * distPercent;
            EntityMethods.moveEntityToXYZ(entity, newX, newY, newZ);
        }
        else if (entity.isBoundToEntity()) {
            if (entity.getOwner() != null) {
                Vector3f lerpVec = EntityMethods.getEntityClientPosWithPartialTicks(entity.getOwner(), partialTick).add(entity.getTargetPosX(), entity.getTargetPosY(), entity.getTargetPosZ());
                EntityMethods.moveEntityToPos(entity, lerpVec);
            }
        }
        poseStack.pushPose();

//        this.defaultRender(poseStack, entity, bufferSource, (RenderType) null, (VertexConsumer) null, this.partialTick, packedLight);
        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucent(getTextureLocation(entity)), (VertexConsumer) null, this.partialTick, packedLight);
//        this.defaultRender(poseStack, entity, bufferSource, RenderType.entityTranslucentEmissive(getTextureLocation(entity)), (VertexConsumer) null, entityYaw, partialTick, packedLight);

        poseStack.popPose();
    }


    @Override
    public Color getRenderColor(SimpleMCSwordCustomEntity animatable, float partialTick, int packedLight) {
        return Color.ofRGBA(255, 255, 255, (int) ( animatable.getLifetime() * (240f / animatable.getMaxLifeTime()) ) + 15);
    }
}
