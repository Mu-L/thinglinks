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
 * 规则动作执行日志表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-12-02 18:54:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("rule_action_execution_log")
public class RuleActionExecutionLog extends Entity<Long> {
    private static final long serialVersionUID = 1L;

    /**
     * 规则执行日志ID（外键）
     */
    @TableField(value = "rule_execution_id", condition = EQUAL)
    private Long ruleExecutionId;
    /**
     * 动作类型：0-命令下发，1-触发告警，2-数据转发
     */
    @TableField(value = "action_type", condition = EQUAL)
    private Integer actionType;
    /**
     * 动作内容
     */
    @TableField(value = "action_content", condition = LIKE)
    private String actionContent;
    /**
     * 动作是否执行成功
     */
    @TableField(value = "result", condition = EQUAL)
    private Boolean result;
    /**
     * 动作开始执行时间
     */
    @TableField(value = "start_time", condition = EQUAL)
    private LocalDateTime startTime;
    /**
     * 动作结束执行时间
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
