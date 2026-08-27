package net.dingdingduang.dfoswordmanskilltree.entity.renderhelper;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.NullRendererResourceLoc;

@OnlyIn(Dist.CLIENT)
public class NullRenderer<T extends Entity> extends EntityRenderer<T> {

    public NullRenderer(EntityRendererProvider.Context entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public boolean shouldRender(T pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return NullRendererResourceLoc;
    }
}
