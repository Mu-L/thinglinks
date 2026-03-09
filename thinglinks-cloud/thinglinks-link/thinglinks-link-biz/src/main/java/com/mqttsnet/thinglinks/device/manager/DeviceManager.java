package com.mqttsnet.thinglinks.device.manager;

import java.util.List;
import java.util.Optional;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.device.dto.DeviceOverviewResultDTO;
import com.mqttsnet.thinglinks.device.dto.DeviceVersionDTO;
import com.mqttsnet.thinglinks.device.entity.Device;
import com.mqttsnet.thinglinks.device.vo.query.DeviceDetailsPageQuery;
import com.mqttsnet.thinglinks.device.vo.query.DevicePageQuery;
import com.mqttsnet.thinglinks.device.vo.result.DeviceResultVO;

/**
 * <p>
 * 通用业务接口
 * 设备档案信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
public interface DeviceManager extends SuperManager<Device> {

    Long findDeviceTotal();

    /**
     * 根据设备ID查询设备信息
     *
     * @param id 设备ID
     * @return 设备信息
     */
    Device findOneById(Long id);

    /**
     * 根据客户端ID查询设备信息
     *
     * @param clientId 客户端ID
     * @return 设备信息
     */
    Device findOneByClientId(String clientId);

    /**
     * 根据条件查询设备信息
     *
     * @param query 查询条件
     * @return Device 设备信息
     */
    List<Device> getDevicList(DevicePageQuery query);


    /**
     * 根据设备标识查询设备信息
     *
     * @param deviceIdentification 设备标识
     * @return {@link Device} 设备信息
     */
    Device findOneByDeviceIdentification(String deviceIdentification);


    /**
     * 查询设备详情分页信息
     *
     * @param params 分页参数
     * @return {@link IPage<Device>} 设备详情分页信息
     */
    IPage<Device> getDeviceDetailsPage(PageParams<DeviceDetailsPageQuery> params);

    /**
     * 分页查询设备档案信息
     *
     * @param params 分页参数
     * @return {@link IPage<Device>} 分页数据
     */
    IPage<Device> getPage(PageParams<DevicePageQuery> params);

    /**
     * 查询设备概览信息
     *
     * @param params 分页参数
     * @return {@link DeviceOverviewResultDTO} 设备概览信息
     */
    DeviceOverviewResultDTO selectDeviceOverview(PageParams<DevicePageQuery> params);

    /**
     * 根据产品标识查询设备版本信息
     *
     * @param productIdentification 产品标识
     * @return {@link Optional<DeviceVersionDTO>} 设备版本信息
     */
    Optional<DeviceVersionDTO> selectDeviceVersionsByProduct(String productIdentification);


    /**
     * 根据设备标识或客户端ID查询设备
     *
     * @param deviceIdOrClientId 设备标识或客户端ID
     * @return {@link Device} 设备信息
     */
    Device findOneByIdOrClientId(String deviceIdOrClientId);

    /**
     * 根据租户ID和设备ID或客户端ID查询设备缓存信息实体
     *
     * @param tenantId           租户ID
     * @param deviceIdOrClientId 设备ID或客户端ID
     * @return {@link Optional<DeviceCacheVO>} 设备缓存信息实体
     */
    Optional<DeviceCacheVO> findDeviceCacheVO(Long tenantId, String deviceIdOrClientId);

    /**
     * 转换设备结果为设备缓存对象
     *
     * @param tenantId       租户ID，不能为null
     * @param deviceResultVO 设备结果VO，不能为null
     * @return 设备缓存VO，不会返回null
     * @throws IllegalArgumentException 如果任何参数为null
     * @throws RuntimeException         当转换失败时抛出
     * @see BeanUtil#toBeanIgnoreError(Object, Class)
     */
    DeviceCacheVO transformToDeviceCacheVO(Long tenantId, DeviceResultVO deviceResultVO);
}