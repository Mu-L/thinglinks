package com.mqttsnet.thinglinks.link.facade;

import java.util.List;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.device.entity.DeviceAction;
import com.mqttsnet.thinglinks.device.entity.DeviceCommand;
import com.mqttsnet.thinglinks.device.vo.result.DeviceDetailsResultVO;
import com.mqttsnet.thinglinks.device.vo.result.DeviceResultVO;
import com.mqttsnet.thinglinks.device.vo.save.DeviceActionSaveVO;
import com.mqttsnet.thinglinks.device.vo.save.DeviceCommandSaveVO;
import com.mqttsnet.thinglinks.device.vo.save.DeviceSaveVO;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import com.mqttsnet.thinglinks.protocol.vo.param.DeviceCommandWrapperParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoAddSubDeviceParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoDeleteSubDeviceParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoDeviceDataReportParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoQueryDeviceParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoUpdateSubDeviceStatusParam;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceCommandResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoAddDeviceResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoDeviceOperationResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoQueryDeviceResultVO;

/**
 * @program: thinglinks-cloud
 * @description: 设备-开放接口API
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-05-06 12:35
 **/
public interface DeviceOpenAnyUserFacade {


    /**
     * 更改设备连接状态
     *
     * @param clientIdentifier 客户端标识
     * @param connectionStatus 连接状态
     * @return
     */
    R<Boolean> updateDeviceConnectionStatus(String clientIdentifier, Integer connectionStatus);


    /**
     * （MQTT）协议新增子设备档案
     *
     * @param topoAddSubDeviceParam 子设备参数
     * @return {@link TopoAddDeviceResultVO} 新增结果
     */
    R<TopoAddDeviceResultVO> saveSubDeviceByMqtt(TopoAddSubDeviceParam topoAddSubDeviceParam);


    /**
     * （HTTP）协议新增子设备档案
     *
     * @param topoAddSubDeviceParam 子设备参数
     * @return {@link TopoAddDeviceResultVO} 新增结果
     */
    R<TopoAddDeviceResultVO> saveSubDeviceByNorthbound(TopoAddSubDeviceParam topoAddSubDeviceParam);


    /**
     * MQTT协议修改子设备连接状态
     *
     * @param topoUpdateSubDeviceStatusParam 连接状态参数
     * @return {@link TopoDeviceOperationResultVO} 修改结果
     */
    R<TopoDeviceOperationResultVO> updateSubDeviceConnectStatusByMqtt(TopoUpdateSubDeviceStatusParam topoUpdateSubDeviceStatusParam);


    /**
     * 北向API-修改子设备连接状态
     *
     * @param topoUpdateSubDeviceStatusParam 连接状态参数
     * @return {@link TopoDeviceOperationResultVO} 修改结果
     */
    R<TopoDeviceOperationResultVO> updateSubDeviceConnectStatusByNorthbound(TopoUpdateSubDeviceStatusParam topoUpdateSubDeviceStatusParam);

    /**
     * MQTT协议删除子设备
     *
     * @param topoDeleteSubDeviceParam 删除参数
     * @return {@link TopoDeviceOperationResultVO} 修改结果
     */
    R<TopoDeviceOperationResultVO> deleteSubDeviceByMqtt(TopoDeleteSubDeviceParam topoDeleteSubDeviceParam);


    /**
     * 北向API-删除子设备
     *
     * @param topoDeleteSubDeviceParam 删除参数
     * @return {@link TopoDeviceOperationResultVO} 删除结果
     */
    R<TopoDeviceOperationResultVO> deleteSubDeviceByNorthbound(TopoDeleteSubDeviceParam topoDeleteSubDeviceParam);


    /**
     * MQTT协议数据上报
     *
     * @param topoDeviceDataReportParam 数据上报参数
     * @return {@link TopoDeviceOperationResultVO} 上报结果
     */
    R<TopoDeviceOperationResultVO> deviceDataReportByMqtt(TopoDeviceDataReportParam topoDeviceDataReportParam);

    /**
     * 北向API-设备数据上报
     *
     * @param topoDeviceDataReportParam 数据上报参数
     * @return {@link TopoDeviceOperationResultVO} 上报结果
     */
    R<TopoDeviceOperationResultVO> deviceDataReportByNorthbound(TopoDeviceDataReportParam topoDeviceDataReportParam);


    /**
     * 新增设备动作
     *
     * @param deviceActionSaveVO 新增设备动作参数
     * @return {@link DeviceAction} 结果
     */
    R<DeviceAction> saveDeviceAction(DeviceActionSaveVO deviceActionSaveVO);


    /**
     * Creates a new device command entry in the database.
     *
     * @param deviceCommandSaveVO The device command data to be saved.
     * @return The saved device command data.
     */
    R<DeviceCommand> saveDeviceCommand(DeviceCommandSaveVO deviceCommandSaveVO);


    /**
     * Queries device information using the MQTT protocol.
     *
     * @param topoQueryDeviceParam The device query parameters.
     * @return {@link R<TopoQueryDeviceResultVO>} The result of the device query.
     */
    R<TopoQueryDeviceResultVO> queryDeviceByMqtt(TopoQueryDeviceParam topoQueryDeviceParam);

    /**
     * 北向API-查询设备信息
     *
     * @param topoQueryDeviceParam 设备查询参数
     * @return {@link R<TopoQueryDeviceResultVO>} 设备查询结果
     */
    R<TopoQueryDeviceResultVO> queryDeviceByNorthbound(TopoQueryDeviceParam topoQueryDeviceParam);


    /**
     * Reports the heartbeat of a device.
     *
     * @param clientIdentifier The unique identifier of the device.
     * @param heartbeatTime    The timestamp of the heartbeat.
     * @return {@link R<Boolean>} A response wrapper indicating the success of the heartbeat report.
     */
    R<Boolean> reportDeviceHeartbeat(String clientIdentifier, Long heartbeatTime);

    /**
     * 北向API-保存设备
     *
     * @param deviceSaveVO 设备保存参数
     * @return {@link R<DeviceResultVO>} 保存的设备信息
     */
    R<DeviceResultVO> saveDeviceByNorthbound(DeviceSaveVO deviceSaveVO);


    /**
     * 北向API-根据设备标识查询设备详情
     *
     * @param deviceIdentification 设备标识
     * @return {@link R<DeviceDetailsResultVO>} 设备详情信息
     */
    R<DeviceDetailsResultVO> getDeviceDetailByNorthbound(String deviceIdentification);

    /**
     * 下发设备命令（串行和并行）
     *
     * @param commandWrapper 命令包装参数
     * @return {@link R<List<DeviceCommandResultVO>>} 下发结果
     */
    R<List<DeviceCommandResultVO>> issueCommands(DeviceCommandWrapperParam commandWrapper);

    /**
     * 北向API-查询设备影子
     *
     * @param deviceIdentification 设备标识
     * @param startTime            开始时间戳（选填）
     * @param endTime              结束时间戳（选填）
     * @param serviceCode          服务编码（选填）
     * @return {@link R<ProductResultVO>} 设备影子信息
     */
    R<ProductResultVO> queryDeviceShadowByNorthbound(String deviceIdentification, Long startTime, Long endTime, String serviceCode);

    /**
     * 北向API-修改设备状态
     *
     * @param deviceIdentification 设备标识
     * @param status               设备状态（0:未激活、1:已激活、2:已禁用）
     * @return {@link R<Boolean>} 修改结果
     */
    R<Boolean> updateDeviceStatusByNorthbound(String deviceIdentification, Integer status);
}
