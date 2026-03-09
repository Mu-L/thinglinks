package com.mqttsnet.thinglinks.empowerment.vo.save;

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
 * @program: thinglinks-cloud
 * @description: 产品赋能新增参数
 * @packagename: com.mqttsnet.thinglinks.empowerment.vo.save
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-09-16 21:36
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode
@Builder
@Schema(title = "ProductEmpowermentSaveVO", description = "赋能记录表")
public class ProductEmpowermentSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 赋能标识
     */
    @Schema(description = "赋能标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请填写赋能标识")
    @Size(max = 100, message = "赋能标识长度不能超过{max}")
    private String empowermentIdentification;

    /**
     * 赋能类型
     */
    @Schema(description = "赋能类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请填写赋能类型")
    private Integer empowermentType;

    /**
     * 依赖关系
     */
    @Schema(description = "依赖关系")
    @Size(max = 255, message = "依赖关系长度不能超过{max}")
    private String dependencies;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Size(max = 500, message = "描述长度不能超过{max}")
    private String remark;

    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


}
