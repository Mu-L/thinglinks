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
 * 规则信息
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-19 23:20:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("rule")
public class Rule extends Entity<Long> {
    private static final long serialVersionUID = 1L;

    /**
     * 规则名称
     */
    @TableField(value = "rule_name", condition = LIKE)
    private String ruleName;
    /**
     * 规则标识
     */
    @TableField(value = "rule_identification", condition = LIKE)
    private String ruleIdentification;
    /**
     * 应用ID
     */
    @TableField(value = "app_id", condition = LIKE)
    private String appId;
    /**
     * 生效类型
     */
    @TableField(value = "effective_type", condition = EQUAL)
    private Integer effectiveType;
    /**
     * 指定内容
     */
    @TableField(value = "appoint_content", condition = LIKE)
    private String appointContent;
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
