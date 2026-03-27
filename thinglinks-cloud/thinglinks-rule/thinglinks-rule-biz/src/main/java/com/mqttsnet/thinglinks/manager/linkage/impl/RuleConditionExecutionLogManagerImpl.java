package com.mqttsnet.thinglinks.manager.linkage.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.entity.linkage.RuleConditionExecutionLog;
import com.mqttsnet.thinglinks.manager.linkage.RuleConditionExecutionLogManager;
import com.mqttsnet.thinglinks.mapper.linkage.RuleConditionExecutionLogMapper;
import com.mqttsnet.thinglinks.vo.query.linkage.RuleConditionExecutionLogPageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通用业务实现类
 * 规则条件执行日志表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-12-02 18:53:47
 * @create [2024-12-02 18:53:47] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RuleConditionExecutionLogManagerImpl extends SuperManagerImpl<RuleConditionExecutionLogMapper, RuleConditionExecutionLog> implements RuleConditionExecutionLogManager {


    private final RuleConditionExecutionLogMapper ruleConditionExecutionLogMapper;


    /**
     * 获取规则条件执行日志列表
     *
     * @param query 查询条件 {@link RuleConditionExecutionLogPageQuery}
     * @return 规则条件执行日志列表 {@link RuleConditionExecutionLog}
     */
    @Override
    public List<RuleConditionExecutionLog> getRuleConditionExecutionLogList(RuleConditionExecutionLogPageQuery query) {
        QueryWrap<RuleConditionExecutionLog> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(query.getRuleExecutionId() != null, RuleConditionExecutionLog::getRuleExecutionId, query.getRuleExecutionId());
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(query.getConditionUuid()), RuleConditionExecutionLog::getConditionUuid, query.getConditionUuid());
        queryWrap.lambda().eq(query.getConditionType() != null, RuleConditionExecutionLog::getConditionType, query.getConditionType());
        queryWrap.lambda().eq(query.getEvaluationResult() != null, RuleConditionExecutionLog::getEvaluationResult, query.getEvaluationResult());
        queryWrap.lambda().like(CharSequenceUtil.isNotBlank(query.getRemark()), RuleConditionExecutionLog::getRemark, query.getRemark());

        return ruleConditionExecutionLogMapper.selectList(queryWrap);
    }
}


