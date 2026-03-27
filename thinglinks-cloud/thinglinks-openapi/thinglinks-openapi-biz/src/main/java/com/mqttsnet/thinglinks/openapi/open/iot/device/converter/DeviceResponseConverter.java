package com.mqttsnet.thinglinks.openapi.open.iot.device.converter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import com.mqttsnet.thinglinks.device.vo.result.DeviceDetailsResultVO;
import com.mqttsnet.thinglinks.device.vo.result.DeviceResultVO;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceCreateResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceDataReportResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceDeleteSubDeviceResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceIssueCommandResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceQueryResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceUpdateStatusResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceUpdateSubDeviceConnectStatusResponse;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceCommandResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoDeviceOperationResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoQueryDeviceResultVO;
import lombok.experimental.UtilityClass;

/**
 * Description:
 * 北向API-设备响应转换器
 * 负责将内部 VO 转换为 OpenAPI 响应类
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@UtilityClass
public class DeviceResponseConverter {

    /**
     * 转换创建设备响应
     *
     * @param deviceResultVO 设备结果VO
     * @return 创建设备响应
     */
    public static IotNorthboundDeviceCreateResponse convertToCreateDeviceResponse(DeviceResultVO deviceResultVO) {
        return Optional.ofNullable(deviceResultVO)
                .map(vo -> IotNorthboundDeviceCreateResponse.builder()
                        .deviceIdentification(vo.getDeviceIdentification())
                        .deviceName(vo.getDeviceName())
                        .clientId(vo.getClientId())
                        .authMode(vo.getAuthMode())
                        .nodeType(vo.getNodeType())
                        .productIdentification(vo.getProductIdentification())
                        .deviceStatus(vo.getDeviceStatus())
                        .connectStatus(vo.getConnectStatus())
                        .swVersion(vo.getSwVersion())
                        .fwVersion(vo.getFwVersion())
                        .deviceSdkVersion(vo.getDeviceSdkVersion())
                        .gatewayId(vo.getGatewayId())
                        .userName(vo.getUserName())
                        .appId(vo.getAppId())
                        .encryptMethod(vo.getEncryptMethod())
                        .description(vo.getDescription())
                        .remark(vo.getRemark())
                        .build())
                .orElseGet(() -> IotNorthboundDeviceCreateResponse.builder().build());
    }

    /**
     * 转换下发命令响应
     *
     * @param commandResultVOs 命令结果VO列表
     * @return 下发命令响应
     */
    public static IotNorthboundDeviceIssueCommandResponse convertToIssueCommandResponse(List<DeviceCommandResultVO> commandResultVOs) {
        if (CollUtil.isEmpty(commandResultVOs)) {
            return IotNorthboundDeviceIssueCommandResponse.builder()
                    .commandResults(Collections.emptyList())
                    .build();
        }

        List<IotNorthboundDeviceIssueCommandResponse.CommandResultItem> commandResults = commandResultVOs.stream()
                .map(vo -> IotNorthboundDeviceIssueCommandResponse.CommandResultItem.builder()
                        .deviceIdentification(vo.getDeviceIdentification())
                        .commandIdentification(vo.getCommandIdentification())
                        .commandType(vo.getCommandType())
                        .status(vo.getStatus())
                        .content(vo.getContent())
                        .remark(vo.getRemark())
                        .build())
                .collect(Collectors.toList());

        return IotNorthboundDeviceIssueCommandResponse.builder()
                .commandResults(commandResults)
                .build();
    }

    /**
     * 转换修改设备状态响应
     *
     * @param success        操作是否成功
     * @param deviceDetails  设备详情
     * @param previousStatus 之前的状态
     * @param currentStatus  当前状态
     * @return 修改设备状态响应
     */
    public static IotNorthboundDeviceUpdateStatusResponse convertToUpdateDeviceStatusResponse(
            Boolean success, DeviceDetailsResultVO deviceDetails, Integer previousStatus, Integer currentStatus) {
        return Optional.ofNullable(deviceDetails)
                .map(details -> IotNorthboundDeviceUpdateStatusResponse.builder()
                        .success(success)
                        .deviceIdentification(details.getDeviceIdentification())
                        .deviceName(details.getDeviceName())
                        .clientId(details.getClientId())
                        .userName(details.getUserName())
                        .appId(details.getAppId())
                        .previousStatus(previousStatus)
                        .currentStatus(currentStatus)
                        .connectStatus(details.getConnectStatus())
                        .nodeType(details.getNodeType())
                        .productIdentification(details.getProductIdentification())
                        .productName(Optional.ofNullable(details.getProductResultVO())
                                .map(ProductResultVO::getProductName)
                                .orElse(null))
                        .swVersion(details.getSwVersion())
                        .fwVersion(details.getFwVersion())
                        .deviceSdkVersion(details.getDeviceSdkVersion())
                        .description(details.getDescription())
                        .gatewayId(details.getGatewayId())
                        .remark(details.getRemark())
                        .updateTime(LocalDateTime.now())
                        .build())
                .orElseGet(() -> IotNorthboundDeviceUpdateStatusResponse.builder()
                        .success(success)
                        .previousStatus(previousStatus)
                        .currentStatus(currentStatus)
                        .updateTime(LocalDateTime.now())
                        .build());
    }

    /**
     * 转换修改子设备连接状态响应
     *
     * @param resultVO 操作结果VO
     * @return 修改子设备连接状态响应
     */
    public static IotNorthboundDeviceUpdateSubDeviceConnectStatusResponse convertToUpdateSubDeviceConnectStatusResponse(TopoDeviceOperationResultVO resultVO) {
        return Optional.ofNullable(resultVO)
                .map(vo -> {
                    List<IotNorthboundDeviceUpdateSubDeviceConnectStatusResponse.OperationResult> operationResults =
                            Optional.ofNullable(vo.getData())
                                    .filter(CollUtil::isNotEmpty)
                                    .map(data -> data.stream()
                                            .map(op -> IotNorthboundDeviceUpdateSubDeviceConnectStatusResponse.OperationResult.builder()
                                                    .deviceId(op.getDeviceId())
                                                    .statusCode(op.getStatusCode())
                                                    .statusDesc(op.getStatusDesc())
                                                    .build())
                                            .collect(Collectors.toList()))
                                    .orElse(Collections.emptyList());

                    return IotNorthboundDeviceUpdateSubDeviceConnectStatusResponse.builder()
                            .statusCode(vo.getStatusCode())
                            .statusDesc(vo.getStatusDesc())
                            .data(operationResults)
                            .build();
                })
                .orElseGet(() -> IotNorthboundDeviceUpdateSubDeviceConnectStatusResponse.builder()
                        .statusCode(0)
                        .statusDesc("操作成功")
                        .build());
    }

    /**
     * 转换删除子设备响应
     *
     * @param resultVO 操作结果VO
     * @return 删除子设备响应
     */
    public static IotNorthboundDeviceDeleteSubDeviceResponse convertToDeleteSubDeviceResponse(TopoDeviceOperationResultVO resultVO) {
        return Optional.ofNullable(resultVO)
                .map(vo -> {
                    List<IotNorthboundDeviceDeleteSubDeviceResponse.OperationResult> operationResults =
                            Optional.ofNullable(vo.getData())
                                    .filter(CollUtil::isNotEmpty)
                                    .map(data -> data.stream()
                                            .map(op -> IotNorthboundDeviceDeleteSubDeviceResponse.OperationResult.builder()
                                                    .deviceId(op.getDeviceId())
                                                    .statusCode(op.getStatusCode())
                                                    .statusDesc(op.getStatusDesc())
                                                    .build())
                                            .collect(Collectors.toList()))
                                    .orElse(Collections.emptyList());

                    return IotNorthboundDeviceDeleteSubDeviceResponse.builder()
                            .statusCode(vo.getStatusCode())
                            .statusDesc(vo.getStatusDesc())
                            .data(operationResults)
                            .build();
                })
                .orElseGet(() -> IotNorthboundDeviceDeleteSubDeviceResponse.builder()
                        .statusCode(0)
                        .statusDesc("删除成功")
                        .build());
    }

    /**
     * 转换数据上报响应
     *
     * @param resultVO 操作结果VO
     * @return 数据上报响应
     */
    public static IotNorthboundDeviceDataReportResponse convertToDataReportResponse(TopoDeviceOperationResultVO resultVO) {
        return Optional.ofNullable(resultVO)
                .map(vo -> {
                    List<IotNorthboundDeviceDataReportResponse.OperationResult> operationResults =
                            Optional.ofNullable(vo.getData())
                                    .filter(CollUtil::isNotEmpty)
                                    .map(data -> data.stream()
                                            .map(op -> IotNorthboundDeviceDataReportResponse.OperationResult.builder()
                                                    .deviceId(op.getDeviceId())
                                                    .statusCode(op.getStatusCode())
                                                    .statusDesc(op.getStatusDesc())
                                                    .build())
                                            .collect(Collectors.toList()))
                                    .orElse(Collections.emptyList());

                    return IotNorthboundDeviceDataReportResponse.builder()
                            .statusCode(vo.getStatusCode())
                            .statusDesc(vo.getStatusDesc())
                            .data(operationResults)
                            .build();
                })
                .orElseGet(() -> IotNorthboundDeviceDataReportResponse.builder()
                        .statusCode(0)
                        .statusDesc("数据上报成功")
                        .build());
    }

    /**
     * 转换查询设备响应
     *
     * @param resultVO 查询设备结果VO
     * @return 查询设备响应
     */
    public static IotNorthboundDeviceQueryResponse convertToQueryDeviceResponse(TopoQueryDeviceResultVO resultVO) {
        return Optional.ofNullable(resultVO)
                .map(vo -> {
                    List<IotNorthboundDeviceQueryResponse.DeviceItem> deviceItems =
                            Optional.ofNullable(vo.getData())
                                    .filter(CollUtil::isNotEmpty)
                                    .map(data -> data.stream()
                                            .map(DeviceResponseConverter::convertToDeviceItem)
                                            .collect(Collectors.toList()))
                                    .orElse(Collections.emptyList());

                    return IotNorthboundDeviceQueryResponse.builder()
                            .statusCode(vo.getStatusCode())
                            .statusDesc(vo.getStatusDesc())
                            .data(deviceItems)
                            .build();
                })
                .orElseGet(() -> IotNorthboundDeviceQueryResponse.builder()
                        .statusCode(0)
                        .statusDesc("查询成功")
                        .build());
    }

    /**
     * 转换设备条目
     *
     * @param item 设备条目VO
     * @return 设备条目响应
     */
    public static IotNorthboundDeviceQueryResponse.DeviceItem convertToDeviceItem(TopoQueryDeviceResultVO.DataItem item) {
        IotNorthboundDeviceQueryResponse.DeviceInfo deviceInfo = Optional.ofNullable(item.getDeviceInfo())
                .map(info -> IotNorthboundDeviceQueryResponse.DeviceInfo.builder()
                        .clientId(info.getClientId())
                        .deviceName(info.getDeviceName())
                        .connector(info.getConnector())
                        .description(info.getDescription())
                        .deviceStatus(info.getDeviceStatus())
                        .connectStatus(info.getConnectStatus())
                        .deviceTags(info.getDeviceTags())
                        .productIdentification(info.getProductIdentification())
                        .swVersion(info.getSwVersion())
                        .fwVersion(info.getFwVersion())
                        .deviceSdkVersion(info.getDeviceSdkVersion())
                        .gatewayId(info.getGatewayId())
                        .nodeType(info.getNodeType())
                        .remark(info.getRemark())
                        .build())
                .orElse(null);

        return IotNorthboundDeviceQueryResponse.DeviceItem.builder()
                .statusCode(item.getStatusCode())
                .statusDesc(item.getStatusDesc())
                .deviceId(item.getDeviceId())
                .deviceInfo(deviceInfo)
                .build();
    }
}
