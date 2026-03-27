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

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 告警规则渠道表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-09 21:14:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("rule_alarm_channel")
public class RuleAlarmChannel extends Entity<Long> {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道名称
     */
    @TableField(value = "channel_name", condition = LIKE)
    private String channelName;
    /**
     * 渠道类型
     */
    @TableField(value = "channel_type", condition = EQUAL)
    private Integer channelType;
    /**
     * 告警配置
     */
    @TableField(value = "channel_config", condition = LIKE)
    private String channelConfig;
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
