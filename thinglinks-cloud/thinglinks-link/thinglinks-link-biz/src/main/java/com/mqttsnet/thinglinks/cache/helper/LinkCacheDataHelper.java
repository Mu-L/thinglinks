package com.mqttsnet.thinglinks.cache.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.cache.redis2.CacheResult;
import com.mqttsnet.basic.cache.repository.CachePlusOps;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.model.cache.CacheHashKey;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.basic.utils.DateUtils;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceActionCacheVO;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.cache.vo.product.ProductModelCacheVO;
import com.mqttsnet.thinglinks.common.cache.link.collectionpool.DeviceActionCollectionPoolCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.link.collectionpool.DeviceDataCollectionPoolCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.link.counter.DownLinkDataCounterCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.link.counter.UpLinkDataCounterCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.link.device.DeviceCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.link.ota.OtaTaskExecutorOffsetCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.link.product.ProductModelCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.link.product.ProductModelSuperTableCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.device.manager.DeviceManager;
import com.mqttsnet.thinglinks.product.entity.Product;
import com.mqttsnet.thinglinks.product.manager.ProductManager;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import com.mqttsnet.thinglinks.tds.vo.result.SuperTableDescribeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

/**
 * -----------------------------------------------------------------------------
 * 文件名称: LinkCacheDataHelper.java
 * -----------------------------------------------------------------------------
 * 描述:
 * LinkCacheDataHelper
 * 设备集成基础数据缓存操作类
 * 1: 原则上此类只负责缓存数据的读取和写入操作，不涉及业务逻辑的处理。
 * 2: 如有需要数据查询构造处理的场景，建议在Manager层处理进行处理,注意切勿在Service层处理并调用,Service大概率会用到此类查询缓存数据，可能出现循环依赖问题。
 * -----------------------------------------------------------------------------
 *
 * @author ShiHuan Sun
 * @version 1.0
 * -----------------------------------------------------------------------------
 * 修改历史:
 * 日期           作者          版本        描述
 * --------      --------     -------   --------------------
 * 2023-10-12    ShiHuan Sun   1.0        初始创建
 * 2024-03-06    ShiHuan Sun   1.1        新增OTA升级任务执行器偏移量 KEY
 * <p>
 * -----------------------------------------------------------------------------
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LinkCacheDataHelper {

    private final CachePlusOps cachePlusOps;
    private final DeviceManager deviceManager;


    /**
     * Retrieves a device cache value from Redis.
     *
     * @param deviceIdOrClientId the deviceIdentification or clientId, must not be {@literal null}.
     * @return a DeviceCacheVO object, or {@literal null} if not present.
     */
    @DS(DsConstant.BASE_TENANT)
    public Optional<DeviceCacheVO> getDeviceCacheVO(String deviceIdOrClientId) {
        Long tenantId = ContextUtil.getTenantId();
        CacheKey cacheKey = DeviceCacheKeyBuilder.build(deviceIdOrClientId);
        CacheResult<DeviceCacheVO> cacheResult = cachePlusOps.get(cacheKey, (k) -> deviceManager.findDeviceCacheVO(tenantId, deviceIdOrClientId).get(), false);
        return Optional.ofNullable(cacheResult.getRawValue());
    }

    /**
     * Deletes a device cache value from Redis.
     *
     * @param deviceIdOrClientId the deviceIdentification or clientId, must not be {@literal null}.
     */
    @DS(DsConstant.BASE_TENANT)
    public void deleteDeviceCacheVO(String deviceIdOrClientId) {
        CacheKey cacheKey = DeviceCacheKeyBuilder.build(deviceIdOrClientId);
        cachePlusOps.del(cacheKey);
        log.info("Device cache deleted for deviceIdOrClientId: {}", deviceIdOrClientId);
    }


    /**
     * product model cache value from Redis.
     *
     * @param productIdentification the product identifier, must not be {@literal null}.
     */
    public ProductModelCacheVO getProductModelCacheVO(String productIdentification) {
        String cacheProductModelKey = ProductModelCacheKeyBuilder.build(productIdentification).getKey();
        CacheResult<Object> objectCacheResult = cachePlusOps.get(cacheProductModelKey);
        if (objectCacheResult == null || objectCacheResult.getRawValue() == null) {
            log.warn("The product model is not existence");
            return null;
        }
        return BeanPlusUtil.toBeanIgnoreError(objectCacheResult.getRawValue(), ProductModelCacheVO.class);
    }

    /**
     * Deletes a product model cache value from Redis.
     *
     * @param productIdentification the product identifier, must not be {@literal null}.
     */
    public void deleteProductModelCacheVO(String productIdentification) {
        CacheKey cacheKey = ProductModelCacheKeyBuilder.build(productIdentification);
        cachePlusOps.del(cacheKey);
        log.info("Product model cache deleted for productIdentification: {}", productIdentification);
    }


    /**
     * Retrieves a device cache value from Redis.
     *
     * @param productIdentification the product identifier, must not be {@literal null}.
     * @param serviceCode           the service code, must not be {@literal null}.
     * @param deviceIdentification  the device identifier, must not be {@literal null}.
     * @return {@link List<SuperTableDescribeVO>} the list of SuperTableDescribeVO objects.
     */
    public List<SuperTableDescribeVO> getProductModelSuperTableCacheVO(String productIdentification, String serviceCode, String deviceIdentification) {
        String cacheProductModelSuperTableKey = ProductModelSuperTableCacheKeyBuilder.build(productIdentification, serviceCode, deviceIdentification).getKey();
        CacheResult<Object> objectCacheResult;

        try {
            objectCacheResult = cachePlusOps.get(cacheProductModelSuperTableKey);
        } catch (Exception e) {
            log.error("Error fetching from cache for key: {}", cacheProductModelSuperTableKey, e);
            return Collections.emptyList();
        }

        if (objectCacheResult == null || objectCacheResult.getRawValue() == null) {
            log.warn("The product model super table is not in the cache for key: {}", cacheProductModelSuperTableKey);
            return Collections.emptyList();
        }
        Object rawValue = objectCacheResult.getRawValue();
        // Check if rawValue is of the expected type before attempting conversion
        if (rawValue instanceof List) {
            List<?> rawList = (List<?>) rawValue;
            if (rawList.isEmpty() || rawList.get(0) instanceof SuperTableDescribeVO) {
                return (List<SuperTableDescribeVO>) rawValue;
            } else {
                log.error("Unexpected type in cached value for key: {}", cacheProductModelSuperTableKey);
                return Collections.emptyList();
            }
        } else {
            log.error("Cached value is not a list for key: {}", cacheProductModelSuperTableKey);
            return Collections.emptyList();
        }
    }

    /**
     * product model super table cache value from Redis.
     *
     * @param productIdentification the product identifier, must not be {@literal null}.
     * @param serviceCode           the service code, must not be {@literal null}.
     * @param deviceIdentification  the device identifier, must not be {@literal null}.
     * @param superTableDescribeOpt the data to set in the cache, must not be {@literal null}.
     */
    public void setProductModelSuperTableCacheVO(String productIdentification, String serviceCode, String deviceIdentification,
                                                 List<SuperTableDescribeVO> superTableDescribeOpt) {
        CacheKey cacheProductModelSuperTableKey = ProductModelSuperTableCacheKeyBuilder.build(productIdentification, serviceCode, deviceIdentification);
        cachePlusOps.del(cacheProductModelSuperTableKey);
        cachePlusOps.set(cacheProductModelSuperTableKey, superTableDescribeOpt);
    }

    /**
     * Sets a device data collection pool cache value in a sorted set in Redis.
     *
     * @param productIdentification the product identifier, must not be {@literal null}.
     * @param deviceIdentification  the device identifier, must not be {@literal null}.
     * @param productResultVO       the data to set in the cache, must not be {@literal null}.
     */
    public void setDeviceDataCollectionPoolCacheVO(String productIdentification, String deviceIdentification, ProductResultVO productResultVO) {
        log.info("Setting device data collection pool cache - Product ID: {}, Device ID: {}, Data: {}",
                productIdentification, deviceIdentification, productResultVO);

        try {
            CacheKey cacheKey = DeviceDataCollectionPoolCacheKeyBuilder.build(productIdentification, deviceIdentification);
            log.info("Generated CacheKey: {}", cacheKey);

            long timestamp = DateUtils.microsecondStampL();
            log.info("Current timestamp: {}", timestamp);

            // 获取最大缓存条数
            Long maxCacheSize = new DeviceDataCollectionPoolCacheKeyBuilder().getMaxCacheSize();

            cachePlusOps.zAdd(cacheKey, productResultVO, timestamp);
            log.info("Data added to cache - CacheKey: {}, ProductResult: {}", cacheKey, productResultVO);

            cachePlusOps.expire(cacheKey);
            log.info("Set cache expiration for CacheKey: {}", cacheKey);

            // 获取当前 Sorted Set 中的元素数量
            long size = cachePlusOps.zCard(cacheKey);
            log.info("Current cache size: {}", size);

            // 如果 Sorted Set 中的元素超过了最大条数，则删除最旧的数据
            if (size > maxCacheSize) {
                // 删除最旧的 excessCount 条数据
                long excessCount = size - maxCacheSize;
                // 删除最旧的 excessCount 条数据
                cachePlusOps.zRem(cacheKey, 0, excessCount - 1);
                log.info("Removed {} oldest data to maintain size limit of {}", excessCount, maxCacheSize);
            }

        } catch (Exception e) {
            log.error("Error setting device data collection pool cache - Product ID: {}, Device ID: {}, Data: {}",
                    productIdentification, deviceIdentification, productResultVO, e);
            throw e;
        }
    }

    /**
     * Retrieves device data collection pool cache values from a sorted set in Redis within the specified score range
     * and optionally clears the retrieved scores from the cache.
     *
     * @param productIdentification the product identifier, must not be {@literal null}.
     * @param deviceIdentification  the device identifier, must not be {@literal null}.
     * @param startScore            the start score of the range to retrieve.
     * @param endScore              the end score of the range to retrieve.
     * @param clear                 flag indicating whether to clear the retrieved data from cache.
     * @return a HashMap containing the scores as keys and the corresponding ProductResultVO objects as values.
     */
    public Map<Long, ProductResultVO> getDeviceDataCollectionPoolCacheVO(String productIdentification, String deviceIdentification, double startScore, double endScore, boolean clear) {
        if (startScore > endScore) {
            log.error("Start score is greater than end score for product: {} and device: {}, range: {} - {}", productIdentification, deviceIdentification, startScore, endScore);
            return Collections.emptyMap();
        }
        CacheKey cacheKey = DeviceDataCollectionPoolCacheKeyBuilder.build(productIdentification, deviceIdentification);
        Set<ZSetOperations.TypedTuple<Object>> resultSet;
        try {
            resultSet = cachePlusOps.zReverseRangeByScoreWithScores(cacheKey, startScore, endScore);
        } catch (Exception e) {
            log.error("Error retrieving from cache for product: {} and device: {}, range: {} - {}", productIdentification, deviceIdentification, startScore, endScore, e);
            return Collections.emptyMap();
        }

        if (resultSet.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, ProductResultVO> resultMap;
        try {
            resultMap = resultSet.stream()
                    .filter(typedTuple -> typedTuple.getValue() instanceof ProductResultVO)
                    .collect(Collectors.toMap(
                            // 将Double的二进制表示转换为Long
                            tuple -> Objects.requireNonNull(tuple.getScore()).longValue(),
                            tuple -> (ProductResultVO) tuple.getValue(),
                            (existing, replacement) -> existing));
        } catch (ClassCastException e) {
            log.error("Cache contains non-ProductResultVO objects for product: {} and device: {}, range: {} - {}",
                    productIdentification, deviceIdentification, startScore, endScore, e);
            return Collections.emptyMap();
        }


        if (clear) {
            deleteDeviceDataCollectionPoolCacheVO(productIdentification, deviceIdentification, startScore, endScore);
        }

        return resultMap;
    }


    /**
     * Deletes device data collection pool cache values from a sorted set in Redis within a score range.
     *
     * @param productIdentification the product identifier, must not be {@literal null}.
     * @param deviceIdentification  the device identifier, must not be {@literal null}.
     * @param startScore            the start of the score range, inclusive.
     * @param endScore              the end of the score range, inclusive.
     */
    public void deleteDeviceDataCollectionPoolCacheVO(String productIdentification, String deviceIdentification, double startScore, double endScore) {
        CacheKey cacheKey = DeviceDataCollectionPoolCacheKeyBuilder.build(productIdentification, deviceIdentification);
        cachePlusOps.zRemRangeByScore(cacheKey, startScore, endScore);
    }


    /**
     * Sets a device action cache value in a sorted set in Redis.
     *
     * @param productIdentification the product identifier, must not be null.
     * @param deviceIdentification  the device identifier, must not be null.
     * @param deviceActionCacheVO   the data to set in the cache, must not be null.
     */
    public void setDeviceActionCacheVO(String productIdentification, String deviceIdentification, DeviceActionCacheVO deviceActionCacheVO) {
        CacheKey cacheKey = DeviceActionCollectionPoolCacheKeyBuilder.build(productIdentification, deviceIdentification);
        cachePlusOps.zAdd(cacheKey, deviceActionCacheVO, DateUtils.microsecondStampL());
        cachePlusOps.expire(cacheKey);
    }

    /**
     * Retrieves all device action cache values from a sorted set in Redis within the score range
     * and optionally clears the retrieved scores from the cache.
     *
     * @param productIdentification the product identifier, must not be null.
     * @param deviceIdentification  the device identifier, must not be null.
     * @param clear                 flag indicating whether to clear the retrieved data from cache.
     * @return a HashMap containing the scores as keys and the corresponding DeviceActionCacheVO objects as values.
     */
    public Map<Long, DeviceActionCacheVO> getDeviceActionCacheVO(String productIdentification, String deviceIdentification, boolean clear) {
        CacheKey cacheKey = DeviceActionCollectionPoolCacheKeyBuilder.build(productIdentification, deviceIdentification);
        Set<ZSetOperations.TypedTuple<Object>> resultSet;
        try {
            resultSet = cachePlusOps.zReverseRangeByScoreWithScores(cacheKey, Double.MIN_VALUE, Double.MAX_VALUE);
        } catch (Exception e) {
            log.error("Error retrieving from cache for product: {} and device: {}", productIdentification, deviceIdentification, e);
            return Collections.emptyMap();
        }

        if (resultSet.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, DeviceActionCacheVO> resultMap;
        try {
            resultMap = resultSet.stream()
                    .filter(typedTuple -> typedTuple.getValue() instanceof DeviceActionCacheVO)
                    .collect(Collectors.toMap(
                            tuple -> Objects.requireNonNull(tuple.getScore()).longValue(),
                            typedTuple -> (DeviceActionCacheVO) typedTuple.getValue(),
                            (existing, replacement) -> existing
                    ));
        } catch (ClassCastException e) {
            log.error("Cache contains non-DeviceActionCacheVO objects for product: {} and device: {}", productIdentification, deviceIdentification, e);
            return Collections.emptyMap();
        }

        if (clear) {
            double startScore = Collections.min(resultMap.keySet());
            double endScore = Collections.max(resultMap.keySet());
            deleteDeviceActionCacheVO(productIdentification, deviceIdentification, startScore, endScore);
        }

        return resultMap;
    }

    /**
     * Deletes device action cache values from a sorted set in Redis within a score range.
     *
     * @param productIdentification the product identifier, must not be null.
     * @param deviceIdentification  the device identifier, must not be null.
     * @param startScore            the start of the score range, inclusive.
     * @param endScore              the end of the score range, inclusive.
     */
    public void deleteDeviceActionCacheVO(String productIdentification, String deviceIdentification, double startScore, double endScore) {
        CacheKey cacheKey = DeviceActionCollectionPoolCacheKeyBuilder.build(productIdentification, deviceIdentification);
        cachePlusOps.zRemRangeByScore(cacheKey, startScore, endScore);
    }


    /**
     * Increments the counter for a specific device's uplink data.
     *
     * @return The incremented count.
     */
    public Long incrementUpLinkCounter() {
        LocalDateTime now = LocalDateTime.now();
        String day = now.format(DateTimeFormatter.ofPattern(DateUtils.YYYYMMDD_FORMAT));
        String hourMinute = now.format(DateTimeFormatter.ofPattern(DateUtils.HHMM_FORMAT));
        CacheHashKey hashKey = UpLinkDataCounterCacheKeyBuilder.build(day, hourMinute);
        return cachePlusOps.incrementHashCounter(hashKey);
    }


    /**
     * Increments the counter for a specific device's downlink data.
     *
     * @return The incremented count.
     */
    public Long incrementDownLinkCounter() {
        LocalDateTime now = LocalDateTime.now();
        String day = now.format(DateTimeFormatter.ofPattern(DateUtils.YYYYMMDD_FORMAT));
        String hourMinute = now.format(DateTimeFormatter.ofPattern(DateUtils.HHMM_FORMAT));
        CacheHashKey hashKey = DownLinkDataCounterCacheKeyBuilder.build(day, hourMinute);
        return cachePlusOps.incrementHashCounter(hashKey);
    }


    /**
     * Retrieves the current time offset for OTA (Over-The-Air) task executor from the cache,
     * stored as a Datetime(yyyy-MM-dd HH:mm:ss). This method facilitates managing the execution
     * sequence of OTA tasks by providing the last known time offset. Using Optional
     * emphasizes the possibility that the offset may not always be present.
     *
     * @return An Optional<String> containing the Datetime(yyyy-MM-dd HH:mm:ss) offset if present;
     * otherwise, Optional.empty() if the offset is not found in the cache.
     */
    public Optional<String> getOtaTaskExecutorOffset() {
        CacheKey key = OtaTaskExecutorOffsetCacheKeyBuilder.build();
        CacheResult<Object> result = cachePlusOps.get(key);
        if (!result.isNullVal() && result.getRawValue() != null) {
            try {
                return Optional.of(result.getRawValue().toString());
            } catch (ClassCastException e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Sets the current time offset for OTA (Over-The-Air) task executor in the cache,
     * stored as a Datetime(yyyy-MM-dd HH:mm:ss). This method facilitates managing the execution
     * sequence of OTA tasks by providing the last known time offset.
     *
     * @param offset The Datetime(yyyy-MM-dd HH:mm:ss) offset to set in the cache.
     */
    public void setOtaTaskExecutorOffset(String offset) {
        CacheKey cacheKey = OtaTaskExecutorOffsetCacheKeyBuilder.build();
        cachePlusOps.set(cacheKey, offset);
        cachePlusOps.expire(cacheKey);
    }

}