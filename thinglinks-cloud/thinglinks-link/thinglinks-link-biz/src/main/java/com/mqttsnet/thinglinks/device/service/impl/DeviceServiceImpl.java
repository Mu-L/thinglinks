package com.mqttsnet.thinglinks.device.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.converter.Builder;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.basic.utils.SnowflakeIdUtil;
import com.mqttsnet.basic.utils.TenantUtil;
import com.mqttsnet.thinglinks.cacert.service.license.CaCertLicenseService;
import com.mqttsnet.thinglinks.cacert.vo.result.license.CaCertLicenseResultVO;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.device.entity.Device;
import com.mqttsnet.thinglinks.device.entity.DeviceAction;
import com.mqttsnet.thinglinks.device.enumeration.DeviceActionStatusEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceActionTypeEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceAuthModeEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceConnectStatusEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceEncryptMethodEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceNodeTypeEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceStatusEnum;
import com.mqttsnet.thinglinks.device.event.publisher.DeviceEventPublisher;
import com.mqttsnet.thinglinks.device.event.source.DeviceInfoUpdatedEventSource;
import com.mqttsnet.thinglinks.device.manager.DeviceManager;
import com.mqttsnet.thinglinks.device.service.DeviceActionService;
import com.mqttsnet.thinglinks.device.service.DeviceLocationService;
import com.mqttsnet.thinglinks.device.service.DeviceService;
import com.mqttsnet.thinglinks.device.vo.query.DeviceAuthenticationQuery;
import com.mqttsnet.thinglinks.device.vo.query.DeviceDetailsPageQuery;
import com.mqttsnet.thinglinks.device.vo.query.DeviceLocationPageQuery;
import com.mqttsnet.thinglinks.device.vo.query.DevicePageQuery;
import com.mqttsnet.thinglinks.device.vo.result.DeviceDetailsResultVO;
import com.mqttsnet.thinglinks.device.vo.result.DeviceLocationResultVO;
import com.mqttsnet.thinglinks.device.vo.result.DeviceOverviewResultVO;
import com.mqttsnet.thinglinks.device.vo.result.DeviceResultVO;
import com.mqttsnet.thinglinks.device.vo.result.DeviceVersionResultVO;
import com.mqttsnet.thinglinks.device.vo.save.DeviceActionSaveVO;
import com.mqttsnet.thinglinks.device.vo.save.DeviceLocationSaveVO;
import com.mqttsnet.thinglinks.device.vo.save.DeviceSaveVO;
import com.mqttsnet.thinglinks.device.vo.update.DeviceUpdateVO;
import com.mqttsnet.thinglinks.product.service.ProductService;
import com.mqttsnet.thinglinks.product.vo.query.ProductPageQuery;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import com.mqttsnet.thinglinks.protocol.enumeration.MqttProtocolTopoStatusEnum;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoAddSubDeviceParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoDeleteSubDeviceParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoDeviceDataReportParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoQueryDeviceParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoUpdateSubDeviceStatusParam;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceAuthenticationResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceInfoResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoAddDeviceResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoDeviceOperationResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoQueryDeviceResultVO;
import com.mqttsnet.thinglinks.utils.cacert.CertificateVerifierUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
 * 设备档案信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class DeviceServiceImpl extends SuperServiceImpl<DeviceManager, Long, Device> implements DeviceService {

    private final ProductService productService;

    private final DeviceLocationService deviceLocationService;

    private final DeviceActionService deviceActionService;

    private final CaCertLicenseService caCertLicenseService;

    private final DeviceEventPublisher deviceEventPublisher;

    private final LinkCacheDataHelper linkCacheDataHelper;

    @Override
    public IPage<DeviceResultVO> getPage(PageParams<DevicePageQuery> params) {
        IPage<Device> page = superManager.getPage(params);
        return BeanPlusUtil.toBeanPage(page, DeviceResultVO.class);
    }


    @Override
    public Long findDeviceTotal() {
        return superManager.findDeviceTotal();
    }

    @Override
    protected <SaveVO> void saveAfter(SaveVO saveVO, Device entity) {
        // 发布设备信息更新事件
        deviceEventPublisher.publishDeviceInfoUpdatedEvent(DeviceInfoUpdatedEventSource.builder()
                .deviceIdentificationList(Collections.singletonList(entity.getDeviceIdentification()))
                .build());
    }

    @Override
    protected <UpdateVO> void updateAfter(UpdateVO updateVO, Device entity) {
        // 发布设备信息更新事件
        deviceEventPublisher.publishDeviceInfoUpdatedEvent(DeviceInfoUpdatedEventSource.builder()
                .deviceIdentificationList(Collections.singletonList(entity.getDeviceIdentification()))
                .build());
    }

    /**
     * 客户端认证
     *
     * @param deviceAuthenticationQuery 设备认证查询对象
     * @return {@link DeviceAuthenticationResultVO} 认证结果
     */
    @Override
    public DeviceAuthenticationResultVO authClient(DeviceAuthenticationQuery deviceAuthenticationQuery) {
        log.info("设备认证请求: clientIdentifier={}, authMode={}", deviceAuthenticationQuery.getClientIdentifier(), deviceAuthenticationQuery.getAuthMode());

        try {
            // 参数校验
            ArgumentAssert.notBlank(deviceAuthenticationQuery.getClientIdentifier(), "clientIdentifier不能为空");
            ArgumentAssert.notBlank(deviceAuthenticationQuery.getUsername(), "username不能为空");
            ArgumentAssert.notBlank(deviceAuthenticationQuery.getPassword(), "password不能为空");
            ArgumentAssert.notNull(deviceAuthenticationQuery.getAuthMode(), "authMode不能为空");

            // 查询设备缓存
            Optional<DeviceCacheVO> deviceResultVOOptional = linkCacheDataHelper.getDeviceCacheVO(deviceAuthenticationQuery.getClientIdentifier());
            if (deviceResultVOOptional.isEmpty()) {
                log.warn("设备认证失败: 设备不存在, clientIdentifier={}", deviceAuthenticationQuery.getClientIdentifier());
                return buildFailureResult("设备不存在");
            }

            DeviceResultVO device = BeanPlusUtil.toBeanIgnoreError(deviceResultVOOptional.get(), DeviceResultVO.class);

            // 校验认证模式
            DeviceAuthModeEnum deviceAuthMode = DeviceAuthModeEnum.fromValue(device.getAuthMode())
                    .orElseThrow(() -> new BizException("无效的设备认证模式: " + device.getAuthMode()));

            if (!deviceAuthMode.getValue().equals(deviceAuthenticationQuery.getAuthMode())) {
                log.warn("设备认证失败: 认证模式不匹配, 期望:{}, 实际:{}",
                        deviceAuthMode.getDesc(), deviceAuthenticationQuery.getAuthMode());
                return buildFailureResult("认证模式不匹配. 期望: " + deviceAuthMode.getDesc());
            }

            // 检查设备状态
            if (DeviceStatusEnum.DISCONNECTION_STATE_COLLECTION.contains(device.getDeviceStatus())) {
                log.warn("设备认证失败: 设备已锁定/未激活, clientIdentifier={}", deviceAuthenticationQuery.getClientIdentifier());
                return buildFailureResult("设备已锁定或未激活");
            }

            // 用户名密码认证
            if (!CharSequenceUtil.equals(deviceAuthenticationQuery.getUsername(), device.getUserName()) ||
                    !CharSequenceUtil.equals(deviceAuthenticationQuery.getPassword(), device.getPassword())) {
                log.warn("设备认证失败: 用户名或密码无效, clientIdentifier={}", deviceAuthenticationQuery.getClientIdentifier());
                return buildFailureResult("用户名或密码无效");
            }

            // SSL模式额外验证证书
            if (DeviceAuthModeEnum.SSL_MODE.getValue().equals(deviceAuthenticationQuery.getAuthMode())) {
                return verifySslCertificate(deviceAuthenticationQuery, device);
            }

            // 认证成功
            log.info("设备认证成功: clientIdentifier={}", deviceAuthenticationQuery.getClientIdentifier());
            return buildSuccessResult(device);
        } catch (BizException e) {
            log.warn("设备认证失败: {}", e.getMessage());
            return buildFailureResult(e.getMessage());
        } catch (Exception e) {
            log.error("设备认证异常: clientIdentifier={}", deviceAuthenticationQuery.getClientIdentifier(), e);
            return buildFailureResult("认证系统异常");
        }
    }

    /**
     * 验证SSL证书
     *
     * @param query  设备认证查询参数
     * @param device 设备信息
     * @return {@link DeviceAuthenticationResultVO} 认证结果
     */
    private DeviceAuthenticationResultVO verifySslCertificate(DeviceAuthenticationQuery query, DeviceResultVO device) {
        if (StrUtil.isBlank(query.getClientCertificate())) {
            log.warn("SSL认证失败: 客户端证书为空, clientIdentifier={}", query.getClientIdentifier());
            return buildFailureResult("SSL模式需要客户端证书");
        }

        try {
            String caCertificate = getCaCertificate(device.getCertSerialNumber());
            CertificateVerifierUtil.setTrustedCertificate(caCertificate);

            if (!CertificateVerifierUtil.verifyCertificate(query.getClientCertificate())) {
                log.warn("SSL认证失败: 证书无效, clientIdentifier={}", query.getClientIdentifier());
                return buildFailureResult("无效的SSL证书");
            }
            log.info("SSL证书验证成功: clientIdentifier={}", query.getClientIdentifier());
            return buildSuccessResult(device);
        } catch (Exception e) {
            log.error("SSL证书验证异常: clientIdentifier={}", query.getClientIdentifier(), e);
            return buildFailureResult("SSL证书验证错误: " + e.getMessage());
        }
    }

    /**
     * 构建认证成功结果
     *
     * @param device 设备信息
     * @return {@link DeviceAuthenticationResultVO} 认证结果
     */
    private DeviceAuthenticationResultVO buildSuccessResult(DeviceResultVO device) {
        log.info("Authentication success for device clientId: {}", device.getClientId());
        return DeviceAuthenticationResultVO.builder()
                .certificationResult(true)
                .deviceInfoResult(BeanPlusUtil.toBeanIgnoreError(device, DeviceInfoResultVO.class))
                .tenantId(ContextUtil.getTenantId())
                .build();
    }

    /**
     * 构建认证失败结果
     *
     * @param errorMessage 错误信息
     * @return {@link DeviceAuthenticationResultVO} 认证结果
     */
    private DeviceAuthenticationResultVO buildFailureResult(String errorMessage) {
        return DeviceAuthenticationResultVO.builder()
                .certificationResult(false)
                .errorMessage(errorMessage)
                .tenantId(ContextUtil.getTenantId())
                .build();
    }

    /**
     * 获取CA证书
     *
     * @param certSerialNumber 证书序列号
     * @return CA证书(Base64编码)
     */
    private String getCaCertificate(String certSerialNumber) {
        //获取CA证书
        CaCertLicenseResultVO caCertLicenseResultVO = caCertLicenseService.getByCertSerialNumber(certSerialNumber);
        return caCertLicenseResultVO.getLicenseBase64();
    }

    /**
     * 保存
     *
     * @param saveVO 保存参数
     * @return {@link DeviceSaveVO} 实体
     */
    @Override
    public DeviceSaveVO saveDevice(DeviceSaveVO saveVO) {
        log.info("saveDevice saveVO:{}", saveVO);
        //校验参数
        checkedDeviceSaveVO(saveVO);

        //构建参数
        Device device = builderDeviceSaveVO(saveVO);

        //保存设备位置信息
        if (null != saveVO.getDeviceLocationSaveVO()) {
            saveVO.getDeviceLocationSaveVO().setDeviceIdentification(device.getDeviceIdentification());
            DeviceLocationSaveVO deviceLocationSaveVO = deviceLocationService.saveDeviceLocation(saveVO.getDeviceLocationSaveVO());
            log.info("saveDevice deviceLocationSaveVO:{}", deviceLocationSaveVO);
        }
        //保存设备档案
        superManager.save(device);

        // 发布设备信息更新事件
        deviceEventPublisher.publishDeviceInfoUpdatedEvent(DeviceInfoUpdatedEventSource.builder()
                .deviceIdentificationList(Collections.singletonList(device.getDeviceIdentification()))
                .build());

        return BeanPlusUtil.toBeanIgnoreError(device, DeviceSaveVO.class);
    }

    /**
     * 北向API保存设备档案
     * 保存设备并返回完整的设备信息，专用于北向接口
     *
     * @param saveVO 保存参数
     * @return {@link DeviceResultVO} 设备结果信息
     */
    @Override
    public DeviceResultVO saveDeviceByNorthbound(DeviceSaveVO saveVO) {
        log.info("saveDeviceByNorthbound saveVO:{}", JSON.toJSONString(saveVO));
        //校验参数
        checkedDeviceSaveVO(saveVO);
        // 租户一致性校验（必须是当前租户 ContextUtil）
        if (!TenantUtil.validateTenantConsistency(saveVO.getClientId())) {
            throw BizException.wrap("Tenant information does not match. No authority to operate resources.");
        }

        Device device = BeanPlusUtil.copyProperties(saveVO, Device.class);
        device.setCreatedOrgId(ContextUtil.getCurrentDeptId());

        //保存设备档案
        superManager.save(device);
        // 发布设备信息更新事件
        deviceEventPublisher.publishDeviceInfoUpdatedEvent(DeviceInfoUpdatedEventSource.builder()
                .deviceIdentificationList(Collections.singletonList(device.getDeviceIdentification()))
                .build());

        return findByDeviceIdentification(device.getDeviceIdentification());
    }

    /**
     * 修改设备档案
     *
     * @param updateVO 更新参数
     * @return {@link DeviceUpdateVO} 更新结果
     */
    @Override
    public DeviceUpdateVO updateDevice(DeviceUpdateVO updateVO) {
        log.info("updateDevice updateVO:{}", updateVO);

        //校验参数
        checkedDeviceUpdateVO(updateVO);

        // 从数据库查询设备信息，确保设备存在
        Device existingDevice = superManager.getById(updateVO.getId());
        if (existingDevice == null) {
            throw BizException.wrap("Device not found for ID:{}", updateVO.getId());
        }

        //构建参数
        Device device = buildDeviceWithBuilder(updateVO);

        // 更新或新增设备位置信息
        updateOrInsertDeviceLocation(updateVO, existingDevice);

        //更新
        superManager.updateById(device);

        // 发布设备信息更新事件
        deviceEventPublisher.publishDeviceInfoUpdatedEvent(DeviceInfoUpdatedEventSource.builder()
                .deviceIdentificationList(Collections.singletonList(existingDevice.getDeviceIdentification()))
                .build());

        return updateVO;
    }


    /**
     * 使用Builder构建设备对象的方法
     *
     * @param updateVO 更新参数
     * @return {@link Device} 设备信息
     */
    private Device buildDeviceWithBuilder(DeviceUpdateVO updateVO) {
        return builderDeviceUpdateVO(updateVO)
                .with(Device::setId, updateVO.getId())
                .build();
    }

    /**
     * 更新或新增设备位置信息的方法
     *
     * @param updateVO       更新参数
     * @param existingDevice 现有设备信息
     */
    private void updateOrInsertDeviceLocation(DeviceUpdateVO updateVO, Device existingDevice) {
        Optional.ofNullable(updateVO.getDeviceLocationUpdateVO()).ifPresent(locationVO -> {
            locationVO.setDeviceIdentification(existingDevice.getDeviceIdentification());
            if (locationVO.getId() == null) {
                deviceLocationService.saveDeviceLocation(
                        BeanPlusUtil.toBeanIgnoreError(locationVO, DeviceLocationSaveVO.class)
                );
            } else {
                deviceLocationService.updateDeviceLocation(locationVO);
            }
        });
    }

    /**
     * 根据设备ID更新设备状态
     *
     * @param id     设备ID
     * @param status 设备状态
     * @return {@link Boolean} 更新结果
     */
    @Override
    public Boolean updateDeviceStatus(Long id, Integer status) {
        ArgumentAssert.notNull(id, "id Cannot be null");
        ArgumentAssert.notNull(status, "status Cannot be null");
        Device device = superManager.findOneById(id);
        if (Objects.isNull(device)) {
            throw BizException.wrap("The device does not exist");
        }
        if (status.equals(device.getDeviceStatus())) {
            throw BizException.wrap("The device status is the same as the current status");
        }
        // 更新设备连接状态
        UpdateWrapper<Device> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(Device::getId, device.getId())
                .set(Device::getDeviceStatus, status);
        return superManager.update(updateWrapper);
    }

    /**
     * 根据设备ID删除设备
     *
     * @param id 设备ID
     * @return {@link Boolean} 删除结果
     */
    @Override
    public Boolean deleteDevice(Long id) {
        ArgumentAssert.notNull(id, "id Cannot be null");
        Device device = superManager.findOneById(id);
        if (Objects.isNull(device)) {
            throw BizException.wrap("The device does not exist");
        }
        //删除设备相关缓存
        linkCacheDataHelper.deleteDeviceCacheVO(device.getDeviceIdentification());
        linkCacheDataHelper.deleteDeviceCacheVO(device.getClientId());
        //TODO 校验设备是否被使用
        return superManager.removeById(id);
    }

    @Override
    public DeviceResultVO findOneByClientId(String clientId) {
        ArgumentAssert.notBlank(clientId, "clientId Cannot be null");
        Device device = superManager.findOneByClientId(clientId);
        return BeanUtil.copyProperties(device, DeviceResultVO.class);
    }

    /**
     * 根据设备标识查询设备信息
     *
     * @param deviceIdentification 设备标识
     * @return {@link DeviceDetailsResultVO} 设备信息
     */
    @Override
    public DeviceDetailsResultVO findOneByDeviceIdentification(String deviceIdentification) {
        ArgumentAssert.notBlank(deviceIdentification, "deviceIdentification Cannot be null");
        Device device = superManager.findOneByDeviceIdentification(deviceIdentification);
        if (device == null) {
            throw BizException.wrap("Device not exist");
        }

        // 将Device转换为DeviceDetailsResultVO
        DeviceDetailsResultVO deviceDetailsResultVO =
                BeanPlusUtil.toBeanIgnoreError(device, DeviceDetailsResultVO.class);

        // 查询产品信息，如果存在则添加到结果中
        Optional.ofNullable(device.getProductIdentification())
                .flatMap(this::queryProductInfo)
                .ifPresent(deviceDetailsResultVO::setProductResultVO);

        // 查询子设备，如果是网关则查询子设备列表，否则设置为空列表
        Optional.ofNullable(device.getNodeType())
                .filter(DeviceNodeTypeEnum.GATEWAY.getValue()::equals)
                .ifPresentOrElse(
                        type -> deviceDetailsResultVO.setSubDeviceResultVOList(
                                querySubDevices(device.getDeviceIdentification())),
                        () -> deviceDetailsResultVO.setSubDeviceResultVOList(Collections.emptyList())
                );

        // 查询设备位置信息
        Optional.ofNullable(device.getDeviceIdentification())
                .flatMap(this::queryDeviceLocation)
                .ifPresent(deviceDetailsResultVO::setDeviceLocationResultVO);

        return deviceDetailsResultVO;
    }

    @Override
    public DeviceResultVO findByDeviceIdentification(String deviceIdentification) {
        ArgumentAssert.notBlank(deviceIdentification, "deviceIdentification Cannot be null");
        Device device = superManager.findOneByDeviceIdentification(deviceIdentification);
        if (Objects.isNull(device)) {
            throw BizException.wrap("Device not exist");
        }
        return BeanPlusUtil.toBean(device, DeviceResultVO.class);
    }


    @Override
    public boolean updateDeviceConnectionStatusById(Long id, Integer connectionStatus) throws IllegalArgumentException {
        // 校验设备ID
        ArgumentAssert.notNull(id, "id cannot be null");

        // 校验连接状态值
        Optional<DeviceConnectStatusEnum> connectStatusEnumOptional = DeviceConnectStatusEnum.fromValue(connectionStatus);
        if (connectStatusEnumOptional.isEmpty()) {
            throw BizException.wrap("Invalid connection status value");
        }

        // 更新设备连接状态
        updateDeviceConnectionStatus(id, connectStatusEnumOptional.get());

        // 查询最新设备信息
        Device device = superManager.findOneById(id);
        if (Objects.isNull(device)) {
            throw BizException.wrap("The device does not exist");
        }

        // 获取设备类型
        Optional<DeviceNodeTypeEnum> deviceNodeTypeEnumOptional = DeviceNodeTypeEnum.fromValue(device.getNodeType());

        // 检查设备是否为网关
        if (deviceNodeTypeEnumOptional.isPresent() && DeviceNodeTypeEnum.GATEWAY.equals(deviceNodeTypeEnumOptional.get())) {
            // 如果设备为网关且设备状态为离线，则更新子设备状态
            if (DeviceConnectStatusEnum.OFFLINE.getValue().equals(connectStatusEnumOptional.get().getValue())) {
                updateSubDevicesConnectionStatus(Long.valueOf(device.getDeviceIdentification()), connectStatusEnumOptional.get().getValue());
            }
        }
        return true;
    }

    /**
     * 更新单个设备的连接状态
     *
     * @param deviceId          设备ID
     * @param connectStatusEnum 连接状态
     */
    private void updateDeviceConnectionStatus(Long deviceId, DeviceConnectStatusEnum connectStatusEnum) {
        UpdateWrapper<Device> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(Device::getId, deviceId)
                .set(Device::getConnectStatus, connectStatusEnum.getValue());

        superManager.update(updateWrapper);
    }

    /**
     * 更新子设备的连接状态
     *
     * @param gatewayId     网关ID
     * @param connectStatus 连接状态
     */
    private void updateSubDevicesConnectionStatus(Long gatewayId, Integer connectStatus) {
        UpdateWrapper<Device> updateSubDeviceWrapper = new UpdateWrapper<>();
        updateSubDeviceWrapper.lambda()
                .eq(Device::getGatewayId, gatewayId)
                .eq(Device::getDeviceStatus, DeviceStatusEnum.ACTIVATED.getValue())
                .set(Device::getConnectStatus, connectStatus);

        superManager.update(updateSubDeviceWrapper);
    }


    /**
     * 查询设备信息VO列表
     *
     * @param query 查询参数
     * @return {@link List<DeviceResultVO>} 设备信息VO列表
     */
    @Override
    public List<DeviceResultVO> getDeviceResultVOList(DevicePageQuery query) {
        List<Device> deviceList = superManager.getDevicList(query);
        return BeanPlusUtil.toBeanList(deviceList, DeviceResultVO.class);
    }

    /**
     * 查询设备信息VO列表
     *
     * @param query 查询参数
     * @return {@link List <deviceDetailsResultVO>} 设备信息VO列表
     */
    @Override
    public List<DeviceDetailsResultVO> getDeviceDetailsResultVOList(DevicePageQuery query) {
        // 获取设备列表
        List<Device> deviceList = superManager.getDevicList(query);

        List<DeviceDetailsResultVO> deviceResultVOS = Optional.ofNullable(deviceList)
                .filter(CollUtil::isNotEmpty)
                .map(list -> BeanPlusUtil.toBeanList(list, DeviceDetailsResultVO.class))
                .orElseGet(Collections::emptyList);

        if (deviceResultVOS.isEmpty()) {
            return Collections.emptyList();
        }

        // 提取设备标识符列表（过滤 null 值）
        List<String> deviceIdentificationList = deviceResultVOS.stream()
                .map(DeviceDetailsResultVO::getDeviceIdentification)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 根据设备标识符列表查询设备位置信息
        Map<String, DeviceLocationResultVO> deviceLocationResultVOMap = queryDeviceLocationForDevices(deviceIdentificationList);

        // 将设备位置信息封装到设备信息中
        deviceResultVOS.forEach(deviceResultVO ->
                Optional.ofNullable(deviceResultVO.getDeviceIdentification())
                        .map(deviceLocationResultVOMap::get)
                        .ifPresent(deviceResultVO::setDeviceLocationResultVO)
        );

        return deviceResultVOS;
    }


    @Override
    public DeviceOverviewResultVO getDeviceOverview() {
        PageParams<DevicePageQuery> params = new PageParams<>();
        params.setModel(new DevicePageQuery());
        return BeanPlusUtil.toBeanIgnoreError(superManager.selectDeviceOverview(params), DeviceOverviewResultVO.class);
    }

    @Override
    public DeviceVersionResultVO getDeviceVersionByProduct(String productIdentification) {
        if (StrUtil.isBlank(productIdentification)) {
            return DeviceVersionResultVO.builder()
                    .swVersionList(Collections.emptyList())
                    .fwVersionList(Collections.emptyList())
                    .build();
        }
        return superManager.selectDeviceVersionsByProduct(productIdentification)
                .map(deviceVersionDTO -> {
                    List<String> swVersionList = StrUtil.isNotBlank(deviceVersionDTO.getSwVersions())
                            ? Arrays.asList(deviceVersionDTO.getSwVersions().split(StrUtil.COMMA))
                            : Collections.emptyList();
                    List<String> fwVersionList = StrUtil.isNotBlank(deviceVersionDTO.getFwVersions())
                            ? Arrays.asList(deviceVersionDTO.getFwVersions().split(StrUtil.COMMA))
                            : Collections.emptyList();
                    return DeviceVersionResultVO.builder()
                            .swVersionList(swVersionList)
                            .fwVersionList(fwVersionList)
                            .build();
                })
                .orElse(DeviceVersionResultVO.builder()
                        .swVersionList(Collections.emptyList())
                        .fwVersionList(Collections.emptyList())
                        .build());
    }


    /**
     * MQTT协议下添加子设备
     *
     * @param topoAddSubDeviceParam 子设备参数
     * @return {@link TopoAddDeviceResultVO} 添加结果
     */
    @Override
    public TopoAddDeviceResultVO saveSubDeviceByMqtt(TopoAddSubDeviceParam topoAddSubDeviceParam) {
        return saveSubDevice(topoAddSubDeviceParam);
    }

    /**
     * 北向API添加子设备
     *
     * @param topoAddSubDeviceParam 子设备参数
     * @return {@link TopoAddDeviceResultVO} 添加结果
     */
    @Override
    public TopoAddDeviceResultVO saveSubDeviceByNorthbound(TopoAddSubDeviceParam topoAddSubDeviceParam) {
        return saveSubDevice(topoAddSubDeviceParam);
    }

    /**
     * MQTT协议下更新子设备连接状态
     *
     * @param topoUpdateSubDeviceStatusParam 更新参数
     * @return {@link TopoDeviceOperationResultVO} 更新结果
     */
    @Override
    public TopoDeviceOperationResultVO updateSubDeviceConnectStatusByMqtt(TopoUpdateSubDeviceStatusParam topoUpdateSubDeviceStatusParam) {
        return updateSubDeviceConnectStatus(topoUpdateSubDeviceStatusParam);
    }

    /**
     * 北向API更新子设备连接状态
     *
     * @param topoUpdateSubDeviceStatusParam 更新参数
     * @return {@link TopoDeviceOperationResultVO} 更新结果
     */
    @Override
    public TopoDeviceOperationResultVO updateSubDeviceConnectStatusByNorthbound(TopoUpdateSubDeviceStatusParam topoUpdateSubDeviceStatusParam) {
        return updateSubDeviceConnectStatus(topoUpdateSubDeviceStatusParam);
    }

    /**
     * MQTT协议下删除子设备
     *
     * @param topoDeleteSubDeviceParam 删除参数
     * @return {@link TopoDeviceOperationResultVO} 删除结果
     */
    @Override
    public TopoDeviceOperationResultVO deleteSubDeviceByMqtt(TopoDeleteSubDeviceParam topoDeleteSubDeviceParam) {
        return deleteSubDevice(topoDeleteSubDeviceParam);
    }

    /**
     * 北向API删除子设备
     *
     * @param topoDeleteSubDeviceParam 删除参数
     * @return {@link TopoDeviceOperationResultVO} 删除结果
     */
    @Override
    public TopoDeviceOperationResultVO deleteSubDeviceByNorthbound(TopoDeleteSubDeviceParam topoDeleteSubDeviceParam) {
        return deleteSubDevice(topoDeleteSubDeviceParam);
    }

    /**
     * MQTT协议下上报设备数据
     *
     * @param topoDeviceDataReportParam 上报参数
     * @return {@link TopoDeviceOperationResultVO} 上报结果
     */
    @Override
    public TopoDeviceOperationResultVO deviceDataReportByMqtt(TopoDeviceDataReportParam topoDeviceDataReportParam) {
        return deviceDataReport(topoDeviceDataReportParam);
    }

    /**
     * 北向API上报设备数据
     *
     * @param topoDeviceDataReportParam 上报参数
     * @return {@link TopoDeviceOperationResultVO} 上报结果
     */
    @Override
    public TopoDeviceOperationResultVO deviceDataReportByNorthbound(TopoDeviceDataReportParam topoDeviceDataReportParam) {
        return deviceDataReport(topoDeviceDataReportParam);
    }

    /**
     * 根据设备ID查询设备详情
     *
     * @param id 设备ID
     * @return {@link DeviceDetailsResultVO} 设备详情
     */
    @Override
    public DeviceDetailsResultVO getDeviceDetails(Long id) {
        if (id == null) {
            throw BizException.wrap("Device ID cannot be null");
        }

        Device device = superManager.findOneById(id);
        if (Objects.isNull(device)) {
            throw BizException.wrap("The device does not exist");
        }

        // 将Device转换为DeviceDetailsResultVO
        DeviceDetailsResultVO deviceDetailsResultVO =
                BeanPlusUtil.toBeanIgnoreError(device, DeviceDetailsResultVO.class);

        // 查询产品信息，如果存在则添加到结果中
        Optional.ofNullable(device.getProductIdentification())
                .flatMap(this::queryProductInfo)
                .ifPresent(deviceDetailsResultVO::setProductResultVO);

        // 查询子设备，如果是网关则查询子设备列表，否则设置为空列表
        Optional.ofNullable(device.getNodeType())
                .filter(DeviceNodeTypeEnum.GATEWAY.getValue()::equals)
                .ifPresentOrElse(
                        type -> deviceDetailsResultVO.setSubDeviceResultVOList(
                                querySubDevices(device.getDeviceIdentification())),
                        () -> deviceDetailsResultVO.setSubDeviceResultVOList(Collections.emptyList())
                );

        // 查询设备位置信息
        Optional.ofNullable(device.getDeviceIdentification())
                .flatMap(this::queryDeviceLocation)
                .ifPresent(deviceDetailsResultVO::setDeviceLocationResultVO);

        return deviceDetailsResultVO;
    }

    /**
     * 获取设备详情分页信息
     *
     * @param params 查询参数
     * @return {@link IPage <Device>} 设备详情分页信息
     */
    @Override
    public IPage<DeviceDetailsResultVO> getDeviceDetailsPage(PageParams<DeviceDetailsPageQuery> params) {
        // 获取设备分页信息
        IPage<Device> deviceIPage = superManager.getDeviceDetailsPage(params);

        // 将 Device 转换为 DeviceDetailsResultVO 列表（防空处理）
        List<DeviceDetailsResultVO> deviceDetailsResultVOS = Optional.ofNullable(deviceIPage.getRecords())
                .filter(CollUtil::isNotEmpty)
                .map(records -> BeanPlusUtil.toBeanList(records, DeviceDetailsResultVO.class))
                .orElseGet(Collections::emptyList);

        if (deviceDetailsResultVOS.isEmpty()) {
            return new Page<>(deviceIPage.getCurrent(), deviceIPage.getSize(), 0);
        }

        // 提取设备标识符列表（过滤 null 值）
        List<String> deviceIdentificationList = deviceDetailsResultVOS.stream()
                .map(DeviceDetailsResultVO::getDeviceIdentification)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 提取产品标识列表（过滤 null 值）
        List<String> productIdentificationList = deviceDetailsResultVOS.stream()
                .map(DeviceDetailsResultVO::getProductIdentification)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 查询产品信息和设备位置信息
        Map<String, ProductResultVO> productResultVOMap = queryProductInfoForDevices(productIdentificationList);
        Map<String, DeviceLocationResultVO> deviceLocationResultVOMap = queryDeviceLocationForDevices(deviceIdentificationList);

        // 批量查询所有网关设备的子设备（1次查询代替N次，解决N+1问题）
        Map<String, List<DeviceResultVO>> subDeviceMap = querySubDevicesForGateways(deviceDetailsResultVOS);

        // 处理每个设备的子设备、产品信息和位置信息（不使用 parallelStream，避免 @DS 数据源上下文丢失）
        deviceDetailsResultVOS.forEach(device ->
                processDeviceSubDetails(device, productResultVOMap, deviceLocationResultVOMap, subDeviceMap)
        );

        // 复用分页信息，避免重复 bean 转换
        Page<DeviceDetailsResultVO> resultPage = new Page<>(deviceIPage.getCurrent(), deviceIPage.getSize(), deviceIPage.getTotal());
        resultPage.setRecords(deviceDetailsResultVOS);
        return resultPage;
    }

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
    @Override
    public boolean isProductInUseByDevices(String productIdentification) {
        if (StrUtil.isBlank(productIdentification)) {
            throw BizException.wrap("Product identification cannot be null or empty.");
        }

        PageParams<DeviceDetailsPageQuery> params = new PageParams<>();
        params.setModel(new DeviceDetailsPageQuery().setProductIdentification(productIdentification));
        IPage<Device> deviceIPage = superManager.getDeviceDetailsPage(params);
        return deviceIPage != null && !deviceIPage.getRecords().isEmpty();
    }

    /**
     * Queries device information using the MQTT protocol.
     *
     * @param topoQueryDeviceParam The device query parameters.
     * @return {@link TopoQueryDeviceResultVO} The result of the device query.
     */
    @Override
    public TopoQueryDeviceResultVO queryDeviceByMqtt(TopoQueryDeviceParam topoQueryDeviceParam) {
        return queryDeviceInfo(topoQueryDeviceParam);
    }

    /**
     * 北向API查询设备信息
     *
     * @param topoQueryDeviceParam The device query parameters.
     * @return {@link TopoQueryDeviceResultVO} The result of the device query.
     */
    @Override
    public TopoQueryDeviceResultVO queryDeviceByNorthbound(TopoQueryDeviceParam topoQueryDeviceParam) {
        return queryDeviceInfo(topoQueryDeviceParam);
    }


    /**
     * Queries device information based on provided parameters.
     *
     * @param topoQueryDeviceParam Parameters for querying device information.
     * @return {@link TopoQueryDeviceResultVO} containing the results of the device query.
     */
    private TopoQueryDeviceResultVO queryDeviceInfo(TopoQueryDeviceParam topoQueryDeviceParam) {
        TopoQueryDeviceResultVO topoQueryDeviceResultVO = new TopoQueryDeviceResultVO();

        List<String> deviceIds = Optional.ofNullable(topoQueryDeviceParam)
                .map(TopoQueryDeviceParam::getDeviceIds)
                .orElseGet(Collections::emptyList);

        // 批量查询所有设备，避免 N+1
        List<String> distinctDeviceIds = deviceIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        Map<String, Device> deviceMap = Optional.of(distinctDeviceIds)
                .filter(CollUtil::isNotEmpty)
                .map(ids -> {
                    DevicePageQuery query = new DevicePageQuery();
                    query.setDeviceIdentificationList(ids);
                    List<Device> devices = superManager.getDevicList(query);
                    return Optional.ofNullable(devices).orElseGet(Collections::emptyList);
                })
                .map(devices -> devices.stream()
                        .filter(d -> d != null && d.getDeviceIdentification() != null)
                        .collect(Collectors.toMap(Device::getDeviceIdentification, Function.identity(), (a, b) -> a)))
                .orElseGet(Collections::emptyMap);

        List<TopoQueryDeviceResultVO.DataItem> deviceInfoList = distinctDeviceIds.stream()
                .map(deviceIdentification -> {
                    TopoQueryDeviceResultVO.DataItem dataItem = new TopoQueryDeviceResultVO.DataItem();
                    try {
                        dataItem.setDeviceId(deviceIdentification);
                        Optional<Device> optionalDevice = Optional.ofNullable(deviceMap.get(deviceIdentification));
                        TopoQueryDeviceResultVO.DataItem.DeviceInfo deviceInfo = optionalDevice
                                .map(device -> BeanUtil.toBean(device, TopoQueryDeviceResultVO.DataItem.DeviceInfo.class))
                                .orElse(new TopoQueryDeviceResultVO.DataItem.DeviceInfo());

                        dataItem.setDeviceInfo(deviceInfo)
                                .setStatusCode(optionalDevice.isPresent() ? MqttProtocolTopoStatusEnum.SUCCESS.getValue() : MqttProtocolTopoStatusEnum.FAILURE.getValue())
                                .setStatusDesc(optionalDevice.isPresent() ? MqttProtocolTopoStatusEnum.SUCCESS.getDesc() : "Device not found");
                    } catch (Exception e) {
                        dataItem.setStatusCode(MqttProtocolTopoStatusEnum.FAILURE.getValue())
                                .setStatusDesc("Error querying device: " + e.getMessage());
                    }
                    return dataItem;
                })
                .collect(Collectors.toList());

        topoQueryDeviceResultVO.setData(deviceInfoList)
                .setStatusCode(MqttProtocolTopoStatusEnum.SUCCESS.getValue())
                .setStatusDesc("Query completed");
        return topoQueryDeviceResultVO;
    }


    private void processDeviceSubDetails(DeviceDetailsResultVO device,
                                         Map<String, ProductResultVO> productResultVOMap,
                                         Map<String, DeviceLocationResultVO> deviceLocationResultVOMap,
                                         Map<String, List<DeviceResultVO>> subDeviceMap) {
        Optional.ofNullable(device)
                .ifPresent(d -> {
                    Optional.ofNullable(d.getProductIdentification())
                            .map(productResultVOMap::get)
                            .ifPresent(d::setProductResultVO);

                    Optional.ofNullable(d.getDeviceIdentification())
                            .map(deviceLocationResultVOMap::get)
                            .ifPresent(d::setDeviceLocationResultVO);

                    Optional.ofNullable(d.getNodeType())
                            .filter(DeviceNodeTypeEnum.GATEWAY.getValue()::equals)
                            .ifPresent(type ->
                                    d.setSubDeviceResultVOList(
                                            Optional.ofNullable(d.getDeviceIdentification())
                                                    .map(subDeviceMap::get)
                                                    .orElseGet(Collections::emptyList)
                                    )
                            );
                });
    }

    private Map<String, ProductResultVO> queryProductInfoForDevices(List<String> productIdentificationList) {
        return Optional.ofNullable(productIdentificationList)
                .filter(CollUtil::isNotEmpty)
                .map(productIdentification -> {
                    ProductPageQuery query = new ProductPageQuery().setProductIdentificationList(productIdentification);
                    return productService.getProductResultVOList(query);
                })
                .map(list -> list.stream()
                        .filter(p -> p != null && p.getProductIdentification() != null)
                        .collect(Collectors.toMap(
                                ProductResultVO::getProductIdentification,
                                Function.identity(),
                                (a, b) -> {
                                    if (a.getCreatedTime() == null) return b;
                                    if (b.getCreatedTime() == null) return a;
                                    return a.getCreatedTime().isAfter(b.getCreatedTime()) ? a : b;
                                }
                        ))
                )
                .orElseGet(Collections::emptyMap);
    }

    private Map<String, DeviceLocationResultVO> queryDeviceLocationForDevices(List<String> deviceIdentificationList) {
        return Optional.ofNullable(deviceIdentificationList)
                .filter(CollUtil::isNotEmpty)
                .map(deviceIdentification -> deviceLocationService.getDeviceLocationResultVOList(
                        new DeviceLocationPageQuery().setDeviceIdentificationList(deviceIdentification)
                ))
                .map(list -> list.stream()
                        .filter(d -> d != null && d.getDeviceIdentification() != null)
                        .collect(Collectors.toMap(
                                DeviceLocationResultVO::getDeviceIdentification,
                                Function.identity(),
                                (a, b) -> {
                                    if (a.getCreatedTime() == null) return b;
                                    if (b.getCreatedTime() == null) return a;
                                    return a.getCreatedTime().isAfter(b.getCreatedTime()) ? a : b;
                                }
                        ))
                )
                .orElseGet(Collections::emptyMap);
    }


    /**
     * 批量查询所有网关设备的子设备（解决N+1查询问题）
     */
    private Map<String, List<DeviceResultVO>> querySubDevicesForGateways(List<DeviceDetailsResultVO> deviceList) {
        return Optional.ofNullable(deviceList)
                .map(list -> list.stream()
                        .filter(d -> d != null && DeviceNodeTypeEnum.GATEWAY.getValue().equals(d.getNodeType()))
                        .map(DeviceDetailsResultVO::getDeviceIdentification)
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList()))
                .filter(CollUtil::isNotEmpty)
                .map(gatewayIds -> {
                    DevicePageQuery query = new DevicePageQuery();
                    query.setGatewayIdList(gatewayIds);
                    query.setNodeType(DeviceNodeTypeEnum.SUBDEVICE.getValue());
                    return getDeviceResultVOList(query);
                })
                .map(subDevices -> subDevices.stream()
                        .filter(d -> d != null && d.getGatewayId() != null)
                        .collect(Collectors.groupingBy(DeviceResultVO::getGatewayId)))
                .orElseGet(Collections::emptyMap);
    }

    private List<DeviceResultVO> querySubDevices(String gatewayId) {
        DevicePageQuery devicePageQuery = new DevicePageQuery();
        devicePageQuery.setGatewayId(gatewayId);
        devicePageQuery.setNodeType(DeviceNodeTypeEnum.SUBDEVICE.getValue());
        return getDeviceResultVOList(devicePageQuery);
    }

    private Optional<DeviceLocationResultVO> queryDeviceLocation(String deviceIdentification) {
        DeviceLocationPageQuery deviceLocationPageQuery = new DeviceLocationPageQuery();
        deviceLocationPageQuery.setDeviceIdentification(deviceIdentification);
        List<DeviceLocationResultVO> deviceLocationResultVOList =
                deviceLocationService.getDeviceLocationResultVOList(deviceLocationPageQuery);
        return deviceLocationResultVOList.isEmpty() ? Optional.empty() : Optional.of(deviceLocationResultVOList.get(0));
    }


    private Optional<ProductResultVO> queryProductInfo(String productIdentification) {
        return Optional.ofNullable(productService.findOneByProductIdentification(productIdentification));
    }


    private TopoDeviceOperationResultVO deviceDataReport(TopoDeviceDataReportParam topoDeviceDataReportParam) {
        // 您的处理逻辑

        return TopoDeviceOperationResultVO.builder()
                .statusCode(MqttProtocolTopoStatusEnum.SUCCESS.getValue())
                .statusDesc(MqttProtocolTopoStatusEnum.SUCCESS.getDesc()).build();
    }

    private TopoDeviceOperationResultVO deleteSubDevice(TopoDeleteSubDeviceParam topoDeleteSubDeviceParam) {
        List<String> deviceIds = Optional.ofNullable(topoDeleteSubDeviceParam)
                .map(TopoDeleteSubDeviceParam::getDeviceIds)
                .orElseGet(Collections::emptyList);

        // 批量查询所有设备，避免 N+1
        List<String> distinctDeviceIds = deviceIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        Map<String, Device> deviceMap = Optional.of(distinctDeviceIds)
                .filter(CollUtil::isNotEmpty)
                .map(ids -> {
                    DevicePageQuery query = new DevicePageQuery();
                    query.setDeviceIdentificationList(ids);
                    List<Device> devices = superManager.getDevicList(query);
                    return Optional.ofNullable(devices).orElseGet(Collections::emptyList);
                })
                .map(devices -> devices.stream()
                        .filter(d -> d != null && d.getDeviceIdentification() != null)
                        .collect(Collectors.toMap(Device::getDeviceIdentification, Function.identity(), (a, b) -> a)))
                .orElseGet(Collections::emptyMap);

        // 逐个删除并记录结果
        List<TopoDeviceOperationResultVO.OperationRsp> operationResultList = distinctDeviceIds.stream()
                .map(deviceId -> {
                    TopoDeviceOperationResultVO.OperationRsp operationRsp = new TopoDeviceOperationResultVO.OperationRsp()
                            .setDeviceId(deviceId);

                    Optional.ofNullable(deviceMap.get(deviceId))
                            .ifPresentOrElse(
                                    subDevice -> {
                                        boolean deleteFlag = superManager.removeById(subDevice);
                                        MqttProtocolTopoStatusEnum statusEnum = deleteFlag
                                                ? MqttProtocolTopoStatusEnum.SUCCESS
                                                : MqttProtocolTopoStatusEnum.FAILURE;
                                        operationRsp.setStatusCode(statusEnum.getValue())
                                                .setStatusDesc(statusEnum.getDesc());
                                    },
                                    () -> operationRsp.setStatusCode(MqttProtocolTopoStatusEnum.FAILURE.getValue())
                                            .setStatusDesc(MqttProtocolTopoStatusEnum.FAILURE.getDesc())
                            );

                    return operationRsp;
                })
                .collect(Collectors.toList());

        return TopoDeviceOperationResultVO.builder()
                .statusCode(MqttProtocolTopoStatusEnum.SUCCESS.getValue())
                .statusDesc(MqttProtocolTopoStatusEnum.SUCCESS.getDesc())
                .data(operationResultList)
                .build();
    }

    /**
     * Updates the connection status of sub-devices and logs their actions.
     *
     * @param topoUpdateSubDeviceStatusParam the parameters containing device statuses to be updated
     * @return an object containing the operation results
     */
    private TopoDeviceOperationResultVO updateSubDeviceConnectStatus(TopoUpdateSubDeviceStatusParam topoUpdateSubDeviceStatusParam) {
        List<TopoDeviceOperationResultVO.OperationRsp> operationRsp = topoUpdateSubDeviceStatusParam.getDeviceStatuses().stream()
                .map(this::processSubDeviceStatus)
                .collect(Collectors.toList());

        return TopoDeviceOperationResultVO.builder()
                .statusCode(MqttProtocolTopoStatusEnum.SUCCESS.getValue())
                .statusDesc(MqttProtocolTopoStatusEnum.SUCCESS.getDesc())
                .data(operationRsp)
                .build();
    }

    /**
     * Processes the status of a single sub-device, updates it, and logs the action.
     *
     * @param subDeviceStatus the status of the sub-device to be processed
     * @return an operation response object for the sub-device
     */
    private TopoDeviceOperationResultVO.OperationRsp processSubDeviceStatus(TopoUpdateSubDeviceStatusParam.DeviceStatus subDeviceStatus) {
        Device subDevice = superManager.findOneByDeviceIdentification(subDeviceStatus.getDeviceId());
        TopoDeviceOperationResultVO.OperationRsp dataItem = new TopoDeviceOperationResultVO.OperationRsp()
                .setDeviceId(subDeviceStatus.getDeviceId());

        if (subDevice != null) {
            // 更新设备连接状态
            UpdateWrapper<Device> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda()
                    .eq(Device::getId, subDevice.getId())
                    .set(Device::getConnectStatus, subDeviceStatus.getStatus().getValue())
                    .set(Device::getLastHeartbeatTime, LocalDateTime.now());
            boolean updateFlag = superManager.update(updateWrapper);
            recordDeviceAction(subDevice, subDeviceStatus.getStatus());

            MqttProtocolTopoStatusEnum updateStatusEnum = updateFlag ? MqttProtocolTopoStatusEnum.SUCCESS : MqttProtocolTopoStatusEnum.FAILURE;
            dataItem.setStatusCode(updateStatusEnum.getValue())
                    .setStatusDesc(updateStatusEnum.getDesc());
        } else {
            dataItem.setStatusCode(MqttProtocolTopoStatusEnum.FAILURE.getValue())
                    .setStatusDesc(MqttProtocolTopoStatusEnum.FAILURE.getDesc());
        }

        return dataItem;
    }

    /**
     * Records an action taken on a device and saves it to the database.
     *
     * @param device        the device on which the action was taken
     * @param connectStatus the new connection status of the device
     */
    private void recordDeviceAction(Device device, DeviceConnectStatusEnum connectStatus) {
        // 构建设备动作描述和类型
        String describable = getDescriptionForStatus(connectStatus);
        DeviceActionTypeEnum actionType = getActionTypeForStatus(connectStatus);

        // 构建并保存设备动作记录
        DeviceActionSaveVO deviceActionSaveVO = new DeviceActionSaveVO();
        deviceActionSaveVO.setDeviceIdentification(device.getDeviceIdentification());
        deviceActionSaveVO.setActionType(actionType.getAction());
        deviceActionSaveVO.setMessage(actionType.getDescription());
        deviceActionSaveVO.setStatus(DeviceActionStatusEnum.SUCCESSFUL.getValue());
        deviceActionSaveVO.setRemark(describable);

        try {
            DeviceAction deviceAction = deviceActionService.saveDeviceAction(deviceActionSaveVO);
            log.info("Device action saved: {}", deviceAction);
        } catch (Exception e) {
            log.error("Failed to save device action for device ID: {}", device.getDeviceIdentification(), e);
        }
    }

    /**
     * Gets a descriptive text based on the device connection status.
     *
     * @param status the device connection status
     * @return a string description for the status
     */
    private String getDescriptionForStatus(DeviceConnectStatusEnum status) {
        String desc = Optional.ofNullable(status)
                .map(DeviceConnectStatusEnum::getDesc)
                .orElse("Unknown Status");

        return "The device connection status is updated to " + desc;
    }

    /**
     * Determines the action type based on the device connection status.
     *
     * @param status the device connection status
     * @return the corresponding action type
     */
    private DeviceActionTypeEnum getActionTypeForStatus(DeviceConnectStatusEnum status) {
        if (DeviceConnectStatusEnum.OFFLINE.equals(status)) {
            return DeviceActionTypeEnum.CLOSE;
        } else if (DeviceConnectStatusEnum.ONLINE.equals(status)) {
            return DeviceActionTypeEnum.CONNECT;
        } else {
            // Handle unexpected status here
            log.warn("Unexpected status: {}", status);
            return DeviceActionTypeEnum.UNKNOWN;
        }
    }


    /**
     * 添加网关子设备
     *
     * @param topoAddSubDeviceParam TopoAddDeviceParam，添加设备的参数信息
     * @return {@link TopoAddDeviceResultVO} 添加设备的结果信息
     */
    private TopoAddDeviceResultVO saveSubDevice(TopoAddSubDeviceParam topoAddSubDeviceParam) {
        // 根据网关ID查找设备
        Device gatewayDevice = superManager.findOneByDeviceIdentification(topoAddSubDeviceParam.getGatewayIdentification());

        // 假设 gatewayDevice.getType() 方法返回设备类型，且 DeviceType.GATEWAY 代表网关设备类型
        MqttProtocolTopoStatusEnum statusEnum = (gatewayDevice != null && DeviceNodeTypeEnum.GATEWAY.getValue().equals(gatewayDevice.getNodeType()))
                ? MqttProtocolTopoStatusEnum.SUCCESS
                : MqttProtocolTopoStatusEnum.FAILURE;

        // 创建返回结果实例并设置状态码和状态描述
        TopoAddDeviceResultVO mqttTopoAddDeviceResultVO = TopoAddDeviceResultVO.builder()
                .statusCode(statusEnum.getValue())
                .statusDesc(statusEnum.getDesc())
                .build();

        // 创建一个设备列表用于存储处理结果
        List<TopoAddDeviceResultVO.DataItem> deviceList = new ArrayList<>();

        // 检查设备信息列表是否为空
        List<TopoAddSubDeviceParam.DeviceInfos> deviceInfos = topoAddSubDeviceParam.getDeviceInfos();
        if (deviceInfos != null) {
            // 遍历添加设备的参数信息列表
            for (TopoAddSubDeviceParam.DeviceInfos item : deviceInfos) {
                try {
                    // 创建数据项实例并验证设备参数
                    TopoAddDeviceResultVO.DataItem dataItem = new TopoAddDeviceResultVO.DataItem();
                    checkedTopoAddDeviceParam(item, dataItem);
                    // 将参数对象转换为设备信息对象并设置到数据项中
                    dataItem.setDeviceInfo(BeanUtil.toBean(item, TopoAddDeviceResultVO.DataItem.DeviceInfo.class, CopyOptions.create().ignoreError()));

                    // 如果设备参数验证不通过，添加到设备列表并继续下一次循环
                    if (!MqttProtocolTopoStatusEnum.SUCCESS.getValue().equals(dataItem.getStatusCode())) {
                        deviceList.add(dataItem);
                        continue;
                    }

                    // 转换并保存子设备信息
                    Device subDeviceDO = conversionDeviceBySaveSubDevice(gatewayDevice, item);
                    boolean saveFlag = superManager.save(subDeviceDO);

                    // 存储子设备经纬度信息
                    DeviceLocationPageQuery deviceLocationPageQuery = new DeviceLocationPageQuery();
                    deviceLocationPageQuery.setDeviceIdentification(gatewayDevice.getDeviceIdentification());

                    List<DeviceLocationResultVO> deviceLocationResultVOList = deviceLocationService.getDeviceLocationResultVOList(deviceLocationPageQuery);

                    Optional.ofNullable(deviceLocationResultVOList)
                            .filter(list -> !list.isEmpty())
                            .map(list -> list.get(0))
                            .map(deviceLocationResultVO -> BeanPlusUtil.toBeanIgnoreError(deviceLocationResultVO, DeviceLocationSaveVO.class))
                            .ifPresent(deviceLocationSaveVO -> {
                                deviceLocationSaveVO.setDeviceIdentification(subDeviceDO.getDeviceIdentification());
                                deviceLocationService.saveDeviceLocation(deviceLocationSaveVO);
                            });

                    // 设置平台生成的设备标识
                    dataItem.getDeviceInfo().setDeviceId(subDeviceDO.getDeviceIdentification());

                    // 根据保存结果设置状态码和状态描述
                    MqttProtocolTopoStatusEnum saveStatusEnum = saveFlag ? MqttProtocolTopoStatusEnum.SUCCESS : MqttProtocolTopoStatusEnum.FAILURE;
                    dataItem.setStatusCode(saveStatusEnum.getValue())
                            .setStatusDesc(saveStatusEnum.getDesc());

                    // 添加数据项到设备列表
                    deviceList.add(dataItem);

                    if (saveFlag) {
                        // 发布设备信息更新事件
                        deviceEventPublisher.publishDeviceInfoUpdatedEvent(DeviceInfoUpdatedEventSource.builder()
                                .deviceIdentificationList(Collections.singletonList(subDeviceDO.getDeviceIdentification()))
                                .build());
                    }
                } catch (Exception e) {
                    // 处理异常情况，将异常信息设置到数据项中
                    TopoAddDeviceResultVO.DataItem dataItem = new TopoAddDeviceResultVO.DataItem();
                    dataItem.setStatusCode(MqttProtocolTopoStatusEnum.FAILURE.getValue())
                            .setStatusDesc(e.getMessage());
                    deviceList.add(dataItem);
                }
            }
        }

        // 将设备列表设置到返回结果实例中
        mqttTopoAddDeviceResultVO.setData(deviceList);
        return mqttTopoAddDeviceResultVO;
    }


    /**
     * 验证Topo添加设备参数，并设置对应的状态码和状态描述。
     *
     * @param item     TopoAddDeviceParam.DeviceInfos，添加设备的参数信息
     * @param dataItem MqttTopoAddDeviceResultVO.DataItem，用于存储设备添加结果的数据项
     */
    private void checkedTopoAddDeviceParam(TopoAddSubDeviceParam.DeviceInfos item,
                                           TopoAddDeviceResultVO.DataItem dataItem) {
        // 根据设备标识查找子设备
        Device subDevice = superManager.findOneByDeviceIdentification(item.getNodeId());
        // 用于拼接错误消息的StringBuilder
        StringBuilder errorMessage = new StringBuilder();

        // 检查各参数是否为空，并将错误消息追加到StringBuilder中
        appendErrorMessageIfEmpty(errorMessage, item.getName(), "name is null; ");
        appendErrorMessageIfEmpty(errorMessage, item.getNodeId(), "nodeId is null; ");
        appendErrorMessageIfEmpty(errorMessage, item.getManufacturerId(), "manufacturerId is null; ");
        appendErrorMessageIfEmpty(errorMessage, item.getModel(), "model is null; ");

        // 检查设备节点ID是否已经存在
        if (subDevice != null) {
            errorMessage.append("nodeId is exist; ");
        }

        // 根据错误消息长度判断是否有错误，并设置相应的状态码和状态描述
        if (!errorMessage.isEmpty()) {
            dataItem.setStatusCode(MqttProtocolTopoStatusEnum.FAILURE.getValue())
                    .setStatusDesc(errorMessage.toString());
        } else {
            dataItem.setStatusCode(MqttProtocolTopoStatusEnum.SUCCESS.getValue())
                    .setStatusDesc(MqttProtocolTopoStatusEnum.SUCCESS.getDesc());
        }
    }

    /**
     * 检查参数值是否为空，如果为空，将错误消息追加到StringBuilder中。
     *
     * @param errorMessage StringBuilder，用于拼接错误消息
     * @param value        CharSequence，待检查的参数值
     * @param message      String，错误消息
     */
    private void appendErrorMessageIfEmpty(StringBuilder errorMessage, CharSequence value, String message) {
        if (CharSequenceUtil.isEmpty(value)) {
            errorMessage.append(message);
        }
    }


    /**
     * 添加网关子设备转换DO Device
     *
     * @param gatewayDevice 网关设备信息
     * @param item          子设备信息
     * @return {@link Device} 设备信息
     */
    private Device conversionDeviceBySaveSubDevice(Device gatewayDevice, TopoAddSubDeviceParam.DeviceInfos item) {
        Device device = new Device();
        BeanUtil.copyProperties(gatewayDevice, device, CopyOptions.create().setIgnoreProperties("id"));
        device.setDeviceName(item.getName());
        device.setClientId(TenantUtil.buildOptionalItem(SnowflakeIdUtil.nextId(), TenantUtil.extractTenantId(device.getClientId())));
        device.setDeviceIdentification(item.getNodeId());
        device.setNodeType(DeviceNodeTypeEnum.SUBDEVICE.getValue());
        device.setGatewayId(gatewayDevice.getDeviceIdentification());
        device.setConnectStatus(DeviceConnectStatusEnum.UNCONNECTED.getValue());
        device.setDeviceStatus(DeviceStatusEnum.ACTIVATED.getValue());
        device.setPassword(gatewayDevice.getPassword());
        // TODO 产品关联处理,支持多产品关联默认不关联网关设备产品
        return device;
    }


    private Builder<Device> builderDeviceUpdateVO(DeviceUpdateVO updateVO) {

        return Builder.of(Device::new)
                .with(Device::setUserName, updateVO.getUserName())
                .with(Device::setPassword, updateVO.getPassword())
                .with(Device::setCertSerialNumber, updateVO.getCertSerialNumber())
                .with(Device::setAppId, updateVO.getAppId())
                .with(Device::setAuthMode, updateVO.getAuthMode())
                .with(Device::setEncryptKey, updateVO.getEncryptKey())
                .with(Device::setEncryptVector, updateVO.getEncryptVector())
                .with(Device::setEncryptMethod, updateVO.getEncryptMethod())
                .with(Device::setSignKey, updateVO.getSignKey())
                .with(Device::setDeviceName, updateVO.getDeviceName())
                .with(Device::setConnector, updateVO.getConnector())
                .with(Device::setDescription, updateVO.getDescription())
                .with(Device::setDeviceStatus, updateVO.getDeviceStatus())
                .with(Device::setDeviceTags, updateVO.getDeviceTags())
                .with(Device::setSwVersion, updateVO.getSwVersion())
                .with(Device::setFwVersion, updateVO.getFwVersion())
                .with(Device::setDeviceSdkVersion, updateVO.getDeviceSdkVersion())
                .with(Device::setGatewayId, updateVO.getGatewayId())
                .with(Device::setProductIdentification, updateVO.getProductIdentification())
                .with(Device::setNodeType, updateVO.getNodeType())
                .with(Device::setRemark, updateVO.getRemark())
                .with(Device::setCreatedOrgId, ContextUtil.getCurrentDeptId());
    }

    /**
     * 构建保存参数
     *
     * @param saveVO 保存参数
     * @return {@link Device} 设备实体
     */
    private Device builderDeviceSaveVO(DeviceSaveVO saveVO) {
        Device device = BeanPlusUtil.copyProperties(saveVO, Device.class);
        //设备clientId 生成规则: 唯一标识 + @ + 租户ID
        device.setClientId(TenantUtil.buildOptionalItem(SnowflakeIdUtil.nextId(), ContextUtil.getTenantId().toString()));
        //设备标识生成规则: 雪花算法生成
        device.setDeviceIdentification(String.valueOf(SnowflakeIdUtil.nextId()));
        device.setCreatedOrgId(ContextUtil.getCurrentDeptId());
        return device;
    }

    /**
     * 校验新增参数
     *
     * @param saveVO 保存参数
     */
    private void checkedDeviceSaveVO(DeviceSaveVO saveVO) {
        //设备认证模式
        ArgumentAssert.notNull(saveVO.getAuthMode(), "authMode Cannot be null");
        ArgumentAssert.notBlank(saveVO.getUserName(), "userName Cannot be null");
        ArgumentAssert.notBlank(saveVO.getPassword(), "password Cannot be null");
        if (DeviceAuthModeEnum.SSL_MODE.getValue().equals(saveVO.getAuthMode())) {
            ArgumentAssert.notBlank(saveVO.getCertSerialNumber(), "certSerialNumber Cannot be null");
        }

        //应用ID
        ArgumentAssert.notBlank(saveVO.getAppId(), "appId Cannot be null");

        //设备协议加密方式
        ArgumentAssert.notBlank(saveVO.getSignKey(), "signKey Cannot be null");
        ArgumentAssert.notNull(saveVO.getEncryptMethod(), "encryptMethod Cannot be null");
        if (DeviceEncryptMethodEnum.AES256.getValue().equals(saveVO.getEncryptMethod()) || DeviceEncryptMethodEnum.SM4.getValue().equals(saveVO.getEncryptMethod())) {
            ArgumentAssert.notBlank(saveVO.getEncryptKey(), "encryptKey Cannot be null");
            ArgumentAssert.notBlank(saveVO.getEncryptVector(), "The key vector cannot be empty.");

        }

        //设备状态
        ArgumentAssert.notNull(saveVO.getDeviceStatus(), "deviceStatus Cannot be null");
        if (!DeviceStatusEnum.ALL_STATE_COLLECTION.contains(saveVO.getDeviceStatus())) {
            throw BizException.wrap("DeviceStatusEnum is not exist");
        }

        //设备类型
        DeviceNodeTypeEnum.fromValue(saveVO.getNodeType()).orElseThrow(() -> BizException.wrap("deviceNodeType is not exist"));

        //子设备校验：如果是子设备，网关设备ID不能为空
        if (DeviceNodeTypeEnum.SUBDEVICE.getValue().equals(saveVO.getNodeType())) {
            ArgumentAssert.notBlank(saveVO.getGatewayId(), "The gateway device ID of the sub-device cannot be empty.");
        }

        //产品标识校验：校验产品是否存在
        ArgumentAssert.notBlank(saveVO.getProductIdentification(), "productIdentification Cannot be null");
        ProductResultVO productResultVO = productService.findOneByProductIdentification(saveVO.getProductIdentification());
        ArgumentAssert.notNull(productResultVO, "productIdentification is not exist");

    }

    /**
     * 校验更新参数
     *
     * @param updateVO 更新参数
     */
    private void checkedDeviceUpdateVO(DeviceUpdateVO updateVO) {

        ArgumentAssert.notNull(updateVO.getId(), "id Cannot be null");

        ArgumentAssert.notBlank(updateVO.getUserName(), "userName Cannot be null");
        ArgumentAssert.notBlank(updateVO.getPassword(), "password Cannot be null");
        if (DeviceAuthModeEnum.SSL_MODE.getValue().equals(updateVO.getAuthMode())) {
            ArgumentAssert.notBlank(updateVO.getCertSerialNumber(), "certSerialNumber Cannot be null");
        }

        //应用ID
        ArgumentAssert.notBlank(updateVO.getAppId(), "appId Cannot be null");

        //设备协议加密方式
        ArgumentAssert.notBlank(updateVO.getSignKey(), "signKey Cannot be null");
        ArgumentAssert.notNull(updateVO.getEncryptMethod(), "encryptMethod Cannot be null");
        if (DeviceEncryptMethodEnum.AES256.getValue().equals(updateVO.getEncryptMethod()) || DeviceEncryptMethodEnum.SM4.getValue().equals(updateVO.getEncryptMethod())) {
            ArgumentAssert.notBlank(updateVO.getEncryptKey(), "encryptKey Cannot be null");
            ArgumentAssert.notBlank(updateVO.getEncryptVector(), "encryptVector Cannot be null");
        }

        //设备状态
        ArgumentAssert.notNull(updateVO.getDeviceStatus(), "deviceStatus Cannot be null");
        if (!DeviceStatusEnum.ALL_STATE_COLLECTION.contains(updateVO.getDeviceStatus())) {
            throw BizException.wrap("DeviceStatusEnum is not exist");
        }

        //设备类型
        DeviceNodeTypeEnum.fromValue(updateVO.getNodeType()).orElseThrow(() -> BizException.wrap("deviceNodeType is not exist"));

        //子设备校验：如果是子设备，网关设备ID不能为空
        if (DeviceNodeTypeEnum.SUBDEVICE.getValue().equals(updateVO.getNodeType())) {
            ArgumentAssert.notBlank(updateVO.getGatewayId(), "The gateway device ID of the sub-device cannot be empty.");
        }

        //产品标识校验：校验产品是否存在
        ArgumentAssert.notBlank(updateVO.getProductIdentification(), "productIdentification Cannot be null");
        ProductResultVO productResultVO = productService.findOneByProductIdentification(updateVO.getProductIdentification());
        ArgumentAssert.notNull(productResultVO, "productIdentification is not exist");

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
        return superManager.findDeviceCacheVO(tenantId, deviceIdOrClientId);
    }

    @Override
    public Boolean reportDeviceHeartbeat(String clientIdentifier, Long heartbeatTime) {
        //根据客户端标识符查询设备缓存信息
        Device device = superManager.findOneByClientId(clientIdentifier);
        if (Objects.isNull(device)) {
            throw BizException.wrap("客户端标识:{} 设备档案信息不存在", clientIdentifier);
        }
        try {
            Device updateDO = new Device();
            updateDO.setId(device.getId());
            //更新设备心跳时间
            LocalDateTime heartbeatDateTime = (heartbeatTime != null)
                    ? DateUtil.date(heartbeatTime).toLocalDateTime()
                    : LocalDateTime.now();
            updateDO.setLastHeartbeatTime(heartbeatDateTime);
            //更新设备状态
            updateDO.setConnectStatus(DeviceConnectStatusEnum.ONLINE.getValue());
            superManager.updateById(updateDO);
            return true;
        } catch (Exception e) {
            log.error("上报设备心跳失败,clientIdentifier:{}", clientIdentifier, e);
            return false;
        }
    }

}