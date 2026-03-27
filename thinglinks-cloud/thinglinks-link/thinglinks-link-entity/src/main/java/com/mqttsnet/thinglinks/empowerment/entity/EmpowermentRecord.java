package com.mqttsnet.thinglinks.empowerment.entity;

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
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 赋能记录表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-15 17:20:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "empowerment_record", autoResultMap = true)
public class EmpowermentRecord extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
    @TableField(value = "app_id", condition = LIKE)
    private String appId;
    /**
     * 赋能标识
     */
    @TableField(value = "empowerment_identification", condition = LIKE)
    private String empowermentIdentification;
    /**
     * 赋能类型
     */
    @TableField(value = "empowerment_type", condition = EQUAL)
    private Integer empowermentType;
    /**
     * 开始时间
     */
    @TableField(value = "startTime", condition = EQUAL)
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @TableField(value = "endTime", condition = EQUAL)
    private LocalDateTime endTime;
    /**
     * 赋能结果
     */
    @TableField(value = "outcome", condition = LIKE)
    private String outcome;
    /**
     * 赋能反馈
     */
    @TableField(value = "feedback", condition = LIKE)
    private String feedback;
    /**
     * 状态
     */
    @TableField(value = "status", condition = EQUAL)
    private Integer status;
    /**
     * 版本
     */
    @TableField(value = "version", condition = LIKE)
    private String version;
    /**
     * 依赖关系
     */
    @TableField(value = "dependencies", condition = LIKE)
    private String dependencies;
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


    /**
     * 逻辑删除标识:0-未删除 1-已删除
     */
    @TableLogic
    @TableField(value = "deleted", condition = EQUAL)
    private Integer deleted;
}
