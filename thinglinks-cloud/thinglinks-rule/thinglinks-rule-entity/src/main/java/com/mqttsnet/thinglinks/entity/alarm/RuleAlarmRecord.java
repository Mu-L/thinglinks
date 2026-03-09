package com.mqttsnet.thinglinks.entity.alarm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mqttsnet.basic.base.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 告警记录表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-09 21:15:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("rule_alarm_record")
public class RuleAlarmRecord extends Entity<Long> {
    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
    @TableField(value = "app_id", condition = LIKE)
    private String appId;
    /**
     * 告警编码
     */
    @TableField(value = "alarm_identification", condition = LIKE)
    private String alarmIdentification;
    /**
     * 发生时间
     */
    @TableField(value = "occurred_time", condition = EQUAL)
    private LocalDateTime occurredTime;
    /**
     * 处理时间
     */
    @TableField(value = "handled_time", condition = EQUAL)
    private LocalDateTime handledTime;
    /**
     * 处理记录
     */
    @TableField(value = "handling_notes", condition = LIKE)
    private String handlingNotes;
    /**
     * 解决时间
     */
    @TableField(value = "resolved_time", condition = EQUAL)
    private LocalDateTime resolvedTime;
    /**
     * 解决记录
     */
    @TableField(value = "resolution_notes", condition = LIKE)
    private String resolutionNotes;
    /**
     * 告警具体内容信息
     */
    @TableField(value = "content_data", condition = LIKE)
    private String contentData;
    /**
     * 处理状态
     */
    @TableField(value = "handled_status", condition = EQUAL)
    private Integer handledStatus;
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


}
