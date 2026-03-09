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

import java.io.Serial;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 告警规则表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-09 21:14:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("rule_alarm")
public class RuleAlarm extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
    @TableField(value = "app_id", condition = LIKE)
    private String appId;
    /**
     * 告警名称
     */
    @TableField(value = "alarm_name", condition = LIKE)
    private String alarmName;
    /**
     * 告警编码
     */
    @TableField(value = "alarm_identification", condition = LIKE)
    private String alarmIdentification;
    /**
     * 告警场景
     */
    @TableField(value = "alarm_scene", condition = LIKE)
    private String alarmScene;
    /**
     * 告警渠道ID集合
     */
    @TableField(value = "alarm_channel_ids", condition = LIKE)
    private String alarmChannelIds;
    /**
     * 告警级别
     */
    @TableField(value = "level", condition = EQUAL)
    private Integer level;
    /**
     * 启用状态
     */
    @TableField(value = "status", condition = EQUAL)
    private Integer status;
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
