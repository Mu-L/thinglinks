package com.mqttsnet.thinglinks.empowerment.vo.update;

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
import java.time.LocalDateTime;

/**
 * <p>
 * 表单修改方法VO
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
@EqualsAndHashCode
@Builder
@Schema(title = "EmpowermentRecordUpdateVO", description = "赋能记录表")
public class EmpowermentRecordUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @NotNull(message = "请填写id", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    @NotEmpty(message = "请填写应用ID")
    @Size(max = 64, message = "应用ID长度不能超过{max}")
    private String appId;
    /**
     * 赋能标识
     */
    @Schema(description = "赋能标识")
    @NotEmpty(message = "请填写赋能标识")
    @Size(max = 100, message = "赋能标识长度不能超过{max}")
    private String empowermentIdentification;
    /**
     * 赋能类型
     */
    @Schema(description = "赋能类型")
    @NotNull(message = "请填写赋能类型")
    private Integer empowermentType;
    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private LocalDateTime endTime;
    /**
     * 赋能结果
     */
    @Schema(description = "赋能结果")
    @Size(max = 2147483647, message = "赋能结果长度不能超过{max}")
    private String outcome;
    /**
     * 赋能反馈
     */
    @Schema(description = "赋能反馈")
    @Size(max = 2147483647, message = "赋能反馈长度不能超过{max}")
    private String feedback;
    /**
     * 状态
     */
    @Schema(description = "状态")
    @NotNull(message = "请填写状态")
    private Integer status;
    /**
     * 版本
     */
    @Schema(description = "版本")
    @NotEmpty(message = "请填写版本")
    @Size(max = 255, message = "版本长度不能超过{max}")
    private String version;
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
