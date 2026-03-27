package com.mqttsnet.thinglinks.manager.linkage;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.entity.linkage.RuleInstance;

/**
 * <p>
 * 通用业务接口
 * 规则实例表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-05 23:04:02
 * @create [2023-07-05 23:04:02] [mqttsnet]
 */
public interface RuleInstanceManager extends SuperManager<RuleInstance> {


    /**
     * 根据flowId 查询实例信息
     *
     * @param flowId 流程id
     * @return {@link RuleInstance}
     */
    RuleInstance selectOneByFlowId(String flowId);

}


