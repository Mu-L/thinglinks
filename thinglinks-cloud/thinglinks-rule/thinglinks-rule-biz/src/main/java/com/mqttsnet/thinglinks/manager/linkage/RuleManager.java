package com.mqttsnet.thinglinks.manager.linkage;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.entity.linkage.Rule;

/**
 * <p>
 * 通用业务接口
 * 规则信息
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-19 23:20:14
 * @create [2023-07-19 23:20:14] [mqttsnet]
 */
public interface RuleManager extends SuperManager<Rule> {


    /**
     * 根据规则标识查询规则信息
     *
     * @param ruleIdentification 规则标识
     * @return {@link Rule} 规则信息
     */
    Rule findOneByRuleIdentification(String ruleIdentification);
}


