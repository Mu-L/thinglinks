package com.mqttsnet.thinglinks.tcp.event.publisher;

import com.mqttsnet.thinglinks.enumeration.TcpEventEnum;
import com.mqttsnet.thinglinks.tcp.event.TcpCloseEvent;
import com.mqttsnet.thinglinks.tcp.event.TcpConnectEvent;
import com.mqttsnet.thinglinks.tcp.event.TcpDisconnectEvent;
import com.mqttsnet.thinglinks.tcp.event.TcpPingEvent;
import com.mqttsnet.thinglinks.tcp.event.TcpPublishEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @program: thinglinks-cloud
 * @description: Tcp事件发布器 用于发布Tcp事件
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2025-01-11 14:07
 **/
@Component
@Slf4j
public class TcpEventPublisher {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void publishTcpConnectEvent(TcpEventEnum eventEnum, String message) {
        log.info("Publishing TCP CONNECT event: message={}", message);
        eventPublisher.publishEvent(new TcpConnectEvent(this, eventEnum, message));
    }

    public void publishTcpCloseEvent(TcpEventEnum eventEnum, String message) {
        log.info("Publishing TCP CLOSE event: message={}", message);
        eventPublisher.publishEvent(new TcpCloseEvent(this, eventEnum, message));
    }

    public void publishTcpDisconnectEvent(TcpEventEnum eventEnum, String message) {
        log.info("Publishing TCP DISCONNECT event: message={}", message);
        eventPublisher.publishEvent(new TcpDisconnectEvent(this, eventEnum, message));
    }

    public void publishTcpPublishEvent(TcpEventEnum eventEnum, String message) {
        log.info("Publishing TCP PUBLISH event: message={}", message);
        eventPublisher.publishEvent(new TcpPublishEvent(this, eventEnum, message));
    }

    public void publishTcpPingEvent(TcpEventEnum eventEnum, String message) {
        log.info("Publishing TCP PING event: message={}", message);
        eventPublisher.publishEvent(new TcpPingEvent(this, eventEnum, message));
    }

}
