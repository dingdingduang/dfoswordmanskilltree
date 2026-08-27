package net.dingdingduang.dfoswordmanskilltree.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.handeff.HandEffEntity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods;

import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL12;

import java.util.HashMap;
import java.util.function.Function;

import static net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoGeneralMethods.getGameRendererMainCamera;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.getEntityClientPosWithPartialTicks;
import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.*;

public class GeoTrailPlaneParticle extends TextureSheetParticle {
    private static final ResourceLocation PARTICLE_RS = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, "textures/particle/dfo_geo_trail_plane_particle.png");
    private static final TextureManager MC_TEX_MGR = getMinecraftInstance().getTextureManager();

    private int StartFadingTicks;
    private float InitialAlpha;
    private float TrailingLength;

    private int index;
    private HashMap<Integer, GeoTrailPlaneParticle> IndexMap;
    private LivingEntity owner;

    private Matrix4f previousRotationMatrix4f;
    private Matrix4f currentRotationMatrix4f;

    protected GeoTrailPlaneParticle(ClientLevel pLevel, double pX, double pY, double pZ, HandEffEntity handEffEntity, SpriteSet spriteSet) {
        super(pLevel, pX, pY, pZ, 0, 0, 0);

        this.TrailingLength = handEffEntity.getTrailingParticleLength();
        this.StartFadingTicks = handEffEntity.getTrailingParticleStartFadingAtRemainingTicks();
        this.rCol = handEffEntity.getTrailingParticleColorRed();
        this.gCol = handEffEntity.getTrailingParticleColorGreen();
        this.bCol = handEffEntity.getTrailingParticleColorBlue();
        this.alpha = handEffEntity.getTrailingParticleColorAlpha();
        this.InitialAlpha = handEffEntity.getTrailingParticleColorAlpha();

        this.previousRotationMatrix4f = handEffEntity.getPreviousRotationMatrix4f();
        this.currentRotationMatrix4f = handEffEntity.getCurrentRotationMatrix4f();

        this.setSize(1, 1);
        this.setSpriteFromAge(spriteSet);

        this.hasPhysics = false;

        this.index = handEffEntity.getGeoParticleIndex();
        handEffEntity.setGeoParticleIndex(this.index + 1);
        this.IndexMap = handEffEntity.getGeoTrailPlaneParticleHashMap();
        this.IndexMap.put(this.index, this);

        if (handEffEntity.getOwner() instanceof LivingEntity livingEntity) {
            this.owner = livingEntity;
        }
        else {
            this.owner = null;
        }

        //bind texture
        RenderSystem.bindTexture(MC_TEX_MGR.getTexture(PARTICLE_RS).getId());

        RenderSystem.texParameter(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        RenderSystem.texParameter(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
    }

    public Matrix4f getCurrentRotationMatrix4f() { return this.currentRotationMatrix4f; }

//    public float getCorrectX() { return (float) (this.x - getGameRendererMainCamera().getPosition().x()); }
//    public float getCorrectY() { return (float) (this.y - getGameRendererMainCamera().getPosition().y()); }
//    public float getCorrectZ() { return (float) (this.z - getGameRendererMainCamera().getPosition().z()); }
    public float getCorrectX(double camX) { return (float) (this.x - camX); }
    public float getCorrectY(double camY) { return (float) (this.y - camY); }
    public float getCorrectZ(double camZ) { return (float) (this.z - camZ); }

    public static String getVecDetails(Vector3f a) {
        return String.format("x: %.2f, y: %.2f, z: %.2f", a.x(), a.y(), a.z());
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        int previousTrailingParticleIndex = this.index - 1;
        if (previousTrailingParticleIndex < 0) { return; }

        var CameraPos = pRenderInfo.getPosition();

//        float currentX = (float) (Mth.lerp(pPartialTicks, this.xo, this.x) - CameraPos.x());
//        float currentY = (float) (Mth.lerp(pPartialTicks, this.yo, this.y) - CameraPos.y());
//        float currentZ = (float) (Mth.lerp(pPartialTicks, this.zo, this.z) - CameraPos.z());

//        float currentX = Vector3fMethods.lerp1D(pPartialTicks, (float) this.xo, (float) this.x) - (float) CameraPos.x();
//        float currentY = Vector3fMethods.lerp1D(pPartialTicks, (float) this.yo, (float) this.y) - (float) CameraPos.y();
//        float currentZ = Vector3fMethods.lerp1D(pPartialTicks, (float) this.zo, (float) this.z) - (float) CameraPos.z();

        float currentX = (float) (this.x - CameraPos.x());
        float currentY = (float) (this.y - CameraPos.y());
        float currentZ = (float) (this.z - CameraPos.z());

        Vector3f[] Vertices = new Vector3f[]{
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, -this.TrailingLength),
                new Vector3f(0, 0, -this.TrailingLength),
                new Vector3f(0, 0, 0)
        };

//        float QuadSize = this.getQuadSize(pPartialTicks);

        Vector3f vertex;

        GeoTrailPlaneParticle previousParticle = this.IndexMap.get(this.index - 1);

        float previousX = previousParticle.getCorrectX(CameraPos.x());
        float previousY = previousParticle.getCorrectY(CameraPos.y());
        float previousZ = previousParticle.getCorrectZ(CameraPos.z());

        vertex = previousParticle.getCurrentRotationMatrix4f().transformPosition(Vertices[0]);
        vertex.add(previousX, previousY, previousZ);
        Vertices[0] = vertex;

        vertex = previousParticle.getCurrentRotationMatrix4f().transformPosition(Vertices[1]);
        vertex.add(previousX, previousY, previousZ);
        Vertices[1] = vertex;

        vertex = this.currentRotationMatrix4f.transformPosition(Vertices[2]);
        vertex.add(currentX, currentY, currentZ);
        Vertices[2] = vertex;

        vertex = this.currentRotationMatrix4f.transformPosition(Vertices[3]);
        vertex.add(currentX, currentY, currentZ);
        Vertices[3] = vertex;

//        float U0 = this.getU0();
//        float U1 = this.getU1();
//        float V0 = this.getV0();
//        float V1 = this.getV1();

        int lightColor = this.getLightColor(pPartialTicks);

//        pBuffer.vertex(Vertices[0].x(), Vertices[0].y(), Vertices[0].z()).uv(U1, V1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
//        pBuffer.vertex(Vertices[1].x(), Vertices[1].y(), Vertices[1].z()).uv(U1, V0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
//        pBuffer.vertex(Vertices[2].x(), Vertices[2].y(), Vertices[2].z()).uv(U0, V0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
//        pBuffer.vertex(Vertices[3].x(), Vertices[3].y(), Vertices[3].z()).uv(U0, V1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();

        pBuffer.vertex(Vertices[0].x(), Vertices[0].y(), Vertices[0].z()).uv(0, 1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
        pBuffer.vertex(Vertices[1].x(), Vertices[1].y(), Vertices[1].z()).uv(0, 1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
        pBuffer.vertex(Vertices[2].x(), Vertices[2].y(), Vertices[2].z()).uv(0, 1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
        pBuffer.vertex(Vertices[3].x(), Vertices[3].y(), Vertices[3].z()).uv(0, 1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
//        pBuffer.vertex(Vertices[2].x(), Vertices[2].y(), Vertices[2].z()).uv(0, 1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
//        pBuffer.vertex(Vertices[1].x(), Vertices[1].y(), Vertices[1].z()).uv(0, 1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
//        pBuffer.vertex(Vertices[0].x(), Vertices[0].y(), Vertices[0].z()).uv(0, 1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(lightColor).endVertex();
    }

    public void tick() {
//        super.tick();
//        this.xo = this.x;
//        this.yo = this.y;
//        this.zo = this.z;

        int remainingTicks = this.lifetime - this.age++;
        if (remainingTicks <= 0) {
            this.remove();
        }
        else if (remainingTicks < this.StartFadingTicks) {
            this.alpha = remainingTicks * this.InitialAlpha / this.StartFadingTicks;
        }
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return PARTICLE_RENDER_TYPE_PROVIDER.apply(PARTICLE_RS);
    }

    public static class GeoTrailPlaneParticleSprite implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public GeoTrailPlaneParticleSprite(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double posX, double posY, double posZ, double unknown0, double unknown1, double DoubleEntityID) {
            int entityID = (int) DoubleEntityID;

            Entity tempEntity = EntityMethods.getEntityByIntID(pLevel, entityID);

            if (tempEntity instanceof HandEffEntity handEffEntity) {
                if (handEffEntity.getPreviousRotationMatrix4f() == null) { return null; }
                return new GeoTrailPlaneParticle(pLevel, posX, posY, posZ, handEffEntity, this.sprites);
            }

            return null;
        }
    }

    //[!] From Epicfight's Source Code
    public static final Function<ResourceLocation, ParticleRenderType> PARTICLE_RENDER_TYPE_PROVIDER = Util.memoize((texturePath) -> new ParticleRenderType() {
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.enableBlend();
            RenderSystem.disableCull();

            getMinecraftInstance().gameRenderer.lightTexture().turnOnLightLayer();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            RenderSystem.setShader(GameRenderer::getParticleShader);
            RenderSystem.setShaderTexture(0, texturePath);

            getMinecraftInstance().gameRenderer.lightTexture().turnOnLightLayer();

            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSorting(VertexSorting.DISTANCE_TO_ORIGIN);
            tesselator.end();

            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();

            getMinecraftInstance().gameRenderer.lightTexture().turnOffLightLayer();
        }

        @Override
        public String toString() {
            return "DFOSWORDMANSKILLTREE:TRAIL";
        }
    });
}
