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

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 规则条件执行日志表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-12-02 18:53:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("rule_condition_execution_log")
public class RuleConditionExecutionLog extends Entity<Long> {
    private static final long serialVersionUID = 1L;

    /**
     * 规则执行日志ID
     */
    @TableField(value = "rule_execution_id", condition = EQUAL)
    private Long ruleExecutionId;
    /**
     * 条件唯一标识
     */
    @TableField(value = "condition_uuid", condition = LIKE)
    private String conditionUuid;
    /**
     * 条件类型：0-设备属性触发，1-定时触发，2-设备动作触发等
     */
    @TableField(value = "condition_type", condition = EQUAL)
    private Integer conditionType;
    /**
     * 条件是否成立
     */
    @TableField(value = "evaluation_result", condition = EQUAL)
    private Boolean evaluationResult;
    /**
     * 条件评估开始时间
     */
    @TableField(value = "start_time", condition = EQUAL)
    private LocalDateTime startTime;
    /**
     * 条件评估结束时间
     */
    @TableField(value = "end_time", condition = EQUAL)
    private LocalDateTime endTime;
    /**
     * 描述
     */
    @TableField(value = "remark", condition = LIKE)
    private String remark;
    /**
     * 扩展参数（文本格式）
     */
    @TableField(value = "extend_params", condition = LIKE)
    private String extendParams;

    /**
     * 创建人组织
     */
    @TableField(value = "created_org_id", condition = EQUAL)
    private Long createdOrgId;

}
