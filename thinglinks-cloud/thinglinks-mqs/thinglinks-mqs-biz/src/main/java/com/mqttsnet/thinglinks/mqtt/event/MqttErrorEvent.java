package com.mqttsnet.thinglinks.mqtt.event;

import com.mqttsnet.thinglinks.enumeration.MqttEventEnum;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @program: thinglinks-cloud
 * @description: MqttPingEvent
 * @author: mqttsnet
 * @e-mainl: 13733918655@163.com
 * @date: 2025-03-07 15:53
 **/
@Getter
public class MqttErrorEvent extends ApplicationEvent {
    private final String message;
    private final MqttEventEnum eventType;

    public MqttErrorEvent(Object source, MqttEventEnum eventType, String message) {
        super(source);
        this.message = message;
        this.eventType = eventType;
    }

}
