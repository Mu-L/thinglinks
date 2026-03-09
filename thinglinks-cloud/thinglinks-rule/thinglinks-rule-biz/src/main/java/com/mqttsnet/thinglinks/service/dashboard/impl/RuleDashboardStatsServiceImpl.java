package com.mqttsnet.thinglinks.service.dashboard.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.entity.alarm.RuleAlarmRecord;
import com.mqttsnet.thinglinks.enumeration.alarm.AlarmRecordHandledStatusEnum;
import com.mqttsnet.thinglinks.service.alarm.RuleAlarmRecordService;
import com.mqttsnet.thinglinks.service.alarm.RuleAlarmService;
import com.mqttsnet.thinglinks.service.dashboard.RuleDashboardStatsService;
import com.mqttsnet.thinglinks.service.linkage.RuleInstanceService;
import com.mqttsnet.thinglinks.service.linkage.RuleService;
import com.mqttsnet.thinglinks.service.plugin.PluginInfoService;
import com.mqttsnet.thinglinks.service.script.RuleGroovyScriptService;
import com.mqttsnet.thinglinks.vo.result.alarm.RuleAlarmRecordResultVO;
import com.mqttsnet.thinglinks.vo.result.dashboard.RuleDashboardSummaryResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * -----------------------------------------------------------------------------
 * File Name: RuleDashboardStatsServiceImpl.java
 * -----------------------------------------------------------------------------
 * Description:
 * 仪表盘数据业务层接口实现类
 * -----------------------------------------------------------------------------
 *
 * @author ShiHuan Sun
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * <p>
 * -----------------------------------------------------------------------------
 * @email 13733918655@163.com
 * @date 2024-10-22 17:02
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class RuleDashboardStatsServiceImpl implements RuleDashboardStatsService {

    private final RuleService ruleService;

    private final RuleAlarmService ruleAlarmService;

    private final RuleAlarmRecordService ruleAlarmRecordService;

    private final RuleInstanceService ruleInstanceService;

    private final PluginInfoService pluginInfoService;

    private final RuleGroovyScriptService ruleGroovyScriptService;


    /**
     * 获取仪表盘概要统计信息
     *
     * @return {@link RuleDashboardSummaryResultVO} 仪表盘概要统计信息
     */
    @Override
    public RuleDashboardSummaryResultVO getDashboardAssetSummary() {
        RuleDashboardSummaryResultVO dashboardSummary = new RuleDashboardSummaryResultVO();

        // 统计规则总数量
        long totalRulesCount = getTotalRulesCount();
        dashboardSummary.setTotalRulesCount(totalRulesCount);

        // 统计规则实例数量
        long totalRuleInstancesCount = getTotalRuleInstancesCount();
        dashboardSummary.setTotalRuleInstancesCount(totalRuleInstancesCount);

        // 统计规则插件数量
        long totalPluginCount = getTotalPluginCount();
        dashboardSummary.setTotalPluginCount(totalPluginCount);

        // 统计规则脚本数量
        long totalRuleGroovyScriptCount = getTotalRuleGroovyScriptCount();
        dashboardSummary.setTotalRuleGroovyScriptCount(totalRuleGroovyScriptCount);

        // 统计规则告警配置数量
        long totalRuleAlarmsCount = getTotalRuleAlarmsCount();
        dashboardSummary.setTotalRuleAlarmsCount(totalRuleAlarmsCount);

        // 统计告警记录数量
        long totalAlarmRecordsCount = getTotalAlarmRecordsCount();
        dashboardSummary.setTotalAlarmRecordsCount(totalAlarmRecordsCount);

        // 获取最近 30 条告警记录
        List<RuleAlarmRecordResultVO> recentAlarmRecords = getRecentAlarmRecords();
        dashboardSummary.setRuleAlarmRecordResultVOList(recentAlarmRecords);

        return dashboardSummary;
    }


    private long getTotalRulesCount() {
        try {
            return ruleService.selectCount(new QueryWrapper<>());
        } catch (Exception e) {
            log.error("Error fetching total rules count: {}", e.getMessage());
            return 0;
        }
    }

    private long getTotalRuleInstancesCount() {
        try {
            return ruleInstanceService.selectCount(new QueryWrapper<>());
        } catch (Exception e) {
            log.error("Error fetching total rule instances count: {}", e.getMessage());
            return 0;
        }
    }

    private long getTotalPluginCount() {
        try {
            return pluginInfoService.selectCount(new QueryWrapper<>());
        } catch (Exception e) {
            log.error("Error fetching total plugin count: {}", e.getMessage());
            return 0;
        }
    }

    private long getTotalRuleGroovyScriptCount() {
        try {
            return ruleGroovyScriptService.selectCount(new QueryWrapper<>());
        } catch (Exception e) {
            log.error("Error fetching total groovy script count: {}", e.getMessage());
            return 0;
        }
    }

    private long getTotalRuleAlarmsCount() {
        try {
            return ruleAlarmService.selectCount(new QueryWrapper<>());
        } catch (Exception e) {
            log.error("Error fetching total rule alarms count: {}", e.getMessage());
            return 0;
        }
    }

    private long getTotalAlarmRecordsCount() {
        try {
            return ruleAlarmRecordService.selectCount(new QueryWrapper<>());
        } catch (Exception e) {
            log.error("Error fetching total alarm records count: {}", e.getMessage());
            return 0;
        }
    }

    // 获取最近 30 条告警记录
    private List<RuleAlarmRecordResultVO> getRecentAlarmRecords() {
        try {
            // 查询条件
            LambdaQueryWrapper<RuleAlarmRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(RuleAlarmRecord::getHandledStatus, Arrays.asList(AlarmRecordHandledStatusEnum.PENDING.getValue(), AlarmRecordHandledStatusEnum.IN_PROGRESS.getValue()));
            lambdaQueryWrapper.orderByDesc(RuleAlarmRecord::getCreatedTime);
            lambdaQueryWrapper.last("LIMIT 30");

            // 获取告警记录列表
            List<RuleAlarmRecord> alarmRecords = ruleAlarmRecordService.list(lambdaQueryWrapper);

            // 过滤掉 null 值，使用 BeanPlusUtil.toBeanIgnoreError 进行转换
            return alarmRecords.stream().filter(Objects::nonNull)
                    .map(record -> {
                        try {
                            return BeanPlusUtil.toBeanIgnoreError(record, RuleAlarmRecordResultVO.class);
                        } catch (Exception e) {
                            log.error("Error converting RuleAlarmRecord to RuleAlarmRecordResultVO: {}", e.getMessage());
                            return null; // 如果转换失败，返回 null
                        }
                    }).filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching recent alarm records: {}", e.getMessage());
            return Collections.emptyList();
        }
    }


}
