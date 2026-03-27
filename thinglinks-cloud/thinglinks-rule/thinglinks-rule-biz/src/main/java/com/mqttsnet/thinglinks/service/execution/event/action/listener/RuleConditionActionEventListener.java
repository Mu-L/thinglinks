package com.mqttsnet.thinglinks.service.execution.event.action.listener;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.dto.linkage.RuleConditionActionPolicyDTO;
import com.mqttsnet.thinglinks.dto.linkage.execution.PolicyContext;
import com.mqttsnet.thinglinks.link.facade.DeviceCommandFacade;
import com.mqttsnet.thinglinks.protocol.vo.param.DeviceAlarmNotificationRequestParam;
import com.mqttsnet.thinglinks.protocol.vo.param.DeviceCommandWrapperParam;
import com.mqttsnet.thinglinks.service.alarm.RuleAlarmRecordService;
import com.mqttsnet.thinglinks.service.execution.event.action.AlertActionEvent;
import com.mqttsnet.thinglinks.service.execution.event.action.CommandActionEvent;
import com.mqttsnet.thinglinks.service.execution.event.action.ForwardActionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * -----------------------------------------------------------------------------
 * File Name: RuleConditionActionEventListener
 * -----------------------------------------------------------------------------
 * Description:
 * 规则条件动作事件监听
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
 * @date 2023/12/17 20:33
 */
@Slf4j
@Component
public class RuleConditionActionEventListener {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    private final LinkCacheDataHelper linkCacheDataHelper;

    @Autowired
    private DeviceCommandFacade deviceCommandApi;

    @Autowired
    private RuleAlarmRecordService ruleAlarmRecordService;

    public RuleConditionActionEventListener(LinkCacheDataHelper linkCacheDataHelper) {
        this.linkCacheDataHelper = linkCacheDataHelper;
    }


    @EventListener
    public void onCommandActionEvent(CommandActionEvent event) {
        RuleConditionActionPolicyDTO actionPolicyDTO = event.getActionPolicyDTO();
        log.info("规则条件动作事件监听：{}", actionPolicyDTO);

        try {
            // Convert actionContent to DeviceCommandWrapperParam
            DeviceCommandWrapperParam commandWrapper = objectMapper.readValue(actionPolicyDTO.getActionContent(), DeviceCommandWrapperParam.class);

            // Call the issueCommands method with the converted object
            R<?> response = deviceCommandApi.issueCommands(commandWrapper);
            log.info("Issued commands: {}", response.getData());

        } catch (JsonProcessingException e) {
            log.error("JSON processing error while converting actionContent to DeviceCommandWrapperParam: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error issuing commands: {}", e.getMessage(), e);
        }
    }

    @EventListener
    public void onAlertActionEvent(AlertActionEvent event) {
        RuleConditionActionPolicyDTO actionPolicyDTO = event.getActionPolicyDTO();
        PolicyContext policyContext = event.getPolicyContext();
        log.info("规则条件动作事件监听：{}", actionPolicyDTO);

        try {
            // Convert actionContent to DeviceAlarmNotificationRequestParam
            DeviceAlarmNotificationRequestParam requestParam = objectMapper.readValue(actionPolicyDTO.getActionContent(), DeviceAlarmNotificationRequestParam.class);
            // Process the content data
            String processed = Optional.ofNullable(requestParam.getContentData())
                    .map(content -> content
                                            .trim()
                                            .replaceAll("(?i)^\\s*<p[^>]*>\\s*", "")
                                            .replaceAll("(?i)\\s*</p>\\s*$", "")
                                            .replace("<br/>", "\n")
                                            // 合并空白字符
                                            .replaceAll("\\s+", " ")
                                    // 追加Markdown备注
                                    + buildPolicyMkRemark(policyContext))
                    .orElse("");
            requestParam.setContentData(processed);
            requestParam.setRemark(buildPolicyRemark(policyContext));
            ruleAlarmRecordService.triggerDeviceAlarm(requestParam);
        } catch (JsonProcessingException e) {
            log.error("JSON processing error while converting actionContent to DeviceAlarmNotificationRequestParam: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error triggering device alarm: {}", e.getMessage(), e);
        }
    }

    @EventListener
    public void onForwardActionEvent(ForwardActionEvent event) {
        // Handle alert triggering logic here
        // This could involve logging the alert, sending notifications, etc.
        RuleConditionActionPolicyDTO actionPolicyDTO = event.getActionPolicyDTO();
        log.info("规则条件动作事件监听：{}", actionPolicyDTO);
        // ... implement the alert triggering ...
    }


    /**
     * 构建Markdown格式策略执行备注
     */
    private String buildPolicyMkRemark(PolicyContext context) {
        return String.format(
                "「策略执行摘要」: \n"
                + "- **触发时间**: %s \n"
                + "- **租户标识**: `%s`  \n"
                + "- **规则名称**: %s  \n"
                + "- **规则标识**: `%s`  \n"
                + "- **执行日志流水号**: `%s`\n\n",
                DateUtil.now(),
                Optional.ofNullable(context.getTenantId())
                        .orElse("系统默认租户"),
                Optional.ofNullable(context.getRuleName())
                        .map(name -> "`" + name + "`")
                        .orElse("*未命名规则*"),
                context.getRuleIdentification(),
                context.getRuleExecutionId()
        );
    }

    /**
     * 构建普通文本格式策略备注
     */
    private String buildPolicyRemark(PolicyContext context) {
        return String.format(
                "「策略执行摘要」:"
                + "触发时间：%s\n"
                + "租户标识：%s\n"
                + "规则名称：%s\n"
                + "规则标识：%s\n"
                + "执行日志流水号：%s\n\n",
                DateUtil.now(),
                Optional.ofNullable(context.getTenantId())
                        .orElse("系统默认租户"),
                Optional.ofNullable(context.getRuleName())
                        .orElse("未命名规则"),
                context.getRuleIdentification(),
                context.getRuleExecutionId()
        );
    }


}
