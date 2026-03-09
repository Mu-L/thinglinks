package com.mqttsnet.thinglinks.video.manager.device;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.video.entity.device.VideoDeviceInfo;

/**
 * <p>
 * 通用业务接口
 * 流媒体设备信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-05-15 17:00:56
 * @create [2025-05-15 17:00:56] [mqttsnet]
 */
public interface VideoDeviceInfoManager extends SuperManager<VideoDeviceInfo> {


    /**
     * 根据设备标识查询设备信息
     *
     * @param deviceIdentification 设备标识
     * @return {@link VideoDeviceInfo} 设备信息
     */
    VideoDeviceInfo getOneByDeviceIdentification(String deviceIdentification);
}


