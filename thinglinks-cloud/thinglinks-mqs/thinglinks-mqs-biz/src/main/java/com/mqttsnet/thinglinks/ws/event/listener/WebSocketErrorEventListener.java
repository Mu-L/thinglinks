package com.mqttsnet.thinglinks.ws.event.listener;

import java.util.Optional;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mqttsnet.basic.cache.utils.CacheUtil;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.common.cache.link.device.DeviceCacheKeyBuilder;
import com.mqttsnet.thinglinks.device.enumeration.DeviceActionTypeEnum;
import com.mqttsnet.thinglinks.service.DeviceEventActionService;
import com.mqttsnet.thinglinks.ws.event.WebSocketPingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * @program: thinglinks-cloud
 * @description: WebSocket Error事件监听器
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2025-03-06 14:48
 **/
@Component
@Slf4j
public class WebSocketErrorEventListener {

    @Autowired
    private DeviceEventActionService deviceEventActionService;

    @Autowired
    private CacheUtil cacheUtil;

    /**
     * 发布WebSocket ERROR事件
     *
     * @param event 事件消息
     */
    @EventListener
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void publishWebSocketPingEvent(WebSocketPingEvent event) {
        log.info("Publishing WebSocket ERROR event: message={}", event.getMessage());

        JSONObject map = JSONUtil.parseObj(event.getMessage());
        String clientId = String.valueOf(map.get("clientId"));
        Optional<DeviceCacheVO> deviceCacheVOOptional = cacheUtil.getObjectFromCache(DeviceCacheKeyBuilder.build(clientId).getKey(), DeviceCacheVO.class);
        if (deviceCacheVOOptional.isEmpty()) {
            return;
        }

        String describable = event.getMessage();
        deviceEventActionService.saveDeviceEventAction(event.getMessage(), DeviceActionTypeEnum.RETRY, describable);
    }
}
