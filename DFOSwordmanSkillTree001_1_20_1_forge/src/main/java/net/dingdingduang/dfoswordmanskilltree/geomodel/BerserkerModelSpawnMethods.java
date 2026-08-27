package net.dingdingduang.dfoswordmanskilltree.geomodel;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.berserker.bloodtwister.BloodTwisterEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.berserker.extreme_overkill.background.ExtremeOverkillBackgroundEntity;
import net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.swd.berserker.extreme_overkill.bloodsword.ExtremeOverkillBloodSwordEntity;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

import static net.dingdingduang.dfoswordmanskilltree.entity.DfoGeneralEntitySpawnMethods.MCLoadEntityRecursive;
import static net.dingdingduang.dfoswordmanskilltree.geomodel.GeoModelSpawnMethods.getEntityIDTag;
import static net.dingdingduang.dfoswordmanskilltree.globalmethods.EntityMethods.*;

public class BerserkerModelSpawnMethods {
    //Blood Twister
    public static BloodTwisterEntity GenerateBloodTwisterEff(Entity owner, float horizontalFacingAngle, float scale, int maxLifetime, float additionalX, float additionalY, float additionalZ, MethodEntityAction serverAction, int actionPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_BERSEKER_BLOOD_TWISTER;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        BloodTwisterEntity Entity000 = (BloodTwisterEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner) + additionalX, getEntityY(owner) + additionalY, getEntityZ(owner) + additionalZ, horizontalFacingAngle, 0f);
            return tempEntity;
        });

        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setTargetLoc(additionalX, additionalY, additionalZ);
        Entity000.setOwner(owner);
        Entity000.setEntityServerAction(serverAction, actionPeriod);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    //Extreme Overkill
    public static ExtremeOverkillBackgroundEntity GenerateExtremeOverkillBackGround(Entity owner, float horizontalFacingAngle, float scale, int maxLifetime, float posX, float posY, float posZ, MethodEntityAction serverAction, int actionPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_BERSEKER_EXTREME_OVERKILL_BACKGROUND;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        ExtremeOverkillBackgroundEntity Entity000 = (ExtremeOverkillBackgroundEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, posX, posY, posZ, horizontalFacingAngle, 0f);
            return tempEntity;
        });

        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setOwner(owner);
        Entity000.setEntityServerAction(serverAction, actionPeriod);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }

    public static ExtremeOverkillBloodSwordEntity GenerateExtremeOverkillBloodSword(Entity owner, float horizontalFacingAngle, float scale, int maxLifetime, float additionalX, float additionalY, float additionalZ, MethodEntityAction serverAction, int actionPeriod, boolean addEntityToWorldRightAway) {
        String entityID = DfoSwordmanSkillTreeConstants.MOD_ID + GeoModelRegistryName.COLON + GeoModelRegistryName.EFF_BERSEKER_EXTREME_OVERKILL_BLOOD_SWORD;

        CompoundTag nbtEntityTag = getEntityIDTag(entityID);

        ExtremeOverkillBloodSwordEntity Entity000 = (ExtremeOverkillBloodSwordEntity) MCLoadEntityRecursive(nbtEntityTag, getEntityLevel(owner), (tempEntity) -> {
            moveEntityToXYZRotation(tempEntity, getEntityX(owner) + additionalX, getEntityY(owner) + additionalY, getEntityZ(owner) + additionalZ, horizontalFacingAngle, 0f);
            return tempEntity;
        });

        Entity000.setEntityScale(scale);
        Entity000.setLifetime(maxLifetime);
        Entity000.setMaxLifeTime(maxLifetime);
        Entity000.setTargetLoc(additionalX, additionalY, additionalZ);
        Entity000.setOwner(owner);
        Entity000.setEntityServerAction(serverAction, actionPeriod);

        if (addEntityToWorldRightAway) {
            addEntityToWorld(getEntityLevel(owner), Entity000);
        }
        return Entity000;
    }
}
