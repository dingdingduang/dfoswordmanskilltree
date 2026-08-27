package net.dingdingduang.dfoswordmanskilltree.geomodel.models.eff.handeff;


import java.util.HashMap;

public class HandEffShouldRenderFuncRegistry {
    private static HashMap<String, HandEffShouldRenderFuncLambda> HandEffShouldRenderFuncMap = new HashMap<>();


    public static void HandEffShouldRenderFuncRegistryInit() {

    }

    public static void HandEffShouldRenderFuncMapClear() {
        HandEffShouldRenderFuncMap.clear();
    }

    public static HandEffShouldRenderFuncLambda getHandEffShouldRenderFunc(String registeredMethodName) {
        return HandEffShouldRenderFuncMap.get(registeredMethodName);
    }
}
