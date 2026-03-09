package com.mqttsnet.thinglinks.openapi.open.iot.ota.req;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * 北向API-保存OTA升级记录请求参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotNorthboundOtaSaveUpgradeRecordRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备唯一标识
     * @mock DEVICE_001
     */
    @NotBlank(message = "请填写设备标识")
    @Size(max = 64, message = "设备标识长度不能超过{max}")
    private String deviceIdentification;

    /**
     * OTA任务ID
     * @mock 123456
     */
    private Long otaTaskId;

    /**
     * 升级状态：0-待升级、1-升级中、2-升级成功、3-升级失败
     * @mock 2
     */
    @Min(value = 0, message = "升级状态最小值为0")
    @Max(value = 3, message = "升级状态最大值为3")
    private Integer upgradeStatus;

    /**
     * 升级进度（百分比）
     * @mock 100
     */
    @Min(value = 0, message = "升级进度最小值为0")
    @Max(value = 100, message = "升级进度最大值为100")
    private Integer progress;

    /**
     * 升级开始时间戳（毫秒）
     * @mock 1706832000000
     */
    private Long startTime;

    /**
     * 升级结束时间戳（毫秒）
     * @mock 1706835600000
     */
    private Long endTime;

    /**
     * 错误码
     * @mock 0
     */
    @Size(max = 32, message = "错误码长度不能超过{max}")
    private String errorCode;

    /**
     * 错误信息
     * @mock
     */
    @Size(max = 512, message = "错误信息长度不能超过{max}")
    private String errorMessage;

    /**
     * 成功详情
     * @mock 升级成功
     */
    @Size(max = 1024, message = "成功详情长度不能超过{max}")
    private String successDetails;

    /**
     * 失败详情
     * @mock
     */
    @Size(max = 1024, message = "失败详情长度不能超过{max}")
    private String failureDetails;

    /**
     * 升级日志详情
     * @mock 开始下载固件...下载完成...开始安装...安装完成
     */
    @Size(max = 4096, message = "日志详情长度不能超过{max}")
    private String logDetails;

}
