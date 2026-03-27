package com.mqttsnet.thinglinks.video.manager.media.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.video.entity.media.VideoStreamPush;
import com.mqttsnet.thinglinks.video.manager.media.VideoStreamPushManager;
import com.mqttsnet.thinglinks.video.mapper.media.VideoStreamPushMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 视频推流信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-07-07 19:19:57
 * @create [2024-07-07 19:19:57] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class VideoStreamPushManagerImpl extends SuperManagerImpl<VideoStreamPushMapper, VideoStreamPush> implements VideoStreamPushManager {

}


