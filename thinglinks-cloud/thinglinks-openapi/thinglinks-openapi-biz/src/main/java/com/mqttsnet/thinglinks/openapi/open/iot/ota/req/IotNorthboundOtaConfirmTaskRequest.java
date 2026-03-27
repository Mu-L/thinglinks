package com.mqttsnet.thinglinks.openapi.open.iot.ota.req;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 物联网北向API-OTA升级确认请求参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/11/10
 */
@Builder
@Data
@EqualsAndHashCode
@ToString
public class IotNorthboundOtaConfirmTaskRequest {

    /**
     * 升级任务ID
     * 必填参数，不能为空
     */
    @NotNull(message = "升级任务ID不能为空")
    private Long taskId;

    /**
     * 设备标识列表
     * 必填参数，不能为空且最多100个设备标识
     * 用于批量确认处于待确认状态的设备升级作业
     */
    @NotEmpty(message = "设备标识列表不能为空")
    @Size(max = 100, message = "设备标识列表最多支持100个设备")
    private List<String> deviceIdentificationList;

}