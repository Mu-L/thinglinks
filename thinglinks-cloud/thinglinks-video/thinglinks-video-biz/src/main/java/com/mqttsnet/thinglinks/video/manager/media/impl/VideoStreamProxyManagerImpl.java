package com.mqttsnet.thinglinks.video.manager.media.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.video.entity.media.VideoStreamProxy;
import com.mqttsnet.thinglinks.video.manager.media.VideoStreamProxyManager;
import com.mqttsnet.thinglinks.video.mapper.media.VideoStreamProxyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 视频拉流代理信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-07-05 22:32:06
 * @create [2024-07-05 22:32:06] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class VideoStreamProxyManagerImpl extends SuperManagerImpl<VideoStreamProxyMapper, VideoStreamProxy> implements VideoStreamProxyManager {

}


