package com.mqttsnet.thinglinks.mqtt.event;

import com.mqttsnet.thinglinks.enumeration.MqttEventEnum;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @program: thinglinks-cloud
 * @description: MqttDisconnectEvent
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 01:52
 **/
@Getter
public class MqttDisconnectEvent extends ApplicationEvent {
    private final String message;
    private final MqttEventEnum eventType;

    public MqttDisconnectEvent(Object source, MqttEventEnum eventType, String message) {
        super(source);
        this.message = message;
        this.eventType = eventType;
    }

}