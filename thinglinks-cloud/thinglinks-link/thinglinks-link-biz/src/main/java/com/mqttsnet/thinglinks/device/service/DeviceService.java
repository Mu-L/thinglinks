package com.mqttsnet.thinglinks.device.service;

import java.util.List;
import java.util.Optional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.device.entity.Device;
import com.mqttsnet.thinglinks.device.vo.query.DeviceAuthenticationQuery;
import com.mqttsnet.thinglinks.device.vo.query.DeviceDetailsPageQuery;
import com.mqttsnet.thinglinks.device.vo.query.DevicePageQuery;
import com.mqttsnet.thinglinks.device.vo.result.DeviceDetailsResultVO;
import com.mqttsnet.thinglinks.device.vo.result.DeviceOverviewResultVO;
import com.mqttsnet.thinglinks.device.vo.result.DeviceResultVO;
import com.mqttsnet.thinglinks.device.vo.result.DeviceVersionResultVO;
import com.mqttsnet.thinglinks.device.vo.save.DeviceSaveVO;
import com.mqttsnet.thinglinks.device.vo.update.DeviceUpdateVO;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoAddSubDeviceParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoDeleteSubDeviceParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoDeviceDataReportParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoQueryDeviceParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoUpdateSubDeviceStatusParam;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceAuthenticationResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoAddDeviceResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoDeviceOperationResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoQueryDeviceResultVO;


/**
 * <p>
 * 业务接口
 * 设备档案信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
public interface DeviceService extends SuperService<Long, Device> {

    /**
     * 分页查询设备档案信息
     *
     * @param params 分页参数
     * @return {@link IPage<DeviceResultVO>} 分页数据
     */
    IPage<DeviceResultVO> getPage(PageParams<DevicePageQuery> params);

    /**
     * 获取设备档案总量
     *
     * @return {@link Long} 设备档案数据总量
     */
    Long findDeviceTotal();

    /**
     * 客户端认证
     *
     * @param deviceAuthenticationQuery 设备认证查询对象
     * @return 认证结果 {@link DeviceAuthenticationResultVO}
     */
    DeviceAuthenticationResultVO authClient(DeviceAuthenticationQuery deviceAuthenticationQuery);

    /**
     * 保存设备档案
     *
     * @param saveVO 保存参数
     * @return {@link DeviceSaveVO} 新增实体
     */
    DeviceSaveVO saveDevice(DeviceSaveVO saveVO);

    /**
     * 北向API保存设备档案
     * 保存设备并返回完整的设备信息，专用于北向接口
     *
     * @param saveVO 保存参数
     * @return {@link DeviceResultVO} 设备结果信息
     */
    DeviceResultVO saveDeviceByNorthbound(DeviceSaveVO saveVO);


    /**
     * 修改设备档案
     *
     * @param updateVO 更新参数
     * @return {@link DeviceUpdateVO} 更新结果
     */
    DeviceUpdateVO updateDevice(DeviceUpdateVO updateVO);

    /**
     * 根据设备ID更新设备状态
     *
     * @param id     设备ID
     * @param status 设备状态
     * @return {@link Boolean} 更新结果
     */
    Boolean updateDeviceStatus(Long id, Integer status);

    /**
     * 根据设备ID删除设备
     *
     * @param id 设备ID
     * @return {@link Boolean} 删除结果
     */
    Boolean deleteDevice(Long id);


    /**
     * 根据客户端ID查询设备信息
     *
     * @param clientId 客户端ID
     * @return {@link DeviceResultVO} 设备信息
     */
    DeviceResultVO findOneByClientId(String clientId);


    /**
     * 根据设备标识查询设备信息
     *
     * @param deviceIdentification 设备标识
     * @return {@link DeviceDetailsResultVO} 设备信息
     */
    DeviceDetailsResultVO findOneByDeviceIdentification(String deviceIdentification);

    /**
     * 根据设备标识查询设备信息
     *
     * @param deviceIdentification 设备标识
     * @return {@link DeviceResultVO} 设备信息
     */
    DeviceResultVO findByDeviceIdentification(String deviceIdentification);

    /**
     * 修改设备连接状态
     *
     * @param id               设备ID
     * @param connectionStatus 新连接状态值
     * @return 更新结果
     * @throws IllegalArgumentException 如果设备ID或连接状态值无效
     */
    boolean updateDeviceConnectionStatusById(Long id, Integer connectionStatus) throws IllegalArgumentException;


    /**
     * 查询设备信息VO列表
     *
     * @param query 查询参数
     * @return {@link List<DeviceResultVO>} 设备信息VO列表
     */
    List<DeviceResultVO> getDeviceResultVOList(DevicePageQuery query);

    /**
     * 查询设备信息VO详情列表 （包含设备详情、产品）
     *
     * @param query 查询参数
     * @return {@link List<DeviceDetailsResultVO>} 设备信息VO详情列表 （包含设备详情、产品）
     */
    List<DeviceDetailsResultVO> getDeviceDetailsResultVOList(DevicePageQuery query);

    /**
     * 获取设备概述统计信息
     *
     * @return {@link DeviceOverviewResultVO} 设备概述统计信息VO
     */
    DeviceOverviewResultVO getDeviceOverview();

    /**
     * 根据产品标识查询设备软固件版本集合信息
     *
     * @param productIdentification 产品标识
     * @return {@link DeviceVersionResultVO} 软固件版本集合信息
     */
    DeviceVersionResultVO getDeviceVersionByProduct(String productIdentification);

    /**
     * MQTT协议下添加子设备
     *
     * @param topoAddSubDeviceParam 子设备参数
     * @return {@link TopoAddDeviceResultVO} 添加结果
     */
    TopoAddDeviceResultVO saveSubDeviceByMqtt(TopoAddSubDeviceParam topoAddSubDeviceParam);

    /**
     * 北向API添加子设备
     *
     * @param topoAddSubDeviceParam 子设备参数
     * @return {@link TopoAddDeviceResultVO} 添加结果
     */
    TopoAddDeviceResultVO saveSubDeviceByNorthbound(TopoAddSubDeviceParam topoAddSubDeviceParam);

    /**
     * MQTT协议下更新子设备连接状态
     *
     * @param topoUpdateSubDeviceStatusParam 更新参数
     * @return {@link TopoDeviceOperationResultVO} 更新结果
     */
    TopoDeviceOperationResultVO updateSubDeviceConnectStatusByMqtt(TopoUpdateSubDeviceStatusParam topoUpdateSubDeviceStatusParam);

    /**
     * 北向API更新子设备连接状态
     *
     * @param topoUpdateSubDeviceStatusParam 更新参数
     * @return {@link TopoDeviceOperationResultVO} 更新结果
     */
    TopoDeviceOperationResultVO updateSubDeviceConnectStatusByNorthbound(TopoUpdateSubDeviceStatusParam topoUpdateSubDeviceStatusParam);

    /**
     * MQTT协议下删除子设备
     *
     * @param topoDeleteSubDeviceParam 删除参数
     * @return {@link TopoDeviceOperationResultVO} 删除结果
     */
    TopoDeviceOperationResultVO deleteSubDeviceByMqtt(TopoDeleteSubDeviceParam topoDeleteSubDeviceParam);


    /**
     * 北向API删除子设备
     *
     * @param topoDeleteSubDeviceParam 删除参数
     * @return {@link TopoDeviceOperationResultVO} 删除结果
     */
    TopoDeviceOperationResultVO deleteSubDeviceByNorthbound(TopoDeleteSubDeviceParam topoDeleteSubDeviceParam);

    /**
     * MQTT协议下上报设备数据
     *
     * @param topoDeviceDataReportParam 上报参数
     * @return {@link TopoDeviceOperationResultVO} 上报结果
     */
    TopoDeviceOperationResultVO deviceDataReportByMqtt(TopoDeviceDataReportParam topoDeviceDataReportParam);


    /**
     * 北向API上报设备数据
     *
     * @param topoDeviceDataReportParam 上报参数
     * @return {@link TopoDeviceOperationResultVO} 上报结果
     */
    TopoDeviceOperationResultVO deviceDataReportByNorthbound(TopoDeviceDataReportParam topoDeviceDataReportParam);


    /**
     * 根据设备ID查询设备详情
     *
     * @param id 设备ID
     * @return {@link DeviceDetailsResultVO} 设备详情
     */
    DeviceDetailsResultVO getDeviceDetails(Long id);

    /**
     * 获取设备详情分页信息
     *
     * @param params 查询参数
     * @return {@link IPage<Device>} 设备详情分页信息
     */
    IPage<DeviceDetailsResultVO> getDeviceDetailsPage(PageParams<DeviceDetailsPageQuery> params);


    /**
     * Checks if any device is currently using the specified product identified by its identification string.
     * <p>
     * This method determines whether the product, identified by the given identification string, is associated with
     * any active devices. This check is essential in scenarios where it's necessary to understand product usage
     * before performing operations like product deletion or modification. By verifying that no active devices are
     * linked to the product, it helps in maintaining data integrity and preventing operational conflicts.
     * </p>
     *
     * @param productIdentification The identification string of the product to be checked.
     * @return {@code true} if the product is currently in use by any device; {@code false} otherwise.
     * @throws IllegalArgumentException if the productIdentification is null or empty.
     */
    boolean isProductInUseByDevices(String productIdentification);

    /**
     * Queries device information using the MQTT protocol.
     *
     * @param topoQueryDeviceParam The device query parameters.
     * @return {@link TopoQueryDeviceResultVO} The result of the device query.
     */
    TopoQueryDeviceResultVO queryDeviceByMqtt(TopoQueryDeviceParam topoQueryDeviceParam);

    /**
     * 北向API查询设备信息
     *
     * @param topoQueryDeviceParam The device query parameters.
     * @return {@link TopoQueryDeviceResultVO} The result of the device query.
     */
    TopoQueryDeviceResultVO queryDeviceByNorthbound(TopoQueryDeviceParam topoQueryDeviceParam);

    /**
     * 根据租户ID和设备ID或客户端ID查询设备缓存信息实体
     *
     * @param tenantId           租户ID
     * @param deviceIdOrClientId 设备ID或客户端ID
     * @return {@link Optional<DeviceCacheVO>} 设备缓存信息实体
     */
    Optional<DeviceCacheVO> findDeviceCacheVO(Long tenantId, String deviceIdOrClientId);

    /**
     * 上报设备心跳信息
     *
     * @param clientIdentifier 客户端标识符
     * @param heartbeatTime    心跳时间(毫秒时间戳)
     * @return 上报结果
     */
    Boolean reportDeviceHeartbeat(String clientIdentifier, Long heartbeatTime);
}