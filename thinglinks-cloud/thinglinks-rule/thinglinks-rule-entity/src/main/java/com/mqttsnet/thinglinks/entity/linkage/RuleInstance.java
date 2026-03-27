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

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 规则实例表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-05 23:04:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("rule_instance")
public class RuleInstance extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
    @TableField(value = "app_id", condition = LIKE)
    private String appId;
    /**
     * 规则实例名称
     */
    @TableField(value = "rule_name", condition = LIKE)
    private String ruleName;
    /**
     * 流程ID， 规则实例类型为“规则编排”时，该项为对应的NedRed流程
     */
    @TableField(value = "flow_id", condition = LIKE)
    private String flowId;
    /**
     * 流程数据
     */
    @TableField(value = "flow_data", condition = LIKE)
    private String flowData;
    /**
     * 规则实例类型(字典标识：RULE_INSTANCE_TYPE）
     */
    @TableField(value = "type", condition = EQUAL)
    private Integer type;

    /**
     * 实例地址
     */
    @TableField(value = "instance_address", condition = LIKE)
    private String instanceAddress;
    /**
     * 状态
     */
    @TableField(value = "status", condition = EQUAL)
    private Integer status;
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


}
