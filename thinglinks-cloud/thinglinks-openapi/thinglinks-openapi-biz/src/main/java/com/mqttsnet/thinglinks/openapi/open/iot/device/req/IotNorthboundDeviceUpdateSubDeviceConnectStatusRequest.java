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
 * 北向API-修改子设备连接状态请求参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotNorthboundDeviceUpdateSubDeviceConnectStatusRequest implements Serializable {

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
     * 子设备状态列表
     * @mock [{"deviceId":"DEVICE_001","status":"ONLINE"}]
     */
    @NotNull(message = "请填写子设备状态列表")
    @Size(min = 1, max = 100, message = "子设备状态列表大小必须在1到100之间")
    private List<DeviceStatus> deviceStatuses;

    /**
     * 子设备状态数据模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceStatus implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 子设备唯一标识
         * @mock DEVICE_001
         */
        @NotBlank(message = "请填写子设备唯一标识")
        @Size(max = 64, message = "子设备唯一标识长度不能超过{max}")
        private String deviceId;

        /**
         * 子设备状态：ONLINE-在线、OFFLINE-离线、INIT-未连接
         * @mock ONLINE
         */
        @NotBlank(message = "请填写子设备状态")
        @Size(max = 16, message = "子设备状态长度不能超过{max}")
        private String status;
    }

}
