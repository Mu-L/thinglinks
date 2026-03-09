package com.mqttsnet.thinglinks.entity.mqtt;

import com.mqttsnet.thinglinks.entity.mqtt.source.MqttMessageEventSource;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * MQTT消息事件
 * <p>
 * 封装MQTT PUBLISH消息的完整事件数据
 * 通过事件源对象提供结构化的消息信息
 *
 * @program: thinglinks-cloud
 * @description: MqttMessageEvent
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 14:42
 **/
@Getter
public class MqttMessageEvent extends ApplicationEvent {
    /**
     * 事件源数据
     * 包含MQTT消息的所有相关信息
     */
    private final MqttMessageEventSource eventSource;

    /**
     * 构造函数
     *
     * @param source 事件源对象，包含完整的MQTT消息数据
     */
    public MqttMessageEvent(MqttMessageEventSource source) {
        super(source);
        this.eventSource = source;
    }
}
