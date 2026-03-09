package com.mqttsnet.thinglinks.video.manager.device.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.video.entity.device.VideoDeviceChannel;
import com.mqttsnet.thinglinks.video.manager.device.VideoDeviceChannelManager;
import com.mqttsnet.thinglinks.video.mapper.device.VideoDeviceChannelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 流媒体设备通道表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-05-15 17:09:48
 * @create [2025-05-15 17:09:48] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class VideoDeviceChannelManagerImpl extends SuperManagerImpl<VideoDeviceChannelMapper, VideoDeviceChannel> implements VideoDeviceChannelManager {

}


