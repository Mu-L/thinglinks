package com.mqttsnet.thinglinks.openapi.open.iot.ota.converter;

import java.util.Optional;

import com.mqttsnet.thinglinks.openapi.open.iot.ota.req.IotNorthboundOtaPullRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.req.IotNorthboundOtaReadResponseRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.req.IotNorthboundOtaReportRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.req.IotNorthboundOtaSaveUpgradeRecordRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.resp.IotNorthboundOtaPullResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.resp.IotNorthboundOtaReadResponseResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.resp.IotNorthboundOtaReportResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.resp.IotNorthboundOtaSaveUpgradeRecordResponse;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaCommandResponseParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaPullResponseParam;
import lombok.experimental.UtilityClass;

/**
 * Description:
 * 北向API-OTA响应转换器
 * 负责将内部 VO 转换为 OpenAPI 响应类
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@UtilityClass
public class OtaResponseConverter {

    /**
     * 转换保存OTA升级记录响应
     *
     * @param savedRecord 保存的记录
     * @param request     原始请求（用于回填默认值）
     * @return OTA升级记录响应
     */
    public static IotNorthboundOtaSaveUpgradeRecordResponse convertToSaveUpgradeRecordResponse(
            TopoOtaCommandResponseParam savedRecord, IotNorthboundOtaSaveUpgradeRecordRequest request) {
        return Optional.ofNullable(savedRecord)
                .map(record -> IotNorthboundOtaSaveUpgradeRecordResponse.builder()
                        .success(true)
                        .deviceIdentification(record.getDeviceIdentification())
                        .otaTaskId(record.getOtaTaskId())
                        .upgradeStatus(record.getUpgradeStatus())
                        .progress(record.getProgress())
                        .build())
                .orElseGet(() -> IotNorthboundOtaSaveUpgradeRecordResponse.builder()
                        .success(true)
                        .deviceIdentification(request.getDeviceIdentification())
                        .otaTaskId(request.getOtaTaskId())
                        .upgradeStatus(request.getUpgradeStatus())
                        .progress(request.getProgress())
                        .build());
    }

    /**
     * 转换OTA拉取响应
     *
     * @param responseParam 响应参数
     * @param request       原始请求（用于回填默认值）
     * @return OTA拉取响应
     */
    public static IotNorthboundOtaPullResponse convertToOtaPullResponse(
            TopoOtaPullResponseParam responseParam, IotNorthboundOtaPullRequest request) {
        return Optional.ofNullable(responseParam)
                .map(param -> IotNorthboundOtaPullResponse.builder()
                        .deviceIdentification(param.getDeviceIdentification())
                        .productIdentification(param.getProductIdentification())
                        .otaTaskId(param.getOtaTaskId())
                        .otaTaskName(param.getOtaTaskName())
                        .packageName(param.getPackageName())
                        .packageType(param.getPackageType())
                        .version(param.getVersion())
                        .fileLocation(param.getFileLocation())
                        .description(param.getDescription())
                        .customInfo(param.getCustomInfo())
                        .build())
                .orElseGet(() -> IotNorthboundOtaPullResponse.builder()
                        .deviceIdentification(request.getDeviceIdentification())
                        .build());
    }

    /**
     * 转换OTA上报响应
     *
     * @param request 原始请求
     * @return OTA上报响应
     */
    public static IotNorthboundOtaReportResponse convertToOtaReportResponse(IotNorthboundOtaReportRequest request) {
        return IotNorthboundOtaReportResponse.builder()
                .success(true)
                .deviceIdentification(request.getDeviceIdentification())
                .packageType(request.getPackageType())
                .currentVersion(request.getCurrentVersion())
                .message("版本上报成功")
                .build();
    }

    /**
     * 转换OTA读取响应
     *
     * @param request 原始请求
     * @return OTA读取响应
     */
    public static IotNorthboundOtaReadResponseResponse convertToOtaReadResponseResponse(IotNorthboundOtaReadResponseRequest request) {
        return IotNorthboundOtaReadResponseResponse.builder()
                .deviceIdentification(request.getDeviceIdentification())
                .packageType(request.getPackageType())
                .currentVersion(request.getCurrentVersion())
                .build();
    }
}
