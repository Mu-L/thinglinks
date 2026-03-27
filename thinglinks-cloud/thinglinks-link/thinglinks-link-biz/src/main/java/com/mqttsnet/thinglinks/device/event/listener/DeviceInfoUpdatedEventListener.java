package com.mqttsnet.thinglinks.device.event.listener;

import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.thinglinks.device.event.DeviceInfoUpdatedEvent;
import com.mqttsnet.thinglinks.device.event.handler.DeviceInfoUpdatedCacheHandler;
import com.mqttsnet.thinglinks.device.event.source.DeviceInfoUpdatedEventSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Description:
 * 设备信息更新事件监听器
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/6/5
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceInfoUpdatedEventListener {

    private final DeviceInfoUpdatedCacheHandler deviceInfoUpdatedCacheHandler;

    @EventListener
    public CompletableFuture<Boolean> handleDeviceInfoUpdatedEvent(DeviceInfoUpdatedEvent event) {
        Map<String, String> localMap = ContextUtil.getLocalMap();
        return CompletableFuture.supplyAsync(() -> {
            ContextUtil.setLocalMap(localMap);
            if (event.getSource() instanceof DeviceInfoUpdatedEventSource source) {
                return deviceInfoUpdatedCacheHandler.handleDeviceInfoUpdatedCache(source.getDeviceIdentificationList());
            }

            log.warn("无效的事件源类型: {}", event.getSource().getClass());
            return false;
        }).exceptionally(ex -> {
            log.error("处理设备更新事件失败", ex);
            return null;
        });
    }
}
