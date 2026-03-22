package com.mqttsnet.thinglinks.cache.device;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.cache.repository.CachePlusOps;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.cache.CacheSuperAbstract;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.cache.vo.product.ProductCacheVO;
import com.mqttsnet.thinglinks.common.cache.link.device.DeviceCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.context.ContextAwareExecutor;
import com.mqttsnet.thinglinks.device.service.DeviceService;
import com.mqttsnet.thinglinks.device.vo.query.DevicePageQuery;
import com.mqttsnet.thinglinks.device.vo.result.DeviceResultVO;
import com.mqttsnet.thinglinks.product.service.ProductService;
import com.mqttsnet.thinglinks.product.vo.query.ProductPageQuery;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * -----------------------------------------------------------------------------
 * File Name: DeviceCacheService.java
 * -----------------------------------------------------------------------------
 * Description:
 * Service layer for Device cache management.
 * Equipment information + Product information
 * -----------------------------------------------------------------------------
 *
 * @author ShiHuan Sun
 * @version 1.1
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2023-10-21    ShiHuan Sun     1.0         Initial version.
 * 2025-07-31    ShiHuan Sun     1.1         优化日志记录和性能优化。
 * <p>
 * -----------------------------------------------------------------------------
 */
@DS(DsConstant.BASE_TENANT)
@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceCacheService extends CacheSuperAbstract {
    private final CachePlusOps cachePlusOps;
    private final DeviceService deviceService;
    private final ProductService productService;
    private final ContextAwareExecutor contextAwareExecutor;


    private static final String BATCH_LOG_FORMAT =
            "[批次-开始] 租户ID={} | 操作类型={} | 总设备数={} | 分页大小={} | 预计批次={}";

    private static final String BATCH_ITEM_LOG =
            "[批次-进度] 租户ID={} | 当前批次={}/{} | 本页设备={} | 成功累计={} | 失败累计={} | 耗时={}ms";

    private static final String BATCH_SUMMARY =
            "[批次-完成] 租户ID={} | 总耗时={}ms | 成功总数={} | 失败总数={} | 平均耗时={}ms/设备";

    private static final String DEVICE_DETAIL_LOG =
            "[批次-设备] 租户ID={} | 设备标识={} | 状态={} | 耗时={}ms | 错误={}";

    /**
     * 刷新指定租户的设备缓存（全量）
     *
     * @param tenantId 租户ID，不能为null
     * @throws IllegalArgumentException 如果tenantId为null
     * @throws RuntimeException         当缓存刷新过程中出现异常时抛出
     * @see #loadProductCacheMap(Long)
     * @see #processDevicesBatch(Long, List, Map, AtomicInteger, AtomicInteger)
     */
    public void refreshDeviceCacheForTenant(Long tenantId) {
        long startTime = System.currentTimeMillis();
        AtomicInteger totalSuccess = new AtomicInteger();
        AtomicInteger totalFail = new AtomicInteger();

        // 批次元数据
        int totalDevices = deviceService.findDeviceTotal().intValue();
        int totalPages = (int) Math.ceil((double) totalDevices / PAGE_SIZE);

        log.info(BATCH_LOG_FORMAT, tenantId, "设备缓存全量刷新", totalDevices, PAGE_SIZE, totalPages);

        // 预加载所有产品数据
        Map<String, ProductCacheVO> productCacheMap = loadProductCacheMap(tenantId);

        // 按页顺序处理
        IntStream.rangeClosed(1, totalPages)
                .sequential() // 保持顺序处理分页
                .forEach(currentPage -> {
                    ContextUtil.setTenantId(tenantId);
                    long pageStartTime = System.currentTimeMillis();
                    // 查询当前页设备
                    List<DeviceResultVO> devices = fetchDevicePage(currentPage);
                    // 处理当前页设备（内部并行处理）
                    processDevicesBatch(tenantId, devices, productCacheMap, totalSuccess, totalFail);
                    log.info(BATCH_ITEM_LOG, tenantId, currentPage, totalPages, devices.size(), totalSuccess.get(), totalFail.get(), System.currentTimeMillis() - pageStartTime);
                });

        long totalCost = System.currentTimeMillis() - startTime;
        log.info(BATCH_SUMMARY, tenantId, totalCost, totalSuccess.get(), totalFail.get(), totalDevices > 0 ? totalCost / totalDevices : 0);
    }

    /**
     * 获取分页设备数据
     *
     * @param currentPage 当前页码(从1开始)
     * @return 当前页的设备列表，如果查询失败返回空列表
     * @throws IllegalArgumentException 如果currentPage小于1
     * @see DeviceService#getPage(PageParams)
     */
    private List<DeviceResultVO> fetchDevicePage(int currentPage) {
        ArgumentAssert.isTrue(currentPage >= 1, "currentPage must be greater than or equal to 1");
        PageParams<DevicePageQuery> params = new PageParams<>(currentPage, PAGE_SIZE);
        params.setModel(DevicePageQuery.builder().build());
        return Optional.ofNullable(deviceService.getPage(params))
                .map(IPage::getRecords)
                .orElse(Collections.emptyList());
    }

    /**
     * 加载产品缓存映射Map
     *
     * @param tenantId 租户ID，不能为null
     * @return 产品缓存映射Map，key为产品标识，value为产品缓存VO
     * @throws IllegalArgumentException 如果tenantId为null
     * @see ProductService#getProductResultVOList(ProductPageQuery)
     */
    private Map<String, ProductCacheVO> loadProductCacheMap(Long tenantId) {
        return productService.getProductResultVOList(new ProductPageQuery()).stream()
                .filter(p -> p != null && p.getProductIdentification() != null)
                .collect(Collectors.toMap(
                        ProductResultVO::getProductIdentification,
                        p -> {
                            ProductCacheVO vo = new ProductCacheVO();
                            BeanUtil.copyProperties(p, vo);
                            vo.setTenantId(tenantId);
                            return vo;
                        },
                        (existing, replacement) -> existing
                ));
    }


    /**
     * 处理设备批次数据
     *
     * @param tenantId        租户ID，不能为null
     * @param devices         当前批次的设备列表，不能为null
     * @param productCacheMap 产品缓存映射Map，不能为null
     * @param totalSuccess    成功计数器(累计)，不能为null
     * @param totalFail       失败计数器(累计)，不能为null
     * @throws IllegalArgumentException 如果任何参数为null
     * @see this#cacheDeviceBasedOnIdentification(DeviceCacheVO)
     * @see this#cacheDeviceBasedOnClientId(DeviceCacheVO)
     */
    private void processDevicesBatch(Long tenantId,
                                     List<DeviceResultVO> devices,
                                     Map<String, ProductCacheVO> productCacheMap,
                                     AtomicInteger totalSuccess,
                                     AtomicInteger totalFail) {
        AtomicInteger pageSuccess = new AtomicInteger();
        AtomicInteger pageFail = new AtomicInteger();

        List<CompletableFuture<Void>> deviceFutures = devices.stream()
                .map(device -> contextAwareExecutor.executeWithContext(() -> {
                    long deviceStart = System.currentTimeMillis();
                    try {
                        DeviceCacheVO cacheVO = BeanPlusUtil.toBeanIgnoreError(device, DeviceCacheVO.class);
                        cacheVO.setTenantId(tenantId);
                        // 关联产品信息
                        Optional.ofNullable(device.getProductIdentification())
                                .map(productCacheMap::get)
                                .ifPresent(cacheVO::setProductCacheVO);

                        // 更新缓存
                        this.cacheDeviceBasedOnIdentification(cacheVO);
                        this.cacheDeviceBasedOnClientId(cacheVO);
                        pageSuccess.incrementAndGet();
                    } catch (Exception e) {
                        pageFail.incrementAndGet();
                        log.error(DEVICE_DETAIL_LOG, tenantId, device.getDeviceIdentification(), "失败", System.currentTimeMillis() - deviceStart, e.getClass().getSimpleName() + ":" + e.getMessage(), e);
                    }
                    return null;
                }))
                .map(future -> future.thenApply(result -> (Void) null))
                .toList();

        CompletableFuture.allOf(deviceFutures.toArray(new CompletableFuture[0]))
                .thenAccept(v -> {
                    totalSuccess.addAndGet(pageSuccess.get());
                    totalFail.addAndGet(pageFail.get());
                })
                .exceptionally(ex -> {
                    log.error("批次处理异常", ex);
                    return null;
                });
    }


    /**
     * 刷新单个设备的缓存
     *
     * @param deviceIdentification 设备标识，不能为空
     * @return 刷新是否成功
     * @throws IllegalArgumentException 如果deviceIdentification为空
     * @see DeviceService#findByDeviceIdentification(String)
     * @see this#cacheDeviceBasedOnIdentification(DeviceCacheVO)
     * @see this#cacheDeviceBasedOnClientId(DeviceCacheVO)
     */
    public boolean refreshDeviceCache(String deviceIdentification) {
        try {
            log.info("开始刷新{}设备缓存: {}", ContextUtil.getTenantId(), deviceIdentification);
            ArgumentAssert.notBlank(deviceIdentification, "deviceIdentification is null");
            // 获取设备信息
            Optional<DeviceCacheVO> deviceCacheVOOptional = deviceService.findDeviceCacheVO(ContextUtil.getTenantId(), deviceIdentification);
            if (deviceCacheVOOptional.isEmpty()) {
                log.warn("未找到设备信息: {}", deviceIdentification);
                return false;
            }
            // 更新缓存
            this.cacheDeviceBasedOnIdentification(deviceCacheVOOptional.get());
            this.cacheDeviceBasedOnClientId(deviceCacheVOOptional.get());
            log.info("设备缓存刷新成功: {}", deviceIdentification);
            return true;
        } catch (Exception e) {
            log.error("刷新设备缓存失败: {}", deviceIdentification, e);
            return false;
        }
    }


    /**
     * Cache the DeviceCacheVO object based on its identification.
     *
     * @param deviceCacheVO DeviceCacheVO object to be cached.
     */
    public void cacheDeviceBasedOnIdentification(DeviceCacheVO deviceCacheVO) {
        CacheKey deviceIdentKey = DeviceCacheKeyBuilder.build(deviceCacheVO.getDeviceIdentification());
        cachePlusOps.del(deviceIdentKey);
        cachePlusOps.set(deviceIdentKey, deviceCacheVO);
    }

    /**
     * Cache the DeviceCacheVO object based on its client ID.
     *
     * @param deviceCacheVO DeviceCacheVO object to be cached.
     */
    public void cacheDeviceBasedOnClientId(DeviceCacheVO deviceCacheVO) {
        CacheKey clientIdKey = DeviceCacheKeyBuilder.build(deviceCacheVO.getClientId());
        cachePlusOps.del(clientIdKey);
        cachePlusOps.set(clientIdKey, deviceCacheVO);
    }


}
