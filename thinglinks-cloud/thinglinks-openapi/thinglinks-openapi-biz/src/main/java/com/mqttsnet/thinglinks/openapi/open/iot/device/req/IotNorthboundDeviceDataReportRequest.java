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
 * 北向API-设备数据上报请求参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotNorthboundDeviceDataReportRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备数据列表
     */
    @NotNull(message = "请填写设备数据列表")
    @Size(min = 1, max = 100, message = "设备数据列表大小必须在1到100之间")
    private List<DeviceData> devices;

    /**
     * 设备数据模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceData implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 设备唯一标识
         * @mock DEVICE_001
         */
        @NotBlank(message = "请填写设备唯一标识")
        @Size(max = 64, message = "设备唯一标识长度不能超过{max}")
        private String deviceId;

        /**
         * 服务列表
         */
        @NotNull(message = "请填写服务列表")
        @Size(min = 1, max = 50, message = "服务列表大小必须在1到50之间")
        private List<ServiceData> services;
    }

    /**
     * 服务数据模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceData implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 服务编码
         * @mock temperature
         */
        @NotBlank(message = "请填写服务编码")
        @Size(max = 64, message = "服务编码长度不能超过{max}")
        private String serviceCode;

        /**
         * 服务数据（JSON格式）
         * @mock {"value":25.5}
         */
        @NotNull(message = "请填写服务数据")
        private Object data;

        /**
         * 事件时间戳（毫秒）
         * @mock 1706832000000
         */
        @NotNull(message = "请填写事件时间")
        private Long eventTime;
    }

}
