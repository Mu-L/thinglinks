package com.mqttsnet.thinglinks.openapi.open.iot.ota.resp;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * 北向API-OTA上报软固件版本响应结果
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotNorthboundOtaReportResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 操作结果：true-成功、false-失败
     * @mock true
     */
    private Boolean success;

    /**
     * 设备唯一标识
     * @mock DEVICE_001
     */
    private String deviceIdentification;

    /**
     * OTA包类型：0-软件包、1-固件包
     * @mock 1
     */
    private Integer packageType;

    /**
     * 当前版本号
     * @mock v2.0.0
     */
    private String currentVersion;

    /**
     * 响应消息
     * @mock 版本上报成功
     */
    private String message;

}
