package com.mqttsnet.thinglinks.device.event.publisher;

import cn.hutool.json.JSONUtil;
import com.mqttsnet.thinglinks.device.event.DeviceInfoUpdatedEvent;
import com.mqttsnet.thinglinks.device.event.source.DeviceInfoUpdatedEventSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Description:
 * 设备事件发布器
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/6/5
 */
@Component
@Slf4j
public class DeviceEventPublisher {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void publishDeviceInfoUpdatedEvent(DeviceInfoUpdatedEventSource source) {
        log.info("Publishing Device Info Updated event:{}", JSONUtil.toJsonStr(source));
        eventPublisher.publishEvent(new DeviceInfoUpdatedEvent(source));
    }
}
