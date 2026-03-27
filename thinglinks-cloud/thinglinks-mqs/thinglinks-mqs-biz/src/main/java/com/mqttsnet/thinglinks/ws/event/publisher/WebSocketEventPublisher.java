package com.mqttsnet.thinglinks.ws.event.publisher;

import com.mqttsnet.thinglinks.enumeration.WsEventEnum;
import com.mqttsnet.thinglinks.ws.event.WebSocketConnectEvent;
import com.mqttsnet.thinglinks.ws.event.WebSocketDisconnectEvent;
import com.mqttsnet.thinglinks.ws.event.WebSocketErrorEvent;
import com.mqttsnet.thinglinks.ws.event.WebSocketPingEvent;
import com.mqttsnet.thinglinks.ws.event.WebSocketReceiveEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @program: thinglinks-cloud
 * @description: webSocket 事件发布器 用于发布WebSocket事件
 * @author: mqttsnet
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 11:07
 **/
@Component
@Slf4j
public class WebSocketEventPublisher {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void publishWebSocketConnectEvent(WsEventEnum eventEnum, String message) {
        log.info("Publishing WebSocket CONNECT event: message={}", message);
        eventPublisher.publishEvent(new WebSocketConnectEvent(this, eventEnum, message));
    }

    public void publishWebSocketDisconnectEvent(WsEventEnum eventEnum, String message) {
        log.info("Publishing WebSocket Disconnect event: message={}", message);
        eventPublisher.publishEvent(new WebSocketDisconnectEvent(this, eventEnum, message));
    }

    public void publishWebSocketReceiveEvent(WsEventEnum eventEnum, String message) {
        log.info("Publishing WebSocket RECEIVE event: message={}", message);
        eventPublisher.publishEvent(new WebSocketReceiveEvent(this, eventEnum, message));
    }

    public void publishWebSocketErrorEvent(WsEventEnum eventEnum, String message) {
        log.info("Publishing WebSocket ERROR event: message={}", message);
        eventPublisher.publishEvent(new WebSocketErrorEvent(this, eventEnum, message));
    }

    public void publishWebSocketPingEvent(WsEventEnum eventEnum, String message) {
        log.info("Publishing WebSocket PING event: message={}", message);
        eventPublisher.publishEvent(new WebSocketPingEvent(this, eventEnum, message));
    }
}
