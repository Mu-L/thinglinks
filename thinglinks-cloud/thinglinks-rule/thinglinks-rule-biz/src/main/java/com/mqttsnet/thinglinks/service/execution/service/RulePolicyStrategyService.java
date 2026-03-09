package com.mqttsnet.thinglinks.service.execution.service;

import com.mqttsnet.thinglinks.dto.linkage.RuleConditionPolicyDTO;
import com.mqttsnet.thinglinks.dto.linkage.execution.PolicyContext;

/**
 * 规则策略服务接口，定义执行规则策略的方法。
 *
 * @author xiaonan
 */
public interface RulePolicyStrategyService {

    /**
     * 根据提供的策略上下文应用相应的策略。
     *
     * @param context            包含所有必要信息的策略上下文
     * @param conditionPolicyDTO 条件策略DTO
     * @throws IllegalArgumentException 如果上下文参数不合法
     */
    void applyPolicy(PolicyContext context, RuleConditionPolicyDTO conditionPolicyDTO);

}
