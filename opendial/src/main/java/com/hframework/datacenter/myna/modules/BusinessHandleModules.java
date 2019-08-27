package com.hframework.datacenter.myna.modules;

import com.hframework.datacenter.myna.BusinessHandler;
import com.hframework.datacenter.myna.RobotManager;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import opendial.DialogueState;
import opendial.DialogueSystem;
import opendial.bn.values.Value;
import opendial.modules.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BusinessHandleModules extends AbstractModule implements Module {
    private static final Logger logger = LoggerFactory.getLogger(BusinessHandleModules.class);

    public BusinessHandleModules(DialogueSystem system) {
        super(system);
    }


    @Override
    public void trigger(DialogueState state, Collection<String> updatedVars) {
        if (updatedVars.contains(DialogSystemHelper.BUSINESS_HANDLE) && state.hasChanceNode(DialogSystemHelper.BUSINESS_HANDLE)) {
//            QnaDescriptor currentQnaDescriptor = getCurrentQnaDescriptor(state);
//            BusinessHandler preHandler = currentQnaDescriptor.getPreHandler();
            String businessHandleCode = state.getChanceNode(DialogSystemHelper.BUSINESS_HANDLE).getValues().iterator().next().toString();
            BusinessHandler preHandler = RobotManager.getBusinessHandler(businessHandleCode);
            if(preHandler != null) {
                Map<String, Object> handle = preHandler.handle(getSlotValue(state));
                for (Map.Entry<String, Object> entry : handle.entrySet()) {
                    system.addContent(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
            system.addContent(DialogSystemHelper.BUSINESS_HANDLE_DOWN,
                    state.getChanceNode(DialogSystemHelper.BUSINESS_HANDLE).getValues().iterator().next());

        }
    }
}
