package com.mqttsnet.thinglinks.mobile.mobilespace.vo.query;

import io.swagger.v3.oas.annotations.media.Schema;
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
 * <p>
 * 表单查询条件VO
 * 移动端-空间表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-29 10:17:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode
@Builder
@Schema(description = "移动端-空间表")
public class MobileSpacePageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    /**
     * 空间名称
     */
    @Schema(description = "空间名称")
    private String spaceName;
    /**
     * 空间经度
     */
    @Schema(description = "空间经度")
    private String longitude;
    /**
     * 空间纬度
     */
    @Schema(description = "空间纬度")
    private String latitude;
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
    /**
     * 位置名称
     */
    @Schema(description = "位置名称")
    private String fullName;
    /**
     * 省,直辖市编码
     */
    @Schema(description = "省,直辖市编码")
    private String provinceCode;
    /**
     * 市编码
     */
    @Schema(description = "市编码")
    private String cityCode;
    /**
     * 区县
     */
    @Schema(description = "区县")
    private String regionCode;

    @Schema(description = "创建人ID")
    private Long createdUserId;

}
