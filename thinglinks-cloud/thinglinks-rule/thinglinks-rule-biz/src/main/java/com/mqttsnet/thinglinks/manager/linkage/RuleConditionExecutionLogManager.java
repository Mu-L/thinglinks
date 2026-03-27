package com.mqttsnet.thinglinks.manager.linkage;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.entity.linkage.RuleConditionExecutionLog;
import com.mqttsnet.thinglinks.vo.query.linkage.RuleConditionExecutionLogPageQuery;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 规则条件执行日志表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-12-02 18:53:47
 * @create [2024-12-02 18:53:47] [mqttsnet]
 */
public interface RuleConditionExecutionLogManager extends SuperManager<RuleConditionExecutionLog> {


    /**
     * 获取规则条件执行日志列表
     *
     * @param query 查询条件 {@link RuleConditionExecutionLogPageQuery}
     * @return 规则条件执行日志列表 {@link RuleConditionExecutionLog}
     */
    List<RuleConditionExecutionLog> getRuleConditionExecutionLogList(RuleConditionExecutionLogPageQuery query);
}


