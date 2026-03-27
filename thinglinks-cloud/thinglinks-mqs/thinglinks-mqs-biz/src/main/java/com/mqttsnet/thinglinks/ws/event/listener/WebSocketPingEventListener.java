package com.mqttsnet.thinglinks.ws.event.listener;

import java.util.Optional;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.cache.utils.CacheUtil;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.common.cache.link.device.DeviceCacheKeyBuilder;
import com.mqttsnet.thinglinks.device.enumeration.DeviceActionTypeEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceConnectStatusEnum;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyUserFacade;
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
 * @description: WebSocket PING事件监听器
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 00:48
 **/
@Component
@Slf4j
public class WebSocketPingEventListener {

    @Autowired
    private DeviceOpenAnyUserFacade deviceOpenAnyUserApi;

    @Autowired
    private DeviceEventActionService deviceEventActionService;

    @Autowired
    private CacheUtil cacheUtil;

    /**
     * 发布WebSocket PING事件
     *
     * @param event 事件消息
     */
    @EventListener
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void publishWebSocketPingEvent(WebSocketPingEvent event) {
        log.info("Publishing WebSocket PING event: message={}", event.getMessage());

        JSONObject messageObj = JSONUtil.parseObj(event.getMessage());
        String clientId = String.valueOf(messageObj.get("clientId"));
        Optional<DeviceCacheVO> deviceCacheVOOptional = cacheUtil.getObjectFromCache(DeviceCacheKeyBuilder.build(clientId).getKey(), DeviceCacheVO.class);
        if (deviceCacheVOOptional.isEmpty()) {
            return;
        }

        Optional.ofNullable(messageObj.get("heartbeatTime"))
                .map(Object::toString)
                .map(Long::parseLong)
                .ifPresent(heartbeatTime -> {
                    R<Boolean> reportDeviceHeartbeat = deviceOpenAnyUserApi.reportDeviceHeartbeat(clientId, heartbeatTime);
                    if (!reportDeviceHeartbeat.getIsSuccess()) {
                        log.error("Failed to report device heartbeat, clientId={}", clientId);
                    }
                });

        String describable = Optional.ofNullable(DeviceConnectStatusEnum.ONLINE.getValue())
                .flatMap(DeviceConnectStatusEnum::fromValue)
                .map(DeviceConnectStatusEnum::getDesc)
                .map(desc -> "The device connection status is updated to " + desc + " via a ping operation")
                .orElse("The device connection status is updated to ONLINE via a ping operation");


        deviceEventActionService.saveDeviceEventAction(event.getMessage(), DeviceActionTypeEnum.PING, describable);
    }
}
