package com.mqttsnet.thinglinks.entity.linkage;

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
 * 实体类 规则条件表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-19 23:36:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("rule_condition")
public class RuleCondition extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 规则
     */
    @TableField(value = "rule_id", condition = EQUAL)
    private Long ruleId;
    /**
     * 条件编码
     */
    @TableField(value = "condition_identification", condition = LIKE)
    private String conditionIdentification;
    /**
     * 条件类型
     */
    @TableField(value = "condition_type", condition = EQUAL)
    private Integer conditionType;
    /**
     * 条件内容
     */
    @TableField(value = "condition_scheme", condition = LIKE)
    private String conditionScheme;
    /**
     * 启用状态
     */
    @TableField(value = "status", condition = EQUAL)
    private Integer status;
    /**
     * 防抖状态
     */
    @TableField(value = "anti_shake", condition = EQUAL)
    private Integer antiShake;
    /**
     * 防抖策略
     */
    @TableField(value = "anti_shake_scheme", condition = LIKE)
    private String antiShakeScheme;
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
