package com.mqttsnet.thinglinks.mqtt.event.publisher;

import com.mqttsnet.thinglinks.enumeration.MqttEventEnum;
import com.mqttsnet.thinglinks.mqtt.event.MqttCloseEvent;
import com.mqttsnet.thinglinks.mqtt.event.MqttConnectEvent;
import com.mqttsnet.thinglinks.mqtt.event.MqttDisconnectEvent;
import com.mqttsnet.thinglinks.mqtt.event.MqttErrorEvent;
import com.mqttsnet.thinglinks.mqtt.event.MqttPingEvent;
import com.mqttsnet.thinglinks.mqtt.event.MqttPublishEvent;
import com.mqttsnet.thinglinks.mqtt.event.MqttSubscribeEvent;
import com.mqttsnet.thinglinks.mqtt.event.MqttUnsubscribeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @program: thinglinks-cloud
 * @description: MQTT事件发布器 用于发布MQTT事件
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 00:07
 **/
@Component
@Slf4j
public class MqttEventPublisher {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void publishMqttConnectEvent(MqttEventEnum eventEnum, String message) {
        eventPublisher.publishEvent(new MqttConnectEvent(this, eventEnum, message));
    }

    public void publishMqttCloseEvent(MqttEventEnum eventEnum, String message) {
        eventPublisher.publishEvent(new MqttCloseEvent(this, eventEnum, message));
    }

    public void publishMqttDisconnectEvent(MqttEventEnum eventEnum, String message) {
        eventPublisher.publishEvent(new MqttDisconnectEvent(this, eventEnum, message));
    }

    public void publishMqttPublishEvent(MqttEventEnum eventEnum, String message) {
        eventPublisher.publishEvent(new MqttPublishEvent(this, eventEnum, message));
    }

    public void publishMqttSubscribeEvent(MqttEventEnum eventEnum, String message) {
        eventPublisher.publishEvent(new MqttSubscribeEvent(this, eventEnum, message));
    }

    public void publishMqttUnsubscribeEvent(MqttEventEnum eventEnum, String message) {
        eventPublisher.publishEvent(new MqttUnsubscribeEvent(this, eventEnum, message));
    }

    public void publishMqttPingEvent(MqttEventEnum eventEnum, String message) {
        eventPublisher.publishEvent(new MqttPingEvent(this, eventEnum, message));
    }

    public void publishMqttErrorEvent(MqttEventEnum eventEnum, String message) {
        eventPublisher.publishEvent(new MqttErrorEvent(this, eventEnum, message));
    }
}
