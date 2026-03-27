package com.mqttsnet.thinglinks.openapi.open.iot.ota.resp;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * 北向API-OTA获取可用升级版本响应结果
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotNorthboundOtaGetAvailableUpgradeVersionsResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备唯一标识
     * @mock DEVICE_001
     */
    private String deviceIdentification;

    /**
     * 产品唯一标识
     * @mock PROD_001
     */
    private String productIdentification;

    /**
     * OTA包类型：0-软件包、1-固件包
     * @mock 1
     */
    private Integer packageType;

    /**
     * 当前版本号
     * @mock v1.0.0
     */
    private String currentVersion;

    /**
     * 可升级版本列表
     */
    private List<UpgradeVersionInfo> upgradeVersions;

    /**
     * 可升级版本信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpgradeVersionInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * OTA任务ID
         * @mock 123456
         */
        private Long otaTaskId;

        /**
         * OTA升级任务名称
         * @mock 固件升级任务-v2.0.0
         */
        private String otaTaskName;

        /**
         * OTA包名称
         * @mock firmware_v2.0.0.bin
         */
        private String packageName;

        /**
         * 版本号
         * @mock v2.0.0
         */
        private String version;

        /**
         * 文件下载地址
         * @mock https://example.com/ota/firmware_v2.0.0.bin
         */
        private String fileLocation;

        /**
         * 包描述
         * @mock 修复若干BUG，优化性能
         */
        private String description;

        /**
         * 自定义信息
         * @mock {"checksum":"abc123"}
         */
        private String customInfo;

        /**
         * 签名方法：0-MD5、1-SHA256
         * @mock 0
         */
        private Integer signMethod;

        /**
         * 签名值
         * @mock abc123def456
         */
        private String sign;
    }

}
