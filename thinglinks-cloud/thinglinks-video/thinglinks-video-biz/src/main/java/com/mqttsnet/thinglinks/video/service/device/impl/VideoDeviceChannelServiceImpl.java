package com.mqttsnet.thinglinks.video.service.device.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.video.entity.device.VideoDeviceChannel;
import com.mqttsnet.thinglinks.video.manager.device.VideoDeviceChannelManager;
import com.mqttsnet.thinglinks.video.service.device.VideoDeviceChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
 * 流媒体设备通道表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-05-15 17:09:48
 * @create [2025-05-15 17:09:48] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class VideoDeviceChannelServiceImpl extends SuperServiceImpl<VideoDeviceChannelManager, Long, VideoDeviceChannel> implements VideoDeviceChannelService {


}


