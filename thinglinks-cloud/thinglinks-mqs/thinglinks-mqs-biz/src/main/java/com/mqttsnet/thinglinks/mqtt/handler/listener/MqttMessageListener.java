package com.mqttsnet.thinglinks.mqtt.handler.listener;

import com.mqttsnet.thinglinks.entity.mqtt.MqttMessageEvent;
import com.mqttsnet.thinglinks.entity.mqtt.source.MqttMessageEventSource;
import com.mqttsnet.thinglinks.mqtt.handler.TopicHandler;
import com.mqttsnet.thinglinks.mqtt.handler.factory.MqttTopicHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * @program: thinglinks-cloud
 * @description:
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 14:43
 **/
@Component
@Slf4j
public class MqttMessageListener {

    private final MqttTopicHandlerFactory mqttTopicHandlerFactory;

    @Autowired
    public MqttMessageListener(MqttTopicHandlerFactory mqttTopicHandlerFactory) {
        this.mqttTopicHandlerFactory = mqttTopicHandlerFactory;
    }

    @EventListener
    public void handleMessage(MqttMessageEvent event) {
        // 校验并处理Topic
        processTopic(event.getEventSource());
    }

    private void processTopic(MqttMessageEventSource eventSource) {
        // Using the TopicHandlerFactory to find the matching handler
        TopicHandler matchingHandler = mqttTopicHandlerFactory.findMatchingHandler(eventSource.getTopic());
        try {
            if (matchingHandler != null) {
                matchingHandler.handle(eventSource);
            } else {
                // Handle the case when no topic matches
                log.warn("No topic handler found for topic: {}", eventSource.getTopic());
            }
        } catch (Exception e) {
            log.error("Exception occurred while handling topic: {},qos: {}, payloadBytes: {}", eventSource.getTopic(), eventSource.getQos(), eventSource.getPayloadBytes(), e);
        }
    }
}