package com.mqttsnet.thinglinks.video.cache.impl;

import java.util.Optional;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.cache.repository.CachePlusOps;
import com.mqttsnet.basic.cache.utils.CacheUtil;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.thinglinks.common.cache.video.device.DeviceInfoCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.video.cache.RedisCacheStorage;
import com.mqttsnet.thinglinks.video.cache.VideoDeviceInfoCacheVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Redis缓存实现
 *
 * @author mqttsnet
 */
@DS(DsConstant.BASE_TENANT)
@RequiredArgsConstructor
@Slf4j
@Component
public class RedisCacheStorageImpl implements RedisCacheStorage {

    private final CachePlusOps cachePlusOps;


    private final CacheUtil cacheUtil;


    @Override
    public void setDeviceInfo(VideoDeviceInfoCacheVO deviceInfoCacheVO) {
        CacheKey cacheKey = DeviceInfoCacheKeyBuilder.build(deviceInfoCacheVO.getDeviceIdentification());
        cachePlusOps.del(cacheKey);
        cachePlusOps.set(cacheKey, deviceInfoCacheVO);
    }

    @Override
    public VideoDeviceInfoCacheVO getDeviceInfo(String deviceIdentification) {
        CacheKey cacheKey = DeviceInfoCacheKeyBuilder.build(deviceIdentification);
        Optional<VideoDeviceInfoCacheVO> objectFromCache = cacheUtil.getObjectFromCache(cacheKey.getKey(), VideoDeviceInfoCacheVO.class);
        return objectFromCache.get();
    }


}
