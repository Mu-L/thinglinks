package com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.save;

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
 * 表单保存方法VO
 * 空间设备绑定表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 14:11:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode
@Builder
@Schema(description = "空间设备绑定表")
public class MobileSpaceDeviceSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 空间ID
     */
    @Schema(description = "空间ID")
    @NotNull(message = "请填写空间ID")
    private Long spaceId;
    /**
     * 产品标识
     */
    @Schema(description = "产品标识")
    @NotEmpty(message = "请填写产品标识")
    @Size(max = 100, message = "产品标识长度不能超过{max}")
    private String productIdentification;
    /**
     * 设备标识
     */
    @Schema(description = "设备标识")
    @NotEmpty(message = "请填写设备标识")
    @Size(max = 255, message = "设备标识长度不能超过{max}")
    private String deviceIdentification;
    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


}
