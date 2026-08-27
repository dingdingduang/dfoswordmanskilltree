package net.dingdingduang.dfoswordmanskilltree.globalvalues;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static net.dingdingduang.somebasicskills.globalmethods.GeneralMethods.getMCResourceLocation;

public class GlobalClientMaps {
    private static HashMap<Integer, BlockState> TemporaryBlockStateMap = new HashMap<Integer, BlockState>();
//    private static HashMap<Entity, Integer> ChannelingIconOrder = new HashMap<>();
    private static HashMap<String, Identifier> ReusableResourcesMap = new HashMap<>();

    public static HashMap<Integer, BlockState> getTemporaryBlockStateMap() { return TemporaryBlockStateMap; }

    public static HashMap<String, Identifier> getReusableResourcesMap() { return ReusableResourcesMap; }
    public static Identifier getReusableResourceByStringID(String ResPath) {
        Identifier resLoc = ReusableResourcesMap.get(ResPath);
        if (resLoc == null) {
            resLoc = getMCResourceLocation(DfoSwordmanSkillTreeConstants.MOD_ID, ResPath);
            ReusableResourcesMap.put(ResPath, resLoc);
        }
        return resLoc;
    }
}
