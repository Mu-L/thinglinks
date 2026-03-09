package com.mqttsnet.thinglinks.mobile.mobilespace.vo.update;

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

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 表单修改方法VO
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
@Schema(description = "MobileSpaceUpdateVO")
public class MobileSpaceUpdateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @NotNull(message = "请填写id", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 空间名称
     */
    @Schema(description = "空间名称")
    @Size(max = 255, message = "空间名称长度不能超过{max}")
    private String spaceName;
    /**
     * 空间经度
     */
    @Schema(description = "空间经度")
    @Size(max = 255, message = "空间经度长度不能超过{max}")
    private String longitude;
    /**
     * 空间纬度
     */
    @Schema(description = "空间纬度")
    @Size(max = 255, message = "空间纬度长度不能超过{max}")
    private String latitude;
    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, message = "备注长度不能超过{max}")
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
    @NotEmpty(message = "请填写位置名称")
    @Size(max = 500, message = "位置名称长度不能超过{max}")
    private String fullName;
    /**
     * 省,直辖市编码
     */
    @Schema(description = "省,直辖市编码")
    @NotEmpty(message = "请填写省,直辖市编码")
    @Size(max = 50, message = "省,直辖市编码长度不能超过{max}")
    private String provinceCode;
    /**
     * 市编码
     */
    @Schema(description = "市编码")
    @NotEmpty(message = "请填写市编码")
    @Size(max = 50, message = "市编码长度不能超过{max}")
    private String cityCode;
    /**
     * 区县
     */
    @Schema(description = "区县")
    @NotEmpty(message = "请填写区县")
    @Size(max = 50, message = "区县长度不能超过{max}")
    private String regionCode;


}
