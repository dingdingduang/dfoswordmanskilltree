package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.humanoidlayer.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;

public class CommonModel<T extends LivingEntity> extends HumanoidModel<T> {
    private ModelPart tempModelBone;

    public CommonModel(ModelPart pRoot) {
        super(pRoot);
    }

    @Override
    @Nonnull
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    @Nonnull
    protected Iterable<ModelPart> bodyParts() {
//        return ImmutableList.of(this.body);

        return ImmutableList.of(this.leftArm);
//        return ImmutableList.of();
    }

    public float getGlint() {
        return 0;
    }

    public float getScale() {
        return 1f;
    }

    public ModelLayerLocation getModelTexture() {
        return null;
    }

    public ModelLayerLocation getModelTextureByArrayIndex(int i) {
        return null;
    }

    public ModelPart getTempModelBone() {
        return this.tempModelBone;
    }

    public void setTempModelBone(ModelPart modelBone) {
        this.tempModelBone = modelBone;
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
//        body.skipDraw = true;

        leftArm.skipDraw = true;
//        rightArm.skipDraw = true;
//        head.skipDraw = true;
//        rightLeg.skipDraw = true;
//        leftLeg.skipDraw = true;
//        hat.skipDraw = true;

        super.renderToBuffer(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }
}
