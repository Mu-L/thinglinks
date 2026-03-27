package com.mqttsnet.thinglinks.device.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.utils.DateUtils;
import com.mqttsnet.thinglinks.broker.MqttBrokerOpenAnyUserFacade;
import com.mqttsnet.thinglinks.cache.CacheSuperAbstract;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.context.ContextAwareExecutor;
import com.mqttsnet.thinglinks.device.entity.DeviceAction;
import com.mqttsnet.thinglinks.device.enumeration.DeviceActionStatusEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceActionTypeEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceConnectStatusEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceNodeTypeEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceStatusEnum;
import com.mqttsnet.thinglinks.device.service.DeviceActionService;
import com.mqttsnet.thinglinks.device.service.DeviceService;
import com.mqttsnet.thinglinks.device.service.DeviceSyncAnyUserService;
import com.mqttsnet.thinglinks.device.vo.query.DevicePageQuery;
import com.mqttsnet.thinglinks.device.vo.result.DeviceResultVO;
import com.mqttsnet.thinglinks.device.vo.save.DeviceActionSaveVO;
import com.mqttsnet.thinglinks.product.enumeration.ProtocolTypeEnum;
import com.mqttsnet.thinglinks.vo.result.MqttSessionDetailsResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * -----------------------------------------------------------------------------
 * File Name: DeviceSyncAnyUserServiceImpl
 * -----------------------------------------------------------------------------
 * Description:
 * 设备数据同步业务层接口实现
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2025/1/11       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2025/1/11 17:23
 */

@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class DeviceSyncAnyUserServiceImpl extends CacheSuperAbstract implements DeviceSyncAnyUserService {

    private final LinkCacheDataHelper linkCacheDataHelper;
    private final DeviceService deviceService;
    private final DeviceActionService deviceActionService;
    private final MqttBrokerOpenAnyUserFacade mqttBrokerOpenAnyUserFacade;
    private final ContextAwareExecutor contextAwareExecutor;

    @Qualifier("linkDefaultExecutor")
    private final ThreadPoolExecutor linkDefaultExecutor;

    // 日志格式常量
    private static final String BATCH_LOG_FORMAT =
            "[设备状态同步-开始] 租户ID={} | 总设备数={} | 分页大小={} | 预计批次={}";

    private static final String BATCH_ITEM_LOG =
            "[设备状态同步-进度] 租户ID={} | 当前批次={}/{} | 本页设备={} | 成功累计={} | 失败累计={} | 耗时={}ms";

    private static final String BATCH_SUMMARY =
            "[设备状态同步-完成] 租户ID={} | 总耗时={}ms | 成功总数={} | 失败总数={} | 平均耗时={}ms/设备";

    private static final String DEVICE_DETAIL_LOG =
            "[设备状态同步-设备] 租户ID={} | 设备标识={} | 协议类型={} | 状态={} | 耗时={}ms | 错误={}";

    /**
     * 同步设备连接状态
     *
     * @param tenantId 租户ID
     */
    @Override
    public void syncDeviceConnectionStatus(Long tenantId) {
        long startTime = System.currentTimeMillis();
        AtomicInteger totalSuccess = new AtomicInteger();
        AtomicInteger totalFail = new AtomicInteger();

        log.info("Starting device connection status sync for tenantId: {}", tenantId);

        try {
            // 构建查询参数
            DevicePageQuery queryModel = DevicePageQuery.builder()
                    .deviceStatusList(Arrays.asList(
                            DeviceStatusEnum.ACTIVATED.getValue(),
                            DeviceStatusEnum.LOCKED.getValue()))
                    .nodeTypeList(Arrays.asList(
                            DeviceNodeTypeEnum.ORDINARY.getValue(),
                            DeviceNodeTypeEnum.GATEWAY.getValue()))
                    .build();

            // 先获取第一页数据获取总数
            PageParams<DevicePageQuery> firstPageParams = new PageParams<>(1, PAGE_SIZE);
            firstPageParams.setModel(queryModel);
            IPage<DeviceResultVO> firstPage = deviceService.getPage(firstPageParams);

            if (firstPage == null || firstPage.getTotal() == 0) {
                log.info("No device connection state to synchronize was found for tenantId: {}", tenantId);
                return;
            }

            // 批次元数据
            long totalDevices = firstPage.getTotal();
            int totalPages = (int) firstPage.getPages();

            log.info(BATCH_LOG_FORMAT, tenantId, totalDevices, PAGE_SIZE, totalPages);

            // 按页顺序处理
            IntStream.rangeClosed(1, totalPages)
                    .sequential()
                    .forEach(currentPage -> {
                        ContextUtil.setTenantId(tenantId);
                        long pageStartTime = System.currentTimeMillis();

                        // 查询当前页设备
                        List<DeviceResultVO> devices = fetchDevicePage(currentPage, queryModel);

                        // 处理当前页设备
                        processDevicesBatch(tenantId, devices, totalSuccess, totalFail);

                        log.info(BATCH_ITEM_LOG, tenantId, currentPage, totalPages, devices.size(), totalSuccess.get(), totalFail.get(), System.currentTimeMillis() - pageStartTime);
                    });

            long totalCost = System.currentTimeMillis() - startTime;
            log.info(BATCH_SUMMARY, tenantId, totalCost, totalSuccess.get(), totalFail.get(), totalDevices > 0 ? totalCost / totalDevices : 0);

        } catch (Exception e) {
            log.error("Error occurred during device status sync for tenantId: {}", tenantId, e);
        }
    }

    /**
     * 获取分页设备数据
     *
     * @param currentPage 当前页码
     * @param queryModel  查询条件
     * @return 设备列表
     */
    private List<DeviceResultVO> fetchDevicePage(int currentPage, DevicePageQuery queryModel) {
        PageParams<DevicePageQuery> params = new PageParams<>(currentPage, PAGE_SIZE);
        params.setModel(queryModel);
        return Optional.ofNullable(deviceService.getPage(params))
                .map(IPage::getRecords)
                .orElse(Collections.emptyList());
    }

    /**
     * 处理设备批次数据
     *
     * @param tenantId     租户ID
     * @param devices      设备列表
     * @param totalSuccess 成功计数器
     * @param totalFail    失败计数器
     */
    private void processDevicesBatch(Long tenantId, List<DeviceResultVO> devices,
                                     AtomicInteger totalSuccess, AtomicInteger totalFail) {
        AtomicInteger pageSuccess = new AtomicInteger();
        AtomicInteger pageFail = new AtomicInteger();

        List<CompletableFuture<Void>> deviceFutures = devices.stream()
                .map(device -> contextAwareExecutor.executeWithContext(() -> {
                    long deviceStart = System.currentTimeMillis();
                    try {
                        syncDeviceStatus(device);
                        pageSuccess.incrementAndGet();
                    } catch (Exception e) {
                        pageFail.incrementAndGet();
                        log.error(DEVICE_DETAIL_LOG, tenantId, device.getDeviceIdentification(), "未知", "失败", System.currentTimeMillis() - deviceStart, e.getClass().getSimpleName() + ":" + e.getMessage());
                    }
                    return null;
                }, linkDefaultExecutor))
                .map(future -> future.thenApply(result -> (Void) null))
                .toList();

        // 等待当前页所有设备处理完成
        CompletableFuture.allOf(deviceFutures.toArray(new CompletableFuture[0]))
                .thenAccept(v -> {
                    totalSuccess.addAndGet(pageSuccess.get());
                    totalFail.addAndGet(pageFail.get());
                })
                .exceptionally(ex -> {
                    log.error("批次处理异常", ex);
                    return null;
                })
                .join();
    }

    /**
     * 同步设备状态
     *
     * @param device 设备信息
     */
    private void syncDeviceStatus(DeviceResultVO device) {
        long startTime = System.currentTimeMillis();

        try {
            DeviceCacheVO deviceCacheVO = linkCacheDataHelper.getDeviceCacheVO(device.getDeviceIdentification())
                    .orElseThrow(() -> new IllegalArgumentException("Device does not exist: " + device.getDeviceIdentification()));

            String protocolType = deviceCacheVO.getProductCacheVO().getProtocolType();

            // 根据协议类型分发处理
            ProtocolTypeEnum.fromValue(protocolType).ifPresent(protocol -> {
                switch (protocol) {
                    case MQTT:
                        handleMqttProtocol(device, deviceCacheVO);
                        break;
                    case WEBSOCKET:
                        handleWebsocketProtocol(device, deviceCacheVO);
                        break;
                    case TCP:
                        handleTcpProtocol(device, deviceCacheVO);
                        break;
                    case HTTP:
                        handleHttpProtocol(device, deviceCacheVO);
                        break;
                    default:
                        handleUnknownProtocol(device, deviceCacheVO, protocol);
                        break;
                }
            });

            log.debug(DEVICE_DETAIL_LOG, ContextUtil.getTenantId(), device.getDeviceIdentification(),
                    protocolType, "成功", System.currentTimeMillis() - startTime, "无");

        } catch (Exception e) {
            log.error(DEVICE_DETAIL_LOG, ContextUtil.getTenantId(), device.getDeviceIdentification(),
                    "未知", "失败", System.currentTimeMillis() - startTime, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 处理MQTT协议设备
     *
     * @param device        设备信息
     * @param deviceCacheVO 设备缓存信息
     */
    private void handleMqttProtocol(DeviceResultVO device, DeviceCacheVO deviceCacheVO) {
        handleMqttProtocol(deviceCacheVO).ifPresent(targetStatus -> {
            DeviceConnectStatusEnum.fromValue(device.getConnectStatus()).ifPresent(currentStatus -> {
                if (!targetStatus.equals(currentStatus)) {
                    updateDeviceStatus(device, currentStatus, targetStatus);
                }
            });
        });
    }

    /**
     * 处理Websocket协议设备
     *
     * @param device        设备信息
     * @param deviceCacheVO 设备缓存信息
     */
    private void handleWebsocketProtocol(DeviceResultVO device, DeviceCacheVO deviceCacheVO) {
        log.debug("Websocket protocol device {} - protocol specific handling needed",
                device.getDeviceIdentification());
        // Websocket协议特定的状态检查逻辑可以在这里实现
    }

    /**
     * 处理TCP协议设备
     *
     * @param device        设备信息
     * @param deviceCacheVO 设备缓存信息
     */
    private void handleTcpProtocol(DeviceResultVO device, DeviceCacheVO deviceCacheVO) {
        log.debug("TCP protocol device {} - protocol specific handling needed",
                device.getDeviceIdentification());
        // TCP连接状态检查逻辑可以在这里实现
    }

    /**
     * 处理HTTP协议设备
     *
     * @param device        设备信息
     * @param deviceCacheVO 设备缓存信息
     */
    private void handleHttpProtocol(DeviceResultVO device, DeviceCacheVO deviceCacheVO) {
        log.debug("HTTP protocol device {} - protocol specific handling needed",
                device.getDeviceIdentification());
        // HTTP心跳或状态查询接口检查逻辑可以在这里实现
    }


    /**
     * 处理未知协议设备
     *
     * @param device        设备信息
     * @param deviceCacheVO 设备缓存信息
     * @param protocol      协议类型
     */
    private void handleUnknownProtocol(DeviceResultVO device, DeviceCacheVO deviceCacheVO, ProtocolTypeEnum protocol) {
        log.debug("Unknown protocol {} for device {}, skipping sync",
                protocol.getDesc(), device.getDeviceIdentification());
    }

    /**
     * MQTT协议具体实现
     *
     * @param deviceCacheVO 设备缓存信息
     * @return 设备连接状态
     */
    private Optional<DeviceConnectStatusEnum> handleMqttProtocol(DeviceCacheVO deviceCacheVO) {
        try {
            R<MqttSessionDetailsResultVO> sessionInfoR = mqttBrokerOpenAnyUserFacade.getSessionInfo(
                    ContextUtil.getTenantIdStr(), deviceCacheVO.getDeviceIdentification(), deviceCacheVO.getClientId());

            if (sessionInfoR == null || R.TIMEOUT_CODE == sessionInfoR.getCode()) {
                log.error("mqttBrokerOpenAnyUserFacade.getSessionInfo timeout for device: {}",
                        deviceCacheVO.getDeviceIdentification());
                return Optional.empty();
            }

            return Optional.of(sessionInfoR.getIsSuccess() ?
                    DeviceConnectStatusEnum.ONLINE : DeviceConnectStatusEnum.OFFLINE);
        } catch (Exception e) {
            log.error("Failed to fetch session info for device {}", deviceCacheVO.getDeviceIdentification(), e);
            return Optional.empty();
        }
    }


    /**
     * 更新设备状态，并记录设备动作
     *
     * @param device        设备信息
     * @param currentStatus 当前状态
     * @param targetStatus  目标状态
     */
    private void updateDeviceStatus(DeviceResultVO device, DeviceConnectStatusEnum currentStatus, DeviceConnectStatusEnum targetStatus) {
        try {
            // 更新设备连接状态
            deviceService.updateDeviceConnectionStatusById(device.getId(), targetStatus.getValue());
            log.info("Device {} status updated to {}", device.getDeviceIdentification(), targetStatus.getDesc());

            // 记录设备动作
            recordDeviceAction(device, currentStatus, targetStatus);
        } catch (Exception e) {
            log.error("Failed to update status for device {} to {}.", device.getDeviceIdentification(), targetStatus.getDesc(), e);
        }
    }


    /**
     * 记录设备动作数据
     *
     * @param device        设备信息
     * @param currentStatus 当前状态
     * @param targetStatus  目标状态
     */
    private void recordDeviceAction(DeviceResultVO device, DeviceConnectStatusEnum currentStatus, DeviceConnectStatusEnum targetStatus) {
        // 构建设备动作描述和类型
        String describable = buildDeviceStatusDescription(device, currentStatus, targetStatus);
        DeviceActionSaveVO deviceActionSaveVO = getDeviceActionSaveVO(device, targetStatus, describable);
        try {
            // 保存设备动作记录
            DeviceAction deviceAction = deviceActionService.saveDeviceAction(deviceActionSaveVO);
            log.info("Device action saved: {}", deviceAction);
        } catch (Exception e) {
            log.error("Failed to save device action for device ID: {}", device.getDeviceIdentification(), e);
        }
    }


    private DeviceActionSaveVO getDeviceActionSaveVO(DeviceResultVO device, DeviceConnectStatusEnum targetStatus, String describable) {
        DeviceActionTypeEnum actionType = getActionTypeForStatus(targetStatus);

        // 构建并保存设备动作记录
        DeviceActionSaveVO deviceActionSaveVO = new DeviceActionSaveVO();
        deviceActionSaveVO.setDeviceIdentification(device.getDeviceIdentification());
        deviceActionSaveVO.setActionType(actionType.getAction());
        deviceActionSaveVO.setMessage(actionType.getDescription());
        deviceActionSaveVO.setStatus(DeviceActionStatusEnum.SUCCESSFUL.getValue());
        deviceActionSaveVO.setRemark(describable);
        return deviceActionSaveVO;
    }

    /**
     * 构建设备状态描述
     *
     * @param device        设备信息
     * @param currentStatus 当前状态
     * @param targetStatus  目标状态
     * @return
     */
    private String buildDeviceStatusDescription(DeviceResultVO device, DeviceConnectStatusEnum currentStatus, DeviceConnectStatusEnum targetStatus) {
        // 生成详细的描述
        StringBuilder description = new StringBuilder();
        description.append("Device ClientId: ").append(device.getClientId())
                .append(", DeviceIdentification: ").append(device.getDeviceIdentification())
                .append(", Current Status: ").append(currentStatus)
                .append(", Target Status: ").append(targetStatus)
                .append(", Timestamp: ").append(DateUtils.millisecondStampL())
                .append(", Source: System Sync");

        if (!currentStatus.equals(targetStatus)) {
            description.append(", Status Change: ")
                    .append(currentStatus)
                    .append(" -> ")
                    .append(targetStatus)
                    .append(" (Status change detected).");
        } else {
            description.append(", No change detected in status.");
        }
        return description.toString();
    }

    /**
     * 获取设备状态对应的动作类型
     *
     * @param targetStatus 连接状态
     * @return {@link DeviceActionTypeEnum} 设备动作类型
     */
    private DeviceActionTypeEnum getActionTypeForStatus(DeviceConnectStatusEnum targetStatus) {
        return switch (targetStatus) {
            case ONLINE -> DeviceActionTypeEnum.CONNECT;
            case OFFLINE -> DeviceActionTypeEnum.DISCONNECT;
            default -> DeviceActionTypeEnum.UNKNOWN;
        };
    }


}