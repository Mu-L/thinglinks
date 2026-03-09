package com.mqttsnet.thinglinks.openapi.open.iot.ota.req;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * 北向API-OTA上报软固件版本请求参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotNorthboundOtaReportRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备唯一标识
     * @mock DEVICE_001
     */
    @NotBlank(message = "请填写设备标识")
    @Size(max = 64, message = "设备标识长度不能超过{max}")
    private String deviceIdentification;

    /**
     * OTA包类型：0-软件包、1-固件包
     * @mock 1
     */
    @NotNull(message = "请填写包类型")
    @Min(value = 0, message = "包类型最小值为0")
    @Max(value = 1, message = "包类型最大值为1")
    private Integer packageType;

    /**
     * 当前版本号
     * @mock v2.0.0
     */
    @NotBlank(message = "请填写当前版本号")
    @Size(max = 32, message = "当前版本号长度不能超过{max}")
    private String currentVersion;

}
