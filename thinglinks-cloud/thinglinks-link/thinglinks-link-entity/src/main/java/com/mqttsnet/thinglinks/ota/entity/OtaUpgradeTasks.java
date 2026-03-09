package com.mqttsnet.thinglinks.ota.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mqttsnet.basic.base.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * OTA升级任务表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-01-12 22:40:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "ota_upgrade_tasks", autoResultMap = true)
public class OtaUpgradeTasks extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 升级包ID，关联ota_upgrades表
     */
    @TableField(value = "upgrade_id", condition = EQUAL)
    private Long upgradeId;
    /**
     * 任务名称
     */
    @TableField(value = "task_name", condition = LIKE)
    private String taskName;

     /**
     * 升级模式
     */
    @TableField(value = "upgrade_method", condition = EQUAL)
    private Integer upgradeMethod;
    /**
     * 升级范围
     */
    @TableField(value = "upgrade_scope", condition = EQUAL)
    private Integer upgradeScope;

    /**
     * 任务状态(0:待发布、1:进行中、2:已完成、3:已取消)
     */
    @TableField(value = "task_status", condition = EQUAL)
    private Integer taskStatus;
    /**
     * 计划执行开始时间
     */
    @TableField(value = "scheduled_start_time", condition = EQUAL)
    private LocalDateTime scheduledStartTime;
    /**
     * 计划执行结束时间
     */
    @TableField(value = "scheduled_end_time", condition = EQUAL)
    private LocalDateTime scheduledEndTime;
    /**
     * 最大重试次数
     */
    @TableField(value = "max_retry_count", condition = EQUAL)
    private Integer maxRetryCount;

    /**
     * 当前重试次数
     */
    @TableField(value = "current_retry_count", condition = EQUAL)
    private Integer currentRetryCount;

    /**
     * 待升级的源版本号
     */
    @TableField(value = "source_versions", condition = EQUAL)
    private String sourceVersions;


    /**
     * APP确认升级
     */
    @TableField(value = "app_confirmation_required", condition = EQUAL)
    private Boolean appConfirmationRequired;


    /**
     * 升级速率(恒定速率升级，10-1000)
     */
    @TableField(value = "upgrade_rate", condition = EQUAL)
    private Integer upgradeRate;


    /**
     * 重试间隔分钟数(默认为10分钟)
     */
    @TableField(value = "retry_interval_minutes", condition = EQUAL)
    private Integer retryIntervalMinutes;

     /**
     * 设备升级超时时间(分钟)
     */
    @TableField(value = "device_upgrade_timeout", condition = EQUAL)
    private Integer deviceUpgradeTimeout;


    /**
     * 最新重试时间
     */
    @TableField(value = "last_retry_time", condition = EQUAL)
    private LocalDateTime lastRetryTime;

    /**
     * 任务描述
     */
    @TableField(value = "description", condition = LIKE)
    private String description;
    /**
     * 描述
     */
    @TableField(value = "remark", condition = LIKE)
    private String remark;
    /**
     * 创建人组织
     */
    @TableField(value = "created_org_id", condition = EQUAL)
    private Long createdOrgId;

    /**
     * 逻辑删除标识:0-未删除 1-已删除
     */
    @TableLogic
    @TableField(value = "deleted", condition = EQUAL)
    private Integer deleted;

}