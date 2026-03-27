package com.mqttsnet.thinglinks.ota.entity;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;

import java.io.Serial;
import java.time.LocalDateTime;

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


/**
 * <p>
 * 实体类
 * OTA升级记录表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-01-12 22:42:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "ota_upgrade_records", autoResultMap = true)
public class OtaUpgradeRecords extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 升级包ID，关联ota_upgrades表
     */
    @TableField(value = "upgrade_id", condition = EQUAL)
    private Long upgradeId;

    /**
     * 任务ID，关联ota_upgrade_tasks表
     */
    @TableField(value = "task_id", condition = EQUAL)
    private Long taskId;
    /**
     * 设备标识
     */
    @TableField(value = "device_identification", condition = LIKE)
    private String deviceIdentification;
    /**
     * 升级状态(0:待升级、1:升级中、2:升级成功、3:升级失败)
     */
    @TableField(value = "upgrade_status", condition = EQUAL)
    private Integer upgradeStatus;

    /**
     * 待升级的源版本号
     */
    @TableField(value = "source_version", condition = EQUAL)
    private String sourceVersion;

    /**
     * 目标版本号
     */
    @TableField(value = "target_version", condition = EQUAL)
    private String targetVersion;


    /**
     * APP确认状态
     */
    @TableField(value = "app_confirmation_status", condition = EQUAL)
    private Integer appConfirmationStatus;


    /**
     * APP确认时间
     */
    @TableField(value = "app_confirmation_time", condition = EQUAL)
    private LocalDateTime appConfirmationTime;

    /**
     *指令下发状态
     */
    @TableField(value = "command_send_status", condition = EQUAL)
    private Integer commandSendStatus;



    /**
     * 最新指令下发时间
     */
    @TableField(value = "last_command_send_time", condition = EQUAL)
    private LocalDateTime lastCommandSendTime;


    /**
     *OTA指令内容
     */
    @TableField(value = "command_content", condition = EQUAL)
    private String commandContent;


    /**
     * 升级进度（百分比）
     */
    @TableField(value = "progress", condition = EQUAL)
    private Integer progress;
    /**
     * 错误代码
     */
    @TableField(value = "error_code", condition = LIKE)
    private String errorCode;
    /**
     * 错误信息
     */
    @TableField(value = "error_message", condition = LIKE)
    private String errorMessage;
    /**
     * 升级开始时间
     */
    @TableField(value = "start_time", condition = EQUAL)
    private LocalDateTime startTime;
    /**
     * 升级结束时间
     */
    @TableField(value = "end_time", condition = EQUAL)
    private LocalDateTime endTime;
    /**
     * 升级成功详细信息
     */
    @TableField(value = "success_details", condition = LIKE)
    private String successDetails;
    /**
     * 升级失败详细信息
     */
    @TableField(value = "failure_details", condition = LIKE)
    private String failureDetails;
    /**
     * 升级过程日志
     */
    @TableField(value = "log_details", condition = LIKE)
    private String logDetails;
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
