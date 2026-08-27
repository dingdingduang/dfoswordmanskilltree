package net.dingdingduang.dfoswordmanskilltree.dfoswdregistry;

import net.dingdingduang.dfoswordmanskilltree.geomodel.models.nullrenderer.clientmovementhelper.ClientMovementHelperEntity;
import net.dingdingduang.dfoswordmanskilltree.skilldata.skillaction.DfoSwordmanSkillMethods;
import net.dingdingduang.somebasicskills.util.MethodEntityAction;

import net.minecraft.entity.Entity;

import java.util.HashMap;

public class DfoEntityClientActionRegistry {
    public static final String STOP_MOVING_IF_NOT_IN_ACTION_OR_INTERRUPTED = "STOP_MOVING_IF_NOT_IN_ACTION_OR_INTERRUPTED";

    public static void GeneralClientMethodInit(HashMap<String, MethodEntityAction> clientMethodMap) {
        clientMethodMap.put(STOP_MOVING_IF_NOT_IN_ACTION_OR_INTERRUPTED, DfoEntityClientActionRegistry::ClientMovementHelperMethod_StopMovingIfPlayerEntityNotInActionOrInterrupted);
    }

    public static void ClientMovementHelperMethod_StopMovingIfPlayerEntityNotInActionOrInterrupted(Entity entity) {
        ClientMovementHelperEntity clientMovementHelper = (ClientMovementHelperEntity) entity;
        if (clientMovementHelper.hasTargetLocXYZ()) {
            Entity owner = clientMovementHelper.getOwner();
            if (owner != null && (!DfoSwordmanSkillMethods.isPlayerInAction() || DfoSwordmanSkillMethods.isPlayerActionInterrupted())) {
                clientMovementHelper.setHasTargetLocXYZBoolean(false);
            }
        }
    }
}
