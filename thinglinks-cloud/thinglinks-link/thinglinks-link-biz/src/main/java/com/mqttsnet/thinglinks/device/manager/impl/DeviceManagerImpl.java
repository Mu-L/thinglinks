package com.mqttsnet.thinglinks.device.manager.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.database.mybatis.conditions.query.LbQueryWrap;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.basic.echo.properties.EchoProperties;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.basic.utils.StringUtils;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.cache.vo.product.ProductCacheVO;
import com.mqttsnet.thinglinks.device.dto.DeviceOverviewResultDTO;
import com.mqttsnet.thinglinks.device.dto.DeviceVersionDTO;
import com.mqttsnet.thinglinks.device.entity.Device;
import com.mqttsnet.thinglinks.device.manager.DeviceManager;
import com.mqttsnet.thinglinks.device.mapper.DeviceMapper;
import com.mqttsnet.thinglinks.device.vo.query.DeviceDetailsPageQuery;
import com.mqttsnet.thinglinks.device.vo.query.DevicePageQuery;
import com.mqttsnet.thinglinks.device.vo.result.DeviceResultVO;
import com.mqttsnet.thinglinks.product.manager.ProductManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类 设备档案信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DeviceManagerImpl extends SuperManagerImpl<DeviceMapper, Device> implements DeviceManager {

    private final DeviceMapper deviceMapper;

    private final ProductManager productManager;

    private final EchoProperties ips;


    @Override
    public Long findDeviceTotal() {
        return deviceMapper.selectCount(null);
    }

    @Override
    public Device findOneById(Long id) {
        if (id == null) {
            return null;
        }
        QueryWrap<Device> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(Device::getId, id);
        return deviceMapper.selectOne(queryWrap);
    }

    /**
     * 根据客户端ID查询设备信息
     *
     * @param clientId 客户端ID
     * @return 设备信息
     */
    @Override
    public Device findOneByClientId(String clientId) {
        QueryWrap<Device> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(clientId), Device::getClientId, clientId);
        return deviceMapper.selectOne(queryWrap);
    }

    /**
     * 根据条件查询设备信息
     *
     * @param query 查询条件
     * @return Device 设备信息
     */
    @Override
    public List<Device> getDevicList(DevicePageQuery query) {
        QueryWrap<Device> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(null != query.getId(), Device::getId, query.getId());
        queryWrap.lambda().in(CollUtil.isNotEmpty(query.getIds()), Device::getId, query.getIds());
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(query.getClientId()), Device::getClientId, query.getClientId());
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(query.getDeviceName()), Device::getDeviceName, query.getDeviceName());
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(query.getPassword()), Device::getPassword, query.getPassword());
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(query.getDeviceIdentification()), Device::getDeviceIdentification, query.getDeviceIdentification());
        queryWrap.lambda().in(CollUtil.isNotEmpty(query.getDeviceIdentificationList()), Device::getDeviceIdentification, query.getDeviceIdentificationList());
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(query.getProductIdentification()), Device::getProductIdentification, query.getProductIdentification());
        queryWrap.lambda().in(CollUtil.isNotEmpty(query.getProductIdentificationList()), Device::getProductIdentification, query.getProductIdentificationList());
        queryWrap.lambda().eq(query.getDeviceStatus() != null, Device::getDeviceStatus, query.getDeviceStatus());
        queryWrap.lambda().eq(query.getConnectStatus() != null, Device::getConnectStatus, query.getConnectStatus());
        queryWrap.lambda().in(CollUtil.isNotEmpty(query.getFwVersionList()), Device::getFwVersion, query.getFwVersionList());
        queryWrap.lambda().in(CollUtil.isNotEmpty(query.getSwVersionList()), Device::getSwVersion, query.getSwVersionList());
        return deviceMapper.selectList(queryWrap);
    }

    /**
     * 根据设备标识查询设备信息
     *
     * @param deviceIdentification 设备标识
     * @return {@link Device} 设备信息
     */
    @Override
    public Device findOneByDeviceIdentification(String deviceIdentification) {
        QueryWrap<Device> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(deviceIdentification), Device::getDeviceIdentification, deviceIdentification);
        return deviceMapper.selectOne(queryWrap);
    }

    /**
     * 查询设备详情分页信息
     *
     * @param params 分页参数
     * @return {@link IPage<Device>} 设备详情分页信息
     */
    @Override
    public IPage<Device> getDeviceDetailsPage(PageParams<DeviceDetailsPageQuery> params) {
        IPage<Device> page = params.buildPage(Device.class);
        DeviceDetailsPageQuery paramsModel = params.getModel();

        // 构建查询条件
        LbQueryWrap<Device> wrap = Wraps.lbQ();
        wrap.eq(!StringUtils.isEmpty(paramsModel.getDeviceIdentification()), Device::getDeviceIdentification, paramsModel.getDeviceIdentification())
                .in(!CollUtil.isEmpty(paramsModel.getDeviceIdentificationList()), Device::getDeviceIdentification, paramsModel.getDeviceIdentificationList())
                .eq(!StringUtils.isEmpty(paramsModel.getDeviceName()), Device::getDeviceName, paramsModel.getDeviceName())
                .eq(!StringUtils.isEmpty(paramsModel.getProductIdentification()), Device::getProductIdentification, paramsModel.getProductIdentification())
                .eq(!StringUtils.isEmpty(paramsModel.getGatewayId()), Device::getGatewayId, paramsModel.getGatewayId())
                .in(!CollUtil.isEmpty(paramsModel.getGatewayIdList()), Device::getGatewayId, paramsModel.getGatewayIdList())
                .eq(paramsModel.getNodeType() != null, Device::getNodeType, paramsModel.getNodeType());

        return deviceMapper.selectPage(page, wrap);
    }


    @Override
    public IPage<Device> getPage(PageParams<DevicePageQuery> params) {
        IPage<Device> page = params.buildPage(Device.class);

        DevicePageQuery paramsModel = params.getModel();

        LbQueryWrap<Device> wrap = Wraps.lbQ();
        wrap.eq(StrUtil.isNotBlank(paramsModel.getDeviceIdentification()), Device::getDeviceIdentification, paramsModel.getDeviceIdentification())
                .in(!CollUtil.isEmpty(paramsModel.getDeviceIdentificationList()), Device::getDeviceIdentification, paramsModel.getDeviceIdentificationList())
                .eq(!StringUtils.isEmpty(paramsModel.getDeviceName()), Device::getDeviceName, paramsModel.getDeviceName())
                .eq(!StringUtils.isEmpty(paramsModel.getProductIdentification()), Device::getProductIdentification, paramsModel.getProductIdentification())
                .eq(!StringUtils.isEmpty(paramsModel.getGatewayId()), Device::getGatewayId, paramsModel.getGatewayId())
                .in(!CollUtil.isEmpty(paramsModel.getGatewayIdList()), Device::getGatewayId, paramsModel.getGatewayIdList())
                .in(CollUtil.isNotEmpty(paramsModel.getDeviceStatusList()), Device::getDeviceStatus, paramsModel.getDeviceStatusList())
                .eq(paramsModel.getDeviceStatus() != null, Device::getDeviceStatus, paramsModel.getDeviceStatus())
                .eq(paramsModel.getNodeType() != null, Device::getNodeType, paramsModel.getNodeType())
                .in(CollUtil.isNotEmpty(paramsModel.getNodeTypeList()), Device::getNodeType, paramsModel.getNodeTypeList());

        return deviceMapper.selectPage(page, wrap);
    }


    @Override
    public DeviceOverviewResultDTO selectDeviceOverview(PageParams<DevicePageQuery> params) {
        DevicePageQuery paramsModel = params.getModel();
        LambdaQueryWrapper<Device> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StrUtil.isNotBlank(paramsModel.getDeviceIdentification()), Device::getDeviceIdentification, paramsModel.getDeviceIdentification())
                .in(!CollUtil.isEmpty(paramsModel.getDeviceIdentificationList()), Device::getDeviceIdentification, paramsModel.getDeviceIdentificationList())
                .eq(!StringUtils.isEmpty(paramsModel.getDeviceName()), Device::getDeviceName, paramsModel.getDeviceName())
                .eq(!StringUtils.isEmpty(paramsModel.getProductIdentification()), Device::getProductIdentification, paramsModel.getProductIdentification())
                .eq(!StringUtils.isEmpty(paramsModel.getGatewayId()), Device::getGatewayId, paramsModel.getGatewayId())
                .in(!CollUtil.isEmpty(paramsModel.getGatewayIdList()), Device::getGatewayId, paramsModel.getGatewayIdList())
                .eq(paramsModel.getNodeType() != null, Device::getNodeType, paramsModel.getNodeType());

        return deviceMapper.selectDeviceOverview(wrapper);
    }

    /**
     * 根据设备标识或客户端ID查询设备
     *
     * @param deviceIdOrClientId 设备标识或客户端ID，为空时返回null
     * @return 设备实体对象，查询不到或参数为空时返回null，优先匹配设备标识
     */
    @Override
    public Device findOneByIdOrClientId(String deviceIdOrClientId) {
        if (StrUtil.isBlank(deviceIdOrClientId)) {
            return null;
        }
        LambdaQueryWrapper<Device> wrapper = Wrappers.lambdaQuery();
        wrapper.and(wq -> wq
                .eq(Device::getDeviceIdentification, deviceIdOrClientId)
                .or()
                .eq(Device::getClientId, deviceIdOrClientId)
        ).last("LIMIT 1");
        return getBaseMapper().selectOne(wrapper);
    }


    /**
     * 根据租户ID和设备ID或客户端ID查询设备缓存信息实体
     *
     * @param tenantId           租户ID
     * @param deviceIdOrClientId 设备ID或客户端ID
     * @return {@link Optional<DeviceCacheVO>} 设备缓存信息实体
     */
    @Override
    public Optional<DeviceCacheVO> findDeviceCacheVO(Long tenantId, String deviceIdOrClientId) {
        ContextUtil.setTenantId(tenantId);
        Device device = this.findOneByIdOrClientId(deviceIdOrClientId);
        if (Objects.isNull(device)) {
            log.warn("设备档案信息不存在..deviceIdOrClientId:{}", deviceIdOrClientId);
            return Optional.empty();
        }
        DeviceCacheVO deviceCacheVO = this.transformToDeviceCacheVO(tenantId, BeanPlusUtil.toBeanIgnoreError(device, DeviceResultVO.class));
        return Optional.of(deviceCacheVO);
    }

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
    @Override
    public DeviceCacheVO transformToDeviceCacheVO(Long tenantId, DeviceResultVO deviceResultVO) {
        DeviceCacheVO deviceCacheVO = BeanUtil.toBeanIgnoreError(deviceResultVO, DeviceCacheVO.class);
        deviceCacheVO.setTenantId(tenantId);
        Optional.ofNullable(deviceCacheVO.getProductIdentification())
                .map(productManager::findOneByProductIdentification)
                .ifPresent(product -> {
                    ProductCacheVO productCacheVO = BeanPlusUtil.toBeanIgnoreError(product, ProductCacheVO.class);
                    productCacheVO.setTenantId(tenantId);
                    deviceCacheVO.setProductCacheVO(productCacheVO);
                });

        return deviceCacheVO;
    }

    /**
     * 根据产品标识查询设备版本信息
     *
     * @param productIdentification 产品标识
     * @return {@link Optional<DeviceVersionDTO>} 设备版本信息
     */
    @Override
    public Optional<DeviceVersionDTO> selectDeviceVersionsByProduct(String productIdentification) {
        if (StrUtil.isBlank(productIdentification)) {
            return Optional.empty();
        }
        DeviceVersionDTO result = deviceMapper.selectDeviceVersionsByProduct(productIdentification);
        return Optional.ofNullable(result);
    }

}