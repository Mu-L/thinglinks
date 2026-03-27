package com.mqttsnet.thinglinks.entity.mqtt;

import com.mqttsnet.thinglinks.enumeration.MqttEventEnum;

/**
 * @program: thinglinks-cloud
 * @description: MQTT协议事件类
 * @packagename: com.mqttsnet.thinglinks.mqtt.entity
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 00:10
 **/
public class MqttProtocolEvent {
    private final MqttEventEnum eventEnum;
    private final String message;

    public MqttProtocolEvent(MqttEventEnum eventEnum, String message) {
        this.eventEnum = eventEnum;
        this.message = message;
    }

    public MqttEventEnum getEventEnum() {
        return eventEnum;
    }

    public String getMessage() {
        return message;
    }
}
