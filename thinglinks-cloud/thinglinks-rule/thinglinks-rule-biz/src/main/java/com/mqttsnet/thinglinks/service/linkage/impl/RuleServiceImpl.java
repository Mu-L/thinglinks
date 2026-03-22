package com.mqttsnet.thinglinks.service.linkage.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.condition.model.dto.AppointEffectiveTimeDTO;
import com.mqttsnet.basic.condition.utils.ConditionCronUtil;
import com.mqttsnet.basic.context.ContextConstants;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.jackson.JsonUtil;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.basic.utils.SnowflakeIdUtil;
import com.mqttsnet.basic.utils.StringUtils;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.common.constant.JobConstant;
import com.mqttsnet.thinglinks.dto.linkage.RuleConditionActionPolicyDTO;
import com.mqttsnet.thinglinks.dto.linkage.RuleConditionPolicyDTO;
import com.mqttsnet.thinglinks.dto.linkage.RulePolicyDTO;
import com.mqttsnet.thinglinks.dto.linkage.execution.PolicyContext;
import com.mqttsnet.thinglinks.entity.linkage.Rule;
import com.mqttsnet.thinglinks.enumeration.linkage.RuleStatusEnum;
import com.mqttsnet.thinglinks.job.dto.JobReturnT;
import com.mqttsnet.thinglinks.job.dto.XxlJobInfoVO;
import com.mqttsnet.thinglinks.job.dto.XxlJobTriggerStatusEnum;
import com.mqttsnet.thinglinks.job.facade.JobFacade;
import com.mqttsnet.thinglinks.manager.linkage.RuleManager;
import com.mqttsnet.thinglinks.service.execution.service.RuleExecutionService;
import com.mqttsnet.thinglinks.service.linkage.RuleConditionActionService;
import com.mqttsnet.thinglinks.service.linkage.RuleConditionService;
import com.mqttsnet.thinglinks.service.linkage.RuleService;
import com.mqttsnet.thinglinks.vo.query.linkage.RuleConditionActionPageQuery;
import com.mqttsnet.thinglinks.vo.query.linkage.RuleConditionPageQuery;
import com.mqttsnet.thinglinks.vo.result.linkage.RuleConditionActionDetailsResultVO;
import com.mqttsnet.thinglinks.vo.result.linkage.RuleConditionDetailsResultVO;
import com.mqttsnet.thinglinks.vo.result.linkage.RuleDetailsResultVO;
import com.mqttsnet.thinglinks.vo.save.linkage.RuleSaveVO;
import com.mqttsnet.thinglinks.vo.update.linkage.RuleUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
 * 规则信息
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-19 23:20:14
 * @create [2023-07-19 23:20:14] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class RuleServiceImpl extends SuperServiceImpl<RuleManager, Long, Rule> implements RuleService {

    private final RuleConditionService ruleConditionService;

    private final RuleConditionActionService ruleConditionActionService;

    private final JobFacade jobFacade;

    private final RuleExecutionService ruleExecutionService;

    /**
     * Saves the rule information.
     *
     * @param saveVO The value object containing details for saving.
     * @return The saved rule.
     */
    @Override
    public Rule saveRule(RuleSaveVO saveVO) {
        log.info("saveRule saveVO:{}", saveVO);

        checkedRuleSaveVO(saveVO);
        Rule rule = builderRuleSaveVO(saveVO);

        if (saveRuleToSuperManager(rule)) {
            XxlJobTriggerStatusEnum jobTriggerStatusEnum = Objects.equals(saveVO.getStatus(), RuleStatusEnum.ACTIVATED.getValue())
                    ? XxlJobTriggerStatusEnum.RUNNING : XxlJobTriggerStatusEnum.STOPPED;
            scheduleTimingTask(rule, jobTriggerStatusEnum);
        }

        return rule;
    }

    /**
     * Saves the rule to the super manager.
     *
     * @param rule The rule to be saved.
     * @return true if the rule was saved successfully, false otherwise.
     */
    private boolean saveRuleToSuperManager(Rule rule) {
        try {
            return superManager.save(rule);
        } catch (Exception e) {
            log.error("Failed to save rule: {}", e.getMessage(), e);
            throw BizException.wrap("Failed to save rule");
        }
    }

    /**
     * Schedules a timing task for the rule.
     *
     * @param rule                 The rule for which the timing task is to be scheduled.
     * @param jobTriggerStatusEnum The job trigger status enum.
     */
    private void scheduleTimingTask(Rule rule, XxlJobTriggerStatusEnum jobTriggerStatusEnum) {

        AppointEffectiveTimeDTO appointEffectiveTimeDTO = Optional.ofNullable(JsonUtil.parse(rule.getAppointContent(), AppointEffectiveTimeDTO.class))
                .orElseThrow(() -> BizException.wrap("Invalid appoint content in rule"));

        String cron = ConditionCronUtil.secondsToCron(appointEffectiveTimeDTO.getFrequency());
        ArgumentAssert.isTrue(ConditionCronUtil.validateCronExpression(cron), "Cron expression is not valid");

        Map<String, String> param = MapUtil.builder(ContextConstants.TENANT_ID_HEADER, ContextUtil.getTenantId().toString())
                .put("ruleIdentification", rule.getRuleIdentification())
                .build();

        XxlJobInfoVO xxlJobInfoVO = XxlJobInfoVO.createFromCronExpression(JobConstant.DEF_IOT_JOB_GROUP_NAME,
                "【Scene linkage rule】" + rule.getRuleIdentification(),
                cron,
                JobConstant.SCENE_LINKAGE_RULE_JOB_HANDLER,
                JsonUtil.toJson(param), jobTriggerStatusEnum);

        AppointEffectiveTimeDTO effectiveTimeDTO = saveTimingTaskToJobApi(xxlJobInfoVO, appointEffectiveTimeDTO);
        superManager.updateById(rule.setAppointContent(JsonUtil.toJson(effectiveTimeDTO)));

    }

    /**
     * Saves the timing task to the Job API.
     *
     * @param xxlJobInfoVO            The job information value object.
     * @param appointEffectiveTimeDTO The appointment effective time data transfer object.
     * @return The updated appointment effective time data transfer object.
     */
    private AppointEffectiveTimeDTO saveTimingTaskToJobApi(XxlJobInfoVO xxlJobInfoVO, AppointEffectiveTimeDTO appointEffectiveTimeDTO) {
        try {
            JobReturnT<String> saveTimingTaskR = jobFacade.saveTimingTask(xxlJobInfoVO);
            if (saveTimingTaskR.getCode() == JobReturnT.SUCCESS_CODE) {
                log.info("saveRule saveTimingTaskR:{}", saveTimingTaskR);
                appointEffectiveTimeDTO.setTaskId(saveTimingTaskR.getContent());
                return appointEffectiveTimeDTO;
            } else {
                log.error("Failed to save timing task: {}", saveTimingTaskR.getMsg());
                throw BizException.wrap("Failed to save timing task");
            }
        } catch (Exception e) {
            log.error("Failed to save timing task: {}", e.getMessage(), e);
            throw BizException.wrap("Failed to save timing task", e);
        }
    }


    /**
     * Updates the rule based on the provided updateVO.
     *
     * @param updateVO The value object containing update details.
     * @return The updated rule.
     */
    @Override
    public Rule updateRule(RuleUpdateVO updateVO) {
        log.info("updateRule updateVO:{}", updateVO);

        // Validate updateVO.
        checkedRuleUpdateVO(updateVO);

        // Load the existing rule from the database or cache.
        Rule existingRule = Optional.ofNullable(superManager.getById(updateVO.getId()))
                .orElseThrow(() -> BizException.wrap("The rule does not exist"));

        // Parse the existing appointment content.
        AppointEffectiveTimeDTO existingAppoint = JsonUtil.parse(existingRule.getAppointContent(), AppointEffectiveTimeDTO.class);

        // Parse the new appointment content.
        AppointEffectiveTimeDTO newAppoint = Optional.ofNullable(JsonUtil.parse(updateVO.getAppointContent(), AppointEffectiveTimeDTO.class))
                .orElseThrow(() -> BizException.wrap("Invalid appoint content in updateVO"));

        // Check if the frequency has changed or the status has changed. If yes, handle job updates.
        if (!Objects.equals(existingAppoint.getFrequency(), newAppoint.getFrequency())
                || !Objects.equals(existingRule.getStatus(), updateVO.getStatus())) {
            XxlJobTriggerStatusEnum jobTriggerStatusEnum = Objects.equals(updateVO.getStatus(), RuleStatusEnum.ACTIVATED.getValue())
                    ? XxlJobTriggerStatusEnum.RUNNING : XxlJobTriggerStatusEnum.STOPPED;
            handleJobUpdates(existingAppoint, updateVO, existingRule, jobTriggerStatusEnum);
        }


        // Map values from updateVO to the existingRule.
        updateRulePropertiesFromVO(existingRule, updateVO);

        // Save the updated rule.
        superManager.updateById(existingRule);

        return existingRule;
    }

    /**
     * Handles the job updates when frequency changes.
     *
     * @param existingAppoint      The existing appointment.
     * @param updateVO             The value object containing update details.
     * @param existingRule         The existing rule.
     * @param jobTriggerStatusEnum The job trigger status enum.
     */
    private void handleJobUpdates(AppointEffectiveTimeDTO existingAppoint, RuleUpdateVO updateVO, Rule existingRule, XxlJobTriggerStatusEnum jobTriggerStatusEnum) {
        // Load job details based on task ID.
        ArgumentAssert.notBlank(existingAppoint.getTaskId(), "The scheduling task ID cannot be empty");
        JobReturnT<XxlJobInfoVO> jobInfoReturn = jobFacade.loadById(Integer.valueOf(existingAppoint.getTaskId()));
        if (jobInfoReturn.getCode() != JobReturnT.SUCCESS_CODE) {
            log.error("Failed to load job info by id: {}", jobInfoReturn.getMsg());
            throw BizException.wrap("Failed to load job info");
        }

        // Fetch the job info from the returned object.
        XxlJobInfoVO jobInfoVO = Optional.ofNullable(jobInfoReturn.getContent())
                .orElseThrow(() -> BizException.wrap("Empty job info content"));

        // Construct the new appointment DTO.
        AppointEffectiveTimeDTO newAppointEffectiveTimeDTO = JsonUtil.parse(updateVO.getAppointContent(), AppointEffectiveTimeDTO.class);

        // Update the cron expression.
        String newCron = ConditionCronUtil.secondsToCron(newAppointEffectiveTimeDTO.getFrequency());
        XxlJobInfoVO updatedJobInfoVO = XxlJobInfoVO.updateFromCronExpression(
                jobInfoVO,
                "【Scene linkage rule】" + existingRule.getRuleIdentification(),
                newCron,
                JobConstant.SCENE_LINKAGE_RULE_JOB_HANDLER,
                JsonUtil.toJson(MapUtil.builder(ContextConstants.TENANT_ID_HEADER, ContextUtil.getTenantId().toString())
                        .put("ruleIdentification", existingRule.getRuleIdentification())
                        .build()), jobTriggerStatusEnum
        );

        JobReturnT<String> stringJobReturnT = jobFacade.updateJob(updatedJobInfoVO);
        if (stringJobReturnT.getCode() != JobReturnT.SUCCESS_CODE) {
            log.error("Failed to update job: {}", stringJobReturnT.getMsg());
            throw BizException.wrap("Failed to update job");
        }

    }

    /**
     * Updates rule properties from the update value object.
     *
     * @param existingRule The existing rule.
     * @param updateVO     The value object containing update details.
     */
    private void updateRulePropertiesFromVO(Rule existingRule, RuleUpdateVO updateVO) {
        BeanPlusUtil.copyProperties(updateVO, existingRule);
    }


    /**
     * 删除规则信息
     *
     * @param id 规则ID
     * @return {@link Boolean} 删除结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteRule(Long id) {
        ArgumentAssert.notNull(id, "id Cannot be null");

        Rule rule = superManager.getById(id);
        if (null == rule) {
            throw BizException.wrap("The Rule does not exist");
        }

        // Remove the job if it exists.
        boolean jobRemoved = removeAssociatedJob(rule);
        log.info("removed job result: {}", jobRemoved);
        return superManager.removeById(id);
    }

    /**
     * 删除关联的定时任务
     *
     * @param rule 规则实体
     * @return {@link Boolean} 删除结果
     */
    private boolean removeAssociatedJob(Rule rule) {
        try {
            AppointEffectiveTimeDTO appointEffectiveTimeDTO = JsonUtil.parse(rule.getAppointContent(), AppointEffectiveTimeDTO.class);
            if (null == appointEffectiveTimeDTO || StringUtils.isBlank(appointEffectiveTimeDTO.getTaskId())) {
                return true;
            }
            Integer taskId = Integer.valueOf(appointEffectiveTimeDTO.getTaskId().trim());
            // 先检查任务是否存在
            JobReturnT<XxlJobInfoVO> jobInfoReturn = jobFacade.loadById(taskId);
            if (jobInfoReturn.getCode() != JobReturnT.SUCCESS_CODE) {
                log.warn("Job not found with id: {}, message: {}", taskId, jobInfoReturn.getMsg());
                return true;
            }

            // 删除任务
            JobReturnT<String> removeResult = jobFacade.removeJob(taskId);
            if (removeResult.getCode() != JobReturnT.SUCCESS_CODE) {
                log.error("Failed to remove job {}: {}", taskId, removeResult.getMsg());
                throw BizException.wrap("Failed to remove associated job");
            }

            log.info("Successfully removed job: {}", taskId);
            return true;

        } catch (NumberFormatException e) {
            log.error("Invalid taskId format in rule {}: {}", rule.getId(), e.getMessage());
            throw BizException.wrap("Invalid taskId format");
        } catch (Exception e) {
            log.error("Error removing associated job for rule {}", rule.getId(), e);
            throw BizException.wrap("Error removing associated job");
        }
    }


    /**
     * 根据规则ID更新规则状态
     *
     * @param id     设备ID
     * @param status 规则状态
     * @return {@link Boolean} 更新结果
     */
    @Override
    public Boolean updateRuleStatus(Long id, Integer status) {
        ArgumentAssert.notNull(id, "id Cannot be null");
        ArgumentAssert.notNull(status, "status Cannot be null");
        Rule rule = superManager.getById(id);
        if (null == rule) {
            throw BizException.wrap("The rule does not exist");
        }
        if (Objects.equals(status, rule.getStatus())) {
            throw BizException.wrap("The rule status is the same as the current status");
        }
        rule.setStatus(status);
        return superManager.updateById(rule);
    }

    /**
     * 根据规则ID获取规则详情
     *
     * @param id 规则ID
     * @return {@link RuleDetailsResultVO} 规则详情
     */
    @Override
    public RuleDetailsResultVO getRuleDetails(Long id) {
        RuleDetailsResultVO ruleDetails = Optional.ofNullable(superManager.getById(id))
                .map(rule -> BeanPlusUtil.toBeanIgnoreError(rule, RuleDetailsResultVO.class))
                .orElseThrow(() -> new BizException("Rule not found"));


        RuleConditionPageQuery ruleConditionPageQuery = new RuleConditionPageQuery();
        ruleConditionPageQuery.setRuleId(id);
        List<RuleConditionDetailsResultVO> conditions = Optional.ofNullable(ruleConditionService.getRuleConditionList(ruleConditionPageQuery))
                .orElse(Collections.emptyList())
                .stream()
                .map(condition -> {
                    RuleConditionDetailsResultVO conditionDetails = BeanPlusUtil.toBeanIgnoreError(condition, RuleConditionDetailsResultVO.class);
                    RuleConditionActionPageQuery ruleConditionActionPageQuery = new RuleConditionActionPageQuery();
                    ruleConditionActionPageQuery.setRuleConditionId(condition.getId());
                    List<RuleConditionActionDetailsResultVO> actions = Optional.ofNullable(ruleConditionActionService.getRuleConditionActionList(ruleConditionActionPageQuery))
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(action -> BeanPlusUtil.toBeanIgnoreError(action, RuleConditionActionDetailsResultVO.class))
                            .collect(Collectors.toList());
                    conditionDetails.setConditionActionDetailsResultVOS(actions);
                    return conditionDetails;
                })
                .collect(Collectors.toList());

        ruleDetails.setConditionDetailsResultVOS(conditions);
        return ruleDetails;
    }


    /**
     * 根据规则标识获取规则详情
     *
     * @param ruleIdentification 规则标识
     * @return {@link RuleDetailsResultVO} 规则详情
     */
    @Override
    public RuleDetailsResultVO getRuleDetailsByIdentification(String ruleIdentification) {
        RuleDetailsResultVO ruleDetails = Optional.ofNullable(superManager.findOneByRuleIdentification(ruleIdentification))
                .map(rule -> BeanPlusUtil.toBeanIgnoreError(rule, RuleDetailsResultVO.class))
                .orElseThrow(() -> new BizException("Rule not found"));

        RuleConditionPageQuery ruleConditionPageQuery = new RuleConditionPageQuery();
        ruleConditionPageQuery.setRuleId(ruleDetails.getId());
        List<RuleConditionDetailsResultVO> conditions = Optional.ofNullable(ruleConditionService.getRuleConditionList(ruleConditionPageQuery))
                .orElse(Collections.emptyList())
                .stream()
                .map(condition -> {
                    RuleConditionDetailsResultVO conditionDetails = BeanPlusUtil.toBeanIgnoreError(condition, RuleConditionDetailsResultVO.class);
                    RuleConditionActionPageQuery ruleConditionActionPageQuery = new RuleConditionActionPageQuery();
                    ruleConditionActionPageQuery.setRuleConditionId(condition.getId());
                    List<RuleConditionActionDetailsResultVO> actions = Optional.ofNullable(ruleConditionActionService.getRuleConditionActionList(ruleConditionActionPageQuery))
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(action -> BeanPlusUtil.toBeanIgnoreError(action, RuleConditionActionDetailsResultVO.class))
                            .collect(Collectors.toList());
                    conditionDetails.setConditionActionDetailsResultVOS(actions);
                    return conditionDetails;
                })
                .collect(Collectors.toList());

        ruleDetails.setConditionDetailsResultVOS(conditions);
        return ruleDetails;
    }


    /**
     * 触发规则策略
     *
     * @param tenantId           租户ID
     * @param ruleIdentification 规则标识
     * @return {@link RuleDetailsResultVO} 规则详情
     */
    @Override
    public RuleDetailsResultVO triggerRulePolicy(Long tenantId, String ruleIdentification) {
        return doTriggerRulePolicy(tenantId, ruleIdentification);
    }

    private RuleDetailsResultVO doTriggerRulePolicy(Long tenantId, String ruleIdentification) {
        try {
            // Retrieve rule details based on the rule identification
            RuleDetailsResultVO ruleDetails = getRuleDetailsByIdentification(ruleIdentification);

            if (Objects.isNull(ruleDetails)) {
                throw BizException.wrap("ruleIdentification is not exist");
            }

            // Set tenant ID in policy context
            PolicyContext policyContext = new PolicyContext();
            policyContext.setRuleExecutionId(Long.valueOf(SnowflakeIdUtil.nextId()));
            policyContext.setRuleIdentification(ruleIdentification);
            policyContext.setRuleName(ruleDetails.getRuleName());
            policyContext.setTenantId(tenantId.toString());

            // Convert rule details to policy DTO
            RulePolicyDTO rulePolicyDTO = convertToRulePolicyDTO(ruleDetails);

            // Execute the policy
            policyContext.setRulePolicyDTO(rulePolicyDTO);
            ruleExecutionService.executePolicy(policyContext);

            return ruleDetails;
        } catch (Exception e) {
            log.warn("Error executing rule policy for Tenant ID: {}, Rule Identification: {}", tenantId, ruleIdentification, e);
            throw BizException.wrap("Failed to execute rule policy", e);
        }
    }

    private RulePolicyDTO convertToRulePolicyDTO(RuleDetailsResultVO ruleDetails) {
        RulePolicyDTO rulePolicyDTO = BeanPlusUtil.toBeanIgnoreError(ruleDetails, RulePolicyDTO.class);
        List<RuleConditionPolicyDTO> conditionPolicyDTOs = ruleDetails.getConditionDetailsResultVOS().stream()
                .map(conditionDetails -> {
                    RuleConditionPolicyDTO conditionPolicyDTO = BeanPlusUtil.toBeanIgnoreError(conditionDetails, RuleConditionPolicyDTO.class);
                    List<RuleConditionActionPolicyDTO> actionPolicyDTOs = conditionDetails.getConditionActionDetailsResultVOS().stream()
                            .map(actionDetails -> BeanPlusUtil.toBeanIgnoreError(actionDetails, RuleConditionActionPolicyDTO.class))
                            .collect(Collectors.toList());
                    conditionPolicyDTO.setConditionActionPolicyDTOS(actionPolicyDTOs);
                    return conditionPolicyDTO;
                })
                .collect(Collectors.toList());
        rulePolicyDTO.setRuleConditionPolicyDTOS(conditionPolicyDTOs);
        return rulePolicyDTO;
    }

    /**
     * 新增 校验参数
     *
     * @param saveVO
     */
    private void checkedRuleSaveVO(RuleSaveVO saveVO) {

        ArgumentAssert.notBlank(saveVO.getAppId(), "AppId Cannot be null");
        //规则信息状态
        ArgumentAssert.notNull(saveVO.getStatus(), "Status Cannot be null");
        if (!RuleStatusEnum.STATE_COLLECTION.contains(saveVO.getStatus())) {
            throw BizException.wrap("Status is not exist");
        }
    }

    /**
     * 新增 构建参数
     *
     * @param saveVO
     * @return
     */
    private Rule builderRuleSaveVO(RuleSaveVO saveVO) {
        Rule rule = BeanUtil.toBeanIgnoreError(saveVO, Rule.class);
        rule.setRuleIdentification(String.valueOf(SnowflakeIdUtil.nextId()));
        rule.setCreatedOrgId(ContextUtil.getCurrentDeptId());
        return rule;
    }

    /**
     * 修改 校验参数
     *
     * @param updateVO
     */
    private void checkedRuleUpdateVO(RuleUpdateVO updateVO) {
        ArgumentAssert.notNull(updateVO.getId(), "id Cannot be null");
        //产品模型状态
        ArgumentAssert.notNull(updateVO.getStatus(), "Status Cannot be null");
        if (!RuleStatusEnum.STATE_COLLECTION.contains(updateVO.getStatus())) {
            throw BizException.wrap("Status is not exist");
        }
    }

}


