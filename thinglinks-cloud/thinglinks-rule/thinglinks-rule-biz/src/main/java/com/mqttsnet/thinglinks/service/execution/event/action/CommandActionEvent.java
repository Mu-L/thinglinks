package com.mqttsnet.thinglinks.service.execution.event.action;

import com.mqttsnet.thinglinks.dto.linkage.RuleConditionActionPolicyDTO;
import com.mqttsnet.thinglinks.dto.linkage.execution.PolicyContext;

public class CommandActionEvent extends RuleConditionActionEvent {

    public CommandActionEvent(Object source, PolicyContext policyContext, RuleConditionActionPolicyDTO actionPolicyDTO) {
        super(source, policyContext, actionPolicyDTO);
    }
}
