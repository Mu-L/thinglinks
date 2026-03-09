package com.mqttsnet.thinglinks.openapi.open.iot.device.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Description:
 * 北向API-修改设备状态请求参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@Data
public class IotNorthboundDeviceUpdateStatusRequest {

    /**
     * 设备标识（设备的唯一标识）
     * @mock DEVICE_001
     */
    @NotBlank(message = "请填写设备标识")
    @Size(max = 64, message = "设备标识长度不能超过{max}")
    private String deviceIdentification;

    /**
     * 设备状态：0-未激活、1-已激活、2-已禁用
     * @mock 1
     */
    @NotNull(message = "请填写设备状态")
    @Min(value = 0, message = "设备状态最小值为0")
    @Max(value = 2, message = "设备状态最大值为2")
    private Integer status;

}
