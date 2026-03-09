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
 * 北向API-查询设备信息响应结果
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotNorthboundDeviceQueryResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 请求处理的结果码：0表示成功，非0表示失败
     * @mock 0
     */
    private Integer statusCode;

    /**
     * 响应状态描述
     * @mock 查询成功
     */
    private String statusDesc;

    /**
     * 设备信息列表
     */
    private List<DeviceItem> data;

    /**
     * 设备信息数据模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceItem implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 请求处理的结果码：0表示成功，非0表示失败
         * @mock 0
         */
        private Integer statusCode;

        /**
         * 响应状态描述
         * @mock 查询成功
         */
        private String statusDesc;

        /**
         * 设备ID
         * @mock DEVICE_001
         */
        private String deviceId;

        /**
         * 设备详细信息
         */
        private DeviceInfo deviceInfo;
    }

    /**
     * 设备详细信息数据模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 客户端标识
         * @mock CLIENT_001
         */
        private String clientId;

        /**
         * 设备名称
         * @mock 测试设备
         */
        private String deviceName;

        /**
         * 连接实例
         * @mock connector1
         */
        private String connector;

        /**
         * 设备描述
         * @mock 这是一个测试设备
         */
        private String description;

        /**
         * 设备状态：0-未激活、1-已启用、2-已禁用
         * @mock 1
         */
        private Integer deviceStatus;

        /**
         * 连接状态：0-未连接、1-在线、2-离线
         * @mock 1
         */
        private Integer connectStatus;

        /**
         * 设备标签
         * @mock tag1,tag2
         */
        private String deviceTags;

        /**
         * 产品标识
         * @mock PROD_001
         */
        private String productIdentification;

        /**
         * 软件版本
         * @mock v1.0.0
         */
        private String swVersion;

        /**
         * 固件版本
         * @mock v1.0.0
         */
        private String fwVersion;

        /**
         * SDK版本
         * @mock v1
         */
        private String deviceSdkVersion;

        /**
         * 网关设备ID（子设备时填写）
         * @mock GATEWAY_001
         */
        private String gatewayId;

        /**
         * 设备类型：0-普通设备、1-网关设备、2-子设备
         * @mock 0
         */
        private Integer nodeType;

        /**
         * 备注
         * @mock 设备备注信息
         */
        private String remark;
    }

}
