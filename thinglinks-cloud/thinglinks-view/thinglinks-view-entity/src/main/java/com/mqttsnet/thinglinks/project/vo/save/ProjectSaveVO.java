package com.mqttsnet.thinglinks.project.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 项目保存vo
 * 项目保存参数
 * <p>
 * 表单保存方法VO
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
@Schema(title = "projectSaveVO", description = "可视化项目表")
public class ProjectSaveVO implements Serializable {

    /**
     * 串行版本uid
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 项目标识
     */
    @Schema(description = "项目标识")
    @Size(max = 255, message = "项目标识长度不能超过{max}")
    private String projectIdentification;
    /**
     * 项目名称
     */
    @Schema(description = "项目名称")
    @NotEmpty(message = "请填写项目名称")
    @Size(max = 255, message = "项目名称长度不能超过{max}")
    private String projectName;
    /**
     * 状态
     * 项目状态[1-发布,-1-未发布]
     */
    @Schema(description = "项目状态[1-发布,-1-未发布]")
    @NotNull(message = "请填写项目状态[1-发布,-1-未发布]")
    private Integer status;
    /**
     * 索引图像
     * 首页图片
     */
    @Schema(description = "首页图片")
    @Size(max = 255, message = "首页图片长度不能超过{max}")
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
    @Size(max = 500, message = "备注长度不能超过{max}")
    private String remark;
    /**
     * 创建org id
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


}
