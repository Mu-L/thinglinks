package com.mqttsnet.thinglinks.video.manager.device.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.video.entity.device.VideoDeviceInfo;
import com.mqttsnet.thinglinks.video.manager.device.VideoDeviceInfoManager;
import com.mqttsnet.thinglinks.video.mapper.device.VideoDeviceInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 流媒体设备信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-05-15 17:00:56
 * @create [2025-05-15 17:00:56] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class VideoDeviceInfoManagerImpl extends SuperManagerImpl<VideoDeviceInfoMapper, VideoDeviceInfo> implements VideoDeviceInfoManager {
    private final VideoDeviceInfoMapper videoDeviceInfoMapper;


    @Override
    public VideoDeviceInfo getOneByDeviceIdentification(String deviceIdentification) {
        QueryWrap<VideoDeviceInfo> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(deviceIdentification), VideoDeviceInfo::getDeviceIdentification, deviceIdentification);
        return videoDeviceInfoMapper.selectOne(queryWrap);
    }
}


