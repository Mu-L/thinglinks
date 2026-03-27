package com.mqttsnet.thinglinks.openapi.open.iot.ota;

import com.gitee.sop.support.annotation.Open;
import com.gitee.sop.support.context.OpenContext;
import com.gitee.sop.support.exception.OpenException;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.req.IotNorthboundOtaConfirmTaskRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.req.IotNorthboundOtaListUpgradeableVersionsRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.req.IotNorthboundOtaPullRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.req.IotNorthboundOtaReadResponseRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.req.IotNorthboundOtaRejectTaskRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.req.IotNorthboundOtaReportRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.req.IotNorthboundOtaSaveUpgradeRecordRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.resp.IotNorthboundOtaConfirmTaskResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.resp.IotNorthboundOtaGetAvailableUpgradeVersionsResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.resp.IotNorthboundOtaPullResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.resp.IotNorthboundOtaReadResponseResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.resp.IotNorthboundOtaRejectTaskResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.resp.IotNorthboundOtaReportResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.ota.resp.IotNorthboundOtaSaveUpgradeRecordResponse;

/**
 * 物联网北向API-OTA升级管理
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/11/10
 */
public interface IotNorthboundOtaUpgradeApi {

    /**
     * 确认OTA升级任务接口
     *
     * @param request 确认OTA升级任务请求参数
     * @param context 上下文参数，包含认证信息和请求上下文
     * @return 操作结果响应，包含操作成功的设备标识列表
     * @throws OpenException 当确认升级任务失败时抛出，可能的原因包括：
     *                       <ul>
     *                            li>ota.confirm-upgrade-failed:确认升级失败</li>
     *                       </ul>
     * @apiNote 该接口用于批量确认处于待确认状态的设备升级作业
     * 典型场景：应用服务器可调用此接口确认一批设备的升级作业，使其进入待升级状态;
     * 具体使用方法请参考 <a href="https://mqttsnet.com" target="_blank">接入指南</a>
     */
    @Open(
            value = "iot.northbound.ota.confirmTask",
            version = "1.0"
    )
    IotNorthboundOtaConfirmTaskResponse confirmOtaTask(IotNorthboundOtaConfirmTaskRequest request, OpenContext context) throws OpenException;

    /**
     * 拒绝OTA升级任务接口
     *
     * @param request 拒绝OTA升级任务请求参数
     * @param context 上下文参数，包含认证信息和请求上下文
     * @return 操作结果响应，包含操作成功的设备标识列表
     * @throws OpenException 当拒绝升级任务失败时抛出，可能的原因包括：
     *                       <ul>
     *                           <li>ota.reject-upgrade-failed:拒绝升级失败</li>
     *                        </ul>
     * @apiNote 该接口用于批量拒绝处于待确认状态的设备升级作业
     * 典型场景：应用服务器可调用此接口拒绝一批设备的升级作业，取消设备的升级作业
     */
    @Open(
            value = "iot.northbound.ota.rejectTask",
            version = "1.0"
    )
    IotNorthboundOtaRejectTaskResponse rejectOtaTask(IotNorthboundOtaRejectTaskRequest request, OpenContext context) throws OpenException;

    /**
     * 保存OTA升级记录接口
     *
     * @param request 保存OTA升级记录请求参数
     * @param context 上下文参数，包含认证信息和请求上下文
     * @return 操作结果响应
     * @throws OpenException 当保存升级记录失败时抛出
     * @apiNote 该接口用于设备通过北向API上报OTA升级进度和结果
     * 典型场景：设备在升级过程中或升级完成后，调用此接口上报升级状态
     */
    @Open(
            value = "iot.northbound.ota.saveUpgradeRecord",
            version = "1.0"
    )
    IotNorthboundOtaSaveUpgradeRecordResponse saveOtaUpgradeRecord(IotNorthboundOtaSaveUpgradeRecordRequest request, OpenContext context) throws OpenException;

    /**
     * OTA拉取软固件信息接口
     *
     * @param request OTA拉取请求参数
     * @param context 上下文参数，包含认证信息和请求上下文
     * @return 软固件信息响应
     * @throws OpenException 当拉取失败时抛出
     * @apiNote 该接口用于设备通过北向API拉取平台最新的软固件信息
     * 典型场景：设备主动检查更新时，调用此接口获取可用的升级包信息
     */
    @Open(
            value = "iot.northbound.ota.pull",
            version = "1.0"
    )
    IotNorthboundOtaPullResponse otaPull(IotNorthboundOtaPullRequest request, OpenContext context) throws OpenException;

    /**
     * OTA上报软固件版本接口
     *
     * @param request OTA上报请求参数
     * @param context 上下文参数，包含认证信息和请求上下文
     * @return 上报结果响应
     * @throws OpenException 当上报失败时抛出
     * @apiNote 该接口用于设备通过北向API上报当前软固件版本信息
     * 典型场景：设备升级完成后或定期向平台上报当前版本信息
     */
    @Open(
            value = "iot.northbound.ota.report",
            version = "1.0"
    )
    IotNorthboundOtaReportResponse otaReport(IotNorthboundOtaReportRequest request, OpenContext context) throws OpenException;

    /**
     * OTA读取设备软固件版本信息响应接口
     *
     * @param request OTA读取响应请求参数
     * @param context 上下文参数，包含认证信息和请求上下文
     * @return 读取响应结果
     * @throws OpenException 当处理失败时抛出
     * @apiNote 该接口用于设备响应平台读取版本信息的请求
     * 典型场景：平台向设备发送读取版本命令后，设备调用此接口上报版本信息
     */
    @Open(
            value = "iot.northbound.ota.readResponse",
            version = "1.0"
    )
    IotNorthboundOtaReadResponseResponse otaReadResponse(IotNorthboundOtaReadResponseRequest request, OpenContext context) throws OpenException;

    /**
     * OTA获取可升级版本列表接口
     *
     * @param request 获取可升级版本列表请求参数
     * @param context 上下文参数，包含认证信息和请求上下文
     * @return 可升级版本列表响应
     * @throws OpenException 当获取失败时抛出
     * @apiNote 该接口用于获取指定设备的所有可升级版本信息
     * 典型场景：设备或应用服务器获取从当前版本之后的所有可升级版本列表，按创建时间倒序排序
     */
    @Open(
            value = "iot.northbound.ota.getAvailableUpgradeVersions",
            version = "1.0"
    )
    IotNorthboundOtaGetAvailableUpgradeVersionsResponse getAvailableUpgradeVersions(IotNorthboundOtaListUpgradeableVersionsRequest request, OpenContext context) throws OpenException;

}