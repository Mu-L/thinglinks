package com.mqttsnet.thinglinks.service.execution.event.executionlog.publisher;

import com.mqttsnet.thinglinks.enumeration.linkage.ConditionTypeEnum;
import com.mqttsnet.thinglinks.service.execution.event.executionlog.ActionExecutionLogEvent;
import com.mqttsnet.thinglinks.service.execution.event.executionlog.ConditionExecutionLogEvent;
import com.mqttsnet.thinglinks.service.execution.event.executionlog.RuleExecutionLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * -----------------------------------------------------------------------------
 * File Name: ExecutionLogEventPublisher
 * -----------------------------------------------------------------------------
 * Description:
 * <p>
 * 执行日志事件发布器
 * -----------------------------------------------------------------------------
 *
 * @author mqttsnet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/12/2       mqttsnet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/12/2 19:33
 */
@Component
@Slf4j
public class ExecutionLogEventPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * 发布规则执行日志事件
     */
    public void publishRuleExecutionLog(Long ruleExecutionId, String ruleIdentification, String ruleName, LocalDateTime startTime, LocalDateTime endTime, String extendParams, String remark) {
        RuleExecutionLogEvent event = new RuleExecutionLogEvent(this, ruleExecutionId, ruleIdentification, ruleName, startTime, endTime, extendParams, remark);
        eventPublisher.publishEvent(event);
        log.info("Published rule execution log event: {}", event);
    }

    /**
     * 发布条件执行日志事件
     */
    public void publishConditionExecutionLog(Long ruleExecutionId, String conditionUuid, ConditionTypeEnum conditionTypeEnum, Boolean evaluationResult, LocalDateTime startTime, LocalDateTime endTime, String extendParams, String remark) {
        ConditionExecutionLogEvent event = new ConditionExecutionLogEvent(this, ruleExecutionId, conditionUuid, conditionTypeEnum, evaluationResult, startTime, endTime, extendParams, remark);
        eventPublisher.publishEvent(event);
        log.info("Published condition execution log event: {}", event);
    }

    /**
     * 发布动作执行日志事件
     */
    public void publishActionExecutionLog(Long ruleExecutionId, Integer actionType, String actionContent, Boolean result,
                                          LocalDateTime startTime, LocalDateTime endTime, String extendParams, String remark) {
        ActionExecutionLogEvent event = new ActionExecutionLogEvent(this, ruleExecutionId, actionType, actionContent, result,
                startTime, endTime, extendParams, remark);
        eventPublisher.publishEvent(event);
        log.info("Published action execution log event: {}", event);
    }
}