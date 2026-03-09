package com.mqttsnet.thinglinks.ota.entity;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;

import java.io.Serial;

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


/**
 * <p>
 * 实体类
 * OTA升级目标表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-10-19 16:28:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "ota_upgrade_targets", autoResultMap = true)
public class OtaUpgradeTargets extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableField(value = "task_id", condition = EQUAL)
    private Long taskId;
    /**
     * 目标值(设备标识/分组ID/省市区域编码)
     */
    @TableField(value = "target_value", condition = LIKE)
    private String targetValue;
    /**
     * 目标状态(0:待推送,1:推送中,2:推送成功,3:推送失败)
     */
    @TableField(value = "target_status", condition = EQUAL)
    private Integer targetStatus;
    /**
     * 描述
     */
    @TableField(value = "remark", condition = LIKE)
    private String remark;
    /**
     * 逻辑删除标识(0-未删除、1-已删除)
     */
    @TableLogic
    @TableField(value = "deleted", condition = EQUAL)
    private Integer deleted;
    /**
     * 创建人组织
     */
    @TableField(value = "created_org_id", condition = EQUAL)
    private Long createdOrgId;


}
