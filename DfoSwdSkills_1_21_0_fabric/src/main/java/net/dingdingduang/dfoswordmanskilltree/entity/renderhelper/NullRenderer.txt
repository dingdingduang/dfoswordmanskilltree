package net.dingdingduang.dfoswordmanskilltree.entity.renderhelper;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import static net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants.NullRendererResourceLoc;

public class NullRenderer<T extends Entity> extends EntityRenderer<T> {

    public NullRenderer(EntityRendererFactory.Context entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public boolean shouldRender(T pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return false;
    }

    @Override
    public Identifier getTexture(T entity) {
        return NullRendererResourceLoc;
    }
}
