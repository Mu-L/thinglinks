package com.mqttsnet.thinglinks.openapi.open.iot.device.req;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * Description:
 * 北向API-查询设备影子请求
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/01/22
 */
@Data
@Builder
public class IotNorthboundDeviceQueryShadowRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备标识（设备的唯一标识，如SN码/IMEI等）
     * @mock SN-2025-ABCD-5678
     */
    @NotBlank(message = "设备标识不能为空")
    @Size(max = 255, message = "设备标识长度不能超过{max}")
    private String deviceIdentification;

    /**
     * 开始时间戳（选填）
     * 不指定默认查询最新的一条
     * 格式：19位纳秒时间戳
     *
     * @mock 1622552643000000000
     */
    private Long startTime;

    /**
     * 结束时间戳（选填）
     * 不指定默认查询最新的一条
     * 格式：19位纳秒时间戳
     *
     * @mock 1622552644000000000
     */
    private Long endTime;

    /**
     * 服务编码（选填，不指定则查询所有服务）
     * @mock service001
     */
    @Size(max = 64, message = "服务编码长度不能超过{max}")
    private String serviceCode;
}
