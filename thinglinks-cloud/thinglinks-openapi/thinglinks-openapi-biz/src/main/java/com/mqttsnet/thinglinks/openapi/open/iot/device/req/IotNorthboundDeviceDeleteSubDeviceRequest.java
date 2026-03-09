package com.mqttsnet.thinglinks.openapi.open.iot.device.req;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * 北向API-删除子设备请求参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotNorthboundDeviceDeleteSubDeviceRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网关设备标识
     * @mock GATEWAY_001
     */
    @NotBlank(message = "请填写网关设备标识")
    @Size(max = 64, message = "网关设备标识长度不能超过{max}")
    private String gatewayIdentification;

    /**
     * 子设备标识集合
     * @mock ["DEVICE_001","DEVICE_002"]
     */
    @NotNull(message = "请填写子设备标识集合")
    @Size(min = 1, max = 100, message = "子设备标识集合大小必须在1到100之间")
    private List<String> deviceIds;

}
