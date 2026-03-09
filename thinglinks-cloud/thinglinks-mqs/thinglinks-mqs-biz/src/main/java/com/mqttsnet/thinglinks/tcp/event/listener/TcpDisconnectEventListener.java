package com.mqttsnet.thinglinks.tcp.event.listener;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.cache.utils.CacheUtil;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.common.cache.link.device.DeviceCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.CommonConstants;
import com.mqttsnet.thinglinks.device.enumeration.DeviceActionTypeEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceConnectStatusEnum;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyUserFacade;
import com.mqttsnet.thinglinks.tcp.event.TcpDisconnectEvent;
import com.mqttsnet.thinglinks.tcp.service.TcpEventActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @program: thinglinks-cloud
 * @description: TCP DISCONNECT事件监听器
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 00:45
 **/
@Component
@Slf4j
public class TcpDisconnectEventListener {

    @Autowired
    private DeviceOpenAnyUserFacade deviceOpenAnyUserApi;

    @Autowired
    private TcpEventActionService tcpEventActionService;

    @Autowired
    private CacheUtil cacheUtil;


    /**
     * 发布TCP DISCONNECT事件
     *
     * @param event 事件消息
     */
    @EventListener
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void publishTcpDisconnectEvent(TcpDisconnectEvent event) {
        log.info("Publishing TCP DISCONNECT event: message={}", event.getMessage());
        JSONObject messageObj = JSONUtil.parseObj(event.getMessage());
        String clientId = String.valueOf(messageObj.get(CommonConstants.CLIENT_ID));

        Optional<DeviceCacheVO> deviceCacheVOOptional = cacheUtil.getObjectFromCache(DeviceCacheKeyBuilder.build(clientId).getKey(), DeviceCacheVO.class);
        if (deviceCacheVOOptional.isEmpty()) {
            return;
        }

        R<Boolean> updateDeviceConnectionStatus = deviceOpenAnyUserApi.updateDeviceConnectionStatus(clientId, DeviceConnectStatusEnum.OFFLINE.getValue());
        if (!updateDeviceConnectionStatus.getIsSuccess()) {
            log.error("Failed to update the device connection status to OFFLINE, clientId={}", clientId);
            return;
        }

        String describable = Optional.ofNullable(DeviceConnectStatusEnum.OFFLINE.getValue())
                .flatMap(DeviceConnectStatusEnum::fromValue)
                .map(DeviceConnectStatusEnum::getDesc)
                .map(desc -> "The device connection status is updated to " + desc)
                .orElse("The device connection status is updated to OFFLINE");

        tcpEventActionService.saveTcpEventAction(event.getMessage(), DeviceActionTypeEnum.CLOSE, describable);
    }
}
