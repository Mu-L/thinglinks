package com.mqttsnet.thinglinks.mqtt.event.listener;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.cache.utils.CacheUtil;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.common.constant.CommonConstants;
import com.mqttsnet.thinglinks.common.cache.link.device.DeviceCacheKeyBuilder;
import com.mqttsnet.thinglinks.device.enumeration.DeviceActionTypeEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceConnectStatusEnum;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyUserFacade;
import com.mqttsnet.thinglinks.mqtt.event.MqttConnectEvent;
import com.mqttsnet.thinglinks.service.DeviceEventActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @program: thinglinks-cloud
 * @description: MQTT CONNECT事件监听器
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 00:36
 **/
@Component
@Slf4j
public class MqttConnectEventListener {

    @Autowired
    private DeviceOpenAnyUserFacade deviceOpenAnyUserApi;

    @Autowired
    private DeviceEventActionService deviceEventActionService;

    @Autowired
    private CacheUtil cacheUtil;

    /**
     * 发布MQTT CONNECT事件
     *
     * @param event 事件消息
     */
    @EventListener
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void publishMqttConnectEvent(MqttConnectEvent event) {
        log.info("Publishing MQTT CONNECT event: message={}", event.getMessage());
        JSONObject messageObj = JSONUtil.parseObj(event.getMessage());
        String clientId = String.valueOf(messageObj.get(CommonConstants.CLIENT_ID));
        Optional<DeviceCacheVO> deviceCacheVOOptional = cacheUtil.getObjectFromCache(DeviceCacheKeyBuilder.build(clientId).getKey(), DeviceCacheVO.class);
        if (deviceCacheVOOptional.isEmpty()) {
            return;
        }

        R<Boolean> updateDeviceConnectionStatus = deviceOpenAnyUserApi.updateDeviceConnectionStatus(clientId, DeviceConnectStatusEnum.ONLINE.getValue());
        if (!updateDeviceConnectionStatus.getIsSuccess()) {
            log.error("Failed to update device connection status to ONLINE, clientId={}", clientId);
            return;
        }

        String describable = Optional.ofNullable(DeviceConnectStatusEnum.ONLINE.getValue())
                .flatMap(DeviceConnectStatusEnum::fromValue)
                .map(DeviceConnectStatusEnum::getDesc)
                .map(desc -> "The device connection status is updated to " + desc)
                .orElse("The device connection status is updated to ONLINE");


        deviceEventActionService.saveDeviceEventAction(event.getMessage(), DeviceActionTypeEnum.CONNECT, describable);
    }
}
