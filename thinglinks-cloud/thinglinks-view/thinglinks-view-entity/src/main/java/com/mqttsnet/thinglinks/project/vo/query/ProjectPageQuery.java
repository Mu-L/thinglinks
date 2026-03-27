package com.mqttsnet.thinglinks.project.vo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * <p>
 * 表单查询条件VO
 * 可视化项目表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:47:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode
@Builder
@Schema(title = "projectPageQuery", description = "可视化项目表")
public class ProjectPageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    /**
     * 项目标识
     */
    @Schema(description = "项目标识")
    private String projectIdentification;
    /**
     * 项目名称
     */
    @Schema(description = "项目名称")
    private String projectName;
    /**
     * 项目状态[1-发布,-1-未发布]
     */
    @Schema(description = "项目状态[1-发布,-1-未发布]")
    private Integer status;
    /**
     * 首页图片
     */
    @Schema(description = "首页图片")
    private String indexImageId;

    /**
     * 存储数据
     */
    @Schema(description = "存储数据")
    private String content;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


}
