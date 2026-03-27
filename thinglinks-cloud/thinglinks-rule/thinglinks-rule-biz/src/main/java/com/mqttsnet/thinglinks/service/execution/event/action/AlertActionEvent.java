package com.mqttsnet.thinglinks.service.execution.event.action;

import com.mqttsnet.thinglinks.dto.linkage.RuleConditionActionPolicyDTO;
import com.mqttsnet.thinglinks.dto.linkage.execution.PolicyContext;

/**
 * -----------------------------------------------------------------------------
 * File Name: AlertActionEvent
 * -----------------------------------------------------------------------------
 * Description:
 * <p>
 * -----------------------------------------------------------------------------
 *
 * @author mqttsnet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2023/12/17       mqttsnet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2023/12/17 20:31
 */
public class AlertActionEvent extends RuleConditionActionEvent {
    public AlertActionEvent(Object source, PolicyContext policyContext, RuleConditionActionPolicyDTO actionPolicyDTO) {
        super(source, policyContext, actionPolicyDTO);
    }
}
