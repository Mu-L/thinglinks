package com.mqttsnet.thinglinks.device.entity;

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

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 设备访问控制(ACL)规则表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-06-11 19:57:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "device_acl_rule", autoResultMap = true)
public class DeviceAclRule extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 规则名称
     */
    @TableField(value = "rule_name", condition = LIKE)
    private String ruleName;

    /**
     * 产品标识
     */
    @TableField(value = "product_identification", condition = EQUAL)
    private String productIdentification;

    /**
     * 设备标识
     */
    @TableField(value = "device_identification", condition = EQUAL)
    private String deviceIdentification;

    /**
     * 规则级别(0:产品级、1:设备级)
     */
    @TableField(value = "rule_level", condition = EQUAL)
    private Integer ruleLevel;

    /**
     * 动作类型((0:全部、1:发布、2:订阅、3:取消订阅))
     */
    @TableField(value = "action_type", condition = EQUAL)
    private Integer actionType;
    /**
     * 规则优先级(0-1000,值越小优先级越高)
     */
    @TableField(value = "priority", condition = EQUAL)
    private Integer priority;
    /**
     * MQTT主题模式(支持通配符)
     */
    @TableField(value = "topic_pattern", condition = LIKE)
    private String topicPattern;
    /**
     * IP白名单地址(多个用逗号分隔)
     */
    @TableField(value = "ip_whitelist", condition = LIKE)
    private String ipWhitelist;
    /**
     * 决策(0:拒绝、1:允许)
     */
    @TableField(value = "decision", condition = EQUAL)
    private Boolean decision;
    /**
     * 是否启用
     */
    @TableField(value = "enabled", condition = EQUAL)
    private Boolean enabled;
    /**
     * 备注
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
