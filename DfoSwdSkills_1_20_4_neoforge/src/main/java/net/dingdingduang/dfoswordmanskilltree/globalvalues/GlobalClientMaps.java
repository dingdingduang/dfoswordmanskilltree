package net.dingdingduang.dfoswordmanskilltree.globalvalues;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;

public class GlobalClientMaps {
    private static HashMap<Integer, BlockState> TemporaryBlockStateMap = new HashMap<Integer, BlockState>();
//    private static HashMap<Entity, Integer> ChannelingIconOrder = new HashMap<>();
    private static HashMap<String, ResourceLocation> ReusableResourcesMap = new HashMap<>();

    public static HashMap<Integer, BlockState> getTemporaryBlockStateMap() { return TemporaryBlockStateMap; }

    public static HashMap<String, ResourceLocation> getReusableResourcesMap() { return ReusableResourcesMap; }
    public static ResourceLocation getReusableResourceByStringID(String ResPath) {
        ResourceLocation resLoc = ReusableResourcesMap.get(ResPath);
        if (resLoc == null) {
            resLoc = new ResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, ResPath);
            ReusableResourcesMap.put(ResPath, resLoc);
        }
        return resLoc;
    }

//    public static HashMap<Entity, Integer> getChannelingIconOrder() { return ChannelingIconOrder; }

//    public static void setTemporaryBlockStateMap(HashMap<Integer, BlockState> temporaryBlockStateMap) {
//        TemporaryBlockStateMap = temporaryBlockStateMap;
//    }
}
