package com.mqttsnet.thinglinks.openapi.open.iot.device;

import com.gitee.sop.support.annotation.Open;
import com.gitee.sop.support.context.OpenContext;
import com.mqttsnet.thinglinks.openapi.open.iot.device.req.IotNorthboundDeviceCreateRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.device.req.IotNorthboundDeviceDataReportRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.device.req.IotNorthboundDeviceDeleteSubDeviceRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.device.req.IotNorthboundDeviceGetDetailRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.device.req.IotNorthboundDeviceIssueCommandRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.device.req.IotNorthboundDeviceQueryRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.device.req.IotNorthboundDeviceQueryShadowRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.device.req.IotNorthboundDeviceUpdateStatusRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.device.req.IotNorthboundDeviceUpdateSubDeviceConnectStatusRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceCreateResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceDataReportResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceDeleteSubDeviceResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceGetDetailResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceIssueCommandResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceQueryResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceQueryShadowResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceUpdateStatusResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.device.resp.IotNorthboundDeviceUpdateSubDeviceConnectStatusResponse;

/**
 * 物联网北向API-设备管理
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/6/22
 */
public interface IotNorthboundDeviceManagerApi {

    /**
     * 创建设备接口
     *
     * @apiNote 该接口用于平台管理员或授权应用通过北向API注册新设备
     * 典型场景：应用服务器可调用此接口可在平台注册一个设备，并获取设备唯一标识等信息
     */
    @Open(
            value = "iot.northbound.device.create",
            version = "1.0"
    )
    IotNorthboundDeviceCreateResponse createDevice(IotNorthboundDeviceCreateRequest request, OpenContext context);

    /**
     * 查询设备详情接口
     *
     * @apiNote 该接口用于平台管理员或授权应用通过北向API查询设备详细信息
     * 典型场景：应用服务器可调用此接口根据设备标识查询设备的完整信息
     */
    @Open(
            value = "iot.northbound.device.getDetail",
            version = "1.0"
    )
    IotNorthboundDeviceGetDetailResponse getDeviceDetail(IotNorthboundDeviceGetDetailRequest request, OpenContext context);

    /**
     * 下发设备命令接口
     *
     * @apiNote 该接口用于平台管理员或授权应用通过北向API向设备下发命令
     * 典型场景：应用服务器可调用此接口向一个或多个设备下发串行或并行命令
     */
    @Open(
            value = "iot.northbound.device.issueCommand",
            version = "1.0"
    )
    IotNorthboundDeviceIssueCommandResponse issueCommand(IotNorthboundDeviceIssueCommandRequest request, OpenContext context);

    /**
     * 查询设备影子接口
     *
     * @apiNote 该接口用于平台管理员或授权应用通过北向API查询设备影子信息
     * 典型场景：应用服务器可调用此接口根据设备标识查询设备的产品模型和服务信息
     */
    @Open(
            value = "iot.northbound.device.queryShadow",
            version = "1.0"
    )
    IotNorthboundDeviceQueryShadowResponse queryShadow(IotNorthboundDeviceQueryShadowRequest request, OpenContext context);

    /**
     * 修改设备状态接口
     *
     * @apiNote 该接口用于平台管理员或授权应用通过北向API修改设备状态
     * 典型场景：应用服务器可调用此接口根据设备标识修改设备状态（未激活/已激活/已禁用）
     */
    @Open(
            value = "iot.northbound.device.updateStatus",
            version = "1.0"
    )
    IotNorthboundDeviceUpdateStatusResponse updateDeviceStatus(IotNorthboundDeviceUpdateStatusRequest request, OpenContext context);

    /**
     * 修改子设备连接状态接口
     *
     * @apiNote 该接口用于平台管理员或授权应用通过北向API修改子设备连接状态
     * 典型场景：应用服务器可调用此接口批量修改子设备的在线/离线状态
     */
    @Open(
            value = "iot.northbound.device.updateSubDeviceConnectStatus",
            version = "1.0"
    )
    IotNorthboundDeviceUpdateSubDeviceConnectStatusResponse updateSubDeviceConnectStatus(IotNorthboundDeviceUpdateSubDeviceConnectStatusRequest request, OpenContext context);

    /**
     * 删除子设备接口
     *
     * @apiNote 该接口用于平台管理员或授权应用通过北向API删除子设备
     * 典型场景：应用服务器可调用此接口批量删除网关下的子设备
     */
    @Open(
            value = "iot.northbound.device.deleteSubDevice",
            version = "1.0"
    )
    IotNorthboundDeviceDeleteSubDeviceResponse deleteSubDevice(IotNorthboundDeviceDeleteSubDeviceRequest request, OpenContext context);

    /**
     * 设备数据上报接口
     *
     * @apiNote 该接口用于平台管理员或授权应用通过北向API上报设备数据
     * 典型场景：应用服务器可调用此接口批量上报设备的属性数据
     */
    @Open(
            value = "iot.northbound.device.dataReport",
            version = "1.0"
    )
    IotNorthboundDeviceDataReportResponse deviceDataReport(IotNorthboundDeviceDataReportRequest request, OpenContext context);

    /**
     * 查询设备信息接口
     *
     * @apiNote 该接口用于平台管理员或授权应用通过北向API批量查询设备信息
     * 典型场景：应用服务器可调用此接口根据设备标识列表批量查询设备详情
     */
    @Open(
            value = "iot.northbound.device.query",
            version = "1.0"
    )
    IotNorthboundDeviceQueryResponse queryDevice(IotNorthboundDeviceQueryRequest request, OpenContext context);

}
