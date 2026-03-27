package com.mqttsnet.thinglinks.openapi.open.iot.device.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Description:
 * 北向API-查询设备详情请求参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/01/22
 */
@Data
public class IotNorthboundDeviceGetDetailRequest {

    /**
     * 设备标识（设备的唯一标识，如SN码/IMEI等）
     * @mock SN-2025-ABCD-5678
     */
    @NotBlank(message = "请填写设备标识")
    @Size(max = 255, message = "设备标识长度不能超过{max}")
    private String deviceIdentification;

}
