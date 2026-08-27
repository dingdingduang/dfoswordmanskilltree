package net.dingdingduang.dfoswordmanskilltree.geomodel;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar.ChannelingBarEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.channelingbar.ChannelingIconEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.EffBuffEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.buff.alternative.EffBuffAltEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.groundquake.EffGroundQuakeEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.hiteff.HitEffEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.simplemcsword.SimpleMCSwordCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.stab.EffStabEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.strikefromair.EffStrikeFromAirEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.draw_sword_qi.DrawSwordQiEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.rain_of_swords.FancySwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.slaughterscape.SlaughterscapeSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_block_eff.SwdTempestBlockEffEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.blademaster.tempest_sword.BMTempestSwordEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swdfadingclone.SwdFadingCloneCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.target.TargetZoneSelectionEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.SwordSlashPurpleEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.SwordSlashWhiteEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.SwordSlashYellowEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.custom.SwordSlashCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.swordslash.customtotalframesonly.SwordSlashCustomTotalFrameOnlyEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodel.TwoDEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.TwoDAnimatedTex128Entity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.TwoDAnimatedTex256Entity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.TwoDAnimatedTex64Entity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.custom.TwoDAnimated256TexCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimated.flat.TwoDAnimated256FlatTexCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.TwoDAnimatedTex128ManipulateYEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.TwoDAnimatedTex256ManipulateYEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.TwoDAnimatedTex64ManipulateYEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.movingpos.TwoDAnimatedTex256ManipulateYCustomEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.twodmodelanimatedyrot.trail.TwoDAnimatedTexRGB256Entity;
import net.dingdingduang.dfoswordmanskilltree.globalmethods.DfoSkillTreeItemMethods;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;

import static net.dingdingduang.dfoswordmanskilltree.entity.DfoGeneralEntitySpawnMethods.MCLoadEntityRecursive;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class GeoModelSpawnMethods {
    private static HashMap<String, CompoundTag> EntityCompoundTagCache = new HashMap<String, CompoundTag>();

    public static CompoundTag getEntityIDTag(String entityID) {
        CompoundTag nbtEntityTag;
        if (!EntityCompoundTagCache.containsKey(entityID)) {
            nbtEntityTag = new CompoundTag();
            nbtEntityTag.putString("id", entityID);
        }
        else {
            nbtEntityTag = EntityCompoundTagCache.get(entityID);
        }
        return nbtEntityTag;
    }

    public static SwordSlashYellowEntity GenerateYellowSlash(Entity owner, float horizontalRotation, float rotationZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.SWD_SLASH_YELLOW;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwordSlashYellowEntity Entity000 = (SwordSlashYellowEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner), getEntityY(owner)+1f, getEntityZ(owner), horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationZ(rotationZ);
        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwordSlashPurpleEntity GeneratePurpleSlash(Entity owner, float horizontalRotation, float rotationZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.SWD_SLASH_PURPLE;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwordSlashPurpleEntity Entity000 = (SwordSlashPurpleEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner), getEntityY(owner)+1f, getEntityZ(owner), horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationZ(rotationZ);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwordSlashWhiteEntity GenerateWhiteSlash(Entity owner, float horizontalRotation, float rotationZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.SWD_SLASH_WHITE;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwordSlashWhiteEntity Entity000 = (SwordSlashWhiteEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner), getEntityY(owner)+1f, getEntityZ(owner), horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationZ(rotationZ);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwordSlashWhiteEntity GenerateRGBSlash(Entity owner, float horizontalRotation, float rotationZ, float scale, int cRed, int cGreen, int cBlue, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.SWD_SLASH_WHITE;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwordSlashWhiteEntity Entity000 = (SwordSlashWhiteEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner), getEntityY(owner)+1f, getEntityZ(owner), horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationZ(rotationZ);
        Entity000.setColor(cRed, cGreen, cBlue);
        Entity000.setEntityScale(scale);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwordSlashWhiteEntity GenerateRGBSlashBoundToEntity(Entity owner, float horizontalRotation, float rotationZ, float scale, int cRed, int cGreen, int cBlue, float additionalX, float additionalY, float additionalZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.SWD_SLASH_WHITE;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwordSlashWhiteEntity Entity000 = (SwordSlashWhiteEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner), getEntityY(owner), getEntityZ(owner), horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationZ(rotationZ);
        Entity000.setColor(cRed, cGreen, cBlue);
        Entity000.setEntityScale(scale);
        Entity000.setOwner(owner);
        Entity000.setIsBoundToEntity(true);
        Entity000.setTargetPosX(additionalX);
        Entity000.setTargetPosY(additionalY);
        Entity000.setTargetPosZ(additionalZ);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwordSlashWhiteEntity GenerateRGBSlashWithTargetLoc(Entity owner, float horizontalRotation, float rotationZ, float scale, int cRed, int cGreen, int cBlue, float addX, float addY, float addZ, String AnimationName, float vx, float vy, float vz, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.SWD_SLASH_WHITE;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwordSlashWhiteEntity Entity000 = (SwordSlashWhiteEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationZ(rotationZ);
        Entity000.setColor(cRed, cGreen, cBlue);
        Entity000.setEntityScale(scale);
        Entity000.setAnimationName(AnimationName);
        Entity000.setTargetLoc(getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, vx, vy, vz, true);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwordSlashCustomEntity GenerateCustomSlashWithTargetLoc(Entity owner, float horizontalRotation, float rotationZ, float scale,
                                                                          int cRed, int cGreen, int cBlue, float x, float y, float z, int maxLifetime,
                                                                          String TexturePath, int StartTotalFrame, int LoopTotalFrame, int EndTotalFrame, boolean isStartFading, boolean isEndFading,
                                                                          String AnimationName, boolean isBoundToEntity, boolean hasTargetLoc, float vx, float vy, float vz, String ParticleName, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.SWD_FANCY_SLASH_CUSTOM;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwordSlashCustomEntity Entity000 = (SwordSlashCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, x, y, z, horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationZ(rotationZ);
        Entity000.setColor(cRed, cGreen, cBlue);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, LoopTotalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setAnimationName(AnimationName);
        Entity000.setOwner(owner);
        Entity000.setIsBoundToEntity(isBoundToEntity);
        Entity000.setParticleName(ParticleName);
        if (hasTargetLoc) {
            Entity000.setTargetLoc(x, y, z, vx, vy, vz, true);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwordSlashCustomEntity GenerateCustomSlash(Entity owner, float horizontalRotation, float rotationZ, float scale,
                                                             int cRed, int cGreen, int cBlue, float x, float y, float z, int maxLifetime,
                                                             String TexturePath, int StartTotalFrame, int LoopTotalFrame, int EndTotalFrame, boolean isStartFading, boolean isEndFading, String AnimationName, String ParticleName, boolean addEntityToWorldRightAway) {
        return GenerateCustomSlashWithTargetLoc(owner, horizontalRotation, rotationZ, scale, cRed, cGreen, cBlue, x, y, z, maxLifetime, TexturePath, StartTotalFrame, LoopTotalFrame, EndTotalFrame, isStartFading, isEndFading, AnimationName, false, false, 0f, 0f, 0f, ParticleName, addEntityToWorldRightAway);
    }

    public static SwordSlashCustomEntity GenerateCustomSlashBoundToEntity(Entity owner, float horizontalRotation, float rotationZ, float scale,
                                                             int cRed, int cGreen, int cBlue, float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                             String TexturePath, int StartTotalFrame, int LoopTotalFrame, int EndTotalFrame, boolean isStartFading, boolean isEndFading, String AnimationName, String ParticleName, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.SWD_FANCY_SLASH_CUSTOM;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwordSlashCustomEntity Entity000 = (SwordSlashCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner), getEntityY(owner), getEntityZ(owner), horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationZ(rotationZ);
        Entity000.setColor(cRed, cGreen, cBlue);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, LoopTotalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setAnimationName(AnimationName);
        Entity000.setOwner(owner);
        Entity000.setIsBoundToEntity(true);
        Entity000.setTargetPosX(additionalX);
        Entity000.setTargetPosY(additionalY);
        Entity000.setTargetPosZ(additionalZ);
        Entity000.setParticleName(ParticleName);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }
    //


    public static SwordSlashCustomTotalFrameOnlyEntity GenerateCustomSlashTotalFrameWithTargetLoc(Entity owner, float horizontalRotation, float verticalRotation, float rotationZ, float scale,
                                                                          int cRed, int cGreen, int cBlue, float x, float y, float z, int maxLifetime,
                                                                          String TexturePath, int StartTotalFrame, int EndTotalFrame, int totalFrame, boolean isStartFading, boolean isEndFading,
                                                                          String AnimationName, boolean isBoundToEntity, boolean hasTargetLoc, float vx, float vy, float vz, String ParticleName, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.SWD_FANCY_SLASH_CUSTOM_TOTAL_FRAME_ONLY;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwordSlashCustomTotalFrameOnlyEntity Entity000 = (SwordSlashCustomTotalFrameOnlyEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, x, y, z, horizontalRotation, verticalRotation);
            return tempEntity;
        });
        Entity000.setRotationZ(rotationZ);
        Entity000.setColor(cRed, cGreen, cBlue);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, EndTotalFrame, totalFrame, isStartFading, isEndFading);
        Entity000.setAnimationName(AnimationName);
        Entity000.setOwner(owner);
        Entity000.setIsBoundToEntity(isBoundToEntity);
        Entity000.setParticleName(ParticleName);
        if (hasTargetLoc) {
            Entity000.setTargetLoc(x, y, z, vx, vy, vz, true);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwordSlashCustomTotalFrameOnlyEntity GenerateCustomSlashTotalFrameBoundToEntity(Entity owner, float horizontalRotation, float verticalRotation, float rotationZ, boolean isAlwaysFaceToOwnerHorizontalFacingAngle, float scale,
                                                                                                  int cRed, int cGreen, int cBlue, float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                                                                  String TexturePath, int StartTotalFrame, int EndTotalFrame, int totalFrame, boolean isStartFading, boolean isEndFading, String AnimationName, String ParticleName, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.SWD_FANCY_SLASH_CUSTOM_TOTAL_FRAME_ONLY;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwordSlashCustomTotalFrameOnlyEntity Entity000 = (SwordSlashCustomTotalFrameOnlyEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner), getEntityY(owner), getEntityZ(owner), horizontalRotation, verticalRotation);
            return tempEntity;
        });
        Entity000.setRotationZ(rotationZ);
        Entity000.setAlwaysFaceToOwnerHorizontalFacingAngle(isAlwaysFaceToOwnerHorizontalFacingAngle);
        Entity000.setColor(cRed, cGreen, cBlue);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, EndTotalFrame, totalFrame, isStartFading, isEndFading);
        Entity000.setAnimationName(AnimationName);
        Entity000.setOwner(owner);
        Entity000.setIsBoundToEntity(true);
        Entity000.setTargetPosX(additionalX);
        Entity000.setTargetPosY(additionalY);
        Entity000.setTargetPosZ(additionalZ);
        Entity000.setParticleName(ParticleName);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }


    //==============================
    //2D Tex
    //==============================
    public static TwoDAnimatedTexRGB256Entity Generate2DRGBImageEntity(Entity owner, float horizontalRotation, float scale,
                                                                       float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                                       int cRed, int cGreen, int cBlue,
                                                                       boolean hasTargetLoc, float x, float y, float z, float vx, float vy, float vz,
                                                                       String TexturePath, String AnimationName, boolean isBoundToEntity, int StartTotalFrame, int EndTotalFrame, int totalFrame, boolean isStartFading, boolean isEndFading,
                                                                       boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_256PX_COLOR_CUSTOM_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDAnimatedTexRGB256Entity Entity000 = (TwoDAnimatedTexRGB256Entity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+additionalX, getEntityY(owner)+additionalY, getEntityZ(owner)+additionalZ, horizontalRotation, 0.0f);
            return tempEntity;
        });

        Entity000.setEntityScale(scale);
        Entity000.setColor(cRed, cGreen, cBlue);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, EndTotalFrame, totalFrame, isStartFading, isEndFading);
        Entity000.setAnimationName(AnimationName);
        Entity000.setOwner(owner);
        if (isBoundToEntity) {
            Entity000.setIsBoundToEntity(isBoundToEntity);
            Entity000.setTargetPosX(additionalX);
            Entity000.setTargetPosY(additionalY);
            Entity000.setTargetPosZ(additionalZ);
        }
        else if (hasTargetLoc) {
            Entity000.setTargetLoc(x, y, z, vx, vy, vz, true);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimatedTexRGB256Entity Generate2DRGBImageEntityBoundToEntity
            (Entity owner, float horizontalRotation, float scale,
             float additionalX, float additionalY, float additionalZ, int maxLifetime,
             int cRed, int cGreen, int cBlue,
             String TexturePath, String AnimationName, int StartTotalFrame, int EndTotalFrame, int totalFrame,
             boolean isStartFading, boolean isEndFading, boolean addEntityToWorldRightAway) {
        return Generate2DRGBImageEntity(owner, horizontalRotation, scale, additionalX, additionalY, additionalZ, maxLifetime, cRed, cGreen, cBlue, false, 0, 0, 0, 0, 0, 0,
                TexturePath, AnimationName, true, StartTotalFrame, EndTotalFrame, totalFrame, isStartFading, isEndFading, addEntityToWorldRightAway);
    }

    public static TwoDAnimatedTexRGB256Entity Generate2DRGBImageEntityFromPos1ToPos2
            (Entity owner, float horizontalRotation, float scale,
             int maxLifetime, float x, float y, float z, float vx, float vy, float vz,
             String TexturePath, String AnimationName, int StartTotalFrame, int EndTotalFrame, int totalFrame,
             int cRed, int cGreen, int cBlue,
             boolean isStartFading, boolean isEndFading, boolean addEntityToWorldRightAway) {
        return Generate2DRGBImageEntity(owner, horizontalRotation, scale, 0, 0, 0, maxLifetime,  cRed, cGreen, cBlue, true, x, y, z, vx, vy, vz,
                TexturePath, AnimationName, false, StartTotalFrame, EndTotalFrame, totalFrame, isStartFading, isEndFading, addEntityToWorldRightAway);
    }

    public static TwoDEntity Generate2DEntity64(Entity owner, String TexturePath, int lifetime, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_64PX_ICON_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDEntity Entity000 = (TwoDEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner), getEntityY(owner)+1f, getEntityZ(owner), 0.0f, 0.0f);
            return tempEntity;
        });

        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(lifetime);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimatedTex64Entity Generate2DAnimatedTexEntity64(Entity owner, String TexturePath, int totalFrame, float addX, float addY, float addZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_ANIMATED_64PX_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDAnimatedTex64Entity Entity000 = (TwoDAnimatedTex64Entity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, 0.0f, 0.0f);
            return tempEntity;
        });

        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(totalFrame);
        Entity000.setMaxLifeTime(totalFrame);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimatedTex64ManipulateYEntity Generate2DAnimatedTexAlternativeEntity64(Entity owner, String TexturePath, int totalFrame, float addX, float addY, float addZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_ANIMATED_64PX_FIXED_YAXIS_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDAnimatedTex64ManipulateYEntity Entity000 = (TwoDAnimatedTex64ManipulateYEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, 0.0f, 0.0f);
            return tempEntity;
        });

        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(totalFrame);
        Entity000.setMaxLifeTime(totalFrame);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimatedTex128Entity Generate2DAnimatedTexEntity128(Entity owner, String TexturePath, int totalFrame, float addX, float addY, float addZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_ANIMATED_128PX_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDAnimatedTex128Entity Entity000 = (TwoDAnimatedTex128Entity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, 0.0f, 0.0f);
            return tempEntity;
        });

        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(totalFrame);
        Entity000.setMaxLifeTime(totalFrame);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimatedTex128ManipulateYEntity Generate2DAnimatedTexAlternativeEntity128(Entity owner, String TexturePath, int totalFrame, float addX, float addY, float addZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_ANIMATED_128PX_FIXED_YAXIS_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDAnimatedTex128ManipulateYEntity Entity000 = (TwoDAnimatedTex128ManipulateYEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, 0.0f, 0.0f);
            return tempEntity;
        });

        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(totalFrame);
        Entity000.setMaxLifeTime(totalFrame);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimatedTex256Entity Generate2DAnimatedTexEntity256(Entity owner, String TexturePath, int totalFrame, float addX, float addY, float addZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_ANIMATED_256PX_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDAnimatedTex256Entity Entity000 = (TwoDAnimatedTex256Entity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, 0.0f, 0.0f);
            return tempEntity;
        });

        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(totalFrame);
        Entity000.setMaxLifeTime(totalFrame);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimatedTex256ManipulateYEntity Generate2DAnimatedTexAlternativeEntity256(Entity owner, String TexturePath, int totalFrame, float addX, float addY, float addZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_ANIMATED_256PX_FIXED_YAXIS_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDAnimatedTex256ManipulateYEntity Entity000 = (TwoDAnimatedTex256ManipulateYEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, 0.0f, 0.0f);
            return tempEntity;
        });

        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(totalFrame);
        Entity000.setMaxLifeTime(totalFrame);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimatedTex256ManipulateYCustomEntity Generate2DAnimatedTexCustomEntityZRotOnly256(Entity owner, float horizontalRotation, boolean isAlwaysFaceToOwnerHorizontalFacingAngle, float scale,
                                                                                                         float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                                                                         boolean hasTargetLoc, float x, float y, float z, float vx, float vy, float vz,
                                                                                                         String TexturePath, String AnimationName, boolean isBoundToEntity, int StartTotalFrame, int EndTotalFrame, int totalFrame, boolean isFrameEvenlyDistributed, boolean isStartFading, boolean isEndFading,
                                                                                                         boolean checkCam, MethodEntityAction serverAction, int serverActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_TOTAL_FRAME_ONLY_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDAnimatedTex256ManipulateYCustomEntity Entity000 = (TwoDAnimatedTex256ManipulateYCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+additionalX, getEntityY(owner)+additionalY, getEntityZ(owner)+additionalZ, horizontalRotation, 0.0f);
            return tempEntity;
        });

        Entity000.setAlwaysFaceToOwnerHorizontalFacingAngle(isAlwaysFaceToOwnerHorizontalFacingAngle);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, EndTotalFrame, totalFrame, isStartFading, isEndFading);
        Entity000.setFrameDistributedEvenly(isFrameEvenlyDistributed);
        Entity000.setOwner(owner);
        Entity000.setAnimationName(AnimationName);
        Entity000.setCheckCamera(checkCam);
        Entity000.setEntityServerAction(serverAction, serverActionTickPeriod);

        if (isBoundToEntity) {
            Entity000.setIsBoundToEntity(isBoundToEntity);
            Entity000.setTargetPosX(additionalX);
            Entity000.setTargetPosY(additionalY);
            Entity000.setTargetPosZ(additionalZ);
        }
        else if (hasTargetLoc) {
            Entity000.setTargetLoc(x, y, z, vx, vy, vz, true);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimatedTex256ManipulateYCustomEntity Generate2DAnimatedTexCustomEntityZRotOnly256(Entity owner, float horizontalRotation, float scale,
                                                                                                         float x, float y, float z, int maxLifetime,
                                                                                                         String TexturePath, String AnimationName, int StartTotalFrame, int EndTotalFrame, int totalFrame, boolean isFrameEvenlyDistributed, boolean isStartFading, boolean isEndFading,
                                                                                                         boolean checkCam, MethodEntityAction serverAction, int serverActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_TOTAL_FRAME_ONLY_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDAnimatedTex256ManipulateYCustomEntity Entity000 = (TwoDAnimatedTex256ManipulateYCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, x, y, z, horizontalRotation, 0.0f);
            return tempEntity;
        });

        Entity000.setAlwaysFaceToOwnerHorizontalFacingAngle(false);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, EndTotalFrame, totalFrame, isStartFading, isEndFading);
        Entity000.setFrameDistributedEvenly(isFrameEvenlyDistributed);
        Entity000.setOwner(owner);
        Entity000.setAnimationName(AnimationName);
        Entity000.setCheckCamera(checkCam);
        Entity000.setIsBoundToEntity(false);
        Entity000.setHasTargetLocXYZBoolean(false);
        Entity000.setEntityServerAction(serverAction, serverActionTickPeriod);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimatedTex256ManipulateYCustomEntity Generate2DAnimatedTexCustomEntityZRotOnly256BoundToEntity
            (Entity owner, float horizontalRotation, boolean isAlwaysFaceToOwnerHorizontalFacingAngle, float scale,
             float additionalX, float additionalY, float additionalZ, int maxLifetime,
             String TexturePath, String AnimationName, int StartTotalFrame, int EndTotalFrame, int totalFrame, boolean isFrameEvenlyDistributed,
             boolean isStartFading, boolean isEndFading, boolean checkCam, MethodEntityAction serverAction, int serverActionTickPeriod, boolean addEntityToWorldRightAway) {
        return Generate2DAnimatedTexCustomEntityZRotOnly256(owner, horizontalRotation, isAlwaysFaceToOwnerHorizontalFacingAngle, scale, additionalX, additionalY, additionalZ, maxLifetime, false, 0, 0, 0, 0, 0, 0,
                TexturePath, AnimationName, true, StartTotalFrame, EndTotalFrame, totalFrame, isFrameEvenlyDistributed, isStartFading, isEndFading, checkCam, serverAction, serverActionTickPeriod, addEntityToWorldRightAway);
    }

    public static TwoDAnimatedTex256ManipulateYCustomEntity Generate2DAnimatedTexCustomEntityZRotOnly256FromPos1ToPos2
            (Entity owner, float horizontalRotation, float scale,
             int maxLifetime, float x, float y, float z, float vx, float vy, float vz,
             String TexturePath, String AnimationName, int StartTotalFrame, int EndTotalFrame, int totalFrame, boolean isFrameEvenlyDistributed,
             boolean isStartFading, boolean isEndFading, boolean checkCam, MethodEntityAction serverAction, int serverActionTickPeriod, boolean addEntityToWorldRightAway) {
        return Generate2DAnimatedTexCustomEntityZRotOnly256(owner, horizontalRotation, false, scale, 0, 0, 0, maxLifetime, true, x, y, z, vx, vy, vz,
                TexturePath, AnimationName, false, StartTotalFrame, EndTotalFrame, totalFrame, isFrameEvenlyDistributed, isStartFading, isEndFading, checkCam, serverAction, serverActionTickPeriod, addEntityToWorldRightAway);
    }

    //======================
    public static TwoDAnimated256TexCustomEntity Generate2DAnimatedTexCustomEntity256(Entity owner, int colorR, int colorG, int colorB, float scale,
                                                                                      float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                                                      boolean hasTargetLoc, float x, float y, float z, float vx, float vy, float vz,
                                                                                      String TexturePath, String AnimationName, String ClientMethodActionName, boolean isBoundToEntity, int StartTotalFrame, int EndTotalFrame, int totalFrame, boolean isStartFading, boolean isEndFading,
                                                                                      boolean isFrameDistributedEvenly, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDAnimated256TexCustomEntity Entity000 = (TwoDAnimated256TexCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+additionalX, getEntityY(owner)+additionalY, getEntityZ(owner)+additionalZ, 0f, 0f);
            return tempEntity;
        });

        Entity000.setColor(colorR, colorG, colorB);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, totalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setOwner(owner);
        Entity000.setAnimationName(AnimationName);
        Entity000.setClientActionName(ClientMethodActionName);
        Entity000.setFrameDistributedEvenly(isFrameDistributedEvenly);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);
        if (isBoundToEntity) {
            Entity000.setIsBoundToEntity(isBoundToEntity);
            Entity000.setTargetPosX(additionalX);
            Entity000.setTargetPosY(additionalY);
            Entity000.setTargetPosZ(additionalZ);
        }
        else if (hasTargetLoc) {
            Entity000.setTargetLoc(x, y, z, vx, vy, vz, true);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimated256TexCustomEntity Generate2DAnimatedTexCustomEntity256BoundToEntity
            (Entity owner, int colorR, int colorG, int colorB, float scale,
             float additionalX, float additionalY, float additionalZ, int maxLifetime,
             String TexturePath, String AnimationName, String ClientMethodActionName, int StartTotalFrame, int EndTotalFrame, int totalFrame,
             boolean isStartFading, boolean isEndFading, boolean isFrameDistributedEvenly, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        return Generate2DAnimatedTexCustomEntity256(owner, colorR, colorG, colorB, scale, additionalX, additionalY, additionalZ, maxLifetime, false, 0, 0, 0, 0, 0, 0,
                TexturePath, AnimationName, ClientMethodActionName, true, StartTotalFrame, EndTotalFrame, totalFrame, isStartFading, isEndFading, isFrameDistributedEvenly, serverEntityAction, serverEntityActionTickPeriod, addEntityToWorldRightAway);
    }

    public static TwoDAnimated256TexCustomEntity Generate2DAnimatedTexCustomEntity256FromPos1ToPos2
            (Entity owner, int colorR, int colorG, int colorB, float scale,
             int maxLifetime, float x, float y, float z, float vx, float vy, float vz,
             String TexturePath, String AnimationName, String ClientMethodActionName, int StartTotalFrame, int EndTotalFrame, int totalFrame,
             boolean isStartFading, boolean isEndFading, boolean isFrameDistributedEvenly, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        return Generate2DAnimatedTexCustomEntity256(owner, colorR, colorG, colorB, scale, 0, 0, 0, maxLifetime, true, x, y, z, vx, vy, vz,
                TexturePath, AnimationName, ClientMethodActionName, false, StartTotalFrame, EndTotalFrame, totalFrame, isStartFading, isEndFading, isFrameDistributedEvenly, serverEntityAction, serverEntityActionTickPeriod,  addEntityToWorldRightAway);
    }

    //======================
    public static TwoDAnimated256FlatTexCustomEntity Generate2DAnimatedTexCustomFlatEntity256(Entity owner, float horizontalFacingAngle, int colorR, int colorG, int colorB, float scale,
                                                                                              float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                                                              boolean hasTargetLoc, float x, float y, float z, float vx, float vy, float vz,
                                                                                              String TexturePath, String AnimationName, String ParticleMethodName, String ClientActionName, float AnimationSpeed, boolean isBoundToEntity, int StartTotalFrame, int EndTotalFrame, int totalFrame, boolean isStartFading, boolean isEndFading,
                                                                                              boolean isFrameDistributedEvenly, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.ENTITY_2D_256PX_CUSTOM_FLAT_NBT_NAME;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TwoDAnimated256FlatTexCustomEntity Entity000 = (TwoDAnimated256FlatTexCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+additionalX, getEntityY(owner)+additionalY, getEntityZ(owner)+additionalZ, horizontalFacingAngle, 0f);
            return tempEntity;
        });

        Entity000.setColor(colorR, colorG, colorB);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, totalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setOwner(owner);
        Entity000.setAnimationName(AnimationName);
        Entity000.setParticleName(ParticleMethodName);
        Entity000.setClientActionName(ClientActionName);
        Entity000.setGeoAnimationSpeedModifier(AnimationSpeed);
        Entity000.setFrameDistributedEvenly(isFrameDistributedEvenly);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);
        if (isBoundToEntity) {
            Entity000.setIsBoundToEntity(isBoundToEntity);
            Entity000.setTargetPosX(additionalX);
            Entity000.setTargetPosY(additionalY);
            Entity000.setTargetPosZ(additionalZ);
        }
        else if (hasTargetLoc) {
            Entity000.setTargetLoc(x, y, z, vx, vy, vz, true);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TwoDAnimated256FlatTexCustomEntity Generate2DAnimatedTexFlatCustomEntity256BoundToEntity
            (Entity owner, float horizontalFacingAngle, int colorR, int colorG, int colorB, float scale,
             float additionalX, float additionalY, float additionalZ, int maxLifetime,
             String TexturePath, String AnimationName, String ParticleMethodName, String ClientActionName, float AnimationSpeed, int StartTotalFrame, int EndTotalFrame, int totalFrame,
             boolean isStartFading, boolean isEndFading, boolean isFrameDistributedEvenly, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        return Generate2DAnimatedTexCustomFlatEntity256(owner, horizontalFacingAngle, colorR, colorG, colorB, scale, additionalX, additionalY, additionalZ, maxLifetime, false, 0, 0, 0, 0, 0, 0,
                TexturePath, AnimationName, ParticleMethodName, ClientActionName, AnimationSpeed, true, StartTotalFrame, EndTotalFrame, totalFrame, isStartFading, isEndFading, isFrameDistributedEvenly, serverEntityAction, serverEntityActionTickPeriod, addEntityToWorldRightAway);
    }

    public static TwoDAnimated256FlatTexCustomEntity Generate2DAnimatedTexFlatCustomEntity256FromPos1ToPos2
            (Entity owner, float horizontalFacingAngle, int colorR, int colorG, int colorB, float scale,
             int maxLifetime, float x, float y, float z, float vx, float vy, float vz,
             String TexturePath, String AnimationName, String ParticleMethodName, String ClientActionName, float AnimationSpeed, int StartTotalFrame, int EndTotalFrame, int totalFrame,
             boolean isStartFading, boolean isEndFading, boolean isFrameDistributedEvenly, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        return Generate2DAnimatedTexCustomFlatEntity256(owner, horizontalFacingAngle, colorR, colorG, colorB, scale, 0, 0, 0, maxLifetime, true, x, y, z, vx, vy, vz,
                TexturePath, AnimationName, ParticleMethodName, ClientActionName, AnimationSpeed, false, StartTotalFrame, EndTotalFrame, totalFrame, isStartFading, isEndFading, isFrameDistributedEvenly, serverEntityAction, serverEntityActionTickPeriod,  addEntityToWorldRightAway);
    }



    //==================================
    //generate eff entity
    public static HitEffEntity Generate2DHitEff(Entity owner, float rotationZdeg, float scale, int maxLifetime,int totalFrame,
                                                float additionalX, float additionalY, float additionalZ,
                                                String TexturePath, boolean isBoundToEntity, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_HIT;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        HitEffEntity Entity000 = (HitEffEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner) + additionalX, getEntityY(owner) + additionalY, getEntityZ(owner) + additionalZ, 0f, 0f);
            return tempEntity;
        });

        Entity000.setRotationDegreeZ(rotationZdeg);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath);
        Entity000.setTotalFrame(totalFrame);
        Entity000.setTargetLoc(additionalX, additionalY, additionalZ);
        Entity000.setOwner(owner);
        Entity000.setIsBoundToEntity(isBoundToEntity);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static EffBuffEntity GenerateEffBuffEntity(Entity owner, String TexturePath, String AnimationName, float scale, int lifeTime, float addX, float addY, float addZ, boolean isBoundToEntity, Entity targetEntity, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.EFF_BUFF;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        EffBuffEntity Entity000 = (EffBuffEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, ExtraMathMethods.randomFloat(360), 0.0f);
            return tempEntity;
        });

        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(lifeTime+Entity000.getRandomDelayTicks());
        Entity000.setMaxLifeTime(lifeTime+Entity000.getRandomDelayTicks());
        Entity000.setAnimationName(AnimationName);
        Entity000.setEntityScale(scale);
        if (isBoundToEntity) {
            Entity000.setBoundToEntity(true, getEntityHashID(targetEntity));
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static EffBuffAltEntity GenerateEffBuffAltEntity(Entity owner, String TexturePath, String AnimationName, boolean isAnimationLooping, float scale, int lifeTime, float addX, float addY, float addZ, boolean isBoundToEntity, Entity targetEntity, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.EFF_BUFF_ALT;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        EffBuffAltEntity Entity000 = (EffBuffAltEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, ExtraMathMethods.randomFloat(360), 0.0f);
            return tempEntity;
        });

        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(lifeTime);
        Entity000.setMaxLifeTime(lifeTime);
        Entity000.setAnimationName(AnimationName);
        Entity000.setEntityScale(scale);
        Entity000.setAnimationLooping(isAnimationLooping);
        if (isBoundToEntity) {
            Entity000.setBoundToEntity(true, getEntityHashID(targetEntity));
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static EffStabEntity GenerateEffStabEntity(Entity owner, float horizontalRotation, float rotationX, String TexturePath, String AnimationName, float scale, int totalFrame, boolean isBoundToEntity, float addX, float addY, float addZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.EFF_STAB;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        EffStabEntity Entity000 = (EffStabEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, horizontalRotation, 0.0f);
            return tempEntity;
        });

        Entity000.setRotationX(rotationX);
        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(totalFrame);
        Entity000.setMaxLifeTime(totalFrame);
        Entity000.setAnimationName(AnimationName);
        Entity000.setEntityScale(scale);
        if (isBoundToEntity) {
            Entity000.setBoundToEntity(true);
            Entity000.setAdditionalPosX(addX);
            Entity000.setAdditionalPosY(addY);
            Entity000.setAdditionalPosZ(addZ);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static EffStrikeFromAirEntity GenerateEffStrikeFromAirEntity(Entity owner, float horizontalRotation, float rotationX, String TexturePath, String AnimationName, float scale, int lifetime, float addX, float addY, float addZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.EFF_STRIKE_FROM_AIR;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        EffStrikeFromAirEntity Entity000 = (EffStrikeFromAirEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, horizontalRotation, 0.0f);
            return tempEntity;
        });

        Entity000.setOwner(owner);
        Entity000.setRotationX(rotationX);
        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(lifetime);
        Entity000.setAnimationName(AnimationName);
        Entity000.setEntityScale(scale);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static EffGroundQuakeEntity GenerateEffGroundQuakeEntity(Entity owner, float horizontalRotation, float rotationX, String TexturePath, String AnimationName, float scale, int totalFrame, float addX, float addY, float addZ, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.EFF_GROUND_QUAKE;
        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        EffGroundQuakeEntity Entity000 = (EffGroundQuakeEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+addX, getEntityY(owner)+addY, getEntityZ(owner)+addZ, horizontalRotation, 0.0f);
            return tempEntity;
        });

        Entity000.setRotationX(rotationX);
        Entity000.setTextureName(TexturePath);
        Entity000.setLifetime(totalFrame);
        Entity000.setMaxLifeTime(totalFrame);
        Entity000.setAnimationName(AnimationName);
        Entity000.setEntityScale(scale);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwdFadingCloneCustomEntity GenerateCustomFadingCloneEntity(Entity owner, float horizontalRotation, float rotationX, float scale,
                                                                             int cRed, int cGreen, int cBlue, int cAlpha, float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                                             String TexturePath, boolean isBoundToEntity, int StartTotalFrame, int LoopTotalFrame, int EndTotalFrame, boolean isStartFading, boolean isEndFading, String AnimationName, float AnimationSpeedModifier, String ParticleName, String ClientMethodActionName,
                                                                             boolean shouldRenderMainHandWeapon, boolean shouldRenderOffhandWeapon, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_SWD_FADING_CLONE_ENTITY_NBT_NAME;
        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwdFadingCloneCustomEntity Entity000 = (SwdFadingCloneCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner) + additionalX, getEntityY(owner) + additionalY, getEntityZ(owner) + additionalZ, horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationX(rotationX);
        Entity000.setColor(cRed, cGreen, cBlue, cAlpha);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, LoopTotalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setAnimationName(AnimationName);
        Entity000.setOwner(owner);
        Entity000.setGeoAnimationSpeedModifier(AnimationSpeedModifier);
        Entity000.setClientActionName(ClientMethodActionName);
        if (isBoundToEntity) {
            Entity000.setIsBoundToEntity(isBoundToEntity);
            Entity000.setTargetPosX(additionalX);
            Entity000.setTargetPosY(additionalY);
            Entity000.setTargetPosZ(additionalZ);
        }
        Entity000.setParticleName(ParticleName);
        if (owner instanceof LivingEntity livingEntityOwner) {
            if (shouldRenderMainHandWeapon && !DfoSkillTreeItemMethods.isEntityMainHandHavingNoWeaponType(livingEntityOwner)) {
                Entity000.setShouldRenderMainHandWeapon(shouldRenderMainHandWeapon);
                Entity000.setOwnerMainHandWeaponStrID(DfoSkillTreeItemMethods.getMainHandNBTID(livingEntityOwner));
            }
            if (shouldRenderOffhandWeapon && !DfoSkillTreeItemMethods.isEntityOffhandHavingNoWeaponType(livingEntityOwner)) {
                Entity000.setShouldRenderOffhandWeapon(shouldRenderOffhandWeapon);
                Entity000.setOwnerOffhandWeaponStrID(DfoSkillTreeItemMethods.getOffHandNBTID(livingEntityOwner));
            }
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwdFadingCloneCustomEntity GenerateCustomFadingCloneEntityXYZ(Entity owner, float horizontalRotation, float rotationX, float scale,
                                                                             int cRed, int cGreen, int cBlue, int cAlpha, float x, float y, float z, float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                                             String TexturePath, boolean isBoundToEntity, int StartTotalFrame, int LoopTotalFrame, int EndTotalFrame, boolean isStartFading, boolean isEndFading, String AnimationName, float AnimationSpeedModifier, String ParticleName, String ClientMethodActionName,
                                                                             boolean shouldRenderMainHandWeapon, boolean shouldRenderOffhandWeapon, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_SWD_FADING_CLONE_ENTITY_NBT_NAME;
        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwdFadingCloneCustomEntity Entity000 = (SwdFadingCloneCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, x, y, z, horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationX(rotationX);
        Entity000.setColor(cRed, cGreen, cBlue, cAlpha);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, LoopTotalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setAnimationName(AnimationName);
        Entity000.setOwner(owner);
        Entity000.setGeoAnimationSpeedModifier(AnimationSpeedModifier);
        Entity000.setClientActionName(ClientMethodActionName);
        if (isBoundToEntity) {
            Entity000.setIsBoundToEntity(isBoundToEntity);
            Entity000.setTargetPosX(additionalX);
            Entity000.setTargetPosY(additionalY);
            Entity000.setTargetPosZ(additionalZ);
        }
        Entity000.setParticleName(ParticleName);
        if (owner instanceof LivingEntity livingEntityOwner) {
            if (shouldRenderMainHandWeapon && !DfoSkillTreeItemMethods.isEntityMainHandHavingNoWeaponType(livingEntityOwner)) {
                Entity000.setShouldRenderMainHandWeapon(shouldRenderMainHandWeapon);
                Entity000.setOwnerMainHandWeaponStrID(DfoSkillTreeItemMethods.getMainHandNBTID(livingEntityOwner));
            }
            if (shouldRenderOffhandWeapon && !DfoSkillTreeItemMethods.isEntityOffhandHavingNoWeaponType(livingEntityOwner)) {
                Entity000.setShouldRenderOffhandWeapon(shouldRenderOffhandWeapon);
                Entity000.setOwnerOffhandWeaponStrID(DfoSkillTreeItemMethods.getOffHandNBTID(livingEntityOwner));
            }
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwdFadingCloneCustomEntity GenerateCustomFadingCloneEntityWithItemStackStrID(Entity owner, float horizontalRotation, float rotationX, float scale,
                                                                             int cRed, int cGreen, int cBlue, int cAlpha, float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                                             String TexturePath, String LeftHandItemStackID, String RightHandItemStackID, boolean isBoundToEntity, int StartTotalFrame, int LoopTotalFrame, int EndTotalFrame, boolean isStartFading, boolean isEndFading, String AnimationName, float AnimationSpeedModifier, String ParticleName, String ClientMethodActionName,
                                                                             boolean shouldRenderMainHandWeapon, boolean shouldRenderOffhandWeapon, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_SWD_FADING_CLONE_ENTITY_NBT_NAME;
        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwdFadingCloneCustomEntity Entity000 = (SwdFadingCloneCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner) + additionalX, getEntityY(owner) + additionalY, getEntityZ(owner) + additionalZ, horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationX(rotationX);
        Entity000.setColor(cRed, cGreen, cBlue, cAlpha);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, LoopTotalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setAnimationName(AnimationName);
        Entity000.setOwner(owner);
        Entity000.setGeoAnimationSpeedModifier(AnimationSpeedModifier);
        Entity000.setClientActionName(ClientMethodActionName);
        if (isBoundToEntity) {
            Entity000.setIsBoundToEntity(isBoundToEntity);
            Entity000.setTargetPosX(additionalX);
            Entity000.setTargetPosY(additionalY);
            Entity000.setTargetPosZ(additionalZ);
        }
        Entity000.setParticleName(ParticleName);
        if (shouldRenderMainHandWeapon) {
            Entity000.setShouldRenderMainHandWeapon(shouldRenderMainHandWeapon);
            Entity000.setOwnerMainHandWeaponStrID(RightHandItemStackID);
        }
        if (shouldRenderOffhandWeapon) {
            Entity000.setShouldRenderOffhandWeapon(shouldRenderOffhandWeapon);
            Entity000.setOwnerOffhandWeaponStrID(LeftHandItemStackID);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwdFadingCloneCustomEntity GenerateCustomFadingCloneEntityWithTargetLoc(Entity owner, float horizontalRotation, float rotationX, float scale,
                                                                                          float s_X, float s_Y, float s_Z, float t_X, float t_Y, float t_Z, int totalTicksForMovingToTargetLoc,
                                                                                          int cRed, int cGreen, int cBlue, int cAlpha, int maxLifetime,
                                                                                          String TexturePath, int StartTotalFrame, int LoopTotalFrame, int EndTotalFrame, boolean isStartFading, boolean isEndFading, String AnimationName, float AnimationSpeedModifier, String ParticleName, String ClientMethodActionName,
                                                                                          boolean shouldRenderMainHandWeapon, boolean shouldRenderOffhandWeapon, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_SWD_FADING_CLONE_ENTITY_NBT_NAME;
        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwdFadingCloneCustomEntity Entity000 = (SwdFadingCloneCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, s_X, s_Y, s_Z, horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationX(rotationX);
        Entity000.setColor(cRed, cGreen, cBlue, cAlpha);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, LoopTotalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setAnimationName(AnimationName);
        Entity000.setOwner(owner);
        Entity000.setGeoAnimationSpeedModifier(AnimationSpeedModifier);
        Entity000.setClientActionName(ClientMethodActionName);
        Entity000.setTargetLoc(s_X, s_Y, s_Z, t_X, t_Y, t_Z, totalTicksForMovingToTargetLoc);
        Entity000.setParticleName(ParticleName);
        if (owner instanceof LivingEntity livingEntityOwner) {
            if (shouldRenderMainHandWeapon && !DfoSkillTreeItemMethods.isEntityMainHandHavingNoWeaponType(livingEntityOwner)) {
                Entity000.setShouldRenderMainHandWeapon(shouldRenderMainHandWeapon);
                Entity000.setOwnerMainHandWeaponStrID(DfoSkillTreeItemMethods.getMainHandNBTID(livingEntityOwner));
            }
            if (shouldRenderOffhandWeapon && !DfoSkillTreeItemMethods.isEntityOffhandHavingNoWeaponType(livingEntityOwner)) {
                Entity000.setShouldRenderOffhandWeapon(shouldRenderOffhandWeapon);
                Entity000.setOwnerOffhandWeaponStrID(DfoSkillTreeItemMethods.getOffHandNBTID(livingEntityOwner));
            }
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SwdFadingCloneCustomEntity GenerateCustomFadingCloneEntityWithTargetLocWithItemStackStrID(Entity owner, float horizontalRotation, float rotationX, float scale,
                                                                                          float s_X, float s_Y, float s_Z, float t_X, float t_Y, float t_Z, int totalTicksForMovingToTargetLoc,
                                                                                          int cRed, int cGreen, int cBlue, int cAlpha, int maxLifetime,
                                                                                          String TexturePath, String LeftHandItemStackID, String RightHandItemStackID, int StartTotalFrame, int LoopTotalFrame, int EndTotalFrame, boolean isStartFading, boolean isEndFading, String AnimationName, float AnimationSpeedModifier, String ParticleName, String ClientMethodActionName,
                                                                                          boolean shouldRenderMainHandWeapon, boolean shouldRenderOffhandWeapon, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_SWD_FADING_CLONE_ENTITY_NBT_NAME;
        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwdFadingCloneCustomEntity Entity000 = (SwdFadingCloneCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, s_X, s_Y, s_Z, horizontalRotation, 0.0f);
            return tempEntity;
        });
        Entity000.setRotationX(rotationX);
        Entity000.setColor(cRed, cGreen, cBlue, cAlpha);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, LoopTotalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setAnimationName(AnimationName);
        Entity000.setOwner(owner);
        Entity000.setGeoAnimationSpeedModifier(AnimationSpeedModifier);
        Entity000.setClientActionName(ClientMethodActionName);
        Entity000.setTargetLoc(s_X, s_Y, s_Z, t_X, t_Y, t_Z, totalTicksForMovingToTargetLoc);
        Entity000.setParticleName(ParticleName);
        if (shouldRenderMainHandWeapon) {
            Entity000.setShouldRenderMainHandWeapon(shouldRenderMainHandWeapon);
            Entity000.setOwnerMainHandWeaponStrID(RightHandItemStackID);
        }
        if (shouldRenderOffhandWeapon) {
            Entity000.setShouldRenderOffhandWeapon(shouldRenderOffhandWeapon);
            Entity000.setOwnerOffhandWeaponStrID(LeftHandItemStackID);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static ChannelingBarEntity GenerateCustomChannelingBarEntity(Entity owner, float scale, float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                                        String TexturePath, boolean isBoundToEntity, float AnimationSpeedModifier, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.ENTITY_CHANNELING_BAR;
        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        ChannelingBarEntity Entity000 = (ChannelingBarEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner) + additionalX, getEntityY(owner) + additionalY, getEntityZ(owner) + additionalZ, 0f, 0f);
            return tempEntity;
        });
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath);
        Entity000.setOwner(owner);
        Entity000.setGeoAnimationSpeedModifier(AnimationSpeedModifier);
        if (isBoundToEntity) {
            Entity000.setIsBoundToEntity(isBoundToEntity);
            Entity000.setTargetPosX(additionalX);
            Entity000.setTargetPosY(additionalY);
            Entity000.setTargetPosZ(additionalZ);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static ChannelingBarEntity GenerateRegularChannelingBarEntity(Entity owner, float scale, float additionalX, float additionalY, float additionalZ, int maxLifetime, float AnimationSpeedModifier, boolean addEntityToWorldRightAway) {
        return GenerateCustomChannelingBarEntity(owner, scale, additionalX, additionalY, additionalZ, maxLifetime, GeoModelRegistryName.ENTITY_CHANNELING_BAR_TEXTURE, true, AnimationSpeedModifier, addEntityToWorldRightAway);
    }

    public static ChannelingIconEntity GenerateCustomChannelingIconEntity(Entity owner, float scale, float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                                          String TexturePath, boolean isBoundToEntity, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.ENTITY_CHANNELING_ICON;
        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        ChannelingIconEntity Entity000 = (ChannelingIconEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner) + additionalX, getEntityY(owner) + additionalY, getEntityZ(owner) + additionalZ, 0f, 0f);
            return tempEntity;
        });
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath);
        Entity000.setOwner(owner);
        if (isBoundToEntity) {
            Entity000.setIsBoundToEntity(isBoundToEntity);
            Entity000.setTargetPosX(additionalX);
            Entity000.setTargetPosY(additionalY);
            Entity000.setTargetPosZ(additionalZ);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static SimpleMCSwordCustomEntity GenerateSimpleMCSwordCustomEntity(Entity owner, float scale,
                                                                              float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                                              boolean hasTargetLoc, float x, float y, float z, float vx, float vy, float vz,
                                                                              String TexturePath, String AnimationName, String ClientMethodActionName, boolean isBoundToEntity,
                                                                              MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.EFF_SWD_SIMPLE_MC_SWORD;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SimpleMCSwordCustomEntity Entity000 = (SimpleMCSwordCustomEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, additionalX, additionalY, additionalZ, 0f, 0f);
            return tempEntity;
        });

        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setTextureName(TexturePath);
        Entity000.setOwner(owner);
        Entity000.setAnimationName(AnimationName);
        Entity000.setClientActionName(ClientMethodActionName);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);
        if (isBoundToEntity) {
            Entity000.setIsBoundToEntity(isBoundToEntity);
            moveEntityToXYZ(Entity000, getEntityX(owner)+additionalX, getEntityY(owner)+additionalY, getEntityZ(owner)+additionalZ);
            Entity000.setTargetPosX(additionalX);
            Entity000.setTargetPosY(additionalY);
            Entity000.setTargetPosZ(additionalZ);
        }
        else if (hasTargetLoc) {
            Entity000.setTargetLoc(x, y, z, vx, vy, vz, true);
        }

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static TargetZoneSelectionEntity GenerateTargetZoneSelectionEntity(Entity owner, float horizontalFacingAngle, float scale,
                                                                              float x, float y, float z, int maxLifetime,
                                                                              int StartTotalFrame, int LoopTotalFrame, int EndTotalFrame, boolean isFrameDistributedEvenly, boolean isStartFading, boolean isEndFading,
                                                                              String TexturePath, String AnimationName, String ClientMethodActionName,
                                                                              MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean isVisibleToAllPlayers, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_TARGET_ZONE;
        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        TargetZoneSelectionEntity Entity000 = (TargetZoneSelectionEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, x, y, z, horizontalFacingAngle, 0f);
            return tempEntity;
        });

        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, LoopTotalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setFrameDistributedEvenly(isFrameDistributedEvenly);
        Entity000.setAnimationName(AnimationName);
        Entity000.setOwner(owner);
        Entity000.setClientActionName(ClientMethodActionName);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);
        Entity000.setVisibleToAllPlayers(isVisibleToAllPlayers);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static BMTempestSwordEntity GenerateBMTempestSwordEntity(Entity owner, float horizontalFacingAngle, float scale, float aoeRange,
                                                                    float x, float y, float z, int maxLifetime,
                                                                    float targetX, float targetY, float targetZ,
                                                                    String TexturePath, String AnimationName, String ClientMethodActionName,
                                                                    MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.EFF_SWD_TEMPEST_SWORD;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        BMTempestSwordEntity Entity000 = (BMTempestSwordEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, x, y, z, horizontalFacingAngle, 0f);
            return tempEntity;
        });

        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setTextureName(TexturePath);
        Entity000.setOwner(owner);
        Entity000.setAnimationName(AnimationName);
        Entity000.setClientActionName(ClientMethodActionName);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);
        Entity000.setTargetLoc(targetX, targetY, targetZ);
        Entity000.setAoeRange(aoeRange);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    //Skill Blademaster Eff
    public static SwdTempestBlockEffEntity GenerateBMSwdTempestBlockEff(Entity owner, float horizontalFacingAngle, float scale, float aoeRange,
                                                                        float x, float y, float z, int maxLifetime,
                                                                        float targetX, float targetY, float targetZ,
                                                                        String TexturePath, String AnimationName, String ClientMethodActionName,
                                                                        MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.EFF_SWD_TEMPEST_BLOCK;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SwdTempestBlockEffEntity Entity000 = (SwdTempestBlockEffEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, x, y, z, horizontalFacingAngle, 0f);
            return tempEntity;
        });

        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setTextureName(TexturePath);
        Entity000.setOwner(owner);
        Entity000.setAnimationName(AnimationName);
        Entity000.setClientActionName(ClientMethodActionName);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);
        Entity000.setTargetLoc(targetX, targetY, targetZ);
        Entity000.setAoeRange(aoeRange);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    //blademaster draw sword
    public static DrawSwordQiEntity GenerateDrawSwordQiEntity(Entity owner, float horizontalFacingAngle, float verticalFacingAngle, float scale, float AoeRange, float MaxAoeScaleMultiplier,
                                                              float additionalX, float additionalY, float additionalZ, int maxLifetime,
                                                              String TexturePath, String AnimationName, float AnimationSpeed, int StartTotalFrame, int EndTotalFrame, int totalFrame, boolean isStartFading, boolean isEndFading,
                                                              boolean isFrameDistributedEvenly, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.EFF_SWD_DRAW_SWORD_QI;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        DrawSwordQiEntity Entity000 = (DrawSwordQiEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner)+additionalX, getEntityY(owner)+additionalY, getEntityZ(owner)+additionalZ, horizontalFacingAngle, verticalFacingAngle);
            return tempEntity;
        });

        Entity000.setAoeRange(AoeRange);
        Entity000.setMaxAoeScaleMultiplier(MaxAoeScaleMultiplier);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, totalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setOwner(owner);
        Entity000.setAnimationName(AnimationName);
        Entity000.setGeoAnimationSpeedModifier(AnimationSpeed);
        Entity000.setFrameDistributedEvenly(isFrameDistributedEvenly);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    //blademaster draw sword
    public static DrawSwordQiEntity GenerateDrawSwordQiEntityXYZ(Entity owner, float horizontalFacingAngle, float verticalFacingAngle, float scale, float AoeRange, float MaxAoeScaleMultiplier,
                                                              float x, float y, float z, int maxLifetime,
                                                              String TexturePath, String AnimationName, float AnimationSpeed, int StartTotalFrame, int EndTotalFrame, int totalFrame, boolean isStartFading, boolean isEndFading,
                                                              boolean isFrameDistributedEvenly, MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID+GeoModelRegistryName.COLON+GeoModelRegistryName.EFF_SWD_DRAW_SWORD_QI;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        DrawSwordQiEntity Entity000 = (DrawSwordQiEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, x, y, z, horizontalFacingAngle, verticalFacingAngle);
            return tempEntity;
        });

        Entity000.setAoeRange(AoeRange);
        Entity000.setMaxAoeScaleMultiplier(MaxAoeScaleMultiplier);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifetime(maxLifetime);
        Entity000.setTextureName(TexturePath, StartTotalFrame, totalFrame, EndTotalFrame, isStartFading, isEndFading);
        Entity000.setOwner(owner);
        Entity000.setAnimationName(AnimationName);
        Entity000.setGeoAnimationSpeedModifier(AnimationSpeed);
        Entity000.setFrameDistributedEvenly(isFrameDistributedEvenly);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static FancySwordEntity GenerateFancySwordEff(Entity owner, float horizontalFacingAngle, float verticalFacingAngle, float rotationZ,
                                                         float scale, float x, float y, float z, int maxLifetime,
                                                         float targetX, float targetY, float targetZ,
                                                         String GeoModelPath, String TexturePath, String AnimationName, String ClientMethodActionName,
                                                         MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_SWD_FANCY_SWORD;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        FancySwordEntity Entity000 = (FancySwordEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, x, y, z, horizontalFacingAngle, verticalFacingAngle);
            return tempEntity;
        });

        Entity000.setRotationZ(rotationZ);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setTextureName(TexturePath);
        Entity000.setOwner(owner);
        Entity000.setGeoModelName(GeoModelPath);
        Entity000.setAnimationName(AnimationName);
        Entity000.setClientActionName(ClientMethodActionName);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);
        Entity000.setTargetLoc(targetX, targetY, targetZ);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    //slaughter scape
    public static SlaughterscapeSwordEntity GenerateSlaughterScapeEntity(Entity owner, float horizontalFacingAngle, float scale, float degreeShouldIncrementEachTime, float swordHitAoeRange, float randomHitHeightOffSet,
                                                                         float x, float y, float z, float addX, float addY, float addZ, float targetCenterX, float targetCenterY, float targetCenterZ,
                                                                         int maxLifetime, int positionID, int targetEntityID,
                                                                         String TexturePath, String AnimationName, String ClientMethodActionName,
                                                                         MethodEntityAction serverEntityAction, int serverEntityActionTickPeriod, boolean isBoundToEntity, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_SWD_SLAUGHTER_SCAPE_SWORD;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        SlaughterscapeSwordEntity Entity000 = (SlaughterscapeSwordEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, x + addX, y + addY, z + addZ, horizontalFacingAngle, 0f);
            return tempEntity;
        });

        Entity000.setSwordPositionID(positionID);
        Entity000.setRandomHitHeightOffset(randomHitHeightOffSet);
        Entity000.setTargetEntityID(targetEntityID);
        Entity000.setSwordHitAoeRange(swordHitAoeRange);
        Entity000.setAdditionalPosX(addX);
        Entity000.setAdditionalPosY(addY);
        Entity000.setAdditionalPosZ(addZ);
        Entity000.setTargetCenterPosX(targetCenterX);
        Entity000.setTargetCenterPosY(targetCenterY);
        Entity000.setTargetCenterPosZ(targetCenterZ);
        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setTextureName(TexturePath);
        Entity000.setOwner(owner);
        Entity000.setAnimationName(AnimationName);
        Entity000.setClientActionName(ClientMethodActionName);
        Entity000.setEntityServerAction(serverEntityAction, serverEntityActionTickPeriod);
        Entity000.setIsBoundToEntity(isBoundToEntity);
        Entity000.setDegreeShouldIncrementEachTime(degreeShouldIncrementEachTime);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }
}
