package com.mqttsnet.thinglinks.video.cache;

/**
 * @author mqttsnet
 */
public interface RedisCacheStorage {


    /**
     * 将device信息写入redis
     *
     * @param deviceInfoCacheVO 设备信息
     */
    void setDeviceInfo(VideoDeviceInfoCacheVO deviceInfoCacheVO);

    /**
     * 获取缓存中的设备信息
     *
     * @param deviceIdentification 设备标识
     * @return {@link VideoDeviceInfoCacheVO}  设备缓存VO
     */
    VideoDeviceInfoCacheVO getDeviceInfo(String deviceIdentification);

}
