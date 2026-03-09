package com.mqttsnet.thinglinks.service.linkage.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.basic.utils.SnowflakeIdUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.entity.linkage.RuleExecutionLog;
import com.mqttsnet.thinglinks.enumeration.linkage.RuleExecutionStatusEnum;
import com.mqttsnet.thinglinks.manager.linkage.RuleExecutionLogManager;
import com.mqttsnet.thinglinks.service.execution.event.executionlog.BaseExecutionLogEvent;
import com.mqttsnet.thinglinks.service.execution.event.executionlog.RuleExecutionLogEvent;
import com.mqttsnet.thinglinks.service.linkage.RuleActionExecutionLogService;
import com.mqttsnet.thinglinks.service.linkage.RuleConditionExecutionLogService;
import com.mqttsnet.thinglinks.service.linkage.RuleExecutionLogService;
import com.mqttsnet.thinglinks.vo.query.linkage.RuleActionExecutionLogPageQuery;
import com.mqttsnet.thinglinks.vo.query.linkage.RuleConditionExecutionLogPageQuery;
import com.mqttsnet.thinglinks.vo.result.linkage.RuleActionExecutionLogDetailsResultVO;
import com.mqttsnet.thinglinks.vo.result.linkage.RuleConditionExecutionLogDetailsResultVO;
import com.mqttsnet.thinglinks.vo.result.linkage.RuleExecutionLogDetailsResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 规则执行日志表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-12-02 18:41:26
 * @create [2024-12-02 18:41:26] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class RuleExecutionLogServiceImpl extends SuperServiceImpl<RuleExecutionLogManager, Long, RuleExecutionLog> implements RuleExecutionLogService {

    private final RuleConditionExecutionLogService ruleConditionExecutionLogService;

    private final RuleActionExecutionLogService ruleActionExecutionLogService;


    /**
     * 保存规则执行日志
     *
     * @param event
     */
    public void saveRuleExecutionLog(BaseExecutionLogEvent event) {
        RuleExecutionLog ruleLog = new RuleExecutionLog();
        ruleLog.setId(event.getRuleExecutionId() != null ? event.getRuleExecutionId() : Long.valueOf(SnowflakeIdUtil.nextId()));
        ruleLog.setRuleIdentification(((RuleExecutionLogEvent) event).getRuleIdentification());
        ruleLog.setRuleName(((RuleExecutionLogEvent) event).getRuleName());
        ruleLog.setStatus(RuleExecutionStatusEnum.COMPLETED.getValue());
        ruleLog.setStartTime(event.getStartTime());
        ruleLog.setEndTime(event.getEndTime());
        ruleLog.setExtendParams(event.getExtendParams());
        ruleLog.setRemark(event.getRemark());
        superManager.save(ruleLog);
        log.info("Rule execution log saved: {}", ruleLog);
    }

    @Override
    public RuleExecutionLogDetailsResultVO getRuleExecutionLogDetails(Long id) {
        // 获取规则执行日志
        RuleExecutionLog ruleExecutionLog = Optional.ofNullable(superManager.getById(id))
                .orElseThrow(() -> new BizException("Rule execution log not found"));

        // 将规则执行日志转换为VO
        RuleExecutionLogDetailsResultVO ruleExecutionLogDetailsResultVO = BeanPlusUtil.toBeanIgnoreError(ruleExecutionLog, RuleExecutionLogDetailsResultVO.class);

        // 获取规则条件及其对应的动作
        List<RuleConditionExecutionLogDetailsResultVO> conditions = getRuleConditions(ruleExecutionLogDetailsResultVO.getId());
        ruleExecutionLogDetailsResultVO.setConditionExecutionLogDetailsResultVOList(conditions);

        // 获取动作执行日志
        List<RuleActionExecutionLogDetailsResultVO> actionExecutionLogs = getRuleActions(ruleExecutionLogDetailsResultVO.getId());
        ruleExecutionLogDetailsResultVO.setActionExecutionLogDetailsResultVOList(actionExecutionLogs);

        return ruleExecutionLogDetailsResultVO;
    }

    private List<RuleConditionExecutionLogDetailsResultVO> getRuleConditions(Long ruleExecutionId) {
        RuleConditionExecutionLogPageQuery conditionQuery = new RuleConditionExecutionLogPageQuery();
        conditionQuery.setRuleExecutionId(ruleExecutionId);
        return ruleConditionExecutionLogService.getRuleConditionExecutionLogResultVOList(conditionQuery).stream()
                .map(condition -> BeanPlusUtil.toBeanIgnoreError(condition, RuleConditionExecutionLogDetailsResultVO.class))
                .collect(Collectors.toList());
    }

    private List<RuleActionExecutionLogDetailsResultVO> getRuleActions(Long ruleExecutionId) {
        RuleActionExecutionLogPageQuery actionQuery = new RuleActionExecutionLogPageQuery();
        actionQuery.setRuleExecutionId(ruleExecutionId);
        return ruleActionExecutionLogService.getRuleActionExecutionLogResultVOList(actionQuery).stream()
                .map(action -> BeanPlusUtil.toBeanIgnoreError(action, RuleActionExecutionLogDetailsResultVO.class))
                .collect(Collectors.toList());
    }


}


