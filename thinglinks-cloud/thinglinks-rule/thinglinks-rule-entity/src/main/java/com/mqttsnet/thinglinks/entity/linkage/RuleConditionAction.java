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
 * 实体类
 * 规则条件动作表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-19 23:24:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("rule_condition_action")
public class RuleConditionAction extends Entity<Long> {
    private static final long serialVersionUID = 1L;

    /**
     * 规则条件ID
     */
    @TableField(value = "rule_condition_id", condition = EQUAL)
    private Long ruleConditionId;
    /**
     * 执行动作
     */
    @TableField(value = "action_type", condition = EQUAL)
    private Integer actionType;
    /**
     * 动作内容
     */
    @TableField(value = "action_content", condition = LIKE)
    private String actionContent;
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
