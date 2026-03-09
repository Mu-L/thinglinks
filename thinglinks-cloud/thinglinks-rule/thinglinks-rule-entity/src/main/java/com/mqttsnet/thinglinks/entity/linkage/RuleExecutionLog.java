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

import java.io.Serial;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 规则执行日志表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-12-02 18:41:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("rule_execution_log")
public class RuleExecutionLog extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 规则标识
     */
    @TableField(value = "rule_identification", condition = LIKE)
    private String ruleIdentification;
    /**
     * 规则名称
     */
    @TableField(value = "rule_name", condition = LIKE)
    private String ruleName;
    /**
     * 规则执行状态：0-未执行，1-执行中，2-已完成
     */
    @TableField(value = "status", condition = EQUAL)
    private Integer status;
    /**
     * 规则执行开始时间
     */
    @TableField(value = "start_time", condition = EQUAL)
    private LocalDateTime startTime;
    /**
     * 规则执行结束时间
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
