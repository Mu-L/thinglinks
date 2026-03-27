package com.mqttsnet.thinglinks.mqtt.event;

import com.mqttsnet.thinglinks.enumeration.MqttEventEnum;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @program: thinglinks-cloud
 * @description: MqttPublishEvent
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 01:13
 **/
@Getter
public class MqttPublishEvent extends ApplicationEvent {
    private final String message;
    private final MqttEventEnum eventType;

    public MqttPublishEvent(Object source, MqttEventEnum eventType, String message) {
        super(source);
        this.message = message;
        this.eventType = eventType;
    }

}

