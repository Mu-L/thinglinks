package com.mqttsnet.thinglinks.empowerment.vo.query;

import io.swagger.v3.oas.annotations.media.Schema;
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
 * 表单查询条件VO
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
@Schema(title = "EmpowermentRecordPageQuery", description = "赋能记录表")
public class EmpowermentRecordPageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private String appId;
    /**
     * 赋能标识
     */
    @Schema(description = "赋能标识")
    private String empowermentIdentification;
    /**
     * 赋能类型
     */
    @Schema(description = "赋能类型")
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
    private String outcome;
    /**
     * 赋能反馈
     */
    @Schema(description = "赋能反馈")
    private String feedback;
    /**
     * 状态
     */
    @Schema(description = "状态")
    private Integer status;
    /**
     * 版本
     */
    @Schema(description = "版本")
    private String version;
    /**
     * 依赖关系
     */
    @Schema(description = "依赖关系")
    private String dependencies;
    /**
     * 描述
     */
    @Schema(description = "描述")
    private String remark;
    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


}
