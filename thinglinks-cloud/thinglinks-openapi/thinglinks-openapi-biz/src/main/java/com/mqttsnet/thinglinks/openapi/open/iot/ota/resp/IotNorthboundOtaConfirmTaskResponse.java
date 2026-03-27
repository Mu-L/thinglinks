package com.mqttsnet.thinglinks.openapi.open.iot.ota.resp;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 物联网北向API-OTA升级确认响应参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/11/10
 */
@Builder
@Data
@EqualsAndHashCode
@ToString
public class IotNorthboundOtaConfirmTaskResponse {

    /**
     * 升级任务ID
     */
    private Long taskId;

    /**
     * 确认成功的设备标识列表
     */
    private List<String> deviceIdentificationList;
}