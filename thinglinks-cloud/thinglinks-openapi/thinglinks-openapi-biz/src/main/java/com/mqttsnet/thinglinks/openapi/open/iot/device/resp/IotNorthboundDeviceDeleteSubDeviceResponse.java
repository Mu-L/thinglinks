package com.mqttsnet.thinglinks.openapi.open.iot.device.resp;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * 北向API-删除子设备响应结果
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotNorthboundDeviceDeleteSubDeviceResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 请求处理的结果码：0表示成功，非0表示失败
     * @mock 0
     */
    private Integer statusCode;

    /**
     * 响应状态描述
     * @mock 操作成功
     */
    private String statusDesc;

    /**
     * 操作结果信息列表
     */
    private List<OperationResult> data;

    /**
     * 操作结果数据模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperationResult implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 设备ID
         * @mock DEVICE_001
         */
        private String deviceId;

        /**
         * 请求处理的结果码：0表示成功，非0表示失败
         * @mock 0
         */
        private Integer statusCode;

        /**
         * 响应状态描述
         * @mock 删除成功
         */
        private String statusDesc;
    }

}
