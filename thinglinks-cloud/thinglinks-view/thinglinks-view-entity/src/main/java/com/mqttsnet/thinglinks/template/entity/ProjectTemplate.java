package com.mqttsnet.thinglinks.template.entity;

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

import java.io.Serial;


/**
 * <p>
 * 实体类
 * 可视化项目模板表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:45:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("view_project_template")
public class ProjectTemplate extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模版标识
     */
    @TableField(value = "template_identification", condition = LIKE)
    private String templateIdentification;
    /**
     * 模版名称
     */
    @TableField(value = "template_name", condition = LIKE)
    private String templateName;
    /**
     * 项目状态[1-发布,-1-未发布]
     */
    @TableField(value = "status", condition = EQUAL)
    private Integer status;
    /**
     * 首页图片
     */
    @TableField(value = "index_image_id", condition = LIKE)
    private String indexImageId;

    /**
     * 存储数据
     */
    @TableField(value = "content", condition = LIKE)
    private String content;
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
