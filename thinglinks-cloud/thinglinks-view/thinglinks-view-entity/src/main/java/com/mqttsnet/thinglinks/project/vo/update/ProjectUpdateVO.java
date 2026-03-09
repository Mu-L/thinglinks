package com.mqttsnet.thinglinks.project.vo.update;

import com.mqttsnet.basic.base.entity.SuperEntity;
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

import java.io.Serializable;

/**
 * <p>
 * 表单修改方法VO
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
@Schema(title = "projectUpdateVO", description = "可视化项目表")
public class ProjectUpdateVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *串行版本uid
     */
    @Schema(description = "id")
    @NotNull(message = "请填写id", groups = SuperEntity.Update.class)
    private Long id;

    /**
     *项目名称
     */
    @Schema(description = "项目名称")
    @NotEmpty(message = "请填写项目名称")
    @Size(max = 255, message = "项目名称长度不能超过{max}")
    private String projectName;
    /**
     *状态
     *项目状态[1-发布,-1-未发布]
     */
    @Schema(description = "项目状态[1-发布,-1-未发布]")
    @NotNull(message = "请填写项目状态[1-发布,-1-未发布]")
    private Integer status;
    /**
     *索引图像
     *首页图片
     */
    @Schema(description = "首页图片")
    @NotEmpty(message = "请填写首页图片")
    @Size(max = 255, message = "首页图片长度不能超过{max}")
    private String indexImageId;

    /**
     * 存储数据
     */
    @Schema(description = "存储数据")
    private String content;

    /**
     *备注
     */
    @Schema(description = "备注")
    @Size(max = 500, message = "备注长度不能超过{max}")
    private String remark;
    /**
     *创建org id
     *创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


}
