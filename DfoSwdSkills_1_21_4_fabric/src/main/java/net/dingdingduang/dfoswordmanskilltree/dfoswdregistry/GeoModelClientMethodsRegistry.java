package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.blademaster.active.*;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import java.util.HashMap;

public class GeoModelClientMethodsRegistry {
    private static HashMap<String, MethodEntityAction> GeoModelClientActionName2Action = new HashMap<>();

    public static HashMap<String, MethodEntityAction> getGeoModelClientActionName2Action() { return GeoModelClientActionName2Action; }
    public static void setGeoModelClientActionName2Action(HashMap<String, MethodEntityAction> GeoModelclientActionName2Action) { GeoModelClientActionName2Action = GeoModelclientActionName2Action; }

    public static void GeoModelClientActionNameMapRegistryInit() {
        DfoEntityClientActionRegistry.GeneralClientMethodInit(GeoModelClientActionName2Action);
        Blademaster023.Blademaster023_ClientMethodInit(GeoModelClientActionName2Action);
        Blademaster024.Blademaster024_ClientMethodInit(GeoModelClientActionName2Action);
        Blademaster025.Blademaster025_ClientMethodInit(GeoModelClientActionName2Action);
        Blademaster035.Blademaster035_ClientMethodInit(GeoModelClientActionName2Action);
        Blademaster037.Blademaster037_ClientMethodInit(GeoModelClientActionName2Action);
    }

    public static void GeoModelClientActionNameMapClear() {
        GeoModelClientActionName2Action.clear();
    }

    public static MethodEntityAction getGeoModelClientActionMethod(String clientActionName) {
        return GeoModelClientActionName2Action.get(clientActionName);
    }
}
